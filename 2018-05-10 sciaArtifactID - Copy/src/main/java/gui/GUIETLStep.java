package gui;

import javafx.beans.property.SimpleStringProperty;

/***
 * ETL Step Names Information for the TableView control in the ProcessETL GUI
 * @author nicomp
 *
 */
public class GUIETLStep {
	// These field names must match the cellValueFactory names in the fxml file!
//	<cellValueFactory>
//        <PropertyValueFactory property="stepName" />
//  </cellValueFactory>
	private final SimpleStringProperty stepName = new SimpleStringProperty("");
	private final SimpleStringProperty stepType = new SimpleStringProperty("");
	private final SimpleStringProperty sql = new SimpleStringProperty("");
	private final SimpleStringProperty table = new SimpleStringProperty("");
	private final SimpleStringProperty connection = new SimpleStringProperty("");
	
	public GUIETLStep() {
        this("", "", "", "", "");
	}

    public GUIETLStep(String stepName, String stepType, String sql, String table, String connection) {
    	setStepName(stepName);
    	setStepType(stepType);
    	setSQL(sql);
    	setTable(table);
    	setConnection(connection);
    }
	public void setConnection(String connection) {
		this.connection.set(connection);
	}
	public String getConnection() {
		return connection.get();
	}

	public String getStepName() {
		return stepName.get();
	}

	public String getStepType() {
		return stepType.get();
	}	
	
	public void setStepType(String stepType) {
		this.stepType.set(stepType);
	}
	
	public void setStepName(String stepName) {
		this.stepName.set(stepName);
	}

	public String getSql() {
		return sql.get();
	}
	public void setSQL(String sql) {
		this.sql.set(sql);
	}

	public String getTable() {
		return table.get();
	}
	
	public void setTable(String table) {
		this.table.set(table);
	}
	public String toString() {
		return stepName.getValue() + ":" + stepType.getValue() + ":" + table.getValue() + ":" + connection.getValue();
	}
}
