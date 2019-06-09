package edu.UC.PhD.CodeProject.nicholdw.databaseEngine;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import lib.SQLUtils;

/***
 * The MySQL Database engine
 * @author nicomp
 *
 */
public class MySQLDatabaseEngine extends DatabaseEngine {

	private DatabaseTables databaseSystemTables;

	public MySQLDatabaseEngine() {
		databaseSystemTables = new DatabaseTables();
		// Add the system tables that will show up in the transaction log. We will ignore them.
		try {
			// Name will be converted to lower case when the DatabaseTable object is created 
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.user"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.tables"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.db"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.tables_priv"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.func"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("performance_schema.events_statements_current"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("performance_schema.events_waits_history_long"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("performance_schema.events_stages_history_long"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("information_schema.tables"));
		} catch (Exception ex) {}
	}

	
	public ArrayList<DatabaseTable> getDatabaseSystemTables() {
		return (ArrayList<DatabaseTable>) databaseSystemTables.getDatabaseTables();
	}

	public void setDatabaseSystemTables(ArrayList<DatabaseTable> databaseSystemTables) {
		this.databaseSystemTables.setDatabaseTables(databaseSystemTables);
	}

	@Override
	public String getSystemTablePrefix() {
		return "mysql.";
	}

	@Override
	public boolean isSystemTable(DatabaseTable databaseTable) {		// TODO something
		return false;
	}

	/***
	 * Check a SQL statement to see if it is an ad-hoc user query.
	 * @param sql The SQL to check
	 * @return True is sql contains an ad-hoc query without a system table, false otherwise
	 * A query that is not ad-hoc must look like this:  SELECT * FROM {named query}
	 *   everything else is ad-hoc.
	 */
	@Override
	public boolean isAdHocQuery(String sql, StringBuilder sqlReduced, java.sql.Connection connection) {
		boolean status = true;		// Assume it's ad-hoc unless we prove otherwise
		String[] p = sql.split(" ");
		try {
			if (p[2].toUpperCase().equals("QUERY") && (   p[3].toUpperCase().equals("SELECT") 
													   || p[3].toUpperCase().equals("INSERT")
													   || p[3].toUpperCase().equals("UPDATE")
													   || p[3].toUpperCase().equals("DELETE"))) {
/*			if (p[2].toUpperCase().equals("QUERY")) {
//			if (p.length == 7) {
				if (p[3].trim().toUpperCase().equals("SELECT")) {  
					if (p[4].trim().equals("*") && p[5].trim().toUpperCase().equals("FROM")) {
					
					// Now we need to know if we are selecting from an existing query or something else
					String t = p[6];
					// If t is a named query then this is not an ad-hoc query
					//Log.logProgress("MySQLDatabase.isAdHocQuery(): Checking table \"" + t + "\" to see if we should keep it.");
					status = true;
					if (!isTable(currentSchema + "." + t, connection)) {status = false;}
					if (isSystemTable(t)) {status = false;}			// We don't want queries with system tables, ad-hoc or not
				} else {
					status = false;
				} 
			} else {
				if (p.length > 7) {
					if (p[3].trim().toUpperCase().equals("SELECT")) {
						status = true;
					}
				}
			}	*/
				if (sqlReduced != null) {
					// Extract the SQL statement from the transaction log entry into sqlReduced
					sqlReduced.setLength(0);
					String space = "";
					for (int i = 3; i < p.length; i++) {
						sqlReduced.append(space + p[i]);
						space = " ";
					}
				}
			} else {status = false;}
		} catch (Exception ex) {
			Log.logError("MySQLDatabase.isAdHodQuery(): " + ex.getMessage());
		}
		return status;
	}
	@Override
	public boolean isTable(String tableName,  java.sql.Connection connection) {
		boolean status = false;
		String parts[] = tableName.split("\\.");
		try {
			String sql = "SELECT * FROM information_schema.tables WHERE TABLE_NAME = " 
			             + Utils.quoteMeSingle(parts[1].replace("`", ""))
			             + " AND " 
			             + "TABLE_SCHEMA = "
			             + Utils.quoteMeSingle(parts[0].replaceAll("`", ""))
			             + " AND " 
			             + " TABLE_TYPE = " + Utils.quoteMeSingle("BASE TABLE");
			
			java.sql.ResultSet resultSet = null;
			resultSet = SQLUtils.executeQuery(connection, sql);
			if (resultSet.first() == true) {status = true;}
		} catch (Exception e) {
			Log.logError("QueryUtils.isTable(" + tableName + "): " + e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	public boolean isSystemTable(String tableName) throws DatabaseTableException {
		boolean result = false;
		if (databaseSystemTables.contains(new DatabaseTable(tableName))) {
			result = true;
		}
		return result;
	}
	/***
	 * Does a SQL statement contain a reference to a system table? 
	 * @param sql The SQL statement to check
	 * @return True if the table immediately after the FROM keyword is a system table, false otherwise
	 */
	@Override
	public boolean checkForSystemTableInSQL(String sql) {
		boolean status = false;
		try {
			String myParts[] = sql.toString().split(" ");
			int i = 0;
			while (!myParts[i].trim().toUpperCase().equals("FROM")) {i++;}
			i++;
			status = databaseSystemTables.containsTableName(myParts[i]);
		} catch (Exception ex) {
			Log.logError("MySQLDatabase.checkForSystemTableInSQL(): " + ex.getLocalizedMessage());
		}
		return status;
	}

	@Override
	public String getSingleLineCommentDelimiter() {
		return "--";
	}
}
