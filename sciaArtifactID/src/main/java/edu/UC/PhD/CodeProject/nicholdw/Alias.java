/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.query.AliasNameClassOLD;

/**
 * An alias for an attribute in a query or a table
 * @author nicomp
 *
 */
public class Alias {
	private String aliasName;

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	
	/***
	 * Copy Constructor
	 * @param alias The Alias to be cloned
	 */
	public Alias(Alias alias) {
		this.setAliasName(alias.getAliasName());
	}
	/**
	 * Compare the name properties of two Alias objects
	 * @param alias
	 * @return true if the names are lexically identical, false otherwise. 
	 */
	public Boolean equals(AliasNameClassOLD alias) {
		Boolean result = false;
		return (Config.getConfig().compareAliasNames(this.getAliasName(), alias.getAliasName()));
	}
	public String toString() {return getAliasName();}
}
