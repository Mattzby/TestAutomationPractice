package com.mattzby.trello;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class TrelloBoardTest extends TrelloTest {
	
	protected String createBoardFromDropdown(String boardName) {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='header-btn header-boards js-boards-menu']/span[@class='header-btn-text']"))).click();		
		driver.findElement(By.xpath("//div[@class='js-board-drawer-buttons']/div/a[@class='quiet-button js-add-board']")).click();		
		driver.findElement(By.xpath("//div[1]/input[@class='subtle-input']")).sendKeys(boardName);
		driver.findElement(By.xpath("//form[@class='create-board-form']/button[@class='primary']")).click();
		
		//wait until board is created and opened, then return the boards unique URL
		wait.until(ExpectedConditions.urlContains(boardName.replaceAll(" ", "-").toLowerCase()));		
		return driver.getCurrentUrl();
	}
	
	protected String createBoardFromHomeTile(String boardName) {
		driver.get("http://www.trello.com");
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='board-tile mod-add']"))).click();
		driver.findElement(By.xpath("//div[1]/input[@class='subtle-input']")).sendKeys(boardName);
		driver.findElement(By.xpath("//form[@class='create-board-form']/button[@class='primary']")).click();
		
		//wait until board is created and opened, then return the boards unique URL
		wait.until(ExpectedConditions.urlContains(boardName.replaceAll(" ", "-").toLowerCase()));		
		return driver.getCurrentUrl();		
	}
	
	protected void openBoardFromHomeTile(String boardUrl) {
		driver.get("http://www.trello.com");
		
		//sample board url: https://trello.com/b/KnulSL7c/testboard
		//board identifier is everything after https://trello.com
		//in this case board identifier would be /b/KnulSL7c/testboard
		if (boardUrl.contains("m")){
			String[] parts = boardUrl.split("m");
			String boardIdentifier = parts[1];	
			System.out.println("BOARD ID = " + boardIdentifier);
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@class='board-tile' and @href='"+ boardIdentifier + "']"))).click();			
		} else {
			throw new IllegalArgumentException("Trello Board URL does not contain m");
		}
	}
	
	protected void closeBoard(String boardUrl) {
		
	}
	
	
	//utility method to remove all boards from current user
	protected void deleteAllBoards(){
		
	}

}
