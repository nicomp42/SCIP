package edu.UC.PhD.CodeProject.nicholdw.dwQuery;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;


public class QueryGraphController {

	public QueryGraphController() {	}

	public void generateQueryNodesAndDependencies(SchemaChangeImpactProject scip){
		generateQueryNodes(scip);
		generateQueryAttributeDependencyRelationships(scip);
		generateQueryRelationDependencyRelationships(scip);
		generateQuerySchemaDependencyRelationships(scip);
	}
	/**
	 * Add graph nodes for the queries.
	 * @param scip The current Schema Change Impact Project
	 */
	public void generateQueryNodes(SchemaChangeImpactProject scip){
		try{
			Log.logProgress("QueryGraphController.generateQueryNodes()");
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \"" + Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.dwhQueriesSubdirectory) + "queries.csv"+"\" AS line " + "MERGE (q:Query{name: line.QueryLabel, schema: line.DatabaseName})");
		} catch (Exception ex){
			Log.logError("QueryGraphController.generateQueryNodes(): " + ex.getMessage(), ex.getStackTrace());
		}
	}

	/**
	 * Connect existing attributes with the queries we just loaded
	 * Nothing happens here unless attributes have already been loaded in a previous step: Operational or ETL
	 * @param scip The current Schema Change Impact Project
	 */
	public void generateQueryAttributeDependencyRelationships(SchemaChangeImpactProject scip){
		try{
			Log.logProgress("QueryGraphController.generateQueryAttributeDependencyRelationships()");
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \"" + Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.dwhQueriesSubdirectory)  + "queries.csv"+"\" AS line "
						+ " MATCH (q:Query{name: line.QueryLabel})"
						+ " MATCH (a:Attribute {name: (line.AttributeName), relation: (line.RelationName), schema: (line.DatabaseName)})"
						+ " MERGE (a)-[:Impacts]->(q)");

		} catch (Exception ex){
			Log.logError("QueryGraphController.generateQueryAttributeDependencyRelationships(): " + ex.getMessage(), ex.getStackTrace());
		}
	}

	public void generateQueryRelationDependencyRelationships(SchemaChangeImpactProject scip){
		try{
			Log.logProgress("QueryGraphController.generateQueryRelationDependencyRelationships()");
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \"" + Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.dwhQueriesSubdirectory) + "queries.csv"+"\" AS line "
						+ " MATCH (q:Query{name: line.QueryLabel})"
						+ " MATCH (a:Relation {name: (line.RelationName), schema: (line.DatabaseName)})"
						+ " MERGE (a)-[:Impacts]->(q)");

		} catch (Exception ex){
			Log.logError("QueryGraphController.generateQueryRelationDependencyRelationships(): " + ex.getMessage(), ex.getStackTrace());
		}
	}

	public void generateQuerySchemaDependencyRelationships(SchemaChangeImpactProject scip){
		try{
			Log.logProgress("QueryGraphController.generateQuerySchemaDependencyRelationships()");
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \"" + Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.dwhQueriesSubdirectory) + "queries.csv" + "\" AS line "
						+ " MATCH (q:Query{name: line.QueryLabel})"
						+ " MATCH (a:Schema {name: (line.DatabaseName)})"
						+ " MERGE (a)-[:Impacts]->(q)");

		} catch (Exception ex){
			Log.logError("QueryGraphController.generateQuerySchemaDependencyRelationships(): " + ex.getMessage(), ex.getStackTrace());
		}
	}
}
