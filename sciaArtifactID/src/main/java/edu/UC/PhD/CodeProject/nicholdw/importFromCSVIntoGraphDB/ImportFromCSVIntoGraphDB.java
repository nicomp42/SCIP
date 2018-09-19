package edu.UC.PhD.CodeProject.nicholdw.importFromCSVIntoGraphDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.OperationalSchemaGraphController;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.dwQuery.QueryGraphController;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.DwhQueries;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.IdsDwh;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.Operational;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.OpsIds;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;
import edu.nicholdw.PhD.CodeProject.ETL.ETLGraphController;
/**
 * Manage the data files and call the methods in SchemaGraphController
 * @author nicomp
 */
public class ImportFromCSVIntoGraphDB {

	public void ImportIntoGraphDB(SchemaChangeImpactProject scip, ArrayList<SchemaChangeImpactProjectComponent> schemaChangeImpactProjectComponents ) {	//,  String architectureLayer) throws IOException {
		try {
			File neo4jGraphDBSchemaFile = new File(scip.getNeo4jGraphDBFilePath());
			if (!neo4jGraphDBSchemaFile.exists()) {	// Is the directory present?
				Log.logError("ImportFromCSVIntoGraphDB.ImportIntoGraphDB(): error accessing " + scip.getNeo4jGraphDBFilePath() + " Please export the schema information as CSV files first and try again");
			} else {
				OperationalSchemaGraphController operationalSchemaGraphController = new OperationalSchemaGraphController();
				Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
				if (Neo4jDB.getDriver() == null) {
					Log.logError("GenerateGraphFromOperationalSchema.ImportIntoGraphDB(): Could not connect to Neo4j. Make sure that the database is running");
				} else {
					scip.copyDirectoryStructures();
					// We must use a relative path because Neo4j assumes the "import" folder in the Neo4j database environment.
					// There is a subdirectory under the Neo4j import folder for each type of schema.

					for (SchemaChangeImpactProjectComponent schemaChangeImpactProjectComponent: schemaChangeImpactProjectComponents) {
						if (schemaChangeImpactProjectComponent instanceof Operational) {
							operationalSchemaGraphController.generateOperationalSchemaGraph(scip);
						}
						if (schemaChangeImpactProjectComponent instanceof OpsIds) {
							ETLGraphController etlGraphController = new ETLGraphController();
							etlGraphController.generateETLStepNodes(scip, SchemaChangeImpactProject.opsIdsSubdirectory);
							etlGraphController.generateRelationships(scip, SchemaChangeImpactProject.opsIdsSubdirectory);
						}
						if (schemaChangeImpactProjectComponent instanceof IdsDwh) {
							ETLGraphController etlGraphController = new ETLGraphController();
							etlGraphController.generateETLStepNodes(scip, SchemaChangeImpactProject.idsDwhSubdirectory);
							etlGraphController.generateRelationships(scip, SchemaChangeImpactProject.idsDwhSubdirectory);
						}
						if (schemaChangeImpactProjectComponent instanceof DwhQueries) {
							QueryGraphController queryGraphController = new QueryGraphController();
							queryGraphController.generateQueryNodesAndDependencies(scip);
						}
					}
				}
			}
	//		if (typeOfConversion.equals("view") == true) {
	//			String neo4j_query="MATCH (s:Schema)-[r]->(rel:Relation)-[ra]-(a:Attribute) where "
	//					+ "s.name=\'"+ schemaName +"\'"+
	//					" WITH s, r, rel, ra, a "
	//					+ "OPTIONAL MATCH (a)-[fk]->(fk_a:Attribute) RETURN s, r, rel, ra, a";

	//			String neo4j_view_query = "Click here to access the graph: http://localhost:7474/"
	//					+ "\nExecute the following query:\n"
	//					+ neo4j_query;
	//			response.sendRedirect("index.jsp");
	//		}
	//		if (typeOfConversion.equals("btn_view") == true) {		// Not sure what to do here
	//			//response.sendRedirect(request.getContextPath() + "/etl.jsp");
	//		}
		} catch (Exception ex) {
			Log.logError("ImportFromCSVIntoGraphDB.ImportIntoGraphDB(): " + ex.getLocalizedMessage(), ex.getStackTrace());
		}
	}
}
