/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;
/**
 * A step in the ETL process. A step is reading from an Operational schema table. There will be SQL in here that needs to be parsed.
 * @author nicomp
 *
 */
public class TableInputStep {

	private String transName;
	private String stepName;
	private String dbName;
	private String sql;
	private String xmlFilePath;
	private String fileName;
	private String etlStage;

	@Override
	public String toString() {
		return "TableInputStep [transName=" + transName + ", stepName="	+ stepName + ", dbName=" + dbName + ", sql=" + sql + "]";
	}

	public TableInputStep(String transName, String stepName, String dbName,	String sql, String xmlFilepath, String fileName, String etlStage) {
		super();
		this.transName = transName;
		this.stepName = stepName;
		this.dbName = dbName;
		this.sql = sql;
		this.setXmlFilePath(xmlFilepath);
		this.setFileName(fileName);
		this.setEtlStage(etlStage);
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
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEtlStage() {
		return etlStage;
	}

	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}

}
