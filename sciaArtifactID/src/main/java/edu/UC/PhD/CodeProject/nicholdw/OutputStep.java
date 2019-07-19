/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.List;

public class OutputStep {
	private String transName;
	private String stepName;
	private String stepType;
	private String dbName;
	private String tableName;
	private List<String> attributes=new ArrayList<String>();
	private String xmlFilename;
	private String xmlFilePath;
	private String etlStage;
	
	/**
	 * Constructor
	 * @param transName
	 * @param stepName
	 * @param stepType
	 * @param dbName
	 * @param tableName
	 * @param attributes
	 * @param xmlFilePath
	 * @param xmlFilename
	 * @param etlStage
	 */
	public OutputStep(String transName, String stepName, String stepType, String dbName, String tableName, List<String> attributes, String xmlFilePath, String xmlFilename, String etlStage) {
		super();
		this.transName = transName;
		this.stepName = stepName;
		this.stepType = stepType;
		this.dbName = dbName;
		this.tableName = tableName;
		this.attributes = attributes;
		this.xmlFilename = xmlFilename;
		this.xmlFilePath = xmlFilePath;
		this.etlStage = etlStage;
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
	public String getStepType() {
		return stepType;
	}
	public void setStepType(String stepType) {
		this.stepType = stepType;
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
	@Override
	public String toString() {
		return "OutputStep [transName=" + transName + ", stepName=" + stepName
				+ ", stepType=" + stepType + ", dbName=" + dbName
				+ ", tableName=" + tableName + ", xmlFilename = " + xmlFilename + ", ETL Stage = " + etlStage +  ", attributes=" + attributes
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
	public String getEtlStage() {
		return etlStage;
	}
	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}
}
