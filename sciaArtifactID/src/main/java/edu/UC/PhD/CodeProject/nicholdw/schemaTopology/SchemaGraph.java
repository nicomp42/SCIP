/* Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.schemaTopology;

import java.util.ArrayList;
import java.util.HashMap;

import edu.UC.PhD.CodeProject.nicholdw.ActionQuery;
import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;
import edu.UC.PhD.CodeProject.nicholdw.TableAttribute;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation.GRAPH_NODE_ANNOTATION;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.SchemaImpact;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
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
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.ActionQueryProcessor;

/**
 * Generate a schema graph
 * @author nicomp
 */
public class SchemaGraph {
	private DatabaseGraphResults schemaTopologyResults;
	private String hostName, userName, password;
//	private String schemaName;
//	private QueryDefinitions queryDefinitions;
	private Schemas schemas;
	private DatabaseGraphConfig schemaTopologyConfig;
	// Keep in lower case - Neo4j is case sensitive but MySQL is not. This will save a lot of debugging
	public  static final String schemaNodeLabel = "schema";
	public  static final String viewNodeLabel = "view";
	public  static final String tableNodeLabel = "table";
	public  static final String attributeNodeLabel = "attribute";
	public  static final String affectedAttributeNodeLabel = "affected_attribute";
	public  static final String affectedViewNodeLabel = "affected_view";
	public  static final String etlFieldNodeLabel = "etl_field";
	public  static final String tableToAttributeLabel = "contains_attribute";
	private static final String viewToAttributeLabel = "references_attribute";
	private static final String attributeToAttributeLabel = "provenance";
	private static final String schemaToTableLabel = "contains_table";
	private static final String schemaToQueryLabel = "contains_view";
	public  static final String etlStepNodeLabel = "etl_step";
	public  static final String etlStepToQueryAttributeLbel = "etl_step_to_query_attribute";
	public  static final String etlFieldToETLStepLabel = "etl_field_to_etl_step";
//	public  static final String etlDBProcNodeLabel = "DBProc";	Don't use this, just use the generic etlStepNodeLabel so all the nodes are the same type.
	public	static final String etlHopLabel = "hop";
//	public	static final String etlMergeJoinLabel = "MergeJoin";	Don't use this, just use the generic etlStepNodeLabel so all the nodes are the same type.

	/**
	 * Test main. Schema name defaults to localhost.schematopologytest
	 * @param args
	 */
/*	public static void main(String[] args) {
		Log.logProgress("SchemaGraph.main(): working...");

		Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
		Neo4jDB.getDriver();
		Neo4jDB.clearDB();
		DatabaseGraphConfig schemaTopologyConfig = new DatabaseGraphConfig();
		schemaTopologyConfig.setIncludeSchemaInGraph(false);
		schemaTopologyConfig.setUseFriendlyNameAsDisplayName(true);

		SchemaGraph schemaTopology = new SchemaGraph(schemaTopologyConfig, "localhost", "root", "Danger42", "SchemaGraphtest", null);
		try {
			//SchemaTopologyResults schemaTopologyResults = new SchemaTopologyResults();
			schemaTopology.generateGraph(null, null);
		} catch (Exception e) {
			Log.logError("SchemaGraph.main(): " + e.getLocalizedMessage());
		}
	} */
	/**
	 *
	 * @param hostName
	 * @param databaseName
	 * @param userName
	 * @param password
	 * @param schemaName
	 * @param queryDefinitions The list of query definitions to be processed, or {null or zero-length} for all queries in the schema.
	 */
	public SchemaGraph(DatabaseGraphConfig schemaTopologyConfig, String hostName, String userName, String password, Schemas schemas) { /*, QueryDefinitions queryDefinitions) { */
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
//		this.schemaName = schemaName;
//		this.queryDefinitions = queryDefinitions;
		this.schemaTopologyConfig = schemaTopologyConfig;
		this.schemaTopologyResults = new DatabaseGraphResults();
		setSchemas(schemas);
	}

	public static void addAllConstraints() {
		addAffectedAttributeConstraint();
		addAttributeConstraint();
		addViewConstraint();
		addAffectedViewConstraint();
		addTableConstraint();
		addSchemaConstraint();
		addETLStepNodeConstraint();
	}
	public static void changeNodeLabel(String key, String oldLabel, String newLabel) {
//		match (n:attribute {key:'temporary.store.cityid'}) remove n:attribute set n:attribute_affected
		Neo4jDB.submitNeo4jQuery("match (n:"
				                + oldLabel 
				                + "{key:"
				                + "'" + key + "'"
				                + "}) remove n:"
				                + oldLabel
				                + " set n:" 
				                + newLabel);
	}
	public DatabaseGraphResults generateGraph(String actionQuerySQL, String actionQueryFile) throws Exception {
		boolean status = true;		// Hope for the best
		for (Schema schema: schemas) {
			try {
				schema.loadTables(hostName, userName, password);
				Tables tables = schema.getTables();	// Get the list of loaded tables.
				for (Table table: tables) {	// Get all the attributes from the tables
					table.setAttributes(Table.readAttributesFromTableDefinition(table.getTableName(), schema.getSchemaName()));
				}
				loadQueryDefinitions(schema);
				if ((actionQuerySQL != null) && (actionQuerySQL.trim().length() > 0)) {
					// Parse the action query and apply it to the schema topology
					SchemaImpact schemaImpact = new SchemaImpact();
					ActionQueryProcessor.processActionQuery(actionQuerySQL, schemaImpact);
//					QueryDefinition actionQueryDefinition = new QueryDefinition(hostName, userName, password, new QueryTypeUnknown(), "myActionQuery", actionQuerySQL, schema.getSchemaName());
//					actionQueryDefinition.crunchIt();
					applyActionQuery(schemaImpact, schema);
				}
				if ((actionQueryFile != null) && (actionQueryFile.trim().length() > 0)) {
					// Parse the action querys in the text file and apply them to the schema topology
					ActionQuerys actionQuerys = new ActionQuerys();
					actionQuerys.loadActionQueries(actionQueryFile);
					for (ActionQuery ac: actionQuerys) {
						SchemaImpact schemaImpact = new SchemaImpact();
						ActionQueryProcessor.processActionQuery(ac.getSql(), schemaImpact);
						applyActionQuery(schemaImpact, schema);
					}
				}				
				addNodesToGraph();
				
			} catch (Exception ex) {
				Log.logError("SchemaGraph.generateGraph(): " + ex.getLocalizedMessage());
				status = false;
			}
		}
		return schemaTopologyResults;
	}
	/**
	 * Apply an action query to the schema to see what views would be affected
	 * @param actionQueryDefinition The action query
	 */
//	private void applyActionQuery(QueryDefinition actionQueryDefinition) {
	private void applyActionQuery(SchemaImpact schemaImpact, Schema schema) {
		Log.logProgress("SchemaGraph.ApplyActionQueryDefinition()");
//		SchemaDiff schemaDiff = new SchemaDiff();
		try {
			for (QueryDefinition qd: schema.getQueryDefinitions()) {	// All the views in this schema
				for (QueryAttribute qa : qd.getQueryAttributes()) {	// All the attributes in the view
					if (schemaImpact.getQueryAttributes().findAttribute(qa)) {
						// The query attribute is referenced in the action query. We need to note that so when we draw the graph we can draw the attribute differently.
						qa.setAffectedByActionQuery(true);
						qd.getQueryTables().setAffectedByActionQuery(qa, true);
						schema.getTables().setAffectedByActionQuery(qa, true);
						schemaTopologyResults.incrementTotalAffectedAttributes();
					}
				}
			}
			// Create a GraphNodeAnnotation object that can be copied into any attributes that have suffered a change
			GraphNodeAnnotation graphNodeAnnotation = new GraphNodeAnnotation();
			graphNodeAnnotation.setGraphNodeAnnotation(GraphNodeAnnotation.GRAPH_NODE_ANNOTATION.Changed);
			for (Table table: schema.getTables()) {
				for (TableAttribute tableAttribute : table.getTableAttributes()) 
				if (schemaImpact.getTableAttributes().findAttributeByTableAndName(tableAttribute.getTableName(), tableAttribute.getAttributeName()) != null) {
					// The table attribute is referenced in the action query. We need to note that so when we draw the graph we can draw the attribute differently.
					tableAttribute.setAffectedByActionQuery(true);
					tableAttribute.setGraphNodeAnnotation(graphNodeAnnotation);
//					table.setAffectedByActionQuery(tableAttribute, true);
//					schema.getTables().setAffectedByActionQuery(tableAttribute, true);
					schemaTopologyResults.incrementTotalAffectedAttributes();
				}	
			}
		} catch (Exception ex) {
			Log.logError("SchemaGraph.ApplyActionQueryDefinition(): " + ex.getLocalizedMessage());
		}
//		return schemaDiff;
	}
	private void loadQueryDefinitions(Schema schema) {
		// Use all the queries in the schema
		// Populate our collection with all the query definitions in the schema
		ArrayList<String> queryNames = Schema.readQueryNames(hostName, schema.getSchemaName(), userName, password);
		QueryDefinition queryDefinition;
		for (String queryName : queryNames) {
			String sql;
			sql = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(hostName, userName, password, schema.getSchemaName(), queryName);
			queryDefinition = new QueryDefinition(hostName, userName, password, new QueryTypeUnknown(), queryName, sql, schema.getSchemaName() );
			schema.getQueryDefinitions().addQueryDefinition(queryDefinition);
			queryDefinition.crunchIt();
			schemaTopologyResults.incrementTotalQueries();
		}
	}
	/**
	 * Actually put nodes and edges on the graph. Finally.
	 */
	private void addNodesToGraph() {
		addAllConstraints();
		for (Schema schema: schemas) {
			addSchemaNode(schema);
			addQueryNodes(schema);
			addTableNodes(schema);
			addAttributeNodes(schema);
			addQueryToAttributeRelationships(schema);	// At this point we have the queries and the table attributes
		}
	}
	private void addSchemaNode(Schema schema) {
		if (schemaTopologyConfig.getIncludeSchemaNodeInGraph() == true) {
			addSchemaNode(schema.getSchemaName());
		}
	}
	public static void addSchemaNode(String schemaName) {
		Neo4jDB.submitNeo4jQuery("CREATE (" + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaName), "`") + ":" + schemaNodeLabel 
	               + " { key:"  + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaName),"\"") + ", "
	               + "   name:" + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaName), "\"") + "})");
	}
	private void addQueryNodes(Schema schema) {
		for (QueryDefinition queryDefinition : schema.getQueryDefinitions()) {
			String queryName;
			queryName = queryDefinition.getQueryName();
			addQueryNode(schema.getSchemaName(), queryName);
			if (schemaTopologyConfig.getIncludeSchemaNodeInGraph() == true) {
				// Add relationships from the schema to the queries
				connectSchemaNodeToQueryNode(schema.getSchemaName(), queryName);
			}
		}
	}
	public static void connectAttributeNodeToAttributeNode(String schemaNameFrom, String queryNameFrom, String attributeNameFrom,
            											   String schemaNameTo, String tableNameTo, String attributeNameTo) {
		Neo4jDB.submitNeo4jQuery("MATCH "
		        +       "(f:" + attributeNodeLabel + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaNameFrom) + "." + Utils.cleanForGraph(queryNameFrom) + "." + Utils.cleanForGraph(attributeNameFrom), "\"") + "}),"
		        + "      (t:" + attributeNodeLabel + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaNameTo)   + "." + Utils.cleanForGraph(tableNameTo)   + "." + Utils.cleanForGraph(attributeNameTo),   "\"") + "}) "
			    + "MERGE (f)-[:" + attributeToAttributeLabel +"]->(t)"); 
	}
	public static void connectQueryNodeToAttributeNode(String querySchemaName, String queryName,
                                                       String attributeSchemaName, String attributeTableName, String attributeName) {
		Neo4jDB.submitNeo4jQuery("MATCH "
        +       "(q:" + viewNodeLabel     + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(querySchemaName) + "." + Utils.cleanForGraph(queryName), "\"") + "}), "
        + "      (a:" + attributeNodeLabel + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(attributeSchemaName) 
     		                                                                + "." + Utils.cleanForGraph(attributeTableName) 
                                                                             + "." + Utils.cleanForGraph(attributeName), "\"") + "}) "
	       + "MERGE (q)-[:" + viewToAttributeLabel +"]->(a)"); 
/*		Neo4jDB.submitNeo4jQuery("MATCH "
		           +       "(q:" + queryNodeLabel     + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(querySchemaName) + "." + Utils.cleanForGraph(queryName), "\"") + "}), "
	               + "      (a:" + attributeNodeLabel + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(attributeSchemaName) 
	            		                                                                + "." + Utils.cleanForGraph(attributeTableName) 
	                                                                                    + "." + Utils.cleanForGraph(attributeName), "\"") + "}) "
			       + "CREATE (q)-[:" + queryToAttributeLabel +"]->(a)"); */
		
	}
	public static void addQueryNode(String schemaName, String queryName) {
		Neo4jDB.submitNeo4jQuery("CREATE (" + Utils.wrapInDelimiter(Utils.cleanForGraph(queryName),"`") + ":" + viewNodeLabel 
	               + " { key: " + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaName) + "." + Utils.cleanForGraph(queryName),"\"") 
	               + ", name:" + Utils.wrapInDelimiter(Utils.cleanForGraph(queryName),"\"") + "})");
	}
	public static void connectSchemaNodeToQueryNode(String schemaName, String queryName) {
		Neo4jDB.submitNeo4jQuery("MATCH "
		           +       "(q:" + viewNodeLabel  + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaName) + "." + Utils.cleanForGraph(queryName), "\"") + "}), "
	               + "      (s:" + schemaNodeLabel + "{key:" + Utils.wrapInDelimiter(Utils.cleanForGraph(schemaName), "\"") + "}) "
			       + "MERGE (s)-[:" + schemaToQueryLabel +"]->(q)");		
	}
	public static void addQueryAttribute(String schemaName, String tableName, String attributeName, String queryAttributeDataType) {
		Neo4jDB.submitNeo4jQuery("CREATE (" + 
								Utils.wrapInDelimiter(Utils.cleanForGraph(attributeName), "`") + 
				                ":" + 
								attributeNodeLabel + 
				                " { key: " 
				                + "\"" 
				                + Utils.cleanForGraph(schemaName)
				                + "." 
				                + Utils.cleanForGraph(tableName)
				                + "." 
				                + Utils.cleanForGraph(attributeName)
				                + "\"" 
				                + ", name:" 
				                + "\"" 
				                + Utils.cleanForGraph(attributeName)
				                + "\"" 
				                + ", table:"
				                + "\"" 
				                + Utils.cleanForGraph(tableName)
				                + "\""
				                + ", datatype:"
				                + "\"" 
				                + Utils.cleanForGraph(queryAttributeDataType)
				                + "\"" 
				                + "}"
				                + ")");
	}

	public static void addTableNode(String schemaName, String tableName) {
		Neo4jDB.submitNeo4jQuery("CREATE (" + Utils.cleanForGraph(tableName) + ":" + tableNodeLabel 
	               + " { key: " + "'" + Utils.cleanForGraph(schemaName) + "." + Utils.cleanForGraph(tableName) + "'" 
	               + ", name:'" + Utils.cleanForGraph(tableName) + "'})");
	}
	/**
	 *  Grab all the table names from the schema and drop in a node for each one
	 */
	private void addTableNodes(Schema schema) {
		Tables tables = schema.getTables();	// Get the list of loaded tables.
		for (Table table : tables) {
			addTableNode(schema.getSchemaName(), table.getTableName());
			schemaTopologyResults.incrementTotalTables();
			if (schemaTopologyConfig.getIncludeSchemaNodeInGraph() == true) {
				// Add relationships from the schema to the tables
				String key1, key2, relationshipKey;
				key1 = Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName());
				key2 = Utils.cleanForGraph(schema.getSchemaName());
				relationshipKey = key2 + "->" + key1;
				Neo4jDB.submitNeo4jQuery("MATCH "
				                       + "(t:" + tableNodeLabel  + "{key:'" + key1 + "'}), "
			                           + " (s:" + schemaNodeLabel + "{key:'" + key2 + "'}) "
					                   + "CREATE (s)-[:" + schemaToTableLabel + "{key:\"" + relationshipKey + "\"}]->(t)");
			}
		}
	}
	private void addAttributeNodes(Schema schema) {
		Tables tables = schema.getTables();	// Get the list of loaded tables. By now we have also populated the attribute collection in each table.
		for (Table table : tables) {
			for (TableAttribute attribute: table.getTableAttributes()) {
				schemaTopologyResults.incrementTotalAttributes();
				String nodeLabel;
				String key;
				key = Utils.cleanForGraph(schema.getSchemaName())
		                + "." 
		                + Utils.cleanForGraph(table.getTableName())
		                + "." 
		                + Utils.cleanForGraph(attribute.getAttributeName());
		        attribute.setKey(key);
				nodeLabel = SchemaGraph.computeNodeLabel(attribute.getGraphNodeAnnotation());						
				//if (attribute.getAffectedByActionQuery() == true) {nodeLabel = affectedAttributeNodeLabel;}
				Neo4jDB.submitNeo4jQuery("CREATE (" + 
						                 Utils.cleanForGraph(attribute.getAttributeName()) + 
				                         ":" + 
				                         nodeLabel + 
				                         " {key:" 
						                 + "\"" 
				                         + key
						                 + "\"" 
				                         + ", name:" 
						                 + "\"" 
				                         + Utils.cleanForGraph(attribute.getAttributeName())
						                 + "\"" 
				                         + ", table:"
						                 + "\"" 
				                         + Utils.cleanForGraph(attribute.getTableName())
						                 + "\"" 
				                         + "}"
				                         + ")");
				// Add the relationship between the table and the attribute now because we have everything we need.
				String key1, key2, relationshipKey;
				key1 = Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName());
				key2 = Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(table.getTableName()) + "." + Utils.cleanForGraph(attribute.getAttributeName());
				relationshipKey = key1 + "->" + key2;
				Neo4jDB.submitNeo4jQuery("MATCH (t:" + tableNodeLabel     
						               + "{key:"
					                   + "\"" 
						               + key1 
						               + "\"" 
						               + "}), "
				                       + " (a:" + nodeLabel 
				                       + "{key:" 
						               + "\"" 
				                       + key2  
						               + "\"" 
				                       + "}) "
						               + "MERGE (t)-[:" + tableToAttributeLabel + " {key:\"" + relationshipKey + "\"}]->(a)");
			}
		}
	}
	private void addQueryToAttributeRelationships(Schema schema) {
		for (QueryDefinition queryDefinition : schema.getQueryDefinitions()) {
			HashMap<String, QueryAttribute> queryAttributes = queryDefinition.getUniqueQueryAttributes(true);
			// traverse queryAttributes and add a relation from the query to the attribute
			for (QueryAttribute queryAttribute: queryAttributes.values()) {
				String neo4jQuery;
//				String nodeLabel;
//				nodeLabel = attributeNodeLabel;
//				if (queryAttribute.getAffectedByActionQuery() == true) {
//					nodeLabel = affectedAttributeNodeLabel;
//				}
				String key1, key2, relationshipKey;
				key1 = Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(queryDefinition.getQueryName());
				key2 = Utils.cleanForGraph(schema.getSchemaName()) + "." + Utils.cleanForGraph(queryAttribute.getTableName()) + "." + Utils.cleanForGraph(queryAttribute.getAttributeName());
				relationshipKey = key1 + "->" + key2;
				neo4jQuery = "MATCH "
				           + " (q:" + viewNodeLabel  + "{key:'" + key1 + "'}), "
			               + " (a:" + attributeNodeLabel + "{key:'" + key2 + "'}) "
					       + " MERGE (q)-[:" + viewToAttributeLabel +"]->(a)";
				Neo4jDB.submitNeo4jQuery(neo4jQuery);
				// This is hokey but it should work because we have a uniqueness constraint on the key attribute.
				// Only one of these two MATCH queries should work because the attribute is either a attributeNodeLabel or a affectedAttributeNodeLabel.
				neo4jQuery = "MATCH "
				           + " (q:" + viewNodeLabel  + "{key:'" + key1 + "'}), "
			               + " (a:" + affectedAttributeNodeLabel + "{key:'" + key2 + "'}) "
					       + " MERGE (q)-[:" + viewToAttributeLabel + " {key:\"" + relationshipKey + "\"}]->(a)";
				Neo4jDB.submitNeo4jQuery(neo4jQuery);
				schemaTopologyResults.incrementTotalQueryAttributes();
			}
		}
	}
	// Don't call these constraint methods individually. Rather, call addAllConstriants() instead.
	private static void addAffectedAttributeConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.affectedAttributeNodeLabel + ") ASSERT a.key IS UNIQUE");}
	private static void addAttributeConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.attributeNodeLabel + ") ASSERT a.key IS UNIQUE");}
	private static void addViewConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.viewNodeLabel + ") ASSERT a.key IS UNIQUE");}
	private static void addAffectedViewConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.affectedViewNodeLabel + ") ASSERT a.key IS UNIQUE");}
	private static void addTableConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.tableNodeLabel + ") ASSERT a.key IS UNIQUE");}
	private static void addSchemaConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.schemaNodeLabel + ") ASSERT a.key IS UNIQUE");}
	private static void addETLStepNodeConstraint() {Neo4jDB.submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + SchemaGraph.etlStepNodeLabel + ") ASSERT a.key IS UNIQUE");}

	public Schemas getSchemas() {
		return schemas;
	}

	public void setSchemas(Schemas schemas) {
		this.schemas = schemas;
	}
	public static String computeNodeLabel(GraphNodeAnnotation graphNodeAnnotation) {
		String nodeLabel;
		nodeLabel = SchemaGraph.attributeNodeLabel;
		if (Config.getConfig().isAdjustNodeLabelAsNodeIsAdded()) {
			if (graphNodeAnnotation.getGraphNodeAnnotation() == GraphNodeAnnotation.GRAPH_NODE_ANNOTATION.Changed) {
				nodeLabel = SchemaGraph.affectedAttributeNodeLabel;
			} else {
				nodeLabel = SchemaGraph.attributeNodeLabel;
			}
		}
		return nodeLabel;
	}
	
}
