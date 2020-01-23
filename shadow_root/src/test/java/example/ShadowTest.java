package example;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/*

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

*/

// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import example.ShadowDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ShadowTest extends BaseTest {

	private final static String baseUrl = "https://www.virustotal.com";
	private static final String urlLocator = "*[data-route=\"url\"]";

	@Before
	public void beforMethod() {
		driver.navigate().to(baseUrl);
	}

	@Test
	public void testJSInjection() {
		WebElement element = shadowDriver.findElement(urlLocator);
		err.println(element);
		String pageSource = shadowDriver.getDriver().getPageSource();
		assertThat(pageSource, notNullValue());
	}

	@Test
	public void testGetAllObject() {
		List<WebElement> elements = shadowDriver.findElements(urlLocator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d %s elements:", elements.size(), urlLocator));
		// NOTE: default toString() is not be particularly useful
		elements.stream().forEach(err::println);
		elements.stream().map(o -> o.getTagName()).forEach(err::println);
		elements.stream()
				.map(o -> String.format("innerHTML: %s", o.getAttribute("innerHTML")))
				.forEach(err::println);
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	@Test
	public void testAPICalls1() {
		WebElement element = shadowDriver.findElements(urlLocator).stream()
				.filter(o -> o.getTagName().matches("div")).collect(Collectors.toList())
				.get(0);

		WebElement element1 = shadowDriver.getNextSiblingElement(element);
		assertThat(element1, notNullValue());
		// TODO: compare to the collection of elements returned earlier
	}

	@Test
	public void testAPICalls2() {
		WebElement element = shadowDriver.findElements(urlLocator).stream()
				.filter(o -> o.getTagName().matches("div")).collect(Collectors.toList())
				.get(0);
		List<WebElement> elements = shadowDriver.findElements(element, "img");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
	}

	@Ignore
	@Test
	public void testAPICalls3() {
		WebElement element = null;

		for (String locator : Arrays
				.asList(new String[] { "#wrapperLink", "*[data-route='url']" })) {
			try {
				element = shadowDriver.findElement(locator);
			} catch (ElementNotVisibleException e) {
				err.println("Exception (ignored): " + e.toString());
			}
			if (element != null) {
				try {
					List<WebElement> elements = shadowDriver.getChildElements(element);
					assertThat(elements, notNullValue());
					assertThat(elements.size(), greaterThan(0));
					err.println(String.format("Located %d child elements in %s:",
							elements.size(), locator));
				} catch (JavascriptException e) {
					err.println("Exception (ignored): " + e.toString());
					// TODO:
					// javascript error: Illegal invocation
					// https://stackoverflow.com/questions/10743596/why-are-certain-function-calls-termed-illegal-invocations-in-javascript
				}
			}
		}
	}

	@Ignore
	// failing because of quotes ?
	@Test
	public void testAPICalls31() {
		WebElement element = null;

		String locator = "*[data-route=\"url\"]";
		try {
			element = shadowDriver.findElement(locator);
		} catch (ElementNotVisibleException e) {
			err.println("Exception (ignored): " + e.toString());
		}
		if (element != null) {
			try {
				List<WebElement> elements = shadowDriver.getChildElements(element);
				assertThat(elements, notNullValue());
				assertThat(elements.size(), greaterThan(0));
				err.println(String.format("Located %d child elements in %s:",
						elements.size(), locator));
			} catch (JavascriptException e) {
				err.println("Exception (ignored): " + e.toString());
				// TODO:
				// javascript error: Illegal invocation
				// https://stackoverflow.com/questions/10743596/why-are-certain-function-calls-termed-illegal-invocations-in-javascript
			}
		}

	}

	@Ignore
	@Test
	public void testAPICalls41() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.getAllShadowElement(element,
				"#wrapperLink");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d #wrapperLink elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
		element = elements.get(0);
		elements.clear();
		try {
			// TODO: javascript error: Cannot read property 'querySelectorAll' of null
			shadowDriver.setDebug(true);
			elements = shadowDriver.getAllShadowElement(element, "span");
			shadowDriver.setDebug(false);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));
			err.println(String.format("Located %d child elements", elements.size()));
		} catch (JavascriptException e) {
			err.println("Exception (ignored): " + e.toString());

			// TODO:
			// javascript error: Cannot read property 'querySelectorAll' of null
			// org.openqa.selenium.JavascriptException: missing ; before statement
		}
	}

	@Ignore
	@Test
	public void testAPICalls4() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.findElements(element,
				"#wrapperLink");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d #wrapperLink elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
		element = elements.get(0);
		elements.clear();
		try {
			// TODO: javascript error: Illegal invocation
			//
			shadowDriver.setDebug(true);
			elements = shadowDriver.getChildElements(element);
			shadowDriver.setDebug(false);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));
			err.println(String.format("Located %d child elements", elements.size()));
		} catch (JavascriptException e) {
			err.println("Exception (ignored): " + e.toString());
			// TODO:
			// javascript error: Illegal invocation
			// https://stackoverflow.com/questions/10743596/why-are-certain-function-calls-termed-illegal-invocations-in-javascript
		}
	}

	// @Ignore
	@Test
	public void testAPICalls5() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.findElements(element,
				"#wrapperLink");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d #wrapperLink elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	@Test
	public void testAPICalls6() {
		WebElement element = shadowDriver.findElement(urlLocator);
		List<WebElement> elements = shadowDriver.getSiblingElements(element);
		assertThat(elements, notNullValue());
		// assertThat(elements.size(), greaterThan(0));
	}
}
