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
}
