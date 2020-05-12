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

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
/***
 * Model an entire ETL Process consisting of steps and connections
 * @author nicomp
 */
public class ETLProcess implements java.io.Serializable {
	private String name;
	private String description;
	private ETLSteps etlSteps;
	private ETLConnections etlConnections;
	private ETLTransformationFiles etlTransformationFiles;
	private ETLHops etlHops;
	private String transformationFileDirectory;	// Where all the Pentaho transformation XML files are. See etlTransformationFiles object herein.

	public ETLProcess(String transformationFileDirectory) {
		etlSteps = new ETLSteps();
		etlConnections = new ETLConnections();
		etlTransformationFiles = new ETLTransformationFiles();
		etlHops = new ETLHops();
		this.transformationFileDirectory = transformationFileDirectory;
		if (this.transformationFileDirectory != null) {
			etlTransformationFiles.loadETLTransformationFileNames(this.transformationFileDirectory);
		}
	}
	public void loadETLTransformationFiles() {
		if (this.transformationFileDirectory != null) {
			etlTransformationFiles.loadETLTransformationFileNames(this.transformationFileDirectory);
		}
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
	public void processTableOutputStepsFields() {
		for (ETLStep etlStep : etlSteps) {
			Log.logProgress("ETLProcess.processTableOutputStepsFields(): ETL Step Name = " + etlStep.getStepName() + ", ETL Step Type = " + etlStep.getStepType());
			if (etlStep.getStepType().equals("TableOutput")) {
				processTableOutputStepFields(transformationFileDirectory, etlStep);
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
			doc = builder.parse(xmlFilePath + etlStep.getFileName());
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
		SchemaGraph.addAllConstraints();	// Force keys to be unique as the graph is drawn
		for (ETLStep etlStep : etlProcess.getETLSteps()) {
			Log.logProgress("ETLParser.createGraph(): ETL Step Type = " + etlStep.getStepType());
			// CREATE (n:Person { name: 'Andy', title: 'Developer' })
			if (etlStep.getStepType().equals("DBProc")) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
				                         " { StepName: " + 
				                         "'" + etlStep.getStepName() 		+ "'" + 
				                         ", procedure:'" + etlStep.getProcedure()		+ "'" + 
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         ",	key:'" + etlStep.getStepName()	+ "'" +
				                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
				                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
				                         "})");
				
			} else if (etlStep.getStepType().equals("MergeJoin")) {
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
					                         " { StepName: " + 
					                         "'" + etlStep.getStepName() 		+ "'" + 
					                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
					                         ",	key:'" + etlStep.getStepName()	+ "'" +
					                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
					                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
					                         "})");

			} else if (etlStep.getStepType().equals("TableInput")) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
				                         " { StepName: " + 
				                         "'" + etlStep.getStepName() 		+ "'" + 
				                         ", sql:'" + etlStep.getSql().substring(0, 6) +"..." + "'" + 
				                         ",	table:'" + etlStep.getTableName()	+ "'" +
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         ",	key:'" + etlStep.getKey()	+ "'" +
				                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
				                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
				                         "})");
				QueryDefinition qd = etlStep.getQueryDefinition();
					// Add the nodes for the attributes in the query that this step uses
				for (QueryAttribute qa : qd.getQueryAttributes()) {
					String key = "";
					key = Utils.cleanForGraph(qa.getSchemaName()) 
						  + "." + Utils.cleanForGraph(qa.getTableName()) 
						  + "." + Utils.cleanForGraph(qa.getAttributeName());
					Neo4jDB.submitNeo4jQuery("CREATE (A:"
					                         + SchemaGraph.attributeNodeLabel
					                         + " { key: "
					                         + "'" + key + "'" 
					                         + ", name:" 
					                         + "'" + Utils.cleanForGraph(qa.getAttributeName()) + "'"
//					                         + ",	etlStage:'" + etlStep.getEtlStage()	+ "'" 
					                         + "})");
					Neo4jDB.submitNeo4jQuery("MATCH (t:" + SchemaGraph.attributeNodeLabel  + "{key:'" + key + "'}), "
				               				 +     "(a:" + SchemaGraph.etlStepNodeLabel    + "{key:'" + etlStep.getStepName() + "'}) "
				               				 + "CREATE (a)-[:" + SchemaGraph.etlStepToQueryAttributeLbel +"]->(t)");
				}
			} else if (etlStep.getStepType().equals("TableOutput")) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
				                         " { StepName: " + "'" + etlStep.getStepName() + "'" + 
				                         ",	table:'" + etlStep.getTableName()	+ "'" +
				                         ",	key:'" + etlStep.getKey()	+ "'" +
				                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
				                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
				                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
				                         "})");
				// There is no query here: we just need to step through all the fields that are accessed in the output table
				for (ETLField etlField: etlStep.getETLFields()) {
					String key;
					key = Utils.cleanForGraph(etlStep.getSchemaName()) 
						  + "." + Utils.cleanForGraph(etlStep.getTableName()) 
						  + "." + Utils.cleanForGraph(etlField.getColumnName());
//					key = Utils.cleanForGraph(etlStep.getStepName()) + "." + Utils.cleanForGraph(etlField.getStreamName())	+ "." + Utils.cleanForGraph(etlField.getColumnName());
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.attributeNodeLabel + 
	                                         " { FieldName: " + "'" + etlField.getStreamName() + ":" + etlField.getColumnName() + "'" + 
	                                         ",	key:'" + key + "'" +
	                                         ",	name:'" + etlField.getColumnName()	+ "'" +
	                                         "})");
					if (key.compareTo("..employeenumber") == 0) {
						Log.logProgress("..employeenumber");
					}
					// Create a relationship between the ETL Step Node and the attribute node we just added
					Neo4jDB.submitNeo4jQuery("MATCH (t:" + SchemaGraph.attributeNodeLabel  + "{key:'" + key + "'}), "
              				                +     "(a:" + SchemaGraph.etlStepNodeLabel    + "{key:'" + etlStep.getKey() + "'}) "
              				                + "CREATE (a)-[:" + SchemaGraph.etlFieldToETLStepLabel +"]->(t)");
				}		
			} else {
				Log.logError("ETLParser.createGraph(): No logic to process ETL step type " + etlStep.getStepType());
			}			
		}
		/* Draw the hops as connections between steps */
		for (ETLHop etlHop: etlProcess.getEtlHops()) {
//			MATCH (t:ETLStep), (f:ETLStep) 
//			WHERE t.StepName='WriteToTemporaryTable' AND t.TransformationFileName='SmallTestOfTableInputAndTableOutput.ktr' 
//			AND  f.StepName='ReadFromSales.TransactionTable' AND f.TransformationFileName='SmallTestOfTableInputAndTableOutput.ktr'  
//			CREATE (t)-[:Hop]->(f)			
			Neo4jDB.submitNeo4jQuery("MATCH "
					               + "(t:" +  SchemaGraph.etlStepNodeLabel +")"
                                   + "," 
					               + "(f:" +  SchemaGraph.etlStepNodeLabel +")"
					               + " WHERE "
					               + "t.StepName='" + etlHop.getToStepName() + "'"
					               + " AND "
					               + "f.TransformationFileName='" + etlHop.getFileName() + "'"
					               + " AND "
					               + "f.StepName='" + etlHop.getFromStepName() + "'"
					               + " AND "
					               + "t.TransformationFileName='" + etlHop.getFileName() + "'"				               
	  				               + " CREATE (f)-[:" + SchemaGraph.etlHopLabel +"]->(t)");
		}
	}
	/**
	 * ToDo: this is risky.
	 * @return
	 */
	public ETLTransformationFiles getEtlTransformationFiles() {
		return etlTransformationFiles;
	}
/*	public void setEtlTransformationFiles(ETLTransformationFiles etlTransformationFiles) {
		this.etlTransformationFiles = etlTransformationFiles;
	} */
	public String getTransformationFileDirectory() {
		return transformationFileDirectory;
	}
	public void setTransformationFileDirectory(String transformationFileDirectory) {
		this.transformationFileDirectory = transformationFileDirectory;
	}
	public ETLHops getEtlHops() {
		return etlHops;
	}
	public void setEtlHops(ETLHops etlHops) {
		this.etlHops.clone(etlHops);
	}
}
