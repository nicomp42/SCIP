package edu.UC.PhD.CodeProject.nicholdw.evolutionOperators;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			
			Mapping m = new Mapping("Add Mapping");
			Mapping m1 = new Mapping("Drop Mapping");
			Mapping m2 = new Mapping("Alter Mapping");

			EvolutionOperators eos = new EvolutionOperators();

			ArrayList<EvolutionOperator> eo = new ArrayList<EvolutionOperator>();
			eo.add(new Schema("Rename Schema", "RENAME SCHEMA S")); eo.add(new Schema("Drop Schema", "DROP SCHEMA S")); 
			eo.add(new Table("Rename Table", "RENAME TABLE T T1")); eo.add(new Table("Drop Table", "DROP TABLE T")); 
			eo.add(new TableAttribute("Rename Table Attribute", "")); eo.add(new TableAttribute("Drop Table Attribute", "")); eo.add(new TableAttribute("Change Table Attribute Data Type", ""));  
			
			eo.add(new Query("Rename Query", "RENAME QUERY Q Q1")); eo.add(new Query("Drop Query", "DROP QUERY Q")); 
			eo.add(new QueryAttribute("Rename Query Attribute", "")); eo.add(new QueryAttribute("Drop Query Attribute", "")); 
					
			eo.add(new Mapping("Drop Mapping")); eo.add(new Mapping("Edit Mapping"));

			eos.setEvolutionOperators(eo);
			
			System.out.println(eos.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
