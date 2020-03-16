/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;


/**
 * A variable that is declared in a SQL statement
 * @author nicomp
 *
 */
public class QueryVariable extends QueryComponent implements  java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 115898228578138015L;
	private String variableName;

	public String getVariableName() {return variableName;}
	public void setVariableName(String variableName) {this.variableName = variableName;}
	public String toString() {return getVariableName();}
	
}
