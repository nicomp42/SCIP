package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

public class Main {

	public static void main(String[] args) {
		try {
			FullCoverage fc = new FullCoverage("Full Coverage Test Case");
			fc.run();
		} catch (Exception ex) {
			System.out.println("Main.main(): " + ex.getLocalizedMessage());
		}
	}
}
