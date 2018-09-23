package gui;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Optional;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.database.SystemDatabaseConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lib.SQLUtils;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

/**
 * Display debug messages in a dedicated window
 * @author nicomp
 */
public class ConfigController /* extends Application */ {
//  <ListView fx:id="lvDatabaseConnections" prefHeight="100.0" prefWidth="450.0" GridPane.columnIndex="1" GridPane.rowIndex="0" /> 

	@FXML AnchorPane apConfig;
	@FXML Button btnSave, btnBrowseForCSVFolder;
	@FXML TextField txtNeo4jDefaultUser, txtNeo4jDefaultPassword, txtNeo4jTableToAttributeRelationName, txtNeo4jQueryToTableRelationName, txtMySQLDefaultLoginName,
				    txtMySQLDefaultPassword, txtMySQLDefaultHostname;
	@FXML TextArea txaCSVFolder, txaGrassURL, txaUserHomeDirectory, txaConfigFilePath;
	@FXML CheckBox cbUseTestData, cbSupressOutputToConsole;
	@FXML Tab tabMain, tabDatabase, tabNeo4j, tabFiles;
	@FXML TabPane tbpConfig;
	@FXML TextField txtSystemDatabaseLoginName, txtSystemDatabasePassword, txtSystemDatabaseHostName, txtSystemDatabaseSchemaName;
	@FXML ListView<String> lvProjectsOnFile;		//, lvDatabaseConnections;
	@FXML TableView<gui.GUIConnectionInformation> tvDatabaseConnections;
	private Scene myScene;
	private Stage myStage;
	private Boolean dataIsDirty;

	public ConfigController() {
	}

	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ConfigController.Initialize() starting...");
		try {
		} catch (Exception e) {
			Log.logError("ConfigController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("ConfigController.Initialize() complete");
	}
	@FXML void mnuFileExit_OnAction(ActionEvent event) {
		if (checkToDiscardData()) {
			myStage.close();		}
	}
	private void setTheScene() {
		dataIsDirty = false;
		scatter();
		loadListViewWithProjects(lvProjectsOnFile, Config.getConfig().getSystemDatabaseConnectionInformation());
//		ConnectionInformation.loadListViewWithDatabaseConnections(lvDatabaseConnections, null);
		ConnectionInformation.loadTableViewWithDatabaseConnections(tvDatabaseConnections, null);
		displaySaveButton();
		setUpChangeListeners();
		myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	checkToCloseWindow(we);
            }
        });
		lvProjectsOnFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	//Use ListView's getSelected Item
		        	String currentSelectedItem = lvProjectsOnFile.getSelectionModel().getSelectedItem();
		        	int projectID = Integer.valueOf(currentSelectedItem.split(" : ")[0]);
		   			ProjectManagerController.openProjectManagerWindow(projectID);
		        }
		    }
		});
		tvDatabaseConnections.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	processDatabaseConnectionsDoubleClick();                   
		        }
		    }
		});	
	}
	private void displaySaveButton() {btnSave.setVisible(dataIsDirty);}
	public void setScene(Scene scene) {
		this.myScene = scene;
	}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {
		this.myStage = myStage;
		setTheScene();
	}

	/**
	 * User clicked Save
	 * @param event The event that called this method
	 */
	@FXML
	private void btnSave_OnClick(ActionEvent event) {save();}
	@FXML private void btnBrowseForCSVFolder_OnClick(ActionEvent event) {
		browseForDefaultCSVFolder();
	}
	@FXML
	public void txaCSVFolder_OnClick(MouseEvent event) {
		// Don't do anything here. I left the event handler in case we need it later.
		/*
		if (event.getButton() == MouseButton.PRIMARY) {
			int clickCount = 0;
			clickCount = event.getClickCount();
			if (clickCount == 2) { // It's a double click! Stuff in some path for a test case.
				setDefaultCVSFilePath();
			}
		}
		*/
	}
	private void setDefaultCVSFilePath() {
		txaCSVFolder.setText(Config.getConfig().getNeo4jDefaultImportFilePath()); // A default location for the CSV files.
	}
	private void browseForDefaultCSVFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select default Neo4j import Directory");
		Stage stage = (Stage) this.btnSave.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {
			txaCSVFolder.setText(file.getAbsolutePath());
			setDirty(true);
		}
	}

	private Boolean scatter() {
		Boolean status = true;		// Hope for the best
		Log.logProgress("ConfigController.scatter()...");
		try {
			txtNeo4jDefaultUser.setText(Config.getConfig().getNeo4jDBDefaultUser());
			txtNeo4jDefaultPassword.setText(Config.getConfig().getNeo4jDBDefaultPassword());
			txtNeo4jTableToAttributeRelationName.setText(Config.getConfig().getNeo4jTableToAttributeRelationName());
			txtNeo4jQueryToTableRelationName.setText(Config.getConfig().getNeo4jQueryToTableRelationName());
			txtMySQLDefaultLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
			txtMySQLDefaultPassword.setText(Config.getConfig().getMySQLDefaultPassword());
			txtMySQLDefaultHostname.setText(Config.getConfig().getMySQLDefaultHostname());
			cbUseTestData.setSelected(Config.getConfig().getUseTestData());
			cbSupressOutputToConsole.setSelected(Config.getConfig().getSupressOutputToConsole());
			txaCSVFolder.setText(Config.getConfig().getNeo4jDefaultImportFilePath());
			txaGrassURL.setText(Config.getConfig().getGrassStyleSheetURL());
			txaUserHomeDirectory.setText(Config.getConfig().getUserHomeDirectory());
			txaConfigFilePath.setText(Config.getConfig().getAbsolutePathOfConfigFile());
			txtSystemDatabaseLoginName.setText(Config.getConfig().getSystemDatabaseConnectionInformation().getLoginName());
			txtSystemDatabasePassword.setText(Config.getConfig().getSystemDatabaseConnectionInformation().getPassword());
			txtSystemDatabaseHostName.setText(Config.getConfig().getSystemDatabaseConnectionInformation().getHostName());
			txtSystemDatabaseSchemaName.setText(Config.getConfig().getSystemDatabaseConnectionInformation().getSchemaName());
		} catch (Exception ex) {
			status = false;
			Log.logError("ConfigController.scatter(): " + ex.getLocalizedMessage());
		}
		Log.logProgress("ConfigController.scatter() done.");
		return status;
	}
	private Boolean gather() {
		Log.logProgress("ConfigController.gather()...");
		Boolean status = true;		// Hope for the best
		try {
			Config.getConfig().setNeo4jDBDefaultUser(txtNeo4jDefaultUser.getText());
			Config.getConfig().setNeo4jDBDefaultPassword(txtNeo4jDefaultPassword.getText());
			Config.getConfig().setNeo4jTableToAttributeRelationName(txtNeo4jTableToAttributeRelationName.getText());
			Config.getConfig().setNeo4jQueryToTableRelationName(txtNeo4jQueryToTableRelationName.getText());
			Config.getConfig().setMySQLDefaultLoginName(txtMySQLDefaultLoginName.getText());
			Config.getConfig().setMySQLDefaultPassword(txtMySQLDefaultPassword.getText());
			Config.getConfig().setMySQLDefaultHostname(txtMySQLDefaultHostname.getText());
			Config.getConfig().setUseTestData(cbUseTestData.isSelected());
			Config.getConfig().setSupressOutputToConsole(cbSupressOutputToConsole.isSelected());
			Config.getConfig().setNeo4jDefaultImportFilePath(txaCSVFolder.getText());
			Config.getConfig().setGrassStyleSheetURL(txaGrassURL.getText());
			Config.getConfig().getSystemDatabaseConnectionInformation().setHostName(txtSystemDatabaseHostName.getText());
			Config.getConfig().getSystemDatabaseConnectionInformation().setLoginName(txtSystemDatabaseLoginName.getText());
			Config.getConfig().getSystemDatabaseConnectionInformation().setPassword(txtSystemDatabasePassword.getText());
			Config.getConfig().getSystemDatabaseConnectionInformation().setSchemaName(txtSystemDatabaseSchemaName.getText());
		} catch (Exception ex) {
			status = false;
			Log.logError("ConfigController.gather(): " + ex.getLocalizedMessage());
		}
		Log.logProgress("ConfigController.gather() done.");
		return status;
	}
	/**
	 * User clicked on the Save button
	 */
	private void save() {
		Log.logProgress("ConfigController.save()...");
		try {
			gather();
			Config.save(Config.getConfig(), Config.getConfig().getConfigFilename());
			dataIsDirty = false;
			displaySaveButton();
		} catch (Exception ex) {
			Log.logError("ConfigController.save(): " + ex.getLocalizedMessage() + ": \n: " + ex.getStackTrace());
		}
		Log.logProgress("ConfigController.save() done.");
	}
	private void setUpChangeListeners() {
		txtNeo4jDefaultUser.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtNeo4jDefaultPassword.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtNeo4jTableToAttributeRelationName.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtNeo4jQueryToTableRelationName.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtMySQLDefaultLoginName.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtMySQLDefaultPassword.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtMySQLDefaultHostname.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txaCSVFolder.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txaGrassURL.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		cbSupressOutputToConsole.selectedProperty().addListener(new ChangeListener<Boolean>() {@Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {setDirty(true);}});
		cbUseTestData.selectedProperty().addListener(new ChangeListener<Boolean>() {@Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {setDirty(true);}});

		txtSystemDatabaseLoginName.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtSystemDatabasePassword.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtSystemDatabaseHostName.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});
		txtSystemDatabaseSchemaName.textProperty().addListener(new ChangeListener<String>() {@Override public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {setDirty(true);}});		
	}

	private void setDirty(Boolean dirty) {this.dataIsDirty = dirty; displaySaveButton();}

    public boolean checkToDiscardData() {
    	boolean dataCanBeDiscarded = true;
    	if (dataIsDirty == true) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);		// http://code.makery.ch/blog/javafx-dialogs-official/
    		alert.setTitle("Close the Configuration window?");
    		alert.setHeaderText("Your changes will not be saved.");
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
    private void checkToCloseWindow(WindowEvent event) {
		if (checkToDiscardData()) {
//    		removeListeners();
    		// The close will continue...
        	Log.logProgress("ConfigController.checkToCloseWindow(): User confirmed that data should be discarded or no changes were made since the last save.");
		} else {
        	Log.logProgress("ConfigController.checkToCloseWindow(): User cancelled the close.");
			event.consume();	// Stop the window from closing
		}
    }
    /***
     * Load existing projects from the system database
     */
    private void loadListViewWithProjects(ListView<String> listView, ConnectionInformation sdci) {
   		ArrayList<String> arr = new ArrayList<>(10);
    	// Connect to the database, execute the query, read records into the ListView
		java.sql.ResultSet resultSet = null;
		String sql = "SELECT * FROM `" + sdci.getSchemaName() + "`.tproject;";
		resultSet = SQLUtils.executeQuery(sdci.getHostName(), 
										  sdci.getSchemaName(),
				              			  sdci.getLoginName(), 
				              			  sdci.getPassword(), 
				              			  sql);
		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
			while (resultSet.next()) {
				// Zero-based. Yikes.
				arr.add(Utils.replaceNullWithEmpty(resultSet.getString(1)) + " : " + Utils.replaceNullWithEmpty(resultSet.getString(2)) + " : " + Utils.replaceNullWithEmpty(resultSet.getString(3)) + " : " + Utils.replaceNullWithEmpty(resultSet.getString(4)));
		    }
			ObservableList<String> list = FXCollections.observableArrayList(arr);
			resultSet.close();
			listView.setItems(list);
		} catch (Exception ex) {
			Log.logError("ConfigController.loadListViewWithProjects(): " + ex.getLocalizedMessage()); 
		}
    }
    private void processDatabaseConnectionsDoubleClick() {
    	gui.GUIConnectionInformation connectionInformation = tvDatabaseConnections.getSelectionModel().getSelectedItem();
    	// Connection Name is unique
    	String connectionName = connectionInformation.getConnectionName();
    }
}
