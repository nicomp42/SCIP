package edu.UC.PhD.CodeProject.nicholdw.databaseEngine;

import java.util.ArrayList;

public class DatabaseTables {
	private ArrayList<DatabaseTable> databaseTables;

	public DatabaseTables() {
		databaseTables = new ArrayList<DatabaseTable>();
	}
	
	public void addDatabaseTable(DatabaseTable databaseTable) throws DatabaseTableException {
		databaseTables.add(new DatabaseTable(databaseTable));
	}
	public ArrayList<DatabaseTable> getDatabaseTables() {
		ArrayList<DatabaseTable> copy = new ArrayList<DatabaseTable>();
		try {
			for (DatabaseTable dt: databaseTables) {
				copy.add(new DatabaseTable(dt));
			}
		} catch (Exception ex) {}
		return copy;
	}

	public void setDatabaseTables(ArrayList<DatabaseTable> databaseSystemTables){
		databaseSystemTables.clear();
		try {
			for (DatabaseTable dt: databaseSystemTables) {
				databaseSystemTables.add(new DatabaseTable(dt));
			}	
		} catch (Exception ex) {}
	}
	/***
	 * Look for a table name in the object
	 * @param databaseTableName The name to look for
	 * @return True if table name is in the object, false otherwise
	 */
	public boolean containsTableName(String databaseTableName) {
		boolean result = false;
		String tmp = databaseTableName.toLowerCase();
		for (DatabaseTable dt: databaseTables) {
			if (dt.getName().compareTo(tmp) == 0) {
				result = true;
				break;
			}
		}	
		return result;
	}
	
	/***
	 * Look for a database table in the object
	 * @param databaseTable The object to look for
	 * @return True if databaseTable is in the object, false otherwise
	 */
	public boolean contains(DatabaseTable databaseTable) {
		boolean result = false;
		for (DatabaseTable dt: databaseTables) {
			if (dt.compare(databaseTable)) {
				result = true;
				break;
			}
		}	
		return result;
	}
}


