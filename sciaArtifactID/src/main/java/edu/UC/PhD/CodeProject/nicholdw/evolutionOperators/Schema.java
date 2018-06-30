/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/***
 * Model a Schema Evolution Operator
 * @author nicomp
 *
 */
public class Schema extends EvolutionOperator {
	private String sql;
	public String getSQL() {
		return sql;
	}

	public void setSQL(String sql) {
		this.sql = sql;
	}

	/***
	 * Constructor
	 * @param SchemaEvolutionOperator The SchemaEvolutionOperator to clone
	 * @throws EvolutionOperatorException
	 */
	public Schema(Schema schema) throws EvolutionOperatorException {
		super((EvolutionOperator)schema);
		this.setSQL(schema.getSQL());
	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @throws EvolutionOperatorException
	 */
	public Schema(String name, String sql) throws EvolutionOperatorException {
		super(name);
		this.setSQL(sql);
	}
	
	public Schema() throws EvolutionOperatorException {super("?");}
	
	
}
