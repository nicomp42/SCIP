package edu.UC.PhD.CodeProject.nicholdw.mapping;

/***
 * ETL Mapping
 * @author nicomp
 */
public class Mapping extends MappingBase {
	private String uniqueName;
	
	public Mapping(Mapping mapping) {
		this.setUniqueName(mapping.getUniqueName());
	}
	
	public Mapping(String uniqueName) {
		setUniqueName(uniqueName);
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
}
