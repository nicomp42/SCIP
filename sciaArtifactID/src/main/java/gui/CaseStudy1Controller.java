/*
 * Entry point for the Case Study 1 GUI
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;

import java.io.File;
import java.util.ArrayList;

import org.neo4j.graphdb.GraphDatabaseService;
import edu.UC.PhD.CodeProject.nicholdw.TableAttribute;
import edu.UC.PhD.CodeProject.nicholdw.TableAttributes;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.ForeignKey;
import edu.UC.PhD.CodeProject.nicholdw.ForeignKeys;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Tables;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyEnvironment;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyQuery;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyQuerys;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyRunner;
import edu.UC.PhD.CodeProject.nicholdw.importFromCSVIntoGraphDB.ImportFromCSVIntoGraphDB;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.DwhQueries;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.IdsDwh;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.Operational;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.OpsIds;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;
import edu.nicholdw.PhD.CodeProject.ETL.ETLToCSV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CaseStudy1Controller {
	private Stage myStage;
	@FXML private AnchorPane apMainWindow;
	@FXML private ListView<String> lvTestCase, lvTestCaseSelected;
	@FXML private Button btnStart, btnCopyAll, btnClearSelectedTests;
	@FXML private TextArea txaProgress;
	@FXML private TextField txtStatus;
	@FXML private Tab tabCaseStudy1;
	@FXML private MenuBar mbrMainMenu;
	@FXML private MenuItem mnuFileExit;
	@FXML void btnStart_OnAction(ActionEvent event) {runTest();}
	@FXML void btnCopyAll_OnAction(ActionEvent event) {copyAll();}
	@FXML void btnClearSelectedTests_OnAction(ActionEvent event) {clearSelectedTests();}
	private CaseStudyEnvironment caseStudyEnvironment;
	
	private void clearSelectedTests() {
		lvTestCaseSelected.getItems().clear();
	}
	
	/***
	 * Copy all the tests to the selected list box
	 */
	private void copyAll() {
		lvTestCaseSelected.getItems().clear();
		for (String description: lvTestCase.getItems()) {
			lvTestCaseSelected.getItems().add(description);
		}
	}
	
	
	/***
	 * Run the tests...
	 */
	private void runTest() {
		if (lvTestCaseSelected.getItems().size() == 0) {
			Alert alert = new Alert(AlertType.ERROR, "No tests selected.", ButtonType.OK);
			alert.showAndWait();			
		}
		txaProgress.clear();
		txtStatus.setText("Tests running...");
		// Set all tests to false
		for (CaseStudyQuery caseStudyQuery: caseStudyEnvironment.getCaseStudyQuerys()) {
			caseStudyQuery.setEnabled(false);
		}
		// Enable the tests selected by the user
		for (String description: lvTestCaseSelected.getItems()) {
			caseStudyEnvironment.getCaseStudyQuerys().setEnabled(description, true);
		}
		CaseStudyRunner caseStudyRunner = new CaseStudyRunner();
		caseStudyRunner.run(caseStudyEnvironment, txaProgress);
		txtStatus.setText("Tests complete");
	}
	public void loadTestCasesIntoListBox() {
		CaseStudyQuerys csqs = caseStudyEnvironment.getCaseStudyQuerys();
		lvTestCase.getItems().clear();
		for (CaseStudyQuery csq : csqs) {
			lvTestCase.getItems().add(csq.getDescription());			
		}
	}
	/***
	 */
	private void scatter() {
		try {
		} catch (Exception ex) {
			Log.logError("CaseStudy1.scatter(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
	/***
	 * Copy from the controls on the form to the SchemaChangeImpactProject object
	 * @param scip The reference to the SchemaChangeImpactProject object
	 */
	private void gather() {
	}
	private void setTheScene() {
		caseStudyEnvironment = new CaseStudyEnvironment();
		loadTestCasesIntoListBox();
		lvTestCase.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {
		    	try {
			        if (click.getClickCount() == 2) {
			        	String description;
			        	description = lvTestCase.getSelectionModel().getSelectedItem();
			        	if (!lvTestCaseSelected.getItems().contains(description)) {
			        		lvTestCaseSelected.getItems().add(description);
			        	}
			        }
		    	} catch(Exception ex) {}
		    }
		});
	}
		
	@FXML void mnuFileExit_OnAction(ActionEvent event) {
		myStage.close();		
	}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {
		this.myStage = myStage;
		setTheScene();
	}
}
