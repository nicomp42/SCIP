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
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import edu.UC.PhD.CodeProject.nicholdw.ActionQuery;
import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.ExecuteSQLScriptStep;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.SchemaImpact;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.StepName;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStep;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.ActionQueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.ActionQueryProcessor;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
import edu.nicholdw.PhD.CodeProject.ETL.DBProcStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnection;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnections;
import edu.nicholdw.PhD.CodeProject.ETL.ETLHops;
import edu.nicholdw.PhD.CodeProject.ETL.ETLProcess;
import edu.nicholdw.PhD.CodeProject.ETL.ETLStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLSteps;
import edu.nicholdw.PhD.CodeProject.ETL.ETLTransformationFile;
import edu.nicholdw.PhD.CodeProject.ETL.XMLParser;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.DatabaseGraphConfig;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.GraphResults;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	@FXML	private Button btnLoadSchemaNames, btnLoadSchema, btnProcessSchema;
	@FXML	private Button btnApplyFilter, btnAttributesInQueries, btnAttributesNotInQueries;
	@FXML	private Button btnBrowseForActionQueryFile, btnClearSchemaComboBox;
	@FXML	private TreeView<String> tvSchemas;
	@FXML	private Label lblSchemaToProcess, lblContentsOfDatabaseHost;
	@FXML	private Label lblDoubleClickPrompt, lblResults, lblWorking, lblActionQuery, lblWorkingOnETL;
	@FXML	private TextArea taResults;
	@FXML	private CheckBox cbClearDB, cbIncludeSchemaNodes, cbOpenInBrowser, cbDisplayAttributes, cbDisplayTables, cbDisplayQuerys;
	@FXML	void mnuEditOpenBrowserWindow_OnAction(ActionEvent event) {openBrowserWindow();}
	@FXML	void btnBrowseForActionQueryFile_OnClick(ActionEvent event) {browseForActionQueryFile();}
	@FXML	void btnClearSchemaComboBox_OnClck(ActionEvent event) {clearSchemaComboBox();}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("DatabaseGraphController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("DatabaseGraphController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("DatabaseGraphController.Initialize() complete");
	}
/*********************************************************************************/
	@FXML	private TableView<GUIETLStep> tvETLSteps;
	@FXML	private TableView<GUIETLConnection> tvETLConnections;
	@FXML	private TableView<GUIetlTransformationFile> tvETLTransformationFiles;
	@FXML	private AnchorPane apProcessETL;
	@FXML	private TextArea txaAffectOfActionQuery, txaETLFilePath, txaOutputStepResults, txaInputStepResults, txaJoinStepResults, txaStepNamesResults, txaETLJobs, txaDBProcStepResults;
	@FXML	private Button btnApplyActionQuery, btnETLSubmit, btnETLBrowse, btnProcessETLTransformationFiles, btnClearTable;
	@FXML 	private Label lblContentsOfETL;
	@FXML	private Pane pneETLResults, pneETLLoad, pneFiles;
	@FXML 	private void btnETLSubmit_OnClick(ActionEvent event) {loadETLTransformationFiles();}
	@FXML 	private void btnETLBrowse_OnClick(ActionEvent event) {browseETL();}
	@FXML	
	private void btnProcessETLTransformationFiles_OnClick(ActionEvent event) {
		scip.getEtlProcess().setTransformationFileDirectory(Utils.formatPath(txaETLFilePath.getText().trim())); 
		processETLTransformationFiles();
	}
//	@FXML	private void txaETLFilePath_DoubleClick(ActionEvent event) {txaETLFilePath.setText("C:\\Users\\nicomp\\SCIP Projects\\Test Case 01\\Pentaho");}
	@FXML	private void btnClearTransformationTable_OnClick(ActionEvent event) {clearTransformationFileTable();}
/*********************************************************************************/
	
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
		displayWorkingOnETLMessage(false);
		showFilters(false);
		scip = Config.getConfig().getCurrentSchemaChangeImpactProject();	
//		*****************************
		scatter(scip);
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
	}
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
		btnProcessSchema.setVisible(visible);
		cbSchema.setVisible(visible);
		btnClearSchemaComboBox.setVisible(visible);
		lblSchemaToProcess.setVisible(visible);
//		taActionQuery.setVisible(visible);
//		lblActionQuery.setVisible(visible);
		pneActionQuery.setVisible(visible);
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
	private void btnProcessSchema_OnClick(ActionEvent event) throws InterruptedException {
		// OK, the user has drilled down to a schema. Generate the topology...
		processSchemas();
		showArtifacts(true);
	}
	private void processSchemas() {
 		if ( cbSchema.getItems().size() > 0) {
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
				btnProcessSchema.setVisible(true);
				taResults.requestFocus();
//				Log.flushAllBuffers();
//				Log.nullAllBuffers();
				showFilters(true);
		    });
			try {
				displayWorkingMessage(true);
				disableEverything(true);
				showResultsControls(true);
				btnProcessSchema.setVisible(false);
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
			(new Alert(Alert.AlertType.ERROR, "Please select one or more schemas from the list", ButtonType.OK)).showAndWait();
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
	private void displayWorkingOnETLMessage(boolean status) {
//		lblWorking.setDisable(!status);
		lblWorkingOnETL.setVisible(status);
		lblWorkingOnETL.setStyle("-fx-opacity: 1.0;");
	}
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
	public void setStage(Stage myStage) {this.myStage = myStage;}

	public void disableEverything(boolean status) {
		try {
			apMain.setDisable(status);	// This also disables the "Working..." message!
//			taActionQuery.setDisable(status);
		} catch (Exception e) {
			Log.logError("DatabaseGraphController.enableEverything(): " + e.getLocalizedMessage());
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
	/**
	 * Used to manage qualified query names while processing querys for display
	 * on the form.
	 *
	 * @author nicomp
	 */
	class FullyQualifiedQueryNameParts {
		public String schemaName;
		public String queryName;
	}
	
/******************************************************************************************/
	private DataBrowseController dataBrowseController;

	private void clearTransformationFileTable() {
		scip.getEtlProcess().getEtlTransformationFiles().deleteAll();
		tvETLTransformationFiles.getItems().clear();
		displayETLProcessControls(false);
	}
	private void gather(SchemaChangeImpactProject scip) {
		
	}
	private void scatter(SchemaChangeImpactProject scip) {
		loadTableViewWithETLSteps(scip.getEtlProcess().getETLSteps()); 
	}
	private void checkToEnableSubmitButton() {
		if (txaETLFilePath.getText() != null && txaETLFilePath.getText().trim().length() > 0) {
			btnETLSubmit.setVisible(true);
		} else {
			btnETLSubmit.setVisible(false);
		}
	}
	/***
	 * Set up the event handler when the user double-clicks on an ETL step
	 */
	private void addDoubleClickHandler() {
		tvETLSteps.setRowFactory( tv -> {
		    TableRow<GUIETLStep> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	GUIETLStep guiETLStep = row.getItem();
//		            System.out.println(guiETLStep.toString());
		            if (dataBrowseController == null) {dataBrowseController = openDataBrowse();} 
	            	dataBrowseController.appendToTextArea(guiETLStep.toString());
	            	ETLStep etlStep = scip.getEtlProcess().getETLSteps().getETLStep(guiETLStep.getStepName());
	            	scip.getEtlProcess().processTableInputStepQuery(etlStep);
	            	for (QueryAttribute queryAttribute : etlStep.getQueryDefinition().getQueryAttributes()) {
	            		String tmp;
	            		tmp = queryAttribute.toString();
	            		dataBrowseController.appendToTextArea(tmp);
	            	}
		        } 
		    });
		    return row ;
		});
	}

	private void loadTableViewWithETLSteps(ETLSteps etlSteps) {
		ETLStep.loadTableViewWithETLSteps(tvETLSteps, etlSteps);
	}	
	private void browseETL() {
		try {
			btnETLSubmit.setVisible(false);
			DirectoryChooser directoryChooser = new DirectoryChooser();
			String userHome = System.getProperty("user.home");
			directoryChooser.setInitialDirectory(new File(userHome + "/"));
			// Try to use the previous file path that was selected by the user
			try {
				String currentDir = txaETLFilePath.getText().trim();
				if (currentDir.length() > 0) {
					File file = (new File(txaETLFilePath.getText())).getParentFile();
					directoryChooser.setInitialDirectory(file);
				}
			} catch (Exception ex) {
				Log.logError("ProcessETLController.browseETL directoryChooser(): " + ex.getLocalizedMessage());
			}
			directoryChooser.setTitle("Select ETL File");
			Stage stage = (Stage) this.btnETLBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
			File file = (directoryChooser.showDialog(stage));
			if (file != null) {
				txaETLFilePath.setText(file.getAbsolutePath());
				btnETLSubmit.setVisible(true);
			} else {
				// Was there already something in the file path field?
				if (txaETLFilePath.getText().trim().length() > 0) {
					btnETLSubmit.setVisible(true);
				}
			}
		} catch (Exception ex) {
			Log.logError("ProcessETLController.browseETL(): " + ex.getLocalizedMessage());
		}
	}
	private void displayLoadETLResults(boolean visible) {
		pneETLResults.setVisible(visible);
	}
	private void disableETLLoadSelectionControls(boolean disable) {
		pneETLLoad.setDisable(disable);
	}
	private ArrayList<String> readDirectory(String xmlFilePath) {
		Log.logProgress("ProcessETLController.readDirectory(): " + xmlFilePath);
		Path tmpPath = Paths.get(xmlFilePath);
		ArrayList<String> files = new ArrayList<String>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(tmpPath, "*.{ktr}")) {
		    for (Path entry: stream) {
		    	files.add(entry.toAbsolutePath().toString());
//		        System.out.println(entry.getFileName());
		    }
		} catch (IOException x) {
		    // IOException can never be thrown by the iteration.
		    // In this snippet, it can // only be thrown by newDirectoryStream.
		    System.err.println(x);
		}	
		return files;
	}
	@FXML
	public void txaETLFilePath_OnClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			int clickCount = 0;
			clickCount = event.getClickCount();
			if (clickCount == 2) { // It's a double click! Stuff in the default path for a test case.
				txaETLFilePath.setText("C:\\Users\\nicomp\\SCIP Projects\\Test Case 01\\Pentaho\\");
			}
		}
	}
	@FXML
	public void tvETLTransformationFiles_OnClick(MouseEvent event) {
		try {
			if (event.getButton() == MouseButton.PRIMARY) {
				int clickCount = 0;
				clickCount = event.getClickCount();
				if (clickCount == 2) { // It's a double click!
					GUIetlTransformationFile g = tvETLTransformationFiles.getSelectionModel().getSelectedItem();
					g.setEtlStage(ETLTransformationFile.getNextETLStage(g.getEtlStage()));
	//				System.out.println(g.getFileName());
					int idx = tvETLTransformationFiles.getSelectionModel().getSelectedIndex();
					tvETLTransformationFiles.getItems().set(idx, g);
					// Now we must update the ETLTransformationFile in the etlProcess object.
					scip.getEtlProcess().getEtlTransformationFiles().updateETLStageInETLTransformationFile(g.getFileName(), g.getEtlStage());
				}
			}
		} catch (Exception ex) {
			Log.logError("ProcessETLController.tvETLTransformationFiles_OnClick(): " + ex.getMessage());
		}
	}

	/***
	 * Load a TableView control with Transformation file names
	 * @param tableView The TableView to be loaded
	 * @param etlSteps The set of ETL steps
	 */
	public void loadTableViewWithTransformationFileNames(TableView<gui.GUIetlTransformationFile> tableView) {
		ETLTransformationFile.loadTableViewWithTransformationFileNames(tableView, scip.getEtlProcess());
    }
	/**
	 * Process the directory that contains some Pentaho transformation files. It should be all .ktr (transformation) files 
	 * @param xmlFilePath Physical path to the directory. We'll take it from there.
	 */
	private void loadETLTransformationFiles() {
		Log.logProgress("processETLTransformationFiles.loadETLTransformationFiles() " + Utils.formatPath(txaETLFilePath.getText().trim()));
		scip.getEtlProcess().setTransformationFileDirectory(Utils.formatPath(txaETLFilePath.getText().trim()));
		ArrayList<String> files = readDirectory(scip.getEtlProcess().getTransformationFileDirectory());
		for (String file : files) {
			scip.getEtlProcess().getEtlTransformationFiles().add(new ETLTransformationFile(file));	// ETL Stage will default to unknown
		}
		loadTableViewWithTransformationFileNames(tvETLTransformationFiles);
		displayETLProcessControls(true);
	}
	private void displayETLProcessControls(Boolean visible) {
		btnProcessETLTransformationFiles.setVisible(visible);
	}
	/**
	 * Take apart all the select transformation files
	 */
	private void processETLTransformationFiles() {
		Log.logProgress("ProcessETLController.processETLTransformationFiles() " + scip.getEtlProcess().getTransformationFileDirectory());
		if (countNumberOfETLTransformationFilesWithAStage() > 0) {
			displayLoadETLResults(false);
			disableETLLoadSelectionControls(true);
			lblWorkingOnETL.setVisible(true);
			clearResultsControls();
			ArrayList<TableOutputStep> tableOutputSteps = new ArrayList<TableOutputStep>();
			ArrayList<TableInputStep> tableInputSteps = new ArrayList<TableInputStep>();
			ArrayList<DBJoinStep> dbJoinSteps = new ArrayList<DBJoinStep>();
			ArrayList<StepName> stepNames = new ArrayList<StepName>();
			ArrayList<String> connectionNames = new ArrayList<String>();
			ArrayList<DBProcStep> dbProcSteps = new ArrayList<DBProcStep>();
//			ArrayList<>
			ArrayList<ExecuteSQLScriptStep> executeSQLScriptSteps = new ArrayList<ExecuteSQLScriptStep>();
			ETLHops etlHops = new ETLHops();
	//		ETLJobs etlJobs = new ETLJobs();
			// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
		    Task <Void> task = new Task<Void>() {
		        @Override public Void call() throws InterruptedException {
		        	// Do not write to any controls in here. An exception will be thrown. It's ugly.
		    		try {
		    			try {
		    				// Step through the selected files in the TableView control
							for (ETLTransformationFile etlTransformationFile: scip.getEtlProcess().getEtlTransformationFiles()) {
								Log.logProgress("ProcessETLController.processETLTransformationFiles().task: Checking " + etlTransformationFile.toString());
								if (ETLTransformationFile.isStageUnknown(etlTransformationFile.getEtlStage()) == false) {
									Log.logProgress("ProcessETLController.processETLTransformationFiles().task: Processing " + etlTransformationFile.toString());
									processETLFile(etlTransformationFile);
								}
							}
		    				Log.logProgress("ProcessETLController.loadETL().Task: parsing complete.");
		    			} catch (Exception ex) {
		    				Log.logError("ProcessETLController.loadETL().Task: " + ex.getLocalizedMessage());
		    			}
		    		} catch (Exception ex) {
		    			Log.logError("ProcessETLController.loadETL().Task: " + ex.getLocalizedMessage());
		    		} finally {
		    		}
		        	return null;
		        }
		        public void processETLFile(ETLTransformationFile etlTransformationFile) {
					Log.logProgress("ProcessETLController.processETLFile() parsing file at " + scip.getEtlProcess().getTransformationFileDirectory() + etlTransformationFile.toString()); 
					XMLParser myXMLParser = new XMLParser();
					myXMLParser.setxmlDirectory(scip.getEtlProcess().getTransformationFileDirectory());
					myXMLParser.getStepNames(etlTransformationFile, stepNames);
					myXMLParser.getConnectionNames(etlTransformationFile, connectionNames);
					myXMLParser.parseXMLForTableOutputSteps(etlTransformationFile, tableOutputSteps);
					myXMLParser.parseXMLForTableInputSteps(etlTransformationFile, tableInputSteps);
					myXMLParser.parseXMLForDBJoinSteps(etlTransformationFile, dbJoinSteps);
					myXMLParser.parseXMLForDBProcSteps(etlTransformationFile, dbProcSteps);
					myXMLParser.parseXMLForHops(etlTransformationFile, etlHops);
					myXMLParser.parseXMLForExecuteSQLScriptSteps(etlTransformationFile, executeSQLScriptSteps);
					
	//				ETLJobs tmpETLJobs = new ETLJobs();
	//				myXMLParser.getETLJobs(myXMLParser.getXMLFilePathPrefix() + myXMLFileName, tmpETLJobs);
	//				for (ETLJob etlJob : tmpETLJobs) {
	//					Log.logProgress("ProcessETLController.loadETL().Task.processETLFile(): parsing XML JOB file at " + etlJob.getFilename());
	//					processETLFile(myXMLParser, etlJob.getFilenameWithoutPenthoPrefix());
	//				}
	//				etlJobs.addETLJobs(tmpETLJobs);
		        }
		    };
		    task.setOnSucceeded(e -> {
		    	lblWorking.setVisible(false);
				XMLParser myXMLParser = new XMLParser();
				//myXMLParser.getStepNames(xmlFilePath, stepNames);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder builder;
				Document doc = null;
				XPath xpath = null;
				try {
					for (StepName stepName: stepNames) {
						builder = factory.newDocumentBuilder();
						doc = builder.parse(scip.getEtlProcess().getTransformationFileDirectory() + stepName.getFileName());
						XPathFactory xpathFactory = XPathFactory.newInstance();
						xpath = xpathFactory.newXPath();
						String tmp, stepType, sql, table, connectionName, schemaName;
						tmp = "";
						// Not all the types of steps will have all these artifacts.
						stepType = myXMLParser.getStepTypeAsString(xpath, doc, stepName.getStepName());
						sql = myXMLParser.getSQL(xpath, doc, stepName.getStepName());
						table = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "lookup/table");
						if (table == "") {table = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "table");}
						connectionName = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "connection");
						// We will add schemaName later after we've read all the connection objects from the XML
						String procedure;
						procedure = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "procedure");
						tmp += " (" + stepType +  ")";
						txaStepNamesResults.appendText(tmp + System.getProperty("line.separator"));
						// Add this new step to the collection of steps. We don't know the schema name, yet.
						scip.getEtlProcess().getETLSteps().addETLStep(new ETLStep(stepName.getStepName(), stepType, sql, table, connectionName, procedure, stepName.getEtlStageNumber(), stepName.getFileName(), "SchemaUnknown"));
						//for (String connectionName: connectionNames) {
						scip.getEtlProcess()
						    .getETLConnections()
						    .addETLConnection(new ETLConnection(connectionName, // These thing names are case-sensitive in the .XML file
								                                myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "server"),
								                                // "database" is the schema in MySQL. 
								                                myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "database"),
								                                myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "username"),
								                                myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "type")
								                                ));
						//}
					}
					// Resolve the schema name for all the ETL steps we just added
					resolveSchemaNamesForETLSteps(scip);

					//ETLConnections etlConnections = new ETLConnections();
					for (TableOutputStep outputStep: tableOutputSteps) {
						txaOutputStepResults.appendText(outputStep.toString() + System.getProperty("line.separator"));
					}
					for (TableInputStep inputStep: tableInputSteps) {
						txaInputStepResults.appendText(inputStep.toString() + System.getProperty("line.separator"));
					}
					for (DBJoinStep joinStep: dbJoinSteps) {
						txaJoinStepResults.appendText(joinStep.toString() + System.getProperty("line.separator"));
					}
					for (DBProcStep dbProcStep: dbProcSteps) {
						txaDBProcStepResults.appendText(dbProcStep.toString() + System.getProperty("line.separator"));
					}
					// Does the file reference other jobs?
	//				for (ETLJob etlJob : etlJobs) {
	//					txaETLJobs.appendText(etlJob.toString() + System.getProperty("line.separator"));
	//				}
					// Copy the collection of steps to the table view control on the GUI
					loadTableViewWithETLSteps(scip.getEtlProcess().getETLSteps());
					displayLoadETLResults(true);
					disableETLLoadSelectionControls(false);
					loadTableViewWithETLConnections(scip.getEtlProcess().getETLConnections());
					// OK, the ETLProcess object is loaded up with ETL Steps and Connections and stuff
					// We should be able to parse the queries in the steps that have queries
					scip.getEtlProcess().processTableInputStepQueries();
					scip.getEtlProcess().processTableOutputStepsFields();
					scip.getEtlProcess().setEtlHops(etlHops);
					scip.getEtlProcess().processExecuteSQLStepQueries();
				} catch (Exception ex) {
					Log.logError("ProcessETLController.loadETL().task.setOnSucceeded: " + ex.getLocalizedMessage());
					disableETLLoadSelectionControls(false);
				}
		      });
		    Thread thread = new Thread(task);
		    thread.setDaemon(true);
		    thread.start();
		} else {
			(new Alert(Alert.AlertType.ERROR, "There is nothing to process because no ETL Transformation Files have been assigned a stage. Double-click on a stage to toggle it", ButtonType.OK)).showAndWait();
		}
	    }
	private Boolean resolveSchemaNamesForETLSteps(SchemaChangeImpactProject scip) {
		Boolean status = true;// Hope for the best
		try {
		for (ETLStep etlStep : scip.getEtlProcess().getETLSteps()) {
			String schemaName, connection;
			connection = etlStep.getConnection();
			schemaName = scip.getEtlProcess().getETLConnections().getConnection(connection).getDatabase();
			etlStep.setSchemaName(schemaName);
		}
	} catch (Exception ex) {
		Log.logError("ProcessETLController.resolveSchemaNamesForETLSteps: " + ex.getLocalizedMessage());
		status = false;
	}
	return status;
	}
		private void loadTableViewWithETLConnections(ETLConnections etlConnections) {
			ETLConnection.loadTableViewWithETLConnections(tvETLConnections, etlConnections);
		}
		private void clearResultsControls() {
			txaOutputStepResults.clear();
			txaInputStepResults.clear();
			txaJoinStepResults.clear();
			txaStepNamesResults.clear();
//			txaETLJobs.clear();
		}
		/**
		 * A place to write interesting data for browsing
		 */
		public DataBrowseController openDataBrowse() {
			DataBrowseController dataBrowseController = null;
			try {
				FXMLLoader fxmlLoader = null;
				// Open the New Project Window
				fxmlLoader = new FXMLLoader(getClass().getResource("dataBrowse.fxml"));
				Parent root = fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.NONE);
				stage.setOpacity(1);
				stage.setTitle("Data Browser");
				Scene scene = new Scene(root, 700, 450);
				stage.setScene(scene);
				dataBrowseController = fxmlLoader.getController();
				dataBrowseController.setScene(scene);
				dataBrowseController.setStage(stage);
				stage.show();
//				dataBrowseController.appendToTextArea("Data Browse Window");
			} catch (Exception ex) {
				Log.logError("DataBrowse.openDataBrowse():" + ex.getLocalizedMessage());
			}
			return dataBrowseController;
		}
		/**
		 * Count the number of ETL Transformation files that have been assigned a stage
		 * @return The number
		 */
		private int countNumberOfETLTransformationFilesWithAStage() {
			int count = 0;
			try {
				for (ETLTransformationFile etlTransformationFile: scip.getEtlProcess().getEtlTransformationFiles()) {
					Log.logProgress("ProcessETLController.processETLTransformationFiles().task: Checking " + etlTransformationFile.toString());
					if (ETLTransformationFile.isStageUnknown(etlTransformationFile.getEtlStage()) == false) {
						count++;
					}
				}
			} catch (Exception ex) {
				Log.logError("ProcessETLController.countNumberOfETLTransformationFilesWithAStage(): " + ex.getLocalizedMessage());
			}
			return count;
		}
/******************************************************************************************/
	
}

