/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;

/**
 * Aggregation of the impact of an action query on a schema
 * @author nicomp
 */
public class SchemaImpact {
	private QueryDefinition queryDefinition;
	private TableAttributes tableAttributes;
	private QueryAttributes queryAttributes;
	
	public SchemaImpact() {
		setTableAttributes(new TableAttributes());
		setQueryAttributes(new QueryAttributes());
	}

	public TableAttributes getTableAttributes() {
		return tableAttributes;
	}

	public void setTableAttributes(TableAttributes tableAttributes) {
		this.tableAttributes = tableAttributes;
	}

	public QueryAttributes getQueryAttributes() {
		return queryAttributes;
	}

	public void setQueryAttributes(QueryAttributes queryAttributes) {
		this.queryAttributes = queryAttributes;
	}

	public QueryDefinition getQueryDefinition() {
		return queryDefinition;
	}

	public void setQueryDefinition(QueryDefinition queryDefinition) {
		this.queryDefinition = queryDefinition;
	}
}
