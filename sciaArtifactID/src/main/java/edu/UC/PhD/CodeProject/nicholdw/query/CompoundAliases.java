/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.UUIDnicholdw;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * A collection of CompoundAlias objects
 * @author nicomp
 */
public class CompoundAliases implements Iterable<CompoundAlias>,  java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8064146952825757524L;
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
	/**
	 * Find a Compound Alias by name
	 * @param aliasName The name to search for
	 * @return A clone of the CompoundAlias object, or null if not found
	 */
	public CompoundAlias findCompoundAlias(String aliasName) {
		CompoundAlias compoundAlias = null;
		for (CompoundAlias ca: compoundAliases) {
			if (Config.getConfig().compareAliasNames(ca.getAliasName(), aliasName)) {
				compoundAlias = new CompoundAlias(ca);
				break;
			}
		}
		return compoundAlias;
	}
	/**
	 * Find a Compound Alias by ID
	 * @param ID The ID to search for
	 * @return A clone of the CompoundAlias object, or null if not found
	 */
	public CompoundAlias findCompoundAlias(UUIDnicholdw ID) {
		CompoundAlias compoundAlias = null;
		for (CompoundAlias ca: compoundAliases) {
			if (ca.getID().compare(ID)) {
				compoundAlias = new CompoundAlias(ca);
				break;
			}
		}
		return compoundAlias;
	}
}
