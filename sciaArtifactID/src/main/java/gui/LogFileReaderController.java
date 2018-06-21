package gui;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.time.ZonedDateTime;


import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader.GeneralLogReader;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
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
 * @author nicomp
 */
public class LogFileReaderController {

	@FXML TextArea txaLogFile;
	@FXML Button btnRead, btnBrowse;
	private Scene myScene;
	private Stage myStage;
	@FXML TextField txtLoginName, txtPassword;
	@FXML void btnRead_OnClick(ActionEvent event) {readLogFile();}
	@FXML void btnBrowse_OnClick_OnClick(ActionEvent event) {browseForLogFile();}

	public LogFileReaderController() {
	}

	@FXML
	private void initialize() { // Automagically called by JavaFX
//		Log.logProgress("LogFileReaderController.Initialize() starting...");
		try {
			setTheScene();
			txaLogFile.setText("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Data\\device.log");
		} catch (Exception e) {
//			Log.logError("LogFileReaderController.Initialize(): " + e.getLocalizedMessage());
		}
//		Log.logProgress("LogFileReaderController.Initialize() complete");
	}
	private void setTheScene() {

	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
	
	public void readLogFile() {
		GeneralLogReader demo = new GeneralLogReader();
		demo.readFromServer(txaLogFile.getText());
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
}

