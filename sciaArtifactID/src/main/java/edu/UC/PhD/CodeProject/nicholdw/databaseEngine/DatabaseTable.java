package edu.UC.PhD.CodeProject.nicholdw.databaseEngine;

/***
 * A table in a database
 * @author nicomp
 *
 */
public class DatabaseTable {
	private String name;

	/***
	 * Copy Constructor
	 * @param databaseTable The object to be copied
	 * @throws DatabaseTableException If the object can't be copied
	 */
	DatabaseTable(DatabaseTable databaseTable) throws DatabaseTableException {
		this.setName(databaseTable.getName());
	}
	public DatabaseTable(String name) throws DatabaseTableException {
		setName(name.toLowerCase());
	}
	public String getName() {
		return name;
	}

	public void setName(String name) throws DatabaseTableException {
		if (name != null && name.trim().length() != 0) {
			this.name = name;
		} else {
			throw new DatabaseTableException("DatabaseTable.setName(): name cannot be empty");
		}
	}
	/***
	 * Compare contents of a databaseTable with the current object
	 * @param databaseTable The thing to compare with
	 * @return True if they are equal, false otherwise.
	 */
	public boolean compare(DatabaseTable databaseTable) {
		boolean result = false;
		if (getName().equals(databaseTable.getName())) {
			result = true;
		}
		return result;
	}
}
