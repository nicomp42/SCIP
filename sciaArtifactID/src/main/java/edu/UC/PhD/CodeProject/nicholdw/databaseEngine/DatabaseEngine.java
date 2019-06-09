package edu.UC.PhD.CodeProject.nicholdw.databaseEngine;

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
	
//	public abstract boolean isAdHocQuery(String sql, StringBuilder sqlReduced);
	public abstract boolean isAdHocQuery(String sql, StringBuilder sqlReduced, java.sql.Connection connection);

	public abstract boolean isTable(String tableName, java.sql.Connection connection);
	
	public abstract boolean isSystemTable(String tableName) throws DatabaseTableException;
	/**
	 * Provide the single line comment delimiter for the SQL dialect
	 * @return The delimiter
	 */
	public abstract String getSingleLineCommentDelimiter();
	
	public abstract boolean checkForSystemTableInSQL(String SQL);
}
