/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

/***
 * A Case Study from the dissertation
 * @author nicomp
 *
 */
public class CaseStudy {
	private String title;
	private CaseStudyEnvironment caseStudyEnvironment;
	
	public CaseStudy(String title) {
		setTitle(title);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CaseStudyEnvironment getCaseStudyEnvironment() {
		return caseStudyEnvironment;
	}
	public void setCaseStudyEnvironment(CaseStudyEnvironment caseStudyEnvironment) {
		this.caseStudyEnvironment = caseStudyEnvironment;
	}
}
