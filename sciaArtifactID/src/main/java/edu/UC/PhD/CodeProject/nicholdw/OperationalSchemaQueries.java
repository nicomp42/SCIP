/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;


/**
 * MySQL queries to extract metadata
 * @author nicomp
 *
 */
public class OperationalSchemaQueries {

	public static final String qSchemas = "SELECT schema_name FROM information_schema.SCHEMATA order by SCHEMA_NAME;";
			/*
			"SELECT table_name, table_schema AS schema_name from information_schema.tables "
            + "WHERE table_schema = '#schemaName' "
            + "AND table_type = 'BASE_TABLE'";
*/


	// Figure 5.2. Read table names for the schema called #s
	public static final String qTablesBySchemaName = "SELECT table_name, table_schema AS schema_name from information_schema.tables "
			                                       + "WHERE table_schema = '#' "
			                                       + "AND table_type = 'BASE TABLE'";


	// Figure 5.3. Read attributes names for the schema called #s
	public static final String qAttributesbySchema = "SELECT c.column_name, c.table_name, c.data_type,"
												   + " CASE WHEN c.column_key = 'PRI' THEN 'YES' ELSE 'NO' END as is_Key, c.table_schema"
												   + " FROM information_schema.columns c"
												   + " WHERE c.table_schema='#s' AND"
												   + " c.table_name IN "
												   + " (SELECT table_name FROM information_schema.TABLES "
												   + "  WHERE table_type LIKE 'BASE TABLE' AND table_schema LIKE '#s')";


	// Figure 5.4. Read attributes names for the schema called #s
	public static final String qForeignKeysbySchema = "SELECT table_name as relation_name, column_name as attr_name, constraint_name as constraint_name, referenced_table_name as referenced_relation_name, referenced_column_name as referenced_column_name "
												   + " FROM information_schema.key_column_usage"
												   + " WHERE referenced_table_schema = '#s'";

	// A tool to see if a query exists in a schema
	public static final String qQuerybyQueryNameAndSchema = "SELECT table_name "
			   + " FROM information_schema.tables"
			   + " WHERE table_schema = '#s' and table_name='#t' and table_type='VIEW'";


}
