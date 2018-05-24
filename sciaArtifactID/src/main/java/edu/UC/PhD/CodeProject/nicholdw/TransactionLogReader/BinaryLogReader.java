/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * https://github.com/shyiko/mysql-binlog-connector-java
 * 
 * Useful info for reading binary logs: https://dzone.com/articles/identifying-useful-info-mysql
 */

package edu.UC.PhD.CodeProject.nicholdw.TransactionLogReader;

import java.io.File;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;

/**
 * Development for MySQL Binary Transaction Log reader using the code at https://github.com/shyiko/mysql-binlog-connector-java
 * @author nicomp
 *
 */
public class BinaryLogReader {

	public static void main(String[] args) {
		BinaryLogReader demo = new BinaryLogReader();
		//demo.readFromBinaryLogFile();
		
		demo.readFromServer();
		
	}
	
	public void readFromServer() {
		try {
			BinaryLogClient blc = new BinaryLogClient("localhost", 3306, "root", "Danger42");
	
			blc.registerEventListener(new EventListener() {
	
			    @Override
			    public void onEvent(Event event) {
					//System.out.println(event.getHeader().toString());
					//System.out.println(event.getData().toString());
					System.out.println(event.toString());
			    }
			});		
			blc.connect();

			//blc.disconnect();
		} catch (Exception ex) {
			System.out.println("readFromServer: " + ex.getLocalizedMessage());
		}
	}

	public void readFromBinaryLogFile() {
		File binlogFile = new File("C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Data\\device-bin.000001");
		BinaryLogFileReader reader = null;
		try {
			reader = new BinaryLogFileReader(binlogFile);
			for (Event event; (event = reader.readEvent()) != null; ) {
				try {
					//System.out.println(event.getClass().toString());
					//System.out.println(event.getData().toString());
					System.out.println(event.getHeader().toString());
					System.out.println(event.getData().toString());
					//System.out.println(event.toString());
				} catch(Exception ex) {}
			}
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		} finally {
			try {
				reader.close();
			} catch (Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
	}
}
