package edu.nicholdw.PhD.CodeProject.ETL;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/***
 * A step in the ETL process. Abstracted, but modeled on Pentaho for now.
 * @author nicomp
 *
 */
public class ETLStep {
	private String stepType;
	private String stepName;
	private String sql;
	private String table;
	
	public ETLStep(ETLStep etlStep) {
		setStepName(etlStep.getStepName());
		setStepType(etlStep.getStepType());
		setSql(etlStep.getSql());
		setTable(etlStep.getTable());
	}
	public ETLStep(String stepName, String stepType, String sql, String table) {
		setStepName(stepName);
		setStepType(stepType);
		setSql(sql);
		setTable(table);
	}
	
	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
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
   	        data.add(new gui.GUIETLStep(etlStep.getStepName(), etlStep.getStepType(), etlStep.getSql(), etlStep.getTable()));
   		}
    }
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}	
}
