package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.ArrayList;
import java.util.Map.Entry;

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

	public Neo4jRelationship findRelationship(Neo4jRelationship neo4jRelationship) {
		Neo4jRelationship found = null;
		for (Neo4jRelationship neo4jRelationshipTest: neo4jRelationships) {
			if (Neo4jRelationship.compareRelationships(neo4jRelationship, neo4jRelationshipTest)) {
				found = neo4jRelationshipTest;
				break;
			}
		}		
		return found;
	}
	
	public void clearMatchedFlags() {
		for (Neo4jRelationship neo4jRelationship: neo4jRelationships) {
			neo4jRelationship.setMatched(false);
		}		
	}
	
    public int countMatchedFlags() {
    	int count = 0;
    	for (Neo4jRelationship neo4jRelationship: neo4jRelationships) {
    		if (neo4jRelationship.isMatched() == true) {count++;}
    	}
    	return count;
    }	
}
