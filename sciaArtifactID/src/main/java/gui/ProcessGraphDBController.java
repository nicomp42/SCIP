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
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jNode;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jNodes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitionFileProcessing;
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
import javafx.scene.control.Label;
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

public class ProcessGraphDBController {

	// test cases. 
	private String qNestedQuery = "select testFieldATableC, testFieldATableD from qc join qd";
	private String q01 = "select testString, testInt, testDateTime, testDouble, Widget, testfieldatablec, testfieldatabled, testfieldatablee, testfieldatablef from ttablea, ttableb, qc, qd, tWidget, qlevelaa";
	private String qSimpleTable = "select testInt from ttablea";		// Can't be more simple!

	@FXML	private AnchorPane apMainWindow;
	@FXML	private TextArea txaGraphDB01FilePath, txaDB01Results;
	@FXML	private Button btnDB01Submit, btnDB01Browse;
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ProcessGraphDBController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("ProcessGraphDBController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("ProcessGraphDBController.Initialize() complete");
	}
	private void setTheScene() {
//		txtPqLoginName.setText(Config.getConfig().getMySQLDefaultLoginName());
//		txtPqPassword.setText(Config.getConfig().getMySQLDefaultPassword());
	}
	@FXML
	private void btnDB01Submit_OnClick(ActionEvent event) {loadDB01();}
	private void loadDB01() {
		txaDB01Results.clear();
		Neo4jNodes neo4jNodes = Neo4jDB.readDatabase(txaGraphDB01FilePath.getText());
		for (Neo4jNode neo4jNode: neo4jNodes.getNeo4jNodes()) {
			txaDB01Results.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
		}
	}
	@FXML
	private void btnDB01Browse_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select Graph DB Directory");
		Stage stage = (Stage) this.btnDB01Browse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaGraphDB01FilePath.setText(file.getAbsolutePath());}
	}
}
