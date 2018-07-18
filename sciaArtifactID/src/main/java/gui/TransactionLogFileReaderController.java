package gui;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader.GeneralLogReader;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryUtils;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryType;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import lib.SQLUtils;

/**
 * GUI for reading the DB transaction log file
 * @author nicomp
 */
public class TransactionLogFileReaderController {

	@FXML TextArea txaLogFile, txaLog;
	@FXML Button btnRead, btnBrowse, btnFilterToAdHocOnly, btnParse;
	@FXML Label lblStatus;
	private Scene myScene;
	private Stage myStage;
	@FXML TextField txtMaxLines;
	@FXML TextField txtHostName, txtLoginName, txtPassword;
	@FXML void btnRead_OnClick(ActionEvent event) {tryToReadLogFile();}
	@FXML void btnBrowse_OnClick(ActionEvent event) {browseForLogFile();}
	@FXML void btnFilterToAdHocOnly_OnClick(ActionEvent event) {filterToAdHocOnly();}
	@FXML void btnParse_OnClick(ActionEvent event) {ParseAdHocQuerys(getStringsFromTextArea());}

	public TransactionLogFileReaderController() {
	} 

	@FXML
	private void initialize() { // Automagically called by JavaFX
//		Log.logProgress("LogFileReaderController.Initialize() starting...");
		try {
			setTheScene();
			txaLogFile.setText("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Data\\device.log");	// TODO: generalize this
			txtHostName.setText(Config.getConfig().getMySQLDefaultHostname());
			txtLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
			txtPassword.setText(Config.getConfig().getMySQLDefaultPassword());
			txtMaxLines.setText("500");
			lblStatus.setText("");
		} catch (Exception e) {
			Log.logError("LogFileReaderController.initialize(): " + e.getLocalizedMessage());
		}
//		Log.logProgress("LogFileReaderController.Initialize() complete");
	}
	private void setTheScene() {

	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
	
	private void tryToReadLogFile() {
		try {
			readLogFile(Integer.valueOf(txtMaxLines.getText()));
		} catch (Exception ex) {
			Log.logError("LogFileReaderController.tryToReadLogFile(): " + ex.getLocalizedMessage());
		}
	}
	public void readLogFile(int maxLines) {
		txaLog.clear();
		GeneralLogReader glr = new GeneralLogReader();
		int totalRecords = glr.readFromServer(txaLogFile.getText(), txaLog, maxLines);
		lblStatus.setText(totalRecords + " record" + (totalRecords != 1 ? "s" : "") + " read.");
	}
	public void browseForLogFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.setTitle("Select the log file");
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
		txaLog.clear();
		FilterOutEverythingButAdHocSelectQueries(myArrayList);
	}
	public void FilterOutEverythingButAdHocSelectQueries(ArrayList<String> lines) {
		for (String s: lines) {
			if (QueryUtils.isAdHocQuery(s, null) ) {
				txaLog.appendText(s + "\n");
			}
		}
	}
	public QueryDefinitions ParseAdHocQuerys(ArrayList<String> querys) {
		QueryDefinitions queryDefinitions = new QueryDefinitions();
		String adHocQueryName = "query";
		int queryCounter = 1;
		StringBuilder sqlReduced = new StringBuilder();
		try {
			for (String s: querys) {
				if (QueryUtils.isAdHocQuery(s, sqlReduced) ) {
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
		} catch(Exception ex) {
			Log.logError("TransactionLogFileReaderController.parseQuerys(): " + ex.getLocalizedMessage());
		}
		return queryDefinitions;
	}
}

