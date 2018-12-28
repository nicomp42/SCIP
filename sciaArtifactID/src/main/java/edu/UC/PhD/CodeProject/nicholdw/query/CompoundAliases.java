/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * A collection of CompoundAlias objects
 * @author nicomp
 */
public class CompoundAliases implements Iterable<CompoundAlias> {
	private ArrayList<CompoundAlias> compoundAliases;

	/**
	 * Constructor
	 */
	public CompoundAliases() {
		compoundAliases = new ArrayList<CompoundAlias>();
	}
	/**
	 * Retrieve the list of CompoundAliases in this CompoundAlias List
	 * @return A reference to the CompoundAlias list in the current object.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CompoundAlias> getCompoundAliases (){return (ArrayList<CompoundAlias>) compoundAliases.clone();}
	public CompoundAlias getQueryAttribute(int i) {return compoundAliases.get(i);}

	/**
	 * Add a CompoundAlias object to the collection
	 * @param compoundAlias
	 * @return true if there was no duplicate and the CompoundAlias was added
	 */
	public boolean addCompoundAlias(CompoundAlias compoundAlias) {
		Boolean result = false;	// expect a duplicate
		try {
			// Don't allow duplicates of the alias name
			boolean matchFound = false;
			for (CompoundAlias ca : compoundAliases) {
				if (ca.getAliasName().trim().toLowerCase().equals(compoundAlias.getAliasName().trim().toLowerCase())) {
					matchFound = true;
					break;
				}
			}
			if (!matchFound) {
				result = true;
				compoundAliases.add(new CompoundAlias(compoundAlias));
			}
		} catch (Exception ex) {
			Log.logError("ComnpoundAliases.AddCopmpoundAlias): " + ex.getMessage());
		}
		return result;
	}
	public void clear() {compoundAliases.clear();}
	@Override
	public Iterator<CompoundAlias> iterator() {
		Iterator<CompoundAlias> myIterator = compoundAliases.iterator();
        return myIterator;
    }
	public String toString() {return compoundAliases.size() + " attributes";}
	public int size() { return compoundAliases.size();}
}
