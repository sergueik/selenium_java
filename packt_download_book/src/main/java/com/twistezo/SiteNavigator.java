package com.twistezo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

class SiteNavigator {
	private static Settings settings = Settings.getInstance();
	private static final Logger LOG = LogManager.getLogger(SiteNavigator.class);
	private static WebDriver driver = settings.getDriver();
	private static Actions builder = settings.getBuilder();
	private static SiteNavigator instance = null;
	private char[] bookIdFromUrl = new char[5];
	private String bookId;
	private String bookTitle;

	private SiteNavigator() {
	}

	static SiteNavigator getInstance() {
		if (instance == null) {
			instance = new SiteNavigator();
		}
		return instance;
	}

	{
		LOG.info("Start navigation on page.");
		goToUrl();
		clickButtonToGetFreeBook();
		logInToSite(settings.getLogin(), settings.getPass());
		retrieveBookIdFromUrl();
		retrieveBookTitle();
		clickButtonToGetFreeBook();
		clickBookAfterLocalizeItsByTitle();
		LOG.info("Navigation on page complete.");
	}

	private void goToUrl() {
		driver.navigate().to(settings.getPAGE_URL());
	}

	private void clickButtonToGetFreeBook() {
		waitFor(1000);
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE);
		action.perform();
		waitFor(1000);
		driver
				.findElement(
						By.xpath("//div[contains(@class, 'book-claim-token-inner' )]"))
				.click();
	}

	private void logInToSite(String login, String password) {
		WebElement email = driver.findElement(By.id("email"));
		WebElement pass = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.id("edit-submit-1"));
		Actions seriesOne = builder.moveToElement(email).click().sendKeys(email,
				login);
		Actions seriesTwo = builder.moveToElement(pass).click()
				.sendKeys(pass, password).click(loginButton);
		seriesOne.perform();
		seriesTwo.perform();
	}

	private void retrieveBookIdFromUrl() {
		String bookUrlWithId = driver
				.findElement(By.xpath("//a[contains(@class, 'twelve-days-claim' )]"))
				.getAttribute("href");
		bookUrlWithId.getChars(44, 49, bookIdFromUrl, 0);
		bookId = String.valueOf(bookIdFromUrl);
	}

	private void retrieveBookTitle() {
		bookTitle = driver
				.findElement(
						By.xpath("//*[@id=\"deal-of-the-day\"]/div/div/div[2]/div[2]/h2"))
				.getText();
	}

	private void clickBookAfterLocalizeItsByTitle() {
		driver
				.findElement(By.xpath("//div[contains(text(), '" + bookTitle + "' )]"))
				.click();
	}

	String getBookTitle() {
		return bookTitle;
	}

	String getBookId() {
		return bookId;
	}

	private void waitFor(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
