package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			
			
			
			
//			Mapping m = new Mapping("Add Mapping");
//			Mapping m1 = new Mapping("Drop Mapping");
//			Mapping m2 = new Mapping("Alter Mapping");

			EvolutionOperators eos = new EvolutionOperators();

			ArrayList<EvolutionOperator> eo = new ArrayList<EvolutionOperator>();
			eos.addEvolutionOperator(new Schema("Rename Schema", "RENAME SCHEMA S")); 
			eos.addEvolutionOperator(new Schema("Drop Schema", "DROP SCHEMA S")); 
			eos.addEvolutionOperator(new Table("Rename Table", "RENAME TABLE T T1")); 
			eos.addEvolutionOperator(new Table("Drop Table", "DROP TABLE T")); 
			eos.addEvolutionOperator(new TableAttribute("Rename Table Attribute", "")); 
			eos.addEvolutionOperator(new TableAttribute("Drop Table Attribute", "")); 
			eos.addEvolutionOperator(new TableAttribute("Change Table Attribute Data Type", ""));  
			eos.addEvolutionOperator(new Query("Rename Query", "RENAME QUERY Q Q1")); 
			eos.addEvolutionOperator(new Query("Drop Query", "DROP QUERY Q")); 
			eos.addEvolutionOperator(new QueryAttribute("Rename Query Attribute", "")); eo.add(new QueryAttribute("Drop Query Attribute", "")); 
			eos.addEvolutionOperator(new Mapping("Drop Mapping")); 
			eos.addEvolutionOperator(new Mapping("Edit Mapping"));
		
			System.out.println(eos.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
