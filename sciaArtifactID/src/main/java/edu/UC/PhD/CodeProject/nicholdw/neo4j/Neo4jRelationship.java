package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.neo4j.graphdb.RelationshipType;

public class Neo4jRelationship {
	private long startNodeID;
	private long endNodeID;
	private RelationshipType relationshipType;
	private ArrayList<String> labels;
//    private HashMap<String, Object> properties;
    private HashMap<String, Neo4jPropertyValues> properties;
    private String name;

	public Neo4jRelationship() {
		labels = new ArrayList<String>();
		properties = new HashMap<String, Neo4jPropertyValues>();
	}
    public long getStartNodeID() {
		return startNodeID;
	}
	public void setStartNodeID(long startNodeID) {
		this.startNodeID = startNodeID;
	}
	public long getEndNodeID() {
		return endNodeID;
	}
	public void setEndNodeID(long otherNodeID) {
		this.endNodeID = otherNodeID;
	}
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}
	
	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}
	
	public void print(PrintStream device) {
		device.println(this.toString());
	}
	public ArrayList<String> getLabels() {
		return labels;
	}
	public HashMap<String, Neo4jPropertyValues> getProperties() {
		return properties;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {return name + ": " + properties.toString() + " [" + getStartNodeID() + "]---[" + getEndNodeID() + "]";}
	
}
