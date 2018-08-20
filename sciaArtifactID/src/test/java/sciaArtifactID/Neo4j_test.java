package sciaArtifactID;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;

import edu.UC.PhD.CodeProject.nicholdw.neo4j.Main;

//@RunWith(Suite.class)
@SuiteClasses({
	Neo4j_test.class,
//	MySecondClassTest.class
})

public class Neo4j_test {
/*
	public static void main(String[] args) {
		String sql = "select A AAlias , B BAlias, E EAlias from TT tAlias inner join U UAlias";
		QueryDefinition qd = new QueryDefinition(new Select(), "qFoo", sql, "schemaName");
		QueryParser qp = new QueryParser();
		qp.parseQuery(qd);
		qd.listAttributes(System.out);
		qd.listTables(System.out);
	}
*/
    @Test
    public void changePassword() {
    	boolean exception = true;
    	boolean status = false;
    	try {
			exception = false;
			status = Main.changePassword();
		} catch (Exception e) {
			exception = true;
		}
    	assertEquals("Neo4j Password change did not throw an exception", false, exception);
    	assertEquals("Neo4j Password change returned true", true, exception);
    }
}
