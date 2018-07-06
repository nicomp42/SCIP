package edu.UC.PhD.CodeProject.nicholdw.testCase;

/***
 * Behavior of test cases in SEQ-AM
 * @author nicomp
 *
 */
public interface ITestCase {
	/***
	 * Execute the test case from end-to-end
	 * @return True if test case passed, false otherwise
	 */
	public boolean run();
	
	/**
	 * Create the operational schemas in the database
	 * @return True if success, false otherwise
	 */
	public boolean createOperationalSchemas();
	
	/***
	 * Create the action queries (as Stored Procedures) that will act upon the attributes in the data warehouse
	 * @return True if success, false otherwise
	 */
	public boolean createAttributeOperationsAsStoredProcedures();

	
	/**
	 * Submit select scripts to the database for the purpose of filling the transaction log
	 * @return
	 */
	public boolean LoadTransactionLog();
	
}
