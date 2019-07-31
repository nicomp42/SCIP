/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.UUID;

import com.sun.org.apache.bcel.internal.generic.Select;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * A member of a QueryDefinition object.
 * @author nicomp
 */
public class QueryAttribute extends QueryComponent implements Name  {
	private AliasNamesOLD aliasNames;
	private String schemaName;		// Not necessarily redundant. A query can span multiple schemas
	private QueryClause queryClause;
	private String attributeName;
	private String tableName;			// The table/query that the attribute lives in. 
	private String tableAliasName;		// If the attribute as it appeared in the query referenced a table alias, we will store it here
	private String expression;
	private String ID;
	private Boolean affectedByActionQuery;		// Defaults to false until an action query is applied to the query where this attribute lives
	private ATTRIBUTE_DISPOSITION attributeDisposition;
	public enum ATTRIBUTE_DISPOSITION {Select, Add, Drop, Alter};
	/**
	 * Create a QueryAttribute object
	 * Attribute Disposition defaults to Select
	 * @param schemaName
	 * @param tableName
	 * @param attributeName
	 * @param aliasName If you only have one alias for this QueryAttribute object
	 * @param queryClause
	 * @param attributeDisposition
	 */
	public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNameClassOLD aliasName, 
			              QueryClause queryClause, String tableAliasName, ATTRIBUTE_DISPOSITION attributeDisposition) {
		this.setTableName(tableName);
		aliasNames = new AliasNamesOLD();
		this.addAliasName (aliasName);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
		ID = UUID.randomUUID().toString();
		this.tableAliasName = tableAliasName;
		this.setAttributeDisposition(attributeDisposition);
		affectedByActionQuery = false;
	}
	/**
	 * Create a QueryAttribute object
	 * Attribute Disposition defaults to Select
	 * @param schemaName
	 * @param tableName
	 * @param attributeName
	 * @param aliasName If you only have one alias for this QueryAttribute object
	 * @param queryClause
	 */
	public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNameClassOLD aliasName, QueryClause queryClause, String tableAliasName) {
		this.setTableName(tableName);
		aliasNames = new AliasNamesOLD();
		this.addAliasName (aliasName);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
		ID = UUID.randomUUID().toString();
		this.tableAliasName = tableAliasName;
		// Default the disposition to Select
		this.setAttributeDisposition(ATTRIBUTE_DISPOSITION.Select);
		affectedByActionQuery = false;
	}
	/**
	 * Create a new QueryAttribute object
	 * Attribute Disposition defaults to Select
	 * @param schemaName
	 * @param tableName
	 * @param attributeName
	 * @param aliasNames If you already have an AliasNames object for this QueryAttribute object
	 * @param queryClause
	 */
	public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNamesOLD aliasNames, QueryClause queryClause, String tableAliasName) {
		this.setTableName(tableName);
		this.aliasNames = new AliasNamesOLD();
		this.aliasNames.addAliasNames(aliasNames);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
		ID = UUID.randomUUID().toString();
		this.tableAliasName = tableAliasName;
		// Default the disposition to Select
		this.setAttributeDisposition(ATTRIBUTE_DISPOSITION.Select);
		affectedByActionQuery = false;
	}
	public String getFirstAlias() {
		return aliasNames.getFirstAlias();
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
			this.tableName = Table.formatTableName(tableName);
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
			if (Config.getConfig().compareAliasNames(aliasName.getAliasName(), aliasNameTarget)) {
				result = true;
				break;
			}
		}
		return result;
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {
		this.schemaName = Schema.formatSchemaName(schemaName);
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
		if (Config.getConfig().compareAttributeNames(this.getAttributeName(), queryAttribute.getAttributeName()) && 
			Config.getConfig().compareTableNames(this.getTableName(), queryAttribute.getTableName()) && 
			Config.getConfig().compareSchemaNames(this.getSchemaName(), queryAttribute.getSchemaName())) {
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
	
	public FullColumnName getFullColumnName() {
		return new FullColumnName(getSchemaName(), getTableName(), getAttributeName());
	}
	/***
	 * If the attribute has an alias, return that, else return the attribute name
	 * @return The alias or attribute
	 */
	public String getAttributeOrAliasName() {
		if (aliasNames.size() > 0) {
			return aliasNames.iterator().next().getAliasName();
		} else {
			return getAttributeName();
		}
	}
	/**
	 * If the attribute as it appeared in the query referenced a table alias, we store it here
	 * @return The table alias name, if it exists, or "" otherwise
	 */
	public String getTableAliasName() {
		return tableAliasName;
	}
	/**
	 * If the attribute as it appeared in the query referenced a table alias, we will store it here
	 * @param tableAliasName The alias name of the table or query that the attribute references. Can be null or "" if it doesn't exist.
	 */
	public void setTableAliasName(String tableAliasName) {
		this.tableAliasName = tableAliasName;
	}
	public ATTRIBUTE_DISPOSITION getAttributeDisposition() {
		return attributeDisposition;
	}
	public void setAttributeDisposition(ATTRIBUTE_DISPOSITION attributeDisposition) {
		this.attributeDisposition = attributeDisposition;
	}
	public Boolean getAffectedByActionQuery() {
		return affectedByActionQuery;
	}
	public void setAffectedByActionQuery(Boolean affectedByActionQuery) {
		this.affectedByActionQuery = affectedByActionQuery;
	}
}
