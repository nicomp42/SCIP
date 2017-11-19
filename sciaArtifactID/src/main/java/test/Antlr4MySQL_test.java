package test;

import org.Antlr4MySQLFromANTLRRepo.MySQLLexxerFromANTLRRepo;
import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepo;
import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepoBaseListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.queryParser.AntlrMySQLListener_test;
import edu.UC.PhD.CodeProject.nicholdw.queryType.Select;

public class Antlr4MySQL_test {

	public static void main( String[] args) throws Exception
	{
		testSelect();
//		testAlter();
	}
	private static void testAlter() {
		//		String sql = "select  x xx, FOOAlias.y yy, z from FOO FOOAlias join bar";
		//      1         2         3         4
		//1234567890123456789012345678901234567890
		String sql = "ALTER TABLE `acme`.`tpilot` ADD COLUMN `FavoriteColor` VARCHAR(45) NULL AFTER `FirstName`;";

		System.out.println("testAlter(" + sql + ")");
		QueryDefinition qd = parseSelectQuery(sql);
		System.out.println("Attributes found in query:");
		for (QueryAttribute queryAttribute: qd.getQueryAttributes()) {
			System.out.println(queryAttribute.toString());
		}
		System.out.println("Tables found in query:");
		for (QueryTable queryTable: qd.getQueryTables()) {
			System.out.println(queryTable.toString());
		}
	}
	private static void testSelect() {
		//		String sql = "select  x xx, FOOAlias.y yy, z from FOO FOOAlias join bar";
		//      1         2         3         4
		//1234567890123456789012345678901234567890
		//String sql = "select A AAlias, B, C from T TAlias join U";
		//String sql = "select A AAlias, T.B, C CAlias, D from T TAlias join U";

		//A test case with attributes in all 4 possible combinations:
		//String sql = "select A , b bAlias, cTable.c, dTable.d dAlias, eSchema.eTable.e eAlias from TTSchema.TT ttAlias inner join UU inner join VV where tAlias.a = 42 and b = 43 and 99=c and 100=f ";
		//String sql = "select A , b bAlias, cTable.c, dTable.d dAlias from TT tAlias inner join U where sum(a) = 42";
		String sql = "select acme.tpilot.PilotID foo, sakila.actor.last_name, boo SumBoo from tpilot, actor";
		//String sql = "select mySchema.myTable.myAttribute myAlias, b from tPilotSchema.tpilot, tActorSchema.actor";	Schema qualifier is not supported in this grammar, yet.

		System.out.println("testSelect(" + sql + ")");
		QueryDefinition qd = parseSelectQuery(sql);
		System.out.println("Attributes found in query:");
		for (QueryAttribute queryAttribute: qd.getQueryAttributes()) {
			System.out.println(queryAttribute.toString());
		}
		System.out.println("Tables found in query:");
		for (QueryTable queryTable: qd.getQueryTables()) {
			System.out.println(queryTable.toString());
		}
	}
	static QueryDefinition parseSelectQuery(String sql) {
		QueryDefinition qd = new QueryDefinition("","","",new Select(), "dummyQueryName", sql, "dummySchemaName");
		// Get our lexxer
		MySQLLexxerFromANTLRRepo lexxer = new MySQLLexxerFromANTLRRepo(new ANTLRInputStream(sql));
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexxer);
		// Pass the tokens to the parser
		MySQLParserFromANTLRRepo parser = new MySQLParserFromANTLRRepo(tokens);
		// Specify our entry point
		ParserRuleContext MYSQLSentenceContext = parser.select_clause();
		// Walk it and attach our listener
		ParseTreeWalker walker = new ParseTreeWalker();
		MySQLParserFromANTLRRepoBaseListener listener = new AntlrMySQLListener_test(qd);
		walker.walk(listener, MYSQLSentenceContext);
		return qd;
	}
	static QueryDefinition parseAlterQuery(String sql) {
		QueryDefinition qd = new QueryDefinition("","","",new Select(), "dummyQueryName", sql, "dummySchemaName");
/*		// Get our lexxer
		MySQLLexxerFromANTLRRepo lexxer = new MySQLLexxerFromANTLRRepo(new ANTLRInputStream(sql));
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexxer);
		// Pass the tokens to the parser
		MySQLParserFromANTLRRepo parser = new MySQLParserFromANTLRRepo(tokens);
		// Specify our entry point

		ParserRuleContext MYSQLSentenceContext = parser.alter_clause();		// oops. Don't have ALTER in the grammer, yet.
		// Walk it and attach our listener
		ParseTreeWalker walker = new ParseTreeWalker();
		MySQLParserFromANTLRRepoBaseListener listener = new AntlrMySQLListener_test(qd);
		walker.walk(listener, MYSQLSentenceContext);
*/
		return qd;
	}
	private static void simpleParseTest(String sql) {

		System.out.println("Parsing SQL: " + sql);
		// Get our lexxer
		MySQLLexxerFromANTLRRepo lexxer = new MySQLLexxerFromANTLRRepo(new ANTLRInputStream(sql));

		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexxer);

		// Pass the tokens to the parser
		MySQLParserFromANTLRRepo parser = new MySQLParserFromANTLRRepo(tokens);

		// Specify our entry point
		ParserRuleContext MYSQLSentenceContext = parser.select_clause();

		// Walk it and attach our listener
		ParseTreeWalker walker = new ParseTreeWalker();
		MySQLParserFromANTLRRepoBaseListener listener = new AntlrMySQLListener_test(null);
		walker.walk(listener, MYSQLSentenceContext);
	}
}
