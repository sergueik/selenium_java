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
						// temporarily catch all exceptions.
						// System.err.println(buttonElement.getAttribute("outerHTML"));
						// System.err.println(buttonElement.findElement(By.xpath("..")).getAttribute("outerHTML"));
						System.err.println(String.format("Connecrtion fee: %s", fee));
						// NOTE: funny console output of cyrillic word:
						// Подключить
						// чить
						// ь
						System.err.println(
								String.format("Button Text: |%s|", buttonElement.getText()));
					} catch (Exception e) {
						System.err.println("Exception: " + e.toString());
					}
					return buttonElement;
				}).collect(Collectors.toList());
		// List<WebElement> checkboxes = new ArrayList<>();
	}
}
