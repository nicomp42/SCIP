/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.UUID;

import edu.UC.PhD.CodeProject.nicholdw.Attributable;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * A member of a QueryDefinition object.
 * @author nicomp
 */
public class QueryAttribute extends QueryComponent implements Name, java.io.Serializable, Attributable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8149287939208412796L;
	private AliasNamesOLD aliasNames;
	private String schemaName;		// Not necessarily redundant. A query can span multiple schemas
	private QueryClause queryClause;
	private String attributeName;
	private String tableName;			// The table/query that the attribute lives in. 
	private String tableAliasName;		// If the attribute as it appeared in the query referenced a table alias, we will store it here
	private String expression;
	private String ID;
	private String key;
	private Boolean affectedByActionQuery;		// Defaults to false until an action query is applied to the query where this attribute lives
	private ATTRIBUTE_DISPOSITION attributeDisposition;
	public enum ATTRIBUTE_DISPOSITION {Select, Add, Drop, Alter};
	private GraphNodeAnnotation graphNodeAnnotation;
	private boolean indirectlyAffectedByActionQuery;
	public Boolean getIndirectlyAffectedByActionQuery() {return indirectlyAffectedByActionQuery;}
	public void setIndirectlyAffectedByActionQuery(Boolean indirectlyAffectedByActionQuery) {
		this.indirectlyAffectedByActionQuery = indirectlyAffectedByActionQuery;
	}

	public QueryAttribute(QueryAttribute qa) {
		this.aliasNames = new AliasNamesOLD();
		setSchemaName(qa.getSchemaName());
		setContainerName(qa.getContainerName());
		setTableAliasName(qa.getTableAliasName());
		setAttributeName(qa.getAttributeName());
		setAliasNames(qa.getAliasNames());
		setAttributeDisposition(qa.getAttributeDisposition());
		setQueryClause(qa.getQueryClause());
	}
	/***
	 * Duplicate a list of alias names into this QueryAttributes object. Deep copy.
	 * @param aliasNames the list of alias names to duplicate
	 */
	public void setAliasNames(AliasNamesOLD aliasNames) {
		if (aliasNames != null) {
			this.aliasNames = new AliasNamesOLD();
			for (AliasNameClassOLD an : aliasNames) {
				this.aliasNames.addAliasName(an);
			}
		} else {
			// Nothing to copy, let's make it an empty data structure
			this.aliasNames = new AliasNamesOLD();
		}
	}
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
	public QueryAttribute(String schemaName, String tableName, String attributeName, 
			              AliasNameClassOLD aliasName, 
			              QueryClause queryClause, String tableAliasName, 
			              ATTRIBUTE_DISPOSITION attributeDisposition) {
		this.setContainerName(tableName);
		aliasNames = new AliasNamesOLD();
		this.addAliasName (aliasName);
		this.setSchemaName(schemaName);
		this.setQueryClause(queryClause);
		this.setAttributeName(attributeName);
		ID = UUID.randomUUID().toString();
		this.tableAliasName = tableAliasName;
		this.setAttributeDisposition(attributeDisposition);
		affectedByActionQuery = false;
		graphNodeAnnotation = new GraphNodeAnnotation();
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
	public QueryAttribute(String schemaName, String tableName, String attributeName, 
			              AliasNameClassOLD aliasName, QueryClause queryClause, 
			              String tableAliasName) {
		this.aliasNames = new AliasNamesOLD();
		this.setContainerName(tableName);
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
		graphNodeAnnotation = new GraphNodeAnnotation();
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
		this.setContainerName(tableName);
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
		graphNodeAnnotation = new GraphNodeAnnotation();
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
	public void setContainerName(String tableName) {
		if (tableName == null || tableName.trim().length() == 0) {
			this.tableName = "";
		} else {
			this.tableName = Table.formatTableName(tableName);
		}
	}
	public String getContainerName() {return this.tableName;}

	public AliasNamesOLD getAliasNames() {
		return aliasNames;
	}
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
			result.append(" (" + getAttributeDisposition().toString() + ")");
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
		return  getSchemaName() + "#" + getContainerName() + "#" + getAttributeName();
	}
	/**
	 * Build the name of the attribute with schema name and table name for display purposes.
	 * @return The formatted name
	 */
	public String getFullyQualifiedName() {
		return  getSchemaName() + "." + getContainerName() + "." + getAttributeName();
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
			Config.getConfig().compareAliasNames(this.getAliasNames(), queryAttribute.getAliasNames()) && 
			Config.getConfig().compareTableNames(this.getContainerName(), queryAttribute.getContainerName()) && 
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
		return new FullColumnName(getSchemaName(), getContainerName(), getAttributeName());
	}
	/***
	 * If the attribute has an alias, return that, else return the attribute name
	 * @return The alias or attribute
	 */
	public String getAttributeNameOrAliasName() {
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
	@Override
	public Boolean getAffectedByActionQuery() {
		return affectedByActionQuery;
	}
	@Override
	public void setAffectedByActionQuery(Boolean affectedByActionQuery) {
		this.affectedByActionQuery = affectedByActionQuery;
	}
	public void setGraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation) {
		this.graphNodeAnnotation = new GraphNodeAnnotation(graphNodeAnnotation);
	}
	/***
	 * Get a copy of the GraphNodeAnnotation for the current object
	 * @return A copy of the GraphNodeAnnotation for the current object
	 */
	public GraphNodeAnnotation getGraphNodeAnnotation() {return new GraphNodeAnnotation(graphNodeAnnotation);}
	@Override
	public String getKey() {
		return key;
	}
	@Override
	public void setKey(String key) {
		this.key = key;
	}
}
