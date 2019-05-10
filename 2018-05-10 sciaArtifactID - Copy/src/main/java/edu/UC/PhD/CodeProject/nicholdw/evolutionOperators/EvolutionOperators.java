package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

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
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.Schema":
				this.evolutionOperators.add(new Schema((Schema)evolutionOperator));
				break;	
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.Table":
				this.evolutionOperators.add(new Table((Table)evolutionOperator));
				break;	
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.TableAttribute":
				this.evolutionOperators.add(new TableAttribute((TableAttribute)evolutionOperator));
				break;	
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.Query":
				this.evolutionOperators.add(new Query((Query)evolutionOperator));
				break;	
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.QueryAttribute":
				this.evolutionOperators.add(new QueryAttribute((QueryAttribute)evolutionOperator));
				break;	
			case "edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.Mapping":
				this.evolutionOperators.add(new Mapping((Mapping)evolutionOperator));
				break;	
			default:
				Log.logError("EvolutionOperators.addEvolutionOperator(): + unidentified operator: " + evolutionOperator.getClass().getName()); 
		}
	}

	public String toString() {
		String myString = "";
		String comma = "";
		for (EvolutionOperator eo: evolutionOperators) {
			myString += comma + eo.getName().toString();
			comma = ", ";
		}
		return myString;
	}
}
