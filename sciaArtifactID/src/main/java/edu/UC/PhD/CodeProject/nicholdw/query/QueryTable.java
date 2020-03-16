/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.Table;

public class QueryTable extends Table implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7332079760987807509L;
	/**
	 * If true, the query has a ".*" qualifier on this table in the SELECT clause. All attributes should be included.
	 */
	private boolean includeAllAttributes;
	private AliasNamesOLD aliasNames;			// ToDo a table referenced in a query can have multiple alias names in the MySQL dialect. Yikes. 
	private QueryClause queryClause;
	private Boolean isQuery;		// If true, this is a query, else it's a table
	private Boolean isProcessed;
	private String containingQueryName;
	private QueryAttribute queryAttributeProvenance;
	public QueryAttribute getQueryAttributeProvenance() {
		return queryAttributeProvenance;
	}
	public void setQueryAttributeProvenance(QueryAttribute queryAttributeProvenance) {
		this.queryAttributeProvenance = queryAttributeProvenance;
	}	

	public QueryTable(String schemaName, String tableName, AliasNameClassOLD aliasNameClass, QueryClause queryClause) {
		super(tableName, schemaName);
		setIncludeAllAttributes(false);
		aliasNames = new AliasNamesOLD();
		addAliasName(aliasNameClass);
		setQueryClause(queryClause);
		setIsQuery(true);		// Assume it's a query. We will reconcile it later. See QueryDefinition.reconcileTables() 
		setIsProcessed(false);
		setContainingQueryName("");
		queryAttributeProvenance = new QueryAttribute("", "", "", new AliasNameClassOLD(""), new QueryClauseUnknown(), "" );
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
	public AliasNamesOLD getAliasNames() {return aliasNames;}
	public void addAliasName(AliasNameClassOLD aliasNameClass) {
		if (aliasNameClass != null && aliasNameClass.getAliasName().length() > 0) {this.aliasNames.addAliasName(aliasNameClass);}
	}
	public QueryClause getQueryClause() {return queryClause;}
	public void setQueryClause(QueryClause queryClause) {this.queryClause = queryClause;}
	public String toString() {return schemaName + ":" + tableName + ((aliasNames.toString().trim().length() > 0) ? " AS " + aliasNames.toString(): "");}
	public Boolean getIsQuery() {return isQuery;}
	public void setIsQuery(Boolean isQuery) {
		this.isQuery = isQuery;
	}
	public Boolean getIsProcessed() {return isProcessed;}
	public void setIsProcessed(Boolean isProcessed) {this.isProcessed = isProcessed;}

	public String getContainingQueryName() {
		return containingQueryName;
	}

	public void setContainingQueryName(String containingQueryName) {
		this.containingQueryName = containingQueryName;
	}
}
