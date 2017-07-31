
package org.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.EdgeDriverManager;

public class EdgeTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		EdgeDriverManager.getInstance().setup();
	}

	@Before
	public void setupTest() {
		driver = new EdgeDriver();
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() {
		// Your test code here. For example:
		WebDriverWait wait = new WebDriverWait(driver, 30); // 30 seconds of timeout
		driver.get("https://en.wikipedia.org/wiki/Main_Page"); // navigate to
																														// Wikipedia

		By searchInput = By.id("searchInput"); // search for "Software"
		wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
		driver.findElement(searchInput).sendKeys("Software");
		By searchButton = By.id("searchButton");
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		driver.findElement(searchButton).click();

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.tagName("body"), "Computer software")); // assert that the resulting
																										// page contains a text
	}

}
