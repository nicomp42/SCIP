/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;

import java.time.ZonedDateTime;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.log.LogMessage;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Display debug messages in a dedicated window
 * @author nicomp
 */
public class DebugController /* extends Application */ {

	@FXML TextArea txaProgress, txaNeo4jQuerys, txaErrors, txaSQLQueryParsing;
	@FXML Button btnClear, btnClearNeo4jQuerys, btnClearErrors, btnClearSQLQueryParsing, btnClearAllLogs;
	@FXML AnchorPane apDebug;
	private Scene myScene;
	private double  btnClearHeight, btnClearWidth, btnClearSQLQueryParsingWidth, btnClearNeo4jQuerysWidth, btnClearNeo4jQuerysHeight, btnClearErrorsWidth, btnClearErrorsHeight, btnClearSQLQueryParsingHeight;
	private Stage myStage;

	public DebugController() {
	}

	@FXML
	private void initialize() { // Automagically called by JavaFX
//		Log.logProgress("DebugController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
//			Log.logError("DebugController.Initialize(): " + e.getLocalizedMessage());
		}
//		Log.logProgress("DebugController.Initialize() complete");
	}
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
/*		This didn't work and I was sick of messing with it.
		apDebug.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        //System.out.println("Height: " + newSceneHeight);
		    	try {
			        txaDebug.setPrefHeight(myScene.getHeight() - 40);
			        txaNeo4jQuerys.setPrefHeight(myScene.getHeight() - 40);
			        relocateBtnClear();
			        relocateBtnClearNeo4jQuerys();
		        } catch (Exception ex) {}
		    }
		});
*/
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
	public void btnClear_OnClick(ActionEvent event) {
		txaProgress.setText("");
	}
	@FXML
	public void btnClearErrors_OnClick(ActionEvent event) {
		txaErrors.setText("");
	}
	@FXML
	public void btnClearNeo4jQuerys_OnClick(ActionEvent event) {
		txaNeo4jQuerys.setText("");
	}
	@FXML
	public void btnClearSQLQueryParsing_OnClick(ActionEvent event) {
		txaSQLQueryParsing.setText("");
	}
	public void writeProgress(LogMessage msg) {
		try {
			txaProgress.appendText("\n" + msg.toString());
		} catch (Exception ex) {
			System.out.println("DebugController.writeProgress: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	}
	public void writeProgress(String msg) {
		try {
			txaProgress.appendText("\n" + msg);
		} catch (Exception ex) {
			System.out.println("DebugController.writeProgress: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	}
	public void writeNeo4jQueryInfo(String msg) {
		try {
			txaNeo4jQuerys.appendText("\n" + msg);
		} catch (Exception ex) {
			System.out.println("DebugController.writeNeo4jQueryInfo: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	}
	public void writeQueryParseProgress(String msg) {
		try {
			txaSQLQueryParsing.appendText("\n" + msg);
		} catch (Exception ex) {
			System.out.println("DebugController.writeQueryParseProgress: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	}
	public void writeError(LogMessage logMessage) {
		try {
			txaErrors.appendText("\n" + logMessage.toString());
		} catch (Exception ex) {
			System.out.println("DebugController.writeError: " + ex.getLocalizedMessage() + "\n message = " + logMessage.toString());
		}
	}

	public void writeError(String msg) {
		try {
			txaErrors.appendText("\n" + msg);
		} catch (Exception ex) {
			System.out.println("DebugController.writeError: " + ex.getLocalizedMessage() + "\n message = " + msg);
		}
	}
	private void clearAllLogs() {
		txaProgress.setText("");
		txaErrors.setText("");
		txaNeo4jQuerys.setText("");
		txaSQLQueryParsing.setText("");
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
}

