/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.UUID;

/**
 * Wrapper for java.util.UUID
 * @author nicomp
 *
 */
public class UUIDnicholdw {
	private String ID;
	/***
	 * Init an instance of this class. A unique UUID is created here.
	 */
	public UUIDnicholdw() {
		setID(UUID.randomUUID().toString());
	}
	/**
	 * Copy Constructor. The Unique ID is duplicated in the new object
	 * @param uUIDnicholdw
	 */
	public UUIDnicholdw(UUIDnicholdw uUIDnicholdw) {
		this.ID = uUIDnicholdw.getID();
	}
	/***
	 * Get the Unique ID for this object
	 * @return The unique ID for this object
	 */
	public String getID() {
		return ID;
	}
	/***
	 * Same as getID()
	 * @return The unique ID for this object
	 */
	public String toString() {return getID();}
	
	private void setID(String iD) {
		this.ID = iD;
	}
	/***
	 * Compare two Unique Identifiers
	 * @param ID The ID to compare
	 * @return True if they have the same value, false otherwise
	 */
	public boolean compare(UUIDnicholdw uUIDnicholdw) {
		if (this.toString().equals(uUIDnicholdw.toString())) {
			return true;
		} else {
			return false;
		}
	}
}
