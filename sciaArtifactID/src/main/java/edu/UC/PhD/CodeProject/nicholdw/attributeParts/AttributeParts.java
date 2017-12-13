package edu.UC.PhD.CodeProject.nicholdw.attributeParts;

/**
 * A class that parses a string into the parts of an Attribute
 * @author nicomp
 *
 */
public class AttributeParts {
	// TODO: make this more OOP-conforming. It's a little clunky.
	private String schemaName;
	private String tableName;
	private String attributeName;
	private String dataType;
	/**
	 * Split this string: schemaName.TableName.AttributeName (dataType) into the component parts of an attribute
	 * @param attributeString The string to be parsed.
	 * @return Nothing. The attributes of the object are populated.
	 */
	public void split(String attributeString) {
		String[] tmp = attributeString.split("\\.");
		this.setSchemaName(tmp[0]);
		this.setTableName(tmp[1]);
		String foo[] = tmp[2].split("\\(");
		this.setAttributeName(foo[0]);
		this.setDataType(foo[1].replace(")", ""));		// remove the closing paren on the data type string
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {return tableName;}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAttributeName() {return attributeName;}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getDataType() {return dataType;}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}