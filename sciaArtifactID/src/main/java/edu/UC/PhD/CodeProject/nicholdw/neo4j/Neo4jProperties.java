package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.UC.PhD.CodeProject.nicholdw.Utils;

public class Neo4jProperties {
    private HashMap<String, Neo4jProperty> neo4jProperties;

    public Neo4jProperties() {
    	neo4jProperties = new HashMap<String, Neo4jProperty>();
    }
    /**
     * Format for use in a cypher CREATE statement
     * @return
     */
    public String formatNeo4jProperties() {
    	StringBuilder results = new StringBuilder();    
    	String comma = "";
    	for (Entry<String, Neo4jProperty> neo4jPropertyEntry : neo4jProperties.entrySet()) {
    		results.append(comma);		// It's blank the first time through this loop
    		results.append(Utils.QuoteMeBack(neo4jPropertyEntry.getKey()).toString());
    		results.append(":");
    		results.append(Utils.QuoteMeDouble(((Neo4jProperty)neo4jPropertyEntry.getValue()).toStringForCypher()));
    		comma = ",";
    	}
    	return results.toString();
    }

    public String toString() {
    	StringBuilder sb = new StringBuilder("(");
    	String space = " ";
    	for (Entry<String, Neo4jProperty> neo4jPropertyEntry : neo4jProperties.entrySet()) {
    		sb.append(space);
    		sb.append(((Neo4jProperty)neo4jPropertyEntry.getValue()).toString());
    		space = " ";
    	}
    	sb.append(")");
    	return sb.toString();
    }

    /***
     * Add a property to the collection
     * Use the name of the property as the key
     * @param key
     * @param neo4jProperty 
     */
    public void addNeo4jProperty(String key, Neo4jProperty neo4jProperty) {
    	neo4jProperties.put(key, neo4jProperty);
    }
    
    public HashMap<String, Neo4jProperty> getNeo4jProperties() {return neo4jProperties;} 
    
    public void clearMatchedFlags() {
    	for (Entry<String, Neo4jProperty> neo4jPropertyEntry : neo4jProperties.entrySet()) {
    		((Neo4jProperty)neo4jPropertyEntry.getValue()).setMatched(false);
    	}
    }
    public int countMatchedFlags() {
    	int count = 0;
    	for (Entry<String, Neo4jProperty> neo4jPropertyEntry : neo4jProperties.entrySet()) {
    		if (((Neo4jProperty)neo4jPropertyEntry.getValue()).isMatched() == true) {count++;}
    	}
    	return count;
    }
    /***
     * Scan the property list for the same property name only. The values are ignored
     * @param neo4jProperty
     * @return the property that was found or null if not found
     */
    public Neo4jProperty findPropertyByName(Neo4jProperty neo4jProperty) {
    	Neo4jProperty found = null;
    	for (Entry<String, Neo4jProperty> neo4jPropertyEntry : neo4jProperties.entrySet()) {
    		Neo4jProperty neo4jPropertyTest = (Neo4jProperty)(neo4jPropertyEntry).getValue();
    		if (!neo4jPropertyTest.isMatched() && neo4jPropertyTest.getName().equals(neo4jProperty.getName())) {	
//    			if (BagUtils.compareBags(neo4jPropertyTest.getNeo4jPropertyValues().getPropertyValues(), neo4jProperty.getNeo4jPropertyValues().getPropertyValues())) {
    				found = neo4jPropertyTest;
    				break;
 //   			}
    		}
    	}
    	return found;
    }

    /***
     * Scan the property list for the same {property name & bag of values}
     * @param neo4jProperty
     * @return the property that was found or null if not found
     */
    public Neo4jProperty findPropertyByNameAndAllValues(Neo4jProperty neo4jProperty) {
    	Neo4jProperty found = null;
    	for (Entry<String, Neo4jProperty> neo4jPropertyEntry : neo4jProperties.entrySet()) {
    		Neo4jProperty neo4jPropertyTest = (Neo4jProperty)(neo4jPropertyEntry).getValue();
    		if (!neo4jPropertyTest.isMatched() && neo4jPropertyTest.getName().equals(neo4jProperty.getName())) {	
    			if (BagUtils.compareBags(neo4jPropertyTest.getNeo4jPropertyValues().getPropertyValues(), neo4jProperty.getNeo4jPropertyValues().getPropertyValues())) {
    				found = neo4jPropertyTest;
    				break;
    			}
    		}
    	}
    	return found;
    }
}
