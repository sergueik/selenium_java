package com.github.sergueik.selenium;

import java.lang.reflect.Method;
import java.util.List;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertTrue;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CodeMirrorTest extends BaseTest {

	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;

	@BeforeMethod
	public void BeforeMethod(Method method) {
		super.beforeMethod(method);
		// ?
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error(s) in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	// only loads first 25 lines of code
	@Test(enabled = true)
	public void test1() {
		// Arrange
		driver.get("https://codemirror.net/demo/simplemode.html");
		WebElement codeElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.xpath("//div[@id = 'code']//div[@class='CodeMirror-code']"))));
		assertThat(codeElement, notNullValue());
		// Act

		List<WebElement> codeLines = codeElement
				.findElements(By.cssSelector("pre[role='presentation']"));

		// Assert
		assertTrue(codeLines.size() > 0);
		System.err.println(String.format("%d Lines of code:", codeLines.size()));
		UnaryOperator<WebElement> scrollInfoView = (e) -> {
			executeScript("arguments[0].scrollIntoView({ behavior: \"smooth\" });", e);
			highlight(e);
			return e;
		};
		List<String> code = codeLines.stream()
				.map(scrollInfoView.andThen(e -> e.getText()))
				.collect(Collectors.toList());

		code.stream().forEach(System.err::println);
	}

	// only loads first 35 lines of code
	@Test(enabled = true)
	public void test2() {
		// Arrange
		driver.get("https://codemirror.net/demo/simplemode.html");
		WebElement codeElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.xpath("//div[@id = 'code']//div[@class='CodeMirror-code']"))));
		assertThat(codeElement, notNullValue());
		// Act

		// brute force ?
		int cnt = 0;
		while (true) {
			cnt++;
			try {
				WebElement codeLine = codeElement.findElement(By.cssSelector(
						String.format("pre[role='presentation']:nth-of-type(%d)", cnt)));
				assertThat(codeLine, notNullValue());
				executeScript("arguments[0].scrollIntoView(true);", codeLine);
				highlight(codeLine);
				System.err.println(codeLine.getText());
			} catch (NoSuchElementException e) {
				// e.printStackTrace();
				break;
			}
		}
		// Assert
	}

	// loads all code
	@Test(enabled = true)
	public void test3() {
		// Arrange
		driver.get("https://codemirror.net/demo/simplemode.html");
		WebElement codeElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.xpath("//div[@id = 'code']//div[@class='CodeMirror-code']"))));
		assertThat(codeElement, notNullValue());
		// Act

		WebElement codeLine = codeElement
				.findElement(By.cssSelector("pre[role='presentation']:nth-of-type(1)"));

		// Assert
		assertThat(codeLine, notNullValue());
		while (true) {
			try {
				List<WebElement> codeLinesFollowing = codeLine
						.findElements(By.xpath("following-sibling::pre"));
				if (codeLinesFollowing.size() == 0) {
					System.err
							.println(String.format("Last line: '%s'", codeLine.getText()));
					break;
				}
				WebElement nextCodeLine = codeLinesFollowing.get(0);
				executeScript("arguments[0].scrollIntoView(true);", nextCodeLine);
				highlight(codeLine);
				System.err.println(codeLine.getText());
				codeLine = nextCodeLine;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
