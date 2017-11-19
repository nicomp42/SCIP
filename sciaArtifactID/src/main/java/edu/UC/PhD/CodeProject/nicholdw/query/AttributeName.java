package edu.UC.PhD.CodeProject.nicholdw.query;

/**
 * Every column / attribute in a table or query has a name. Assume the naming requirements of whatever database server is being used
 * @author nicomp
 *
 */
public class AttributeName {
	private String attributeName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public AttributeName() {}

	public AttributeName(String attributeName) {setAttributeName(attributeName);}
}
