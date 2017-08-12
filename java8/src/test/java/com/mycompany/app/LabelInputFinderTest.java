package com.mycompany.app;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		List<WebElement> inputs = (List<WebElement>) (js.executeScript(
				"var labels = arguments[0], inputs = []; for (var i=0; i < labels.length; i++){"
						+ "inputs.push(document.getElementById(labels[i].getAttribute('for'))); } return inputs;",
				labels));
		inputs.stream().forEach(o -> {
			try {
				System.err.println(o.getAttribute("outerHTML"));
				scrolltoElement(o);
				highlight(o);				
				flash(o);
			} catch(NullPointerException e) {
				// why ?
			}
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

	// origin
	// https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection/blob/master/src/utils/UtilsQAAutoman.java
	public void scrolltoElement(WebElement ScrolltoThisElement) {
		Coordinates coordinate = ((Locatable) ScrolltoThisElement).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

}
