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

import edu.UC.PhD.CodeProject.nicholdw.Attributable;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDrop;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeInsert;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeRenameTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
/***
 * Model an entire ETL Transformation consisting of steps and connections.
 * This is one .KTR file
 * .KTR files are grouped together by a .KJB file
 * @author nicomp
 */
public class ETLKTRFile implements java.io.Serializable{
	private static final long serialVersionUID = 2729702901520146865L;
	private String name;
	private String description;
	private ETLSteps etlSteps;
	private ETLConnections etlConnections;
	private ETLTransformationFiles etlTransformationFiles;
	private ETLHops etlHops;
	private String transformationFileDirectory;	// Where all the Pentaho transformation XML files are. See etlTransformationFiles object herein.

	public ETLKTRFile(String transformationFileDirectory) {
		etlSteps = new ETLSteps();
		etlConnections = new ETLConnections();
		etlTransformationFiles = new ETLTransformationFiles();
		etlHops = new ETLHops();
		this.transformationFileDirectory = transformationFileDirectory;
		try {
			if (this.transformationFileDirectory != null) {
				etlTransformationFiles.loadETLTransformationFileNames(this.transformationFileDirectory);
			}
		} catch (Exception ex) {
			Log.logError("ETLProcess.ETLProcess: " + ex.getLocalizedMessage());
		}
	}
	/***
	 * Traverse the ETL Process for a particular query attribute 
	 * @param etlStep The step to start with
	 * @param qa The query attribute in question
	 */
	public static void traverseFromAttribute(ETLKTRFile etlProcess, ETLStep etlStep, Attributable qa) {
		System.out.println("ETLProcess.traverseFromAttribute(): Starting step = " + etlStep.toString() + ", attribute = " + qa.toString());
		Log.logProgress("ETLProcess.traverseFromAttribute(): " + etlProcess.toString() + ", " + etlStep.toString() + ", " + qa.toString());
		String fileName = etlStep.getFileName();
		ETLHop etlHopStart = etlProcess.getEtlHops().getETLHopWithStartStep(etlStep);
		if (etlHopStart != null) {
			while (etlHopStart != null) {
				System.out.println("ETLProcess.traverseFromAttribute(): Hop Start = " + etlHopStart.toString());
				Log.logProgress("ETLProcess.traverseFromAttribute(): Hop Start = " + etlHopStart.toString());
				ETLSteps etlSteps; etlSteps = etlProcess.getETLSteps();
				String toStepName; toStepName = etlHopStart.getToStepName();
				ETLStep etlStepNext; etlStepNext = etlSteps.getETLStep(toStepName, fileName);
//              .getETLStep(etlProcess.getEtlHops().getETLHop(etlHopStart.getToStepName()).getToStepName(), fileName);
				System.out.println("ETLProcess.traverseFromAttribute(): Next Step = " + etlStepNext.toString());
				Log.logProgress("ETLProcess.traverseFromAttribute(): Next Step = " + etlStepNext.toString());
				QueryAttribute attributeFoundInAttributeCollection; ETLField attributeFoundInETLFieldCollection;
				attributeFoundInAttributeCollection = null; attributeFoundInETLFieldCollection = null;
				try {
					QueryDefinition qd = etlStepNext.getQueryDefinition();
					QueryAttributes qas = qd.getQueryAttributes();
					attributeFoundInAttributeCollection = qas.findAttributeByNameOnly(qa);
				} catch (Exception ex) {
					Log.logError("ETLProcess.traverseFromAttribute(): checking for queryAttribute " , ex);
				}	// There may not be any attributes, that's OK
				try {
					attributeFoundInETLFieldCollection = etlStepNext.getETLFields().findETLFieldByColumnName(qa.getAttributeName());
				} catch (Exception ex) {}	// There may not be any attributes, that's OK
				if (attributeFoundInAttributeCollection != null) {
					System.out.println("ETLProcess.traverseFromAttribute(): Attribute found in this ETL Step (Query Attribute Collection).");
					Log.logProgress("ETLProcess.traverseFromAttribute(): Attribute found in this ETL Step (Query Attribute Collection).");
					GraphNodeAnnotation graphNodeAnnotation = new GraphNodeAnnotation();
					graphNodeAnnotation.setGraphNodeAnnotation(GraphNodeAnnotation.GRAPH_NODE_ANNOTATION.Changed);
					attributeFoundInAttributeCollection.setGraphNodeAnnotation(graphNodeAnnotation);
				} else {
					System.out.println("ETLProcess.traverseFromAttribute(): Attribute NOT found in this ETL Step (Query Attribute Collection).");
					Log.logProgress("ETLProcess.traverseFromAttribute(): Attribute NOT found in this ETL Step (Query Attribute Collection).");
				}
				if (attributeFoundInETLFieldCollection != null) {
					System.out.println("ETLProcess.traverseFromAttribute(): Attribute found in this ETL Step (ETL Field Collection).");
					Log.logProgress("ETLProcess.traverseFromAttribute(): Attribute found in this ETL Step (ETL Field Collection).");
					GraphNodeAnnotation graphNodeAnnotation = new GraphNodeAnnotation();
					graphNodeAnnotation.setGraphNodeAnnotation(GraphNodeAnnotation.GRAPH_NODE_ANNOTATION.Changed);
					attributeFoundInETLFieldCollection.setGraphNodeAnnotation(graphNodeAnnotation);					
				} else {
					System.out.println("ETLProcess.traverseFromAttribute(): Attribute NOT found in this ETL Step (ETL Field Collection).");
					Log.logProgress("ETLProcess.traverseFromAttribute(): Attribute NOT found in this ETL Step (ETL Field Collection).");
				}
				etlHopStart = etlProcess.getEtlHops().getETLHopWithStartStep(etlStepNext);
			}
		} else {
			Log.logProgress("ETLProcess.traverseFromAttribute(): No initial Hop Start found with this ETL step.");			
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
	public void processExecuteSQLStepQueries() {
		for (ETLStep etlStep : etlSteps) {
			if (etlStep.getStepType().equals("ExecSQL")) {
				processExecuteSQLScriptStepQuery(etlStep);
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
			} else if (etlStep.getStepType().equals("InsertUpdate")) {
				processTableInsertUpdateStepFields(transformationFileDirectory, etlStep);
			}
		}
	}
	/**
	 * Look up the fields for a Table Output Step and add them to the etlStep
	 * @param etlStep The ETLStep to receive the fields
	 */
	public void processTableInsertUpdateStepFields(String xmlFilePath, ETLStep etlStep) {
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
			etlFields = myXMLParser.getETLFieldsForInsertUpdateStep(xpath, doc, etlStep.getStepName());
			etlStep.addETLFields(etlFields);
		} catch (Exception ex) {		
			Log.logError("ETLProcess.processTableOutputStepFields(): " + ex.getLocalizedMessage());
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
	public void processExecuteSQLScriptStepQuery(ETLStep etlStep) {
		// Look up the connection 
		ETLConnection etlConnection = etlConnections.getConnection(etlStep.getConnection());
		// Parse the query
		etlStep.setQueryDefinition(new QueryDefinition(etlConnection.getServer(), 
				                                       etlConnection.getUserName(), 
				                                       etlConnection.getPasswordDefinedExternally(),
				                                       new QueryTypeInsert(),
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
	public static void createGraph(SchemaChangeImpactProject scip) {
		try {
			ETLKTRFile etlProcess = scip.getETLKTRFile();
			SchemaGraph.addAllConstraints();	// Force keys to be unique as the graph is drawn
			for (ETLStep etlStep : etlProcess.getETLSteps()) {
				Log.logProgress("ETLParser.createGraph(): ETL Step Type = " + etlStep.getStepType());
				// CREATE (n:Person { name: 'Andy', title: 'Developer' })
				if (etlStep.getStepType().equals("DBProc")) {
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
					                         " { StepName: " + "'" + etlStep.getStepName() 		+ "'" + 
					                         ", procedure:'" + etlStep.getProcedure()		+ "'" + 
					                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
					                         ",	key:'" + etlStep.getKey()	+ "'" +
					                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
					                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
					                         "})");

				} else if (etlStep.getStepType().equals("MergeJoin")) {
						Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
						                         " { StepName: " + "'" + etlStep.getStepName() + "'" + 
						                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
						                         ",	key:'" + etlStep.getKey()	+ "'" +
						                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
						                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
						                         "})");

				} else if (etlStep.getStepType().equals("TableInput")) {
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
					                         " { StepName: " + "'" + etlStep.getStepName() 		+ "'" + 
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
							  + "." + Utils.cleanForGraph(qa.getContainerName()) 
							  + "." + Utils.cleanForGraph(qa.getAttributeName());
						if (applyActionQuerys(scip, qa)) {
							ETLKTRFile.traverseFromAttribute(etlProcess, etlStep, qa);
						}
						String nodeLabel; nodeLabel = SchemaGraph.computeNodeLabel(qa.getGraphNodeAnnotation());
						Neo4jDB.submitNeo4jQuery("CREATE (A:"
						                         + nodeLabel
						                         + " { key: " + "'" + key + "'" 
						                         + ", name:" + "'" + Utils.cleanForGraph(qa.getAttributeName()) + "'"
						                         + buildAnnotationInfo(qa.getGraphNodeAnnotation())
	//					                         + ",	etlStage:'" + etlStep.getEtlStage()	+ "'" 
						                         + "})");
						Neo4jDB.submitNeo4jQuery("MATCH (t:" + nodeLabel  + " {key:'" + key + "'}), "
					               				 +     "(a:" + SchemaGraph.etlStepNodeLabel    + "{key:'" + etlStep.getKey() + "'}) "
					               				 + " MERGE (a)-[:" + SchemaGraph.etlStepToQueryAttributeLbel +"]->(t)");
					}
				} else if (etlStep.getStepType().equals("TableOutput") || etlStep.getStepType().equals("InsertUpdate")) {
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
						etlField.setContainerName(etlStep.getTableName());
						etlField.setSchemaName(etlStep.getSchemaName());
						key = Utils.cleanForGraph(etlStep.getSchemaName()) 
							  + "." + Utils.cleanForGraph(etlStep.getTableName()) 
							  + "." + Utils.cleanForGraph(etlField.getColumnName());
						String nodeLabel; nodeLabel = SchemaGraph.computeNodeLabel(etlField.getGraphNodeAnnotation());
	//					key = Utils.cleanForGraph(etlStep.getStepName()) + "." + Utils.cleanForGraph(etlField.getStreamName())	+ "." + Utils.cleanForGraph(etlField.getColumnName());
						if (applyActionQuerys(scip, etlField)) {
							ETLKTRFile.traverseFromAttribute(etlProcess, etlStep, etlField);
						}
						Neo4jDB.submitNeo4jQuery("CREATE (A:" + nodeLabel + 
		                                         " { FieldName: " + "'" + etlField.getStreamName() + ":" + etlField.getColumnName() + "'" + 
		                                         ",	key:'" + key + "'" +
		                                         ",	name:'" + etlField.getColumnName()	+ "'" +
						                         buildAnnotationInfo(etlField.getGraphNodeAnnotation()) +
		                                         "})");
						// Create a relationship between the ETL Step Node and the attribute node we just added
						Neo4jDB.submitNeo4jQuery("MATCH (t:" + nodeLabel  + "{key:'" + key + "'}), "
	              				                +     "(a:" + SchemaGraph.etlStepNodeLabel    + "{key:'" + etlStep.getKey() + "'}) "
	              				                + "MERGE (a)-[:" + SchemaGraph.etlFieldToETLStepLabel +"]->(t)");
					}		
				} else if (etlStep.getStepType().equals("ExecSQL")) {
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
	                        " { StepName: " + "'" + etlStep.getStepName() 		+ "'" + 
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
							  + "." + Utils.cleanForGraph(qa.getContainerName()) 
							  + "." + Utils.cleanForGraph(qa.getAttributeName());
						if (applyActionQuerys(scip, qa)) {
							ETLKTRFile.traverseFromAttribute(etlProcess, etlStep, qa);
						}
						String nodeLabel; nodeLabel = SchemaGraph.computeNodeLabel(qa.getGraphNodeAnnotation());						
						Neo4jDB.submitNeo4jQuery("CREATE (A:"
						                         + nodeLabel		/* SchemaGraph.attributeNodeLabel */
						                         + " { key: " + "'" + key + "'" 
						                         + ", name:"  + "'" + Utils.cleanForGraph(qa.getAttributeName()) + "'"
						                         + buildAnnotationInfo(qa.getGraphNodeAnnotation())
						                         //				                         + ",	etlStage:'" + etlStep.getEtlStage()	+ "'" 
						                         + "})");
						Neo4jDB.submitNeo4jQuery("MATCH (t:" + nodeLabel /*SchemaGraph.attributeNodeLabel*/  + "{key:'" + key + "'}), "
					               				 +     "(a:" + SchemaGraph.etlStepNodeLabel    + "{key:'" + etlStep.getKey() + "'}) "
					               				 + " MERGE (a)-[:" + SchemaGraph.etlStepToQueryAttributeLbel +"]->(t)");
					}
				} else {
					// All the other steps
					Neo4jDB.submitNeo4jQuery("CREATE (A:" + SchemaGraph.etlStepNodeLabel + 
	                         " { StepName: " + "'" + etlStep.getStepName() + "'" + 
	                         ",	stepType:'" + etlStep.getStepType()	+ "'" +
	                         ",	key:'" + etlStep.getKey()	+ "'" +
	                         ",	etlStage:'" + etlStep.getEtlStage()	+ "'" +
	                         ", TransformationFileName:'" + etlStep.getFileName() + "'" +
	                         "})");

//					Log.logError("ETLParser.applyActionQueries(): No logic to process ETL step type " + etlStep.getStepType());
				}			
			}
/*			for (ETLHop etlHop: etlProcess.getEtlHops()) {
				Neo4jDB.submitNeo4jQuery("CREATE (A:"
	                    + SchemaGraph.etlStepNodeLabel
	                    + " { key: " + "'" + etlHop.getKey() + "'" 
	                    + ", StepName:"  + "'" + etlHop.getStepName()) + "'"
	                    + ", TransformationFileName:"  + "'" + etlHop.getFileName()) + "'"
	                    + "})");
			} */
			/* Draw the hops as connections between steps */
			for (ETLHop etlHop: etlProcess.getEtlHops()) {
				Neo4jDB.submitNeo4jQuery("MATCH "
						               + "(t:" + SchemaGraph.etlStepNodeLabel +")"
	                                   + "," 
						               + "(f:" + SchemaGraph.etlStepNodeLabel +")"
						               + " WHERE "
						               + "t.StepName='" + etlHop.getToStepName() + "'"
						               + " AND "
						               + "f.TransformationFileName='" + etlHop.getFileName() + "'"
						               + " AND "
						               + "f.StepName='" + etlHop.getFromStepName() + "'"
						               + " AND "
						               + "t.TransformationFileName='" + etlHop.getFileName() + "'"
		  				               + " CREATE (f)-[:" + SchemaGraph.etlHopLabel +"{key:\"" + etlHop.getKey() +"\"}]->(t)");
//		                   + "CREATE (s)-[:" + schemaToTableLabel + "{key:\"" + relationshipKey + "\"}]->(t)");
			}
		} catch (Exception ex) {
			Log.logError("ETLProcess.createGraph(): " + ex.getLocalizedMessage());
		}
	}
	public static String buildAnnotationInfo(GraphNodeAnnotation graphNodeAnnotation) {
		String annotationInfo;
		if(graphNodeAnnotation.getGraphNodeAnnotation() == GraphNodeAnnotation.GRAPH_NODE_ANNOTATION.Changed) {
			annotationInfo = ", altered:"  + "'" + graphNodeAnnotation.getGraphNodeAnnotation().toString() + "'";
		} else {
			annotationInfo = "";
		}
		return annotationInfo;
	}
	/***
	 * @param scip Project where the Action Queries are
 	 * @param attribute The Query Attribute to the looked-for in the action queries
	 */
	public static Boolean applyActionQuerys(SchemaChangeImpactProject scip, Attributable attribute) {
		Boolean attributeAffected = false;
		// We have a graph and that's great. Now for the big finale...
		// Apply the action querys, if any, to the graph to highlight the affected nodes
		// Create a GraphNodeAnnotation object that we can use to change the affected nodes
		GraphNodeAnnotation graphNodeAnnotation = new GraphNodeAnnotation();
		graphNodeAnnotation.setGraphNodeAnnotation(GraphNodeAnnotation.GRAPH_NODE_ANNOTATION.Changed);
		for (QueryDefinition aqd : scip.GetActionQueryDefinitions()) {
			// ToDo: Make it work
			Object myQueryType = aqd.getQueryType();
			if (myQueryType instanceof QueryTypeAlterTable) {
				Log.logProgress("ETLProcessController.applyActionQueries(): It's an alter table query");
//				for (QueryAttribute aqa: aqd.getQueryAttributes()) {
					// find the same query in the original QueryDefintion and change the GraphNodeAnnotation
//					Log.logProgress("ETLProcessController.applyActionQueries: changing GraphNodeAttribute for " + aqa.toString());
//					if (aqd.getQueryAttributes().contains(qa, true)) {
					if (aqd.getQueryAttributes().contains(attribute, false)) {
						attribute.setGraphNodeAnnotation(graphNodeAnnotation);
						attribute.setAffectedByActionQuery(true);
						attributeAffected = true;
					}
//				}
			} else if (myQueryType instanceof QueryTypeDropTable ) {
				Log.logProgress("ETLProcessController.applyActionQueries(): It's a drop table query");
				// We need to get all the attributes in the table and then change each one that appears in the QueryAttributes collection
				
			} else if (myQueryType instanceof QueryTypeAlterView ) {
				Log.logProgress("ETLProcessController.applyActionQueries(): It's an alter view query");
				
			} else if (myQueryType instanceof QueryTypeRenameTable ) {
				Log.logProgress("ETLProcessController.applyActionQueries(): It's a rename table query");
			} else {
				Log.logError("ETLProcessController.applyActionQueries(): query type not recognized: " + myQueryType.toString() );
			}
		}
		return attributeAffected;
	}

	/**
	 * 
	 * @return A reference to the ETL Transformation files collection
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