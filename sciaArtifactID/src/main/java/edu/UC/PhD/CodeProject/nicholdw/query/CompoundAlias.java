/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import edu.UC.PhD.CodeProject.nicholdw.UUIDnicholdw;

/**
 * An alias in a SQL statement that refers to an expression which may have zero or more attributes in it
 * For example: SELECT (tAlpha.beta + tAlpha.delta) AS mySum FROM ...
 * @author nicomp
 */
public class CompoundAlias implements NameThing {

	private ArrayList<FullColumnName> fullColumnNames;
	private String aliasName;
	private UUIDnicholdw ID;
	@SuppressWarnings("unchecked")
	public CompoundAlias(CompoundAlias compoundAlias) {
		this.setAliasName(compoundAlias.getAliasName());
		this.fullColumnNames = (ArrayList<FullColumnName>) compoundAlias.fullColumnNames.clone();
		this.ID = new UUIDnicholdw(compoundAlias.getID());
	}
	public CompoundAlias(String aliasName) {
		fullColumnNames = new ArrayList<FullColumnName>();
		this.setAliasName(aliasName);
		ID = new UUIDnicholdw();
	}
	public void addFullColumnName(FullColumnName fullColumnName) {
		fullColumnNames.add(fullColumnName);
	}
	public String getName() {return getAliasName();}
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
	@SuppressWarnings("unchecked")
	public ArrayList<FullColumnName> getFullColumnNames() {
		return (ArrayList<FullColumnName>) fullColumnNames.clone();
	}
	public UUIDnicholdw getID() {
		return ID;
	}
/*	public void setID(String iD) {
		ID = iD;
	} */
}
