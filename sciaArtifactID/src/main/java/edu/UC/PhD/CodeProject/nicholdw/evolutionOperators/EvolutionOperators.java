package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

import java.util.ArrayList;

public class EvolutionOperators {
	private ArrayList<EvolutionOperator> evolutionOperators;
	
	public EvolutionOperators() throws EvolutionOperatorException {
		evolutionOperators = new ArrayList<EvolutionOperator>();
	}

	/**
	 * Get a clone of the list of operators 
	 * @return The clone. 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<EvolutionOperator> getEvolutionOperators() {
			return (ArrayList<EvolutionOperator>) evolutionOperators.clone();
	}

	/**
	 * Define the list of operators
	 * @param evolutionOperators The list of operators to use. Any current operators are erased first.
	 * @throws EvolutionOperatorException 
	 */
	public void setEvolutionOperators(ArrayList<EvolutionOperator> evolutionOperators) throws EvolutionOperatorException {
		this.evolutionOperators.clear();
		for (EvolutionOperator eo: evolutionOperators) {
			System.out.println(eo.getClass().getName());
			this.addEvolutionOperator(eo);
		}
	}
	
	public void addEvolutionOperator(EvolutionOperator evolutionOperator) throws EvolutionOperatorException {
		switch (evolutionOperator.getClass().getName()) {
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.SchemaEvolutionOperator":
				this.evolutionOperators.add(new SchemaEvolutionOperator(evolutionOperator));
				break;	
		}
	}
	
	public String toString() {
		String myString = "";
		String comma = "";
		for (EvolutionOperator eo: evolutionOperators) {
			myString += comma + eo.getName().toString();
			comma = " ,";
		}
		return myString;
	}
}
