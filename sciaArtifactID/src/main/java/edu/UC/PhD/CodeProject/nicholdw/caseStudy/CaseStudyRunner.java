package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import javafx.scene.control.TextArea;

public class CaseStudyRunner {

	public static void run(CaseStudyEnvironment caseStudyEnvironment, TextArea txaProgress) {
		Log.logProgress("CaseStudyClassRunner.run()");
		try {
			for (CaseStudyQuery csq : caseStudyEnvironment.getCaseStudyQuerys()) {
				Log.logProgress("CaseStudyClassRunner.run(): parsing " + csq.getDescription());
				txaProgress.appendText(csq.getDescription() + "\n");
				// String hostName, String loginName, String password, QueryType queryType,String queryName, String sql, String schemaName) {
				QueryDefinition qd = new QueryDefinition("localhost", "root", "Danger42", null, "", csq.getSQL(), "");
				qd.crunchIt();
			}
		} catch (Exception ex) {
			Log.logError("CaseStudyRunner.run()", ex);
		}
	}
}
