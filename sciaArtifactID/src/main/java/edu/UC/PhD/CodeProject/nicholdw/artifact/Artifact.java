/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.artifact;
/***
 * An chunk of a Data Warehouse that we will be watching. The term 'artifact' is defined in our research
 * There must be enough here to uniquely identify the artifact in an entire test case. See the testCase package.
 * @author nicomp
 *
 */
public class Artifact {
	private String host;
	private String schema;
	private String table;
	private String query;
	private String attribute;
	public Artifact(String host, String schema, String table, String query, String attribute) {
		setHost(host);
		setSchema(schema);
		setTable(table);
		setQuery(query);
		setAttribute(attribute);
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
