package edu.UC.PhD.CodeProject.nicholdw.queryType;

public abstract class QueryType {
	private String friendlyName;

	public QueryType(String friendlyName) {setFriendlyName(friendlyName);}
	public String getFriendlyName() {return friendlyName;}
	public void setFriendlyName(String friendlyName) {this.friendlyName = friendlyName;}

}
