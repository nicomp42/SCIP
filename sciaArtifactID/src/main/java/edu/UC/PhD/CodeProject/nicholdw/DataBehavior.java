/**
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.Attribute.enumNullableCheck;
import lib.MySQL;
import lib.SQLUtils;

/**
 * Analyze the behavior and characteristics of the data
 * @author nicomp
 *
 */
public class DataBehavior {

	/**
	 * Annotate the list of attributes in a table that are nullable but have no null values in the data  
	 * @param tableName
	 * @param databaseName
	 * @return The list of attributes in the table
	 */
	Attributes checkForNulls(String tableName, String databaseName) {

		java.sql.Connection connection = null;
	    connection = new MySQL().connectToDatabase(databaseName);
		Table table = new Table(tableName, databaseName);
		table.setAttributes(Table.readAttributesFromTableDefinition(tableName, databaseName));
		return checkForNulls(table, connection);
	}
	/**
	 * Annotate the list of attributes in a table that are nullable and may or may not have null values in the data  
	 * @param table The table to be processed. The list of Attributes should already be populated
	 * @param connection An open connection to the database
	 * @return The list of attributes, but that list is already a member of the table object, so it's redundant.
	 */
	Attributes checkForNulls(Table table, java.sql.Connection connection) {
		System.out.println("DataBehavior.checkForNulls(): Processing table " + table.getTableName());
		Attributes attributes = table.getAttributes();
		// Step through the attributes that are nullable and see if any of them are never null. 
		for (Attribute attribute :  attributes) {
			if (attribute.isNullable()) {
				System.out.print("Attribute " + attribute.getAttributeName() + " can be null ");
				// The table design says it can be null: are any of the values null?
				long count = (long) SQLUtils.MyDLookup(attribute.getAttributeName(), table.getTableName(), attribute.getAttributeName() + " Is not null", "Count", "", connection);
				if (count > 0) {
					System.out.println("  and there are null values in the table.");
					attribute.setNullableCheck(enumNullableCheck.nullsFoundInTheData);
				} else {
					System.out.println("  but there are no null values in the table.");
					attribute.setNullableCheck(enumNullableCheck.noNullsFoundInTheData);
				}
			} else {
				System.out.println("Attribute " + attribute.getAttributeName() + " cannot be null.");
				attribute.setNullableCheck(enumNullableCheck.notNullable);
			}
		}
		return attributes;
	}
}
