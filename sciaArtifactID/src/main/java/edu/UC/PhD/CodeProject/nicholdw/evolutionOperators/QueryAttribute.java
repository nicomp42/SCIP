/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/***
 * Model a Query Attribute Evolution Operator
 * @author nicomp
 *
 */
public class QueryAttribute extends EvolutionOperator {
	private String sql;
	public String getSQL() {
		return sql;
	}

	public void setSQL(String sql) {
		this.sql = sql;
	}

	/***
	 * Constructor
	 * @param queryAttribute The queryAttribute to clone
	 * @throws EvolutionOperatorException
	 */
	public QueryAttribute(QueryAttribute queryAttribute) throws EvolutionOperatorException {
		super((EvolutionOperator)queryAttribute);
		this.setSQL(queryAttribute.getSQL());

	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @throws EvolutionOperatorException
	 */
	public QueryAttribute(String name, String sql) throws EvolutionOperatorException {
		super(name);
		this.setSQL(sql);
	}
}
