/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.log.LogMessage;
import edu.UC.PhD.CodeProject.nicholdw.log.WriteLogMessage;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;

/**
 * Display debug messages in a dedicated window
 * @author nicomp
 */
public class DebugController implements javafx.fxml.Initializable, WriteLogMessage {

	@FXML TextArea txaProgress, txaNeo4jQuerys, txaErrors, txaSQLQueryParsing, txaFilePath;
	@FXML Button btnClear, btnClearNeo4jQuerys, btnClearErrors, btnClearSQLQueryParsing, btnClearAllLogs, btnBrowse, btnSaveToFile;
	@FXML AnchorPane apDebug;
	private Scene myScene;
	private double  btnClearHeight, btnClearWidth, btnClearSQLQueryParsingWidth, btnClearNeo4jQuerysWidth, btnClearNeo4jQuerysHeight, btnClearErrorsWidth, btnClearErrorsHeight, btnClearSQLQueryParsingHeight;
	private Stage myStage;
	private Timeline heartbeat;
	public DebugController() {
	}

/*	@FXML
	private void initialize() { // Automagically called by JavaFX
//		Log.logProgress("DebugController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
//			Log.logError("DebugController.Initialize(): " + e.getLocalizedMessage());
		}
//		Log.logProgress("DebugController.Initialize() complete");
	}*/
	private void setTheScene() {
		btnClearHeight = 25;					// btnClear.getHeight();
		btnClearWidth = btnClear.getWidth();							//108;
		btnClearNeo4jQuerysWidth = btnClearNeo4jQuerys.getWidth();		//137;
		btnClearNeo4jQuerysHeight = 25;			//btnClearNeo4jQuerys.getHeight();
		btnClearErrorsWidth = btnClearErrors.getWidth();		//137;
		btnClearErrorsHeight = 25;			//btnClearNeo4jQuerys.getHeight();

		apDebug.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	try {
			        txaProgress.setPrefWidth(myScene.getWidth() - 10);
			        txaNeo4jQuerys.setPrefWidth(myScene.getWidth() - 10);
			        txaErrors.setPrefWidth(myScene.getWidth() - 10);
			        txaSQLQueryParsing.setPrefWidth(myScene.getWidth() - 10);

//			        txaProgress.setPrefHeight(myScene.getHeight() - 50);
//			        txaNeo4jQuerys.setPrefHeight(myScene.getHeight() - 50);
//			        txaErrors.setPrefHeight(myScene.getHeight() - 50);

			        relocateBtnClear();
			        relocateBtnClearNeo4jQuerys();
			        relocateBtnClearErrors();
			        relocateBtnClearSQLQueryParsing();
		    	} catch (Exception ex) {}
		    }
		});
	}
	private void relocateBtnClearSQLQueryParsing() {
		btnClearSQLQueryParsing.relocate(txaSQLQueryParsing.getWidth() - btnClearSQLQueryParsing.getWidth() - 3, txaSQLQueryParsing.getHeight() + 7);
	}
	private void relocateBtnClear() {
		btnClear.relocate(txaProgress.getWidth() - btnClear.getWidth() - 3, txaProgress.getHeight() + 7);
	}
	private void relocateBtnClearNeo4jQuerys() {
		btnClearNeo4jQuerys.relocate(txaProgress.getWidth() - btnClearNeo4jQuerys.getWidth() - 3, txaNeo4jQuerys.getHeight() + 7);
	}
	private void relocateBtnClearErrors() {
		btnClearErrors.relocate(txaErrors.getWidth() - btnClearErrors.getWidth() - 3, txaErrors.getHeight() + 7);
	}
	@FXML
	public void btnClearAllLogs_OnClick(ActionEvent event) {
		clearAllLogs();
	}
	@FXML
	public void btnBrowse_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select the location for the log files");
		Stage stage = (Stage) this.btnBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaFilePath.setText(file.getAbsolutePath());}
	}
	@FXML
	public void btnSaveToFile_OnClick(ActionEvent event) {
		saveAllLogFiles(txaFilePath.getText());
	}
	@FXML
	public void btnClear_OnClick(ActionEvent event) {txaProgress.setText("");}
	@FXML
	public void btnClearErrors_OnClick(ActionEvent event) {txaErrors.setText("");}
	@FXML
	public void btnClearNeo4jQuerys_OnClick(ActionEvent event) {txaNeo4jQuerys.setText("");}
	@FXML
	public void btnClearSQLQueryParsing_OnClick(ActionEvent event) {txaSQLQueryParsing.setText("");}
	/**
	 * Write a log message to the view
	 * @param logMessage the message to be written. Should have a message type so it goes to the correct place
	 */
	public void writeLogMessage(LogMessage logMessage) {
		switch (logMessage.getLogMessageType()) {
	    	case progress:
	    		writeProgressMessage(logMessage);
	    		break;
	    	case error:
	    		writeErrorMessage(logMessage);
	    		break;
	    	case neo4jQuery:
	    		writeNeo4jQueryHistoryMessage(logMessage);
	    		break;
	    	case queryParseProgress:
	    		writeQueryParseProgressMessage(logMessage);
	    		break;
	    	default:
	    		// We don't know the message type but we cannot simply discard it!
	    		writeProgressMessage(logMessage);
	    		break;
		}	
	}
	private void writeProgressMessage(LogMessage msg) {
		try {
			txaProgress.appendText("\n" + msg.toString());
		} catch (Exception ex) {
			System.out.println("DebugController.writeProgress: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	}
/*	public void writeProgress(String msg) {
		try {
			txaProgress.appendText("\n" + msg);
		} catch (Exception ex) {
			System.out.println("DebugController.writeProgress: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	} */
	private void writeNeo4jQueryHistoryMessage(LogMessage msg) {
		try {
			txaNeo4jQuerys.appendText("\n" + msg.toString());
		} catch (Exception ex) {
			System.out.println("DebugController.writeNeo4jQueryInfo: " + ex.getLocalizedMessage() + "\n message = " + msg.toString());
		}
	}
	private void writeQueryParseProgressMessage(LogMessage msg) {
		try {
			txaSQLQueryParsing.appendText("\n" + msg.toString());
		} catch (Exception ex) {
			System.out.println("DebugController.writeQueryParseProgress: " + ex.getLocalizedMessage() + "\n message = " + msg.toString());
		}
	}
	private void writeErrorMessage(LogMessage logMessage) {
		try {
			txaErrors.appendText("\n" + logMessage.toString());
		} catch (Exception ex) {
			System.out.println("DebugController.writeError: " + ex.getLocalizedMessage() + "\n message = " + logMessage.toString());
		}
	}
/*	public void writeError(String msg) {
		try {
			txaErrors.appendText("\n" + msg);
		} catch (Exception ex) {
			System.out.println("DebugController.writeError: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	} */
	private void clearAllLogs() {
		txaProgress.setText("");
		txaErrors.setText("");
		txaNeo4jQuerys.setText("");
		txaSQLQueryParsing.setText("");
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
	static class MessageTask extends TimerTask {
		@Override
		public void run() {
			  System.out.println((new Date()) + " beep");
			  Log.flushAllBuffers();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTheScene();
//		System.out.println("DebugController.initialize(URL location, ResourceBundle resources)...");
		startHeartbeat();
	}
	private void startHeartbeat() {
		heartbeat = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
//				  System.out.println("beep");
				  Log.flushAllBuffers();
		    }
		}));
		heartbeat.setCycleCount(Timeline.INDEFINITE);
		heartbeat.play();
	}
	public void stopHeartbeat() {
		heartbeat.stop();
	}
	private void saveAllLogFiles(String filePath) {
		saveLogFile(txaProgress,        "Progress",     filePath, false);
		saveLogFile(txaNeo4jQuerys,     "Neo4jQueries", filePath, false);
		saveLogFile(txaErrors,          "Errors",       filePath, false);
		saveLogFile(txaSQLQueryParsing, "QueryParsing", filePath, false);
	}
	private void saveLogFile(TextArea ta, String fileNamePrefix, String filePath, Boolean append) {
		String[] lines = ta.getText().split("\n");
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(filePath + "/" + fileNamePrefix + ".txt", append);
			bw = new BufferedWriter(fw);
			for (String s: lines) {
				bw.write(s); bw.newLine(); 
			}
			bw.close();
		} catch (Exception ex) {
			Log.logError("DebugController.saveLogFile(): " + ex.getLocalizedMessage());
		}
	}
}
