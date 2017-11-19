package edu.UC.PhD.CodeProject.nicholdw;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jUtils;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;

/**
 * Import from ETL schema CSV files into Neo4j DB
 * @author nicomp
 *
 */
public class ETLGraphController {

	public ETLGraphController() {	}

	public void generateRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateRelationships() ...");
		generateReconciledToETLDependencyRelationships(scip, etlLayer);
		generateETLToDWHDependencyRelationships(scip, etlLayer);
		generateDBLookupDependencyRelationships(scip, etlLayer);
		generateDBJoinDependencyRelationships(scip, etlLayer);
		generateDimLookupUpdateDependencyRelationships(scip, etlLayer);
		generateCombinationLookupUpdateDependencyRelationships(scip, etlLayer);
		generateInputToOutputStepsRelationships(scip);
		generateRelationToETLStepRelationships(scip, etlLayer);
		generateETLStepToRelationRelationships(scip, etlLayer);
		generateSchemaToETLStepRelationships(scip, etlLayer);
		generateETLStepToSchemaRelationships(scip, etlLayer);
		Log.logProgress("ETLGraphController.generateRelationships() done.");
	}
	public void generateETLStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateETLStepNodes() ...");
		generateInputStepNodes(scip, etlLayer);
		generateOutputStepNodes(scip, etlLayer);
		generateDBLookupStepNodes(scip, etlLayer);
		generateDBJoinStepNodes(scip, etlLayer);
		generateDimLookupUpdateStepNodes(scip, etlLayer);
		generateCombinationLookupUpdateStepNodes(scip, etlLayer);
		Log.logProgress("ETLGraphController.generateETLStepNodes() done.");
	}

	// Neo4jUtils.filePrefix + Utils.formatPath(SchemaChangeImpactProject.operationalSubDirectory)


	public void generateInputStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateInputStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
										+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "input_steps.csv\" AS line "
										+ "MERGE (st:Step{name: line.StepName, transname: line.TransformationName,"
										+ "steptype:\""+ "TableInput\"," + "layer:\'"+etlLayer+"\'})");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateInputStepNodes(): " + ex.getMessage());
		}
	}
	public void generateOutputStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateOutputStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "output_steps.csv\" AS line "
								+ "MERGE (st:Step{name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType, layer:\'"+etlLayer+"\'})");

		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateOutputStepNodes(): " + ex.getMessage());
		}

	}
	public void generateDBLookupStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateDBLookupStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dblookup_steps.csv\" AS line "
								+ "MERGE (st:Step{name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType, layer:\'"+etlLayer+"\'})");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateDBLookupStepNodes(): " + ex.getMessage());
		}
	}
	public void generateDBJoinStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateDBJoinStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dbjoin_steps.csv\" AS line "
								+ "MERGE (st:Step{name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType, layer:\'"+etlLayer+"\'})");

		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateDBJoinStepNodes(): " + ex.getMessage());
		}
	}
	public void generateDimLookupUpdateStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateDimLookupUpdateStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
								+ "MERGE (st:Step{name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType, update_lookup:line.isUpdateStep, layer:\'"+etlLayer+"\'})");

		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateDimLookupUpdateStepNodes(): " + ex.getMessage());
		}
	}
	public void generateCombinationLookupUpdateStepNodes(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateCombinationLookupUpdateStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "comblookupupdate_steps.csv\" AS line "
								+ "MERGE (st:Step{name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType, layer:\'"+etlLayer+"\'})");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateCombinationLookupUpdateStepNodes(): " + ex.getMessage());
		}
	}
	public void generateDimLookupUpdateDependencyRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateDimLookupUpdateDependencyRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Attribute{name: (line.AttributeName), schema:(line.DatabaseName), "
								+ "relation: line.TableName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType}) WHERE st.update_lookup=\'Update\'"
								+ " MERGE (st)-[:Impacts]->(a)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
								+ "MATCH (a:Attribute{name: (line.AttributeName), schema:(line.DatabaseName), "
								+ "relation: line.TableName})"
								+ "MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType}) WHERE st.update_lookup=\'Lookup\'"
								+ " MERGE (a)-[:Impacts]->(st)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateDimLookupUpdateDependencyRelationships(): " + ex.getMessage());
		}
	}
	public void generateCombinationLookupUpdateDependencyRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateCombinationLookupUpdateDependencyRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "comblookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Attribute{name: (line.AttributeName), schema:(line.DatabaseName), "
								+ "relation: line.TableName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (st)-[:Impacts]->(a)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateCombinationLookupUpdateDependencyRelationships(): " + ex.getMessage());
		}
	}
	public void generateETLToDWHDependencyRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateETLToDWHDependencyRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "output_steps.csv\" AS line "
								+ " MATCH (a:Attribute{name: (line.AttributeName), schema:(line.DatabaseName), "
								+ "relation: line.TableName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (st)-[:Impacts]->(a)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateETLToDWHDependencyRelationships(): " + ex.getMessage());
		}
	}
	public void generateDBLookupDependencyRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateDBLookupDependencyRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dblookup_steps.csv\" AS line "
								+ " MATCH (a:Attribute{name: (line.AttributeName), schema:(line.DatabaseName), "
								+ "relation: line.TableName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (a)-[:Impacts]->(st)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateDBLookupDependencyRelationships(): " + ex.getMessage());
		}
	}
	public void generateDBJoinDependencyRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateInputStepNodes()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dbjoin_steps.csv\" AS line "
								+ " MATCH (a:Attribute{name: (line.AttributeName), schema:(line.DatabaseName), "
								+ "relation: line.TableName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (a)-[:Impacts]->(st)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateDBJoinDependencyRelationships(): " + ex.getMessage());
		}
	}
	public void generateReconciledToETLDependencyRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateReconciledToETLDependencyRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "input_steps.csv\" AS line "
								+ " MATCH (a:Attribute{name: line.AttributeName, schema:line.DatabaseName, "
								+ "relation: line.TableName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype:\'"
								+ "TableInput\'" + "})"
								+ " MERGE (a)-[:Impacts]->(st)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateReconciledToETLDependencyRelationships(): " + ex.getMessage());
		}
	}
	public void generateInputToOutputStepsRelationships(SchemaChangeImpactProject scip) {
		Log.logProgress("ETLGraphController.generateInputToOutputStepsRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("MATCH (input:Step) where input.steptype=\'"
								+ "TableInput\'"
								+ " OR input.steptype=\'"
								+ "DBLookup\'"
								+ " OR input.steptype=\'"
								+ "DBJoin\'"
								+ " MATCH (output:Step)"
								+ " WHERE input.transname=output.transname AND input.steptype <> output.steptype AND "
								+ "(output.steptype=\'"
								+ "Insert/Update\'"
								+ " OR output.steptype=\'"
								+ "TableOutput\'"
								+ " OR output.steptype=\'"
								+ "DimensionLookup\'"
								+ " OR output.steptype=\'"
								+ "CombinationLookup\')"
								+ " MERGE (input)-[:Impacts]->(output)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateInputToOutputStepsRelationships(): " + ex.getMessage());
		}
	}

	public void generateRelationToETLStepRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateRelationToETLStepRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "input_steps.csv\" AS line "
								+ " MATCH (a:Relation{name: line.TableName, schema:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, steptype:\'"
								+ "TableInput\'" + "})"
								+ " MERGE (a)-[:Impacts]->(st)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
							+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dblookup_steps.csv\" AS line "
							+ " MATCH (a:Relation{name: line.TableName, schema:line.DatabaseName})"
							+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
							+ "steptype: line.StepType})"
							+ " MERGE (a)-[:Impacts]->(st)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
							+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
							+ " MATCH (a:Relation{name: line.TableName, schema:line.DatabaseName})"
							+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
							+ "steptype: line.StepType}) WHERE st.update_lookup=\'Lookup\'"
							+ " MERGE (a)-[:Impacts]->(st)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
							+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dbjoin_steps.csv\" AS line "
							+ " MATCH (a:Relation{name: (line.TableName), schema:(line.DatabaseName)})"
							+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
							+ "steptype: line.StepType})"
							+ " MERGE (a)-[:Impacts]->(st)");
			}
		 catch (Exception ex) {
				Log.logError("ETLGraphController.generateRelationToETLStepRelationships(): " + ex.getMessage());
		}
	}
	public void generateETLStepToRelationRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateETLStepToRelationRelationships()...");
		try {
				Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "output_steps.csv\" AS line "
								+ " MATCH (a:Relation{name: line.TableName, schema:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (st)-[:Impacts]->(a)");

				Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Relation{name:line.TableName, schema:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType}) WHERE st.update_lookup=\'Update\'"
								+ " MERGE (st)-[:Impacts]->(a)");

				Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "comblookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Relation{name: (line.TableName), schema:(line.DatabaseName)})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (st)-[:Impacts]->(a)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateETLStepToRelationRelationships(): " + ex.getMessage());
		}
	}
	public void generateSchemaToETLStepRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateSchemaToETLStepRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "input_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, steptype:\'"
								+ "TableInput\'" + "})"
								+ " MERGE (a)-[:Impacts]->(st)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dblookup_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (a)-[:Impacts]->(st)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType}) WHERE st.update_lookup=\'Lookup\'"
								+ " MERGE (a)-[:Impacts]->(st)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dbjoin_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (a)-[:Impacts]->(st)");
		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateSchemaToETLStepRelationships(): " + ex.getMessage());
		}
	}

	public void generateETLStepToSchemaRelationships(SchemaChangeImpactProject scip, String etlLayer) {
		Log.logProgress("ETLGraphController.generateETLStepToSchemaRelationships()...");
		try {
			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "output_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (st)-[:Impacts]->(a)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "dimlookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType}) WHERE st.update_lookup=\'Update\'"
								+ " MERGE (st)-[:Impacts]->(a)");

			Neo4jUtils.ExecActionQuery("LOAD CSV WITH HEADERS FROM \""
								+ Neo4jUtils.filePrefix + Utils.formatPath(etlLayer) + "comblookupupdate_steps.csv\" AS line "
								+ " MATCH (a:Schema{name:line.DatabaseName})"
								+ " MATCH (st:Step {name: line.StepName, transname: line.TransformationName, "
								+ "steptype: line.StepType})"
								+ " MERGE (st)-[:Impacts]->(a)");

		} catch (Exception ex) {
			Log.logError("ETLGraphController.generateETLStepToSchemaRelationships(): " + ex.getMessage());
		}
	}
}
