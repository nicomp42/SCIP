/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.background;

/***
 * A background process
 * @author nicomp
 *
 */
public class BackgroundThread extends Thread {
    
	private BackgroundObject backgroundObject;
	
	BackgroundThread(BackgroundObject backgroundObject) {
		this.backgroundObject = backgroundObject;
	}
	
	public void run() {
        System.out.println("Hello from a thread!");
        backgroundObject.doSomething();
    }
	
}
