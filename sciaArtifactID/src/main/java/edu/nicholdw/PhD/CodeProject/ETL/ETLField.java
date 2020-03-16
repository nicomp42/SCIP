/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.Utils;

/**
 * A field in an ETL TableOutput Step
 * @author nicomp
 *
 */
public class ETLField implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8544741338416810657L;
	private String streamName;		// The name of the field in the ETL that will be written the DBMS table. 
	private String columnName;		// The destination of the stream field. Called "Table Field" in the Pentaho/Spoon UI. Called "column_name" in the XML file 
	
	public ETLField(String columnName, String streamName) {
		setColumnName(columnName);
		setStreamName(streamName);
	}
	public ETLField(ETLField etlField) {
		this.setColumnName(etlField.getColumnName());
		this.setStreamName(etlField.getStreamName());
	}
	/**
	 * 
	 * @return The destination into the DBMS of the stream field 
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * 
	 * @param columnName The destination into the DBMS of the stream field
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * 
	 * @return The name of the field created in the ETL job
	 */
	public String getStreamName() {
		return streamName;
	}
	/**
	 * 
	 * @param streamName The name of the field created in the ETL job
	 */
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	/**
	 * @return A string representation of the object
	 */
	public String toString() {return getColumnName() + ":" + getStreamName();}
	
}
