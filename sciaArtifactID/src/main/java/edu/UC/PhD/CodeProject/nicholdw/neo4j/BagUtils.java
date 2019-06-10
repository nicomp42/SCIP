/*
 * Bill Nicholsonm
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.ArrayList;
import java.util.Arrays;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class BagUtils {
	/***
	 * Compare the contents of two unordered globs that could contain duplicates
	 * @param b1
	 * @param b2
	 * @return True if b1 and b2 have the same length and contain the same elements, not necessarily in the same order.
	 */
	public static Boolean compareBags(String[] b1, String b2[]) {
		Boolean isEqual = true;
		try {
			if (b1.length == b2.length) {
				String[] b1Clone = b1.clone();
				String[] b2Clone = b2.clone();
				Arrays.sort(b1Clone);
				Arrays.sort(b2Clone);
				for (int i = 0; i < b1Clone.length; i++) {
					if (!b1Clone[i].equals(b2Clone[i])) {isEqual = false; break;}
				}
			} else {isEqual = false;}
		} catch (Exception ex) {
			Log.logError("BagUtils.CompareBags();: " + ex.getLocalizedMessage());
			isEqual = false;		// If it blows up, assume the bags are not equal
		}
		return isEqual;
	}

	public static Boolean compareBags(ArrayList<String> b1, ArrayList<String> b2) {
		return compareBags(b1.toArray(new String[0]), b2.toArray(new String[0]));
	}
}
