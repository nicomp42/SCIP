package edu.UC.PhD.CodeProject.nicholdw.queryType;

/**
 * A query that changes the structure of a table
 * @author nicomp
 */
public abstract class QueryTypeAlter extends QueryType {
	public QueryTypeAlter() {
		super("Alter");
	}
	public QueryTypeAlter(String type) {
		super(type);
	}

}
