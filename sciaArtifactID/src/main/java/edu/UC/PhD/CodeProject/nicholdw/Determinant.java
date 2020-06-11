/***************************************
 * Manage determinants                 *
 * Bill Nicholson                      *
 * nicholdw@ucmail.uc.edu              *
 ***************************************/
package edu.UC.PhD.CodeProject.nicholdw;

public class Determinant {
		
	private TableAttributes attributes;
	private TableAttribute attributeThatIsDetermimed;
	private boolean isRedundant;		// If redundant then it can be deleted form the list
	public Determinant() {
		attributes = new TableAttributes();
		setRedundant(false);
	}

	/**
	 * @return The number of attributes in the determinant. For example (A1, A2, A4)->A3 returns 2
	 */
	public int getCountOfAttributes() {
		return attributes.size();
	}
	/**
	 * Compare two determinants. All the attribute comparisons are by name, not by reference.
	 * @param anotherDeterminant The other determinant to be compared
	 * @return True if the two determinants contain the same attributes and determine the same attribute, false otherwise
	 */
	public boolean compare(Determinant anotherDeterminant) {
		boolean result = true;
		// Do both determinants determine the same attribute?
		if (attributeThatIsDetermimed.getAttributeName().contentEquals(anotherDeterminant.getAttributeThatIsDetermined().getAttributeName()) == false) {
			result = false;
		} else {
			TableAttributes anotherAttributes = anotherDeterminant.getAttributes();
			if (anotherAttributes.size() == attributes.size()) {
				for (TableAttribute tmpAttribute : anotherAttributes) {
					if (!containsAttributeByName(tmpAttribute)) {result = false; break;}
				}
			} else {result = false;}
		}
		return result;
	}
	/**
	 * Check to see if determinant contains a particular attribute. The search is by value of the name of the attribute. 
	 * @param attributeTarget The attribute to be scanned for
	 * @return True if the Attribute reference is found in the Determinant, false otherwise.
	 */
	public boolean containsAttributeByName(TableAttribute attributeTarget) {
		boolean contains = false;
		for (TableAttribute tmpAttribute : attributes) {
			if (tmpAttribute.getAttributeName().contentEquals(attributeTarget.getAttributeName())) {
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	/**
	 * Check to see if determinant contains an attribute. The search is by reference. 
	 * @param attributeTarget The attribute to be scanned for
	 * @return True if the Attribute reference is found in the Determinant, false otherwise.
	 */
	public boolean containsAttribute(TableAttribute attributeTarget) {
		boolean contains = false;
		for (TableAttribute tmpAttribute : attributes) {
			if (tmpAttribute == attributeTarget) {
				contains = true;
				break;
			}
		}
		return contains;
	}
		
	/***
	 * Get the list of attributes in this determinant
	 * @return The list of attributes in this determinant
	 */
	public TableAttributes getAttributes() {
		return attributes;
	}
	
	/***
	 * Add an attribute to this determinant
	 * @param attribute The attribute to add
	 */
	public void addAttribute(TableAttribute attribute) {
		attributes.addAttribute(attribute);
	}
	
	/***
	 * Add a set of attributes to this determinant
	 * @param attributes The set of attributes to add
	 */
	public void addAttributes(TableAttributes newAttributes) {
		for (TableAttribute attribute: newAttributes) {
			attributes.addAttribute(attribute);
		}
	}

	public void setAttributeThatIsDetermined(TableAttribute attributeThatIsDetermimed) {
		// ToDo make a copy constructor for the Attribute class and use it here.
		this.attributeThatIsDetermimed = attributeThatIsDetermimed;		// new Attribute(attributeThatIsDetermimed);
	}

	public TableAttribute getAttributeThatIsDetermined() {
		return attributeThatIsDetermimed;
	}
	/**
	 * To String
	 */
	public String toString() {
		String result = "";
		String comma = "";
		for (TableAttribute myAttribute: attributes) {
			result += comma + myAttribute.getAttributeName();
			comma = ", " ;
		}
		result += " determines " + attributeThatIsDetermimed.getAttributeName();
		return result;
	}
	/**
	 * To CSV
	 */
	public String toCSV() {
		String result = "";
		String comma = "";
		for (TableAttribute myAttribute: attributes) {
			result += comma + myAttribute.getAttributeName();
			comma = "," ;
		}
		result += ",determines," + attributeThatIsDetermimed.getAttributeName();
		return result;
	}

	/**
	 * Is the determinant redundant? Can it be deleted because there's a subset of attributes that do the same job?
	 * @return True if redundant, false otherwise. 
	 */
	public boolean isRedundant() {
		return isRedundant;
	}
	/**
	 * Set the isRedundant flag. True if redundant, false otherwise.
	 * @param isRedundant
	 * @return results of the 'set'
	 */
	public boolean setRedundant(boolean isRedundant) {
		this.isRedundant = isRedundant;
		return this.isRedundant;
	}
}
