/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.background;

public class Main {

	public static void main(String[] args) {
		BackgroundObject backgroundObject = new BackgroundObject();
		BackgroundThread backgroundThread = new BackgroundThread(backgroundObject);

		backgroundThread.start();

	}
}
