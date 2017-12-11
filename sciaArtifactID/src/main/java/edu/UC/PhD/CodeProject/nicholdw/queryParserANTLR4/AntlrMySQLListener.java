
package edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4;

import java.util.List;
import java.util.Objects;

import org.Antlr4MySQLFromANTLRRepo.MySqlParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
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
	boolean includeAllFields;

	public AntlrMySQLListener(QueryDefinition queryDefinition) {
		System.out.println("AntlrMySQLListener.AntlrMySQLListener(qd)");
		this.queryDefinition = queryDefinition;
		queryClause = new QueryClauseUnknown();
	}
	@Override public void enterRoot(MySqlParser.RootContext ctx) {
		System.out.println("AntlrMySQLListener.enterRoot()");
	}
	@Override public void exitRoot(MySqlParser.RootContext ctx) {
		System.out.println("AntlrMySQLListener.exitRoot()");
	}
	@Override public void enterSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSimpleSelect: " + ctx.getText());

	}

	@Override public void exitSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSimpleSelect: " + ctx.getText());
	}

	@Override public void enterSelectElements(MySqlParser.SelectElementsContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSelectElements: " + ctx.getText());
	}

	@Override public void exitSelectElements(MySqlParser.SelectElementsContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSelectElements: " + ctx.getText());
	}

	@Override public void enterSelectStarElement(MySqlParser.SelectStarElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSelectStarElement: " + ctx.getText());
	}

	@Override public void exitSelectStarElement(MySqlParser.SelectStarElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSelectStarElement: " + ctx.getText());
	}
	@Override public void enterSelectColumnElement(MySqlParser.SelectColumnElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSelectColumnElement: " + ctx.getText());
	}
	@Override public void exitSelectColumnElement(MySqlParser.SelectColumnElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSelectColumnElement: " + ctx.getText());
	}

	@Override public void enterSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSelectFunctionElement: " + ctx.getText());
	}
	@Override public void exitSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSelectFunctionElement: " + ctx.getText());
	}
	@Override public void enterSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSelectExpressionElement: " + ctx.getText());
	}
	@Override public void exitSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSelectExpressionElement: " + ctx.getText());
	}

	@Override public void enterSelectIntoVariables(MySqlParser.SelectIntoVariablesContext ctx) {
		Log.logProgress("AntlrMySQLListener.enterSelectIntoVariables: " + ctx.getText());
	}
	@Override public void exitSelectIntoVariables(MySqlParser.SelectIntoVariablesContext ctx) {
		Log.logProgress("AntlrMySQLListener.exitSelectIntoVariables: " + ctx.getText());

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
	@Override public void enterSelectIntoDumpFile(MySqlParser.SelectIntoDumpFileContext ctx) { }
	@Override public void exitSelectIntoDumpFile(MySqlParser.SelectIntoDumpFileContext ctx) { }
	@Override public void enterSelectIntoTextFile(MySqlParser.SelectIntoTextFileContext ctx) { }
	@Override public void exitSelectIntoTextFile(MySqlParser.SelectIntoTextFileContext ctx) { }
	@Override public void enterSelectFieldsInto(MySqlParser.SelectFieldsIntoContext ctx) { }
	@Override public void exitSelectFieldsInto(MySqlParser.SelectFieldsIntoContext ctx) { }
	@Override public void enterSelectLinesInto(MySqlParser.SelectLinesIntoContext ctx) { }
	@Override public void exitSelectLinesInto(MySqlParser.SelectLinesIntoContext ctx) { }
	@Override public void enterFromClause(MySqlParser.FromClauseContext ctx) { }
	@Override public void exitFromClause(MySqlParser.FromClauseContext ctx) { }
	@Override public void enterGroupByItem(MySqlParser.GroupByItemContext ctx) { }
	@Override public void exitGroupByItem(MySqlParser.GroupByItemContext ctx) { }
	@Override public void enterLimitClause(MySqlParser.LimitClauseContext ctx) { }
	@Override public void exitLimitClause(MySqlParser.LimitClauseContext ctx) { }
	@Override public void enterStartTransaction(MySqlParser.StartTransactionContext ctx) { }
	@Override public void exitStartTransaction(MySqlParser.StartTransactionContext ctx) { }
	@Override public void enterBeginWork(MySqlParser.BeginWorkContext ctx) { }
	@Override public void exitBeginWork(MySqlParser.BeginWorkContext ctx) { }
	@Override public void enterCommitWork(MySqlParser.CommitWorkContext ctx) { }
	@Override public void exitCommitWork(MySqlParser.CommitWorkContext ctx) { }
	@Override public void enterRollbackWork(MySqlParser.RollbackWorkContext ctx) { }
	@Override public void exitRollbackWork(MySqlParser.RollbackWorkContext ctx) { }
	@Override public void enterSavepointStatement(MySqlParser.SavepointStatementContext ctx) { }
	@Override public void exitSavepointStatement(MySqlParser.SavepointStatementContext ctx) { }
	@Override public void enterRollbackStatement(MySqlParser.RollbackStatementContext ctx) { }
	@Override public void exitRollbackStatement(MySqlParser.RollbackStatementContext ctx) { }
	@Override public void enterReleaseStatement(MySqlParser.ReleaseStatementContext ctx) { }
	@Override public void exitReleaseStatement(MySqlParser.ReleaseStatementContext ctx) { }
	@Override public void enterLockTables(MySqlParser.LockTablesContext ctx) { }
	@Override public void exitLockTables(MySqlParser.LockTablesContext ctx) { }
	@Override public void enterUnlockTables(MySqlParser.UnlockTablesContext ctx) { }
	@Override public void exitUnlockTables(MySqlParser.UnlockTablesContext ctx) { }
	@Override public void enterSetAutocommitStatement(MySqlParser.SetAutocommitStatementContext ctx) { }
	@Override public void exitSetAutocommitStatement(MySqlParser.SetAutocommitStatementContext ctx) { }
	@Override public void enterSetTransactionStatement(MySqlParser.SetTransactionStatementContext ctx) { }
	@Override public void exitSetTransactionStatement(MySqlParser.SetTransactionStatementContext ctx) { }
	@Override public void enterTransactionMode(MySqlParser.TransactionModeContext ctx) { }
	@Override public void exitTransactionMode(MySqlParser.TransactionModeContext ctx) { }
	@Override public void enterLockTableElement(MySqlParser.LockTableElementContext ctx) { }
	@Override public void exitLockTableElement(MySqlParser.LockTableElementContext ctx) { }
	@Override public void enterLockAction(MySqlParser.LockActionContext ctx) { }
	@Override public void exitLockAction(MySqlParser.LockActionContext ctx) { }
	@Override public void enterTransactionOption(MySqlParser.TransactionOptionContext ctx) { }
	@Override public void exitTransactionOption(MySqlParser.TransactionOptionContext ctx) { }
	@Override public void enterTransactionLevel(MySqlParser.TransactionLevelContext ctx) { }
	@Override public void exitTransactionLevel(MySqlParser.TransactionLevelContext ctx) { }
	@Override public void enterChangeMaster(MySqlParser.ChangeMasterContext ctx) { }
	@Override public void exitChangeMaster(MySqlParser.ChangeMasterContext ctx) { }
	@Override public void enterChangeReplicationFilter(MySqlParser.ChangeReplicationFilterContext ctx) { }
	@Override public void exitChangeReplicationFilter(MySqlParser.ChangeReplicationFilterContext ctx) { }
	@Override public void enterPurgeBinaryLogs(MySqlParser.PurgeBinaryLogsContext ctx) { }
	@Override public void exitPurgeBinaryLogs(MySqlParser.PurgeBinaryLogsContext ctx) { }
	@Override public void enterResetMaster(MySqlParser.ResetMasterContext ctx) { }
	@Override public void exitResetMaster(MySqlParser.ResetMasterContext ctx) { }
	@Override public void enterResetSlave(MySqlParser.ResetSlaveContext ctx) { }
	@Override public void exitResetSlave(MySqlParser.ResetSlaveContext ctx) { }
	@Override public void enterStartSlave(MySqlParser.StartSlaveContext ctx) { }
	@Override public void exitStartSlave(MySqlParser.StartSlaveContext ctx) { }
	@Override public void enterStopSlave(MySqlParser.StopSlaveContext ctx) { }
	@Override public void exitStopSlave(MySqlParser.StopSlaveContext ctx) { }
	@Override public void enterStartGroupReplication(MySqlParser.StartGroupReplicationContext ctx) { }
	@Override public void exitStartGroupReplication(MySqlParser.StartGroupReplicationContext ctx) { }
	@Override public void enterStopGroupReplication(MySqlParser.StopGroupReplicationContext ctx) { }
	@Override public void exitStopGroupReplication(MySqlParser.StopGroupReplicationContext ctx) { }
	@Override public void enterMasterStringOption(MySqlParser.MasterStringOptionContext ctx) { }
	@Override public void exitMasterStringOption(MySqlParser.MasterStringOptionContext ctx) { }
	@Override public void enterMasterDecimalOption(MySqlParser.MasterDecimalOptionContext ctx) { }
	@Override public void exitMasterDecimalOption(MySqlParser.MasterDecimalOptionContext ctx) { }
	@Override public void enterMasterBoolOption(MySqlParser.MasterBoolOptionContext ctx) { }
	@Override public void exitMasterBoolOption(MySqlParser.MasterBoolOptionContext ctx) { }
	@Override public void enterMasterRealOption(MySqlParser.MasterRealOptionContext ctx) { }
	@Override public void exitMasterRealOption(MySqlParser.MasterRealOptionContext ctx) { }
	@Override public void enterMasterUidListOption(MySqlParser.MasterUidListOptionContext ctx) { }
	@Override public void exitMasterUidListOption(MySqlParser.MasterUidListOptionContext ctx) { }
	@Override public void enterStringMasterOption(MySqlParser.StringMasterOptionContext ctx) { }
	@Override public void exitStringMasterOption(MySqlParser.StringMasterOptionContext ctx) { }
	@Override public void enterDecimalMasterOption(MySqlParser.DecimalMasterOptionContext ctx) { }
	@Override public void exitDecimalMasterOption(MySqlParser.DecimalMasterOptionContext ctx) { }
	@Override public void enterBoolMasterOption(MySqlParser.BoolMasterOptionContext ctx) { }
	@Override public void exitBoolMasterOption(MySqlParser.BoolMasterOptionContext ctx) { }
	@Override public void enterChannelOption(MySqlParser.ChannelOptionContext ctx) { }
	@Override public void exitChannelOption(MySqlParser.ChannelOptionContext ctx) { }
	@Override public void enterDoDbReplication(MySqlParser.DoDbReplicationContext ctx) { }
	@Override public void exitDoDbReplication(MySqlParser.DoDbReplicationContext ctx) { }
	@Override public void enterIgnoreDbReplication(MySqlParser.IgnoreDbReplicationContext ctx) { }
	@Override public void exitIgnoreDbReplication(MySqlParser.IgnoreDbReplicationContext ctx) { }
	@Override public void enterDoTableReplication(MySqlParser.DoTableReplicationContext ctx) { }
	@Override public void exitDoTableReplication(MySqlParser.DoTableReplicationContext ctx) { }
	@Override public void enterIgnoreTableReplication(MySqlParser.IgnoreTableReplicationContext ctx) { }
	@Override public void exitIgnoreTableReplication(MySqlParser.IgnoreTableReplicationContext ctx) { }
	@Override public void enterWildDoTableReplication(MySqlParser.WildDoTableReplicationContext ctx) { }
	@Override public void exitWildDoTableReplication(MySqlParser.WildDoTableReplicationContext ctx) { }
	@Override public void enterWildIgnoreTableReplication(MySqlParser.WildIgnoreTableReplicationContext ctx) { }
	@Override public void exitWildIgnoreTableReplication(MySqlParser.WildIgnoreTableReplicationContext ctx) { }
	@Override public void enterRewriteDbReplication(MySqlParser.RewriteDbReplicationContext ctx) { }
	@Override public void exitRewriteDbReplication(MySqlParser.RewriteDbReplicationContext ctx) { }
	@Override public void enterTablePair(MySqlParser.TablePairContext ctx) { }
	@Override public void exitTablePair(MySqlParser.TablePairContext ctx) { }
	@Override public void enterThreadType(MySqlParser.ThreadTypeContext ctx) { }
	@Override public void exitThreadType(MySqlParser.ThreadTypeContext ctx) { }
	@Override public void enterGtidsUntilOption(MySqlParser.GtidsUntilOptionContext ctx) { }
	@Override public void exitGtidsUntilOption(MySqlParser.GtidsUntilOptionContext ctx) { }
	@Override public void enterMasterLogUntilOption(MySqlParser.MasterLogUntilOptionContext ctx) { }
	@Override public void exitMasterLogUntilOption(MySqlParser.MasterLogUntilOptionContext ctx) { }
	@Override public void enterRelayLogUntilOption(MySqlParser.RelayLogUntilOptionContext ctx) { }
	@Override public void exitRelayLogUntilOption(MySqlParser.RelayLogUntilOptionContext ctx) { }
	@Override public void enterSqlGapsUntilOption(MySqlParser.SqlGapsUntilOptionContext ctx) { }
	@Override public void exitSqlGapsUntilOption(MySqlParser.SqlGapsUntilOptionContext ctx) { }
	@Override public void enterUserConnectionOption(MySqlParser.UserConnectionOptionContext ctx) { }
	@Override public void exitUserConnectionOption(MySqlParser.UserConnectionOptionContext ctx) { }
	@Override public void enterPasswordConnectionOption(MySqlParser.PasswordConnectionOptionContext ctx) { }
	@Override public void exitPasswordConnectionOption(MySqlParser.PasswordConnectionOptionContext ctx) { }
	@Override public void enterDefaultAuthConnectionOption(MySqlParser.DefaultAuthConnectionOptionContext ctx) { }
	@Override public void exitDefaultAuthConnectionOption(MySqlParser.DefaultAuthConnectionOptionContext ctx) { }
	@Override public void enterPluginDirConnectionOption(MySqlParser.PluginDirConnectionOptionContext ctx) { }
	@Override public void exitPluginDirConnectionOption(MySqlParser.PluginDirConnectionOptionContext ctx) { }
	@Override public void enterGtuidSet(MySqlParser.GtuidSetContext ctx) { }
	@Override public void exitGtuidSet(MySqlParser.GtuidSetContext ctx) { }
	@Override public void enterXaStartTransaction(MySqlParser.XaStartTransactionContext ctx) { }
	@Override public void exitXaStartTransaction(MySqlParser.XaStartTransactionContext ctx) { }
	@Override public void enterXaEndTransaction(MySqlParser.XaEndTransactionContext ctx) { }
	@Override public void exitXaEndTransaction(MySqlParser.XaEndTransactionContext ctx) { }
	@Override public void enterXaPrepareStatement(MySqlParser.XaPrepareStatementContext ctx) { }
	@Override public void exitXaPrepareStatement(MySqlParser.XaPrepareStatementContext ctx) { }
	@Override public void enterXaCommitWork(MySqlParser.XaCommitWorkContext ctx) { }
	@Override public void exitXaCommitWork(MySqlParser.XaCommitWorkContext ctx) { }
	@Override public void enterXaRollbackWork(MySqlParser.XaRollbackWorkContext ctx) { }
	@Override public void exitXaRollbackWork(MySqlParser.XaRollbackWorkContext ctx) { }
	@Override public void enterXaRecoverWork(MySqlParser.XaRecoverWorkContext ctx) { }
	@Override public void exitXaRecoverWork(MySqlParser.XaRecoverWorkContext ctx) { }
	@Override public void enterPrepareStatement(MySqlParser.PrepareStatementContext ctx) { }
	@Override public void exitPrepareStatement(MySqlParser.PrepareStatementContext ctx) { }
	@Override public void enterExecuteStatement(MySqlParser.ExecuteStatementContext ctx) { }
	@Override public void exitExecuteStatement(MySqlParser.ExecuteStatementContext ctx) { }
	@Override public void enterDeallocatePrepare(MySqlParser.DeallocatePrepareContext ctx) { }
	@Override public void exitDeallocatePrepare(MySqlParser.DeallocatePrepareContext ctx) { }
	@Override public void enterRoutineBody(MySqlParser.RoutineBodyContext ctx) { }
	@Override public void exitRoutineBody(MySqlParser.RoutineBodyContext ctx) { }
	@Override public void enterBlockStatement(MySqlParser.BlockStatementContext ctx) { }
	@Override public void exitBlockStatement(MySqlParser.BlockStatementContext ctx) { }
	@Override public void enterCaseStatement(MySqlParser.CaseStatementContext ctx) { }
	@Override public void exitCaseStatement(MySqlParser.CaseStatementContext ctx) { }
	@Override public void enterIfStatement(MySqlParser.IfStatementContext ctx) { }
	@Override public void exitIfStatement(MySqlParser.IfStatementContext ctx) { }
	@Override public void enterIterateStatement(MySqlParser.IterateStatementContext ctx) { }
	@Override public void exitIterateStatement(MySqlParser.IterateStatementContext ctx) { }
	@Override public void enterLeaveStatement(MySqlParser.LeaveStatementContext ctx) { }
	@Override public void exitLeaveStatement(MySqlParser.LeaveStatementContext ctx) { }
	@Override public void enterLoopStatement(MySqlParser.LoopStatementContext ctx) { }
	@Override public void exitLoopStatement(MySqlParser.LoopStatementContext ctx) { }
	@Override public void enterRepeatStatement(MySqlParser.RepeatStatementContext ctx) { }
	@Override public void exitRepeatStatement(MySqlParser.RepeatStatementContext ctx) { }
	@Override public void enterReturnStatement(MySqlParser.ReturnStatementContext ctx) { }
	@Override public void exitReturnStatement(MySqlParser.ReturnStatementContext ctx) { }
	@Override public void enterWhileStatement(MySqlParser.WhileStatementContext ctx) { }
	@Override public void exitWhileStatement(MySqlParser.WhileStatementContext ctx) { }
	@Override public void enterCloseCursor(MySqlParser.CloseCursorContext ctx) { }
	@Override public void exitCloseCursor(MySqlParser.CloseCursorContext ctx) { }
	@Override public void enterFetchCursor(MySqlParser.FetchCursorContext ctx) { }
	@Override public void exitFetchCursor(MySqlParser.FetchCursorContext ctx) { }
	@Override public void enterOpenCursor(MySqlParser.OpenCursorContext ctx) { }
	@Override public void exitOpenCursor(MySqlParser.OpenCursorContext ctx) { }
	@Override public void enterDeclareVariable(MySqlParser.DeclareVariableContext ctx) { }
	@Override public void exitDeclareVariable(MySqlParser.DeclareVariableContext ctx) { }
	@Override public void enterDeclareCondition(MySqlParser.DeclareConditionContext ctx) { }
	@Override public void exitDeclareCondition(MySqlParser.DeclareConditionContext ctx) { }
	@Override public void enterDeclareCursor(MySqlParser.DeclareCursorContext ctx) { }
	@Override public void exitDeclareCursor(MySqlParser.DeclareCursorContext ctx) { }
	@Override public void enterDeclareHandler(MySqlParser.DeclareHandlerContext ctx) { }
	@Override public void exitDeclareHandler(MySqlParser.DeclareHandlerContext ctx) { }
	@Override public void enterHandlerConditionCode(MySqlParser.HandlerConditionCodeContext ctx) { }
	@Override public void exitHandlerConditionCode(MySqlParser.HandlerConditionCodeContext ctx) { }
	@Override public void enterHandlerConditionState(MySqlParser.HandlerConditionStateContext ctx) { }
	@Override public void exitHandlerConditionState(MySqlParser.HandlerConditionStateContext ctx) { }
	@Override public void enterHandlerConditionName(MySqlParser.HandlerConditionNameContext ctx) { }
	@Override public void exitHandlerConditionName(MySqlParser.HandlerConditionNameContext ctx) { }
	@Override public void enterHandlerConditionWarning(MySqlParser.HandlerConditionWarningContext ctx) { }
	@Override public void exitHandlerConditionWarning(MySqlParser.HandlerConditionWarningContext ctx) { }
	@Override public void enterHandlerConditionNotfound(MySqlParser.HandlerConditionNotfoundContext ctx) { }
	@Override public void exitHandlerConditionNotfound(MySqlParser.HandlerConditionNotfoundContext ctx) { }
	@Override public void enterHandlerConditionException(MySqlParser.HandlerConditionExceptionContext ctx) { }
	@Override public void exitHandlerConditionException(MySqlParser.HandlerConditionExceptionContext ctx) { }
	@Override public void enterProcedureSqlStatement(MySqlParser.ProcedureSqlStatementContext ctx) { }
	@Override public void exitProcedureSqlStatement(MySqlParser.ProcedureSqlStatementContext ctx) { }
	@Override public void enterCaseAlternative(MySqlParser.CaseAlternativeContext ctx) { }
	@Override public void exitCaseAlternative(MySqlParser.CaseAlternativeContext ctx) { }
	@Override public void enterElifAlternative(MySqlParser.ElifAlternativeContext ctx) { }
	@Override public void exitElifAlternative(MySqlParser.ElifAlternativeContext ctx) { }
	@Override public void enterAlterUserMysqlV56(MySqlParser.AlterUserMysqlV56Context ctx) { }
	@Override public void exitAlterUserMysqlV56(MySqlParser.AlterUserMysqlV56Context ctx) { }
	@Override public void enterAlterUserMysqlV57(MySqlParser.AlterUserMysqlV57Context ctx) { }
	@Override public void exitAlterUserMysqlV57(MySqlParser.AlterUserMysqlV57Context ctx) { }
	@Override public void enterCreateUserMysqlV56(MySqlParser.CreateUserMysqlV56Context ctx) { }
	@Override public void exitCreateUserMysqlV56(MySqlParser.CreateUserMysqlV56Context ctx) { }
	@Override public void enterCreateUserMysqlV57(MySqlParser.CreateUserMysqlV57Context ctx) { }
	@Override public void exitCreateUserMysqlV57(MySqlParser.CreateUserMysqlV57Context ctx) { }
	@Override public void enterDropUser(MySqlParser.DropUserContext ctx) { }
	@Override public void exitDropUser(MySqlParser.DropUserContext ctx) { }
	@Override public void enterGrantStatement(MySqlParser.GrantStatementContext ctx) { }
	@Override public void exitGrantStatement(MySqlParser.GrantStatementContext ctx) { }
	@Override public void enterGrantProxy(MySqlParser.GrantProxyContext ctx) { }
	@Override public void exitGrantProxy(MySqlParser.GrantProxyContext ctx) { }
	@Override public void enterRenameUser(MySqlParser.RenameUserContext ctx) { }
	@Override public void exitRenameUser(MySqlParser.RenameUserContext ctx) { }
	@Override public void enterDetailRevoke(MySqlParser.DetailRevokeContext ctx) { }
	@Override public void exitDetailRevoke(MySqlParser.DetailRevokeContext ctx) { }
	@Override public void enterShortRevoke(MySqlParser.ShortRevokeContext ctx) { }
	@Override public void exitShortRevoke(MySqlParser.ShortRevokeContext ctx) { }
	@Override public void enterRevokeProxy(MySqlParser.RevokeProxyContext ctx) { }
	@Override public void exitRevokeProxy(MySqlParser.RevokeProxyContext ctx) { }
	@Override public void enterSetPasswordStatement(MySqlParser.SetPasswordStatementContext ctx) { }
	@Override public void exitSetPasswordStatement(MySqlParser.SetPasswordStatementContext ctx) { }
	@Override public void enterUserSpecification(MySqlParser.UserSpecificationContext ctx) { }
	@Override public void exitUserSpecification(MySqlParser.UserSpecificationContext ctx) { }
	@Override public void enterPasswordAuthOption(MySqlParser.PasswordAuthOptionContext ctx) { }
	@Override public void exitPasswordAuthOption(MySqlParser.PasswordAuthOptionContext ctx) { }
	@Override public void enterStringAuthOption(MySqlParser.StringAuthOptionContext ctx) { }
	@Override public void exitStringAuthOption(MySqlParser.StringAuthOptionContext ctx) { }
	@Override public void enterHashAuthOption(MySqlParser.HashAuthOptionContext ctx) { }
	@Override public void exitHashAuthOption(MySqlParser.HashAuthOptionContext ctx) { }
	@Override public void enterSimpleAuthOption(MySqlParser.SimpleAuthOptionContext ctx) { }
	@Override public void exitSimpleAuthOption(MySqlParser.SimpleAuthOptionContext ctx) { }
	@Override public void enterTlsOption(MySqlParser.TlsOptionContext ctx) { }
	@Override public void exitTlsOption(MySqlParser.TlsOptionContext ctx) { }
	@Override public void enterUserResourceOption(MySqlParser.UserResourceOptionContext ctx) { }
	@Override public void exitUserResourceOption(MySqlParser.UserResourceOptionContext ctx) { }
	@Override public void enterUserPasswordOption(MySqlParser.UserPasswordOptionContext ctx) { }
	@Override public void exitUserPasswordOption(MySqlParser.UserPasswordOptionContext ctx) { }
	@Override public void enterUserLockOption(MySqlParser.UserLockOptionContext ctx) { }
	@Override public void exitUserLockOption(MySqlParser.UserLockOptionContext ctx) { }
	@Override public void enterPrivelegeClause(MySqlParser.PrivelegeClauseContext ctx) { }
	@Override public void exitPrivelegeClause(MySqlParser.PrivelegeClauseContext ctx) { }
	@Override public void enterPrivilege(MySqlParser.PrivilegeContext ctx) { }
	@Override public void exitPrivilege(MySqlParser.PrivilegeContext ctx) { }
	@Override public void enterCurrentSchemaPriviLevel(MySqlParser.CurrentSchemaPriviLevelContext ctx) { }
	@Override public void exitCurrentSchemaPriviLevel(MySqlParser.CurrentSchemaPriviLevelContext ctx) { }
	@Override public void enterGlobalPrivLevel(MySqlParser.GlobalPrivLevelContext ctx) { }
	@Override public void exitGlobalPrivLevel(MySqlParser.GlobalPrivLevelContext ctx) { }
	@Override public void enterDefiniteSchemaPrivLevel(MySqlParser.DefiniteSchemaPrivLevelContext ctx) { }
	@Override public void exitDefiniteSchemaPrivLevel(MySqlParser.DefiniteSchemaPrivLevelContext ctx) { }
	@Override public void enterDefiniteFullTablePrivLevel(MySqlParser.DefiniteFullTablePrivLevelContext ctx) { }
	@Override public void exitDefiniteFullTablePrivLevel(MySqlParser.DefiniteFullTablePrivLevelContext ctx) { }
	@Override public void enterDefiniteTablePrivLevel(MySqlParser.DefiniteTablePrivLevelContext ctx) { }
	@Override public void exitDefiniteTablePrivLevel(MySqlParser.DefiniteTablePrivLevelContext ctx) { }
	@Override public void enterRenameUserClause(MySqlParser.RenameUserClauseContext ctx) { }
	@Override public void exitRenameUserClause(MySqlParser.RenameUserClauseContext ctx) { }
	@Override public void enterAnalyzeTable(MySqlParser.AnalyzeTableContext ctx) { }
	@Override public void exitAnalyzeTable(MySqlParser.AnalyzeTableContext ctx) { }
	@Override public void enterCheckTable(MySqlParser.CheckTableContext ctx) { }
	@Override public void exitCheckTable(MySqlParser.CheckTableContext ctx) { }
	@Override public void enterChecksumTable(MySqlParser.ChecksumTableContext ctx) { }
	@Override public void exitChecksumTable(MySqlParser.ChecksumTableContext ctx) { }
	@Override public void enterOptimizeTable(MySqlParser.OptimizeTableContext ctx) { }
	@Override public void exitOptimizeTable(MySqlParser.OptimizeTableContext ctx) { }
	@Override public void enterRepairTable(MySqlParser.RepairTableContext ctx) { }
	@Override public void exitRepairTable(MySqlParser.RepairTableContext ctx) { }
	@Override public void enterCheckTableOption(MySqlParser.CheckTableOptionContext ctx) { }
	@Override public void exitCheckTableOption(MySqlParser.CheckTableOptionContext ctx) { }
	@Override public void enterCreateUdfunction(MySqlParser.CreateUdfunctionContext ctx) { }
	@Override public void exitCreateUdfunction(MySqlParser.CreateUdfunctionContext ctx) { }
	@Override public void enterInstallPlugin(MySqlParser.InstallPluginContext ctx) { }
	@Override public void exitInstallPlugin(MySqlParser.InstallPluginContext ctx) { }
	@Override public void enterUninstallPlugin(MySqlParser.UninstallPluginContext ctx) { }
	@Override public void exitUninstallPlugin(MySqlParser.UninstallPluginContext ctx) { }
	@Override public void enterSetVariable(MySqlParser.SetVariableContext ctx) { }
	@Override public void exitSetVariable(MySqlParser.SetVariableContext ctx) { }
	@Override public void enterSetCharset(MySqlParser.SetCharsetContext ctx) { }
	@Override public void exitSetCharset(MySqlParser.SetCharsetContext ctx) { }
	@Override public void enterSetNames(MySqlParser.SetNamesContext ctx) { }
	@Override public void exitSetNames(MySqlParser.SetNamesContext ctx) { }
	@Override public void enterSetPassword(MySqlParser.SetPasswordContext ctx) { }
	@Override public void exitSetPassword(MySqlParser.SetPasswordContext ctx) { }
	@Override public void enterSetTransaction(MySqlParser.SetTransactionContext ctx) { }
	@Override public void exitSetTransaction(MySqlParser.SetTransactionContext ctx) { }
	@Override public void enterSetAutocommit(MySqlParser.SetAutocommitContext ctx) { }
	@Override public void exitSetAutocommit(MySqlParser.SetAutocommitContext ctx) { }
	@Override public void enterShowMasterLogs(MySqlParser.ShowMasterLogsContext ctx) { }
	@Override public void exitShowMasterLogs(MySqlParser.ShowMasterLogsContext ctx) { }
	@Override public void enterShowLogEvents(MySqlParser.ShowLogEventsContext ctx) { }
	@Override public void exitShowLogEvents(MySqlParser.ShowLogEventsContext ctx) { }
	@Override public void enterShowObjectFilter(MySqlParser.ShowObjectFilterContext ctx) { }
	@Override public void exitShowObjectFilter(MySqlParser.ShowObjectFilterContext ctx) { }
	@Override public void enterShowColumns(MySqlParser.ShowColumnsContext ctx) { }
	@Override public void exitShowColumns(MySqlParser.ShowColumnsContext ctx) { }
	@Override public void enterShowCreateDb(MySqlParser.ShowCreateDbContext ctx) { }
	@Override public void exitShowCreateDb(MySqlParser.ShowCreateDbContext ctx) { }
	@Override public void enterShowCreateFullIdObject(MySqlParser.ShowCreateFullIdObjectContext ctx) { }
	@Override public void exitShowCreateFullIdObject(MySqlParser.ShowCreateFullIdObjectContext ctx) { }
	@Override public void enterShowCreateUser(MySqlParser.ShowCreateUserContext ctx) { }
	@Override public void exitShowCreateUser(MySqlParser.ShowCreateUserContext ctx) { }
	@Override public void enterShowEngine(MySqlParser.ShowEngineContext ctx) { }
	@Override public void exitShowEngine(MySqlParser.ShowEngineContext ctx) { }
	@Override public void enterShowGlobalInfo(MySqlParser.ShowGlobalInfoContext ctx) { }
	@Override public void exitShowGlobalInfo(MySqlParser.ShowGlobalInfoContext ctx) { }
	@Override public void enterShowErrors(MySqlParser.ShowErrorsContext ctx) { }
	@Override public void exitShowErrors(MySqlParser.ShowErrorsContext ctx) { }
	@Override public void enterShowCountErrors(MySqlParser.ShowCountErrorsContext ctx) { }
	@Override public void exitShowCountErrors(MySqlParser.ShowCountErrorsContext ctx) { }
	@Override public void enterShowSchemaFilter(MySqlParser.ShowSchemaFilterContext ctx) { }
	@Override public void exitShowSchemaFilter(MySqlParser.ShowSchemaFilterContext ctx) { }
	@Override public void enterShowRoutine(MySqlParser.ShowRoutineContext ctx) { }
	@Override public void exitShowRoutine(MySqlParser.ShowRoutineContext ctx) { }
	@Override public void enterShowGrants(MySqlParser.ShowGrantsContext ctx) { }
	@Override public void exitShowGrants(MySqlParser.ShowGrantsContext ctx) { }
	@Override public void enterShowIndexes(MySqlParser.ShowIndexesContext ctx) { }
	@Override public void exitShowIndexes(MySqlParser.ShowIndexesContext ctx) { }
	@Override public void enterShowOpenTables(MySqlParser.ShowOpenTablesContext ctx) { }
	@Override public void exitShowOpenTables(MySqlParser.ShowOpenTablesContext ctx) { }
	@Override public void enterShowProfile(MySqlParser.ShowProfileContext ctx) { }
	@Override public void exitShowProfile(MySqlParser.ShowProfileContext ctx) { }
	@Override public void enterShowSlaveStatus(MySqlParser.ShowSlaveStatusContext ctx) { }
	@Override public void exitShowSlaveStatus(MySqlParser.ShowSlaveStatusContext ctx) { }
	@Override public void enterVariableClause(MySqlParser.VariableClauseContext ctx) { }
	@Override public void exitVariableClause(MySqlParser.VariableClauseContext ctx) { }
	@Override public void enterShowCommonEntity(MySqlParser.ShowCommonEntityContext ctx) { }
	@Override public void exitShowCommonEntity(MySqlParser.ShowCommonEntityContext ctx) { }
	@Override public void enterShowFilter(MySqlParser.ShowFilterContext ctx) { }
	@Override public void exitShowFilter(MySqlParser.ShowFilterContext ctx) { }
	@Override public void enterShowGlobalInfoClause(MySqlParser.ShowGlobalInfoClauseContext ctx) { }
	@Override public void exitShowGlobalInfoClause(MySqlParser.ShowGlobalInfoClauseContext ctx) { }
	@Override public void enterShowSchemaEntity(MySqlParser.ShowSchemaEntityContext ctx) { }
	@Override public void exitShowSchemaEntity(MySqlParser.ShowSchemaEntityContext ctx) { }
	@Override public void enterShowProfileType(MySqlParser.ShowProfileTypeContext ctx) { }
	@Override public void exitShowProfileType(MySqlParser.ShowProfileTypeContext ctx) { }
	@Override public void enterBinlogStatement(MySqlParser.BinlogStatementContext ctx) { }
	@Override public void exitBinlogStatement(MySqlParser.BinlogStatementContext ctx) { }
	@Override public void enterCacheIndexStatement(MySqlParser.CacheIndexStatementContext ctx) { }
	@Override public void exitCacheIndexStatement(MySqlParser.CacheIndexStatementContext ctx) { }
	@Override public void enterFlushStatement(MySqlParser.FlushStatementContext ctx) { }
	@Override public void exitFlushStatement(MySqlParser.FlushStatementContext ctx) { }
	@Override public void enterKillStatement(MySqlParser.KillStatementContext ctx) { }
	@Override public void exitKillStatement(MySqlParser.KillStatementContext ctx) { }
	@Override public void enterLoadIndexIntoCache(MySqlParser.LoadIndexIntoCacheContext ctx) { }
	@Override public void exitLoadIndexIntoCache(MySqlParser.LoadIndexIntoCacheContext ctx) { }
	@Override public void enterResetStatement(MySqlParser.ResetStatementContext ctx) { }
	@Override public void exitResetStatement(MySqlParser.ResetStatementContext ctx) { }
	@Override public void enterShutdownStatement(MySqlParser.ShutdownStatementContext ctx) { }
	@Override public void exitShutdownStatement(MySqlParser.ShutdownStatementContext ctx) { }
	@Override public void enterTableIndexes(MySqlParser.TableIndexesContext ctx) { }
	@Override public void exitTableIndexes(MySqlParser.TableIndexesContext ctx) { }
	@Override public void enterSimpleFlushOption(MySqlParser.SimpleFlushOptionContext ctx) { }
	@Override public void exitSimpleFlushOption(MySqlParser.SimpleFlushOptionContext ctx) { }
	@Override public void enterChannelFlushOption(MySqlParser.ChannelFlushOptionContext ctx) { }
	@Override public void exitChannelFlushOption(MySqlParser.ChannelFlushOptionContext ctx) { }
	@Override public void enterTableFlushOption(MySqlParser.TableFlushOptionContext ctx) { }
	@Override public void exitTableFlushOption(MySqlParser.TableFlushOptionContext ctx) { }
	@Override public void enterFlushTableOption(MySqlParser.FlushTableOptionContext ctx) { }
	@Override public void exitFlushTableOption(MySqlParser.FlushTableOptionContext ctx) { }
	@Override public void enterLoadedTableIndexes(MySqlParser.LoadedTableIndexesContext ctx) { }
	@Override public void exitLoadedTableIndexes(MySqlParser.LoadedTableIndexesContext ctx) { }
	@Override public void enterSimpleDescribeStatement(MySqlParser.SimpleDescribeStatementContext ctx) { }
	@Override public void exitSimpleDescribeStatement(MySqlParser.SimpleDescribeStatementContext ctx) { }
	@Override public void enterFullDescribeStatement(MySqlParser.FullDescribeStatementContext ctx) { }
	@Override public void exitFullDescribeStatement(MySqlParser.FullDescribeStatementContext ctx) { }
	@Override public void enterHelpStatement(MySqlParser.HelpStatementContext ctx) { }
	@Override public void exitHelpStatement(MySqlParser.HelpStatementContext ctx) { }
	@Override public void enterUseStatement(MySqlParser.UseStatementContext ctx) { }
	@Override public void exitUseStatement(MySqlParser.UseStatementContext ctx) { }
	@Override public void enterDescribeStatements(MySqlParser.DescribeStatementsContext ctx) { }
	@Override public void exitDescribeStatements(MySqlParser.DescribeStatementsContext ctx) { }
	@Override public void enterDescribeConnection(MySqlParser.DescribeConnectionContext ctx) { }
	@Override public void exitDescribeConnection(MySqlParser.DescribeConnectionContext ctx) { }
	@Override public void enterFullId(MySqlParser.FullIdContext ctx) { }
	@Override public void exitFullId(MySqlParser.FullIdContext ctx) { }
	@Override public void enterTableName(MySqlParser.TableNameContext ctx) { }
	@Override public void exitTableName(MySqlParser.TableNameContext ctx) { }
	@Override public void enterFullColumnName(MySqlParser.FullColumnNameContext ctx) { }
	@Override public void exitFullColumnName(MySqlParser.FullColumnNameContext ctx) { }
	@Override public void enterIndexColumnName(MySqlParser.IndexColumnNameContext ctx) { }
	@Override public void exitIndexColumnName(MySqlParser.IndexColumnNameContext ctx) { }
	@Override public void enterUserName(MySqlParser.UserNameContext ctx) { }
	@Override public void exitUserName(MySqlParser.UserNameContext ctx) { }
	@Override public void enterMysqlVariable(MySqlParser.MysqlVariableContext ctx) { }
	@Override public void exitMysqlVariable(MySqlParser.MysqlVariableContext ctx) { }
	@Override public void enterCharsetName(MySqlParser.CharsetNameContext ctx) { }
	@Override public void exitCharsetName(MySqlParser.CharsetNameContext ctx) { }
	@Override public void enterCollationName(MySqlParser.CollationNameContext ctx) { }
	@Override public void exitCollationName(MySqlParser.CollationNameContext ctx) { }
	@Override public void enterEngineName(MySqlParser.EngineNameContext ctx) { }
	@Override public void exitEngineName(MySqlParser.EngineNameContext ctx) { }
	@Override public void enterUuidSet(MySqlParser.UuidSetContext ctx) { }
	@Override public void exitUuidSet(MySqlParser.UuidSetContext ctx) { }
	@Override public void enterXid(MySqlParser.XidContext ctx) { }
	@Override public void exitXid(MySqlParser.XidContext ctx) { }
	@Override public void enterXuidStringId(MySqlParser.XuidStringIdContext ctx) { }
	@Override public void exitXuidStringId(MySqlParser.XuidStringIdContext ctx) { }
	@Override public void enterAuthPlugin(MySqlParser.AuthPluginContext ctx) { }
	@Override public void exitAuthPlugin(MySqlParser.AuthPluginContext ctx) { }
	@Override public void enterUid(MySqlParser.UidContext ctx) { }
	@Override public void exitUid(MySqlParser.UidContext ctx) { }
	@Override public void enterSimpleId(MySqlParser.SimpleIdContext ctx) { }
	@Override public void exitSimpleId(MySqlParser.SimpleIdContext ctx) { }
	@Override public void enterDottedId(MySqlParser.DottedIdContext ctx) { }
	@Override public void exitDottedId(MySqlParser.DottedIdContext ctx) { }
	@Override public void enterDecimalLiteral(MySqlParser.DecimalLiteralContext ctx) { }
	@Override public void exitDecimalLiteral(MySqlParser.DecimalLiteralContext ctx) { }
	@Override public void enterFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx) { }
	@Override public void exitFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx) { }
	@Override public void enterStringLiteral(MySqlParser.StringLiteralContext ctx) { }
	@Override public void exitStringLiteral(MySqlParser.StringLiteralContext ctx) { }
	@Override public void enterBooleanLiteral(MySqlParser.BooleanLiteralContext ctx) { }
	@Override public void exitBooleanLiteral(MySqlParser.BooleanLiteralContext ctx) { }
	@Override public void enterHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx) { }
	@Override public void exitHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx) { }
	@Override public void enterNullNotnull(MySqlParser.NullNotnullContext ctx) { }
	@Override public void exitNullNotnull(MySqlParser.NullNotnullContext ctx) { }
	@Override public void enterConstant(MySqlParser.ConstantContext ctx) { }
	@Override public void exitConstant(MySqlParser.ConstantContext ctx) { }
	@Override public void enterStringDataType(MySqlParser.StringDataTypeContext ctx) { }
	@Override public void exitStringDataType(MySqlParser.StringDataTypeContext ctx) { }
	@Override public void enterDimensionDataType(MySqlParser.DimensionDataTypeContext ctx) { }
	@Override public void exitDimensionDataType(MySqlParser.DimensionDataTypeContext ctx) { }
	@Override public void enterSimpleDataType(MySqlParser.SimpleDataTypeContext ctx) { }
	@Override public void exitSimpleDataType(MySqlParser.SimpleDataTypeContext ctx) { }
	@Override public void enterCollectionDataType(MySqlParser.CollectionDataTypeContext ctx) { }
	@Override public void exitCollectionDataType(MySqlParser.CollectionDataTypeContext ctx) { }
	@Override public void enterSpatialDataType(MySqlParser.SpatialDataTypeContext ctx) { }
	@Override public void exitSpatialDataType(MySqlParser.SpatialDataTypeContext ctx) { }
	@Override public void enterConvertedDataType(MySqlParser.ConvertedDataTypeContext ctx) { }
	@Override public void exitConvertedDataType(MySqlParser.ConvertedDataTypeContext ctx) { }
	@Override public void enterLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx) { }
	@Override public void exitLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx) { }
	@Override public void enterLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx) { }
	@Override public void exitLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx) { }
	@Override public void enterLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx) { }
	@Override public void exitLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx) { }
	@Override public void enterUidList(MySqlParser.UidListContext ctx) { }
	@Override public void exitUidList(MySqlParser.UidListContext ctx) { }
	@Override public void enterTables(MySqlParser.TablesContext ctx) { }
	@Override public void exitTables(MySqlParser.TablesContext ctx) { }
	@Override public void enterIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx) { }
	@Override public void exitIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx) { }
	@Override public void enterExpressions(MySqlParser.ExpressionsContext ctx) { }
	@Override public void exitExpressions(MySqlParser.ExpressionsContext ctx) { }
	@Override public void enterExpressionsWithDefaults(MySqlParser.ExpressionsWithDefaultsContext ctx) { }
	@Override public void exitExpressionsWithDefaults(MySqlParser.ExpressionsWithDefaultsContext ctx) { }
	@Override public void enterConstants(MySqlParser.ConstantsContext ctx) { }
	@Override public void exitConstants(MySqlParser.ConstantsContext ctx) { }
	@Override public void enterSimpleStrings(MySqlParser.SimpleStringsContext ctx) { }
	@Override public void exitSimpleStrings(MySqlParser.SimpleStringsContext ctx) { }
	@Override public void enterUserVariables(MySqlParser.UserVariablesContext ctx) { }
	@Override public void exitUserVariables(MySqlParser.UserVariablesContext ctx) { }
	@Override public void enterDefaultValue(MySqlParser.DefaultValueContext ctx) { }
	@Override public void exitDefaultValue(MySqlParser.DefaultValueContext ctx) { }
	@Override public void enterExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx) { }
	@Override public void exitExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx) { }
	@Override public void enterIfExists(MySqlParser.IfExistsContext ctx) { }
	@Override public void exitIfExists(MySqlParser.IfExistsContext ctx) { }
	@Override public void enterIfNotExists(MySqlParser.IfNotExistsContext ctx) { }
	@Override public void exitIfNotExists(MySqlParser.IfNotExistsContext ctx) { }
	@Override public void enterSpecificFunctionCall(MySqlParser.SpecificFunctionCallContext ctx) { }
	@Override public void exitSpecificFunctionCall(MySqlParser.SpecificFunctionCallContext ctx) { }
	@Override public void enterAggregateFunctionCall(MySqlParser.AggregateFunctionCallContext ctx) { }
	@Override public void exitAggregateFunctionCall(MySqlParser.AggregateFunctionCallContext ctx) { }
	@Override public void enterScalarFunctionCall(MySqlParser.ScalarFunctionCallContext ctx) { }
	@Override public void exitScalarFunctionCall(MySqlParser.ScalarFunctionCallContext ctx) { }
	@Override public void enterUdfFunctionCall(MySqlParser.UdfFunctionCallContext ctx) { }
	@Override public void exitUdfFunctionCall(MySqlParser.UdfFunctionCallContext ctx) { }
	@Override public void enterPasswordFunctionCall(MySqlParser.PasswordFunctionCallContext ctx) { }
	@Override public void exitPasswordFunctionCall(MySqlParser.PasswordFunctionCallContext ctx) { }
	@Override public void enterSimpleFunctionCall(MySqlParser.SimpleFunctionCallContext ctx) { }
	@Override public void exitSimpleFunctionCall(MySqlParser.SimpleFunctionCallContext ctx) { }
	@Override public void enterDataTypeFunctionCall(MySqlParser.DataTypeFunctionCallContext ctx) { }
	@Override public void exitDataTypeFunctionCall(MySqlParser.DataTypeFunctionCallContext ctx) { }
	@Override public void enterValuesFunctionCall(MySqlParser.ValuesFunctionCallContext ctx) { }
	@Override public void exitValuesFunctionCall(MySqlParser.ValuesFunctionCallContext ctx) { }
	@Override public void enterCaseFunctionCall(MySqlParser.CaseFunctionCallContext ctx) { }
	@Override public void exitCaseFunctionCall(MySqlParser.CaseFunctionCallContext ctx) { }
	@Override public void enterCharFunctionCall(MySqlParser.CharFunctionCallContext ctx) { }
	@Override public void exitCharFunctionCall(MySqlParser.CharFunctionCallContext ctx) { }
	@Override public void enterPositionFunctionCall(MySqlParser.PositionFunctionCallContext ctx) { }
	@Override public void exitPositionFunctionCall(MySqlParser.PositionFunctionCallContext ctx) { }
	@Override public void enterSubstrFunctionCall(MySqlParser.SubstrFunctionCallContext ctx) { }
	@Override public void exitSubstrFunctionCall(MySqlParser.SubstrFunctionCallContext ctx) { }
	@Override public void enterTrimFunctionCall(MySqlParser.TrimFunctionCallContext ctx) { }
	@Override public void exitTrimFunctionCall(MySqlParser.TrimFunctionCallContext ctx) { }
	@Override public void enterWeightFunctionCall(MySqlParser.WeightFunctionCallContext ctx) { }
	@Override public void exitWeightFunctionCall(MySqlParser.WeightFunctionCallContext ctx) { }
	@Override public void enterExtractFunctionCall(MySqlParser.ExtractFunctionCallContext ctx) { }
	@Override public void exitExtractFunctionCall(MySqlParser.ExtractFunctionCallContext ctx) { }
	@Override public void enterGetFormatFunctionCall(MySqlParser.GetFormatFunctionCallContext ctx) { }
	@Override public void exitGetFormatFunctionCall(MySqlParser.GetFormatFunctionCallContext ctx) { }
	@Override public void enterCaseFuncAlternative(MySqlParser.CaseFuncAlternativeContext ctx) { }
	@Override public void exitCaseFuncAlternative(MySqlParser.CaseFuncAlternativeContext ctx) { }
	@Override public void enterLevelWeightList(MySqlParser.LevelWeightListContext ctx) { }
	@Override public void exitLevelWeightList(MySqlParser.LevelWeightListContext ctx) { }
	@Override public void enterLevelWeightRange(MySqlParser.LevelWeightRangeContext ctx) { }
	@Override public void exitLevelWeightRange(MySqlParser.LevelWeightRangeContext ctx) { }
	@Override public void enterLevelInWeightListElement(MySqlParser.LevelInWeightListElementContext ctx) { }
	@Override public void exitLevelInWeightListElement(MySqlParser.LevelInWeightListElementContext ctx) { }
	@Override public void enterAggregateWindowedFunction(MySqlParser.AggregateWindowedFunctionContext ctx) { }
	@Override public void exitAggregateWindowedFunction(MySqlParser.AggregateWindowedFunctionContext ctx) { }
	@Override public void enterScalarFunctionName(MySqlParser.ScalarFunctionNameContext ctx) { }
	@Override public void exitScalarFunctionName(MySqlParser.ScalarFunctionNameContext ctx) { }
	@Override public void enterPasswordFunctionClause(MySqlParser.PasswordFunctionClauseContext ctx) { }
	@Override public void exitPasswordFunctionClause(MySqlParser.PasswordFunctionClauseContext ctx) { }
	@Override public void enterFunctionArgs(MySqlParser.FunctionArgsContext ctx) { }
	@Override public void exitFunctionArgs(MySqlParser.FunctionArgsContext ctx) { }
	@Override public void enterFunctionArg(MySqlParser.FunctionArgContext ctx) { }
	@Override public void exitFunctionArg(MySqlParser.FunctionArgContext ctx) { }
	@Override public void enterIsExpression(MySqlParser.IsExpressionContext ctx) { }
	@Override public void exitIsExpression(MySqlParser.IsExpressionContext ctx) { }
	@Override public void enterNotExpression(MySqlParser.NotExpressionContext ctx) { }
	@Override public void exitNotExpression(MySqlParser.NotExpressionContext ctx) { }
	@Override public void enterLogicalExpression(MySqlParser.LogicalExpressionContext ctx) { }
	@Override public void exitLogicalExpression(MySqlParser.LogicalExpressionContext ctx) { }
	@Override public void enterPredicateExpression(MySqlParser.PredicateExpressionContext ctx) { }
	@Override public void exitPredicateExpression(MySqlParser.PredicateExpressionContext ctx) { }
	@Override public void enterSoundsLikePredicate(MySqlParser.SoundsLikePredicateContext ctx) { }
	@Override public void exitSoundsLikePredicate(MySqlParser.SoundsLikePredicateContext ctx) { }
	@Override public void enterExpressionAtomPredicate(MySqlParser.ExpressionAtomPredicateContext ctx) { }
	@Override public void exitExpressionAtomPredicate(MySqlParser.ExpressionAtomPredicateContext ctx) { }
	@Override public void enterInPredicate(MySqlParser.InPredicateContext ctx) { }
	@Override public void exitInPredicate(MySqlParser.InPredicateContext ctx) { }
	@Override public void enterSubqueryComparasionPredicate(MySqlParser.SubqueryComparasionPredicateContext ctx) { }
	@Override public void exitSubqueryComparasionPredicate(MySqlParser.SubqueryComparasionPredicateContext ctx) { }
	@Override public void enterBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) { }
	@Override public void exitBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) { }
	@Override public void enterBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) { }
	@Override public void exitBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) { }
	@Override public void enterIsNullPredicate(MySqlParser.IsNullPredicateContext ctx) { }
	@Override public void exitIsNullPredicate(MySqlParser.IsNullPredicateContext ctx) { }
	@Override public void enterLikePredicate(MySqlParser.LikePredicateContext ctx) { }
	@Override public void exitLikePredicate(MySqlParser.LikePredicateContext ctx) { }
	@Override public void enterRegexpPredicate(MySqlParser.RegexpPredicateContext ctx) { }
	@Override public void exitRegexpPredicate(MySqlParser.RegexpPredicateContext ctx) { }
	@Override public void enterUnaryExpressionAtom(MySqlParser.UnaryExpressionAtomContext ctx) { }
	@Override public void exitUnaryExpressionAtom(MySqlParser.UnaryExpressionAtomContext ctx) { }
	@Override public void enterCollateExpressionAtom(MySqlParser.CollateExpressionAtomContext ctx) { }
	@Override public void exitCollateExpressionAtom(MySqlParser.CollateExpressionAtomContext ctx) { }
	@Override public void enterSubqueryExpessionAtom(MySqlParser.SubqueryExpessionAtomContext ctx) { }
	@Override public void exitSubqueryExpessionAtom(MySqlParser.SubqueryExpessionAtomContext ctx) { }
	@Override public void enterMysqlVariableExpressionAtom(MySqlParser.MysqlVariableExpressionAtomContext ctx) { }
	@Override public void exitMysqlVariableExpressionAtom(MySqlParser.MysqlVariableExpressionAtomContext ctx) { }
	@Override public void enterNestedExpressionAtom(MySqlParser.NestedExpressionAtomContext ctx) { }
	@Override public void exitNestedExpressionAtom(MySqlParser.NestedExpressionAtomContext ctx) { }
	@Override public void enterNestedRowExpressionAtom(MySqlParser.NestedRowExpressionAtomContext ctx) { }
	@Override public void exitNestedRowExpressionAtom(MySqlParser.NestedRowExpressionAtomContext ctx) { }
	@Override public void enterMathExpressionAtom(MySqlParser.MathExpressionAtomContext ctx) { }
	@Override public void exitMathExpressionAtom(MySqlParser.MathExpressionAtomContext ctx) { }
	@Override public void enterIntervalExpressionAtom(MySqlParser.IntervalExpressionAtomContext ctx) { }
	@Override public void exitIntervalExpressionAtom(MySqlParser.IntervalExpressionAtomContext ctx) { }
	@Override public void enterExistsExpessionAtom(MySqlParser.ExistsExpessionAtomContext ctx) { }
	@Override public void exitExistsExpessionAtom(MySqlParser.ExistsExpessionAtomContext ctx) { }
	@Override public void enterConstantExpressionAtom(MySqlParser.ConstantExpressionAtomContext ctx) { }
	@Override public void exitConstantExpressionAtom(MySqlParser.ConstantExpressionAtomContext ctx) { }
	@Override public void enterFunctionCallExpressionAtom(MySqlParser.FunctionCallExpressionAtomContext ctx) { }
	@Override public void exitFunctionCallExpressionAtom(MySqlParser.FunctionCallExpressionAtomContext ctx) { }
	@Override public void enterBinaryExpressionAtom(MySqlParser.BinaryExpressionAtomContext ctx) { }
	@Override public void exitBinaryExpressionAtom(MySqlParser.BinaryExpressionAtomContext ctx) { }
	@Override public void enterFullColumnNameExpressionAtom(MySqlParser.FullColumnNameExpressionAtomContext ctx) { }
	@Override public void exitFullColumnNameExpressionAtom(MySqlParser.FullColumnNameExpressionAtomContext ctx) { }
	@Override public void enterBitExpressionAtom(MySqlParser.BitExpressionAtomContext ctx) { }
	@Override public void exitBitExpressionAtom(MySqlParser.BitExpressionAtomContext ctx) { }
	@Override public void enterUnaryOperator(MySqlParser.UnaryOperatorContext ctx) { }
	@Override public void exitUnaryOperator(MySqlParser.UnaryOperatorContext ctx) { }
	@Override public void enterComparisonOperator(MySqlParser.ComparisonOperatorContext ctx) { }
	@Override public void exitComparisonOperator(MySqlParser.ComparisonOperatorContext ctx) { }
	@Override public void enterLogicalOperator(MySqlParser.LogicalOperatorContext ctx) { }
	@Override public void exitLogicalOperator(MySqlParser.LogicalOperatorContext ctx) { }
	@Override public void enterBitOperator(MySqlParser.BitOperatorContext ctx) { }
	@Override public void exitBitOperator(MySqlParser.BitOperatorContext ctx) { }
	@Override public void enterMathOperator(MySqlParser.MathOperatorContext ctx) { }
	@Override public void exitMathOperator(MySqlParser.MathOperatorContext ctx) { }
	@Override public void enterCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx) { }
	@Override public void exitCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx) { }
	@Override public void enterTransactionLevelBase(MySqlParser.TransactionLevelBaseContext ctx) { }
	@Override public void exitTransactionLevelBase(MySqlParser.TransactionLevelBaseContext ctx) { }
	@Override public void enterPrivilegesBase(MySqlParser.PrivilegesBaseContext ctx) { }
	@Override public void exitPrivilegesBase(MySqlParser.PrivilegesBaseContext ctx) { }
	@Override public void enterIntervalTypeBase(MySqlParser.IntervalTypeBaseContext ctx) { }
	@Override public void exitIntervalTypeBase(MySqlParser.IntervalTypeBaseContext ctx) { }
	@Override public void enterDataTypeBase(MySqlParser.DataTypeBaseContext ctx) { }
	@Override public void exitDataTypeBase(MySqlParser.DataTypeBaseContext ctx) { }
	@Override public void enterKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx) { }
	@Override public void exitKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx) { }
	@Override public void enterFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx) { }
	@Override public void exitFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx) { }

	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	@Override public void visitTerminal(TerminalNode node) { }
	@Override public void visitErrorNode(ErrorNode node) { }
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