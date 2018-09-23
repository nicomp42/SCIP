// ":style reset" to reset the colors in an existing graph

package gui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.OutputStep;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
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
import edu.nicholdw.PhD.CodeProject.ETL.XMLParser;
import javafx.application.Application;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class ProcessETLController {
	@FXML	private TableView<Label> tvwETLSteps;
	@FXML	private AnchorPane apMainWindow;
	@FXML	private TextArea txaETLFilePath, txaOutputStepResults, txaInputStepResults, txaJoinStepResults, txaStepNamesResults;
	@FXML	private Button btnDBSubmit, btnETLBrowse;
	@FXML 	private Label lblContentsOfETL;
	@FXML	private Label lblWorking;
	@FXML	private Pane pneETLResults, pneETLLoad;
	@FXML 	private void btnETLSubmit_OnClick(ActionEvent event) {loadETL();}
	@FXML 	private void btnETLBrowse_OnClick(ActionEvent event) {browseETL();}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ProcessETLController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("ProcessETLController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("ProcessETLController.Initialize() complete");
	}
	private void setTheScene() {

	}
	private void browseETL() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		directoryChooser.setTitle("Select ETL Directory");
		Stage stage = (Stage) this.btnETLBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
		File file = (new FileChooser()).showOpenDialog(stage);
		if (file != null) {txaETLFilePath.setText(file.getAbsolutePath());}	
	}
	private void displayLoadETLResults(boolean visible) {
		pneETLResults.setVisible(visible);
	}
	private void disableETLLoadSelectionControls(boolean disable) {
		pneETLLoad.setDisable(disable);
	}
	private void loadETL() {
		String xmlFilePath = txaETLFilePath.getText().trim();
		Log.logProgress("ProcessETLController.loadDB() " + txaETLFilePath.getText().trim());
		displayLoadETLResults(false);
		disableETLLoadSelectionControls(true);
		lblWorking.setVisible(true);
		clearResultsControls();
		ArrayList<OutputStep> os = new ArrayList<OutputStep>();
		ArrayList<TableInputStep> is = new ArrayList<TableInputStep>();
		ArrayList<DBJoinStep> js = new ArrayList<DBJoinStep>();
		ArrayList<String> stepNames = new ArrayList<String>();
		// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
	    Task <Void> task = new Task<Void>() {
	        @Override public Void call() throws InterruptedException {
	        	// Do not write to any controls in here. An exception will be thrown. It's ugly.
	    		try {
	    			try {
	    				XMLParser myXMLParser = new XMLParser();
	    				myXMLParser.getStepNames(xmlFilePath, stepNames);
	    				myXMLParser.parseXMLForOutputSteps(xmlFilePath, os);
	    				myXMLParser.parseXMLForInputSteps(xmlFilePath, is);
	    				myXMLParser.parseXMLForDBJoinSteps(xmlFilePath, js);
	    				Log.logProgress("ProcessETLController.loadETL(): parsing complete.");
	    			} catch (Exception ex) {
	    				Log.logError("ProcessETLController.loadETL().Task: " + ex.getLocalizedMessage());
	    			}
	    		} catch (Exception ex) {
	    			Log.logError("ProcessETLController.loadETL().Task: " + ex.getLocalizedMessage());
	    		} finally {
	    		}
	        	return null;
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
			try {
				builder = factory.newDocumentBuilder();
				doc = builder.parse(xmlFilePath);
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();
				int counter = 0;
				for (String stepName: stepNames) {
					String tmp;
					counter++;
					tmp = String.valueOf(counter) + ": ";
					tmp += stepName;
					tmp += " (" + myXMLParser.getStepTypeAsString(xpath, doc, stepName) +  ")";
					txaStepNamesResults.appendText(tmp + System.getProperty("line.separator"));
					
			        ObservableList<Label> data = tvwETLSteps.getItems();
			        data.add(new Label(stepName));	//, new Label(tmp));
				}
				for (OutputStep outputStep: os) {
					txaOutputStepResults.appendText(outputStep.toString() + System.getProperty("line.separator"));
				}
				for (TableInputStep inputStep: is) {
					txaInputStepResults.appendText(inputStep.toString() + System.getProperty("line.separator"));
				}
				for (DBJoinStep joinStep: js) {
					txaJoinStepResults.appendText(joinStep.toString() + System.getProperty("line.separator"));
				}
				displayLoadETLResults(true);
				disableETLLoadSelectionControls(false);
			} catch (Exception ex) {
				Log.logError("ProcessETLController.loadETL(): " + ex.getLocalizedMessage());
			}
	      });
	    Thread thread = new Thread(task);
	    thread.setDaemon(true);
	    thread.start();
	    }
	
		private void clearResultsControls() {
			txaOutputStepResults.clear();
			txaInputStepResults.clear();
			txaJoinStepResults.clear();
			txaStepNamesResults.clear();
		}
	}

