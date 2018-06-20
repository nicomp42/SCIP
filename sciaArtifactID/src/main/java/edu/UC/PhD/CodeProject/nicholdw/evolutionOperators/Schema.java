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

	/***
	 * Constructor
	 * @param SchemaEvolutionOperator The SchemaEvolutionOperator to clone
	 * @throws EvolutionOperatorException
	 */
	public Schema(Schema schema) throws EvolutionOperatorException {
		super((EvolutionOperator)schema);
	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @throws EvolutionOperatorException
	 */
	public Schema(String name) throws EvolutionOperatorException {
		super(name);
	}
}
