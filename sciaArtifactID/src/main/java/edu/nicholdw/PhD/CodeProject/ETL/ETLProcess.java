/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaTopology;
/***
 * Model an entire ETL Process consisting of steps and connections
 * @author nicomp
 */
public class ETLProcess {
	private String name;
	private String description;
	private ETLSteps etlSteps;
	private ETLConnections etlConnections;
	
	public ETLProcess() {
		etlSteps = new ETLSteps();
		etlConnections = new ETLConnections();
	}
	public void processTableInputStepQueries() {
		for (ETLStep etlStep : etlSteps) {
			if (etlStep.getStepType().equals("TableInput")) {
				processTableInputStepQuery(etlStep);
			}
		}
	}
	/**
	 * Step through all the Table Output Steps and look up the Fields for each step
	 */
	public void processTableOutputStepsFields(String xmlFilePath) {
		for (ETLStep etlStep : etlSteps) {
			Log.logProgress("ETLProcess.processTableOutputStepsFields(): ETL Step Name = " + etlStep.getStepName() + ", ETL Step Type = " + etlStep.getStepType());
			if (etlStep.getStepType().equals("TableOutput")) {
				processTableOutputStepFields(xmlFilePath, etlStep);
			}
		}
	}
	/**
	 * Look up the fields for a Table Output Step and add them to the etlStep
	 * @param etlStep The ETLStep to receive the fields
	 */
	public void processTableOutputStepFields(String xmlFilePath, ETLStep etlStep) {
		// Look up the connection 
		ETLConnection etlConnection = etlConnections.getConnection(etlStep.getConnection());
		// Read the fields into the etlStep object
		XMLParser myXMLParser = new XMLParser();
		//myXMLParser.getStepNames(xmlFilePath, stepNames);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			ETLFields etlFields;
			etlFields = myXMLParser.getETLFields(xpath, doc, etlStep.getStepName());
			etlStep.addETLFields(etlFields);
		} catch (Exception ex) {		
			Log.logError("ETLProcess.processTableOutputStepFields(): " + ex.getLocalizedMessage());
		}
	}
	public void processTableInputStepQuery(ETLStep etlStep) {
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
			// CREATE (n:Person { name: 'Andy', title: 'Developer' })
			if (etlStep.getStepType().equals("DBProc")) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaTopology.etlDBProcNodeLabel + 
				                         " { StepName: " + 
				                         "'" + etlStep.getStepName() 		+ "'" + 
				                         ", sql:'" + etlStep.getSql()		+ "'" + 
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         ",	key:'" + etlStep.getStepName()	+ "'" +
				                         "})");
				
			} else if (etlStep.getStepType().equals("TableInput")) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaTopology.etlStepNodeLabel + 
				                         " { StepName: " + 
				                         "'" + etlStep.getStepName() 		+ "'" + 
				                         ", sql:'" + etlStep.getSql().substring(0, 6) +"..." + "'" + 
				                         ",	table:'" + etlStep.getTable()	+ "'" +
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         ",	key:'" + etlStep.getStepName()	+ "'" +
				                         "})");
				QueryDefinition qd = etlStep.getQueryDefinition();
					// Add the nodes for the attributes in the query that this step uses
				for (QueryAttribute qa : qd.getQueryAttributes()) {
					String key = "";
					key = etlStep.getStepName() + "." + qa.getSchemaName() + "." + qa.getTableName() + "." + qa.getAttributeName();
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
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaTopology.etlStepNodeLabel + 
				                         " { StepName: " + "'" + etlStep.getStepName() + "'" + 
				                         ",	table:'" + etlStep.getTable()	+ "'" +
				                         ",	key:'" + etlStep.getStepName()	+ "'" +
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         "})");
				// There is no query here: we just need to step through all the fields that are accessed in the output table
				for (ETLField etlField: etlStep.getETLFields()) {
					String key;
					key = etlStep.getStepName() + ":" + etlField.getStreamName()	+ ":" + etlField.getColumnName();
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaTopology.etlFieldNodeLabel + 
	                         " { FieldName: " + "'" + etlField.getStreamName() + ":" + etlField.getColumnName() + "'" + 
	                         ",	key:'" + key + "'" +
	                         ",	stepName:'" + etlStep.getStepName()	+ "'" +
	                         "})");

					Neo4jDB.submitNeo4jQuery("MATCH (t:" + SchemaTopology.etlFieldNodeLabel  + "{key:'" + key + "'}), "
              				 +     "(a:" + SchemaTopology.etlStepNodeLabel    + "{key:'" + etlStep.getStepName() + "'}) "
              				 + "CREATE (t)-[:" + SchemaTopology.etlFieldToETLStepLabel +"]->(a)");
				}		
			}
		}
	}
}
