package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			Schema seo = new Schema("Rename Schema");
			Schema seo1 = new Schema("Drop Schema");
			Schema seo2 = new Schema("Add Schema");

			Mapping m = new Mapping("Add Mapping");
			Mapping m1 = new Mapping("Drop Mapping");
			Mapping m2 = new Mapping("Alter Mapping");
			
			EvolutionOperators eos = new EvolutionOperators();

			ArrayList<EvolutionOperator> eo = new ArrayList<EvolutionOperator>();
			eo.add(seo); eo.add(seo1); eo.add(seo2);
			eo.add(m); eo.add(m1);eo.add(m2); 
			
			eos.setEvolutionOperators(eo);
			
			System.out.println(eos.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
