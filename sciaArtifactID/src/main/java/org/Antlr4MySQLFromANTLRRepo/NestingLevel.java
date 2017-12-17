package org.Antlr4MySQLFromANTLRRepo;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * Keep track of the level of nesting in a SQL statement.
 * Each attribute (column) is assigned a nesting level as it is parsed
 * @author nicomp
 */
public class NestingLevel {
	private int nestSet;		//  The index, from the beginning of the SQL statement, of this set of parens
								// 	As we traverse the SQL statement, nestSet can only go up or stay the same, cannot go down.
	private int nestLevel;		// The nesting level within this nestSet
	/**
	 * Create a new NestingLevel object and initialize to no nesting.
	 */
	public NestingLevel() {
		init();
	}
	/**
	 * Copy constructor
	 * @param nestingLevel The object to be copied
	 */
	public NestingLevel(NestingLevel nestingLevel) {
		if (nestingLevel != null) {
			this.nestLevel = nestingLevel.nestLevel;
			this.nestSet = nestingLevel.nestSet;
		} else {
			init();
		}
	}
	/**
	 * Reset the nest values back to zero nesting (outside all parens)
	 */
	public void init() {
		nestLevel = 0;
		nestSet = 0;
	}
	public int getNestSet() {return nestSet;}
	public int getNestLevel() {return nestLevel;}
	/**
	 *  We are entering a new set of parens
	 */
	public void incrementNestingLevel() {
		if (nestLevel == 0) {
			nestSet++;
			nestLevel = 1;
		} else {
			nestLevel++;
		}
	}
	/**
	 * We are closing an existing set of parens
	 */
	public void decrementNestingLevel() {
		if (nestLevel == 1) {
			// We are no longer nested in any parens
			nestLevel = 0;
			nestSet = 0;
		} else {
			nestLevel--;
		}
	}
	/**
	 * Check to see if we are not inside any parens
	 * @return True if we are not inside any parens, false otherwise
	 */
	public boolean isNone() {return ((nestLevel == 0)? true:false);}
	/**
	 * Convert the nesting level to a printable representation
	 */
	public String toString() {return String.valueOf(nestSet) + ":" + nestLevel;}
	/**
	 * Compare to another NestingLevel object
	 * @param nestingLevel The one to compare to
	 * @return true if the two NestingLevel objects contain the same values, false otherwise.
	 */
	public Boolean isEqual(NestingLevel nestingLevel) {
		Boolean result = false;
		try {
			if (this.nestLevel == nestingLevel.getNestLevel() && this.nestSet == nestingLevel.getNestSet()) {
				result = true;
			}
		} catch (Exception ex) {
			Log.logError("NestingLevel.isEqual(): " + ex.getLocalizedMessage());
		}
		return result;
	}
	/**
	 * Compare to another NestingLevel object
	 * @param nestingLevel The one to compare to
	 * @return true if this object is equal to or nested inside nestingLevel, false otherwise
	 */
	public Boolean isNestedInOrIsEqualTo(NestingLevel nestingLevel) {
		Boolean result = false;
		try {
			if (this.nestLevel >= nestingLevel.getNestLevel() && this.nestSet == nestingLevel.getNestSet()) {
				result = true;
			}
		} catch (Exception ex) {
			Log.logError("NestingLevel.isEqual(): " + ex.getLocalizedMessage());
		}
		return result;
	}

}
