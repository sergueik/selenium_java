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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Maps;

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
import org.openqa.selenium.By;
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

public class LocalFileTest extends BaseTest {

	private static String filePath = null;

	// @Ignore
	@Test
	public void test1() {
		filePath = "index.html";
		driver.navigate().to(getPageContent(filePath));
		WebElement element = shadowDriver.findElement("#container");
		List<WebElement> elements = shadowDriver.getAllShadowElement(element,
				"#inside");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d shadow document elements:", elements.size()));
		elements.stream()
				.map(o -> String.format("outerHTML: %s", o.getAttribute("outerHTML")))
				.forEach(err::println);
	}

	@Test
	public void test2() {
		filePath = "button.html";
		driver.navigate().to(getPageContent(filePath));
		shadowDriver = new ShadowDriver(driver);
		WebElement element = shadowDriver.findElement("button");
		shadowDriver.scrollTo(element);
		assertThat(element, notNullValue());
		err.println("outerTML: " + shadowDriver.getAttribute(element, "outerHTML"));
		List<WebElement> elements = shadowDriver.getChildElements(element);
		assertThat(elements, notNullValue());
		err.println(elements);
		// java.lang.ClassCastException:
		// com.google.common.collect.Maps$TransformedEntriesMap cannot be cast to
		// org.openqa.selenium.WebElement
		try {
			element = elements.get(0);
			err.println(element);
		} catch (ClassCastException e) {
			err.println("Exception(ignored): " + e.toString());
		}
	}

	@Ignore
	@Test
	public void test3() {

		filePath = "button.html";
		driver.navigate().to(getPageContent(filePath));
		WebElement parent = shadowDriver.findElement("body");
		assertThat(parent, notNullValue());
		// Cannot read property 'querySelector' of null
		WebElement element = shadowDriver.getShadowElement(parent, "button");

		assertThat(element, notNullValue());
	}

	@Ignore
	@Test
	public void test4() {

		filePath = "inner_html_example.html";
		driver.navigate().to(getPageContent(filePath));
		WebElement element1 = driver.findElement(By.cssSelector("h3"));
		assertThat(element1, notNullValue());
		err.println("innerHTML: " + element1.getAttribute("innerHTML"));

		WebElement element2 = shadowDriver.findElement("h3");
		assertThat(element2, notNullValue());
		err.println("innerTML: " + shadowDriver.getAttribute(element2, "innerHTM"));
	}

	@Test
	public void test6() {
		filePath = "inner_html_example.html";
		driver.navigate().to(getPageContent(filePath));
		WebElement element = driver.findElement(By.cssSelector("body > div > h3"));
		assertThat(element, notNullValue());
		err.println("outerHTML: " + element.getAttribute("outerHTML"));
		err.println(String.format("Text: \"%s\"", element.getText()));
		// TODO: assert
		assertThat(element.getText(), is(""));
		WebElement parent = shadowDriver.findElement("body > div");
		assertThat(parent, notNullValue());
		err.println("Parent outerHTML: " + parent.getAttribute("outerHTML"));
		err.println(
				String.format("Parent text(old API): \"%s\"", parent.getText()));
		element = null;
		element = shadowDriver.getShadowElement(parent, "h3");
		assertThat(element, notNullValue());
		err.println("Got shadow element: " + element); // toString
		err.println("outerHTML (old API): " + element.getAttribute("outerHTML"));
		err.println("outerHTML (new API): "
				+ shadowDriver.getAttribute(element, "outerHTML"));
		err.println(String.format("Text(old API): \"%s\"", element.getText()));
		err.println(
				"Text(new API): " + shadowDriver.getAttribute(element, "value"));

		List<WebElement> elements = shadowDriver.getAllShadowElement(parent, "h3");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		err.println(
				String.format("Located %d shadow document elements:", elements.size()));
		elements.stream().map(e -> String.format("outerHTML (new API): %s",
				shadowDriver.getAttribute(e, "outerHTML"))).forEach(err::println);
		elements.stream().map(e -> String.format("outerHTML (old API): %s",
				e.getAttribute("outerHTML"))).forEach(err::println);
		elements.stream()
				.map(e -> String.format("Text (old API): \"%s\"", e.getText()))
				.forEach(err::println);

	}
}
