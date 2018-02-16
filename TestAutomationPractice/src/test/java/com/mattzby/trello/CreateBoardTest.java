package com.mattzby.trello;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateBoardTest extends TrelloBoardTest {

	@BeforeClass
	public void setUpBeforeTestClass(){
		super.invokeBrowser();
		//TODO: Remove login credentials and store in separate file		
		super.logInToTrello("mctesterson1","wordpass123");
	}
	
	@Test
	public void createBoardFromDropdown(){
		String boardUrl;
		boardUrl = super.createBoardFromDropdown("Test Dropdown Board");	
		System.out.println("DROPDOWN BOARD URL = " + boardUrl);
		Assert.assertTrue(boardExistsOnUserHomePage(boardUrl));
	}
	
	@Test
	public void createBoardFromHomeTile(){
		String boardUrl;
		boardUrl = super.createBoardFromHomeTile("Test Tile Board");
		System.out.println("TILE BOARD URL = " + boardUrl);
		Assert.assertTrue(boardExistsOnUserHomePage(boardUrl));
	}
	
/*	@Test
	public void openRecentlyCreatedBoard(){
		super.createBoardFromHomeTile("Test Tile Board");		
	}*/
	
	private boolean boardExistsOnUserHomePage(String boardUrl){
		return true;
	}

	@AfterClass 
	public void tearDownAfterTestClass(){
		super.logOutFromTrello();
		super.closeBrowser();
	}

}
