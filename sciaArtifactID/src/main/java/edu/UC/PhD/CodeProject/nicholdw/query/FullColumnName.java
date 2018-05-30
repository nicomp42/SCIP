
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;

import org.Antlr4MySQLFromANTLRRepo.NestingLevel;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

	/**
	 * Fully qualified (or not) column reference from an SQL statement.
	 * Could be schema.table.attribute, table.attribute, or just attribute. May also have one or more aliases.
	 * @author nicomp
	 */
	public class FullColumnName {
		private String schemaName;
		private String tableName;
		private String attributeName;
		private QueryClause queryClause;
		private AliasNamesOLD aliasNames;
		//private String aliasName;		// Removed because a query attribute can have multiple aliases
		private NestingLevel nestingLevel;
		private String rawData;			// Taken from the SQL during parsing. could be schema.table.attribute, table.attribute, or just attribute
		/**
		 * Create a FullColumnName object with just the raw data taken from the query parser
		 * @param rawData
		 */
		public FullColumnName(String rawData) {
			setRawData(rawData);
		}
		public String setRawData(String rawData) {this.rawData = rawData.trim(); return this.rawData;}
		public String getRawData() {return rawData;}
		public String toString() {return (schemaName.length() > 0? schemaName + ".":"") + (tableName.length() > 0 ? tableName + "." : "") + attributeName + (aliasNames.toString().length() > 0 ? " AS " + aliasNames.toString():"");}
		public String getAttributeName() {return attributeName;}
		public String getTableName() {return tableName;}
		public String getSchemaName() {return schemaName;}
		public FullColumnName(String rawData, NestingLevel nestingLevel) {
			init();
			setRawData(rawData);
			setNestingLevel(nestingLevel);			
		}
		public void init() {
			schemaName = "";
			tableName = "";
			attributeName = "";
			aliasNames = new AliasNamesOLD();
			queryClause = new QueryClauseUndefined();
		}
		public AliasNamesOLD getAliasNames() {return aliasNames;}
		public void addAliasName(AliasNameClassOLD aliasName) {this.aliasNames.addAliasName(aliasName);}
		/**
		 * Start with a string formatted like schemaName.TableName.AttributeName and extract the parts into a structure.
		 * Beware: A table name and an attribute name CAN have a . (period in it) Yikes.
		 * There cannot be an alias in the string to parse.
		 * @return The number of parts that were parsed: 0 - 3
		 */
		public int processRawData() {
			int numberOfColumnParts = 0;
			String tmpColumnParts[] = rawData.split("\\`");
			ArrayList<String> columnParts = new ArrayList<String>();
			for (String s : tmpColumnParts) {
				if ((s.trim().length() > 0) && (s.equals(".") == false)) {
					columnParts.add(s);
				}
			}			
//			String columnParts[] = rawData.split("\\.");			// The . character is special in RegEx so we gotta hide it. Doesn't work because a symbol can contain a .
			switch (columnParts.size()) {
			case 1:			// Just a column name
				this.attributeName = Utils.removeBackQuotes(columnParts.get(0).trim());
				numberOfColumnParts = 1;
				break;
			case 2:			// column name and table name
				this.attributeName = Utils.removePeriod(Utils.removeBackQuotes(columnParts.get(1))).trim();
				this.tableName = Utils.removePeriod(Utils.removeBackQuotes(columnParts.get(0))).trim();
				numberOfColumnParts = 2;
				break;

			case 3:			// column name, table name, schema name
				this.attributeName = Utils.removePeriod(Utils.removeBackQuotes(columnParts.get(2))).trim();
				this.tableName = Utils.removePeriod(Utils.removeBackQuotes(columnParts.get(1))).trim();
				this.schemaName = Utils.removePeriod(Utils.removeBackQuotes(columnParts.get(0))).trim();
				numberOfColumnParts = 3;
				break;

			default:
				Log.logQueryParseProgress("AntlrMySQLListener.processRawData(): unexpected number of parts to parse (" + columnParts.size() + "): " + " started with " + rawData, true);
			}
			return numberOfColumnParts;
		}
		/**
		 * Copy the data in this class into an Attribute object and add that Attribute object to the QueryDefinition
		 * @param queryDefinition The target QueryDefinition
		 */
		public void copyIntoQueryDefinition(QueryDefinition queryDefinition) {
			queryDefinition.getQueryAttributes().addAttribute(new QueryAttribute(schemaName, tableName, attributeName, aliasNames, queryClause));
		}
		/**
		 * Set the nesting level of this attribute
		 * @param nestingLevel
		 */
		public void setNestingLevel(NestingLevel nestingLevel) {
			this.nestingLevel = new NestingLevel(nestingLevel);
		}
		/**
		 * Get the nesting Level of this column
		 * @return The nesting level of this column
		 */
		public NestingLevel getNestingLevel() {return nestingLevel;}
	}
