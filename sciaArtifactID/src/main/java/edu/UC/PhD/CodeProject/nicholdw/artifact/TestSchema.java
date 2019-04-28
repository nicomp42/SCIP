package edu.UC.PhD.CodeProject.nicholdw.artifact;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSchema {

	@Test
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
	void test02() {
		try {
			String name = "";
			Schema schema = new Schema(name);
			fail("should not accept a blank schema name");
		} catch (Exception ex) {
			// Just getting here means we passed
			assertEquals(1,1);
		}
	}
}
