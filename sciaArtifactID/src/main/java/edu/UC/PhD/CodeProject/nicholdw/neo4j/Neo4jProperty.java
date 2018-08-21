package edu.UC.PhD.CodeProject.nicholdw.neo4j;

public class Neo4jProperty {
	private Neo4jPropertyValues neo4jPropertyValues;
	private String name;
	
	/**
	 * A constructor for a property that has only one value
	 * @param name
	 * @param value
	 */
	public Neo4jProperty(String name, String value) {
		setName(name);
		neo4jPropertyValues = new Neo4jPropertyValues(value);
	}
	/**
	 * A constructor for a property that has multiple values stored in a String array 
	 * @param name
	 * @param values
	 */
	public Neo4jProperty(String name, String[] values) {
		setName(name);
		neo4jPropertyValues = new Neo4jPropertyValues(values);
	}
	/***
	 * Compare two properties  
	 * @param p1 
	 * @param p2
	 * @return True if p1 is equal to p2, meaning they have the same name and the same values. 
	 */
	public static boolean compareProperties(Neo4jProperty p1, Neo4jProperty p2) {
		boolean isEqual = false;
		if (p1.getName().equals(p2.getName())) {
			if (BagUtils.compareBags(p1.getNeo4jPropertyValues().getPropertyValues(), p2.getNeo4jPropertyValues().getPropertyValues())) {
				isEqual = true;
			}
		}
		return isEqual;
	}
	public Neo4jPropertyValues getNeo4jPropertyValues() {return neo4jPropertyValues;}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
