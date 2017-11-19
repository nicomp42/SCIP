package edu.UC.PhD.CodeProject.nicholdw.browser;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
/**
 * Manage the browser windows that will display Neo4j graphs
 * @author nicomp
 */
public class Browser {
	private String neo4jURL = "http://127.0.0.1:7474/browser/";
	private org.w3c.dom.Document xmlDom;
	private WebDriver driver;
	private String browserID;
	private EventFiringWebDriver eventDriver;
	private static Integer browserSerialNumber = 0;

	public Browser(String browserID) {
		this.browserID = browserID;
	}
	public static Browser prepareNewBrowser() {
		Browser browser = null;
		try {
			if (Config.getConfig().getBrowsers().size() == 0) {
				// Create a new Browser instance and add it to the list
				browser = new Browser("Schema Topology");
				Config.getConfig().getBrowsers().add(browser);
			} else {
				browser = Config.getConfig().getBrowsers().get(0);
			}
		} catch (Exception ex) {
			Log.logError("Browser.prepareNewBrowser(): " + ex.getLocalizedMessage());
		}
		return browser;
	}
	// Do everything necessary to open browser window, log in, and display the entire graph.
	public void initAndLoad(String cypherStatement) {
		initBrowser();
		browseToNeo4j();
		loginToNeo4j();
		displayGraphPane();
		setStyleSheet();
		if (cypherStatement == null) {
			displayEntireGraph();
		} else {
			submitCypherStatement(cypherStatement);
		}
	}
	public void initBrowser() {
		try {
			System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
			driver = new FirefoxDriver();
			// We will use the event-handling driver but it only handles events generated programmatically, not user-generated events. Bummer!
			eventDriver = new EventFiringWebDriver(driver);
			OverrideClass oc = new OverrideClass();
			eventDriver.register(oc);
			eventDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception ex) {
			Log.logError("Browser.initBrowser(): " + ex.getLocalizedMessage());
		}
	}
	public void browseToNeo4j() {
		Log.logProgress("Browser.displayBrowser()");
		try {
			eventDriver.get("http://127.0.0.1:7474/browser/");
		} catch (Exception ex) {
			Log.logError("Browser.displayBrowser(): " + ex.getLocalizedMessage());
		}
	}
	public void setStyleSheet() {
		Log.logProgress("Browser.setStyleSheet()");
		try {
			submitCypherStatement(":style " + Config.getConfig().getGrassStyleSheetURL());
		} catch (Exception ex) {
			Log.logError("Browser.setStyleSheet(): " + ex.getLocalizedMessage());
		}
	}
	public void loginToNeo4j() {
		Log.logProgress("Browser.LoginToNeo4j()");
		try {
			eventDriver.findElement(By.xpath("//input[@type='password']")).sendKeys("Danger42");
			eventDriver.findElement(By.cssSelector("button.gcQmAh")).click();
		} catch (Exception ex) {
			Log.logError("Browser.LoginToNeo4j(): " + ex.getLocalizedMessage());
		}
	}
	public void displayGraphPane() {
		Log.logProgress("Browser.displayGraphPane()");
		try {
			eventDriver.findElement(By.name("DB")).click();
		} catch (Exception ex) {
			Log.logError("Browser.displayGraphPane(): " + ex.getLocalizedMessage());
		}
	}
	public void displayEntireGraph() {
		Log.logProgress("Browser.displayEntireGraph()");
		try {
			submitCypherStatement("MATCH (n) return n");		// No Limit clause
//			 List<WebElement> we;
//			 we = driver.findElements(By.className("cGoIxi"));
//			 we.get(0).click();
		} catch (Exception ex) {
			Log.logError("Browser.displayEntireGraph(): " + ex.getLocalizedMessage());
		}

	}
	public void submitCypherStatement(String cypherStatement) {
		Log.logProgress("Browser.submitCypherStatement()");
		try {
			 List<WebElement> we;
			 // type the content of chpterStatement into the text area
			 we = eventDriver.findElements(By.className("hHDPGA"));
			 we.get(0).click();
			 we.get(0).sendKeys(cypherStatement);

			 // Click the Play button to run what we put in the text area
			 we = eventDriver.findElements(By.className("bputMa"));
			 we.get(0).click();
		} catch (Exception ex) {
			Log.logError("Browser.submitCypherStatement(): " + ex.getLocalizedMessage());
		}
	}
	public String getBrowserID() {
		return browserID;
	}
	public void setBrowserID(String browserID) {
		this.browserID = browserID;
	}
	public static Integer getNextBrowserSerialNumber() {
		browserSerialNumber++;
		return browserSerialNumber;
	}

	public static Browser openBrowserWindow() {
		Browser browser = null;
		try {
			browser = new Browser(Browser.getNextBrowserSerialNumber().toString());
			Config.getConfig().addBrowser(browser);
			browser.initAndLoad(null);
		} catch (Exception ex) {
			Log.logError("Browser.openBrowserWindow(): " , ex);
		}
		return browser;
	}

/*
	public void test() {
		Log.logProgress("Browser.test()");
		try {
			initBrowser();
			displayBrowser();
			loginToNeo4j();
			displayGraphPane();
			displayEntireGraph();
			SubmitCypherStatement("Match (a:Attribute {name:'BetaFieldB'}) return a");

		} catch (Exception ex) {
			Log.logError("Browser.test(): " + ex.getLocalizedMessage());
		}
	}
*/
}
