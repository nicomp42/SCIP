package edu.nicholdw.PhD.CodeProject.ETL;
/**
 * Model an ETL Job in a Pentaho ETL file
 * @author nicomp
 *
 */
public class ETLJob {
	private String jobName;
	private String filename;
	private String description;
	public ETLJob(String name, String filename, String description) {
		setJobName(name);
		setFilename(filename);
		setDescription(description);
	}
	/**
	 * Copy Constructor
	 * @param etlJob
	 */
	public ETLJob(ETLJob etlJob) {
		setJobName(etlJob.getJobName());
		setFilename(etlJob.getFilename());
		setDescription(etlJob.getDescription());
	}
	public String toString() {
		return jobName + ":" + getFilenameWithoutPrefix() + ((description.length() > 0) ? ":" + description : "");
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description.trim();
	}
	/**
	 * Chop off the prefix of the filename where the job is stored.
	 * @return Just the filename with the prefix removed. 
	 */
	public String getFilenameWithoutPrefix() {
		String result = filename;
		// ${Internal.Entry.Current.Directory}/KettleJobReconciledToDataWarehouse.kjb
		int slashIdx = filename.indexOf("/");
		if (slashIdx > 0) {
			result = filename.substring(slashIdx);
		}
		return result;
	}
}
