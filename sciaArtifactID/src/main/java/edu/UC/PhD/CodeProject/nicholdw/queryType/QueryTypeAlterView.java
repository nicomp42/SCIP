/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.queryType;

public class QueryTypeAlterView extends QueryTypeAlter {
	/**
	 * We should not be using this because the syntax is "CREATE OR ALTER VIEW..." and 
	 * there's a QueryType class for that.
	 */
	private static final long serialVersionUID = -2136708885528236275L;

	public QueryTypeAlterView() {
		super("Alter View");
	}
}
