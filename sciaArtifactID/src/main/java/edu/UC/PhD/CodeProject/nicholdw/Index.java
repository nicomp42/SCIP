/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

/**
 * An index in a table. See Indexes class.
 * @author nicomp
 *
 */
public class Index {
	private TableAttributes attributes;	// The attributes comprising the index
	private Boolean unique;
	private String name;
	private Boolean primary;

	/***
	 * Constructor
	 * @param name
	 * @param unique
	 * @param primary
	 */
	public Index(String name, Boolean unique, boolean primary)
	{
		this.unique = unique;
		this.name = name;
		this.primary = primary;
		attributes = new TableAttributes();
	}

	public void AddAttribute(TableAttribute attribute) {
		attributes.addAttribute(attribute);
	}	

	public String getName() {if (name.trim().length() > 0) return name; else return buildIndexName();}
	public Boolean isUnique() {return unique;}
	public Boolean isPrimary() {return primary;}
	/**
	 * Build an index name based on the attributes that make up the name.
	 * This is useful if the index has no name in the name property of the object
	 * @return
	 */
	public String buildIndexName() {
		String result = "";
		String underscore = "";
		for (TableAttribute a : attributes) {
			result = result + underscore + a.getAttributeName();
			underscore = "_";
		}
		return result;
	}
	
	TableAttributes getAttributes() {return attributes;}
}
