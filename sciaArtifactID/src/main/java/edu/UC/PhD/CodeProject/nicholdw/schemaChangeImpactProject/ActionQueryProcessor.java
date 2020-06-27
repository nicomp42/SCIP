/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.SchemaImpact;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.TableAttribute;
import edu.UC.PhD.CodeProject.nicholdw.TableAttributes;
import edu.UC.PhD.CodeProject.nicholdw.caseStudy.CaseStudyRunner;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.View;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterTableChangeColumn;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterTableDropColumn;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeCreateOrReplaceView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropForeignKey;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropSchema;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeRenameTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeUnknown;
import javafx.scene.control.TextArea;

public class ActionQueryProcessor {
	public static void processActionQuery(String sql, SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processActionQuery()");
		// String hostName, String loginName, String password, QueryType queryType,String queryName, String sql, String schemaName) {
		QueryDefinition qd = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
												 Config.getConfig().getMySQLDefaultLoginName(),
												 Config.getConfig().getMySQLDefaultPassword(),
												 new QueryTypeUnknown(), "", sql, "");
		schemaImpact.setQueryDefinition(qd);
		qd.crunchIt();
		// OK, we know what we have. 
		// Now we need to figure out what to do with it.
		// Be careful: an object is an instanceof even if the class is anywhere in the inheritance hierarchy.
		//   Therefore these if/else constructs must be from most specific to least specific
		if (qd.getQueryType() instanceof QueryTypeCreateOrReplaceView) {
			processCreateOrReplaceView(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeDropSchema) {
			processQueryTypeDropSchema(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeDropTable ) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropTable ");
			processQueryTypeDropATable(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeRenameTable ) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeRenameTable ");
			processQueryTypeRenameATable(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeAlterTableDropColumn) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTableDropColumn ");
			processQueryTypeAlterTableDropColumn(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeAlterTableChangeColumn ) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTableChangeColumn ");
			processQueryTypeAlterTableChangeColumn(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeAlterTable) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTable ");
			processQueryTypeAlterTable(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeDropForeignKey) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropForeignKey ");
			processQueryTypeDropForiegnKey(schemaImpact);
		} else if (qd.getQueryType() instanceof QueryTypeDropView) {
			Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropView ");
			processQueryTypeDropView(schemaImpact);
		}
	}
	private static void processQueryTypeDropView(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeDropView()");
		// What is the difference between the original view and the new version?
		View viewToDrop = schemaImpact.getQueryDefinition().getViewToDrop();
		schemaImpact.getViews().addView(new View(viewToDrop.getSchemaName(), viewToDrop.getViewName()));
		String sqlStart = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  			   Config.getConfig().getMySQLDefaultLoginName(),
													  			   Config.getConfig().getMySQLDefaultPassword(), 
													  			   viewToDrop.getSchemaName(), 
													  			   viewToDrop.getViewName());
		Log.logProgress("ActionQueryProcessor.run(): processDropView, view = " + viewToDrop.getSchemaName() + "." + viewToDrop.getViewName());
		QueryDefinition qdStart = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  Config.getConfig().getMySQLDefaultLoginName(),
													  Config.getConfig().getMySQLDefaultPassword(),
													  new QueryTypeUnknown(), "", sqlStart, "");
		qdStart.crunchIt();
		for (QueryAttribute qa : qdStart.getQueryAttributes()) {
			schemaImpact.getQueryAttributes().addAttribute(qa);
		}
	}
	private static void processQueryTypeDropForiegnKey(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeDropForiegnKey()");
		// Not exactly sure what to do with this one. 
	}
	private static void processQueryTypeAlterTableDropColumn(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeAlterTableDropColumn()");
		for (QueryAttribute queryAttribute : schemaImpact.getQueryDefinition().getQueryAttributes()) {
			schemaImpact.getTableAttributes().addAttribute(new TableAttribute(queryAttribute.getContainerName(), 
														   queryAttribute.getAttributeName()));
		}
	}
	private static void processQueryTypeAlterTableChangeColumn(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeAlterTableChangeColumn()");
		for (QueryAttribute queryAttribute : schemaImpact.getQueryDefinition().getQueryAttributes()) {
			schemaImpact.getTableAttributes().addAttribute(new TableAttribute(queryAttribute.getContainerName(), 
					                                  	   queryAttribute.getAttributeName()));
		}
	}
	private static void processQueryTypeAlterTable(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeAlterTable()");
		// The list of attributes in the query definition is what's being altered
		
	}
	private static void processQueryTypeRenameATable(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeRenameATable()");
		// Same as dropping a table
		// All the columns in the table will potentially impact the schema.
		try {
			Table tableToRename = schemaImpact.getQueryDefinition().getTableToRename();
			schemaImpact.getTables().addTable(new Table(tableToRename.getTableName(), tableToRename.getSchemaName()));
			TableAttributes tableAttributes;
			tableAttributes = QueryTable.readAttributesFromTableDefinition(schemaImpact.getQueryDefinition().getTableToRename().getTableName(), 
																		   schemaImpact.getQueryDefinition().getTableToRename().getSchemaName());
			for (TableAttribute ta: tableAttributes) {
				schemaImpact.getTableAttributes().addAttribute(ta);
			}
		} catch (Exception ex) {
			Log.logError("ActionQueryProcessor.processQueryTypeRenameATable()", ex);
		}
	}
	private static void processQueryTypeDropATable(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeDropATable()");
		// All the columns in the table will potentially impact the schema because they are, well, deleted.
		try {
			Table tableToDrop= schemaImpact.getQueryDefinition().getTableToDrop();
			schemaImpact.getTables().addTable(new Table(tableToDrop.getTableName(), tableToDrop.getSchemaName()));
			TableAttributes tableAttributes;
			tableAttributes = QueryTable.readAttributesFromTableDefinition(schemaImpact.getQueryDefinition().getTableToDrop().getTableName(), 
																		   schemaImpact.getQueryDefinition().getTableToDrop().getSchemaName());
			for (TableAttribute ta: tableAttributes) {
				schemaImpact.getTableAttributes().addAttribute(ta);
			}
		} catch (Exception ex) {
			Log.logError("ActionQueryProcessor.processQueryTypeRenameATable()", ex);
		}
	}
	private static void processCreateOrReplaceView(SchemaImpact schemaImpact) {
		// What is the difference between the original view and the new version?
		View viewStart = schemaImpact.getQueryDefinition().getViewToCreateOrAlter();
		String sqlStart = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  			   Config.getConfig().getMySQLDefaultLoginName(),
													  			   Config.getConfig().getMySQLDefaultPassword(), 
													  			   viewStart.getSchemaName(), 
													  			   viewStart.getViewName());
		Log.logProgress("ActionQueryProcessor.processCreateOrReplaceView():  QueryTypeCreateOrReplaceView, view = " + viewStart.toString() + "." + viewStart);
		QueryDefinition qdStart = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  Config.getConfig().getMySQLDefaultLoginName(),
													  Config.getConfig().getMySQLDefaultPassword(),
													  new QueryTypeUnknown(), "", sqlStart, "");
		qdStart.crunchIt();
		QueryAttributes qas = QueryDefinition.findMissingQueryAttributes(qdStart, schemaImpact.getQueryDefinition());
		for (QueryAttribute queryAttribute : qas) {
			schemaImpact.getQueryAttributes().addAttribute(queryAttribute);
		}
	}
	private static void processQueryTypeDropSchema(SchemaImpact schemaImpact) {
		Log.logProgress("ActionQueryProcessor.processQueryTypeDropSchema(): Dropping Schema (" + schemaImpact.getQueryDefinition().getSchemaToDrop() + ")");
		// Oh, my. Drop an entire schema? Yikes
		// We need all the tables and all the views in the schema
		ArrayList<String> viewNames;
		Schema schema = new Schema(schemaImpact.getQueryDefinition().getSchemaToDrop());
		schema.loadTables(Config.getConfig().getMySQLDefaultHostname(),
						  Config.getConfig().getMySQLDefaultLoginName(),
						  Config.getConfig().getMySQLDefaultPassword());
		viewNames = Schema.readQueryNames(Config.getConfig().getMySQLDefaultHostname(),
				schemaImpact.getQueryDefinition().getSchemaToDrop(),
				                   		  Config.getConfig().getMySQLDefaultLoginName(),
				                   		  Config.getConfig().getMySQLDefaultPassword());
		for (String viewName : viewNames) {
			QueryAttributes queryAttributes = null;
			String sqlStart = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
																	  			   	   Config.getConfig().getMySQLDefaultLoginName(),
																	  			   	   Config.getConfig().getMySQLDefaultPassword(), 
																	  			   	   schema.getSchemaName(), 
																	  			   	   viewName);
			QueryDefinition qd1 = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
					  								 Config.getConfig().getMySQLDefaultLoginName(),
					  								 Config.getConfig().getMySQLDefaultPassword(),
					  								 new QueryTypeSelect(), "", sqlStart, "");
			qd1.crunchIt();
			queryAttributes = qd1.getQueryAttributes();
			for (QueryAttribute qa : queryAttributes) {
				schemaImpact.getQueryAttributes().addAttribute(qa);
			}
		}
		for (Table table : schema.getTables()) {
			TableAttributes tableAttributes;
			tableAttributes = QueryTable.readAttributesFromTableDefinition(table.getTableName(), schema.getSchemaName());
			for (TableAttribute ta: tableAttributes) {
				schemaImpact.getTableAttributes().addAttribute(ta);
			}
		}
	}

}
