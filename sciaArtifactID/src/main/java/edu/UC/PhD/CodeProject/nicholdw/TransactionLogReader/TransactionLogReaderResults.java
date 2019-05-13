/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

public class TransactionLogReaderResults {
	private int totalRecords;
	private String lastErrorMsg;

	
	public TransactionLogReaderResults() {
		totalRecords = 0;
		lastErrorMsg = "";
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
}
