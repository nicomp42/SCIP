package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.List;

public class DBLookupStep {

	private String transName;
	private String stepName;
	private String dbName;
	private String tableName;
	private List<String> attributes = new ArrayList<String>();
	private String etlStage;

	@Override
	public String toString() {
		return "DBLookupStep [transName=" + transName + ", stepName="
				+ stepName + ", dbName=" + dbName + ", tableName=" + tableName + ", ETL Stage=" + etlStage
				+ ", attributes=" + attributes + "]";
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public DBLookupStep(String transName, String stepName, String dbName, String tableName, List<String> attributes, String etlStage) {
		super();
		this.transName = transName;
		this.stepName = stepName;
		this.dbName = dbName;
		this.tableName = tableName;
		this.attributes = attributes;
		this.etlStage = etlStage;
	}

	public String getEtlStage() {
		return etlStage;
	}

	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}
}
