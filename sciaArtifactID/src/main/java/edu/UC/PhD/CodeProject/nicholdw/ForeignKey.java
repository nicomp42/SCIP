/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * This is a cool class.
 */
package edu.UC.PhD.CodeProject.nicholdw;

/**
 * The table and attribute name of a referred-to primary key from a foreign key attribute. See Attribute class
 * @author nicomp
 *
 */
public class ForeignKey {
	private String tableName, attributeName, referencedTableName, foreignKeyName, referencedAttributeName;
	public ForeignKey(String tableName, String attributeName, String referencedTableName, String foreignKeyName, String referencedAttributeName) {
		this.tableName = tableName;
		this.attributeName = attributeName;
		this.referencedTableName = referencedTableName;
		this.referencedAttributeName = referencedAttributeName;
		this.foreignKeyName = foreignKeyName;
	}
	public String getDisplayName() {
		return this.getForeignKeyName() + " (" + this.getTableName() + "." + this.getAttributeName() + "->" + this.getReferencedTableName() + "." + this.getReferencedAttributeName() + ")";
	}
	public String getReferencedTableName() {return referencedTableName;}
	public String getReferencedAttributeName() {return referencedAttributeName;}
	public String getForeignKeyName(){ return foreignKeyName;}
	public String getTableName() {return tableName;}
	public void setTableName(String tableName) {this.tableName = tableName;}
	public String getAttributeName() {return attributeName;}
	public void setAttributeName(String attributeName) {this.attributeName = attributeName;}
}
