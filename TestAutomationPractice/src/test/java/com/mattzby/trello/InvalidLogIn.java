package com.mattzby.trello;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InvalidLogIn extends TrelloTest {
	
	@BeforeClass
	public void setUpBeforeTestClass(){
		super.invokeBrowser();
		driver.findElement(By.linkText("Log In")).click();
	}
	
	@Test
	public void invalidUsernameTest(){		
		driver.findElement(By.id("user")).clear();
		driver.findElement(By.id("password")).clear();		
		
		driver.findElement(By.id("user")).sendKeys("mctesterson123");
		driver.findElement(By.id("password")).sendKeys("wordpass");   
		driver.findElement(By.id("login")).click();		
		
		//Check for username error message
		try{
			WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@id='error']/p[@class='error-message' and text()=\"There isn't an account for this username\"]")));	
			Assert.assertTrue(errorMessage.isDisplayed());		
		} catch (TimeoutException te){
			Assert.fail("Expected error message not visible");
		}		
	}
	
	@Test
	public void invalidPasswordTest(){
		driver.findElement(By.id("user")).clear();
		driver.findElement(By.id("password")).clear();
		
		driver.findElement(By.id("user")).sendKeys("mattzby");
		driver.findElement(By.id("password")).sendKeys("wordpass");
		driver.findElement(By.id("login")).click();		
		
		//Check for password error message displayed		
		try{
			WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@id='error']/p[@class='error-message' and text()='Invalid password']")));	
			Assert.assertTrue(errorMessage.isDisplayed());		
		} catch (TimeoutException te){
			Assert.fail("Expected error message not visible");
		}	
	}
	
	//TODO: Create test for too many incorrect login attempts - using @Test(invocationCount = 10)

	@AfterClass 
	public void tearDownAfterTestClass(){
		super.closeBrowser();
	}

}
