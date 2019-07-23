/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu 
 */
package edu.nicholdw.PhD.CodeProject.ETL;

public class DBProcStep {
	private String transName;
	private String stepName;
//	private String stepType;
	private String dbName;
	private String xmlFilename;
	private String xmlFilePath;
	private String procedure;
	private String etlStage;

	public DBProcStep(String transName, String stepName, String dbName, String procedure, String xmlFilePath, String xmlFilename, String etlStage) {
		super();
		this.transName = transName;
		this.stepName = stepName;
//		this.stepType = stepType;
		this.dbName = dbName;
		this.procedure = procedure;
		this.xmlFilename = xmlFilename;
		this.xmlFilePath = xmlFilePath;
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
/*	public String getStepType() {
		return stepType;
	}
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}*/
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	@Override
	public String toString() {
		return "DBProc [transName = " + transName 
				+ ", stepName = " + stepName
				+ ", dbName =" + dbName
				+ ", Procedure = " + procedure 
				+ ", xmlFilename = " + xmlFilename 
				+ ", etlStage = " + etlStage
				+ "]";
	}
	public String getXmlFilename() {
		return xmlFilename;
	}
	public void setXmlFilename(String xmlFilename) {
		this.xmlFilename = xmlFilename;
	}
	public String getXmlFilePath() {
		return xmlFilePath;
	}
	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public String getEtlStage() {
		return etlStage;
	}
	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}

}
