/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
// ":style reset" to reset the colors in an existing graph

package gui;
import java.io.File;
import java.io.FileFilter;
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

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.OutputStep;
import edu.UC.PhD.CodeProject.nicholdw.StepName;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.nicholdw.PhD.CodeProject.ETL.DBProcStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnection;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnections;
import edu.nicholdw.PhD.CodeProject.ETL.ETLJob;
import edu.nicholdw.PhD.CodeProject.ETL.ETLJobs;
import edu.nicholdw.PhD.CodeProject.ETL.ETLProcess;
import edu.nicholdw.PhD.CodeProject.ETL.ETLStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLSteps;
import edu.nicholdw.PhD.CodeProject.ETL.ETLTransformationFile;
import edu.nicholdw.PhD.CodeProject.ETL.ETLTransformationFiles;
import edu.nicholdw.PhD.CodeProject.ETL.XMLParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
	@FXML	private TableView<GUIETLStep> tvETLSteps;
	@FXML	private TableView<GUIETLConnection> tvETLConnections;
	@FXML	private TableView<GUIetlTransformationFile> tvETLTransformationFiles;
	@FXML	private AnchorPane apMainWindow;
	@FXML	private TextArea txaETLFilePath, txaOutputStepResults, txaInputStepResults, txaJoinStepResults, txaStepNamesResults, txaETLJobs, txaDBProcStepResults;
	@FXML	private Button btnETLSubmit, btnETLBrowse, btnCreateGraph, btnProcessETLTransformationFiles;
	@FXML 	private Label lblContentsOfETL;
	@FXML	private Label lblWorking;
	@FXML	private CheckBox cbClearDB;
	@FXML	private Pane pneETLResults, pneETLLoad, pneCreateGraph, pneFiles;
	@FXML 	private void btnETLSubmit_OnClick(ActionEvent event) {loadETLTransformationFiles();}
	@FXML 	private void btnETLBrowse_OnClick(ActionEvent event) {browseETL();}
	@FXML	private void btnCreateGraph_OnClick(ActionEvent event) {createGraph();}
	@FXML	private void btnProcessETLTransformationFiles_OnClick(ActionEvent event) {processETLTransformationFiles(Utils.formatPath(txaETLFilePath.getText().trim()));}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ProcessETLController.Initialize() starting...");
		try {
			setTheScene();
			etlProcess = new ETLProcess();
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
		btnCreateGraph.setVisible(visible);
		cbClearDB.setVisible(visible);
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
				txaETLFilePath.setText("C:\\Temp\\ThrowawayProject\\ThrowawayProject\\ETLJob\\");
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
					etlProcess.getEtlTransformationFiles().updateETLStageInETLTransformationFile(g.getFileName(), g.getEtlStage());
				}
			}
		} catch (Exception ex) {}
	}

	/***
	 * Load a TableView control with Transformation file names
	 * @param tableView The TableView to be loaded
	 * @param etlSteps The set of ETL steps
	 */
	public void loadTableViewWithTransformationFileNames(TableView<gui.GUIetlTransformationFile> tableView) {
		ETLTransformationFile.loadTableViewWithTransformationFileNames(tableView, etlProcess);
    }
	/**
	 * Process the directory that contains some Pentaho transformation files. It should be all .ktr (transformation) files 
	 * @param xmlFilePath Physical path to the directory. We'll take it from there.
	 */
	private void loadETLTransformationFiles() {
		Log.logProgress("processETLTransformationFiles.loadETLTransformationFiles() " + etlProcess.getTransformationFileDirectory());
		ArrayList<String> files = readDirectory(etlProcess.getTransformationFileDirectory());
		for (String file : files) {
			etlProcess.getEtlTransformationFiles().add(new ETLTransformationFile(file));	// ETL Stage will default to unknown
		}
		loadTableViewWithTransformationFileNames(tvETLTransformationFiles);
		displayETLProcessControls(true);
	}
	private void displayETLProcessControls(Boolean visible) {
		btnProcessETLTransformationFiles.setVisible(visible);
		btnCreateGraph.setVisible(visible);
		cbClearDB.setVisible(visible);
	}
	/**
	 * Take apart all the select transformation files
	 * @param xmlFilePath The directory where all the files are. The files are in the Table View control tvETLTransformationFiles
	 */
	private void processETLTransformationFiles(String xmlFilePath) {
		Log.logProgress("ProcessETLController.processETLTransformationFiles() " + xmlFilePath);
		displayLoadETLResults(false);
		disableETLLoadSelectionControls(true);
		lblWorking.setVisible(true);
		clearResultsControls();
		ArrayList<OutputStep> outputSteps = new ArrayList<OutputStep>();
		
		ArrayList<TableInputStep> tableInputSteps = new ArrayList<TableInputStep>();
		ArrayList<DBJoinStep> dbJoinSteps = new ArrayList<DBJoinStep>();
		ArrayList<StepName> stepNames = new ArrayList<StepName>();
		ArrayList<String> connectionNames = new ArrayList<String>();
		ArrayList<DBProcStep> dbProcSteps = new ArrayList<DBProcStep>();
//		ETLJobs etlJobs = new ETLJobs();
		// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
	    Task <Void> task = new Task<Void>() {
	        @Override public Void call() throws InterruptedException {
	        	// Do not write to any controls in here. An exception will be thrown. It's ugly.
	    		try {
	    			try {
//	    				XMLParser myXMLParser = new XMLParser();
//	    				myXMLParser.setXMLFilePathPrefix(etlProcess.getTransformationFileDirectory());
	    				// Step through the selected files in the TableView control
	    				while (true) {
	    					try {
	    						for (ETLTransformationFile etlTransformationFile: etlProcess.getEtlTransformationFiles()) {
	    							if (ETLTransformationFile.isStageUnknown(etlTransformationFile.getEtlStage()) == false) {
	    								Log.logProgress("ProcessETLController.processETLTransformationFiles().task: Processing " + etlTransformationFile.getFileName());
	    								processETLFile(xmlFilePath, etlTransformationFile);
	    							}
	    						}
	    					} catch (Exception ex) {break;}
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
	        public void processETLFile(String xmlDirectory, ETLTransformationFile etlTransformationFile) {
				Log.logProgress("ProcessETLController.processETLFile() parsing file at " + xmlDirectory + etlTransformationFile.getFileName() 
				                + ", stage = " + etlTransformationFile.lookupETLStage()); 
				XMLParser myXMLParser = new XMLParser();
				myXMLParser.setxmlDirectory(xmlDirectory);
				myXMLParser.getStepNames(etlTransformationFile, stepNames);
				myXMLParser.getConnectionNames(etlTransformationFile, connectionNames);
				myXMLParser.parseXMLForOutputSteps(etlTransformationFile, outputSteps);
				myXMLParser.parseXMLForInputSteps(etlTransformationFile, tableInputSteps);
				myXMLParser.parseXMLForDBJoinSteps(etlTransformationFile, dbJoinSteps);
				myXMLParser.parseXMLForDBProcSteps(etlTransformationFile, dbProcSteps);
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
					doc = builder.parse(xmlFilePath + stepName.getFileName());
					XPathFactory xpathFactory = XPathFactory.newInstance();
					xpath = xpathFactory.newXPath();
					// Get # lines that were already in the TextArea and use are the line number for each new line we add.
/*					int counter = 0;
					try {
						counter = txaStepNamesResults.getText().split("\n").length - 1 ;
					} catch (Exception ex) {
						Log.logError("ProcessETLController.processETLTransformationFiles().task.setOnSucceeded: " + ex.getLocalizedMessage());
					}
					counter++; */
					String tmp, stepType, sql, table, connection;
					tmp = "";
//					tmp = String.valueOf(counter) + ": ";
//					tmp += stepName.toString();
					// Not all the types of steps will have all these artifacts.
					stepType = myXMLParser.getStepTypeAsString(xpath, doc, stepName.getStepName());
					sql = myXMLParser.getSQL(xpath, doc, stepName.getStepName());
					table = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "table");
					connection = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "connection");
					String procedure;
					procedure = myXMLParser.getSomethingInAStep(xpath, doc, stepName.getStepName(), "procedure");
					tmp += " (" + stepType +  ")";
					txaStepNamesResults.appendText(tmp + System.getProperty("line.separator"));
					// Add this new step to the collection of steps
					etlProcess.getETLSteps().addETLStep(new ETLStep(stepName.getStepName(), stepType, sql, table, connection, procedure));
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
				for (OutputStep outputStep: outputSteps) {
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
	}

