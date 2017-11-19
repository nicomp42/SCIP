package edu.UC.PhD.CodeProject.nicholdw.queryParser;

import java.util.ArrayList;

import org.Antlr4MySQLFromANTLRRepo.MySQLLexxerFromANTLRRepo;
import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepo;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTables;
import edu.UC.PhD.CodeProject.nicholdw.queryType.Select;

public class QueryParser {
	private static ArrayList<String> keywords;
	static {
		QueryParser.keywords = new ArrayList<String>();
		QueryParser.keywords.add("SELECT");
		QueryParser.keywords.add("APPEND");
		QueryParser.keywords.add("INSERT");
		QueryParser.keywords.add("HAVING");
		QueryParser.keywords.add("WHERE");
	}
	/**
	 * Populate the Query Definition with attributes and associated alias names, table names, and schema.
	 * @param queryDefinition
	 */
	public void parseQuery(QueryDefinition queryDefinition) {
		// TODO finish this! ;)
		String sql = queryDefinition.getSql();

		sql = sql.replace("`", "");			// TODO this has to come out when the parser is updated.

	    // Get our lexxer
		MySQLLexxerFromANTLRRepo lexxer = new MySQLLexxerFromANTLRRepo(new ANTLRInputStream(sql));

	    // Get a list of matched tokens
	    CommonTokenStream tokens = new CommonTokenStream(lexxer);

	    // Pass the tokens to the parser
	    MySQLParserFromANTLRRepo parser = new MySQLParserFromANTLRRepo(tokens);

	    // Specify our entry point
	    ParserRuleContext MYSQLSentenceContext = parser.select_clause();

	    // Walk it with our listener
	    ParseTreeWalker walker = new ParseTreeWalker();
	    AntlrMySQLListener_test listener = new AntlrMySQLListener_test(queryDefinition);
	    walker.walk(listener, MYSQLSentenceContext);
	}

	/**
	 * Check to see if a string is an attribute or something else like a constant or a keyword
	 * @param attribute
	 * @return True if attribute is indeed an attribute, false otherwise
	 */
	public static boolean isAttribute(String attribute) {
		boolean status = true;		// Hope for the best
		char firstChar = attribute.charAt(0);
		CharSequence charSeq = firstChar + "";
		if ("0123456789".contains(charSeq)) {
			status = false;		// Attribute starts with a number
		} else {
			String upperCaseAttribute = attribute.toUpperCase();	// Attribute is a keyword
			if (QueryParser.keywords.contains(upperCaseAttribute)) {
				status = false;
			} else {

			}
		}
		return status;
	}
}
