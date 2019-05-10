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

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MySQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MySQLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MySQLParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(MySQLParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#simpleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStatement(MySQLParser.SimpleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterStatement(MySQLParser.AlterStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterDatabase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterDatabase(MySQLParser.AlterDatabaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterEvent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterEvent(MySQLParser.AlterEventContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterLogfileGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterLogfileGroup(MySQLParser.AlterLogfileGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterLogfileGroupOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterLogfileGroupOptions(MySQLParser.AlterLogfileGroupOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterLogfileGroupOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterLogfileGroupOption(MySQLParser.AlterLogfileGroupOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterServer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterServer(MySQLParser.AlterServerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterTable(MySQLParser.AlterTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterTableActions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterTableActions(MySQLParser.AlterTableActionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterCommandList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterCommandList(MySQLParser.AlterCommandListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterCommandsModifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterCommandsModifierList(MySQLParser.AlterCommandsModifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#standaloneAlterCommands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandaloneAlterCommands(MySQLParser.StandaloneAlterCommandsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterPartition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterPartition(MySQLParser.AlterPartitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterList(MySQLParser.AlterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterCommandsModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterCommandsModifier(MySQLParser.AlterCommandsModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterListItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterListItem(MySQLParser.AlterListItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlace(MySQLParser.PlaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#restrict}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRestrict(MySQLParser.RestrictContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterOrderList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterOrderList(MySQLParser.AlterOrderListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterAlgorithmOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterAlgorithmOption(MySQLParser.AlterAlgorithmOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterLockOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterLockOption(MySQLParser.AlterLockOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexLockAndAlgorithm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexLockAndAlgorithm(MySQLParser.IndexLockAndAlgorithmContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#withValidation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithValidation(MySQLParser.WithValidationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#removePartitioning}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRemovePartitioning(MySQLParser.RemovePartitioningContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#allOrPartitionNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllOrPartitionNameList(MySQLParser.AllOrPartitionNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#reorgPartitionRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReorgPartitionRule(MySQLParser.ReorgPartitionRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterTablespace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterTablespace(MySQLParser.AlterTablespaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterTablespaceOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterTablespaceOption(MySQLParser.AlterTablespaceOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#changeTablespaceOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChangeTablespaceOption(MySQLParser.ChangeTablespaceOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterView}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterView(MySQLParser.AlterViewContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewTail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewTail(MySQLParser.ViewTailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewSelect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewSelect(MySQLParser.ViewSelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewCheckOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewCheckOption(MySQLParser.ViewCheckOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateStatement(MySQLParser.CreateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createDatabase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateDatabase(MySQLParser.CreateDatabaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createDatabaseOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateDatabaseOption(MySQLParser.CreateDatabaseOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTable(MySQLParser.CreateTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableElementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableElementList(MySQLParser.TableElementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableElement(MySQLParser.TableElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#duplicateAsQueryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuplicateAsQueryExpression(MySQLParser.DuplicateAsQueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#queryExpressionOrParens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpressionOrParens(MySQLParser.QueryExpressionOrParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createWithDefiner}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateWithDefiner(MySQLParser.CreateWithDefinerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createRoutine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateRoutine(MySQLParser.CreateRoutineContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createProcedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateProcedure(MySQLParser.CreateProcedureContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateFunction(MySQLParser.CreateFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createUdf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateUdf(MySQLParser.CreateUdfContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#routineCreateOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutineCreateOption(MySQLParser.RoutineCreateOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#routineAlterOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutineAlterOptions(MySQLParser.RoutineAlterOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#routineOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutineOption(MySQLParser.RoutineOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateIndex(MySQLParser.CreateIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexNameAndType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexNameAndType(MySQLParser.IndexNameAndTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createIndexTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateIndexTarget(MySQLParser.CreateIndexTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createLogfileGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateLogfileGroup(MySQLParser.CreateLogfileGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#logfileGroupOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogfileGroupOptions(MySQLParser.LogfileGroupOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#logfileGroupOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogfileGroupOption(MySQLParser.LogfileGroupOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createServer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateServer(MySQLParser.CreateServerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#serverOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitServerOptions(MySQLParser.ServerOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#serverOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitServerOption(MySQLParser.ServerOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createTablespace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTablespace(MySQLParser.CreateTablespaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tablespaceOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTablespaceOptions(MySQLParser.TablespaceOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tablespaceOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTablespaceOption(MySQLParser.TablespaceOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createView}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateView(MySQLParser.CreateViewContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewReplaceOrAlgorithm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewReplaceOrAlgorithm(MySQLParser.ViewReplaceOrAlgorithmContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewAlgorithm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewAlgorithm(MySQLParser.ViewAlgorithmContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewSuid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewSuid(MySQLParser.ViewSuidContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createTrigger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTrigger(MySQLParser.CreateTriggerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#triggerFollowsPrecedesClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriggerFollowsPrecedesClause(MySQLParser.TriggerFollowsPrecedesClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createEvent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateEvent(MySQLParser.CreateEventContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateRole(MySQLParser.CreateRoleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createSpatialReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateSpatialReference(MySQLParser.CreateSpatialReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#srsAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSrsAttribute(MySQLParser.SrsAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropStatement(MySQLParser.DropStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropDatabase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropDatabase(MySQLParser.DropDatabaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropEvent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropEvent(MySQLParser.DropEventContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropFunction(MySQLParser.DropFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropProcedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropProcedure(MySQLParser.DropProcedureContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropIndex(MySQLParser.DropIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropLogfileGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropLogfileGroup(MySQLParser.DropLogfileGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropLogfileGroupOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropLogfileGroupOption(MySQLParser.DropLogfileGroupOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropServer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropServer(MySQLParser.DropServerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropTable(MySQLParser.DropTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropTableSpace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropTableSpace(MySQLParser.DropTableSpaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropTrigger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropTrigger(MySQLParser.DropTriggerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropView}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropView(MySQLParser.DropViewContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropRole(MySQLParser.DropRoleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropSpatialReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropSpatialReference(MySQLParser.DropSpatialReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#renameTableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameTableStatement(MySQLParser.RenameTableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#renamePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenamePair(MySQLParser.RenamePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#truncateTableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruncateTableStatement(MySQLParser.TruncateTableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(MySQLParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#callStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStatement(MySQLParser.CallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatement(MySQLParser.DeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionDelete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDelete(MySQLParser.PartitionDeleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#deleteStatementOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatementOption(MySQLParser.DeleteStatementOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#doStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStatement(MySQLParser.DoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#handlerStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandlerStatement(MySQLParser.HandlerStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#handlerReadOrScan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandlerReadOrScan(MySQLParser.HandlerReadOrScanContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(MySQLParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertLockOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertLockOption(MySQLParser.InsertLockOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertFromConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertFromConstructor(MySQLParser.InsertFromConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFields(MySQLParser.FieldsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertValues}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertValues(MySQLParser.InsertValuesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertQueryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertQueryExpression(MySQLParser.InsertQueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueList(MySQLParser.ValueListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code values}
	 * labeled alternative in {@link MySQLParser#exprexprexprexprexprboolPriboolPriboolPriboolPripredicateOperationspredicateOperationspredicateOperationspredicateOperationssimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprsimpleExprpartitionTypeDefpartitionTypeDefpartitionTypeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValues(MySQLParser.ValuesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertUpdateList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertUpdateList(MySQLParser.InsertUpdateListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#loadStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadStatement(MySQLParser.LoadStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dataOrXml}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataOrXml(MySQLParser.DataOrXmlContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#xmlRowsIdentifiedBy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXmlRowsIdentifiedBy(MySQLParser.XmlRowsIdentifiedByContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#loadDataFileTail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadDataFileTail(MySQLParser.LoadDataFileTailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#loadDataFileTargetList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadDataFileTargetList(MySQLParser.LoadDataFileTargetListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldOrVariableList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldOrVariableList(MySQLParser.FieldOrVariableListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#replaceStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReplaceStatement(MySQLParser.ReplaceStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatement(MySQLParser.SelectStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#selectStatementWithInto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatementWithInto(MySQLParser.SelectStatementWithIntoContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#queryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpression(MySQLParser.QueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#queryExpressionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpressionBody(MySQLParser.QueryExpressionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#queryExpressionParens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpressionParens(MySQLParser.QueryExpressionParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#querySpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySpecification(MySQLParser.QuerySpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#subquery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubquery(MySQLParser.SubqueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#querySpecOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySpecOption(MySQLParser.QuerySpecOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#limitClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitClause(MySQLParser.LimitClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#simpleLimitClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleLimitClause(MySQLParser.SimpleLimitClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#limitOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitOptions(MySQLParser.LimitOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#limitOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitOption(MySQLParser.LimitOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#intoClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntoClause(MySQLParser.IntoClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#procedureAnalyseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureAnalyseClause(MySQLParser.ProcedureAnalyseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#havingClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHavingClause(MySQLParser.HavingClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowClause(MySQLParser.WindowClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowDefinition(MySQLParser.WindowDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowSpec(MySQLParser.WindowSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowSpecDetails}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowSpecDetails(MySQLParser.WindowSpecDetailsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameClause(MySQLParser.WindowFrameClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameUnits}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameUnits(MySQLParser.WindowFrameUnitsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameExtent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameExtent(MySQLParser.WindowFrameExtentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameStart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameStart(MySQLParser.WindowFrameStartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameBetween}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameBetween(MySQLParser.WindowFrameBetweenContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameBound(MySQLParser.WindowFrameBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFrameExclusion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrameExclusion(MySQLParser.WindowFrameExclusionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#withClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithClause(MySQLParser.WithClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#commonTableExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommonTableExpression(MySQLParser.CommonTableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#groupByClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByClause(MySQLParser.GroupByClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#olapOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOlapOption(MySQLParser.OlapOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#orderClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderClause(MySQLParser.OrderClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#direction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirection(MySQLParser.DirectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fromClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromClause(MySQLParser.FromClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableReferenceList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableReferenceList(MySQLParser.TableReferenceListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#selectOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectOption(MySQLParser.SelectOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lockingClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockingClause(MySQLParser.LockingClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lockStrengh}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockStrengh(MySQLParser.LockStrenghContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lockedRowAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockedRowAction(MySQLParser.LockedRowActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#selectItemList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectItemList(MySQLParser.SelectItemListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#selectItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectItem(MySQLParser.SelectItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#selectAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectAlias(MySQLParser.SelectAliasContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(MySQLParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableReference(MySQLParser.TableReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#joinedTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinedTable(MySQLParser.JoinedTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#naturalJoinType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaturalJoinType(MySQLParser.NaturalJoinTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#innerJoinType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerJoinType(MySQLParser.InnerJoinTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#outerJoinType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOuterJoinType(MySQLParser.OuterJoinTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableFactor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableFactor(MySQLParser.TableFactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#singleTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleTable(MySQLParser.SingleTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#singleTableParens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleTableParens(MySQLParser.SingleTableParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#derivedTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDerivedTable(MySQLParser.DerivedTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#joinedTableParens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinedTableParens(MySQLParser.JoinedTableParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableReferenceListParens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableReferenceListParens(MySQLParser.TableReferenceListParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableFunction(MySQLParser.TableFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#columnsClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnsClause(MySQLParser.ColumnsClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#jtColumn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJtColumn(MySQLParser.JtColumnContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#onEmptyOrError}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnEmptyOrError(MySQLParser.OnEmptyOrErrorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#onEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnEmpty(MySQLParser.OnEmptyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#onError}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnError(MySQLParser.OnErrorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#jtOnResponse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJtOnResponse(MySQLParser.JtOnResponseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#unionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionOption(MySQLParser.UnionOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAlias(MySQLParser.TableAliasContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexHintList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexHintList(MySQLParser.IndexHintListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexHint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexHint(MySQLParser.IndexHintContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexHintType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexHintType(MySQLParser.IndexHintTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyOrIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyOrIndex(MySQLParser.KeyOrIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexHintClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexHintClause(MySQLParser.IndexHintClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexList(MySQLParser.IndexListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexListElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexListElement(MySQLParser.IndexListElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#updateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateStatement(MySQLParser.UpdateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#transactionOrLockingStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransactionOrLockingStatement(MySQLParser.TransactionOrLockingStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#transactionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransactionStatement(MySQLParser.TransactionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#beginWork}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeginWork(MySQLParser.BeginWorkContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#transactionCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransactionCharacteristic(MySQLParser.TransactionCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setTransactionCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetTransactionCharacteristic(MySQLParser.SetTransactionCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#isolationLevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsolationLevel(MySQLParser.IsolationLevelContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#savepointStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSavepointStatement(MySQLParser.SavepointStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockStatement(MySQLParser.LockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockItem(MySQLParser.LockItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lockOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockOption(MySQLParser.LockOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#xaStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXaStatement(MySQLParser.XaStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#xaConvert}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXaConvert(MySQLParser.XaConvertContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#xid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXid(MySQLParser.XidContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#replicationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReplicationStatement(MySQLParser.ReplicationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resetOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResetOption(MySQLParser.ResetOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#masterResetOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMasterResetOptions(MySQLParser.MasterResetOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#replicationLoad}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReplicationLoad(MySQLParser.ReplicationLoadContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#changeMaster}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChangeMaster(MySQLParser.ChangeMasterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#changeMasterOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChangeMasterOptions(MySQLParser.ChangeMasterOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#masterOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMasterOption(MySQLParser.MasterOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#masterFileDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMasterFileDef(MySQLParser.MasterFileDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#serverIdList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitServerIdList(MySQLParser.ServerIdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#changeReplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChangeReplication(MySQLParser.ChangeReplicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterDefinition(MySQLParser.FilterDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterDbList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterDbList(MySQLParser.FilterDbListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterTableList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterTableList(MySQLParser.FilterTableListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterStringList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterStringList(MySQLParser.FilterStringListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterWildDbTableString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterWildDbTableString(MySQLParser.FilterWildDbTableStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterDbPairList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterDbPairList(MySQLParser.FilterDbPairListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#slave}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlave(MySQLParser.SlaveContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#slaveUntilOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlaveUntilOptions(MySQLParser.SlaveUntilOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#slaveConnectionOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlaveConnectionOptions(MySQLParser.SlaveConnectionOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#slaveThreadOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlaveThreadOptions(MySQLParser.SlaveThreadOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#slaveThreadOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlaveThreadOption(MySQLParser.SlaveThreadOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#groupReplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupReplication(MySQLParser.GroupReplicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#preparedStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreparedStatement(MySQLParser.PreparedStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#executeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExecuteStatement(MySQLParser.ExecuteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#executeVarList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExecuteVarList(MySQLParser.ExecuteVarListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#cloneStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloneStatement(MySQLParser.CloneStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#accountManagementStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccountManagementStatement(MySQLParser.AccountManagementStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterUser(MySQLParser.AlterUserContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterUserTail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterUserTail(MySQLParser.AlterUserTailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateUser(MySQLParser.CreateUserContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createUserTail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateUserTail(MySQLParser.CreateUserTailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#defaultRoleClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultRoleClause(MySQLParser.DefaultRoleClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#requireClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequireClause(MySQLParser.RequireClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#connectOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnectOptions(MySQLParser.ConnectOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#accountLockPasswordExpireOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccountLockPasswordExpireOptions(MySQLParser.AccountLockPasswordExpireOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropUser(MySQLParser.DropUserContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#grant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrant(MySQLParser.GrantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#grantTargetList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrantTargetList(MySQLParser.GrantTargetListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#grantOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrantOptions(MySQLParser.GrantOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#versionedRequireClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVersionedRequireClause(MySQLParser.VersionedRequireClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#renameUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameUser(MySQLParser.RenameUserContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#revoke}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRevoke(MySQLParser.RevokeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#onTypeTo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnTypeTo(MySQLParser.OnTypeToContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#aclType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAclType(MySQLParser.AclTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setPassword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetPassword(MySQLParser.SetPasswordContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleOrPrivilegesList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleOrPrivilegesList(MySQLParser.RoleOrPrivilegesListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleOrPrivilege}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleOrPrivilege(MySQLParser.RoleOrPrivilegeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#grantIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrantIdentifier(MySQLParser.GrantIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#requireList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequireList(MySQLParser.RequireListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#requireListElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequireListElement(MySQLParser.RequireListElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#grantOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrantOption(MySQLParser.GrantOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetRole(MySQLParser.SetRoleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleList(MySQLParser.RoleListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#role}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRole(MySQLParser.RoleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableAdministrationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAdministrationStatement(MySQLParser.TableAdministrationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#histogram}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHistogram(MySQLParser.HistogramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#checkOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckOption(MySQLParser.CheckOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#repairType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepairType(MySQLParser.RepairTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#installUninstallStatment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstallUninstallStatment(MySQLParser.InstallUninstallStatmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStatement(MySQLParser.SetStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#optionValueNoOptionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionValueNoOptionType(MySQLParser.OptionValueNoOptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setSystemVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetSystemVariable(MySQLParser.SetSystemVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#optionValueFollowingOptionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionValueFollowingOptionType(MySQLParser.OptionValueFollowingOptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setExprOrDefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetExprOrDefault(MySQLParser.SetExprOrDefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#optionValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionValueList(MySQLParser.OptionValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#optionValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionValue(MySQLParser.OptionValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#showStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowStatement(MySQLParser.ShowStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#showCommandType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowCommandType(MySQLParser.ShowCommandTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#nonBlocking}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonBlocking(MySQLParser.NonBlockingContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fromOrIn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromOrIn(MySQLParser.FromOrInContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#inDb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInDb(MySQLParser.InDbContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#profileType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProfileType(MySQLParser.ProfileTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#otherAdministrativeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtherAdministrativeStatement(MySQLParser.OtherAdministrativeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyCacheListOrParts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyCacheListOrParts(MySQLParser.KeyCacheListOrPartsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyCacheList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyCacheList(MySQLParser.KeyCacheListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#assignToKeycache}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignToKeycache(MySQLParser.AssignToKeycacheContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#assignToKeycachePartition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignToKeycachePartition(MySQLParser.AssignToKeycachePartitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#cacheKeyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCacheKeyList(MySQLParser.CacheKeyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyUsageElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyUsageElement(MySQLParser.KeyUsageElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyUsageList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyUsageList(MySQLParser.KeyUsageListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#flushOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlushOption(MySQLParser.FlushOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#logType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogType(MySQLParser.LogTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#flushTables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlushTables(MySQLParser.FlushTablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#flushTablesOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlushTablesOptions(MySQLParser.FlushTablesOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#preloadTail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreloadTail(MySQLParser.PreloadTailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#preloadList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreloadList(MySQLParser.PreloadListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#preloadKeys}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreloadKeys(MySQLParser.PreloadKeysContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#adminPartition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdminPartition(MySQLParser.AdminPartitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resourceGroupManagement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceGroupManagement(MySQLParser.ResourceGroupManagementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createResourceGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateResourceGroup(MySQLParser.CreateResourceGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resourceGroupVcpuList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceGroupVcpuList(MySQLParser.ResourceGroupVcpuListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#vcpuNumOrRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVcpuNumOrRange(MySQLParser.VcpuNumOrRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resourceGroupPriority}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceGroupPriority(MySQLParser.ResourceGroupPriorityContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resourceGroupEnableDisable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceGroupEnableDisable(MySQLParser.ResourceGroupEnableDisableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#alterResourceGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlterResourceGroup(MySQLParser.AlterResourceGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setResourceGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetResourceGroup(MySQLParser.SetResourceGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#threadIdList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThreadIdList(MySQLParser.ThreadIdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dropResourceGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropResourceGroup(MySQLParser.DropResourceGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#utilityStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUtilityStatement(MySQLParser.UtilityStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#describeCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescribeCommand(MySQLParser.DescribeCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#explainCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplainCommand(MySQLParser.ExplainCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#explainableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplainableStatement(MySQLParser.ExplainableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#helpCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHelpCommand(MySQLParser.HelpCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#useCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUseCommand(MySQLParser.UseCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#restartServer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRestartServer(MySQLParser.RestartServerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprOr}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOr(MySQLParser.ExprOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprNot}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNot(MySQLParser.ExprNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprIs}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprIs(MySQLParser.ExprIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprAnd}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAnd(MySQLParser.ExprAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprXor}
	 * labeled alternative in {@link MySQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprXor(MySQLParser.ExprXorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExprPredicate}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExprPredicate(MySQLParser.PrimaryExprPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExprCompare}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExprCompare(MySQLParser.PrimaryExprCompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExprAllAny}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExprAllAny(MySQLParser.PrimaryExprAllAnyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExprIsNull}
	 * labeled alternative in {@link MySQLParser#boolPri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExprIsNull(MySQLParser.PrimaryExprIsNullContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(MySQLParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(MySQLParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicateExprIn}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateExprIn(MySQLParser.PredicateExprInContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicateExprBetween}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateExprBetween(MySQLParser.PredicateExprBetweenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicateExprLike}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateExprLike(MySQLParser.PredicateExprLikeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicateExprRegex}
	 * labeled alternative in {@link MySQLParser#predicateOperations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateExprRegex(MySQLParser.PredicateExprRegexContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#bitExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitExpr(MySQLParser.BitExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprConvert}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprConvert(MySQLParser.SimpleExprConvertContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprVariable}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprVariable(MySQLParser.SimpleExprVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprCast}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprCast(MySQLParser.SimpleExprCastContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprUnary}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprUnary(MySQLParser.SimpleExprUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprOdbc}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprOdbc(MySQLParser.SimpleExprOdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprRuntimeFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprRuntimeFunction(MySQLParser.SimpleExprRuntimeFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprFunction(MySQLParser.SimpleExprFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprCollate}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprCollate(MySQLParser.SimpleExprCollateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprMatch}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprMatch(MySQLParser.SimpleExprMatchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprWindowingFunction}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprWindowingFunction(MySQLParser.SimpleExprWindowingFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprBinary}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprBinary(MySQLParser.SimpleExprBinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprColumnRef}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprColumnRef(MySQLParser.SimpleExprColumnRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprParamMarker}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprParamMarker(MySQLParser.SimpleExprParamMarkerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprSum}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprSum(MySQLParser.SimpleExprSumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprConvertUsing}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprConvertUsing(MySQLParser.SimpleExprConvertUsingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprSubQuery}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprSubQuery(MySQLParser.SimpleExprSubQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprGroupingOperation}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprGroupingOperation(MySQLParser.SimpleExprGroupingOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprNot}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprNot(MySQLParser.SimpleExprNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprValues}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprValues(MySQLParser.SimpleExprValuesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprDefault}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprDefault(MySQLParser.SimpleExprDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprList}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprList(MySQLParser.SimpleExprListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprInterval}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprInterval(MySQLParser.SimpleExprIntervalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprCase}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprCase(MySQLParser.SimpleExprCaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprConcat}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprConcat(MySQLParser.SimpleExprConcatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleExprLiteral}
	 * labeled alternative in {@link MySQLParser#simpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExprLiteral(MySQLParser.SimpleExprLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#jsonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonOperator(MySQLParser.JsonOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#sumExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumExpr(MySQLParser.SumExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#groupingOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupingOperation(MySQLParser.GroupingOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFunctionCall(MySQLParser.WindowFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowingClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowingClause(MySQLParser.WindowingClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#leadLagInfo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeadLagInfo(MySQLParser.LeadLagInfoContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#nullTreatment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullTreatment(MySQLParser.NullTreatmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#jsonFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonFunction(MySQLParser.JsonFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#inSumExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInSumExpr(MySQLParser.InSumExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#identListArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentListArg(MySQLParser.IdentListArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#identList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentList(MySQLParser.IdentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fulltextOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFulltextOptions(MySQLParser.FulltextOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#runtimeFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuntimeFunctionCall(MySQLParser.RuntimeFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#geometryFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeometryFunction(MySQLParser.GeometryFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#timeFunctionParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeFunctionParameters(MySQLParser.TimeFunctionParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fractionalPrecision}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFractionalPrecision(MySQLParser.FractionalPrecisionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#weightStringLevels}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeightStringLevels(MySQLParser.WeightStringLevelsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#weightStringLevelListItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeightStringLevelListItem(MySQLParser.WeightStringLevelListItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dateTimeTtype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateTimeTtype(MySQLParser.DateTimeTtypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#trimFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrimFunction(MySQLParser.TrimFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#substringFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstringFunction(MySQLParser.SubstringFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(MySQLParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#udfExprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUdfExprList(MySQLParser.UdfExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#udfExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUdfExpr(MySQLParser.UdfExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(MySQLParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#userVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserVariable(MySQLParser.UserVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#systemVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSystemVariable(MySQLParser.SystemVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#internalVariableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInternalVariableName(MySQLParser.InternalVariableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#whenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenExpression(MySQLParser.WhenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#thenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThenExpression(MySQLParser.ThenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#elseExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseExpression(MySQLParser.ElseExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#castType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastType(MySQLParser.CastTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(MySQLParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#charset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharset(MySQLParser.CharsetContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#notRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotRule(MySQLParser.NotRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#not2Rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot2Rule(MySQLParser.Not2RuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#interval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterval(MySQLParser.IntervalContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#intervalTimeStamp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalTimeStamp(MySQLParser.IntervalTimeStampContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#exprListWithParentheses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprListWithParentheses(MySQLParser.ExprListWithParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#orderOrGroupList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderOrGroupList(MySQLParser.OrderOrGroupListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#orderExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderExpression(MySQLParser.OrderExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#channel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChannel(MySQLParser.ChannelContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(MySQLParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MySQLParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MySQLParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ifBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBody(MySQLParser.IfBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#thenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThenStatement(MySQLParser.ThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#compoundStatementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatementList(MySQLParser.CompoundStatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#caseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseStatement(MySQLParser.CaseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#elseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatement(MySQLParser.ElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#labeledBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledBlock(MySQLParser.LabeledBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#unlabeledBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnlabeledBlock(MySQLParser.UnlabeledBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(MySQLParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#beginEndBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeginEndBlock(MySQLParser.BeginEndBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#labeledControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledControl(MySQLParser.LabeledControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#unlabeledControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnlabeledControl(MySQLParser.UnlabeledControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#loopBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopBlock(MySQLParser.LoopBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#whileDoBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileDoBlock(MySQLParser.WhileDoBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#repeatUntilBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatUntilBlock(MySQLParser.RepeatUntilBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#spDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpDeclarations(MySQLParser.SpDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#spDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpDeclaration(MySQLParser.SpDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MySQLParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#conditionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionDeclaration(MySQLParser.ConditionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#spCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpCondition(MySQLParser.SpConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#sqlstate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSqlstate(MySQLParser.SqlstateContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#handlerDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandlerDeclaration(MySQLParser.HandlerDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#handlerCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandlerCondition(MySQLParser.HandlerConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#cursorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCursorDeclaration(MySQLParser.CursorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#iterateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterateStatement(MySQLParser.IterateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#leaveStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeaveStatement(MySQLParser.LeaveStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#getDiagnostics}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetDiagnostics(MySQLParser.GetDiagnosticsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#signalAllowedExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignalAllowedExpr(MySQLParser.SignalAllowedExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#statementInformationItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementInformationItem(MySQLParser.StatementInformationItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#conditionInformationItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionInformationItem(MySQLParser.ConditionInformationItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#signalInformationItemName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignalInformationItemName(MySQLParser.SignalInformationItemNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#signalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignalStatement(MySQLParser.SignalStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resignalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResignalStatement(MySQLParser.ResignalStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#signalInformationItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignalInformationItem(MySQLParser.SignalInformationItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#cursorOpen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCursorOpen(MySQLParser.CursorOpenContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#cursorClose}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCursorClose(MySQLParser.CursorCloseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#cursorFetch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCursorFetch(MySQLParser.CursorFetchContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#schedule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchedule(MySQLParser.ScheduleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinition(MySQLParser.ColumnDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#checkOrReferences}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckOrReferences(MySQLParser.CheckOrReferencesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#checkConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckConstraint(MySQLParser.CheckConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableConstraintDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableConstraintDef(MySQLParser.TableConstraintDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDefinition(MySQLParser.FieldDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#columnAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnAttribute(MySQLParser.ColumnAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#gcolAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGcolAttribute(MySQLParser.GcolAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#references}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferences(MySQLParser.ReferencesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#deleteOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteOption(MySQLParser.DeleteOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyList(MySQLParser.KeyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#keyPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyPart(MySQLParser.KeyPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexType(MySQLParser.IndexTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexOption(MySQLParser.IndexOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#commonIndexOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommonIndexOption(MySQLParser.CommonIndexOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#visibility}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVisibility(MySQLParser.VisibilityContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexTypeClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexTypeClause(MySQLParser.IndexTypeClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fulltextIndexOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFulltextIndexOption(MySQLParser.FulltextIndexOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#spatialIndexOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpatialIndexOption(MySQLParser.SpatialIndexOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dataTypeDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataTypeDefinition(MySQLParser.DataTypeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(MySQLParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#nchar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNchar(MySQLParser.NcharContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#varchar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarchar(MySQLParser.VarcharContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#nvarchar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNvarchar(MySQLParser.NvarcharContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldLength}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldLength(MySQLParser.FieldLengthContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldOptions(MySQLParser.FieldOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#charsetWithOptBinary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharsetWithOptBinary(MySQLParser.CharsetWithOptBinaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ascii}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAscii(MySQLParser.AsciiContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#unicode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnicode(MySQLParser.UnicodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#wsNumCodepoints}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWsNumCodepoints(MySQLParser.WsNumCodepointsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#typeDatetimePrecision}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDatetimePrecision(MySQLParser.TypeDatetimePrecisionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#charsetName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharsetName(MySQLParser.CharsetNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#collationName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollationName(MySQLParser.CollationNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createTableOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTableOptions(MySQLParser.CreateTableOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createTableOptionsSpaceSeparated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTableOptionsSpaceSeparated(MySQLParser.CreateTableOptionsSpaceSeparatedContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createTableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTableOption(MySQLParser.CreateTableOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ternaryOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryOption(MySQLParser.TernaryOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#defaultCollation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultCollation(MySQLParser.DefaultCollationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#defaultCharset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultCharset(MySQLParser.DefaultCharsetContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionClause(MySQLParser.PartitionClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionDefKey}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefKey(MySQLParser.PartitionDefKeyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionDefHash}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefHash(MySQLParser.PartitionDefHashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionDefRangeList}
	 * labeled alternative in {@link MySQLParser#partitionTypeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefRangeList(MySQLParser.PartitionDefRangeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#subPartitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubPartitions(MySQLParser.SubPartitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionKeyAlgorithm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionKeyAlgorithm(MySQLParser.PartitionKeyAlgorithmContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionDefinitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefinitions(MySQLParser.PartitionDefinitionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefinition(MySQLParser.PartitionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionValuesIn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionValuesIn(MySQLParser.PartitionValuesInContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOption(MySQLParser.PartitionOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#subpartitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubpartitionDefinition(MySQLParser.SubpartitionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionValueItemListParen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionValueItemListParen(MySQLParser.PartitionValueItemListParenContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#partitionValueItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionValueItem(MySQLParser.PartitionValueItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#definerClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinerClause(MySQLParser.DefinerClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ifExists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExists(MySQLParser.IfExistsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ifNotExists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfNotExists(MySQLParser.IfNotExistsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#procedureParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureParameter(MySQLParser.ProcedureParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#functionParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameter(MySQLParser.FunctionParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#typeWithOptCollate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeWithOptCollate(MySQLParser.TypeWithOptCollateContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#schemaIdentifierPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaIdentifierPair(MySQLParser.SchemaIdentifierPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewRefList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewRefList(MySQLParser.ViewRefListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#updateList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateList(MySQLParser.UpdateListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#updateElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateElement(MySQLParser.UpdateElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#charsetClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharsetClause(MySQLParser.CharsetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldsClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldsClause(MySQLParser.FieldsClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldTerm(MySQLParser.FieldTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#linesClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinesClause(MySQLParser.LinesClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#lineTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineTerm(MySQLParser.LineTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#userList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserList(MySQLParser.UserListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createOrAlterUserList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateOrAlterUserList(MySQLParser.CreateOrAlterUserListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#createOrAlterUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateOrAlterUser(MySQLParser.CreateOrAlterUserContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#user}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUser(MySQLParser.UserContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#likeClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLikeClause(MySQLParser.LikeClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#likeOrWhere}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLikeOrWhere(MySQLParser.LikeOrWhereContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#onlineOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnlineOption(MySQLParser.OnlineOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#noWriteToBinLog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoWriteToBinLog(MySQLParser.NoWriteToBinLogContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#usePartition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsePartition(MySQLParser.UsePartitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#fieldIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldIdentifier(MySQLParser.FieldIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#columnInternalRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnInternalRef(MySQLParser.ColumnInternalRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#columnInternalRefList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnInternalRefList(MySQLParser.ColumnInternalRefListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#columnRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnRef(MySQLParser.ColumnRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#insertIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertIdentifier(MySQLParser.InsertIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexName(MySQLParser.IndexNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#indexRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexRef(MySQLParser.IndexRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableWild}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableWild(MySQLParser.TableWildContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#schemaName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaName(MySQLParser.SchemaNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#schemaRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaRef(MySQLParser.SchemaRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#procedureName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureName(MySQLParser.ProcedureNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#procedureRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureRef(MySQLParser.ProcedureRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionName(MySQLParser.FunctionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#functionRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionRef(MySQLParser.FunctionRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#triggerName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriggerName(MySQLParser.TriggerNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#triggerRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriggerRef(MySQLParser.TriggerRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewName(MySQLParser.ViewNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#viewRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewRef(MySQLParser.ViewRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tablespaceName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTablespaceName(MySQLParser.TablespaceNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tablespaceRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTablespaceRef(MySQLParser.TablespaceRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#logfileGroupName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogfileGroupName(MySQLParser.LogfileGroupNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#logfileGroupRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogfileGroupRef(MySQLParser.LogfileGroupRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#eventName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventName(MySQLParser.EventNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#eventRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventRef(MySQLParser.EventRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#udfName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUdfName(MySQLParser.UdfNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#serverName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitServerName(MySQLParser.ServerNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#serverRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitServerRef(MySQLParser.ServerRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#engineRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEngineRef(MySQLParser.EngineRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(MySQLParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#filterTableRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterTableRef(MySQLParser.FilterTableRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableRefWithWildcard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableRefWithWildcard(MySQLParser.TableRefWithWildcardContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableRef(MySQLParser.TableRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableRefList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableRefList(MySQLParser.TableRefListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#tableAliasRefList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAliasRefList(MySQLParser.TableAliasRefListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#parameterName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterName(MySQLParser.ParameterNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#labelIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelIdentifier(MySQLParser.LabelIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#labelRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelRef(MySQLParser.LabelRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleIdentifier(MySQLParser.RoleIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleRef(MySQLParser.RoleRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#pluginRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPluginRef(MySQLParser.PluginRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#componentRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponentRef(MySQLParser.ComponentRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#resourceGroupRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceGroupRef(MySQLParser.ResourceGroupRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#windowName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowName(MySQLParser.WindowNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#pureIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPureIdentifier(MySQLParser.PureIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(MySQLParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#identifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierList(MySQLParser.IdentifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#identifierListWithParentheses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierListWithParentheses(MySQLParser.IdentifierListWithParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#qualifiedIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedIdentifier(MySQLParser.QualifiedIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleIdentifier(MySQLParser.SimpleIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#dotIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotIdentifier(MySQLParser.DotIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ulong_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUlong_number(MySQLParser.Ulong_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#real_ulong_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_ulong_number(MySQLParser.Real_ulong_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#ulonglong_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUlonglong_number(MySQLParser.Ulonglong_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#real_ulonglong_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_ulonglong_number(MySQLParser.Real_ulonglong_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MySQLParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#signedLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignedLiteral(MySQLParser.SignedLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#stringList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringList(MySQLParser.StringListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#textStringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextStringLiteral(MySQLParser.TextStringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#textString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextString(MySQLParser.TextStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#textLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextLiteral(MySQLParser.TextLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#textStringNoLinebreak}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextStringNoLinebreak(MySQLParser.TextStringNoLinebreakContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#textStringLiteralList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextStringLiteralList(MySQLParser.TextStringLiteralListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#numLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumLiteral(MySQLParser.NumLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#boolLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(MySQLParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#nullLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(MySQLParser.NullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#temporalLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemporalLiteral(MySQLParser.TemporalLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#floatOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatOptions(MySQLParser.FloatOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#precision}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrecision(MySQLParser.PrecisionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#textOrIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextOrIdentifier(MySQLParser.TextOrIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleIdentifierOrText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleIdentifierOrText(MySQLParser.RoleIdentifierOrTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#sizeNumber}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSizeNumber(MySQLParser.SizeNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#parentheses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentheses(MySQLParser.ParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#equal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(MySQLParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#optionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionType(MySQLParser.OptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#varIdentType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarIdentType(MySQLParser.VarIdentTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#setVarIdentType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetVarIdentType(MySQLParser.SetVarIdentTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#identifierKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierKeyword(MySQLParser.IdentifierKeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#labelKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelKeyword(MySQLParser.LabelKeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleOrIdentifierKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleOrIdentifierKeyword(MySQLParser.RoleOrIdentifierKeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleOrLabelKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleOrLabelKeyword(MySQLParser.RoleOrLabelKeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySQLParser#roleKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoleKeyword(MySQLParser.RoleKeywordContext ctx);
}