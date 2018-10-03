package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaTopology;

public class ETLProcess {
	private String name;
	private String description;
	private ETLSteps etlSteps;
	private ETLConnections etlConnections;
	
	public ETLProcess() {
		etlSteps = new ETLSteps();
		etlConnections = new ETLConnections();
	}
	public void processTableInputSteps() {
		for (ETLStep etlStep : etlSteps) {
			if (etlStep.getStepType().equals("TableInput")) {
				processTableInputStep(etlStep);
			}
		}
	}
	public void processTableInputStep(ETLStep etlStep) {
		// Look up the connection 
		ETLConnection etlConnection = etlConnections.getConnection(etlStep.getConnection());
		// Parse the query
		etlStep.setQueryDefinition(new QueryDefinition(etlConnection.getServer(), 
				                                       etlConnection.getUserName(), 
				                                       etlConnection.getPasswordDefinedExternally(),
				                                       new QueryTypeSelect(),
				                                       "",				// No query name 'cause it's from an ETLStep 
				                                       etlStep.getSql(),
				                                       etlConnection.getDatabase()));
		etlStep.getQueryDefinition().crunchIt();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ETLSteps getETLSteps() {
		return etlSteps;
	}

	public void setEtlSteps(ETLSteps etlSteps) {
		this.etlSteps = etlSteps;
	}

	public ETLConnections getETLConnections() {
		return etlConnections;
	}

	public void setEtlConnections(ETLConnections etlConnections) {
		this.etlConnections = etlConnections;
	}
	public static void createGraph(ETLProcess etlProcess) {
		for (ETLStep etlStep : etlProcess.getETLSteps()) {
			if (etlStep.getStepType().equals("TableInput")) {
				// CREATE (n:Person { name: 'Andy', title: 'Developer' })
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaTopology.etlStepNodeLabel + 
				                         " { StepName: " + 
				                         "'" + etlStep.getStepName() 		+ "'" + 
				                         ", sql:'" + etlStep.getSql() 		+ "'" + 
				                         ",	table:'" + etlStep.getTable()	+ "'" +
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         ",	key:'" + etlStep.getStepName()	+ "'" +
				                         "})");
				QueryDefinition qd = etlStep.getQueryDefinition();
					// Add the nodes for the attributes in the query that this step uses
				for (QueryAttribute qa : qd.getQueryAttributes()) {
					String key = "";
					key = qa.getSchemaName() + "." + qa.getTableName() + "." + qa.getAttributeName(); 
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + 
	                         SchemaTopology.attributeNodeLabel +
	                         ":" + 
							 qa.getAttributeName() + 
	                         " { key: "
	                         + "'" 
							 + key
	                         + "'" 
	                         + ", name:'" 
	                         + qa.getAttributeName() 
	                         + "'})");
					Neo4jDB.submitNeo4jQuery("MATCH (t:" + SchemaTopology.attributeNodeLabel  + "{key:'" + key + "'}), "
				               				 +     "(a:" + SchemaTopology.etlStepNodeLabel    + "{key:'" + etlStep.getStepName() + "'}) "
				               				 + "CREATE (t)-[:" + SchemaTopology.etlStepToQueryAttributeLbel +"]->(a)");
				}
			} else if (etlStep.getStepType().equals("TableOutput")) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:ETLStep" + 
                        " { StepName: " + 
                        "'" + etlStep.getStepName() 		+ "'" + 
                        ",	table:'" + etlStep.getTable()	+ "'" +
                        ",	stepType:'" + etlStep.getStepType()	+ "'" +
                        "})");
			}
		}
	}
}
