package edu.UC.PhD.CodeProject.nicholdw.mapping;

/***
 * ETL Mapping
 * @author nicomp
 */
public class Mapping extends MappingBase {
	
	/***
	 * Copy Constructor
	 * @param mapping The Mapping object to be copied
	 */
	public Mapping(Mapping mapping) {
		super(mapping.getUniqueName());
	}
	
	public Mapping(String uniqueName) {
		super(uniqueName);
	}
}
