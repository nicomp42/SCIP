
package edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4;

import java.util.List;
import java.util.Objects;

import org.Antlr4MySQLFromANTLRRepo.MySqlParser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.AliasNameClass;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClause;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClauseSelect;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClauseUnknown;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClauseWhere;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;

public class AntlrMySQLListener extends org.Antlr4MySQLFromANTLRRepo.MySqlParserBaseListener {

	private QueryDefinition queryDefinition;
	private QueryClause queryClause;
	private ColumnNameParts columnNameParts = new ColumnNameParts();
	private TableNameParts tableNameParts = new TableNameParts();
	private QueryTable currentQueryTable;

	public AntlrMySQLListener(QueryDefinition queryDefinition) {
		System.out.println("AntlrMySQLListener.AntlrMySQLListener(qd)");
		this.queryDefinition = queryDefinition;
		queryClause = new QueryClauseUnknown();
	}

	boolean includeAllFields;

	/*
	 * @Override public void enterEveryRule(ParserRuleContext ctx) {
	 * System.out.println(ctx.getText()); }
	 */
	@Override
	public void enterSchema_name(@NotNull MySqlParser.Schema_nameContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSchema_name: " + ctx.getText() + ":");
	}

	@Override
	public void exitSchema_name(@NotNull MySqlParser.Schema_nameContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSchema_name: " + ctx.getText() + ":");
		columnNameParts.schemaName = ctx.getText();
	}
	@Override public void enterSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSimpleSelect: " + ctx.getText());
	}

	@Override
	public void enterSelect_clause(MySqlParser.Select_clauseContext ctx) {
		queryClause = new QueryClauseSelect();
		Log.logProgress("enterSelect_clause"); // + ctx.getText() + ":" );
	}

	@Override
	public void exitRight_element(@NotNull MySqlParser.Right_elementContext ctx) {
		Log.logProgress("exitRight_element(): " + ctx.getText() + ": ctx.getText() = " + ctx.getText());
		try {
			visit(ctx);
		} catch (Exception ex) {
			Log.logError("exitRight_element: " + ex.getLocalizedMessage(), ex);
		}
	}

	public void exitLeft_element(@NotNull MySqlParser.Left_elementContext ctx) {
		Log.logProgress("exitLeft_element(): " + ctx.getText() + ": ctx.getText() = " + ctx.getText());
		try {
			visit(ctx);
		} catch (Exception ex) {
			Log.logError("exitLeft_element: " + ex.getLocalizedMessage(), ex);
		}
	}

	private boolean checkForAttribute(ParseTree pt) {
		boolean isAttribute = false;
		String attribute = pt.getText();
		Log.logProgress("Terminal Node: " + attribute);
		if (QueryParser.isAttribute(attribute)) {
			// It's an attribute. Add it to the list of attributes in the current query definition
			isAttribute = true;
			queryDefinition.getQueryAttributes().addAttribute(new QueryAttribute(queryDefinition.getSchemaName(), "", attribute, null, queryClause));
		}
		return isAttribute;
	}

	private void visitParseTree(ParseTree pt) throws ParseException {
		int terminalNodeCount = 0;
		for (int i = 0; i < pt.getChildCount(); i++) {
			if (pt.getChild(i) instanceof TerminalNode) {
				terminalNodeCount++;
			}
		}
		if (terminalNodeCount == 3) {
			// The expression is {table or alias}.{attribute} - we can't store
			// it as three attributes!
			String tableName = pt.getChild(0).getText(); // Index 0 is the table
															// name or table
															// alias
			String attributeName = pt.getChild(2).getText(); // Index 2 is the
																// attribute
																// name or
																// attribute
																// alias
			queryDefinition.getQueryAttributes().addAttribute(
					new QueryAttribute(queryDefinition.getSchemaName(), tableName, attributeName, null, queryClause));
		} else /* if (terminalNodeCount == 1) */ {
			for (int i = 0; i < pt.getChildCount(); i++) {
				if (pt.getChild(i) instanceof TerminalNode) {
					checkForAttribute(pt.getChild(i));
				} else {
					ParseTree x = pt.getChild(i);
					visitParseTree(x);
				}
			}
		} // else {
			// throw new ParseException("visitParseTree(): terminal node count
			// must be 1 or 3, it turned out to be " + terminalNodeCount + "
			// parseTree = " + pt.getText());
			// }
	}
	private void visit(Left_elementContext ctx) throws ParseException {
		for (int i = 0; i < ctx.getChildCount(); i++) {
			if (ctx.getChild(i) instanceof TerminalNode) {
				checkForAttribute(ctx.getChild(i));
			} else {
				ParseTree x = ctx.getChild(i);
				visitParseTree(x);
			}
		}
	}
	private void visit(Right_elementContext ctx) throws ParseException {
		for (int i = 0; i < ctx.getChildCount(); i++) {
			if (ctx.getChild(i) instanceof TerminalNode) {
				checkForAttribute(ctx.getChild(i));
			} else {
				ParseTree x = ctx.getChild(i);
				visitParseTree(x);
			}
		}
	}
	@Override
	public void enterWhere_clause(MySqlParser.Where_clauseContext ctx) {
		queryClause = new QueryClauseWhere();
		Log.logProgress("enterWhere_clause()"); // : " + ctx.getText() + ":
													// ctx.getText() = " +
													// ctx.getText());
	}
	public void exitWhere_clause(MySqlParser.Where_clauseContext ctx) {
		Log.logProgress("exitWhere_clause()"); // :
													// ctx.expression().getText()
													// = " +
													// ctx.expression().getText());
		// System.out.println("ctx.expression().getChild(0) = " +
		// ctx.expression().getChild(0).getText());
		// printTerminalNodes(ctx.getTokens(0));
	}
	@Override
	public void enterColumn_name(MySqlParser.Column_nameContext ctx) {
		Log.logProgress("enterColumn_name(): " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
		columnNameParts.init();
	}
	@Override
	public void enterColumn_name_alias(MySqlParser.Column_name_aliasContext ctx) {
		Log.logProgress("enterColumn_name_alias(): " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
	}
	@Override
	public void exitColumn_name_alias(MySqlParser.Column_name_aliasContext ctx) {
		Log.logProgress("exitColumn_name_alias(): " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
		columnNameParts.aliasName = ctx.getText(); // There will be nothing but
													// the terminal node when we
													// are in this context
	}
	@Override
	public void exitColumn_name(MySqlParser.Column_nameContext ctx) {
		Log.logProgress("exitColumn_name(): " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
		parseColumnName(ctx.ID()); // ctx.getText());
		queryDefinition.getQueryAttributes().addAttribute(new QueryAttribute(columnNameParts.schemaName,
				                                                             columnNameParts.tableName,
				                                                             columnNameParts.attributeName,
				                                                             new AliasNameClass(columnNameParts.aliasName),
				                                                             queryClause));
		columnNameParts.init();
	}
	private void parseColumnName(List<TerminalNode> parts) {
		// columnNameParts.init(); Do not do this! The alias is already stored
		// here
		switch (parts.size()) {
		case 1:
			columnNameParts.attributeName = parts.get(0).toString();
			break;
		case 2:
			columnNameParts.attributeName = parts.get(1).toString();
			columnNameParts.tableName = parts.get(0).toString();
			break;
		case 3:
			columnNameParts.attributeName = parts.get(2).toString();
			columnNameParts.tableName = parts.get(1).toString();
			columnNameParts.schemaName = parts.get(0).toString();
			break;
		default:
			Log.logProgress("parseColumnName(List<TerminalNode>): not the right number of parts: " + parts.size() + " ("
					+ parts.toString() + ")");
		}
	}
	private void parseTableName(List<TerminalNode> parts) {
		// tableNameParts.init(); Do not do this! The alias is already stored
		// here
		switch (parts.size()) {
		case 1:
			tableNameParts.tableName = parts.get(0).toString();
			break;
		case 2:
			tableNameParts.tableName = parts.get(1).toString();
			columnNameParts.schemaName = parts.get(0).toString();
			break;
		default:
			Log.logProgress("parseTableName(List<TerminalNode>): not the right number of parts: " + parts.size() + " ("
					+ parts.toString() + ")");
		}
	}
	public void exitTable_name(MySqlParser.Table_nameContext ctx) {
		// TODO our grammar doesn't handle appended schema names on tables, we need to add that logic when we get new grammar.
		Log.logProgress("exitTable_name: " + ctx.getText());
		tableNameParts.tableName = ctx.getText();
		currentQueryTable = new QueryTable(tableNameParts.schemaName, tableNameParts.tableName, tableNameParts.aliasName, queryClause);	// There won't be an alias, yet.
		queryDefinition.getQueryTables().addQueryTable(currentQueryTable);
		tableNameParts.init();
		// parseTableName(ctx.ID()); // ctx.getText());

	}
	public void enterTable_alias(Table_aliasContext ctx) {
		Log.logProgress("enterTable_alias: " + ctx.getText());
	}
	public void exitTable_alias(Table_aliasContext ctx) {
		Log.logProgress("exitTable_alias: " + ctx.getText());
		currentQueryTable.setAliasName(ctx.getText());	// There'd better be a table! See the exitTable_name() method
	}
	private void printTerminalNodes(List<TerminalNode> tns) {
		for (TerminalNode tn : tns) {
			Log.logProgress(tn.toString());
		}
	}
	class ColumnNameParts {
		String schemaName;
		String tableName;
		String attributeName;
		String aliasName;
		ColumnNameParts() {init();}
		void init() {
			schemaName = "";
			tableName = "";
			attributeName = "";
			aliasName = "";
		}
	}
	class TableNameParts {
		String schemaName;
		String tableName;
		String aliasName;
		TableNameParts() {init();}
		void init() {
			schemaName = "";
			tableName = "";
			aliasName = "";
		}
	}
}
/*
 * @Override public void exitExpression(@NotNull
 * MySqlParser.ExpressionContext ctx) {
 * System.out.println("exitExpression(): " + ctx.getText() +
 * ": ctx.getText() = " + ctx.getText()); }
 *
 * @Override public void exitSimple_expression(@NotNull
 * MySqlParser.Simple_expressionContext ctx) { try {
 * System.out.println("exitSimple_expression(): " + ctx.getText() +
 * ": ctx.getText() = " + ctx.getText() + " left element = " +
 * ctx.left_element().getText() + " right element = " +
 * ctx.right_element().getText()); visit(ctx.left_element());
 * visit(ctx.right_element()); // for (TerminalNode t: ctx.getTokens(1)) { //
 * System.out.println("********************** exitSimple_expression Token " +
 * t.toString()); // } } catch (Exception ex) {
 * Log.logProgress("AntlrMySQLListener.exitSimple_expression(): " +
 * ex.getLocalizedMessage()); } }
 */