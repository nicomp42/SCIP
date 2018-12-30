package gui;

import java.io.File;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTables;
import edu.UC.PhD.CodeProject.nicholdw.attributeParts.AttributeParts;
import edu.UC.PhD.CodeProject.nicholdw.attributeProvenance.AttributeProvenanceForNe04j;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Display the provenance of an attribute in the target query in a dedicated window
 * @author nicomp
 */
public class AttributeProvenanceController /* extends Application */ {

	@FXML private TreeView<String> tvAttributeProvenance;
	@FXML private TextArea txaCSVFolder;
	@FXML private Button btnOK, btnSaveSchemaArtifactsToCSVFiles, btnExportCSVFilesToNeo4j, btnBrowseForCSVFolder, btnProcessEverything;
	@FXML private ComboBox<String> cbPqAttributes;
	@FXML private CheckBox cbClearNeo4jBeforeExport, cbOpenBrowserWindowAfterExporting;

	private QueryDefinition queryDefinition;
	private ListView<String> lvPqAttributes;	// Already populated in ProcessQueryController. We just need to copy its' items into the combo-box on this form
	private int startingIndexInListView;		// The index of the attribute selected by the user in ProcessQueryController just before this form was loaded. It will be used to initialize the controls on this form.

	public AttributeProvenanceController() {
	}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("AttributeProvenanceController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("AttributeProvenanceController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("AttributeProvenanceController.Initialize() complete");
	}
	@FXML void btnSaveSchemaArtifactsToCSVFiles_OnClick(ActionEvent event) {
		saveSchemaArtifactsToCSVFiles();
	}
	@FXML void btnBrowseForCSVFolder_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select Neo4j import Directory");
		Stage stage = (Stage) this.btnBrowseForCSVFolder.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {
			txaCSVFolder.setText(file.getAbsolutePath());
		}
	}
	@FXML void btnExportCSVFilesToNeo4j_OnClick(ActionEvent event) {
		String CSVFolder = txaCSVFolder.getText().trim();
		if (CSVFolder.length() != 0) {
			exportCSVToNeo4j();
			if (cbOpenBrowserWindowAfterExporting.isSelected() == true) {
				openBrowserWindow();
			}
		} else {
    		Alert alert = new Alert(AlertType.ERROR);		// http://code.makery.ch/blog/javafx-dialogs-official/
    		alert.setTitle("File path needed");
    		alert.setHeaderText("Enter a destination folder for the CSV files. \n It is probably the 'import' folder in the current Neo4j DB. \n Double-click the destination folder for the default value.");
    		alert.setContentText("");
    		alert.showAndWait();
    		txaCSVFolder.requestFocus();
		}
	}
	@FXML
	public void txaCSVFolder_OnClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			int clickCount = 0;
			clickCount = event.getClickCount();
			if (clickCount == 2) { // It's a double click! Stuff in the default path for a test case.
				setDefaultCVSFilePath();
			}
		}
	}
	private void saveSchemaArtifactsToCSVFiles() {
		String CSVFolder = txaCSVFolder.getText().trim();
		if (CSVFolder.length() != 0) {
			// This is hinkey: we have a formatted string with schema, table, attribute and we will extract those individual values
			AttributeParts attributeParts = new AttributeParts();
			attributeParts.split(cbPqAttributes.getSelectionModel().getSelectedItem());
			AttributeProvenanceForNe04j.exportCSVFiles(attributeParts, queryDefinition, txaCSVFolder.getText() );
		} else {
    		Alert alert = new Alert(AlertType.ERROR);		// http://code.makery.ch/blog/javafx-dialogs-official/
    		alert.setTitle("File path needed");
    		alert.setHeaderText("Enter a destination folder for the CSV files. \n It is probably the 'import' folder in the current Neo4j DB. \n Double-click the destination folder for the default value.");
    		alert.setContentText("");
    		alert.showAndWait();
    		txaCSVFolder.requestFocus();
		}
	}
	public void setDefaultCVSFilePath() {
		txaCSVFolder.setText(Config.getConfig().getNeo4jDefaultImportFilePath()); // A default location for the CSV files.
	}
	/**
	 * Send the provenance of the selected attribute in cbPqAttributes to the current Neo4j DB
	 */
	private void exportCSVToNeo4j() {
		// If the user selected the check box, clear the current Neo4j DB before exporting
		if (cbClearNeo4jBeforeExport.isSelected()) {
			Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
			Neo4jDB.getDriver();
			Neo4jDB.clearDB();
		}
		AttributeProvenanceForNe04j.executeCypherQueries( txaCSVFolder.getText());
	}
	private void setTheScene() {
		// Lambda method
		cbPqAttributes.setOnAction((event) -> {
		    String attribute = cbPqAttributes.getSelectionModel().getSelectedItem();
		    Log.logProgress("AttributeProvenanceController.setTheScene(): cbPqAttributes Action selected: " + attribute);
		    populateTreeView();
		});
	}
	public void copyLvPqAttributes() {
		for (String string : lvPqAttributes.getItems()) {
			cbPqAttributes.getItems().add(string);
		}
		cbPqAttributes.getSelectionModel().select(startingIndexInListView);
	}
	public void setQueryDefinition(QueryDefinition queryDefinition) {
		this.queryDefinition = queryDefinition;
	}
	@FXML void cbPqAttributes_OnClick(MouseEvent event) {
		// Build provenance in the Tree View control for the selected attribute
		tvAttributeProvenance.setRoot(null);
	}
	@FXML
	public void btnProcessEverything_OnClick(ActionEvent event) {
		processEverything();
	}
//	@FXML
//	public void btnOK_OnClick(ActionEvent event) {
//		Stage stage = (Stage) this.cbPqAttributes.getScene().getWindow();		// I picked some arbitrary control to look up the scene.
//		stage.close();
//	}
	/**
	 * Send all the artifacts in the drop-down list to CSV files then on to the active Neo4j DB
	 */
	private void processEverything() {
		Log.logProgress("AttributeProvenanceController.processEverything()");
		try {
			String CSVFolder = txaCSVFolder.getText().trim();
			if (CSVFolder.length() != 0) {
				if (cbClearNeo4jBeforeExport.isSelected()) {Neo4jDB.clearDB();}
				ObservableList<String> items = cbPqAttributes.getItems();
				for (String s: items) {
					AttributeParts attributeParts;
					attributeParts = new AttributeParts();
					attributeParts.split(s);
					AttributeProvenanceForNe04j.exportCSVFiles(attributeParts, queryDefinition, txaCSVFolder.getText());
					AttributeProvenanceForNe04j.executeCypherQueries(txaCSVFolder.getText());
				}
				if (cbOpenBrowserWindowAfterExporting.isSelected() == true) {openBrowserWindow();}
			} else {
	    		Alert alert = new Alert(AlertType.ERROR);		// http://code.makery.ch/blog/javafx-dialogs-official/
	    		alert.setTitle("File path needed");
	    		alert.setHeaderText("Enter a destination folder for the CSV files. \n It is probably the 'import' folder in the current Neo4j DB. \n Double-click the destination folder for the default value.");
	    		alert.setContentText("");
	    		alert.showAndWait();
	    		txaCSVFolder.requestFocus();
			}
		} catch (Exception ex) {
			Log.logError("AttributeProvenanceController.processEverything(): ", ex);
		}
	}
	public void start(Stage primaryStage) throws Exception {
	}
	public void populateTreeView() {
		Log.logProgress("AttributeProvenanceController.populateTreeView()");
		try {
			// TODO: This is hinkey: we have a formatted string with schema, table, attribute, alias, data type and we will extract those individual values.
			// This is more hinkey because the full provenance is not in the structure returned by buildProvenance. 
			AttributeParts attributeParts = new AttributeParts();
			attributeParts.split(cbPqAttributes.getSelectionModel().getSelectedItem());	// Doesn't work for compound attribute entries in this ComboBox
			QueryTables qt = new QueryTables();
			QueryDefinition.buildProvenance(queryDefinition, attributeParts.getAliasName(), qt);
			TreeItem<String> rootItem = null;
			int nodeCount = 1;
			for (QueryTable queryTable: qt) {
				if (nodeCount == 1) {
					Node rootIcon = new ImageView(new Image(ProcessQueryController.class.getClassLoader().getResourceAsStream("images/Places-network-server-database-icon24px.png")));
//					TreeItem<String> rootItem = new TreeItem<String> (queryDefinition.getSchemaName() + "." + queryDefinition.getQueryName() + "." + attributeParts.getAliasName(), rootIcon);
					String aliasName;
					aliasName = queryTable.getQueryAttributeProvenance().getAliasNames().toString();
					rootItem = new TreeItem<String>(queryTable.getSchemaName() + "." + queryTable.getTableName() + "." + aliasName, rootIcon);
					rootItem.setExpanded(true);
					tvAttributeProvenance.setRoot(rootItem);
				} else {
					Node schemaIcon = new ImageView(new Image(ProcessQueryController.class.getClassLoader().getResourceAsStream("images/database-iconSilver24px.png")));
	//				TreeItem<String> schemaItem = new TreeItem<String>(queryTable.getSchemaName() + "." + queryTable.getTableName() + "." + attributeParts.getAttributeName(), schemaIcon);  2018=05-26
					String aliasName;
					aliasName = queryTable.getQueryAttributeProvenance().getAliasNames().toString();
					TreeItem<String> provenanceItem = new TreeItem<String>(queryTable.getSchemaName() + "." + queryTable.getTableName() + "." + aliasName, schemaIcon);
					rootItem.getChildren().add(provenanceItem);
					rootItem = provenanceItem;
					rootItem.setExpanded(true);
				}
				nodeCount++;
			}
		} catch (Exception ex) {
			Log.logError("AttributeProvenanceController.populateTreeView(): " + ex.getLocalizedMessage());
		}
	}
	public ListView<String> getLvPqAttributes() {return lvPqAttributes;}
	public void setLvPqAttributes(ListView<String> lvPqAttributes) {this.lvPqAttributes = lvPqAttributes;}
	public int getStartingIndexInListView() {return startingIndexInListView;}
	public void setStartingIndexInListView(int startingIndexInListView) {this.startingIndexInListView = startingIndexInListView;}
	private void openBrowserWindow() {Browser.openBrowserWindow();}
}
