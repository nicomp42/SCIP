package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

public class foo {

	public static void main(String[] args) {
		System.out.println(edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.foo.class.getName());	// Cannot make a static reference to the non-static method getClass() from the type Object
		foo f = new foo();								// This works but I don't want to instantiate an object
		System.out.println(f.getClass().getName());
		
	}

}
