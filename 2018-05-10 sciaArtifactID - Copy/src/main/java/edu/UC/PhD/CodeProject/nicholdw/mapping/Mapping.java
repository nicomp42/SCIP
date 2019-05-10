package edu.UC.PhD.CodeProject.nicholdw.mapping;

/***
 * ETL Mapping
 * @author nicomp
 */
public class Mapping extends MappingBase {
	
	private MappingBase start, end;
	
	public MappingBase clone(MappingBase mappingBase) {
		Mapping newMapping = new Mapping(mappingBase.getUniqueName());
		return newMapping;
	}
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

	public MappingBase getStart() {
		return start;
	}

	public void setStart(MappingBase start) {
		this.start = clone(start);
	}
	public MappingBase getEnd() {
		return end;
	}
	public void setEnd(MappingBase end) {
		this.end = clone(end);
	}
}
