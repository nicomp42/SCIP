package edu.UC.PhD.CodeProject.nicholdw;

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
}
