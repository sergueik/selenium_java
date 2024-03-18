package com.mycompany.app;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

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
		List<WebElement> inputs = (List<WebElement>) (js
				.executeScript("var labels = arguments[0],\n" + "inputs = [];\n"
						+ "		for (var i = 0; i < labels.length; i++) {\n"
						+ "				if (labels[i].getAttribute('for') != null) {\n"
						+ "						inputs.push(document.getElementById(labels[i].getAttribute('for')));\n"
						+ "				}\n" + "		}\n" + "return inputs;", labels));
		inputs.stream().forEach(o -> {
			System.err.println(o.getAttribute("outerHTML"));
			scrolltoElement(o);
			highlight(o);
			flash(o);
		});
	}

	@Test(priority = 2, enabled = false)
	public void testInputByLabelTextOrdered() {
		List<WebElement> labelElements = driver.findElements(By.tagName("label"));
		// more info:
		// https://www.mkyong.com/java/java-how-to-convert-a-primitive-array-to-list/
		List<Integer> elementOrder = new ArrayList<Integer>();
		for (int cnt = 0; cnt < labelElements.size(); cnt++) {
			elementOrder.add(cnt);
		}
		Map<String, WebElement> elementMap = labelElements.stream()
				// java.lang.IllegalArgumentException: Cannot find elements with a null
				// id attribute.
				.filter(o -> o.getAttribute("for") != null)
				// java.lang.IllegalStateException: Duplicate key
				.collect(Collectors.toMap(o -> {
					// local variables referenced from a lambda expression must be final
					// or effectively final
					// return String.format("%d_%s_%d", ++pos, o.getText());
					return String.format("%02d_%s", elementOrder.remove(0), o.getText());
				}, o -> driver.findElement(By.id(o.getAttribute("for")))));

		elementMap.keySet().stream().sorted().forEach(o -> {
			WebElement input = elementMap.get(o);
			String text = o.replaceAll("^(?:\\d+_)", "");
			System.err.println(String.format("Input for %s is\n%s\n", text,
					input.getAttribute("outerHTML")));
			scrolltoElement(input);
			highlight(input);
			flash(input);
		});
	}

	@Test(priority = 3, enabled = false)
	public void testInputByLabelTextRandom() {
		Random suffix = new Random();
		List<WebElement> labelElements = driver.findElements(By.tagName("label"));
		Map<String, WebElement> elementMap = labelElements.stream()
				// java.lang.IllegalArgumentException: Cannot find elements with a null
				// id attribute.
				.filter(o -> o.getAttribute("for") != null)
				// java.lang.IllegalStateException: Duplicate key
				.collect(Collectors.toMap(o -> {
					return String.format("%s_%d", o.getText(), suffix.nextInt());
				}, o -> driver.findElement(By.id(o.getAttribute("for")))));

		elementMap.entrySet().stream().forEach(o -> {
			String text = o.getKey().replaceAll("(?:_[-\\d]+)$", "");
			WebElement input = o.getValue();
			System.err.println(String.format("Input for %s is\n%s\n", text,
					input.getAttribute("outerHTML")));
			scrolltoElement(input);
			highlight(input);
			flash(input);
		});
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 3; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgcolor, element);
		}
	}

	public void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'",
				element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	// Scroll
	public void scroll(final int x, final int y) {
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i <= x; i = i + 50) {
			js.executeScript("scroll(" + i + ",0)");
		}
		for (int j = 0; j <= y; j = j + 50) {
			js.executeScript("scroll(0," + j + ")");
		}
	}

	// origin:
	// https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection/blob/master/src/utils/UtilsQAAutoman.java
	public void scrolltoElement(WebElement element) {
		Coordinates coordinate = ((Locatable) element).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

}
