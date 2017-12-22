package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.Config;

public class AliasNameClass {
	private String aliasName;

	public AliasNameClass(String aliasName) {
		setAliasName(aliasName);
	}
	public String getAliasName() {return aliasName;}

	public void setAliasName(String aliasName) {
		if (aliasName != null) {
			this.aliasName = aliasName.trim();
		} else {
			this.aliasName = "";
		}
	}
	/**
	 * Compare the name properties of two AliasNameClass objects
	 * @param aliasNameClass
	 * @return true if the names are lexically identical, false otherwise. 
	 */
	public Boolean equals(AliasNameClass aliasNameClass) {
		Boolean result = false;
		return (Config.getConfig().compareAliasNames(this.getAliasName(), aliasNameClass.getAliasName()));
	}
	public String toString() {return getAliasName();}
}
