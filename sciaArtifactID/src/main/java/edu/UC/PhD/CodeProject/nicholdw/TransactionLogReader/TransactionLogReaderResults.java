/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

public class TransactionLogReaderResults {
	private int totalRecords;
	private String lastErrorMsg;
	private int totalQueriesProcessed;

	
	public TransactionLogReaderResults() {
		totalRecords = 0;
		lastErrorMsg = "";
		setTotalQueriesProcessed(0);
	}
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getLastErrorMsg() {
		return lastErrorMsg;
	}

	public void setLastErrorMsg(String lastErrorMsg) {
		this.lastErrorMsg = lastErrorMsg;
	}
	public int getTotalQueriesProcessed() {
		return totalQueriesProcessed;
	}
	public void setTotalQueriesProcessed(int totalQueriesProcessed) {
		this.totalQueriesProcessed = totalQueriesProcessed;
	}
}
