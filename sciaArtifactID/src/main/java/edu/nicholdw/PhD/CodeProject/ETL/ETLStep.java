/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/***
 * A step in the ETL process. Abstracted, but modeled on Pentaho for now.
 * @author nicomp
 *
 */
public class ETLStep {
	public static final String type_TableInput = "TableInput";
	
	private String stepType;
	private String stepName;
	private String sql;
	private String table;
	private QueryDefinition queryDefinition;
	private String connection;
	private ETLFields etlFields;
	private String procedure;			// Used in DBProc steps
	private int etlStageNumber;		// Index into ETLTransformationFile.enumETLStage
	private String fileName;		// No Path
	
	/**
	 * Copy Constructor
	 * @param etlStep
	 */
	public ETLStep(ETLStep etlStep) {
		setStepName(etlStep.getStepName());
		setStepType(etlStep.getStepType());
		setSql(etlStep.getSql());
		setTable(etlStep.getTable());
		setConnection(etlStep.getConnection());
		setProcedure(etlStep.getProcedure());
		setETLStageNumber(etlStep.getETLStageNumber());
		etlFields = new ETLFields();
		setFileName(etlStep.getFileName());
	}
/*	public ETLStep(String stepName, String stepType, String sql, String table, String connection) {
		setStepName(stepName);
		setStepType(stepType);
		setSql(sql);
		setTable(table);
		setConnection(connection);
		etlFields = new ETLFields();
		setProcedure("");
	} */
	public ETLStep(String stepName, String stepType, String sql, String table, String connection, String procedure, int etlStageNumber, String fileName) {
		setStepName(stepName);
		setStepType(stepType);
		setSql(sql);
		setTable(table);
		setConnection(connection);
		setProcedure(procedure);
		etlFields = new ETLFields();
		setETLStageNumber(etlStageNumber);
		setFileName(fileName);
	}

	public void addETLFields(ETLFields etlFields) {
		for (ETLField etlField: etlFields) {
			this.etlFields.add(etlField);
		}
	}
	public ETLFields getETLFields() {return etlFields;}

	public String getConnection() {
		return connection;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType.trim();
	}

	public void setConnection(String connection) {
		this.connection = connection.trim();
	}
	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName.trim();
	}
	/***
	 * Load a TableView control with ETL Steps
	 * @param tableView The TableView to be loaded
	 * @param etlSteps The set of ETL steps
	 */
	public static void loadTableViewWithETLSteps(TableView<gui.GUIETLStep> tableView, ETLSteps etlSteps) {
        ObservableList<gui.GUIETLStep> data = tableView.getItems();
        data.clear();
        for (ETLStep etlStep : etlSteps) {
   	        data.add(new gui.GUIETLStep(etlStep.getStepName(), etlStep.getStepType(), etlStep.getSql(), etlStep.getTable(), etlStep.getConnection()));
   		}
    }
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql.trim();
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table.trim();
	}
	public void setQueryDefinition(QueryDefinition queryDefinition) {
		this.queryDefinition = queryDefinition;	
	}
	public QueryDefinition getQueryDefinition() {
		return queryDefinition;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure.trim();
	}
	public int getETLStageNumber() {
		return etlStageNumber;
	}
	public void setETLStageNumber(int etlStageNumber) {
		this.etlStageNumber = etlStageNumber;
	}
	public String getEtlStage() {return ETLTransformationFile.lookupETLStage(etlStageNumber);}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName.trim();
	}
}
