/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;
/***
 * The text of an action query. Nothing more.
 * Use QueryDefinition for a full-blown query model
 * @author nicomp
 *
 */
public class ActionQuery {
	private String sql;

	public ActionQuery(String sql) {
		setSql(sql);
	}
	/**
	 * Copy Constructor
	 * @param actionQuery ActionQuery to clone.
	 */
	public ActionQuery(ActionQuery actionQuery) {
		setSql(actionQuery.getSql());
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
