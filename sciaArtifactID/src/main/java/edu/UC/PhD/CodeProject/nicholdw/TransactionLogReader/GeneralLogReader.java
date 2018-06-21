/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * 
 */
package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.scene.control.TextArea;

public class GeneralLogReader {

	public static void main(String[] args) {
		GeneralLogReader demo = new GeneralLogReader();
		demo.readFromServer("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Data\\device.log");
	}
	/***
	 * Read records that we care about: SELECT queries that access tables in our databases
	 */
	public void readFromServer(String logFilePath) {
		BufferedReader br = null;
		try {
			FileReader generalLogFile = new FileReader(logFilePath);
			br = new BufferedReader(generalLogFile);
			// Skip the header and the column headers
			br.readLine();
			br.readLine();
			br.readLine();
			
			String buffer;
			while (true) {
				buffer = br.readLine();
				//System.out.println(buffer);
				MySQLGeneralLogEntry gle = new MySQLGeneralLogEntry(buffer); 
				 
				if (gle.doWeCare()) {
					if (gle.getText().toUpperCase().startsWith("ALTER")) {
						buffer= br.readLine();
						gle.setText(gle.getText() + " " + buffer.trim());
					}
					System.out.println(gle.toString());
				}
			}
		} catch (Exception ex) {
			System.out.println("GeneralLogReader.readFromServer(): " + ex.getLocalizedMessage());
		}
		try {br.close();} catch (Exception ex) {}
	}
	/***
	 * Read records that we care about: SELECT queries that access tables in our databases
	 */
	public void readFromServer(String logFilePath, TextArea txaOutput) {
		BufferedReader br = null;
		try {
			FileReader generalLogFile = new FileReader(logFilePath);
			br = new BufferedReader(generalLogFile);
			// Skip the header and the column headers
//			br.readLine();
//			br.readLine();
//			br.readLine();
			
			String buffer;
			while (true) {
				buffer = br.readLine();
				//System.out.println(buffer);
				MySQLGeneralLogEntry gle = new MySQLGeneralLogEntry(buffer); 
				 
//				if (gle.doWeCare()) {
					if (gle.getText().toUpperCase().startsWith("ALTER")) {
						buffer= br.readLine();
						gle.setText(gle.getText() + " " + buffer.trim());
					}
					txaOutput.appendText(gle.toString() + "\n");
					//System.out.println(gle.toString());
//				}
			}
		} catch (Exception ex) {
			System.out.println("GeneralLogReader.readFromServer(): " + ex.getLocalizedMessage());
		}
		try {br.close();} catch (Exception ex) {}
	}
}
