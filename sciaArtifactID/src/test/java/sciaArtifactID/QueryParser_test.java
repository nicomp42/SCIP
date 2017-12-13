package sciaArtifactID;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;

//@RunWith(Suite.class)
@SuiteClasses({
	QueryParser_test.class,
//	MySecondClassTest.class
})

public class QueryParser_test {
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
    public void parseQueryForAttributesAndTables() {
		String sql = "select A AAlias , B BAlias, E EAlias from TT TAlias inner join U UAlias";
		QueryDefinition qd = new QueryDefinition("","","", new QueryTypeSelect(), "qFoo", sql, "schemaName");
		QueryParser qp = new QueryParser();
		qp.parseQuery(qd);

        // assert statements
        assertEquals("Found three attributes", 3, (qd.getQueryAttributes()).size());
        assertEquals("Found two tables", 2, (qd.getQueryTables()).size());
        assertEquals("First attribute is \"A\"", "A", qd.getQueryAttributes().iterator().next().getAttributeName());
        assertEquals("First attribute alias is \"AAlias\"", "AAlias", qd.getQueryAttributes().iterator().next().getAliasNames().toString());
        assertEquals("First table is \"TT\"", "TT", qd.getQueryTables().iterator().next().getTableName());
        assertEquals("First table alias is \"TAlias\"", "TAlias", qd.getQueryTables().iterator().next().getAliasName());
    }
}
