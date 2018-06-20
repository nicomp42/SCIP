/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Display about window
 * @author nicomp
 */
public class ProjectManagerController /* extends Application */ {
	private int projectID;
	@FXML AnchorPane apProjectManager;
	private Scene myScene;
	private Stage myStage;
	@FXML void mnuFileExit_OnAction(ActionEvent event) {Platform.exit();}

	public ProjectManagerController() {
		projectID = 0;		// This could get overridden in the setScene method, below
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
	public void setScene(Scene scene) {this.myScene = scene; projectID = 0;}
	public void setScene(Scene scene, int projectID) {this.myScene = scene; this.projectID = projectID;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
	
	/***
	 * Open an instance of the Project Manager window
	 * @param projectID Project ID from tProject table in the SEQ-AM system database, or zero if you don't need one. 
	 */
	public static void openProjectManagerWindow(int projectID) {
		try {
			FXMLLoader fxmlLoader = null;
			// Open the New Project Window
			fxmlLoader = new FXMLLoader(ProjectManagerController.class.getResource("ProjectManager.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			stage.setOpacity(1);
			stage.setTitle("Project Manager");
			Scene scene = new Scene(root);		//, 700, 450);
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {public void handle(WindowEvent we) {}});
			stage.setScene(scene);
			ProjectManagerController pmc = fxmlLoader.getController();
			pmc.setScene(scene, projectID);
			pmc.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openProjectManagerWindow():" + ex.getLocalizedMessage());
		}
	}	
}

