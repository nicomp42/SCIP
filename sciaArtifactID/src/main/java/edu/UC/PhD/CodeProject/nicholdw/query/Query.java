/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.ImpactGraphNode;
import edu.UC.PhD.CodeProject.nicholdw.Utils;

/**
 * A query attribute including attribute name and corresponding relation, database (actually, schema).
 * We populate these little objects and then export collections of them to a CSV file for import into the graph DB, Neo4j
 * @author nicomp
 *
 */
public class Query extends ImpactGraphNode {

	private String queryLabel;
	private String attribute;
	private String relationName;
	private String dbName;
	public String getQueryLabel() {
		return queryLabel;
	}
	public void setQueryLabel(String queryLabel) {
		this.queryLabel = queryLabel;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return "Query [queryLabel=" + queryLabel + ", attribute=" + attribute + ", relationName=" + relationName + ", dbName=" + dbName + "]";
	}

	public Query(String queryLabel, String attribute, String relationName, String dbName) {
		super();
		this.queryLabel = Utils.removeBackQuotes(queryLabel);
		this.attribute = Utils.removeBackQuotes(attribute);
		this.relationName = Utils.removeBackQuotes(relationName);
		this.dbName = Utils.removeBackQuotes(dbName);
	}
	public Query() {
		super();
	}
}
