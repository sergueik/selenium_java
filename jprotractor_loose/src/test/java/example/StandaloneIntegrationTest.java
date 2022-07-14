package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Integration tests of Calculator http://juliemr.github.io/protractor-demo/
 * without actually using NgWebDriver class at Java level
 * but not using
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 * 
 */

public class StandaloneIntegrationTest extends CommonTest {

	private static List<WebElement> elements = new ArrayList<>();
	private static WebElement element = null;
	private static String script = null;
	private static int cnt;

	// @Ignore
	@SuppressWarnings("unchecked")
	@Test
	public void test1() {
		driver.navigate().to("http://juliemr.github.io/protractor-demo/");
		for (cnt = 0; cnt != 3; cnt++) {
			script = getScriptContent("model.js");
			elements = (List<WebElement>) executeScript(script, null, "first", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			assertThat(element, notNullValue());
			highlight(element);
			element.clear();
			element.sendKeys("40");
			elements = (List<WebElement>) executeScript(script, null, "second", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			assertThat(element, notNullValue());
			element.clear();
			element.sendKeys("2");

			elements = (List<WebElement>) executeScript(
					getScriptContent("options.js"), null,
					"value for (key, value) in operators", null);
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			element.click();
			elements = (List<WebElement>) executeScript(
					getScriptContent("buttonText.js"), null, "Go!");
			assertThat(elements, notNullValue());
			assertThat(elements.size(), greaterThan(0));

			element = elements.get(0);
			assertThat(element.getText(), containsString("Go"));
			element.click();
			// sleep(1000);
			// NOTE: waitForAngular is timing out possibly due to incrorrect arguments
			/*
						try {
							executeAsyncScript(getScriptContent("waitForAngular.js"), null, null); // does not work
						} catch (ScriptTimeoutException e) {
							// ignore
						}
						*/
			try {
				wait.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver d) {
						elements = (List<WebElement>) executeScript(
								getScriptContent("binding.js"), null, "latest", null);
						Boolean result = false;
						if (elements != null && elements.size() > 0) {
							element = elements.get(0);
							highlight(element);
							String text = element.getText();
							result = !text.contains(".");
							System.err.println("in apply " + cnt + ": Text = " + text
									+ "\nresult = " + result.toString());
						} else {
							System.err.println("in apply " + cnt + ": element not yet found");
							result = false;
						}
						return result;
					}
				});
			} catch (Exception e) {
				System.err.println("Exception in custom wait: " + e.toString());
			}

		}

		elements = (List<WebElement>) executeScript(getScriptContent("repeater.js"),
				null, "result in memory", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(2));
		element = elements.get(0);
		System.err.println(element.getAttribute("outerHTML"));

		elements = (List<WebElement>) executeScript(getScriptContent("binding.js"),
				null, "result.timestamp", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(2));
		element = elements.get(0);
		elements.stream().map(element -> element.getText())
				.map(o -> String.format("item: %s", o)).forEach(System.err::println);

	}

	// @Ignore
	@SuppressWarnings("unchecked")
	@Test
	// entirely unmodified "clientscripts.js" - no longer runs due to typescript
	// and 'require' dependencies within, but these parts can be removed with no
	// damage to main functionality
	public void test2() {
		driver.navigate().to("http://juliemr.github.io/protractor-demo/");
		elements = (List<WebElement>) executeScript(String.format(
				"%s\nvar using = arguments[0] || document;\n"
						+ "var model = arguments[1];\n"
						+ "var rootSelector = arguments[2];\n"
						+ "window.findByModel = functions.findByModel;\n"
						+ "window.findByOptions = functions.findByOptions;\n"
						+ "window.findRepeaterColumn = functions.findRepeaterColumn;\n"
						+ "return functions.findByModel(model, using, rootSelector);",
				getScriptContent("clientsidescripts.js")), null, "first", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));

		element = elements.get(0);
		assertThat(element, notNullValue());
		element.clear();
		element.sendKeys("19");
		System.err.println("Found element: " + element.getAttribute("outerHTML"));
		elements = (List<WebElement>) executeScript(
				"var using = arguments[0] || document;\n"
						+ "var model = arguments[1];\n"
						+ "var rootSelector = arguments[2];\n"
						+ "return window.findByModel(model, using, rootSelector);",
				null, "second", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		assertThat(element, notNullValue());
		element.clear();
		element.sendKeys("23");
		elements = (List<WebElement>) executeScript(
				"var using = arguments[0] || document;\n"
						+ "var options = arguments[1];\n"
						+ "return window.findByOptions(options, using);",
				null, "value for (key, value) in operators", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));

		element = elements.get(0);
		System.err.println("Found element: " + element.getAttribute("outerHTML"));
		element.click();

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() {

		getLocalPage("ng_multi_select2.htm");
		element = driver.findElement(By.cssSelector(
				"multiselect-dropdown button[data-ng-click='openDropdown()']"));
		assertThat(element, notNullValue());
		element.click();
		// NOTE: selectedRepeaterOption("option in options")
		// was not working with this directive

		elements = (List<WebElement>) executeScript(getScriptContent("binding.js"),
				null, "option.name", null);
		assertTrue(elements.size() > 0);

		if (elements != null && elements.size() > 0) {
			element = elements.get(0);

			// find what is selected based on the bootstrap class attribute
			String expression = "\\-remove\\-";
			Pattern pattern = Pattern.compile(expression);
			int count = 0;
			for (WebElement element : elements) {
				String optionName = (String) executeScript(
						getScriptContent("evaluate.js"), element, "option.name", null);

				WebElement element2 = element.findElement(By.tagName("span"));
				assertThat(element2, notNullValue());
				String classAttribute = element2.getAttribute("class");
				System.err
						.println("option.name = " + optionName + " " + classAttribute);
				Matcher matcher = pattern.matcher(classAttribute);
				if (!matcher.find()) {
					System.err.println("selected: " + optionName);
					highlight(element);
					count++;
				}
			}
			elements = (List<WebElement>) executeScript(
					getScriptContent("repeaterColumn.js"), null,
					"country in SelectedCountries", "country.name", null);

			assertTrue(elements.size() == count);
			elements.stream().map(o -> String.format("%s\n", o.getText()))
					.forEach(System.err::format);
		}
	}

}