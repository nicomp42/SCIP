/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/***
 * Model a Table Evolution Operator
 * @author nicomp
 *
 */
public class Table extends EvolutionOperator {
	private String sql;
	public String getSQL() {
		return sql;
	}

	public void setSQL(String sql) {
		this.sql = sql;
	}

	/***
	 * Constructor
	 * @param table The table to clone
	 * @throws EvolutionOperatorException
	 */
	public Table(Table table) throws EvolutionOperatorException {
		super((EvolutionOperator)table);
		this.setSQL(table.getSQL());
	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @param sql the SQL statement that will do this thing
	 * @throws EvolutionOperatorException
	 */
	public Table(String name, String sql) throws EvolutionOperatorException {
		super(name);
		this.setSQL(sql);
		
	}
}
