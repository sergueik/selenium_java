package com.github.sergueik.selenium;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.Nullable;

/**
 * Selected test scenarions for Selenium WebDriver
 * based on:  https://testerslittlehelper.wordpress.com/
 * use XPath ancestor  /  clile navigation to manipulate heavily styled page.
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class XPathNavigationTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final Logger log = LogManager
			.getLogger(XPathNavigationTest.class);

	private static String baseURL = "https://spb.rt.ru/packages/tariffs";

	@SuppressWarnings("unused")
	private static Pattern pattern;
	private static Matcher matcher;

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(driver, notNullValue());
	}

	@BeforeMethod
	public void BeforeMethod() {
		driver.get(baseURL);
	}

	@Test(enabled = true)
	public void test1() {
		List<WebElement> elements = new ArrayList<>();
		elements = driver.findElements(By.cssSelector("*[data-fee]"));

		List<String> fees = elements.stream().map(_e -> _e.getAttribute("data-fee"))
				.collect(Collectors.toList());
		fees.stream().forEach(System.err::println);
		List<WebElement> buttons = fees.stream()
				.filter(fee -> Integer.parseInt(fee) > 0).map(fee -> {
					String xpath = String.format(
							"//*[@data-fee='%s']/ancestor::div[contains(@class,'js-price')]//a[contains(@class, 'button')]",
							fee);
					WebElement buttonElement = null;
					try {
						buttonElement = driver.findElement(By.xpath(xpath));
						executeScript(
								"arguments[0].scrollIntoView({ behavior: \"smooth\" });",
								buttonElement);
						highlight(buttonElement.findElement(By.xpath("..")));
						// System.err.println(buttonElement.getAttribute("outerHTML"));
						// System.err.println(buttonElement.findElement(By.xpath("..")).getAttribute("outerHTML"));
						System.err.println(String.format("Connection fee: %s", fee));
						// NOTE: funny console output of cyrillic word:
						// Подключить
						// чить
						// ь
						System.err.println(
								String.format("Button Text: |%s|", buttonElement.getText()));
						System.err.println(xpathOfElement(buttonElement));
					} catch (Exception e) {
						// temporarily catch all exceptions.
						System.err.println("Exception: " + e.toString());
					}
					return buttonElement;
				}).collect(Collectors.toList());
	}

	// a debug version of test1.
	// NOTE: slower
	@Test(enabled = true)
	public void test2() {
		List<WebElement> elements = new ArrayList<>();
		elements = driver.findElements(By.cssSelector("*[data-fee]"));

		List<WebElement> buttons = elements.stream().map(_element -> {
			String fee = _element.getAttribute("data-fee");
			WebElement containerElement = null;
			WebElement buttonElement = null;
			if (Integer.parseInt(fee) > 0) {
				String xpath = String
						.format("ancestor::div[contains(@class,'js-price')]", fee);
				try {
					containerElement = _element.findElement(By.xpath(xpath));
					if (containerElement != null) {

						// System.err.println("Container element: "
						// + containerElement.getAttribute("innerHTML"));
						try {
							buttonElement = containerElement
									.findElement(By.cssSelector("a[class *= 'button']"));
							if (buttonElement != null) {
								executeScript(
										"arguments[0].scrollIntoView({ behavior: \"smooth\" });",
										buttonElement);
								highlight(buttonElement.findElement(By.xpath("..")));
								System.err.println(String.format("Connection fee: %s", fee));
								System.err.println(String.format("Button Text: |%s|",
										buttonElement.getText()));
								System.err.println(cssSelectorOfElement(buttonElement));
								
							}
						} catch (TimeoutException e2) {
							System.err.println(
									"Exception finding the button element: " + e2.toString());
						}
					}
				} catch (TimeoutException e1) {
					System.err.println(
							"Exception finding the container element: " + e1.toString());
				}
			}
			return buttonElement;
		}).collect(Collectors.toList());
	}
}
