/*
 * Bill Nicholsonm
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.util.Map;

public class RowWrapper {

	public Map<String, Object> r;
	public RowWrapper (Map<String, Object> r) {
		this.r = r;
	}
	public Map<String, Object> getRow() {return r;}
}
