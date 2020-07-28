/*
 * Entry point for the Case Studies GUI
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;

import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudy;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyEnvironment;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyQuery;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyQuerys;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyRunner;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CaseStudyController {
	private Stage myStage;
	@FXML private AnchorPane apMainWindow;
	@FXML private ListView<String> lvTestCase, lvTestCaseSelected;
	@FXML private Button btnStart, btnCopyAll, btnClearSelectedTests;
	@FXML private TextArea txaProgress;
	@FXML private TextField txtStatus;
	@FXML private Tab tabCaseStudy;
	@FXML private ComboBox<String> cbCaseStudy;
	@FXML private MenuBar mbrMainMenu;
	@FXML private MenuItem mnuFileExit;
	@FXML private TextArea txaSQL;
	@FXML void btnStart_OnAction(ActionEvent event) {runTest();}
	@FXML void btnCopyAll_OnAction(ActionEvent event) {copyAll();}
	@FXML void btnClearSelectedTests_OnAction(ActionEvent event) {clearSelectedTests();}
	@FXML void cbCaseStudy_OnAction(ActionEvent event) {changeCaseStudy();}
//	@FXML void lvTestCase_OnAction(ActionEvent event) {displaySelectedSQL();}
	private CaseStudy caseStudy;
	private CaseStudyEnvironment caseStudyEnvironment;
	
	/**
	 * User selected a case study from the combo box
	 */
	private void changeCaseStudy() {
		String caseStudyTitle = cbCaseStudy.getValue();
		switch (caseStudyTitle) {
			case "Case Study 1":
				clearSelectedTests();
				caseStudy.setTitle(caseStudyTitle);
				caseStudy.getCaseStudyEnvironment().SetUpCaseStudy01();
				loadTestCasesIntoListBox();
			break;
			case "Case Study 2":
				clearSelectedTests();
				caseStudy.setTitle(caseStudyTitle);
				caseStudy.getCaseStudyEnvironment().SetUpCaseStudy02();
				loadTestCasesIntoListBox();
			break;
			case "Case Study 3":
				clearSelectedTests();
				caseStudy.setTitle(caseStudyTitle);
				caseStudy.getCaseStudyEnvironment().SetUpCaseStudy03();
				loadTestCasesIntoListBox();
			break;
		}
	}
	/**
	 * Remove all selected tests from the selected tests list box
	 */
	private void clearSelectedTests() {
		lvTestCaseSelected.getItems().clear();
	}
	
	/***
	 * Copy all the tests to the selected tests list box
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
		caseStudyRunner.run(caseStudy, txaProgress);
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
			Log.logError("CaseStudy.scatter(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
	/***
	 * Copy from the controls on the form to the SchemaChangeImpactProject object
	 * @param scip The reference to the SchemaChangeImpactProject object
	 */
	private void gather() {
		
	}
	/**
	 * Set up the GUI when it opens
	 */
	private void setTheScene() {
		caseStudy = new CaseStudy("Case Study");
		caseStudyEnvironment = new CaseStudyEnvironment();
		caseStudy.setCaseStudyEnvironment(caseStudyEnvironment);
		loadCaseStudyComboBox();
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
			        if (click.getClickCount() == 1) {
			        	String description, sql;
			        	description = lvTestCase.getSelectionModel().getSelectedItem();
						sql = caseStudyEnvironment.getCaseStudyQuerys().getSQL(description);
						txaSQL.setText(sql);
			        }
		    	} catch(Exception ex) {}
		    }
		});
	}
	private void loadCaseStudyComboBox() {
		cbCaseStudy.getItems().add("Case Study 1");
		cbCaseStudy.getItems().add("Case Study 2");
		cbCaseStudy.getItems().add("Case Study 3");
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
