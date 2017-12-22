package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

	/***
	 * List of Table objects
	 * @author nicomp
	 *
	 */
	public class QueryTables implements Iterable<QueryTable> {

		private ArrayList<QueryTable> queryTables;

		/**
		 * Constructor
		 */
		public QueryTables() {
			queryTables = new ArrayList<QueryTable>();
		}

		/**
		 * Retrieve the list of tables in this Index List
		 * @return A reference to the table list in the current object.
		 */
		public ArrayList<QueryTable> getTables ()
		{
			return queryTables;
		}

		public void addQueryTable(QueryTable queryTable) {
			queryTables.add(queryTable);
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
		 * Find the Query Table in this QueryDefinition object that contains a particular query attribute name
		 * @param attributeName The attribute name
		 * @return The Query Table, or null if none.
		 */
		public QueryTable findQueryAttribute(QueryAttribute queryAttribute) {
			Log.logProgress("QueryTables.findQueryAttribute(): Looking for " + queryAttribute.toString());
			QueryTable qt = null;
			boolean foundTable = false;
			// Look through all the tables
			for (QueryTable queryTable: queryTables) {
				// Check the schema name first
				if (Config.compareSchemaNames(queryTable.getSchemaName(), queryAttribute.getSchemaName()) == true) {
					// Check the table name for a match
					if (Config.compareTableNames(queryTable.getTableName(), queryAttribute.getTableName()) == true) {
						if (queryTable.getAttributeDataType(queryAttribute.getAttributeName()) != null) {
							qt = queryTable;
							break;
						}
						// No match in the table names? Perhaps the table name in the queryAttribute is an alias
						for (AliasNameClass aliasNameClass : queryTable.getAliasNames()) {
							if (Config.compareAliasNames(aliasNameClass.getAliasName(), queryAttribute.getTableName()) == true) {
								if (queryTable.getAttributeDataType(queryAttribute.getAttributeName()) != null) {
									qt = queryTable;
									foundTable = true;
									break;
								}
							}
						}
						if (foundTable == true) {break;}
					}
				}
			}
			Log.logProgress("QueryTables.findQueryAttribute(): " + ((qt != null) ? "Table found: " + qt.toString(): "Table not found"));
			return qt;
		}
	}


