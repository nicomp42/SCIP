/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package sciaArtifactID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;

class ActionQuerys_test {

	@Test
	void test() {
		ActionQuerys actionQuerys = new ActionQuerys();
		// File path is relative to src/main/test/resources folder, not the src/main/resources folder!. 
		actionQuerys.loadActionQueries("actionQuerysUnitTest.txt");
    	assertEquals("Action Querys read from text file", 4, actionQuerys.getSize());
	}
}
