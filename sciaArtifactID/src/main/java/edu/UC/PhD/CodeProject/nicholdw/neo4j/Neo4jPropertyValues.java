package edu.UC.PhD.CodeProject.nicholdw.neo4j;

/***
 * A node property and a relation property. The key is separate from this value list
 * It can be an array of strings or a single string. Hence, two constructors. 
 * @author nicomp
 *
 */
public class Neo4jPropertyValues {
	private String[] propertyValues;
	
	/***
	 * A constructor for only one property, stored as a string
	 * @param property The property
	 */
	public Neo4jPropertyValues(String propertyValue) {
		propertyValues = new String[1];
		propertyValues[0] = new String(propertyValue);
	}
	/***
	 * A constructor for an array of properties
	 * @param properties The array
	 */
	public Neo4jPropertyValues(String[] propertyValues) {
		this.propertyValues = new String[propertyValues.length];
		int i = 0;
		for (String s: propertyValues) {
			this.propertyValues[i] = new String(s);
			i++;
		}
	}
	public String[] getPropertyValues() {return propertyValues.clone();}
	
	public String toString() {
		StringBuilder s = new StringBuilder("");
		String comma = "";
		for (String p : propertyValues) {
			s.append(comma);
			s.append(p);
			comma = ", ";
		}
		return s.toString();
	}
}
