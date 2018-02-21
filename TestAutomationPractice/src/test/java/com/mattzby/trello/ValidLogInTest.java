package com.mattzby.trello;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ValidLogInTest extends TrelloTest {	
	
	boolean loginSuccessful = false;
	
	@Parameters({"browser"})
	@BeforeClass
	public void setUpBeforeTestClass(String browser){
		super.invokeBrowser(browser);
		//TODO: Remove login credentials and store in separate file		
		super.logInToTrello("mctesterson1","wordpass123");		
	}
	
	@Test
	public void validLoginTest(){		
		//Check for username web element 
		try{
			WebElement userInitials = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@class='header-btn header-avatar js-open-header-member-menu']/span[@class='member']/span[@class='member-initials']")));
			loginSuccessful = userInitials.isDisplayed();			
		} catch (TimeoutException te){
			//TODO: If an error message is present on the page - pull the text of that error message and put into Assert fail message
			Assert.fail("Could not verify login before timeout.");
		}		
		Assert.assertTrue(loginSuccessful);	
	}
	
	@AfterClass 
	public void tearDownAfterTestClass(){
		if (loginSuccessful){
			super.logOutFromTrello();
		}
		super.closeBrowser();
	}

}
