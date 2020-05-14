/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

/***
 * Describe how a node in a graph should be annotated
 * @author nicomp
 *
 */
public class GraphNodeAnnotation {
	public static enum GRAPH_NODE_ANNOTATION  {None, Changed};
	private GRAPH_NODE_ANNOTATION graphNodeAnnotation;
	public GRAPH_NODE_ANNOTATION getGraphNodeAnnotation() {
		return graphNodeAnnotation;
	}
	public void setGraphNodeAnnotation(GRAPH_NODE_ANNOTATION graphNodeAnnotation) {
		this.graphNodeAnnotation = graphNodeAnnotation;
	}
	/***
	 * Default constructor. Annotation is set to None
	 */
	public GraphNodeAnnotation() {
		setGraphNodeAnnotation(GRAPH_NODE_ANNOTATION.None);
	}
	/***
	 * Copy Constructor
	 * @param graphNodeAnnotation
	 */
	public GraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation) {
		setGraphNodeAnnotation(graphNodeAnnotation.getGraphNodeAnnotation());
	}
	public String toString() {
		return graphNodeAnnotation.name();
	}
}
