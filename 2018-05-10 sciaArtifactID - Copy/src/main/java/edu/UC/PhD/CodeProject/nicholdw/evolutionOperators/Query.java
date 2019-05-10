/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/***
 * Model a Query Evolution Operator
 * @author nicomp
 *
 */
public class Query extends EvolutionOperator {
	private String sql;
	
	/***
	 * Constructor
	 * @param query The query to clone
	 * @throws EvolutionOperatorException
	 */
	public Query(Query query) throws EvolutionOperatorException {
		super((EvolutionOperator)query);
		this.setSQL(query.getSQL());
	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @throws EvolutionOperatorException
	 */
	public Query(String name, String sql) throws EvolutionOperatorException {
		super(name);
		this.setSQL(sql);
	}

	public String getSQL() {
		return sql;
	}

	public void setSQL(String sql) {
		this.sql = sql;
	}
}
