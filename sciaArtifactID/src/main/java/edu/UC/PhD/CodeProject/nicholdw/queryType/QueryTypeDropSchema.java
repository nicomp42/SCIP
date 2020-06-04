package edu.UC.PhD.CodeProject.nicholdw.queryType;

public  class QueryTypeDropSchema extends QueryType {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public QueryTypeDropSchema(String queryType) {
		super(queryType);
	}
	public QueryTypeDropSchema() {
		super("DROP SCHEMA");
	}
}
