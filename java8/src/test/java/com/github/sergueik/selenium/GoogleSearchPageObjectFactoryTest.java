package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// based on https://github.com/iainrose/pageFactory
public class GoogleSearchPageObjectFactoryTest {
	WebDriver driver;

	@Before
	public void setUp() {
		// Create a new instance of a driver
		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		driver = new ChromeDriver(capabilities);
		// Navigate to the right place
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://www.google.ca/");
	}

	@After
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

	@Test
	public void searchForCheese() {
		// Create a new strongly typed class instance of the search page
		// with WebElement fields in the page.
		GoogleSearchPage searchPage = PageFactory.initElements(driver,
				GoogleSearchPage.class);
		// And now do the search.
		searchPage.searchFor("Cheese");
		// Initialize the results page
		GoogleSearchResultsPage resultsPage = PageFactory.initElements(driver,
				GoogleSearchResultsPage.class);
		// http://www.jworks.nl/2013/01/15/introduction-to-hamcrest-string-matchers/
		assertThat(resultsPage.getResultStats(),
				containsString("About 160,000,000 results"));
		// Hamcrest 2.0 has the Matchers.matchesPattern(String)
		// assertThat(resultsPage.getResultStats(),
		// matchesPattern("About 160,000,000 results .*"));
	}

	public static class GoogleSearchPage {

		@FindBy(name = "q")
		private WebElement searchBox;

		public void searchFor(String text) {
			searchBox.sendKeys(text);
			searchBox.submit();
		}

	}

	public static class GoogleSearchResultsPage {

		@FindBy(css = "#resultStats")
		WebElement resultStats;

		public String getResultStats() {
			return resultStats.getText();
		}
	}
}
