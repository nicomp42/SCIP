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
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;

public class Antlr4MySQL_test {

	public static void main( String[] args) throws Exception
	{
//		testSelect();
		testAlter();
//		testMiscellaneous();
	}
	private static void testMiscellaneous() {
		//simpleParseTest("SHOW TABLES");
		//simpleParseTest("DROP TABLE `foo`");
		//simpleParseTest("DROP VIEW IF EXISTS `foo`, `bar` RESTRICT");
	}
	private static void testAlter() throws Exception {
		simpleParseTest("ALTER VIEW `acme`.`tpilot` AS SELECT `qbert` from `tFoo`");
		simpleParseTest("ALTER TABLE `acme`.`tpilot` ADD COLUMN `FavoriteColor` VARCHAR(45) NULL AFTER `FirstName`");
	}
	private static void testSelect() throws Exception {
		simpleParseTest(" SELECT `schematopologytest01`.`talpha`.`CommonField` AS `CommonField_tAlpha`,`schematopologytest01`.`tbeta`.`CommonField` AS `CommonField_tBeta` FROM (`schematopologytest01`.`talpha` JOIN `schematopologytest01`.`tbeta`)");
		//simpleParseTest(hereIsASakilaView());
		//simpleParseTest("SELECT `fruit`, (`abc`.`gorp` + 2) AS `foo` , MAX(`happy`)  FROM `tFruit` WHERE `tree`=\"apple\" AND `limit` > 100 ORDER BY `tfruit`.`fruit` ");
/*		simpleParseTest("SELECT "
				+ " `mySchema`.`myTable`.`myAttribute` AS `myAttributeAlias`"
				+ ", `mySchema01`.`myTable01`.`myAttribute01` AS `myAttributeAlias01`"
				+ ", `myTable02`.`myAttribute02` AS `myAttributeAlias02`"
				+ ", `myAttribute03` AS `myAlias03`"
				+ ", `myAttribute04` "
				+ "FROM `tFruit` ORDER BY `fruit`");
*/
		//simpleParseTest("SELECT 123 AS `myConstant`, `SchemaA`.`ttablea`.`testString` AS `testString`,`ttablea`.`testInt` AS `testInt`,`ttablea`.`testDateTime` AS `testDateTime`,`ttablea`.`testDouble` AS `testDouble`, `twidget`.`Widget` AS `Widget`,`qc`.`testFieldATableC` AS `testfieldatablec`,`qd`.`testFieldATableD` AS `testfieldatabled`,`qlevelaa`.`TestFieldATableE` AS `testfieldatablee`,`qlevelaa`.`TestFieldATableF` AS `testfieldatablef` FROM (((((`ttablea` JOIN `ttableb`) JOIN `qc`) JOIN `qd`) JOIN `twidget`) JOIN `qlevelaa`)");
		//simpleParseTest("SELECT 1.e-3 as `123e`");
		//simpleParseTest("SELECT `hello world` as bar");
		//simpleParseTest("select acme.tpilot.PilotID foo, sakila.actor.last_name, boo SumBoo from tpilot, actor");
		//simpleParseTest("SELECT a,b,c FROM tFoo ORDER BY Gorp WHERE a=`aaa`");
		//simpleParseTest("SELECT a,b,c FROM tFoo ORDER BY Gorp WHERE a=`aaa`");
	}
	private static void simpleParseTest(String sql) {
		try {
			QueryDefinition qd = new QueryDefinition("","","",new QueryTypeSelect(), "dummyQueryName", sql, "dummySchemaName");
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
	/**
	 * A relative complex query from the Sakila schema
	 * @return
	 */
	static String hereIsASakilaView() {
		return "SELECT`a`.`actor_id` AS `actor_id`,  `a`.`first_name` AS `first_name`,  `a`.`last_name` AS `last_name`,  GROUP_CONCAT(DISTINCT CONCAT(`c`.`name`,  ': ',  (SELECT GROUP_CONCAT(`f`.`title`ORDER BY `f`.`title` ASC SEPARATOR ', ')FROM ((`sakila`.`film` `f` JOIN `sakila`.`film_category` `fc` ON ((`f`.`film_id` = `fc`.`film_id`))) JOIN `sakila`.`film_actor` `fa` ON ((`f`.`film_id` = `fa`.`film_id`)))WHERE ((`fc`.`category_id` = `c`.`category_id`)  AND (`fa`.`actor_id` = `a`.`actor_id`))))ORDER BY `c`.`name` ASC SEPARATOR '; ') AS `film_info` FROM  (((`sakila`.`actor` `a`  LEFT JOIN `sakila`.`film_actor` `fa` ON ((`a`.`actor_id` = `fa`.`actor_id`)))  LEFT JOIN `sakila`.`film_category` `fc` ON ((`fa`.`film_id` = `fc`.`film_id`)))  LEFT JOIN `sakila`.`category` `c` ON ((`fc`.`category_id` = `c`.`category_id`))) GROUP BY `a`.`actor_id` , `a`.`first_name` , `a`.`last_name`";
	}
}
