package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.View;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeCreateOrReplaceView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropSchema;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeUnknown;
import javafx.scene.control.TextArea;

public class CaseStudyRunner {

	public static void run(CaseStudyEnvironment caseStudyEnvironment, TextArea txaProgress) {
		Log.logProgress("CaseStudyClassRunner.run()");
		try {
			for (CaseStudyQuery csq : caseStudyEnvironment.getCaseStudyQuerys()) {
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
					// Now we need to figure out what to do with it
					if (qd.getQueryType() instanceof QueryTypeCreateOrReplaceView) {
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
					} else if (qd.getQueryType() instanceof QueryTypeDropSchema) {
						Log.logProgress("CaseStudyClassRunner.run(): QueryTypeDropSchema ");
						
					} else if (qd.getQueryType() instanceof QueryTypeAlterTable) {
						Log.logProgress("CaseStudyClassRunner.run(): QueryTypeAlterTable ");

					}
				} catch (Exception ex1) {
					Log.logError("CaseStudyRunner.run()", ex1);
					txaProgress.appendText(" * " + ex1.getLocalizedMessage() + "\n");
				}
			}
		} catch (Exception ex) {
			Log.logError("CaseStudyRunner.run()", ex);
			txaProgress.appendText("* " + ex.getLocalizedMessage() + "\n");
		}
	}
}
