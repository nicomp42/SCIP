/****************************************************************************************
 * Watch out for file paths. APOC calls use an absolute path, Neo4j queries assume the
 * 'import' folder inside the currently open DB.
 *
 * The method ProcessCSVFileByLine() can be replaced by CALL apoc.merge.node(), which apparently exists but is not in the documentation.
 *  See https://stackoverflow.com/questions/45516284/is-there-a-way-to-call-apoc-create-node-such-that-it-will-not-fail-if-one-or-mor/45540198#45540198
 *
 *
 */
package edu.UC.PhD.CodeProject.nicholdw.attributeProvenance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.attributeParts.AttributeParts;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jUtils;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitionFileProcessing;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTables;
/**
 * Helper class that creates CSV files and submits queries to the current Neo4j DB.
 * @author nicomp
 */
public class AttributeProvenanceForNe04j {

	private static final String attributeProvenanceFileSuffix = "AttributeProvenance";
//	private static final String provenanceRealationName = "Provenance";
	private static final String provenanceRealationName = "Impacts";

	public static boolean exportCSVFiles(AttributeParts attributeParts, QueryDefinition qd, String filePath) {
		String fileName = Utils.formatPath(Utils.cleanPath(filePath)) + Config.getConfig().getNeo4jSuffix() + attributeProvenanceFileSuffix + Config.getConfig().getCSVFileExtension();
		QueryTables queryTables = QueryDefinition.buildProvenance(qd, attributeParts.getAliasName());
		return writeAttributeProvenanceCSVFile(fileName, qd, queryTables, attributeParts);
	}
	/**
	 * Execute the queries needed to import the CSV files into the current Neo4j DB
	 * @param filePath The path with no filename at the end. We will add the file name
	 * @return True if no errors happened. false otherwise
	 */
	public static boolean executeCypherQueries(String filePath) {
		boolean status = true;	// Hope for the best
		try {
			Log.logProgress("AttributeProvenance.executeCypherQueries(" + filePath  + ")");
			Neo4jUtils.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
			// Run the queries that add the constraints
			QueryDefinitionFileProcessing.executeCypherQueries(Config.getConfig().getNeo4jSuffix(), AttributeProvenanceForNe04j.cypherQueriesStep1, Utils.formatPath(filePath));	// Folder defaults to the import folder in the Neo4j project structure

			String fileName = Utils.formatPath(Utils.cleanPath(filePath)) + Config.getConfig().getNeo4jSuffix() + attributeProvenanceFileSuffix + Config.getConfig().getCSVFileExtension();
			ProcessCSVFileByLine(fileName);

			// Run the queries that build the relationships
			QueryDefinitionFileProcessing.executeCypherQueries(Config.getConfig().getNeo4jSuffix(), AttributeProvenanceForNe04j.cypherQueriesStep2, Utils.formatPath(filePath));	// Folder defaults to the import folder in the Neo4j project structure
			Log.logProgress("AttributeProvenance.executeCypherQueries(" + filePath  + ") Done");
		} catch (Exception ex) {
			Log.logError("AttributeProvenance.executeCypherQueries(" + filePath  + ")", ex );
			status = false;
		} finally {
		}
		return status;
	}
	private static boolean writeAttributeProvenanceCSVFile(String fileName, QueryDefinition qd, QueryTables queryTables, AttributeParts attributeParts) {
		boolean status = true;	// Hope for the best
		try {
			String previousKey = "";
			boolean fileExists = false;
			Log.logProgress("AttributeProvenance.writeAttributeProvenanceCSVFile(): ");
			File f = new File(fileName);
			try {
				Files.deleteIfExists(f.toPath());
			} catch (Exception ex) {
				Log.logError("AttributeProvenance.writeAttributeProvenanceCSVFile() deleting " + fileName + ": " + ex.getLocalizedMessage());
			}
			FileWriter writer = new FileWriter(fileName, true);
			if (!fileExists) {
				writer.append("NodeType");
				writer.append(",");
				writer.append("Key");		// Unique Identifier for the node
				writer.append(",");
				writer.append("SchemaName");
				writer.append(",");
				writer.append("TableName");
				writer.append(",");
				writer.append("AttributeName");
				writer.append(",");
				writer.append("DataType");
				writer.append(",");
				writer.append("PreviousKey");
				writer.append('\n');
			}
			Log.logProgress("AttributeProvenance.writeAttributeProvenanceCSVFile(): writing attributes");
			// The first row in the file is the attribute for which we are writing the provenance.
			// It should be a different type than all the other nodes just so the visual presentation is improved.
			writer.append("RootNode");
			writer.append(",");
			writer.append(qd.getSchemaName().trim() + "." + qd.getQueryName().trim() + "." + attributeParts.getAttributeName().trim());
			writer.append(",");
			writer.append(qd.getSchemaName().trim());
			writer.append(",");
			writer.append(qd.getQueryName().trim());
			writer.append(",");
			writer.append( attributeParts.getAttributeName().trim());
			writer.append(",");
			writer.append(attributeParts.getDataType().trim());
			writer.append(",");
			writer.append(previousKey.trim());
			writer.append('\n');
			previousKey = qd.getSchemaName().trim() + "." + qd.getQueryName().trim() + "." + attributeParts.getAttributeName().trim();
			for (QueryTable queryTable : queryTables) {
				writer.append("SubNode");
				writer.append(",");
				writer.append(queryTable.getSchemaName().trim() + "." + queryTable.getTableName().trim() + "." + attributeParts.getAttributeName().trim());
				writer.append(",");
				writer.append(queryTable.getSchemaName().trim());
				writer.append(",");
				writer.append(queryTable.getTableName().trim());
				writer.append(",");
				writer.append( attributeParts.getAttributeName().trim());
				writer.append(",");
				writer.append(attributeParts.getDataType().trim());
				writer.append(",");
				writer.append(previousKey.trim());
				writer.append('\n');
				previousKey = queryTable.getSchemaName().trim() + "." + queryTable.getTableName().trim() + "." + attributeParts.getAttributeName().trim();
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("QueryDefinitionFileProcessing.writeAttributeProvenanceCSVFile(): " + e.getLocalizedMessage());
			status = false;
		}
		return status;
	}
	/**
	 * The chained call to apoc.load.csv/apoc.create.node will completely fail if one constraint is violated on a node creation.
	 * This method will process a CSV file into multiple CSV files, each with the header line and one line of data, and invoke the apoc.load.csv/apoc.create.node script on each little CSV file.
	 * @param fileName The original CSV file with multiple lines of data.
	 */
	public static void ProcessCSVFileByLine(String fileName) {
//		TODO: This method is cool and it works but it might be replaced by CALL apoc.merge.node()
		String query = "CALL apoc.load.csv('FILE:///$1',{sep:\",\"}) YIELD map " +
					   " CALL apoc.create.node([map.NodeType], {Key:map.Key, AttributeName:map.AttributeName, TableName:map.TableName, SchemaName:map.SchemaName, DataType:map.DataType, PreviousKey:map.PreviousKey}) yield node return count(*)";
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String header = br.readLine();
			String data;
			String littleCSVFileName;
			littleCSVFileName = fileName.replace(Config.getConfig().getCSVFileExtension(), "X" + Config.getConfig().getCSVFileExtension());	// Make up a new name for the new CSV file. It's temporary so we don't care what it is.
			query = query.replace("$1",  littleCSVFileName);
			while ((data = br.readLine()) != null) {
				// Create a new CSV file with just the header and the one line of data.
				FileWriter fw = new FileWriter(littleCSVFileName);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(header); bw.newLine();
				bw.write(data); bw.newLine();
				bw.close();
				// Invoke the apoc.load.csv/apoc.create.node script on this newly created CSV file with one line of data
				Neo4jUtils.ExecActionQuery(query);
			}
			br.close();

		} catch(Exception ex) {
			Log.logError("QueryDefinitionFileProcessing.ProcessCSVFileByLine(): " + ex.getLocalizedMessage());
		}
	}
	public static final ArrayList<String> cypherQueriesStep1 = new ArrayList<String>(Arrays.asList(
			/*
			CALL apoc.load.csv('FILE:///C:/Temp/Test/Test/Neo4jTest/import/Neo4j_AttributeProvenance.csv',{sep:","})  YIELD map
			CALL apoc.create.node([map.NodeType], {Key:map.Key, TableName:map.TableName, SchemaName:map.SchemaName, DataType:map.DataType}) yield node return count(*)
			*/

			// TODO: generalize these constraints to handle all the node types in the CSV file. We have hard coded RootNode and SubNode as the only node types
			"CREATE CONSTRAINT ON (n:RootNode) ASSERT n.Key IS UNIQUE",
			"CREATE CONSTRAINT ON (n:SubNode) ASSERT n.Key IS UNIQUE"

			// Add nodes to illustrate the provenance. Note the absolute file path because APOC calls require it. This will fail completely if there is a constraint violation in a node that is being created!
//			"CALL apoc.load.csv('FILE:///$2$1AttributeProvenance.csv',{sep:\",\"}) YIELD map " +
//				" CALL apoc.create.node([map.NodeType], {Key:map.Key, AttributeName:map.AttributeName, TableName:map.TableName, SchemaName:map.SchemaName, DataType:map.DataType, PreviousKey:map.PreviousKey}) yield node return count(*)",

	));
	public static final ArrayList<String> cypherQueriesStep2 = new ArrayList<String>(Arrays.asList(
			// Draw the relations between nodes we just added.
//			"MATCH (first), (second) WHERE first.Key = second.PreviousKey MERGE (first)-[:" + provenanceRealationName + "]->(second)"
			"MATCH (first), (second) WHERE first.Key = second.PreviousKey MERGE (second)-[:" + provenanceRealationName + "]->(first)"
	));
}
