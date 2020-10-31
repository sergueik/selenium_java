package example;

/**
 * Selected test scenarios for Selenium jBrowserDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuppressWarnings("deprecation")
public class JBrowserDriverTest extends BaseTest {

	private static String selector = null;

	private static String baseURL = "http://www.tripadvisor.com/";
	private static String testFileName = "test.txt";
	private static String testFilePath = new File(testFileName).getAbsolutePath()
			.replaceAll("\\\\", "/");
	private static final Logger log = LogManager
			.getLogger(JBrowserDriverTest.class);

	@Before
	public void beforeTest() {
		driver.get(baseURL);
	}

	@After
	public void afterTest() {
	}

	// downloading test file
	// the jquery file upload plugin issue:
	// https://github.com/MachinePublishers/jBrowserDriver/issues/110
	// the file upload plugin issue:
	// https://github.com/MachinePublishers/jBrowserDriver/issues/143

	@Ignore
	@Test
	public void test1SendKeys() {
		System.err.println("test1SendKeys");
		driver.get("http://blueimp.github.io/jQuery-File-Upload/basic.html");
		element = driver.findElement(By.id("fileupload"));
		assertThat(element, notNullValue());
		// highlight(element);

		assertTrue(element.getAttribute("multiple") != null);
		executeScript("$('#fileupload').removeAttr('multiple');");
		element.sendKeys(testFilePath);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		element = driver.findElement(By.className("progress-bar"));
		assertThat(element.getAttribute("class"),
				containsString("progress-bar-success"));
		element = driver.findElement(By.id("files"));
		assertThat(element.getText(), containsString(testFileName));
	}

	// downloading test file
	@Ignore
	@Test
	public void test2SendKeys() {
		System.err.println("test2SendKeys");
		driver.get("http://siptv.eu/converter/");
		element = driver
				.findElement(By.cssSelector("div#container form#file_form input#file"));
		assertThat(element, notNullValue());
		highlight(element);

		element.sendKeys(testFilePath);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		assertThat(element.getAttribute("value"), containsString(testFileName));

		element = driver.findElement(
				By.cssSelector("div#container form#file_form input#submit"));
		element.click();
	}

	@Test
	public void fastSetTextTest() {
		final String url = "https://www.seleniumeasy.com/test/input-form-demo.html";
		driver.get(url);
		selector = "form#contact_form > fieldset div.form-group div.input-group textarea.form-control";
		element = findElement("css_selector", selector);
		final String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
		try {
			log.info("running fastSetTextTest");
			fastSetText(element, text);
			assertEquals(text, element.getText());
			log.info("completed fastSetTextTest");
		} catch (Error e) {
			log.info("failed fastSetTextTest");
			verificationErrors
					.append("Error in verifyTextTest() : " + e.toString() + "\n");
		}
	}

	// @Ignore
	@Test
	public void verifyTextTest() throws Exception {
		try {
			assertEquals("Hotels", findElement("link_text", "Hotels").getText());
			log.info("completed verifyTextTest");
		} catch (Error e) {
			verificationErrors
					.append("Error in verifyTextTest() : " + e.toString() + "\n");
		}
	}

	@Ignore
	@Test
	public void xpathOfElementTest() throws Exception {
		element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		selector = xpathOfElement(element);
		// Assert
		assertThat(selector, notNullValue());
		assertEquals("//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a", selector);
		element = findElement("xpath", selector);
		assertThat(element, notNullValue());
		highlight(element);
		log.info("completed xpathOfElementTest");
	}

	// NOTE: this test is hanging the jbrowserdriver
	// the site http://suvian.in is down
	// after the test is run orphaned java processess require a taskkill
	// NOTE: java.lang.NoClassDefFoundError:
	// org/openqa/selenium/internal/WrapsDriver
	@Ignore
	@Test
	public void test13_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.3frame.html");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
				By.cssSelector(".container .row .intro-message iframe")));

		// Act
		WebElement buttonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("h3 button"))));

		assertThat(buttonElement, notNullValue());
		buttonElement.click();
		// Assert
		try {
			// confirm alert
			Alert alert = driver.switchTo().alert();
			String alert_text = alert.getText();
			assertThat(alert_text, containsString("You clicked on Green"));
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	// NOTE: org.junit.ComparisonFailure
	@Ignore
	@Test
	public void cssSelectorOfElementWithIdInParentTest() throws Exception {
		element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		selector = cssSelectorOfElement(element);
		assertEquals(
				"div#HEAD > div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 > span.tabLink.arwLink > a.arrow_text.pid2972",
				// NOTE: old script was
				// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a",
				selector);
		assertThat(selector, notNullValue());
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	// @Ignore
	@Test
	public void cssSelectorOfElementTest() {
		try {
			log.info("running cssSelectorOfElementTest");
			element = driver.findElement(By.cssSelector("input[type = 'search']"));
			log.info("running cssSelectorOfElementTest (2)");
			element = findElement("css_selector", "input[type = 'search']");
			log.info("running cssSelectorOfElementTest");
			selector = cssSelectorOfElement(element);
			log.info("running cssSelectorOfElementTest");
			highlight(element);
			assertEquals("input#mainSearch", selector);
			log.info("completed cssSelectorOfElementTest");
		} catch (Error e) {
			log.info("failed cssSelectorOfElementTest");
			verificationErrors.append(
					"Error in cssSelectorOfElementTest() : " + e.toString() + "\n");
		}
	}

	// @Ignore
	@Test
	public void cssSelectorOfElementWithIdTest() {
		try {
			element = findElement("id", "searchbox");
			selector = cssSelectorOfElement(element);
			highlight(element);
			assertEquals("input#searchbox", selector);
			log.info("completed cssSelectorOfElementWithIdTest");

		} catch (Error e) {
			log.info("failed cssSelectorOfElementWithIdTest");
			verificationErrors.append(
					"Error in cssSelectorOfElementWithIdTest() : " + e.toString() + "\n");
		}
	}

	@Ignore
	@Test
	public void testcssSelectorOfElementAlternative() throws Exception {

		try {
			element = findElement("id", "searchbox");
			highlight(element);
			selector = cssSelectorOfElementAlternative(element);
			System.err.println("css_selector: " + selector);
			assertEquals(
					"form[name=\"PTPT_HAC_FORM\"] > div > label > input[name=\"q\"]",
					selector);
		} catch (Error e) {
			verificationErrors
					.append("Error in testcssSelectorOfElementAlternative() : "
							+ e.toString() + "\n");
		}
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors
					.append("Error in testcssSelectorOfElementAlternative() : "
							+ e.toString() + "\n");
		}
	}
}
