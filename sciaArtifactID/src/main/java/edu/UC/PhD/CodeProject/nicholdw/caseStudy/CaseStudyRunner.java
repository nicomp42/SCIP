/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import edu.UC.PhD.CodeProject.nicholdw.TableAttribute;
import edu.UC.PhD.CodeProject.nicholdw.SchemaImpact;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.ActionQueryProcessor;
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
						Log.logProgress("CaseStudyClassRunner.run(): processing " + csq.getDescription());
						appendText(csq.getDescription());
						appendText(csq.getSQL());
						SchemaImpact schemaImpact = new SchemaImpact();
						ActionQueryProcessor.processActionQuery(csq.getSQL(), schemaImpact);
						appendText(" Query identified as " + schemaImpact.getQueryDefinition().getQueryType().toString());
						displayResults(schemaImpact);
						appendDelimiter();
					} catch (Exception ex1) {
						Log.logError("CaseStudyRunner.run()", ex1);
						appendText(" * " + ex1.getLocalizedMessage() + "\n");
					}
				}
			}
		} catch (Exception ex) {
			Log.logError("CaseStudyRunner.run()", ex);
			txaProgress.appendText("* " + ex.getLocalizedMessage() + "\n");
		}
	}
	private void appendText(String message) {
		try {
			txaProgress.appendText(message + "\n");
		} catch (Exception ex) {}
	}
	private void appendDelimiter() {
		try {
			txaProgress.appendText("*****************************" + "\n");
		} catch (Exception ex) {}
	}
	private void displayResults(SchemaImpact schemaImpact) {
		if (schemaImpact.getQueryAttributes().size() > 0) {
			appendText("  Query Attributes impacted:");
			for (QueryAttribute qa : schemaImpact.getQueryAttributes()) {
				appendText("   " + qa.toString());
			}
		} else {
			appendText ("  No Query Attributes impacted.");
		}
		if (schemaImpact.getTableAttributes().size() > 0) {
			appendText("  Table Attributes impacted:");
			for (TableAttribute qa : schemaImpact.getTableAttributes()) {
				appendText("   " + qa.toString());
			}
		} else {
			appendText ("  No Table Attributes impacted.");
		}
	}
}
