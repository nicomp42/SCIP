package edu.UC.PhD.CodeProject.nicholdw.testCase;

import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.EvolutionOperator;
import edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.EvolutionOperatorException;
import edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.Schema;

public class Main {

	public static void main(String[] args) {

/*		
		// A little test to see if our architecture is Ok
		try {
			Schema s = new Schema("Rename Schema", "RENAME SCHEMA S");
			TestCaseQuery tsq = new TestCaseQuery(s);
			EvolutionOperator e = tsq.getEvolutionOperator();
			System.out.println(e.toString());
			System.out.println(((Schema)e).getSQL());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
*/		
		
/*		
		try {
			ConnectionInformation connectionInformation = new ConnectionInformation("localhost", "root", "Danger42", "");
			FullCoverage fc = new FullCoverage("Full Coverage Test Case", connectionInformation);
			fc.run();
		} catch (Exception ex) {
			System.out.println("Main.main(): " + ex.getLocalizedMessage());
		}
*/
	}
}
