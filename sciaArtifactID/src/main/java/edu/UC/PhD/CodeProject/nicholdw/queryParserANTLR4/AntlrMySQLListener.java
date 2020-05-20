package edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4;

import java.util.ArrayList;
import java.util.List;
import org.Antlr4MySQLFromANTLRRepo.NestingLevel;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser.FullColumnNameContext;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser.FullIdContext;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser.OrderByClauseContext;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser.OrderByExpressionContext;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser.TableNameContext;
import org.Antlr4MySQLFromANTLRRepo.MySqlParser.UidContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.AliasNameClassOLD;
import edu.UC.PhD.CodeProject.nicholdw.query.CompoundAlias;
import edu.UC.PhD.CodeProject.nicholdw.query.CompoundAliases;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClause;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClauseUnknown;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTable;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryTerminalSymbol;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlter;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeAlterView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeCreate;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeCreateTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeCreateView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDrop;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeDropView;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeInsert;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeRenameTable;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeUnknown;
import edu.UC.PhD.CodeProject.nicholdw.query.FullColumnName;
import edu.UC.PhD.CodeProject.nicholdw.query.FullColumnNames;
/**
 * Parsing MySQL SQL statements and event handlers for such.
 * @author nicomp
 */
public class AntlrMySQLListener extends org.Antlr4MySQLFromANTLRRepo.MySqlParserBaseListener {

	private QueryDefinition queryDefinition;
	private QueryClause queryClause;
	private QueryTable currentQueryTable;
	private boolean includeAllFields;
	private String currentAlias;

	FullColumnNames fullColumnNames;
	ArrayList<FullTableName> fullTableNames;	
	private NestingLevel currentNestingLevel, previousNestingLevel;
	private String lastTerminalNode;
	CompoundAliases compoundAliases;
	boolean firstVisit;

	public AntlrMySQLListener(QueryDefinition queryDefinition) {
		Log.logProgress("AntlrMySQLListener.AntlrMySQLListener(qd)");
		this.queryDefinition = queryDefinition;
		queryClause = new QueryClauseUnknown();
		fullColumnNames = new FullColumnNames();
		fullTableNames = new ArrayList<FullTableName>();
		compoundAliases = new CompoundAliases();
		currentNestingLevel = new NestingLevel();
		queryDefinition.setQueryType(new QueryTypeUnknown());
		lastTerminalNode = "";
		firstVisit = true;
		currentAlias = "";
	}
	private void printTerminalNodes(List<TerminalNode> tns) {
		for (TerminalNode tn : tns) {
			Log.logQueryParseProgress(tn.toString());
		}
	}
	@Override public void enterRoot(MySqlParser.RootContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterRoot()" + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitRoot(MySqlParser.RootContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitRoot()");
		Log.logQueryParseProgress("Attributes collected from the SQL...");
		for (FullColumnName fcn: fullColumnNames) {
//			fcn.processRawData();
			fcn.copyIntoQueryDefinition(queryDefinition);
			Log.logQueryParseProgress(fcn.toString());
		}
		Log.logQueryParseProgress("Tables collected from the SQL...");
		for (FullTableName ftn: fullTableNames) {
			ftn.processRawData();
			ftn.copyIntoQueryDefinition(queryDefinition);
			Log.logQueryParseProgress(ftn.toString());
		}
		queryDefinition.setCompoundAliases(compoundAliases);
		Log.logQueryParseProgress("Compound Attributes collected from the SQL...");
		for (CompoundAlias ca: compoundAliases) {
			Log.logQueryParseProgress(ca.toString());
		}
	}
	@Override public void enterInsertStatement(MySqlParser.InsertStatementContext ctx) { 
		Log.logQueryParseProgress("AntlrMySQLListener.enterInsertStatement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		FullTableName fullTableName = null;
		for (Object o : ctx.children) {
			if (o.getClass() ==  MySqlParser.TableNameContext.class  ) {
				enterTableName((TableNameContext)o);
				fullTableName = fullTableNames.get(fullTableNames.size() - 1);
			}
			if (o.getClass() == MySqlParser.UidListContext.class ) {
				for (Object oo : ((MySqlParser.UidListContext)o).children) {
					try {processUidContext(((MySqlParser.UidContext)oo), fullTableName.schemaName, fullTableName.tableName);} catch (Exception ex) {}
				}
			}
		}
	}
	@Override public void exitInsertStatement(MySqlParser.InsertStatementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitInsertStatement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void enterInsertStatementValue(MySqlParser.InsertStatementValueContext ctx) { 
		Log.logQueryParseProgress("AntlrMySQLListener.enterInsertStatementValue: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitInsertStatementValue(MySqlParser.InsertStatementValueContext ctx) { 
		Log.logQueryParseProgress("AntlrMySQLListener.exitInsertStatementValue: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void enterSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleSelect: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleSelect: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void enterSelectElements(MySqlParser.SelectElementsContext ctx) {
		// TODO: need to differentiate between * and TableName.* in the SQL statement. Right now we are not catching TableName.* at all
		Log.logQueryParseProgress("AntlrMySQLListener.enterSelectElements: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		boolean isAsterisk = false;
		// If it's an asterisk, we need all the fields in all the tables.
		if (ctx.getText().equals("*")) {
			Log.logQueryParseProgress("AntlrMySQLListener.enterSelectElements: it's a star!");
			isAsterisk = true;
		}
		queryDefinition.setSelectIsWildcard(isAsterisk);
	}
	@Override public void exitSelectElements(MySqlParser.SelectElementsContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSelectElements: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}

	@Override public void enterSelectStarElement(MySqlParser.SelectStarElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSelectStarElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitSelectStarElement(MySqlParser.SelectStarElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSelectStarElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void enterSelectColumnElement(MySqlParser.SelectColumnElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSelectColumnElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		processSelectColumnElementContext(ctx);
	}
	@Override public void exitSelectColumnElement(MySqlParser.SelectColumnElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSelectColumnElement: " + ctx.getText());
		clearCurrentAlias();
	}
	@Override public void enterSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx) {
		// ToDo capture this as a function and store it somewhere
		Log.logQueryParseProgress("AntlrMySQLListener.enterSelectFunctionElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		processEnterSelectFunctionElement(ctx);
	}
	@Override public void exitSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSelectFunctionElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		processExitSelectFunctionElement(ctx);
	}
	@Override public void enterSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSelectExpressionElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		processEnterSelectExpressionElement(ctx);
	}
	@Override public void exitSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSelectExpressionElement: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		processExitSelectExpressionElement(ctx);
	}
	@Override public void enterSelectIntoVariables(MySqlParser.SelectIntoVariablesContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSelectIntoVariables: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitSelectIntoVariables(MySqlParser.SelectIntoVariablesContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitSelectIntoVariables: " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void enterSelectIntoDumpFile(MySqlParser.SelectIntoDumpFileContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSelectIntoDumpFile()");}
	@Override public void exitSelectIntoDumpFile(MySqlParser.SelectIntoDumpFileContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSelectIntoDumpFile()");}
	@Override public void enterSelectIntoTextFile(MySqlParser.SelectIntoTextFileContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSelectIntoTextFile()");}
	@Override public void exitSelectIntoTextFile(MySqlParser.SelectIntoTextFileContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSelectIntoTextFile()");}
	@Override public void enterSelectFieldsInto(MySqlParser.SelectFieldsIntoContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSelectFieldsInto()");}
	@Override public void exitSelectFieldsInto(MySqlParser.SelectFieldsIntoContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSelectLinesInto()");}
	@Override public void enterSelectLinesInto(MySqlParser.SelectLinesIntoContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSelectLinesInto()");}
	@Override public void exitSelectLinesInto(MySqlParser.SelectLinesIntoContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSelectLinesInto()");}
	@Override public void enterFromClause(MySqlParser.FromClauseContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterFromClause() " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitFromClause(MySqlParser.FromClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFromClause()");}
	@Override public void enterGroupByItem(MySqlParser.GroupByItemContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterGroupByItem()");}
	@Override public void exitGroupByItem(MySqlParser.GroupByItemContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGroupByItem()");}
	@Override public void enterLimitClause(MySqlParser.LimitClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLimitClause()");}
	@Override public void exitLimitClause(MySqlParser.LimitClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLimitClause()");}
	@Override public void enterStartTransaction(MySqlParser.StartTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStartTransaction()");}
	@Override public void exitStartTransaction(MySqlParser.StartTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStartTransaction()");}
	@Override public void enterBeginWork(MySqlParser.BeginWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBeginWork()");}
	@Override public void exitBeginWork(MySqlParser.BeginWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBeginWork()");}
	@Override public void enterCommitWork(MySqlParser.CommitWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCommitWork()");}
	@Override public void exitCommitWork(MySqlParser.CommitWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCommitWork()");}
	@Override public void enterRollbackWork(MySqlParser.RollbackWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRollbackWork()");}
	@Override public void exitRollbackWork(MySqlParser.RollbackWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRollbackWork()");}
	@Override public void enterSavepointStatement(MySqlParser.SavepointStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSavepointStatement()");}
	@Override public void exitSavepointStatement(MySqlParser.SavepointStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSavepointStatement()");}
	@Override public void enterRollbackStatement(MySqlParser.RollbackStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRollbackStatement()");}
	@Override public void exitRollbackStatement(MySqlParser.RollbackStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRollbackStatement()");}
	@Override public void enterReleaseStatement(MySqlParser.ReleaseStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void exitReleaseStatement(MySqlParser.ReleaseStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterReleaseStatement()");}
	@Override public void enterLockTables(MySqlParser.LockTablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLockTables()");}
	@Override public void exitLockTables(MySqlParser.LockTablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLockTables()");}
	@Override public void enterUnlockTables(MySqlParser.UnlockTablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUnlockTables()");}
	@Override public void exitUnlockTables(MySqlParser.UnlockTablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUnlockTables()");}
	@Override public void enterSetAutocommitStatement(MySqlParser.SetAutocommitStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetAutocommitStatement()");}
	@Override public void exitSetAutocommitStatement(MySqlParser.SetAutocommitStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetAutocommitStatement()");}
	@Override public void enterSetTransactionStatement(MySqlParser.SetTransactionStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetTransactionStatement()");}
	@Override public void exitSetTransactionStatement(MySqlParser.SetTransactionStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetTransactionStatement()");}
	@Override public void enterTransactionMode(MySqlParser.TransactionModeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTransactionMode()");}
	@Override public void exitTransactionMode(MySqlParser.TransactionModeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTransactionMode()");}
	@Override public void enterLockTableElement(MySqlParser.LockTableElementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLockTableElement()");}
	@Override public void exitLockTableElement(MySqlParser.LockTableElementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLockTableElement()");}
	@Override public void enterLockAction(MySqlParser.LockActionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLockAction()");}
	@Override public void exitLockAction(MySqlParser.LockActionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLockAction()");}
	@Override public void enterTransactionOption(MySqlParser.TransactionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTransactionOption()");}
	@Override public void exitTransactionOption(MySqlParser.TransactionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTransactionOption()");}
	@Override public void enterTransactionLevel(MySqlParser.TransactionLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTransactionLevel()");}
	@Override public void exitTransactionLevel(MySqlParser.TransactionLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTransactionLevel()");}
	@Override public void enterChangeMaster(MySqlParser.ChangeMasterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterChangeMaster()");}
	@Override public void exitChangeMaster(MySqlParser.ChangeMasterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitChangeMaster()");}
	@Override public void enterChangeReplicationFilter(MySqlParser.ChangeReplicationFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterChangeReplicationFilter()");}
	@Override public void exitChangeReplicationFilter(MySqlParser.ChangeReplicationFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitChangeReplicationFilter()");}
	@Override public void enterPurgeBinaryLogs(MySqlParser.PurgeBinaryLogsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPurgeBinaryLogs()");}
	@Override public void exitPurgeBinaryLogs(MySqlParser.PurgeBinaryLogsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPurgeBinaryLogs()");}
	@Override public void enterResetMaster(MySqlParser.ResetMasterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterResetMaster()");}
	@Override public void exitResetMaster(MySqlParser.ResetMasterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitResetMaster()");}
	@Override public void enterResetSlave(MySqlParser.ResetSlaveContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterResetSlave()");}
	@Override public void exitResetSlave(MySqlParser.ResetSlaveContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitResetSlave()");}
	@Override public void enterStartSlave(MySqlParser.StartSlaveContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStartSlave()");}
	@Override public void exitStartSlave(MySqlParser.StartSlaveContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStartSlave()");}
	@Override public void enterStopSlave(MySqlParser.StopSlaveContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStopSlave()");}
	@Override public void exitStopSlave(MySqlParser.StopSlaveContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStopSlave()");}
	@Override public void enterStartGroupReplication(MySqlParser.StartGroupReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStartGroupReplication()");}
	@Override public void exitStartGroupReplication(MySqlParser.StartGroupReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStartGroupReplication()");}
	@Override public void enterStopGroupReplication(MySqlParser.StopGroupReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStopGroupReplication()");}
	@Override public void exitStopGroupReplication(MySqlParser.StopGroupReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStopGroupReplication()");}
	@Override public void enterMasterStringOption(MySqlParser.MasterStringOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMasterStringOption()");}
	@Override public void exitMasterStringOption(MySqlParser.MasterStringOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMasterStringOption()");}
	@Override public void enterMasterDecimalOption(MySqlParser.MasterDecimalOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMasterDecimalOption()");}
	@Override public void exitMasterDecimalOption(MySqlParser.MasterDecimalOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMasterDecimalOption()");}
	@Override public void enterMasterBoolOption(MySqlParser.MasterBoolOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMasterBoolOption()");}
	@Override public void exitMasterBoolOption(MySqlParser.MasterBoolOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMasterBoolOption()");}
	@Override public void enterMasterRealOption(MySqlParser.MasterRealOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMasterRealOption()");}
	@Override public void exitMasterRealOption(MySqlParser.MasterRealOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMasterRealOption()");}
	@Override public void enterMasterUidListOption(MySqlParser.MasterUidListOptionContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterMasterUidListOption()" + ctx.getText()  + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitMasterUidListOption(MySqlParser.MasterUidListOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMasterUidListOption()");}
	@Override public void enterStringMasterOption(MySqlParser.StringMasterOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStringMasterOption()");}
	@Override public void exitStringMasterOption(MySqlParser.StringMasterOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStringMasterOption()");}
	@Override public void enterDecimalMasterOption(MySqlParser.DecimalMasterOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDecimalMasterOption()");}
	@Override public void exitDecimalMasterOption(MySqlParser.DecimalMasterOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDecimalMasterOption()");}
	@Override public void enterBoolMasterOption(MySqlParser.BoolMasterOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBoolMasterOption()");}
	@Override public void exitBoolMasterOption(MySqlParser.BoolMasterOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBoolMasterOption()");}
	@Override public void enterChannelOption(MySqlParser.ChannelOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterChannelOption()");}
	@Override public void exitChannelOption(MySqlParser.ChannelOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitChannelOption()");}
	@Override public void enterDoDbReplication(MySqlParser.DoDbReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDoDbReplication()");}
	@Override public void exitDoDbReplication(MySqlParser.DoDbReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDoDbReplication()");}
	@Override public void enterIgnoreDbReplication(MySqlParser.IgnoreDbReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIgnoreDbReplication()");}
	@Override public void exitIgnoreDbReplication(MySqlParser.IgnoreDbReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIgnoreDbReplication()");}
	@Override public void enterDoTableReplication(MySqlParser.DoTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDoTableReplication()");}
	@Override public void exitDoTableReplication(MySqlParser.DoTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDoTableReplication()");}
	@Override public void enterIgnoreTableReplication(MySqlParser.IgnoreTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIgnoreTableReplication()");}
	@Override public void exitIgnoreTableReplication(MySqlParser.IgnoreTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIgnoreTableReplication()");}
	@Override public void enterWildDoTableReplication(MySqlParser.WildDoTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterWildDoTableReplication()");}
	@Override public void exitWildDoTableReplication(MySqlParser.WildDoTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitWildDoTableReplication()");}
	@Override public void enterWildIgnoreTableReplication(MySqlParser.WildIgnoreTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterWildIgnoreTableReplication()");}
	@Override public void exitWildIgnoreTableReplication(MySqlParser.WildIgnoreTableReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitWildIgnoreTableReplication()");}
	@Override public void enterRewriteDbReplication(MySqlParser.RewriteDbReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void exitRewriteDbReplication(MySqlParser.RewriteDbReplicationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRewriteDbReplication()");}
	@Override public void enterTablePair(MySqlParser.TablePairContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTablePair()");}
	@Override public void exitTablePair(MySqlParser.TablePairContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTablePair()");}
	@Override public void enterThreadType(MySqlParser.ThreadTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterThreadType()");}
	@Override public void exitThreadType(MySqlParser.ThreadTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitThreadType()");}
	@Override public void enterGtidsUntilOption(MySqlParser.GtidsUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterGtidsUntilOption()");}
	@Override public void exitGtidsUntilOption(MySqlParser.GtidsUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGtidsUntilOption()");}
	@Override public void enterMasterLogUntilOption(MySqlParser.MasterLogUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMasterLogUntilOption()");}
	@Override public void exitMasterLogUntilOption(MySqlParser.MasterLogUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMasterLogUntilOption()");}
	@Override public void enterRelayLogUntilOption(MySqlParser.RelayLogUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRelayLogUntilOption()");}
	@Override public void exitRelayLogUntilOption(MySqlParser.RelayLogUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRelayLogUntilOption()");}
	@Override public void enterSqlGapsUntilOption(MySqlParser.SqlGapsUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSqlGapsUntilOption()");}
	@Override public void exitSqlGapsUntilOption(MySqlParser.SqlGapsUntilOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSqlGapsUntilOption()");}
	@Override public void enterUserConnectionOption(MySqlParser.UserConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserConnectionOption()");}
	@Override public void exitUserConnectionOption(MySqlParser.UserConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserConnectionOption()");}
	@Override public void enterPasswordConnectionOption(MySqlParser.PasswordConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPasswordConnectionOption()");}
	@Override public void exitPasswordConnectionOption(MySqlParser.PasswordConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPasswordConnectionOption()");}
	@Override public void enterDefaultAuthConnectionOption(MySqlParser.DefaultAuthConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDefaultAuthConnectionOption()");}
	@Override public void exitDefaultAuthConnectionOption(MySqlParser.DefaultAuthConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDefaultAuthConnectionOption()");}
	@Override public void enterPluginDirConnectionOption(MySqlParser.PluginDirConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPluginDirConnectionOption()");}
	@Override public void exitPluginDirConnectionOption(MySqlParser.PluginDirConnectionOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPluginDirConnectionOption()");}
	@Override public void enterGtuidSet(MySqlParser.GtuidSetContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterGtuidSet() " + ctx.getText()  + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		}
	@Override public void exitGtuidSet(MySqlParser.GtuidSetContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGtuidSet()");}
	@Override public void enterXaStartTransaction(MySqlParser.XaStartTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXaStartTransaction()");}
	@Override public void exitXaStartTransaction(MySqlParser.XaStartTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXaStartTransaction()");}
	@Override public void enterXaEndTransaction(MySqlParser.XaEndTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXaEndTransaction()");}
	@Override public void exitXaEndTransaction(MySqlParser.XaEndTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXaEndTransaction()");}
	@Override public void enterXaPrepareStatement(MySqlParser.XaPrepareStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXaPrepareStatement()");}
	@Override public void exitXaPrepareStatement(MySqlParser.XaPrepareStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXaPrepareStatement()");}
	@Override public void enterXaCommitWork(MySqlParser.XaCommitWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXaCommitWork()");}
	@Override public void exitXaCommitWork(MySqlParser.XaCommitWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXaCommitWork()");}
	@Override public void enterXaRollbackWork(MySqlParser.XaRollbackWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXaRollbackWork()");}
	@Override public void exitXaRollbackWork(MySqlParser.XaRollbackWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXaRollbackWork()");}
	@Override public void enterXaRecoverWork(MySqlParser.XaRecoverWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXaRecoverWork()");}
	@Override public void exitXaRecoverWork(MySqlParser.XaRecoverWorkContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXaRecoverWork()");}
	@Override public void enterPrepareStatement(MySqlParser.PrepareStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPrepareStatement()");}
	@Override public void exitPrepareStatement(MySqlParser.PrepareStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPrepareStatement()");}
	@Override public void enterExecuteStatement(MySqlParser.ExecuteStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExecuteStatement()");}
	@Override public void exitExecuteStatement(MySqlParser.ExecuteStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExecuteStatement()");}
	@Override public void enterDeallocatePrepare(MySqlParser.DeallocatePrepareContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDeallocatePrepare()");}
	@Override public void exitDeallocatePrepare(MySqlParser.DeallocatePrepareContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDeallocatePrepare()");}
	@Override public void enterRoutineBody(MySqlParser.RoutineBodyContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRoutineBodyenterRoutineBody()");}
	@Override public void exitRoutineBody(MySqlParser.RoutineBodyContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRoutineBody()");}
	@Override public void enterBlockStatement(MySqlParser.BlockStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBlockStatement()");}
	@Override public void exitBlockStatement(MySqlParser.BlockStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBlockStatement()");}
	@Override public void enterCaseStatement(MySqlParser.CaseStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCaseStatement()");}
	@Override public void exitCaseStatement(MySqlParser.CaseStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCaseStatement()");}
	@Override public void enterIfStatement(MySqlParser.IfStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIfStatement()");}
	@Override public void exitIfStatement(MySqlParser.IfStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIfStatement()");}
	@Override public void enterIterateStatement(MySqlParser.IterateStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIterateStatement()");}
	@Override public void exitIterateStatement(MySqlParser.IterateStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIterateStatement()");}
	@Override public void enterLeaveStatement(MySqlParser.LeaveStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLeaveStatement()");}
	@Override public void exitLeaveStatement(MySqlParser.LeaveStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLeaveStatement()");}
	@Override public void enterLoopStatement(MySqlParser.LoopStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLoopStatement()");}
	@Override public void exitLoopStatement(MySqlParser.LoopStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLoopStatement()");}
	@Override public void enterRepeatStatement(MySqlParser.RepeatStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRepeatStatement()");}
	@Override public void exitRepeatStatement(MySqlParser.RepeatStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRepeatStatement()");}
	@Override public void enterReturnStatement(MySqlParser.ReturnStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterReturnStatement()");}
	@Override public void exitReturnStatement(MySqlParser.ReturnStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitReturnStatement()");}
	@Override public void enterWhileStatement(MySqlParser.WhileStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterWhileStatement()");}
	@Override public void exitWhileStatement(MySqlParser.WhileStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitWhileStatement()");}
	@Override public void enterCloseCursor(MySqlParser.CloseCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCloseCursor()");}
	@Override public void exitCloseCursor(MySqlParser.CloseCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCloseCursor()");}
	@Override public void enterFetchCursor(MySqlParser.FetchCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFetchCursor()");}
	@Override public void exitFetchCursor(MySqlParser.FetchCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFetchCursor()");}
	@Override public void enterOpenCursor(MySqlParser.OpenCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterOpenCursor()");}
	@Override public void exitOpenCursor(MySqlParser.OpenCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitOpenCursor()");}
	@Override public void enterDeclareVariable(MySqlParser.DeclareVariableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDeclareVariable()");}
	@Override public void exitDeclareVariable(MySqlParser.DeclareVariableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDeclareVariable()");}
	@Override public void enterDeclareCondition(MySqlParser.DeclareConditionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDeclareCondition()");}
	@Override public void exitDeclareCondition(MySqlParser.DeclareConditionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDeclareCondition()");}
	@Override public void enterDeclareCursor(MySqlParser.DeclareCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDeclareCursor()");}
	@Override public void exitDeclareCursor(MySqlParser.DeclareCursorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDeclareCursor()");}
	@Override public void enterDeclareHandler(MySqlParser.DeclareHandlerContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDeclareHandler()");}
	@Override public void exitDeclareHandler(MySqlParser.DeclareHandlerContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void enterHandlerConditionCode(MySqlParser.HandlerConditionCodeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDeclareHandler()");}
	@Override public void exitHandlerConditionCode(MySqlParser.HandlerConditionCodeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHandlerConditionCode()");}
	@Override public void enterHandlerConditionState(MySqlParser.HandlerConditionStateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHandlerConditionCode()");}
	@Override public void exitHandlerConditionState(MySqlParser.HandlerConditionStateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHandlerConditionState()");}
	@Override public void enterHandlerConditionName(MySqlParser.HandlerConditionNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHandlerConditionState()");}
	@Override public void exitHandlerConditionName(MySqlParser.HandlerConditionNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHandlerConditionName()");}
	@Override public void enterHandlerConditionWarning(MySqlParser.HandlerConditionWarningContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHandlerConditionName()");}
	@Override public void exitHandlerConditionWarning(MySqlParser.HandlerConditionWarningContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHandlerConditionWarning()");}
	@Override public void enterHandlerConditionNotfound(MySqlParser.HandlerConditionNotfoundContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHandlerConditionWarning()");}
	@Override public void exitHandlerConditionNotfound(MySqlParser.HandlerConditionNotfoundContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHandlerConditionNotfound()");}
	@Override public void enterHandlerConditionException(MySqlParser.HandlerConditionExceptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHandlerConditionException()");}
	@Override public void exitHandlerConditionException(MySqlParser.HandlerConditionExceptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHandlerConditionException()");}
	@Override public void enterProcedureSqlStatement(MySqlParser.ProcedureSqlStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterProcedureSqlStatement()");}
	@Override public void exitProcedureSqlStatement(MySqlParser.ProcedureSqlStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitProcedureSqlStatement()");}
	@Override public void enterCaseAlternative(MySqlParser.CaseAlternativeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCaseAlternative()");}
	@Override public void exitCaseAlternative(MySqlParser.CaseAlternativeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCaseAlternative()");}
	@Override public void enterElifAlternative(MySqlParser.ElifAlternativeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterElifAlternative()");}
	@Override public void exitElifAlternative(MySqlParser.ElifAlternativeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitElifAlternative()");}
	@Override public void enterAlterUserMysqlV56(MySqlParser.AlterUserMysqlV56Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterAlterUserMysqlV56()");}
	@Override public void exitAlterUserMysqlV56(MySqlParser.AlterUserMysqlV56Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitAlterUserMysqlV56()");}
	@Override public void enterAlterUserMysqlV57(MySqlParser.AlterUserMysqlV57Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterAlterUserMysqlV57()");}
	@Override public void exitAlterUserMysqlV57(MySqlParser.AlterUserMysqlV57Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitAlterUserMysqlV57()");}
	@Override public void enterCreateUserMysqlV56(MySqlParser.CreateUserMysqlV56Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCreateUserMysqlV56()");}
	@Override public void exitCreateUserMysqlV56(MySqlParser.CreateUserMysqlV56Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCreateUserMysqlV56()");}
	@Override public void enterCreateUserMysqlV57(MySqlParser.CreateUserMysqlV57Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCreateUserMysqlV57()");}
	@Override public void exitCreateUserMysqlV57(MySqlParser.CreateUserMysqlV57Context ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCreateUserMysqlV57()");}
	@Override public void enterDropUser(MySqlParser.DropUserContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDropUser()");}
	@Override public void exitDropUser(MySqlParser.DropUserContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDropUser()");}
	@Override public void enterGrantStatement(MySqlParser.GrantStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterGrantStatement()");}
	@Override public void exitGrantStatement(MySqlParser.GrantStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGrantStatement()");}
	@Override public void enterGrantProxy(MySqlParser.GrantProxyContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterGrantProxy()");}
	@Override public void exitGrantProxy(MySqlParser.GrantProxyContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGrantProxy()");}
	@Override public void enterRenameUser(MySqlParser.RenameUserContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterQueryTypeRenameTableUser()");}
	@Override public void exitRenameUser(MySqlParser.RenameUserContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRenameUser()");}
	@Override public void enterDetailRevoke(MySqlParser.DetailRevokeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDetailRevoke()");}
	@Override public void exitDetailRevoke(MySqlParser.DetailRevokeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDetailRevoke()");}
	@Override public void enterShortRevoke(MySqlParser.ShortRevokeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShortRevoke()");}
	@Override public void exitShortRevoke(MySqlParser.ShortRevokeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShortRevoke()");}
	@Override public void enterRevokeProxy(MySqlParser.RevokeProxyContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRevokeProxy()");}
	@Override public void exitRevokeProxy(MySqlParser.RevokeProxyContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRevokeProxy()");}
	@Override public void enterSetPasswordStatement(MySqlParser.SetPasswordStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetPasswordStatement()");}
	@Override public void exitSetPasswordStatement(MySqlParser.SetPasswordStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListenerexitSetPasswordStatement.()");}
	@Override public void enterUserSpecification(MySqlParser.UserSpecificationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserSpecification()");}
	@Override public void exitUserSpecification(MySqlParser.UserSpecificationContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserSpecification()");}
	@Override public void enterPasswordAuthOption(MySqlParser.PasswordAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPasswordAuthOption()");}
	@Override public void exitPasswordAuthOption(MySqlParser.PasswordAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPasswordAuthOption()");}
	@Override public void enterStringAuthOption(MySqlParser.StringAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStringAuthOption()");}
	@Override public void exitStringAuthOption(MySqlParser.StringAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStringAuthOption()");}
	@Override public void enterHashAuthOption(MySqlParser.HashAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHashAuthOption()");}
	@Override public void exitHashAuthOption(MySqlParser.HashAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHashAuthOption()");}
	@Override public void enterSimpleAuthOption(MySqlParser.SimpleAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleAuthOption()");}
	@Override public void exitSimpleAuthOption(MySqlParser.SimpleAuthOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleAuthOption()");}
	@Override public void enterTlsOption(MySqlParser.TlsOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTlsOption()");}
	@Override public void exitTlsOption(MySqlParser.TlsOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTlsOption()");}
	@Override public void enterUserResourceOption(MySqlParser.UserResourceOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserResourceOption()");}
	@Override public void exitUserResourceOption(MySqlParser.UserResourceOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserResourceOption()");}
	@Override public void enterUserPasswordOption(MySqlParser.UserPasswordOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserPasswordOption()");}
	@Override public void exitUserPasswordOption(MySqlParser.UserPasswordOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserPasswordOption()");}
	@Override public void enterUserLockOption(MySqlParser.UserLockOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserLockOption()");}
	@Override public void exitUserLockOption(MySqlParser.UserLockOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserLockOption()");}
	@Override public void enterPrivelegeClause(MySqlParser.PrivelegeClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPrivelegeClause()");}
	@Override public void exitPrivelegeClause(MySqlParser.PrivelegeClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPrivelegeClause()");}
	@Override public void enterPrivilege(MySqlParser.PrivilegeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPrivilege()");}
	@Override public void exitPrivilege(MySqlParser.PrivilegeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPrivilege()");}
	@Override public void enterCurrentSchemaPriviLevel(MySqlParser.CurrentSchemaPriviLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCurrentSchemaPriviLevel()");}
	@Override public void exitCurrentSchemaPriviLevel(MySqlParser.CurrentSchemaPriviLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCurrentSchemaPriviLevel()");}
	@Override public void enterGlobalPrivLevel(MySqlParser.GlobalPrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterGlobalPrivLevel()");}
	@Override public void exitGlobalPrivLevel(MySqlParser.GlobalPrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGlobalPrivLevel()");}
	@Override public void enterDefiniteSchemaPrivLevel(MySqlParser.DefiniteSchemaPrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDefiniteSchemaPrivLevel()");}
	@Override public void exitDefiniteSchemaPrivLevel(MySqlParser.DefiniteSchemaPrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDefiniteSchemaPrivLevel()");}
	@Override public void enterDefiniteFullTablePrivLevel(MySqlParser.DefiniteFullTablePrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDefiniteFullTablePrivLevel()");}
	@Override public void exitDefiniteFullTablePrivLevel(MySqlParser.DefiniteFullTablePrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDefiniteFullTablePrivLevel()");}
	@Override public void enterDefiniteTablePrivLevel(MySqlParser.DefiniteTablePrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDefiniteTablePrivLevel()");}
	@Override public void exitDefiniteTablePrivLevel(MySqlParser.DefiniteTablePrivLevelContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDefiniteTablePrivLevel()");}
	@Override public void enterRenameUserClause(MySqlParser.RenameUserClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserClause()");}
	@Override public void exitRenameUserClause(MySqlParser.RenameUserClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRenameUserClause()");}
	@Override public void enterAnalyzeTable(MySqlParser.AnalyzeTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterAnalyzeTable()");}
	@Override public void exitAnalyzeTable(MySqlParser.AnalyzeTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitAnalyzeTable()");}
	@Override public void enterCheckTable(MySqlParser.CheckTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCheckTable()");}
	@Override public void exitCheckTable(MySqlParser.CheckTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCheckTable()");}
	@Override public void enterChecksumTable(MySqlParser.ChecksumTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterChecksumTable()");}
	@Override public void exitChecksumTable(MySqlParser.ChecksumTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitChecksumTable()");}
	@Override public void enterOptimizeTable(MySqlParser.OptimizeTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterOptimizeTable()");}
	@Override public void exitOptimizeTable(MySqlParser.OptimizeTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitOptimizeTable()");}
	@Override public void enterRepairTable(MySqlParser.RepairTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRepairTable()");}
	@Override public void exitRepairTable(MySqlParser.RepairTableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRepairTable()");}
	@Override public void enterCheckTableOption(MySqlParser.CheckTableOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCheckTableOption()");}
	@Override public void exitCheckTableOption(MySqlParser.CheckTableOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCheckTableOption()");}
	@Override public void enterCreateUdfunction(MySqlParser.CreateUdfunctionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCreateUdfunction()");}
	@Override public void exitCreateUdfunction(MySqlParser.CreateUdfunctionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCreateUdfunction()");}
	@Override public void enterInstallPlugin(MySqlParser.InstallPluginContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterInstallPlugin()");}
	@Override public void exitInstallPlugin(MySqlParser.InstallPluginContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitInstallPlugin()");}
	@Override public void enterUninstallPlugin(MySqlParser.UninstallPluginContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUninstallPlugin()");}
	@Override public void exitUninstallPlugin(MySqlParser.UninstallPluginContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUninstallPlugin()");}
	@Override public void enterSetVariable(MySqlParser.SetVariableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetVariable()");}
	@Override public void exitSetVariable(MySqlParser.SetVariableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetVariable()");}
	@Override public void enterSetCharset(MySqlParser.SetCharsetContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetCharset()");}
	@Override public void exitSetCharset(MySqlParser.SetCharsetContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetCharset()");}
	@Override public void enterSetNames(MySqlParser.SetNamesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetNames()");}
	@Override public void exitSetNames(MySqlParser.SetNamesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetNames()");}
	@Override public void enterSetPassword(MySqlParser.SetPasswordContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetPassword()");}
	@Override public void exitSetPassword(MySqlParser.SetPasswordContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetPassword()");}
	@Override public void enterSetTransaction(MySqlParser.SetTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetTransaction()");}
	@Override public void exitSetTransaction(MySqlParser.SetTransactionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetTransaction()");}
	@Override public void enterSetAutocommit(MySqlParser.SetAutocommitContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSetAutocommit()");}
	@Override public void exitSetAutocommit(MySqlParser.SetAutocommitContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSetAutocommit()");}
	@Override public void enterShowMasterLogs(MySqlParser.ShowMasterLogsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowMasterLogs()");}
	@Override public void exitShowMasterLogs(MySqlParser.ShowMasterLogsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowMasterLogs()");}
	@Override public void enterShowLogEvents(MySqlParser.ShowLogEventsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowLogEvents()");}
	@Override public void exitShowLogEvents(MySqlParser.ShowLogEventsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowLogEvents()");}
	@Override public void enterShowObjectFilter(MySqlParser.ShowObjectFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowObjectFilter()");}
	@Override public void exitShowObjectFilter(MySqlParser.ShowObjectFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowObjectFilter()");}
	@Override public void enterShowColumns(MySqlParser.ShowColumnsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowColumns()");}
	@Override public void exitShowColumns(MySqlParser.ShowColumnsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowColumns()");}
	@Override public void enterShowCreateDb(MySqlParser.ShowCreateDbContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowCreateDb()");}
	@Override public void exitShowCreateDb(MySqlParser.ShowCreateDbContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowCreateDb()");}
	@Override public void enterShowCreateFullIdObject(MySqlParser.ShowCreateFullIdObjectContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowCreateFullIdObject()");}
	@Override public void exitShowCreateFullIdObject(MySqlParser.ShowCreateFullIdObjectContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowCreateFullIdObject()");}
	@Override public void enterShowCreateUser(MySqlParser.ShowCreateUserContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowCreateUser()");}
	@Override public void exitShowCreateUser(MySqlParser.ShowCreateUserContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowCreateUser()");}
	@Override public void enterShowEngine(MySqlParser.ShowEngineContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowEngine()");}
	@Override public void exitShowEngine(MySqlParser.ShowEngineContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowEngine()");}
	@Override public void enterShowGlobalInfo(MySqlParser.ShowGlobalInfoContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowGlobalInfo()");}
	@Override public void exitShowGlobalInfo(MySqlParser.ShowGlobalInfoContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void enterShowErrors(MySqlParser.ShowErrorsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowGlobalInfo()");}
	@Override public void exitShowErrors(MySqlParser.ShowErrorsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowErrors()");}
	@Override public void enterShowCountErrors(MySqlParser.ShowCountErrorsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowCountErrors()");}
	@Override public void exitShowCountErrors(MySqlParser.ShowCountErrorsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowCountErrors()");}
	@Override public void enterShowSchemaFilter(MySqlParser.ShowSchemaFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowSchemaFilter()");}
	@Override public void exitShowSchemaFilter(MySqlParser.ShowSchemaFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowSchemaFilter()");}
	@Override public void enterShowRoutine(MySqlParser.ShowRoutineContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowRoutine()");}
	@Override public void exitShowRoutine(MySqlParser.ShowRoutineContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowRoutine()");}
	@Override public void enterShowGrants(MySqlParser.ShowGrantsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowGrants()");}
	@Override public void exitShowGrants(MySqlParser.ShowGrantsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowGrants()");}
	@Override public void enterShowIndexes(MySqlParser.ShowIndexesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowIndexes()");}
	@Override public void exitShowIndexes(MySqlParser.ShowIndexesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowIndexes()");}
	@Override public void enterShowOpenTables(MySqlParser.ShowOpenTablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowOpenTables()");}
	@Override public void exitShowOpenTables(MySqlParser.ShowOpenTablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowOpenTables()");}
	@Override public void enterShowProfile(MySqlParser.ShowProfileContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowProfile()");}
	@Override public void exitShowProfile(MySqlParser.ShowProfileContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowProfile()");}
	@Override public void enterShowSlaveStatus(MySqlParser.ShowSlaveStatusContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowSlaveStatus()");}
	@Override public void exitShowSlaveStatus(MySqlParser.ShowSlaveStatusContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowSlaveStatus()");}
	@Override public void enterVariableClause(MySqlParser.VariableClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterVariableClause()");}
	@Override public void exitVariableClause(MySqlParser.VariableClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitVariableClause()");}
	@Override public void enterShowCommonEntity(MySqlParser.ShowCommonEntityContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowCommonEntity()");}
	@Override public void exitShowCommonEntity(MySqlParser.ShowCommonEntityContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowCommonEntity()");}
	@Override public void enterShowFilter(MySqlParser.ShowFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowFilter()");}
	@Override public void exitShowFilter(MySqlParser.ShowFilterContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowFilter()");}
	@Override public void enterShowGlobalInfoClause(MySqlParser.ShowGlobalInfoClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowGlobalInfoClause()");}
	@Override public void exitShowGlobalInfoClause(MySqlParser.ShowGlobalInfoClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowGlobalInfoClause()");}
	@Override public void enterShowSchemaEntity(MySqlParser.ShowSchemaEntityContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowSchemaEntity()");}
	@Override public void exitShowSchemaEntity(MySqlParser.ShowSchemaEntityContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowSchemaEntity()");}
	@Override public void enterShowProfileType(MySqlParser.ShowProfileTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShowProfileType()");}
	@Override public void exitShowProfileType(MySqlParser.ShowProfileTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShowProfileType()");}
	@Override public void enterBinlogStatement(MySqlParser.BinlogStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBinlogStatement()");}
	@Override public void exitBinlogStatement(MySqlParser.BinlogStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBinlogStatement()");}
	@Override public void enterCacheIndexStatement(MySqlParser.CacheIndexStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCacheIndexStatement()");}
	@Override public void exitCacheIndexStatement(MySqlParser.CacheIndexStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCacheIndexStatement()");}
	@Override public void enterFlushStatement(MySqlParser.FlushStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFlushStatement()");}
	@Override public void exitFlushStatement(MySqlParser.FlushStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFlushStatement()");}
	@Override public void enterKillStatement(MySqlParser.KillStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterKillStatement()");}
	@Override public void exitKillStatement(MySqlParser.KillStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitKillStatement()");}
	@Override public void enterLoadIndexIntoCache(MySqlParser.LoadIndexIntoCacheContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLoadIndexIntoCache()");}
	@Override public void exitLoadIndexIntoCache(MySqlParser.LoadIndexIntoCacheContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLoadIndexIntoCache()");}
	@Override public void enterResetStatement(MySqlParser.ResetStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterResetStatement()");}
	@Override public void exitResetStatement(MySqlParser.ResetStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitResetStatement()");}
	@Override public void enterShutdownStatement(MySqlParser.ShutdownStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterShutdownStatement()");}
	@Override public void exitShutdownStatement(MySqlParser.ShutdownStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitShutdownStatement()");}
	@Override public void enterTableIndexes(MySqlParser.TableIndexesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTableIndexes()");}
	@Override public void exitTableIndexes(MySqlParser.TableIndexesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTableIndexes()");}
	@Override public void enterSimpleFlushOption(MySqlParser.SimpleFlushOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleFlushOption()");}
	@Override public void exitSimpleFlushOption(MySqlParser.SimpleFlushOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleFlushOption()");}
	@Override public void enterChannelFlushOption(MySqlParser.ChannelFlushOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterChannelFlushOption()");}
	@Override public void exitChannelFlushOption(MySqlParser.ChannelFlushOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitChannelFlushOption()");}
	@Override public void enterTableFlushOption(MySqlParser.TableFlushOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTableFlushOption()");}
	@Override public void exitTableFlushOption(MySqlParser.TableFlushOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTableFlushOption()");}
	@Override public void enterFlushTableOption(MySqlParser.FlushTableOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFlushTableOption()");}
	@Override public void exitFlushTableOption(MySqlParser.FlushTableOptionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFlushTableOption()");}
	@Override public void enterLoadedTableIndexes(MySqlParser.LoadedTableIndexesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLoadedTableIndexes()");}
	@Override public void exitLoadedTableIndexes(MySqlParser.LoadedTableIndexesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLoadedTableIndexes()");}
	@Override public void enterSimpleDescribeStatement(MySqlParser.SimpleDescribeStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleDescribeStatement()");}
	@Override public void exitSimpleDescribeStatement(MySqlParser.SimpleDescribeStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleDescribeStatement()");}
	@Override public void enterFullDescribeStatement(MySqlParser.FullDescribeStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFullDescribeStatement()");}
	@Override public void exitFullDescribeStatement(MySqlParser.FullDescribeStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFullDescribeStatement()");}
	@Override public void enterHelpStatement(MySqlParser.HelpStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHelpStatement()");}
	@Override public void exitHelpStatement(MySqlParser.HelpStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHelpStatement()");}
	@Override public void enterUseStatement(MySqlParser.UseStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUseStatement()");}
	@Override public void exitUseStatement(MySqlParser.UseStatementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUseStatement()");}
	@Override public void enterDescribeStatements(MySqlParser.DescribeStatementsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDescribeStatements()");}
	@Override public void exitDescribeStatements(MySqlParser.DescribeStatementsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDescribeStatements()");}
	@Override public void enterDescribeConnection(MySqlParser.DescribeConnectionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDescribeConnection()");}
	@Override public void exitDescribeConnection(MySqlParser.DescribeConnectionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDescribeConnection()");}
	@Override public void enterFullId(MySqlParser.FullIdContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterFullId(): " + ctx.getText());
		}
	@Override public void exitFullId(MySqlParser.FullIdContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitFullId() " + ctx.getText()  + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		}
	/**
	 * This is useful because it's called in DROP, ALTER, etc., but enter enterAtomTableItem() is not called for tables in those queries.
	 */
	@Override public void enterTableName(MySqlParser.TableNameContext ctx) {
		FullIdContext f = null;
		try {
			Log.logQueryParseProgress("AntlrMySQLListener.enterTableName(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop() + " Parent Context = " + ctx.getParent().getClass());
			FullIdContext fullIdContext = (FullIdContext)ctx.getChild(0);
			processFullIdContext(fullIdContext);
		} catch (Exception ex) {
			Log.logError("AntlrMySQLListener.enterTableName(): " + ex.getLocalizedMessage());
		}
	}
	/***
	 * We have a table name that may or may not have a schema appended to it
	 * @param fullIdContext
	 */
	public void processFullIdContext(FullIdContext fullIdContext) {
		try {
			switch (fullIdContext.getChildCount()) {
			case 1:
				// Just the table name, no schema
				fullTableNames.add(new FullTableName(queryDefinition.getSchemaName(), fullIdContext.getChild(0).getText(), "")); 
				break;
			case 2:
				// Schema followed by table name
				fullTableNames.add(new FullTableName(fullIdContext.getChild(0).getText(), fullIdContext.getChild(1).toString(), "")); 
				break;
			case 3:
				// Schema followed by dot then table name
				fullTableNames.add(new FullTableName(fullIdContext.getChild(0).getText(), fullIdContext.getChild(2).getText(), "")); 
				break;
			default:
				Log.logQueryParseProgress("AntlrMySQLListener.processFullIdContext(): too many children found");
			}
		} catch (Exception ex) {
			Log.logError("AntlrMySQLListener.processFullIdContext(): " + ex.getLocalizedMessage());
		}
	}
	@Override public void exitTableName(MySqlParser.TableNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTableName()");}
	@Override public void enterFullColumnName(MySqlParser.FullColumnNameContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterFullColumnName(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop() + " Parent.stop = " + ctx.getParent().getStop().getText());
		processFullColumnNameContext(ctx);
	}
	@Override public void exitFullColumnName(MySqlParser.FullColumnNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFullColumnName()");}
	@Override public void enterIndexColumnName(MySqlParser.IndexColumnNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIndexColumnName()");}
	@Override public void exitIndexColumnName(MySqlParser.IndexColumnNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIndexColumnName()");}
	@Override public void enterUserName(MySqlParser.UserNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserName()");}
	@Override public void exitUserName(MySqlParser.UserNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserName()");}
	@Override public void enterMysqlVariable(MySqlParser.MysqlVariableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMysqlVariable()");}
	@Override public void exitMysqlVariable(MySqlParser.MysqlVariableContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMysqlVariable()");}
	@Override public void enterCharsetName(MySqlParser.CharsetNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCharsetName()");}
	@Override public void exitCharsetName(MySqlParser.CharsetNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCharsetName()");}
	@Override public void enterCollationName(MySqlParser.CollationNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCollationName()");}
	@Override public void exitCollationName(MySqlParser.CollationNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCollationName()");}
	@Override public void enterEngineName(MySqlParser.EngineNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterEngineName()");}
	@Override public void exitEngineName(MySqlParser.EngineNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitEngineName()");}
	@Override public void enterUuidSet(MySqlParser.UuidSetContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUuidSet()");}
	@Override public void exitUuidSet(MySqlParser.UuidSetContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUuidSet()");}
	@Override public void enterXid(MySqlParser.XidContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXid()");}
	@Override public void exitXid(MySqlParser.XidContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXid()");}
	@Override public void enterXuidStringId(MySqlParser.XuidStringIdContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterXuidStringId()");}
	@Override public void exitXuidStringId(MySqlParser.XuidStringIdContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitXuidStringId()");}
	@Override public void enterAuthPlugin(MySqlParser.AuthPluginContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterAuthPlugin()");}
	@Override public void exitAuthPlugin(MySqlParser.AuthPluginContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitAuthPlugin()");}
	@Override public void enterUid(MySqlParser.UidContext ctx) {
		// We land here when parsing an ALTER Table statement. The field we are altering is this one
		Log.logQueryParseProgress("AntlrMySQLListener.enterUid(): " + ctx.getText() + ", " + ctx.getChild(0).getText() + " Parent Context = " + ctx.getParent().getClass());
//		processUidContext(ctx);
	}
	private void processUidContext(MySqlParser.UidContext ctx, String schemaName, String tableName) {
		String foo = " " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop();
		// Add to the list of columns that are referenced in this query
		//String start = ctx.getStart().getText();
		String stop = ctx.getStop().getText();
		FullColumnName fullColumnName = null;
		switch (ctx.children.size()) {
		case 1:	
			// Just an attribute Name
			if (ctx.children.get(0).getText().compareTo(",") != 0) { 
				fullColumnName = new FullColumnName(schemaName, tableName, ctx.children.get(0).getText());
			} else {
				fullColumnName = null;
			}
			break;
		case 2:
			// An Attribute Name and a table/query name/alias
			fullColumnName = new FullColumnName(schemaName, 
												ctx.children.get(0).getText(),  
					                            ctx.children.get(1).getText());
			break;
		case 3:
			// An Attribute Name, a table/query name/alias, and a schema name
			fullColumnName = new FullColumnName(ctx.children.get(0).getText(),  
												ctx.children.get(1).getText(), 
												ctx.children.get(2).getText());
			break;
		default: 
			Log.logError("processUidContext(): unrecognized number of children (" + ctx.children.size() +")");
			break;
		}
		if (fullColumnName != null) {
			fullColumnName.addAliasName(new AliasNameClassOLD(currentAlias));
			fullColumnNames.addFullColumnName(fullColumnName);
		}
	}
	@Override public void exitUid(MySqlParser.UidContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUid()");}
	@Override public void enterSimpleId(MySqlParser.SimpleIdContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleId(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitSimpleId(MySqlParser.SimpleIdContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleId() " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());}
	@Override public void enterDottedId(MySqlParser.DottedIdContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterDottedId(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		}
	@Override public void exitDottedId(MySqlParser.DottedIdContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDottedId()");}
	@Override public void enterDecimalLiteral(MySqlParser.DecimalLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDecimalLiteral()");}
	@Override public void exitDecimalLiteral(MySqlParser.DecimalLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDecimalLiteral()");}
	@Override public void enterFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFileSizeLiteral()");}
	@Override public void exitFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFileSizeLiteral()");}
	@Override public void enterStringLiteral(MySqlParser.StringLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStringLiteral()");}
	@Override public void exitStringLiteral(MySqlParser.StringLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitStringLiteral()");}
	@Override public void enterBooleanLiteral(MySqlParser.BooleanLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBooleanLiteral()");}
	@Override public void exitBooleanLiteral(MySqlParser.BooleanLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBooleanLiteral()");}
	@Override public void enterHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterHexadecimalLiteral()");}
	@Override public void exitHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitHexadecimalLiteral()");}
	@Override public void enterNullNotnull(MySqlParser.NullNotnullContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterNullNotnull()");}
	@Override public void exitNullNotnull(MySqlParser.NullNotnullContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitNullNotnull()");}
	@Override public void enterConstant(MySqlParser.ConstantContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterConstant()");}
	@Override public void exitConstant(MySqlParser.ConstantContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitConstant()");}
	@Override public void enterStringDataType(MySqlParser.StringDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void exitStringDataType(MySqlParser.StringDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterStringDataType()");}
	@Override public void enterDimensionDataType(MySqlParser.DimensionDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDimensionDataType()");}
	@Override public void exitDimensionDataType(MySqlParser.DimensionDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDimensionDataType()");}
	@Override public void enterSimpleDataType(MySqlParser.SimpleDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleDataType()");}
	@Override public void exitSimpleDataType(MySqlParser.SimpleDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleDataType()");}
	@Override public void enterCollectionDataType(MySqlParser.CollectionDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCollectionDataType()");}
	@Override public void exitCollectionDataType(MySqlParser.CollectionDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCollectionDataType()");}
	@Override public void enterSpatialDataType(MySqlParser.SpatialDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSpatialDataType()");}
	@Override public void exitSpatialDataType(MySqlParser.SpatialDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSpatialDataType()");}
	@Override public void enterConvertedDataType(MySqlParser.ConvertedDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterConvertedDataType()");}
	@Override public void exitConvertedDataType(MySqlParser.ConvertedDataTypeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitConvertedDataType()");}
	@Override public void enterLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLengthOneDimension()");}
	@Override public void exitLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLengthOneDimension()");}
	@Override public void enterLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLengthTwoDimension()");}
	@Override public void exitLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLengthTwoDimension()");}
	@Override public void enterLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLengthTwoOptionalDimension()");}
	@Override public void exitLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLengthTwoOptionalDimension()");}
	@Override public void enterUidList(MySqlParser.UidListContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterUidList()" + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void exitUidList(MySqlParser.UidListContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUidList()");}
	@Override public void enterTables(MySqlParser.TablesContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterTables()");
	}
	@Override public void exitTables(MySqlParser.TablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTables()");}
	@Override public void enterIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIndexColumnNames()");}
	@Override public void exitIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIndexColumnNames()");}
	@Override public void enterExpressions(MySqlParser.ExpressionsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExpressions()");}
	@Override public void exitExpressions(MySqlParser.ExpressionsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExpressions()");}
	@Override public void enterExpressionsWithDefaults(MySqlParser.ExpressionsWithDefaultsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExpressionsWithDefaults()");}
	@Override public void exitExpressionsWithDefaults(MySqlParser.ExpressionsWithDefaultsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExpressionsWithDefaults()");}
	@Override public void enterConstants(MySqlParser.ConstantsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterConstants()");}
	@Override public void exitConstants(MySqlParser.ConstantsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitConstants()");}
	@Override public void enterSimpleStrings(MySqlParser.SimpleStringsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleStrings()");}
	@Override public void exitSimpleStrings(MySqlParser.SimpleStringsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleStrings()");}
	@Override public void enterUserVariables(MySqlParser.UserVariablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUserVariables()");}
	@Override public void exitUserVariables(MySqlParser.UserVariablesContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUserVariables()");}
	@Override public void enterDefaultValue(MySqlParser.DefaultValueContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDefaultValue()");}
	@Override public void exitDefaultValue(MySqlParser.DefaultValueContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDefaultValue()");}
	@Override public void enterExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExpressionOrDefault()");}
	@Override public void exitExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExpressionOrDefault()");}
	@Override public void enterIfExists(MySqlParser.IfExistsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIfExists()");}
	@Override public void exitIfExists(MySqlParser.IfExistsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIfExists()");}
	@Override public void enterIfNotExists(MySqlParser.IfNotExistsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIfNotExists()");}
	@Override public void exitIfNotExists(MySqlParser.IfNotExistsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIfNotExists()");}
	@Override public void enterSpecificFunctionCall(MySqlParser.SpecificFunctionCallContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterSpecificFunctionCall(): " + ctx.getText() );
	}
	@Override public void exitSpecificFunctionCall(MySqlParser.SpecificFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSpecificFunctionCall()");}
	@Override public void enterAggregateFunctionCall(MySqlParser.AggregateFunctionCallContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterAggregateFunctionCall(): " + ctx.getText());
	}
	@Override public void exitAggregateFunctionCall(MySqlParser.AggregateFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitAggregateFunctionCall()");}
	@Override public void enterScalarFunctionCall(MySqlParser.ScalarFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterScalarFunctionCall()");}
	@Override public void exitScalarFunctionCall(MySqlParser.ScalarFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitScalarFunctionCall()");}
	@Override public void enterUdfFunctionCall(MySqlParser.UdfFunctionCallContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterUdfFunctionCall(): " + ctx.getText() + ", " + ctx.getChild(0).getText());
		
		// ToDo capture this as a UDF. child(0) is the name of the function. :)
	}
	@Override public void exitUdfFunctionCall(MySqlParser.UdfFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUdfFunctionCall()");}
	@Override public void enterPasswordFunctionCall(MySqlParser.PasswordFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPasswordFunctionCall()");}
	@Override public void exitPasswordFunctionCall(MySqlParser.PasswordFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPasswordFunctionCall()");}
	@Override public void enterSimpleFunctionCall(MySqlParser.SimpleFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSimpleFunctionCall()");}
	@Override public void exitSimpleFunctionCall(MySqlParser.SimpleFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSimpleFunctionCall()");}
	@Override public void enterDataTypeFunctionCall(MySqlParser.DataTypeFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDataTypeFunctionCall()");}
	@Override public void exitDataTypeFunctionCall(MySqlParser.DataTypeFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDataTypeFunctionCall()");}
	@Override public void enterValuesFunctionCall(MySqlParser.ValuesFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterValuesFunctionCall()");}
	@Override public void exitValuesFunctionCall(MySqlParser.ValuesFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitValuesFunctionCall()");}
	@Override public void enterCaseFunctionCall(MySqlParser.CaseFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCaseFunctionCall()");}
	@Override public void exitCaseFunctionCall(MySqlParser.CaseFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCaseFunctionCall()");}
	@Override public void enterCharFunctionCall(MySqlParser.CharFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCharFunctionCall()");}
	@Override public void exitCharFunctionCall(MySqlParser.CharFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCharFunctionCall()");}
	@Override public void enterPositionFunctionCall(MySqlParser.PositionFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPositionFunctionCall()");}
	@Override public void exitPositionFunctionCall(MySqlParser.PositionFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPositionFunctionCall()");}
	@Override public void enterSubstrFunctionCall(MySqlParser.SubstrFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSubstrFunctionCall()");}
	@Override public void exitSubstrFunctionCall(MySqlParser.SubstrFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSubstrFunctionCall()");}
	@Override public void enterTrimFunctionCall(MySqlParser.TrimFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTrimFunctionCall()");}
	@Override public void exitTrimFunctionCall(MySqlParser.TrimFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterWeightFunctionCall()");}
	@Override public void enterWeightFunctionCall(MySqlParser.WeightFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void exitWeightFunctionCall(MySqlParser.WeightFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitWeightFunctionCall()");}
	@Override public void enterExtractFunctionCall(MySqlParser.ExtractFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExtractFunctionCall()");}
	@Override public void exitExtractFunctionCall(MySqlParser.ExtractFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExtractFunctionCall()");}
	@Override public void enterGetFormatFunctionCall(MySqlParser.GetFormatFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterGetFormatFunctionCall()");}
	@Override public void exitGetFormatFunctionCall(MySqlParser.GetFormatFunctionCallContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitGetFormatFunctionCall()");}
	@Override public void enterCaseFuncAlternative(MySqlParser.CaseFuncAlternativeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCaseFuncAlternative()");}
	@Override public void exitCaseFuncAlternative(MySqlParser.CaseFuncAlternativeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCaseFuncAlternative()");}
	@Override public void enterLevelWeightList(MySqlParser.LevelWeightListContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLevelWeightList()");}
	@Override public void exitLevelWeightList(MySqlParser.LevelWeightListContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLevelWeightList()");}
	@Override public void enterLevelWeightRange(MySqlParser.LevelWeightRangeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLevelWeightRange()");}
	@Override public void exitLevelWeightRange(MySqlParser.LevelWeightRangeContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLevelWeightRange()");}
	@Override public void enterLevelInWeightListElement(MySqlParser.LevelInWeightListElementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLevelInWeightListElement()");}
	@Override public void exitLevelInWeightListElement(MySqlParser.LevelInWeightListElementContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLevelInWeightListElement()");}
	@Override public void enterAggregateWindowedFunction(MySqlParser.AggregateWindowedFunctionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterAggregateWindowedFunction()");}
	@Override public void exitAggregateWindowedFunction(MySqlParser.AggregateWindowedFunctionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitAggregateWindowedFunction()");}
	@Override public void enterScalarFunctionName(MySqlParser.ScalarFunctionNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterScalarFunctionName()");}
	@Override public void exitScalarFunctionName(MySqlParser.ScalarFunctionNameContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitScalarFunctionName()");}
	@Override public void enterPasswordFunctionClause(MySqlParser.PasswordFunctionClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPasswordFunctionClause()");}
	@Override public void exitPasswordFunctionClause(MySqlParser.PasswordFunctionClauseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPasswordFunctionClause()");}
	@Override public void enterFunctionArgs(MySqlParser.FunctionArgsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFunctionArgs()");}
	@Override public void exitFunctionArgs(MySqlParser.FunctionArgsContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFunctionArgs()");}
	@Override public void enterFunctionArg(MySqlParser.FunctionArgContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFunctionArg()");}
	@Override public void exitFunctionArg(MySqlParser.FunctionArgContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFunctionArg()");}
	@Override public void enterIsExpression(MySqlParser.IsExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIsExpression()");}
	@Override public void exitIsExpression(MySqlParser.IsExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIsExpression()");}
	@Override public void enterNotExpression(MySqlParser.NotExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterNotExpression()");}
	@Override public void exitNotExpression(MySqlParser.NotExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitNotExpression()");}
	@Override public void enterLogicalExpression(MySqlParser.LogicalExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLogicalExpression()");}
	@Override public void exitLogicalExpression(MySqlParser.LogicalExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLogicalExpression()");}
	@Override public void enterPredicateExpression(MySqlParser.PredicateExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPredicateExpression()");}
	@Override public void exitPredicateExpression(MySqlParser.PredicateExpressionContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPredicateExpression()");}
	@Override public void enterSoundsLikePredicate(MySqlParser.SoundsLikePredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSoundsLikePredicate()");}
	@Override public void exitSoundsLikePredicate(MySqlParser.SoundsLikePredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSoundsLikePredicate()");}
	@Override public void enterExpressionAtomPredicate(MySqlParser.ExpressionAtomPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExpressionAtomPredicate()");}
	@Override public void exitExpressionAtomPredicate(MySqlParser.ExpressionAtomPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExpressionAtomPredicate()");}
	@Override public void enterInPredicate(MySqlParser.InPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterInPredicate()");}
	@Override public void exitInPredicate(MySqlParser.InPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitInPredicate()");}
	@Override public void enterSubqueryComparasionPredicate(MySqlParser.SubqueryComparasionPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSubqueryComparasionPredicate()");}
	@Override public void exitSubqueryComparasionPredicate(MySqlParser.SubqueryComparasionPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSubqueryComparasionPredicate()");}
	@Override public void enterBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBetweenPredicate()");}
	@Override public void exitBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBetweenPredicate()");}
	@Override public void enterBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBinaryComparasionPredicate()");}
	@Override public void exitBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBinaryComparasionPredicate()");}
	@Override public void enterIsNullPredicate(MySqlParser.IsNullPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIsNullPredicate()");}
	@Override public void exitIsNullPredicate(MySqlParser.IsNullPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIsNullPredicate()");}
	@Override public void enterLikePredicate(MySqlParser.LikePredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLikePredicate()");}
	@Override public void exitLikePredicate(MySqlParser.LikePredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLikePredicate()");}
	@Override public void enterRegexpPredicate(MySqlParser.RegexpPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterRegexpPredicate()");}
	@Override public void exitRegexpPredicate(MySqlParser.RegexpPredicateContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitRegexpPredicate()");}
	@Override public void enterUnaryExpressionAtom(MySqlParser.UnaryExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUnaryExpressionAtom()");}
	@Override public void exitUnaryExpressionAtom(MySqlParser.UnaryExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUnaryExpressionAtom()");}
	@Override public void enterCollateExpressionAtom(MySqlParser.CollateExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCollateExpressionAtom()");}
	@Override public void exitCollateExpressionAtom(MySqlParser.CollateExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCollateExpressionAtom()");}
	@Override public void enterSubqueryExpessionAtom(MySqlParser.SubqueryExpessionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterSubqueryExpessionAtom()");}
	@Override public void exitSubqueryExpessionAtom(MySqlParser.SubqueryExpessionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitSubqueryExpessionAtom()");}
	@Override public void enterMysqlVariableExpressionAtom(MySqlParser.MysqlVariableExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMysqlVariableExpressionAtom()");}
	@Override public void exitMysqlVariableExpressionAtom(MySqlParser.MysqlVariableExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMysqlVariableExpressionAtom()");}
	@Override public void enterNestedExpressionAtom(MySqlParser.NestedExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterNestedExpressionAtom() : " + ctx.getText());}
	@Override public void exitNestedExpressionAtom(MySqlParser.NestedExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitNestedExpressionAtom() : " + ctx.getText());}
	@Override public void enterNestedRowExpressionAtom(MySqlParser.NestedRowExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterNestedRowExpressionAtom() : " + ctx.getText());}
	@Override public void exitNestedRowExpressionAtom(MySqlParser.NestedRowExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitNestedRowExpressionAtom() : " + ctx.getText());}
	@Override public void enterMathExpressionAtom(MySqlParser.MathExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMathExpressionAtom() : " + ctx.getText());}
	@Override public void exitMathExpressionAtom(MySqlParser.MathExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMathExpressionAtom() : " + ctx.getText());}
	@Override public void enterIntervalExpressionAtom(MySqlParser.IntervalExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIntervalExpressionAtom()");}
	@Override public void exitIntervalExpressionAtom(MySqlParser.IntervalExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitIntervalExpressionAtom()");}
	@Override public void enterExistsExpessionAtom(MySqlParser.ExistsExpessionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterExistsExpessionAtom()");}
	@Override public void exitExistsExpessionAtom(MySqlParser.ExistsExpessionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitExistsExpessionAtom()");}
	@Override public void enterConstantExpressionAtom(MySqlParser.ConstantExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterConstantExpressionAtom()");}
	@Override public void exitConstantExpressionAtom(MySqlParser.ConstantExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitConstantExpressionAtom()");}
	@Override public void enterFunctionCallExpressionAtom(MySqlParser.FunctionCallExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterFunctionCallExpressionAtom()");}
	@Override public void exitFunctionCallExpressionAtom(MySqlParser.FunctionCallExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFunctionCallExpressionAtom()");}
	@Override public void enterBinaryExpressionAtom(MySqlParser.BinaryExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBinaryExpressionAtom()");}
	@Override public void exitBinaryExpressionAtom(MySqlParser.BinaryExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBinaryExpressionAtom()");}
	@Override public void enterFullColumnNameExpressionAtom(MySqlParser.FullColumnNameExpressionAtomContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterFullColumnNameExpressionAtom()");
		}
	// An expression, such as a WHERE clause or an ORDER BY clause
	@Override public void exitFullColumnNameExpressionAtom(MySqlParser.FullColumnNameExpressionAtomContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitFullColumnNameExpressionAtom() " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	@Override public void enterBitExpressionAtom(MySqlParser.BitExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBitExpressionAtom()");}
	@Override public void exitBitExpressionAtom(MySqlParser.BitExpressionAtomContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBitExpressionAtom()");}
	@Override public void enterUnaryOperator(MySqlParser.UnaryOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterUnaryOperator()");}
	@Override public void exitUnaryOperator(MySqlParser.UnaryOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitUnaryOperator()");}
	@Override public void enterComparisonOperator(MySqlParser.ComparisonOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterComparisonOperator()");}
	@Override public void exitComparisonOperator(MySqlParser.ComparisonOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitComparisonOperator()");}
	@Override public void enterLogicalOperator(MySqlParser.LogicalOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterLogicalOperator()");}
	@Override public void exitLogicalOperator(MySqlParser.LogicalOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitLogicalOperator()");}
	@Override public void enterBitOperator(MySqlParser.BitOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterBitOperator()");}
	@Override public void exitBitOperator(MySqlParser.BitOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitBitOperator()");}
	@Override public void enterMathOperator(MySqlParser.MathOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterMathOperator()");}
	@Override public void exitMathOperator(MySqlParser.MathOperatorContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitMathOperator()");}
	@Override public void enterCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterCharsetNameBase()");}
	@Override public void exitCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitCharsetNameBase()");}
	@Override public void enterTransactionLevelBase(MySqlParser.TransactionLevelBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterTransactionLevelBase()");}
	@Override public void exitTransactionLevelBase(MySqlParser.TransactionLevelBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitTransactionLevelBase()");}
	@Override public void enterPrivilegesBase(MySqlParser.PrivilegesBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterPrivilegesBase()");}
	@Override public void exitPrivilegesBase(MySqlParser.PrivilegesBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitPrivilegesBase()");}
	@Override public void enterIntervalTypeBase(MySqlParser.IntervalTypeBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterIntervalTypeBase()");}
	@Override public void exitIntervalTypeBase(MySqlParser.IntervalTypeBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.()");}
	@Override public void enterDataTypeBase(MySqlParser.DataTypeBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterDataTypeBase()");}
	@Override public void exitDataTypeBase(MySqlParser.DataTypeBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitDataTypeBase()");}
	@Override public void enterKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.enterKeywordsCanBeId()");}
	@Override public void exitKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitKeywordsCanBeId()");}
	@Override public void enterFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterFunctionNameBase(): " + ctx.getText());
	}
	@Override public void exitFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx) {Log.logQueryParseProgress("AntlrMySQLListener.exitFunctionNameBase()");}
	@Override public void enterEveryRule(ParserRuleContext ctx) {
//		Log.logQueryParseProgress("AntlrMySQLListener.enterEveryRule(): context = " + ctx.getClass()); 
	}
	@Override public void exitEveryRule(ParserRuleContext ctx) {/*Log.logQueryParseProgress("AntlrMySQLListener.exitEveryRule()");*/}
	@Override public void visitTerminal(TerminalNode node) {
 		Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): " + node.getText());
		Object context = node.getParent().getPayload();
 		Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): Parent context = " + context.getClass());
		
		if (context instanceof org.Antlr4MySQLFromANTLRRepo.MySqlParser.SqlStatementsContext) {
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): context = SqlStatementsContext");			
		} else if (context instanceof org.Antlr4MySQLFromANTLRRepo.MySqlParser.SelectColumnElementContext) {
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): context = SelectColumnElementContext");			
		}
		try {
			queryDefinition.getQueryTerminalSymbols().addQueryTerminalSymbol(new QueryTerminalSymbol(node.getText()));
		} catch (Exception ex) {}		// Eat the exception? ToDo check this.
		switch (node.getText().toUpperCase()) {
		case "INSERT":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): found INSERT");
			if (firstVisit == true) {queryDefinition.setQueryType(new QueryTypeInsert()); firstVisit = false;}
			processTerminalNodeInsert(node);
			break;
		case "RENAME":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): found RENAME");
			processTerminalNodeRename(node);
			break;
		case "ALTER":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): found ALTER");
			processTerminalNodeAlter(node);
			break;
		case "SELECT":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): found SELECT");
			if (firstVisit == true) {queryDefinition.setQueryType(new QueryTypeSelect()); firstVisit = false;}
			break;
		case Config.LR_BRACKET:		// Left Paren
			// Adjust the current nesting level
			currentNestingLevel.incrementNestingLevel();
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): Left Paren, Nesting Level is now " + currentNestingLevel.toString());
			lastTerminalNode = Config.LR_BRACKET;
			break;
		case Config.RT_BRACKET:		// Right Paren
			// Adjust the current nesting level
			previousNestingLevel = new NestingLevel(currentNestingLevel);	// Save a copy in case we encounter an AS in the next token
			currentNestingLevel.decrementNestingLevel();
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): Right Paren, Nesting Level is now " + currentNestingLevel.toString());
			lastTerminalNode = Config.RT_BRACKET;
			break;
		case "AS":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): AS alias = " + node.getParent().getChild(2).getText());
			processTerminalNodeAs(node);
			break;
		case "WHERE":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): WHERE found");
			lastTerminalNode = "WHERE";
			break;
		case "ORDERBY":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): ORDERBY found");
			lastTerminalNode = "ORDERBY";
			break;
		case "ORDER":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): ORDER found");
			lastTerminalNode = "ORDER";
			break;
		case "BY":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): BY found");
			lastTerminalNode = "BY";
			break;
		case Config.SQL_STATEMENT_DELIMITER:
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): SQL statement delimiter found: " + Config.SQL_STATEMENT_DELIMITER);
			break;
		case "CREATE":		// We are creating something. 
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): CREATE found, but what are we creating? ");
			processTerminalNodeCreate(node);
			break;
		case "DROP":		// We are dropping something. 
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): DROP found, but what are we dropping? ");
			processTerminalNodeDrop(node);
			break;
		case "JOIN":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): JOIN found");
			break;
		case "LEFT":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): LEFT found");
			break;
		// All the SELECT statement qualifiers
		case "ALL": case "DISTINCT" : case "DISTINCTROW" : case "HIGH_PRIORITY" : case "STRAIGHT_JOIN" : case "SQL_SMALL_RESULT" : 
		case "SQL_BIG_RESULT" : case "SQL_BUFFER_RESULT": case "SQL_CACHE" : case "SQL_NO_CACHE" : case "SQL_CALC_FOUND_ROWS":
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): SELECT statement qualifier: " + node.getText().toUpperCase());
			break;
		default:
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): UNKNOWN symbol: " + node.getText());
			lastTerminalNode = "UNKNOWN";
		}
	}
	/***
	 * The SQL begins with INSERT
	 * @param ctx
	 */
	public void processTerminalNodeInsert(ParseTree ctx) {
	
	}
	/***
	 * The SQL begins with RENAME
	 * @param ctx
	 */
	public void processTerminalNodeRename(ParseTree ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeRename(): RENAME statement qualifier: " + ctx.toString());
		try {
			Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeRename(): ctx.getParent().getChild(1).toString().toUpperCase(): " + ctx.getParent().getChild(1).toString().toUpperCase());
			if (ctx.getParent().getChild(1).toString().toUpperCase().equals("TABLE")) {
				queryDefinition.setQueryType(new QueryTypeRenameTable());
				// Grab up the table name being renamed and add it to the table list
				processRenameTableClauseContext(ctx.getParent().getChild(2));
			}
		} catch (Exception ex) {
			Log.logError("AntlrMySQLListener.processTerminalNodeRename(): " + ex.getLocalizedMessage());
		}
	}
	public void processRenameTableClauseContext(ParseTree ctx) {
		FullIdContext fullIdContext = (FullIdContext)ctx.getChild(0);
		processFullIdContext(fullIdContext);
	}
	@Override public void visitErrorNode(ErrorNode node) {
		Log.logQueryParseProgress("AntlrMySQLListener.visitErrorNode(): " + node.getSymbol());
		Log.logError("AntlrMySQLListener.visitErrorNode(): node.symbol = " + node.getSymbol());
	}
//	@Override public void enterOrderByClauseLabel(MySqlParser.OrderByClauseLabelContext ctx) {
	@Override public void enterOrderByClause(MySqlParser.OrderByClauseContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterOrderByClause(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
		// The third child will be the order by expression
		MySqlParser.OrderByExpressionContext obec = (OrderByExpressionContext) ctx.getChild(2);
	}
//	@Override public void exitOrderByClauseLabel(MySqlParser.OrderByClauseLabelContext ctx) {
	@Override public void exitOrderByClause(OrderByClauseContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitOrderByClause(): " + ctx.getText());
	}
	@Override public void enterTableSourceBase(MySqlParser.TableSourceBaseContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterTableSourceBase(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop());
	}
	/**
	 * This event will have the "LEFT" in "LEFT JOIN" tacked onto it. Yuk
	 */
	@Override public void enterAtomTableItem(MySqlParser.AtomTableItemContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterAtomTableItem(): " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop() + " parent = " + ctx.getParent().getClass());
		String classType = ctx.getChild(0).getClass().toString();
		switch (classType) {
		case "org.Antlr4MySQLFromANTLRRepo.MySqlParser$TableNameContext":
			
			break;
		}
		TableNameContext tableNameContext = (TableNameContext)(ctx.getChild(0));
		//FullIdContext fullIdContext = ctx;		//(FullIdContext)tableNameContext.getChild(0);
		FullTableName fullTableName = new FullTableName("");
		try {
			int childCount = tableNameContext.getChild(0).getChildCount();
			switch (childCount) {
			case 1:
				// Just the table name, no schema. Use the default schema name
				fullTableName.setSchemaName(queryDefinition.getSchemaName());
				fullTableName.setTableName(tableNameContext.getChild(0).getChild(0).getText()); 
				break;
			case 2:
				// Schema followed by table name
				fullTableName.setSchemaName(tableNameContext.getChild(0).getChild(0).getText()); 
				fullTableName.setTableName(tableNameContext.getChild(0).getChild(1).toString()); 
				break;
			case 3:
				// Schema followed by dot followed by table name
				fullTableName.setSchemaName(tableNameContext.getChild(0).getChild(0).getText()); 
				fullTableName.setTableName(tableNameContext.getChild(0).getChild(2).getText()); 
				break;
			default:
				Log.logQueryParseProgress("AntlrMySQLListener.enterAtomTableItem(): too many children found in TableNameContext object (" + tableNameContext.getChildCount() + ")");
			}
			// Now check for the alias
			switch (ctx.getChildCount()) {
			case 1:
				// No alias
				break;
			case 2:
				// Alias with no "AS" keyword
				fullTableName.setAliasName(ctx.getChild(1).getText());
				break;
			case 3:
				// Alias with "AS" keyword
				fullTableName.setAliasName(ctx.getChild(2).getText());
				break;
			default:
				Log.logQueryParseProgress("AntlrMySQLListener.enterAtomTableItem(): too many children found in fullIdContext object (" + ctx.getChildCount() + ")");
				break;
			}
		} catch (Exception ex) {
			Log.logError("AntlrMySQLListener.enterAtomTableItem(): " + ex.getLocalizedMessage());
		}
		fullTableNames.add(fullTableName);
	}
	@Override public void exitAtomTableItem(MySqlParser.AtomTableItemContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitAtomTableItem(): " + ctx.getText());
	}
	@Override public void exitAlterTable(MySqlParser.AlterTableContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitAlterTable(): " + ctx.getText());
	}
	@Override public void enterAlterTablespace(MySqlParser.AlterTablespaceContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitAlterTable(): " + ctx.getText());
	}
	@Override public void exitAlterTablespace(MySqlParser.AlterTablespaceContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitAlterTablespace(): " + ctx.getText());
	}
	@Override public void enterAlterView(MySqlParser.AlterViewContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterAlterView(): " + ctx.getText());
		if (queryDefinition.getQueryType() instanceof QueryTypeUnknown) {
			Log.logQueryParseProgress("AntlrMySQLListener.enterAlterView(): this is an ALTER statement ");
			queryDefinition.setQueryType(new QueryTypeAlterView());
		} else {Log.logQueryParseProgress("AntlrMySQLListener.enterAlterView(): this statement was already defined as " + queryDefinition.getQueryType().toString());}
	}
	@Override public void exitAlterView(MySqlParser.AlterViewContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitAlterView(): " + ctx.getText());
	}
	@Override public void enterDropView(MySqlParser.DropViewContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.enterDropView(): " + ctx.getText());
	}
	@Override public void exitDropView(MySqlParser.DropViewContext ctx) {
		Log.logQueryParseProgress("AntlrMySQLListener.exitDropView(): " + ctx.getText());
	}
	/**
	 * This could be part of a DROP TABLE command or an ALTER TABLE command...
	 * @param node
	 */
	private void processTerminalNodeDrop(TerminalNode node) {
		if (firstVisit == true) {queryDefinition.setQueryType(new QueryTypeDrop()); firstVisit = false;}
		for (int i = 1; i < node.getParent().getChildCount(); i++) {
			String dropType = node.getParent().getChild(i).getText().trim().toUpperCase();
			ParseTree child = null;
			switch (dropType) {
				case "COLUMN": 
					queryDefinition.setQueryType(new QueryTypeDropView());
					Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeDrop(): it's a " + queryDefinition.getQueryType().toString());
					break;
				case "TABLE": 
					// The next child is TablesContext that will hold all the tables to be deleted
					queryDefinition.setQueryType(new QueryTypeDropTable());
					Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeDrop(): it's a " + queryDefinition.getQueryType().toString());
					i = i + 1;
					child = node.getParent().getChild(i);
					processTablesContext(queryDefinition, child, "processTerminalNodeDrop");
					break;
				case "VIEW": 
					// A DROP VIEW parses differently than a DROP TABLE even though they are so similar.
					queryDefinition.setQueryType(new QueryTypeDropView());
					Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeDrop(): it's a " + queryDefinition.getQueryType().toString());
					i = i + 1;
					for (int j = i; j < node.getParent().getChildCount(); j += 2) {
						String mySchemaName = queryDefinition.getSchemaName();
						String myTableName = "";
						StringBuilder s = new StringBuilder();
						StringBuilder t = new StringBuilder();
						child = node.getParent().getChild(j);
						processUidNameContext(s, t, child, "processTerminalNodeDrop");
						mySchemaName = s.toString(); 
						myTableName = t.toString();
						queryDefinition.getQueryTables().addQueryTable(new QueryTable(mySchemaName, myTableName, null, null));
					}
					break;
				default:		// It's an error. We don't know what we're dropping
					Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeDrop(): DROP something is unidentified: " + dropType, true);
			}
		}
	}
	/***
	 * A list of tables separated by commas
	 * @param myQueryDefinition The QueryDefinition object we are populating
	 * @param child The ParseTree with the list of tables, comma separated
	 * @param context A string used in debugging to describe who called this method 
	 */
	private void processTablesContext(QueryDefinition myQueryDefinition, ParseTree child, String context) {
		String mySchemaName = queryDefinition.getSchemaName();
		String myTableName = "";
		StringBuilder s = null;
		StringBuilder t = null;
		for (int j = 0; j < child.getChildCount(); j += 2) {	// Skip the commas
			s = new StringBuilder().append(mySchemaName);
			t = new StringBuilder();
			processTableNameContext(s, t, child.getChild(j), context);
			mySchemaName = s.toString(); 
			myTableName = t.toString();
			myQueryDefinition.getQueryTables().addQueryTable(new QueryTable(mySchemaName, myTableName, null, null));
			Log.logQueryParseProgress("AntlrMySQLListener." + context + ": it's a " + queryDefinition.getQueryType().toString());
		}
	}
	private void processTerminalNodeCreate(TerminalNode node) {
		if (firstVisit == true) {queryDefinition.setQueryType(new QueryTypeCreate()); firstVisit = false;}
		String createType = node.getParent().getChild(1).getText().trim().toUpperCase();
		if (createType.trim().toUpperCase().equals("TEMPORARY")) {createType = node.getParent().getChild(1).getText().trim().toUpperCase();}
		switch (createType) {
			case "TABLE": 
				queryDefinition.setQueryType(new QueryTypeCreateTable());
				Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): it's a " + queryDefinition.getQueryType().toString());
				break;
			case "VIEW": 
				queryDefinition.setQueryType(new QueryTypeCreateView());
				Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): it's a " + queryDefinition.getQueryType().toString());
				break;
			default:		// It's an error. We don't know what we're creating
				Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): CREATE somthing is unidentified: " + createType, true);
		}
	}
	// This is an AS for a column name and for a table/query name. We have to handle each.
	private void processTerminalNodeAs(TerminalNode node) {
/*		System.out.println(node.getParent().getChild(0).getClass().getName());
		Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAs(): context = " + node.getParent().getChild(0).getClass().getName()); 
		ParserRuleContext ctx = null;
		switch (node.getParent().getChild(0).getClass().getName()) {
		case "org.Antlr4MySQLFromANTLRRepo.MySqlParser$FullColumnNameContext":
			// The alias is for a column name
			ctx = (FullColumnNameContext) node.getParent().getChild(0);
			
			break;
		case "org.Antlr4MySQLFromANTLRRepo.MySqlParser$TableNameContext":
			// The alias is for a table name or query name
			ctx = (TableNameContext) node.getParent().getChild(0);
			
			break;
		case "org.Antlr4MySQLFromANTLRRepo.MySqlParser$ParenthesisSelectContext":
			// The alias is for an expression. We don't care. 
			Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAs(): this is an alias for an expression."); 
			
			break;

		} */
		Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAs(): Found \"AS\" ");// after \"" 
//	                              + ctx.start.getText() +"\"" + " NestingLevel = " + currentNestingLevel.toString());
/*		System.out.println(ctx.start.getText());
		try {
		// Add the alias to the last column name we processed. If we are not nested at all
		if (lastTerminalNode.equals(Config.RT_BRACKET)) {
			Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAs(): Found \"AS\" after \"" + Config.RT_BRACKET + "\"" + " NestingLevel = " + currentNestingLevel.toString());
			// Add to aliasAggregate structure that accumulates sets of attributes that are under one alias
			// Get the previous nesting level just before the AS
			// Create a new alias in the compoundAlias structure
			// For each attribute already captured
			//   If the Nestinglevel of the attribute == the previous nesting level
			//		Add the attribute to the collection of attributes for this compoundAlias
			// Neat stuff!
			CompoundAlias compoundAlias = new CompoundAlias(Utils.removeBackQuotes(node.getParent().getChild(2).getText()));
			for (FullColumnName fcn: fullColumnNames) {
//				fcn.processRawData();
				if (fcn.getNestingLevel().isNestedInOrIsEqualTo(previousNestingLevel)) {
					Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAs(): Adding column " + fcn.getRawData() + " to compoundAlias " + node.getParent().getChild(2).getText());
					compoundAlias.addFullColumnName(fcn);
				}
			}
			compoundAliases.addCompoundAlias(compoundAlias);
		} else {
			fullColumnNames.get(fullColumnNames.size()-1).addAliasName(new AliasNameClassOLD(node.getParent().getChild(2).getText().replace("`", "")));
		}
		lastTerminalNode = "AS";
		} catch (Exception ex) {
			Log.logError("AntlrMySQLListener.processTerminalNodeAs(): " + ex.getLocalizedMessage()); 
		}
*/	}
	/***
	 * Extract the class name without the package
	 * @param qualifiedClassName the class name with the package appended to it. A $ is the delimiter
	 * @return The class name
	 */
	private String getClassName(String qualifiedClassName) {
		String className = "";
		try {
			int idx = qualifiedClassName.lastIndexOf("$");
			className = qualifiedClassName.substring(idx + 1);
		} catch (Exception ex) {
			Log.logError("ANTLRMySQLListener.getClassName(): " + ex.getLocalizedMessage());
		}
		return className;
	}
	/**
	 * A Terminal Node has been identified as "ALTER"
	 * @param node
	 */
	private void processTerminalNodeAlter(TerminalNode node) {
		if (firstVisit == true) {
			queryDefinition.setQueryType(new QueryTypeAlter()); 
			firstVisit = false;
			String alterType = node.getParent().getChild(1).getText().trim().toUpperCase();
			if (alterType.trim().toUpperCase().equals("TEMPORARY")) {alterType = node.getParent().getChild(1).getText().trim().toUpperCase();}
			String className = "";
			String mySchemaName = queryDefinition.getSchemaName();	// In case we don't have a schema name in the SQL we are processing
			String myTableName = "";
			String innerClassName = "";
			switch (alterType) {
			case "TABLE": 
				queryDefinition.setQueryType(new QueryTypeAlterTable());
				Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): it's a " + queryDefinition.getQueryType().toString());
				for (int i = 2; i < node.getParent().getChildCount(); i++) {
					className = getClassName(node.getParent().getChild(i).getClass().getName());
//					System.out.println(className);
					switch (className) {
					case "AlterByRenameContext":
						queryDefinition.setQueryType(new QueryTypeRenameTable()); 
						// We are renaming a table. We  have the table name being renamed because we processed the TableNameContext case already.
						// For our purposes we don't care what the new name is anyway
						Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): renaming a table");
						break;
					case "TableNameContext":
						StringBuilder s = new StringBuilder().append(mySchemaName);
						StringBuilder t = new StringBuilder();
						processTableNameContext(s, t, node.getParent().getChild(i), "processTerminalNodeAlter");
						mySchemaName = s.toString(); 
						myTableName = t.toString();
						queryDefinition.getQueryTables().addQueryTable(new QueryTable(mySchemaName, myTableName, null, null));
						break;
					case "AlterByDropColumnContext":
						Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): dropping a column");
						innerClassName = "";
						// Look for UIdContext class
						for (int j = 0; j < node.getParent().getChild(i).getChildCount(); j++) {
							innerClassName = getClassName(node.getParent().getChild(i).getChild(j).getClass().getName());
							switch (innerClassName) {
							case "UidContext":
								String attribute = "";
								attribute = node.getParent().getChild(i).getChild(j).getText();
								Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): column to drop = " + attribute);
								queryDefinition.getQueryAttributes().addAttribute(new QueryAttribute(mySchemaName, myTableName, attribute, (AliasNameClassOLD)null, (QueryClause)null, "", QueryAttribute.ATTRIBUTE_DISPOSITION.Drop));
								break;
							}							
						}
						break;
					case "AlterByChangeColumnContext":
						Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): altering a column");
						innerClassName = "";
						boolean keepGoing;
						keepGoing = true;
						// Look for UIdContext class
						for (int j = 0; keepGoing && j < node.getParent().getChild(i).getChildCount(); j++) {
							innerClassName = getClassName(node.getParent().getChild(i).getChild(j).getClass().getName());
							switch (innerClassName) {
							case "UidContext":
								String attribute = "";
								attribute = node.getParent().getChild(i).getChild(j).getText();
								Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): column to alter = " + attribute);
								queryDefinition.getQueryAttributes().addAttribute(new QueryAttribute(mySchemaName, myTableName, attribute, (AliasNameClassOLD)null, (QueryClause)null, "", QueryAttribute.ATTRIBUTE_DISPOSITION.Alter));
								keepGoing = false;	// We only want the first attribute. The second one is the new name. 
								break;
							}							
						}
						break;
					case "AlterByAddColumnContext":
						Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): adding a column");
						break;
					default:
					}
				}
				break;
			case "VIEW": 
				queryDefinition.setQueryType(new QueryTypeAlterView());
				Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): it's a " + queryDefinition.getQueryType().toString());
				break;
			default:		// It's an error. We don't know what we're creating
				Log.logQueryParseProgress("AntlrMySQLListener.processTerminalNodeAlter(): ALTER somthing is unidentified: " + alterType, true);
			}
			// Now process the table attributes that are being changed
			
		}
	}
	private void processUidNameContext(StringBuilder mySchemaName, StringBuilder myTableName, ParseTree child, String caller)
	{
//		String myClass = node.getParent().getChild(i).getChild(0).getClass().getName();
		switch (child.getChildCount()) {
		case 1:
			// Just a table name
			myTableName.append(child.getChild(0).getText());
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): table name = " + myTableName + ", schema name defaulted to " + mySchemaName);
			break;
		case 3:
			// A schema name and a table name with a dot in between
			mySchemaName.setLength(0);
			mySchemaName.append(child.getChild(0).getText());
			myTableName.setLength(0);
			myTableName.append(child.getChild(2).getText());
			Log.logQueryParseProgress("AntlrMySQLListener." + caller + ": table name = " + myTableName + ", schema name = " + mySchemaName);
			break;
		default:
			Log.logError("ANTLRMySQLListener." + caller + "(): unexpected number of child nodes (" + child.getChildCount() + ")");
			break;
		}
	}
	private void processTableNameContext(StringBuilder mySchemaName, StringBuilder myTableName, ParseTree child, String caller)
	{
//		String myClass = node.getParent().getChild(i).getChild(0).getClass().getName();
		switch (child.getChild(0).getChildCount()) {
		case 1:
			// Just a table name
			myTableName.append(child.getChild(0).getChild(0).getText());
			Log.logQueryParseProgress("AntlrMySQLListener.visitTerminal(): table name = " + myTableName + ", schema name defaulted to " + mySchemaName);
			break;
		case 2:
			// A schema name and another element that is a table name prefaced by a dot. Hmm...
			mySchemaName.setLength(0);
			mySchemaName.append(child.getChild(0).getChild(0).getText());
			myTableName.setLength(0);
			myTableName.append(child.getChild(0).getChild(1).getText().substring(1));
			break;
		case 3:
			// A schema name and a table name with a dot in between
			mySchemaName.setLength(0);
			mySchemaName.append(child.getChild(0).getChild(0).getText());
			myTableName.setLength(0);
			myTableName.append(child.getChild(0).getChild(2).getText());
			Log.logQueryParseProgress("AntlrMySQLListener." + caller + ": table name = " + myTableName + ", schema name = " + mySchemaName);
			break;
		default:
			Log.logError("ANTLRMySQLListener." + caller + "(): unexpected number of child nodes (" + child.getChild(0).getChildCount() + ")");
			break;
		}
	}

	private void processFullColumnNameContext(MySqlParser.FullColumnNameContext ctx){
		String foo = " " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop();
		// Add to the list of columns that are referenced in this query
		//String start = ctx.getStart().getText();
		String stop = ctx.getStop().getText();
		FullColumnName fullColumnName = null;
		switch (ctx.children.size()) {
		case 1:	
			// Just an attribute Name
			fullColumnName = new FullColumnName("", "", ctx.children.get(0).getText());
			break;
		case 2:
			// An Attribute Name and a table/query name/alias
			fullColumnName = new FullColumnName("", 
												ctx.children.get(0).getText(),  
					                            ctx.children.get(1).getText());
			break;
		case 3:
			// An Attribute Name, a table/query name/alias, and a schema name
			fullColumnName = new FullColumnName(ctx.children.get(0).getText(),  
												ctx.children.get(1).getText(), 
												ctx.children.get(2).getText());
			break;
		default: 
			Log.logError("processFullColumnNameContext(): unrecognized number of children (" + ctx.children.size() +")");
			break;
		}
		fullColumnName.addAliasName(new AliasNameClassOLD(currentAlias));
		fullColumnNames.addFullColumnName(fullColumnName);
		
	}
	private void processExitSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx) {
		clearCurrentAlias();
	}
	private void processSelectColumnElementContext(MySqlParser.SelectColumnElementContext ctx) {
//		String foo = " " + ctx.getText() + ", start = " + ctx.getStart() + " stop = " + ctx.getStop();
		// Add to the list of columns that are referenced in this query
		//String start = ctx.getStart().getText();
//		String stop = ctx.getStop().getText();
		MySqlParser.FullColumnNameContext fullColumnNameContext = (FullColumnNameContext) ctx.children.get(0);
		FullColumnName fullColumnName = null;
		switch (fullColumnNameContext.children.size()) {
		case 1:	
			// Just an attribute Name
			fullColumnName = new FullColumnName("", "", fullColumnNameContext.children.get(0).getText());
			break;
		case 2:
			// An Attribute Name and a table/query name/alias
			fullColumnName = new FullColumnName("", 
												fullColumnNameContext.children.get(0).getText(),  
					                            fullColumnNameContext.children.get(1).getText());
			break;
		case 3:
			// An Attribute Name, a table/query name/alias, and a schema name
			fullColumnName = new FullColumnName(fullColumnNameContext.children.get(0).getText(),  
												fullColumnNameContext.children.get(1).getText(), 
												fullColumnNameContext.children.get(2).getText());
			break;
		}
		if (ctx.children.size() > 1) {
			// There is an alias. We want it. Now.
			MySqlParser.UidContext alias = (UidContext) ctx.children.get(ctx.children.size()-1);
			fullColumnName.addAliasName(new AliasNameClassOLD(alias.getStop().getText()));
		}
		fullColumnName.addAliasName(new AliasNameClassOLD(currentAlias));
		
		fullColumnNames.addFullColumnName(fullColumnName);
	}
	private void clearCurrentAlias() {
		currentAlias = "";
	}
	private void processEnterSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx) {
		String expr = buildParseRuleContext(ctx);
		Log.logProgress("AntlrMySQLListener.processEnterSelectFunctionElement(): " + expr);
		// Figure out what the alias is, if any
		ParseTree aliasMaybe = ctx.getChild(ctx.getChildCount()-1);  
		currentAlias = checkForAlias(aliasMaybe);
	}
	private String buildParseRuleContext(RuleContext rc) {
		String expr = "";
		for (int i = 0; i < rc.getChildCount(); i++) {
			Object o;
			o = rc.getChild(i);
			if (o instanceof org.antlr.v4.runtime.RuleContext) {
				expr += " " + buildParseRuleContext((org.antlr.v4.runtime.RuleContext)o);
			} else {
				expr += " " + o.toString().trim();
			}
		}
		return expr;
	}
	private void processEnterSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx) {
		// Figure out what the alias is, if any
		ParseTree aliasMaybe = ctx.getChild(ctx.getChildCount()-1);  
		currentAlias = checkForAlias(aliasMaybe);
	}
	private String checkForAlias(ParseTree alias) {
		String aliasName = "";
		try {
		if (alias instanceof MySqlParser.UidContext) {
			//	It's an alias
			aliasName = alias.getChild(alias.getChildCount()-1).getText();
			Log.logQueryParseProgress("checkForAlias(): found alias = #" + aliasName + "#");
		} else {
			aliasName = "";	// There is no alias
			Log.logQueryParseProgress("checkForAlias(): No alias found");
		}
		} catch (Exception ex) {
			Log.logQueryParseProgress("checkForAlias(): ERROR: " + ex.getLocalizedMessage());
			Log.logError("checkForAlias(): " + ex.getLocalizedMessage());
		}
		return aliasName;
	}
	private void processExitSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx) {
		clearCurrentAlias();
	}
	/************************************************************************************************
	 * Static (internal) classes follow
	 ************************************************************************************************/
	class FullTableName {
		String schemaName;
		String tableName;
		String aliasName;
		String rawData;	// {schema.}table {alias}
		/**
		 * Constructor
		 * @param rawData -> The {schema.}table {alias} data extracted from the SQL statement. Note the alias!
		 */
		FullTableName(String rawData) {
			init();
			this.rawData = rawData;
		}
		FullTableName(String schemaName, String tableName,  String alias) {
			init();
			setSchemaName(schemaName);
			setTableName(tableName);
			setAliasName(aliasName);
		}
		void setSchemaName(String schemaName) {this.schemaName = cleanUpArtifactName(schemaName);}
		void setTableName(String tableName) {this.tableName = cleanUpArtifactName(tableName);}
		void setAliasName(String aliasName) {this.aliasName = cleanUpArtifactName(aliasName);}
		public String toString() {
			return (schemaName.length() > 0? schemaName + ".": "") + 
					tableName 
					+ (aliasName.length() > 0 ? " AS " + aliasName:"");
		}
		private String cleanUpArtifactName(String artifactName) {
			String tmp = artifactName.trim();
			if (tmp.length() != 0) {
				if (tmp.trim().startsWith("`")) {
					tmp = tmp.substring(1, artifactName.length() - 1);
				}
				if (tmp.startsWith(".")) {
					tmp = tmp.substring(1);
				}
				tmp = Utils.wrapInDelimiter(tmp.trim(), "`");
			}
			return tmp;
		}
		void init() {
			schemaName = "";
			tableName = "";
			aliasName = "";
			rawData = "";
		}
		/**
		 * Figure out exactly what is in the SQL that we have in our clutches.
		 * Beware: A table name and an attribute name can have a . (period in it) Yikes.
		 */
		private void processRawData() {
			ArrayList<String> data = new ArrayList<String>();
			// Does it have two consecutive `` delimiters? If so, there's an alias
			String s[] = rawData.split("``");
			String s1[];
			if (s.length > 1) {
				// There is an alias
				aliasName = Utils.removeBackQuotes(s[1]);
				s1 = s[0].split("`");		// Can't split on a . because an attribute and a table can have a . in the name. Can't have a ` in the name, however
			} else {
				// There is no alias
				s1 = rawData.split("`");
			}
			for (String ss: s1) {
				int len = ss.trim().length();
				boolean isDot = ss.trim().equals(".");
				if (len > 0 && !isDot) {
					data.add(ss);
				}
			}
			switch (data.size()) {
				case 1:			// No schema name
					schemaName = "";
					tableName = Utils.removeBackQuotes(data.get(0)).trim();
					break;
				case 2:			// Schema and table name
					schemaName = Utils.removeBackQuotes(data.get(0)).trim();
					tableName = Utils.removeBackQuotes(data.get(1)).trim();
					break;
			}
		}
		public void copyIntoQueryDefinition(QueryDefinition queryDefinition) {
			queryDefinition.getQueryTables().addQueryTable(new QueryTable(schemaName, tableName, new AliasNameClassOLD(aliasName), queryClause));
		}
	}
}
