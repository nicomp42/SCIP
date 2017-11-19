// Generated from MySQLParserFromANTLRRepo.g4 by ANTLR 4.4
package org.Antlr4MySQLFromANTLRRepo;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MySQLParserFromANTLRRepo}.
 */
public interface MySQLParserFromANTLRRepoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#where_clause}.
	 * @param ctx the parse tree
	 */
	void enterWhere_clause(@NotNull MySQLParserFromANTLRRepo.Where_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#where_clause}.
	 * @param ctx the parse tree
	 */
	void exitWhere_clause(@NotNull MySQLParserFromANTLRRepo.Where_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#subquery}.
	 * @param ctx the parse tree
	 */
	void enterSubquery(@NotNull MySQLParserFromANTLRRepo.SubqueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#subquery}.
	 * @param ctx the parse tree
	 */
	void exitSubquery(@NotNull MySQLParserFromANTLRRepo.SubqueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#partition_clause}.
	 * @param ctx the parse tree
	 */
	void enterPartition_clause(@NotNull MySQLParserFromANTLRRepo.Partition_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#partition_clause}.
	 * @param ctx the parse tree
	 */
	void exitPartition_clause(@NotNull MySQLParserFromANTLRRepo.Partition_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#simple_expression}.
	 * @param ctx the parse tree
	 */
	void enterSimple_expression(@NotNull MySQLParserFromANTLRRepo.Simple_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#simple_expression}.
	 * @param ctx the parse tree
	 */
	void exitSimple_expression(@NotNull MySQLParserFromANTLRRepo.Simple_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_name(@NotNull MySQLParserFromANTLRRepo.Table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_name(@NotNull MySQLParserFromANTLRRepo.Table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#select_key}.
	 * @param ctx the parse tree
	 */
	void enterSelect_key(@NotNull MySQLParserFromANTLRRepo.Select_keyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#select_key}.
	 * @param ctx the parse tree
	 */
	void exitSelect_key(@NotNull MySQLParserFromANTLRRepo.Select_keyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#expr_op}.
	 * @param ctx the parse tree
	 */
	void enterExpr_op(@NotNull MySQLParserFromANTLRRepo.Expr_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#expr_op}.
	 * @param ctx the parse tree
	 */
	void exitExpr_op(@NotNull MySQLParserFromANTLRRepo.Expr_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#index_hint_list}.
	 * @param ctx the parse tree
	 */
	void enterIndex_hint_list(@NotNull MySQLParserFromANTLRRepo.Index_hint_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#index_hint_list}.
	 * @param ctx the parse tree
	 */
	void exitIndex_hint_list(@NotNull MySQLParserFromANTLRRepo.Index_hint_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#column_name_alias}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name_alias(@NotNull MySQLParserFromANTLRRepo.Column_name_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#column_name_alias}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name_alias(@NotNull MySQLParserFromANTLRRepo.Column_name_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#relational_op}.
	 * @param ctx the parse tree
	 */
	void enterRelational_op(@NotNull MySQLParserFromANTLRRepo.Relational_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#relational_op}.
	 * @param ctx the parse tree
	 */
	void exitRelational_op(@NotNull MySQLParserFromANTLRRepo.Relational_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#index_hint}.
	 * @param ctx the parse tree
	 */
	void enterIndex_hint(@NotNull MySQLParserFromANTLRRepo.Index_hintContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#index_hint}.
	 * @param ctx the parse tree
	 */
	void exitIndex_hint(@NotNull MySQLParserFromANTLRRepo.Index_hintContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#column_list}.
	 * @param ctx the parse tree
	 */
	void enterColumn_list(@NotNull MySQLParserFromANTLRRepo.Column_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#column_list}.
	 * @param ctx the parse tree
	 */
	void exitColumn_list(@NotNull MySQLParserFromANTLRRepo.Column_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#index_name}.
	 * @param ctx the parse tree
	 */
	void enterIndex_name(@NotNull MySQLParserFromANTLRRepo.Index_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#index_name}.
	 * @param ctx the parse tree
	 */
	void exitIndex_name(@NotNull MySQLParserFromANTLRRepo.Index_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#right_element}.
	 * @param ctx the parse tree
	 */
	void enterRight_element(@NotNull MySQLParserFromANTLRRepo.Right_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#right_element}.
	 * @param ctx the parse tree
	 */
	void exitRight_element(@NotNull MySQLParserFromANTLRRepo.Right_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#partition_names}.
	 * @param ctx the parse tree
	 */
	void enterPartition_names(@NotNull MySQLParserFromANTLRRepo.Partition_namesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#partition_names}.
	 * @param ctx the parse tree
	 */
	void exitPartition_names(@NotNull MySQLParserFromANTLRRepo.Partition_namesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#partition_name}.
	 * @param ctx the parse tree
	 */
	void enterPartition_name(@NotNull MySQLParserFromANTLRRepo.Partition_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#partition_name}.
	 * @param ctx the parse tree
	 */
	void exitPartition_name(@NotNull MySQLParserFromANTLRRepo.Partition_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(@NotNull MySQLParserFromANTLRRepo.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(@NotNull MySQLParserFromANTLRRepo.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#subquery_alias}.
	 * @param ctx the parse tree
	 */
	void enterSubquery_alias(@NotNull MySQLParserFromANTLRRepo.Subquery_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#subquery_alias}.
	 * @param ctx the parse tree
	 */
	void exitSubquery_alias(@NotNull MySQLParserFromANTLRRepo.Subquery_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#left_element}.
	 * @param ctx the parse tree
	 */
	void enterLeft_element(@NotNull MySQLParserFromANTLRRepo.Left_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#left_element}.
	 * @param ctx the parse tree
	 */
	void exitLeft_element(@NotNull MySQLParserFromANTLRRepo.Left_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor1}.
	 * @param ctx the parse tree
	 */
	void enterTable_factor1(@NotNull MySQLParserFromANTLRRepo.Table_factor1Context ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor1}.
	 * @param ctx the parse tree
	 */
	void exitTable_factor1(@NotNull MySQLParserFromANTLRRepo.Table_factor1Context ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(@NotNull MySQLParserFromANTLRRepo.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(@NotNull MySQLParserFromANTLRRepo.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor3}.
	 * @param ctx the parse tree
	 */
	void enterTable_factor3(@NotNull MySQLParserFromANTLRRepo.Table_factor3Context ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor3}.
	 * @param ctx the parse tree
	 */
	void exitTable_factor3(@NotNull MySQLParserFromANTLRRepo.Table_factor3Context ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull MySQLParserFromANTLRRepo.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull MySQLParserFromANTLRRepo.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor2}.
	 * @param ctx the parse tree
	 */
	void enterTable_factor2(@NotNull MySQLParserFromANTLRRepo.Table_factor2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor2}.
	 * @param ctx the parse tree
	 */
	void exitTable_factor2(@NotNull MySQLParserFromANTLRRepo.Table_factor2Context ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor4}.
	 * @param ctx the parse tree
	 */
	void enterTable_factor4(@NotNull MySQLParserFromANTLRRepo.Table_factor4Context ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_factor4}.
	 * @param ctx the parse tree
	 */
	void exitTable_factor4(@NotNull MySQLParserFromANTLRRepo.Table_factor4Context ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#column_name}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name(@NotNull MySQLParserFromANTLRRepo.Column_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#column_name}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name(@NotNull MySQLParserFromANTLRRepo.Column_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_atom}.
	 * @param ctx the parse tree
	 */
	void enterTable_atom(@NotNull MySQLParserFromANTLRRepo.Table_atomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_atom}.
	 * @param ctx the parse tree
	 */
	void exitTable_atom(@NotNull MySQLParserFromANTLRRepo.Table_atomContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#schema_name}.
	 * @param ctx the parse tree
	 */
	void enterSchema_name(@NotNull MySQLParserFromANTLRRepo.Schema_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#schema_name}.
	 * @param ctx the parse tree
	 */
	void exitSchema_name(@NotNull MySQLParserFromANTLRRepo.Schema_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#column_list_clause}.
	 * @param ctx the parse tree
	 */
	void enterColumn_list_clause(@NotNull MySQLParserFromANTLRRepo.Column_list_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#column_list_clause}.
	 * @param ctx the parse tree
	 */
	void exitColumn_list_clause(@NotNull MySQLParserFromANTLRRepo.Column_list_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#is_or_is_not}.
	 * @param ctx the parse tree
	 */
	void enterIs_or_is_not(@NotNull MySQLParserFromANTLRRepo.Is_or_is_notContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#is_or_is_not}.
	 * @param ctx the parse tree
	 */
	void exitIs_or_is_not(@NotNull MySQLParserFromANTLRRepo.Is_or_is_notContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#index_list}.
	 * @param ctx the parse tree
	 */
	void enterIndex_list(@NotNull MySQLParserFromANTLRRepo.Index_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#index_list}.
	 * @param ctx the parse tree
	 */
	void exitIndex_list(@NotNull MySQLParserFromANTLRRepo.Index_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_references}.
	 * @param ctx the parse tree
	 */
	void enterTable_references(@NotNull MySQLParserFromANTLRRepo.Table_referencesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_references}.
	 * @param ctx the parse tree
	 */
	void exitTable_references(@NotNull MySQLParserFromANTLRRepo.Table_referencesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#select_clause}.
	 * @param ctx the parse tree
	 */
	void enterSelect_clause(@NotNull MySQLParserFromANTLRRepo.Select_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#select_clause}.
	 * @param ctx the parse tree
	 */
	void exitSelect_clause(@NotNull MySQLParserFromANTLRRepo.Select_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#join_condition}.
	 * @param ctx the parse tree
	 */
	void enterJoin_condition(@NotNull MySQLParserFromANTLRRepo.Join_conditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#join_condition}.
	 * @param ctx the parse tree
	 */
	void exitJoin_condition(@NotNull MySQLParserFromANTLRRepo.Join_conditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#join_clause}.
	 * @param ctx the parse tree
	 */
	void enterJoin_clause(@NotNull MySQLParserFromANTLRRepo.Join_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#join_clause}.
	 * @param ctx the parse tree
	 */
	void exitJoin_clause(@NotNull MySQLParserFromANTLRRepo.Join_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#from_clause}.
	 * @param ctx the parse tree
	 */
	void enterFrom_clause(@NotNull MySQLParserFromANTLRRepo.From_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#from_clause}.
	 * @param ctx the parse tree
	 */
	void exitFrom_clause(@NotNull MySQLParserFromANTLRRepo.From_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_reference}.
	 * @param ctx the parse tree
	 */
	void enterTable_reference(@NotNull MySQLParserFromANTLRRepo.Table_referenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_reference}.
	 * @param ctx the parse tree
	 */
	void exitTable_reference(@NotNull MySQLParserFromANTLRRepo.Table_referenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#target_element}.
	 * @param ctx the parse tree
	 */
	void enterTarget_element(@NotNull MySQLParserFromANTLRRepo.Target_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#target_element}.
	 * @param ctx the parse tree
	 */
	void exitTarget_element(@NotNull MySQLParserFromANTLRRepo.Target_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#index_options}.
	 * @param ctx the parse tree
	 */
	void enterIndex_options(@NotNull MySQLParserFromANTLRRepo.Index_optionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#index_options}.
	 * @param ctx the parse tree
	 */
	void exitIndex_options(@NotNull MySQLParserFromANTLRRepo.Index_optionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#table_alias}.
	 * @param ctx the parse tree
	 */
	void enterTable_alias(@NotNull MySQLParserFromANTLRRepo.Table_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#table_alias}.
	 * @param ctx the parse tree
	 */
	void exitTable_alias(@NotNull MySQLParserFromANTLRRepo.Table_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParserFromANTLRRepo#between_op}.
	 * @param ctx the parse tree
	 */
	void enterBetween_op(@NotNull MySQLParserFromANTLRRepo.Between_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParserFromANTLRRepo#between_op}.
	 * @param ctx the parse tree
	 */
	void exitBetween_op(@NotNull MySQLParserFromANTLRRepo.Between_opContext ctx);
}