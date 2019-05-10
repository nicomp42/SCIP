package gui;

import java.sql.ResultSetMetaData;
import java.time.ZonedDateTime;


import edu.UC.PhD.CodeProject.nicholdw.Config;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import lib.SQLUtils;

/**
 * @author nicomp
 */
public class SubmitSQLController {

	@FXML TextArea txaSQL, txaResults;
	@FXML Button btnSubmitSQL, btnCancel;
	@FXML AnchorPane apSubmitSQL;
	private Scene myScene;
	private Stage myStage;
	@FXML TextField txtDatabaseName;

	public SubmitSQLController() {
	}

	@FXML
	private void initialize() { // Automagically called by JavaFX
//		Log.logProgress("SubmitSQLController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
//			Log.logError("SubmitSQLController.Initialize(): " + e.getLocalizedMessage());
		}
//		Log.logProgress("SubmitSQLController.Initialize() complete");
	}
	private void setTheScene() {

	}
	@FXML
	public void btnSubmit_OnClick(ActionEvent event) {
		java.sql.ResultSet resultSet = null;
		resultSet = SQLUtils.executeQuery(Config.getConfig().getMySQLDefaultHostname(), 
										 txtDatabaseName.getText(),
				              			 Config.getConfig().getMySQLDefaultLoginName(), 
				              			 Config.getConfig().getMySQLDefaultPassword(), 
				              			 txaSQL.getText());
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (resultSet.next()) {
		    	for (int i = 1; i <= columnCount; i++) {
		    		String text = resultSet.getString(i);
		    		txaResults.appendText(text + " ");
		    	}
		    	txaResults.appendText("\n");
		    }
		    resultSet.close();
		} catch (Exception ex) {
			Log.logError("SubmitSQLController.btnSubmit_OnClick(): " + ex.getLocalizedMessage()); 
			txaResults.appendText(ex.getLocalizedMessage());
		}
	}
	public void btnCancel_OnClick(ActionEvent event) {

	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage;}
}

