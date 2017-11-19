// Generated from MySQLParserFromANTLRRepo.g4 by ANTLR 4.4
package org.Antlr4MySQLFromANTLRRepo;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MySQLParserFromANTLRRepo extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ALL=12, JOIN=50, BETWEEN=18, FROM=2, MINUS=21, INDEX=53, PARTITION=61,
		ID=68, INNER=48, NOT_EQ=40, NULL=8, LEFT=64, IN=10, NEWLINE=70, LPAREN=32,
		DOT=46, FOR=58, TRUE=14, POWER_OP=25, IS=7, SHIFT_LEFT=27, RPAREN=31,
		WHERE=3, EQ=37, USING=52, RBRACK=33, NOT=41, AS=73, LIKE=9, LBRACK=34,
		AND=4, GET=43, XOR=6, HAVING=74, KEY=54, PLUS=20, LTH=38, USER_VAR=72,
		BINARY=26, SEMI=44, ANY=13, IGNORE=60, INT=69, DIVIDE=16, ORDER=55, GROUP=56,
		NATURAL=63, REGEXP=19, BY=57, STRAIGHT_JOIN=62, RIGHT=65, OUTER=49, OJ=66,
		WS=71, ON=67, COMMA=45, BITAND=24, OR=5, MOD=17, VERTBAR=23, EXISTS=11,
		COLLATE=47, USE=59, NEGATION=22, ASTERISK=30, COLON=35, SELECT=1, SHIFT_RIGHT=28,
		ESCAPE=29, ALL_FIELDS=36, CROSS=51, FALSE=15, GTH=39, LET=42;
	public static final String[] tokenNames = {
		"<INVALID>", "'select'", "'from'", "'where'", "AND", "OR", "'xor'", "'is'",
		"'null'", "'like'", "'in'", "'exists'", "'all'", "'any'", "'true'", "'false'",
		"DIVIDE", "MOD", "'between'", "'regexp'", "'+'", "'-'", "'~'", "'|'",
		"'&'", "'^'", "'binary'", "'<<'", "'>>'", "'escape'", "'*'", "')'", "'('",
		"']'", "'['", "':'", "'.*'", "'='", "'<'", "'>'", "'!='", "'not'", "'<='",
		"'>='", "';'", "','", "'.'", "'collate'", "'inner'", "'outer'", "'join'",
		"'cross'", "'using'", "'index'", "'key'", "'order'", "'group'", "'by'",
		"'for'", "'use'", "'ignore'", "'partition'", "'straight_join'", "'natural'",
		"'left'", "'right'", "'oj'", "'on'", "ID", "INT", "NEWLINE", "WS", "USER_VAR",
		"'as'", "'having'"
	};
	public static final int
		RULE_stat = 0, RULE_schema_name = 1, RULE_select_clause = 2, RULE_table_name = 3,
		RULE_table_alias = 4, RULE_column_name = 5, RULE_column_name_alias = 6,
		RULE_index_name = 7, RULE_column_list = 8, RULE_column_list_clause = 9,
		RULE_from_clause = 10, RULE_select_key = 11, RULE_where_clause = 12, RULE_expression = 13,
		RULE_element = 14, RULE_right_element = 15, RULE_left_element = 16, RULE_target_element = 17,
		RULE_relational_op = 18, RULE_expr_op = 19, RULE_between_op = 20, RULE_is_or_is_not = 21,
		RULE_simple_expression = 22, RULE_table_references = 23, RULE_table_reference = 24,
		RULE_table_factor1 = 25, RULE_table_factor2 = 26, RULE_table_factor3 = 27,
		RULE_table_factor4 = 28, RULE_table_atom = 29, RULE_join_clause = 30,
		RULE_join_condition = 31, RULE_index_hint_list = 32, RULE_index_options = 33,
		RULE_index_hint = 34, RULE_index_list = 35, RULE_partition_clause = 36,
		RULE_partition_names = 37, RULE_partition_name = 38, RULE_subquery_alias = 39,
		RULE_subquery = 40;
	public static final String[] ruleNames = {
		"stat", "schema_name", "select_clause", "table_name", "table_alias", "column_name",
		"column_name_alias", "index_name", "column_list", "column_list_clause",
		"from_clause", "select_key", "where_clause", "expression", "element",
		"right_element", "left_element", "target_element", "relational_op", "expr_op",
		"between_op", "is_or_is_not", "simple_expression", "table_references",
		"table_reference", "table_factor1", "table_factor2", "table_factor3",
		"table_factor4", "table_atom", "join_clause", "join_condition", "index_hint_list",
		"index_options", "index_hint", "index_list", "partition_clause", "partition_names",
		"partition_name", "subquery_alias", "subquery"
	};

	@Override
	public String getGrammarFileName() { return "MySQLParserFromANTLRRepo.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MySQLParserFromANTLRRepo(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StatContext extends ParserRuleContext {
		public Select_clauseContext select_clause(int i) {
			return getRuleContext(Select_clauseContext.class,i);
		}
		public List<Select_clauseContext> select_clause() {
			return getRuleContexts(Select_clauseContext.class);
		}
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitStat(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_stat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(82); select_clause();
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SELECT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Schema_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Schema_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_schema_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterSchema_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitSchema_name(this);
		}
	}

	public final Schema_nameContext schema_name() throws RecognitionException {
		Schema_nameContext _localctx = new Schema_nameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_schema_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_clauseContext extends ParserRuleContext {
		public Table_referencesContext table_references() {
			return getRuleContext(Table_referencesContext.class,0);
		}
		public Column_list_clauseContext column_list_clause() {
			return getRuleContext(Column_list_clauseContext.class,0);
		}
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public TerminalNode FROM() { return getToken(MySQLParserFromANTLRRepo.FROM, 0); }
		public TerminalNode SELECT() { return getToken(MySQLParserFromANTLRRepo.SELECT, 0); }
		public Select_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterSelect_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitSelect_clause(this);
		}
	}

	public final Select_clauseContext select_clause() throws RecognitionException {
		Select_clauseContext _localctx = new Select_clauseContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_select_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); match(SELECT);
			setState(90); column_list_clause();
			setState(93);
			_la = _input.LA(1);
			if (_la==FROM) {
				{
				setState(91); match(FROM);
				setState(92); table_references();
				}
			}

			setState(96);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(95); where_clause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Table_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_name(this);
		}
	}

	public final Table_nameContext table_name() throws RecognitionException {
		Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_table_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_aliasContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Table_aliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_alias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_alias(this);
		}
	}

	public final Table_aliasContext table_alias() throws RecognitionException {
		Table_aliasContext _localctx = new Table_aliasContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_table_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_nameContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(MySQLParserFromANTLRRepo.ID); }
		public List<TerminalNode> DOT() { return getTokens(MySQLParserFromANTLRRepo.DOT); }
		public Column_name_aliasContext column_name_alias() {
			return getRuleContext(Column_name_aliasContext.class,0);
		}
		public TerminalNode ID(int i) {
			return getToken(MySQLParserFromANTLRRepo.ID, i);
		}
		public TerminalNode USER_VAR() { return getToken(MySQLParserFromANTLRRepo.USER_VAR, 0); }
		public Schema_nameContext schema_name() {
			return getRuleContext(Schema_nameContext.class,0);
		}
		public Table_aliasContext table_alias() {
			return getRuleContext(Table_aliasContext.class,0);
		}
		public TerminalNode DOT(int i) {
			return getToken(MySQLParserFromANTLRRepo.DOT, i);
		}
		public TerminalNode AS() { return getToken(MySQLParserFromANTLRRepo.AS, 0); }
		public Column_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterColumn_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitColumn_name(this);
		}
	}

	public final Column_nameContext column_name() throws RecognitionException {
		Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_column_name);
		int _la;
		try {
			setState(157);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(109);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(105);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						setState(102); schema_name();
						setState(103); match(DOT);
						}
						break;
					}
					setState(107); match(ID);
					setState(108); match(DOT);
					}
					break;
				}
				setState(111); match(ID);
				setState(114);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(112); match(AS);
					setState(113); column_name_alias();
					}
				}

				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(119);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(116); table_alias();
					setState(117); match(DOT);
					}
					break;
				}
				setState(121); match(ID);
				setState(124);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(122); match(AS);
					setState(123); column_name_alias();
					}
				}

				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(126); match(USER_VAR);
				setState(129);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(127); match(AS);
					setState(128); column_name_alias();
					}
				}

				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(138);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(134);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						setState(131); schema_name();
						setState(132); match(DOT);
						}
						break;
					}
					setState(136); match(ID);
					setState(137); match(DOT);
					}
					break;
				}
				setState(140); match(ID);
				setState(142);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(141); column_name_alias();
					}
				}

				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				{
				setState(147);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(144); table_alias();
					setState(145); match(DOT);
					}
					break;
				}
				setState(149); match(ID);
				setState(151);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(150); column_name_alias();
					}
				}

				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				{
				setState(153); match(USER_VAR);
				setState(155);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(154); column_name_alias();
					}
				}

				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_name_aliasContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Column_name_aliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_name_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterColumn_name_alias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitColumn_name_alias(this);
		}
	}

	public final Column_name_aliasContext column_name_alias() throws RecognitionException {
		Column_name_aliasContext _localctx = new Column_name_aliasContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_column_name_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Index_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Index_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterIndex_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitIndex_name(this);
		}
	}

	public final Index_nameContext index_name() throws RecognitionException {
		Index_nameContext _localctx = new Index_nameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_index_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_listContext extends ParserRuleContext {
		public Column_nameContext column_name(int i) {
			return getRuleContext(Column_nameContext.class,i);
		}
		public List<Column_nameContext> column_name() {
			return getRuleContexts(Column_nameContext.class);
		}
		public TerminalNode LPAREN() { return getToken(MySQLParserFromANTLRRepo.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public TerminalNode RPAREN() { return getToken(MySQLParserFromANTLRRepo.RPAREN, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public Column_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterColumn_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitColumn_list(this);
		}
	}

	public final Column_listContext column_list() throws RecognitionException {
		Column_listContext _localctx = new Column_listContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_column_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163); match(LPAREN);
			setState(164); column_name();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(165); match(COMMA);
				setState(166); column_name();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(172); match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_list_clauseContext extends ParserRuleContext {
		public Column_nameContext column_name(int i) {
			return getRuleContext(Column_nameContext.class,i);
		}
		public List<Column_nameContext> column_name() {
			return getRuleContexts(Column_nameContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public Column_list_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_list_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterColumn_list_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitColumn_list_clause(this);
		}
	}

	public final Column_list_clauseContext column_list_clause() throws RecognitionException {
		Column_list_clauseContext _localctx = new Column_list_clauseContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_column_list_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174); column_name();
			setState(179);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(175); match(COMMA);
				setState(176); column_name();
				}
				}
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class From_clauseContext extends ParserRuleContext {
		public List<Table_nameContext> table_name() {
			return getRuleContexts(Table_nameContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public TerminalNode FROM() { return getToken(MySQLParserFromANTLRRepo.FROM, 0); }
		public Table_nameContext table_name(int i) {
			return getRuleContext(Table_nameContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public From_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_from_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterFrom_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitFrom_clause(this);
		}
	}

	public final From_clauseContext from_clause() throws RecognitionException {
		From_clauseContext _localctx = new From_clauseContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_from_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182); match(FROM);
			setState(183); table_name();
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(184); match(COMMA);
				setState(185); table_name();
				}
				}
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_keyContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(MySQLParserFromANTLRRepo.SELECT, 0); }
		public Select_keyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_key; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterSelect_key(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitSelect_key(this);
		}
	}

	public final Select_keyContext select_key() throws RecognitionException {
		Select_keyContext _localctx = new Select_keyContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_select_key);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191); match(SELECT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Where_clauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(MySQLParserFromANTLRRepo.WHERE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Where_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_where_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterWhere_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitWhere_clause(this);
		}
	}

	public final Where_clauseContext where_clause() throws RecognitionException {
		Where_clauseContext _localctx = new Where_clauseContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_where_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193); match(WHERE);
			setState(194); expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<Expr_opContext> expr_op() {
			return getRuleContexts(Expr_opContext.class);
		}
		public Expr_opContext expr_op(int i) {
			return getRuleContext(Expr_opContext.class,i);
		}
		public List<Simple_expressionContext> simple_expression() {
			return getRuleContexts(Simple_expressionContext.class);
		}
		public Simple_expressionContext simple_expression(int i) {
			return getRuleContext(Simple_expressionContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(196); simple_expression();
			setState(202);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(197); expr_op();
					setState(198); simple_expression();
					}
					}
				}
				setState(204);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public TerminalNode USER_VAR() { return getToken(MySQLParserFromANTLRRepo.USER_VAR, 0); }
		public TerminalNode INT() { return getToken(MySQLParserFromANTLRRepo.INT, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitElement(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_element);
		try {
			setState(212);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205); match(USER_VAR);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(206); match(ID);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(207); match(VERTBAR);
				setState(208); match(ID);
				setState(209); match(VERTBAR);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(210); match(INT);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(211); column_name();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Right_elementContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public Right_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_right_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterRight_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitRight_element(this);
		}
	}

	public final Right_elementContext right_element() throws RecognitionException {
		Right_elementContext _localctx = new Right_elementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_right_element);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214); element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Left_elementContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public Left_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_left_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterLeft_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitLeft_element(this);
		}
	}

	public final Left_elementContext left_element() throws RecognitionException {
		Left_elementContext _localctx = new Left_elementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_left_element);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216); element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Target_elementContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public Target_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_target_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTarget_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTarget_element(this);
		}
	}

	public final Target_elementContext target_element() throws RecognitionException {
		Target_elementContext _localctx = new Target_elementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_target_element);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218); element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Relational_opContext extends ParserRuleContext {
		public TerminalNode GTH() { return getToken(MySQLParserFromANTLRRepo.GTH, 0); }
		public TerminalNode LTH() { return getToken(MySQLParserFromANTLRRepo.LTH, 0); }
		public TerminalNode NOT_EQ() { return getToken(MySQLParserFromANTLRRepo.NOT_EQ, 0); }
		public TerminalNode EQ() { return getToken(MySQLParserFromANTLRRepo.EQ, 0); }
		public TerminalNode LET() { return getToken(MySQLParserFromANTLRRepo.LET, 0); }
		public TerminalNode GET() { return getToken(MySQLParserFromANTLRRepo.GET, 0); }
		public Relational_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterRelational_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitRelational_op(this);
		}
	}

	public final Relational_opContext relational_op() throws RecognitionException {
		Relational_opContext _localctx = new Relational_opContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_relational_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << LTH) | (1L << GTH) | (1L << NOT_EQ) | (1L << LET) | (1L << GET))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr_opContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(MySQLParserFromANTLRRepo.NOT, 0); }
		public TerminalNode XOR() { return getToken(MySQLParserFromANTLRRepo.XOR, 0); }
		public TerminalNode AND() { return getToken(MySQLParserFromANTLRRepo.AND, 0); }
		public TerminalNode OR() { return getToken(MySQLParserFromANTLRRepo.OR, 0); }
		public Expr_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterExpr_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitExpr_op(this);
		}
	}

	public final Expr_opContext expr_op() throws RecognitionException {
		Expr_opContext _localctx = new Expr_opContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expr_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Between_opContext extends ParserRuleContext {
		public TerminalNode BETWEEN() { return getToken(MySQLParserFromANTLRRepo.BETWEEN, 0); }
		public Between_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_between_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterBetween_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitBetween_op(this);
		}
	}

	public final Between_opContext between_op() throws RecognitionException {
		Between_opContext _localctx = new Between_opContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_between_op);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224); match(BETWEEN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Is_or_is_notContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(MySQLParserFromANTLRRepo.NOT, 0); }
		public TerminalNode IS() { return getToken(MySQLParserFromANTLRRepo.IS, 0); }
		public Is_or_is_notContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_is_or_is_not; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterIs_or_is_not(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitIs_or_is_not(this);
		}
	}

	public final Is_or_is_notContext is_or_is_not() throws RecognitionException {
		Is_or_is_notContext _localctx = new Is_or_is_notContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_is_or_is_not);
		try {
			setState(229);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(226); match(IS);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(227); match(IS);
				setState(228); match(NOT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Simple_expressionContext extends ParserRuleContext {
		public Is_or_is_notContext is_or_is_not() {
			return getRuleContext(Is_or_is_notContext.class,0);
		}
		public TerminalNode NULL() { return getToken(MySQLParserFromANTLRRepo.NULL, 0); }
		public Relational_opContext relational_op() {
			return getRuleContext(Relational_opContext.class,0);
		}
		public Between_opContext between_op() {
			return getRuleContext(Between_opContext.class,0);
		}
		public Right_elementContext right_element() {
			return getRuleContext(Right_elementContext.class,0);
		}
		public TerminalNode AND() { return getToken(MySQLParserFromANTLRRepo.AND, 0); }
		public Left_elementContext left_element() {
			return getRuleContext(Left_elementContext.class,0);
		}
		public Target_elementContext target_element() {
			return getRuleContext(Target_elementContext.class,0);
		}
		public Simple_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterSimple_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitSimple_expression(this);
		}
	}

	public final Simple_expressionContext simple_expression() throws RecognitionException {
		Simple_expressionContext _localctx = new Simple_expressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_simple_expression);
		try {
			setState(245);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(231); left_element();
				setState(232); relational_op();
				setState(233); right_element();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(235); target_element();
				setState(236); between_op();
				setState(237); left_element();
				setState(238); match(AND);
				setState(239); right_element();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(241); target_element();
				setState(242); is_or_is_not();
				setState(243); match(NULL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_referencesContext extends ParserRuleContext {
		public Table_referenceContext table_reference(int i) {
			return getRuleContext(Table_referenceContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public List<Join_clauseContext> join_clause() {
			return getRuleContexts(Join_clauseContext.class);
		}
		public List<Table_referenceContext> table_reference() {
			return getRuleContexts(Table_referenceContext.class);
		}
		public Join_clauseContext join_clause(int i) {
			return getRuleContext(Join_clauseContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public Table_referencesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_references; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_references(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_references(this);
		}
	}

	public final Table_referencesContext table_references() throws RecognitionException {
		Table_referencesContext _localctx = new Table_referencesContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_table_references);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247); table_reference();
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 45)) & ~0x3f) == 0 && ((1L << (_la - 45)) & ((1L << (COMMA - 45)) | (1L << (INNER - 45)) | (1L << (JOIN - 45)) | (1L << (CROSS - 45)) | (1L << (STRAIGHT_JOIN - 45)) | (1L << (NATURAL - 45)) | (1L << (LEFT - 45)) | (1L << (RIGHT - 45)))) != 0)) {
				{
				setState(251);
				switch (_input.LA(1)) {
				case COMMA:
					{
					{
					setState(248); match(COMMA);
					setState(249); table_reference();
					}
					}
					break;
				case INNER:
				case JOIN:
				case CROSS:
				case STRAIGHT_JOIN:
				case NATURAL:
				case LEFT:
				case RIGHT:
					{
					setState(250); join_clause();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_referenceContext extends ParserRuleContext {
		public Table_factor1Context table_factor1() {
			return getRuleContext(Table_factor1Context.class,0);
		}
		public Table_atomContext table_atom() {
			return getRuleContext(Table_atomContext.class,0);
		}
		public Table_referenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_reference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_reference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_reference(this);
		}
	}

	public final Table_referenceContext table_reference() throws RecognitionException {
		Table_referenceContext _localctx = new Table_referenceContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_table_reference);
		try {
			setState(258);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(256); table_factor1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(257); table_atom();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_factor1Context extends ParserRuleContext {
		public Join_conditionContext join_condition() {
			return getRuleContext(Join_conditionContext.class,0);
		}
		public TerminalNode INNER() { return getToken(MySQLParserFromANTLRRepo.INNER, 0); }
		public TerminalNode CROSS() { return getToken(MySQLParserFromANTLRRepo.CROSS, 0); }
		public Table_factor2Context table_factor2() {
			return getRuleContext(Table_factor2Context.class,0);
		}
		public Table_atomContext table_atom() {
			return getRuleContext(Table_atomContext.class,0);
		}
		public TerminalNode JOIN() { return getToken(MySQLParserFromANTLRRepo.JOIN, 0); }
		public Table_factor1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_factor1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_factor1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_factor1(this);
		}
	}

	public final Table_factor1Context table_factor1() throws RecognitionException {
		Table_factor1Context _localctx = new Table_factor1Context(_ctx, getState());
		enterRule(_localctx, 50, RULE_table_factor1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260); table_factor2();
			setState(269);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(262);
				_la = _input.LA(1);
				if (_la==INNER || _la==CROSS) {
					{
					setState(261);
					_la = _input.LA(1);
					if ( !(_la==INNER || _la==CROSS) ) {
					_errHandler.recoverInline(this);
					}
					consume();
					}
				}

				setState(264); match(JOIN);
				setState(265); table_atom();
				setState(267);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(266); join_condition();
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_factor2Context extends ParserRuleContext {
		public TerminalNode ON() { return getToken(MySQLParserFromANTLRRepo.ON, 0); }
		public Table_factor3Context table_factor3() {
			return getRuleContext(Table_factor3Context.class,0);
		}
		public Table_atomContext table_atom() {
			return getRuleContext(Table_atomContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode STRAIGHT_JOIN() { return getToken(MySQLParserFromANTLRRepo.STRAIGHT_JOIN, 0); }
		public Table_factor2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_factor2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_factor2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_factor2(this);
		}
	}

	public final Table_factor2Context table_factor2() throws RecognitionException {
		Table_factor2Context _localctx = new Table_factor2Context(_ctx, getState());
		enterRule(_localctx, 52, RULE_table_factor2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271); table_factor3();
			setState(278);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(272); match(STRAIGHT_JOIN);
				setState(273); table_atom();
				setState(276);
				switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
				case 1:
					{
					setState(274); match(ON);
					setState(275); expression();
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_factor3Context extends ParserRuleContext {
		public TerminalNode LEFT() { return getToken(MySQLParserFromANTLRRepo.LEFT, 0); }
		public Join_conditionContext join_condition() {
			return getRuleContext(Join_conditionContext.class,0);
		}
		public Table_factor4Context table_factor4(int i) {
			return getRuleContext(Table_factor4Context.class,i);
		}
		public TerminalNode OUTER() { return getToken(MySQLParserFromANTLRRepo.OUTER, 0); }
		public List<Table_factor4Context> table_factor4() {
			return getRuleContexts(Table_factor4Context.class);
		}
		public TerminalNode RIGHT() { return getToken(MySQLParserFromANTLRRepo.RIGHT, 0); }
		public TerminalNode JOIN() { return getToken(MySQLParserFromANTLRRepo.JOIN, 0); }
		public Table_factor3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_factor3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_factor3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_factor3(this);
		}
	}

	public final Table_factor3Context table_factor3() throws RecognitionException {
		Table_factor3Context _localctx = new Table_factor3Context(_ctx, getState());
		enterRule(_localctx, 54, RULE_table_factor3);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280); table_factor4();
			setState(289);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(281);
				_la = _input.LA(1);
				if ( !(_la==LEFT || _la==RIGHT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(283);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(282); match(OUTER);
					}
				}

				setState(285); match(JOIN);
				setState(286); table_factor4();
				setState(287); join_condition();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_factor4Context extends ParserRuleContext {
		public TerminalNode LEFT() { return getToken(MySQLParserFromANTLRRepo.LEFT, 0); }
		public TerminalNode OUTER() { return getToken(MySQLParserFromANTLRRepo.OUTER, 0); }
		public TerminalNode NATURAL() { return getToken(MySQLParserFromANTLRRepo.NATURAL, 0); }
		public List<Table_atomContext> table_atom() {
			return getRuleContexts(Table_atomContext.class);
		}
		public Table_atomContext table_atom(int i) {
			return getRuleContext(Table_atomContext.class,i);
		}
		public TerminalNode RIGHT() { return getToken(MySQLParserFromANTLRRepo.RIGHT, 0); }
		public TerminalNode JOIN() { return getToken(MySQLParserFromANTLRRepo.JOIN, 0); }
		public Table_factor4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_factor4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_factor4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_factor4(this);
		}
	}

	public final Table_factor4Context table_factor4() throws RecognitionException {
		Table_factor4Context _localctx = new Table_factor4Context(_ctx, getState());
		enterRule(_localctx, 56, RULE_table_factor4);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291); table_atom();
			setState(301);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(292); match(NATURAL);
				setState(297);
				_la = _input.LA(1);
				if (_la==LEFT || _la==RIGHT) {
					{
					setState(293);
					_la = _input.LA(1);
					if ( !(_la==LEFT || _la==RIGHT) ) {
					_errHandler.recoverInline(this);
					}
					consume();
					setState(295);
					_la = _input.LA(1);
					if (_la==OUTER) {
						{
						setState(294); match(OUTER);
						}
					}

					}
				}

				setState(299); match(JOIN);
				setState(300); table_atom();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_atomContext extends ParserRuleContext {
		public Subquery_aliasContext subquery_alias() {
			return getRuleContext(Subquery_aliasContext.class,0);
		}
		public Table_referenceContext table_reference(int i) {
			return getRuleContext(Table_referenceContext.class,i);
		}
		public TerminalNode LEFT() { return getToken(MySQLParserFromANTLRRepo.LEFT, 0); }
		public TerminalNode ON() { return getToken(MySQLParserFromANTLRRepo.ON, 0); }
		public SubqueryContext subquery() {
			return getRuleContext(SubqueryContext.class,0);
		}
		public Table_referencesContext table_references() {
			return getRuleContext(Table_referencesContext.class,0);
		}
		public TerminalNode OUTER() { return getToken(MySQLParserFromANTLRRepo.OUTER, 0); }
		public TerminalNode RPAREN() { return getToken(MySQLParserFromANTLRRepo.RPAREN, 0); }
		public List<Table_referenceContext> table_reference() {
			return getRuleContexts(Table_referenceContext.class);
		}
		public Partition_clauseContext partition_clause() {
			return getRuleContext(Partition_clauseContext.class,0);
		}
		public TerminalNode OJ() { return getToken(MySQLParserFromANTLRRepo.OJ, 0); }
		public Index_hint_listContext index_hint_list() {
			return getRuleContext(Index_hint_listContext.class,0);
		}
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MySQLParserFromANTLRRepo.LPAREN, 0); }
		public Table_aliasContext table_alias() {
			return getRuleContext(Table_aliasContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode JOIN() { return getToken(MySQLParserFromANTLRRepo.JOIN, 0); }
		public Table_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterTable_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitTable_atom(this);
		}
	}

	public final Table_atomContext table_atom() throws RecognitionException {
		Table_atomContext _localctx = new Table_atomContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_table_atom);
		int _la;
		try {
			setState(329);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(303); table_name();
				setState(305);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(304); partition_clause();
					}
				}

				setState(308);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(307); table_alias();
					}
				}

				setState(311);
				_la = _input.LA(1);
				if (_la==USE || _la==IGNORE) {
					{
					setState(310); index_hint_list();
					}
				}

				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(313); subquery();
				setState(314); subquery_alias();
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(316); match(LPAREN);
				setState(317); table_references();
				setState(318); match(RPAREN);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(320); match(OJ);
				setState(321); table_reference();
				setState(322); match(LEFT);
				setState(323); match(OUTER);
				setState(324); match(JOIN);
				setState(325); table_reference();
				setState(326); match(ON);
				setState(327); expression();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Join_clauseContext extends ParserRuleContext {
		public TerminalNode LEFT() { return getToken(MySQLParserFromANTLRRepo.LEFT, 0); }
		public TerminalNode ON() { return getToken(MySQLParserFromANTLRRepo.ON, 0); }
		public TerminalNode OUTER() { return getToken(MySQLParserFromANTLRRepo.OUTER, 0); }
		public TerminalNode NATURAL() { return getToken(MySQLParserFromANTLRRepo.NATURAL, 0); }
		public Table_atomContext table_atom() {
			return getRuleContext(Table_atomContext.class,0);
		}
		public Table_factor4Context table_factor4() {
			return getRuleContext(Table_factor4Context.class,0);
		}
		public TerminalNode RIGHT() { return getToken(MySQLParserFromANTLRRepo.RIGHT, 0); }
		public Join_conditionContext join_condition() {
			return getRuleContext(Join_conditionContext.class,0);
		}
		public TerminalNode INNER() { return getToken(MySQLParserFromANTLRRepo.INNER, 0); }
		public TerminalNode CROSS() { return getToken(MySQLParserFromANTLRRepo.CROSS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode STRAIGHT_JOIN() { return getToken(MySQLParserFromANTLRRepo.STRAIGHT_JOIN, 0); }
		public TerminalNode JOIN() { return getToken(MySQLParserFromANTLRRepo.JOIN, 0); }
		public Join_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterJoin_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitJoin_clause(this);
		}
	}

	public final Join_clauseContext join_clause() throws RecognitionException {
		Join_clauseContext _localctx = new Join_clauseContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_join_clause);
		int _la;
		try {
			setState(362);
			switch (_input.LA(1)) {
			case INNER:
			case JOIN:
			case CROSS:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(332);
				_la = _input.LA(1);
				if (_la==INNER || _la==CROSS) {
					{
					setState(331);
					_la = _input.LA(1);
					if ( !(_la==INNER || _la==CROSS) ) {
					_errHandler.recoverInline(this);
					}
					consume();
					}
				}

				setState(334); match(JOIN);
				setState(335); table_atom();
				setState(337);
				_la = _input.LA(1);
				if (_la==USING || _la==ON) {
					{
					setState(336); join_condition();
					}
				}

				}
				}
				break;
			case STRAIGHT_JOIN:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(339); match(STRAIGHT_JOIN);
				setState(340); table_atom();
				setState(343);
				_la = _input.LA(1);
				if (_la==ON) {
					{
					setState(341); match(ON);
					setState(342); expression();
					}
				}

				}
				}
				break;
			case LEFT:
			case RIGHT:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(345);
				_la = _input.LA(1);
				if ( !(_la==LEFT || _la==RIGHT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(347);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(346); match(OUTER);
					}
				}

				setState(349); match(JOIN);
				setState(350); table_factor4();
				setState(351); join_condition();
				}
				}
				break;
			case NATURAL:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(353); match(NATURAL);
				setState(358);
				_la = _input.LA(1);
				if (_la==LEFT || _la==RIGHT) {
					{
					setState(354);
					_la = _input.LA(1);
					if ( !(_la==LEFT || _la==RIGHT) ) {
					_errHandler.recoverInline(this);
					}
					consume();
					setState(356);
					_la = _input.LA(1);
					if (_la==OUTER) {
						{
						setState(355); match(OUTER);
						}
					}

					}
				}

				setState(360); match(JOIN);
				setState(361); table_atom();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Join_conditionContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(MySQLParserFromANTLRRepo.ON, 0); }
		public List<Expr_opContext> expr_op() {
			return getRuleContexts(Expr_opContext.class);
		}
		public Expr_opContext expr_op(int i) {
			return getRuleContext(Expr_opContext.class,i);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode USING() { return getToken(MySQLParserFromANTLRRepo.USING, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public Column_listContext column_list() {
			return getRuleContext(Column_listContext.class,0);
		}
		public Join_conditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterJoin_condition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitJoin_condition(this);
		}
	}

	public final Join_conditionContext join_condition() throws RecognitionException {
		Join_conditionContext _localctx = new Join_conditionContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_join_condition);
		int _la;
		try {
			setState(376);
			switch (_input.LA(1)) {
			case ON:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(364); match(ON);
				setState(365); expression();
				setState(371);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << OR) | (1L << XOR) | (1L << NOT))) != 0)) {
					{
					{
					setState(366); expr_op();
					setState(367); expression();
					}
					}
					setState(373);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			case USING:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(374); match(USING);
				setState(375); column_list();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Index_hint_listContext extends ParserRuleContext {
		public List<Index_hintContext> index_hint() {
			return getRuleContexts(Index_hintContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public Index_hintContext index_hint(int i) {
			return getRuleContext(Index_hintContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public Index_hint_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index_hint_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterIndex_hint_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitIndex_hint_list(this);
		}
	}

	public final Index_hint_listContext index_hint_list() throws RecognitionException {
		Index_hint_listContext _localctx = new Index_hint_listContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_index_hint_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(378); index_hint();
			setState(383);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(379); match(COMMA);
					setState(380); index_hint();
					}
					}
				}
				setState(385);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Index_optionsContext extends ParserRuleContext {
		public TerminalNode INDEX() { return getToken(MySQLParserFromANTLRRepo.INDEX, 0); }
		public TerminalNode KEY() { return getToken(MySQLParserFromANTLRRepo.KEY, 0); }
		public TerminalNode ORDER() { return getToken(MySQLParserFromANTLRRepo.ORDER, 0); }
		public TerminalNode FOR() { return getToken(MySQLParserFromANTLRRepo.FOR, 0); }
		public TerminalNode GROUP() { return getToken(MySQLParserFromANTLRRepo.GROUP, 0); }
		public TerminalNode BY() { return getToken(MySQLParserFromANTLRRepo.BY, 0); }
		public TerminalNode JOIN() { return getToken(MySQLParserFromANTLRRepo.JOIN, 0); }
		public Index_optionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index_options; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterIndex_options(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitIndex_options(this);
		}
	}

	public final Index_optionsContext index_options() throws RecognitionException {
		Index_optionsContext _localctx = new Index_optionsContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_index_options);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(386);
			_la = _input.LA(1);
			if ( !(_la==INDEX || _la==KEY) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(395);
			_la = _input.LA(1);
			if (_la==FOR) {
				{
				setState(387); match(FOR);
				setState(393);
				switch (_input.LA(1)) {
				case JOIN:
					{
					{
					setState(388); match(JOIN);
					}
					}
					break;
				case ORDER:
					{
					{
					setState(389); match(ORDER);
					setState(390); match(BY);
					}
					}
					break;
				case GROUP:
					{
					{
					setState(391); match(GROUP);
					setState(392); match(BY);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Index_hintContext extends ParserRuleContext {
		public TerminalNode IGNORE() { return getToken(MySQLParserFromANTLRRepo.IGNORE, 0); }
		public Index_optionsContext index_options() {
			return getRuleContext(Index_optionsContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MySQLParserFromANTLRRepo.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MySQLParserFromANTLRRepo.RPAREN, 0); }
		public Index_listContext index_list() {
			return getRuleContext(Index_listContext.class,0);
		}
		public TerminalNode USE() { return getToken(MySQLParserFromANTLRRepo.USE, 0); }
		public Index_hintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index_hint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterIndex_hint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitIndex_hint(this);
		}
	}

	public final Index_hintContext index_hint() throws RecognitionException {
		Index_hintContext _localctx = new Index_hintContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_index_hint);
		int _la;
		try {
			setState(411);
			switch (_input.LA(1)) {
			case USE:
				enterOuterAlt(_localctx, 1);
				{
				setState(397); match(USE);
				setState(398); index_options();
				setState(399); match(LPAREN);
				setState(401);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(400); index_list();
					}
				}

				setState(403); match(RPAREN);
				}
				break;
			case IGNORE:
				enterOuterAlt(_localctx, 2);
				{
				setState(405); match(IGNORE);
				setState(406); index_options();
				setState(407); match(LPAREN);
				setState(408); index_list();
				setState(409); match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Index_listContext extends ParserRuleContext {
		public List<Index_nameContext> index_name() {
			return getRuleContexts(Index_nameContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public Index_nameContext index_name(int i) {
			return getRuleContext(Index_nameContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public Index_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterIndex_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitIndex_list(this);
		}
	}

	public final Index_listContext index_list() throws RecognitionException {
		Index_listContext _localctx = new Index_listContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_index_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(413); index_name();
			setState(418);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(414); match(COMMA);
				setState(415); index_name();
				}
				}
				setState(420);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Partition_clauseContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(MySQLParserFromANTLRRepo.LPAREN, 0); }
		public TerminalNode PARTITION() { return getToken(MySQLParserFromANTLRRepo.PARTITION, 0); }
		public TerminalNode RPAREN() { return getToken(MySQLParserFromANTLRRepo.RPAREN, 0); }
		public Partition_namesContext partition_names() {
			return getRuleContext(Partition_namesContext.class,0);
		}
		public Partition_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partition_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterPartition_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitPartition_clause(this);
		}
	}

	public final Partition_clauseContext partition_clause() throws RecognitionException {
		Partition_clauseContext _localctx = new Partition_clauseContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_partition_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421); match(PARTITION);
			setState(422); match(LPAREN);
			setState(423); partition_names();
			setState(424); match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Partition_namesContext extends ParserRuleContext {
		public List<TerminalNode> COMMA() { return getTokens(MySQLParserFromANTLRRepo.COMMA); }
		public List<Partition_nameContext> partition_name() {
			return getRuleContexts(Partition_nameContext.class);
		}
		public Partition_nameContext partition_name(int i) {
			return getRuleContext(Partition_nameContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(MySQLParserFromANTLRRepo.COMMA, i);
		}
		public Partition_namesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partition_names; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterPartition_names(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitPartition_names(this);
		}
	}

	public final Partition_namesContext partition_names() throws RecognitionException {
		Partition_namesContext _localctx = new Partition_namesContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_partition_names);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426); partition_name();
			setState(431);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(427); match(COMMA);
				setState(428); partition_name();
				}
				}
				setState(433);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Partition_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Partition_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partition_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterPartition_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitPartition_name(this);
		}
	}

	public final Partition_nameContext partition_name() throws RecognitionException {
		Partition_nameContext _localctx = new Partition_nameContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_partition_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Subquery_aliasContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MySQLParserFromANTLRRepo.ID, 0); }
		public Subquery_aliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subquery_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterSubquery_alias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitSubquery_alias(this);
		}
	}

	public final Subquery_aliasContext subquery_alias() throws RecognitionException {
		Subquery_aliasContext _localctx = new Subquery_aliasContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_subquery_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(436); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubqueryContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(MySQLParserFromANTLRRepo.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MySQLParserFromANTLRRepo.RPAREN, 0); }
		public Select_clauseContext select_clause() {
			return getRuleContext(Select_clauseContext.class,0);
		}
		public SubqueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subquery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).enterSubquery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MySQLParserFromANTLRRepoListener ) ((MySQLParserFromANTLRRepoListener)listener).exitSubquery(this);
		}
	}

	public final SubqueryContext subquery() throws RecognitionException {
		SubqueryContext _localctx = new SubqueryContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_subquery);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438); match(LPAREN);
			setState(439); select_clause();
			setState(440); match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3L\u01bd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2\6\2"+
		"V\n\2\r\2\16\2W\3\3\3\3\3\4\3\4\3\4\3\4\5\4`\n\4\3\4\5\4c\n\4\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\7\5\7l\n\7\3\7\3\7\5\7p\n\7\3\7\3\7\3\7\5\7u\n\7\3"+
		"\7\3\7\3\7\5\7z\n\7\3\7\3\7\3\7\5\7\177\n\7\3\7\3\7\3\7\5\7\u0084\n\7"+
		"\3\7\3\7\3\7\5\7\u0089\n\7\3\7\3\7\5\7\u008d\n\7\3\7\3\7\5\7\u0091\n\7"+
		"\3\7\3\7\3\7\5\7\u0096\n\7\3\7\3\7\5\7\u009a\n\7\3\7\3\7\5\7\u009e\n\7"+
		"\5\7\u00a0\n\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\7\n\u00aa\n\n\f\n\16\n"+
		"\u00ad\13\n\3\n\3\n\3\13\3\13\3\13\7\13\u00b4\n\13\f\13\16\13\u00b7\13"+
		"\13\3\f\3\f\3\f\3\f\7\f\u00bd\n\f\f\f\16\f\u00c0\13\f\3\r\3\r\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\7\17\u00cb\n\17\f\17\16\17\u00ce\13\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00d7\n\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\5\27\u00e8\n\27\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30"+
		"\u00f8\n\30\3\31\3\31\3\31\3\31\7\31\u00fe\n\31\f\31\16\31\u0101\13\31"+
		"\3\32\3\32\5\32\u0105\n\32\3\33\3\33\5\33\u0109\n\33\3\33\3\33\3\33\5"+
		"\33\u010e\n\33\5\33\u0110\n\33\3\34\3\34\3\34\3\34\3\34\5\34\u0117\n\34"+
		"\5\34\u0119\n\34\3\35\3\35\3\35\5\35\u011e\n\35\3\35\3\35\3\35\3\35\5"+
		"\35\u0124\n\35\3\36\3\36\3\36\3\36\5\36\u012a\n\36\5\36\u012c\n\36\3\36"+
		"\3\36\5\36\u0130\n\36\3\37\3\37\5\37\u0134\n\37\3\37\5\37\u0137\n\37\3"+
		"\37\5\37\u013a\n\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u014c\n\37\3 \5 \u014f\n \3 \3 \3"+
		" \5 \u0154\n \3 \3 \3 \3 \5 \u015a\n \3 \3 \5 \u015e\n \3 \3 \3 \3 \3"+
		" \3 \3 \5 \u0167\n \5 \u0169\n \3 \3 \5 \u016d\n \3!\3!\3!\3!\3!\7!\u0174"+
		"\n!\f!\16!\u0177\13!\3!\3!\5!\u017b\n!\3\"\3\"\3\"\7\"\u0180\n\"\f\"\16"+
		"\"\u0183\13\"\3#\3#\3#\3#\3#\3#\3#\5#\u018c\n#\5#\u018e\n#\3$\3$\3$\3"+
		"$\5$\u0194\n$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u019e\n$\3%\3%\3%\7%\u01a3\n"+
		"%\f%\16%\u01a6\13%\3&\3&\3&\3&\3&\3\'\3\'\3\'\7\'\u01b0\n\'\f\'\16\'\u01b3"+
		"\13\'\3(\3(\3)\3)\3*\3*\3*\3*\3*\2\2+\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPR\2\7\4\2\'*,-\4\2\6\b++\4\2\62"+
		"\62\65\65\3\2BC\3\2\678\u01d8\2U\3\2\2\2\4Y\3\2\2\2\6[\3\2\2\2\bd\3\2"+
		"\2\2\nf\3\2\2\2\f\u009f\3\2\2\2\16\u00a1\3\2\2\2\20\u00a3\3\2\2\2\22\u00a5"+
		"\3\2\2\2\24\u00b0\3\2\2\2\26\u00b8\3\2\2\2\30\u00c1\3\2\2\2\32\u00c3\3"+
		"\2\2\2\34\u00c6\3\2\2\2\36\u00d6\3\2\2\2 \u00d8\3\2\2\2\"\u00da\3\2\2"+
		"\2$\u00dc\3\2\2\2&\u00de\3\2\2\2(\u00e0\3\2\2\2*\u00e2\3\2\2\2,\u00e7"+
		"\3\2\2\2.\u00f7\3\2\2\2\60\u00f9\3\2\2\2\62\u0104\3\2\2\2\64\u0106\3\2"+
		"\2\2\66\u0111\3\2\2\28\u011a\3\2\2\2:\u0125\3\2\2\2<\u014b\3\2\2\2>\u016c"+
		"\3\2\2\2@\u017a\3\2\2\2B\u017c\3\2\2\2D\u0184\3\2\2\2F\u019d\3\2\2\2H"+
		"\u019f\3\2\2\2J\u01a7\3\2\2\2L\u01ac\3\2\2\2N\u01b4\3\2\2\2P\u01b6\3\2"+
		"\2\2R\u01b8\3\2\2\2TV\5\6\4\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2"+
		"X\3\3\2\2\2YZ\7F\2\2Z\5\3\2\2\2[\\\7\3\2\2\\_\5\24\13\2]^\7\4\2\2^`\5"+
		"\60\31\2_]\3\2\2\2_`\3\2\2\2`b\3\2\2\2ac\5\32\16\2ba\3\2\2\2bc\3\2\2\2"+
		"c\7\3\2\2\2de\7F\2\2e\t\3\2\2\2fg\7F\2\2g\13\3\2\2\2hi\5\4\3\2ij\7\60"+
		"\2\2jl\3\2\2\2kh\3\2\2\2kl\3\2\2\2lm\3\2\2\2mn\7F\2\2np\7\60\2\2ok\3\2"+
		"\2\2op\3\2\2\2pq\3\2\2\2qt\7F\2\2rs\7K\2\2su\5\16\b\2tr\3\2\2\2tu\3\2"+
		"\2\2u\u00a0\3\2\2\2vw\5\n\6\2wx\7\60\2\2xz\3\2\2\2yv\3\2\2\2yz\3\2\2\2"+
		"z{\3\2\2\2{~\7F\2\2|}\7K\2\2}\177\5\16\b\2~|\3\2\2\2~\177\3\2\2\2\177"+
		"\u00a0\3\2\2\2\u0080\u0083\7J\2\2\u0081\u0082\7K\2\2\u0082\u0084\5\16"+
		"\b\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u00a0\3\2\2\2\u0085"+
		"\u0086\5\4\3\2\u0086\u0087\7\60\2\2\u0087\u0089\3\2\2\2\u0088\u0085\3"+
		"\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\7F\2\2\u008b"+
		"\u008d\7\60\2\2\u008c\u0088\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\3"+
		"\2\2\2\u008e\u0090\7F\2\2\u008f\u0091\5\16\b\2\u0090\u008f\3\2\2\2\u0090"+
		"\u0091\3\2\2\2\u0091\u00a0\3\2\2\2\u0092\u0093\5\n\6\2\u0093\u0094\7\60"+
		"\2\2\u0094\u0096\3\2\2\2\u0095\u0092\3\2\2\2\u0095\u0096\3\2\2\2\u0096"+
		"\u0097\3\2\2\2\u0097\u0099\7F\2\2\u0098\u009a\5\16\b\2\u0099\u0098\3\2"+
		"\2\2\u0099\u009a\3\2\2\2\u009a\u00a0\3\2\2\2\u009b\u009d\7J\2\2\u009c"+
		"\u009e\5\16\b\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3"+
		"\2\2\2\u009fo\3\2\2\2\u009fy\3\2\2\2\u009f\u0080\3\2\2\2\u009f\u008c\3"+
		"\2\2\2\u009f\u0095\3\2\2\2\u009f\u009b\3\2\2\2\u00a0\r\3\2\2\2\u00a1\u00a2"+
		"\7F\2\2\u00a2\17\3\2\2\2\u00a3\u00a4\7F\2\2\u00a4\21\3\2\2\2\u00a5\u00a6"+
		"\7\"\2\2\u00a6\u00ab\5\f\7\2\u00a7\u00a8\7/\2\2\u00a8\u00aa\5\f\7\2\u00a9"+
		"\u00a7\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2"+
		"\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\7!\2\2\u00af"+
		"\23\3\2\2\2\u00b0\u00b5\5\f\7\2\u00b1\u00b2\7/\2\2\u00b2\u00b4\5\f\7\2"+
		"\u00b3\u00b1\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6"+
		"\3\2\2\2\u00b6\25\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8\u00b9\7\4\2\2\u00b9"+
		"\u00be\5\b\5\2\u00ba\u00bb\7/\2\2\u00bb\u00bd\5\b\5\2\u00bc\u00ba\3\2"+
		"\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf"+
		"\27\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c2\7\3\2\2\u00c2\31\3\2\2\2\u00c3"+
		"\u00c4\7\5\2\2\u00c4\u00c5\5\34\17\2\u00c5\33\3\2\2\2\u00c6\u00cc\5.\30"+
		"\2\u00c7\u00c8\5(\25\2\u00c8\u00c9\5.\30\2\u00c9\u00cb\3\2\2\2\u00ca\u00c7"+
		"\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"\35\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d7\7J\2\2\u00d0\u00d7\7F\2\2"+
		"\u00d1\u00d2\7\31\2\2\u00d2\u00d3\7F\2\2\u00d3\u00d7\7\31\2\2\u00d4\u00d7"+
		"\7G\2\2\u00d5\u00d7\5\f\7\2\u00d6\u00cf\3\2\2\2\u00d6\u00d0\3\2\2\2\u00d6"+
		"\u00d1\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d5\3\2\2\2\u00d7\37\3\2\2"+
		"\2\u00d8\u00d9\5\36\20\2\u00d9!\3\2\2\2\u00da\u00db\5\36\20\2\u00db#\3"+
		"\2\2\2\u00dc\u00dd\5\36\20\2\u00dd%\3\2\2\2\u00de\u00df\t\2\2\2\u00df"+
		"\'\3\2\2\2\u00e0\u00e1\t\3\2\2\u00e1)\3\2\2\2\u00e2\u00e3\7\24\2\2\u00e3"+
		"+\3\2\2\2\u00e4\u00e8\7\t\2\2\u00e5\u00e6\7\t\2\2\u00e6\u00e8\7+\2\2\u00e7"+
		"\u00e4\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e8-\3\2\2\2\u00e9\u00ea\5\"\22\2"+
		"\u00ea\u00eb\5&\24\2\u00eb\u00ec\5 \21\2\u00ec\u00f8\3\2\2\2\u00ed\u00ee"+
		"\5$\23\2\u00ee\u00ef\5*\26\2\u00ef\u00f0\5\"\22\2\u00f0\u00f1\7\6\2\2"+
		"\u00f1\u00f2\5 \21\2\u00f2\u00f8\3\2\2\2\u00f3\u00f4\5$\23\2\u00f4\u00f5"+
		"\5,\27\2\u00f5\u00f6\7\n\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00e9\3\2\2\2\u00f7"+
		"\u00ed\3\2\2\2\u00f7\u00f3\3\2\2\2\u00f8/\3\2\2\2\u00f9\u00ff\5\62\32"+
		"\2\u00fa\u00fb\7/\2\2\u00fb\u00fe\5\62\32\2\u00fc\u00fe\5> \2\u00fd\u00fa"+
		"\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff"+
		"\u0100\3\2\2\2\u0100\61\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0105\5\64\33"+
		"\2\u0103\u0105\5<\37\2\u0104\u0102\3\2\2\2\u0104\u0103\3\2\2\2\u0105\63"+
		"\3\2\2\2\u0106\u010f\5\66\34\2\u0107\u0109\t\4\2\2\u0108\u0107\3\2\2\2"+
		"\u0108\u0109\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\7\64\2\2\u010b\u010d"+
		"\5<\37\2\u010c\u010e\5@!\2\u010d\u010c\3\2\2\2\u010d\u010e\3\2\2\2\u010e"+
		"\u0110\3\2\2\2\u010f\u0108\3\2\2\2\u010f\u0110\3\2\2\2\u0110\65\3\2\2"+
		"\2\u0111\u0118\58\35\2\u0112\u0113\7@\2\2\u0113\u0116\5<\37\2\u0114\u0115"+
		"\7E\2\2\u0115\u0117\5\34\17\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2"+
		"\u0117\u0119\3\2\2\2\u0118\u0112\3\2\2\2\u0118\u0119\3\2\2\2\u0119\67"+
		"\3\2\2\2\u011a\u0123\5:\36\2\u011b\u011d\t\5\2\2\u011c\u011e\7\63\2\2"+
		"\u011d\u011c\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0120"+
		"\7\64\2\2\u0120\u0121\5:\36\2\u0121\u0122\5@!\2\u0122\u0124\3\2\2\2\u0123"+
		"\u011b\3\2\2\2\u0123\u0124\3\2\2\2\u01249\3\2\2\2\u0125\u012f\5<\37\2"+
		"\u0126\u012b\7A\2\2\u0127\u0129\t\5\2\2\u0128\u012a\7\63\2\2\u0129\u0128"+
		"\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012c\3\2\2\2\u012b\u0127\3\2\2\2\u012b"+
		"\u012c\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012e\7\64\2\2\u012e\u0130\5"+
		"<\37\2\u012f\u0126\3\2\2\2\u012f\u0130\3\2\2\2\u0130;\3\2\2\2\u0131\u0133"+
		"\5\b\5\2\u0132\u0134\5J&\2\u0133\u0132\3\2\2\2\u0133\u0134\3\2\2\2\u0134"+
		"\u0136\3\2\2\2\u0135\u0137\5\n\6\2\u0136\u0135\3\2\2\2\u0136\u0137\3\2"+
		"\2\2\u0137\u0139\3\2\2\2\u0138\u013a\5B\"\2\u0139\u0138\3\2\2\2\u0139"+
		"\u013a\3\2\2\2\u013a\u014c\3\2\2\2\u013b\u013c\5R*\2\u013c\u013d\5P)\2"+
		"\u013d\u014c\3\2\2\2\u013e\u013f\7\"\2\2\u013f\u0140\5\60\31\2\u0140\u0141"+
		"\7!\2\2\u0141\u014c\3\2\2\2\u0142\u0143\7D\2\2\u0143\u0144\5\62\32\2\u0144"+
		"\u0145\7B\2\2\u0145\u0146\7\63\2\2\u0146\u0147\7\64\2\2\u0147\u0148\5"+
		"\62\32\2\u0148\u0149\7E\2\2\u0149\u014a\5\34\17\2\u014a\u014c\3\2\2\2"+
		"\u014b\u0131\3\2\2\2\u014b\u013b\3\2\2\2\u014b\u013e\3\2\2\2\u014b\u0142"+
		"\3\2\2\2\u014c=\3\2\2\2\u014d\u014f\t\4\2\2\u014e\u014d\3\2\2\2\u014e"+
		"\u014f\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0151\7\64\2\2\u0151\u0153\5"+
		"<\37\2\u0152\u0154\5@!\2\u0153\u0152\3\2\2\2\u0153\u0154\3\2\2\2\u0154"+
		"\u016d\3\2\2\2\u0155\u0156\7@\2\2\u0156\u0159\5<\37\2\u0157\u0158\7E\2"+
		"\2\u0158\u015a\5\34\17\2\u0159\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a"+
		"\u016d\3\2\2\2\u015b\u015d\t\5\2\2\u015c\u015e\7\63\2\2\u015d\u015c\3"+
		"\2\2\2\u015d\u015e\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0160\7\64\2\2\u0160"+
		"\u0161\5:\36\2\u0161\u0162\5@!\2\u0162\u016d\3\2\2\2\u0163\u0168\7A\2"+
		"\2\u0164\u0166\t\5\2\2\u0165\u0167\7\63\2\2\u0166\u0165\3\2\2\2\u0166"+
		"\u0167\3\2\2\2\u0167\u0169\3\2\2\2\u0168\u0164\3\2\2\2\u0168\u0169\3\2"+
		"\2\2\u0169\u016a\3\2\2\2\u016a\u016b\7\64\2\2\u016b\u016d\5<\37\2\u016c"+
		"\u014e\3\2\2\2\u016c\u0155\3\2\2\2\u016c\u015b\3\2\2\2\u016c\u0163\3\2"+
		"\2\2\u016d?\3\2\2\2\u016e\u016f\7E\2\2\u016f\u0175\5\34\17\2\u0170\u0171"+
		"\5(\25\2\u0171\u0172\5\34\17\2\u0172\u0174\3\2\2\2\u0173\u0170\3\2\2\2"+
		"\u0174\u0177\3\2\2\2\u0175\u0173\3\2\2\2\u0175\u0176\3\2\2\2\u0176\u017b"+
		"\3\2\2\2\u0177\u0175\3\2\2\2\u0178\u0179\7\66\2\2\u0179\u017b\5\22\n\2"+
		"\u017a\u016e\3\2\2\2\u017a\u0178\3\2\2\2\u017bA\3\2\2\2\u017c\u0181\5"+
		"F$\2\u017d\u017e\7/\2\2\u017e\u0180\5F$\2\u017f\u017d\3\2\2\2\u0180\u0183"+
		"\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182C\3\2\2\2\u0183"+
		"\u0181\3\2\2\2\u0184\u018d\t\6\2\2\u0185\u018b\7<\2\2\u0186\u018c\7\64"+
		"\2\2\u0187\u0188\79\2\2\u0188\u018c\7;\2\2\u0189\u018a\7:\2\2\u018a\u018c"+
		"\7;\2\2\u018b\u0186\3\2\2\2\u018b\u0187\3\2\2\2\u018b\u0189\3\2\2\2\u018c"+
		"\u018e\3\2\2\2\u018d\u0185\3\2\2\2\u018d\u018e\3\2\2\2\u018eE\3\2\2\2"+
		"\u018f\u0190\7=\2\2\u0190\u0191\5D#\2\u0191\u0193\7\"\2\2\u0192\u0194"+
		"\5H%\2\u0193\u0192\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0195\3\2\2\2\u0195"+
		"\u0196\7!\2\2\u0196\u019e\3\2\2\2\u0197\u0198\7>\2\2\u0198\u0199\5D#\2"+
		"\u0199\u019a\7\"\2\2\u019a\u019b\5H%\2\u019b\u019c\7!\2\2\u019c\u019e"+
		"\3\2\2\2\u019d\u018f\3\2\2\2\u019d\u0197\3\2\2\2\u019eG\3\2\2\2\u019f"+
		"\u01a4\5\20\t\2\u01a0\u01a1\7/\2\2\u01a1\u01a3\5\20\t\2\u01a2\u01a0\3"+
		"\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5"+
		"I\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a8\7?\2\2\u01a8\u01a9\7\"\2\2\u01a9"+
		"\u01aa\5L\'\2\u01aa\u01ab\7!\2\2\u01abK\3\2\2\2\u01ac\u01b1\5N(\2\u01ad"+
		"\u01ae\7/\2\2\u01ae\u01b0\5N(\2\u01af\u01ad\3\2\2\2\u01b0\u01b3\3\2\2"+
		"\2\u01b1\u01af\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2M\3\2\2\2\u01b3\u01b1"+
		"\3\2\2\2\u01b4\u01b5\7F\2\2\u01b5O\3\2\2\2\u01b6\u01b7\7F\2\2\u01b7Q\3"+
		"\2\2\2\u01b8\u01b9\7\"\2\2\u01b9\u01ba\5\6\4\2\u01ba\u01bb\7!\2\2\u01bb"+
		"S\3\2\2\2:W_bkoty~\u0083\u0088\u008c\u0090\u0095\u0099\u009d\u009f\u00ab"+
		"\u00b5\u00be\u00cc\u00d6\u00e7\u00f7\u00fd\u00ff\u0104\u0108\u010d\u010f"+
		"\u0116\u0118\u011d\u0123\u0129\u012b\u012f\u0133\u0136\u0139\u014b\u014e"+
		"\u0153\u0159\u015d\u0166\u0168\u016c\u0175\u017a\u0181\u018b\u018d\u0193"+
		"\u019d\u01a4\u01b1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}