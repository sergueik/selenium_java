package net.randomsync.googlesearch.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(name = "q")
	WebElement txtSearch;

	@FindBy(name = "btnK")
	WebElement btnSearch;

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void search(String query) {
		txtSearch.clear();
		txtSearch.sendKeys(query);
		txtSearch.sendKeys(Keys.RETURN);
	}
}
