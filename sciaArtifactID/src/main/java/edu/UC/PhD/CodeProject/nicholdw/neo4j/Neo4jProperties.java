package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.HashMap;

public class Neo4jProperties {
    private HashMap<String, Neo4jProperty> neo4jProperties;

    public Neo4jProperties() {
    	neo4jProperties = new HashMap<String, Neo4jProperty>();
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
    
}
