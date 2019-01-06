/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.UUID;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * A member of a QueryDefinition object.
 * @author nicomp
 */
public class QueryAttribute extends QueryComponent implements NameThing  {
	private AliasNamesOLD aliasNames;
	private String schemaName;		// Not necessarily redundant. A query can span multiple schemas
	private QueryClause queryClause;
	private String attributeName;
	private String tableName;
	private String expression;
	private String ID;
	/**
	 * Create a QueryAttribute object
	 * @param schemaName
	 * @param tableName
	 * @param attributeName
	 * @param aliasName If you only have one alias for this QueryAttribute object
	 * @param queryClause
	 */
	public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNameClassOLD aliasName, QueryClause queryClause) {
		this.setTableName(tableName);
		aliasNames = new AliasNamesOLD();
		this.addAliasName (aliasName);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
		ID = UUID.randomUUID().toString();
	}
	/**
	 * Create a new QueryAttribute object
	 * @param schemaName
	 * @param tableName
	 * @param attributeName
	 * @param aliasNames If you already have an AliasNames object for this QueryAttribute object
	 * @param queryClause
	 */
	public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNamesOLD aliasNames, QueryClause queryClause) {
		this.setTableName(tableName);
		this.aliasNames = new AliasNamesOLD();
		this.aliasNames.addAliasNames(aliasNames);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
		ID = UUID.randomUUID().toString();
	}
	public Boolean isConstant() {
		// TODO: write this
		return false;
	}
	public void setAttributeName(String attributeName) {this.attributeName = attributeName;}
	public String getAttributeName() {return this.attributeName;}

	/**
	 * Set the table name from whence this attribute originates. This could be an alias!
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		if (tableName == null) {
			this.tableName = "";
		} else {
			this.tableName = tableName.trim();
		}
	}
	public String getTableName() {return this.tableName;}

	public AliasNamesOLD getAliasNames() {return aliasNames;}
	public void addAliasName(AliasNameClassOLD aliasNameClass) {
		if (aliasNameClass != null) {
			aliasNames.addAliasName(aliasNameClass);
		}
	}
	public void addAliasNames(AliasNamesOLD aliasNames) {
		for (AliasNameClassOLD aliasName : aliasNames) {
			this.aliasNames.addAliasName(aliasName);
		}
	}
	public Boolean hasAliasName(String aliasNameTarget) {
		Boolean result = false;
		for (AliasNameClassOLD aliasName: aliasNames) {
			if (aliasName.equals(aliasNameTarget)) {
				result = true;
				break;
			}
		}
		return result;
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {
		if (schemaName == null) {
			this.schemaName = "";
		} else {
			this.schemaName = schemaName.trim();
		}
	}
	public QueryClause getQueryClause() {return queryClause;}
	public void setQueryClause(QueryClause queryClause) {this.queryClause = queryClause;}

	public String toString() {
		String aliasNameToString = aliasNameListToString();
		StringBuilder result = new StringBuilder("");
		try {
			result.append(schemaName + ":");
			result.append(tableName + ":");
			result.append(attributeName);
			result.append((aliasNameToString.length() > 0? " AS " + aliasNameToString:""));
//			result.append("  (" + queryClause.toString() + " query clause)");
		} catch (Exception ex) {
			Log.logError("QueryAttribute.toString(); " + ex.getLocalizedMessage());
		}
		return result.toString();
	}

	/**
	 * Build a comma-delimited list of the alias names for this attribute or an empty string if there are none
	 * @return The comma-delimited string
	 */
	public String aliasNameListToString() {
		StringBuilder result = new StringBuilder("");
		// There may be no attribute list, just return an empty string
		String comma = "";
		try {
			for (AliasNameClassOLD aliasName: aliasNames) {
				result.append(comma + aliasName.getAliasName());
				comma = ", ";
			}
		} catch (Exception ex) {
			// Eat it.
		}
		if (aliasNames.size() > 1) { result.insert(0, "{").append("}"); }
		return result.toString();
	}
	/**
	 * Build a reference to this attribute that will be unique in the query. 
	 * This is useful because sometimes an attribute will not have an alias in the original SQL taken from the RDBMS.
	 * @return
	 */
	public String getUniqueAttributeName() {
		return  getSchemaName() + "#" + getTableName() + "#" + getAttributeName();
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
	/**
	 * Is an attribute the same as this one?
	 * @param queryAttribute
	 * @return True if the attributes are the same for {attribute name, table name, schema name}, false otherwise. 
	 */
	public boolean equals(QueryAttribute queryAttribute) {
		boolean result = false;
		if (this.getAttributeName().equals(queryAttribute.getAttributeName()) && 
			this.getTableName().equals(queryAttribute.getTableName()) && 
			this.getSchemaName().equals(queryAttribute.getSchemaName())) {
			result = true;
		}
		return result;
	}
	/***
	 * Get the unique identifier of the attribute
	 * @return the ID
	 */
	public String getID() {
		return ID;
	}
/*	
	public void setID(int iD) {
		ID = iD;
	} */
	@Override
	public String getName() {
		return getAttributeName();
	}
}
