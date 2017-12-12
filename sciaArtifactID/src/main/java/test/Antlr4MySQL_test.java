package test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.Antlr4MySQLFromANTLRRepo.MySqlLexer;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.AntlrMySQLListener;
import edu.UC.PhD.CodeProject.nicholdw.queryType.Select;

public class Antlr4MySQL_test {

	public static void main( String[] args) throws Exception
	{
		//simpleParseTest("ALTER TABLE `acme`.`tpilot` ADD COLUMN `FavoriteColor` VARCHAR(45) NULL AFTER `FirstName`");
		simpleParseTest("SELECT `fruit`, (`abc`.`gorp` + 2) AS `foo`  FROM `tFruit` WHERE `tree`=\"apple\" AND `limit` > 100 ORDER BY `tfruit`.`fruit` ");
/*		simpleParseTest("SELECT "
				+ " `mySchema`.`myTable`.`myAttribute` AS `myAttributeAlias`"
				+ ", `mySchema01`.`myTable01`.`myAttribute01` AS `myAttributeAlias01`"
				+ ", `myTable02`.`myAttribute02` AS `myAttributeAlias02`"
				+ ", `myAttribute03` AS `myAlias03`"
				+ ", `myAttribute04` "
				+ "FROM `tFruit` ORDER BY `fruit`");
*/
		//simpleParseTest("SELECT 123 AS `myConstant`, `SchemaA`.`ttablea`.`testString` AS `testString`,`ttablea`.`testInt` AS `testInt`,`ttablea`.`testDateTime` AS `testDateTime`,`ttablea`.`testDouble` AS `testDouble`, `twidget`.`Widget` AS `Widget`,`qc`.`testFieldATableC` AS `testfieldatablec`,`qd`.`testFieldATableD` AS `testfieldatabled`,`qlevelaa`.`TestFieldATableE` AS `testfieldatablee`,`qlevelaa`.`TestFieldATableF` AS `testfieldatablef` FROM (((((`ttablea` JOIN `ttableb`) JOIN `qc`) JOIN `qd`) JOIN `twidget`) JOIN `qlevelaa`)");
		//simpleParseTest("SELECT 1.e-3 as 123e;");
		//simpleParseTest("SELECT `hello world` as bar");
		//simpleParseTest("select acme.tpilot.PilotID foo, sakila.actor.last_name, boo SumBoo from tpilot, actor");
		//simpleParseTest("SELECT a,b,c FROM tFoo ORDER BY Gorp WHERE a=`aaa`");
		//simpleParseTest("SELECT a,b,c FROM tFoo ORDER BY Gorp WHERE a=`aaa`");
//		testSelect();
//		testAlter();
	}
	private static void testAlter() throws Exception {
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
	private static void testSelect() throws Exception {
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
	static QueryDefinition parseSelectQuery(String sql) throws Exception {
		QueryDefinition qd = new QueryDefinition("","","",new Select(), "dummyQueryName", sql, "dummySchemaName");
		// Get our lexxer
		MySqlLexer lexxer = new MySqlLexer(new ANTLRInputStream(sql));
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexxer);
		// Pass the tokens to the parser
		MySqlParser parser = new MySqlParser(tokens);
		// Specify our entry point

        ParserRuleContext MYSQLSentenceContext = parser.getContext();
		// Walk it and attach our listener
		ParseTreeWalker walker = new ParseTreeWalker();
		throw new Exception("NOT IMPLEMENTED");
//		MySqlParserBaseListener listener = new MySqlParserBaseListener(qd);
//		walker.walk(listener, MYSQLSentenceContext);
//		return qd;
	}
	static QueryDefinition parseAlterQuery(String sql) {
		QueryDefinition qd = new QueryDefinition("","","",new Select(), "dummyQueryName", sql, "dummySchemaName");
/*		// Get our lexxer
		MySQLLexxerRepo lexxer = new MySQLLexxerRepo(new ANTLRInputStream(sql));
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexxer);
		// Pass the tokens to the parser
		MySQLParserRepo parser = new MySQLParserRepo(tokens);
		// Specify our entry point

		ParserRuleContext MYSQLSentenceContext = parser.alter_clause();		// oops. Don't have ALTER in the grammer, yet.
		// Walk it and attach our listener
		ParseTreeWalker walker = new ParseTreeWalker();
		MySQLParserRepoBaseListener listener = new AntlrMySQLListener_test(qd);
		walker.walk(listener, MYSQLSentenceContext);
*/
		return qd;
	}
	private static void simpleParseTest(String sql) {
		try {
			QueryDefinition qd = new QueryDefinition("","","",new Select(), "dummyQueryName", sql, "dummySchemaName");
			System.out.println("Parsing SQL: " + sql);
			// Get our lexxer

//			This works for ANTLR Runtime 4.7, but not for 4.3.5
			InputStream stream = new ByteArrayInputStream(sql.getBytes(StandardCharsets.UTF_8));
			MySqlLexer lexxer = new MySqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));

// 			This works for ANTLR 4.3.5...
//			CharStream stream = new ANTLRInputStream(sql);
//			MySqlLexer lexxer = new MySqlLexer(stream);

			// Get a list of matched tokens
			//List<? extends Token> x = lexxer.getAllTokens();

			CommonTokenStream tokens = new CommonTokenStream(lexxer);

			// Pass the tokens to the parser
			MySqlParser parser = new MySqlParser(tokens);
			// Specify our entry point
			ParserRuleContext MYSQLSentenceContext = parser.root();

			// Walk it and attach our listener
			ParseTreeWalker walker = new ParseTreeWalker();
			AntlrMySQLListener listener = new AntlrMySQLListener(qd);
			parser.addParseListener(listener);
			walker.walk(listener, MYSQLSentenceContext);
			System.out.println("simpleParseTest() done.");
			for (QueryAttribute qa: qd.getQueryAttributes()) {
				System.out.println(qa.toString());
			}
		} catch (Exception ex) {
			System.out.println("simpleParseTest: " + ex.getLocalizedMessage());
		}
	}
}
