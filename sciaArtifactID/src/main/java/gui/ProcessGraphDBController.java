/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
// ":style reset" to reset the colors in an existing graph

package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jNode;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jNodes;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jXML;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/*
 		Some controls I may use later in the DB comparison logic
        <CheckBox fx:id="cbTables" layoutX="17.0" layoutY="195.0" mnemonicParsing="false" text="Tables" />
        <CheckBox fx:id="cbAttributes" layoutX="17.0" layoutY="217.0" mnemonicParsing="false" text="Attributes" />
        <CheckBox fx:id="cbRelationships" layoutX="17.0" layoutY="238.0" mnemonicParsing="false" text="Relationships" />
        <Label layoutX="19.0" layoutY="174.0" text="Artifacts to include in results" />
        <Rectangle arcHeight="5.0" arcWidth="5.0" fill="#1ffff9" height="97.0" layoutX="9.0" layoutY="171.0" opacity="0.06" stroke="BLACK" strokeType="INSIDE" strokeWidth="2.0" width="200.0" />
 */
public class ProcessGraphDBController {

	@FXML	private AnchorPane apMainWindow;
	@FXML	private TextArea txaGraphDBFilePath, txaDBResults, txaSaveToXMLFile;
	@FXML	private TextArea txaGraphDB01FilePath, txaDB01Results, txaGraphDB02FilePath, txaDB02Results, txaSaveResultsToThisFolder;
	@FXML	private Button btnDBSubmit, btnDBBrowse, btnDBCompare, btnDB01Browse, btnDB02Browse, btnXMLBrowse, btnSaveResultsToThisFolderBrowse, btnSaveResultsToThisFolder;
	@FXML 	private Label lblDB01UnmatchedNodes, lblDB02UnmatchedNodes, lblResults, lblCompareWorking, lblSaveToXMLFile, lblContentsOfGraphDB;
	@FXML	private Label lblLoadWorking;
	@FXML	private Pane pneDBResults, pneDBLoad;
	@FXML	private CheckBox cbHideRelationships, cbIgnoreKey;
//	@FXML	private CheckBox cbTables, cbRelationships, cbAttributes;
	@FXML	private Label lblSaveResultsToThisFolder;
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
		displayComparisonResultsControls(false);
		lblCompareWorking.setVisible(false);
		txaGraphDBFilePath.setText("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01");
		txaSaveToXMLFile.setText("c:\\Temp\\foo.xml");
		displayLoadGraphDBResults(false);
//		cbTables.setSelected(true);
//		cbRelationships.setSelected(true);
//		cbAttributes.setSelected(true);
	}
	private void displayLoadGraphDBResults(boolean visible) {
		pneDBResults.setVisible(visible);
	}
	private void displayComparisonResultsControls(boolean visible) {
		lblDB01UnmatchedNodes.setVisible(visible);
		lblDB02UnmatchedNodes.setVisible(visible);
		lblResults.setVisible(visible);
		txaDB01Results.setVisible(visible);
		txaDB02Results.setVisible(visible);
		lblSaveResultsToThisFolder.setVisible(visible);
		txaSaveResultsToThisFolder.setVisible(visible);
		btnSaveResultsToThisFolderBrowse.setVisible(visible);
		btnSaveResultsToThisFolder.setVisible(visible);
	}
	private void disableDBSelectionControls(boolean disable) {
		txaGraphDB01FilePath.setDisable(disable);
		txaGraphDB02FilePath.setDisable(disable);
		btnDB01Browse.setDisable(disable);
		btnDB02Browse.setDisable(disable);
	}
	private void disableDBLoadSelectionControls(boolean disable) {
		pneDBLoad.setDisable(disable);
	}
	@FXML private void btnDBSubmit_OnClick(ActionEvent event) {loadDB();}
	@FXML private void btnSaveResultsToThisFolder_OnClick(ActionEvent event) {saveResultsToThisFolder();}
	@FXML private void btnSaveResultsToThisFolderBrowse_OnClick(ActionEvent event) {SaveResultsToThisFolderBrowse();}
	@FXML private void btnDBCompare_OnClick(ActionEvent event) {CompareDB();}
	@FXML private void cbHideRelationships_OnClick(ActionEvent event) {processCBHideRelationships();}
	private void processCBHideRelationships() {}	// We read the state of this control when displaying the results of a DB Compare
	private void saveResultsToThisFolder() {
		Boolean statusDB1 = false, statusDB2 = false;
		try {
			String directory = txaSaveResultsToThisFolder.getText();
			if (directory.trim().length() > 0) {
				String title = "Unmatched artifacts in " + txaGraphDB01FilePath.getText() + " when compared to " + txaGraphDB02FilePath.getText();
				statusDB1 = saveResults(title, directory, txaGraphDB01FilePath.getText(), txaDB01Results);
				title = "Unmatched artifacts in " + txaGraphDB02FilePath.getText() + " when compared to " + txaGraphDB01FilePath.getText();
				statusDB2 = saveResults(title, directory, txaGraphDB02FilePath.getText(), txaDB02Results);
			}
		} catch (Exception ex) {
			Log.logError("ProcessGraphController.saveResultsToThisFolder(): " + ex.getLocalizedMessage());
 		} finally {
 			if (statusDB1 && statusDB2) {
 	    		Alert alert = new Alert(AlertType.INFORMATION);
 	    		alert.setTitle("Results saved.");
 	    		alert.setHeaderText("Results were written to files.");
 	    		alert.showAndWait();
 			}
		}
	}
	private Boolean saveResults(String title, String targetDirectory, String sourceOfGraph, TextArea txaResults) {
		Boolean allIsWell = true;
		BufferedWriter bw = null;
		try {
			File f = new File(sourceOfGraph);
			Path p = Paths.get(f.toURI());
			String file = p.getFileName().toString();
			String db1File = targetDirectory + "\\" + file.toString() + ".txt";
			bw = new BufferedWriter(new FileWriter(db1File));
			String crlf = System.getProperty("line.separator");
			if (title.length() > 0) {bw.write(title + crlf);}
			if (txaResults.getText().trim().length() > 0) {
				for (String line : txaResults.getText().trim().split("\\n")) {
					bw.write(line + crlf);
				}
			} else {
				bw.write("There are no unmatched artifacts in the graph.");
			}
		} catch (Exception ex) {
			allIsWell = false;
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error during save");
    		alert.setHeaderText("");
    		alert.setContentText(ex.getLocalizedMessage());
    		alert.showAndWait();
			Log.logError("ProcessGraphController.saveResults(): " + ex.getLocalizedMessage());
 		} finally {
			try {bw.close();} catch(Exception ex) {}
		}
		return allIsWell;
	}
	private void SaveResultsToThisFolderBrowse() {
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			directoryChooser.setTitle("Select the location for the results files");
			Stage stage = (Stage) this.btnSaveResultsToThisFolderBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
			File file = directoryChooser.showDialog(stage);
			if (file != null) {txaSaveResultsToThisFolder.setText(file.getAbsolutePath());}
		} catch (Exception ex) {
			Log.logError("ProcessGraphController.SaveResultsToThisFolderBrowse(): " + ex.getLocalizedMessage());
		}
	}
	private void loadDB() {
		Log.logProgress("ProcessGraphDBController.loadDB() " + txaGraphDBFilePath.getText().trim());
		displayLoadGraphDBResults(false);
		disableDBLoadSelectionControls(true);
		lblLoadWorking.setVisible(true);
		txaDBResults.clear();
		Neo4jNodes neo4jNodes = new Neo4jNodes();
		// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
	    Task <Void> task = new Task<Void>() {
	        @Override public Void call() throws InterruptedException {
	        	// Do not access any controls in here. An exception will be thrown. It's ugly.
	    		try {
	    			Neo4jDB.readDatabase(txaGraphDBFilePath.getText().trim(), neo4jNodes);
	    		} catch (Exception ex) {
	    			Log.logError("ProcessGraphDBController.loadDB().Task: " + ex.getLocalizedMessage());
	    		} finally {
	    		}
	        	return null;
	        }
	    };
	    task.setOnSucceeded(e -> {
			lblLoadWorking.setVisible(false);
			for (Neo4jNode neo4jNode: neo4jNodes.getNeo4jNodes()) {
				txaDBResults.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
			}
			if (txaSaveToXMLFile.getText().trim().length() > 0) {
				Neo4jDB neo4jDB = new Neo4jDB("Neo4jDB", txaGraphDBFilePath.getText().trim(), neo4jNodes);
				Neo4jXML.WriteGraphDBToXMLFile(txaSaveToXMLFile.getText().trim(), neo4jDB);
			}
			displayLoadGraphDBResults(true);
			disableDBLoadSelectionControls(false);
	      });
	    Thread thread = new Thread(task);
	    thread.setDaemon(true);
	    thread.start();
	    }
	private void clearResults() {
		txaDB01Results.clear();
		txaDB02Results.clear();
	}
	private void CompareDB() {
		Log.logProgress("ProcessGraphDB.CompareDB(): " + txaGraphDB01FilePath.getText().trim() + ", " + txaGraphDB02FilePath.getText().trim());
		Neo4jNodes neo4jNodes01 = new Neo4jNodes();
		Neo4jNodes neo4jNodes02 = new Neo4jNodes();
		btnDBCompare.setVisible(false);
		btnDBCompare.setDisable(true);
		cbHideRelationships.setVisible(false);
		cbIgnoreKey.setVisible(false);
		clearResults();
		displayComparisonResultsControls(false);
		lblCompareWorking.setVisible(true);
		disableDBSelectionControls(true);
		// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
	    Task <Void> task = new Task<Void>() {
	        @Override public Void call() throws InterruptedException {
	    		try {
	    			String [] keysToIgnore = {"key"};
	    			if (!cbIgnoreKey.isSelected()) {keysToIgnore = null; }
	    			Neo4jDB.compareDatabases(txaGraphDB01FilePath.getText().trim(), txaGraphDB02FilePath.getText().trim(), false, neo4jNodes01, neo4jNodes02, keysToIgnore);
	    		} catch (Exception ex) {
	    			Log.logError("ProcessGraphDBController.CompareDB().Task: " + ex.getLocalizedMessage());
	    		} finally {
	    		}
	        	return null;
	        }
	    };
	    task.setOnSucceeded(e -> {
	    	// This runs after the thread completes. 
			lblCompareWorking.setVisible(false);
			displayComparisonResultsControls(true);
			btnDBCompare.setVisible(true);
			btnDBCompare.setDisable(false);
			cbHideRelationships.setVisible(true);
			disableDBSelectionControls(false);
			cbIgnoreKey.setVisible(true);
			displayResults(neo4jNodes01, neo4jNodes02);
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
	/**
	 * Copy the results of the comparison to the form controls
	 * @param neo4jNodes01 Results from DB 01
	 * @param neo4jNodes02 Results from DB 02
	 */
	private void displayResults(Neo4jNodes neo4jNodes01, Neo4jNodes neo4jNodes02) {
		if (neo4jNodes01.countUnmatchedNodes() == 0 && neo4jNodes02.countUnmatchedNodes() == 0) {
			//lblResults.setStyle("-fx-background-color:green; -fx-font-color:white;");
			lblResults.setText("The graphs are equivalent.");
		} else {
			//lblResults.setStyle("-fx-background-color:red; -fx-font-color:white;");
			lblResults.setText("The graphs are not equivalent.");
		}
		for (Neo4jNode neo4jNode: neo4jNodes01.getNeo4jNodes()) {
			if (cbHideRelationships.isSelected()) {
				txaDB01Results.appendText(neo4jNode.toStringNoRelationships() + System.getProperty("line.separator"));					
			} else {
				txaDB01Results.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
			}
		}
		for (Neo4jNode neo4jNode: neo4jNodes02.getNeo4jNodes()) {
			if (cbHideRelationships.isSelected()) {
				txaDB02Results.appendText(neo4jNode.toStringNoRelationships() + System.getProperty("line.separator"));
			} else {
				txaDB02Results.appendText(neo4jNode.toString() + System.getProperty("line.separator"));
			}
		}
	}
}
