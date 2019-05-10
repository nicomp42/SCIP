/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/***
 * Model a Table Attribute Evolution Operator
 * @author nicomp
 *
 */
public class TableAttribute extends EvolutionOperator {
	private String sql;
	public String getSQL() {
		return sql;
	}

	public void setSQL(String sql) {
		this.sql = sql;
	}

	/***
	 * Constructor
	 * @param tableAttribute The tableAttribute to clone
	 * @throws EvolutionOperatorException
	 */
	public TableAttribute(TableAttribute tableAttribute) throws EvolutionOperatorException {
		super((EvolutionOperator)tableAttribute);
		this.setSQL(tableAttribute.getSQL());
	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @throws EvolutionOperatorException
	 */
	public TableAttribute(String name, String sql) throws EvolutionOperatorException {
		super(name);
		this.setSQL(sql);
	}
}
