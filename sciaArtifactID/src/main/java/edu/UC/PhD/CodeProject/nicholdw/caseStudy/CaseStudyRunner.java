package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.Attribute;
import edu.UC.PhD.CodeProject.nicholdw.Attributes;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.Table;
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

public class CaseStudyRunner {
	private TextArea txaProgress;
	public void run(CaseStudyEnvironment caseStudyEnvironment, TextArea txaProgress) {
		this.txaProgress = txaProgress;
		Log.logProgress("CaseStudyClassRunner.run()");
		try {
			for (CaseStudyQuery csq : caseStudyEnvironment.getCaseStudyQuerys()) {
				if (csq.getEnabled() == true) {
					try {
						Log.logProgress("CaseStudyClassRunner.run(): parsing " + csq.getDescription());
						txaProgress.appendText(csq.getDescription() + "\n");
						// String hostName, String loginName, String password, QueryType queryType,String queryName, String sql, String schemaName) {
						QueryDefinition qd = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
																 Config.getConfig().getMySQLDefaultLoginName(),
																 Config.getConfig().getMySQLDefaultPassword(),
																 new QueryTypeUnknown(), "", csq.getSQL(), "");
						qd.crunchIt();
						txaProgress.appendText(" Query identified as " + qd.getQueryType().toString() + "\n");
						// OK, we know what we have. 
						// Now we need to figure out what to do with it.
						// Be careful: an object is an instanceof even if the class is anywhere in the inheritance heirarchy.
						//   Therefore these if/else constructs must be from most specific to least specific
						if (qd.getQueryType() instanceof QueryTypeCreateOrReplaceView) {
							processCreateOrReplaceView(qd);
						} else if (qd.getQueryType() instanceof QueryTypeDropSchema) {
							processQueryTypeDropSchema(qd);
						} else if (qd.getQueryType() instanceof QueryTypeDropTable ) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropTable ");
							processQueryTypeDropATable(qd);
						} else if (qd.getQueryType() instanceof QueryTypeRenameTable ) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeRenameTable ");
							processQueryTypeRenameATable(qd);
						} else if (qd.getQueryType() instanceof QueryTypeAlterTableDropColumn) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTableDropColumn ");
							processQueryTypeAlterTableDropColumn(qd);
						} else if (qd.getQueryType() instanceof QueryTypeAlterTableChangeColumn ) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTableChangeColumn ");
							processQueryTypeAlterTableChangeColumn(qd);
						} else if (qd.getQueryType() instanceof QueryTypeAlterTable) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTable ");
							processQueryTypeAlterTable(qd);
						} else if (qd.getQueryType() instanceof QueryTypeDropForeignKey) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropForeignKey ");
							processQueryTypeDropForiegnKey(qd);
						} else if (qd.getQueryType() instanceof QueryTypeDropView) {
							Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropView ");
							processQueryTypeDropView(qd);
						}
					} catch (Exception ex1) {
						Log.logError("CaseStudyRunner.run()", ex1);
						txaProgress.appendText(" * " + ex1.getLocalizedMessage() + "\n");
					}
				}
			}
		} catch (Exception ex) {
			Log.logError("CaseStudyRunner.run()", ex);
			txaProgress.appendText("* " + ex.getLocalizedMessage() + "\n");
		}
	}
	private QueryAttributes processQueryTypeDropView(QueryDefinition qd) {
		// What is the difference between the original view and the new version?
		View viewToDrop = qd.getViewToDrop();
		String sqlStart = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  			   Config.getConfig().getMySQLDefaultLoginName(),
													  			   Config.getConfig().getMySQLDefaultPassword(), 
													  			   viewToDrop.getSchemaName(), 
													  			   viewToDrop.getViewName());
		Log.logProgress("CaseStudyClassRunner.run(): processDropView, view = " + viewToDrop.getSchemaName() + "." + viewToDrop.getViewName());
		QueryDefinition qdStart = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  Config.getConfig().getMySQLDefaultLoginName(),
													  Config.getConfig().getMySQLDefaultPassword(),
													  new QueryTypeUnknown(), "", sqlStart, "");
		qdStart.crunchIt();
		txaProgress.appendText("   View Attributes affected by dropping " + qd.getViewToDrop().toString() + ": \n");
		for (QueryAttribute qa : qdStart.getQueryAttributes()) {
			txaProgress.appendText("    " + qa.toString() + "\n");
		}		
		return qdStart.getQueryAttributes();
	}
	private Attributes processQueryTypeDropForiegnKey(QueryDefinition qd) {
		// Not exactly sure what to do with this one. 
		txaProgress.appendText("   Foreign Key affected: \n");
		txaProgress.appendText("    " + qd.getForeignKeyToDrop() + "\n");
		return null;
	}
	private Attributes processQueryTypeAlterTableDropColumn(QueryDefinition qd) {
		Attributes tableAttributesAggregate = new Attributes(); 
		for (QueryAttribute queryAttribute : qd.getQueryAttributes()) {
			tableAttributesAggregate.addAttribute(new Attribute(queryAttribute.getTableName(), 
					                                            queryAttribute.getAttributeName()));
		}
		txaProgress.appendText("   Table Attributes affected: \n");
		for (Attribute ta : tableAttributesAggregate) {
			txaProgress.appendText("    " + ta.toString() + "\n");
		}
		return tableAttributesAggregate;
	}
	private Attributes processQueryTypeAlterTableChangeColumn(QueryDefinition qd) {
		Attributes tableAttributesAggregate = new Attributes(); 
		for (QueryAttribute queryAttribute : qd.getQueryAttributes()) {
			tableAttributesAggregate.addAttribute(new Attribute(queryAttribute.getTableName(), 
					                                            queryAttribute.getAttributeName()));
		}
		txaProgress.appendText("   Table Attributes affected: \n");
		for (Attribute ta : tableAttributesAggregate) {
			txaProgress.appendText("    " + ta.toString() + "\n");
		}
		return tableAttributesAggregate;
	}
	private void processQueryTypeAlterTable(QueryDefinition qd) {
		txaProgress.appendText("   Altering a Table(" + qd.getTableToAlter().toString() + ")" + "\n");
		// The list of attributes in the query definition is what's being altered
		
	}
	private Attributes processQueryTypeRenameATable(QueryDefinition qd) {
		txaProgress.appendText("   Renaming a Table(" + qd.getTableToRename().toString() + ")" + "\n");
		// Same as dropping a table
		// All the columns in the table will potentially impact the schema.
		Attributes tableAttributes;
		Attributes tableAttributesAggregate = new Attributes(); 
		tableAttributes = QueryTable.readAttributesFromTableDefinition(qd.getTableToRename().getTableName(), 
																	   qd.getTableToRename().getSchemaName());
		for (Attribute ta: tableAttributes) {
			tableAttributesAggregate.addAttribute(ta);
		}
		txaProgress.appendText("   Table Attributes affected: \n");
		for (Attribute ta : tableAttributesAggregate) {
			txaProgress.appendText("    " + ta.toString() + "\n");
		}
		return tableAttributesAggregate;
	}
	private Attributes processQueryTypeDropATable(QueryDefinition qd) {
		txaProgress.appendText("   Dropping a Table(" + qd.getTableToDrop().toString() + ")" + "\n");
		// All the columns in the table will potentially impact the schema because they are, well, deleted.
		Attributes tableAttributes;
		Attributes tableAttributesAggregate = new Attributes(); 
		tableAttributes = QueryTable.readAttributesFromTableDefinition(qd.getTableToDrop().getTableName(), 
																	   qd.getTableToDrop().getSchemaName());
		for (Attribute ta: tableAttributes) {
			tableAttributesAggregate.addAttribute(ta);
		}
		txaProgress.appendText("   Table Attributes affected: \n");
		for (Attribute ta : tableAttributesAggregate) {
			txaProgress.appendText("    " + ta.toString() + "\n");
		}
		return tableAttributesAggregate;
	}
	private void processCreateOrReplaceView(QueryDefinition qd) {
		// What is the difference between the original view and the new version?
		View viewStart = qd.getViewToCreateOrAlter();
		String sqlStart = QueryDefinition.readSQLFromDatabaseServerQueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  			   Config.getConfig().getMySQLDefaultLoginName(),
													  			   Config.getConfig().getMySQLDefaultPassword(), 
													  			   viewStart.getSchemaName(), 
													  			   viewStart.getViewName());
		Log.logProgress("CaseStudyClassRunner.run(): QueryTypeCreateOrReplaceView, view = " + viewStart.toString() + "." + viewStart);
		QueryDefinition qdStart = new QueryDefinition(Config.getConfig().getMySQLDefaultHostname(),
													  Config.getConfig().getMySQLDefaultLoginName(),
													  Config.getConfig().getMySQLDefaultPassword(),
													  new QueryTypeUnknown(), "", sqlStart, "");
		qdStart.crunchIt();
		QueryAttributes qas = QueryDefinition.findMissingQueryAttributes(qdStart, qd);
		// Now we have a list of missing attributes in the new version of the view. 
		if (qas.size() > 0) {
			for (QueryAttribute qa : qas) {
				txaProgress.appendText(" Query Attribute change: " + qa.toString() + "\n");
			}
		} else {
			txaProgress.appendText(" No attribute changes detected" + "\n");
		}
		
	}
	private void processQueryTypeDropSchema(QueryDefinition qd) {
		Log.logProgress("   Dropping Schema (" + qd.getSchemaToDrop() + ")");
		txaProgress.appendText("Query identified as " + qd.getQueryType().toString() + " (" + qd.getSchemaToDrop() + ")\n");
		// Oh, my. Drop an entire schema? Yikes
		// We need all the tables and all the views in the schema
		ArrayList<String> viewNames;
		Schema schema = new Schema(qd.getSchemaToDrop());
		schema.loadTables(Config.getConfig().getMySQLDefaultHostname(),
						  Config.getConfig().getMySQLDefaultLoginName(),
						  Config.getConfig().getMySQLDefaultPassword());
		QueryAttributes queryAttributesAggregate = new QueryAttributes();
		Attributes tableAttributesAggregate = new Attributes(); 
		viewNames = Schema.readQueryNames(Config.getConfig().getMySQLDefaultHostname(),
				                   		  qd.getSchemaToDrop(),
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
				queryAttributesAggregate.addAttribute(qa);
			}
		}
		for (Table table : schema.getTables()) {
			Attributes tableAttributes;
			tableAttributes = QueryTable.readAttributesFromTableDefinition(table.getTableName(), schema.getSchemaName());
			for (Attribute ta: tableAttributes) {
				tableAttributesAggregate.addAttribute(ta);
			}
		}
		txaProgress.appendText("  View Attributes affected: \n");
		for (QueryAttribute qa : queryAttributesAggregate) {
			txaProgress.appendText("    " + qa.toString() + "\n");
		}
		txaProgress.appendText("  Table Attributes affected: \n");
		for (Attribute ta : tableAttributesAggregate) {
			txaProgress.appendText("    " + ta.toString() + "\n");
		}
	}
}
