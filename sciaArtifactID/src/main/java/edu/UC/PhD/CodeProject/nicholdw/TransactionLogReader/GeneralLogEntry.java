/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * 
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

/***
 * One log entry from the general log
 * @author nicomp
 *
 */
public abstract class GeneralLogEntry {
	private String timeStamp;
	private String id;
	private String command;
	private String text;
	
	public GeneralLogEntry() {}
	public GeneralLogEntry(String timeStamp, String id, String command, String text) {
		setTimeStamp(timeStamp);
		setId(id);
		setCommand(command);
		setText(text);
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp.trim();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id.trim();
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command.trim();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text.trim();
	}
	public String toString() {
		return getTimeStamp() + " " + getId() + " " + getCommand() + " " + getText();
	}
	/***
	 * Convert the ID field to an integer
	 * @return the integer value of id, else -1 if it can't be converted
	 */
	public int getIdValue() {
		int value;
		try {
			value = Integer.valueOf(this.id);
		} catch (Exception ex) {
			value = -1;
		}
		return value;
	}
	public abstract boolean doWeCare();
}
