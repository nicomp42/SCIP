package edu.UC.PhD.CodeProject.nicholdw.query;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.UC.PhD.CodeProject.nicholdw.Attributes;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.OperationalSchemaQueries;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryType;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import lib.MySQL;

/**
 * Query definition from a MySQL schema.
 * This is for creating the CSV file to be imported into Neo4j.
 * We care about attributes and the tables/schemas they come from.
 *  
 * @author nicomp
 *
 */
public class QueryDefinition {
	private String sql;
	private String queryName;
	private QueryTables queryTables;
	private QueryAttributes queryAttributes;
	private String schemaName;
	private QueryType queryType;
	private String hostName, loginName, password;
	private QueryDefinitions children;
	private QueryDefinition parent;
	private Boolean noNestedQuerys;
	private Boolean isFinal;
	private CompoundAliases compoundAliases;
	  
	public QueryDefinition(String hostName, String loginName, String password, QueryType queryType, String queryName, String sql, String schemaName) {
		setQueryType(queryType);
		setQueryName(queryName);
		setSql(sql);
		setQueryTables(new QueryTables());
		setQueryAttributes(new QueryAttributes());
		setSchemaName(schemaName);
		setHostName(hostName);
		setloginName(loginName);
		setPassword(password);
		children = new QueryDefinitions();
		parent = null;
		setNoNestedQuerys(false);
		setIsFinal(false);
		setCompoundAliases(compoundAliases);
	}
	public void setCompoundAliases(CompoundAliases compoundAliases) {this.compoundAliases = compoundAliases;}
	/*
	public QueryDefinition(QueryType queryType, String queryName, String schemaName) {
		setQueryType(queryType);
		setQueryName(queryName);
		setSql("");
		setQueryTables(new QueryTables());
		setQueryAttributes(new QueryAttributes());
		setSchemaName(schemaName);
	}
*/
	/**
	 * Look up the data type for a specific Query Attribute
	 * @param queryAttribute The Query Attribute in question
	 * @return The data type
	 */
	public String getQueryAttributeType(QueryAttribute queryAttribute) {
		String type = "";
		try {
			// Get the schema/table where the attribute resides
			String tableName = queryAttribute.getTableName();
			String schemaName = queryAttribute.getSchemaName();
			// Get the table definition for the table where the attribute resides. It has to be in the collection of tables in this query definition or in one of the children.
			QueryTable queryTable = lookupTable(this, schemaName, tableName);
			if (queryTable == null) {
				// Is wasn't in the QueryDefinition. Check the children.
				queryTable = traverseQueryDefinitionsForQueryTable(this, schemaName, tableName);
			}
			type = queryTable.getAttributeDataType(queryAttribute.getAttributeName());
			// TODO: need to search children for the data type if the attribute originates in a nested query
		} catch (Exception ex) {
			Log.logError("QueryDefinition.getQueryAttributeType(" + queryAttribute.getAttributeName() + "): " + ex.getLocalizedMessage());
			type = "UNDEFINED";
		}
		// Using the table, look up the data type for the attribute.
		return type;
	}
	/**
	 * Eventually this method should find a table in the query definition
	 * @param queryDefinition
	 * @param tableName
	 * @param schemaName
	 */
	private QueryTable traverseQueryDefinitionsForQueryTable(QueryDefinition queryDefinition, String schemaName, String tableName) {
		QueryTable result = null;
		Log.logProgress("QueryDefinition.traverseQueryDefinitionsForQueryTable(): " + schemaName + "." + tableName);
		for (QueryDefinition qdChild: queryDefinition.children) {
			result = lookupTable(qdChild, schemaName, tableName);
			if (result != null) {return result;}
			result = traverseQueryDefinitionsForQueryTable(qdChild, schemaName, tableName);
		}
		Log.logProgress("QueryDefinition.traverseQueryDefinitions(): finished " + queryDefinition.getSchemaName() + "." + queryDefinition.getQueryName() );
		return result;
	}
	/**
	 * Look up a Table object in the Query Definition based on the table and schema names
	 * @param schemaName The schema name
	 * @param tableName The table name
	 * @return The QueryTable object that corresponds, or null if not found
	 */
	public QueryTable lookupTable(QueryDefinition qd, String schemaName, String tableName) {
		QueryTable result = null;
		for (QueryTable queryTable : qd.getQueryTables()) {
			if (queryTable.getTableName().equals(tableName) && queryTable.getSchemaName().equals(schemaName)) {
				result = queryTable;
				break;
			}
		}
		return result;
	}
	/**
	 * Read all the table definitions in this query definition. We need the data types of the attributes.
	 */
	public void readTableDefinitions() {
		for (QueryTable queryTable : queryTables) {
			Attributes attributes;
			attributes = QueryTable.readAttributesFromTableDefinition(queryTable.getTableName(), queryTable.getSchemaName());
			queryTable.setAttributes(attributes);
		}
	}
	/**
	 *	Populate the list of schemas in this query definition.
	 *  We don't store this, we generate it as-needed. It is derived from the list of tables.
	 * @return The list of schemas
	 */
	public QuerySchemas getQuerySchemas() {
		QuerySchemas querySchemas = new QuerySchemas();
		// Add the schema that this query belongs to. It's always there!
		querySchemas.addSchema(new QuerySchema(getSchemaName()));
		for (QueryTable queryTable : queryTables) {
			if (querySchemas.contains(queryTable.getSchemaName()) != null) {
				querySchemas.addSchema(new QuerySchema(queryTable.getSchemaName()));
				break;
			}
		}
		return querySchemas;
	}
	public void setQueryType(QueryType queryType){this.queryType = queryType;}
	public QueryType getQueryType(){return queryType;}
	public String getSql() {return sql;}
	public void setSql(String sql) {this.sql = sql;}
	public String getQueryName() {return queryName;}
	public void setQueryName(String queryName) {this.queryName = queryName;}
	public QueryTables getQueryTables() {return queryTables;}
	public void setQueryTables(QueryTables queryTables) {this.queryTables = queryTables;}
	public QueryAttributes getQueryAttributes() {return queryAttributes;}
	public void setQueryAttributes(QueryAttributes queryAttributes) {this.queryAttributes = queryAttributes;}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {this.schemaName = schemaName;}
	public String toString() {return schemaName + ":" + queryName + ":" + queryType.getFriendlyName();}
	public void listAttributes(PrintStream myPrintStream) {
		myPrintStream.println("Attributes found in query:");
		for (QueryAttribute queryAttribute: this.getQueryAttributes()) {
			myPrintStream.println(queryAttribute.toString());
		}
	}
	/**
	 * Read the SQL statement from a query defined in the database server
	 * @param hostName Name of the host
	 * @param loginName Login name
	 * @param password Password for the login name
	 * @param schemaName Schema name
	 * @param queryName Query name
	 * @return The SQL statement stored in the query or an empty string if something goes wrong
	 */
	public static String readSQLFromDatabaseServerQueryDefinition(String hostName, String loginName, String password, String schemaName, String queryName) {
		String sql = "";
		if (Config.getConfig().getUseTestData()) {
			Log.logProgress("QueryDefinition.readSQLFromQueryDefinition(): using test data for sql statement");
			try {
				sql = TestQueries.getSQL(queryName.toLowerCase());
			} catch (Exception ex) {
				Log.logError("QueryDefinition.readSQLFromQueryDefinition(): " + ex.getLocalizedMessage());
			}
		} else {
			String query = "SELECT view_definition FROM information_schema.VIEWS where table_schema = " +  MySQL.wrapStringInSingleQuotes(schemaName) + " AND table_name=" + MySQL.wrapStringInSingleQuotes(queryName);
			java.sql.Connection connection = null;
			try {
				connection = new MySQL().connectToDatabase(hostName, schemaName, loginName, password);
			    java.sql.PreparedStatement preparedStatement = null;
			    try {
					preparedStatement = connection.prepareStatement(query);
				} catch (SQLException e) {
					System.out.println("QueryDefinition.readSQLFromSchema() : " + e.getLocalizedMessage());
				}
			    java.sql.ResultSet resultSet = null;
			    try {
					resultSet = preparedStatement.executeQuery();
				} catch (SQLException e) {System.out.println("loadSchemasFromDatabaseServer() : " + e.getLocalizedMessage());}
			    try {
			    	resultSet.next();					// Move to the first record
					sql = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
				} catch (Exception e) {Log.logError("QueryDefinition.readSQLFromSchema() : " + e.getLocalizedMessage());}
			} catch (Exception ex) {Log.logError("QueryDefinition.readSQLFromSchema(): " + ex.getLocalizedMessage()); }
			try {connection.close();} catch (Exception ex) {}
		}
		return sql;
	}
	public void listTables(PrintStream myPrintStream) {
		myPrintStream.println("Tables found in query:");
		for (QueryTable queryTable: this.getQueryTables()) {
			myPrintStream.println(queryTable.toString());
		}
	}
	/**
	 * Make sure attributes have schema names and table names, etc.
	 * After the query has been parsed, this information must be available if the query is properly formed.
	 */
	public void reconcileAttributes() {
		Log.logProgress("QueryDefinition.reconcileAttributes(): " + this.getSchemaName() + "." + this.getQueryName());
		// Make sure every query attribute has a table/query that it belongs to
		for (QueryAttribute qa : this.getQueryAttributes()) {
			Log.logProgress("QueryDefinition.reconcileAttributes(): attribute = " + qa.getAttributeName());
			if (qa.getTableName() == null || qa.getTableName().equals("")) {
				Log.logProgress("QueryDefinition.reconcileArtifacts(); Attribute " + qa.getAttributeName() + " has no table");
				// The attribute has no table name. Assuming this is a properly formed query, there must be one and only one table containing this attribute name
				QueryTable qt = this.getQueryTables().findQueryAttribute(qa);	// This must work, else the query is not properly formed or there is a nested query
				if (qt != null) {
					qa.setTableName(qt.getTableName());
					qa.setSchemaName(qt.getSchemaName());
					Log.logProgress("QueryDefinition.reconcileAttributes(); Attribute " + qa.getAttributeName() + " found in " + qt.getSchemaName() + "." +  qt.getTableName());
				} else {
					Log.logError("QueryDefinition.reconcileAttributes(); Attribute " + qa.getAttributeName() + " not found in any tables. Looking through nested queries.");
					for (QueryDefinition qdChild : children) {
						qt = qdChild.getQueryTables().findQueryAttribute(qa);	// This must work, else the query is not properly formed
						if (qt != null) {
							qa.setTableName(qt.getTableName());
							qa.setSchemaName(qt.getSchemaName());
							// TODO: need data type
							Log.logProgress("QueryDefinition.reconcileAttributes(); Attribute " + qa.getAttributeName() + " found in nested query " + qt.getSchemaName() + "." +  qt.getTableName());
							break;
						}
					}
				}
				if (qt == null) {	// we did not find the attribute in any table
					Log.logError("QueryDefinition.reconcileAttributes(); Attribute " + qa.getAttributeName() + " not found in any tables or nested queries. Very bad. Program will probably crash.");
				}
			} else {
				Log.logProgress("QueryDefinition.reconcileAttributes(): attribute " + qa.getAttributeName() + " already has a table, " + qa.getTableName());
			}
		}
	}
	/**
	 * Make sure tables have schema names and figure out if it's a table or a query
	 * After the query has been parsed, this information must be available because the query is assumed to be properly formed.
	 */
	public void reconcileTables() {
		// Make sure every table/query has a schema
		for (QueryTable qt: this.getQueryTables()) {
			if (qt.getSchemaName() == null || qt.getSchemaName().equals("")) {
				qt.setSchemaName(this.getSchemaName());
				// Figure out if it's a table or a query
				qt.setIsQuery(isItAQuery(qt.getSchemaName(), qt.getTableName(), this));
			}
		}
	}
	/**
	 * Figure out if a table is a table or q query
	 * @param schemaName Schema where the table is
	 * @param queryName name of the table/query
	 * @return True if it's a query, false otherwise
	 */
	private static Boolean isItAQuery(String schemaName, String queryName, QueryDefinition qd) {
	    java.sql.ResultSet resultSet = null;
		Boolean result = false;			// Assume it's not a query
		String sql = OperationalSchemaQueries.qQuerybyQueryNameAndSchema.replace("#s", schemaName).replace("#t", queryName);
    	try {
			resultSet = MySQL.loadResultSet(qd.getHostName(), qd.getloginName(), qd.getPassword(), sql);
			if (resultSet.first() == true) {result = true;}
		} catch (SQLException e) {
			Log.logError("QueryDefinition.isItAQuery(); " +  e.getLocalizedMessage());
		}
		return result;
	}
	/**
	 * Scan through the list of tables/queries. If there are any queries, process them.
	 * Be careful to reconcile the queries in the parent QueryDefinition before calling this method so they have schema names attached to them
	 */
	private void processNestedQueries(QueryDefinition parent) {
		try {
			for (QueryTable qt: this.getQueryTables()) {
				if (qt.getIsQuery()) {
					// Ok. Here we go.
					Log.logProgress("QueryDefinition.processNestedQueries(); processing " + qt.getSchemaName() + "." + qt.getTableName());
					QueryParser qp = new QueryParser();
					QueryDefinition qd;
					String tmpSQL;
					tmpSQL = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(hostName, loginName, password, qt.getSchemaName(), qt.getTableName());
					qd = new QueryDefinition(hostName, loginName, password, new QueryTypeSelect(), qt.getTableName(), tmpSQL, qt.getSchemaName());
					qd.setParent(parent);
					parent.children.addQueryDefinition(qd);
					qp.parseQuery(qd);
				    qd.reconcileTables();
					qd.readTableDefinitions();
					// At this point there will be some attributes that cannot be fully reconciled because the nested queries have not been processed.
				    qd.reconcileAttributes();
					Log.logProgress("QueryDefinition.processNestedQueries(): done with " + qt.getSchemaName() + "." + qt.getTableName());
				    qd.processNestedQueries(qd);
				}
			}
		} catch (Exception ex) {
			Log.logError("QueryDefinition.processNestedQueries(): " + ex.getLocalizedMessage());
		}
	}
	/**
	 * Check all the children of this Query Definition to see if all attributes in all nested querys have been resolved or if there weren't any nested querys.
	 * @return True if either condition is met
	 */
	public Boolean checkAllChildrenForFinal() {
		Boolean isFinalTmp = true;		// Hope for the best
		for (QueryDefinition qd : children) {
			if (qd.getIsFinal() == true) {continue;}
			if (qd.checkForNestedQueries() == false) {
				isFinalTmp = false;
				break;
			}
		}
//		if (isFinal == true) {setIsFinal(true);}
		return isFinalTmp;
	}
	public Boolean checkForNestedQueries() {
		Boolean noNestedQuerysFound = true;		// Hope for the best
		for (QueryTable qt: this.getQueryTables()) {
			if (qt.getIsQuery()) {
				noNestedQuerysFound = false;
				break;
			}
		}
		setNoNestedQuerys(noNestedQuerysFound);
		if (noNestedQuerysFound == true) {setIsFinal(true);}	// No nested queries so this query definition is good to go.
		return getNoNestedQuerys();
	}
	public void mergeQueryDefinitions(QueryDefinition qd) {
		Log.logProgress("QueryDefinition.mergeQueryDefinitions(): Merging " + this.getSchemaName() + "." + this.getQueryName() + " and " + qd.getSchemaName() + "." + qd.getQueryName());
	}
	/**
	 * Do everything to a Query Definition. All we need is a schema name, a query name, the SQL it contains, and login credentials
	 */
	public void crunchIt() {
		Log.logProgress("QueryDefinition.crunchIt(): starting on " + this.getSchemaName() + "." + this.getQueryName() );
		try {
			QueryParser qp = new QueryParser();
			qp.parseQuery(this);
		    reconcileTables();
			readTableDefinitions();
			// At this point there will be some attributes that cannot be fully reconciled because the nested queries have not been processed.
		    reconcileAttributes();
		    processNestedQueries(this);
			traverseQueryDefinitions(this);
		} catch (Exception ex) {
			Log.logError("QueryDefinition.crunchIt(): " + ex.getLocalizedMessage());
		}
	}
	/**
	 * Eventually this method should find that all query definitions are either without children or are marked final
	 * @param queryDefinition
	 */
	private void traverseQueryDefinitions(QueryDefinition queryDefinition) {
		Log.logProgress("QueryDefinition.traverseQueryDefinitions(): " + queryDefinition.getSchemaName() + "." + queryDefinition.getQueryName() );
		for (QueryDefinition qdChild: queryDefinition.children) {
			qdChild.reconcileAttributes();
//			qdChild.checkForNestedQueries();
//			qdChild.checkAllChildrenForFinal();
			traverseQueryDefinitions(qdChild);
		}
		queryDefinition.reconcileAttributes();	// After doing all the children, the parent should be ready to have all attributes reconciled
		Log.logProgress("QueryDefinition.traverseQueryDefinitions(): finished " + queryDefinition.getSchemaName() + "." + queryDefinition.getQueryName() );
	}

	public static QueryTables buildProvenance(QueryDefinition qd, String schemaName, String tableName, String attributeName) {
		// TODO: need to process alias's as well as attribute names
		Log.logProgress("QueryDefinition.buildProvenance(): " + qd.getSchemaName() + "." + qd.getQueryName() + ", attribute = " + schemaName + "." + tableName + "." + attributeName );
		QueryTables queryTablesProvenance = new QueryTables();
		try {
			QueryTable qt = qd.getQueryTables().findQueryAttribute(new QueryAttribute(schemaName, tableName, attributeName, null, null));
//			queryTablesProvenance.addQueryTable(qt);
//			Log.logProgress("QueryDefinition.buildProvenance(): first table added" + qt.getSchemaName() + "." + qt.getTableName());
			// if this is a table, we're done. If it's not a table, it's a query and we need to find that query in the children collection for this Query Definition
			while (true) {
				QueryDefinition qdTmp = qd.children.findQueryDefinitionBySchemaAndTableName(schemaName, tableName);
				if (qdTmp != null) {
					// We found it. Add it to the provenance
					queryTablesProvenance.addQueryTable(new QueryTable(qdTmp.getSchemaName(), qdTmp.getQueryName(), "", null));
					qd = qdTmp;
					QueryTable queryTable = qd.getQueryTables().findQueryAttribute(new QueryAttribute(schemaName, tableName, attributeName, null, null));
					schemaName = queryTable.getSchemaName();
					tableName = queryTable.getTableName();
				} else {
					// Add the last item to the provenance, which must be a table not a query. Use the schema name and table name that we didn't find in the children collection.
					queryTablesProvenance.addQueryTable(new QueryTable(schemaName, tableName, "", null));
					break;
				}
			}
		} catch (Exception ex) {
			Log.logError("QueryDefinition.buildProvenance(): " + ex.getLocalizedMessage() );
		}
		return queryTablesProvenance;
	}

	public HashMap<String, Schema>  getUniqueSchemaNames() {
		// A 'set' is maintained as a collection of unique items
		HashMap<String, Schema> schemaNames = new HashMap<String, Schema>();
		traverseForUniqueSchemaNames(this, schemaNames);
		return schemaNames;
	}
	private void traverseForUniqueSchemaNames(QueryDefinition qd, HashMap<String, Schema> schemas) {
		Log.logProgress("QueryDefinition.traverseForUniqueSchemsNames(): " + qd.getSchemaName() + "." + qd.getQueryName() );
		schemas.put(qd.getSchemaName(), new Schema(qd.getSchemaName()));
		for (QueryDefinition qdChild: qd.children) {
			traverseForUniqueSchemaNames(qdChild, schemas);
		}
		Log.logProgress("QueryDefinition.traverseForUniqueSchemsNames(): finished ");
	}

	/***
	 * Unique = SchemaName.TableName
	 * @return The list of unique SchemaName.TableName entries
	 */
	public HashMap<String, QueryTable> getUniqueTableNames() {
		// A 'set' is maintained as a collection of unique items
		HashMap<String, QueryTable> tableNames = new HashMap<String, QueryTable>();
		traverseForUniqueTableNames(this, tableNames);
		return tableNames;
	}
	private void traverseForUniqueTableNames(QueryDefinition qd, HashMap<String, QueryTable> queryTables) {
		Log.logProgress("QueryDefinition.traverseForUniqueTableNames(): " + qd.getSchemaName() + "." + qd.getQueryName() );
		for (QueryTable queryTable: qd.getQueryTables() ) {
			queryTable.setContainingQueryName(qd.getQueryName());
			queryTables.put(qd.getQueryName() + "." + queryTable.getSchemaName() + "." + queryTable.getTableName(), queryTable);
		}
		for (QueryDefinition qdChild: qd.children) {
			traverseForUniqueTableNames(qdChild, queryTables);
		}
		Log.logProgress("QueryDefinition.traverseForUniqueTableNames(): finished ");
	}

	public HashMap<String, QueryAttribute> getUniqueQueryAttributes() {
		// A 'set' is maintained as a collection of unique items
		HashMap<String, QueryAttribute> queryAttributes = new HashMap<String, QueryAttribute>();
		traverseForUniqueAttributes(this, queryAttributes);
		return queryAttributes;
	}
	private void traverseForUniqueAttributes(QueryDefinition qd, HashMap<String, QueryAttribute> attributes) {
		Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): " + qd.getSchemaName() + "." + qd.getQueryName() );
		for (QueryAttribute queryAttribute : qd.getQueryAttributes()) {
			Boolean isItAQuery;
			isItAQuery = QueryDefinition.isItAQuery(queryAttribute.getSchemaName(), queryAttribute.getTableName(), qd);
			Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): checking " + queryAttribute.getSchemaName() +  queryAttribute.getTableName() + "." + queryAttribute.getAttributeName() );
			// We only want attributes that are in the originating table, or are a constant because a query can define a constant.
			if (!isItAQuery || queryAttribute.isConstant())  {
				Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): adding " + queryAttribute.getSchemaName() + queryAttribute.getTableName() + "." + queryAttribute.getAttributeName() );
				attributes.put(queryAttribute.getAttributeName(), queryAttribute);
			}
		}
		for (QueryDefinition qdChild: qd.children) {
			traverseForUniqueAttributes(qdChild, attributes);
		}
		Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): finished ");
	}
	public String getHostName() {return hostName;}
	public void setHostName(String hostName) {this.hostName = hostName;}
	public String getloginName() {return loginName;}
	public void setloginName(String userName) {this.loginName = userName;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public QueryDefinition getParent() {return parent;}
	public void setParent(QueryDefinition parent) {this.parent = parent;}
	public Boolean getNoNestedQuerys() {return noNestedQuerys;}
	public void setNoNestedQuerys(Boolean noNestedQueries) {this.noNestedQuerys = noNestedQueries;}
	public Boolean getIsFinal() {return isFinal;}
	public void setIsFinal(Boolean isFinal) {this.isFinal = isFinal;}
}
