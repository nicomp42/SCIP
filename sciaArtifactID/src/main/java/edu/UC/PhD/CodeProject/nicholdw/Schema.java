/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.dwQuery.QueryExcelExporter;
import edu.UC.PhD.CodeProject.nicholdw.exception.DataTypeException;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import lib.MySQL;

/**
 * Model a schema in mySQL
 * @author nicomp
 *
 */
public class Schema {
	private static final String qQueriesBySchemaName = "SELECT table_name, table_schema AS schema_name from information_schema.tables "
            										    + "WHERE table_schema = '#' "
            											+ "AND table_type = 'VIEW'";

	private static final String qSQLbyQueryName = "SELECT VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '#' AND TABLE_NAME = '@';";

	private String schemaName;
	private Tables tables;						// Tables in this schema
	private QueryDefinitions queryDefinitions;	// Queries in this schema
	private Attributes attributes;		// A big jumble of all the attributes in all the tables in the schema. See OperationalSchemaQueries.qAttributesbySchema
	private ForeignKeys foreignKeys;	// A big jumble of all the foreign keys in all the tables in the schema. See OperationalSchemaQueries.qForeignKeysbySchema
	private java.sql.Connection connection;

	/**
	 * Load all the query/view definitions in this schema into our collection
	 */
	public void loadQueryDefinitions(String hostName, String loginName, String password) {
		ArrayList<String> queryNames = readQueryNames(hostName, schemaName, loginName, password);
		QueryDefinition queryDefinition;
		for (String queryName: queryNames) {
			queryDefinition = readQueryDefinition(queryName, hostName, loginName, password);
			queryDefinitions.addQueryDefinition(queryDefinition);
		}
	}
	/**
	 * Generate a CSV file with the query attributes for consumption by Neo4j
	 * @param fileName The target file, with any necessary path. It will be appended to if it already exists.
	 */
	public void exportQueryDefinitionsToCSV(String fileName) {
		try {
			QueryParser queryParser = new QueryParser();
			for (QueryDefinition queryDefinition: queryDefinitions) {
				queryParser.parseQuery(queryDefinition);
				// Now we have little query bits we can append to the CSV file.
				// Everything goes in the same file in the Dwh step. Just keep appending.
				QueryExcelExporter.generateCsvFile(fileName, queryDefinition);
			}
		} catch (Exception ex) {
			Log.logError("Schema.exportQueryDefinitionsToCSV(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
	/**
	 * Look up the table name based on the alias. If the alias is already a table name the alias will be returned.
	 * @param tableList List of tables that are in the query
	 * @param alias alias to search for
	 * @return Table name corresponding to the alias or "" if no match
	 */
/*
	private String findTableBasedOnAlias(TTableList tableList, String alias) {
//		for (int n = 0; n < x.size(); n++) {
//			TTable t = x.getTable(n);
//			System.out.println(t.getFullNameWithAliasString());
//		}
		String tableName = "";
		TTable t;
		for (int i = 0; i < tableList.size(); i++) {
			t = tableList.getTable(i);
			if (t.getAliasName().equals(alias) || t.getName().equals(alias)) {tableName = t.getName();  break;}
		}
		return tableName;
	}
*/
/*
	public static void main(String[] args) {
//		lucerneParse("select `employee_id`,last_name,sal, sum(sal) as salSum    \n" + "from employees\n" + "where department_id = 90\n" + "group by employee_id having sal>10\n" + "order by last_name;");
//		JSqlParse("select sum(`f`.`count_rentals`) AS `sum(count_rentals)`,`c`.`customer_first_name` AS `customer_first_name`,`c`.`customer_last_name` AS `customer_last_name`,`c`.`customer_email` AS `customer_email` from (`sakila_dwh`.`fact_rental` `f` join `sakila_dwh`.`dim_customer` `c` on((`f`.`customer_key` = `c`.`customer_key`))) group by `c`.`customer_key` having (sum(`f`.`count_rentals`) > 10) order by sum(`f`.`count_rentals`)");
//		JSqlParse("SELECT a.*, b.*, sum(a.foo) FROM dim_film as a INNER JOIN fact_rental as b ON a.film_key = b.film_key");


		String host = "localhost";
		String userName = "root";
		String password = "Danger42";
		Schema schema = new Schema("sakila_dwh");
		ArrayList<String> queryNames = schema.readQueryNames(host, userName, password);
		System.out.println("Queries in " + schema.getSchemaName());
		for (String queryName: queryNames) {
			System.out.println(queryName);
			QueryDefinition queryDefinition;
			queryDefinition = schema.readQueryDefinition(queryName, host, userName, password);
			schema.queryDefinitions.addQueryDefinition(queryDefinition);
		}
		QueryDefinitions queryDefinitions = schema.getQueryDefinitions();
		System.out.println("Query definitions in " + schema.getSchemaName());
		for (QueryDefinition queryDefinition: queryDefinitions) {
			System.out.println(queryDefinition.getQueryName() + ": " + queryDefinition.getSql());
		}
		schema.exportQueryDefinitionsToCSV("c:\\temp\\foo");
	}
*/
	public Schema(String name) {
		setSchemaName(name);
		tables = new Tables();
		setAttributes(new Attributes());
		foreignKeys = new ForeignKeys();
		connection = null;
		setQueryDefinitions(new QueryDefinitions());
	}
	/**
	 * Read a query/view definition from the schema
	 * @param queryName The name of the query/view
	 * @return The QueryDefinition object, initialized from the query definition in the schems
	 */
	public QueryDefinition readQueryDefinition(String queryName, String hostName, String loginName, String password ) {
		QueryDefinition queryDefinition = new QueryDefinition(hostName, loginName, password, new QueryTypeSelect(), queryName, "", schemaName);
		ArrayList<String> queryNames = new ArrayList<String>();
		try {
			String sql = qSQLbyQueryName.replace("#", schemaName).replace("@",  queryName);
		    try {
		    	java.sql.ResultSet resultSet = MySQL.loadResultSet(hostName, loginName, password, sql);
				while (resultSet.next()) {
					// Extract the value from the attribute in the current row of the result set.
					queryDefinition.setSql(resultSet.getString(1));		// The argument to getString() is one-based, not zero-based
					queryNames.add(queryName);
				}
			} catch (Exception e) {Log.logError("Schema.loadQueryNames() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("Schema.loadQueryNames(): " + ex.getLocalizedMessage()); }
		return queryDefinition;
	}

	public int saveOperationalArtifactsToCSVFiles(String path, String hostName, String loginName, String password) {
		int count = 0;
		saveTablesToCSV(path, hostName, loginName, password);
		saveAttributesToCSV(path, hostName, loginName, password);
		saveForeignKeysToCSV(path, hostName, loginName, password);
		return count;
	}

	/***
	 * Write the tables to a csv that can be used to create the graph DB
	 * @return The number of tables written
	 */
	public int saveTablesToCSV(String path, String hostName, String loginName, String password) {
		int count = 0;
	    java.sql.ResultSet resultSet = null;
		// Read the schema names from the mySQL server and populate the list
		try {
			String sql = OperationalSchemaQueries.qTablesBySchemaName.replace("#", schemaName);
		    try {
		    	resultSet = MySQL.loadResultSet(hostName, loginName, password, sql);
		    	Utils.ExportMySQLResultSetToCSV(resultSet, path + "/tables.csv");
			} catch (Exception e) {Log.logError("saveTablesToCSV() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("saveTablesToCSV(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return count;
	}
	/***
	 * Write the attributes to a csv that can be used to create the graph DB
	 * @return The number of tables written
	 */
	public int saveAttributesToCSV(String path, String hostName, String loginName, String password) {
		int count = 0;		// ToDo make this value do something
	    java.sql.ResultSet resultSet = null;
		// Read the schema names from the mySQL server and populate the list
		try {
			String sql = OperationalSchemaQueries.qAttributesbySchema.replace("#s", schemaName);
		    try {
		    	resultSet = MySQL.loadResultSet(hostName, loginName, password, sql);
		    	Utils.ExportMySQLResultSetToCSV(resultSet, path + "/attributes.csv");
			} catch (Exception e) {Log.logError("saveAttributesToCSV() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("saveAttributesToCSV(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return count;
	}
	/***
	 * Write the attributes to a csv that can be used to create the graph DB
	 * @return The number of tables written
	 */
	public int saveForeignKeysToCSV(String path, String hostName, String loginName, String password) {
		int count = 0;		// ToDo make this value do something
	    java.sql.ResultSet resultSet = null;
		// Read the schema names from the mySQL server and populate the list
		try {
			String sql = OperationalSchemaQueries.qForeignKeysbySchema.replace("#s", schemaName);
		    try {
		    	resultSet = MySQL.loadResultSet(hostName, loginName, password, sql);
		    	Utils.ExportMySQLResultSetToCSV(resultSet, path + "/foreignKeys.csv");
			} catch (Exception e) {Log.logError("saveForeignKeysToCSV() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("saveForeignKeysToCSV(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return count;
	}

	public int loadForeignKeys(String hostName, String loginName, String password) {
		// Read the attribute names and isPrimaryKey values from the mySQL server and populate the Attribute list
		int count = 0;
		try {
			connection = new MySQL().connectToDatabase(hostName, Config.getConfig().getInformationSchemaName(), loginName, password);
			String sql = OperationalSchemaQueries.qForeignKeysbySchema.replace("#s", schemaName);

			java.sql.PreparedStatement preparedStatement = null;
		    try {
				preparedStatement = connection.prepareStatement(sql);
			} catch (SQLException e) {
				Log.logError("Schema.loadForeignKeys() : " + e.getLocalizedMessage());
			}
		    java.sql.ResultSet resultSet = null;
		    try {
				resultSet = preparedStatement.executeQuery();
			} catch (SQLException e) {Log.logError("Schema.loadForeignKeys() : " + e.getLocalizedMessage());}
		    try {
				while (resultSet.next()) {
					// Extract the value from the attribute in the current row of the result set.
					String tableName = resultSet.getString(1);
					String attributeName = resultSet.getString(2);		// The argument to getString() is one-based, not zero-based
					String foreignKeyName = resultSet.getString(3);
					String referencedTableName = resultSet.getString(4);
					String referencedAttributeName = resultSet.getString(5);
					foreignKeys.addForeignKey(new ForeignKey(tableName, attributeName, referencedTableName, foreignKeyName, referencedAttributeName));

					count++;
				}
			} catch (Exception e) {Log.logError("Schema.loadForeignKeys(): " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("Schema.loadForeignKeys(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return count;
	}
	public int loadAttributes(String hostName, String loginName, String password) {
		// Read the attribute names and isPrimaryKey values from the mySQL server and populate the Attribute list
		int count = 0;
		try {
			connection = new MySQL().connectToDatabase(hostName, Config.getConfig().getInformationSchemaName(), loginName, password);
			String sql = OperationalSchemaQueries.qAttributesbySchema.replace("#s", schemaName);

			java.sql.PreparedStatement preparedStatement = null;
		    try {
				preparedStatement = connection.prepareStatement(sql);
			} catch (SQLException e) {
				Log.logError("Schema.loadAttributes() : " + e.getLocalizedMessage());
			}
		    java.sql.ResultSet resultSet = null;
		    try {
				resultSet = preparedStatement.executeQuery();
			} catch (SQLException e) {Log.logError("Schema.loadAttributes() : " + e.getLocalizedMessage());}
		    try {
				while (resultSet.next()) {
					// Extract the value from the attribute in the current row of the result set.
					String attributeName = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
					String tableName = resultSet.getString(2);
					String type = resultSet.getString(3);
					Boolean isPrimaryKey = resultSet.getString(4).equals("YES") == true? true:false;
					attributes.addAttribute(new Attribute(attributeName, tableName, isPrimaryKey, type, null, null, null, null, 0, (Aliases)null));
					count++;
				}
			} catch (Exception e) {Log.logError("Schema.loadAttributes(): " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("Schema.loadAttributes(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return count;
	}
	public int loadTables(String hostName, String loginName, String password) {
		// Read the schema names from the mySQL server and populate the list
		int count = 0;
		try {
			String sql = OperationalSchemaQueries.qTablesBySchemaName.replace("#", schemaName);
		    try {
		    	java.sql.ResultSet resultSet = MySQL.loadResultSet(hostName, loginName, password, sql);
				while (resultSet.next()) {
					// Extract the value from the attribute in the current row of the result set.
					String tableName = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
					tables.addTable(new Table(tableName, schemaName));
					count++;
				}
			} catch (Exception e) {Log.logError("Schema.loadTables() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("Schema.loadTables(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return count;
	}

	public Tables getTables() {return tables;}
	public void addTable(Table table) {
		try {
			tables.addTable(table);
		} catch (DataTypeException e) {
			Log.logError("Schema.addTable() : " + e.getLocalizedMessage());
		}
	}

	public String getSchemaName() {return schemaName;}
	
	public void setSchemaName(String schemaName) {this.schemaName = formatSchemaName(schemaName);}
	
	public static String formatSchemaName(String schemaName) {
		if (schemaName == null) {
			return "``";
		} else {
			if (schemaName.trim().startsWith("`") && schemaName.trim().endsWith("`")) {
				return schemaName.trim();
			} else {
				return Utils.QuoteMeBack(schemaName.trim());
			}
		}		
	}
	public Attributes getAttributes() {return attributes;}
	public void setAttributes(Attributes attributes) {this.attributes = attributes;}
	public ForeignKeys getForeignKeys() {return foreignKeys;}
	public void setForeignKeys(ForeignKeys foreignKeys) {this.foreignKeys = foreignKeys;}

	public QueryDefinitions getQueryDefinitions() {return queryDefinitions;}
	public void setQueryDefinitions(QueryDefinitions queryDefinitions) {this.queryDefinitions = queryDefinitions;}

	/**
	 * Read the query/view names from the mySQL server and populate the list
	 * @param hostName Name of the mySQL Host
	 * @param loginName Login name
	 * @param password password
	 * @return List of query/view names in the schema
	 */
	public static ArrayList<String> readQueryNames(String hostName, String schemaName, String loginName, String password) {
		ArrayList<String> queryNames = new ArrayList<String>();
		try {
			String sql = qQueriesBySchemaName.replace("#", schemaName);
		    try {
		    	java.sql.ResultSet resultSet = MySQL.loadResultSet(hostName, loginName, password, sql);
				while (resultSet.next()) {
					// Extract the value from the attribute in the current row of the result set.
					String queryName = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
					queryNames.add(queryName);
				}
			} catch (Exception e) {Log.logError("Schema.loadQueryNames() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("Schema.loadQueryNames(): " + ex.getLocalizedMessage()); }
		return queryNames;
	}
/*
	private static void testParse(String sql) {
        TGSqlParser sqlparser = new TGSqlParser(EDbVendor.dbvmysql);
        sqlparser.sqltext = sql;
        sqlparser.parse();

        for (int k = 0; k < sqlparser.sqlstatements.size(); k++) {
        	try {
		        TSelectSqlStatement select = (TSelectSqlStatement)sqlparser.sqlstatements.get(k);
		        System.out.println("sql statement = " + select);
		        for (int j = 0; j < select.tables.size(); j++) {
		        	TTable table;
		        	table = select.tables.getTable(j);
		        	TObjectName o;
		        	System.out.println("Table = " + table.getFullName());
		        	for(int i=0;i<table.getLinkedColumns().size();i++){
		        		o = table.getLinkedColumns().getObjectName(i);
		        		System.out.println(o.toString()+"\t\t\tlocation:"+o.getLocation());
		        	}
		        }
        	} catch (Exception ex) {}
        }
	}
*/
}
