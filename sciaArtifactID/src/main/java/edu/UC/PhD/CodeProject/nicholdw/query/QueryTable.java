package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.Table;

public class QueryTable extends Table {

	/**
	 * If true, the query has a ".*" qualifier on this table in the SELECT clause. All attributes should be included.
	 */
	private boolean includeAllAttributes;
	private String aliasName;
	private QueryClause queryClause;
	private Boolean isQuery;		// If true, this is a query, else it's a table
	private Boolean isProcessed;
	private String containingQueryName;

	public QueryTable(String schemaName, String tableName, String alias, QueryClause queryClause) {
		super(tableName, schemaName);
		setIncludeAllAttributes(false);
		setAliasName(alias);
		setQueryClause(queryClause);
		setIsQuery(true);		// Assume it's a table. We will reconcile it later. See QueryDefinition.reconcileTables() 
		setIsProcessed(false);
		setContainingQueryName("");
	}

	/**
	 * @return 	true if the query includes all attributes in this table: table has a ".*" in the SELECT clause
	 */
	public boolean isIncludeAllAttributes() {return includeAllAttributes;}
	/**
	 * Does the query include all attributes in this table?
	 * @param includeAllAttributes  True if this table has a ".*" in the SELECT clause
	 */
	public void setIncludeAllAttributes(boolean includeAllAttributes) {this.includeAllAttributes = includeAllAttributes;}
	public String getAliasName() {return aliasName;}
	public void setAliasName(String aliasName) {this.aliasName = aliasName;}
	public QueryClause getQueryClause() {return queryClause;}
	public void setQueryClause(QueryClause queryClause) {this.queryClause = queryClause;}
	public String toString() {return schemaName + ":" + tableName + ":" + aliasName;}
	public Boolean getIsQuery() {return isQuery;}
	public void setIsQuery(Boolean isQuery) {this.isQuery = isQuery;}
	public Boolean getIsProcessed() {return isProcessed;}
	public void setIsProcessed(Boolean isProcessed) {this.isProcessed = isProcessed;}

	public String getContainingQueryName() {
		return containingQueryName;
	}

	public void setContainingQueryName(String containingQueryName) {
		this.containingQueryName = containingQueryName;
	}
}
