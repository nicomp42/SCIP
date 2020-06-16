/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * 
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

import java.io.BufferedReader;
import java.io.FileReader;

import com.mysql.jdbc.PreparedStatement;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.DatabaseEngine;
import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.MySQLDatabaseEngine;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import javafx.scene.control.TextArea;
import lib.SQLUtils;
/***
 * Process the database transaction log file
 * @author nicomp
 */
public class GeneralLogReader {

	public static void main(String[] args) {
//		GeneralLogReader demo = new GeneralLogReader();
//		demo.readFromServer("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Data\\device.log");
	}
	/***
	 * Read records that we care about: SELECT queries that access tables in our databases
	 */
/*	public void readFromServer(String logFilePath) {
		BufferedReader br = null;
		try {
			FileReader generalLogFile = new FileReader(logFilePath);
			br = new BufferedReader(generalLogFile);
			// Skip the header and the column headers
			br.readLine();
			br.readLine();
			br.readLine();

			String buffer;
			while (true) {
				buffer = br.readLine();
				//System.out.println(buffer);
				MySQLGeneralLogEntry gle = new MySQLGeneralLogEntry(buffer); 

				if (gle.doWeCare()) {
					if (gle.getText().toUpperCase().startsWith("ALTER")) {
						buffer= br.readLine();
						gle.setText(gle.getText() + " " + buffer.trim());
					}
					System.out.println(gle.toString());
				}
			}
		} catch (Exception ex) {
			System.out.println("GeneralLogReader.readFromServer(): " + ex.getLocalizedMessage());
		}
		try {br.close();} catch (Exception ex) {}
	}
*/
	/***
	 * Read the transaction log, filter the ad-hoc queries, write all of them to the project database ad-hoc query table
	 * @param logFilePath 
	 * @param connectionInformation What's needed to connect to the database
	 * @param projectID the primary key of a project in tProject, or zero. No validation is performed here. 
	 * @return The number of records written to the database
	 */
	public static TransactionLogReaderResults doEverything(String logFilePath, ConnectionInformation connectionInformation, int projectID, boolean clearDatabaseFirst) {
		Log.logProgress("GeneralLogReader.doEverything(" + logFilePath + ")");
		TransactionLogReaderResults transactionLogReaderResults = null;
		try {
			transactionLogReaderResults = loadFromTransactionLogIntoDatabase(logFilePath, connectionInformation, projectID, clearDatabaseFirst);
			extractAndLoadArtifacts(connectionInformation, projectID, transactionLogReaderResults);
		} catch (Exception ex) {
			Log.logError("GeneralLogReader.doEverything(): " + ex.getLocalizedMessage() + " logFilePath = (" + logFilePath + ")");
		}
		return transactionLogReaderResults;
	}
	private static void extractAndLoadArtifacts(ConnectionInformation connectionInformation, int projectID, TransactionLogReaderResults transactionLogReaderResults) {
		Log.logProgress("GeneralLogReader.processTransactionLogRecordsInDatabase()");
		int totalQueriesProcessed = 0;
		java.sql.Connection connection = SQLUtils.openJDBCConnection(new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", 
				                                                     connectionInformation.getHostName(), 
				                                                     connectionInformation.getLoginName(),
				                                                     connectionInformation.getPassword(),""));
		String currentSchemaName = "";
		try {
			SQLUtils.executeActionQuery(connection, "DELETE FROM `seq-am`.tArtifact WHERE ProjectID = " + projectID + ";");		// Clear any artifacts already captured for this project
			// Be sure to read by adhocqueryID so the records are in physical order. The time stamp doesn't have sufficient resolution
			String selectSQL = "SELECT * FROM `seq-am`.tadhocquery WHERE ProjectID = " + projectID + " ORDER BY adhocqueryID ASC";
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
			java.sql.ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String sql;
				sql = resultSet.getString("sqlStatement");
				String[] parts = null;
				parts = sql.split(" ");
				if (parts[0].toUpperCase().equals("USE")) {
					currentSchemaName = parts[1].replace("`", "");
				} else {
					Log.logProgress("GeneralLogReader.extractAndLoadArtifacts(): Parsing sql: " + sql);
					// OK. Parse the query to get all the artifacts. Woo hoo.
					QueryDefinition queryDefinition = new QueryDefinition("", "", "", null, "", sql, currentSchemaName);
					queryDefinition.crunchIt();
					for (QueryAttribute qa: queryDefinition.getQueryAttributes()) {
						String schemaName = "";
						if (qa.getSchemaName().trim().length() == 0) {
							schemaName = currentSchemaName;
						} else {
							schemaName = qa.getSchemaName();
						} 
						String sqlInsert = "INSERT INTO `seq-am`." + Config.getConfig().getArtifactTableName() + "(artifact, schemaName, tableName, alias, ProjectID)" +
                                     "VALUES(" + 
								     Utils.quoteMeSingle(qa.getAttributeName()) + 
                                     ", " + 
                                     Utils.quoteMeSingle(schemaName) + 
                                     ", " + 
                                     Utils.quoteMeSingle(qa.getContainerName()) + 
                                     ", " + 
                                     Utils.quoteMeSingle(qa.aliasNameListToString()) + 
                                     ", " + projectID + ")";
						SQLUtils.executeActionQuery(connection, sqlInsert); 
					}
					totalQueriesProcessed++;
				}
			}
		} catch (Exception ex) {
			Log.logError("GeneralLogReader.processTransactionLogRecordsInDatabase(): " + ex.getLocalizedMessage() );
		}
		try {transactionLogReaderResults.setTotalQueriesProcessed(totalQueriesProcessed);} catch (Exception ex) {}
		try {connection.close();} catch(Exception ex) {}
	}
	private static TransactionLogReaderResults loadFromTransactionLogIntoDatabase(String logFilePath, ConnectionInformation connectionInformation, int projectID, boolean clearDatabaseFirst) {
		Log.logProgress("GeneralLogReader.loadFromTransactionLogIntoDatabase()");
		TransactionLogReaderResults transactionLogReaderResults = new TransactionLogReaderResults();
		int totalRecords = 0;
		StringBuilder sanitizedSQL = new StringBuilder();
		DatabaseEngine databaseEngine = Config.getConfig().getDatabaseEngine();
		java.sql.Connection connection = SQLUtils.openJDBCConnection(new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", 
																																		connectionInformation.getHostName(), 
																																		connectionInformation.getLoginName(),
																																		connectionInformation.getPassword(),""));
		SQLUtils.executeActionQuery(connection, "DELETE FROM `seq-am`.tadhocquery WHERE ProjectID = " + projectID + ";");		// Clear any queries already captured for this project
		BufferedReader br = null;
		try {
			FileReader generalLogFile = new FileReader(logFilePath);
			br = new BufferedReader(generalLogFile);
			String buffer;
			while (true) {
				buffer = br.readLine();
				if (buffer == null) {break;}		// End of file
				totalRecords++;
				MySQLGeneralLogEntry gle = new MySQLGeneralLogEntry(buffer);

				if (gle.getText().toUpperCase().startsWith("USE")) {
					Log.logProgress("GeneraLogReader.doEverything(): found a USE statement: " + gle.getText().toUpperCase()); 
					SQLUtils.executeActionQuery(connection, "INSERT INTO `seq-am`.`tadhocquery` (projectID, SQLStatement) VALUES(" + String.valueOf(projectID) + ", " + Utils.QuoteMeDouble(gle.getText().toUpperCase()) + ")");
				} else {
					if (gle.getText().toUpperCase().startsWith("ALTER")) {
						buffer= br.readLine();
						gle.setText(gle.getText() + " " + buffer.trim());
					} //					System.out.println(gle.getText());
					if (databaseEngine.isAdHocQuery(gle.toString(), sanitizedSQL, connection) ) {
						Log.logProgress("GeneraLogReader.doEverything(): sanitized SQL is an ad hoc query: " + sanitizedSQL.toString()); 
						// It's an ad-hoc query but does it reference a system table? If so, we don't want it
						if (!databaseEngine.checkForSystemTableInSQL(sanitizedSQL.toString())) {
							Log.logProgress("GeneraLogReader.doEverything(): adding to artifact database."); 
							SQLUtils.executeActionQuery(connection, "INSERT INTO `seq-am`.`tadhocquery` (projectID, SQLStatement) VALUES(" + String.valueOf(projectID) + ", " + Utils.QuoteMeDouble(sanitizedSQL.toString()) + ")");
						}
					}
				}
			}
		} catch (Exception ex) {
			Log.logError("GeneralLogReader.doEverything(): " + ex.getLocalizedMessage());
			transactionLogReaderResults.setLastErrorMsg(ex.getLocalizedMessage());
		}
		try {br.close();} catch (Exception ex) {}
		try {connection.close();} catch(Exception ex) {}
		transactionLogReaderResults.setTotalRecords(totalRecords);
		return transactionLogReaderResults;
	}

	/***
	 * Read records that we care about: SELECT queries that access tables in our databases
	 */
	public int readFromServer(String logFilePath, TextArea txaOutput, int maxLines) {
		Log.logProgress("GeneraLogReader.readFromServer(): " + logFilePath); 
		int totalRecords = 0;
		BufferedReader br = null;
		try {
			FileReader generalLogFile = new FileReader(logFilePath);
			br = new BufferedReader(generalLogFile);
			int lineCount = 0;
			String buffer;
			while (true) {
				buffer = br.readLine();
				if (buffer == null) {break;}		// End of file
				totalRecords++;
				MySQLGeneralLogEntry gle = new MySQLGeneralLogEntry(buffer);
//				if (gle.doWeCare()) {
					if (gle.getText().toUpperCase().startsWith("ALTER")) {
						buffer= br.readLine();
						gle.setText(gle.getText() + " " + buffer.trim());
					}
					txaOutput.appendText(gle.toString() + "\n");
					lineCount++;
					if (lineCount == maxLines) {break;}
//				}
			}
		} catch (Exception ex) {
			Log.logError("GeneralLogReader.readFromServer(): " + ex.getLocalizedMessage());
		}
		try {br.close();} catch (Exception ex) {}
		return totalRecords;
	}
}
