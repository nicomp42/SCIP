package edu.UC.PhD.CodeProject.nicholdw.pentaho;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * Do stuff with Pentaho
 * @author nicomp
 *
 */
public class Pentaho {

	public static void main(String[] arg) {
		(new Pentaho()).ExecuteJob("C:\\Users\\nicomp\\Google Drive\\PhD (1)\\TestCases\\FullCoverageTestCase\\FullCoverageTestCase.kjb");
	}
	/***
	 * @param filename
	 */
	public void ExecuteJob(String filename) {
//	    String filename = "C:\\Users\\nicomp\\Google Drive\\PhD (1)\\TestCases\\FullCoverageTestCase\\FullCoverageTestCaseSalesMappingToReconciledSchema.ktr";
	    try {
		    KettleEnvironment.init();
		    
		    JobMeta jobMeta = new JobMeta(filename, null);
		    Job job = new Job(null, jobMeta);
		    job.start();
		    job.waitUntilFinished();
		    
		    if (job.getErrors() != 0) {
				Log.logError(this.getClass().toString() + ".ExecuteJob(): error executing " + filename);
		    }
		  
		} catch(Exception ex) {
			Log.logError(this.getClass().toString() + ".ExecuteJob(" + filename + ": " + ex.getLocalizedMessage());
		}	    
	}
}
