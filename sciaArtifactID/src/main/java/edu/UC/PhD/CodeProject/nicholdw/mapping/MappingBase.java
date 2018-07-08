package edu.UC.PhD.CodeProject.nicholdw.mapping;

/***
 * Base class for ETL Mapping classes 
 * @author nicomp
 *
 */
public abstract class MappingBase {
	private String uniqueName;

	public MappingBase(String uniqueName2) {
		setUniqueName(uniqueName);
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	
}
