package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

// Explore Chrome History setting screen

public class ChromeHistoryTest extends BaseTest {

	private final String site = "www.wikipedia.org";
	private static final String url = "chrome://history/";

	private List<WebElement> elements = new ArrayList<>();
	private WebElement element = null;
	private WebElement element2 = null;

	// browse to some site to be run first
	@Before
	public void browse() {
		if ((browser.equals("chrome") && !isCIBuild)) {
			driver.navigate().to(String.format("https://%s", site));
		}
	}

	@Test
	public void test1() {
		Assume.assumeTrue(browser.equals("chrome"));
		Assume.assumeFalse(isCIBuild);
		Assume.assumeFalse(headless);

		driver.navigate().to(url);
		element = driver.findElement(By.cssSelector("#history-app"));
		elements = shadowDriver.getAllShadowElement(element,
				"#main-container #content");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(1) HTML: " + element.getAttribute("innerHTML"));
		element2 = element.findElement(By.cssSelector("#history"));

		elements = shadowDriver.getAllShadowElement(element2,
				".history-cards history-item");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(2) HTML: " + element2.getAttribute("outerHTML"));
		elements = shadowDriver.getAllShadowElement(element2, "#main-container");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(3) HTML: " + element.getAttribute("outerHTML"));
		elements = element.findElements(By.cssSelector("#date-accessed"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		assertThat(element2.getText(), containsString("Today"));
		System.err.println("Element(4) text: " + element2.getText());
		elements = element.findElements(By.cssSelector("#title-and-domain"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(5) HTML: " + element2.getAttribute("outerHTML"));
		elements = element2.findElements(By.cssSelector("#domain"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2.getText(), containsString(site));
		System.err.println("Element(6) text: " + element2.getText());
	}

	// the locating the shadow elements relies on querySelector, and does not
	// support evaluate
	// https://developer.mozilla.org/en-US/docs/Web/API/Document/evaluate
	// https://developer.mozilla.org/en-US/docs/Web/XPath/Introduction_to_using_XPath_in_JavaScript
	// but once into the hadow DOM, further down one can use xpath
	@Test
	public void test3() {
		Assume.assumeTrue(browser.equals("chrome"));
		Assume.assumeFalse(isCIBuild);
		Assume.assumeFalse(headless);

		driver.navigate().to(url);
		element = driver.findElement(By.cssSelector("#history-app"));
		elements = shadowDriver.getAllShadowElement(element,
				"#main-container #content");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(1) HTML: " + element.getAttribute("innerHTML"));
		// NOTE: need relative XPath
		element2 = element.findElement(By.xpath(".//*[@id='history']"));

		elements = shadowDriver.getAllShadowElement(element2,
				".history-cards history-item");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(2) HTML: " + element2.getAttribute("outerHTML"));
		elements = shadowDriver.getAllShadowElement(element2, "#main-container");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(3) HTML: " + element.getAttribute("outerHTML"));
		// NOTE: need relative XPath
		elements = element.findElements(By.xpath(".//*[@id='date-accessed']"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		assertThat(element2.getText(), containsString("Today"));
		System.err.println("Element(4) text: " + element2.getText());
		// NOTE: need relative XPath
		elements = element.findElements(By.xpath(".//*[@id='title-and-domain']"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(5) HTML: " + element2.getAttribute("outerHTML"));
		// NOTE: need relative XPath
		elements = element2.findElements(By.xpath(".//*[@id='domain']"));
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2.getText(), containsString(site));
		System.err.println("Element(6) text: " + element2.getText());
	}

	// @Ignore
	// browses through Shadow roots, but a wrong pick
	@Test
	public void test2() {
		Assume.assumeTrue(browser.equals("chrome"));
		Assume.assumeFalse(isCIBuild);
		Assume.assumeFalse(headless);

		driver.navigate().to(url);
		element = driver.findElement(By.cssSelector("#history-app"));
		elements = shadowDriver.getAllShadowElement(element,
				"#main-container #content");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(1) HTML: " + element.getAttribute("innerHTML"));
		element2 = element.findElement(By.cssSelector("#history"));

		elements = shadowDriver.getAllShadowElement(element2, ".history-cards");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		assertThat(element2, notNullValue());
		if (debug)
			System.err
					.println("Element(2) HTML: " + element2.getAttribute("outerHTML"));
		elements = shadowDriver.getAllShadowElement(element2, "#items");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		if (debug)
			System.err
					.println("Element(3) HTML: " + element.getAttribute("outerHTML"));
		// nowhere to continue
	}
}
