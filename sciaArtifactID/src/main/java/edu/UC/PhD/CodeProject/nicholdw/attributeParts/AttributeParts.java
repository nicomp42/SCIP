package edu.UC.PhD.CodeProject.nicholdw.attributeParts;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * A class that parses a string into the parts of an Attribute. See AttributeProvenanceController ComboBox. 
 * @author nicomp
 *
 */
public class AttributeParts {
	// TODO: make this more OOP-conforming. It's a little clunky.
	private String schemaName;
	private String tableName;
	private String attributeName;
	private String dataType;
	private String aliasName;
	/**
	 * Split this string: schemaName.TableName.AttributeName AS alias (dataType) into the component parts of an attribute
	 * @param attributeString The string to be parsed.
	 * @return Nothing. The attributes of the object are populated.
	 */
	public void split(String attributeString) {
		try {
			String tmp1[] = attributeString.split(" AS ");	// tmp1[0] = schemaName.TableName.AttributeName tmp1[1] = alias (dataType)
			String tmp2[] = tmp1[1].split(" ");
			this.setAliasName(tmp2[0]);
			String tmp[] = tmp1[0].split(":");
			this.setSchemaName(tmp[0]);
			this.setTableName(tmp[1]);
			this.setAttributeName(tmp[2]);
			String foo[] = tmp1[1].split("\\(");
			this.setDataType(foo[1].replace(")", ""));		// remove the closing paren on the data type string
		} catch (Exception ex) {
			Log.logError("AttributeParts.split(" + "\"" + attributeString + "\"): " + ex.getLocalizedMessage());
		}
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {this.schemaName = schemaName.trim();}
	public String getTableName() {return tableName;}
	public void setTableName(String tableName) {this.tableName = tableName.trim();}
	public String getAttributeName() {return attributeName;}
	public void setAttributeName(String attributeName) {this.attributeName = attributeName.trim();}
	public String getDataType() {return dataType;}
	public void setDataType(String dataType) {this.dataType = dataType.trim();}
	public void setAliasName(String aliasName) {this.aliasName = aliasName.trim();}
	public String getAliasName() {return aliasName;}
}