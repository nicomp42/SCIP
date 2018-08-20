package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;

/***
 * An abstracted version of the Neo4j Node. We need this because the Node objects we read from the Graph DB can't be accessed unless in a transaction.
 * @author nicomp
 *
 */
public class Neo4jNode {

	private String value;
	private String key;
	private ArrayList<String> labels;
    private HashMap<String, String> properties;
    private long nodeID;			// The Node ID established by the GraphDB engine
	
	public Neo4jNode() {
		labels = new ArrayList<String>();
		properties = new HashMap<String, String>();
	}
	public void addLabel (String label) {
		labels.add(label);
	}
	public void addProperty(String pkey, String value) {
		properties.put(key, value);
	}
	
	public ArrayList<String> getLabels() {return labels;}
	
	public HashMap<String, String> getProperties() {return properties;}
	
	public void printProperties(PrintStream device) {
		for (Map.Entry<String, String> p: properties.entrySet()) {
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
	public static void cloneNode(Node node, ArrayList<Neo4jNode> neo4jNodes) {
		Neo4jNode neo4jNode = new Neo4jNode();
        for (org.neo4j.graphdb.Label label: node.getLabels()) {
        	neo4jNode.getLabels().add(label.name());
        }
        Map<String, Object> properties = node.getAllProperties();
        for (Map.Entry<String, Object> property: properties.entrySet()) {
        	neo4jNode.getProperties().put(property.getKey(), (String) property.getValue());
        }
        neo4jNode.nodeID = node.getId();
        neo4jNodes.add(neo4jNode);
	}
	public String toString() {
		String string = "Node[" + nodeID + "]: " + labels.toString() + " : " + properties.toString();
		return string;
	}
	public long getNodeID() {return nodeID;}
}
