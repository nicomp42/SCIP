/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
// ":style reset" to reset the colors in an existing graph

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

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.ExecuteSQLScriptStep;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStep;
import edu.UC.PhD.CodeProject.nicholdw.StepName;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.nicholdw.PhD.CodeProject.ETL.DBProcStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnection;
import edu.nicholdw.PhD.CodeProject.ETL.ETLConnections;
import edu.nicholdw.PhD.CodeProject.ETL.ETLHops;
import edu.nicholdw.PhD.CodeProject.ETL.ETLKTRFile;
import edu.nicholdw.PhD.CodeProject.ETL.ETLStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLSteps;
import edu.nicholdw.PhD.CodeProject.ETL.ETLTransformationFile;
import edu.nicholdw.PhD.CodeProject.ETL.XMLParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class ProcessETLController {
	private SchemaChangeImpactProject scip;
	@FXML	private TableView<GUIETLStep> tvETLSteps;
	@FXML	private TableView<GUIETLConnection> tvETLConnections;
	@FXML	private TableView<GUIetlTransformationFile> tvETLTransformationFiles;
	@FXML	private AnchorPane apProcessETL;
	@FXML	private TextArea txaAffectOfActionQuery, txaActionQueryToApply, txaETLFilePath, txaOutputStepResults, txaInputStepResults, txaJoinStepResults, txaStepNamesResults, txaETLJobs, txaDBProcStepResults;
	@FXML	private Button btnApplyActionQuery, btnETLSubmit, btnETLBrowse, btnCreateGraph, btnProcessETLTransformationFiles, btnClearTable;
	@FXML 	private Label lblContentsOfETL;
	@FXML	private Label lblWorking;
	@FXML	private CheckBox cbClearDB, cbOpenInBrowser;
	@FXML	private Pane pneActionQueryToApply, pneETLResults, pneETLLoad, pneCreateGraph, pneFiles;
	@FXML 	private void btnETLSubmit_OnClick(ActionEvent event) {loadETLTransformationFiles();}
	@FXML 	private void btnETLBrowse_OnClick(ActionEvent event) {browseETL();}
	@FXML	private void btnCreateGraph_OnClick(ActionEvent event) {createGraph();}
	@FXML	
	private void btnProcessETLTransformationFiles_OnClick(ActionEvent event) {
		scip.getETLKTRFile().setTransformationFileDirectory(Utils.formatPath(txaETLFilePath.getText().trim())); 
		processETLTransformationFiles();
	}
//	@FXML	private void txaETLFilePath_DoubleClick(ActionEvent event) {txaETLFilePath.setText("C:\\Users\\nicomp\\SCIP Projects\\Test Case 01\\Pentaho");}
	@FXML	private void btnClearTransformationTable_OnClick(ActionEvent event) {clearTransformationFileTable();}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("ProcessETLController.initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("ProcessETLController.initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("ProcessETLController.initialize() complete");
	}
	@FXML private void btnApplyActionQuery_OnClick(ActionEvent event) {
		String actionQueryText = txaActionQueryToApply.getText();
		if (actionQueryText == null || actionQueryText.trim().length() == 0) {
			(new Alert(Alert.AlertType.ERROR, "Enter an action query to apply.", ButtonType.OK)).showAndWait();
		} else {
			Log.logProgress("ProcessETLController.btnApplyActionQuery_OnClick()");
			actionQueryText = actionQueryText.trim();
			try {
				// Let's find out what this query is going to do
				// Try to put the action query into the scip object
				QueryDefinition qd = scip.addActionQuery(actionQueryText);
				if (qd != null) {
					txaAffectOfActionQuery.appendText("SQL: " + qd.getSql() + System.getProperty("line.separator"));
					txaAffectOfActionQuery.appendText("Query Type: " + qd.getQueryType().toString() + System.getProperty("line.separator"));
					txaAffectOfActionQuery.appendText("Tables:" + System.getProperty("line.separator"));
					for (QueryTable qt: qd.getQueryTables()) {
						txaAffectOfActionQuery.appendText(qt.toString() + System.getProperty("line.separator"));
					}
					txaAffectOfActionQuery.appendText("Attributes:" +  System.getProperty("line.separator"));
					for (QueryAttribute qa : qd.getQueryAttributes()) {
						txaAffectOfActionQuery.appendText(qa.toString() + System.getProperty("line.separator"));
					}
				}
			} catch (Exception ex) {
				Log.logError("ProcessETLController.btnApplyActionQuery_OnClick(): ", ex);
			}
		}
	}
	private DataBrowseController dataBrowseController;

	private void clearTransformationFileTable() {
		scip.getETLKTRFile().getEtlTransformationFiles().deleteAll();
		tvETLTransformationFiles.getItems().clear();
		displayETLProcessControls(false);
	}
	/***
	 * Create a GraphDB from the currently loaded ETL steps
	 */
	private void createGraph() {
		Log.logProgress("ProcessETLController.createGraph()");
		try {
			Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(),  Config.getConfig().getNeo4jDBDefaultPassword());
			if (cbClearDB.isSelected()) {Neo4jDB.clearDB();}
			ETLKTRFile.createGraph(scip);
			if (cbOpenInBrowser.isSelected() ) {
				Browser browser = Browser.prepareNewBrowser();
				browser.initAndLoad(null);
			}
		} catch (Exception ex) {
			Log.logError("ProcessETLController.createGraph(): " + ex.getLocalizedMessage());
		}
	}
	private void gather(SchemaChangeImpactProject scip) {
		
	}
	private void scatter(SchemaChangeImpactProject scip) {
		loadTableViewWithETLSteps(scip.getETLKTRFile().getETLSteps()); 
	}
	private void setTheScene() {
		scip = Config.getConfig().getCurrentSchemaChangeImpactProject();
		scatter(scip);
//		We are setting the title of this form in the Main. It works and I can't figure out how to get the stage object here.
		addDoubleClickHandler();
		dataBrowseController = null;
		displayLoadETLResults(false);
		btnETLSubmit.setVisible(false);
		displayETLProcessControls(false);
		pneCreateGraph.setVisible(false);
		pneActionQueryToApply.setVisible(false);
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
		            if (dataBrowseController == null) {dataBrowseController = openDataBrowse();} 
	            	dataBrowseController.appendToTextArea(guiETLStep.toString());
	            	ETLStep etlStep = scip.getETLKTRFile().getETLSteps().getETLStep(guiETLStep.getStepName());
	            	scip.getETLKTRFile().processTableInputStepQuery(etlStep);
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
		cbOpenInBrowser.setVisible(visible);
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
				txaETLFilePath.setText("C:\\Users\\nicomp\\SCIP Projects\\Test Case 02\\Pentaho\\");
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
					scip.getETLKTRFile().getEtlTransformationFiles().updateETLStageInETLTransformationFile(g.getFileName(), g.getEtlStage());
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
		ETLTransformationFile.loadTableViewWithTransformationFileNames(tableView, scip.getETLKTRFile());
    }
	/**
	 * Process the directory that contains some Pentaho transformation files. It should be all .ktr (transformation) files 
	 * @param xmlFilePath Physical path to the directory. We'll take it from there.
	 */
	private void loadETLTransformationFiles() {
		Log.logProgress("processETLTransformationFiles.loadETLTransformationFiles() " + Utils.formatPath(txaETLFilePath.getText().trim()));
		scip.getETLKTRFile().setTransformationFileDirectory(Utils.formatPath(txaETLFilePath.getText().trim()));
		ArrayList<String> files = readDirectory(scip.getETLKTRFile().getTransformationFileDirectory());
		for (String file : files) {
			scip.getETLKTRFile().getEtlTransformationFiles().add(new ETLTransformationFile(file));	// ETL Stage will default to unknown
		}
		loadTableViewWithTransformationFileNames(tvETLTransformationFiles);
		displayETLProcessControls(true);
	}
	private void displayETLProcessControls(Boolean visible) {
		btnProcessETLTransformationFiles.setVisible(visible);
		btnCreateGraph.setVisible(visible);
		cbClearDB.setVisible(visible);
		cbOpenInBrowser.setVisible(visible);
		pneCreateGraph.setVisible(true);
	}
	/**
	 * Take apart all the select transformation files
	 */
	private void processETLTransformationFiles() {
		Log.logProgress("ProcessETLController.processETLTransformationFiles() " + scip.getETLKTRFile().getTransformationFileDirectory());
		if (countNumberOfETLTransformationFilesWithAStage() > 0) {
			displayLoadETLResults(false);
			disableETLLoadSelectionControls(true);
			lblWorking.setVisible(true);
			clearResultsControls();
			ArrayList<TableOutputStep> tableOutputSteps = new ArrayList<TableOutputStep>();
			ArrayList<TableInputStep> tableInputSteps = new ArrayList<TableInputStep>();
			ArrayList<DBJoinStep> dbJoinSteps = new ArrayList<DBJoinStep>();
			ArrayList<StepName> stepNames = new ArrayList<StepName>();
			ArrayList<String> connectionNames = new ArrayList<String>();
			ArrayList<DBProcStep> dbProcSteps = new ArrayList<DBProcStep>();
			ArrayList<ExecuteSQLScriptStep> executeSQLScriptSteps = new ArrayList<ExecuteSQLScriptStep>();
			ETLHops etlHops = new ETLHops();
			// See https://stackoverflow.com/questions/19968012/javafx-update-ui-label-asynchronously-with-messages-while-application-different/19969793#19969793
		    Task <Void> task = new Task<Void>() {
		        @Override public Void call() throws InterruptedException {
		        	// Do not write to any controls in here. An exception will be thrown. It's ugly.
		    		try {
		    			try {
		    				// Step through the selected files in the TableView control
							for (ETLTransformationFile etlTransformationFile: scip.getETLKTRFile().getEtlTransformationFiles()) {
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
					Log.logProgress("ProcessETLController.processETLFile() parsing file at " + scip.getETLKTRFile().getTransformationFileDirectory() + etlTransformationFile.toString()); 
					XMLParser myXMLParser = new XMLParser();
					myXMLParser.setxmlDirectory(scip.getETLKTRFile().getTransformationFileDirectory());
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
						doc = builder.parse(scip.getETLKTRFile().getTransformationFileDirectory() + stepName.getFileName());
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
						scip.getETLKTRFile().getETLSteps().addETLStep(new ETLStep(stepName.getStepName(), stepType, sql, table, connectionName, procedure, stepName.getEtlStageNumber(), stepName.getFileName(), "SchemaUnknown"));
						//for (String connectionName: connectionNames) {
						scip.getETLKTRFile()
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
					loadTableViewWithETLSteps(scip.getETLKTRFile().getETLSteps());
					displayLoadETLResults(true);
					disableETLLoadSelectionControls(false);
					loadTableViewWithETLConnections(scip.getETLKTRFile().getETLConnections());
					// OK, the ETLProcess object is loaded up with ETL Steps and Connections and stuff
					// We should be able to parse the queries in the steps that have queries
					scip.getETLKTRFile().processTableInputStepQueries();
					scip.getETLKTRFile().processTableOutputStepsFields();
					scip.getETLKTRFile().setEtlHops(etlHops);
					scip.getETLKTRFile().processExecuteSQLStepQueries();
					pneActionQueryToApply.setVisible(true);
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
		for (ETLStep etlStep : scip.getETLKTRFile().getETLSteps()) {
			String schemaName, connection;
			connection = etlStep.getConnection();
			schemaName = scip.getETLKTRFile().getETLConnections().getConnection(connection).getDatabase();
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
				for (ETLTransformationFile etlTransformationFile: scip.getETLKTRFile().getEtlTransformationFiles()) {
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
	}

