/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Display about window
 * @author nicomp
 */
public class ProjectManagerController /* extends Application */ {

	@FXML AnchorPane apProjectManager;
	private Scene myScene;
	private Stage myStage;
	@FXML void mnuFileExit_OnAction(ActionEvent event) {Platform.exit();}

	public ProjectManagerController() {
	} 

	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ProjectManagerController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("ProjectManager.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("ProjectManager.Initialize() complete");
	}
//	private void closeMe() {
//	    Stage stage = (Stage) btnAboutOK.getScene().getWindow();	// get a handle to the stage
//	    stage.close();												// do what you have to do
//	}

	private void setTheScene() {
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
}

