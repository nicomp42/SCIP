/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;

/**
 * Import from Operational schema CSV files into Neo4j DB
 * @author nicomp
 *
 */
public class OperationalSchemaGraphController {
	/***
	 * Instantiate a SchemaGraph Controller
	 */
	public OperationalSchemaGraphController() {	}
	/**
	 * Start here when generating the entire graph for the an operational schema
	 * @param scip The SchemaChangeImpactProkject object to be processed
	 * @param architectureLayer
	 * @param path ProjectName
	 */
	public void generateOperationalSchemaGraph(SchemaChangeImpactProject scip) {
		Log.logProgress("SchemaGraphController.generateOperationalSchemaGraph(): Generating operational schema graph for project:" + scip.getProjectName() + " and schema " + scip.getOperational().getOperationalSchemaName());
		generateNodes(scip);
		generateEdges(scip);
	}
	public void generateNodes(SchemaChangeImpactProject scip) {
		generateSchemaNodes(scip);
		generateRelationNodes(scip);
		generateAttributeNodes(scip);
	}
	public void generateEdges(SchemaChangeImpactProject scip) {
		generateSchemaRelationRelationships(scip);
		generateRelationAttributeRelationships(scip);
		generateEntityintegrityRelationships(scip);
		generateReferentialIntegrityRelationships(scip);
	}
	/**
	 * Add the schema node using the name of the Operational Schema in the current Schema Change Impact Project
	 * @param scip the current Schema Change Impact Project
	 */
	public void generateSchemaNodes(SchemaChangeImpactProject scip) {
		//, "file:///" + scip.getProjectName() + "\\\\");		// Yes. it's supposed to be file:///   --> three slashes.
		try {
			Log.logProgress("SchemaGraphController: ********Generating schema nodes***********");
			Neo4jDB.ExecActionQuery("MERGE(m:Schema{name:'" + scip.getOperational().getOperationalSchemaName() + "',type:'" + "Operational" + "'})");

		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateSchemaNodes(): ", ex.getStackTrace());
		}
	}
	/**
	 * Add the relation nodes in the Operational Schema in the current Schema Change Impact Project
	 * @param scip the current Schema Change Impact Project
	 */
	public void generateRelationNodes(SchemaChangeImpactProject scip) {
		try {
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \"" +
									   Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory)  + "tables.csv\" AS line " +
									   "MERGE (r:Relation{name:line.table_name, schema:line.schema_name})");
		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateRelationNodes(): ", ex.getStackTrace());
		}
	}
	public void generateAttributeNodes(SchemaChangeImpactProject scip) {
		try {
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
							+ Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory) + "attributes.csv\" AS line "
							+ "MERGE (a:Attribute{name: line.column_name, datatype: line.data_type, "
							+ "relation:line.table_name, schema: line.table_schema, isKey: line.is_Key})");

		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateAttributeNodes(): ", ex.getStackTrace());
		}
	}
	public void generateEntityintegrityRelationships(SchemaChangeImpactProject scip) {
		try {
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory) + "attributes.csv\" AS line "
								+ "MATCH (src:Attribute{name: line.column_name, datatype: line.data_type, "
								+ "relation:line.table_name, schema: line.table_schema, isKey: line.is_Key})"
								+ "MATCH (to:Relation {name: line.table_name, schema: line.table_schema})"
								+ " where src.relation=to.name and src.schema=to.schema and src.isKey='YES' "
								+ " MERGE (src)-[:Impacts {type:'PrimaryKey'}]->(to)");

		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateEntityintegrityRelationships(): ", ex.getStackTrace());
		}
	}
	public void generateRelationAttributeRelationships(SchemaChangeImpactProject scip) {
		try {
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory) + "attributes.csv\" AS line "
								+ "MATCH (to:Attribute{name: line.column_name, datatype: line.data_type, "
								+ "relation:line.table_name, schema: line.table_schema, isKey: line.is_Key})"
								+ "MATCH (src:Relation {name: line.table_name, schema: line.table_schema})"
								+ " where src.name=to.relation and src.schema=to.schema "
								+ "MERGE (src)-[:Impacts]->(to)");

		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateRelationAttributeRelationships(): ", ex.getStackTrace());
		}
	}
	public void generateReferentialIntegrityRelationships(SchemaChangeImpactProject scip) {
		try {
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory) + "foreignKeys.csv\" AS line "
								+ "MATCH (to:Attribute{name: line.attr_name, relation: line.relation_name, "
								+ "schema:'"
								+ scip.getOperational().getOperationalSchemaName() + "'})"
								+ " MATCH (src:Attribute {name: line.referenced_column_name, "
								+ "relation: line.referenced_relation_name, schema:'"
								+ scip.getOperational().getOperationalSchemaName() + "'})"
								+ " MERGE (src)-[:Impacts {type:'ForeignKey'}]->(to)");

		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateReferentialIntegrityRelationships(): ", ex.getStackTrace());
		}
	}
	public void generateSchemaRelationRelationships(SchemaChangeImpactProject scip) {
		try {
			Neo4jDB.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jDB.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory) + "tables.csv\" AS line MATCH (src:Schema {name: line.schema_name}) "
								+ "MATCH (to:Relation {name: line.table_name, schema:line.schema_name})"
								+ " where src.name=to.schema MERGE (src)-[:Impacts]->(to)");

		} catch (Exception ex) {
			Log.logError("SchemaGraphController.generateSchemaRelationRelationships(): ", ex.getStackTrace());
		}
	}
	public void deleteSchema() {
		// MATCH (n) DETACH DELETE n		// Deletes nodes and relationships!
		// MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
		try {
			Log.logProgress("********Generating relation nodes***********");
			// Not sure if both are needed and not sure how much is actually deleted.
			Neo4jDB.ExecActionQuery("MATCH (n) DETACH DELETE n");
			//stmt.executeQuery("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r");
		} catch (Exception ex) {
			Log.logError("SchemaGraphController.deleteSchema(): ", ex.getStackTrace());
		}
	}
}
