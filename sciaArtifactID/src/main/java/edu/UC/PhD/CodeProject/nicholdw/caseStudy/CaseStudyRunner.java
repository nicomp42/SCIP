package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
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
