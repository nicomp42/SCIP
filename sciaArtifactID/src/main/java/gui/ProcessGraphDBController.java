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
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jXML;
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
import javafx.concurrent.Task;
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

	@FXML	private AnchorPane apMainWindow;
	@FXML	private TextArea txaGraphDBFilePath, txaDBResults, txaSaveToXMLFile;
	@FXML	private TextArea txaGraphDB01FilePath, txaDB01Results, txaGraphDB02FilePath, txaDB02Results;
	@FXML	private Button btnDBSubmit, btnDBBrowse, btnDBCompare, btnDB01Browse, btnDB02Browse, btnXMLBrowse;
	@FXML 	private Label lblDB01UnmatchedNodes, lblDB02UnmatchedNodes, lblResults, lblWorking, lblSaveToXMLFile;
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
		displayResultsControls(false);
		lblWorking.setVisible(false);
		txaGraphDBFilePath.setText("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01");
		txaSaveToXMLFile.setText("c:\\Temp\\foo.xml");
	}
	private void displayResultsControls(boolean visible) {
		lblDB01UnmatchedNodes.setVisible(visible);
		lblDB02UnmatchedNodes.setVisible(visible);
		lblResults.setVisible(visible);
		txaDB01Results.setVisible(visible);
		txaDB02Results.setVisible(visible);
	}
	private void disableDBSelectionControls(boolean disable) {
		txaGraphDB01FilePath.setDisable(disable);
		txaGraphDB02FilePath.setDisable(disable);
		btnDB01Browse.setDisable(disable);
		btnDB02Browse.setDisable(disable);
	}
	@FXML private void btnDBSubmit_OnClick(ActionEvent event) {loadDB();}
	@FXML private void btnDBCompare_OnClick(ActionEvent event) {CompareDB();}
	
	private void loadDB() {
		txaDBResults.clear();
		Neo4jNodes neo4jNodes = Neo4jDB.readDatabase(txaGraphDBFilePath.getText().trim());
		for (Neo4jNode neo4jNode: neo4jNodes.getNeo4jNodes()) {
			txaDBResults.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
		}
		if (txaSaveToXMLFile.getText().trim().length() > 0) {
			Neo4jDB neo4jDB = new Neo4jDB("Neo4jDB", txaGraphDBFilePath.getText().trim(), neo4jNodes);
			Neo4jXML.WriteGraphDBToXMLFile(txaSaveToXMLFile.getText().trim(), neo4jDB);
		}
	}
	private void clearResults() {
		txaDB01Results.clear();
		txaDB02Results.clear();
	}
	private void CompareDB() {
		Neo4jNodes neo4jNodes01 = new Neo4jNodes();
		Neo4jNodes neo4jNodes02 = new Neo4jNodes();
		btnDBCompare.setVisible(false);
		btnDBCompare.setDisable(true);
		clearResults();
		displayResultsControls(false);
		lblWorking.setVisible(true);
		disableDBSelectionControls(true);
		// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
	    Task <Void> task = new Task<Void>() {
	        @Override public Void call() throws InterruptedException {
	        	// Do not access any controls in here. An exception will be thrown. It's ugly.
	    		try {
	    			Neo4jDB.compareDatabases(txaGraphDB01FilePath.getText(), txaGraphDB02FilePath.getText(), false, neo4jNodes01, neo4jNodes02);
	    		} catch (Exception ex) {
	    			Log.logError("ProcessGraphDBController.CompareDB().Task: " + ex.getLocalizedMessage());
	    		} finally {
	    		}
	        	return null;
	        }
	    };
	    task.setOnSucceeded(e -> {
	    	// This runs after the thread completes. 
			lblWorking.setVisible(false);
			displayResultsControls(true);
			btnDBCompare.setVisible(true);
			btnDBCompare.setDisable(false);
			disableDBSelectionControls(false);
			if (neo4jNodes01.countUnmatchedNodes() == 0 && neo4jNodes02.countUnmatchedNodes() == 0) {
				//lblResults.setStyle("-fx-background-color:green; -fx-font-color:white;");
				lblResults.setText("The graphs are equivalent.");
			} else {
				//lblResults.setStyle("-fx-background-color:red; -fx-font-color:white;");
				lblResults.setText("The graphs are not equivalent.");
			}
			for (Neo4jNode neo4jNode: neo4jNodes01.getNeo4jNodes()) {
				txaDB01Results.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
			}
			for (Neo4jNode neo4jNode: neo4jNodes02.getNeo4jNodes()) {
				txaDB02Results.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
			}
	      });
	    // Do the comparison work in a separate thread
	    Thread thread = new Thread(task);
	    thread.setDaemon(true);
	    thread.start();
	}
	@FXML
	private void btnDBBrowse_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select Graph DB Directory");
		Stage stage = (Stage) this.btnDBBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaGraphDBFilePath.setText(file.getAbsolutePath());}
	}
	@FXML
	private void btnXMLBrowse_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select XML File Directory");
		Stage stage = (Stage) this.btnXMLBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaSaveToXMLFile.setText(file.getAbsolutePath());}
	}
	@FXML
	private void btnDB01Browse_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select Graph DB 1 Directory");
		Stage stage = (Stage) this.btnDB01Browse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaGraphDB01FilePath.setText(file.getAbsolutePath());}
	}
	@FXML
	private void btnDB02Browse_OnClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select Graph DB 2 Directory");
		Stage stage = (Stage) this.btnDB02Browse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = directoryChooser.showDialog(stage);
		if (file != null) {txaGraphDB02FilePath.setText(file.getAbsolutePath());}
	}
}
