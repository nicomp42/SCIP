package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/***
 * List of AliasName objects
 * @author nicomp
 *
 */
public class AliasNames implements Iterable<AliasNameClass> {

	private ArrayList<AliasNameClass> aliasNames;

	/**
	 * Constructor
	 */
	public AliasNames() {
		aliasNames = new ArrayList<AliasNameClass>();
	}

	@Override
	public Iterator<AliasNameClass> iterator() {
		Iterator<AliasNameClass> iprof = aliasNames.iterator();
        return iprof;
    }
	public int size() {return aliasNames.size();}

	public void addAliasName(AliasNameClass aliasNameClass) {
		if (!this.contains(aliasNameClass) ) {aliasNames.add(aliasNameClass);}
	}

	/**
	 * Does this collection of alias name objects contain a particular alias name object
	 * @param aliasNameClassTarget
	 * @return True if aliasNameClassTarget is in the collection, false otherwise
	 */
	public Boolean contains(AliasNameClass aliasNameClassTarget) {
		Boolean result = false;
		for (AliasNameClass aliasNameClass: aliasNames) {
			if (Config.compareAliasNames(aliasNameClass.getAliasName(), aliasNameClassTarget.getAliasName())) {
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
		for (AliasNameClass aliasNameClass : aliasNames) {
			result.append(comma);
			result.append(aliasNameClass.toString());
			comma = ", ";
		}
		return result.toString();
	}
}
