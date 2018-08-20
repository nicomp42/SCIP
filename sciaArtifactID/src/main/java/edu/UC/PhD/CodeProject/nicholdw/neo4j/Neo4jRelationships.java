package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.ArrayList;

public class Neo4jRelationships {

	private ArrayList<Neo4jRelationship> neo4jRelationships;
	
	public Neo4jRelationships() {
		neo4jRelationships = new ArrayList<Neo4jRelationship>();
	}
	
	public void addRelationship(Neo4jRelationship neo4jRelationship) {
		neo4jRelationships.add(neo4jRelationship);
	}

	public ArrayList<Neo4jRelationship> getNeo4jRelationships() {return neo4jRelationships;}

	public String toString() { return neo4jRelationships.toString();}
	
}
