/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.schemaTopology;

/***
 * A compendium of quantitative results from the generation of a Schema Topology
 * @author nicomp
 *
 */
public class DatabaseGraphResults {
	private int totalQueries;
	private int totalTables;
	private int totalAttributes;
	private int totalQueryAttributes;
	private int totalAffectedAttributes;

	public void clearResults() {
		totalQueries = 0;
		totalTables = 0;
		totalAttributes = 0;
		totalQueryAttributes = 0;
		totalAffectedAttributes = 0;
	}
	public int incrementTotalQueries() {totalQueries++; return totalQueries;}
	public int incrementTotalTables() {totalTables++; return totalTables;}
	public int incrementTotalAttributes() {totalAttributes++; return totalAttributes;}
	public int incrementTotalQueryAttributes() {totalQueryAttributes++; return totalQueryAttributes;}
	public int incrementTotalAffectedAttributes() {totalAffectedAttributes++; return totalAffectedAttributes;}

	public String toString() {
		String result = "undefined";
		try {
			result = "Results from graph generation:"
					+ "\n total views = " + totalQueries 
					+ "\n total tables = " + totalTables 
					+ "\n total attributes = " + totalAttributes 
					+ "\n total view attributes = " + totalQueryAttributes 
					+ "\n total affected attributes = " + totalAffectedAttributes;
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
	public int getTotalAffectedAttributes() {return totalAffectedAttributes;}
	public void setTotalAffectedAttributes(int totalAffectedAttributes) {this.totalAffectedAttributes = totalAffectedAttributes;}
}
