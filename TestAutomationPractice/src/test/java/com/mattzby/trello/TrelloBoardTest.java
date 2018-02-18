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
		driver.get(TRELLO_URL);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='board-tile mod-add']"))).click();
		driver.findElement(By.xpath("//div[1]/input[@class='subtle-input']")).sendKeys(boardName);
		driver.findElement(By.xpath("//form[@class='create-board-form']/button[@class='primary']")).click();
		
		//wait until board is created and opened, then return the boards unique URL
		wait.until(ExpectedConditions.urlContains(boardName.replaceAll(" ", "-").toLowerCase()));		
		return driver.getCurrentUrl();		
	}
	
/*	protected void openBoardFromHomeTile(String boardUrl) {
		driver.get(TRELLO_URL);
		
		//sample board url: https://trello.com/b/KnulSL7c/testboard
		//board identifier is everything after https://trello.com
		//in this case board identifier would be /b/KnulSL7c/testboard
		if (boardUrl.contains(TRELLO_URL)){
			String[] parts = boardUrl.split(TRELLO_URL);
			String boardIdentifier = parts[1];	
			System.out.println("BOARD ID = " + boardIdentifier);
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@class='board-tile' and @href='"+ boardIdentifier + "']"))).click();			
		} else {
			throw new IllegalArgumentException("Trello Board URL does not contain m");
		}
	}*/
	
	//Only closes a board
	protected void closeBoard(String boardUrl) {
		driver.get(boardUrl);
		//TODO: handle the scenario where side menu is hidden
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='board-menu-navigation-item-link js-open-more']"))).click();		
		driver.findElement(By.xpath("//li[@class='board-menu-navigation-item']"
				+ "/a[@class='board-menu-navigation-item-link js-close-board']")).click();
		driver.findElement(By.xpath("//div/input[@class='js-confirm full negate' and @value= 'Close']")).click();
	}
	
	//Closes the board and then performs immediate delete from the screen following board closure
	protected void closeAndDeleteBoard(String boardUrl) {
		closeBoard(boardUrl);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='quiet js-delete' and text()='Permanently Delete Board…']"))).click();
		driver.findElement(By.xpath("//input[@class='js-confirm full negate' and @value= 'Delete']")).click();
	}
	
	//Deletes a board that was closed previously
	protected void deleteBoardByDropdown() {
		driver.get(TRELLO_URL);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//a[@class='header-btn header-boards js-boards-menu']/span[@class='header-btn-text' and text()='Boards']"))).click();		
		driver.findElement(By.xpath("//a[@class='quiet-button js-open-closed-boards' and text()='See closed boards…']")).click();
		
		//TODO: Need to get find row that desired board is listed, then click delete button in that same row		
		
	}
	
	//utility method to remove all boards from current user
	protected void deleteAllBoards(){
		
	}

}
