package edu.UC.PhD.CodeProject.nicholdw.database;

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
public class MySQLDatabase extends DatabaseEngine {

	private DatabaseTables databaseSystemTables;

	public MySQLDatabase() {
		databaseSystemTables = new DatabaseTables();
		// Add the system tables that will show up in the transaction log. We will ignore them.
		try {
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.user"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.tables"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.db"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("performance_schema"));
			databaseSystemTables.addDatabaseTable(new DatabaseTable("mysql.tables_priv"));
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
	public boolean isSystemTable(DatabaseTable databaseTable) {
		return false;
	}

	/***
	 * Check a SQL statement to see if it is an ad-hoc query
	 * @param sql The SQL to check
	 * @return True is sql contains an ad-hoc query, false otherwise
	 */
	@Override
	public boolean isAdHocQuery(String sql, StringBuilder sqlReduced, java.sql.Connection connection) {
		boolean status = false;
		String[] p = sql.split(" ");
		try {
			if (p.length >= 7) {
				if (p[3].toUpperCase().equals("SELECT")) {
					if (p[4].trim().substring(0, 1).equals("*")) {
						int i = 5;
						while (!p[i].toUpperCase().equals("FROM")) {i++;}
						i++;
						// Now we need to know if we are selecting from an existing query or something else
						String t = p[i];
						// If t is a query then this is not an ad-hoc query
						Log.logProgress("MySQLDatabase.isAdHocQuery(): Checking table \"" + t + "\" to see if we should keep it.");
						if (isTable(t, connection) && !isSystemTable(t)) {
							status = true;
						} else {
							status = false;
						}
					}
				}
				if (status == true && sqlReduced != null) {
					// Extract the SQL statement from the transaction log entry into sqlReduced
					sqlReduced.setLength(0);
					String space = "";
					for (int i = 3; i < p.length; i++) {
						sqlReduced.append(space + p[i]);
						space = " ";
					}
				}
			}
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
			             + Utils.QuoteMeSingle(parts[1])
			             + " AND " 
			             + "TABLE_SCHEMA = "
			             + Utils.QuoteMeSingle(parts[0])
			             + " AND " 
			             + " TABLE_TYPE = " + Utils.QuoteMeSingle("BASE TABLE");
			
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
}
