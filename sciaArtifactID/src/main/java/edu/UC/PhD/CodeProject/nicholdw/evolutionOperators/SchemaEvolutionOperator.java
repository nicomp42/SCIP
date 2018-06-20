package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

public class SchemaEvolutionOperator extends EvolutionOperator {

	public SchemaEvolutionOperator(EvolutionOperator evolutionOperator) throws EvolutionOperatorException {
		super(evolutionOperator);
	}
	
	public SchemaEvolutionOperator(String name) throws EvolutionOperatorException {
		super(name);
	}

}
