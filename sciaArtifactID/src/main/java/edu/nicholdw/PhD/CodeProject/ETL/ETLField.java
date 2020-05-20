/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
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
	private GraphNodeAnnotation graphNodeAnnotation;

	public ETLField(String columnName, String streamName) {
		setColumnName(columnName);
		setStreamName(streamName);
		graphNodeAnnotation = new GraphNodeAnnotation();
	}
	/**
	 * Copy Constructor
	 * @param etlField ETLField object to be copied
	 */
	public ETLField(ETLField etlField) {
		this.setColumnName(etlField.getColumnName());
		this.setStreamName(etlField.getStreamName());
		this.setGraphNodeAnnotation(etlField.getGraphNodeAnnotation());
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
	
	/**
	 * Compare two ETLField objects
	 * @param etlField1
	 * @param etlField2
	 * @return True if they contain all the same properties, false otherwise
	 */
	public static Boolean compare(ETLField etlField1, ETLField etlField2) {
		if (etlField1.getColumnName().compareTo(etlField2.getColumnName()) == 0 &&
			etlField1.getStreamName().compareTo(etlField2.getStreamName()) == 0) {
			return true;
		} else {
			return false;
		}
	}
	public void setGraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation) {
		this.graphNodeAnnotation = new GraphNodeAnnotation(graphNodeAnnotation);
	}
	/***
	 * Get a copy of the GraphNodeAnnotation for the current object
	 * @return A copy of the GraphNodeAnnotation for the current object
	 */
	public GraphNodeAnnotation getGraphNodeAnnotation() {return new GraphNodeAnnotation(graphNodeAnnotation);}
}
