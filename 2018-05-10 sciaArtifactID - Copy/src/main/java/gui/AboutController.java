package gui;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
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
public class AboutController /* extends Application */ {

	@FXML Button btnAboutOK;
	@FXML AnchorPane apAboutWindow;
	@FXML Label lblVersion;
	private Scene myScene;
	private Stage myStage;

	public AboutController() {
	}

	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("AboutController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("AboutController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("AboutController.Initialize() complete");
	}
	@FXML
	public void btnAboutOK_OnClick(ActionEvent event) {
		closeMe();
	}
	private void closeMe() {
	    Stage stage = (Stage) btnAboutOK.getScene().getWindow();	// get a handle to the stage
	    stage.close();												// do what you have to do
	}

	private void setTheScene() {
		lblVersion.setText("Version " + Config.getConfig().getVersion());
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
}

