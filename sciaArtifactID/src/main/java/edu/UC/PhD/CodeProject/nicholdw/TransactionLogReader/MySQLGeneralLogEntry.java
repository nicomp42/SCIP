/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

public class MySQLGeneralLogEntry extends GeneralLogEntry {
	public MySQLGeneralLogEntry(String timeStamp, String id, String command, String text) {
		super(timeStamp, id, command, text);
	}
	/***
	 * Map the log entry to fields we can process
	 * @param buffer The log entry
	 */
	public MySQLGeneralLogEntry(String buffer) {
		//           1         2         3         4    
		// 012345678901234567890123456789012345678901234567890
		// 2018-05-23T02:13:59.623852Z	    7 Query	SHOW GLOBAL STATUS
		try {
			setTimeStamp(buffer.substring(0,  27));
			setId(buffer.substring(32, 33));
			setCommand(buffer.substring(34, 39));
			setText(buffer.substring(40));
		} catch (Exception ex) {
			// If something went wrong in the mapping, fill all fields with ?
			setTimeStamp("?");
			setId("?");
			setCommand("?");
			setText("?");
		}
	}

	/***
	 * Do we care about this log entry?
	 * @return true if we care, false otherwise
	 */
	public boolean doWeCare() {
		boolean status = false;
		if ((this.getText().toUpperCase().startsWith("SELECT") ||
			this.getText().toUpperCase().startsWith("ALTER") ||
			this.getText().toUpperCase().startsWith("DROP")) && 
			!this.getText().contains(" performance_schema.events_statements_current ") && 
			!this.getText().contains(" performance_schema.events_waits_history_long ") && 
			!this.getText().contains(" performance_schema.events_stages_history_long ") ) {
			status = true;
		}
		return status;
	}
}


