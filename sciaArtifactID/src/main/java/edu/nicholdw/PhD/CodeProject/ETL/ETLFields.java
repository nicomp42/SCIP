/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

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

	public void addField(ETLField etlField) {
		etlFields.add(etlField);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ETLField> getETLFields() {return (ArrayList<ETLField>) etlFields.clone();}
	
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
	public void add(ETLField etlField) {
		etlFields.add(new ETLField(etlField));
	}
	
	@Override
	public Iterator<ETLField> iterator() {
		return etlFields.iterator();
	}
}
