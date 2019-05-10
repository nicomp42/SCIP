/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * 
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

import java.io.BufferedReader;
import java.io.FileReader;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.database.MySQLDatabase;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
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
	public static int doEverything(String logFilePath, ConnectionInformation connectionInformation, int projectID, boolean clearDatabaseFirst) {
		int totalRecords = 0;
		StringBuilder sanitizedSQL = new StringBuilder();
		MySQLDatabase mySQLDatabase = new MySQLDatabase();		// TODO generalize
		java.sql.Connection connection = SQLUtils.openJDBCConnection(new edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation("", 
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
				
//				if (gle.doWeCare()) {
					if (gle.getText().toUpperCase().startsWith("ALTER")) {
						buffer= br.readLine();
						gle.setText(gle.getText() + " " + buffer.trim());
					}
//					System.out.println(gle.getText());
					if (mySQLDatabase.isAdHocQuery(gle.toString(), sanitizedSQL, connection) ) {
						Log.logProgress("GeneraLogReader.doEverything(): " + sanitizedSQL.toString()); 
						// It's an ad-hoc query but does it reference a system table? If so, we don't want it
						if (!mySQLDatabase.checkForSystemTableInSQL(sanitizedSQL.toString())) {
							SQLUtils.executeActionQuery(connection, "INSERT INTO `seq-am`.`tadhocquery` (projectID, SQLStatement) VALUES(" + String.valueOf(projectID) + ", " + Utils.QuoteMeDouble(sanitizedSQL.toString()) + ")");
						}
					}
//				}
			}
		} catch (Exception ex) {
			Log.logError("GeneralLogReader.doEverything(): " + ex.getLocalizedMessage());
		}
		try {br.close();} catch (Exception ex) {}
		try {connection.close();} catch(Exception ex) {}
		return totalRecords;
	}

	/***
	 * Read records that we care about: SELECT queries that access tables in our databases
	 */
	public int readFromServer(String logFilePath, TextArea txaOutput, int maxLines) {
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