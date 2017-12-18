package edu.UC.PhD.CodeProject.nicholdw.query;

/**
 * A function name extracted from a SQL statement
 * @author nicomp
 *
 */
public class QueryFunction extends QueryComponent {
	private String functionName;
	private String schemaName;
	
	public QueryFunction(String functionName, String schemaName) throws Exception {this.setFunctionName(functionName); this.setSchemaName(schemaName);}
	public String getQueryFunctionName() {return functionName;}

	public void setFunctionName(String queryFunctionName) throws Exception {
		if (queryFunctionName.trim().length() == 0) {
			throw new Exception("QueryFunctionName.QueryFunctionName(): function name cannot be blank");
		}
		this.functionName = queryFunctionName;
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {this.schemaName = schemaName;}
}
