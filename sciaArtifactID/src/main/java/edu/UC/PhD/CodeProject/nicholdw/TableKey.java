package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.exception.DataTypeException;

/**
 * The unique identifier for a table in the Tables data structure
 * Typically it's DBInstanceName.SchemaName.TableName
 * @author nicomp
 *
 */
public class TableKey {
	private String dbInstanceName;
	private String schemaName;
	private String tableName;

	public TableKey(Table table) throws DataTypeException {
		setDBInstanceName(table.getDBInstanceName());
		setSchemaName(table.getSchemaName());
		setTableName(table.getTableName());
	}
	/**
	 * String representation of the TableKey object
	 */
	public String toString() {
		return dbInstanceName + "." + schemaName + "." + tableName;
	}

	/**
	 * Get the key.
	 * @return The key.
	 */
	public String getTableKey() {return (dbInstanceName + "." + schemaName + "." + tableName).toLowerCase();}

	public static String buildTableKey(Table table) {return (table.getDBInstanceName().toLowerCase() + "." + table.getSchemaName().toLowerCase() + "." + table.getTableName()).toLowerCase();}

	public String getDbInstanceName() {return dbInstanceName;}
	public void setDBInstanceName(String dbInstanceName) throws DataTypeException {
//		if (dbInstanceName.trim().length() > 0) {
			this.dbInstanceName = dbInstanceName.trim();
//		} else {
//			throw new DataTypeException("TableKey.setDBInstanceName");
//		}
	}

	public String getSchemaName() {return schemaName;}

	public void setSchemaName(String schemaName) throws DataTypeException {
		if (schemaName.trim().length() > 0) {
			this.schemaName = schemaName.trim();
		} else {
			throw new DataTypeException("TableKey.setTableName");
		}
	}

	public String getTableName() {return tableName;}

	public void setTableName(String tableName) throws DataTypeException {
		if (tableName.trim().length() > 0) {
			this.tableName = tableName.trim();
		} else {
			throw new DataTypeException("TableKey.setTableName");
		}
	}
}
