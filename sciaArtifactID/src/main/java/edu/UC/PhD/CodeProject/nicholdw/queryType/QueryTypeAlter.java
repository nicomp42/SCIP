package edu.UC.PhD.CodeProject.nicholdw.queryType;

/**
 * A query that changes the structure of a table
 * @author nicomp
 */
public class QueryTypeAlter extends QueryType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3162310338240127704L;
	public QueryTypeAlter() {
		super("Alter");
	}
	public QueryTypeAlter(String type) {
		super(type);
	}

}
