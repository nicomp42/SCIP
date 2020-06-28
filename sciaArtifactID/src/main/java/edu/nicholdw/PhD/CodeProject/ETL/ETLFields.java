/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/**
 * A collection of fields that belong to an ETL TableOutput step
 * @author nicomp
 *
 */
public class ETLFields implements Iterable<ETLField>, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7560565847627375788L;
	private ArrayList<ETLField> etlFields;

	public ETLFields() {
		etlFields = new ArrayList<ETLField>();
	}

	/**
	 * Add an ETLField object to the collection if it's not already there.
	 * @param etlFieldToAdd ETLField object to be added. 
	 */
	public void addETLField(ETLField etlFieldToAdd) {
		if (!contains(etlFieldToAdd)) {
			etlFields.add(new ETLField(etlFieldToAdd));
		}
	}
	public Boolean containsBySchemaTableAttribute(String schemaName, String tableName, String attributeName) {
		Boolean foundIt = false;
		for (ETLField etlField : etlFields) {
			if (etlField.compareBySchemaTableAttribute(schemaName, tableName, attributeName) == true) {foundIt = true; break;}
		}
		return foundIt;
		
	}
	public Boolean contains(ETLField etlFieldToLookFor) {
		Boolean foundIt = false;
		for (ETLField etlField : etlFields) {
			if (ETLField.compare(etlFieldToLookFor, etlField) == true) {foundIt = true; break;}
		}
		return foundIt;
	}
	/**
	 * Search for an ETL Field by stream name only (Case-insensitive)
	 * @param etlColumnName The column name to search for
	 * @return a reference to the ETLField object if found, null otherwise
	 */
	public ETLField findETLFieldByStreamName(String etlStreamName) {
		ETLField etlFieldFound = null;
		for (ETLField etlField : etlFields) {
			// ToDo: We are doing a case-insensitive comparison here. 
			if (Config.getConfig().compareAttributeNames(etlField.getStreamName(), etlStreamName)) {etlFieldFound = etlField; break;}
		}
		return etlFieldFound;
	}
	/**
	 * Search for an ETL Field by column name only (Case-insensitive)
	 * @param etlColumnName The column name to search for
	 * @return a reference to the ETLField object if found, null otherwise
	 */
	public ETLField findETLFieldByColumnName(String etlColumnName) {
		ETLField etlFieldFound = null;
		for (ETLField etlField : etlFields) {
			// ToDo: We are doing a case-insensitive comparison here. 
			if (etlField.getColumnName().toUpperCase().equals(etlColumnName.toUpperCase())) {etlFieldFound = etlField; break;}
		}
		return etlFieldFound;
	}
	/**
	 * Get the reference to the ETLFields collection
	 * @return The reference to the ETLFields collection
	 */
//	@SuppressWarnings("unchecked")
	public ArrayList<ETLField> getETLFields() {
//		return (ArrayList<ETLField>) etlFields.clone();
		return etlFields;
	}

	/**
	 * @return A string representation of the object
	 */
	public String toString() {
		String result = "";
		String delimiter = "";
		for (ETLField etlField : etlFields) {
			result += delimiter + etlField.toString();
			delimiter = ", ";
		}
		return result;
	}
/*	public void add(ETLField etlField) {
		etlFields.add(new ETLField(etlField));
	} Use addETLField instead! */
	
	@Override
	public Iterator<ETLField> iterator() {
		return etlFields.iterator();
	}
}
