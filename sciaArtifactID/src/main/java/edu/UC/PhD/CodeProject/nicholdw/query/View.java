/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

/***
 * The schema name and view name of a view in a MySQL instance
 * @author nicomp
 *
 */
public class View {
	private String viewName;
	private String schemaName;
	public View(String schemaName, String viewName) {
		setViewName(viewName);
		setSchemaName(schemaName);
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String toString() {
		return getSchemaName() + "." + getViewName();
	}
}
