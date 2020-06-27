/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.Views;

/**
 * Aggregation of the impact of an action query on a schema
 * @author nicomp
 */
public class SchemaImpact {
	private QueryDefinition queryDefinition;
	private TableAttributes tableAttributes;
	private QueryAttributes queryAttributes;
	private Tables tables;
	private Schemas schemas;
	private Views views;
	
	public SchemaImpact() {
		setTableAttributes(new TableAttributes());
		setQueryAttributes(new QueryAttributes());
		setTables(new Tables());
		setSchemas(new Schemas());
		setViews(new Views());
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

	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	public Schemas getSchemas() {
		return schemas;
	}

	public void setSchemas(Schemas schemas) {
		this.schemas = schemas;
	}

	public Views getViews() {
		return views;
	}

	public void setViews(Views views) {
		this.views = views;
	}
}
