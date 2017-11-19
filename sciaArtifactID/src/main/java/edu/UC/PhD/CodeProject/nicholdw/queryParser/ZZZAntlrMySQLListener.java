package edu.UC.PhD.CodeProject.nicholdw.queryParser;

import java.util.Objects;

import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepo;
import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepo.Column_name_aliasContext;
import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepo.Table_aliasContext;
import org.Antlr4MySQLFromANTLRRepo.MySQLParserFromANTLRRepoBaseListener;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class ZZZAntlrMySQLListener extends MySQLParserFromANTLRRepoBaseListener {

	final String[] columnNameAndAlias = new String[]{null, null, null};	// (Table, Attribute, Alias)
	boolean includeAllFields;
	/*
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println(ctx.getText());
    }
	 */
	/*
    @Override
    public void enterSelect_clause(MySQLParser.Select_clauseContext  ctx) {
        System.out.println("enterSelect_clause: " + ctx.getText() + ":" );
    }
	 */
	@Override
	public void enterColumn_name(MySQLParserFromANTLRRepo.Column_nameContext  ctx) {
		System.out.println("enterColumn_name: " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
	}
	@Override
	public void enterColumn_name_alias(MySQLParserFromANTLRRepo.Column_name_aliasContext  ctx) {
		System.out.println("enterColumn_name_alias: " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
	}
	@Override
	public void exitColumn_name_alias(MySQLParserFromANTLRRepo.Column_name_aliasContext ctx) {
		System.out.println("exitColumn_name_alias: " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
		columnNameAndAlias[2] = ctx.getText();
	}
	@Override
	public void exitColumn_name(MySQLParserFromANTLRRepo.Column_nameContext ctx) {
        System.out.println("exitColumn_name: " + ctx.getText() + ": ctx.ID() = " + ctx.ID().toString());
        if (ctx.ID() == null) {
            if (Objects.equals(ctx.getText(), "*")) {
        		columnNameAndAlias[1] = "*";
            }
            super.exitColumn_name(ctx);
            return;
          }
		if (ctx.ID() == null) {
			if (Objects.equals(ctx.getText(), "*")) {
				includeAllFields = true;
			}
			super.exitColumn_name(ctx);
			return;
		}
		columnNameAndAlias[1] = ctx.ID().toString();
		System.out.print("exitColumn_name(): " ); printInfo();
		clearInfo();
	}
	public void enterTable_name(MySQLParserFromANTLRRepo.Table_nameContext ctx) {
		System.out.println("enterTable_name: " + ctx.getText());
	}
	public void exitTable_name(MySQLParserFromANTLRRepo.Table_nameContext ctx) {
		System.out.println("exitTable_name: " + ctx.getText());
	}
	public void enterTable_alias(Table_aliasContext ctx) {
		System.out.println("enterTable_alias: " + ctx.getText());
	}

	public void exitTable_alias(Table_aliasContext ctx) {
		System.out.println("exitTable_alias: " + ctx.getText());
	}
	void clearInfo() {
		for (int i = 0; i < columnNameAndAlias.length; i++) {
			columnNameAndAlias[i]  = null;
		}
	}
	void printInfo() {
		String comma = "";
		for (int i = 0; i < columnNameAndAlias.length; i++) {
			System.out.print(comma + columnNameAndAlias[i]);
			comma = ", ";
		}
		System.out.println();
	}
}
