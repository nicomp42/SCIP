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

	public String toString() {return schemaName + ":" + tableName + ":" + queryClause.toString() + ":" + attributeName + ":" + aliasNameListToString();}

	public String aliasNameListToString() {
		String result = "";
		String comma = "";
		for (AliasNameClass aliasName: aliasNames) {
			result += comma + aliasName;
			comma = ", ";
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
