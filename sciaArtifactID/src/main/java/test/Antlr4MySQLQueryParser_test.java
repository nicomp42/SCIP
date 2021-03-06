package test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
/**
 * MySQL tests. The syntax is exclusively MySQL. 
 * @author nicomp
 *
 */
public class Antlr4MySQLQueryParser_test {

	public static void main( String[] args) throws Exception
	{
		testSelect();
//		testCreate();
//		testAlter();
//		testDrop();
//		testMiscellaneous();
	}
	private static void testMiscellaneous() {
		//simpleParseTest("SHOW TABLES");
		//simpleParseTest("DROP TABLE `foo`");
		//simpleParseTest("DROP VIEW IF EXISTS `foo`, `bar` RESTRICT");
	}
	private static void testAlter() throws Exception {
		QueryDefinition qd = null;
		qd = simpleParseTest(alterTests[0]);
		qd.print(System.out, false);		
	}
	private static void testSelect() throws Exception {
		QueryDefinition qd = null;
		qd = simpleParseTest(selectTests[16]);
		qd.print(System.out, false);		
	}
	private static void testCreate() throws Exception {
		QueryDefinition qd = null;
		qd = simpleParseTest(createTests[0]);
		qd.print(System.out, false);		
	}
	private static void testDrop() throws Exception {
		QueryDefinition qd = null;
		qd = simpleParseTest(dropTests[1]);
		qd.print(System.out, false);		
	}
	private static QueryDefinition simpleParseTest(String sql) {
		System.out.println("Antlr4MySQLQueryParser_test.simpleParseTest(): Parsing SQL: " + sql);
		QueryDefinition queryDefinition = null;
		try {
			queryDefinition = new QueryDefinition("","","",new QueryTypeSelect(), "dummyQueryName", sql, "dummySchemaName");
			QueryParser queryParser = new QueryParser();
			queryParser.parseQuery(queryDefinition);
			//System.out.println("Antlr4MySQLQueryParser_test.simpleParseTest(): Atttibutes found in the query:");
			//for (QueryAttribute qa: queryDefinition.getQueryAttributes()) {
			//	System.out.println(qa.toString());
			//}
		} catch (Exception ex) {
			System.out.println("Antlr4MySQLQueryParser_test.simpleParseTest(): " + ex.getLocalizedMessage());
		}
		return queryDefinition;
	}
	// Sakila Queries for parse testing
	private static String actor_info = "SELECT`a`.`actor_id` AS `actor_id`,  `a`.`first_name` AS `first_name`,  `a`.`last_name` AS `last_name`,  GROUP_CONCAT(DISTINCT CONCAT(`c`.`name`,  ': ',  (SELECT GROUP_CONCAT(`f`.`title`ORDER BY `f`.`title` ASC SEPARATOR ', ')FROM ((`sakila`.`film`  JOIN `sakila`.`film_category` `fc` ON ((`f`.`film_id` = `fc`.`film_id`))) JOIN `sakila`.`film_actor` `fa` ON ((`f`.`film_id` = `fa`.`film_id`)))WHERE ((`fc`.`category_id` = `c`.`category_id`)  AND (`fa`.`actor_id` = `a`.`actor_id`))))ORDER BY `c`.`name` ASC SEPARATOR '; ') AS `film_info` FROM  (((`sakila`.`actor` `a`  LEFT JOIN `sakila`.`film_actor` `fa` ON ((`a`.`actor_id` = `fa`.`actor_id`)))  LEFT JOIN `sakila`.`film_category` `fc` ON ((`fa`.`film_id` = `fc`.`film_id`)))  LEFT JOIN `sakila`.`category` `c` ON ((`fc`.`category_id` = `c`.`category_id`))) GROUP BY `a`.`actor_id` , `a`.`first_name` , `a`.`last_name`";
	private static String sales_by_film_category = "SELECT `c`.`name` AS `category`, SUM(`p`.`amount`) AS `total_sales` FROM (((((`sakila`.`payment` `p` JOIN `sakila`.`rental` `r` ON ((`p`.`rental_id` = `r`.`rental_id`))) JOIN `sakila`.`inventory` `i` ON ((`r`.`inventory_id` = `i`.`inventory_id`))) JOIN `sakila`.`film` `f` ON ((`i`.`film_id` = `f`.`film_id`))) JOIN `sakila`.`film_category` `fc` ON ((`f`.`film_id` = `fc`.`film_id`))) JOIN `sakila`.`category` `c` ON ((`fc`.`category_id` = `c`.`category_id`))) GROUP BY `c`.`name` ORDER BY `total_sales` DESC";
	private static String selectTests[] = {	// https://dev.mysql.com/doc/refman/5.7/en/select.html
		// 0: myView01: one minimal attribute, one minimal table.
		"SELECT `myAttribute01` FROM `myTable01`",	 
		// 1: myView01a: one minimal attribute, one minimal table and some SELECT qualifiers.
		"SELECT ALL HIGH_PRIORITY STRAIGHT_JOIN `myAttribute01` FROM `myTable01`",	 
		// 2: myView01b: three minimal attributes, one minimal table.
		"SELECT `myAttribute01`, `myAttribute02`, `myAttribute03` FROM `myTable01`",	 
		// 3: myView02: one fully qualified attribute with alias, one fully qualified table.
		"SELECT `mySchema01`.`myTable01`.`myAttribute01` AS `myAlias01` FROM `mySchema01`.`myTable01`",
		// 4: myView03: one fully qualified attribute with alias, one fully qualified table using the table alias
		"SELECT `myTablealias`.`myAttribute01` AS `myAlias01` FROM `mySchema01`.`myTable01` mytablealias",
		// 5: myView04: one function call with minimal attribute, one minimal table.
		"SELECT `MYFUNCTION01`(`myschema01`.`mytable01`.`myAttribute01`) AS `myAlias` FROM `myschema01`.`mytable01`",
		// 6: myView05: an aggregate function. Note the weird alias that was built by default by MySQLWorkbench or the MySQL Server.
		"SELECT MAX(`myschema01`.`mytable01`.`myAttribute01`) AS `MAX(myAttribute01)` FROM `myschema01`.`mytable01`",
		// myViewnn: Use variables in the SELECT statement. This can be done in an SQL tab in MySQLWorkbench but cannot be done in a view: https://dev.mysql.com/doc/refman/5.6/en/create-view.html
		//"SELECT `myAttribute01`,`myAttribute02`,`myAttribute03` INTO @myvar01 , @@myvar02, @myvar03 FROM `mytable01` WHERE `myAttribute01` = 1;"
		// 7: myView06a: referencing attributes in a view and a table
		"SELECT `myview06`.`myAttribute01` AS `myView06.myAttribute01`, `myschema01`.`mytable02`.`myAttribute01` AS `myTable02.myAttribute01` FROM (`myschema01`.`mytable02` JOIN `myschema01`.`myview06`)",
		// 8: myView06b: Another level of nesting: myView06b selects from myView06a which selects from myTable01
		" SELECT `myview06a`.`myView06.myAttribute01` AS `myView06a.myAttribute01`, `myview06`.`myAttribute01` AS `myView06.myAttribute01`, `mytable02`.`myAttribute01` AS `myTable02.myAttribute01` FROM ((`mytable02` JOIN `myview06`) JOIN `myview06a`)",
		// 9: myView08: simple select with an ORDER by Clause
		"SELECT `myschema01`.`mytable01`.`myAttribute01` AS `myAttribute01` FROM `myschema01`.`mytable01` ORDER BY `myschema01`.`mytable01`.`myAttribute02`",
		// 10: myView09: a SELECT using the alias on the table name
		"SELECT `alias01`.`myAttribute01` AS `myAttribute01` FROM `mytable01` `alias01`",
		// 11: myview10: a SELECT using the alias on the table name and with a join of two tables
		"SELECT `alias01`.`myAttribute01` AS `alias01.myattribute01`, `alias02`.`myAttribute01` AS `alias02.myAttribute01` FROM(`mytable01` `alias01 `JOIN `mytable02` `alias02`",
		// 12: myView10a: two attributes selected from a table 
		"SELECT `alias01`.`myAttribute01` AS `myAttribute01`, `alias01`.`myAttribute02` AS `myAttribute02` FROM (`mytable01` `alias01` JOIN `mytable02` `alias02`)",
		// 13: Just made it up. Attributes with no qualifiers, with table only, and with schema and table. Then three more similarly patterened attributes with aliases. Three tables also with aliases
		"SELECT `a`, `tt`.`aa`, `sss`.`ttt`.`aaa`, `a1` AS `alias1`, `tt1`.`aa1` AS `alias2`, `sss1`.`ttt1`.`aaa1` AS `alias3` from `t` `talias1` join `tt` `talias2` join `ttt` `talias2` WHERE `aFilter`=\"12345\" ORDER BY `aOrderBy` + `bOrderBy` + SUM(`cOrderBy`) ;",
		// 14. myView11: An attribute with two aliases
		"SELECT `mytable01`.`myAttribute01` AS `myAlias01`, `mytable01`.`myAttribute01` AS `myAlias01a` FROM `mytable01`",
		// 15. myView12: A simple select that includes a function call. The point is that the expression does not need a table to complete the provenance
		"SELECT `mytable01`.`myAttribute01` AS `myAlias01`, `mytable01`.`myAttribute02` AS `myAttribute02`, CURDATE() AS `myCurDate` FROM `mytable01`",
		// 16. myView12a: A view that references a calculated field in another view. The provenance of the calculated field reference does not include a table
		"SELECT `myview12`.`myCurDate` AS `myCurDate` FROM `myview12`",
		};
	
	private static String dropTests[] = {
		// Drop a table	
		"DROP TABLE tTable99",
		"DROP TABLE mySchema01.tTable99",
		"DROP TABLE tTable99, tTable98, tTable97",
		"DROP TABLE mySchema01.tTable99, mySchema01.tTable98, mySchema01.tTable97",
		"DROP TEMPORARY TABLE tTable99",
		"DROP TEMPORARY TABLE IF EXISTS tTable99",
		"DROP TEMPORARY TABLE IF EXISTS tTable99, tTable98",
		"DROP TEMPORARY TABLE IF EXISTS tTable99, tTable98 RESTRICT",
		"DROP TEMPORARY TABLE IF EXISTS tTable99, tTable98 CASCADE",
		};
	
	private static String createTests[] = {
		// Stored Procedure spCreateMyTable99: Create a table. We can't store this as a view so it's a stored procedure so we have a place to put it. 	
		"CREATE TABLE `myschema01`.`mytable99` (`myAttribute01` INT NOT NULL, `myAttribute02` VARCHAR(45) NULL, `myAttribute03` VARCHAR(45) NULL, PRIMARY KEY (`myAttribute01`));",		
		};
	
	private static String alterTests[] = {
		// spAlter01: drop an attribute from a table	
		"Alter table mySchema01.myTable01 drop column `myAttribute01`",
		// spAlter02: drop an attribute from a view. There is no ALTER VIEW except to completely redefine the view.
		"Alter table myView10a drop column `myAttribute01`;",  
	};
		
	private static String createTables[] = {
		// Create myyTable01
		"CREATE TABLE `mytable01` (`myAttribute01` int(11) NOT NULL, `myAttribute02` varchar(45) DEFAULT NULL,  `myAttribute03` varchar(45) DEFAULT NULL,  PRIMARY KEY (`myAttribute01`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;"
	};
}

//simpleParseTest("SELECT `tAlpha`.`epsilon` AS `myEpsilon`, (`tAlpha`.`beta` + `tDelta`.`gamma`) AS `mySUM` FROM `tAlpha` `tA` INNER JOIN `tDelta` `tD`;");		// Test compound attributes 
//qd = simpleParseTest("SELECT `mySchema`.`fSIN`(`tZeta`.`zeta`) as sinZeta, SIN(`tZeta`.`zeta`) as sinZeta01, `GetAlphaStdDev`(\"tAlpha\") AS `myFunctionResult`, `mySchema`.`tAlpha`.`epsilon` AS `myEpsilon`, `tAlpha`.`omega`, ((`tZeta`.`upsilon`) + (`tAlpha`.`beta` + `tDelta`.`gamma`)) AS `mySUM` FROM `tAlpha` `tA` INNER JOIN `tDelta` `tD`;");		// Test compound attributes 
//qd.print(System.out);
//		simpleParseTest("select `queryprocessingtest`.`ttablea`.`testString` AS `testString`,`queryprocessingtest`.`ttablea`.`testInt` AS `testInt`,`queryprocessingtest`.`ttablea`.`testDateTime` AS `testDateTime`,`queryprocessingtest`.`ttablea`.`testDouble` AS `testDouble`,`queryprocessingtest`.`twidget`.`Widget` AS `Widget`,`qc`.`testFieldATableC` AS `testfieldatablec`,`qd`.`testFieldATableD` AS `testfieldatabled`,`qlevelaa`.`TestFieldATableE` AS `testfieldatablee`,`qlevelaa`.`TestFieldATableF` AS `testfieldatablef` from `queryprocessingtest`.`ttablea` join `queryprocessingtest`.`ttableb` join `queryprocessingtest`.`qc` join `queryprocessingtest`.`qd` join `queryprocessingtest`.`twidget` join `queryprocessingtest`.`qlevelaa`");
//simpleParseTest(" SELECT `schematopologytest01`.`talpha`.`CommonField` AS `CommonField_tAlpha`,`schematopologytest01`.`tbeta`.`CommonField` AS `CommonField_tBeta`, `tBeta`.`Spoon` FROM (`schematopologytest01`.`talpha` JOIN `schematopologytest01`.`tbeta`)");
//simpleParseTest(actor_info);
//simpleParseTest(sales_by_film_category);
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
