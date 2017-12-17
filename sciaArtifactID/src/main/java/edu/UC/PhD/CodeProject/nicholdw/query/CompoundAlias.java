package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;

/**
 * An alias in a SQL statement that refers to an expression which may have zero or more attributes in it
 * For example: SELECT (tAlpha.beta + tAlpha.delta) AS mySum FROM ...
 * @author nicomp
 */
public class CompoundAlias {

	private ArrayList<FullColumnName> fullColumnNames;
	private String aliasName;
	public CompoundAlias(String aliasName) {
		fullColumnNames = new ArrayList<FullColumnName>();
		this.setAliasName(aliasName);
	}
	public void addFullColumnName(FullColumnName fullColumnName) {
		fullColumnNames.add(fullColumnName);
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	/**
	 * Compose a nicely formatted list of the alias name and all the associated columns names
	 */
	public String toString() {
		String myComma = "";
		String result = aliasName + "(";
		for (FullColumnName fcn : fullColumnNames) {
			result +=  myComma + fcn.toString();
			myComma = ", ";
		}
		result += ")";
		return result;
	}
}
