package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */

/***
 * Model a Schema Evolution Operator
 * @author nicomp
 *
 */
public class Mapping extends EvolutionOperator {

	/***
	 * Constructor
	 * @param SchemaEvolutionOperator The SchemaEvolutionOperator to clone
	 * @throws EvolutionOperatorException
	 */
	public Mapping(Mapping mapping) throws EvolutionOperatorException {
		super((EvolutionOperator)mapping);
	}
	
	/***
	 * Constructor
	 * @param name The name of the operator
	 * @throws EvolutionOperatorException
	 */
	public Mapping(String name) throws EvolutionOperatorException {
		super(name);
	}
}
