package edu.UC.PhD.CodeProject.nicholdw.mapping;

/***
 * Base class for ETL Mapping classes 
 * @author nicomp
 *
 */
public abstract class MappingBase {
	private String uniqueName;

	abstract MappingBase clone(MappingBase mappingBase);
	
	public MappingBase(String uniqueName) {
		setUniqueName(uniqueName);
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	
}
