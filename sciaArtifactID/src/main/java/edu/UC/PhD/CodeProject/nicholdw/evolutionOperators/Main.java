package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			SchemaEvolutionOperator seo = new SchemaEvolutionOperator("Schema Evolution Operator #1");
			SchemaEvolutionOperator seo1 = new SchemaEvolutionOperator("Schema Evolution Operator #2");
			
			EvolutionOperators eos = new EvolutionOperators();

			ArrayList<EvolutionOperator> eo = new ArrayList<EvolutionOperator>();
			eo.add(seo);
			eo.add(seo1);
			
			eos.setEvolutionOperators(eo);
			
			System.out.println(eos.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
