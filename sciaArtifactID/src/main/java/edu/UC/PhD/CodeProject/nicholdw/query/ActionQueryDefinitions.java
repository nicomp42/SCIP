/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */

package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.Iterator;

/***
 * We are storing QueryDefinition objects in here for now.
 * @author nicomp
 *
 */
public class ActionQueryDefinitions implements Iterable<QueryDefinition>, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2518388618922401368L;
	private ArrayList<QueryDefinition> actionQueryDefinitions;
	@Override
	public Iterator<QueryDefinition> iterator() {
		return actionQueryDefinitions.iterator();
	}
	public void addActionQueryDefinition(QueryDefinition qd) {
		actionQueryDefinitions.add(qd);
	}
	public ActionQueryDefinitions() {
		actionQueryDefinitions = new ArrayList<QueryDefinition>();
	}
	public void clear() {
		actionQueryDefinitions.clear();
	}
}
