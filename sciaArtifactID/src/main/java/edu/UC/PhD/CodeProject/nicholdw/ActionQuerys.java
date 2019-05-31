/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class ActionQuerys implements Iterable<ActionQuery> {
	private ArrayList<ActionQuery> actionQuerys;

	public ActionQuerys() {
		actionQuerys = new ArrayList<ActionQuery>();
	}

	@Override
	public Iterator<ActionQuery> iterator() {
		Iterator<ActionQuery> myIterator = actionQuerys.iterator();
        return myIterator;
	}
	/***
	 * Read from a line-based text file
	 * @param filePath The location and name of the file
	 * @return the number of lines read
	 */
	public int loadActionQueries(String filePath) {
		int count = 0;
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filePath));
			while (sc.hasNextLine()) {
			  actionQuerys.add(new ActionQuery(sc.nextLine()));
			  count++;
			}
		} catch (Exception ex) {
			Log.logError("ActionQuerys.loadActionQuerys(): " + ex.getLocalizedMessage());
		} finally {
			try { sc.close();} catch (Exception ex) {}
		}
		return count;
	}
	public void add(ActionQuery actionQuery) {actionQuerys.add(actionQuery);}
	public void clear() {actionQuerys.clear();}
}
