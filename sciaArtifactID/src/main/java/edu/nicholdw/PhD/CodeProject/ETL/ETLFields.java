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

	/**
	 * Add an ETLField object to the collection if it's not already there.
	 * @param etlFieldToAdd ETLField object to be added. 
	 */
	public void addETLField(ETLField etlFieldToAdd) {
		Boolean alreadyThere = false;
		for (ETLField etlField : etlFields) {
			if (ETLField.compare(etlFieldToAdd, etlField) == true) {alreadyThere = true; break;}
		}
		if (!alreadyThere) {
			etlFields.add(new ETLField(etlFieldToAdd));
		}
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
/*	public void add(ETLField etlField) {
		etlFields.add(new ETLField(etlField));
	} Use addETLField instead! */
	
	@Override
	public Iterator<ETLField> iterator() {
		return etlFields.iterator();
	}
}
