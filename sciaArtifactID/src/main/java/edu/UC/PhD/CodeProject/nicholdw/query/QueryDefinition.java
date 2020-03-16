/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import edu.UC.PhD.CodeProject.nicholdw.Attribute;
import edu.UC.PhD.CodeProject.nicholdw.Attributes;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.OperationalSchemaQueries;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.exception.NotImplementedException;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute.ATTRIBUTE_DISPOSITION;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryType;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeRenameTable;
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
public class QueryDefinition implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818619155041768791L;
	private String sql;
	private String queryName;
	private QueryTables queryTables;	// The tables that this query draws from, if any.
	private QueryAttributes queryAttributes;
	private String schemaName;
	private QueryType queryType;
	private String hostName, loginName, password;
	private QueryDefinitions children;	// The queries that this query draws from, if any.
	private QueryDefinition parent;
	private Boolean noNestedQuerys;
	private Boolean isFinal;
	private CompoundAliases compoundAliases;
	private QueryFunctions queryFunctions;
	private QueryVariables queryVariables;
	private QueryTerminalSymbols queryTerminalSymbols;
	// This is irrelevant in MySQL stored views. Attribute lists are frozen when the view is created. See https://dev.mysql.com/doc/refman/8.0/en/create-view.html
	// However, a wildcard can appear in an ad-hoc query that shows up in the transaction log!
	private Boolean selectIsWildcard;		 
	public QueryDefinition(String hostName, String loginName, String password, QueryType queryType, 
			               String queryName, String sql, String schemaName) {
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
		setQueryFunctions(new QueryFunctions());
		setQueryVariables(new QueryVariables());
		setQueryTerminalSymbols(new QueryTerminalSymbols());		
		setSelectIsWildcard(false);
	}
	public QueryDefinitions getChildren() {return children;}
//	public void initWildcards() {selectIsWildcard = false;}
//	public void pushWildcardFlag(Boolean isAsterisk) {wildcards.push(isAsterisk);}
//	public Boolean popWildcardFlag() {return wildcards.pop();}
	public CompoundAliases getCompoundAliases() {return compoundAliases;}
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
	public String getQueryAttributeDataType(QueryAttribute queryAttribute) {
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
		} catch (Exception ex) {
			Log.logError("QueryDefinition.getQueryAttributeType(" + queryAttribute.toString() + "): " + ex.getLocalizedMessage());
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
		boolean matchFound = false;
		for (QueryTable queryTable : qd.getQueryTables()) {
			matchFound = false;
			if (Config.getConfig().compareTableNames(queryTable.getTableName(), tableName) == true && Config.getConfig().compareSchemaNames(queryTable.getSchemaName(), schemaName) == true) {
				matchFound = true;
				result = queryTable;
				break;
			}
			for (AliasNameClassOLD aliasName : queryTable.getAliasNames()) {
				if (Config.getConfig().compareAliasNames(aliasName.getAliasName(),  tableName) == true) {
					matchFound = true;
					result = queryTable;
					break;
				}
			}
			if (matchFound == true) {
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
			if (querySchemas.contains(queryTable.getSchemaName()) == null) {
				querySchemas.addSchema(new QuerySchema(Utils.removeBackQuotes(queryTable.getSchemaName())));
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
	public String toString() {return schemaName + Config.getConfig().getAttributePartsDelimiter() + queryName + ", " + queryType.getQueryType();}
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
				Log.logError("QueryDefinition.readSQLFromDatabaseServerQueryDefinition(): " + ex.getLocalizedMessage());
			}
		} else {
			String query = "SELECT view_definition FROM information_schema.VIEWS where table_schema = " +  MySQL.wrapStringInSingleQuotes(Utils.removeBackQuotes(schemaName)) + " AND table_name=" + MySQL.wrapStringInSingleQuotes(Utils.removeBackQuotes(queryName));
			java.sql.Connection connection = null;
			try {
				connection = new MySQL().connectToDatabase(hostName, schemaName, loginName, password);
			    java.sql.PreparedStatement preparedStatement = null;
			    try {
					preparedStatement = connection.prepareStatement(query);
				} catch (SQLException e) {
					Log.logError("QueryDefinition.readSQLFromDatabaseServerQueryDefinition() : " + e.getLocalizedMessage());
				}
			    java.sql.ResultSet resultSet = null;
			    try {
					resultSet = preparedStatement.executeQuery();
				} catch (SQLException e) {System.out.println("loadSchemasFromDatabaseServer() : " + e.getLocalizedMessage());}
			    try {
			    	resultSet.next();					// Move to the first record
					sql = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
					if (Config.getConfig().getCompensateForWeakParser()) {
						sql = sql.replace("CHARSET UTF8", "");
						sql = sql.replace("charset utf8", "");
					}

				} catch (Exception e) {
					Log.logError("QueryDefinition.readSQLFromDatabaseServerQueryDefinition() : " + e.getLocalizedMessage());}
			} catch (Exception ex) {
				Log.logError("QueryDefinition.readSQLFromDatabaseServerQueryDefinition(): " + ex.getLocalizedMessage()); }
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
	 * After the query has been parsed, this information must be available because we are assuming the query was properly formed from the get-go.
	 */
	public void reconcileAttributes() {
		// Taken from a SQL statement, an attribute can have:
		// 1. No table or schema, 
		// 2. A table but no schema, or
		// 3. A table and a schema.
		Log.logProgress("QueryDefinition.reconcileAttributes(): processing the query " + this.getSchemaName() + "." + this.getQueryName());
		try {
			Log.logProgress("QueryDefinition.reconcileAttributes(): Checking for wildcard in attribute list " + this.getSchemaName() + "." + this.getQueryName());
			boolean b;
			b = this.getSelectIsWildcard();
			if (b) {
				Log.logProgress("QueryDefinition.reconcileAttributes(): Wildcard found");
				// we need all the attributes in all the tables in this select statement
				for (QueryTable qt: this.getQueryTables()) {
					Attributes as = new Attributes();
					Log.logProgress("QueryDefinition.reconcileAttributes(): Wildcard found, copying attributes from " + qt.getSchemaName() + "." + qt.getTableName());
					as = Table.readAttributesFromTableDefinition(qt.getTableName(), qt.getSchemaName());
					this.getQueryAttributes().addTableAttributes(qt.getSchemaName(), as);
				}
			}
		} catch (Exception ex) {
			Log.logError("QueryDefinition.reconcileAttributes(): " + ex.getLocalizedMessage());
		}
		try {
			// Make sure every query attribute has a table/query that it belongs to
			for (QueryAttribute qa : this.getQueryAttributes()) {
				Log.logProgress("QueryDefinition.reconcileAttributes(): processing the attribute " + qa.toString());
//				Log.logProgress("QueryDefinition.reconcileAttributes(): attribute = " + qa.toString());
				if (!Utils.isBlank(qa.getTableName()) && !Utils.isBlank(qa.getSchemaName())) {
					Log.logProgress("QueryDefinition.reconcileArtifacts(); Attribute has a schema and a table. Checking to see it it's a table alias.");
					// However, the table name could be an alias name. We need to use the actual table name rather than the table alias.
					// found = false
					// for all the table names in the query definition
					// 	if attribute.table name == query definition.table name
					//		found = true
					//		break out of all loops
					//	for all the aliases in the current table
					//		if attribute.table name == table.alias name
					//			attribute.table alias name = attribute.table name
					//			attribute.table name = table.table name
					//			found = true
					//			break out of all loops
					//	if found == true
					//		we should always be here. The attribute table name must always be in the list of (table names & table alias names)
					//	else
					//		something bad happened. We should never be here
					boolean found = false;
					for (QueryTable qt: queryTables) {
						if (Config.getConfig().compareTableNames(qt.getSchemaName(), qa.getSchemaName())) {
							if (Config.getConfig().compareTableNames(qt.getTableName(), qa.getTableName())) {
								found = true;
								Log.logProgress("QueryDefinition.reconcileArtifacts(); Attribute has a table name that is actually a table name (" + qa.getTableName() + ").");
								break;
							}
							// Check the list of aliases for this table
							for (AliasNameClassOLD an: qt.getAliasNames()) {
								if (Config.getConfig().compareAliasNames(an.getAliasName(), qa.getTableName())) {
									Log.logProgress("QueryDefinition.reconcileArtifacts(); Attribute has a table name that is an alias (" + qa.getTableName() + "), changing to " + qt.getTableName() + ".");
									qa.setTableAliasName(qa.getTableName());
									qa.setTableName(qt.getTableName());
									found = true;
									break;
								}
							}
							if (found) {break;}
						}
					}
					if (!found) {
						Log.logError("QueryDefinition.reconcileArtifacts(): Very bad: attribute has a schema and a table BUT table not found in the query definition (" + qa.getTableName() + ")");
					}
				} else  {
					QueryTable qt = null;
					qt = this.getQueryTables().findQueryOrTableContainingAttribute(qa);	// This must work, else the query is not properly formed or there are > 0 nested queries to search
					if (qt != null) {
						Log.logProgress("QueryDefinition.reconcileArtifacts(): found the table: " + qt.toString());
						qa.setTableName(qt.getTableName());
						qa.setSchemaName(qt.getSchemaName());
					} else {
						Log.logError("QueryDefinition.reconcileAttributes(); Attribute " + qa.getAttributeName() + " not found in any tables. Even checked aliases. Looking in child queries");
						for (QueryDefinition qdChild : children) {
							qt = qdChild.getQueryTables().findQueryOrTableContainingAttribute(qa);	// This must work, else the query is not properly formed
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
						Log.logError("QueryDefinition.reconcileAttributes(); Attribute " + qa.toString() + " not found in any tables or nested queries. Very bad. Program results will probably be incomplete/incorrect.");
					}
				}
			}
		} catch (Exception ex) {Log.logError("QueryDefinition.reconcileAttributes(): " + ex.getLocalizedMessage());}

	}
	/**
	 * Make sure tables/queries have schema names and figure out if it's a table or a query
	 * After the query has been parsed, this information must be available because the query is assumed to be properly formed.
	 */
	public void reconcileTables() {
		// Make sure every table/query has a schema
		try {
			for (QueryTable qt: this.getQueryTables()) {
				if (qt.getSchemaName() == null || qt.getSchemaName().equals("")) {
					qt.setSchemaName(this.getSchemaName());
				}
				// Figure out if it's a table or a query
				qt.setIsQuery(isItAQuery(qt.getSchemaName(), qt.getTableName(), this));
			}
		} catch (Exception ex) {
			Log.logError("QueryDefinition.reconcileTables(): " + ex.getLocalizedMessage());
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
		String sql = OperationalSchemaQueries.qQuerybyQueryNameAndSchema.replace("#s", Utils.removeBackQuotes(schemaName)).replace("#t", Utils.removeBackQuotes(queryName));
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
	public void mergeQueryDefinitions(QueryDefinition qd) throws Exception {
		Log.logProgress("QueryDefinition.mergeQueryDefinitions(): Merging " + this.getSchemaName() + "." + this.getQueryName() + " and " + qd.getSchemaName() + "." + qd.getQueryName());
		throw new NotImplementedException("QueryDefinition/mergeQueryDefinition(): not implemented");
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
			reconcileAliases(this);
			processSpecialtyQuerys(this);
		} catch (Exception ex) {
			Log.logError("QueryDefinition.crunchIt(): " + ex.getLocalizedMessage());
		}
	}
	private static void processSpecialtyQuerys(QueryDefinition qd) {
		if (qd.getQueryType() instanceof QueryTypeDropTable) {
			// We need to load all the attributes into the query attribute collection from the table(s) we are dropping
			for (QueryTable qt: qd.getQueryTables()) {
				for (Attribute ta: qt.getAttributes()) {
//					public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNameClassOLD aliasName, QueryClause queryClause, String tableAliasName, ATTRIBUTE_DISPOSITION attributeDisposition) {
					qd.queryAttributes.addAttribute(new QueryAttribute(qt.getSchemaName(), ta.getTableName(), ta.getAttributeName(), new AliasNameClassOLD(""), new QueryClauseUndefined(), null));
				}
			}
		}
		if (qd.getQueryType() instanceof QueryTypeRenameTable) {
			// We need to load all the attributes into the query attribute collection from the table(s) we are renaming
			for (QueryTable qt: qd.getQueryTables()) {
				for (Attribute ta: qt.getAttributes()) {
//					public QueryAttribute(String schemaName, String tableName, String attributeName, AliasNameClassOLD aliasName, QueryClause queryClause, String tableAliasName, ATTRIBUTE_DISPOSITION attributeDisposition) {
					qd.queryAttributes.addAttribute(new QueryAttribute(qt.getSchemaName(), ta.getTableName(), ta.getAttributeName(), new AliasNameClassOLD(""), new QueryClauseUndefined(), null));
				}
			}
		}
	}
	/***
	 * Make sure each attribute in the query definition and in its' children has an alias. 
	 * @param qd The Query Definition to be processed. Children of qd will also be processed.
	 */
	public static void reconcileAliases(QueryDefinition qd) {
		for (QueryAttribute queryAttribute : qd.getQueryAttributes()) {
			if (queryAttribute.getAliasNames() == null || queryAttribute.getAliasNames().size() == 0) {
				queryAttribute.getAliasNames().addAliasName(new AliasNameClassOLD(queryAttribute.getUniqueAttributeName()));
			}
		}
		for (QueryDefinition queryDefinition : qd.children) {
			reconcileAliases(queryDefinition);
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
	/**
	 * Schema Name/Table Name/Attribute name must uniquely identify an attribute in a table or query
	 * @param qd Query Definition that we are processing
	 * @param fcn Attribute Name
	 * @return The ordered list of tables that define the provenance of the attribute
	 */
	public static QueryTables buildProvenance(QueryDefinition qd, FullColumnName targetFCN, QueryTables queryTablesProvenance) {
		FullColumnName currentFCN = targetFCN;
//		String currentAttributeName = null;
		QueryDefinition qdTmp = qd;
		Log.logProgress("QueryDefinition.buildProvenance(): query = " + qd.getSchemaName() + "." + qd.getQueryName() + ", attribute = " + currentFCN.toString() );
//		QueryTables queryTablesProvenance = new QueryTables();
		boolean keepGoing = true;
		QueryTable queryTableProvenance = null;
		QueryAttribute queryAttributeProvenance = null;
		QueryTable qt = null;
		while (keepGoing) {
			try {
				if (qdTmp != null) {	// We found the underlying query that provides the attribute

					// We were given the full column name or part of it so we need to get that table from the table collection of this query definition
					QueryAttribute queryAttribute = qdTmp.getQueryAttributes().findAttribute(currentFCN);
					if (queryAttribute != null) {
						Log.logProgress("QueryDefinition.buildProvenance(): query attribute with alias `" + currentFCN.toString() + "` = " + queryAttribute.toString());
						currentFCN = queryAttribute.getFullColumnName();
						qt = qdTmp.getQueryTables().lookupBySchemaAndTable(queryAttribute.getSchemaName(), queryAttribute.getTableName());
						// If this is a table, we're done. If it's not a table, it's a query and we need to find that query in the children collection for this Query Definition
						queryTableProvenance = null;
						queryAttributeProvenance = null;

						Log.logProgress("QueryDefinition.buildProvenance(): found next table = " + qt.getSchemaName() + "." + qt.getTableName());
						queryTableProvenance = new QueryTable(qdTmp.getSchemaName(), qdTmp.getQueryName(), new AliasNameClassOLD(""), null);
						queryAttributeProvenance = qdTmp.getQueryAttributes().findAttribute(currentFCN);
						queryTableProvenance.setQueryAttributeProvenance(queryAttributeProvenance);
						queryTablesProvenance.addQueryTable(queryTableProvenance);
						Log.logProgress("QueryDefinition.buildProvenance(): table added to provenance: " + qdTmp.getSchemaName() + "." + qdTmp.getQueryName());
						Log.logProgress("QueryDefinition.buildProvenance(): searching for table/query with attribute: " + queryAttributeProvenance.getSchemaName() + "." +  queryAttributeProvenance.getTableName() + "." +  queryAttributeProvenance.getAttributeName() );
						qdTmp = qdTmp.children.findQueryDefinitionBySchemaAndTableName(queryAttribute.getSchemaName(), queryAttribute.getTableName());
//						currentAliasName = currentAttributeName;		// We need the attribute name used in this query
					} else {
						// If queryAttribute is null, we need to look in the list of compoundAliases
						CompoundAlias compoundAlias = qdTmp.getCompoundAliases().findCompoundAlias(currentFCN.getAttributeName());	// It's an alias, it can't be anything else
						if (compoundAlias != null) {
							// Now we have a new problem because we need to compute provenance for all the attributes in the compound attribute.
							for (FullColumnName fcn : compoundAlias.getFullColumnNames()) {
								queryTableProvenance = new QueryTable(fcn.getSchemaName(), fcn.getTableName(), new AliasNameClassOLD(""), null);
								queryAttributeProvenance = qdTmp.getQueryAttributes().findAttribute(fcn);
								queryTableProvenance.setQueryAttributeProvenance(queryAttributeProvenance);
								queryTablesProvenance.addQueryTable(queryTableProvenance);
								Log.logProgress("QueryDefinition.buildProvenance(): Calling buildProvenance() again for query definition " + qdTmp.toString() + ", attribute alias/name = " + fcn.toString());
								System.out.print(".");
//								buildProvenance(qdTmp, fcn.getName(), queryTablesProvenance);
							}
						}
						// qdTmp needs to change or else we will never leave this loop. 
						// TODO: At this point are we really done with the provenance computation?
						qdTmp = null;
						keepGoing = false;
					}
				} else {
					// Add the last item to the provenance, which must be a table not a query. Use the schema name and table name that we didn't find in the children collection.
					queryTableProvenance = new QueryTable(qt.getSchemaName(), qt.getTableName(), new AliasNameClassOLD(""), null );
					// ToDo: This is hinkey: A table attribute dressed up as a query attribute so it will fit into the queryTableProvenance data structure
					queryAttributeProvenance = new QueryAttribute(qt.getSchemaName(), qt.getTableName(), currentFCN.getAttributeName(), new AliasNameClassOLD((String) null), null, "");
					queryTableProvenance.setQueryAttributeProvenance(queryAttributeProvenance);
					Log.logProgress("QueryDefinition.buildProvenance(): adding last item to provenance: " + queryAttributeProvenance.getSchemaName() + "." +  queryAttributeProvenance.getTableName() + "." +  queryAttributeProvenance.getAttributeName() );
					queryTablesProvenance.addQueryTable(queryTableProvenance);
					keepGoing = false;
				}
			} catch (Exception ex) {
				Log.logError("QueryDefinition.buildProvenance(): " + ex.getLocalizedMessage() );
				// We need to do something here, else we may loop forever, throwing and catching errors
				keepGoing = false;
			}
		}	// while(keepGoing)
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
	public HashMap<String, QueryAttribute> getUniqueQueryAttributes(boolean tablesOnly) {
		// A 'set' is maintained as a collection of unique items
		HashMap<String, QueryAttribute> queryAttributes = new HashMap<String, QueryAttribute>();
		traverseForUniqueAttributes(this, queryAttributes, tablesOnly);
		return queryAttributes;
	}
	private void traverseForUniqueAttributes(QueryDefinition qd, HashMap<String, QueryAttribute> attributes, boolean tablesOnly) {
		Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): " + qd.getSchemaName() + "." + qd.getQueryName() );
		for (QueryAttribute queryAttribute : qd.getQueryAttributes()) {
			Boolean isItAQuery;
			isItAQuery = QueryDefinition.isItAQuery(queryAttribute.getSchemaName(), queryAttribute.getTableName(), qd);
			Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): checking " + queryAttribute.getSchemaName() +  queryAttribute.getTableName() + "." + queryAttribute.getAttributeName() );
			// We only want attributes that are in the originating table, or are a constant because a query can define a constant.
			if (!isItAQuery || !tablesOnly || queryAttribute.isConstant())  {
				Log.logProgress("QueryDefinition.traverseForUniqueAttributes(): adding " + queryAttribute.getSchemaName() + "." + queryAttribute.getTableName() + "." + queryAttribute.getAttributeName() );
				attributes.put(queryAttribute.getSchemaName() + "." + queryAttribute.getTableName() + "." + queryAttribute.getAttributeName(), queryAttribute);
			}
		}
		for (QueryDefinition qdChild: qd.children) {
			traverseForUniqueAttributes(qdChild, attributes, tablesOnly);
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
	
	/**
	 * Tell us everything you know about the query
	 * @param os Where the info should go
	 * @param printTerminalSymbols true if terminal symbols should be printed, false otherwise
	 */
	public void print(PrintStream os, boolean printTerminalSymbols) {
		os.println("Query Name: " + this.getQueryName());
		os.println(" Attributes:");
		for (QueryAttribute qa: this.getQueryAttributes()) {
			os.println("\t" + qa.toString());
		}
		os.println(" Compound Aliases:");
		for (CompoundAlias ca: this.getCompoundAliases()) {
			os.println("\t" + ca.toString());
		}
		os.println(" Tables/Views:");
		for (QueryTable qt: this.getQueryTables()) {
			os.println("\t" + qt.toString());
		}
		if (printTerminalSymbols) {			// This will be long
			os.println(" Terminal Symbols:");
			int counter = 1;
			for (QueryTerminalSymbol qts: queryTerminalSymbols) {
				os.print("\t" + qts.toString());
				if (counter++ % 5 == 0) {os.println("");}
			}
		}
	}
	public QueryFunctions getQueryFunctions() {return queryFunctions;}
	public void setQueryFunctions(QueryFunctions queryFunctions) {this.queryFunctions = queryFunctions;}
	public QueryVariables getQueryVariables() {return queryVariables;}
	public void setQueryVariables(QueryVariables queryVariables) {this.queryVariables = queryVariables;}
	public QueryTerminalSymbols getQueryTerminalSymbols() {
		return queryTerminalSymbols;
	}
	public void setQueryTerminalSymbols(QueryTerminalSymbols queryTerminalSymbols) {this.queryTerminalSymbols = queryTerminalSymbols;}
/*	public Stack<Boolean> getWildcards() {
		return wildcards;
	}
	public void setWildcards(Stack<Boolean> wildcards) {
		this.wildcards = wildcards;
	} */
	public Boolean getSelectIsWildcard() {
		return selectIsWildcard;
	}
	public void setSelectIsWildcard(Boolean selectIsWildcard) {
		this.selectIsWildcard = selectIsWildcard;
	}
}
