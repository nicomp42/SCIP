/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
// ":style reset" to reset the colors in an existing graph

/*
 *  All attributes with only one relationship, which would be the table the attribute belongs to
 *  MATCH (n:Attribute) WHERE size((n)-[]-()) = 1 RETURN n UNION MATCH (n:Table) RETURN n union MATCH (n:Query) RETURN n;
 *
 *  All attributes with at least 2 relationships, which would be the attributes that are referenced in at least one query
 *  MATCH (n:Attribute) WHERE size((n)-[]-()) > 1 RETURN n UNION MATCH (n:Table) RETURN n union MATCH (n:Query) RETURN n;
 *
 */

package gui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.concurrent.Task;
import edu.UC.PhD.CodeProject.nicholdw.ActionQuery;
import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
import edu.nicholdw.PhD.CodeProject.ETL.ETLKJBFile;
import edu.nicholdw.PhD.CodeProject.ETL.ETLKJBFiles;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.DatabaseGraphConfig;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.GraphResults;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DatabaseGraphController {
	private Scene myScene;
	private Stage myStage;
	private SchemaChangeImpactProject scip;
	GraphResults graphResults;
	@FXML	private Pane pneFilter, pneQuickGraphs, pneActionQuery;
	@FXML	private AnchorPane apMain;
	@FXML	private TextField txtHostName, txtLoginName, txtPassword;
	@FXML	private TextArea taActionQueryFile, taActionQuery;
	@FXML	private ComboBox<String> cbSchema;
	@FXML	private Button btnLoadSchemaNames, btnLoadSchema, btnProcess;
	@FXML	private Button btnApplyFilter, btnAttributesInQueries, btnAttributesNotInQueries;
	@FXML	private Button btnBrowseForActionQueryFile, btnClearSchemaComboBox;
	@FXML	private TreeView<String> tvSchemas;
	@FXML	private Label lblSchemaToProcess, lblContentsOfDatabaseHost;
	@FXML	private Label lblDoubleClickPrompt, lblResults, lblWorking, lblActionQuery, lblWorkingOnETL;
	@FXML	private TextArea taResults;
	@FXML	private CheckBox cbClearDB, cbIncludeSchemaNodes, cbOpenInBrowser, cbDisplayAttributes, cbDisplayTables, cbDisplayQuerys;
	@FXML	void mnuEditOpenBrowserWindow_OnAction(ActionEvent event) {openBrowserWindow();}
	@FXML	void btnBrowseForActionQueryFile_OnClick(ActionEvent event) {browseForActionQueryFile();}
	@FXML	void btnClearSchemaComboBox_OnClick(ActionEvent event) {clearSchemaComboBox();}
	@FXML	private void btnBrowseForKJBFile_OnClick(ActionEvent event) {browseForKJBFile();}
	@FXML	private	void btnClearKJBListView_OnClick(ActionEvent event) {clearKJBListView(); } 
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("DatabaseGraphController.Initialize() starting...");
		try {
//			setTheScene();		// Called from setTheStage()
		} catch (Exception e) {
			Log.logError("DatabaseGraphController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("DatabaseGraphController.Initialize() complete");
	}
	@FXML	private ListView<String> lvKJBFiles;
	@FXML	private Button btnBrowseForKTRFile;
	private void browseForKJBFile() {
		try {
			Path resourceDirectory = Paths.get("src","main","resources");
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(resourceDirectory.toString()));
			fileChooser.setTitle("Select the .ktr file to add to the project.");
			Stage stage = (Stage) this.btnBrowseForActionQueryFile.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				if (!lvKJBFiles.getItems().contains(file.getAbsolutePath())) {
					lvKJBFiles.getItems().add(file.getAbsolutePath());
				}
			}		
		} catch (Exception ex) {
			Log.logError("DatabaseGraphController.browseForKTRFile(): " + ex.getLocalizedMessage() );
		}
	}
	private void clearKJBListView() {lvKJBFiles.getItems().clear(); }
	
	private void clearSchemaComboBox() {
		cbSchema.getItems().clear();
	}
	private void setTheScene() {
		txtHostName.setText(Config.getConfig().getMySQLDefaultHostname());
		txtLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
		txtPassword.setText(Config.getConfig().getMySQLDefaultPassword());
		showProcessSchemaControls(false);
		showArtifacts(false);
		showContentsOfDatabaseHostControls(false);
		showResultsControls(false);
		displayWorkingMessage(false);
//		displayWorkingOnETLMessage(false);
		showFilters(false);
		scip = Config.getConfig().getCurrentSchemaChangeImpactProject();	
		scatter(scip);
		lvKJBFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
		        	lvKJBFiles.getItems().add("C:\\Users\\nicomp\\SCIP Projects\\Test Case 01\\Pentaho\\JobFile.kjb");    
		        }
		    }
		});	
		myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	checkToCloseWindow(we);
            }
        });

	}
    private void checkToCloseWindow(WindowEvent event) {
		if (checkToDiscardData()) {
			gather();
    		// The close will continue...
        	Log.logProgress("DatabaseGraphController.checkToCloseWindow(): User confirmed that data should be discarded or no changes were made since the last save.");
		} else {
        	Log.logProgress("DatabaseGraphController.checkToCloseWindow(): User cancelled the close.");
			event.consume();	// Stop the window from closing
		}
    }
    /**
     * Put something here if the user should be able to keep/discard edits to the project
     * @return
     */
    private Boolean checkToDiscardData() {return true;}
    
	private void browseForActionQueryFile() {
		try {
		    //File resourcesDirectory = new File("src/test/resources");
		    //resourcesDirectory.getAbsolutePath();
			Path resourceDirectory = Paths.get("src","main","resources");
			FileChooser fileChooser = new FileChooser();
//			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			fileChooser.setInitialDirectory(new File(resourceDirectory.toString()));
			fileChooser.setTitle("Select the file containing action queries");
			Stage stage = (Stage) this.btnBrowseForActionQueryFile.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				taActionQueryFile.setText(file.getAbsolutePath());
			}		
		} catch (Exception ex) {
			Log.logError("DatabaseGraphController.browseForActionQueryFile(): " + ex.getLocalizedMessage() );
		}
	}
	private void showArtifacts(boolean status) {

	}
	private void showFilters(boolean status) {
		pneFilter.setVisible(status);
		pneQuickGraphs.setVisible(status);
	}
	@FXML
	private void btnLoadSchemaNames_OnClick(ActionEvent event) {loadSchemaNames();}
	private void loadSchemaNames() {
		loadTreeViewWithSchemaNames(txtHostName.getText(), txtLoginName.getText(), txtPassword.getText(), tvSchemas);
		showContentsOfDatabaseHostControls(true);
		showProcessSchemaControls(true);
		showArtifacts(false);
	}
	private void showContentsOfDatabaseHostControls(Boolean visible) {
		tvSchemas.setVisible(visible);
		lblContentsOfDatabaseHost.setVisible(visible);
		lblDoubleClickPrompt.setVisible(visible);
	}
	@FXML void btnLoadSchema_OnClick(ActionEvent event) {

	}
	/**
	 * Open the database and read all the queries in all the schemas, loading
	 * the names into a Tree View
	 */
	private void loadTreeViewWithSchemaNames(String hostName, String loginName, String password, TreeView<String> tvSchemas) {
		// http://www.iconarchive.com/show/oxygen-icons-by-oxygen-icons.org/Places-network-server-database-icon.html
		try {
			// Schemas schemas = new Schemas();
			ArrayList<String> schemaNames = Schemas.loadSchemaNamesFromDatabaseServer(hostName, loginName, password);
//			java.net.URL imgURL = DatabaseGraphController.class.getClassLoader().getResource("images/Places-network-server-database-icon24px.png");
//			Image icon  = new Image("../../images/Places-network-server-database-icon24px.png");
			Node rootIcon = new ImageView(new Image(DatabaseGraphController.class.getClassLoader().getResourceAsStream("images/Places-network-server-database-icon24px.png")));
			TreeItem<String> rootItem = new TreeItem<String>(hostName, rootIcon);
			rootItem.setExpanded(true);
			for (String schemaName : schemaNames) {
				Node schemaIcon = new ImageView(new Image(DatabaseGraphController.class.getClassLoader().getResourceAsStream("images/database-iconSilver24px.png")));
				TreeItem<String> schemaItem = new TreeItem<String>(schemaName, schemaIcon);
				rootItem.getChildren().add(schemaItem);
			}
			tvSchemas.setRoot(rootItem);
		} catch (Exception ex) {
			Log.logError("DatabaseGraphController.loadTreeViewWithSchemaNamesAndQueries() : " + ex.getLocalizedMessage());
		}
	}
	@FXML
	void mnuFileExit_OnAction(ActionEvent event) {
		Stage stage = (Stage) this.btnLoadSchemaNames.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		stage.close();
	}
	@FXML
	void tvSchemas_OnClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			processSelectedSchemaInTreeView();
		}
	}
	private void processSelectedSchemaInTreeView(){
		showProcessSchemaControls(false);
		showArtifacts(false);
		// We don't know what was clicked: this could throw an error. If a query was clicked, we're good to go.
		try {
			String newSchema = tvSchemas.getSelectionModel().getSelectedItem().getValue();
			cbSchema.getItems().add(newSchema);
			cbSchema.setValue(newSchema);
			showProcessSchemaControls(true);
			taResults.setText("");
		} catch (Exception ex) {
			Log.logError("DatabaseGraphController.processSelectedQueryInTreeView() : " + ex.getLocalizedMessage());
		}
	}
	private void showProcessSchemaControls(Boolean visible) {
		btnProcess.setVisible(visible);
		cbSchema.setVisible(visible);
		btnClearSchemaComboBox.setVisible(visible);
		lblSchemaToProcess.setVisible(visible);
		pneActionQuery.setVisible(visible);
		btnProcess.setVisible(visible);
		cbClearDB.setVisible(visible);
		cbIncludeSchemaNodes.setVisible(visible);
		cbOpenInBrowser.setVisible(visible);
	}
	@FXML
	private void btnAttributesNotInQueries_OnClick(ActionEvent event) throws InterruptedException {
		GenerateGraphAttributesNotInQueries();
	}
	@FXML
	private void btnAttributesInQueries_OnClick(ActionEvent event) throws InterruptedException {
		GenerateGraphAttributesInQueries();
	}

	@FXML
	private void btnApplyFilter_OnClick(ActionEvent event) throws InterruptedException {
		applyFilters();
	}
	@FXML
	private void btnProcess_OnClick(ActionEvent event) throws InterruptedException {
		// OK, the user has drilled down to a schema. Generate the topology...
		process();
		showArtifacts(true);
	}
	private void process() {
		Log.logProgress("DatabaseGraphController.process()");
		gather();
 		if ( cbSchema.getItems().size() > 0 || lvKJBFiles.getItems().size() > 0) {
//			databaseGraphResults = new GraphResults();
			DatabaseGraphConfig databaseGraphConfig = new DatabaseGraphConfig();
			// Here is some stuff we want to run in another thread so the window has time to update itself.
			 Task<Void> runnable = new Task<Void>() {		// https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
				 // This thread cannot write to JavaFX controls, even in the Debug window.
				  public Void call() {
					Log.logProgress("DatabaseGraphController.ProcessSchema(): entering local thread. ****************");
//					try {Thread.sleep(2000);} catch (InterruptedException e) {}
					Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
					Neo4jDB.getDriver();
					if (cbClearDB.isSelected()) {Neo4jDB.clearDB();}
					databaseGraphConfig.setIncludeSchemaNodeInGraph(cbIncludeSchemaNodes.isSelected());
					databaseGraphConfig.setUseFriendlyNameAsDisplayName(true);
					Schemas schemas = new Schemas();
					for (Object schemaNameObject: cbSchema.getItems()) {schemas.addSchema(new Schema((String)schemaNameObject));}
					scip.setSchemas(schemas);
					scip.setDatabaseGraphConfig(databaseGraphConfig);
					scip.setHostName(txtHostName.getText()); scip.setUserName(txtLoginName.getText()); scip.setPassword(txtPassword.getText());
				    String actionQueryFile = taActionQueryFile.getText();
					ActionQuerys actionQuerys = new ActionQuerys();
					if ((actionQueryFile != null) && (actionQueryFile.trim().length() > 0)) {
						actionQuerys.loadActionQuerys(actionQueryFile);
					}
					if (taActionQuery.getText().trim().length() > 0) {actionQuerys.add(new ActionQuery(taActionQuery.getText().trim()));}
					scip.setActionQuerys(actionQuerys);
					try {
						scip.generateGraph();
						if (cbOpenInBrowser.isSelected() ) {
							Browser browser = Browser.prepareNewBrowser();
							browser.initAndLoad(null);
						}
					} catch (Exception e) {
						Log.logError("DatabaseGraphController.ProcessSchema() local thread: " + e.getLocalizedMessage());
					}
					Log.logProgress("DatabaseGraphController.ProcessSchema(): exiting local thread");
					return null;
				}
			};
			long startTime = System.currentTimeMillis();
			// Here is the cleanup after the background thread completes.
		    runnable.setOnSucceeded(e -> {
				Log.logProgress("DatabaseGraphController.ProcessSchema(): local thread complete. ****************");
		        final long endTime = System.currentTimeMillis();
		        disableEverything(false);
				displayWorkingMessage(false);
				taResults.setText(scip.getGraphResults().toString());
				taResults.setText(taResults.getText() + "\n" + "Total execution time: " + ((double)(endTime - startTime))/1000. + " seconds.");
				showResultsControls(true);
				btnProcess.setVisible(true);
				taResults.requestFocus();
//				Log.flushAllBuffers();
//				Log.nullAllBuffers();
				showFilters(true);
		    });
			try {
				displayWorkingMessage(true);
				disableEverything(true);
				showResultsControls(true);
				btnProcess.setVisible(false);
				taResults.requestFocus();
				taResults.setText("");				// We need to wait for this to happen so the user isn't confused.
				// Since we can't write to the JavaFX controls in the worker thread, we will buffer the messages and write them when the worker thread is finished.
//				Log.flushAllBuffers();
//				Log.resetAllBuffers();
			    Thread thread = new Thread(runnable);
			    thread.start();
			} catch (Exception e) {
				Log.logError("DatabaseGraphController.ProcessSchema(): " + e.getLocalizedMessage());
			}
		} else {
			(new Alert(Alert.AlertType.ERROR, "Please select one or more schemas and/or ETL Job Files", ButtonType.OK)).showAndWait();
			cbSchema.requestFocus();
		}
	}
	/**
	 * Tell the user to be patient: the data is being processed.
	 * @param status true to display the message, false otherwise.
	 */
	private void displayWorkingMessage(boolean status) {
//		lblWorking.setDisable(!status);
		lblWorking.setVisible(status);
		lblWorking.setStyle("-fx-opacity: 1.0;");
	}
	/**
	 * Tell the user to be patient: the data is being processed.
	 * @param status true to display the message, false otherwise.
	 */
//	private void displayWorkingOnETLMessage(boolean status) {
//		lblWorking.setDisable(!status);
//		lblWorkingOnETL.setVisible(status);
//		lblWorkingOnETL.setStyle("-fx-opacity: 1.0;");
//	}
	private void showResultsControls(boolean status) {
		taResults.setVisible(status);
		lblResults.setVisible(status);
	}
	private TreeItem<String> searchTreeItem(TreeItem<String> item, String name) {
		// TODO : rewrite this to have only one exit point, at the bottom
//		http://www.bigdev.de/2015/09/recursive-search-in-non-binary-tree-in.html
	    if(item.getValue().equals(name)) return item; // hit!

	    // continue on the children:
	    TreeItem<String> result = null;
	    for(TreeItem<String> child : item.getChildren()){
	         result = searchTreeItem(child, name);
	         if(result != null) return result; // hit!
	    }
	    //no hit:
	    return null;
	}
	public void setScene(Scene scene) {this.myScene = scene;}
	public Scene getScene() {return this.myScene;}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {this.myStage = myStage; setTheScene();}

	public void disableEverything(boolean status) {
		try {
			apMain.setDisable(status);	// This also disables the "Working..." message!
//			taActionQuery.setDisable(status);
		} catch (Exception e) {
			Log.logError("DatabaseGraphController.enableEverything(): " + e.getLocalizedMessage());
		}
	}
	private void gather() {
		(scip.getEtlProcess().getEtlKJBFiles()).clear();
		for (String kjbFile : lvKJBFiles.getItems()) {
			(scip.getEtlProcess().getEtlKJBFiles()).add(new ETLKJBFile(kjbFile));
		}
	}
	private void scatter(SchemaChangeImpactProject scip) {
		loadListViewWithKJBFiles(scip.getEtlProcess().getEtlKJBFiles());
	}
	private void loadListViewWithKJBFiles(ETLKJBFiles etlKJBFiles) {
		for (ETLKJBFile etlKJBFile: etlKJBFiles) {
			lvKJBFiles.getItems().add(etlKJBFile.getFileName());
		}
	}

	/**
	 * Apply the graph filters selected by the user in the pneFilter pane
	 */
	public void applyFilters() {
		// match (n) where n:Table or n:Attribute return n
		String cypherStatement = "MATCH (n)";
		String or = "";
		String where = " WHERE ";

		if (cbDisplayAttributes.isSelected()) {
				cypherStatement += (where + or + " n:" + SchemaGraph.attributeNodeLabel);
				where = "";
				or = " or ";
		}
		if (cbDisplayTables.isSelected()) {
			cypherStatement += (where + or + " n:" + SchemaGraph.tableNodeLabel);
			where = "";
			or = " or ";
		}
		if (cbDisplayQuerys.isSelected()) {
			cypherStatement += (where + or + "n:" + SchemaGraph.viewNodeLabel);
			where = "";
			or = " or ";
		}
		cypherStatement += " return n";
		// If there's no ':' in the cypher statement then the user has applied no filters
		if (!cypherStatement.contains(":")) {
			java.util.Optional<ButtonType> result = (new Alert(Alert.AlertType.CONFIRMATION, "You have applied no filters. You will get the entire graph.", ButtonType.OK, ButtonType.CANCEL)).showAndWait();
			 if (result.isPresent() && result.get() == ButtonType.OK) {
				// We have a wonderfully elegant cypher query. Let's run it.
				Browser browser = Browser.prepareNewBrowser();
				browser.initAndLoad(cypherStatement);
			 }
		} else {
			// We have a wonderfully elegant cypher query. Let's run it.
			Browser browser = Browser.prepareNewBrowser();
			browser.initAndLoad(cypherStatement);
		}
	}
	/**
	 * Filter the graph with only the attributes that are referenced by at least one query.
	 * The graph should be
	 */
	private void GenerateGraphAttributesInQueries() {
		String cypherStatement = "MATCH (n:attribute) WHERE size((n)-[]-()) > 1 RETURN n UNION MATCH (n:table) RETURN n union MATCH (n:view) RETURN n;";
		Browser browser = Browser.prepareNewBrowser();
		browser.initAndLoad(cypherStatement);
	}
	/**
	 * Filter the graph with only the attributes that are not referenced by at least one view
	 */
	private void GenerateGraphAttributesNotInQueries() {
		String cypherStatement = "MATCH (n:attribute) WHERE size((n)-[]-()) < 2 RETURN n UNION MATCH (n:table) RETURN n union MATCH (n:view) RETURN n;";
		Browser browser = Browser.prepareNewBrowser();
		browser.initAndLoad(cypherStatement);
	}
	private void openBrowserWindow() {
		Browser.openBrowserWindow();
	}
}
/*********************************************************************************/
// Old stuff before we added the KJB file processing
//@FXML	private TableView<GUIETLStep> tvETLSteps;
//@FXML	private TableView<GUIETLConnection> tvETLConnections;
//@FXML	private TableView<GUIetlTransformationFile> tvETLTransformationFiles;
//@FXML	private AnchorPane apProcessETL;
//@FXML	private TextArea txaAffectOfActionQuery, txaETLFilePath, txaOutputStepResults, txaInputStepResults, txaJoinStepResults, txaStepNamesResults, txaETLJobs, txaDBProcStepResults;
//@FXML	private Button btnApplyActionQuery, btnETLSubmit, btnETLBrowse, btnProcessETLTransformationFiles, btnClearTable;
//@FXML 	private Label lblContentsOfETL;
//@FXML	private Pane pneETLResults, pneETLLoad, pneFiles;
//@FXML 	private void btnETLSubmit_OnClick(ActionEvent event) {loadETLTransformationFiles();}
//@FXML 	private void btnETLBrowse_OnClick(ActionEvent event) {browseETL();}
//@FXML	
//private void btnProcessETLTransformationFiles_OnClick(ActionEvent event) {
//	scip.getETLKTRFile().setTransformationFileDirectory(Utils.formatPath(txaETLFilePath.getText().trim())); 
//	processETLTransformationFiles();
//}



/*
//		We are setting the title of this form in the Main. It works and I can't figure out how to get the stage object here.
		addDoubleClickHandler();
		dataBrowseController = null;
		displayLoadETLResults(false);
		btnETLSubmit.setVisible(false);
		displayETLProcessControls(false);
		txaETLFilePath.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            if (newValue){
	                // txaETLFilePath has focus
	            	checkToEnableSubmitButton();
	            } else {
	                // txaETLFilePath does not have the focus
	            	checkToEnableSubmitButton();
	            }
		    }
		});
 */
