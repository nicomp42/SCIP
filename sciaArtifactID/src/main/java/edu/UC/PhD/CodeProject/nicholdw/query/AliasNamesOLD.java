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
	public String getFirstAlias() {return aliasNames.get(0).toString().replace("`", "");}
	@Override
	public Iterator<AliasNameClassOLD> iterator() {
		Iterator<AliasNameClassOLD> iprof = aliasNames.iterator();
        return iprof;
    }
	public int size() {return aliasNames.size();}

	/**
	 * Add a alias name to the collection. Dups and blanks will be ignored.
	 * @param aliasNameClass
	 */
	public void addAliasName(AliasNameClassOLD aliasNameClassOLD) {
		Boolean found = false;
		if (aliasNameClassOLD.getAliasName().trim().length() > 0) {
			for (AliasNameClassOLD ancOLD : aliasNames) {
				if (ancOLD.equals(aliasNameClassOLD)) {
					found = true;
					break;
				}
			}
			if (found == false) {
				aliasNames.add(new AliasNameClassOLD(aliasNameClassOLD));
			}
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
