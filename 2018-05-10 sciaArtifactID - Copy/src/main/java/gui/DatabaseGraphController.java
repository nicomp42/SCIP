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

import java.util.ArrayList;
import javafx.concurrent.Task;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaTopology;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.DatabaseGraphConfig;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.DatabaseGraphResults;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DatabaseGraphController {
	private Scene myScene;
	private Stage myStage;
	SchemaTopology schemaTopology;
	DatabaseGraphResults schemaTopologyResults;
	@FXML	private Pane pneFilter, pneQuickGraphs;
	@FXML	private AnchorPane apSchemaTopology;
	@FXML	private TextField txtHostName, txtLoginName, txtPassword, txtSchemaName;
	@FXML	private Button btnLoadSchemaNames, btnLoadSchema, btnProcessSchema, btnApplyFilter, btnAttributesInQueries, btnAttributesNotInQueries;
//	@FXML	private ListView<String> lvTables, lvAttributes, lvSchemas;
	@FXML	private TreeView<String> tvSchemas;
	@FXML	private Label lblSchemaToProcess, lblContentsOfDatabaseHost, lblResults, lblWorking;
	@FXML	private TextArea taResults;
	@FXML	private CheckBox cbClearDB, cbIncludeSchemaNodes, cbOpenInBrowser, cbDisplayAttributes, cbDisplayTables, cbDisplayQuerys;
	@FXML void mnuEditOpenBrowserWindow_OnAction(ActionEvent event) {openBrowserWindow();}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("SchemaTopologyController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("SchemaTopologyController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("SchemaTopologyController.Initialize() complete");
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
		showFilters(false);
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
//			java.net.URL imgURL = SchemaTopologyController.class.getClassLoader().getResource("images/Places-network-server-database-icon24px.png");
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
			Log.logError("SchemaTopologyController.loadTreeViewWithSchemaNamesAndQueries() : " + ex.getLocalizedMessage());
		}
	}
	@FXML
	void mnuFileExit_OnAction(ActionEvent event) {
		Stage stage = (Stage) this.btnLoadSchemaNames.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		stage.close();
	}
	@FXML
	void tvSchemas_OnClicked(MouseEvent event) {
		processSelectedSchemaInTreeView();
	}
	private void processSelectedSchemaInTreeView(){
		showProcessSchemaControls(false);
		showArtifacts(false);
		// We don't know what was clicked: this could throw an error. If a query was clicked, we're good to go.
		try {
			txtSchemaName.setText(/*tvSchemas.getSelectionModel().getSelectedItem().getParent().getValue()	+ "." + */tvSchemas.getSelectionModel().getSelectedItem().getValue());
			showProcessSchemaControls(true);
			taResults.setText("");
		} catch (Exception ex) {
			Log.logError("SchemaTopologyController.processSelectedQueryInTreeView() : " + ex.getLocalizedMessage());
		}
	}
	private void showProcessSchemaControls(Boolean visible) {
		btnProcessSchema.setVisible(visible);
		txtSchemaName.setVisible(visible);
		lblSchemaToProcess.setVisible(visible);
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
	private void btnProcessSchema_OnClick(ActionEvent event) throws InterruptedException {
		// OK, the user has drilled down to a schema. Generate the topology...
		processSchema();
		showArtifacts(true);
	}
	private void processSchema() {
 		if ( txtSchemaName.getText().trim().length() > 0) {
			schemaTopologyResults = new DatabaseGraphResults();
			DatabaseGraphConfig schemaTopologyConfig = new DatabaseGraphConfig();
			// Here is some stuff we want to run in another thread so the window has time to update itself.
			 Task<Void> runnable = new Task<Void>() {		// https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
				 // This thread cannot write to JavaFX controls, even in the Debug window.
				  public Void call() {
					Log.logProgress("SchemaTopologyController.ProcessSchema(): entering local thread. ****************");
//					try {Thread.sleep(2000);} catch (InterruptedException e) {}
					Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
					Neo4jDB.getDriver();
					if (cbClearDB.isSelected()) {Neo4jDB.clearDB();}
					schemaTopologyConfig.setIncludeSchemaInGraph(cbIncludeSchemaNodes.isSelected());
					schemaTopologyConfig.setUseFriendlyNameAsDisplayName(true);
				    schemaTopology = new SchemaTopology(schemaTopologyConfig, txtHostName.getText(), txtLoginName.getText(), txtPassword.getText(), txtSchemaName.getText(), null);
					try {
						schemaTopologyResults = schemaTopology.generateGraph();
						if (cbOpenInBrowser.isSelected() ) {
							Browser browser = Browser.prepareNewBrowser();
							browser.initAndLoad(null);
						}
					} catch (Exception e) {
						Log.logError("SchemaTopologyController.ProcessSchema() local thread: " + e.getLocalizedMessage());
					}
					Log.logProgress("SchemaTopologyController.ProcessSchema(): exiting local thread");
					return null;
				}
			};
			long startTime = System.currentTimeMillis();
			// Here is the cleanup after the background thread completes.
		    runnable.setOnSucceeded(e -> {
				Log.logProgress("SchemaTopologyController.ProcessSchema(): local thread complete. ****************");
		        final long endTime = System.currentTimeMillis();
		        disableEverything(false);
				displayWorkingMessage(false);
				taResults.setText(schemaTopologyResults.toString());
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
				Log.logError("SchemaTopologyController.ProcessSchema(): " + e.getLocalizedMessage());
			}
		} else {
			(new Alert(Alert.AlertType.ERROR, "Please enter an existing schema name or select one from the list", ButtonType.OK)).showAndWait();
			txtSchemaName.requestFocus();
		}
	}
	/**
	 * Tell the user to be patient: the query is being processed.
	 * @param status true to display the message, false otherwise.
	 */
	private void displayWorkingMessage(boolean status) {
		lblWorking.setDisable(!status);
		lblWorking.setVisible(status);
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
			apSchemaTopology.setDisable(status);
		} catch (Exception e) {
			Log.logError("SchemaTopologyController.enableEverything(): " + e.getLocalizedMessage());
		}
	}
	/**
	 * apply the graph filters selected by the user in the pneFilter pane
	 */
	public void applyFilters() {
		// match (n) where n:Table or n:Attribute return n
		String cypherStatement = "MATCH (n)";
		String or = "";
		String where = " WHERE ";

		if (cbDisplayAttributes.isSelected()) {
				cypherStatement += (where + or + " n:Attribute ");
				where = "";
				or = " or ";
		}
		if (cbDisplayTables.isSelected()) {
			cypherStatement += (where + or + " n:Table ");
			where = "";
			or = " or ";
		}
		if (cbDisplayQuerys.isSelected()) {
			cypherStatement += (where + or + "n:Query ");
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
	 * Draw the graph with only the attributes that are referenced by at least one query
	 */
	private void GenerateGraphAttributesInQueries() {
		String cypherStatement = "MATCH (n:Attribute) WHERE size((n)-[]-()) > 1 RETURN n UNION MATCH (n:Table) RETURN n union MATCH (n:Query) RETURN n;";
		Browser browser = Browser.prepareNewBrowser();
		browser.initAndLoad(cypherStatement);
	}
	/**
	 * Draw the graph with only the attributes that are not referenced by at least one query
	 */
	private void GenerateGraphAttributesNotInQueries() {
		String cypherStatement = "MATCH (n:Attribute) WHERE size((n)-[]-()) < 2 RETURN n UNION MATCH (n:Table) RETURN n union MATCH (n:Query) RETURN n;";
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
}
