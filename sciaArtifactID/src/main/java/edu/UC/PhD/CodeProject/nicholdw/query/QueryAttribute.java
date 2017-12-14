package edu.UC.PhD.CodeProject.nicholdw.query;

/**
 * A member of a QueryDefinition object.
 * @author nicomp
 */
public class QueryAttribute /* extends Attribute */ {
	private AliasNames aliasNames;
	private String schemaName;		// Not necessarily redundant. A query can span multiple schemas
	private QueryClause queryClause;
	private String attributeName;
	private String tableName;
	private String expression;
	public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNameClass aliasName, QueryClause queryClause) {
		this.setTableName(tableName);
		aliasNames = new AliasNames();
		this.addAliasName (aliasName);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
	}
	public Boolean isConstant() {
		// TODO: write this
		return false;
	}
	public void setAttributeName(String attributeName) {this.attributeName = attributeName;}
	public String getAttributeName() {return this.attributeName;}

	public void setTableName(String tableName) {this.tableName = tableName;}
	public String getTableName() {return this.tableName;}

	public AliasNames getAliasNames() {return aliasNames;}
	public void addAliasName(AliasNameClass aliasName) {aliasNames.addAliasName(aliasName);}
	public Boolean hasAliasName(String aliasNameTarget) {
		Boolean result = false;
		for (AliasNameClass aliasName: aliasNames) {
			if (aliasName.equals(aliasNameTarget)) {
				result = true;
				break;
			}
		}
		return result;
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {this.schemaName = schemaName;}

	public QueryClause getQueryClause() {return queryClause;}
	public void setQueryClause(QueryClause queryClause) {this.queryClause = queryClause;}

	public String toString() {
		String aliasNameToString = aliasNameListToString();
		return ":" + schemaName
			   + ":" + tableName
			   + ":" + attributeName
			   + (aliasNameToString.length() > 0? " AS " + aliasNameToString:"")
			   + "  (" + queryClause.toString() + " query clause)";
	}

	/**
	 * Build a comma-delimited list of the alias names for this attribute or an empty string if there are none
	 * @return The comma-delimited string
	 */
	public String aliasNameListToString() {
		String result = "";
		// There may be no attribute list, just return an empty string
		String comma = "";
		try {
			for (AliasNameClass aliasName: aliasNames) {
				result += comma + aliasName.getAliasName();
				comma = ", ";
			}
		} catch (Exception ex) {
			// Eat it.
		}
		return result;
	}
	/**
	 * Build the name of the attribute with schema name and table name for display purposes.
	 * @return The formatted name
	 */
	public String getFullyQualifiedName() {
		return  getSchemaName() + "." + getTableName() + "." + getAttributeName();
	}
	/** The attribute could be part of a function call, a math expression, etc.
	 * @return The expression, or null if none.
	 */
	public String getExpression() {return expression;}
	public void setExpression(String expression) {this.expression = expression;}
}
