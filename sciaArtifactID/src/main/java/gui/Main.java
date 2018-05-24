/*
 * Entry point for the GUI
 * See http://www.iconarchive.com for icons
 * Thousands of lines of awesomeness
 */
package gui;

import java.io.File;
import java.util.ArrayList;
import org.neo4j.graphdb.GraphDatabaseService;
import edu.UC.PhD.CodeProject.nicholdw.Attribute;
import edu.UC.PhD.CodeProject.nicholdw.Attributes;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.ETLToCSV;
import edu.UC.PhD.CodeProject.nicholdw.ForeignKey;
import edu.UC.PhD.CodeProject.nicholdw.ForeignKeys;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Tables;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.importFromCSVIntoGraphDB.ImportFromCSVIntoGraphDB;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jUtils;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.DwhQueries;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.IdsDwh;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.Operational;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.OpsIds;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	private Stage myPrimaryStage;
	private NewSchemaChangeImpactProject newSchemaChangeImpactProject;
	@FXML private Label lblNeo4jWarning;
	@FXML private TextField txtOperationalHostName, txtOperationalLoginName, txtOperationalPassword, txtProjectHomeDirectory, txtProjectName, txtNeo4jConfigFile, txtNeo4jDBName, txtOperationalSchemaName;
	@FXML private TextField txtDwhHostName, txtDwhLoginName, txtDwhPassword, txtDwhUserHome, txtDwhSchemaName;
	@FXML private AnchorPane apMainWindow;
	@FXML private ListView<String> lvOperationalSchemaNames, lvOperationalTables, lvOperationalAttributes, lvOperationalForeignKeys, lvETLLayer;
	@FXML private ListView<String> lvDwhSchemaNames, lvDwhTables, lvDwhAttributes, lvDwhForeignKeys, lvDwhQuerys;
	@FXML private Button btnSaveOperationalSchemaArtifactsToCSVFiles, btnPentahoProjectDirectory, btnCreateGraphDB, btnDatabaseFilePath, btnCreateDatabase, btnImportFromCSVFiles, btnLoadOperationalSchemaArtifacts, btnSavePentahoArtifactsToCSVFiles, btnLoadOperationalSchemaNames;
	@FXML private Button btnSaveDwhSchemaArtifactsToCSVFiles, btnLoadDwhSchemaArtifacts, btnLoadDwhSchemaNames, btnBrowse;
	@FXML private CheckBox cbAllETLLayers;
	@FXML private TextArea txtProjectComment, txaPentahoProjectDirectory;
	@FXML private Tab tabProject, tabOperationalSchema, tabPentahoETL, tabDWSchema, tabNeo4j;
	@FXML private TabPane tpnProject;
	@FXML private CheckBox cbOperationalSchemaCSVFiles, cbETLCSVFiles, cbDWCSVFiles;
	@FXML private MenuBar mbrMainMenu;
	@FXML private MenuItem mnuFileNewProject, mnuFileOpenProject, mnuFileSaveProject, mnuFileExit, mnuEditDebug, mnuEditProcessAQuery, mnuHelpAbout, mnuEditClearNeo4jDB, mnuFileConfig, mnuToolsGenerateSchemaTopology, mnuSubmitSQL;
	@FXML private WebView wbNeo4j;
	@FXML private ImageView imgNeo4jReminder;
	@FXML void lvOperationalSchemaNames_OnClicked(MouseEvent event) {txtOperationalSchemaName.setText(lvOperationalSchemaNames.getSelectionModel().getSelectedItem());}
	@FXML void mnuHelpAbout_OnClick(ActionEvent event) {openAboutWindow();}
	@FXML void lvDwhSchemaNames_OnClicked(MouseEvent event) {txtDwhSchemaName.setText(lvDwhSchemaNames.getSelectionModel().getSelectedItem());}
	@FXML void mnuEditProcessAQuery_OnAction(ActionEvent event) {openProcessQueryWindow();}
	@FXML void mnuEditDebug_OnAction(ActionEvent event) {openDebugWindow();}
	@FXML void mnuFileExit_OnAction(ActionEvent event) {Platform.exit();}
	@FXML void mnuEditClearNeo4jDB_OnAction(ActionEvent event) {clearNeo4jDB();}
	@FXML void mnuToolsGenerateSchemaTopology_OnClick(ActionEvent event) {openSchemaTopologyWindow();}
	@FXML void mnuEditSubmitSQL_OnAction(ActionEvent event) {openSubmitSQLWindow();}
	@FXML
	void mnuFileSaveProject_OnAction(ActionEvent event) {
		Log.logProgress("main.mnuFileSaveProject_OnAction(): Saving scip...");
		try {
			gather(Config.getConfig().getCurrentSchemaChangeImpactProject());
			Config.getConfig().getCurrentSchemaChangeImpactProject().save();
			Log.logProgress("main.mnuFileSaveProject_OnAction(): Done.");
		} catch(Exception ex) {
			Log.logError("main.mnuFileSaveProject_OnAction():" + ex.getLocalizedMessage(),ex.getStackTrace());
		}
	}
	@FXML
	void mnuFileConfig_OnAction(ActionEvent event) {
		// Display the Import button if at least one of the 'importable' CSV file categories is selected
		openConfigWindow();
	}
	@FXML
	void cbCSVFiles_OnAction(ActionEvent event) {
		// Display the Import button if at least one of the 'importable' CSV file categories is selected
		checkCSVImportCheckBoxes();
	}
	private void checkCSVImportCheckBoxes() {
		if (cbOperationalSchemaCSVFiles.isSelected() ||
			cbETLCSVFiles.isSelected() ||
			cbDWCSVFiles.isSelected()) {
			btnImportFromCSVFiles.setVisible(true);
			lblNeo4jWarning.setVisible(true);
			imgNeo4jReminder.setVisible(true);
		} else {
			btnImportFromCSVFiles.setVisible(false);
			lblNeo4jWarning.setVisible(false);
			imgNeo4jReminder.setVisible(false);
		}
	}
	@FXML
	void mnuEditOpenBrowserWindow_OnAction(ActionEvent event) {
		openBrowserWindow();
	}
	@FXML
	void mnuFileOpenProject_OnAction(ActionEvent event) {
		Log.logProgress("Main.mnuFileOpenProject_OnAction()");
		boolean keepGoing = true;
		String startingDirectory = Config.getConfig().getInitialDirectory();
		while (keepGoing) {
			try {
				FileChooser chooser = new FileChooser();
				chooser.setInitialDirectory(new File(startingDirectory));
				chooser.setTitle("Select an Existing Project");
				File file = chooser.showOpenDialog(new Stage());
				if (file != null) {
					Log.logProgress("Main.mnuFileOpenProject_OnAction(): project opened from " + file.getAbsolutePath());
					SchemaChangeImpactProject scip = new SchemaChangeImpactProject();
					scip = SchemaChangeImpactProject.load(file.getAbsolutePath());
					scatter(scip);
					Config.getConfig().setCurrentSchemaChangeImpactProject(scip);
					myPrimaryStage.setTitle(Config.getConfig().buildWindowBarTitleWithAProjectName());
					keepGoing = false;
					tpnProject.setVisible(true);	// Until the user opens or creates a project, this is hidden
					mnuFileSaveProject.setDisable(false);
				} else {keepGoing = false;}
			} catch (java.lang.IllegalArgumentException e) {
				startingDirectory = Config.getConfig().getUserHome();		// "./";
			} catch(Exception ex) {
				Log.logError("Main.mnuFileOpenProject_OnAction:" + ex.getLocalizedMessage(), ex);
				keepGoing = false;
			}
		}
	}
	/***
	 * Copy from SchemaChangeImpactProject object to controls on the form
	 * @param scip The reference to the SchemaChangeImpactProject object
	 */
	private void scatter(SchemaChangeImpactProject scip) {
		try {
			txtProjectHomeDirectory.setText(scip.getFilePath());
			txtProjectComment.setText(scip.getComment());
			txtProjectName.setText(scip.getProjectName());
			txaPentahoProjectDirectory.setText(scip.getPentahoProjectDirectory());
			txtNeo4jDBName.setText(scip.getNeo4jDBName());

			txtOperationalSchemaName.setText(scip.getOperational().getOperationalSchemaName());
			txtOperationalHostName.setText(scip.getOperational().getHostName());
			txtOperationalLoginName.setText(scip.getOperational().getLoginName());
			txtOperationalPassword.setText(scip.getOperational().getPassword());

			txtDwhSchemaName.setText(scip.getDwhQueries().getDwhSchemaName());
			txtOperationalHostName.setText(scip.getDwhQueries().getHostName());
			txtOperationalLoginName.setText(scip.getDwhQueries().getLoginName());
			txtOperationalPassword.setText(scip.getDwhQueries().getPassword());
		} catch (Exception ex) {
			Log.logError("Main.scatter(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
	/***
	 * Copy from the controls on the form to the SchemaChangeImpactProject object
	 * @param scip The reference to the SchemaChangeImpactProject object
	 */
	private void gather(SchemaChangeImpactProject scip) {
		scip.setFilePath(txtProjectHomeDirectory.getText().trim());
		scip.setComment(txtProjectComment.getText().trim());
		try {scip.setProjectName(txtProjectName.getText().trim());} catch (Exception ex) {}
		scip.setPentahoProjectDirectory(txaPentahoProjectDirectory.getText().trim());
		scip.setNeo4jDBName(txtNeo4jDBName.getText().trim());

		scip.getOperational().setOperationalSchemaName(txtOperationalSchemaName.getText());
		scip.getOperational().setHostName(txtOperationalHostName.getText());
		scip.getOperational().setLoginName(txtOperationalLoginName.getText());
		scip.getOperational().setPassword(txtOperationalPassword.getText());

		scip.getDwhQueries().setDwhSchemaName(txtDwhSchemaName.getText());
		scip.getDwhQueries().setHostName(txtDwhHostName.getText());
		scip.getDwhQueries().setLoginName(txtDwhLoginName.getText());
		scip.getDwhQueries().setPassword(txtDwhPassword.getText());
	}
	/**
	 * A place to write debug messages generated by the application
	 */
	private void openDebugWindow() {
		if (Config.getConfig().getDebugController() == null) {
			try {
				FXMLLoader fxmlLoader = null;
				// Open the New Project Window
				fxmlLoader = new FXMLLoader(getClass().getResource("debug.fxml"));
				Parent root = fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.NONE);
				stage.setOpacity(1);
				stage.setTitle("Debug");
				Scene scene = new Scene(root, 700, 450);
		        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		            public void handle(WindowEvent we) {
		                Config.getConfig().setDebugController(null);
		            }
		        });
				stage.setScene(scene);
				DebugController dc = fxmlLoader.getController();
				dc.setScene(scene);
				dc.setStage(stage);
				Config.getConfig().setDebugController(dc);
				stage.show();
			} catch (Exception ex) {
				Log.logError("Main.openDebugWindow():" + ex.getLocalizedMessage());
			}
		} else {
			// The debug window is already open. Give it the focus.
			Config.getConfig().getDebugController().getStage().toFront();
		}
	}
	private void openProcessQueryWindow() {
//		ProcessAQuery processAQuery = new ProcessAQuery();
		try {
			FXMLLoader fxmlLoader = null;
			// Open the New Process A Query Window
			fxmlLoader = new FXMLLoader(getClass().getResource("processQuery.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			stage.setOpacity(1);
			//stage.setTitle("Process A Query");
			stage.setTitle(Config.getConfig().getApplicationTitle() + " - Process a Query");
			stage.setResizable(false);
			stage.setScene(new Scene(root, 988, 833));
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openProcessQueryWindow():" + ex.getLocalizedMessage());
		}
	}
	public void start(Stage primaryStage) {
		Log.logProgress("Main.start() starting...");
		try {
			Config.loadConfig(Config.getConfig().getConfigFilename());
			myPrimaryStage = primaryStage;

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("main.fxml"));
//			Define the controller for the FXML file, then assign that controller to the loader
//	        Main controller = new Main();
			fxmlLoader.setController(this);
			//			BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			Scene scene = new Scene((Parent) fxmlLoader.load());			//, 755, 574);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle(Config.getConfig().getApplicationTitle());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			apMainWindow.autosize();
			primaryStage.show();
			setTheScene();
		} catch(Exception e) {
			Log.logError("Main.start(): " + e.getLocalizedMessage());
		}
		Log.logProgress("Main.start() complete");
	}
	private void setTheScene() {
		txtOperationalHostName.setText(Config.getConfig().getMySQLDefaultHostname());
		txtOperationalLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
		txtOperationalPassword.setText(Config.getConfig().getMySQLDefaultPassword());
		//		btnLoadOperationalSchemaArtifacts.setVisible(false);
		btnSaveOperationalSchemaArtifactsToCSVFiles.setVisible(false);
		btnSaveDwhSchemaArtifactsToCSVFiles.setVisible(false);
		//btnCreateGraphDB.setVisible(false);
		txtNeo4jConfigFile.setText(Config.getConfig().getNeo4jConfigurationFile());
		loadETLLayers();
		myPrimaryStage.setTitle(Config.getConfig().buildWindowBarTitleWithAProjectName());
		tpnProject.setVisible(false);	// Until the user opens or creates a project, this is hidden
		mnuFileSaveProject.setDisable(true);
		txtProjectName.setEditable(false);
		txtProjectHomeDirectory.setEditable(false);
		checkCSVImportCheckBoxes();
	}
	private int loadETLLayers() {
		lvETLLayer.setItems(FXCollections.observableArrayList(Config.getConfig().getETLLayers()));
		return lvETLLayer.getItems().size();
	}
	private int loadSchemaNames(ListView<String> lvSchemaNames) {
		//ObservableList<String> schemaNames = FXCollections.observableArrayList();
		// schemaNames.add("Master");	Just a test
		try {
			String hostName = txtOperationalHostName.getText();
			String loginName= txtOperationalLoginName.getText();
			String password = txtOperationalPassword.getText();
			ArrayList<String> schemaNames = Schemas.loadSchemaNamesFromDatabaseServer(hostName, loginName, password);
			lvSchemaNames.setItems(FXCollections.observableArrayList(schemaNames));
		} catch (Exception ex) {
			Log.logError("loadSchemaNames(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
		return lvSchemaNames.getItems().size();
	}
	@FXML
	protected void btnSavePentahoArtifactsToCSVFiles_OnClick(ActionEvent event) {
		// Process the Pentaho XML file into CSV files
		ETLToCSV e = new ETLToCSV();
		try {
			e.convertETLToCSV(Config.getConfig().getCurrentSchemaChangeImpactProject());
		} catch (Exception ex) {
			Log.logError("Main.btnSavePentahoArtifactsToCSVFiles_OnClick(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
	@FXML
	protected void btnSaveDWQueriesToCSVFiles_OnClick(ActionEvent event) {
		// Process the Pentaho XML file into CSV files
		ETLToCSV e = new ETLToCSV();
		try {
			e.convertETLToCSV(Config.getConfig().getCurrentSchemaChangeImpactProject());
		} catch (Exception ex) {
			Log.logError("Main.btnSavePentahoArtifactsToCSVFiles_OnClick(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
	@FXML
	protected void btnSaveOperationalSchemaArtifactsToCSVFiles_OnClick(ActionEvent event) {
		String selectedSchemaName = lvOperationalSchemaNames.getSelectionModel().getSelectedItem();
		Schema schema = new Schema(selectedSchemaName);			//Config.getConfig().getSchemas().getSchemaByName(selectedSchemaName);
		String graphDBFilePath = Utils.formatPath(Config.getConfig().getCurrentSchemaChangeImpactProject().getFullProjectPath()) + SchemaChangeImpactProject.operationalSubDirectory;  // Config.getConfig().getCSVFilePath(selectedSchemaName);		// This is an absolute path. Do not let Neo4j see it.
		schema.saveOperationalArtifactsToCSVFiles(graphDBFilePath, txtOperationalHostName.getText(), txtOperationalLoginName.getText(), txtOperationalPassword.getText());
		// Since the user picked a schema, we need to save that schema name to the current SchemaChangeImpactProject object
		Config.getConfig().getCurrentSchemaChangeImpactProject().getOperational().setOperationalSchemaName(selectedSchemaName);
		Config.getConfig().getCurrentSchemaChangeImpactProject().save();
		btnCreateGraphDB.setVisible(true);
	}
	@FXML
	protected void btnSaveDwhSchemaArtifactsToCSVFiles_OnClick(ActionEvent event) {
		String selectedSchemaName = txtDwhSchemaName.getText();
		Schema schema = new Schema(selectedSchemaName);
		String graphDBFilePath = Config.getConfig().getCurrentSchemaChangeImpactProject().getDwhCSVFilePath();
		schema.loadQueryDefinitions(txtDwhHostName.getText(), txtDwhLoginName.getText(), txtDwhPassword.getText());
		schema.exportQueryDefinitionsToCSV(graphDBFilePath);
		// Since the user picked a schema, we need to save that schema name to the current SchemaChangeImpactProject object
		Config.getConfig().getCurrentSchemaChangeImpactProject().getDwhQueries().setDwhSchemaName(selectedSchemaName);
		Config.getConfig().getCurrentSchemaChangeImpactProject().save();
		btnCreateGraphDB.setVisible(true);
	}
	@FXML
	protected void btnLoadDwhSchemaNames_OnClick(ActionEvent event) {
		if (loadSchemaNames(lvDwhSchemaNames) > 0) {
			btnLoadDwhSchemaArtifacts.setVisible(true);
			btnSaveDwhSchemaArtifactsToCSVFiles.setVisible(true);
		}
	}
	@FXML
	protected void btnLoadOperationalSchemaNames_OnClick(ActionEvent event) {
		if (loadSchemaNames(lvOperationalSchemaNames) > 0) {
			btnLoadOperationalSchemaArtifacts.setVisible(true);
			btnSaveOperationalSchemaArtifactsToCSVFiles.setVisible(true);
		}
	}
	@FXML
	protected void btnCreateGraphDB_OnClick(ActionEvent event) {
		String neo4jDBName = txtNeo4jDBName.getText().trim();   // lvSchemaNames.getSelectionModel().getSelectedItem();
		try {
			createNewGraphDB(neo4jDBName);
		} catch (Exception ex) {
			Log.logError("btnCreateGraphDB_OnClick():  " + ex.getLocalizedMessage());
		}
	}
	private Boolean createNewGraphDB(String neo4jDBName) {
		Log.logProgress("Main.createNewGraphDB(): " + neo4jDBName + "...");
		Boolean status = true;
		GraphDatabaseService graphDB = null;
		try {
			String graphDBFilePath = Utils.formatPath(Utils.formatPath(txtProjectHomeDirectory.getText()) + txtProjectName.getText()) +  neo4jDBName;		// This is an absolute path. Do not let Neo4j see it.
			try { new File(graphDBFilePath).mkdir();} catch (Exception ex) {}
			graphDB = Neo4jUtils.createDB(graphDBFilePath, false);
			registerShutdownHook(graphDB);
			// Make the import folder where we will put the .csv files. Neo4j requires that folder and it doesn't get created when the DB is created.
			try {new File(Utils.formatPath(graphDBFilePath) + "import").mkdirs();} catch (Exception ex) {
				Log.logError("Main.createNewGraphDB(): Making folder for import folder: " + ex.getLocalizedMessage());
			}

			//Neo4jUtils.ExecActionQuery("Change Password");		// TODO fix
		} catch (Exception ex) {
			status = false;
			Log.logError("Main.createNewGraphDB(): " + ex.getLocalizedMessage());
		}
		try {graphDB.shutdown();} catch (Exception ex){}
		Log.logProgress("Main.createNewGraphDB(): " + neo4jDBName + " done.");
		return status;
	}
	@FXML
	protected void btnLoadOperationalSchemaArtifacts_OnClick(ActionEvent event) {
		// Grab the artifacts for the selected schema
		String selectedSchemaName = txtOperationalSchemaName.getText();	//	 lvSchemaNames.getSelectionModel().getSelectedItem();
		if ((selectedSchemaName != null) && selectedSchemaName.trim().length() != 0) {
			loadTableNames(lvOperationalTables,  selectedSchemaName, txtOperationalHostName.getText(), txtOperationalLoginName.getText(), txtOperationalPassword.getText());
			loadAttributes(lvOperationalAttributes,  selectedSchemaName, txtOperationalHostName.getText(), txtOperationalLoginName.getText(), txtOperationalPassword.getText());
			loadForeignKeys(lvOperationalForeignKeys,  selectedSchemaName, txtOperationalHostName.getText(), txtOperationalLoginName.getText(), txtOperationalPassword.getText());
			btnSaveOperationalSchemaArtifactsToCSVFiles.setVisible(true);
		} else {
			(new Alert(Alert.AlertType.INFORMATION, "Please enter a schema name", ButtonType.OK)).showAndWait();
		}
	}
	/**
	 * Load foreign keys from a schema into the ListView
	 * @param schemaName The schema containing the tables
	 * @return The number of foreign keys names loaded
	 */
	private int loadForeignKeys(ListView<String> lv, String schemaName, String hostName, String loginName, String password) {
		lv.getItems().clear();
		Schema schema = new Schema(schemaName);
		schema.loadForeignKeys(hostName, loginName, password);			// Load all the attributes for the schema
		ForeignKeys foreignKeys;
		foreignKeys = schema.getForeignKeys();	// Get the list of loaded foreign keys.
		for (ForeignKey foreignKey : foreignKeys) {
			lv.getItems().add(foreignKey.getTableName() + "." + foreignKey.getForeignKeyName());
		}
		return lv.getItems().size();
	}
	/**
	 * Load all attributes names from a schema into the ListView
	 * @param schemaName The schema containing the tables
	 * @return The number of attributes loaded
	 */
	private int loadAttributes(ListView<String> lv, String schemaName, String hostName, String loginName, String password) {
		lv.getItems().clear();
		Schema schema = new Schema(schemaName);
		schema.loadAttributes(hostName, loginName, password);			// Load all the attributes for the schema
		Attributes attributes;
		attributes = schema.getAttributes();	// Get the list of loaded attributes.
		for (Attribute attribute : attributes) {
			lv.getItems().add(attribute.getTableName() + "." + attribute.getAttributeName());
		}
		return lv.getItems().size();
	}
	/**
	 * Load table names from a schema into the ListView
	 * @param schemaName The schema containing the tables
	 * @return The number of table names loaded
	 */
	private int loadTableNames(ListView<String> lv, String schemaName, String hostName, String loginName, String password) {
		lv.getItems().clear();
		Schema schema = new Schema(schemaName);
		schema.loadTables(hostName, loginName, password);			// Load the tables for the schema
		Tables tables;
		tables = schema.getTables();	// Get the list of loaded tables.
		for (Table table : tables) {
			lv.getItems().add(table.getTableName());
		}
		return lv.getItems().size();
	}
	/**
	 * Load query names from a schema into the ListView
	 * @param schemaName The schema containing the tables
	 * @return The number of query names loaded
	 */
	private int loadQueryNames(ListView<String> lv, String schemaName, String hostName, String loginName, String password) {
		lv.getItems().clear();
		Schema schema = new Schema(schemaName);
		schema.loadTables(hostName, loginName, password);			// Load the tables for the schema
		Tables tables;
		//    	QueryDefinitions queryDefinitions = schema.getQueryDefinitions();
		ArrayList<String> queryNames = Schema.readQueryNames(hostName, schemaName, loginName, password);
		for (String queryName : queryNames) {
			lv.getItems().add(queryName);
		}
		return lv.getItems().size();
	}
	@FXML
	protected void btnImportFromCSVFiles_OnClick(ActionEvent event) {
		//String selectedSchemaName = lvSchemaNames.getSelectionModel().getSelectedItem();
		gather(Config.getConfig().getCurrentSchemaChangeImpactProject());
		ImportFromCSVIntoGraphDB gg = new ImportFromCSVIntoGraphDB();
		try {
			ArrayList<SchemaChangeImpactProjectComponent> schemaChangeImpactProjectComponent = new ArrayList<SchemaChangeImpactProjectComponent>();
			if (cbOperationalSchemaCSVFiles.isSelected()) {schemaChangeImpactProjectComponent.add(new Operational());}
			if (cbETLCSVFiles.isSelected()) {schemaChangeImpactProjectComponent.add(new OpsIds());}
			if (cbETLCSVFiles.isSelected()) {schemaChangeImpactProjectComponent.add(new IdsDwh());}
			if (cbDWCSVFiles.isSelected()) {schemaChangeImpactProjectComponent.add(new DwhQueries());}
			gg.ImportIntoGraphDB(Config.getConfig().getCurrentSchemaChangeImpactProject(), schemaChangeImpactProjectComponent);		//, "convertToGraph", "operational schema");
		} catch(Exception ex) {
			Log.logError("btnImportFromCSVFiles_OnClick(): " + ex.getLocalizedMessage());
		}
	}
	@FXML
	protected void btnPentahoProjectDirectory_OnClick(ActionEvent event) {
		// docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select Pentaho Directory");
		File file = directoryChooser.showDialog(myPrimaryStage);
		txaPentahoProjectDirectory.setText(file.getAbsolutePath());
	}
	@FXML
	protected void btnLoadDwhSchemaArtifacts_OnClick(ActionEvent event) {
		// Grab the artifacts for the selected schema
		String selectedSchemaName = txtDwhSchemaName.getText();	//	 lvSchemaNames.getSelectionModel().getSelectedItem();
		if ((selectedSchemaName != null) && selectedSchemaName.trim().length() != 0) {
			loadTableNames(lvDwhTables,  selectedSchemaName, txtDwhHostName.getText(), txtDwhLoginName.getText(), txtDwhPassword.getText());
			loadAttributes(lvDwhAttributes,  selectedSchemaName, txtDwhHostName.getText(), txtDwhLoginName.getText(), txtDwhPassword.getText());
			loadForeignKeys(lvDwhForeignKeys,  selectedSchemaName, txtDwhHostName.getText(), txtDwhLoginName.getText(), txtDwhPassword.getText());
			loadQueryNames(lvDwhQuerys,  selectedSchemaName, txtDwhHostName.getText(), txtDwhLoginName.getText(), txtDwhPassword.getText());

			btnSaveDwhSchemaArtifactsToCSVFiles.setVisible(true);
		} else {
			(new Alert(Alert.AlertType.INFORMATION, "Please enter a schema name", ButtonType.OK)).showAndWait();
		}
	}
	private static void registerShutdownHook(GraphDatabaseService graphDb)
	{
		// Registers a shutdown hook for the Neo4j instance so that it shuts down nicely when the VM exits (even if you "Ctrl-C" the running application).
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				try {
					Neo4jUtils.getGraphDatabaseService().shutdown();
				} catch (Exception ex) {
					Log.logProgress("ShutdownHook() graphDb.shutdown(): " + ex.getLocalizedMessage());
				}
			}
		} );
	}
	public void openAboutWindow() {
		try {
			FXMLLoader fxmlLoader = null;
//			Open the New Project Window
			fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("About");
			Scene scene = new Scene(root);//, 700, 450);
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {public void handle(WindowEvent we) {}});
			stage.setResizable(false);
			stage.setScene(scene);
			AboutController about = fxmlLoader.getController();
			about.setScene(scene);
			about.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openAboutWindow():" + ex.getLocalizedMessage());
		}
	}
	private void clearNeo4jDB() {
		Log.logProgress("Main.clearNeo4jDB()...");
		try {
		// If we are not connected to a DB, do it now. We are guessing the credentials from our default values in the Config class
		if (Neo4jUtils.getDriver() == null) {
			Neo4jUtils.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
			Neo4jUtils.getDriver();
		}
		Neo4jUtils.clearDB();
		Log.logProgress("Main.clearNeo4jDB() done.");
		} catch (Exception ex) {
			Log.logError("Main.clearNeo4jDB(): " + ex.getLocalizedMessage());
		}
	}
	private void openConfigWindow() {
		Log.logProgress("Main.openConfigWindow()");
		try {
			FXMLLoader fxmlLoader = null;
			fxmlLoader = new FXMLLoader(getClass().getResource("Config.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("Configuration");
			Scene scene = new Scene(root);//, 700, 450);
			stage.setResizable(false);
			stage.setScene(scene);
			ConfigController configController = fxmlLoader.getController();
//			configController.setScene(scene);
			configController.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openConfigWindow():" + ex.getLocalizedMessage());
		}
	}
	@FXML
	void mnuFileNew_OnAction(ActionEvent event) {
		try {
			// Open the New Project Window
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("newSchemaChangeImpactProject.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("Add a new Schema Change Impact Project");
			stage.setScene(new Scene(root, 450, 450));
			newSchemaChangeImpactProject = fxmlLoader.getController();
			newSchemaChangeImpactProject.setStage(stage);
			stage.showAndWait();
			if (newSchemaChangeImpactProject.isBtnSaveClicked() == true) {
				// User clicked Save on the New Project form
				scatter(Config.getConfig().getCurrentSchemaChangeImpactProject());
				myPrimaryStage.setTitle(Config.getConfig().buildWindowBarTitleWithAProjectName());
				tpnProject.setVisible(true);	// Until the user opens or creates a project, this is hidden
				mnuFileSaveProject.setDisable(false);
			}
		} catch (Exception ex) {
			Log.logError("mnuFileNew_OnAction:" + ex.getLocalizedMessage());
		}
	}
	private void openSubmitSQLWindow() {
		try {
			FXMLLoader fxmlLoader = null;
			// Open the New Project Window
			fxmlLoader = new FXMLLoader(getClass().getResource("submitSQL.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			stage.setOpacity(1);
			stage.setTitle("Submit SQL");
			Scene scene = new Scene(root);		//, 700, 450);
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {public void handle(WindowEvent we) {}});
			stage.setScene(scene);
			SubmitSQLController ssqlc = fxmlLoader.getController();
			ssqlc.setScene(scene);
			ssqlc.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openSubmitSQLWindow():" + ex.getLocalizedMessage());
		}
	}
	private void openSchemaTopologyWindow() {
		try {
			FXMLLoader fxmlLoader = null;
			// Open the New Project Window
			fxmlLoader = new FXMLLoader(getClass().getResource("schemaTopology.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			stage.setOpacity(1);
			stage.setTitle("Schema Topology");
			Scene scene = new Scene(root);		//, 700, 450);
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {public void handle(WindowEvent we) {}});
			stage.setScene(scene);
			SchemaTopologyController stc = fxmlLoader.getController();
			stc.setScene(scene);
			stc.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openSchemaTopologyWindow():" + ex.getLocalizedMessage());
		}
	}
	private void openBrowserWindow() {
		Browser browser = new Browser(Browser.getNextBrowserSerialNumber().toString());
		Config.getConfig().addBrowser(browser);
		browser.initAndLoad(null);
	}
/*
		try {
			FXMLLoader fxmlLoader = null;
			// Open the New Project Window
			fxmlLoader = new FXMLLoader(getClass().getResource("browser.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			stage.setOpacity(1);
			stage.setTitle("Browser");
			Scene scene = new Scene(root);		//, 700, 450);
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {

	            }
	        });
			stage.setScene(scene);
			BrowserController bc = fxmlLoader.getController();
			bc.setScene(scene);
			bc.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("Main.openBrowserWindow():" + ex.getLocalizedMessage());
		}
	}
	*/
}
