/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.Attributable;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
import edu.UC.PhD.CodeProject.nicholdw.ImpactGraphNode;

/**
 * A field in an ETL TableOutput Step or some other kind of step
 * @author nicomp
 *
 */
public class ETLField extends ImpactGraphNode implements java.io.Serializable, Attributable {
	/*
	 * 					                    --------- ETLFieldType from ----------	
	 * Step Type		SQL SELECT			Stream		Destination Table
	 * ----------------------------------------------------------------------------------------
	 * Table Input			Yes				No			No
	 * Insert/Update		No				Yes			Yes
	 * Database Lookup
	 * 
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = 8544741338416810657L;
	// In the Input/Output Step XML:
	// 		TableField is in the target table of this step, called "Name" in the XML
	// 		StreamField is what's supplied from the previous hop. Called "rename"
	
	public enum ETLFIELDTYPE {Unknown, FromSQLSelect, StreamAndDestinationTable};
	
	// The name of the field in the ETL that comes from the previous ETL Step.
	private String streamName; 

	// The destination of the stream field, if applicable. 
	// Must be a column name in the DBMS table.
	// Called "Table Field" in the Pentaho/Spoon UI. Called "column_name" or "name" in the XML file
	private String columnName;		
	private ETLFIELDTYPE etlFieldType;
	
	private GraphNodeAnnotation graphNodeAnnotation;
	private String tableName, schemaName;
	private boolean affectedByActionQuery;
	private boolean indirectlyAffectedByActionQuery;
	private String key;
	
	public Boolean getIndirectlyAffectedByActionQuery() {return indirectlyAffectedByActionQuery;}
	public void setIndirectlyAffectedByActionQuery(Boolean indirectlyAffectedByActionQuery) {
		this.indirectlyAffectedByActionQuery = indirectlyAffectedByActionQuery;
	}

	public ETLField(ETLFIELDTYPE etlFieldType, String schemaName, String tableName, String columnName, String streamName) {
		setColumnName(columnName);
		setStreamName(streamName);
		setSchemaName(schemaName);
		setContainerName(tableName);
		graphNodeAnnotation = new GraphNodeAnnotation();
		setEtlFieldType(etlFieldType);
	}
	/**
	 * Copy Constructor
	 * @param etlField ETLField object to be copied
	 */
	public ETLField(ETLField etlField) {
		this.setColumnName(etlField.getColumnName());
		this.setStreamName(etlField.getStreamName());
		setContainerName(etlField.getContainerName());
		setSchemaName(etlField.getSchemaName());
		this.setGraphNodeAnnotation(etlField.getGraphNodeAnnotation());
		setEtlFieldType(etlField.getEtlFieldType());
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
	 * @return The name of the field created in the the previous ETL step
	 */
	public String getStreamName() {
		return streamName;
	}
	/**
	 * 
	 * @param streamName  The name of the field created in the the previous ETL step
	 */
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	/**
	 * @return A string representation of the object
	 */
	public String toString() {return etlFieldType.toString() + ": " + getColumnName() + ":" + getStreamName();}
	
	/**
	 * Compare two ETLField objects column and stream names
	 * @param etlField1
	 * @param etlField2
	 * @return True if they contain the same column and stream names, false otherwise
	 */
	public static Boolean compareByColumnStream(ETLField etlField1, ETLField etlField2) {
		if (etlField1.getColumnName().compareTo(etlField2.getColumnName()) == 0 &&
			etlField1.getStreamName().compareTo(etlField2.getStreamName()) == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Compare two ETLField objects stream names
	 * @param etlField1
	 * @param etlField2
	 * @return True if they contain the same stream names, false otherwise
	 */
	public static Boolean compareByStream(ETLField etlField1, ETLField etlField2) {
		if (etlField1.getStreamName().compareTo(etlField2.getStreamName()) == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Compare two ETLField objects column names
	 * @param etlField1
	 * @param etlField2
	 * @return True if they contain the same column name, false otherwise
	 */
	public static Boolean compareByColumn(ETLField etlField1, ETLField etlField2) {
		if (etlField1.getColumnName().compareTo(etlField2.getColumnName()) == 0) {
			return true;
		} else {
			return false;
		}
	}
	public Boolean compareBySchemaTableAttribute(String schemaName, String tableName, String attributeName) {
		Boolean status = false;
		// Does this ETLField object even have an attribute?
		if (this.getEtlFieldType() == ETLFIELDTYPE.FromSQLSelect) {
			if (Config.getConfig().compareSchemaNames(this.schemaName, schemaName) &&
				Config.getConfig().compareTableNames(this.tableName, tableName)	   &&
				Config.getConfig().compareAttributeNames(this.getAttributeName(), attributeName)) {
				status = true;
			}
		}
		return status;
	}
	public void setGraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation) {
		this.graphNodeAnnotation = new GraphNodeAnnotation(graphNodeAnnotation);
	}
	/***
	 * Get a copy of the GraphNodeAnnotation for the current object
	 * @return A copy of the GraphNodeAnnotation for the current object
	 */
	public GraphNodeAnnotation getGraphNodeAnnotation() {return new GraphNodeAnnotation(graphNodeAnnotation);}
	@Override
	public String getContainerName() {
		return tableName;
	}
	@Override
	public String getSchemaName() {
		return schemaName;
	}
	@Override
	public String getAttributeName() {
		return columnName;
	}
	@Override
	public void setContainerName(String containerName) {
		this.tableName = containerName;
	}
	@Override
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;		
	}
	@Override
	public void setAttributeName(String attributeName) {
		this.columnName = attributeName;
	}
	@Override
	public Boolean getAffectedByActionQuery() {
		return affectedByActionQuery;
	}
	@Override
	public void setAffectedByActionQuery(Boolean affectedByActionQuery) {
		this.affectedByActionQuery = affectedByActionQuery;
		
	}
	@Override
	public String getKey() {
		if (key == null || key.trim().length() == 0) {
			return schemaName + "." + tableName + "." + columnName;
		} else {
			return key;
		}
	}
	@Override
	public void setKey(String key) {
		this.key = key;
	}
	public ETLFIELDTYPE getEtlFieldType() {
		return etlFieldType;
	}
	public void setEtlFieldType(ETLFIELDTYPE etlFieldType) {
		this.etlFieldType = etlFieldType;
	}
}
