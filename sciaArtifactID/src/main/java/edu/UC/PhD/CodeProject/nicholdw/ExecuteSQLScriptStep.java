package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.List;

public class ExecuteSQLScriptStep {
	private String transName;
	private String stepName;
	private String dbName;
	private String tableName;
	private List<String> attributes = new ArrayList<String>();
	private String stepType;
	private boolean isUpdateStep;
	private String etlStage;
	private String SQL;
	private String xmlFilename;

	@Override
	public String toString() {
		return "DimLookupUpdateStep [transName=" + transName + ", stepName="
				+ stepName + ", dbName=" + dbName + ", tableName=" + tableName
				+ ", attributes=" + attributes + ", SQL = " + SQL + "]";
	}

	public boolean isUpdateStep() {
		return isUpdateStep;
	}

	public void setUpdateStep(boolean isUpdateStep) {
		this.isUpdateStep = isUpdateStep;
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
	public ExecuteSQLScriptStep(String transName, 
			                    String stepName, 
			                    String dbName, 
			                    String SQL,
			                    String xmlFilename) {
		super();
		this.transName = transName;
		this.stepName = stepName;
		this.dbName = dbName;
		this.SQL = SQL;
		this.setXmlFilename(xmlFilename);
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public String getEtlStage() {
		return etlStage;
	}

	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}

	public String getXmlFilename() {
		return xmlFilename;
	}

	public void setXmlFilename(String xmlFilename) {
		this.xmlFilename = xmlFilename;
	}
	public String getSQL() {return SQL;}
}
