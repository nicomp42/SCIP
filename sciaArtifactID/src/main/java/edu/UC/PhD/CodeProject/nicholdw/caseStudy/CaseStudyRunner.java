package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;

public class CaseStudyRunner {

	public static void run() {
		Log.logProgress("CaseStudyClassRunner.run()");
		
		CaseStudyEnvironment cse = new CaseStudyEnvironment();
		for (CaseStudyQuery csq : cse.getCaseStudyQuerys()) {
			Log.logProgress("CaseStudyClassRunner.run(): parsing " + csq.getDescription());
			// String hostName, String loginName, String password, QueryType queryType,String queryName, String sql, String schemaName) {
			QueryDefinition qd = new QueryDefinition("localhost", "root", "Danger42", null, "", csq.getSQL(), "");
			qd.crunchIt();
		}
	}
}
