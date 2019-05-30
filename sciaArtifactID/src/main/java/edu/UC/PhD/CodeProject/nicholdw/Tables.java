package edu.UC.PhD.CodeProject.nicholdw;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import edu.UC.PhD.CodeProject.nicholdw.exception.DataTypeException;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;

	/***
	 * List of Table objects
	 * @author nicomp
	 *
	 */
	public class Tables implements Iterable<Table> {

		//private ArrayList<Table> tables;
		private HashMap<String, Table> tableHashMap;

		/**
		 * Constructor
		 */
		public Tables() {
			tableHashMap = new HashMap<String, Table>();
		}

		/**
		 * Retrieve the list of tables in this Index List
		 * @return A reference to the table list in the current object.
		 */
		public HashMap<String, Table> getTableHashMap ()
		{
			return tableHashMap;
		}

		public void addTable(Table table) throws DataTypeException {
			tableHashMap.put((new TableKey(table)).toString(), table);
		}

		public Iterator<Table> iterator() {
	        return tableHashMap.values().iterator();
	    }
		
		public void print(PrintStream out) {
			out.println(tableHashMap.size() + " tables in the hash map");
			for (Entry<String, Table> entry : tableHashMap.entrySet()) {
			    out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());
			}
		}
		/***
		 * Look up the table that originates the query attribute and set the AffectedByActionQuery flag to a value
		 * @param queryAttribute The Query Attribute to look up
		 * @param value true if affected by action query is true for this attribute
		 */
		public void setAffectedByActionQuery(QueryAttribute queryAttribute, Boolean value) {
			Log.logProgress("Tables.setAffectedByActionQuery(): looking for " + queryAttribute.getSchemaName() + "." + queryAttribute.getTableName() + "." + queryAttribute.getAttributeName());
			Boolean found = false;
			for (Entry<String, Table> entry: tableHashMap.entrySet()) {
				// Find the attribute in the table
				for (Attribute qa : entry.getValue().getAttributes()) {
					if (Config.getConfig().compareSchemaNames(entry.getValue().getSchemaName(), queryAttribute.getSchemaName()) && 
						Config.getConfig().compareTableNames(entry.getValue().getTableName(),   queryAttribute.getTableName())) {
						Attribute attribute;
						attribute = entry.getValue().findAttribute(queryAttribute.getAttributeName());
						if (attribute != null) {
							attribute.setAffectedByActionQuery(value);
							found = true;
							break;
						}
					}
				}
			} 
			if (!found) {
				Log.logError("QueryTables.setAffectedByActionQuery(): can't find table " + queryAttribute.getSchemaName() + "." + queryAttribute.getTableName());
			}
		}		
		
/*
		public static void main(String args[]) throws DataTypeException {
			Tables tables = new Tables();
			Table t1 =  new Table("table1", "schema1");
			Table t2 =  new Table("table2", "schema2");
			Table t3 =  new Table("table3", "schema3");
			Table t3a = new Table("table3", "schema3");		// Intentional duplicate
			Table tx =  new Table("tablex", "schemax");
			Table tx1 =  new Table("tablex", "schemax");	// Intentional duplicate
			tables.addTable(t1);
			tables.addTable(t2);
			tables.addTable(t3);
			tables.addTable(t3a);
			tables.addTable(tx);
			tables.addTable(tx);
			tables.addTable(tx1);
			tables.print(System.out);
		}
*/
	}
