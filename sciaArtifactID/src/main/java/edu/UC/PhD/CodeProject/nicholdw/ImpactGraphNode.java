package edu.UC.PhD.CodeProject.nicholdw;

import java.util.Hashtable;

/**
 * Model a node that could appear on a Schema Change Impact Graph.
 * All classes that could possibly appear on any graph should inherit this.
 * @author nicomp
 *
 */
public abstract class ImpactGraphNode {
	private Boolean addToImpactGraph;
	private Hashtable<String, String> relationshipKeys;	// Use Hashtable over the HashMap. Thread-safe!
	
	public ImpactGraphNode() {
		addToImpactGraph = false;
		relationshipKeys = new Hashtable<String, String>();
	}

	public Boolean getAddtoImpactGraph() {
		return addToImpactGraph;
	}

	public void setAddtoImpactGraph(Boolean addToImpactGraph) {
		this.addToImpactGraph = addToImpactGraph;
	}

	public Hashtable<String, String> getRelationshipKeys() {
		return relationshipKeys;
	}

	public void setRelationshipKeys(Hashtable<String, String> relationshipKeys) {
		this.relationshipKeys = relationshipKeys;
	}
	
	
}
