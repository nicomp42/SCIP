// Generated from MySQLParser.g4 by ANTLR 4.7.1
package org.Antlr4MySQLFromMySQLRepo;
/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License, version 2.0,
 * as published by the Free Software Foundation.
 *
 * This program is also distributed with certain software (including
 * but not limited to OpenSSL) that is licensed under separate terms, as
 * designated in a particular file or component or in included license
 * documentation. The authors of MySQL hereby grant you an additional
 * permission to link the program and your derivative works with the
 * separately licensed software that they have included with MySQL.
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License, version 2.0, for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MySQLParser}.
 */
public interface MySQLParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MySQLParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(MySQLParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(MySQLParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStatement(MySQLParser.SimpleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStatement(MySQLParser.SimpleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterStatement}.
	 * @param ctx the parse tree
	 */
	void enterAlterStatement(MySQLParser.AlterStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterStatement}.
	 * @param ctx the parse tree
	 */
	void exitAlterStatement(MySQLParser.AlterStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterDatabase}.
	 * @param ctx the parse tree
	 */
	void enterAlterDatabase(MySQLParser.AlterDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterDatabase}.
	 * @param ctx the parse tree
	 */
	void exitAlterDatabase(MySQLParser.AlterDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterEvent}.
	 * @param ctx the parse tree
	 */
	void enterAlterEvent(MySQLParser.AlterEventContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterEvent}.
	 * @param ctx the parse tree
	 */
	void exitAlterEvent(MySQLParser.AlterEventContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterLogfileGroup}.
	 * @param ctx the parse tree
	 */
	void enterAlterLogfileGroup(MySQLParser.AlterLogfileGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterLogfileGroup}.
	 * @param ctx the parse tree
	 */
	void exitAlterLogfileGroup(MySQLParser.AlterLogfileGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterLogfileGroupOptions}.
	 * @param ctx the parse tree
	 */
	void enterAlterLogfileGroupOptions(MySQLParser.AlterLogfileGroupOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterLogfileGroupOptions}.
	 * @param ctx the parse tree
	 */
	void exitAlterLogfileGroupOptions(MySQLParser.AlterLogfileGroupOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterLogfileGroupOption}.
	 * @param ctx the parse tree
	 */
	void enterAlterLogfileGroupOption(MySQLParser.AlterLogfileGroupOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterLogfileGroupOption}.
	 * @param ctx the parse tree
	 */
	void exitAlterLogfileGroupOption(MySQLParser.AlterLogfileGroupOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterServer}.
	 * @param ctx the parse tree
	 */
	void enterAlterServer(MySQLParser.AlterServerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterServer}.
	 * @param ctx the parse tree
	 */
	void exitAlterServer(MySQLParser.AlterServerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void enterAlterTable(MySQLParser.AlterTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void exitAlterTable(MySQLParser.AlterTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterTableActions}.
	 * @param ctx the parse tree
	 */
	void enterAlterTableActions(MySQLParser.AlterTableActionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterTableActions}.
	 * @param ctx the parse tree
	 */
	void exitAlterTableActions(MySQLParser.AlterTableActionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterCommandList}.
	 * @param ctx the parse tree
	 */
	void enterAlterCommandList(MySQLParser.AlterCommandListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterCommandList}.
	 * @param ctx the parse tree
	 */
	void exitAlterCommandList(MySQLParser.AlterCommandListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterCommandsModifierList}.
	 * @param ctx the parse tree
	 */
	void enterAlterCommandsModifierList(MySQLParser.AlterCommandsModifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterCommandsModifierList}.
	 * @param ctx the parse tree
	 */
	void exitAlterCommandsModifierList(MySQLParser.AlterCommandsModifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#standaloneAlterCommands}.
	 * @param ctx the parse tree
	 */
	void enterStandaloneAlterCommands(MySQLParser.StandaloneAlterCommandsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#standaloneAlterCommands}.
	 * @param ctx the parse tree
	 */
	void exitStandaloneAlterCommands(MySQLParser.StandaloneAlterCommandsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterPartition}.
	 * @param ctx the parse tree
	 */
	void enterAlterPartition(MySQLParser.AlterPartitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterPartition}.
	 * @param ctx the parse tree
	 */
	void exitAlterPartition(MySQLParser.AlterPartitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterList}.
	 * @param ctx the parse tree
	 */
	void enterAlterList(MySQLParser.AlterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterList}.
	 * @param ctx the parse tree
	 */
	void exitAlterList(MySQLParser.AlterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterCommandsModifier}.
	 * @param ctx the parse tree
	 */
	void enterAlterCommandsModifier(MySQLParser.AlterCommandsModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterCommandsModifier}.
	 * @param ctx the parse tree
	 */
	void exitAlterCommandsModifier(MySQLParser.AlterCommandsModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterListItem}.
	 * @param ctx the parse tree
	 */
	void enterAlterListItem(MySQLParser.AlterListItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterListItem}.
	 * @param ctx the parse tree
	 */
	void exitAlterListItem(MySQLParser.AlterListItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#place}.
	 * @param ctx the parse tree
	 */
	void enterPlace(MySQLParser.PlaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#place}.
	 * @param ctx the parse tree
	 */
	void exitPlace(MySQLParser.PlaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#restrict}.
	 * @param ctx the parse tree
	 */
	void enterRestrict(MySQLParser.RestrictContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#restrict}.
	 * @param ctx the parse tree
	 */
	void exitRestrict(MySQLParser.RestrictContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterOrderList}.
	 * @param ctx the parse tree
	 */
	void enterAlterOrderList(MySQLParser.AlterOrderListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterOrderList}.
	 * @param ctx the parse tree
	 */
	void exitAlterOrderList(MySQLParser.AlterOrderListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterAlgorithmOption}.
	 * @param ctx the parse tree
	 */
	void enterAlterAlgorithmOption(MySQLParser.AlterAlgorithmOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterAlgorithmOption}.
	 * @param ctx the parse tree
	 */
	void exitAlterAlgorithmOption(MySQLParser.AlterAlgorithmOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterLockOption}.
	 * @param ctx the parse tree
	 */
	void enterAlterLockOption(MySQLParser.AlterLockOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterLockOption}.
	 * @param ctx the parse tree
	 */
	void exitAlterLockOption(MySQLParser.AlterLockOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexLockAndAlgorithm}.
	 * @param ctx the parse tree
	 */
	void enterIndexLockAndAlgorithm(MySQLParser.IndexLockAndAlgorithmContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexLockAndAlgorithm}.
	 * @param ctx the parse tree
	 */
	void exitIndexLockAndAlgorithm(MySQLParser.IndexLockAndAlgorithmContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#withValidation}.
	 * @param ctx the parse tree
	 */
	void enterWithValidation(MySQLParser.WithValidationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#withValidation}.
	 * @param ctx the parse tree
	 */
	void exitWithValidation(MySQLParser.WithValidationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#removePartitioning}.
	 * @param ctx the parse tree
	 */
	void enterRemovePartitioning(MySQLParser.RemovePartitioningContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#removePartitioning}.
	 * @param ctx the parse tree
	 */
	void exitRemovePartitioning(MySQLParser.RemovePartitioningContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#allOrPartitionNameList}.
	 * @param ctx the parse tree
	 */
	void enterAllOrPartitionNameList(MySQLParser.AllOrPartitionNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#allOrPartitionNameList}.
	 * @param ctx the parse tree
	 */
	void exitAllOrPartitionNameList(MySQLParser.AllOrPartitionNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#reorgPartitionRule}.
	 * @param ctx the parse tree
	 */
	void enterReorgPartitionRule(MySQLParser.ReorgPartitionRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#reorgPartitionRule}.
	 * @param ctx the parse tree
	 */
	void exitReorgPartitionRule(MySQLParser.ReorgPartitionRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterTablespace}.
	 * @param ctx the parse tree
	 */
	void enterAlterTablespace(MySQLParser.AlterTablespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterTablespace}.
	 * @param ctx the parse tree
	 */
	void exitAlterTablespace(MySQLParser.AlterTablespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterTablespaceOption}.
	 * @param ctx the parse tree
	 */
	void enterAlterTablespaceOption(MySQLParser.AlterTablespaceOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterTablespaceOption}.
	 * @param ctx the parse tree
	 */
	void exitAlterTablespaceOption(MySQLParser.AlterTablespaceOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#changeTablespaceOption}.
	 * @param ctx the parse tree
	 */
	void enterChangeTablespaceOption(MySQLParser.ChangeTablespaceOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#changeTablespaceOption}.
	 * @param ctx the parse tree
	 */
	void exitChangeTablespaceOption(MySQLParser.ChangeTablespaceOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterView}.
	 * @param ctx the parse tree
	 */
	void enterAlterView(MySQLParser.AlterViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterView}.
	 * @param ctx the parse tree
	 */
	void exitAlterView(MySQLParser.AlterViewContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewTail}.
	 * @param ctx the parse tree
	 */
	void enterViewTail(MySQLParser.ViewTailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewTail}.
	 * @param ctx the parse tree
	 */
	void exitViewTail(MySQLParser.ViewTailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewSelect}.
	 * @param ctx the parse tree
	 */
	void enterViewSelect(MySQLParser.ViewSelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewSelect}.
	 * @param ctx the parse tree
	 */
	void exitViewSelect(MySQLParser.ViewSelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewCheckOption}.
	 * @param ctx the parse tree
	 */
	void enterViewCheckOption(MySQLParser.ViewCheckOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewCheckOption}.
	 * @param ctx the parse tree
	 */
	void exitViewCheckOption(MySQLParser.ViewCheckOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateStatement(MySQLParser.CreateStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateStatement(MySQLParser.CreateStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createDatabase}.
	 * @param ctx the parse tree
	 */
	void enterCreateDatabase(MySQLParser.CreateDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createDatabase}.
	 * @param ctx the parse tree
	 */
	void exitCreateDatabase(MySQLParser.CreateDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createDatabaseOption}.
	 * @param ctx the parse tree
	 */
	void enterCreateDatabaseOption(MySQLParser.CreateDatabaseOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createDatabaseOption}.
	 * @param ctx the parse tree
	 */
	void exitCreateDatabaseOption(MySQLParser.CreateDatabaseOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(MySQLParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(MySQLParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableElementList}.
	 * @param ctx the parse tree
	 */
	void enterTableElementList(MySQLParser.TableElementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableElementList}.
	 * @param ctx the parse tree
	 */
	void exitTableElementList(MySQLParser.TableElementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableElement}.
	 * @param ctx the parse tree
	 */
	void enterTableElement(MySQLParser.TableElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableElement}.
	 * @param ctx the parse tree
	 */
	void exitTableElement(MySQLParser.TableElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#duplicateAsQueryExpression}.
	 * @param ctx the parse tree
	 */
	void enterDuplicateAsQueryExpression(MySQLParser.DuplicateAsQueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#duplicateAsQueryExpression}.
	 * @param ctx the parse tree
	 */
	void exitDuplicateAsQueryExpression(MySQLParser.DuplicateAsQueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#queryExpressionOrParens}.
	 * @param ctx the parse tree
	 */
	void enterQueryExpressionOrParens(MySQLParser.QueryExpressionOrParensContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#queryExpressionOrParens}.
	 * @param ctx the parse tree
	 */
	void exitQueryExpressionOrParens(MySQLParser.QueryExpressionOrParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createWithDefiner}.
	 * @param ctx the parse tree
	 */
	void enterCreateWithDefiner(MySQLParser.CreateWithDefinerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createWithDefiner}.
	 * @param ctx the parse tree
	 */
	void exitCreateWithDefiner(MySQLParser.CreateWithDefinerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createRoutine}.
	 * @param ctx the parse tree
	 */
	void enterCreateRoutine(MySQLParser.CreateRoutineContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createRoutine}.
	 * @param ctx the parse tree
	 */
	void exitCreateRoutine(MySQLParser.CreateRoutineContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createProcedure}.
	 * @param ctx the parse tree
	 */
	void enterCreateProcedure(MySQLParser.CreateProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createProcedure}.
	 * @param ctx the parse tree
	 */
	void exitCreateProcedure(MySQLParser.CreateProcedureContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createFunction}.
	 * @param ctx the parse tree
	 */
	void enterCreateFunction(MySQLParser.CreateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createFunction}.
	 * @param ctx the parse tree
	 */
	void exitCreateFunction(MySQLParser.CreateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createUdf}.
	 * @param ctx the parse tree
	 */
	void enterCreateUdf(MySQLParser.CreateUdfContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createUdf}.
	 * @param ctx the parse tree
	 */
	void exitCreateUdf(MySQLParser.CreateUdfContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#routineCreateOption}.
	 * @param ctx the parse tree
	 */
	void enterRoutineCreateOption(MySQLParser.RoutineCreateOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#routineCreateOption}.
	 * @param ctx the parse tree
	 */
	void exitRoutineCreateOption(MySQLParser.RoutineCreateOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#routineAlterOptions}.
	 * @param ctx the parse tree
	 */
	void enterRoutineAlterOptions(MySQLParser.RoutineAlterOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#routineAlterOptions}.
	 * @param ctx the parse tree
	 */
	void exitRoutineAlterOptions(MySQLParser.RoutineAlterOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#routineOption}.
	 * @param ctx the parse tree
	 */
	void enterRoutineOption(MySQLParser.RoutineOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#routineOption}.
	 * @param ctx the parse tree
	 */
	void exitRoutineOption(MySQLParser.RoutineOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createIndex}.
	 * @param ctx the parse tree
	 */
	void enterCreateIndex(MySQLParser.CreateIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createIndex}.
	 * @param ctx the parse tree
	 */
	void exitCreateIndex(MySQLParser.CreateIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexNameAndType}.
	 * @param ctx the parse tree
	 */
	void enterIndexNameAndType(MySQLParser.IndexNameAndTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexNameAndType}.
	 * @param ctx the parse tree
	 */
	void exitIndexNameAndType(MySQLParser.IndexNameAndTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createIndexTarget}.
	 * @param ctx the parse tree
	 */
	void enterCreateIndexTarget(MySQLParser.CreateIndexTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createIndexTarget}.
	 * @param ctx the parse tree
	 */
	void exitCreateIndexTarget(MySQLParser.CreateIndexTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createLogfileGroup}.
	 * @param ctx the parse tree
	 */
	void enterCreateLogfileGroup(MySQLParser.CreateLogfileGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createLogfileGroup}.
	 * @param ctx the parse tree
	 */
	void exitCreateLogfileGroup(MySQLParser.CreateLogfileGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#logfileGroupOptions}.
	 * @param ctx the parse tree
	 */
	void enterLogfileGroupOptions(MySQLParser.LogfileGroupOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#logfileGroupOptions}.
	 * @param ctx the parse tree
	 */
	void exitLogfileGroupOptions(MySQLParser.LogfileGroupOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#logfileGroupOption}.
	 * @param ctx the parse tree
	 */
	void enterLogfileGroupOption(MySQLParser.LogfileGroupOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#logfileGroupOption}.
	 * @param ctx the parse tree
	 */
	void exitLogfileGroupOption(MySQLParser.LogfileGroupOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createServer}.
	 * @param ctx the parse tree
	 */
	void enterCreateServer(MySQLParser.CreateServerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createServer}.
	 * @param ctx the parse tree
	 */
	void exitCreateServer(MySQLParser.CreateServerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#serverOptions}.
	 * @param ctx the parse tree
	 */
	void enterServerOptions(MySQLParser.ServerOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#serverOptions}.
	 * @param ctx the parse tree
	 */
	void exitServerOptions(MySQLParser.ServerOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#serverOption}.
	 * @param ctx the parse tree
	 */
	void enterServerOption(MySQLParser.ServerOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#serverOption}.
	 * @param ctx the parse tree
	 */
	void exitServerOption(MySQLParser.ServerOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createTablespace}.
	 * @param ctx the parse tree
	 */
	void enterCreateTablespace(MySQLParser.CreateTablespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createTablespace}.
	 * @param ctx the parse tree
	 */
	void exitCreateTablespace(MySQLParser.CreateTablespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tablespaceOptions}.
	 * @param ctx the parse tree
	 */
	void enterTablespaceOptions(MySQLParser.TablespaceOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tablespaceOptions}.
	 * @param ctx the parse tree
	 */
	void exitTablespaceOptions(MySQLParser.TablespaceOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tablespaceOption}.
	 * @param ctx the parse tree
	 */
	void enterTablespaceOption(MySQLParser.TablespaceOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tablespaceOption}.
	 * @param ctx the parse tree
	 */
	void exitTablespaceOption(MySQLParser.TablespaceOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createView}.
	 * @param ctx the parse tree
	 */
	void enterCreateView(MySQLParser.CreateViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createView}.
	 * @param ctx the parse tree
	 */
	void exitCreateView(MySQLParser.CreateViewContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewReplaceOrAlgorithm}.
	 * @param ctx the parse tree
	 */
	void enterViewReplaceOrAlgorithm(MySQLParser.ViewReplaceOrAlgorithmContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewReplaceOrAlgorithm}.
	 * @param ctx the parse tree
	 */
	void exitViewReplaceOrAlgorithm(MySQLParser.ViewReplaceOrAlgorithmContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewAlgorithm}.
	 * @param ctx the parse tree
	 */
	void enterViewAlgorithm(MySQLParser.ViewAlgorithmContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewAlgorithm}.
	 * @param ctx the parse tree
	 */
	void exitViewAlgorithm(MySQLParser.ViewAlgorithmContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewSuid}.
	 * @param ctx the parse tree
	 */
	void enterViewSuid(MySQLParser.ViewSuidContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewSuid}.
	 * @param ctx the parse tree
	 */
	void exitViewSuid(MySQLParser.ViewSuidContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createTrigger}.
	 * @param ctx the parse tree
	 */
	void enterCreateTrigger(MySQLParser.CreateTriggerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createTrigger}.
	 * @param ctx the parse tree
	 */
	void exitCreateTrigger(MySQLParser.CreateTriggerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#triggerFollowsPrecedesClause}.
	 * @param ctx the parse tree
	 */
	void enterTriggerFollowsPrecedesClause(MySQLParser.TriggerFollowsPrecedesClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#triggerFollowsPrecedesClause}.
	 * @param ctx the parse tree
	 */
	void exitTriggerFollowsPrecedesClause(MySQLParser.TriggerFollowsPrecedesClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createEvent}.
	 * @param ctx the parse tree
	 */
	void enterCreateEvent(MySQLParser.CreateEventContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createEvent}.
	 * @param ctx the parse tree
	 */
	void exitCreateEvent(MySQLParser.CreateEventContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createRole}.
	 * @param ctx the parse tree
	 */
	void enterCreateRole(MySQLParser.CreateRoleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createRole}.
	 * @param ctx the parse tree
	 */
	void exitCreateRole(MySQLParser.CreateRoleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createSpatialReference}.
	 * @param ctx the parse tree
	 */
	void enterCreateSpatialReference(MySQLParser.CreateSpatialReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createSpatialReference}.
	 * @param ctx the parse tree
	 */
	void exitCreateSpatialReference(MySQLParser.CreateSpatialReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#srsAttribute}.
	 * @param ctx the parse tree
	 */
	void enterSrsAttribute(MySQLParser.SrsAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#srsAttribute}.
	 * @param ctx the parse tree
	 */
	void exitSrsAttribute(MySQLParser.SrsAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropStatement}.
	 * @param ctx the parse tree
	 */
	void enterDropStatement(MySQLParser.DropStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropStatement}.
	 * @param ctx the parse tree
	 */
	void exitDropStatement(MySQLParser.DropStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropDatabase}.
	 * @param ctx the parse tree
	 */
	void enterDropDatabase(MySQLParser.DropDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropDatabase}.
	 * @param ctx the parse tree
	 */
	void exitDropDatabase(MySQLParser.DropDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropEvent}.
	 * @param ctx the parse tree
	 */
	void enterDropEvent(MySQLParser.DropEventContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropEvent}.
	 * @param ctx the parse tree
	 */
	void exitDropEvent(MySQLParser.DropEventContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropFunction}.
	 * @param ctx the parse tree
	 */
	void enterDropFunction(MySQLParser.DropFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropFunction}.
	 * @param ctx the parse tree
	 */
	void exitDropFunction(MySQLParser.DropFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropProcedure}.
	 * @param ctx the parse tree
	 */
	void enterDropProcedure(MySQLParser.DropProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropProcedure}.
	 * @param ctx the parse tree
	 */
	void exitDropProcedure(MySQLParser.DropProcedureContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropIndex}.
	 * @param ctx the parse tree
	 */
	void enterDropIndex(MySQLParser.DropIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropIndex}.
	 * @param ctx the parse tree
	 */
	void exitDropIndex(MySQLParser.DropIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropLogfileGroup}.
	 * @param ctx the parse tree
	 */
	void enterDropLogfileGroup(MySQLParser.DropLogfileGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropLogfileGroup}.
	 * @param ctx the parse tree
	 */
	void exitDropLogfileGroup(MySQLParser.DropLogfileGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropLogfileGroupOption}.
	 * @param ctx the parse tree
	 */
	void enterDropLogfileGroupOption(MySQLParser.DropLogfileGroupOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropLogfileGroupOption}.
	 * @param ctx the parse tree
	 */
	void exitDropLogfileGroupOption(MySQLParser.DropLogfileGroupOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropServer}.
	 * @param ctx the parse tree
	 */
	void enterDropServer(MySQLParser.DropServerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropServer}.
	 * @param ctx the parse tree
	 */
	void exitDropServer(MySQLParser.DropServerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void enterDropTable(MySQLParser.DropTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void exitDropTable(MySQLParser.DropTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropTableSpace}.
	 * @param ctx the parse tree
	 */
	void enterDropTableSpace(MySQLParser.DropTableSpaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropTableSpace}.
	 * @param ctx the parse tree
	 */
	void exitDropTableSpace(MySQLParser.DropTableSpaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropTrigger}.
	 * @param ctx the parse tree
	 */
	void enterDropTrigger(MySQLParser.DropTriggerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropTrigger}.
	 * @param ctx the parse tree
	 */
	void exitDropTrigger(MySQLParser.DropTriggerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropView}.
	 * @param ctx the parse tree
	 */
	void enterDropView(MySQLParser.DropViewContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropView}.
	 * @param ctx the parse tree
	 */
	void exitDropView(MySQLParser.DropViewContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropRole}.
	 * @param ctx the parse tree
	 */
	void enterDropRole(MySQLParser.DropRoleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropRole}.
	 * @param ctx the parse tree
	 */
	void exitDropRole(MySQLParser.DropRoleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropSpatialReference}.
	 * @param ctx the parse tree
	 */
	void enterDropSpatialReference(MySQLParser.DropSpatialReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropSpatialReference}.
	 * @param ctx the parse tree
	 */
	void exitDropSpatialReference(MySQLParser.DropSpatialReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#renameTableStatement}.
	 * @param ctx the parse tree
	 */
	void enterRenameTableStatement(MySQLParser.RenameTableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#renameTableStatement}.
	 * @param ctx the parse tree
	 */
	void exitRenameTableStatement(MySQLParser.RenameTableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#renamePair}.
	 * @param ctx the parse tree
	 */
	void enterRenamePair(MySQLParser.RenamePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#renamePair}.
	 * @param ctx the parse tree
	 */
	void exitRenamePair(MySQLParser.RenamePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#truncateTableStatement}.
	 * @param ctx the parse tree
	 */
	void enterTruncateTableStatement(MySQLParser.TruncateTableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#truncateTableStatement}.
	 * @param ctx the parse tree
	 */
	void exitTruncateTableStatement(MySQLParser.TruncateTableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(MySQLParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(MySQLParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#callStatement}.
	 * @param ctx the parse tree
	 */
	void enterCallStatement(MySQLParser.CallStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#callStatement}.
	 * @param ctx the parse tree
	 */
	void exitCallStatement(MySQLParser.CallStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeleteStatement(MySQLParser.DeleteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeleteStatement(MySQLParser.DeleteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionDelete}.
	 * @param ctx the parse tree
	 */
	void enterPartitionDelete(MySQLParser.PartitionDeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionDelete}.
	 * @param ctx the parse tree
	 */
	void exitPartitionDelete(MySQLParser.PartitionDeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#deleteStatementOption}.
	 * @param ctx the parse tree
	 */
	void enterDeleteStatementOption(MySQLParser.DeleteStatementOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#deleteStatementOption}.
	 * @param ctx the parse tree
	 */
	void exitDeleteStatementOption(MySQLParser.DeleteStatementOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#doStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(MySQLParser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#doStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(MySQLParser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#handlerStatement}.
	 * @param ctx the parse tree
	 */
	void enterHandlerStatement(MySQLParser.HandlerStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#handlerStatement}.
	 * @param ctx the parse tree
	 */
	void exitHandlerStatement(MySQLParser.HandlerStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#handlerReadOrScan}.
	 * @param ctx the parse tree
	 */
	void enterHandlerReadOrScan(MySQLParser.HandlerReadOrScanContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#handlerReadOrScan}.
	 * @param ctx the parse tree
	 */
	void exitHandlerReadOrScan(MySQLParser.HandlerReadOrScanContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void enterInsertStatement(MySQLParser.InsertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void exitInsertStatement(MySQLParser.InsertStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertLockOption}.
	 * @param ctx the parse tree
	 */
	void enterInsertLockOption(MySQLParser.InsertLockOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertLockOption}.
	 * @param ctx the parse tree
	 */
	void exitInsertLockOption(MySQLParser.InsertLockOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertFromConstructor}.
	 * @param ctx the parse tree
	 */
	void enterInsertFromConstructor(MySQLParser.InsertFromConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertFromConstructor}.
	 * @param ctx the parse tree
	 */
	void exitInsertFromConstructor(MySQLParser.InsertFromConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fields}.
	 * @param ctx the parse tree
	 */
	void enterFields(MySQLParser.FieldsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fields}.
	 * @param ctx the parse tree
	 */
	void exitFields(MySQLParser.FieldsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertValues}.
	 * @param ctx the parse tree
	 */
	void enterInsertValues(MySQLParser.InsertValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertValues}.
	 * @param ctx the parse tree
	 */
	void exitInsertValues(MySQLParser.InsertValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertQueryExpression}.
	 * @param ctx the parse tree
	 */
	void enterInsertQueryExpression(MySQLParser.InsertQueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertQueryExpression}.
	 * @param ctx the parse tree
	 */
	void exitInsertQueryExpression(MySQLParser.InsertQueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#valueList}.
	 * @param ctx the parse tree
	 */
	void enterValueList(MySQLParser.ValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#valueList}.
	 * @param ctx the parse tree
	 */
	void exitValueList(MySQLParser.ValueListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code values}
	 * labeled alternative in {@link MySQLParser#exprexprexprexprexprboolPriboolPriboolPriboolPripredicateOperationspredicateOperationspredicateOperationspredicateOperationssimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprpartitionTypeDefpartitionTypeDefpartitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterValues(MySQLParser.ValuesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code values}
	 * labeled alternative in {@link MySQLParser#exprexprexprexprexprboolPriboolPriboolPriboolPripredicateOperationspredicateOperationspredicateOperationspredicateOperationssimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprpartitionTypeDefpartitionTypeDefpartitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitValues(MySQLParser.ValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertUpdateList}.
	 * @param ctx the parse tree
	 */
	void enterInsertUpdateList(MySQLParser.InsertUpdateListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertUpdateList}.
	 * @param ctx the parse tree
	 */
	void exitInsertUpdateList(MySQLParser.InsertUpdateListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#loadStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoadStatement(MySQLParser.LoadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#loadStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoadStatement(MySQLParser.LoadStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dataOrXml}.
	 * @param ctx the parse tree
	 */
	void enterDataOrXml(MySQLParser.DataOrXmlContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dataOrXml}.
	 * @param ctx the parse tree
	 */
	void exitDataOrXml(MySQLParser.DataOrXmlContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#xmlRowsIdentifiedBy}.
	 * @param ctx the parse tree
	 */
	void enterXmlRowsIdentifiedBy(MySQLParser.XmlRowsIdentifiedByContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#xmlRowsIdentifiedBy}.
	 * @param ctx the parse tree
	 */
	void exitXmlRowsIdentifiedBy(MySQLParser.XmlRowsIdentifiedByContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#loadDataFileTail}.
	 * @param ctx the parse tree
	 */
	void enterLoadDataFileTail(MySQLParser.LoadDataFileTailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#loadDataFileTail}.
	 * @param ctx the parse tree
	 */
	void exitLoadDataFileTail(MySQLParser.LoadDataFileTailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#loadDataFileTargetList}.
	 * @param ctx the parse tree
	 */
	void enterLoadDataFileTargetList(MySQLParser.LoadDataFileTargetListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#loadDataFileTargetList}.
	 * @param ctx the parse tree
	 */
	void exitLoadDataFileTargetList(MySQLParser.LoadDataFileTargetListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldOrVariableList}.
	 * @param ctx the parse tree
	 */
	void enterFieldOrVariableList(MySQLParser.FieldOrVariableListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldOrVariableList}.
	 * @param ctx the parse tree
	 */
	void exitFieldOrVariableList(MySQLParser.FieldOrVariableListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#replaceStatement}.
	 * @param ctx the parse tree
	 */
	void enterReplaceStatement(MySQLParser.ReplaceStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#replaceStatement}.
	 * @param ctx the parse tree
	 */
	void exitReplaceStatement(MySQLParser.ReplaceStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectStatement(MySQLParser.SelectStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectStatement(MySQLParser.SelectStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#selectStatementWithInto}.
	 * @param ctx the parse tree
	 */
	void enterSelectStatementWithInto(MySQLParser.SelectStatementWithIntoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#selectStatementWithInto}.
	 * @param ctx the parse tree
	 */
	void exitSelectStatementWithInto(MySQLParser.SelectStatementWithIntoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#queryExpression}.
	 * @param ctx the parse tree
	 */
	void enterQueryExpression(MySQLParser.QueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#queryExpression}.
	 * @param ctx the parse tree
	 */
	void exitQueryExpression(MySQLParser.QueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#queryExpressionBody}.
	 * @param ctx the parse tree
	 */
	void enterQueryExpressionBody(MySQLParser.QueryExpressionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#queryExpressionBody}.
	 * @param ctx the parse tree
	 */
	void exitQueryExpressionBody(MySQLParser.QueryExpressionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#queryExpressionParens}.
	 * @param ctx the parse tree
	 */
	void enterQueryExpressionParens(MySQLParser.QueryExpressionParensContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#queryExpressionParens}.
	 * @param ctx the parse tree
	 */
	void exitQueryExpressionParens(MySQLParser.QueryExpressionParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void enterQuerySpecification(MySQLParser.QuerySpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void exitQuerySpecification(MySQLParser.QuerySpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#subquery}.
	 * @param ctx the parse tree
	 */
	void enterSubquery(MySQLParser.SubqueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#subquery}.
	 * @param ctx the parse tree
	 */
	void exitSubquery(MySQLParser.SubqueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#querySpecOption}.
	 * @param ctx the parse tree
	 */
	void enterQuerySpecOption(MySQLParser.QuerySpecOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#querySpecOption}.
	 * @param ctx the parse tree
	 */
	void exitQuerySpecOption(MySQLParser.QuerySpecOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void enterLimitClause(MySQLParser.LimitClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void exitLimitClause(MySQLParser.LimitClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#simpleLimitClause}.
	 * @param ctx the parse tree
	 */
	void enterSimpleLimitClause(MySQLParser.SimpleLimitClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#simpleLimitClause}.
	 * @param ctx the parse tree
	 */
	void exitSimpleLimitClause(MySQLParser.SimpleLimitClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#limitOptions}.
	 * @param ctx the parse tree
	 */
	void enterLimitOptions(MySQLParser.LimitOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#limitOptions}.
	 * @param ctx the parse tree
	 */
	void exitLimitOptions(MySQLParser.LimitOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#limitOption}.
	 * @param ctx the parse tree
	 */
	void enterLimitOption(MySQLParser.LimitOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#limitOption}.
	 * @param ctx the parse tree
	 */
	void exitLimitOption(MySQLParser.LimitOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#intoClause}.
	 * @param ctx the parse tree
	 */
	void enterIntoClause(MySQLParser.IntoClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#intoClause}.
	 * @param ctx the parse tree
	 */
	void exitIntoClause(MySQLParser.IntoClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#procedureAnalyseClause}.
	 * @param ctx the parse tree
	 */
	void enterProcedureAnalyseClause(MySQLParser.ProcedureAnalyseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#procedureAnalyseClause}.
	 * @param ctx the parse tree
	 */
	void exitProcedureAnalyseClause(MySQLParser.ProcedureAnalyseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void enterHavingClause(MySQLParser.HavingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void exitHavingClause(MySQLParser.HavingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowClause}.
	 * @param ctx the parse tree
	 */
	void enterWindowClause(MySQLParser.WindowClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowClause}.
	 * @param ctx the parse tree
	 */
	void exitWindowClause(MySQLParser.WindowClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowDefinition}.
	 * @param ctx the parse tree
	 */
	void enterWindowDefinition(MySQLParser.WindowDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowDefinition}.
	 * @param ctx the parse tree
	 */
	void exitWindowDefinition(MySQLParser.WindowDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void enterWindowSpec(MySQLParser.WindowSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowSpec}.
	 * @param ctx the parse tree
	 */
	void exitWindowSpec(MySQLParser.WindowSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowSpecDetails}.
	 * @param ctx the parse tree
	 */
	void enterWindowSpecDetails(MySQLParser.WindowSpecDetailsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowSpecDetails}.
	 * @param ctx the parse tree
	 */
	void exitWindowSpecDetails(MySQLParser.WindowSpecDetailsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameClause}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameClause(MySQLParser.WindowFrameClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameClause}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameClause(MySQLParser.WindowFrameClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameUnits}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameUnits(MySQLParser.WindowFrameUnitsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameUnits}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameUnits(MySQLParser.WindowFrameUnitsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameExtent}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameExtent(MySQLParser.WindowFrameExtentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameExtent}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameExtent(MySQLParser.WindowFrameExtentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameStart}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameStart(MySQLParser.WindowFrameStartContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameStart}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameStart(MySQLParser.WindowFrameStartContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameBetween}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameBetween(MySQLParser.WindowFrameBetweenContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameBetween}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameBetween(MySQLParser.WindowFrameBetweenContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameBound}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameBound(MySQLParser.WindowFrameBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameBound}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameBound(MySQLParser.WindowFrameBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFrameExclusion}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrameExclusion(MySQLParser.WindowFrameExclusionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFrameExclusion}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrameExclusion(MySQLParser.WindowFrameExclusionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#withClause}.
	 * @param ctx the parse tree
	 */
	void enterWithClause(MySQLParser.WithClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#withClause}.
	 * @param ctx the parse tree
	 */
	void exitWithClause(MySQLParser.WithClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#commonTableExpression}.
	 * @param ctx the parse tree
	 */
	void enterCommonTableExpression(MySQLParser.CommonTableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#commonTableExpression}.
	 * @param ctx the parse tree
	 */
	void exitCommonTableExpression(MySQLParser.CommonTableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupByClause(MySQLParser.GroupByClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupByClause(MySQLParser.GroupByClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#olapOption}.
	 * @param ctx the parse tree
	 */
	void enterOlapOption(MySQLParser.OlapOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#olapOption}.
	 * @param ctx the parse tree
	 */
	void exitOlapOption(MySQLParser.OlapOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#orderClause}.
	 * @param ctx the parse tree
	 */
	void enterOrderClause(MySQLParser.OrderClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#orderClause}.
	 * @param ctx the parse tree
	 */
	void exitOrderClause(MySQLParser.OrderClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#direction}.
	 * @param ctx the parse tree
	 */
	void enterDirection(MySQLParser.DirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#direction}.
	 * @param ctx the parse tree
	 */
	void exitDirection(MySQLParser.DirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void enterFromClause(MySQLParser.FromClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void exitFromClause(MySQLParser.FromClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableReferenceList}.
	 * @param ctx the parse tree
	 */
	void enterTableReferenceList(MySQLParser.TableReferenceListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableReferenceList}.
	 * @param ctx the parse tree
	 */
	void exitTableReferenceList(MySQLParser.TableReferenceListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#selectOption}.
	 * @param ctx the parse tree
	 */
	void enterSelectOption(MySQLParser.SelectOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#selectOption}.
	 * @param ctx the parse tree
	 */
	void exitSelectOption(MySQLParser.SelectOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lockingClause}.
	 * @param ctx the parse tree
	 */
	void enterLockingClause(MySQLParser.LockingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lockingClause}.
	 * @param ctx the parse tree
	 */
	void exitLockingClause(MySQLParser.LockingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lockStrengh}.
	 * @param ctx the parse tree
	 */
	void enterLockStrengh(MySQLParser.LockStrenghContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lockStrengh}.
	 * @param ctx the parse tree
	 */
	void exitLockStrengh(MySQLParser.LockStrenghContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lockedRowAction}.
	 * @param ctx the parse tree
	 */
	void enterLockedRowAction(MySQLParser.LockedRowActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lockedRowAction}.
	 * @param ctx the parse tree
	 */
	void exitLockedRowAction(MySQLParser.LockedRowActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#selectItemList}.
	 * @param ctx the parse tree
	 */
	void enterSelectItemList(MySQLParser.SelectItemListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#selectItemList}.
	 * @param ctx the parse tree
	 */
	void exitSelectItemList(MySQLParser.SelectItemListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void enterSelectItem(MySQLParser.SelectItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void exitSelectItem(MySQLParser.SelectItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#selectAlias}.
	 * @param ctx the parse tree
	 */
	void enterSelectAlias(MySQLParser.SelectAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#selectAlias}.
	 * @param ctx the parse tree
	 */
	void exitSelectAlias(MySQLParser.SelectAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(MySQLParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(MySQLParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableReference}.
	 * @param ctx the parse tree
	 */
	void enterTableReference(MySQLParser.TableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableReference}.
	 * @param ctx the parse tree
	 */
	void exitTableReference(MySQLParser.TableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#joinedTable}.
	 * @param ctx the parse tree
	 */
	void enterJoinedTable(MySQLParser.JoinedTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#joinedTable}.
	 * @param ctx the parse tree
	 */
	void exitJoinedTable(MySQLParser.JoinedTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#naturalJoinType}.
	 * @param ctx the parse tree
	 */
	void enterNaturalJoinType(MySQLParser.NaturalJoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#naturalJoinType}.
	 * @param ctx the parse tree
	 */
	void exitNaturalJoinType(MySQLParser.NaturalJoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#innerJoinType}.
	 * @param ctx the parse tree
	 */
	void enterInnerJoinType(MySQLParser.InnerJoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#innerJoinType}.
	 * @param ctx the parse tree
	 */
	void exitInnerJoinType(MySQLParser.InnerJoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#outerJoinType}.
	 * @param ctx the parse tree
	 */
	void enterOuterJoinType(MySQLParser.OuterJoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#outerJoinType}.
	 * @param ctx the parse tree
	 */
	void exitOuterJoinType(MySQLParser.OuterJoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableFactor}.
	 * @param ctx the parse tree
	 */
	void enterTableFactor(MySQLParser.TableFactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableFactor}.
	 * @param ctx the parse tree
	 */
	void exitTableFactor(MySQLParser.TableFactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#singleTable}.
	 * @param ctx the parse tree
	 */
	void enterSingleTable(MySQLParser.SingleTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#singleTable}.
	 * @param ctx the parse tree
	 */
	void exitSingleTable(MySQLParser.SingleTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#singleTableParens}.
	 * @param ctx the parse tree
	 */
	void enterSingleTableParens(MySQLParser.SingleTableParensContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#singleTableParens}.
	 * @param ctx the parse tree
	 */
	void exitSingleTableParens(MySQLParser.SingleTableParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#derivedTable}.
	 * @param ctx the parse tree
	 */
	void enterDerivedTable(MySQLParser.DerivedTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#derivedTable}.
	 * @param ctx the parse tree
	 */
	void exitDerivedTable(MySQLParser.DerivedTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#joinedTableParens}.
	 * @param ctx the parse tree
	 */
	void enterJoinedTableParens(MySQLParser.JoinedTableParensContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#joinedTableParens}.
	 * @param ctx the parse tree
	 */
	void exitJoinedTableParens(MySQLParser.JoinedTableParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableReferenceListParens}.
	 * @param ctx the parse tree
	 */
	void enterTableReferenceListParens(MySQLParser.TableReferenceListParensContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableReferenceListParens}.
	 * @param ctx the parse tree
	 */
	void exitTableReferenceListParens(MySQLParser.TableReferenceListParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableFunction}.
	 * @param ctx the parse tree
	 */
	void enterTableFunction(MySQLParser.TableFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableFunction}.
	 * @param ctx the parse tree
	 */
	void exitTableFunction(MySQLParser.TableFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#columnsClause}.
	 * @param ctx the parse tree
	 */
	void enterColumnsClause(MySQLParser.ColumnsClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#columnsClause}.
	 * @param ctx the parse tree
	 */
	void exitColumnsClause(MySQLParser.ColumnsClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#jtColumn}.
	 * @param ctx the parse tree
	 */
	void enterJtColumn(MySQLParser.JtColumnContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#jtColumn}.
	 * @param ctx the parse tree
	 */
	void exitJtColumn(MySQLParser.JtColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#onEmptyOrError}.
	 * @param ctx the parse tree
	 */
	void enterOnEmptyOrError(MySQLParser.OnEmptyOrErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#onEmptyOrError}.
	 * @param ctx the parse tree
	 */
	void exitOnEmptyOrError(MySQLParser.OnEmptyOrErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#onEmpty}.
	 * @param ctx the parse tree
	 */
	void enterOnEmpty(MySQLParser.OnEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#onEmpty}.
	 * @param ctx the parse tree
	 */
	void exitOnEmpty(MySQLParser.OnEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#onError}.
	 * @param ctx the parse tree
	 */
	void enterOnError(MySQLParser.OnErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#onError}.
	 * @param ctx the parse tree
	 */
	void exitOnError(MySQLParser.OnErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#jtOnResponse}.
	 * @param ctx the parse tree
	 */
	void enterJtOnResponse(MySQLParser.JtOnResponseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#jtOnResponse}.
	 * @param ctx the parse tree
	 */
	void exitJtOnResponse(MySQLParser.JtOnResponseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#unionOption}.
	 * @param ctx the parse tree
	 */
	void enterUnionOption(MySQLParser.UnionOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#unionOption}.
	 * @param ctx the parse tree
	 */
	void exitUnionOption(MySQLParser.UnionOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableAlias}.
	 * @param ctx the parse tree
	 */
	void enterTableAlias(MySQLParser.TableAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableAlias}.
	 * @param ctx the parse tree
	 */
	void exitTableAlias(MySQLParser.TableAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexHintList}.
	 * @param ctx the parse tree
	 */
	void enterIndexHintList(MySQLParser.IndexHintListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexHintList}.
	 * @param ctx the parse tree
	 */
	void exitIndexHintList(MySQLParser.IndexHintListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexHint}.
	 * @param ctx the parse tree
	 */
	void enterIndexHint(MySQLParser.IndexHintContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexHint}.
	 * @param ctx the parse tree
	 */
	void exitIndexHint(MySQLParser.IndexHintContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexHintType}.
	 * @param ctx the parse tree
	 */
	void enterIndexHintType(MySQLParser.IndexHintTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexHintType}.
	 * @param ctx the parse tree
	 */
	void exitIndexHintType(MySQLParser.IndexHintTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyOrIndex}.
	 * @param ctx the parse tree
	 */
	void enterKeyOrIndex(MySQLParser.KeyOrIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyOrIndex}.
	 * @param ctx the parse tree
	 */
	void exitKeyOrIndex(MySQLParser.KeyOrIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexHintClause}.
	 * @param ctx the parse tree
	 */
	void enterIndexHintClause(MySQLParser.IndexHintClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexHintClause}.
	 * @param ctx the parse tree
	 */
	void exitIndexHintClause(MySQLParser.IndexHintClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexList}.
	 * @param ctx the parse tree
	 */
	void enterIndexList(MySQLParser.IndexListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexList}.
	 * @param ctx the parse tree
	 */
	void exitIndexList(MySQLParser.IndexListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexListElement}.
	 * @param ctx the parse tree
	 */
	void enterIndexListElement(MySQLParser.IndexListElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexListElement}.
	 * @param ctx the parse tree
	 */
	void exitIndexListElement(MySQLParser.IndexListElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#updateStatement}.
	 * @param ctx the parse tree
	 */
	void enterUpdateStatement(MySQLParser.UpdateStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#updateStatement}.
	 * @param ctx the parse tree
	 */
	void exitUpdateStatement(MySQLParser.UpdateStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#transactionOrLockingStatement}.
	 * @param ctx the parse tree
	 */
	void enterTransactionOrLockingStatement(MySQLParser.TransactionOrLockingStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#transactionOrLockingStatement}.
	 * @param ctx the parse tree
	 */
	void exitTransactionOrLockingStatement(MySQLParser.TransactionOrLockingStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#transactionStatement}.
	 * @param ctx the parse tree
	 */
	void enterTransactionStatement(MySQLParser.TransactionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#transactionStatement}.
	 * @param ctx the parse tree
	 */
	void exitTransactionStatement(MySQLParser.TransactionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#beginWork}.
	 * @param ctx the parse tree
	 */
	void enterBeginWork(MySQLParser.BeginWorkContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#beginWork}.
	 * @param ctx the parse tree
	 */
	void exitBeginWork(MySQLParser.BeginWorkContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#transactionCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterTransactionCharacteristic(MySQLParser.TransactionCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#transactionCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitTransactionCharacteristic(MySQLParser.TransactionCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setTransactionCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterSetTransactionCharacteristic(MySQLParser.SetTransactionCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setTransactionCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitSetTransactionCharacteristic(MySQLParser.SetTransactionCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#isolationLevel}.
	 * @param ctx the parse tree
	 */
	void enterIsolationLevel(MySQLParser.IsolationLevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#isolationLevel}.
	 * @param ctx the parse tree
	 */
	void exitIsolationLevel(MySQLParser.IsolationLevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#savepointStatement}.
	 * @param ctx the parse tree
	 */
	void enterSavepointStatement(MySQLParser.SavepointStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#savepointStatement}.
	 * @param ctx the parse tree
	 */
	void exitSavepointStatement(MySQLParser.SavepointStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lockStatement}.
	 * @param ctx the parse tree
	 */
	void enterLockStatement(MySQLParser.LockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lockStatement}.
	 * @param ctx the parse tree
	 */
	void exitLockStatement(MySQLParser.LockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lockItem}.
	 * @param ctx the parse tree
	 */
	void enterLockItem(MySQLParser.LockItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lockItem}.
	 * @param ctx the parse tree
	 */
	void exitLockItem(MySQLParser.LockItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lockOption}.
	 * @param ctx the parse tree
	 */
	void enterLockOption(MySQLParser.LockOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lockOption}.
	 * @param ctx the parse tree
	 */
	void exitLockOption(MySQLParser.LockOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#xaStatement}.
	 * @param ctx the parse tree
	 */
	void enterXaStatement(MySQLParser.XaStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#xaStatement}.
	 * @param ctx the parse tree
	 */
	void exitXaStatement(MySQLParser.XaStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#xaConvert}.
	 * @param ctx the parse tree
	 */
	void enterXaConvert(MySQLParser.XaConvertContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#xaConvert}.
	 * @param ctx the parse tree
	 */
	void exitXaConvert(MySQLParser.XaConvertContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#xid}.
	 * @param ctx the parse tree
	 */
	void enterXid(MySQLParser.XidContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#xid}.
	 * @param ctx the parse tree
	 */
	void exitXid(MySQLParser.XidContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#replicationStatement}.
	 * @param ctx the parse tree
	 */
	void enterReplicationStatement(MySQLParser.ReplicationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#replicationStatement}.
	 * @param ctx the parse tree
	 */
	void exitReplicationStatement(MySQLParser.ReplicationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resetOption}.
	 * @param ctx the parse tree
	 */
	void enterResetOption(MySQLParser.ResetOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resetOption}.
	 * @param ctx the parse tree
	 */
	void exitResetOption(MySQLParser.ResetOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#masterResetOptions}.
	 * @param ctx the parse tree
	 */
	void enterMasterResetOptions(MySQLParser.MasterResetOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#masterResetOptions}.
	 * @param ctx the parse tree
	 */
	void exitMasterResetOptions(MySQLParser.MasterResetOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#replicationLoad}.
	 * @param ctx the parse tree
	 */
	void enterReplicationLoad(MySQLParser.ReplicationLoadContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#replicationLoad}.
	 * @param ctx the parse tree
	 */
	void exitReplicationLoad(MySQLParser.ReplicationLoadContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#changeMaster}.
	 * @param ctx the parse tree
	 */
	void enterChangeMaster(MySQLParser.ChangeMasterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#changeMaster}.
	 * @param ctx the parse tree
	 */
	void exitChangeMaster(MySQLParser.ChangeMasterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#changeMasterOptions}.
	 * @param ctx the parse tree
	 */
	void enterChangeMasterOptions(MySQLParser.ChangeMasterOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#changeMasterOptions}.
	 * @param ctx the parse tree
	 */
	void exitChangeMasterOptions(MySQLParser.ChangeMasterOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#masterOption}.
	 * @param ctx the parse tree
	 */
	void enterMasterOption(MySQLParser.MasterOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#masterOption}.
	 * @param ctx the parse tree
	 */
	void exitMasterOption(MySQLParser.MasterOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#masterFileDef}.
	 * @param ctx the parse tree
	 */
	void enterMasterFileDef(MySQLParser.MasterFileDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#masterFileDef}.
	 * @param ctx the parse tree
	 */
	void exitMasterFileDef(MySQLParser.MasterFileDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#serverIdList}.
	 * @param ctx the parse tree
	 */
	void enterServerIdList(MySQLParser.ServerIdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#serverIdList}.
	 * @param ctx the parse tree
	 */
	void exitServerIdList(MySQLParser.ServerIdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#changeReplication}.
	 * @param ctx the parse tree
	 */
	void enterChangeReplication(MySQLParser.ChangeReplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#changeReplication}.
	 * @param ctx the parse tree
	 */
	void exitChangeReplication(MySQLParser.ChangeReplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFilterDefinition(MySQLParser.FilterDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFilterDefinition(MySQLParser.FilterDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterDbList}.
	 * @param ctx the parse tree
	 */
	void enterFilterDbList(MySQLParser.FilterDbListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterDbList}.
	 * @param ctx the parse tree
	 */
	void exitFilterDbList(MySQLParser.FilterDbListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterTableList}.
	 * @param ctx the parse tree
	 */
	void enterFilterTableList(MySQLParser.FilterTableListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterTableList}.
	 * @param ctx the parse tree
	 */
	void exitFilterTableList(MySQLParser.FilterTableListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterStringList}.
	 * @param ctx the parse tree
	 */
	void enterFilterStringList(MySQLParser.FilterStringListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterStringList}.
	 * @param ctx the parse tree
	 */
	void exitFilterStringList(MySQLParser.FilterStringListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterWildDbTableString}.
	 * @param ctx the parse tree
	 */
	void enterFilterWildDbTableString(MySQLParser.FilterWildDbTableStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterWildDbTableString}.
	 * @param ctx the parse tree
	 */
	void exitFilterWildDbTableString(MySQLParser.FilterWildDbTableStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterDbPairList}.
	 * @param ctx the parse tree
	 */
	void enterFilterDbPairList(MySQLParser.FilterDbPairListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterDbPairList}.
	 * @param ctx the parse tree
	 */
	void exitFilterDbPairList(MySQLParser.FilterDbPairListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#slave}.
	 * @param ctx the parse tree
	 */
	void enterSlave(MySQLParser.SlaveContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#slave}.
	 * @param ctx the parse tree
	 */
	void exitSlave(MySQLParser.SlaveContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#slaveUntilOptions}.
	 * @param ctx the parse tree
	 */
	void enterSlaveUntilOptions(MySQLParser.SlaveUntilOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#slaveUntilOptions}.
	 * @param ctx the parse tree
	 */
	void exitSlaveUntilOptions(MySQLParser.SlaveUntilOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#slaveConnectionOptions}.
	 * @param ctx the parse tree
	 */
	void enterSlaveConnectionOptions(MySQLParser.SlaveConnectionOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#slaveConnectionOptions}.
	 * @param ctx the parse tree
	 */
	void exitSlaveConnectionOptions(MySQLParser.SlaveConnectionOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#slaveThreadOptions}.
	 * @param ctx the parse tree
	 */
	void enterSlaveThreadOptions(MySQLParser.SlaveThreadOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#slaveThreadOptions}.
	 * @param ctx the parse tree
	 */
	void exitSlaveThreadOptions(MySQLParser.SlaveThreadOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#slaveThreadOption}.
	 * @param ctx the parse tree
	 */
	void enterSlaveThreadOption(MySQLParser.SlaveThreadOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#slaveThreadOption}.
	 * @param ctx the parse tree
	 */
	void exitSlaveThreadOption(MySQLParser.SlaveThreadOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#groupReplication}.
	 * @param ctx the parse tree
	 */
	void enterGroupReplication(MySQLParser.GroupReplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#groupReplication}.
	 * @param ctx the parse tree
	 */
	void exitGroupReplication(MySQLParser.GroupReplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#preparedStatement}.
	 * @param ctx the parse tree
	 */
	void enterPreparedStatement(MySQLParser.PreparedStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#preparedStatement}.
	 * @param ctx the parse tree
	 */
	void exitPreparedStatement(MySQLParser.PreparedStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#executeStatement}.
	 * @param ctx the parse tree
	 */
	void enterExecuteStatement(MySQLParser.ExecuteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#executeStatement}.
	 * @param ctx the parse tree
	 */
	void exitExecuteStatement(MySQLParser.ExecuteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#executeVarList}.
	 * @param ctx the parse tree
	 */
	void enterExecuteVarList(MySQLParser.ExecuteVarListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#executeVarList}.
	 * @param ctx the parse tree
	 */
	void exitExecuteVarList(MySQLParser.ExecuteVarListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#cloneStatement}.
	 * @param ctx the parse tree
	 */
	void enterCloneStatement(MySQLParser.CloneStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#cloneStatement}.
	 * @param ctx the parse tree
	 */
	void exitCloneStatement(MySQLParser.CloneStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#accountManagementStatement}.
	 * @param ctx the parse tree
	 */
	void enterAccountManagementStatement(MySQLParser.AccountManagementStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#accountManagementStatement}.
	 * @param ctx the parse tree
	 */
	void exitAccountManagementStatement(MySQLParser.AccountManagementStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterUser}.
	 * @param ctx the parse tree
	 */
	void enterAlterUser(MySQLParser.AlterUserContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterUser}.
	 * @param ctx the parse tree
	 */
	void exitAlterUser(MySQLParser.AlterUserContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterUserTail}.
	 * @param ctx the parse tree
	 */
	void enterAlterUserTail(MySQLParser.AlterUserTailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterUserTail}.
	 * @param ctx the parse tree
	 */
	void exitAlterUserTail(MySQLParser.AlterUserTailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createUser}.
	 * @param ctx the parse tree
	 */
	void enterCreateUser(MySQLParser.CreateUserContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createUser}.
	 * @param ctx the parse tree
	 */
	void exitCreateUser(MySQLParser.CreateUserContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createUserTail}.
	 * @param ctx the parse tree
	 */
	void enterCreateUserTail(MySQLParser.CreateUserTailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createUserTail}.
	 * @param ctx the parse tree
	 */
	void exitCreateUserTail(MySQLParser.CreateUserTailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#defaultRoleClause}.
	 * @param ctx the parse tree
	 */
	void enterDefaultRoleClause(MySQLParser.DefaultRoleClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#defaultRoleClause}.
	 * @param ctx the parse tree
	 */
	void exitDefaultRoleClause(MySQLParser.DefaultRoleClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#requireClause}.
	 * @param ctx the parse tree
	 */
	void enterRequireClause(MySQLParser.RequireClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#requireClause}.
	 * @param ctx the parse tree
	 */
	void exitRequireClause(MySQLParser.RequireClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#connectOptions}.
	 * @param ctx the parse tree
	 */
	void enterConnectOptions(MySQLParser.ConnectOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#connectOptions}.
	 * @param ctx the parse tree
	 */
	void exitConnectOptions(MySQLParser.ConnectOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#accountLockPasswordExpireOptions}.
	 * @param ctx the parse tree
	 */
	void enterAccountLockPasswordExpireOptions(MySQLParser.AccountLockPasswordExpireOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#accountLockPasswordExpireOptions}.
	 * @param ctx the parse tree
	 */
	void exitAccountLockPasswordExpireOptions(MySQLParser.AccountLockPasswordExpireOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropUser}.
	 * @param ctx the parse tree
	 */
	void enterDropUser(MySQLParser.DropUserContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropUser}.
	 * @param ctx the parse tree
	 */
	void exitDropUser(MySQLParser.DropUserContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#grant}.
	 * @param ctx the parse tree
	 */
	void enterGrant(MySQLParser.GrantContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#grant}.
	 * @param ctx the parse tree
	 */
	void exitGrant(MySQLParser.GrantContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#grantTargetList}.
	 * @param ctx the parse tree
	 */
	void enterGrantTargetList(MySQLParser.GrantTargetListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#grantTargetList}.
	 * @param ctx the parse tree
	 */
	void exitGrantTargetList(MySQLParser.GrantTargetListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#grantOptions}.
	 * @param ctx the parse tree
	 */
	void enterGrantOptions(MySQLParser.GrantOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#grantOptions}.
	 * @param ctx the parse tree
	 */
	void exitGrantOptions(MySQLParser.GrantOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#versionedRequireClause}.
	 * @param ctx the parse tree
	 */
	void enterVersionedRequireClause(MySQLParser.VersionedRequireClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#versionedRequireClause}.
	 * @param ctx the parse tree
	 */
	void exitVersionedRequireClause(MySQLParser.VersionedRequireClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#renameUser}.
	 * @param ctx the parse tree
	 */
	void enterRenameUser(MySQLParser.RenameUserContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#renameUser}.
	 * @param ctx the parse tree
	 */
	void exitRenameUser(MySQLParser.RenameUserContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#revoke}.
	 * @param ctx the parse tree
	 */
	void enterRevoke(MySQLParser.RevokeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#revoke}.
	 * @param ctx the parse tree
	 */
	void exitRevoke(MySQLParser.RevokeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#onTypeTo}.
	 * @param ctx the parse tree
	 */
	void enterOnTypeTo(MySQLParser.OnTypeToContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#onTypeTo}.
	 * @param ctx the parse tree
	 */
	void exitOnTypeTo(MySQLParser.OnTypeToContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#aclType}.
	 * @param ctx the parse tree
	 */
	void enterAclType(MySQLParser.AclTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#aclType}.
	 * @param ctx the parse tree
	 */
	void exitAclType(MySQLParser.AclTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setPassword}.
	 * @param ctx the parse tree
	 */
	void enterSetPassword(MySQLParser.SetPasswordContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setPassword}.
	 * @param ctx the parse tree
	 */
	void exitSetPassword(MySQLParser.SetPasswordContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleOrPrivilegesList}.
	 * @param ctx the parse tree
	 */
	void enterRoleOrPrivilegesList(MySQLParser.RoleOrPrivilegesListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleOrPrivilegesList}.
	 * @param ctx the parse tree
	 */
	void exitRoleOrPrivilegesList(MySQLParser.RoleOrPrivilegesListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleOrPrivilege}.
	 * @param ctx the parse tree
	 */
	void enterRoleOrPrivilege(MySQLParser.RoleOrPrivilegeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleOrPrivilege}.
	 * @param ctx the parse tree
	 */
	void exitRoleOrPrivilege(MySQLParser.RoleOrPrivilegeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#grantIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterGrantIdentifier(MySQLParser.GrantIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#grantIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitGrantIdentifier(MySQLParser.GrantIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#requireList}.
	 * @param ctx the parse tree
	 */
	void enterRequireList(MySQLParser.RequireListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#requireList}.
	 * @param ctx the parse tree
	 */
	void exitRequireList(MySQLParser.RequireListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#requireListElement}.
	 * @param ctx the parse tree
	 */
	void enterRequireListElement(MySQLParser.RequireListElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#requireListElement}.
	 * @param ctx the parse tree
	 */
	void exitRequireListElement(MySQLParser.RequireListElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#grantOption}.
	 * @param ctx the parse tree
	 */
	void enterGrantOption(MySQLParser.GrantOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#grantOption}.
	 * @param ctx the parse tree
	 */
	void exitGrantOption(MySQLParser.GrantOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setRole}.
	 * @param ctx the parse tree
	 */
	void enterSetRole(MySQLParser.SetRoleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setRole}.
	 * @param ctx the parse tree
	 */
	void exitSetRole(MySQLParser.SetRoleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleList}.
	 * @param ctx the parse tree
	 */
	void enterRoleList(MySQLParser.RoleListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleList}.
	 * @param ctx the parse tree
	 */
	void exitRoleList(MySQLParser.RoleListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#role}.
	 * @param ctx the parse tree
	 */
	void enterRole(MySQLParser.RoleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#role}.
	 * @param ctx the parse tree
	 */
	void exitRole(MySQLParser.RoleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableAdministrationStatement}.
	 * @param ctx the parse tree
	 */
	void enterTableAdministrationStatement(MySQLParser.TableAdministrationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableAdministrationStatement}.
	 * @param ctx the parse tree
	 */
	void exitTableAdministrationStatement(MySQLParser.TableAdministrationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#histogram}.
	 * @param ctx the parse tree
	 */
	void enterHistogram(MySQLParser.HistogramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#histogram}.
	 * @param ctx the parse tree
	 */
	void exitHistogram(MySQLParser.HistogramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#checkOption}.
	 * @param ctx the parse tree
	 */
	void enterCheckOption(MySQLParser.CheckOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#checkOption}.
	 * @param ctx the parse tree
	 */
	void exitCheckOption(MySQLParser.CheckOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#repairType}.
	 * @param ctx the parse tree
	 */
	void enterRepairType(MySQLParser.RepairTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#repairType}.
	 * @param ctx the parse tree
	 */
	void exitRepairType(MySQLParser.RepairTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#installUninstallStatment}.
	 * @param ctx the parse tree
	 */
	void enterInstallUninstallStatment(MySQLParser.InstallUninstallStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#installUninstallStatment}.
	 * @param ctx the parse tree
	 */
	void exitInstallUninstallStatment(MySQLParser.InstallUninstallStatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(MySQLParser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(MySQLParser.SetStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#optionValueNoOptionType}.
	 * @param ctx the parse tree
	 */
	void enterOptionValueNoOptionType(MySQLParser.OptionValueNoOptionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#optionValueNoOptionType}.
	 * @param ctx the parse tree
	 */
	void exitOptionValueNoOptionType(MySQLParser.OptionValueNoOptionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setSystemVariable}.
	 * @param ctx the parse tree
	 */
	void enterSetSystemVariable(MySQLParser.SetSystemVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setSystemVariable}.
	 * @param ctx the parse tree
	 */
	void exitSetSystemVariable(MySQLParser.SetSystemVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#optionValueFollowingOptionType}.
	 * @param ctx the parse tree
	 */
	void enterOptionValueFollowingOptionType(MySQLParser.OptionValueFollowingOptionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#optionValueFollowingOptionType}.
	 * @param ctx the parse tree
	 */
	void exitOptionValueFollowingOptionType(MySQLParser.OptionValueFollowingOptionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setExprOrDefault}.
	 * @param ctx the parse tree
	 */
	void enterSetExprOrDefault(MySQLParser.SetExprOrDefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setExprOrDefault}.
	 * @param ctx the parse tree
	 */
	void exitSetExprOrDefault(MySQLParser.SetExprOrDefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#optionValueList}.
	 * @param ctx the parse tree
	 */
	void enterOptionValueList(MySQLParser.OptionValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#optionValueList}.
	 * @param ctx the parse tree
	 */
	void exitOptionValueList(MySQLParser.OptionValueListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#optionValue}.
	 * @param ctx the parse tree
	 */
	void enterOptionValue(MySQLParser.OptionValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#optionValue}.
	 * @param ctx the parse tree
	 */
	void exitOptionValue(MySQLParser.OptionValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#showStatement}.
	 * @param ctx the parse tree
	 */
	void enterShowStatement(MySQLParser.ShowStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#showStatement}.
	 * @param ctx the parse tree
	 */
	void exitShowStatement(MySQLParser.ShowStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#showCommandType}.
	 * @param ctx the parse tree
	 */
	void enterShowCommandType(MySQLParser.ShowCommandTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#showCommandType}.
	 * @param ctx the parse tree
	 */
	void exitShowCommandType(MySQLParser.ShowCommandTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#nonBlocking}.
	 * @param ctx the parse tree
	 */
	void enterNonBlocking(MySQLParser.NonBlockingContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#nonBlocking}.
	 * @param ctx the parse tree
	 */
	void exitNonBlocking(MySQLParser.NonBlockingContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fromOrIn}.
	 * @param ctx the parse tree
	 */
	void enterFromOrIn(MySQLParser.FromOrInContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fromOrIn}.
	 * @param ctx the parse tree
	 */
	void exitFromOrIn(MySQLParser.FromOrInContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#inDb}.
	 * @param ctx the parse tree
	 */
	void enterInDb(MySQLParser.InDbContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#inDb}.
	 * @param ctx the parse tree
	 */
	void exitInDb(MySQLParser.InDbContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#profileType}.
	 * @param ctx the parse tree
	 */
	void enterProfileType(MySQLParser.ProfileTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#profileType}.
	 * @param ctx the parse tree
	 */
	void exitProfileType(MySQLParser.ProfileTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#otherAdministrativeStatement}.
	 * @param ctx the parse tree
	 */
	void enterOtherAdministrativeStatement(MySQLParser.OtherAdministrativeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#otherAdministrativeStatement}.
	 * @param ctx the parse tree
	 */
	void exitOtherAdministrativeStatement(MySQLParser.OtherAdministrativeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyCacheListOrParts}.
	 * @param ctx the parse tree
	 */
	void enterKeyCacheListOrParts(MySQLParser.KeyCacheListOrPartsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyCacheListOrParts}.
	 * @param ctx the parse tree
	 */
	void exitKeyCacheListOrParts(MySQLParser.KeyCacheListOrPartsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyCacheList}.
	 * @param ctx the parse tree
	 */
	void enterKeyCacheList(MySQLParser.KeyCacheListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyCacheList}.
	 * @param ctx the parse tree
	 */
	void exitKeyCacheList(MySQLParser.KeyCacheListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#assignToKeycache}.
	 * @param ctx the parse tree
	 */
	void enterAssignToKeycache(MySQLParser.AssignToKeycacheContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#assignToKeycache}.
	 * @param ctx the parse tree
	 */
	void exitAssignToKeycache(MySQLParser.AssignToKeycacheContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#assignToKeycachePartition}.
	 * @param ctx the parse tree
	 */
	void enterAssignToKeycachePartition(MySQLParser.AssignToKeycachePartitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#assignToKeycachePartition}.
	 * @param ctx the parse tree
	 */
	void exitAssignToKeycachePartition(MySQLParser.AssignToKeycachePartitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#cacheKeyList}.
	 * @param ctx the parse tree
	 */
	void enterCacheKeyList(MySQLParser.CacheKeyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#cacheKeyList}.
	 * @param ctx the parse tree
	 */
	void exitCacheKeyList(MySQLParser.CacheKeyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyUsageElement}.
	 * @param ctx the parse tree
	 */
	void enterKeyUsageElement(MySQLParser.KeyUsageElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyUsageElement}.
	 * @param ctx the parse tree
	 */
	void exitKeyUsageElement(MySQLParser.KeyUsageElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyUsageList}.
	 * @param ctx the parse tree
	 */
	void enterKeyUsageList(MySQLParser.KeyUsageListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyUsageList}.
	 * @param ctx the parse tree
	 */
	void exitKeyUsageList(MySQLParser.KeyUsageListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#flushOption}.
	 * @param ctx the parse tree
	 */
	void enterFlushOption(MySQLParser.FlushOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#flushOption}.
	 * @param ctx the parse tree
	 */
	void exitFlushOption(MySQLParser.FlushOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#logType}.
	 * @param ctx the parse tree
	 */
	void enterLogType(MySQLParser.LogTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#logType}.
	 * @param ctx the parse tree
	 */
	void exitLogType(MySQLParser.LogTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#flushTables}.
	 * @param ctx the parse tree
	 */
	void enterFlushTables(MySQLParser.FlushTablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#flushTables}.
	 * @param ctx the parse tree
	 */
	void exitFlushTables(MySQLParser.FlushTablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#flushTablesOptions}.
	 * @param ctx the parse tree
	 */
	void enterFlushTablesOptions(MySQLParser.FlushTablesOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#flushTablesOptions}.
	 * @param ctx the parse tree
	 */
	void exitFlushTablesOptions(MySQLParser.FlushTablesOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#preloadTail}.
	 * @param ctx the parse tree
	 */
	void enterPreloadTail(MySQLParser.PreloadTailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#preloadTail}.
	 * @param ctx the parse tree
	 */
	void exitPreloadTail(MySQLParser.PreloadTailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#preloadList}.
	 * @param ctx the parse tree
	 */
	void enterPreloadList(MySQLParser.PreloadListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#preloadList}.
	 * @param ctx the parse tree
	 */
	void exitPreloadList(MySQLParser.PreloadListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#preloadKeys}.
	 * @param ctx the parse tree
	 */
	void enterPreloadKeys(MySQLParser.PreloadKeysContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#preloadKeys}.
	 * @param ctx the parse tree
	 */
	void exitPreloadKeys(MySQLParser.PreloadKeysContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#adminPartition}.
	 * @param ctx the parse tree
	 */
	void enterAdminPartition(MySQLParser.AdminPartitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#adminPartition}.
	 * @param ctx the parse tree
	 */
	void exitAdminPartition(MySQLParser.AdminPartitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resourceGroupManagement}.
	 * @param ctx the parse tree
	 */
	void enterResourceGroupManagement(MySQLParser.ResourceGroupManagementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resourceGroupManagement}.
	 * @param ctx the parse tree
	 */
	void exitResourceGroupManagement(MySQLParser.ResourceGroupManagementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createResourceGroup}.
	 * @param ctx the parse tree
	 */
	void enterCreateResourceGroup(MySQLParser.CreateResourceGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createResourceGroup}.
	 * @param ctx the parse tree
	 */
	void exitCreateResourceGroup(MySQLParser.CreateResourceGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resourceGroupVcpuList}.
	 * @param ctx the parse tree
	 */
	void enterResourceGroupVcpuList(MySQLParser.ResourceGroupVcpuListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resourceGroupVcpuList}.
	 * @param ctx the parse tree
	 */
	void exitResourceGroupVcpuList(MySQLParser.ResourceGroupVcpuListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#vcpuNumOrRange}.
	 * @param ctx the parse tree
	 */
	void enterVcpuNumOrRange(MySQLParser.VcpuNumOrRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#vcpuNumOrRange}.
	 * @param ctx the parse tree
	 */
	void exitVcpuNumOrRange(MySQLParser.VcpuNumOrRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resourceGroupPriority}.
	 * @param ctx the parse tree
	 */
	void enterResourceGroupPriority(MySQLParser.ResourceGroupPriorityContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resourceGroupPriority}.
	 * @param ctx the parse tree
	 */
	void exitResourceGroupPriority(MySQLParser.ResourceGroupPriorityContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resourceGroupEnableDisable}.
	 * @param ctx the parse tree
	 */
	void enterResourceGroupEnableDisable(MySQLParser.ResourceGroupEnableDisableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resourceGroupEnableDisable}.
	 * @param ctx the parse tree
	 */
	void exitResourceGroupEnableDisable(MySQLParser.ResourceGroupEnableDisableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#alterResourceGroup}.
	 * @param ctx the parse tree
	 */
	void enterAlterResourceGroup(MySQLParser.AlterResourceGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#alterResourceGroup}.
	 * @param ctx the parse tree
	 */
	void exitAlterResourceGroup(MySQLParser.AlterResourceGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setResourceGroup}.
	 * @param ctx the parse tree
	 */
	void enterSetResourceGroup(MySQLParser.SetResourceGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setResourceGroup}.
	 * @param ctx the parse tree
	 */
	void exitSetResourceGroup(MySQLParser.SetResourceGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#threadIdList}.
	 * @param ctx the parse tree
	 */
	void enterThreadIdList(MySQLParser.ThreadIdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#threadIdList}.
	 * @param ctx the parse tree
	 */
	void exitThreadIdList(MySQLParser.ThreadIdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dropResourceGroup}.
	 * @param ctx the parse tree
	 */
	void enterDropResourceGroup(MySQLParser.DropResourceGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dropResourceGroup}.
	 * @param ctx the parse tree
	 */
	void exitDropResourceGroup(MySQLParser.DropResourceGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#utilityStatement}.
	 * @param ctx the parse tree
	 */
	void enterUtilityStatement(MySQLParser.UtilityStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#utilityStatement}.
	 * @param ctx the parse tree
	 */
	void exitUtilityStatement(MySQLParser.UtilityStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#describeCommand}.
	 * @param ctx the parse tree
	 */
	void enterDescribeCommand(MySQLParser.DescribeCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#describeCommand}.
	 * @param ctx the parse tree
	 */
	void exitDescribeCommand(MySQLParser.DescribeCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#explainCommand}.
	 * @param ctx the parse tree
	 */
	void enterExplainCommand(MySQLParser.ExplainCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#explainCommand}.
	 * @param ctx the parse tree
	 */
	void exitExplainCommand(MySQLParser.ExplainCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#explainableStatement}.
	 * @param ctx the parse tree
	 */
	void enterExplainableStatement(MySQLParser.ExplainableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#explainableStatement}.
	 * @param ctx the parse tree
	 */
	void exitExplainableStatement(MySQLParser.ExplainableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#helpCommand}.
	 * @param ctx the parse tree
	 */
	void enterHelpCommand(MySQLParser.HelpCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#helpCommand}.
	 * @param ctx the parse tree
	 */
	void exitHelpCommand(MySQLParser.HelpCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#useCommand}.
	 * @param ctx the parse tree
	 */
	void enterUseCommand(MySQLParser.UseCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#useCommand}.
	 * @param ctx the parse tree
	 */
	void exitUseCommand(MySQLParser.UseCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#restartServer}.
	 * @param ctx the parse tree
	 */
	void enterRestartServer(MySQLParser.RestartServerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#restartServer}.
	 * @param ctx the parse tree
	 */
	void exitRestartServer(MySQLParser.RestartServerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprOr}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprOr(MySQLParser.ExprOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprOr}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprOr(MySQLParser.ExprOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprNot}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprNot(MySQLParser.ExprNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprNot}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprNot(MySQLParser.ExprNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprIs}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprIs(MySQLParser.ExprIsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprIs}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprIs(MySQLParser.ExprIsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprAnd}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprAnd(MySQLParser.ExprAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprAnd}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprAnd(MySQLParser.ExprAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprXor}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprXor(MySQLParser.ExprXorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprXor}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprXor(MySQLParser.ExprXorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExprPredicate}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExprPredicate(MySQLParser.PrimaryExprPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExprPredicate}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExprPredicate(MySQLParser.PrimaryExprPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExprCompare}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExprCompare(MySQLParser.PrimaryExprCompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExprCompare}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExprCompare(MySQLParser.PrimaryExprCompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExprAllAny}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExprAllAny(MySQLParser.PrimaryExprAllAnyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExprAllAny}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExprAllAny(MySQLParser.PrimaryExprAllAnyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExprIsNull}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExprIsNull(MySQLParser.PrimaryExprIsNullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExprIsNull}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExprIsNull(MySQLParser.PrimaryExprIsNullContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(MySQLParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(MySQLParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(MySQLParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(MySQLParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicateExprIn}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void enterPredicateExprIn(MySQLParser.PredicateExprInContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicateExprIn}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void exitPredicateExprIn(MySQLParser.PredicateExprInContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicateExprBetween}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void enterPredicateExprBetween(MySQLParser.PredicateExprBetweenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicateExprBetween}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void exitPredicateExprBetween(MySQLParser.PredicateExprBetweenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicateExprLike}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void enterPredicateExprLike(MySQLParser.PredicateExprLikeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicateExprLike}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void exitPredicateExprLike(MySQLParser.PredicateExprLikeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicateExprRegex}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void enterPredicateExprRegex(MySQLParser.PredicateExprRegexContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicateExprRegex}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 */
	void exitPredicateExprRegex(MySQLParser.PredicateExprRegexContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#bitExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitExpr(MySQLParser.BitExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#bitExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitExpr(MySQLParser.BitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprConvert}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprConvert(MySQLParser.SimpleExprConvertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprConvert}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprConvert(MySQLParser.SimpleExprConvertContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprVariable}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprVariable(MySQLParser.SimpleExprVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprVariable}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprVariable(MySQLParser.SimpleExprVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprCast}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprCast(MySQLParser.SimpleExprCastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprCast}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprCast(MySQLParser.SimpleExprCastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprUnary}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprUnary(MySQLParser.SimpleExprUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprUnary}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprUnary(MySQLParser.SimpleExprUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprOdbc}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprOdbc(MySQLParser.SimpleExprOdbcContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprOdbc}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprOdbc(MySQLParser.SimpleExprOdbcContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprRuntimeFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprRuntimeFunction(MySQLParser.SimpleExprRuntimeFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprRuntimeFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprRuntimeFunction(MySQLParser.SimpleExprRuntimeFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprFunction(MySQLParser.SimpleExprFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprFunction(MySQLParser.SimpleExprFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprCollate}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprCollate(MySQLParser.SimpleExprCollateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprCollate}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprCollate(MySQLParser.SimpleExprCollateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprMatch}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprMatch(MySQLParser.SimpleExprMatchContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprMatch}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprMatch(MySQLParser.SimpleExprMatchContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprWindowingFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprWindowingFunction(MySQLParser.SimpleExprWindowingFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprWindowingFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprWindowingFunction(MySQLParser.SimpleExprWindowingFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprBinary}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprBinary(MySQLParser.SimpleExprBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprBinary}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprBinary(MySQLParser.SimpleExprBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprColumnRef}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprColumnRef(MySQLParser.SimpleExprColumnRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprColumnRef}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprColumnRef(MySQLParser.SimpleExprColumnRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprParamMarker}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprParamMarker(MySQLParser.SimpleExprParamMarkerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprParamMarker}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprParamMarker(MySQLParser.SimpleExprParamMarkerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprSum}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprSum(MySQLParser.SimpleExprSumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprSum}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprSum(MySQLParser.SimpleExprSumContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprConvertUsing}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprConvertUsing(MySQLParser.SimpleExprConvertUsingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprConvertUsing}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprConvertUsing(MySQLParser.SimpleExprConvertUsingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprSubQuery}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprSubQuery(MySQLParser.SimpleExprSubQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprSubQuery}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprSubQuery(MySQLParser.SimpleExprSubQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprGroupingOperation}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprGroupingOperation(MySQLParser.SimpleExprGroupingOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprGroupingOperation}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprGroupingOperation(MySQLParser.SimpleExprGroupingOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprNot}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprNot(MySQLParser.SimpleExprNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprNot}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprNot(MySQLParser.SimpleExprNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprValues}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprValues(MySQLParser.SimpleExprValuesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprValues}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprValues(MySQLParser.SimpleExprValuesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprDefault}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprDefault(MySQLParser.SimpleExprDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprDefault}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprDefault(MySQLParser.SimpleExprDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprList}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprList(MySQLParser.SimpleExprListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprList}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprList(MySQLParser.SimpleExprListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprInterval}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprInterval(MySQLParser.SimpleExprIntervalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprInterval}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprInterval(MySQLParser.SimpleExprIntervalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprCase}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprCase(MySQLParser.SimpleExprCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprCase}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprCase(MySQLParser.SimpleExprCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprConcat}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprConcat(MySQLParser.SimpleExprConcatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprConcat}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprConcat(MySQLParser.SimpleExprConcatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleExprLiteral}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExprLiteral(MySQLParser.SimpleExprLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleExprLiteral}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExprLiteral(MySQLParser.SimpleExprLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#jsonOperator}.
	 * @param ctx the parse tree
	 */
	void enterJsonOperator(MySQLParser.JsonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#jsonOperator}.
	 * @param ctx the parse tree
	 */
	void exitJsonOperator(MySQLParser.JsonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#sumExpr}.
	 * @param ctx the parse tree
	 */
	void enterSumExpr(MySQLParser.SumExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#sumExpr}.
	 * @param ctx the parse tree
	 */
	void exitSumExpr(MySQLParser.SumExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#groupingOperation}.
	 * @param ctx the parse tree
	 */
	void enterGroupingOperation(MySQLParser.GroupingOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#groupingOperation}.
	 * @param ctx the parse tree
	 */
	void exitGroupingOperation(MySQLParser.GroupingOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterWindowFunctionCall(MySQLParser.WindowFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitWindowFunctionCall(MySQLParser.WindowFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowingClause}.
	 * @param ctx the parse tree
	 */
	void enterWindowingClause(MySQLParser.WindowingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowingClause}.
	 * @param ctx the parse tree
	 */
	void exitWindowingClause(MySQLParser.WindowingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#leadLagInfo}.
	 * @param ctx the parse tree
	 */
	void enterLeadLagInfo(MySQLParser.LeadLagInfoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#leadLagInfo}.
	 * @param ctx the parse tree
	 */
	void exitLeadLagInfo(MySQLParser.LeadLagInfoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#nullTreatment}.
	 * @param ctx the parse tree
	 */
	void enterNullTreatment(MySQLParser.NullTreatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#nullTreatment}.
	 * @param ctx the parse tree
	 */
	void exitNullTreatment(MySQLParser.NullTreatmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#jsonFunction}.
	 * @param ctx the parse tree
	 */
	void enterJsonFunction(MySQLParser.JsonFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#jsonFunction}.
	 * @param ctx the parse tree
	 */
	void exitJsonFunction(MySQLParser.JsonFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#inSumExpr}.
	 * @param ctx the parse tree
	 */
	void enterInSumExpr(MySQLParser.InSumExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#inSumExpr}.
	 * @param ctx the parse tree
	 */
	void exitInSumExpr(MySQLParser.InSumExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#identListArg}.
	 * @param ctx the parse tree
	 */
	void enterIdentListArg(MySQLParser.IdentListArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#identListArg}.
	 * @param ctx the parse tree
	 */
	void exitIdentListArg(MySQLParser.IdentListArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#identList}.
	 * @param ctx the parse tree
	 */
	void enterIdentList(MySQLParser.IdentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#identList}.
	 * @param ctx the parse tree
	 */
	void exitIdentList(MySQLParser.IdentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fulltextOptions}.
	 * @param ctx the parse tree
	 */
	void enterFulltextOptions(MySQLParser.FulltextOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fulltextOptions}.
	 * @param ctx the parse tree
	 */
	void exitFulltextOptions(MySQLParser.FulltextOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#runtimeFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterRuntimeFunctionCall(MySQLParser.RuntimeFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#runtimeFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitRuntimeFunctionCall(MySQLParser.RuntimeFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#geometryFunction}.
	 * @param ctx the parse tree
	 */
	void enterGeometryFunction(MySQLParser.GeometryFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#geometryFunction}.
	 * @param ctx the parse tree
	 */
	void exitGeometryFunction(MySQLParser.GeometryFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#timeFunctionParameters}.
	 * @param ctx the parse tree
	 */
	void enterTimeFunctionParameters(MySQLParser.TimeFunctionParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#timeFunctionParameters}.
	 * @param ctx the parse tree
	 */
	void exitTimeFunctionParameters(MySQLParser.TimeFunctionParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fractionalPrecision}.
	 * @param ctx the parse tree
	 */
	void enterFractionalPrecision(MySQLParser.FractionalPrecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fractionalPrecision}.
	 * @param ctx the parse tree
	 */
	void exitFractionalPrecision(MySQLParser.FractionalPrecisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#weightStringLevels}.
	 * @param ctx the parse tree
	 */
	void enterWeightStringLevels(MySQLParser.WeightStringLevelsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#weightStringLevels}.
	 * @param ctx the parse tree
	 */
	void exitWeightStringLevels(MySQLParser.WeightStringLevelsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#weightStringLevelListItem}.
	 * @param ctx the parse tree
	 */
	void enterWeightStringLevelListItem(MySQLParser.WeightStringLevelListItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#weightStringLevelListItem}.
	 * @param ctx the parse tree
	 */
	void exitWeightStringLevelListItem(MySQLParser.WeightStringLevelListItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dateTimeTtype}.
	 * @param ctx the parse tree
	 */
	void enterDateTimeTtype(MySQLParser.DateTimeTtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dateTimeTtype}.
	 * @param ctx the parse tree
	 */
	void exitDateTimeTtype(MySQLParser.DateTimeTtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#trimFunction}.
	 * @param ctx the parse tree
	 */
	void enterTrimFunction(MySQLParser.TrimFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#trimFunction}.
	 * @param ctx the parse tree
	 */
	void exitTrimFunction(MySQLParser.TrimFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#substringFunction}.
	 * @param ctx the parse tree
	 */
	void enterSubstringFunction(MySQLParser.SubstringFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#substringFunction}.
	 * @param ctx the parse tree
	 */
	void exitSubstringFunction(MySQLParser.SubstringFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(MySQLParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(MySQLParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#udfExprList}.
	 * @param ctx the parse tree
	 */
	void enterUdfExprList(MySQLParser.UdfExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#udfExprList}.
	 * @param ctx the parse tree
	 */
	void exitUdfExprList(MySQLParser.UdfExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#udfExpr}.
	 * @param ctx the parse tree
	 */
	void enterUdfExpr(MySQLParser.UdfExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#udfExpr}.
	 * @param ctx the parse tree
	 */
	void exitUdfExpr(MySQLParser.UdfExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(MySQLParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(MySQLParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#userVariable}.
	 * @param ctx the parse tree
	 */
	void enterUserVariable(MySQLParser.UserVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#userVariable}.
	 * @param ctx the parse tree
	 */
	void exitUserVariable(MySQLParser.UserVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#systemVariable}.
	 * @param ctx the parse tree
	 */
	void enterSystemVariable(MySQLParser.SystemVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#systemVariable}.
	 * @param ctx the parse tree
	 */
	void exitSystemVariable(MySQLParser.SystemVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#internalVariableName}.
	 * @param ctx the parse tree
	 */
	void enterInternalVariableName(MySQLParser.InternalVariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#internalVariableName}.
	 * @param ctx the parse tree
	 */
	void exitInternalVariableName(MySQLParser.InternalVariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhenExpression(MySQLParser.WhenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhenExpression(MySQLParser.WhenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void enterThenExpression(MySQLParser.ThenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#thenExpression}.
	 * @param ctx the parse tree
	 */
	void exitThenExpression(MySQLParser.ThenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#elseExpression}.
	 * @param ctx the parse tree
	 */
	void enterElseExpression(MySQLParser.ElseExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#elseExpression}.
	 * @param ctx the parse tree
	 */
	void exitElseExpression(MySQLParser.ElseExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#castType}.
	 * @param ctx the parse tree
	 */
	void enterCastType(MySQLParser.CastTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#castType}.
	 * @param ctx the parse tree
	 */
	void exitCastType(MySQLParser.CastTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(MySQLParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(MySQLParser.ExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#charset}.
	 * @param ctx the parse tree
	 */
	void enterCharset(MySQLParser.CharsetContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#charset}.
	 * @param ctx the parse tree
	 */
	void exitCharset(MySQLParser.CharsetContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#notRule}.
	 * @param ctx the parse tree
	 */
	void enterNotRule(MySQLParser.NotRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#notRule}.
	 * @param ctx the parse tree
	 */
	void exitNotRule(MySQLParser.NotRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#not2Rule}.
	 * @param ctx the parse tree
	 */
	void enterNot2Rule(MySQLParser.Not2RuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#not2Rule}.
	 * @param ctx the parse tree
	 */
	void exitNot2Rule(MySQLParser.Not2RuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(MySQLParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(MySQLParser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#intervalTimeStamp}.
	 * @param ctx the parse tree
	 */
	void enterIntervalTimeStamp(MySQLParser.IntervalTimeStampContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#intervalTimeStamp}.
	 * @param ctx the parse tree
	 */
	void exitIntervalTimeStamp(MySQLParser.IntervalTimeStampContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#exprListWithParentheses}.
	 * @param ctx the parse tree
	 */
	void enterExprListWithParentheses(MySQLParser.ExprListWithParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#exprListWithParentheses}.
	 * @param ctx the parse tree
	 */
	void exitExprListWithParentheses(MySQLParser.ExprListWithParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#orderOrGroupList}.
	 * @param ctx the parse tree
	 */
	void enterOrderOrGroupList(MySQLParser.OrderOrGroupListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#orderOrGroupList}.
	 * @param ctx the parse tree
	 */
	void exitOrderOrGroupList(MySQLParser.OrderOrGroupListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#orderExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrderExpression(MySQLParser.OrderExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#orderExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrderExpression(MySQLParser.OrderExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#channel}.
	 * @param ctx the parse tree
	 */
	void enterChannel(MySQLParser.ChannelContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#channel}.
	 * @param ctx the parse tree
	 */
	void exitChannel(MySQLParser.ChannelContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(MySQLParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(MySQLParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MySQLParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MySQLParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MySQLParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MySQLParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ifBody}.
	 * @param ctx the parse tree
	 */
	void enterIfBody(MySQLParser.IfBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ifBody}.
	 * @param ctx the parse tree
	 */
	void exitIfBody(MySQLParser.IfBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#thenStatement}.
	 * @param ctx the parse tree
	 */
	void enterThenStatement(MySQLParser.ThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#thenStatement}.
	 * @param ctx the parse tree
	 */
	void exitThenStatement(MySQLParser.ThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#compoundStatementList}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatementList(MySQLParser.CompoundStatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#compoundStatementList}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatementList(MySQLParser.CompoundStatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#caseStatement}.
	 * @param ctx the parse tree
	 */
	void enterCaseStatement(MySQLParser.CaseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#caseStatement}.
	 * @param ctx the parse tree
	 */
	void exitCaseStatement(MySQLParser.CaseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void enterElseStatement(MySQLParser.ElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void exitElseStatement(MySQLParser.ElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#labeledBlock}.
	 * @param ctx the parse tree
	 */
	void enterLabeledBlock(MySQLParser.LabeledBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#labeledBlock}.
	 * @param ctx the parse tree
	 */
	void exitLabeledBlock(MySQLParser.LabeledBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#unlabeledBlock}.
	 * @param ctx the parse tree
	 */
	void enterUnlabeledBlock(MySQLParser.UnlabeledBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#unlabeledBlock}.
	 * @param ctx the parse tree
	 */
	void exitUnlabeledBlock(MySQLParser.UnlabeledBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(MySQLParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(MySQLParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#beginEndBlock}.
	 * @param ctx the parse tree
	 */
	void enterBeginEndBlock(MySQLParser.BeginEndBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#beginEndBlock}.
	 * @param ctx the parse tree
	 */
	void exitBeginEndBlock(MySQLParser.BeginEndBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#labeledControl}.
	 * @param ctx the parse tree
	 */
	void enterLabeledControl(MySQLParser.LabeledControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#labeledControl}.
	 * @param ctx the parse tree
	 */
	void exitLabeledControl(MySQLParser.LabeledControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#unlabeledControl}.
	 * @param ctx the parse tree
	 */
	void enterUnlabeledControl(MySQLParser.UnlabeledControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#unlabeledControl}.
	 * @param ctx the parse tree
	 */
	void exitUnlabeledControl(MySQLParser.UnlabeledControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#loopBlock}.
	 * @param ctx the parse tree
	 */
	void enterLoopBlock(MySQLParser.LoopBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#loopBlock}.
	 * @param ctx the parse tree
	 */
	void exitLoopBlock(MySQLParser.LoopBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#whileDoBlock}.
	 * @param ctx the parse tree
	 */
	void enterWhileDoBlock(MySQLParser.WhileDoBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#whileDoBlock}.
	 * @param ctx the parse tree
	 */
	void exitWhileDoBlock(MySQLParser.WhileDoBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#repeatUntilBlock}.
	 * @param ctx the parse tree
	 */
	void enterRepeatUntilBlock(MySQLParser.RepeatUntilBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#repeatUntilBlock}.
	 * @param ctx the parse tree
	 */
	void exitRepeatUntilBlock(MySQLParser.RepeatUntilBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#spDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterSpDeclarations(MySQLParser.SpDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#spDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitSpDeclarations(MySQLParser.SpDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#spDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSpDeclaration(MySQLParser.SpDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#spDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSpDeclaration(MySQLParser.SpDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MySQLParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MySQLParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#conditionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConditionDeclaration(MySQLParser.ConditionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#conditionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConditionDeclaration(MySQLParser.ConditionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#spCondition}.
	 * @param ctx the parse tree
	 */
	void enterSpCondition(MySQLParser.SpConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#spCondition}.
	 * @param ctx the parse tree
	 */
	void exitSpCondition(MySQLParser.SpConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#sqlstate}.
	 * @param ctx the parse tree
	 */
	void enterSqlstate(MySQLParser.SqlstateContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#sqlstate}.
	 * @param ctx the parse tree
	 */
	void exitSqlstate(MySQLParser.SqlstateContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#handlerDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterHandlerDeclaration(MySQLParser.HandlerDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#handlerDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitHandlerDeclaration(MySQLParser.HandlerDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#handlerCondition}.
	 * @param ctx the parse tree
	 */
	void enterHandlerCondition(MySQLParser.HandlerConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#handlerCondition}.
	 * @param ctx the parse tree
	 */
	void exitHandlerCondition(MySQLParser.HandlerConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#cursorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterCursorDeclaration(MySQLParser.CursorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#cursorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitCursorDeclaration(MySQLParser.CursorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#iterateStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterateStatement(MySQLParser.IterateStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#iterateStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterateStatement(MySQLParser.IterateStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#leaveStatement}.
	 * @param ctx the parse tree
	 */
	void enterLeaveStatement(MySQLParser.LeaveStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#leaveStatement}.
	 * @param ctx the parse tree
	 */
	void exitLeaveStatement(MySQLParser.LeaveStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#getDiagnostics}.
	 * @param ctx the parse tree
	 */
	void enterGetDiagnostics(MySQLParser.GetDiagnosticsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#getDiagnostics}.
	 * @param ctx the parse tree
	 */
	void exitGetDiagnostics(MySQLParser.GetDiagnosticsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#signalAllowedExpr}.
	 * @param ctx the parse tree
	 */
	void enterSignalAllowedExpr(MySQLParser.SignalAllowedExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#signalAllowedExpr}.
	 * @param ctx the parse tree
	 */
	void exitSignalAllowedExpr(MySQLParser.SignalAllowedExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#statementInformationItem}.
	 * @param ctx the parse tree
	 */
	void enterStatementInformationItem(MySQLParser.StatementInformationItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#statementInformationItem}.
	 * @param ctx the parse tree
	 */
	void exitStatementInformationItem(MySQLParser.StatementInformationItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#conditionInformationItem}.
	 * @param ctx the parse tree
	 */
	void enterConditionInformationItem(MySQLParser.ConditionInformationItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#conditionInformationItem}.
	 * @param ctx the parse tree
	 */
	void exitConditionInformationItem(MySQLParser.ConditionInformationItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#signalInformationItemName}.
	 * @param ctx the parse tree
	 */
	void enterSignalInformationItemName(MySQLParser.SignalInformationItemNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#signalInformationItemName}.
	 * @param ctx the parse tree
	 */
	void exitSignalInformationItemName(MySQLParser.SignalInformationItemNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#signalStatement}.
	 * @param ctx the parse tree
	 */
	void enterSignalStatement(MySQLParser.SignalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#signalStatement}.
	 * @param ctx the parse tree
	 */
	void exitSignalStatement(MySQLParser.SignalStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resignalStatement}.
	 * @param ctx the parse tree
	 */
	void enterResignalStatement(MySQLParser.ResignalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resignalStatement}.
	 * @param ctx the parse tree
	 */
	void exitResignalStatement(MySQLParser.ResignalStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#signalInformationItem}.
	 * @param ctx the parse tree
	 */
	void enterSignalInformationItem(MySQLParser.SignalInformationItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#signalInformationItem}.
	 * @param ctx the parse tree
	 */
	void exitSignalInformationItem(MySQLParser.SignalInformationItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#cursorOpen}.
	 * @param ctx the parse tree
	 */
	void enterCursorOpen(MySQLParser.CursorOpenContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#cursorOpen}.
	 * @param ctx the parse tree
	 */
	void exitCursorOpen(MySQLParser.CursorOpenContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#cursorClose}.
	 * @param ctx the parse tree
	 */
	void enterCursorClose(MySQLParser.CursorCloseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#cursorClose}.
	 * @param ctx the parse tree
	 */
	void exitCursorClose(MySQLParser.CursorCloseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#cursorFetch}.
	 * @param ctx the parse tree
	 */
	void enterCursorFetch(MySQLParser.CursorFetchContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#cursorFetch}.
	 * @param ctx the parse tree
	 */
	void exitCursorFetch(MySQLParser.CursorFetchContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#schedule}.
	 * @param ctx the parse tree
	 */
	void enterSchedule(MySQLParser.ScheduleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#schedule}.
	 * @param ctx the parse tree
	 */
	void exitSchedule(MySQLParser.ScheduleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void enterColumnDefinition(MySQLParser.ColumnDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void exitColumnDefinition(MySQLParser.ColumnDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#checkOrReferences}.
	 * @param ctx the parse tree
	 */
	void enterCheckOrReferences(MySQLParser.CheckOrReferencesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#checkOrReferences}.
	 * @param ctx the parse tree
	 */
	void exitCheckOrReferences(MySQLParser.CheckOrReferencesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#checkConstraint}.
	 * @param ctx the parse tree
	 */
	void enterCheckConstraint(MySQLParser.CheckConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#checkConstraint}.
	 * @param ctx the parse tree
	 */
	void exitCheckConstraint(MySQLParser.CheckConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableConstraintDef}.
	 * @param ctx the parse tree
	 */
	void enterTableConstraintDef(MySQLParser.TableConstraintDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableConstraintDef}.
	 * @param ctx the parse tree
	 */
	void exitTableConstraintDef(MySQLParser.TableConstraintDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFieldDefinition(MySQLParser.FieldDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFieldDefinition(MySQLParser.FieldDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#columnAttribute}.
	 * @param ctx the parse tree
	 */
	void enterColumnAttribute(MySQLParser.ColumnAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#columnAttribute}.
	 * @param ctx the parse tree
	 */
	void exitColumnAttribute(MySQLParser.ColumnAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#gcolAttribute}.
	 * @param ctx the parse tree
	 */
	void enterGcolAttribute(MySQLParser.GcolAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#gcolAttribute}.
	 * @param ctx the parse tree
	 */
	void exitGcolAttribute(MySQLParser.GcolAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#references}.
	 * @param ctx the parse tree
	 */
	void enterReferences(MySQLParser.ReferencesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#references}.
	 * @param ctx the parse tree
	 */
	void exitReferences(MySQLParser.ReferencesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#deleteOption}.
	 * @param ctx the parse tree
	 */
	void enterDeleteOption(MySQLParser.DeleteOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#deleteOption}.
	 * @param ctx the parse tree
	 */
	void exitDeleteOption(MySQLParser.DeleteOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyList}.
	 * @param ctx the parse tree
	 */
	void enterKeyList(MySQLParser.KeyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyList}.
	 * @param ctx the parse tree
	 */
	void exitKeyList(MySQLParser.KeyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#keyPart}.
	 * @param ctx the parse tree
	 */
	void enterKeyPart(MySQLParser.KeyPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#keyPart}.
	 * @param ctx the parse tree
	 */
	void exitKeyPart(MySQLParser.KeyPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexType}.
	 * @param ctx the parse tree
	 */
	void enterIndexType(MySQLParser.IndexTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexType}.
	 * @param ctx the parse tree
	 */
	void exitIndexType(MySQLParser.IndexTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexOption}.
	 * @param ctx the parse tree
	 */
	void enterIndexOption(MySQLParser.IndexOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexOption}.
	 * @param ctx the parse tree
	 */
	void exitIndexOption(MySQLParser.IndexOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#commonIndexOption}.
	 * @param ctx the parse tree
	 */
	void enterCommonIndexOption(MySQLParser.CommonIndexOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#commonIndexOption}.
	 * @param ctx the parse tree
	 */
	void exitCommonIndexOption(MySQLParser.CommonIndexOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#visibility}.
	 * @param ctx the parse tree
	 */
	void enterVisibility(MySQLParser.VisibilityContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#visibility}.
	 * @param ctx the parse tree
	 */
	void exitVisibility(MySQLParser.VisibilityContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexTypeClause}.
	 * @param ctx the parse tree
	 */
	void enterIndexTypeClause(MySQLParser.IndexTypeClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexTypeClause}.
	 * @param ctx the parse tree
	 */
	void exitIndexTypeClause(MySQLParser.IndexTypeClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fulltextIndexOption}.
	 * @param ctx the parse tree
	 */
	void enterFulltextIndexOption(MySQLParser.FulltextIndexOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fulltextIndexOption}.
	 * @param ctx the parse tree
	 */
	void exitFulltextIndexOption(MySQLParser.FulltextIndexOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#spatialIndexOption}.
	 * @param ctx the parse tree
	 */
	void enterSpatialIndexOption(MySQLParser.SpatialIndexOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#spatialIndexOption}.
	 * @param ctx the parse tree
	 */
	void exitSpatialIndexOption(MySQLParser.SpatialIndexOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dataTypeDefinition}.
	 * @param ctx the parse tree
	 */
	void enterDataTypeDefinition(MySQLParser.DataTypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dataTypeDefinition}.
	 * @param ctx the parse tree
	 */
	void exitDataTypeDefinition(MySQLParser.DataTypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(MySQLParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(MySQLParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#nchar}.
	 * @param ctx the parse tree
	 */
	void enterNchar(MySQLParser.NcharContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#nchar}.
	 * @param ctx the parse tree
	 */
	void exitNchar(MySQLParser.NcharContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#varchar}.
	 * @param ctx the parse tree
	 */
	void enterVarchar(MySQLParser.VarcharContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#varchar}.
	 * @param ctx the parse tree
	 */
	void exitVarchar(MySQLParser.VarcharContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#nvarchar}.
	 * @param ctx the parse tree
	 */
	void enterNvarchar(MySQLParser.NvarcharContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#nvarchar}.
	 * @param ctx the parse tree
	 */
	void exitNvarchar(MySQLParser.NvarcharContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldLength}.
	 * @param ctx the parse tree
	 */
	void enterFieldLength(MySQLParser.FieldLengthContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldLength}.
	 * @param ctx the parse tree
	 */
	void exitFieldLength(MySQLParser.FieldLengthContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldOptions}.
	 * @param ctx the parse tree
	 */
	void enterFieldOptions(MySQLParser.FieldOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldOptions}.
	 * @param ctx the parse tree
	 */
	void exitFieldOptions(MySQLParser.FieldOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#charsetWithOptBinary}.
	 * @param ctx the parse tree
	 */
	void enterCharsetWithOptBinary(MySQLParser.CharsetWithOptBinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#charsetWithOptBinary}.
	 * @param ctx the parse tree
	 */
	void exitCharsetWithOptBinary(MySQLParser.CharsetWithOptBinaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ascii}.
	 * @param ctx the parse tree
	 */
	void enterAscii(MySQLParser.AsciiContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ascii}.
	 * @param ctx the parse tree
	 */
	void exitAscii(MySQLParser.AsciiContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#unicode}.
	 * @param ctx the parse tree
	 */
	void enterUnicode(MySQLParser.UnicodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#unicode}.
	 * @param ctx the parse tree
	 */
	void exitUnicode(MySQLParser.UnicodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#wsNumCodepoints}.
	 * @param ctx the parse tree
	 */
	void enterWsNumCodepoints(MySQLParser.WsNumCodepointsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#wsNumCodepoints}.
	 * @param ctx the parse tree
	 */
	void exitWsNumCodepoints(MySQLParser.WsNumCodepointsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#typeDatetimePrecision}.
	 * @param ctx the parse tree
	 */
	void enterTypeDatetimePrecision(MySQLParser.TypeDatetimePrecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#typeDatetimePrecision}.
	 * @param ctx the parse tree
	 */
	void exitTypeDatetimePrecision(MySQLParser.TypeDatetimePrecisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#charsetName}.
	 * @param ctx the parse tree
	 */
	void enterCharsetName(MySQLParser.CharsetNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#charsetName}.
	 * @param ctx the parse tree
	 */
	void exitCharsetName(MySQLParser.CharsetNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#collationName}.
	 * @param ctx the parse tree
	 */
	void enterCollationName(MySQLParser.CollationNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#collationName}.
	 * @param ctx the parse tree
	 */
	void exitCollationName(MySQLParser.CollationNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createTableOptions}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableOptions(MySQLParser.CreateTableOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createTableOptions}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableOptions(MySQLParser.CreateTableOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createTableOptionsSpaceSeparated}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableOptionsSpaceSeparated(MySQLParser.CreateTableOptionsSpaceSeparatedContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createTableOptionsSpaceSeparated}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableOptionsSpaceSeparated(MySQLParser.CreateTableOptionsSpaceSeparatedContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createTableOption}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableOption(MySQLParser.CreateTableOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createTableOption}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableOption(MySQLParser.CreateTableOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ternaryOption}.
	 * @param ctx the parse tree
	 */
	void enterTernaryOption(MySQLParser.TernaryOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ternaryOption}.
	 * @param ctx the parse tree
	 */
	void exitTernaryOption(MySQLParser.TernaryOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#defaultCollation}.
	 * @param ctx the parse tree
	 */
	void enterDefaultCollation(MySQLParser.DefaultCollationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#defaultCollation}.
	 * @param ctx the parse tree
	 */
	void exitDefaultCollation(MySQLParser.DefaultCollationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#defaultCharset}.
	 * @param ctx the parse tree
	 */
	void enterDefaultCharset(MySQLParser.DefaultCharsetContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#defaultCharset}.
	 * @param ctx the parse tree
	 */
	void exitDefaultCharset(MySQLParser.DefaultCharsetContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionClause}.
	 * @param ctx the parse tree
	 */
	void enterPartitionClause(MySQLParser.PartitionClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionClause}.
	 * @param ctx the parse tree
	 */
	void exitPartitionClause(MySQLParser.PartitionClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionDefKey}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterPartitionDefKey(MySQLParser.PartitionDefKeyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionDefKey}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitPartitionDefKey(MySQLParser.PartitionDefKeyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionDefHash}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterPartitionDefHash(MySQLParser.PartitionDefHashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionDefHash}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitPartitionDefHash(MySQLParser.PartitionDefHashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionDefRangeList}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterPartitionDefRangeList(MySQLParser.PartitionDefRangeListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionDefRangeList}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitPartitionDefRangeList(MySQLParser.PartitionDefRangeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#subPartitions}.
	 * @param ctx the parse tree
	 */
	void enterSubPartitions(MySQLParser.SubPartitionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#subPartitions}.
	 * @param ctx the parse tree
	 */
	void exitSubPartitions(MySQLParser.SubPartitionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionKeyAlgorithm}.
	 * @param ctx the parse tree
	 */
	void enterPartitionKeyAlgorithm(MySQLParser.PartitionKeyAlgorithmContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionKeyAlgorithm}.
	 * @param ctx the parse tree
	 */
	void exitPartitionKeyAlgorithm(MySQLParser.PartitionKeyAlgorithmContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionDefinitions}.
	 * @param ctx the parse tree
	 */
	void enterPartitionDefinitions(MySQLParser.PartitionDefinitionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionDefinitions}.
	 * @param ctx the parse tree
	 */
	void exitPartitionDefinitions(MySQLParser.PartitionDefinitionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterPartitionDefinition(MySQLParser.PartitionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitPartitionDefinition(MySQLParser.PartitionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionValuesIn}.
	 * @param ctx the parse tree
	 */
	void enterPartitionValuesIn(MySQLParser.PartitionValuesInContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionValuesIn}.
	 * @param ctx the parse tree
	 */
	void exitPartitionValuesIn(MySQLParser.PartitionValuesInContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionOption}.
	 * @param ctx the parse tree
	 */
	void enterPartitionOption(MySQLParser.PartitionOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionOption}.
	 * @param ctx the parse tree
	 */
	void exitPartitionOption(MySQLParser.PartitionOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#subpartitionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterSubpartitionDefinition(MySQLParser.SubpartitionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#subpartitionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitSubpartitionDefinition(MySQLParser.SubpartitionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionValueItemListParen}.
	 * @param ctx the parse tree
	 */
	void enterPartitionValueItemListParen(MySQLParser.PartitionValueItemListParenContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionValueItemListParen}.
	 * @param ctx the parse tree
	 */
	void exitPartitionValueItemListParen(MySQLParser.PartitionValueItemListParenContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#partitionValueItem}.
	 * @param ctx the parse tree
	 */
	void enterPartitionValueItem(MySQLParser.PartitionValueItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#partitionValueItem}.
	 * @param ctx the parse tree
	 */
	void exitPartitionValueItem(MySQLParser.PartitionValueItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#definerClause}.
	 * @param ctx the parse tree
	 */
	void enterDefinerClause(MySQLParser.DefinerClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#definerClause}.
	 * @param ctx the parse tree
	 */
	void exitDefinerClause(MySQLParser.DefinerClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ifExists}.
	 * @param ctx the parse tree
	 */
	void enterIfExists(MySQLParser.IfExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ifExists}.
	 * @param ctx the parse tree
	 */
	void exitIfExists(MySQLParser.IfExistsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ifNotExists}.
	 * @param ctx the parse tree
	 */
	void enterIfNotExists(MySQLParser.IfNotExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ifNotExists}.
	 * @param ctx the parse tree
	 */
	void exitIfNotExists(MySQLParser.IfNotExistsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#procedureParameter}.
	 * @param ctx the parse tree
	 */
	void enterProcedureParameter(MySQLParser.ProcedureParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#procedureParameter}.
	 * @param ctx the parse tree
	 */
	void exitProcedureParameter(MySQLParser.ProcedureParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#functionParameter}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParameter(MySQLParser.FunctionParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#functionParameter}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParameter(MySQLParser.FunctionParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#typeWithOptCollate}.
	 * @param ctx the parse tree
	 */
	void enterTypeWithOptCollate(MySQLParser.TypeWithOptCollateContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#typeWithOptCollate}.
	 * @param ctx the parse tree
	 */
	void exitTypeWithOptCollate(MySQLParser.TypeWithOptCollateContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#schemaIdentifierPair}.
	 * @param ctx the parse tree
	 */
	void enterSchemaIdentifierPair(MySQLParser.SchemaIdentifierPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#schemaIdentifierPair}.
	 * @param ctx the parse tree
	 */
	void exitSchemaIdentifierPair(MySQLParser.SchemaIdentifierPairContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewRefList}.
	 * @param ctx the parse tree
	 */
	void enterViewRefList(MySQLParser.ViewRefListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewRefList}.
	 * @param ctx the parse tree
	 */
	void exitViewRefList(MySQLParser.ViewRefListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#updateList}.
	 * @param ctx the parse tree
	 */
	void enterUpdateList(MySQLParser.UpdateListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#updateList}.
	 * @param ctx the parse tree
	 */
	void exitUpdateList(MySQLParser.UpdateListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#updateElement}.
	 * @param ctx the parse tree
	 */
	void enterUpdateElement(MySQLParser.UpdateElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#updateElement}.
	 * @param ctx the parse tree
	 */
	void exitUpdateElement(MySQLParser.UpdateElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#charsetClause}.
	 * @param ctx the parse tree
	 */
	void enterCharsetClause(MySQLParser.CharsetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#charsetClause}.
	 * @param ctx the parse tree
	 */
	void exitCharsetClause(MySQLParser.CharsetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldsClause}.
	 * @param ctx the parse tree
	 */
	void enterFieldsClause(MySQLParser.FieldsClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldsClause}.
	 * @param ctx the parse tree
	 */
	void exitFieldsClause(MySQLParser.FieldsClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldTerm}.
	 * @param ctx the parse tree
	 */
	void enterFieldTerm(MySQLParser.FieldTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldTerm}.
	 * @param ctx the parse tree
	 */
	void exitFieldTerm(MySQLParser.FieldTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#linesClause}.
	 * @param ctx the parse tree
	 */
	void enterLinesClause(MySQLParser.LinesClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#linesClause}.
	 * @param ctx the parse tree
	 */
	void exitLinesClause(MySQLParser.LinesClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#lineTerm}.
	 * @param ctx the parse tree
	 */
	void enterLineTerm(MySQLParser.LineTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#lineTerm}.
	 * @param ctx the parse tree
	 */
	void exitLineTerm(MySQLParser.LineTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#userList}.
	 * @param ctx the parse tree
	 */
	void enterUserList(MySQLParser.UserListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#userList}.
	 * @param ctx the parse tree
	 */
	void exitUserList(MySQLParser.UserListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createOrAlterUserList}.
	 * @param ctx the parse tree
	 */
	void enterCreateOrAlterUserList(MySQLParser.CreateOrAlterUserListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createOrAlterUserList}.
	 * @param ctx the parse tree
	 */
	void exitCreateOrAlterUserList(MySQLParser.CreateOrAlterUserListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#createOrAlterUser}.
	 * @param ctx the parse tree
	 */
	void enterCreateOrAlterUser(MySQLParser.CreateOrAlterUserContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#createOrAlterUser}.
	 * @param ctx the parse tree
	 */
	void exitCreateOrAlterUser(MySQLParser.CreateOrAlterUserContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#user}.
	 * @param ctx the parse tree
	 */
	void enterUser(MySQLParser.UserContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#user}.
	 * @param ctx the parse tree
	 */
	void exitUser(MySQLParser.UserContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#likeClause}.
	 * @param ctx the parse tree
	 */
	void enterLikeClause(MySQLParser.LikeClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#likeClause}.
	 * @param ctx the parse tree
	 */
	void exitLikeClause(MySQLParser.LikeClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#likeOrWhere}.
	 * @param ctx the parse tree
	 */
	void enterLikeOrWhere(MySQLParser.LikeOrWhereContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#likeOrWhere}.
	 * @param ctx the parse tree
	 */
	void exitLikeOrWhere(MySQLParser.LikeOrWhereContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#onlineOption}.
	 * @param ctx the parse tree
	 */
	void enterOnlineOption(MySQLParser.OnlineOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#onlineOption}.
	 * @param ctx the parse tree
	 */
	void exitOnlineOption(MySQLParser.OnlineOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#noWriteToBinLog}.
	 * @param ctx the parse tree
	 */
	void enterNoWriteToBinLog(MySQLParser.NoWriteToBinLogContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#noWriteToBinLog}.
	 * @param ctx the parse tree
	 */
	void exitNoWriteToBinLog(MySQLParser.NoWriteToBinLogContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#usePartition}.
	 * @param ctx the parse tree
	 */
	void enterUsePartition(MySQLParser.UsePartitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#usePartition}.
	 * @param ctx the parse tree
	 */
	void exitUsePartition(MySQLParser.UsePartitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#fieldIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterFieldIdentifier(MySQLParser.FieldIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#fieldIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitFieldIdentifier(MySQLParser.FieldIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#columnInternalRef}.
	 * @param ctx the parse tree
	 */
	void enterColumnInternalRef(MySQLParser.ColumnInternalRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#columnInternalRef}.
	 * @param ctx the parse tree
	 */
	void exitColumnInternalRef(MySQLParser.ColumnInternalRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#columnInternalRefList}.
	 * @param ctx the parse tree
	 */
	void enterColumnInternalRefList(MySQLParser.ColumnInternalRefListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#columnInternalRefList}.
	 * @param ctx the parse tree
	 */
	void exitColumnInternalRefList(MySQLParser.ColumnInternalRefListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#columnRef}.
	 * @param ctx the parse tree
	 */
	void enterColumnRef(MySQLParser.ColumnRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#columnRef}.
	 * @param ctx the parse tree
	 */
	void exitColumnRef(MySQLParser.ColumnRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#insertIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterInsertIdentifier(MySQLParser.InsertIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#insertIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitInsertIdentifier(MySQLParser.InsertIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexName}.
	 * @param ctx the parse tree
	 */
	void enterIndexName(MySQLParser.IndexNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexName}.
	 * @param ctx the parse tree
	 */
	void exitIndexName(MySQLParser.IndexNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#indexRef}.
	 * @param ctx the parse tree
	 */
	void enterIndexRef(MySQLParser.IndexRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#indexRef}.
	 * @param ctx the parse tree
	 */
	void exitIndexRef(MySQLParser.IndexRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableWild}.
	 * @param ctx the parse tree
	 */
	void enterTableWild(MySQLParser.TableWildContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableWild}.
	 * @param ctx the parse tree
	 */
	void exitTableWild(MySQLParser.TableWildContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#schemaName}.
	 * @param ctx the parse tree
	 */
	void enterSchemaName(MySQLParser.SchemaNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#schemaName}.
	 * @param ctx the parse tree
	 */
	void exitSchemaName(MySQLParser.SchemaNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#schemaRef}.
	 * @param ctx the parse tree
	 */
	void enterSchemaRef(MySQLParser.SchemaRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#schemaRef}.
	 * @param ctx the parse tree
	 */
	void exitSchemaRef(MySQLParser.SchemaRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#procedureName}.
	 * @param ctx the parse tree
	 */
	void enterProcedureName(MySQLParser.ProcedureNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#procedureName}.
	 * @param ctx the parse tree
	 */
	void exitProcedureName(MySQLParser.ProcedureNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#procedureRef}.
	 * @param ctx the parse tree
	 */
	void enterProcedureRef(MySQLParser.ProcedureRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#procedureRef}.
	 * @param ctx the parse tree
	 */
	void exitProcedureRef(MySQLParser.ProcedureRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(MySQLParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(MySQLParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#functionRef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionRef(MySQLParser.FunctionRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#functionRef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionRef(MySQLParser.FunctionRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#triggerName}.
	 * @param ctx the parse tree
	 */
	void enterTriggerName(MySQLParser.TriggerNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#triggerName}.
	 * @param ctx the parse tree
	 */
	void exitTriggerName(MySQLParser.TriggerNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#triggerRef}.
	 * @param ctx the parse tree
	 */
	void enterTriggerRef(MySQLParser.TriggerRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#triggerRef}.
	 * @param ctx the parse tree
	 */
	void exitTriggerRef(MySQLParser.TriggerRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewName}.
	 * @param ctx the parse tree
	 */
	void enterViewName(MySQLParser.ViewNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewName}.
	 * @param ctx the parse tree
	 */
	void exitViewName(MySQLParser.ViewNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#viewRef}.
	 * @param ctx the parse tree
	 */
	void enterViewRef(MySQLParser.ViewRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#viewRef}.
	 * @param ctx the parse tree
	 */
	void exitViewRef(MySQLParser.ViewRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tablespaceName}.
	 * @param ctx the parse tree
	 */
	void enterTablespaceName(MySQLParser.TablespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tablespaceName}.
	 * @param ctx the parse tree
	 */
	void exitTablespaceName(MySQLParser.TablespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tablespaceRef}.
	 * @param ctx the parse tree
	 */
	void enterTablespaceRef(MySQLParser.TablespaceRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tablespaceRef}.
	 * @param ctx the parse tree
	 */
	void exitTablespaceRef(MySQLParser.TablespaceRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#logfileGroupName}.
	 * @param ctx the parse tree
	 */
	void enterLogfileGroupName(MySQLParser.LogfileGroupNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#logfileGroupName}.
	 * @param ctx the parse tree
	 */
	void exitLogfileGroupName(MySQLParser.LogfileGroupNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#logfileGroupRef}.
	 * @param ctx the parse tree
	 */
	void enterLogfileGroupRef(MySQLParser.LogfileGroupRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#logfileGroupRef}.
	 * @param ctx the parse tree
	 */
	void exitLogfileGroupRef(MySQLParser.LogfileGroupRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#eventName}.
	 * @param ctx the parse tree
	 */
	void enterEventName(MySQLParser.EventNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#eventName}.
	 * @param ctx the parse tree
	 */
	void exitEventName(MySQLParser.EventNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#eventRef}.
	 * @param ctx the parse tree
	 */
	void enterEventRef(MySQLParser.EventRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#eventRef}.
	 * @param ctx the parse tree
	 */
	void exitEventRef(MySQLParser.EventRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#udfName}.
	 * @param ctx the parse tree
	 */
	void enterUdfName(MySQLParser.UdfNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#udfName}.
	 * @param ctx the parse tree
	 */
	void exitUdfName(MySQLParser.UdfNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#serverName}.
	 * @param ctx the parse tree
	 */
	void enterServerName(MySQLParser.ServerNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#serverName}.
	 * @param ctx the parse tree
	 */
	void exitServerName(MySQLParser.ServerNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#serverRef}.
	 * @param ctx the parse tree
	 */
	void enterServerRef(MySQLParser.ServerRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#serverRef}.
	 * @param ctx the parse tree
	 */
	void exitServerRef(MySQLParser.ServerRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#engineRef}.
	 * @param ctx the parse tree
	 */
	void enterEngineRef(MySQLParser.EngineRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#engineRef}.
	 * @param ctx the parse tree
	 */
	void exitEngineRef(MySQLParser.EngineRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void enterTableName(MySQLParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void exitTableName(MySQLParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#filterTableRef}.
	 * @param ctx the parse tree
	 */
	void enterFilterTableRef(MySQLParser.FilterTableRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#filterTableRef}.
	 * @param ctx the parse tree
	 */
	void exitFilterTableRef(MySQLParser.FilterTableRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableRefWithWildcard}.
	 * @param ctx the parse tree
	 */
	void enterTableRefWithWildcard(MySQLParser.TableRefWithWildcardContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableRefWithWildcard}.
	 * @param ctx the parse tree
	 */
	void exitTableRefWithWildcard(MySQLParser.TableRefWithWildcardContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableRef}.
	 * @param ctx the parse tree
	 */
	void enterTableRef(MySQLParser.TableRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableRef}.
	 * @param ctx the parse tree
	 */
	void exitTableRef(MySQLParser.TableRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableRefList}.
	 * @param ctx the parse tree
	 */
	void enterTableRefList(MySQLParser.TableRefListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableRefList}.
	 * @param ctx the parse tree
	 */
	void exitTableRefList(MySQLParser.TableRefListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#tableAliasRefList}.
	 * @param ctx the parse tree
	 */
	void enterTableAliasRefList(MySQLParser.TableAliasRefListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#tableAliasRefList}.
	 * @param ctx the parse tree
	 */
	void exitTableAliasRefList(MySQLParser.TableAliasRefListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#parameterName}.
	 * @param ctx the parse tree
	 */
	void enterParameterName(MySQLParser.ParameterNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#parameterName}.
	 * @param ctx the parse tree
	 */
	void exitParameterName(MySQLParser.ParameterNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#labelIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterLabelIdentifier(MySQLParser.LabelIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#labelIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitLabelIdentifier(MySQLParser.LabelIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#labelRef}.
	 * @param ctx the parse tree
	 */
	void enterLabelRef(MySQLParser.LabelRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#labelRef}.
	 * @param ctx the parse tree
	 */
	void exitLabelRef(MySQLParser.LabelRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterRoleIdentifier(MySQLParser.RoleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitRoleIdentifier(MySQLParser.RoleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleRef}.
	 * @param ctx the parse tree
	 */
	void enterRoleRef(MySQLParser.RoleRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleRef}.
	 * @param ctx the parse tree
	 */
	void exitRoleRef(MySQLParser.RoleRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#pluginRef}.
	 * @param ctx the parse tree
	 */
	void enterPluginRef(MySQLParser.PluginRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#pluginRef}.
	 * @param ctx the parse tree
	 */
	void exitPluginRef(MySQLParser.PluginRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#componentRef}.
	 * @param ctx the parse tree
	 */
	void enterComponentRef(MySQLParser.ComponentRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#componentRef}.
	 * @param ctx the parse tree
	 */
	void exitComponentRef(MySQLParser.ComponentRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#resourceGroupRef}.
	 * @param ctx the parse tree
	 */
	void enterResourceGroupRef(MySQLParser.ResourceGroupRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#resourceGroupRef}.
	 * @param ctx the parse tree
	 */
	void exitResourceGroupRef(MySQLParser.ResourceGroupRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#windowName}.
	 * @param ctx the parse tree
	 */
	void enterWindowName(MySQLParser.WindowNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#windowName}.
	 * @param ctx the parse tree
	 */
	void exitWindowName(MySQLParser.WindowNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#pureIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterPureIdentifier(MySQLParser.PureIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#pureIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitPureIdentifier(MySQLParser.PureIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(MySQLParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(MySQLParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(MySQLParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(MySQLParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#identifierListWithParentheses}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierListWithParentheses(MySQLParser.IdentifierListWithParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#identifierListWithParentheses}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierListWithParentheses(MySQLParser.IdentifierListWithParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#qualifiedIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedIdentifier(MySQLParser.QualifiedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#qualifiedIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedIdentifier(MySQLParser.QualifiedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleIdentifier(MySQLParser.SimpleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleIdentifier(MySQLParser.SimpleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#dotIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterDotIdentifier(MySQLParser.DotIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#dotIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitDotIdentifier(MySQLParser.DotIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ulong_number}.
	 * @param ctx the parse tree
	 */
	void enterUlong_number(MySQLParser.Ulong_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ulong_number}.
	 * @param ctx the parse tree
	 */
	void exitUlong_number(MySQLParser.Ulong_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#real_ulong_number}.
	 * @param ctx the parse tree
	 */
	void enterReal_ulong_number(MySQLParser.Real_ulong_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#real_ulong_number}.
	 * @param ctx the parse tree
	 */
	void exitReal_ulong_number(MySQLParser.Real_ulong_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#ulonglong_number}.
	 * @param ctx the parse tree
	 */
	void enterUlonglong_number(MySQLParser.Ulonglong_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#ulonglong_number}.
	 * @param ctx the parse tree
	 */
	void exitUlonglong_number(MySQLParser.Ulonglong_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#real_ulonglong_number}.
	 * @param ctx the parse tree
	 */
	void enterReal_ulonglong_number(MySQLParser.Real_ulonglong_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#real_ulonglong_number}.
	 * @param ctx the parse tree
	 */
	void exitReal_ulonglong_number(MySQLParser.Real_ulonglong_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MySQLParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MySQLParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#signedLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSignedLiteral(MySQLParser.SignedLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#signedLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSignedLiteral(MySQLParser.SignedLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#stringList}.
	 * @param ctx the parse tree
	 */
	void enterStringList(MySQLParser.StringListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#stringList}.
	 * @param ctx the parse tree
	 */
	void exitStringList(MySQLParser.StringListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#textStringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterTextStringLiteral(MySQLParser.TextStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#textStringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitTextStringLiteral(MySQLParser.TextStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#textString}.
	 * @param ctx the parse tree
	 */
	void enterTextString(MySQLParser.TextStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#textString}.
	 * @param ctx the parse tree
	 */
	void exitTextString(MySQLParser.TextStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#textLiteral}.
	 * @param ctx the parse tree
	 */
	void enterTextLiteral(MySQLParser.TextLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#textLiteral}.
	 * @param ctx the parse tree
	 */
	void exitTextLiteral(MySQLParser.TextLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#textStringNoLinebreak}.
	 * @param ctx the parse tree
	 */
	void enterTextStringNoLinebreak(MySQLParser.TextStringNoLinebreakContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#textStringNoLinebreak}.
	 * @param ctx the parse tree
	 */
	void exitTextStringNoLinebreak(MySQLParser.TextStringNoLinebreakContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#textStringLiteralList}.
	 * @param ctx the parse tree
	 */
	void enterTextStringLiteralList(MySQLParser.TextStringLiteralListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#textStringLiteralList}.
	 * @param ctx the parse tree
	 */
	void exitTextStringLiteralList(MySQLParser.TextStringLiteralListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#numLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumLiteral(MySQLParser.NumLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#numLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumLiteral(MySQLParser.NumLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(MySQLParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(MySQLParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(MySQLParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(MySQLParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#temporalLiteral}.
	 * @param ctx the parse tree
	 */
	void enterTemporalLiteral(MySQLParser.TemporalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#temporalLiteral}.
	 * @param ctx the parse tree
	 */
	void exitTemporalLiteral(MySQLParser.TemporalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#floatOptions}.
	 * @param ctx the parse tree
	 */
	void enterFloatOptions(MySQLParser.FloatOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#floatOptions}.
	 * @param ctx the parse tree
	 */
	void exitFloatOptions(MySQLParser.FloatOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#precision}.
	 * @param ctx the parse tree
	 */
	void enterPrecision(MySQLParser.PrecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#precision}.
	 * @param ctx the parse tree
	 */
	void exitPrecision(MySQLParser.PrecisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#textOrIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterTextOrIdentifier(MySQLParser.TextOrIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#textOrIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitTextOrIdentifier(MySQLParser.TextOrIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleIdentifierOrText}.
	 * @param ctx the parse tree
	 */
	void enterRoleIdentifierOrText(MySQLParser.RoleIdentifierOrTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleIdentifierOrText}.
	 * @param ctx the parse tree
	 */
	void exitRoleIdentifierOrText(MySQLParser.RoleIdentifierOrTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#sizeNumber}.
	 * @param ctx the parse tree
	 */
	void enterSizeNumber(MySQLParser.SizeNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#sizeNumber}.
	 * @param ctx the parse tree
	 */
	void exitSizeNumber(MySQLParser.SizeNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#parentheses}.
	 * @param ctx the parse tree
	 */
	void enterParentheses(MySQLParser.ParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#parentheses}.
	 * @param ctx the parse tree
	 */
	void exitParentheses(MySQLParser.ParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#equal}.
	 * @param ctx the parse tree
	 */
	void enterEqual(MySQLParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#equal}.
	 * @param ctx the parse tree
	 */
	void exitEqual(MySQLParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#optionType}.
	 * @param ctx the parse tree
	 */
	void enterOptionType(MySQLParser.OptionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#optionType}.
	 * @param ctx the parse tree
	 */
	void exitOptionType(MySQLParser.OptionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#varIdentType}.
	 * @param ctx the parse tree
	 */
	void enterVarIdentType(MySQLParser.VarIdentTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#varIdentType}.
	 * @param ctx the parse tree
	 */
	void exitVarIdentType(MySQLParser.VarIdentTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#setVarIdentType}.
	 * @param ctx the parse tree
	 */
	void enterSetVarIdentType(MySQLParser.SetVarIdentTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#setVarIdentType}.
	 * @param ctx the parse tree
	 */
	void exitSetVarIdentType(MySQLParser.SetVarIdentTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#identifierKeyword}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierKeyword(MySQLParser.IdentifierKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#identifierKeyword}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierKeyword(MySQLParser.IdentifierKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#labelKeyword}.
	 * @param ctx the parse tree
	 */
	void enterLabelKeyword(MySQLParser.LabelKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#labelKeyword}.
	 * @param ctx the parse tree
	 */
	void exitLabelKeyword(MySQLParser.LabelKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleOrIdentifierKeyword}.
	 * @param ctx the parse tree
	 */
	void enterRoleOrIdentifierKeyword(MySQLParser.RoleOrIdentifierKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleOrIdentifierKeyword}.
	 * @param ctx the parse tree
	 */
	void exitRoleOrIdentifierKeyword(MySQLParser.RoleOrIdentifierKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleOrLabelKeyword}.
	 * @param ctx the parse tree
	 */
	void enterRoleOrLabelKeyword(MySQLParser.RoleOrLabelKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleOrLabelKeyword}.
	 * @param ctx the parse tree
	 */
	void exitRoleOrLabelKeyword(MySQLParser.RoleOrLabelKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySQLParser#roleKeyword}.
	 * @param ctx the parse tree
	 */
	void enterRoleKeyword(MySQLParser.RoleKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySQLParser#roleKeyword}.
	 * @param ctx the parse tree
	 */
	void exitRoleKeyword(MySQLParser.RoleKeywordContext ctx);
}