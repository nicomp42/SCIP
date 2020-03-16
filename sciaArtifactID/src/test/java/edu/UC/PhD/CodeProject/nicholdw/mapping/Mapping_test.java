package edu.UC.PhD.CodeProject.nicholdw.mapping;
import static org.junit.Assert.*;
import org.junit.Test;

class Mapping_test {

	@Test
	void test() {
		Mapping myMapping = new Mapping("My Mapping");

		Mapping start = new Mapping("Mapping Start");
		Mapping end = new Mapping("Mapping End");
		
		myMapping.setStart(start);
		myMapping.setEnd(end);
		
		assertEquals("My Mapping", myMapping.getUniqueName(), "My Mapping Unique Name = ");
		assertEquals("Mapping Start", myMapping.getStart().getUniqueName(), "My Mapping Start Unique Name = ");
		assertEquals("Mapping End", myMapping.getEnd().getUniqueName(), "My Mapping End Name = ");
	}

}
