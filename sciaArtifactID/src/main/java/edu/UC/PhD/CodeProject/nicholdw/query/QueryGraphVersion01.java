/*
 * Queries to execute after the CSV files have been created:
 * 1. Import the root query node
 * LOAD CSV WITH HEADERS FROM "FILE:///foo_RootQuery.csv" AS CSVLine CREATE (q:RootQuery { ID:CSVLine.SchemaName + "." + CSVLine.QueryName, SchemaName:CSVLine.SchemaName, QueryName:CSVLine.QueryName })
 *
 * 2. Import the tables
 * LOAD CSV WITH HEADERS FROM "FILE:///foo_Tables.csv" AS CSVLine CREATE (T:Table  { ID:CSVLine.SchemaName+"."+CSVLine.TableName, SchemaName:CSVLine.SchemaName, TableName:CSVLine.TableName, ContainingQuery:CSVLine.ContainingQuery})
 *
 * 3. Create relationships from the root query to the tables contained in the root query
 * MATCH (t:Table), (q:RootQuery) where (t.ContainingQueryName=q.QueryName )  CREATE (q)-[r:Contains]->(t);
 *
 * 3. Import attributes
 * LOAD CSV WITH HEADERS FROM "FILE:///foo_Attributes.csv" AS CSVLine CREATE (A:Attribute  { ID:CSVLine.SchemaName+"."+CSVLine.TableName+"."+CSVLine.AttributeName, SchemaName:CSVLine.SchemaName, TableName:CSVLine.TableName, AttributeName:CSVLine.AttributeName, DataType:CSVLine.DataType})
 *
 * 4.Create relationships from tables to the attributes contained in them
 * MATCH (t:Table), (a:Attribute) where (t.SchemaName = a.SchemaName and t.TableName = a.TableName) CREATE (t)-[r:comprises]->(a);
 */

package edu.UC.PhD.CodeProject.nicholdw.query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
import edu.nicholdw.PhD.CodeProject.ETL.ETLKTRFile;

public class QueryGraphVersion01 {
	
	public static void createGraph(QueryDefinition qd, Boolean traverseChildQueries) {
		Log.logProgress("QueryDefinitionFileProcessing.createGraph(): " + qd.getQueryName());
		SchemaGraph.addAllConstraints();
		
		HashMap<String, Schema> schemas = qd.getUniqueSchemaNames();
		// Iterating over keys only
		for (String schemaName : schemas.keySet()) {		// https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
			SchemaGraph.addSchemaNode(schemaName);
		}
		SchemaGraph.addQueryNode(qd.getSchemaName(), qd.getQueryName());
		SchemaGraph.connectSchemaNodeToQueryNode(qd.getSchemaName(), qd.getQueryName());

		HashMap<String, QueryAttribute> queryAttributes = qd.getUniqueQueryAttributes(false);
		Log.logProgress("QueryDefinitionFileProcessing.createGraph(): writing query attributes");
		for (QueryAttribute queryAttribute : queryAttributes.values()) {		// https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
			SchemaGraph.addQueryAttribute(queryAttribute,
										  queryAttribute.getSchemaName(), 
					                      queryAttribute.getContainerName(), 
					                      queryAttribute.getAttributeName(), 
					                      qd.getQueryAttributeDataType(queryAttribute));
			SchemaGraph.connectQueryNodeToAttributeNode(qd.getSchemaName(), qd.getQueryName(),
					                                    queryAttribute.getSchemaName(), 
                                                        queryAttribute.getContainerName(), 
                                                        queryAttribute.getAttributeName());
		}
		if (traverseChildQueries) {
			for (QueryDefinition qdChild : qd.getChildren())
			QueryGraphVersion01.createGraph(qdChild, traverseChildQueries);
		}
/*		HashMap<String, QueryTable> queryTables = qd.getUniqueTableNames();
		Log.logProgress("QueryDefinitionToCSV.createGraph(): writing tables");
		for (QueryTable queryTable : queryTables.values()) {		// https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
			SchemaGraph.addTableNode(queryTable.getSchemaName(), queryTable.getTableName());
		} */
		// Match all the attributes with their tables
/*		Neo4jDB.submitNeo4jQuery("MATCH (t:Table), (a:Attribute) "
				               + " WHERE (t.SchemaName = a.SchemaName AND t.TableName = a.TableName)"
				               + " CREATE (t)-[r:" + Config.getConfig().getNeo4jTableToAttributeRelationName() + "]->(a);"); */
	}	
	private static final String rootQuerySuffix = "RootQuery" + Config.getConfig().getCSVFileExtension();
	private static final String schemaSuffix = "Schemas" + Config.getConfig().getCSVFileExtension();
	private static final String tableSuffix = "Tables" + Config.getConfig().getCSVFileExtension();
	private static final String attributeSuffix = "Attributes" + Config.getConfig().getCSVFileExtension();

	public static void generateQueryDefinitionCSVFiles(String fileNamePrefix, QueryDefinition qd) {
		Log.logProgress("QueryDefinitionFileProcessing.generateQueryDefinitionCSVFile()");
		writeRootQuery(fileNamePrefix + rootQuerySuffix, qd);
		writeSchemas(fileNamePrefix + schemaSuffix, qd);
		writeQueryAttributes(fileNamePrefix + attributeSuffix, qd);
		writeTables(fileNamePrefix + tableSuffix, qd);
		Log.logProgress("QueryDefinitionFileProcessing.generateQueryDefinitionCSVFile(): done");
	}
/***
 * @param fileNamePrefix "Neo4j" or some useful prefix
 * @param myCypherQueries The array of queries to be executed
 * @param filePath The filePath to be appended to the CSV files. APOC uses absolute file paths but cypher commands assume the import folder in the current DB. This can be "" if APOC is not being used.
 */
	public static void executeCypherQueries(String fileNamePrefix, ArrayList<String> myCypherQueries, String filePath) {
		Log.logProgress("QueryDefinitionFileProcessing.executeCypherQueries()");
		try {
			Neo4jDB.getDriver();
			for (String queryTemplate: myCypherQueries) {
				String query;
				query = queryTemplate.replace("$1", fileNamePrefix);
				query = query.replace("$2", Utils.formatPath(filePath));
				try {
					// Submit the query to the Neo4J server. It will be executed against the currently open db instance. There'd better be a currently open db instance.
					Log.logProgress("QueryDefinitionFileProcessing.executeCypherQueries(): Executing " + query);
					Neo4jDB.ExecActionQuery(query);
				} catch (Exception e) {
					Log.logError("QueryDefinitionFileProcessing.executeCypherQueries(): " + e.getLocalizedMessage());
				}
			}
		} catch (Exception ex) {
			Log.logError("QueryDefinitionFileProcessing.executeCypherQueries(): " + ex.getLocalizedMessage());
		}
		Log.logProgress("QueryDefinitionFileProcessing.executeCypherQueries(): done");
	}
	private static void writeRootQuery(String fileName, QueryDefinition qd) {
		try {
			boolean fileExists = false;
			Log.logProgress("QueryDefinitionFileProcessing.writeRootQuery():");
			File f = new File(fileName);
			Log.logProgress("QueryDefinitionFileProcessing.writeRootQuery(): writing to " + f.toPath());
			try {
				Files.deleteIfExists(f.toPath());
			} catch (Exception ex) {
				Log.logError("QueryDefinitionFileProcessing.writeRootQuery() deleting " + fileName +  ": " + ex.getLocalizedMessage(), ex);
			}
//			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(fileName, true);
			if (!fileExists) {
				writer.append("SchemaName");
				writer.append(",");
				writer.append("QueryName");
				writer.append('\n');
			}
			writer.append(Utils.cleanForGraph(qd.getSchemaName()));
			writer.append(",");
			writer.append(Utils.cleanForGraph(qd.getQueryName()));
			writer.append('\n');

			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("QueryDefinitionFileProcessing.writeRootQuery(): " + e.getLocalizedMessage());
		}
	}

	/**
	 * After writing this file, use "LOAD CSV with headers FROM "FILE:///foo_Schemas.csv" AS CSVLine create (S:Schema { ID:CSVLine.SchemaName, name:CSVLine.SchemaName});" to import into CSV
	 * @param fileName The target file
	 * @param qd The Query Definition object to process
	 */
	private static void writeSchemas(String fileName, QueryDefinition qd) {
		try {
			boolean fileExists = false;
			Log.logProgress("QueryDefinitionFileProcessing.writeSchemas(): ");
			File f = new File(fileName);
			Log.logProgress("QueryDefinitionFileProcessing.writeSchemas(): writing to " + f.toPath());
			try {
				Files.deleteIfExists(f.toPath());}
			catch (Exception ex) {
				Log.logError("QueryDefinitionFileProcessing.writeSchemas() deleting " + fileName +  ": " + ex.getLocalizedMessage(), ex);
			}
//			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(fileName, true);
			if (!fileExists) {
				writer.append("SchemaName");
				writer.append('\n');
			}
			HashMap<String, Schema> schemas = qd.getUniqueSchemaNames();
			// Iterating over keys only
			for (String schemaName : schemas.keySet()) {		// https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
				Log.logProgress("QueryDefinitionFileProcessing.writeSchemas(): Schemas = " + schemaName);
				writer.append(Utils.cleanForGraph(schemaName));
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("QueryDefinitionFileProcessing.writeSchemas(): " + e.getLocalizedMessage());
		}
	}
	/**
	 * After writing this file, use
	 * "LOAD CSV WITH HEADERS FROM "FILE:///foo_Tables.csv" AS CSVLine CREATE (T:Table { ID:CSVLine.SchemaName+"."+CSVLine.TableName, TableName:CSVLine.TableName})" to import into CSV
	 * @param fileName The target file
	 * @param qd The Query Definition object to process
	 */
	private static void writeTables(String fileName, QueryDefinition qd) {
		try {
			boolean fileExists = false;
			Log.logProgress("QueryDefinitionFileProcessing.writeTables(): ");
			File f = new File(fileName);
			Log.logProgress("QueryDefinitionFileProcessing.writeTables(): writing to " + f.toPath());
			try {Files.deleteIfExists(f.toPath());} catch (Exception ex) {}
//			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(fileName, true);
			if (!fileExists) {
				writer.append("ContainingQueryName");
				writer.append(",");
				writer.append("SchemaName");
				writer.append(",");
				writer.append("TableName");
				writer.append('\n');
			}
			HashMap<String, QueryTable> queryTables = qd.getUniqueTableNames();
			for (QueryTable queryTable : queryTables.values()) {		// https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
				Log.logProgress("QueryDefinitionToCSV.writeTables(): table = " + queryTable.getSchemaName() + "." + queryTable.getTableName());
				writer.append(Utils.cleanForGraph(queryTable.getContainingQueryName()));
				writer.append(",");
				writer.append(Utils.cleanForGraph(queryTable.getSchemaName()));
				writer.append(",");
				writer.append(Utils.cleanForGraph(queryTable.getTableName()));
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("QueryDefinitionToCSV.writeTables(): " + e.getLocalizedMessage());
		}
	}
	private static void writeQueryAttributes(String fileName, QueryDefinition qd) {
		try {
			boolean fileExists = false;
			Log.logProgress("QueryDefinitionFileProcessing.writeQueryAttributes(): ");
			File f = new File(fileName);
			Log.logProgress("QueryDefinitionFileProcessing.writeQueryAttributes(): writing to " + f.toPath());
			try {Files.deleteIfExists(f.toPath());} catch (Exception ex) {}
//			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(fileName, true);
			if (!fileExists) {
				writer.append("AttributeName");
				writer.append(",");
				writer.append("TableName");
				writer.append(",");
				writer.append("SchemaName");
				writer.append(",");
				writer.append("DataType");
				writer.append('\n');
			}
			HashMap<String, QueryAttribute> queryAttributes = qd.getUniqueQueryAttributes(false);

			Log.logProgress("QueryDefinitionFileProcessing.writeQueryAttributes(): writing attributes");
			for (QueryAttribute queryAttribute : queryAttributes.values()) {		// https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
				writer.append(Utils.cleanForGraph(queryAttribute.getAttributeName()));
				writer.append(",");
				writer.append(Utils.cleanForGraph(queryAttribute.getContainerName()));
				writer.append(",");
				writer.append(Utils.cleanForGraph(queryAttribute.getSchemaName()));
				writer.append(",");
				writer.append(Utils.cleanForGraph(qd.getQueryAttributeDataType(queryAttribute)));
//				writer.append(",");
//				writer.append(queryAttribute.get.getTableName());
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("QueryDefinitionFileProcessing.writeQueryAttributes(): " + e.getLocalizedMessage());
		}
	}
	public static final ArrayList<String>  cypherQueries = new ArrayList<String>(Arrays.asList(
			"LOAD CSV WITH HEADERS FROM \"FILE:///$1RootQuery.csv\" AS CSVLine CREATE (q:RootQuery { ID:CSVLine.SchemaName + \".\" + CSVLine.QueryName, SchemaName:CSVLine.SchemaName, QueryName:CSVLine.QueryName });",
			"LOAD CSV WITH HEADERS FROM \"FILE:///$1Tables.csv\" AS CSVLine CREATE (T:Table  { ID:CSVLine.SchemaName+\".\"+CSVLine.TableName, SchemaName:CSVLine.SchemaName, TableName:CSVLine.TableName, ContainingQueryName:CSVLine.ContainingQueryName});",
			"MATCH (t:Table), (q:RootQuery) where (t.ContainingQueryName=q.QueryName )  CREATE (q)-[r:" + 	Config.getConfig().getNeo4jQueryToTableRelationName() + "]->(t);",
			"MATCH (t:Table), (q:Table) where (t.ContainingQueryName=q.TableName )  CREATE (q)-[r:" + 	Config.getConfig().getNeo4jQueryToTableRelationName() + "]->(t);",
			"LOAD CSV WITH HEADERS FROM \"FILE:///$1Attributes.csv\" AS CSVLine CREATE (A:Attribute  { ID:CSVLine.SchemaName+\".\"+CSVLine.TableName+\".\"+CSVLine.AttributeName, SchemaName:CSVLine.SchemaName, TableName:CSVLine.TableName, AttributeName:CSVLine.AttributeName, DataType:CSVLine.DataType});",
			"MATCH (t:Table), (a:Attribute) where (t.SchemaName = a.SchemaName and t.TableName = a.TableName) CREATE (t)-[r:" + Config.getConfig().getNeo4jTableToAttributeRelationName() + "]->(a);"
			));
}
