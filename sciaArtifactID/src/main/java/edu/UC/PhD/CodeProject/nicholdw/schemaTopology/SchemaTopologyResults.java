package edu.UC.PhD.CodeProject.nicholdw.schemaTopology;

/***
 * A compendium of quantitative results from the generation of a Schema Topology
 * @author nicomp
 *
 */
public class SchemaTopologyResults {
	private int totalQueries;
	private int totalTables;
	private int totalAttributes;
	private int totalQueryAttributes;

	public void clearResults() {
		totalQueries = 0;
		totalTables = 0;
		totalAttributes = 0;
		totalQueryAttributes = 0;
	}
	public int incrementTotalQueries() {totalQueries++; return totalQueries;}
	public int incrementTotalTables() {totalTables++; return totalTables;}
	public int incrementTotalAttributes() {totalAttributes++; return totalAttributes;}
	public int incrementTotalQueryAttributes() {totalQueryAttributes++; return totalQueryAttributes;}

	public String toString() {
		String result = "undefined";
		try {
			result = "Results from topology generation: total queries = " + totalQueries + ", total tables = " + totalTables + ", total attributes = " + totalAttributes + ", total query attributes = " + totalQueryAttributes;
		} catch (Exception ex) {}
		return result;
	}
	public int getTotalQueries() {return totalQueries;}
	public void setTotalQueries(int totalQueries) {this.totalQueries = totalQueries;}
	public int getTotalTables() {return totalTables;}
	public void setTotalTables(int totalTables) {this.totalTables = totalTables;}
	public int getTotalAttributes() {return totalAttributes;}
	public void setTotalAttributes(int totalAttributes) {this.totalAttributes = totalAttributes;}
	public int getTotalQueryAttributes() {return totalQueryAttributes;}
	public void setTotalQueryAttributes(int totalQueryAttributes) {this.totalQueryAttributes = totalQueryAttributes;}
}
