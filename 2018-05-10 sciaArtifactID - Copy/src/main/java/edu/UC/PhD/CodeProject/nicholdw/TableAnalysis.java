/*
 * Analyze the structure of a table.
 * Reference: http://www.java2s.com/Tutorial/MySQL/CatalogMySQL.htm
 *
 *
 * Add a composite key: ALTER TABLE `tstore` ADD UNIQUE(`Store`,`Address1`)
 * Add a foreign key constraint: http://www.w3schools.com/sql/sql_foreignkey.asp
 *    Example: "ALTER TABLE tStore ADD FOREIGN KEY (ManagerID) REFERENCES tEmpl(EmplID)"
 *
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
*/
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import lib.MySQL;
import lib.SQLUtils;

public class TableAnalysis {
	/**
	 * Detect and analyze a schema containing a many-to-many relationship. This doesn't do anything because it's leftover from the old PhD
	 */
	public void DetectAndAnalyzeManyToManyRelationship(Schema schema) {
		Tables tables = schema.getTables();
		Table intermediateTable = null;
		Indexes intermediateTableIndexes = null;
//		for (Table table : tables.iterator()) {
		HashMap<String, Table> tableHashMap;
		tableHashMap = tables.getTableHashMap();
		for (Entry<String, Table> entry : tableHashMap.entrySet()) {
			Indexes indexes = new Indexes();
			if (isIntermediateTable(entry.getValue().getSchemaName(), entry.getValue().getTableName(), indexes)) {
				intermediateTable = entry.getValue();
				intermediateTableIndexes = indexes;
				for (Index index : intermediateTableIndexes) {
					System.out.println("DetectAndAnalyzeManyToManyRelationship: The index " + index.getName() + " in table " + intermediateTable.getTableName() + " is composed of all foreign keys.");
					for (Attribute attribute : index.getAttributes()) {
					}
				}
			}
		}
	}
	/**
	 * Design Pattern 002: A table that is the intermediate table in a many-to-many relationship
	 * @param tableName
	 */
	public void DetectDesignPattern002 (String databaseName, String tableName) {
		Indexes indexes= new Indexes();
		if (isIntermediateTable(databaseName, tableName, indexes)) {
			for (Index index : indexes) {
				System.out.println("DetectDesignPattern002: The index " + index.getName() + "in table " + tableName + " is composed of all foreign keys.");
			}
		}
	}
	/**
	 * Analyze a table to see if it contains a unique index that is composed of foreign keys
	 * @param table
	 * @return
	 */
	public boolean isIntermediateTable(String databaseName, String tableName, Indexes indexesFound) {
		boolean status = false;
		Attributes attributes = Table.readAttributesFromTableDefinition(tableName, databaseName);				// Build the Attribute List for the table
		ProcessReferentialConstraints(databaseName, tableName, attributes);		// Look through the Attribute List for any referential constraints
		Indexes indexes = ProcessIndices(databaseName, tableName, attributes);
		// Look for an index consisting of two or more fields that are all foreign keys
		for (Index index : indexes) {
			Attributes idxAttributes = index.getAttributes();
			if (index.isUnique() &&  idxAttributes.size() > 1) {
				// Are all the attributes foreign keys?
				boolean allForeignKeys = true;
				for (Attribute attribute : idxAttributes) {
					if (!attribute.isForeignKey() ) {
						allForeignKeys = false;
					}
				}
				if (allForeignKeys) {		// Is the index composed of all foreign keys?
					status = true;
					indexesFound.addIndex(index);
				}
			}
		}
		return status;
	}

	private Indexes ProcessIndices(String databaseName, String tableName, Attributes attributes) {
		Indexes indexes = new Indexes();
		java.sql.Connection connection = null;
	    java.sql.PreparedStatement indexPreparedStatement = null;
	    java.sql.ResultSet indexResultSet = null;

	    java.sql.PreparedStatement tablePreparedStatement = null;
	    java.sql.ResultSet tableResultSet = null;

	    java.sql.PreparedStatement fieldPreparedStatement = null;
	    java.sql.ResultSet fieldResultSet = null;

	    connection = new MySQL().connectToDatabase("information_schema");
		String tableSQL = "SELECT TABLE_ID FROM `INNODB_SYS_TABLES` WHERE name = " + SQLUtils.DoubleQuoteMe(databaseName + "/" + tableName) ;
		String indexSQL = null;
		try {
			tablePreparedStatement = connection.prepareStatement(tableSQL);
			tableResultSet = tablePreparedStatement.executeQuery();
			int tableID = 0;
			// Look up the table ID in INNODB_SYS_TABLES
			if (tableResultSet.next()) {
				tableID = Integer.valueOf(tableResultSet.getString(1));
			}
			// Look up the index IDs in INNODB_SYS_INDEXES
			indexSQL = "SELECT INDEX_ID, NAME, TYPE FROM `INNODB_SYS_INDEXES` WHERE TABLE_ID = " + tableID;
			// Type: 1 = Non-Unique, 2 = Unique, 3 = Primary
			indexPreparedStatement = connection.prepareStatement(indexSQL);
			indexResultSet = indexPreparedStatement.executeQuery();
			int indexID = 0;
			int indexType = 0;
			String indexName = null;
			while(indexResultSet.next()) {
				indexID = Integer.valueOf(indexResultSet.getString(1));
				indexName = indexResultSet.getString(2);
				indexType = Integer.valueOf(indexResultSet.getString(3));
				System.out.print("Found index for table " + tableName + " called " + indexName + " with attributes ");
				// Look up the columns corresponding to this index
				String fieldSQL = "SELECT * FROM `INNODB_SYS_FIELDS` WHERE INDEX_ID = " + indexID + " ORDER BY POS";
				fieldPreparedStatement = connection.prepareStatement(fieldSQL);
				fieldResultSet = fieldPreparedStatement.executeQuery();
				Index index = new Index(indexName, (indexType == 2?true:false), (indexType == 3?true:false));		// Name, unique, primary
				while(fieldResultSet.next()) {
					// Now we have a table, an index, and a field in that index. Woo hoo
					index.AddAttribute(attributes.findAttributeByName(fieldResultSet.getString(2).trim()));
					System.out.print(" " + fieldResultSet.getString(2).trim());
				}
				indexes.addIndex(index);
				System.out.println();
			}
		} catch (Exception ex) {
			Log.logError("TableAnalysis.ProcessIndices() " + ex.getMessage());
		}
		return indexes;
	}

	public void ProcessReferentialConstraints(String databaseName, String tableName, Attributes attributeList) {
		// SELECT * FROM `REFERENTIAL_CONSTRAINTS`
		java.sql.Connection connection = null;
	    java.sql.ResultSet referentialConstraintsResultSet = null;
	    java.sql.ResultSet tableConstraintsResultSet = null;
	    java.sql.ResultSet foreignColsResultSet = null;
	    java.sql.ResultSet foreignKeysResultSet = null;
	    java.sql.PreparedStatement referentialConstraintsPreparedStatement = null;
	    java.sql.PreparedStatement tableConstraintsPreparedStatement = null;
	    java.sql.PreparedStatement foreignColsPreparedStatement = null;
	    java.sql.PreparedStatement foreignKeysPreparedStatement = null;
		String tableConstraintsSQL = "SELECT * FROM `TABLE_CONSTRAINTS` WHERE Table_Name = " + SQLUtils.DoubleQuoteMe(tableName) + " AND CONSTRAINT_TYPE = " + SQLUtils.DoubleQuoteMe("FOREIGN KEY") + ";" ;
		String foreignColsSQL;
		String foreignKeysSQL;

		//String sql = "SELECT * FROM `REFERENTIAL_CONSTRAINTS` WHERE Table_Name = " + Utils.DoubleQuoteMe(tableName) +  " OR REFERENCED_TABLE_NAME = " + Utils.DoubleQuoteMe(tableName) + ";" ;
		String referentialConstraintsSQL = "SELECT * FROM `REFERENTIAL_CONSTRAINTS` WHERE Table_Name = " + SQLUtils.DoubleQuoteMe(tableName) +  ";" ;
		connection = new MySQL().connectToDatabase("information_schema");
		try {
			String attributeName, referencedTableName, referencedAttributeName, foreignKeyName;
			attributeName = null; referencedTableName = null; referencedAttributeName = null; foreignKeyName = null;
			referentialConstraintsPreparedStatement = connection.prepareStatement(referentialConstraintsSQL);
			referentialConstraintsResultSet = referentialConstraintsPreparedStatement.executeQuery();

			tableConstraintsPreparedStatement = connection.prepareStatement(tableConstraintsSQL);
			tableConstraintsResultSet = tableConstraintsPreparedStatement.executeQuery();
			while (referentialConstraintsResultSet.next()) {
				//try {
				//	System.out.println("All attributes from Referential Constraints Result Set: ");
				//	for (int i = 1; i < 100; i++) {
				//		String tmp = referentialConstraintsResultSet.getString(i);
				//		System.out.println(i + ": " + tmp);
				//	}
				//} catch (Exception ex) {} finally {System.out.println("================");}

				Config.getConfig().Debug("Referential_Constraints: Table name = " + referentialConstraintsResultSet.getString(10) + "\n");
				Config.getConfig().Debug("Referential_Constraints: Referenced Table name = " + referentialConstraintsResultSet.getString(11) + "\n");
				referencedTableName = referentialConstraintsResultSet.getString(11);

				String constraintName = referentialConstraintsResultSet.getString(3);
				foreignKeyName = referentialConstraintsResultSet.getString(3);
				Config.getConfig().Debug("Referential_Constraints: Constraint name = " + constraintName + "\n");

				// Process the records in the Foreign Column Constraint table for the constraint name we just found
				// http://dev.mysql.com/doc/refman/5.7/en/innodb-sys-foreign-cols-table.html

				foreignKeysSQL = "SELECT * FROM `INNODB_SYS_FOREIGN` WHERE ID = " + SQLUtils.DoubleQuoteMe(databaseName + "/" + constraintName);	// The names of the foreign keys and the associated tables
				foreignKeysPreparedStatement = connection.prepareStatement(foreignKeysSQL);
				foreignKeysResultSet = foreignKeysPreparedStatement.executeQuery();
				while (foreignKeysResultSet.next()) {

					//try {
					//	System.out.println("All attributes from Foreign Keys Result Set: ");
					//	for (int i = 1; i < 100; i++) {
					//		String tmp = foreignKeysResultSet.getString(i);
					//		System.out.println(i + ": " + tmp);
					//	}
					//} catch (Exception ex) {} finally {System.out.println("================");}

					Config.getConfig().Debug("Foreign Keys: ID = " + foreignKeysResultSet.getString(1) + "\n");
					Config.getConfig().Debug("Foreign Keys: FOR_COL_TABLE_NAME = " + foreignKeysResultSet.getString(2) + "\n");
					Config.getConfig().Debug("Foreign Keys: REF_COL_TABLE_NAME = " + foreignKeysResultSet.getString(3) + "\n");
					//referencedTableName = foreignKeysResultSet.getString(3);
					Config.getConfig().Debug("Foreign Keys: N_COLS = " + foreignKeysResultSet.getString(4) + "\n");
					Config.getConfig().Debug("Foreign Keys: Type = " + foreignKeysResultSet.getString(5) + "\n");	// Bit Flags
					// Type			ON DELETE	ON UPDATE
					//=====================================
					//	0 			RESTRICT	RESTRICT
					//	4			RESTRICT	CASCADE
					//	8			RESTRICT	SET NULL
				}

				foreignColsSQL = "SELECT * FROM `INNODB_SYS_FOREIGN_COLS` WHERE ID = " + SQLUtils.DoubleQuoteMe(databaseName + "/" + constraintName);	// The attributes used in the foreign key relationship
				foreignColsPreparedStatement = connection.prepareStatement(foreignColsSQL);
				foreignColsResultSet = foreignColsPreparedStatement.executeQuery();
				while (foreignColsResultSet.next()) {

					//try {
					//	System.out.println("All attributes from Foreign Columns Result Set: ");
					//	for (int i = 1; i < 100; i++) {
					//		String tmp = foreignColsResultSet.getString(i);
					//		System.out.println(i + ": " + tmp);
					//	}
					//} catch (Exception ex) {} finally {System.out.println("================");}

					Config.getConfig().Debug("Foreign Cols: ID = " + foreignColsResultSet.getString(1) + "\n");
					Config.getConfig().Debug("Foreign Cols: FOR_COL_NAME = " + foreignColsResultSet.getString(2) + "\n");
					attributeName = foreignColsResultSet.getString(2);
					Config.getConfig().Debug("Foreign Cols: REF_COL_NAME = " + foreignColsResultSet.getString(3) + "\n");
					referencedAttributeName = foreignColsResultSet.getString(3);
					Config.getConfig().Debug("Foreign Cols: POS = " + foreignColsResultSet.getString(4) + "\n");
				}
				if (attributeName != null && referencedTableName != null && referencedAttributeName != null) {
					if (attributeList != null) {
						attributeList.addForeignKeyRef(attributeName, referencedTableName, foreignKeyName, referencedAttributeName);
					}
				}
			}
			while (tableConstraintsResultSet.next()) {
				//try {
				//	System.out.println("All attributes from Table Constraints Result Set: ");
				//	for (int i = 1; i < 100; i++) {
				//		String tmp = tableConstraintsResultSet.getString(i);
				//		System.out.println(i + ": " + tmp);
				//	}
				//} catch (Exception ex) {} finally {System.out.println("================");}
				Log.logProgress("TableAnalysis.ProcessReferentialConstraints() Constraint name = " + tableConstraintsResultSet.getString(3) + "\n");
			}
		} catch (SQLException e) {
			Log.logProgress("TableAnalysis.ProcessReferentialConstraints(): " + e.getLocalizedMessage());
		}
	}
}
