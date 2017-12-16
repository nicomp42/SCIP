package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;

/**
 * An alias in a SQL statement that refers to an expression which may have zero or more attributes in it
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
	public String toString() {return aliasName;}
}
