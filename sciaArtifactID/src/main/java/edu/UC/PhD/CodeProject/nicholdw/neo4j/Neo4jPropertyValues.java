package edu.UC.PhD.CodeProject.nicholdw.neo4j;

/***
 * A node property and a relation property. The key is separate from this value list
 * It can be an array of strings or a single string. Hence, two constructors. 
 * @author nicomp
 *
 */
public class Neo4jPropertyValues {
	private String[] properties;
	
	public Neo4jPropertyValues(String property) {
		properties = new String[1];
		properties[0] = new String(property);
	}
	
	public Neo4jPropertyValues(String[] properties) {
		this.properties = new String[properties.length];
		int i = 0;
		for (String s: properties) {
			this.properties[i] = new String(s);
			i++;
		}
	}
	public String[] getProperties() {return properties.clone();}
	
	public String toString() {
		StringBuilder s = new StringBuilder("");
		String comma = "";
		for (String p : properties) {
			s.append(comma);
			s.append(p);
			comma = ", ";
		}
		return s.toString();
	}
}
