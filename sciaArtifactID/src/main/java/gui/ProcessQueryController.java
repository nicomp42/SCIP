/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
// ":style reset" to reset the colors in an existing graph

package gui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.CompoundAlias;
import edu.UC.PhD.CodeProject.nicholdw.query.Name;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryGraph;
import edu.UC.PhD.CodeProject.nicholdw.query.QuerySchema;
import edu.UC.PhD.CodeProject.nicholdw.query.QuerySchemas;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTables;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import javafx.application.Application;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ProcessQueryController /* extends Application */ {

	// test cases. 
	private String qNestedQuery = "select testFieldATableC, testFieldATableD from qc join qd";
	private String q01 = "select testString, testInt, testDateTime, testDouble, Widget, testfieldatablec, testfieldatabled, testfieldatablee, testfieldatablef from ttablea, ttableb, qc, qd, tWidget, qlevelaa";
	private String qSimpleTable = "select testInt from ttablea";		// Can't be more simple!

	@FXML	private AnchorPane apMainWindow;
	@FXML	private CheckBox cbClearDB, cbOpenInBrowser;
	@FXML	private TextArea txaSQL, txaCSVFolder;
	@FXML	private TextField txtPqHostName, txtPqLoginName, txtPqPassword, txtPqQueryName;
	@FXML	private Button btnLoadPqSchemaNames, btnLoadPqSQueries, btnProcessQuery;
	@FXML	private Button btnBrowseForCSVFolder, btnCreateGraph;
	@FXML	private ListView<String> lvPqTables, lvPqSchemas;
	@FXML	private ListView<RowPqAttribute>  lvPqAttributes;
	@FXML	private TreeView<String> tvSchemasAndQueries;
	@FXML	private Label lblQueryToProcess, lblQueriesReferencedInTheQuery, lblAttributesReferencedInTheQuery,	lblTablesReferencedInTheQuery, lblSchemasReferencedInTheQuery, lblCSVFolder, lblContentsOfDatabaseHost;
	@FXML	private Pane pneArtifacts;
	@FXML	private MenuItem mnuEditTestCasesTestCaseq01, mnuEditTestCasesTestCasesqNestedQuery, mnuEditTestCasesTestCasesqSimpleTableQuery, mnuEditOpenBrowserWindow;
	private QueryDefinition rootQueryDefinition;
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ProcessQueryController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("ProcessQueryController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("ProcessQueryController.Initialize() complete");
	}
	@FXML void mnuEditTestCasesTestCaseq01_OnAction(ActionEvent event) {ExecuteTestCase("q01", q01);}
	@FXML void mnuEditTestCasesTestCasesqNestedQuery_OnClick(ActionEvent event) {ExecuteTestCase("qnestedquery", qNestedQuery);}
	@FXML void mnuEditTestCasesTestCasesqSimpleTableQuery_OnClick(ActionEvent event) {ExecuteTestCase("qsimpletable", qSimpleTable);}
	@FXML void mnuEditOpenBrowserWindow_OnAction(ActionEvent event) {openBrowserWindow();}
	private void setTheScene() {
		txtPqHostName.setText(Config.getConfig().getMySQLDefaultHostname());
		txtPqLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
		txtPqPassword.setText(Config.getConfig().getMySQLDefaultPassword());
		showProcessQueryControls(false);
		showArtifacts(false);
		showContentsOfDatabaseHostControls(false);
		initlvPqAttributes();
	}
	@FXML
	private void btnLoadPqSchemaNames_OnClick(ActionEvent event) {loadPqSchemaNames();}
	@FXML
	private void btnCreateGraph_OnClick(ActionEvent event) {createGraph();}
	private void createGraph() {
		QueryGraph.createGraph(rootQueryDefinition);
	}
	private void loadPqSchemaNames() {
		loadTreeViewWithSchemaNamesAndQueries(txtPqHostName.getText(), txtPqLoginName.getText(), txtPqPassword.getText(), tvSchemasAndQueries);
		showContentsOfDatabaseHostControls(true);
		showProcessQueryControls(false);
		showArtifacts(false);
	}
	private void showContentsOfDatabaseHostControls(Boolean visible) {
		tvSchemasAndQueries.setVisible(visible);
		lblContentsOfDatabaseHost.setVisible(visible);
	}
	@FXML
	void btnLoadPqSchemaArtifacts_OnClick(ActionEvent event) {

	}
	@FXML void btnLoadPqQueries_OnClick(ActionEvent event) {}
	private void importIntoNeo4j() {
		try {
			String CSVFolder = txaCSVFolder.getText().trim();
			if (CSVFolder.length() != 0) {
				if (cbClearDB.isSelected()) {
					Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(),  Config.getConfig().getNeo4jDBDefaultPassword());
					Neo4jDB.clearDB();
				}
				Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
				QueryGraph.executeCypherQueries(Config.getConfig().getNeo4jFilePrefix(), QueryGraph.cypherQueries, "");	// Folder defaults to the import folder in the Neo4j project structure
				if (cbOpenInBrowser.isSelected() ) {
					Browser browser = Browser.prepareNewBrowser();
					browser.initAndLoad(null);
				}
			} else {
	    		Alert alert = new Alert(AlertType.ERROR);		// http://code.makery.ch/blog/javafx-dialogs-official/
	    		alert.setTitle("File path needed");
	    		alert.setHeaderText("Enter a destination folder for the CSV files. \n It is probably the 'import' folder in the current Neo4j DB. \n Double-click the destination folder for the default value.");
	    		alert.setContentText("");
	    		alert.showAndWait();
	    		txaCSVFolder.requestFocus();
			}
		} catch (Exception ex) {
			Log.logError("ProcessQueryController.importIntoNeo4j() : " + ex.getLocalizedMessage());
		}
	}
	@FXML
	private void btnBrowseForCSVFolder_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select CSV Directory");
		Stage stage = (Stage) this.btnLoadPqSchemaNames.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaCSVFolder.setText(file.getAbsolutePath());}
	}
	/**
	 * Open the database and read all the queries in all the schemas, loading
	 * the names into a Tree View
	 */
	private void loadTreeViewWithSchemaNamesAndQueries(String hostName, String loginName, String password, TreeView<String> tvSchemasAndQueries) {
		// http://www.iconarchive.com/show/oxygen-icons-by-oxygen-icons.org/Places-network-server-database-icon.html
		try {
			// Schemas schemas = new Schemas();
			ArrayList<String> schemaNames = Schemas.loadSchemaNamesFromDatabaseServer(hostName, loginName, password);
//			java.net.URL imgURL = ProcessQueryController.class.getClassLoader().getResource("images/Places-network-server-database-icon24px.png");
//			Image icon  = new Image("../../images/Places-network-server-database-icon24px.png");
			Node rootIcon = new ImageView(new Image(ProcessQueryController.class.getClassLoader().getResourceAsStream("images/Places-network-server-database-icon24px.png")));
			TreeItem<String> rootItem = new TreeItem<String>(hostName, rootIcon);
			rootItem.setExpanded(true);
			for (String schemaName : schemaNames) {
				Node schemaIcon = new ImageView(new Image(ProcessQueryController.class.getClassLoader().getResourceAsStream("images/database-iconSilver24px.png")));
				TreeItem<String> schemaItem = new TreeItem<String>(schemaName, schemaIcon);
				rootItem.getChildren().add(schemaItem);
				ArrayList<String> queryNames = Schema.readQueryNames(hostName, schemaName, loginName, password);
				for (String queryName : queryNames) {
					Node queryIcon = new ImageView(new Image(ProcessQueryController.class.getClassLoader().getResourceAsStream("images/Microsoft-Query-icon24px.png")));
					TreeItem<String> queryItem = new TreeItem<String>(queryName, queryIcon);
					schemaItem.getChildren().add(queryItem);
				}
			}
			tvSchemasAndQueries.setRoot(rootItem);
		} catch (Exception ex) {
			Log.logError("ProcessQueryController.loadTreeViewWithSchemaNamesAndQueries() : " + ex.getLocalizedMessage());
		}
	}
	@FXML
	void mnuFileExit_OnAction(ActionEvent event) {
		Stage stage = (Stage) this.btnLoadPqSchemaNames.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		stage.close();
	}
	@FXML
	void tvSchemasAndQueries_OnClicked(MouseEvent event) {
		processSelectedQueryInTreeView();
	}
	private void processSelectedQueryInTreeView(){
		showProcessQueryControls(false);
		showArtifacts(false);
		// We don't know what was clicked: this could throw an error. If a query was clicked, we're good to go.
		try {
			txaSQL.setText("");
			txtPqQueryName.setText(tvSchemasAndQueries.getSelectionModel().getSelectedItem().getParent().getValue()	+ "." + tvSchemasAndQueries.getSelectionModel().getSelectedItem().getValue());
			txaSQL.setText(loadSQLFromSchema(txtPqQueryName.getText()));
			showProcessQueryControls(true);
		} catch (Exception ex) {
			Log.logError("ProcessQueryController.processSelectedQueryInTreeView() : " + ex.getLocalizedMessage());
		}
	}
	private void showProcessQueryControls(Boolean visible) {
		btnProcessQuery.setVisible(visible);
		txtPqQueryName.setVisible(visible);
		lblQueryToProcess.setVisible(visible);
	}
	/**
	 * Show/Hide the controls related to the results of processing the query
	 * @param visible True if controls would be visible, false if controls should be hidden
	 */
	private void showArtifacts(Boolean visible) {
		lblAttributesReferencedInTheQuery.setVisible(visible);
		lblTablesReferencedInTheQuery.setVisible(visible);
		lblSchemasReferencedInTheQuery.setVisible(visible);
		lvPqAttributes.setVisible(visible);
		lvPqTables.setVisible(visible);
		lvPqSchemas.setVisible(visible);
		lblCSVFolder.setVisible(visible);
		txaCSVFolder.setVisible(visible);
		btnBrowseForCSVFolder.setVisible(visible);
		cbClearDB.setVisible(visible);
		cbOpenInBrowser.setVisible(visible);
		btnCreateGraph.setVisible(visible);
	}

	@FXML
	private void btnProcessQuery_OnClick(ActionEvent event) {
		// OK, the user has drilled down to a query. It's time to put up or shut up.
		ProcessQuery(txtPqQueryName.getText());
		showArtifacts(true);
	}
	private String loadSQLFromSchema(String fullyQualifiedQueryName) {
		FullyQualifiedQueryNameParts queryNameParts = parseFullyQualifiedQueryName(fullyQualifiedQueryName);
		String sql = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(txtPqHostName.getText(), txtPqLoginName.getText(), txtPqPassword.getText(), queryNameParts.schemaName, queryNameParts.queryName);
		return sql;
	}
	/**
	 * Split the fully qualified query name into schema name and query name
	 *
	 * @param fullyQualifiedQueryName
	 * @return The schema name in element 0, query name in element 1
	 */
	private FullyQualifiedQueryNameParts parseFullyQualifiedQueryName(String fullyQualifiedQueryName) {
		FullyQualifiedQueryNameParts fqqnp = null;
		try {
			fqqnp = new FullyQualifiedQueryNameParts();
			// TODO A query name in MySQL can contain a period. This needs to be fixed.
			String tmp[] = fullyQualifiedQueryName.split("\\."); // Regular expression, we need to hide the . so it looks like a .
			fqqnp.schemaName = tmp[0];
			fqqnp.queryName = tmp[1];
		} catch (Exception ex) {
			Log.logError("ProcessQueryController.parseFullyQualifiedQueryName():" + ex.getLocalizedMessage());
		}
		return fqqnp;
	}
	private void ProcessQuery(String fullyQualifiedQueryName) {
		FullyQualifiedQueryNameParts fqqnp = parseFullyQualifiedQueryName(fullyQualifiedQueryName);
		String sql;
		sql = txaSQL.getText(); // Process whatever is in the text area. It may have been edited by the user.
		// sql = = loadSQLFromSchema(fullyQualifiedQueryName);
		// txaSQL.setText(sql);
		rootQueryDefinition = new QueryDefinition(txtPqHostName.getText(), txtPqLoginName.getText(), txtPqPassword.getText(), new QueryTypeSelect(), fqqnp.queryName, sql, fqqnp.schemaName);
		rootQueryDefinition.crunchIt();
		scatterQueryParts(rootQueryDefinition);
	}
	private void scatterQueryParts(QueryDefinition qd) {
		try {
			// Attributes referenced in the query
			lvPqAttributes.getItems().clear();
			QueryAttributes queryAttributes = qd.getQueryAttributes();
			for (QueryAttribute queryAttribute : queryAttributes) {
				lvPqAttributes.getItems().add(new RowPqAttribute(queryAttribute, queryAttribute.getID(), queryAttribute.toString() + " (" + qd.getQueryAttributeDataType(queryAttribute) + ")"));
			}
			for (CompoundAlias qca : qd.getCompoundAliases()) {
				lvPqAttributes.getItems().add(new RowPqAttribute(qca, qca.getID().toString(), qca.toString()));
			}
			// Schemas referenced in the query
			lvPqSchemas.getItems().clear();
			QuerySchemas querySchemas = qd.getQuerySchemas();
			for (QuerySchema querySchema : querySchemas) {
				lvPqSchemas.getItems().add(querySchema.getSchemaName());
			}
			// Tables/Nested queries referenced in the query
			lvPqTables.getItems().clear();
			QueryTables queryTables = qd.getQueryTables();
			for (QueryTable queryTable : queryTables) {
				lvPqTables.getItems().add(queryTable.getSchemaName() + "." + queryTable.getTableName() + " (" + (queryTable.getIsQuery() ? "query" : "table") + ")");
			}
		} catch (Exception ex) {
			Log.logError("ProcessQueryController.scatterQueryParts():" + ex.getLocalizedMessage());
		}
	}
	@FXML
	public void txaCSVFolder_OnClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			int clickCount = 0;
			clickCount = event.getClickCount();
			if (clickCount == 2) { // It's a double click! Stuff in some path for a test case.
				setDefaultCVSFilePath();
			}
		}
	}
	private void setDefaultCVSFilePath() {
		txaCSVFolder.setText(Config.getConfig().getNeo4jDefaultImportFilePath()); // A default location for the CSV files.
	}

	@FXML
	public void txaSQL_OnClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			int clickCount = 0;
			clickCount = event.getClickCount();
			// Log.logProgress("ProcessAQuery.txaSQL_OnClick(): clickCount = " + clickCount);
			if (clickCount == 2) { // It's a double click! Stuff in some dummy SQL for a test case.
				txaSQL.setText(q01); // Watch case. The parser is still weak.
			}
		}
	}

	@FXML
	public void lvPqAttributes_OnClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			int clickCount = 0;
			clickCount = event.getClickCount();
			if (clickCount == 2) {
				// It's a double click! Open the Attribute Provenance window
				openAttributeProvenanceWindow();
			}
		}
	}

	private void openAttributeProvenanceWindow() {
//		AttributeProvenanceController x = new AttributeProvenanceController();
		if (rootQueryDefinition != null) {
			Log.logProgress("ProcessQueryController.openAttributeProvenanceWindow():"
					+ rootQueryDefinition.getSchemaName() + "." + rootQueryDefinition.getQueryName());
			try {
				FXMLLoader fxmlLoader = null;
				// Open the New Process A Query Window
				fxmlLoader = new FXMLLoader(getClass().getResource("attributeProvenance.fxml"));
				Parent root = fxmlLoader.load();
				AttributeProvenanceController controller = fxmlLoader.<AttributeProvenanceController>getController();
				controller.setStartingIndexInListView(lvPqAttributes.getSelectionModel().getSelectedIndex());
				controller.setQueryDefinition(rootQueryDefinition);
				controller.setLvPqAttributes(lvPqAttributes);
				controller.copyLvPqAttributes();
				controller.populateTreeView();
				Stage stage = new Stage();
				stage.initModality(Modality.NONE);
				stage.setOpacity(1);
				stage.setTitle(Config.getConfig().getApplicationTitle() + " - Attribute Provenance");
				stage.setScene(new Scene(root));
				stage.show();
			} catch (Exception ex) {
				Log.logError("ProcessQueryController.openAttributeProvenanceWindow():" + ex.getLocalizedMessage());
			}
		}
	}
	/**
	 * Run a test case based on a query.
	 * The test case must be in server instance localhost in schema queryprocessingtest
	 * @param testCase The query that is the test case
	 */
	private void ExecuteTestCase(String testCase, String sql) {
		try {
			loadPqSchemaNames();		// Load schemas and queries into the Tree View control
			tvSchemasAndQueries.getSelectionModel().selectFirst();
			TreeItem<String> ti = tvSchemasAndQueries.getSelectionModel().getSelectedItem();
			TreeItem<String> myTreeItem = searchTreeItem(ti, testCase);
			tvSchemasAndQueries.getSelectionModel().select(myTreeItem);

			processSelectedQueryInTreeView();

			txaSQL.setText(sql); // Watch case. The parser is still weak.

			ProcessQuery(txtPqQueryName.getText());
			showArtifacts(true);

			// We need a path to the Neo4j import folder otherwise these files will end up in a useless location ...
			createGraph();
		} catch (Exception ex) {
			Log.logError("ProcessQueryController.ExecuteTestCase():" + ex.getLocalizedMessage());
		}
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
	/**
	 * Used to represent a row in lvPqAttributes 
	 * @author nicomp
	 *
	 */
	public class RowPqAttribute {
		private String ID;
		private String text;
		private Name sourceObject;
		public RowPqAttribute(Name sourceObject, String ID, String text) {
			setID(ID);
			setText(text);
			setSourceObject(sourceObject);
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Name getSourceObject() {
			return sourceObject;
		}
		private void setSourceObject(Name sourceObject) {
			this.sourceObject = sourceObject;
		}
	}
	private void initlvPqAttributes() {
		lvPqAttributes.setCellFactory(lv -> new ListCell< RowPqAttribute>() {
			@Override
			public void updateItem(RowPqAttribute row, boolean empty) {
			    super.updateItem(row, empty) ;
			    setText(empty ? null : row.getText());
			}
		});
/*		
		lvPqAttributes.setConverter(new StringConverter<RowlvPqAttributes>() {
		    @Override
		    public String toString(RowlvPqAttributes object) {
		        return object.getText();
		    }

		    @Override
		    public RowlvPqAttributes fromString(String string) {
		        return lvPqAttributes.getItems().stream().filter(ap -> 
		            ap.getText().equals(string)).findFirst().orElse(null);
		    }	
		}); */
	}
}
