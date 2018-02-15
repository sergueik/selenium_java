package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class LabelInputFinderTest extends BaseTest {

	public String baseURL = "https://v4-alpha.getbootstrap.com/components/forms/";

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.get(baseURL);
	}

	// origin:
	// https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection/blob/master/src/seleniumOrgJQuery/JQUeryElementsLocators.java
	@Test(priority = 1, enabled = true)
	public void testInputByLabel() {
		List<WebElement> labels = driver.findElements(By.tagName("label"));
		@SuppressWarnings("unchecked")
		List<WebElement> inputs = (List<WebElement>) (js.executeScript(
				// https://stackoverflow.com/questions/1820908/how-to-turn-off-the-eclipse-code-formatter-for-certain-sections-of-java-code
				// @formatter:off
							"var labels = arguments[0],\n" 
						+ "inputs = [];\n"
						+ "		for (var i = 0; i < labels.length; i++) {\n"
						+ "				if (labels[i].getAttribute('for') != null) {\n"
						+ "						inputs.push(document.getElementById(labels[i].getAttribute('for')));\n"
						+ "				}\n" 
						+ "		}\n" 
						+ "return inputs;",
				// @formatter:on

				labels));

		Function<WebElement, String> showWebElement = element -> {
			scrolltoElement(element);
			highlight(element);
			flash(element);
			return element.getAttribute("outerHTML");
		};

		inputs.stream().map(showWebElement).forEach(System.err::println);
	}

	@Test(priority = 2, enabled = false)
	public void testInputByLabelTextOrdered() {
		List<WebElement> labelElements = driver.findElements(By.tagName("label"));
		// http://www.deadcoderising.com/2015-05-19-java-8-replace-traditional-for-loops-with-intstreams/
		// https://stackoverflow.com/questions/17640754/zipping-streams-using-jdk8-with-lambda-java-util-stream-streams-zip
		// see also https://github.com/poetix/protonpack for zip
		List<Integer> elementOrder = IntStream.range(0, labelElements.size())
				.boxed().collect(Collectors.toList());

		Map<String, WebElement> elementMap = labelElements.stream()
				// NOTE: java.lang.IllegalArgumentException:
				// Cannot find elements with a null id attribute.
				.filter(o -> o.getAttribute("for") != null)
				// NOTE: java.lang.IllegalStateException:
				// Duplicate key
				.collect(Collectors.toMap(o -> {
					// NOTE: local variables referenced from a lambda expression
					// must be final or effectively final
					return String.format("%02d_%s", elementOrder.remove(0), o.getText());
				}, o -> driver.findElement(By.id(o.getAttribute("for")))));

		Consumer<WebElement> showElement = element -> {
			scrolltoElement(element);
			highlight(element);
			flash(element);
		};

		Function<String, WebElement> getWebElement = label -> {
			WebElement input = elementMap.get(label);
			System.err.println(String.format("Input for %s is\n%s\n",
					label.replaceAll("^(?:\\d+_)", ""), input.getAttribute("outerHTML")));
			return input;
		};

		elementMap.keySet().stream().sorted().map(getWebElement)
				.forEach(showElement);
	}

	@Test(priority = 3, enabled = false)
	public void testInputByLabelTextRandom() {
		Random suffix = new Random();
		List<WebElement> labelElements = driver.findElements(By.tagName("label"));
		Map<String, WebElement> elementMap = labelElements.stream()
				// NOTE: java.lang.IllegalArgumentException:
				// Cannot find elements with a null id attribute.
				.filter(o -> o.getAttribute("for") != null)
				// NOTE: java.lang.IllegalStateException:
				// Duplicate key
				.collect(Collectors.toMap(o -> {
					return String.format("%s_%d", o.getText(), suffix.nextInt());
				}, o -> driver.findElement(By.id(o.getAttribute("for")))));

		Consumer<Entry<String, WebElement>> showElement = o -> {
			String text = o.getKey().replaceAll("(?:_[-\\d]+)$", "");
			WebElement input = o.getValue();
			System.err.println(String.format("Input for %s is\n%s\n", text,
					input.getAttribute("outerHTML")));
			scrolltoElement(input);
			highlight(input);
			flash(input);
		};
		elementMap.entrySet().stream().forEach(showElement);
	}
}
