/*
 * Entry point for the newSchemaChangeImpactProject form
 */
package gui;

import javafx.scene.control.Button;
import java.io.File;
import java.util.Optional;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * GUI for creating a new Schema Change Impact Project
 * @author nicomp
 */
public class NewSchemaChangeImpactProject extends Application {
	   @FXML
	    private AnchorPane apMainWindow;
	   @FXML
	   private Button btnSave, btnProjectFilePath, btnPentahoProjectDirectory;
	   @FXML
	   private TextField txtProjectName, txtProjectFilePath, txtNeo4jDBName;
	   @FXML
	   private TextArea txaPentahoProjectDirectory, txaComment;
	   private Stage stage;
	   private ChangeListener<Boolean> myListener;
	   private boolean btnSaveClicked;

	public void setStage(Stage stage) {
		this.stage = stage;
    	setTheScene();
//    	stage.setOnCloseRequest(event -> { checkToCloseWindow(); });	// Remove the listeners so they don't fire when the form closes

    	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    	    @Override
    	    public void handle(WindowEvent event) {
    	    	checkToCloseWindow(event);
    	    }
    	});
	}
    public NewSchemaChangeImpactProject() {
    	super();
    	myListener = new ChangeListener<Boolean>() {@Override public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) { if (newPropertyValue) { } else {areAllRequiredFieldsFilledIn();}}};
		setBtnSaveClicked(false);
    }
    /**
     * Check to see if the user wants to throw away the input on this form.
     * @return true if data can be discarded and the form can be closed
     */
    public boolean checkToDiscardData() {
    	boolean dataCanBeDiscarded = true;
    	if (areAllRequiredFieldsFilledIn() == true) {	// Are all the required fields filled in?
    		Alert alert = new Alert(AlertType.CONFIRMATION);		// http://code.makery.ch/blog/javafx-dialogs-official/
    		alert.setTitle("Close the window?");
    		alert.setHeaderText("Your new project will not be created.");
    		alert.setContentText("Are you sure?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			dataCanBeDiscarded = true;
    		} else {
    			dataCanBeDiscarded = false;
    		}
    	}
    	return dataCanBeDiscarded;
    }
	/**
	 * Called by FXMLLoader after content is created.
	 */
    @FXML
    private void initialize() {
    }
    private void checkToCloseWindow(WindowEvent event) {
    	if (btnSaveClicked == true) {		// User clicked the Save button
        	Log.logProgress("NewSchemaChangeImpactProject.checkToCloseWindow(): User clicked close button.");
    		removeListeners();
    		// The close will continue...
    	} else {
    		if (checkToDiscardData()) {
        		removeListeners();
        		// The close will continue...
            	Log.logProgress("NewSchemaChangeImpactProject.checkToCloseWindow(): User confirmed that data should be discarded.");
    		} else {
            	Log.logProgress("NewSchemaChangeImpactProject.checkToCloseWindow(): User cancelled the close.");
    			event.consume();	// Stop the window from closing
    		}
    	}
    }
    /**
     * Remove the event listeners from the text fields.
     */
    private void removeListeners() {
    	txtProjectName.focusedProperty().removeListener(myListener);
    	txtProjectFilePath.focusedProperty().removeListener(myListener);
    	txtNeo4jDBName.focusedProperty().removeListener(myListener);
    	txaPentahoProjectDirectory.focusedProperty().removeListener(myListener);
    }
    private void addListeners() {
		// Add a bunch of listeners for the fields that must be filled in before the Save button can be enabled
		txtProjectName.focusedProperty().addListener(myListener);
		txtProjectFilePath.focusedProperty().addListener(myListener);
		txtNeo4jDBName.focusedProperty().addListener(myListener);
		txaPentahoProjectDirectory.focusedProperty().addListener(myListener);
    }
	private void setTheScene() {
		addListeners();
		txtNeo4jDBName.setText(Config.getDefaultNeo4jDBDirectory());
		txaPentahoProjectDirectory.setText(Config.getDefaultPentahoProjectDirectory());
		btnSave.setDisable(true);	// Disable the Save button until the user fills in the fields
		areAllRequiredFieldsFilledIn();
	}
	/**
	 * Check to see if the Save button should be enabled
	 */
	private boolean areAllRequiredFieldsFilledIn() {
		boolean allfieldsFilled = false;
		if ((txtProjectName.getText().trim().length() > 0) &&
			(txtProjectFilePath.getText().trim().length() > 0) &&
			(txtNeo4jDBName.getText().trim().length() > 0) &&
			(txaPentahoProjectDirectory.getText().trim().length() > 0))  {
			btnSave.setDisable(false);
			allfieldsFilled = true;
		}
		return allfieldsFilled;
	}
	/**
	 * User clicked Save
	 * @param event The event that called this method
	 */
	@FXML
	private void btnSave_OnClick(ActionEvent event) {
		try {
			Log.logProgress("NewSchemaChangeImpactProject.btnSave_OnClick(): Creating Project. Project Name = " + txtProjectName.getText().trim() + ", Project File Path = " +  txtProjectFilePath.getText().trim() + ", Neo4j DB Directory = " + txtNeo4jDBName.getText().trim());
			SchemaChangeImpactProject scip = new SchemaChangeImpactProject(txtProjectName.getText().trim(), txtProjectFilePath.getText().trim(), txtNeo4jDBName.getText().trim());
			scip.setComment(txaComment.getText());
			scip.setPentahoProjectDirectory(txaPentahoProjectDirectory.getText());
			scip.buildFileStructure();
			scip.save();
			Config.getConfig().setCurrentSchemaChangeImpactProject(scip);
			setBtnSaveClicked(true);				// User closed form by clicking Save.
	        Stage stage = (Stage) btnSave.getScene().getWindow();
	        stage.close();
		} catch (Exception ex) {
			Log.logError("NewSchemaChangeImpactProject.btnSave_OnClick(): " + ex.getLocalizedMessage() + ": \n: " + ex.getStackTrace());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Save failed");
			alert.setHeaderText("Cannot create project");
			alert.setContentText(ex.getLocalizedMessage());
			alert.showAndWait();
		}
	}
    @FXML
    void mnuFileExit_OnAction(ActionEvent event) {
    	// TODO ask if the user is sure
    	// get a handle to the stage
        Stage stage = (Stage) this.btnSave.getScene().getWindow();		// I picked some arbitrary control to look up the scene.
        stage.close();
    }
    @FXML
    protected void btnProjectFilePath_OnClick(ActionEvent event) {
    	// docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
    	DirectoryChooser  directoryChooser  = new DirectoryChooser ();
    	directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	directoryChooser.setTitle("Create Neo4j Database");
    	Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
        File file = directoryChooser.showDialog(stage);
        try {
        	txtProjectFilePath.setText(file.getAbsolutePath());
        } catch (Exception ex) {}
    }
    @FXML
    protected void btnPentahoProjectDirectory_OnClick(ActionEvent event) {
    	// docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
    	DirectoryChooser  directoryChooser = new DirectoryChooser();
    	directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	directoryChooser.setTitle("Select the Pentaho Project Directory");
    	Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
    	File file = directoryChooser.showDialog(stage);
        txaPentahoProjectDirectory.setText(file.getAbsolutePath());
    }
	public boolean isBtnSaveClicked() {return btnSaveClicked;}
	private void setBtnSaveClicked(boolean btnSaveClicked) {this.btnSaveClicked = btnSaveClicked;}
	@Override
	public void start(Stage primaryStage) throws Exception {}

/******************************************* Test Main ***************************************************************
    public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		Log.logProgress("NewSchemaChangeImpactProject.start() starting...");
		try {
//			Open the New Project Window
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	fxmlLoader.setLocation(getClass().getResource("newSchemaChangeImpactProject.fxml"));
	    	Parent root = fxmlLoader.load();
	    	NewSchemaChangeImpactProject foo = fxmlLoader.getController();
	    	Stage stage = new Stage();
	    	foo.setStage(stage);
	    	stage.initModality(Modality.APPLICATION_MODAL);
	    	stage.setOpacity(1);
	    	stage.setTitle("Add a new Schema Change Impact Project");
	    	stage.setScene(new Scene(root, 450, 450));

	    	stage.showAndWait();
		} catch (Exception ex) {
			Log.logError("NewSchemaChangeImpactProject.start():" + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
			Log.logProgress("NewSchemaChangeImpactProject.start() complete");
	}
*/
}
