package com.mattzby.trello;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class TrelloTest {
	WebDriver driver;
	WebDriverWait wait;
	
	protected static final String TRELLO_URL = "https://trello.com";
	String driverPath = "src/resources/drivers/";
	
	//TODO: investigate how to not include .exes directly in project, but extract them from JARs that are pulled in by Gradle
	protected void invokeBrowser(String browser){
		
		//set up driver based on browser parameter
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
		default:
			throw new IllegalArgumentException("Invalid browser requested for WebDriver - " + browser);		
		}
		
		try {
			wait = new WebDriverWait(driver, 10);
			driver.manage().deleteAllCookies();
			//driver.manage().window().maximize();		
			driver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
			driver.get(TRELLO_URL);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void logInToTrello(String userName, String password){
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log In"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("user"))).sendKeys(userName);
		driver.findElement(By.id("password")).sendKeys(password);   
		driver.findElement(By.id("login")).click();
	}
	
	protected void logOutFromTrello(){	
		//I shouldn't have to navigate to Trello.com to log out?
		//TODO: Figure out why logging out from within a Trello board is not working
		driver.get(TRELLO_URL);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='header-btn header-avatar js-open-header-member-menu']/span[@class='member']/span[@class='member-initials']"))).click();				
		driver.findElement(By.xpath("//ul[@class='pop-over-list'][3]/li/a[@class='js-logout']")).click();
	}
	
	protected void closeBrowser(){
		driver.quit();
	}	
	
}


