/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import sun.misc.ClassLoaderUtil;
/***
 * A collection of ActionQuery objects. Yes, the plural of query is not querys. However, appending an s is my standard.
 * @author nicomp
 *
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
	 * @param filePath The location and name of the file
	 * @return the number of lines read
	 */
	public int loadActionQueries(String filePath) {
		int count = 0;
		File file = null;
		BufferedReader br = null;
		try {
			//ClassLoader classLoader = ClassLoader.getSystemClassLoader(); 
			file = new File(filePath);
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {actionQuerys.add(new ActionQuery(line.trim()));}
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
