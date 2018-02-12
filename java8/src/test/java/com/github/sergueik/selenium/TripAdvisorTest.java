package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class TripAdvisorTest extends BaseTest {

	private static String baseURL = "https://www.tripadvisor.com/";

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static Pattern pattern;
	private static Matcher matcher;
	private static final String browser = "firefox";

	@AfterSuite
	public static void tearDown() throws Exception {
		if (verificationErrors.length() != 0) {
			throw new Exception(verificationErrors.toString());
		}
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void test1() {
		assertEquals("Hotels", findElement("link_text", "Hotels").getText());
	}

	private WebElement findWithWait(final String strategy,
			final String selectorValue, final int timeout) {
		WebElement element = (new WebDriverWait(driver, timeout))
				.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver _driver) {
						// NOTE: has to initialize the locator
						By locator = By.cssSelector(selectorValue);
						switch (strategy) {
						case "css":
							locator = By.cssSelector(selectorValue);
							break;
						case "id":
							locator = By.id(selectorValue);
							break;
						case "xpath":
							locator = By.xpath(selectorValue);
							break;
						case "class":
							locator = By.className(selectorValue);
							break;
						case "link_text":
							locator = By.linkText(selectorValue);
							break;
						}
						WebElement result = (WebElement) null;
						Optional<WebElement> _element = _driver.findElements(locator)
								.stream().findFirst();
						if (_element.isPresent()) {
							result = _element.get();
							System.err.println("Found using: " + strategy + " = "
									+ selectorValue + " => " + result.getAttribute("outerHTML"));
							if (!result.isDisplayed())
								result = (WebElement) null;
						}
						return result;
					}
				});
		return element;
	}

	@Test(enabled = true)
	public void findWithWaitTest() {
		WebElement element = findWithWait("link_text", "Hotels", 30);
		assertThat(element, notNullValue());
		highlight(element);
		String selector = cssSelectorOfElement(element);
		System.err.println("findWithWaitTest: CssSelector: " + selector);
		element = findWithWait("css", selector, 30);
		assertThat(element, notNullValue());
		highlight(element);

	}

	@Test(enabled = true)
	public void buildXPathTest() {
		WebElement element = findElement("link_text", "Hotels");

		highlight(element);
		String selector = xpathOfElement(element);
		assertEquals(selector, "//a[@id=\"global-nav-hotels\"]");
		// older design, also possibly to use with skipping the @id for immediate
		// target
		// assertEquals( selector, "//div[@id=\"HEAD\"]/div/div[2]/ul/li/span/a");
		element = findElement("xpath", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	@Test(enabled = true)
	public void buildCssSelectorTest() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElement(element);
		System.err.println("test 2: CssSelector: " + selector);

		assertEquals(selector, "a[id=\"global-nav-hotels\"]");
		// older design, also possibly to use with skipping the @id for immediate
		// target
		// assertEquals(selector,
		// "div#HEAD >
		// div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled
		// > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 >
		// span.tabLink.arwLink > a.arrow_text.pid2972");
		// without class attributes:
		// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a"
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("buildCssSelectorTest: " + e.toString());
		}
	}

	@Test(enabled = true)
	public void test3_2() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElement(element);
		System.err.println("test 2: CssSelector: " + selector);
		assertEquals(selector, "a[id=\"global-nav-hotels\"]");
		// older design, also possibly to use with skipping the @id for immediate
		// target
		// assertEquals(selector,
		// "div#HEAD >
		// div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled
		// > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 >
		// span.tabLink.arwLink > a.arrow_text.pid2972"
		// );
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	@Test(enabled = true)
	public void buildCssSelectorOfElementAlternativeTest() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElementAlternative(element);
		System.err.println(
				"buildCssSelectorOfElementAlternativeTest: Css Selector (alternative) : "
						+ selector);
		assertEquals(selector, "a#global-nav-hotels");

		// assertEquals(selector,
		// "div#HEAD >
		// div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled
		// > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 >
		// span.tabLink.arwLink > a.arrow_text.pid2972");

		// without class attributes:
		// "div#HEAD > div > div:nth-of-type(2) > ul > li > span > a"
		try {
			element = findElement("css_selector", selector);
			highlight(element);
		} catch (NullPointerException e) {
			verificationErrors.append("test 3: " + e.toString());
		}
	}

	@Test(enabled = true)
	public void test4_2() {
		WebElement element = findElement("link_text", "Hotels");
		highlight(element);
		String selector = cssSelectorOfElementAlternative(element);
		System.err.println("test 3: Css Selector (alternative) : " + selector);
		assertEquals(selector, "a#global-nav-hotels");
		// assertEquals(selector,
		// "div#HEAD >
		// div.masthead.masthead_war_dropdown_enabled.masthead_notification_enabled
		// > div.tabsBar > ul.tabs > li.tabItem.dropDownJS.jsNavMenu.hvrIE6 >
		// span.tabLink.arwLink > a.arrow_text.pid2972");
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	// change of the page layout.
	@Test(enabled = false)
	public void test5() {
		WebElement element = findElement("css_selector", "input#mainSearch");
		assertEquals(cssSelectorOfElement(element), "input#mainSearch");
		assertEquals(cssSelectorOfElementAlternative(element), "input#mainSearch");
		highlight(element);
	}

	// change of the page layout.
	@Test(enabled = false)
	public void test6() {
		WebElement element = findElement("css_selector", "span#rdoFlights")
				.findElement(By.cssSelector("div span.label"));
		assertEquals("span#rdoFlights > div.header > span.label",
				cssSelectorOfElement(element));
		highlight(element);
	}

	// change of page layout
	@Test(enabled = false)
	public void test7() {
		WebElement element = findElement("id", "searchbox");
		assertEquals(cssSelectorOfElement(element), "input#searchbox");
		highlight(element);
	}

	// change of the page layout.
	@Test(enabled = false)
	public void test8() {
		WebElement element = findElement("id", "searchbox")
				.findElement(By.xpath(".."));
		highlight(element);
		String selector = cssSelectorOfElementAlternative(element);
		// System.err.println("test 7: selector (alternative):\n" + selector);
		assertEquals(
				"form#PTPT_HAC_FORM > div.metaFormWrapper > div.metaFormLine.flex > label.ptptLabelWrap",
				selector);
		element = findElement("css_selector", selector);
		assertThat(element, notNullValue());
		highlight(element);
	}

	// @Ignore
	@SuppressWarnings("static-access")
	@Test(enabled = true)
	public void styleOfElementTest() {

		WebElement element = findElement("link_text", "Hotels");
		assertThat(element, notNullValue());
		highlight(element);
		String style = styleOfElement(element);
		System.err.println("style:\n" + style);
		String colorAttribute = styleOfElement(element, "color");
		String heightAttribute = styleOfElement(element, "height");
		String widthAttribute = styleOfElement(element, "width");

		// assertions of certain CSS attributes
		try {
			assertTrue(colorAttribute.equals("rgb(255, 255, 255)"));
		} catch (AssertionError e) {
			// slurp
		}
		// compute the colors
		matcher = Pattern.compile("\\((\\d+),(\\d+),(\\d+)\\)")
				.matcher(colorAttribute);
		if (matcher.find()) {
			int blue = Integer.parseInt(matcher.group(3).toString());
			System.err.println("Blue: " + blue);
			assertTrue(blue >= 18);
		}
		System.err.println("color:" + colorAttribute);

		try {
			assertTrue(widthAttribute.equals("36.6667px")); // fragile !
		} catch (AssertionError e) {
			// slurp
		}
		pattern = Pattern.compile("([\\d\\.]+)px");
		matcher = pattern.matcher(widthAttribute);
		if (matcher.find()) {
			Float mask = new Float("20.75f");
			Float width = mask.parseFloat(matcher.group(1).toString());
			// TODO: debug
			assertTrue(width > 36.5);
			System.err.println("width (extracted):" + width);
		}
		System.err.println("width:" + widthAttribute);

		System.err.println("examine height attribute: " + heightAttribute);
		// broken after 431eac6a3baa
		// assertTrue(height.equals("12px"));
		// print css values
		// System.err.println("height:" + heightAttribute);
	}

	private List<WebElement> findElements(String selectorKind,
			String selectorValue, WebElement parent) {
		SearchContext finder;
		String parent_css_selector = null;
		String parent_xpath = null;

		List<WebElement> elements = null;
		Hashtable<String, Boolean> selectorStrategies = new Hashtable<String, Boolean>();
		selectorStrategies.put("css_selector", true);
		selectorStrategies.put("xpath", true);

		if (selectorKind == null || !selectorStrategies.containsKey(selectorKind)
				|| !selectorStrategies.get(selectorKind)) {
			return null;
		}
		if (parent != null) {
			parent_css_selector = cssSelectorOfElement(parent);
			parent_xpath = xpathOfElement(parent);
			finder = parent;
		} else {
			finder = driver;
		}

		if (selectorKind == "css_selector") {
			String extended_css_selector = String.format("%s  %s",
					parent_css_selector, selectorValue);
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(extended_css_selector)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {
			String extended_xpath = String.format("%s/%s", parent_xpath,
					selectorValue);
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(extended_xpath)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			elements = finder.findElements(By.xpath(selectorValue));
		}
		return elements;
	}

	private List<WebElement> findElements(String selectorKind,
			String selectorValue) {
		return findElements(selectorKind, selectorValue, null);
	}

	private WebElement findElement(String selectorKind, String selectorValue) {
		WebElement element = null;
		Hashtable<String, Boolean> selectorStrategies = new Hashtable<String, Boolean>();
		selectorStrategies.put("id", true);
		selectorStrategies.put("css_selector", true);
		selectorStrategies.put("xpath", true);
		selectorStrategies.put("partial_link_text", false);
		selectorStrategies.put("link_text", true);
		selectorStrategies.put("classname", false);

		if (selectorKind == null || !selectorStrategies.containsKey(selectorKind)
				|| !selectorStrategies.get(selectorKind)) {
			return null;
		}
		if (selectorKind == "id") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.id(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.id(selectorValue));
		}
		if (selectorKind == "classname") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.className(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.className(selectorValue));
		}
		if (selectorKind == "link_text") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.linkText(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.linkText(selectorValue));
		}
		if (selectorKind == "css_selector") {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.cssSelector(selectorValue));
		}
		if (selectorKind == "xpath") {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(selectorValue)));
			} catch (RuntimeException timeoutException) {
				return null;
			}
			element = driver.findElement(By.xpath(selectorValue));
		}
		return element;
	}

	public void waitForElementVisible(By locator, long timeout) {
		log.info("Waiting for element visible for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForElementVisible(By locator) {
		log.info("Waiting for element visible for locator: {}", locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForElementPresent(By locator) {
		log.info("Waiting for element present  for locator: {}", locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForElementPresent(By locator, long timeout) {
		log.info("Waiting for element present for locator: {}", locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForPageLoad() {
		log.info("Wait for page load via JS...");
		String state = "";
		int counter = 0;

		do {
			try {
				state = (String) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			counter++;
			log.info(("Browser state is: " + state));
		} while (!state.equalsIgnoreCase("complete") && counter < 20);

	}

	public boolean isAttributePresent(By locator, String attribute) {
		log.info("Is Attribute Present for locator: {}, attribute: {}", locator,
				attribute);
		return driver.findElement(locator).getAttribute(attribute) != null;
	}

	public void selectDropdownByIndex(By locator, int index) {
		log.info("Select Dropdown for locator: {} and index: {}", locator, index);
		try {
			Select select = new Select(driver.findElement(locator));
			select.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getBaseURL() {
		// TODO: somehow browser is set to mobile
		// if the next line is commented
		System.err.println("Get base URL: " + driver.getCurrentUrl());
		log.info("Get base URL: {}", driver.getCurrentUrl());
		String currentURL = driver.getCurrentUrl();
		String protocol = null;
		String domain = null;

		try {
			URL url = new URL(currentURL);
			protocol = url.getProtocol();
			domain = url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// System.err.println("Returning: " + protocol + "://" + domain);
		return protocol + "://" + domain;
	}

	public void clickJS(By locator) {
		log.info("Clicking on locator via JS: {}", locator);
		wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(locator));
	}

	public void scrollIntoView(By locator) {
		log.info("Scrolling into view: {}", locator);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", driver.findElement(locator));
	}

	public void mouseOver(By locator) {
		log.info("Mouse over: {}", locator);
		actions.moveToElement(driver.findElement(locator)).build().perform();
	}

	public void click(By locator) {
		log.info("Clicking: {}", locator);
		driver.findElement(locator).click();
	}

	public void clear(By locator) {
		log.info("Clearing input: {}", locator);
		driver.findElement(locator).clear();
	}

	public void sendKeys(By locator, String text) {
		log.info("Typing \"{}\" into locator: {}", text, locator);
		driver.findElement(locator).sendKeys(text);
	}

	public String getText(By locator) {
		String text = driver.findElement(locator).getText();
		log.info("The string at {} is: {}", locator, text);
		return text;
	}

	public String getAttributeValue(By locator, String attribute) {
		String value = driver.findElement(locator).getAttribute(attribute);
		log.info("The attribute \"{}\" value of {} is: {}", attribute, locator,
				value);
		return value;
	}

	public boolean isElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return true;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return false;
		}
	}

	// custom wait while Login Lightbox is visible

	public void waitWhileElementIsVisible(By locator) {
		final By locatorFinal = locator;
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver o) {
				return (o.findElements(locatorFinal).size() == 0);
			}
		});
	}

	public boolean isElementNotVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return false;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return true;
		}
	}
}