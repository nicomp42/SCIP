/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/***
 * A step in the ETL process. Abstracted, but modeled on Pentaho for now.
 * Called a Transformation in Pentaho
 * @author nicomp
 *
 */
public class ETLStep implements java.io.Serializable {
	public static final String type_TableInput = "TableInput";
//	private String key;
	private String stepType;
	private String stepName;
	private String sql;
	private String tableName;
	private QueryDefinition queryDefinition;
	private String connection;
	private ETLFields etlFields;
	private String procedure;		// Used in DBProc steps
	private int etlStageNumber;		// Index into ETLTransformationFile.enumETLStage
	private String fileName;		// No Path
	private String schemaName;		// Output steps have a schema name in the XML, other steps probably do too
	private GraphNodeAnnotation graphNodeAnnotation;

	/**
	 * Copy Constructor
	 * @param etlStep
	 */
	public ETLStep(ETLStep etlStep) {
		setStepName(etlStep.getStepName());
		setStepType(etlStep.getStepType());
		setSql(etlStep.getSql());
		setTableName(etlStep.getTableName());
		setConnection(etlStep.getConnection());
		setProcedure(etlStep.getProcedure());
		setETLStageNumber(etlStep.getETLStageNumber());
		etlFields = new ETLFields();
		setFileName(etlStep.getFileName());
		setSchemaName(etlStep.getSchemaName());
		graphNodeAnnotation = new GraphNodeAnnotation();
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
	public ETLStep(String stepName, String stepType, String sql, String table, String connection, String procedure, int etlStageNumber, String fileName, String schemaName) {
		setStepName(stepName);
		setStepType(stepType);
		setSql(sql);
		setTableName(table);
		setConnection(connection);
		setProcedure(procedure);
		etlFields = new ETLFields();
		setETLStageNumber(etlStageNumber);
		setFileName(fileName);
		setSchemaName(schemaName);
		graphNodeAnnotation = new GraphNodeAnnotation();
	}
	public String toString() {
		return getStepName() + ", " + getStepType() + ", " + getFileName() ;
	}
	public void addETLFields(ETLFields etlFields) {
		for (ETLField etlField: etlFields) {
			this.etlFields.addETLField(etlField);
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
   	        data.add(new gui.GUIETLStep(etlStep.getStepName(), etlStep.getStepType(), etlStep.getSql(), etlStep.getTableName(), etlStep.getConnection()));
   		}
    }
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql.trim();
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName.trim();
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
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getKey() {return getFileName() + "." + getStepName();}
//	public void setKey(String key) {this.key = key;}

	public void setGraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation) {
		this.graphNodeAnnotation = new GraphNodeAnnotation(graphNodeAnnotation);
	}
	/***
	 * Get a copy of the GraphNodeAnnotation for the current object
	 * @return A copy of the GraphNodeAnnotation for the current object
	 */
	public GraphNodeAnnotation getGraphNodeAnnotation() {return new GraphNodeAnnotation(graphNodeAnnotation);}
}
