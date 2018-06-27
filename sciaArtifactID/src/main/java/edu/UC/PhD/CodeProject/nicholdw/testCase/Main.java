package edu.UC.PhD.CodeProject.nicholdw.testCase;

import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;

public class Main {

	public static void main(String[] args) {
		try {
			ConnectionInformation connectionInformation = new ConnectionInformation("localhost", "root", "Danger42", "");
			FullCoverage fc = new FullCoverage("Full Coverage Test Case", connectionInformation);
			fc.run();
		} catch (Exception ex) {
			System.out.println("Main.main(): " + ex.getLocalizedMessage());
		}
	}
}
