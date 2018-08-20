/*
 * In mathematics, a bag (multi-set) is an unordered collection of elements with duplicates.
 */

package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * An abstracted version of the Neo4j Node. We need this because the Node objects we read from the Graph DB can't be accessed unless in a transaction.
 * @author nicomp
 *
 */
public class Neo4jNode {

	private String value;
	private String key;
	private ArrayList<String> labels;
    private HashMap<String, Object> properties;
    private Neo4jRelationships neo4jRelationships;
    private long nodeID;			// The Node ID established by the GraphDB engine
	
	public Neo4jNode() {
		labels = new ArrayList<String>();
		properties = new HashMap<String, Object>();
		neo4jRelationships = new Neo4jRelationships();
	}
	
	public void addRelationships(Iterable<Relationship> relationships) {
		try {
	        for (org.neo4j.graphdb.Relationship relationship: relationships) {
	        	Neo4jRelationship r = new Neo4jRelationship();
	        	r.setStartNodeID(relationship.getStartNodeId());
	        	r.setEndNodeID(relationship.getEndNodeId());
	        	r.setRelationshipType(relationship.getType());
	        	if (relationship.getStartNodeId() == this.getNodeID()) {
	        		// It's an outgoing relationship
	//        		System.out.print("--->");
	        	} else {
	//        		System.out.print("<---");
	        	}
	        	// Get the key list for the properties and then read the properties by key, putting them into the abstracted Relationship object
	        	Iterable<String> keys = relationship.getPropertyKeys();
	        	for (String key : keys) {
	        		r.getProperties().put(key, relationship.getProperty(key).toString());
	        	}
	        	r.setName(relationship.getType().name());
	        	neo4jRelationships.addRelationship(r);
	        }
		} catch (Exception ex) {
			Log.logError("Neo4jNode.addRelationships(): " + ex.getLocalizedMessage()); 
		}
	}
	
	public void addLabel (String label) {
		labels.add(label);
	}
	public void addProperty(String pkey, String value) {
		properties.put(key, value);
	}
	
	public ArrayList<String> getLabels() {return labels;}
	
	public HashMap<String, Object> getProperties() {return properties;}
	
	public void printProperties(PrintStream device) {
		for (Map.Entry<String, Object> p: properties.entrySet()) {
			device.print("(" + p.getKey() + "= " + p.getValue() + ")");
		}
    }
	public void printLabels(PrintStream device) {
		String comma = "";
		for (String l : labels) {
			device.print(l + comma);
			comma = ",";
		}
	}
	public void printRelationships(PrintStream device) {
		for (Neo4jRelationship neo4jRelationship : this.getNeo4jRelationships().getNeo4jRelationships()) {
			device.println(neo4jRelationship.toString());
		}
	}
	/***
	 * Clone a node from a graph into our abstraction of that node
	 * @param node The node to clone
	 * @param neo4jNodes The collection to store the clone into
	 */
	public static void cloneNode(Node node, Neo4jNodes neo4jNodes) {
		Log.logProgress("Neo4jNode.cloneNode(): Cloning Node ID " +  + node.getId() + " (" + node.getLabels().toString() + "), (" + node.getAllProperties().toString() + ")");
		try {
			Neo4jNode neo4jNode = new Neo4jNode();
	        for (org.neo4j.graphdb.Label label: node.getLabels()) {
	        	neo4jNode.getLabels().add(label.name());
	        }
	        Map<String, Object> properties = node.getAllProperties();
	        for (Map.Entry<String, Object> property: properties.entrySet()) {
//				property.getValue() could be an array
//	        	if (property.getValue().getClass() == String[].class) {
	        		
//	        	} else {
	        		neo4jNode.getProperties().put(property.getKey(), property.getValue());
//	        	}
	        }
	        neo4jNode.nodeID = node.getId();
	        neo4jNode.addRelationships(node.getRelationships());
	        neo4jNodes.addNeo4jNode(neo4jNode);
		} catch (Exception ex) {
			Log.logError("Neo4jNode.cloneNode(): " + ex.getLocalizedMessage()); 
		}
	}
	public String toString() {
		String string = "Node[" + nodeID + "]: " + labels.toString() + " : " + properties.toString();
		for (Neo4jRelationship neo4jRelationship: this.getNeo4jRelationships().getNeo4jRelationships() ) {
			string += "\n\t\t" + neo4jRelationship.toString(); 
		}
		return string;
	}
	public long getNodeID() {return nodeID;}
	
	public void print(PrintStream device) {
		printLabels(device);
		printProperties(device);
		printRelationships(device);
	}
	/***
	 * Write the node to the log. Just a nice debugging tool
	 */
	public void log() {
		StringBuilder sb = new StringBuilder();
		sb.append("Neo4jNode.log(): ");
		sb.append(toString());
		Log.logProgress(sb.toString());  
	}
	public Neo4jRelationships getNeo4jRelationships() {return neo4jRelationships;}
 	public static boolean compareNodes(Neo4jNode n1, Neo4jNode n2) {
 		Boolean isEqual = false;
 		try {
 			// Compare labels, if any 
 			// Do they have the same number of labels?
 			if (n1.getLabels().size() == n2.getLabels().size())
 			
	 		// Compare properties, if any

	 		// Compare relationships, if any
	 				
			// If we get this far, the nodes are equal. Woo hoo
	 	 			isEqual = true;

 		} catch (Exception ex) {
 			Log.logError("Neo4jUtils.compareNodes(): " + ex.getLocalizedMessage());
 		}
 		return isEqual;
 	}
	/***
	 * Print the name/value pairs for all the properties of a node
	 * @param node The node to be processed
	 */
	public static void printNodeProperties(Node node) {
        Map<String, Object> properties = node.getAllProperties();
        for (Map.Entry<String, Object> property: properties.entrySet()) {
        	System.out.print("(" + property.getKey() + "= " + property.getValue() + ")");
        }
	}

}
