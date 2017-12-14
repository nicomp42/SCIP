package edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.Antlr4MySQLFromANTLRRepo.CaseInsensitiveInputStream;
import org.Antlr4MySQLFromANTLRRepo.MySqlLexer;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTables;

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
	 * @throws IOException
	 */
	public void parseQuery(QueryDefinition queryDefinition) throws IOException {
		String sql = queryDefinition.getSql();
		// Get our lexxer
//		This works for ANTLR Runtime 4.7, but not for 4.3.5
//		InputStream stream = new ByteArrayInputStream(sql.getBytes(StandardCharsets.UTF_8));
//		MySqlLexer lexxer = new MySqlLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));	// This will throw an exception.

//		This works for ANTLR 4.3.5...
//		CharStream stream = new ANTLRInputStream(sql);
		// See gist.github.com/sharwell/9424666 for the case-insensitive input stream.
		CharStream stream = new CaseInsensitiveInputStream(sql);
		MySqlLexer lexxer = new MySqlLexer(stream);

		// Get a list of matched tokens
		//List<? extends Token> x = lexxer.getAllTokens();

		CommonTokenStream tokens = new CommonTokenStream(lexxer);

		// Pass the tokens to the parser
		MySqlParser parser = new MySqlParser(tokens);
		// Specify our entry point
		ParserRuleContext MYSQLSentenceContext = parser.root();

		// Walk it and attach our listener
		ParseTreeWalker walker = new ParseTreeWalker();
		AntlrMySQLListener listener = new AntlrMySQLListener(queryDefinition);
		parser.addParseListener(listener);
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
