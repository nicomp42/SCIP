/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.query.Views;

/**
 * Aggregation of the impact of an action query on a schema
 * @author nicomp
 */
public class SchemaImpact {
	private QueryDefinition queryDefinition;
	private QueryDefinitions queryDefinitions;
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
		setQueryDefinitions(new QueryDefinitions());
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
	/**
	 * Get the action query for this SchemaImpact object
	 * @return The action query for this SchemaImpact object
	 */
	public QueryDefinition getQueryDefinition() {
		return queryDefinition;
	}
	/**
	 * Define the action query for this SchemaImpact object
	 * @param queryDefinition The action query for this SchemaImpact object
	 */
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
	/**
	 * The querys affected by this action query, if any
	 * @return
	 */
	public QueryDefinitions getQueryDefinitions() {
		return queryDefinitions;
	}
	/**
	 * The querys affected by this action query, if any
	 * @param queryDefinitions
	 */
	public void setQueryDefinitions(QueryDefinitions queryDefinitions) {
		this.queryDefinitions = queryDefinitions;
	}
}
