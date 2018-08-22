package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * A collection of Neo4jNode objects and some utilities to manipulate them
 * @author nicomp
 *
 */
public class Neo4jNodes {
	private ArrayList<Neo4jNode> neo4jNodes;

	/***
	 * Add a Neo4jNode to the object
	 * @param neo4jNode The Neo4jNode to add
	 */
	public void addNeo4jNode(Neo4jNode neo4jNode) {
		neo4jNodes.add(neo4jNode);
	}

	/***
	 * Not the best solution because it returns a reference to a private class member
	 * @return The reference to the list of Neo4j Nodes stored in the object
	 */
	public ArrayList<Neo4jNode> getNeo4jNodes() {return neo4jNodes;}

	/***
	 * Constructor
	 */
	public Neo4jNodes() {
		neo4jNodes= new ArrayList<Neo4jNode>();
	}
	public void clearMatchedFlags() {
		for (Neo4jNode neo4jNode : neo4jNodes) {
			neo4jNode.setMatched(false);
		}
	}
	/***
	 * Call this after a comparison to get the list of unmatched nodes in this DB
	 * @param neo4jNodes
	 */
	public void copyUnmatchedNodes(Neo4jNodes neo4jNodesUnmatched) {
		for (Neo4jNode neo4jNode : neo4jNodes) {
			if (!neo4jNode.isMatched()) {
				neo4jNodesUnmatched.addNeo4jNode(neo4jNode);
			}
		}
	}
	
	/***
	 * After a comparison of two graphs, count the number of unmatched nodes in the object.
	 * @return The count of unmatched nodes
	 */
	public int countUnmatchedNodes() {
		int unmatchedNodes = 0;
		for (Neo4jNode neo4jNode : neo4jNodes) {
			if (!neo4jNode.isMatched()) {unmatchedNodes++;}
		}
		return unmatchedNodes;
	}
	public int size() {return neo4jNodes.size();}

	public void print() {
		for (Neo4jNode neo4jNode : neo4jNodes) {
			neo4jNode.print(System.out);
		}
	}
	/***
	 * Write the contents to the log. Just a nice debugging tool
	 */
	public void log() {
		for (Neo4jNode neo4jNode: getNeo4jNodes()) {
			neo4jNode.log();
		}
	}
 	public static Neo4jNode findNode(Neo4jNode targetNode, Neo4jNodes db) {
 		Neo4jNode foundNode = null;
 		for (Neo4jNode neo4jNode: db.getNeo4jNodes()) {
            if (Neo4jNode.compareNodes(targetNode, neo4jNode) == true) {foundNode = neo4jNode; break;}
 		}
 		return foundNode;
 	}
 	/***
 	 * Look up a node based on the ID
 	 * @param nodeID The Node ID to look up
 	 * @param db The Graph DB with all the nodes
 	 * @return The node that was found, or null
 	 */
 	public static Neo4jNode findNode(long nodeID, Neo4jNodes db) {
 		Neo4jNode foundNode = null;
 		for (Neo4jNode neo4jNode: db.getNeo4jNodes()) {
            if (nodeID == neo4jNode.getNodeID()) {foundNode = neo4jNode; break;}
 		}
 		return foundNode;
 	}
 	public void printUnmatchedNodes() {
		for (Neo4jNode neo4jNode : neo4jNodes) {
			if (!neo4jNode.isMatched()) {
				Log.logProgress("Neo4jNodes.printUnmatchedNodes()" + neo4jNode.toString());
			}
		}
 	}
}
