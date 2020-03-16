/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

public abstract class QueryClause extends QueryComponent implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4664753047326915403L;
	private String queryClauseName;

	public QueryClause(String queryClauseName){
		setQueryClauseName(queryClauseName);
	}
	public String getQueryClause() {return queryClauseName;}
	public void setQueryClauseName(String queryClauseName) {
		this.queryClauseName = queryClauseName;
	}
	public String toString() {return queryClauseName;}
}
