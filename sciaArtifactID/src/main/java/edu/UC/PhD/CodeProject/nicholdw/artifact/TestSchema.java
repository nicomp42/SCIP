package edu.UC.PhD.CodeProject.nicholdw.artifact;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSchema {

	@Test
	/**
	 * Test a non-blank schema name
	 */
	void test01() {
		try {
			String name = "TestSchema";
			Schema schema = new Schema(name);
			assertEquals(schema.getName(), name);
		} catch (Exception ex) {
			fail("Should not have thrown an exception");
		}
	}
	@Test
	/**
	 * Test a blank schema name
	 */
	void test02() {
		try {
			new Schema("");	// Should throw an exception
			fail("should not accept a blank schema name");
		} catch (Exception ex) {
			// Just getting here means we passed
			assertEquals(1,1);
		}
	}
	@Test
	/**
	 * Test the compareTo method
	 */
	void test03() {
		String name = "TestSchema";
		try {
			Schema schema = new Schema(name);
			Schema copiedSchema = new Schema(schema);
			assertTrue(schema.compareTo(copiedSchema));
		} catch (Exception ex) {
			fail("Should not have thrown an exception");
		}
	}	
}
