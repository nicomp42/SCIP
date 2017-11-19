package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.Iterator;

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

		public QueryTable matchQueryTable(String queryTableName) {
			QueryTable queryTable = null;
			for (QueryTable qt : queryTables) {
				if (qt.getTableName().trim().equals(queryTableName) || qt.getAliasName().trim().equals(queryTableName)) {
					queryTable = qt;
					break;
				}
			}
			return queryTable;
		}
		public int size() {return queryTables.size();}

		/**
		 * Find the Query Table in this QueryDefinition object that contains a particular query attribute name
		 * @param attributeName The attribute name
		 * @return The Query Table, or null if none.
		 */
		public QueryTable findQueryAttribute(QueryAttribute queryAttribute) {
			QueryTable qt = null;
			// Look through all the tables
			for (QueryTable queryTable: queryTables) {
				if (queryTable.getAttributeDataType(queryAttribute.getAttributeName()) != null) {
					qt = queryTable;
					break;
				}
			}
			// TODO: need to search by alias(es) as well
			return qt;
		}
	}


