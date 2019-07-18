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
	private String dbProc;

	public DBProcStep(String transName, String stepName, String dbName, String dbProc, String xmlFilePath, String xmlFilename) {
		super();
		this.transName = transName;
		this.stepName = stepName;
//		this.stepType = stepType;
		this.dbName = dbName;
		this.dbProc = dbProc;
		this.xmlFilename = xmlFilename;
		this.xmlFilePath = xmlFilePath;
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
		return "DBProc [transName = " + transName + ", stepName = " + stepName
				+ ", dbName =" + dbName
				+ ", DBProc =" + dbProc + ", xmlFilename = " + xmlFilename 
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
	public String getDbProc() {
		return dbProc;
	}
	public void setDbProc(String dbProc) {
		this.dbProc = dbProc;
	}

}
