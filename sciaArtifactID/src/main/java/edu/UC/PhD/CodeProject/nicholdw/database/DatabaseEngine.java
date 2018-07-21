package edu.UC.PhD.CodeProject.nicholdw.database;

import java.util.ArrayList;

/***
 * Base class for all the DB Engines we support
 * @author nicomp
 *
 */
public abstract class DatabaseEngine {
	public abstract String getSystemTablePrefix();
	/***
	 * Check for a system table 
	 * @param databaseTable The table to be examined
	 * @return True if databaseTable is a system table, false otherwise
	 */
	public abstract boolean isSystemTable(DatabaseTable databaseTable);

	public abstract ArrayList<DatabaseTable> getDatabaseSystemTables();

	public abstract void setDatabaseSystemTables(ArrayList<DatabaseTable> databaseSystemTables);
	
	public abstract boolean isAdHocQuery(String sql, StringBuilder sqlReduced);

	public abstract boolean isTable(String tableName);
	
	public abstract boolean isSystemTable(String tableName) throws DatabaseTableException;

}
