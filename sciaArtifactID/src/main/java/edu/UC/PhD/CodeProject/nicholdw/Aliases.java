/*
 * Table/query attribute aliases
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;

public class Aliases implements Iterable<Alias> {

	private ArrayList<Alias> aliases;

	/**
	 * Constructor
	 */
	public Aliases() {
		aliases = new ArrayList<Alias>();
	}

	/**
	 * Retrieve the list of attributes in this Alias List
	 * @return A reference to the attribute list in the current object.
	 */
	public ArrayList<Alias> getAliases ()
	{
		return aliases;
	}
	public Alias getAlias(int i) {
		return aliases.get(i);
	}
	public void addAlias(Alias alias) {
		aliases.add(alias);
	}
	public void clear() {
		aliases.clear();
	}
	@Override
	public Iterator<Alias> iterator() {
		Iterator<Alias> myIterator = aliases.iterator();
        return myIterator;
    }
	public int size() {return aliases.size();}

	/**
	 * Search the list of attributes by attribute name
	 * @param attributeName The attribute name to search for
	 * @return The Alias object corresponding to attributeName
	 */
	public Alias findAliasByName(String aliasName) {
		Alias tmp = null;
		for (Alias attribute: aliases) {
			if (attribute.getAliasName().contentEquals(aliasName)) {
				tmp = attribute;
				break;
			}
		}
		return tmp;
	}
	/**
	 * Build a list of the alias names in the Aliases object
	 * @return The list of alias names, comma delimited.
	 */
	public String toString() {
		String result = "";
		String comma = "";
		for (Alias alias: aliases) {
			result += comma + alias.getAliasName();
			comma = ", ";
		}
		return result;
	}
	/***
	 * Perform a clone with deep copy
	 * @param aliases The Aliases object to be cloned
	 * @return The clone
	 */
	public static Aliases clone(Aliases aliases) {
		Aliases aliasesClone = new Aliases();
		if (aliases != null) {
			for (Alias alias: aliases) {
				aliasesClone.addAlias(new Alias(alias));
			}
		}
		return aliasesClone;
	}
}
