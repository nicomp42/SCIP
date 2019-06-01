package sciaArtifactID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;

class ActionQuerys_test {

	@Test
	void test() {
		ActionQuerys actionQuerys = new ActionQuerys();
		actionQuerys.loadActionQueries("ActionQuerys/actionQuerysUnitTest.txt");
    	assertEquals("Action Querys read from text file", 4, actionQuerys.getSize());

	}

}
