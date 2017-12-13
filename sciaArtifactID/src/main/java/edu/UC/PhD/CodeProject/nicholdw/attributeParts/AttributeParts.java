package edu.UC.PhD.CodeProject.nicholdw.attributeParts;

/**
 * A class that parses a string into the parts of an Attribute
 * @author nicomp
 *
 */
public class AttributeParts {
	// TODO: make this more OOP-conforming. It's a little clunky.
	public String schemaName;
	public String tableName;
	public String attributeName;
	public String dataType;
	/**
	 * Split this string: schemaName.TableName.AttributeName (dataType) into the component parts of an attribute
	 * @param attributeString The string to be parsed.
	 * @return Nothing. The attributes of the object are populated.
	 */
	public void split(String attributeString) {
		String[] tmp = attributeString.split("\\.");
		this.schemaName = tmp[0];
		this.tableName = tmp[1];
		String foo[] = tmp[2].split("\\(");
		this.attributeName = foo[0];
		this.dataType = foo[1].replace(")", "");		// remove the closing paren on the data type string
	}
}