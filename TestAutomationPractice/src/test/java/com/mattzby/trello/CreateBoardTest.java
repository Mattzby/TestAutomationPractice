package com.mattzby.trello;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateBoardTest extends TrelloBoardTest {

	String dropdownBoardUrl, tileBoardUrl;
	
	@Parameters({"browser"})
	@BeforeClass
	public void setUpBeforeTestClass(String browser){
		super.invokeBrowser(browser);
		//TODO: Remove login credentials and store in separate file		
		super.logInToTrello("mctesterson1","wordpass123");
	}
	
	@Test
	public void createBoardFromDropdown(){
		dropdownBoardUrl = super.createBoardFromDropdown("Test Dropdown Board");	
		Assert.assertTrue(boardExistsOnUserHomePage(dropdownBoardUrl));
	}
	
	@Test
	public void createBoardFromHomeTile(){
		tileBoardUrl = super.createBoardFromHomeTile("Test Tile Board");
		Assert.assertTrue(boardExistsOnUserHomePage(tileBoardUrl));
	}
		
	private boolean boardExistsOnUserHomePage(String boardUrl){
		driver.get(TRELLO_URL);
		boolean boardFound = false;
		
		if (boardUrl.contains(TRELLO_URL)){
			String[] parts = boardUrl.split(TRELLO_URL);
			String boardIdentifier = parts[1];	
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@class='board-tile' and @href='"+ boardIdentifier + "']")));
			boardFound = true;
		} else {
			throw new IllegalArgumentException("Trello Board URL not formatted correctly - " + boardUrl);
		}
		return boardFound;
	}

	@AfterClass 
	public void tearDownAfterTestClass(){
		super.closeAndDeleteBoard(dropdownBoardUrl);
		super.closeAndDeleteBoard(tileBoardUrl);
		super.logOutFromTrello();
		super.closeBrowser();
	}

}
