/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CaseStudyQuerys implements Iterable<CaseStudyQuery> {
	private List<CaseStudyQuery> caseStudyQuerys;
	
	public CaseStudyQuerys() {
		setCaseStudyQuerys(new ArrayList<CaseStudyQuery>());
	}

	public List<CaseStudyQuery> getCaseStudyQuerys() {
		return caseStudyQuerys;
	}

	private void setCaseStudyQuerys(List<CaseStudyQuery> caseStudyQuerys) {
		this.caseStudyQuerys = caseStudyQuerys;
	}
	@Override
	public Iterator<CaseStudyQuery> iterator() {
		Iterator<CaseStudyQuery> iprof = caseStudyQuerys.iterator();
        return iprof;
    }
	public void setEnabled(String description, boolean enabled) {
		for (CaseStudyQuery csq: caseStudyQuerys) {
			if (description.equals(csq.getDescription())) {
				csq.setEnabled(enabled);
				break;
			}
		}
	}
	public String getSQL(String description) {
		String sql = "";
		for (CaseStudyQuery csq: caseStudyQuerys) {
			if (description.equals(csq.getDescription())) {
				sql = csq.getSQL();
				break;
			}
		}
		return sql;
	}
}
