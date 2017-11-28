package edu.UC.PhD.CodeProject.nicholdw.schemaTopology;

import java.util.ArrayList;
import java.util.HashMap;

import edu.UC.PhD.CodeProject.nicholdw.Attribute;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Tables;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jUtils;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.queryType.Unknown;

/**
 * Generate the Schema Topology graph
 * @author nicomp
 */
public class SchemaTopology {
	private SchemaTopologyResults schemaTopologyResults;
	private String hostName, userName, password;
	private String schemaName;
	private QueryDefinitions queryDefinitions;
	private Schema schema;
	private SchemaTopologyConfig schemaTopologyConfig;
	private static final String schemaNodeLabel = "Schema";
	private static final String queryNodeLabel = "Query";
	private static final String tableNodeLabel = "Table";
	private static final String attributeNodeLabel = "Attribute";
	private static final String tableToAttributeLabel = "contains";
	private static final String queryToAttributeLabel = "references";
	private static final String schemaToTableLabel = "has";
	private static final String schemaToQueryLabel = "encloses";

	public static void main(String[] args) {
		Log.logProgress("SchemaTopology.main(): working...");

		Neo4jUtils.setNeo4jConnectionParameters( Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
		Neo4jUtils.getDriver();
		Neo4jUtils.clearDB();
		SchemaTopologyConfig schemaTopologyConfig = new SchemaTopologyConfig();
		schemaTopologyConfig.setIncludeSchemaInGraph(false);
		schemaTopologyConfig.setUseFriendlyNameAsDisplayName(true);

		SchemaTopology schemaTopology = new SchemaTopology(schemaTopologyConfig, "localhost", "root", "Danger42", "schematopologytest", null);
		try {
			//SchemaTopologyResults schemaTopologyResults = new SchemaTopologyResults();
			schemaTopology.generateGraph();
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
	public SchemaTopology(SchemaTopologyConfig schemaTopologyConfig, String hostName, String userName, String password, String schemaName, QueryDefinitions queryDefinitions) {
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
		this.schemaName = schemaName;
		this.queryDefinitions = queryDefinitions;
		this.schemaTopologyConfig = schemaTopologyConfig;
		this.schemaTopologyResults = new SchemaTopologyResults();
	}
	public SchemaTopologyResults generateGraph() throws Exception {
		boolean status = true;		// Hope for the best
		schema = new Schema(schemaName);
		schema.loadTables(hostName, userName, password);			// Load the tables for the schema
		Tables tables = schema.getTables();	// Get the list of loaded tables.
		for (Table table : tables) {	// Get all the attributes from the tables
			table.setAttributes(Table.readAttributesFromTableDefinition(table.getTableName(), schemaName));
		}

		try {
			if (queryDefinitions == null || queryDefinitions.getQueryDefinitions().size() == 0) {
				loadQueryDefinitions();
				addNodesToGraph();
			} else {
				throw new Exception ("SchemaTopology.generateGraph(): subset of queries is not yet supported. Only 'all' queries can be processed");		// TODO implement this.
			}
		} catch (Exception ex) {
			Log.logError("SchemaTopology.generateGraph(): " + ex.getLocalizedMessage());
			status = false;
		}
		return schemaTopologyResults;
	}
	private void loadQueryDefinitions() {
		// Use all the queries in the schema
		queryDefinitions = new QueryDefinitions();
		// Populate our collection with all the query definitions in the schema
		ArrayList<String> queryNames = Schema.readQueryNames(hostName, schemaName, userName, password);
		QueryDefinition queryDefinition;
		for (String queryName : queryNames) {
			String sql;
			sql = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(hostName, userName, password, schemaName, queryName);
			queryDefinition = new QueryDefinition(hostName, userName, password, new Unknown(), queryName, sql, schemaName );
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
			submitNeo4jQuery("CREATE (" + schemaName + ":" + schemaNodeLabel + " { key: " + "'" + schemaName + "'" + ", name:'" + schemaName + "'})");
		}
	}
	private void addQueryNodes() {
		for (QueryDefinition queryDefinition : queryDefinitions) {
			String queryName;
			queryName = queryDefinition.getQueryName();
			submitNeo4jQuery("CREATE (" + queryName + ":" + queryNodeLabel + " { key: " + "'" + schemaName + "." + queryName + "'" + ", name:'" + queryName + "'})");
			if (schemaTopologyConfig.getIncludeSchemaInGraph() == true) {
				// Add relationships from the schema to the queries
				submitNeo4jQuery("MATCH "
				           +       "(q:" + queryNodeLabel  + "{key:'" + schemaName + "." + queryDefinition.getQueryName() + "'}), "
			               + "      (s:" + schemaNodeLabel + "{key:'" + schemaName + "'}) "
					       + "CREATE (s)-[:" + schemaToQueryLabel +"]->(q)");
			}
		}
	}
	private void addTableConstraint() {
		submitNeo4jQuery("CREATE CONSTRAINT ON (t:" + tableNodeLabel + ") ASSERT t.Key IS UNIQUE");
	}
	/**
	 *  Grab all the table names from the schema and drop in a node for each one
	 */
	private void addTableNodes() {
		Tables tables = schema.getTables();	// Get the list of loaded tables.
		for (Table table : tables) {
			submitNeo4jQuery("CREATE (" + table.getTableName() + ":" + tableNodeLabel + " { key: " + "'" + schemaName + "." + table.getTableName() + "'" + ", name:'" + table.getTableName() + "'})");
			schemaTopologyResults.incrementTotalTables();
			if (schemaTopologyConfig.getIncludeSchemaInGraph() == true) {
				// Add relationships from the schema to the tables
				submitNeo4jQuery("MATCH "
				           +       "(t:" + tableNodeLabel  + "{key:'" + schemaName + "." + table.getTableName() + "'}), "
			               + "      (s:" + schemaNodeLabel + "{key:'" + schemaName + "'}) "
					       + "CREATE (s)-[:" + schemaToTableLabel +"]->(t)");
			}
		}
	}
	private void addAttributeConstraint() {
		submitNeo4jQuery("CREATE CONSTRAINT ON (a:" + attributeNodeLabel + ") ASSERT a.Key IS UNIQUE");
	}
	private void addAttributeNodes() {
		Tables tables = schema.getTables();	// Get the list of loaded tables. By now we have also populated the attribute collection in each table.
		for (Table table : tables) {
			for (Attribute attribute: table.getAttributes()) {
				schemaTopologyResults.incrementTotalAttributes();
				submitNeo4jQuery("CREATE (" + attribute.getAttributeName() + ":" + attributeNodeLabel + " { key: " + "'" + schemaName + "." + table.getTableName() + "." + attribute.getAttributeName() + "'" + ", name:'" + attribute.getAttributeName() + "'})");
				// Add the relationship between the table and the attribute now because we have everything we need.
				submitNeo4jQuery("MATCH (t:" + tableNodeLabel     + "{key:'" + schemaName + "." + table.getTableName() + "'}), "
				               + "      (a:" + attributeNodeLabel + "{key:'" + schemaName + "." + table.getTableName() + "." + attribute.getAttributeName() + "'}) "
						       + "CREATE (t)-[:" + tableToAttributeLabel +"]->(a)");
			}
		}
	}
	private void addQueryToAttributeRelationships() {
		for (QueryDefinition queryDefinition : queryDefinitions) {
			HashMap<String, QueryAttribute> queryAttributes = queryDefinition.getUniqueQueryAttributes();
			// traverse queryAttributes and add a relation from the query to the attribute
			for (QueryAttribute queryAttribute: queryAttributes.values()) {
				submitNeo4jQuery("MATCH "
				           + "           (q:" + queryNodeLabel  + "{key:'" + schemaName + "." + queryDefinition.getQueryName() + "'}), "
			               + "           (a:" + attributeNodeLabel + "{key:'" + schemaName + "." + queryAttribute.getTableName() + "." + queryAttribute.getAttributeName() + "'}) "
					       + "    CREATE (q)-[:" + queryToAttributeLabel +"]->(a)");
				schemaTopologyResults.incrementTotalQueryAttributes();
			}
		}
	}
	private boolean submitNeo4jQuery(String query) {
		boolean status = true;		// Hope for the best
		try {
			Neo4jUtils.getDriver();
			Neo4jUtils.ExecActionQuery(query);
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}
}
