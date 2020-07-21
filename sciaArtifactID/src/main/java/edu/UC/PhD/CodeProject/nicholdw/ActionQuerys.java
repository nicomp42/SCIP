/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.DatabaseEngine;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
/***
 * A collection of ActionQuery objects. Yes, the plural of query is not querys. However, appending an s is my standard.
 * @author nicomp
 * See also ActionQueryDefinitions class for a container of Action Query Definitions. 
 * Don't use this if you really need ActionQueryDefinitions
 */
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
	 * @param filePath The location and name of the file. It's an absolute file path!
	 * @return the number of lines read
	 */
	public int loadActionQuerys(String filePath) {
		DatabaseEngine databaseEngine = Config.getConfig().getDatabaseEngine();		// TODO generalize
		int count = 0;
		File file = null;
		BufferedReader br = null;
		try {
			//ClassLoader classLoader = ClassLoader.getSystemClassLoader(); 
			// getClass().getResourceAsStream("/
//			URL tmpURL = this.getClass().getResource(filePath);
//			String tmp = tmpURL.toString();
			file = new File(filePath);
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				if ((line.trim().length() > 0) && (!line.trim().startsWith(databaseEngine.getSingleLineCommentDelimiter()))) {
					actionQuerys.add(new ActionQuery(line.trim()));
				}
				count++;
			}
		} catch (Exception ex) {
			Log.logError("ActionQuerys.loadActionQuerys(): " + ex.getLocalizedMessage());
			System.out.println("ActionQuerys.loadActionQuerys(): " + ex.getLocalizedMessage());
		} finally {
			try { br.close();} catch (Exception ex) {}
		}
		return count;
	}
	public void add(ActionQuery actionQuery) {actionQuerys.add(actionQuery);}
	public void clear() {actionQuerys.clear();}
	public int getSize() {return actionQuerys.size();}
}
