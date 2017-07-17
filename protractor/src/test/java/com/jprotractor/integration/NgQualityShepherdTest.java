package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

/**
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com) 
 * ref. Protractor test spec
 * https://github.com/qualityshepherd/protractor_example/specs/friendSpec.js
 */

public class NgQualityShepherdTest {

	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;
	// set to true for Desktop, false for headless browser testing
	static boolean isCIBuild = false;
	private static String baseUrl = "http://qualityshepherd.com/angular/friends/";

	@BeforeClass
	public static void setup() throws IOException {
		isCIBuild = CommonFunctions.checkEnvironment();
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
		CommonFunctions.setHighlightTimeout(1000);
	}

	@Before
	public void beforeEach() {
    // TODO: investigate the failure under TRAVIS 
    assumeFalse(isCIBuild);
		ngDriver.navigate().to(baseUrl);
	}

	@Test
	public void testAddFriend() {
		// When we add a friend
		String friendName = "John Doe";
		int friendCount = ngDriver.findElements(NgBy.repeater("row in rows"))
				.size();
		NgWebElement addnameBox = ngDriver.findElement(NgBy.model("addName"));
		assertThat(addnameBox, notNullValue());
		highlight(addnameBox);
		addnameBox.sendKeys(friendName);
		NgWebElement addButton = ngDriver.findElement(NgBy.buttonText("+ add"));
		assertThat(addButton, notNullValue());
		highlight(addButton);
		addButton.click();
		ngDriver.waitForAngular();
		// Then there the total number of friends is increased by one
		assertThat(ngDriver.findElements(NgBy.repeater("row in rows")).size()
				- friendCount, equalTo(1));
		// And we can find the new friend using search
		WebElement addedFriendElement = ngDriver.findElements(
				NgBy.cssContainingText("td.ng-binding", friendName)).get(0);
		assertThat(addedFriendElement, notNullValue());
		highlight(addedFriendElement);
		System.err.println("Added friend name: " + addedFriendElement.getText());
	}

	@Test
	public void testSearchAndDeleteFriend() {
		// Given we pick friend to delete
		List<WebElement> friendNames = ngDriver.findElements(NgBy.repeaterColumn(
				"row in rows", "row"));
		WebElement friendName = friendNames.get(0);
		highlight(friendName);
		String deleteFriendName = friendName.getText();
		assertFalse(deleteFriendName.isEmpty());
		// When we delete every friend with the chosen name
		Iterator<WebElement> friendRows = ngDriver.findElements(
				NgBy.repeater("row in rows")).iterator();
		while (friendRows.hasNext()) {
			WebElement currentfriendRow = friendRows.next();
			WebElement currentfriendName = new NgWebElement(ngDriver,
					currentfriendRow).findElement(NgBy.binding("row"));
			if (currentfriendName.getText().indexOf(deleteFriendName) >= 0) {
				System.err.println("Delete: " + currentfriendName.getText());
				WebElement deleteButton = currentfriendRow.findElement(By
						.cssSelector("i.icon-trash"));
				highlight(deleteButton);
				deleteButton.click();
				ngDriver.waitForAngular();
			}
		}
		// That the deleted friend's name cannot be found through search
		System.err.println("Search name: " + deleteFriendName);
		NgWebElement searchBox = ngDriver.findElement(NgBy.model("search"));
		searchBox.sendKeys(deleteFriendName);
		List<WebElement> rows = ngDriver.findElements(NgBy.repeater("row in rows"));
		assertTrue(rows.size() == 0);
		// clear search
		WebElement clearSearchBox = searchBox.findElement(By.xpath(".."))
				.findElement(By.cssSelector("i.icon-remove"));
		assertThat(clearSearchBox, notNullValue());
		System.err.println("Clear Search");
		clearSearchBox.click();
		ngDriver.waitForAngular();
		// And the deleted friend cannot be found by looking at the remaining friend names
		List<WebElement> elements = ngDriver.findElements(NgBy.cssContainingText(
				"td.ng-binding", deleteFriendName));
		assertTrue(elements.size() == 0);
		// examine remaining friends
		friendRows = ngDriver.findElements(NgBy.repeater("row in rows")).iterator();
		while (friendRows.hasNext()) {
			WebElement currentFriendRow = friendRows.next();
			highlight(currentFriendRow);
			String currentFriendName = new NgWebElement(ngDriver, currentFriendRow)
					.evaluate("row").toString();
			System.err.println("Found name: " + currentFriendName);
			assertTrue(currentFriendName.indexOf(deleteFriendName) == -1);
		}
	}

	@Ignore
	// TODO
	@Test
	public void testRemoveAllFriends() {
		ngDriver.waitForAngular();
		List<WebElement> elements = ngDriver.findElements(NgBy
				.repeater("row in rows"));
		assertTrue(elements.size() != 0);
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}
}
