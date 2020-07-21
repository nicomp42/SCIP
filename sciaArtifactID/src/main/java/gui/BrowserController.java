/*
 * See http://www.java2s.com/Tutorials/Java/JavaFX/1500__JavaFX_WebEngine.htm for interfacing to the WebBrowser control. Cool stuff
 *
 * https://github.com/mozilla/geckodriver/releases for Gecko Driver

 */
package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;


public class BrowserController {
	private Stage myStage;
	private Scene myScene;
	private String neo4jURL = "http://127.0.0.1:7474/browser/";
	private org.w3c.dom.Document xmlDom;
	@FXML private Button btnBrowse;
	@FXML private WebView wbNeo4j;
	@FXML private Label lblStatus;
	@FXML void mnuFileExit_OnAction(ActionEvent event) {Platform.exit();}
	@FXML void mnuHelpAbout_OnClick(ActionEvent event) {openAboutWindow();}
	@FXML
	void btnBrowse_OnClick(ActionEvent event) {
		// Refresh the Web Control
//		refreshWebControl();
		remoteControl();
	}

	public void remoteControl() {
		Log.logProgress("BrowserController.remoteControl()");
		try {
			System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
			 WebDriver driver;
			 driver = new FirefoxDriver();
			 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	// Wait for the textarea to become visible
			 startBrowser(driver);
			 loginToNeo4j(driver);

			 displayGraphPane(driver);
			 displayEntireGraph(driver);

			 SubmitCypherStatement(driver, "Match (a:Attribute {name:'BetaFieldB'}) return a");

/*
			 driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);	// Wait for the textarea to become visible
			 WebElement fpp = driver.findElement(By.cssSelector("div.CodeMirror-gutter.cypher-hints"));
			 System.out.println("IsDisplayed = " + fpp.isDisplayed());
			 fpp.sendKeys("Match (a:Attribute {name:'BetaFieldB'}) return a");
			 driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div[1]/div[1]/div/div/div[1]/textarea")).sendKeys("Match (a:Attribute {name:'BetaFieldB'}) return a");
*/


//			 driver.findElement(By.cssSelector("span.bputMa")).click();

		} catch (Exception ex) {
			Log.logError("BrowserController.remoteControl(): " + ex.getLocalizedMessage());
		}
	}
	private void startBrowser(WebDriver driver) {
		 driver.get("http://127.0.0.1:7474/browser/");
	}
	private void loginToNeo4j(WebDriver driver) {
		Log.logProgress("BrowserController.LoginToNeo4j()");
		try {
			driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Danger42");
			driver.findElement(By.cssSelector("button.gcQmAh")).click();
		} catch (Exception ex) {
			Log.logError("BrowserController.LoginToNeo4j(): " + ex.getLocalizedMessage());
		}
	}
	private void displayGraphPane(WebDriver driver) {
		 driver.findElement(By.name("DB")).click();
	}
	private void displayEntireGraph(WebDriver driver) {
		 List<WebElement> we;
		 we = driver.findElements(By.className("cGoIxi"));
		 we.get(0).click();
	}

	private void SubmitCypherStatement(WebDriver driver, String cypherStatement) {
		 List<WebElement> we;
		 // type the content of chpterStatement into the text area
		 we = driver.findElements(By.className("hHDPGA"));
		 we.get(0).click();
		 we.get(0).sendKeys(cypherStatement);

		 // Click the Play button to run what we put in the text area
		 we = driver.findElements(By.className("bputMa"));
		 we.get(0).click();
	}
	private void refreshWebControl() {
		WebEngine webEngine = wbNeo4j.getEngine();
		webEngine.getLoadWorker().stateProperty()
        .addListener((obs, oldValue, newValue) -> {
          if (newValue == State.SUCCEEDED) {
        	  lblStatus.setText("finished loading");
        	  xmlDom  = webEngine.getDocument();
          }
        }); // addListener()

//		webEngine.load("https://yahoo.com");
		lblStatus.setText("loading " + neo4jURL + "...");
		webEngine.load(neo4jURL);
//		bolt://127.0.0.1:7687 is the connection address for the server when neo4j starts up
	}
	public void openAboutWindow() {
		try {
			FXMLLoader fxmlLoader = null;
//			Open the New Project Window
			fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("About");
			Scene scene = new Scene(root);//, 700, 450);
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            }
	        });
			stage.setResizable(false);
			stage.setScene(scene);
			AboutController about = fxmlLoader.getController();
			about.setScene(scene);
			about.setStage(stage);
			stage.show();
		} catch (Exception ex) {
			Log.logError("BrowserController.openAboutWindow():" + ex.getLocalizedMessage());
		}
	}
	public void setScene(Scene scene) {
		this.myScene = scene;
	}
	public Stage getStage() {return myStage;}
	public void setStage(Stage myStage) {
		this.myStage = myStage;
		setTheScene();
	}
	private void setTheScene() {

	}
	@FXML
	private void initialize() { // Automagically called by JavaFX
		Log.logProgress("BrowserController.Initialize() starting...");
		try {
			setTheScene();
		} catch (Exception e) {
			Log.logError("BrowserController.Initialize(): " + e.getLocalizedMessage());
		}
		Log.logProgress("BrowserController.Initialize() complete");
	}

}
