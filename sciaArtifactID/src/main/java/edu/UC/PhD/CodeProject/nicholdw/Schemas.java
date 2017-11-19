package edu.UC.PhD.CodeProject.nicholdw;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import lib.MySQL;

/***
 * List of Schema objects
 * @author nicomp
 *
 */
public class Schemas implements Iterable<Schema> {

	private ArrayList<Schema> schemas;

	/**
	 * Get a schema by name
	 * @param schemaName The name to look up
	 * @return The schema that matches the name
	 */
	public Schema getSchemaByName(String schemaName) {
		Schema schema = null;
		for (Schema tmpSchema: schemas ) {
			if (tmpSchema.getSchemaName().toLowerCase().equals(schemaName.toLowerCase())) {schema = tmpSchema; break;}
		}
		return schema;
	}
	/**
	 * Get a copy of all the schema names in this object
	 * @return The ArrayList of schema names. Just the names, nothing but the names
	 */
	public ArrayList<String> getSchemaNames() {
		ArrayList<String> schemaNames = new ArrayList<String>();
		for (Schema schema: schemas ) {
			schemaNames.add(schema.getSchemaName());
		}
		return schemaNames;
	}

	public static ArrayList<String> loadSchemaNamesFromDatabaseServer() {
		return loadSchemaNamesFromDatabaseServer(Config.getConfig().getMySQLDefaultHostname(), Config.getConfig().getMySQLDefaultLoginName(), Config.getConfig().getMySQLDefaultPassword());
	}
	public static ArrayList<String> loadSchemaNamesFromDatabaseServer(String hostName, String loginName, String password) {
		ArrayList<String> schemaNames = new ArrayList<String>();
		// Read the schema names from the mySQL server and populate the list
		java.sql.Connection connection = null;
		//int count = 0;
		try {
			connection = new MySQL().connectToDatabase(hostName, Config.getConfig().getInformationSchemaName(), loginName, password);
			String sql = OperationalSchemaQueries.qSchemas;
		    java.sql.PreparedStatement preparedStatement = null;
		    try {
				preparedStatement = connection.prepareStatement(sql);
			} catch (SQLException e) {
				System.out.println("loadSchemasFromDatabaseServer() : " + e.getLocalizedMessage());
			}
		    java.sql.ResultSet resultSet = null;
		    try {
				resultSet = preparedStatement.executeQuery();
			} catch (SQLException e) {System.out.println("loadSchemasFromDatabaseServer() : " + e.getLocalizedMessage());}
		    try {
				while (resultSet.next()) {
					// Extract the value from the attribute in the current row of the result set.
					String schemaName = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
					schemaNames.add(schemaName);
					//Config.getConfig().getSchemas().addSchema(new Schema(schemaName));
					//schemaNames.add(schemaName);
					//count++;
				}
			} catch (Exception e) {Log.logError("loadSchemasFromDatabaseServer() : " + e.getLocalizedMessage());}
		} catch (Exception ex) {Log.logError("loadSchemasFromDatabaseServer(): " + ex.getLocalizedMessage()); }
		try {connection.close();} catch (Exception ex) {}
		return schemaNames;
	}

	/**
	 * Constructor
	 */
	public Schemas() {
		schemas = new ArrayList<Schema>();
	}

	/**
	 * Retrieve the list of Schemas in this Index List
	 * @return A reference to the Schema list in the current object.
	 */
	public ArrayList<Schema> getSchemas ()
	{
		return schemas;
	}

	public void addSchema(Schema schema) {
		schemas.add(schema);
	}

	@Override
	public Iterator<Schema> iterator() {
		Iterator<Schema> iprof = schemas.iterator();
        return iprof;
    }
}




