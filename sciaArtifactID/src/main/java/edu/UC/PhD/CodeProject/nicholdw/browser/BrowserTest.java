/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
/**
 * Testing that we can launch a FireFox window. I had to update Geckodriver.exe to make this run all the way through
 * @author nicomp
 *
 */
public class BrowserTest {

	public static void main(String[] args) {
		WebDriver driver;
		EventFiringWebDriver eventDriver;
		System.out.println("BrowserTest...");
		try {
			System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions().setProfile(new FirefoxProfile());
			driver = new FirefoxDriver(options);
			// We will use the event-handling driver but it only handles events generated programmatically, not user-generated events. Bummer!
			eventDriver = new EventFiringWebDriver(driver);
			OverrideClass oc = new OverrideClass();
			eventDriver.register(oc);
			eventDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			System.out.println("BrowserTest... made it through");
		} catch (Exception ex) {
			Log.logError("BrowserTest: " + ex.getLocalizedMessage());
		}
	}
}
