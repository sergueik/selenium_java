package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.IOException;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import static org.openqa.selenium.By.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class AppTest {

	private FirefoxDriver driver;
	private WebDriverWait wait;
	static Actions actions;
	private Predicate<WebElement> hasClass;
	private Predicate<WebElement> hasAttr;
	private Predicate<WebElement> hasText;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseUrl = "http://suvian.in/selenium";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get(baseUrl);
	}

	@AfterMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	// the Test0 contains extra debugging in the ExpectedConditions anonimous
	// class and filter
	// this test is somewat redundant, used for debugging
	// validate in Firebug
	// $x("xpath to validate")
	// $$("cssSelector to validate")
	@Test(enabled = true)
	public void test0() {

		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		String cssSelector = ".container .row .intro-message h3 a";
		String xpath = "//div[1]/div/div/div/div/h3[2]/a";
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector(cssSelector))));
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		assertTrue(elements.size() > 0);

		// Act
		String linkText = "Click Here";
		WebElement element = elements.get(0);
		highlight(element);
		assertTrue(element.getText().equalsIgnoreCase(linkText), element.getText());
		element.click();

		linkText = "Link Successfully clicked";
		cssSelector = ".container .row .intro-message h3";
		String className = "intro-message";

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
		}

		// Assert
		// 1. Expected Condition uses enclosing element
		try {
			(new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					WebElement e = d.findElement(By.className("intro-message"));
					String t = e.getText();
					Boolean result = t.contains("Link Successfully clicked");
					System.err.println(
							"in apply: Text = " + t + "\n result = " + result.toString());
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		// 2. Expected condition with Iterator, uses String methods
		try {
			(new WebDriverWait(driver, 5)).until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					Iterator<WebElement> elementsIterator = d
							.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
							.iterator();
					WebElement result = null;
					while (elementsIterator.hasNext()) {
						WebElement e = (WebElement) elementsIterator.next();
						System.err.println("in apply iterator (1): Text = " + e.getText());
						if (e.getText().contains("Navigate Back")) {
							result = e;
							break;
						}
					}
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// 2. Alternative Iterator, uses Regex methods
		try {
			WebElement checkElement = (new WebDriverWait(driver, 5))
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver d) {
							Iterator<WebElement> i = d.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
									.iterator();
							WebElement result = null;
							Pattern pattern = Pattern.compile("(?:" + "Navigate Back" + ")",
									java.util.regex.Pattern.CASE_INSENSITIVE);
							while (i.hasNext()) {
								WebElement e = (WebElement) i.next();
								System.err
										.println("in apply iterator (2): Text = " + e.getText());
								Matcher matcher = pattern.matcher(e.getText());
								if (matcher.find()) {
									result = e;
									break;
								}
							}
							return result;
						}
					});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// 3. Alternative wait, functional style, with Optional <WebElement>
		// http://www.nurkiewicz.com/2013/08/optional-in-java-8-cheat-sheet.html
		try {
			WebElement checkElement = (new WebDriverWait(driver, 5))
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver d) {
							Optional<WebElement> e = d
									.findElements(By.cssSelector(
											"div.container div.row div.intro-message h3"))
									.stream().filter(o -> {
										System.err
												.println("in stream filter (3): Text = " + o.getText());
										return (Boolean) (o.getText().contains("Navigate Back"));
									}).findFirst();

							return (e.isPresent()) ? e.get() : (WebElement) null;
						}
					});
			System.err
					.println("element check: " + checkElement.getAttribute("innerHTML"));
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// http://stackoverflow.com/questions/12858972/how-can-i-ask-the-selenium-webdriver-to-wait-for-few-seconds-in-java
		// http://stackoverflow.com/questions/31102351/selenium-java-lambda-implementation-for-explicit-waits
		elements = driver.findElements(By.cssSelector(cssSelector));
		Stream<WebElement> elementsStream = elements.stream(); // convert list to
																														// stream
		elements = elementsStream.filter(o -> {
			System.err.println("in filter: Text = " + o.getText());
			return (Boolean) (o.getText()
					.equalsIgnoreCase("Link Successfully clicked"));
		}).collect(Collectors.toList());

		assertThat(elements.size(), equalTo(1));

		element = elements.get(0);
		highlight(element);
		assertTrue(element.getText().equalsIgnoreCase(linkText), element.getText());
	}

	@Test(enabled = true)
	public void test1() {

		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		List<WebElement> elements = driver
				.findElements(By.xpath("//div[1]/div/div/div/div/h3[2]/a"));
		assertTrue(elements.size() > 0);

		// Act
		WebElement element = elements.get(0);
		highlight(element);
		assertTrue(element.getText().equalsIgnoreCase("Click Here"),
				element.getText());
		element.click();

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Inspect enclosing element to confirm the page loaded
		try {
			(new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					return (Boolean) d.findElement(By.className("intro-message"))
							.getText().contains("Link Successfully clicked");
				}
			});
		} catch (Exception e) {
		}

		// Assert
		elements = driver
				.findElements(By.cssSelector(".container .row .intro-message h3"))
				.stream()
				.filter(o -> o.getText().equalsIgnoreCase("Link Successfully clicked"))
				.collect(Collectors.toList());
		assertThat(elements.size(), equalTo(1));
		element = elements.get(0);
		highlight(element);
		assertTrue(element.getText().equalsIgnoreCase("Link Successfully clicked"),
				element.getText());
		elements.forEach(System.out::println); // output : ??
	}

	@Test(enabled = false)
	public void test2() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.2text_field.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("1.2text_field_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test3() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.3age_plceholder.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("1.3age_plceholder_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = true)
	public void test4() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.4gender_dropdown.html");
		// Wait page to load
		wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".intro-header .container .row .col-lg-12 .intro-message select"))));
		// Act
		WebElement element = driver.findElement(By.cssSelector(
				".intro-header .container .row .col-lg-12 .intro-message select"));
		Select select = new Select(element);
		String optionString = "Male";
		// create predicate driven case-insensitive option search
		hasText = o -> o.getText()
				.matches("(?i:" + optionString.toLowerCase() + ")");
		List<WebElement> options = select.getOptions().stream().filter(hasText)
				.collect(Collectors.toList());
		// Assert
		assertThat(options.size(), equalTo(1));
		element = options.get(0);
		assertTrue(element.getText().equals(optionString), element.getText());
	}

	@Test(enabled = true)
	public void test5_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.5married_radio.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		String label = "yes";
		String elementContents = driver
				.findElement(By.xpath(
						"//div[@class='intro-header']/div[@class='container']/div[@class='row']/div[@class='col-lg-12']/div[@class='intro-message']/form"))
				.getAttribute("outerHTML");

		String line = new ArrayList<String>(
				Arrays.asList(elementContents.split("<br/?>"))).stream()
						.filter(o -> o.toLowerCase().indexOf(label) > -1).findFirst().get();
		Matcher matcher = Pattern.compile("value=\\\"([^\"]*)\\\"").matcher(line);
		String checkboxValue = null;
		if (matcher.find()) {
			checkboxValue = matcher.group(1);
			System.err.println("checkox value = " + checkboxValue);
		} else {
			System.err.println("checkox value not found");
		}
		WebElement checkBoxElement = null;
		if (checkboxValue != null) {
			checkBoxElement = driver.findElement(By.xpath(String.format(
					"//div[@class='intro-header']/div[@class='container']/div[@class='row']/div[@class='col-lg-12']/div[@class='intro-message']/form/input[@name='married'][@value='%s']",
					checkboxValue)));
		}
		// Act
		assertThat(checkBoxElement, notNullValue());
		highlight(checkBoxElement);
		checkBoxElement.sendKeys(Keys.SPACE);
		// Assert
		// NOTE: behaves differently in C#
		assertTrue(checkBoxElement.isSelected());
	}

	@Test(enabled = true)
	public void test5_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.5married_radio.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		String label = "no";
		WebElement formElement = driver.findElement(By.cssSelector(
				".intro-header .container .row .col-lg-12 .intro-message form"));
		String elementContents = formElement.getAttribute("outerHTML");

		String line = new ArrayList<String>(
				Arrays.asList(elementContents.split("<br/?>"))).stream()
						.filter(o -> o.toLowerCase().indexOf(label) > -1).findFirst().get();
		Matcher matcher = Pattern.compile("value=\\\"([^\"]*)\\\"").matcher(line);
		String checkboxValue = null;
		if (matcher.find()) {
			checkboxValue = matcher.group(1);
			System.err.println("checkox value = " + checkboxValue);
		} else {
			System.err.println("checkox value not found");
		}
		WebElement checkBoxElement = null;
		if (checkboxValue != null) {
			checkBoxElement = formElement.findElement(By.cssSelector(
					String.format("input[name='married'][value='%s']", checkboxValue)));
		}
		// Assert
		assertThat(checkBoxElement, notNullValue());
		highlight(checkBoxElement);
		// Act
		checkBoxElement.sendKeys(Keys.SPACE);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// ignore
		}
		// Assert
		// NOTE: behaves differently in C#
		assertTrue(checkBoxElement.isSelected());
		// Act
		checkBoxElement.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// ignore
		}
		// Assert
		assertTrue(checkBoxElement.isSelected());

	}

	@Test(enabled = false)
	public void test6() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.6checkbox.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("1.6checkbox_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test7() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.7button.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("1.7button_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test8() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.8copyAndPasteText.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("1.8copyAndPasteText_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test9() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.9copyAndPasteTextAdvanced.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions
					.urlContains("1.9copyAndPasteTextAdvanced_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test10() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.10selectElementFromDD.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions
					.urlContains("1.10selectElementFromDD_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test11() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.1alert.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("2.1alert_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test12() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.2browserPopUp.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("2.2browserPopUp_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test13() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.3frame.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("2.3frame_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test14() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.4mouseHover.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("2.4mouseHover_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test15() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.5resize.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("2.5resize_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test16() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.6liCount.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("2.6liCount_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test17() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.7waitUntil.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("2.7waitUntil_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test18() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.8progressBar.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("2.8progressBar_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test19() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.9greenColorBlock.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("2.9greenColorBlock_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test20() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("2.10dragAndDrop_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test21() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.1fileupload.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("3.1fileupload_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test22() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.2dragAndDrop.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("3.2dragAndDrop_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test23() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.3javaemail.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("3.3javaemail_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test24() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.4readWriteExcel.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("3.4readWriteExcel_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test25() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.5cricketScorecard.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("3.5cricketScorecard_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test26() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.6copyTextFromTextField.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions
					.urlContains("3.6copyTextFromTextField_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test27() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.7correspondingRadio.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(ExpectedConditions
					.urlContains("3.7correspondingRadio_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test28() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.8screeshotToEmail.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("3.8screeshotToEmail_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test29() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.9FacebookTest.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("3.9FacebookTest_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	@Test(enabled = false)
	public void test30() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.10select1stFriday.html");

		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));

		// Act

		// Wait page to load
		try {
			wait.until(
					ExpectedConditions.urlContains("3.10select1stFriday_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Assert
	}

	private void highlight(WebElement element) {
		highlight(element, 100);
	}

	private void highlight(WebElement element, long highlight_interval) {
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].style.border='3px solid yellow'", element);
			}
			Thread.sleep(highlight_interval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
						.executeScript("arguments[0].style.border=''", element);
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}
	}

}
