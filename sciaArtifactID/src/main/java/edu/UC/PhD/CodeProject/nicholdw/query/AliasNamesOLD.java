package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/***
 * List of AliasName objects
 * @author nicomp
 *
 */
public class AliasNamesOLD implements Iterable<AliasNameClassOLD> {
	private ArrayList<AliasNameClassOLD> aliasNames;

	/**
	 * Constructor
	 */
	public AliasNamesOLD() {
		aliasNames = new ArrayList<AliasNameClassOLD>();
	}

	@Override
	public Iterator<AliasNameClassOLD> iterator() {
		Iterator<AliasNameClassOLD> iprof = aliasNames.iterator();
        return iprof;
    }
	public int size() {return aliasNames.size();}

	public void addAliasName(AliasNameClassOLD aliasNameClass) {
		if (aliasNameClass.getAliasName().length() > 0) {
			if (!this.contains(aliasNameClass) ) {aliasNames.add(new AliasNameClassOLD(aliasNameClass));}
		}
	}
	public void addAliasNames(AliasNamesOLD aliasNames) {
		for (AliasNameClassOLD aliasName : aliasNames) {
			addAliasName(aliasName);
		}
	}
	/**
	 * Does this collection of alias name objects contain a particular alias name object
	 * @param aliasNameClassTarget
	 * @return True if aliasNameClassTarget is in the collection, false otherwise
	 */
	public Boolean contains(AliasNameClassOLD aliasNameClassTarget) {
		Boolean result = false;
		for (AliasNameClassOLD aliasNameClass: aliasNames) {
			if (Config.getConfig().compareAliasNames(aliasNameClass.getAliasName(), aliasNameClassTarget.getAliasName())) {
				result = true;
				break;
			}
		}
		return result;
	}
	/**
	 * Create a comma-delimited list of all the alias names in the collection
	 */
	public String toString() {
		StringBuilder result = new StringBuilder("");
		String comma = "";
		for (AliasNameClassOLD aliasNameClass : aliasNames) {
			result.append(comma);
			result.append(aliasNameClass.toString());
			comma = ", ";
		}
		// If the alias set is > 1, add curly braces for clarity
		if (aliasNames.size() > 1) { result.insert(0, "{").append("}"); }
		return result.toString();
	}
}