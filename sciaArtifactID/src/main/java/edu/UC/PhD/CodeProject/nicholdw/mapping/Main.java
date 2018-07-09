package edu.UC.PhD.CodeProject.nicholdw.mapping;

public class Main {

	/***
	 * Test some stuff in this package
	 * @param args
	 */
	public static void main(String[] args) {
		Mapping myMapping = new Mapping("My Mapping");

		Mapping start = new Mapping("Mapping Start");
		Mapping end = new Mapping("Mapping End");
		
		myMapping.setStart(start);
		myMapping.setEnd(end);
		
		System.out.println("My Mapping = " + myMapping.getUniqueName());
		System.out.println("My Mapping Start= " + myMapping.getStart().getUniqueName());
		System.out.println("My Mapping End = " + myMapping.getEnd().getUniqueName());
	}

}
