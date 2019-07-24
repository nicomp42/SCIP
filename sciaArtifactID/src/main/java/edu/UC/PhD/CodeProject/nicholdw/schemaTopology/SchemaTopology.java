/* Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.schemaTopology;

import java.util.ArrayList;
import java.util.HashMap;

import edu.UC.PhD.CodeProject.nicholdw.ActionQuery;
import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;
import edu.UC.PhD.CodeProject.nicholdw.Attribute;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Tables;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryType;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeUnknown;

/**
 * Generate the Schema Topology graph
 * @author nicomp
 */
public class SchemaTopology {
	private DatabaseGraphResults schemaTopologyResults;
	private String hostName, userName, password;
//	private String schemaName;
	private QueryDefinitions queryDefinitions;
	private Schema schema;
	private DatabaseGraphConfig schemaTopologyConfig;
	private static final String schemaNodeLabel = "Schema";
	private static final String queryNodeLabel = "Query";
	public  static final String tableNodeLabel = "Table";
	public  static final String attributeNodeLabel = "Attribute";
	public  static final String affectedAttributeNodeLabel = "Affected_Attribute";
	public  static final String etlFieldNodeLabel = "ETLField";
	public  static final String tableToAttributeLabel = "contains_attribute";
	private static final String queryToAttributeLabel = "references_attribute";
	private static final String schemaToTableLabel = "contains_table";
	private static final String schemaToQueryLabel = "contains_query";
	public  static final String etlStepNodeLabel = "ETLStep";
	public  static final String etlStepToQueryAttributeLbel = "ETLStepToQueryAttribute";
	public  static final String etlFieldToETLStepLabel = "ETLFieldToETLStep";
	public  static final String etlDBProcNodeLabel = "DBProc";
	public	static final String etlHopLabel = "Hop";

	/**
	 * Test main. Schema name defaults to localhost.schematopologytest
	 * @param args
	 */
	public static void main(String[] args) {
		Log.logProgress("SchemaTopology.main(): working...");

		Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
		Neo4jDB.getDriver();
		Neo4jDB.clearDB();
		DatabaseGraphConfig schemaTopologyConfig = new DatabaseGraphConfig();
		schemaTopologyConfig.setIncludeSchemaInGraph(false);
		schemaTopologyConfig.setUseFriendlyNameAsDisplayName(true);

		SchemaTopology schemaTopology = new SchemaTopology(schemaTopologyConfig, "localhost", "root", "Danger42", "schematopologytest", null);
		try {
			//SchemaTopologyResults schemaTopologyResults = new SchemaTopologyResults();
			schemaTopology.generateGraph(null, null);
		} catch (Exception e) {
			Log.logError("SchemaTopology.main(): " + e.getLocalizedMessage());
		}
	}
	/**
	 *
	 * @param hostName
	 * @param databaseName
	 * @param userName
	 * @param password
	 * @param schemaName
	 * @param queryDefinitions The list of query definitions to be processed, or {null or zero-length} for all queries in the schema.
	 */
	public SchemaTopology(DatabaseGraphConfig schemaTopologyConfig, String hostName, String userName, String password, String schemaName, QueryDefinitions queryDefinitions) {
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
//		this.schemaName = schemaName;
		this.queryDefinitions = queryDefinitions;
		this.schemaTopologyConfig = schemaTopologyConfig;
		this.schemaTopologyResults = new DatabaseGraphResults();
		schema = new Schema(schemaName);
	}
	public DatabaseGraphResults generateGraph(String actionQuerySQL, String actionQueryFile) throws Exception {
		boolean status = true;		// Hope for the best
		schema.loadTables(hostName, userName, password);			// Load the tables for the schema
		Tables tables = schema.getTables();	// Get the list of loaded tables.
		for (Table table : tables) {	// Get all the attributes from the tables
			table.setAttributes(Table.readAttributesFromTableDefinition(table.getTableName(), schema.getSchemaName()));
		}
		try {
			if (queryDefinitions == null || queryDefinitions.getQueryDefinitions().size() == 0) {
				loadQueryDefinitions();
				if ((actionQuerySQL != null) && (actionQuerySQL.trim().length() > 0)) {
					// Parse the action query and apply it to the schema topology
					QueryDefinition actionQueryDefinition = new QueryDefinition(hostName, userName, password, new QueryTypeUnknown(), "myActionQuery", actionQuerySQL, schema.getSchemaName());
					actionQueryDefinition.crunchIt();
					applyActionQuery(actionQueryDefinition);
				}
				if ((actionQueryFile != null) && (actionQueryFile.trim().length() > 0)) {
					// Parse the action querys in the text file and apply them to the schema topology
					ActionQuerys actionQuerys = new ActionQuerys();
					actionQuerys.loadActionQueries(actionQueryFile);
					for (ActionQuery ac : actionQuerys) {	// Yay Iterable interface!
						QueryDefinition actionQueryDefinition = new QueryDefinition(hostName, userName, password, new QueryTypeUnknown(), "myActionQuery", ac.getSql(), schema.getSchemaName());
						actionQueryDefinition.crunchIt();
						applyActionQuery(actionQueryDefinition);
					}
				}				
				addNodesToGraph();
			} else {
				Log.logError("SchemaTopology.generateGraph(): subset of queries is not yet supported. Only 'all' queries can be processed");
				throw new Exception ("SchemaTopology.generateGraph(): subset of queries is not yet supported. Only 'all' queries can be processed");		// TODO implement this.
			}
		} catch (Exception ex) {
			Log.logError("SchemaTopology.generateGraph(): " + ex.getLocalizedMessage());
			status = false;
		}
		return schemaTopologyResults;
	}
	/**
	 * Apply an action query to the schema to see what views would be affected
	 * @param actionQueryDefinition The action query
	 */
	private void applyActionQuery(QueryDefinition actionQueryDefinition) {
		Log.logProgress("SchemaTopology.ApplyActionQueryDefinition()");
//		SchemaDiff schemaDiff = new SchemaDiff();
		try {
			for (QueryDefinition qd: queryDefinitions) {
				for (QueryAttribute qa : qd.getQueryAttributes()) {
					if (actionQueryDefinition.getQueryAttributes().findAttribute(qa)) {
						// The query attribute is referenced in the action query. We need to note that so when we draw the graph we can draw the attribute differently.
						qa.setAffectedByActionQuery(true);
						qd.getQueryTables().setAffectedByActionQuery(qa, true);
						schema.getTables().setAffectedByActionQuery(qa, true);
						schemaTopologyResults.incrementTotalAffectedAttributes();
					}
				}
			}
		} catch (Exception ex) {
			Log.logError("SchemaTopology.ApplyActionQueryDefinition(): " + ex.getLocalizedMessage());
		}
//		return schemaDiff;
	}
	private void loadQueryDefinitions() {
		// Use all the queries in the schema
		queryDefinitions = new QueryDefinitions();
		// Populate our collection with all the query definitions in the schema
		ArrayList<String> queryNames = Schema.readQueryNames(hostName, schema.getSchemaName(), userName, password);
		QueryDefinition queryDefinition;
		for (String queryName : queryNames) {
			String sql;
			sql = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(hostName, userName, password, schema.getSchemaName(), queryName);
			queryDefinition = new QueryDefinition(hostName, userName, password, new QueryTypeUnknown(), queryName, sql, schema.getSchemaName() );
			queryDefinitions.addQueryDefinition(queryDefinition);
			queryDefinition.crunchIt();
			schemaTopologyResults.incrementTotalQueries();
		}
	}
	private void addNodesToGraph() {
		addSchemaNode();
		addQueryNodes();
		addTableConstraint();		// We want each table to appear only one time
		addTableNodes();
		addAttributeConstraint();		// We want each attribute to appear only one time
		addAttributeNodes();
		addQueryToAttributeRelationships();	// At this point we have the queries and the table attributes
	}
	private void addSchemaNode() {
		if (schemaTopologyConfig.getIncludeSchemaInGraph() == true) {
			Neo4jDB.submitNeo4jQuery("CREATE (" + Utils.cleanForGraph(schema.getSchemaName()) + ":" + schemaNodeLabel 
					               + " { key: " + "'" + Utils.cleanForGraph(schema.getSchemaName()) + "'" + ", name:'" + Utils.cleanForGraph(schema.getSchemaName()) + "'})");
		}
	}
	private void addQueryNodes() {
		for (QueryDefinition queryDefinition : queryDefinitions) {
			String queryName;
			queryName = queryDefinition.getQueryName();
			Neo4jDB.submitNeo4jQuery("CREATE (" + Utils.cleanForGraph(queryName) + ":" + queryNodeLabel 
					               + " { key: " + "'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(queryName) + "'" + ", name:'" + Utils.cleanForGraph(queryName) + "'})");
			if (schemaTopologyConfig.getIncludeSchemaInGraph() == true) {
				// Add relationships from the schema to the queries
				Neo4jDB.submitNeo4jQuery("MATCH "
				           +       "(q:" + queryNodeLabel  + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(queryDefinition.getQueryName()) + "'}), "
			               + "      (s:" + schemaNodeLabel + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "'}) "
					       + "CREATE (s)-[:" + schemaToQueryLabel +"]->(q)");
			}
		}
	}
	private void addTableConstraint() {
		Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (t:" + tableNodeLabel + ") ASSERT t.Key IS UNIQUE");
	}
	/**
	 *  Grab all the table names from the schema and drop in a node for each one
	 */
	private void addTableNodes() {
		Tables tables = schema.getTables();	// Get the list of loaded tables.
		for (Table table : tables) {
			Neo4jDB.submitNeo4jQuery("CREATE (" + Utils.cleanForGraph(table.getTableName()) + ":" + tableNodeLabel 
					               + " { key: " + "'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName()) + "'" 
					               + ", name:'" + Utils.cleanForGraph(table.getTableName()) + "'})");
			schemaTopologyResults.incrementTotalTables();
			if (schemaTopologyConfig.getIncludeSchemaInGraph() == true) {
				// Add relationships from the schema to the tables
				Neo4jDB.submitNeo4jQuery("MATCH "
				                       + "(t:" + tableNodeLabel  + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName()) + "'}), "
			                           + " (s:" + schemaNodeLabel + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "'}) "
					                   + "CREATE (s)-[:" + schemaToTableLabel +"]->(t)");
			}
		}
	}
	private void addAttributeConstraint() {
		Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + attributeNodeLabel + ") ASSERT a.Key IS UNIQUE");
	}
	private void addAttributeNodes() {
		Tables tables = schema.getTables();	// Get the list of loaded tables. By now we have also populated the attribute collection in each table.
		for (Table table : tables) {
			for (Attribute attribute: table.getAttributes()) {
				schemaTopologyResults.incrementTotalAttributes();
				String nodeLabel;
				nodeLabel = attributeNodeLabel;
				if (attribute.getAffectedByActionQuery() == true) {
					nodeLabel = affectedAttributeNodeLabel;
				}
				Neo4jDB.submitNeo4jQuery("CREATE (" + 
						                 Utils.cleanForGraph(attribute.getAttributeName()) + 
				                         ":" + 
				                         nodeLabel + 
				                         " { key: " 
				                         + "'" 
				                         + Utils.cleanForGraph(schema.getSchemaName()) 
				                         + "." 
				                         + Utils.cleanForGraph(table.getTableName()) 
				                         + "." 
				                         + Utils.cleanForGraph(attribute.getAttributeName()) 
				                         + "'" 
				                         + ", name:'" 
				                         + Utils.cleanForGraph(attribute.getAttributeName()) 
				                         + "'"
				                         + ", table:'"
				                         + Utils.cleanForGraph(attribute.getTableName()) 
				                         + "'"
				                         + "}"
				                         + ")");
				// Add the relationship between the table and the attribute now because we have everything we need.
				Neo4jDB.submitNeo4jQuery("MATCH (t:" + tableNodeLabel     
						               + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName()) + "'}), "
				                       + " (a:" + nodeLabel 
				                       + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName()) + "." + Utils.cleanForGraph(attribute.getAttributeName()) + "'}) "
						               + "CREATE (t)-[:" + tableToAttributeLabel +"]->(a)");
			}
		}
	}
	private void addQueryToAttributeRelationships() {
		for (QueryDefinition queryDefinition : queryDefinitions) {
			HashMap<String, QueryAttribute> queryAttributes = queryDefinition.getUniqueQueryAttributes(true);
			// traverse queryAttributes and add a relation from the query to the attribute
			for (QueryAttribute queryAttribute: queryAttributes.values()) {
				String neo4jQuery;
				String nodeLabel;
				nodeLabel = attributeNodeLabel;
				if (queryAttribute.getAffectedByActionQuery() == true) {
					nodeLabel = affectedAttributeNodeLabel;
				}
				neo4jQuery = "MATCH "
				           + " (q:" + queryNodeLabel  + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(queryDefinition.getQueryName()) + "'}), "
			               + " (a:" + nodeLabel + "{key:'" + Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(queryAttribute.getTableName()) + "." + Utils.cleanForGraph(queryAttribute.getAttributeName()) + "'}) "
					       + " CREATE (q)-[:" + queryToAttributeLabel +"]->(a)";
				Neo4jDB.submitNeo4jQuery(neo4jQuery);
				schemaTopologyResults.incrementTotalQueryAttributes();
			}
		}
	}
}
