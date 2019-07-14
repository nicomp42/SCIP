/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

/***
 * A set of ETLJob objects.
 * @author nicomp
 *
 */
public class ETLJobs implements Iterable<ETLJob> {
	private ArrayList<ETLJob> ETLJobs;
	
	public ETLJobs() {
		ETLJobs = new ArrayList<ETLJob>();
	}
	public ETLJob getETLJob(String jobName) {
		ETLJob ETLJobFound = null;
		for (ETLJob ETLJob : ETLJobs) {
			if (ETLJob.getJobName().equals(jobName)) {
				ETLJobFound = new ETLJob(ETLJob);
				break;
			}
		}
		return ETLJobFound;
	}
	/***
	 * Add a new object to the set
	 * @param ETLJob The ETLJob object to be added. A deep copy is performed.
	 */
	public void addETLJob(ETLJob ETLJob) {
		ETLJobs.add(new ETLJob(ETLJob));
	}
	/**
	 * Add all jobs from another ETLJobs object. Deep copy of each ETLJob object!
	 * @param etlJobs
	 */
	public void addETLJobs(ETLJobs etlJobs) {
		for (ETLJob etlJob: etlJobs) {
			addETLJob(new ETLJob(etlJob));
		}
	}
	public String toString() {
		String result = "";
		String crlf = "";
		for (ETLJob ETLJob : ETLJobs) {
			result += crlf + ETLJob.toString();
			crlf = "\n";
		}
		return result;
	}

	@Override
	public Iterator<ETLJob> iterator() {
		Iterator<ETLJob> myIterator = ETLJobs.iterator();
        return myIterator;
	}
}
