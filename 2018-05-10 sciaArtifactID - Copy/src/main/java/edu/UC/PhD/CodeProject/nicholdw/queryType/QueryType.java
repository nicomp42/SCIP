/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.queryType;

public abstract class QueryType {
	private String queryType;

	public QueryType(String queryType) {setQueryType(queryType);}
	public String getQueryType() {return queryType;}
	public void setQueryType(String queryType) {this.queryType = queryType;}
	public String toString() {return queryType;}
}
