/*
 * In mathematics, a bag (multi-set) is an unordered collection of elements with duplicates.
 */

package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * An abstracted version of the Neo4j Node. We need this because the Node
 * objects we read from the Graph DB can't be accessed unless in a transaction.
 * 
 * @author nicomp
 *
 */
public class Neo4jNode {

	private String value;
	private String key;
	private ArrayList<String> labels;
	// private HashMap<String, Neo4jPropertyValues> properties;
	private Neo4jProperties neo4jProperties;
	private Neo4jRelationships neo4jRelationships;
	private long nodeID; // The Node ID established by the GraphDB engine
	private boolean matched;

	public Neo4jNode() {
		labels = new ArrayList<String>();
		neo4jProperties = new Neo4jProperties(); // new HashMap<String, Neo4jPropertyValues>();
		neo4jRelationships = new Neo4jRelationships();
		matched = false;
	}

	public void addRelationships(Iterable<Relationship> relationships) {
		try {
			for (org.neo4j.graphdb.Relationship relationship : relationships) {
				Neo4jRelationship r = new Neo4jRelationship();
				r.setStartNodeID(relationship.getStartNodeId());
				r.setEndNodeID(relationship.getEndNodeId());
				r.setRelationshipType(relationship.getType());
				if (relationship.getStartNodeId() == this.getNodeID()) {
					// It's an outgoing relationship
					// System.out.print("--->");
				} else {
					// System.out.print("<---");
				}
				// Get the key list for the properties and then read the properties by key,
				// putting them into the abstracted Relationship object
				Iterable<String> keys = relationship.getPropertyKeys();
				for (String key : keys) {
					if (relationship.getProperty(key).getClass() == String[].class) {
						String[] valueList = (String[]) relationship.getProperty(key);
						r.getProperties().addNeo4jProperty(key, new Neo4jProperty(key, valueList));
					} else {
						String value = (String) relationship.getProperty(key);
						r.getProperties().addNeo4jProperty(key, new Neo4jProperty(key, value));
					}
				}
				r.setName(relationship.getType().name());
				neo4jRelationships.addRelationship(r);
			}
		} catch (Exception ex) {
			Log.logError("Neo4jNode.addRelationships(): " + ex.getLocalizedMessage());
		}
	}

	public void addLabel(String label) {
		labels.add(label);
	}
	// public void addProperty(String pkey, String value) {
	// neo4jProperties.put(key, new Neo4jPropertyValues(value));
	// }

	public ArrayList<String> getLabels() {
		return labels;
	}

	public Neo4jProperties getProperties() {
		return neo4jProperties;
	}

	public void printProperties(PrintStream device) {
		for (Entry<String, Neo4jProperty> p : neo4jProperties.getNeo4jProperties().entrySet()) {
			device.print("(" + p.getKey() + "= [" + p.getValue().toString() + "])");
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
	 * 
	 * @param node
	 *            The node to clone
	 * @param neo4jNodes
	 *            The collection to store the clone into
	 */
	public static void cloneNode(Node node, Neo4jNodes neo4jNodes) {
		Log.logProgress("Neo4jNode.cloneNode(): Cloning Node ID " + +node.getId() + " (" + node.getLabels().toString()
				+ "), (" + node.getAllProperties().toString() + ")");
		try {
			Neo4jNode neo4jNode = new Neo4jNode();
			for (org.neo4j.graphdb.Label label : node.getLabels()) {
				neo4jNode.getLabels().add(label.name());
			}
			Map<String, Object> properties = node.getAllProperties();
			for (Map.Entry<String, Object> property : properties.entrySet()) {
				// property.getValue() could be an array of Strings or a single string.
				if (property.getValue().getClass() == String[].class) {
					String[] valueList = (String[]) property.getValue();
					neo4jNode.getProperties().addNeo4jProperty(property.getKey(),
							new Neo4jProperty(property.getKey(), valueList));
				} else {
					neo4jNode.getProperties().addNeo4jProperty(property.getKey(),
							new Neo4jProperty(property.getKey(), (String) property.getValue()));
				}
			}
			neo4jNode.nodeID = node.getId();
			neo4jNode.addRelationships(node.getRelationships());
			neo4jNodes.addNeo4jNode(neo4jNode);
		} catch (Exception ex) {
			Log.logError("Neo4jNode.cloneNode(): " + ex.getLocalizedMessage());
		}
	}

	public String toString() {
		String string = "Node[" + nodeID + "]: " + labels.toString() + " : "
				+ getProperties().getNeo4jProperties().toString();
		for (Neo4jRelationship neo4jRelationship : this.getNeo4jRelationships().getNeo4jRelationships()) {
			string += "\n\t\t" + neo4jRelationship.toString();
		}
		return string;
	}

	public long getNodeID() {
		return nodeID;
	}

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

	public Neo4jRelationships getNeo4jRelationships() {
		return neo4jRelationships;
	}

	/***
	 * Compare two nodes
	 * 
	 * @param n1
	 * @param n2
	 * @return True if the nodes have the same relationships, properties, and labels
	 */
	public static boolean compareNodes(Neo4jNode n1, Neo4jNode n2) {
		Log.logProgress("Neo4jNode.CompareNodes(): Comparing Node IDs " + n1.getNodeID() + " & " + n2.getNodeID());
		Boolean isEqual = false;
		try {
			// Compare labels, if any
			if (BagUtils.compareBags(n1.getLabels(), n2.getLabels())) {
				Log.logProgress("Neo4jNode.CompareNodes(): labels match.");

				// Compare properties, if any
				n1.getProperties().clearMatchedFlags();
				n2.getProperties().clearMatchedFlags();
				if (n1.getProperties().getNeo4jProperties().size() == n2.getProperties().getNeo4jProperties().size()) {
					Log.logProgress("Neo4jNode.CompareNodes(): property counts match.");
					for (Entry<String, Neo4jProperty> neo4jPropertyEntry : n1.getProperties().getNeo4jProperties().entrySet()) {
						Neo4jProperty neo4jProperty = (Neo4jProperty) neo4jPropertyEntry.getValue();
						Neo4jProperty neo4jPropertyFound = n2.getProperties().findProperty(neo4jProperty);
						if (neo4jPropertyFound != null) {
							neo4jPropertyFound.setMatched(true);
							neo4jProperty.setMatched(true);
							Log.logProgress("Neo4jNode.CompareNodes(): found a property match.");
						}
					}
					// Did all the node properties match up?
					if (n1.getProperties().countMatchedFlags() == n1.getProperties().getNeo4jProperties().size() && 
						n2.getProperties().countMatchedFlags() == n2.getProperties().getNeo4jProperties().size()) {
						Log.logProgress("Neo4jNode.CompareNodes(): ALL properties match.");

						// Compare relationships, if any
						n1.getNeo4jRelationships().clearMatchedFlags();
						n2.getNeo4jRelationships().clearMatchedFlags();
						for (Neo4jRelationship neo4jRelationship : n1.getNeo4jRelationships().getNeo4jRelationships()) {
							Neo4jRelationship found = n2.getNeo4jRelationships().findRelationship(neo4jRelationship);
							if (found != null) {
								n1.setMatched(true);
								found.setMatched(true);
								Log.logProgress("Neo4jNode.CompareNodes(): found a relationship match.");
							}
						}
						// Did all the relationship properties match up?
						if (n1.getNeo4jRelationships().countMatchedFlags() == n1.getNeo4jRelationships().getNeo4jRelationships().size() && 
								n2.getProperties().countMatchedFlags() == n2.getProperties().getNeo4jProperties().size()) {
								Log.logProgress("Neo4jNode.CompareNodes(): ALL properties match.");
								// If we get this far, the nodes are equal. Woo hoo
								isEqual = true;
						}
					}
				} else {
					Log.logProgress("Neo4jNode.CompareNodes(): property counts do not match.");
				}
			} else {
				Log.logProgress("Neo4jNode.CompareNodes(): labels do not match");
			}
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.compareNodes(): " + ex.getLocalizedMessage());
		}
		return isEqual;
	}

	/***
	 * Print the name/value pairs for all the properties of a node
	 * 
	 * @param node
	 *            The node to be processed
	 */
	public static void printNodeProperties(Node node) {
		Map<String, Object> properties = node.getAllProperties();
		for (Map.Entry<String, Object> property : properties.entrySet()) {
			System.out.print(
					"(" + property.getKey() + "= " + ((Neo4jPropertyValues) property.getValue()).toString() + ")");
		}
	}

	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}
}
