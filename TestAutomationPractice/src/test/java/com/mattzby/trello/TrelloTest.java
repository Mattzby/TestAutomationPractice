package com.mattzby.trello;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class TrelloTest {
	WebDriver driver;
	WebDriverWait wait;
	
	protected static final String TRELLO_URL = "https://trello.com";
	
	//TODO: pass browser in as parameter, add logic to handle Firefox/Chrome/IE
	protected void invokeBrowser(){
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Matt\\Desktop\\Selenium\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
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


