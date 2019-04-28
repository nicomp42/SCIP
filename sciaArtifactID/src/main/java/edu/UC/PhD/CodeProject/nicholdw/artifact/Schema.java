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
