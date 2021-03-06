/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.TableAttribute;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

	/***
	 * List of Table objects
	 * @author nicomp
	 *
	 */
	public class QueryTables implements Iterable<QueryTable>, java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6018476677517462793L;
		private ArrayList<QueryTable> queryTables;

		/**
		 * Constructor
		 */
		public QueryTables() {
			queryTables = new ArrayList<QueryTable>();
		}
		/***
		 * Look up the table that originates the query attribute and set the AffectedByActionQuery flag to a value
		 * @param queryAttribute The Query Attribute to look up
		 * @param value true if affected by action query is true for this attribute
		 */
		public void setAffectedByActionQuery(QueryAttribute queryAttribute, Boolean value) {
			Log.logProgress("QueryTables.setAffectedByActionQuery(): looking for " + queryAttribute.getSchemaName() + "." + queryAttribute.getContainerName() + "." + queryAttribute.getAttributeName());
			QueryTable queryTable = lookupBySchemaAndTable(queryAttribute.getSchemaName(), queryAttribute.getContainerName());
			if (queryTable != null) {
				// Find the attribute in the table
				for (TableAttribute qa : queryTable.getTableAttributes()) {
					if (Config.getConfig().compareSchemaNames(queryTable.getSchemaName(), queryAttribute.getSchemaName()) && 
						Config.getConfig().compareTableNames(queryTable.getTableName(),   queryAttribute.getContainerName()) &&
						Config.getConfig().compareAttributeNames(qa.getAttributeName(),   queryAttribute.getAttributeName())) {
						qa.setAffectedByActionQuery(value);
						break;
					}
				}
			} else {
				Log.logError("QueryTables.setAffectedByActionQuery(): can't find table " + queryAttribute.getSchemaName() + "." + queryAttribute.getContainerName());
			}
		}
		
		
		public QueryTable lookupBySchemaAndTable(String schemaName, String tableName) {
			QueryTable queryTableResult = null;
			Log.logProgress("QueryTables.lookUpBySchemaAndTable(): Looking for " + schemaName + "." + tableName);
			try {
				// Look through all the tables
				for (QueryTable queryTable: queryTables) {
					// Check the schema name first or skip it if the attribute has no schema name
					if (Config.getConfig().compareSchemaNames(queryTable.getSchemaName(), schemaName) == true) {
						// Check the table name for a match or skip if it the attribute has no table name
						if (Config.getConfig().compareTableNames(queryTable.getTableName(), tableName) == true) {
								queryTableResult = queryTable;
								break;
						}
					}
				}
			} catch (Exception ex) {
				Log.logError("QueryTables.lookUpBySchemaAndTable(): " + ex.getLocalizedMessage());
			}
			Log.logProgress("QueryTables.lookUpBySchemaAndTable(): " + ((queryTableResult != null) ? "Table found: " + queryTableResult.toString(): "Table not found"));
			return queryTableResult;
		}
		
		/**
		 * Retrieve the list of tables in this Index List
		 * @return A reference to the table list in the current object.
		 */
		public ArrayList<QueryTable> getTables ()
		{
			return queryTables;
		}
		/***
		 * Add a query table
		 * @param queryTableNew The QueryTable to look for
		 * @return True if the table was added, false if it already exists 
		 */
		public Boolean addQueryTable(QueryTable queryTableNew) {
			Boolean queryTableAdded = false;
				QueryTable queryTableExists = null;
				queryTableExists = lookupBySchemaAndTable(queryTableNew.getSchemaName(), queryTableNew.getTableName());
				if (queryTableExists == null) {
					queryTables.add(queryTableNew);
					queryTableAdded = false;
				} else {
					queryTableAdded = true;
				}
				return queryTableAdded;
		}

		@Override
		public Iterator<QueryTable> iterator() {
			Iterator<QueryTable> iprof = queryTables.iterator();
	        return iprof;
	    }
/*
 * 		Not used and let's not start using it because it's logically incorrect anyway
		public QueryTable matchQueryTable(String queryTableName) {
			QueryTable queryTable = null;
			for (QueryTable qt : queryTables) {
				if (Config.compareTableNames(qt.getTableName(), queryTableName) == true || Config.compareAliasNames(qt.getAliasName(), queryTableName) == true) {
					queryTable = qt;
					break;
				}
			}
			return queryTable;
		}
*/
		public int size() {return queryTables.size();}

		/**
		 * Find the Query Table in this QueryDefinition object that provides a particular query attribute name
		 * @param attributeName The attribute name
		 * @return The Query Table, or null if none.
		 */
		public QueryTable findQueryOrTableContainingAttribute(QueryAttribute queryAttribute) {
			Log.logProgress("QueryTables.findQueryOrTableContainingAttribute(): Looking for " + queryAttribute.toString());
			QueryTable queryTableResult = null;
			try {
				boolean foundTable = false;
				// Look through all the tables
				for (QueryTable queryTable: queryTables) {
					// Check the schema name first or skip it if the attribute has no schema name
					if (Utils.isBlank(queryAttribute.getSchemaName()) || (Config.getConfig().compareSchemaNames(queryTable.getSchemaName(), queryAttribute.getSchemaName()) == true)) {
						// Check the table name for a match or skip if it the attribute has no table name
						if (Utils.isBlank(queryAttribute.getContainerName()) || (Config.getConfig().compareTableNames(queryTable.getTableName(), queryAttribute.getContainerName()) == true)) {
							if (queryTable.findAttribute(queryAttribute.getAttributeName()) != null) {
								Log.logProgress("QueryTables.findQueryOrTableContainingAttribute(): found the attribute in table " + queryTable.toString());
								queryTableResult = queryTable;
								foundTable = true;
								break;
							} else {
								Log.logProgress("QueryTables.findQueryOrTableContainingAttribute(): table name match but there's no data type. Table = " + queryTable.toString());
							}
						}
						if (foundTable == false) {
							// No match in the table names? Perhaps the table name in the queryAttribute is an alias
							for (AliasNameClassOLD aliasNameClass : queryTable.getAliasNames()) {
								if (Config.getConfig().compareAliasNames(aliasNameClass.getAliasName(), queryAttribute.getContainerName()) == true) {
									if (queryTable.findAttribute(queryAttribute.getAttributeName()) != null) {
										Log.logProgress("QueryTables.findQueryOrTableContainingAttribute(): found the attribute in table using alias " + queryTable.toString());
										queryTableResult = queryTable;
										foundTable = true;
										break;
									} else {Log.logProgress("QueryTables.findQueryOrTableContainingAttribute(): table alias match, but there's no attribute with matching name. Table = " + queryTable.toString());}
								}
							}
						}
						if (foundTable == true) {break;}					
					}
				}
			} catch (Exception ex) {Log.logError("QueryTables.findQueryOrTableContainingAttribute(): " + ex.getLocalizedMessage());}
			Log.logProgress("QueryTables.findQueryOrTableContainingAttribute(): " + ((queryTableResult != null) ? "Table found: " + queryTableResult.toString(): "Table not found"));
			return queryTableResult;
		}
	}


