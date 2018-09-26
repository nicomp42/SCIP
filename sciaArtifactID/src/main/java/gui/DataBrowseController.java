package gui;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DataBrowseController {
	private Scene myScene;
	private Stage myStage;
	@FXML
	private Button btnOK;
	@FXML 
	private ScrollPane scpData;
	@FXML
	private TextArea txaData;
	
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("DataBrowseController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("DataBrowseController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("DataBrowseController.Initialize() complete");
	}

	private void setTheScene() {
	
	}
	public DataBrowseController() {
		
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
	
	public void appendToTextArea(String msg) {
		txaData.appendText(msg);
	}
}
