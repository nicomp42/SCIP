/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
// ":style reset" to reset the colors in an existing graph

package gui;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.OutputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnection;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnections;
import edu.nicholdw.PhD.CodeProject.ETL.ETLJob;
import edu.nicholdw.PhD.CodeProject.ETL.ETLJobs;
import edu.nicholdw.PhD.CodeProject.ETL.ETLProcess;
import edu.nicholdw.PhD.CodeProject.ETL.ETLStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLSteps;
import edu.nicholdw.PhD.CodeProject.ETL.XMLParser;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class ProcessETLController {
	@FXML	private TableView<GUIETLStep> tvETLSteps;
	@FXML	private TableView<GUIETLConnection> tvETLConnections;
	@FXML	private AnchorPane apMainWindow;
	@FXML	private TextArea txaETLFilePath, txaOutputStepResults, txaInputStepResults, txaJoinStepResults, txaStepNamesResults, txaETLJobs;
	@FXML	private Button btnETLSubmit, btnETLBrowse, btnCreateGraph;
	@FXML 	private Label lblContentsOfETL;
	@FXML	private Label lblWorking;
	@FXML	private CheckBox cbClearDB;
	@FXML	private Pane pneETLResults, pneETLLoad;
	@FXML 	private void btnETLSubmit_OnClick(ActionEvent event) {loadETL(txaETLFilePath.getText().trim());}
	@FXML 	private void btnETLBrowse_OnClick(ActionEvent event) {browseETL();}
	@FXML	private void btnCreateGraph_OnClick(ActionEvent event) {createGraph();}
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
	private DataBrowseController dataBrowseController;
	private ETLProcess etlProcess;
	/***
	 * Create a GraphDB from the currently loaded ETL steps
	 */
	private void createGraph() {
		Log.logProgress("ProcessETLController.createGraph()");
		try {
			Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(),  Config.getConfig().getNeo4jDBDefaultPassword());
			if (cbClearDB.isSelected()) {Neo4jDB.clearDB();}
			ETLProcess.createGraph(etlProcess);
		} catch (Exception ex) {
			Log.logError("ProcessETLController.createGraph(): " + ex.getLocalizedMessage());
		}
	}
	private void setTheScene() {
		loadTableViewWithETLSteps(new ETLSteps());		// Load nothing. 
		addDoubleClickHandler();
		dataBrowseController = null;
		cbClearDB.setVisible(false);
		displayLoadETLResults(false);
		btnETLSubmit.setVisible(false);
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
		            System.out.println(guiETLStep.toString());
		            if (dataBrowseController == null) {
		            	dataBrowseController = openDataBrowse();
		            } 
	            	dataBrowseController.appendToTextArea(guiETLStep.toString());
	            	ETLStep etlStep = etlProcess.getETLSteps().getETLStep(guiETLStep.getStepName());
	            	etlProcess.processTableInputStepQuery(etlStep);
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
			FileChooser fileChooser = new FileChooser();
			String userHome = System.getProperty("user.home");
			fileChooser.setInitialDirectory(new File(userHome + "/"));
			// Try to use the previous file path that was selected by the user
			try {
				String currentDir = txaETLFilePath.getText().trim();
				if (currentDir.length() > 0) {
					File file = (new File(txaETLFilePath.getText())).getParentFile();
					fileChooser.setInitialDirectory(file);
				}
			} catch (Exception ex) {
				Log.logError("ProcessETLController.browseETL directoryChooser(): " + ex.getLocalizedMessage());
			}
			fileChooser.setTitle("Select ETL File");
			Stage stage = (Stage) this.btnETLBrowse.getScene().getWindow(); // I picked some arbitrary control to look up the scene.
			File file = (fileChooser.showOpenDialog(stage));
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
		btnCreateGraph.setVisible(visible);
		cbClearDB.setVisible(visible);
	}
	private void disableETLLoadSelectionControls(boolean disable) {
		pneETLLoad.setDisable(disable);
	}
	/**
	 * Process the XML file that contains some Pentaho steps. It could be a .ktr (transformation) or a .kjb (job) 
	 * @param xmlFilePath Physical file path to the disk file. We'll take it from there.
	 */
	private void loadETL(String xmlFilePath) {
		etlProcess = new ETLProcess();
		Log.logProgress("ProcessETLController.loadDB() " + txaETLFilePath.getText().trim());
		displayLoadETLResults(false);
		disableETLLoadSelectionControls(true);
		lblWorking.setVisible(true);
		clearResultsControls();
		ArrayList<OutputStep> os = new ArrayList<OutputStep>();
		ArrayList<TableInputStep> is = new ArrayList<TableInputStep>();
		ArrayList<DBJoinStep> js = new ArrayList<DBJoinStep>();
		ArrayList<String> stepNames = new ArrayList<String>();
		ArrayList<String> connectionNames = new ArrayList<String>();
		ETLJobs etlJobs = new ETLJobs();
		// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
	    Task <Void> task = new Task<Void>() {
	        @Override public Void call() throws InterruptedException {
	        	// Do not write to any controls in here. An exception will be thrown. It's ugly.
	    		try {
	    			try {
	    				XMLParser myXMLParser = new XMLParser();
	    				myXMLParser.getStepNames(xmlFilePath, stepNames);
	    				myXMLParser.getConnectionNames(xmlFilePath, connectionNames);
	    				myXMLParser.parseXMLForOutputSteps(xmlFilePath, os);
	    				myXMLParser.parseXMLForInputSteps(xmlFilePath, is);
	    				myXMLParser.parseXMLForDBJoinSteps(xmlFilePath, js);
	    				myXMLParser.getETLJobs(xmlFilePath, etlJobs);
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
					String tmp, stepType, sql, table, connection;
					counter++;
					tmp = String.valueOf(counter) + ": ";
					tmp += stepName;
					stepType = myXMLParser.getStepTypeAsString(xpath, doc, stepName);
					sql = myXMLParser.getSQL(xpath, doc, stepName);
					table = myXMLParser.getSomethingInAStep(xpath, doc, stepName, "table");
					connection = myXMLParser.getSomethingInAStep(xpath, doc, stepName, "connection");
					tmp += " (" + stepType +  ")";
					txaStepNamesResults.appendText(tmp + System.getProperty("line.separator"));
					// Add this new step to the collection of steps
					etlProcess.getETLSteps().addETLStep(new ETLStep(stepName, stepType, sql, table, connection));
				}
				//ETLConnections etlConnections = new ETLConnections();
				for (String connectionName: connectionNames) {
					etlProcess.getETLConnections().addETLConnection(new ETLConnection(connectionName, // These thing names are case-sensitive in the .XML file
							                                          myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "server"),
							                                          myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "database"),
							                                          myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "username"),
							                                          myXMLParser.getSomethingInAConnection(xpath, doc, connectionName, "type")
							                                         ));
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
				// Does the file reference other jobs?
				for (ETLJob etlJob : etlJobs) {
					txaETLJobs.appendText(etlJob.toString() + System.getProperty("line.separator"));
				}
				// Copy the collection of steps to the table view control on the GUI
				loadTableViewWithETLSteps(etlProcess.getETLSteps());
				displayLoadETLResults(true);
				disableETLLoadSelectionControls(false);
				loadTableViewWithETLConnections(etlProcess.getETLConnections());
				// OK, the ETLProcess object is loaded up with ETL Steps and Connections and stuff
				// We should be able to parse the queries in the table input steps
				etlProcess.processTableInputStepQueries();
				etlProcess.processTableOutputStepsFields(xmlFilePath);
			} catch (Exception ex) {
				Log.logError("ProcessETLController.loadETL().task.setOnSucceeded: " + ex.getLocalizedMessage());
				disableETLLoadSelectionControls(false);
			}
	      });
	    Thread thread = new Thread(task);
	    thread.setDaemon(true);
	    thread.start();
	    }
		private void loadTableViewWithETLConnections(ETLConnections etlConnections) {
			ETLConnection.loadTableViewWithETLConnections(tvETLConnections, etlConnections);
		}
		private void clearResultsControls() {
			txaOutputStepResults.clear();
			txaInputStepResults.clear();
			txaJoinStepResults.clear();
			txaStepNamesResults.clear();
			txaETLJobs.clear();
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
	}

