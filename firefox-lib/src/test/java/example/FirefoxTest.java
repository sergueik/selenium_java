package example;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class FirefoxTest extends BaseTest {

	private static String baseURL = "http://codef0rmer.github.io/angular-dragdrop/#!/";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@Before
	public void beforeMethod() {
		// Go to URL
		driver.get(baseURL);
	}

	@After
	public void AfterMethod() {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(verificationErrors.toString());
		}
		driver.get("about:blank");
	}

	@Ignore
	@Test
	public void openElementContextMenuTest() {
		// return browser.executeScript(helper.rightMouseBtnClick, selector,
		// {elemIndex: 0, location: {x: 100, y: 100}});
	}

	@Ignore
	@Test
	public void performDnDTest() {
	}

	@Ignore
	@Test
	public void mouseUpTest() {
		// pointCoordinates = {x: 100, y: 100}) {
		// return browser.executeScript(helper.mouseUp, pointCoordinates);
	}

	@Test
	public void mouseDownTest() {
		mouseDown();
	}
}
