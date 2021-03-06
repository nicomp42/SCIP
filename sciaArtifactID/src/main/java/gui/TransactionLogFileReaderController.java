package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader.GeneralLogReader;
import edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader.TransactionLogReaderResults;
import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.DatabaseEngine;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.FullColumnName;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import lib.SQLUtils;

/**
 * GUI for reading the DB transaction log file
 * @author nicomp
 */
public class TransactionLogFileReaderController {

	@FXML TextArea txaLogFile, txaLog;
	@FXML Button btnRead, btnBrowse, btnFilterToAdHocOnly, btnParse, btnClearLogFileArea, btnCopyQueriesToProject, btnDoEverything;
	@FXML Label lblStatus;
	private Scene myScene;
	private Stage myStage;
	@FXML TextField txtMaxLines;
	@FXML TextField txtHostName, txtLoginName, txtPassword;
	@FXML void btnRead_OnClick(ActionEvent event) {tryToReadLogFile();}
	@FXML void btnBrowse_OnClick(ActionEvent event) {browseForLogFile();}
	@FXML void btnFilterToAdHocOnly_OnClick(ActionEvent event) {filterToAdHocOnly();}
	@FXML void btnParse_OnClick(ActionEvent event) {ParseAdHocQuerys(getStringsFromTextArea());}
	@FXML void btnCopyQueriesToProject_OnClick(ActionEvent event) {copyQuerysToProject(getStringsFromTextArea());}
	@FXML void btnClearLogFileArea_OnClick(ActionEvent event) {clearLogFileArea();}
	@FXML void btnDoEverything_OnClick(ActionEvent event) {askToPerformEndToEndProcessingOfTransactionLog();}

	public TransactionLogFileReaderController() {
	} 

	@FXML
	private void initialize() { // Automagically called by JavaFX
//		Log.logProgress("LogFileReaderController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("LogFileReaderController.initialize(): " + e.getLocalizedMessage());
		}
//		Log.logProgress("LogFileReaderController.Initialize() complete");
	}
	private void setTheScene() {
		btnParse.setVisible(false);
		btnFilterToAdHocOnly.setVisible(false);
		btnClearLogFileArea.setVisible(false);
		btnCopyQueriesToProject.setVisible(false);
		txaLogFile.setText(Config.getConfig().getDefaultTransactionLogFilePath());
		txtHostName.setText(Config.getConfig().getMySQLDefaultHostname());
		txtLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
		txtPassword.setText(Config.getConfig().getMySQLDefaultPassword());
		txtMaxLines.setText("500");
		lblStatus.setText("");
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
	
	private void tryToReadLogFile() {
		try {
			readLogFile(Integer.valueOf(txtMaxLines.getText()));
			btnParse.setVisible(true);
			btnFilterToAdHocOnly.setVisible(true);
			btnClearLogFileArea.setVisible(true);
		} catch (Exception ex) {
			Log.logError("LogFileReaderController.tryToReadLogFile(): " + ex.getLocalizedMessage());
		}
	}
	public void readLogFile(int maxLines) {
		clearLogFileArea();
		GeneralLogReader glr = new GeneralLogReader();
		int totalRecords = glr.readFromServer(txaLogFile.getText(), txaLog, maxLines);
		lblStatus.setText(totalRecords + " record" + (totalRecords != 1 ? "s" : "") + " read.");
	}
	public void browseForLogFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(Config.getConfig().getDefaultTransactionLogFilePath()));
		fileChooser.setTitle("Select the log file. It must be available for read operations.");
		Stage stage = (Stage) this.btnBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			txaLogFile.setText(file.getAbsolutePath());
		}
	}
	private ArrayList<String> getStringsFromTextArea() {
		String[] rows = txaLog.getText().split("\n");
		ArrayList<String> myArrayList = new ArrayList<String>();
		Collections.addAll(myArrayList, rows);
		return myArrayList;
	}
	public void filterToAdHocOnly() {
		ArrayList<String> myArrayList = getStringsFromTextArea();
		clearLogFileArea();
		FilterOutEverythingButAdHocSelectQueries(myArrayList);
		btnCopyQueriesToProject.setVisible(true);
	}
	public void FilterOutEverythingButAdHocSelectQueries(ArrayList<String> lines) {
		DatabaseEngine databaseEngine = Config.getConfig().getDatabaseEngine();		// TODO generalize
		StringBuilder sanitizedSQL = new StringBuilder();
		java.sql.Connection connection = SQLUtils.openJDBCConnection(new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", txtHostName.getText(), txtLoginName.getText(), txtPassword.getText(),""));
		for (String s: lines) {
			if (databaseEngine.isAdHocQuery(s, sanitizedSQL, connection) ) {
				if (!databaseEngine.checkForSystemTableInSQL(sanitizedSQL.toString())) {
					txaLog.appendText(sanitizedSQL + "\n");
				}
			}
		}
	}
	public void copyQuerysToProject(ArrayList<String> querys) {
		try {
			int projectID = 0;
			String currentProjectName = Config.getConfig().getCurrentSchemaChangeImpactProject().getProjectName();
			// Look up the ProjectID of the current project name
			if (currentProjectName.trim().length() > 0) {
				try {
					projectID = Config.getConfig().getProjectID(currentProjectName);
				} catch (Exception ex) {}
			}
			edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation connectionInformation = new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", txtHostName.getText(), txtLoginName.getText(), txtPassword.getText(),"");
			for (String s: querys) {
				// Write the string to the ad-hoc query table. if we have a project, write that too.
				SQLUtils.executeActionQuery(connectionInformation, "INSERT INTO `seq-am`.`tadhocquery` (projectID, SQLStatement) VALUES(" + String.valueOf(projectID) + ", " + Utils.QuoteMeDouble(s) + ")");
			}
		} catch (Exception ex) {
			Log.logError("TransactionLogFileReaderController.copyQuerysToProject(): " + ex.getLocalizedMessage());
		}
	}
	public QueryDefinitions ParseAdHocQuerys(ArrayList<String> querys) {
		DatabaseEngine databaseEngine = Config.getConfig().getDatabaseEngine();
		QueryDefinitions queryDefinitions = new QueryDefinitions();
		String adHocQueryName = "adhocQuery";
		int queryCounter = 1;
		StringBuilder sqlReduced = new StringBuilder();
		java.sql.Connection connection = SQLUtils.openJDBCConnection(new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", txtHostName.getText(), txtLoginName.getText(), txtPassword.getText(),""));
		try {
			for (String s: querys) {
				if (databaseEngine.isAdHocQuery(s, sqlReduced, connection) ) {
					if (!databaseEngine.checkForSystemTableInSQL(sqlReduced.toString())) {
						QueryDefinition queryDefinition = new QueryDefinition(txtHostName.getText(), 
								                                              txtLoginName.getText(), 
								                                              txtPassword.getText(), 
								                                              new QueryTypeSelect(), 
								                                              adHocQueryName + queryCounter,
								                                              sqlReduced.toString(),
								                                              "");
						queryCounter++;
						queryDefinition.crunchIt();
						queryDefinitions.addQueryDefinition(queryDefinition);
					}
				}
			}
/*			for (QueryDefinition qd : queryDefinitions) {
				txaLog.appendText(qd.getSql() + "\n");
				for (QueryAttribute qa : qd.getQueryAttributes()) {
					txaLog.appendText(qa.getFullyQualifiedName() + "\n");
				}
			} */
		} catch(Exception ex) {
			Log.logError("TransactionLogFileReaderController.parseQuerys(): " + ex.getLocalizedMessage());
		}
		return queryDefinitions;
	}
	private void clearLogFileArea() {
		txaLog.clear();
		// Hide the buttons to process the log file area if the log file area is empty
		btnParse.setVisible(false);
		btnFilterToAdHocOnly.setVisible(false);
		btnCopyQueriesToProject.setVisible(false);
	}
	/***
	 * Read the log, filter down to Ad-hoc queries, write them into the database for the current project. Woo Hoo
	 */
	private void askToPerformEndToEndProcessingOfTransactionLog() {
		int projectID = Config.getConfig().getProjectID(Config.getConfig().getCurrentSchemaChangeImpactProject().getProjectName());
		if (projectID == 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Continue without a project?");
			alert.setHeaderText("Since you have not selected a project, the project ID in the database table, " + Config.getConfig().getArtifactTableName() + " will be zero.");
			alert.setContentText("You may want to select a project first.");
			alert.showAndWait().ifPresent(rs -> {
			    if (rs == ButtonType.OK) {
					Alert innerAlert = new Alert(AlertType.CONFIRMATION);
					innerAlert.setTitle("Do Everything?");
					innerAlert.setHeaderText("Read the entire transaction log, filter the ad-hoc queries, copy them to the database for this project. ");
					innerAlert.setContentText("The log file you selected must be available for reading and the DB Engine must be running. Are you ready?");
					innerAlert.showAndWait().ifPresent(innerRs -> {
					    if (innerRs == ButtonType.OK) {
						    	performEndToEndProcessingOfTransactionLog();
					    }
					});
			    }
			});
		}
	}
	/***
	 * Read the transaction file, filter the ad-hoc queries, write them to the system database
	 */
	private void performEndToEndProcessingOfTransactionLog() {
		try {
			clearLogFileArea();
			int projectID = Config.getConfig().getProjectID(Config.getConfig().getCurrentSchemaChangeImpactProject().getProjectName());
			ConnectionInformation connectionInformation = new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", txtHostName.getText(), txtLoginName.getText(), txtPassword.getText(),"");
			TransactionLogReaderResults transactionLogReaderResults = GeneralLogReader.doEverything(txaLogFile.getText(),
															          connectionInformation, 
															          projectID,
															          true);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Transaction Log Processing Results");
			alert.setHeaderText("");
			String msg = transactionLogReaderResults.getTotalRecords() + " transaction log entries processed."; 
	        msg += "\n";
	        if (transactionLogReaderResults.getLastErrorMsg().trim().length() > 0) {
	        	msg +=  "Last error message: " + transactionLogReaderResults.getLastErrorMsg();
	        } else {
	        	msg += "No error reported.";
	        }
			alert.setContentText(msg);
			alert.showAndWait();
			ReadFromDBIntoTextArea(connectionInformation, projectID, txaLog);
		} catch (Exception ex) {
			Log.logError("TransactionLogFileReaderController.performEndToEndProcessingOfTransactionLog(): " + ex.getLocalizedMessage());
		}
	}
	private void ReadFromDBIntoTextArea(ConnectionInformation connectionInformation, int projectID, TextArea txaLog) {
		try {
			java.sql.Connection connection = SQLUtils.openJDBCConnection(new edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation("", 
	                connectionInformation.getHostName(), 
	                connectionInformation.getLoginName(),
	                connectionInformation.getPassword(),""));
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `seq-am`.tartifact WHERE ProjectID = " + projectID + " ORDER BY artifactID ASC");
			java.sql.ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if (resultSet.getString("Artifact").equals("Qty")) {
					System.out.println("goo");
				}
				txaLog.appendText((new FullColumnName(resultSet.getString("SchemaName"),
													  resultSet.getString("tableName"),
													  resultSet.getString("Artifact"))).toString());
				if (resultSet.getString("alias").trim().length() > 0) {
					txaLog.appendText(" AS " + resultSet.getString("alias"));
				}
				txaLog.appendText("\n");
			}
		} catch (Exception ex) {
			Log.logError("TransactionLogFileReaderController.ReadFromDBIntoTextArea(): " + ex.getLocalizedMessage());
		}
	}
}

