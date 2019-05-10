/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.artifact;
/**
 * Model a schema object
 * @author nicomp
 *
 */
public class Schema {
	private String name;

	public Schema(Schema schema) throws Exception {
		setName(schema.getName());
	}
	/**
	 * Compare the values in another schema
	 * @param schema The Schema object to compare
	 * @return true if the two schemas contain all the same values. 
	 */
	public boolean compareTo(Schema schema) {
		boolean result = true;
		if (!getName().equals(schema.getName())) {result = false;}
		return result;
	}
	
	public Schema (String name) throws Exception {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		if (name.trim().length() > 0) {
			this.name = name.trim();
		} else { throw new Exception(this.getClass() + ": name cannot be blank");}
	}
	public String toString() {return "name: " + name;}
}
