package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.ArrayList;

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
	
	public int size() {return neo4jNodes.size();}

	public void print() {
		for (Neo4jNode neo4jNode : neo4jNodes) {
			neo4jNode.print(System.out);
		}
	}
}
