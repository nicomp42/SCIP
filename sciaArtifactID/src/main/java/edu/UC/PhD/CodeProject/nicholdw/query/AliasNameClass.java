package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.Config;

public class AliasNameClass {
	private String aliasName;

	public AliasNameClass(String aliasName) {
		setAliasName(aliasName);
	}
	public String getAliasName() {return aliasName;}

	public void setAliasName(String aliasName) {this.aliasName = aliasName;}

	public Boolean equals(AliasNameClass aliasNameClass) {
		Boolean result = false;
		if (Config.getConfig().getUsecasesensitivealiasnamecomparison() == true) {
			if (this.aliasName.equals(aliasNameClass.getAliasName())) {
				result = true;
			}
		} else {
			if (this.aliasName.toLowerCase().equals(aliasNameClass.getAliasName().toLowerCase())) {
				result = true;
			}
		}
		return result;
	}
}
