package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.neo4j.graphdb.RelationshipType;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class Neo4jRelationship {
	private long startNodeID;
	private long endNodeID;
	private RelationshipType relationshipType;	// TODO : this should be abstracted. It's currently a native artifact of the Neo4j ecosystem.
	private ArrayList<String> labels;
//    private HashMap<String, Neo4jPropertyValues> properties;
	private Neo4jProperties neo4jProperties;
    private String name;
    private boolean matched;
	private long ID; // The ID of the relationship, as established by the GraphDB engine. This may overlap a Node ID!

	public Neo4jRelationship() {
		labels = new ArrayList<String>();
		neo4jProperties = new Neo4jProperties();
		matched = false;
		ID = -1;		// Invalid node ID
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
	public void setEndNodeID(long endNodeID) {
		this.endNodeID = endNodeID;
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
	public Neo4jProperties getProperties() {
		return neo4jProperties;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Format the name property. If it's blank, replace it with a message saying so.
	 * @return The formatted name
	 */
	public String formatName() {
		if (name.trim().length() > 0) {
			return name.trim();
		} else {
			return "{no name}";
		}
	}
	public String toString() {return "Relationship ID = " + ID + " type = {" + name + "} property = " + neo4jProperties.toString() + " [" + getStartNodeID() + "]---[" + getEndNodeID() + "]";}

	public static boolean compareRelationships(Neo4jRelationship r1, Neo4jRelationship r2) {
		Log.logProgress("Neo4jRelationship.compareRelationships(): comparing " + r1.getName() + " and " + r2.getName());
		boolean match = false;
		if (r1.getName().equals(r2.getName())) {
			if (BagUtils.compareBags(r1.getLabels(), r2.getLabels())) {
				Log.logProgress("Neo4jRelationship.compareRelationships(): labels match");
				r1.getProperties().clearMatchedFlags();
				r2.getProperties().clearMatchedFlags();
				for (Entry<String, Neo4jProperty> neo4jPropertyEntry : r1.getProperties().getNeo4jProperties().entrySet()) {
					Neo4jProperty neo4jProperty = (Neo4jProperty) neo4jPropertyEntry.getValue();
					Neo4jProperty neo4jPropertyFound = r2.getProperties().findPropertyByNameAndAllValues(neo4jProperty);
					if (neo4jPropertyFound != null) {
						neo4jProperty.setMatched(true);
						neo4jPropertyFound.setMatched(true);
					}
				}
				if (r1.getProperties().countMatchedFlags() == r1.getProperties().getNeo4jProperties().size() && 
					r2.getProperties().countMatchedFlags() == r2.getProperties().getNeo4jProperties().size()) {
					Log.logProgress("Neo4jRelationship.compareRelationships(): ALL properties match.");
					match = true;
				}
			} else {Log.logProgress("Neo4jRelationship.compareRelationships(): labels do not match");}
		}
		return match;
	}
	public boolean isMatched() {
		return matched;
	}
	public void setMatched(boolean matched) {
		this.matched = matched;
	}
	public long getID() {
		return ID;
	}
	public void setID(long ID) {
		this.ID = ID;
	}
}
