/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

/**
 * A query presented to the tool as part of a case study.
 * Intended to be action queries from Table 9 in the dissertation
 * @author nicomp
 *
 */
public class CaseStudyQuery {
	private String SQL;
	private String description;
	
	public CaseStudyQuery(String description, String SQL) {
		setSQL(SQL);
		setDescription(description);
	}

	public String getSQL() {
		return SQL;
	}

	public void setSQL(String SQL) {
		this.SQL = SQL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
