package edu.UC.PhD.CodeProject.nicholdw.background;

public class BackgroundObject implements BackgroundBehavior {

	@Override
	public void doSomething() {
		System.out.println("Hello from the Background Object");
		while (true) {
			System.out.println("Beep");
			try {Thread.sleep(1000);} catch (Exception ex) {}
		}
		
	}
}
