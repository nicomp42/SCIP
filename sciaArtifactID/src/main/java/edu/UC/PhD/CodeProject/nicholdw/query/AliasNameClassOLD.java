package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.Config;

public class AliasNameClassOLD implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4744403665512150211L;
	private String aliasName;

	/**
	 * Copy constructor
	 * @param aliasName
	 */
	public AliasNameClassOLD(AliasNameClassOLD aliasName) {
		this.aliasName = aliasName.getAliasName(); 
	}
	
	public AliasNameClassOLD(String aliasName) {
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
	public Boolean equals(AliasNameClassOLD aliasNameClass) {
//		Boolean result = false;
		return (Config.getConfig().compareAliasNames(this.getAliasName(), aliasNameClass.getAliasName()));
	}
	public String toString() {return getAliasName();}
}
