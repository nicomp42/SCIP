/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package sciaArtifactID;

import static org.junit.Assert.*;
import org.junit.Test;



import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;

class ActionQuerys_test {

	@Test
	void test() {
		ActionQuerys actionQuerys = new ActionQuerys();
		// File path is relative to src/main/test/resources folder, not the src/main/resources folder!. 
		actionQuerys.loadActionQuerys("actionQuerysUnitTest.txt");
    	assertEquals("Action Querys read from text file", 4, actionQuerys.getSize());
	}
}
