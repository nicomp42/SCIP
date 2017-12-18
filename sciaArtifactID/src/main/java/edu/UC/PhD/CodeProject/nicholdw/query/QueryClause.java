package edu.UC.PhD.CodeProject.nicholdw.query;

public abstract class QueryClause extends QueryComponent {
	private String queryClauseName;

	public QueryClause(String queryClauseName){
		setQueryClauseName(queryClauseName);
	}
	public String getQueryClause() {return queryClauseName;}
	public void setQueryClauseName(String queryClauseName) {
		this.queryClauseName = queryClauseName;
	}
	public String toString() {return queryClauseName;}
}
