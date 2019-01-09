/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

/***
 * Commonality between QueryAtrribute class and CompopundAlias class
 * @author nicomp
 *
 */
public interface Name {
	/***
	 * Get the name of the thing
	 * @return The name of the thing
	 */
	String getName();
	FullColumnName getFullColumnName();
}
