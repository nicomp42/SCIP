package edu.UC.PhD.CodeProject.nicholdw.query;

import edu.UC.PhD.CodeProject.nicholdw.ImpactGraphNode;

/**
 * Anything we parse out of a query falls into this category
 * @author nicomp
 *
 */
public abstract class QueryComponent extends ImpactGraphNode {
	public abstract String toString(); // {return "Query Component";}
}
