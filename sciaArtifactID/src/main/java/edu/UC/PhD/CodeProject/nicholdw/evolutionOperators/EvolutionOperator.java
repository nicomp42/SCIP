/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

/**
 * Something that can be done to change a DW artifact
 * @author nicomp
 *
 */
public abstract class EvolutionOperator {
	private String name;

	/**
	 * Copy Constructor
	 * @param evolutionOperator the thing to be copied 
	 * @throws EvolutionOperatorException 
	 */
	public EvolutionOperator(EvolutionOperator evolutionOperator) throws EvolutionOperatorException { 
		this.setName(evolutionOperator.getName());
	}
	
	public EvolutionOperator(String name) throws EvolutionOperatorException {
		this.setName(name);
	}
	/** 
	 * Get name of the operation
	 * @return the name of the operation
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the name of the evolution operator.
	 * @param name Cannot be blank
	 * @throws EvolutionOperatorException if name is blank
	 */
	public void setName(String name) throws EvolutionOperatorException {
		if (name.trim().length() > 0) {
			this.name = name.trim();
		} else {
			throw new EvolutionOperatorException("EvolutionOperator.setName(): name cannot be blank."); 
		}
	}
	public String toString() {return name;}
}
