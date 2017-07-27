package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import org.hamcrest.CoreMatchers;

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

public class SuvianTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private static Actions actions;
	private static Alert alert;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private static String baseURL = "about:blank";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static String defaultScript = null;
	private static final String browser = "chrome";
	private static String osName;

	@BeforeSuite
	@SuppressWarnings("deprecation")
	public void beforeSuite() throws Exception {
		getOsName();
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();

			HashMap<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String downloadFilepath = System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "target"
					+ System.getProperty("file.separator");
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("enableNetwork", "true");
			options.setExperimentalOption("prefs", chromePrefs);

			for (String optionAgrument : (new String[] {
					"allow-running-insecure-content", "allow-insecure-localhost",
					"enable-local-file-accesses", "disable-notifications",
					/* "start-maximized" , */
					"browser.download.folderList=2",
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
					String.format("browser.download.dir=%s", downloadFilepath)
					/* "user-data-dir=/path/to/your/custom/profile"  , */
			})) {
				options.addArguments(optionAgrument);
			}

			// options for headless
			/*
			for (String optionAgrument : (new String[] { "headless",
					"window-size=1200x600", })) {
				options.addArguments(optionAgrument);
			}
			*/
			capabilities
					.setBrowserName(DesiredCapabilities.chrome().getBrowserName());

			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					osName.toLowerCase().startsWith("windows")
							? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
							: "/tmp/geckodriver");
			System
					.setProperty("webdriver.firefox.bin",
							osName.toLowerCase().startsWith("windows") ? new File(
									"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
											.getAbsolutePath()
									: "/usr/bin/firefox");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			// use legacy FirefoxDriver
			capabilities.setCapability("marionette", false);
			// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
			capabilities.setCapability("locationContextEnabled", false);
			capabilities.setCapability("acceptSslCerts", true);
			capabilities.setCapability("elementScrollBehavior", 1);
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			profile.setEnableNativeEvents(false);

			System.out.println(System.getProperty("user.dir"));
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			try {
				driver = new FirefoxDriver(capabilities);
			} catch (WebDriverException e) {
				e.printStackTrace();
				throw new RuntimeException("Cannot initialize Firefox driver");
			}
		}
		actions = new Actions(driver);
		/*
		System.setProperty("webdriver.chrome.driver",
				"c:/java/selenium/chromedriver.exe");
		driver = new ChromeDriver();
		*/
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@AfterSuite
	public void afterSuite() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void BeforeMethod() {
		driver.get(baseURL);
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			throw new RuntimeException(String.format("Error in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		driver.get("about:blank");
	}

	// Firebug console validation:
	// $x("<xpath>")
	// $$("<cssSelector>")
	@Test(enabled = false)
	public void test0_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");

		// Wait page to load
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

		// Assert
		// 1. Expected Condition uses enclosing element
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					String t = d.findElement(By.className("intro-message")).getText();
					Boolean result = t.contains("Link Successfully clicked");
					System.err.println(
							"in apply: Text = " + t + "\nresult = " + result.toString());
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// 3. Alternative wait, functional style, with Optional <WebElement>
		// http://www.nurkiewicz.com/2013/08/optional-in-java-8-cheat-sheet.html
		try {
			WebElement checkElement = wait.until(new ExpectedCondition<WebElement>() {

				@Override
				public WebElement apply(WebDriver d) {
					Optional<WebElement> e = d
							.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
							.stream().filter(o -> {
								String t = o.getText();
								System.err.println("in stream filter (3): Text = " + t);
								return (Boolean) (t.contains("Navigate Back"));
							}).findFirst();
					return (e.isPresent()) ? e.get() : (WebElement) null;
				}
			});

			System.err
					.println("element check: " + checkElement.getAttribute("innerHTML"));
		}

		catch (

		Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// http://stackoverflow.com/questions/12858972/how-can-i-ask-the-selenium-webdriver-to-wait-for-few-seconds-in-java
		// http://stackoverflow.com/questions/31102351/selenium-java-lambda-implementation-for-explicit-waits
		elements = driver
				.findElements(By.cssSelector(".container .row .intro-message h3"));
		// longer version
		Stream<WebElement> elementsStream = elements.stream();
		elements = elementsStream.filter(o -> {
			String t = o.getText();
			System.err.println("(in filter) Text: " + t);
			return (Boolean) (t.equalsIgnoreCase("Link Successfully clicked"));
		}).collect(Collectors.toList());

		// shorter version
		elements = driver
				.findElements(By.cssSelector(".container .row .intro-message h3"))
				.stream()
				.filter(o -> "Link Successfully clicked".equalsIgnoreCase(o.getText()))
				.collect(Collectors.toList());

		assertThat(elements.size(), equalTo(1));

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
	}

	@Test(enabled = false)
	public void test0_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");
		// Wait page to load
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		// Act
		assertTrue(element.getText().equalsIgnoreCase("Click Here"),
				element.getText());
		highlight(element);
		element.click();
		try {
			wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// 2. Alternative Iterator, uses Regex methods
		try {
			WebElement checkElement = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					Iterator<WebElement> i = d
							.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
							.iterator();
					WebElement result = null;
					Pattern pattern = Pattern.compile(
							"(?:" + Pattern.quote("Navigate Back") + ")",
							Pattern.CASE_INSENSITIVE);
					while (i.hasNext()) {
						WebElement e = i.next();
						String t = e.getText();
						// System.err.println("in apply iterator (2): Text = " + t);
						Matcher matcher = pattern.matcher(t);
						if (matcher.find()) {
							result = e;
							break;
						}
					}
					return result;
				}
			});
			assertThat(checkElement, notNullValue());

		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
	}

	@Test(enabled = false)
	public void test0_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");
		// Wait page to load
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		// Act
		element.click();
		// 2. Expected condition with Iterator, uses String methods
		try {
			element = wait.until(new ExpectedCondition<WebElement>() {

				@Override
				public WebElement apply(WebDriver d) {
					Iterator<WebElement> elementsIterator = d
							.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
							.iterator();
					WebElement result = null;
					while (elementsIterator.hasNext()) {
						WebElement e = elementsIterator.next();
						String t = e.getText();
						System.err.println("in apply iterator (1): Text = " + t);
						if (t.contains("Navigate Back")) {
							result = e;
							break;
						}
					}
					return result;
				}
			});
		} catch (

		Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		// Act
		element.click();
	}

	@Test(enabled = false)
	public void test0_4() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");
		// Wait page to load
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		// Act
	}

	@Test(enabled = false)
	public void test0_5() {
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
		String simulateRightMouseButtonClick = getScriptContent(
				"simulateRightMouseButtonClick.js");
		System.err.println("Simulate (1) right mouse click on element");
		executeScript(simulateRightMouseButtonClick, element);
		sleep(3000);
		String simulateClick = getScriptContent("simulateClick.js");
		System.err.println("Simulate (2) right mouse click on element");
		executeScript(simulateClick, element, "contextmenu");
		sleep(3000);
		System.err.println("Simulate (3) right mouse click on element");
		actions.moveToElement(element).build().perform();
		actions.keyDown(Keys.CONTROL).contextClick().keyUp(Keys.CONTROL).build()
				.perform();
		sleep(3000);
	}

	// http://software-testing.ru/forum/index.php?/topic/17746-podskazhite-po-xpath/
	@Test(enabled = false)
	public void test0_8() {

		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");
		String elementText = "Click Here";
		WebElement element = null;
		try {
			element = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver _driver) {
					int cnt = 4;
					String[] xpaths = new String[] { "//a[text() = '%s']",
							"//a[normalize-space(.) = '%s']",
							"//a[normalize-space(text()) = '%s']",
							"//*[normalize-space(text()) = '%s']",
							"//a[contains(normalize-space(.), '%s')]",
							// NOTE: way too permissive for a selector
							"//*[contains(normalize-space(.), '%s')]" };
					String xpath = String.format(xpaths[cnt], elementText);
					System.err.println("Locator: xpath " + xpath);
					Optional<WebElement> _element = _driver.findElements(By.xpath(xpath))
							.stream().filter(o -> {
								String t = o.getText();
								System.err.println("In filter: " + o.getTagName() + ' '
										+ (t.length() > 20 ? t.substring(0, 20) : t));
								Pattern pattern = Pattern.compile(
										"^ *" + Pattern.quote(elementText),
										Pattern.CASE_INSENSITIVE);
								return pattern.matcher(t).find();
								// quicker, less precise
								// return (Boolean) (__element.getText().contains(elementText));
							}).findFirst();
					return (_element.isPresent()) ? _element.get() : (WebElement) null;
				}
			});
			System.err.println("Element: " + element.getAttribute("innerHTML"));
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// Act
		highlight(element);
		highlightNew(element, 1000);
		assertTrue(element.getText().equalsIgnoreCase(elementText),
				element.getText());
	}

	@Test(enabled = false)
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
		try { // Wait page to load
			wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
		}
		// Inspect enclosing element to confirm the page loaded
		try {
			wait.until(new ExpectedCondition<Boolean>() {
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
	public void test4() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.4gender_dropdown.html");
		wait.until( // Wait page to load
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".intro-header .container .row .col-lg-12 .intro-message select"))));
		// Act
		WebElement element = driver.findElement(By.cssSelector(
				".intro-header .container .row .col-lg-12 .intro-message select"));
		Select select = new Select(element);
		String optionString = "Male";
		// create predicate driven case-insensitive option search
		Predicate<WebElement> hasText = o -> o.getText()
				.matches("(?i:" + optionString.toLowerCase() + ")");
		List<WebElement> options = select.getOptions().stream().filter(hasText)
				.collect(Collectors.toList());
		// Assert
		assertThat(options.size(), equalTo(1));
		element = options.get(0);
		assertTrue(element.getText().equals(optionString), element.getText());
	}

	@Test(enabled = false)
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
		Matcher matcher = Pattern.compile("value=\\\"([^\"]*)\\\"").matcher(line); // Pattern.quote
																																								// ?
		String checkboxValue = null;
		if (matcher.find()) {
			checkboxValue = matcher.group(1);
			System.err.println("checkbox value = " + checkboxValue);
		} else {
			System.err.println("checkbox value not found");
		}
		WebElement checkBoxElement = null;
		if (checkboxValue != null) {
			checkBoxElement = driver.findElement(By.xpath(String.format(
					"//div[@class='intro-header']/div[@class='container']/div[@class='row']/div[@class='col-lg-12']/div[@class='intro-message']/form/input[@name='married' and @value='%s']",
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

	@Test(enabled = false)
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
			System.err.println("checkbox value = " + checkboxValue);
		} else {
			System.err.println("checkbox value not found");
		}
		WebElement checkBoxElement = null;
		if (checkboxValue != null) {
			checkBoxElement = formElement.findElement(By.cssSelector(
					String.format("input[name='married'][value='%s']", checkboxValue)));
		}
		// Act
		assertThat(checkBoxElement, notNullValue());
		highlight(checkBoxElement);
		checkBoxElement.click();
		sleep(500);
		// Assert
		assertTrue(checkBoxElement.isSelected());
	}

	@Test(enabled = false)
	public void test6_1() {
		// Arrange
		ArrayList<String> hobbies = new ArrayList<>(
				Arrays.asList("Singing", "Dancing"));
		driver.get("http://suvian.in/selenium/1.6checkbox.html");
		try {
			WebElement checkElement = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return d
							.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
							.stream().filter(o -> o.getText().toLowerCase()
									.indexOf("select your hobbies") > -1)
							.findFirst().get();
				}
			});
			System.err
					.println("element check: " + checkElement.getAttribute("innerHTML"));
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}

		// Act
		WebElement formElement = driver.findElement(By.cssSelector("input[id]"))
				.findElement(By.xpath(".."));
		assertThat(formElement, notNullValue());
		highlight(formElement);
		List<WebElement> inputElements = formElement
				.findElements(By.cssSelector("label[for]")).stream()
				.filter(o -> hobbies.contains(o.getText()))
				.collect(Collectors.toList());
		// C#: dataMap = elements.ToDictionary(x => x.GetAttribute("for"), x =>
		// x.Text);
		Map<String, String> dataMap = inputElements.stream().collect(
				Collectors.toMap(o -> o.getText(), o -> o.getAttribute("for")));
		ArrayList<WebElement> checkboxes = new ArrayList<>();
		for (String hobby : hobbies) {
			try {
				System.err.println("finding: " + dataMap.get(hobby));
				checkboxes.add(formElement.findElement(
						// will throw exception
						By.cssSelector(String.format("input#%s", dataMap.get(hobby)))));
			} catch (InvalidSelectorException e) {
				System.err.println("ignored: " + e.toString());
			}
			try {
				checkboxes.add(formElement.findElement(
						// will not throw exception
						By.xpath(String.format("input[@id='%s']", dataMap.get(hobby)))));
			} catch (Exception e) {
				System.err.println("ignored: " + e.toString());
			}

		}
		checkboxes.stream().forEach(o -> {
			highlight(o);
			o.click();
		});

		// Assert

		assertTrue(
				formElement.findElements(By.cssSelector("input[type='checkbox']"))
						.stream().filter(o -> o.isSelected()).count() == hobbies.size());
	}

	// NOTE: this test is broken
	// label follows the check box therefore
	// the following-sibling to find the check box by its label does not seem to
	// be appropriate
	// - see the test6_3 for the solution
	// however preceding-sibling always finds the check box #1
	@Test(enabled = false)
	public void test6_2() {
		// Arrange
		ArrayList<String> hobbies = new ArrayList<>(
				Arrays.asList("Singing", "Dancing", "Sports"));
		driver.get("http://suvian.in/selenium/1.6checkbox.html");
		WebElement checkElement = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d
						.findElements(
								By.cssSelector("div.container div.row div.intro-message h3"))
						.stream().filter(o -> o.getText().toLowerCase()
								.indexOf("select your hobbies") > -1)
						.findFirst().get();
			}
		});
		assertThat(checkElement, notNullValue());
		// Act
		List<WebElement> elements = checkElement
				.findElements(By.xpath("..//label[@for]")).stream()
				.filter(o -> hobbies.contains(o.getText()))
				.collect(Collectors.toList());
		assertTrue(elements.size() > 0);
		List<WebElement> checkBoxes = elements.stream()
				.map(o -> o.findElement(By.xpath("preceding-sibling::input")))
				.collect(Collectors.toList());
		assertTrue(checkBoxes.size() > 0);

		checkBoxes.stream().forEach(o -> {
			highlight(o);
			sleep(100);
			o.click();
		});
		// Assert
		assertTrue(
				driver
						.findElements(By.cssSelector(
								".container .intro-message input[type='checkbox']"))
						.stream().filter(o -> o.isSelected()).count() == hobbies.size());
	}

	// reverse usage of following-sibling to locate check box by its label
	@Test(enabled = false)
	public void test6_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.6checkbox.html");
		ArrayList<String> hobbies = new ArrayList<>(
				Arrays.asList("Singing", "Dancing", "Sports"));
		WebElement checkElement = null;
		try {
			checkElement = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return d
							.findElements(
									By.cssSelector("div.container div.row div.intro-message h3"))
							.stream().filter(o -> o.getText().toLowerCase()
									.indexOf("select your hobbies") > -1)
							.findFirst().get();
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		assertThat(checkElement, notNullValue());
		// Act
		List<WebElement> checkBoxes = checkElement
				.findElements(By.xpath("..//input[@type = 'checkbox']")).stream()
				.filter(o -> {
					WebElement label = o
							.findElement(By.xpath("following-sibling::label"));
					if (hobbies.contains(label.getText())) {
						System.err.println(String.format("checkbox element %s: '%s'",
								o.getAttribute("id"), label.getText()));
						return true;
					} else {
						return false;
					}
				}).collect(Collectors.toList());

		assertTrue(checkBoxes.size() > 0);
		checkBoxes.stream().forEach(o -> {
			highlight(o);
			sleep(100);
			o.click();
		});
		// Assert
		assertTrue(
				driver
						.findElements(By.cssSelector(
								".container .intro-message input[type='checkbox']"))
						.stream().filter(o -> o.isSelected()).count() == hobbies.size());
	}

	@Test(enabled = false)
	public void test10() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.10selectElementFromDD.html");
		WebElement buttonDropDown = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message div.dropdown button.dropbtn"))));
		assertThat(buttonDropDown, notNullValue());

		// Act
		buttonDropDown.click();
		wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message div.dropdown div#myDropdown"))));
		List<WebElement> optionElements = driver
				.findElements(By.cssSelector(
						".container .row .intro-message div.dropdown div#myDropdown"))
				.stream().filter(o -> o.getText().contains("Option 2"))
				.collect(Collectors.toList());
		assertTrue(optionElements.size() > 0);
		final String currentHandle = driver.getWindowHandle();
		final String text = "Congratulations.. You Selected option 2. Close this browser tab and proceed to end of Level 1.";
		optionElements.get(0).click();

		// Assert
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					Boolean result = false;
					System.err.println("Inspecting driver Window handles");
					Set<String> windowHandles = d.getWindowHandles();
					if (windowHandles.size() > 1) {
						System.err.println("Found " + (windowHandles.size() - 1)
								+ " additional tabs opened");
					} else {
						System.out.println("No other tabs found");
						return false;
					}
					Iterator<String> windowHandleIterator = windowHandles.iterator();
					while (windowHandleIterator.hasNext()) {
						String handle = windowHandleIterator.next();
						if (!handle.equals(currentHandle)) {
							System.err.println("Switch to: " + handle);
							driver.switchTo().window(handle);
							String t = d.getPageSource();
							System.err.println(String.format("Page source: %s",
									t.substring(
											org.apache.commons.lang3.StringUtils.indexOf(t, "<body>"),
											t.length() - 1)));
							if (t.contains(text)) {
								System.err.println("Found text: " + text);

								result = true;
							}
							if (result) {
								System.err.println("Close the browser tab: " + handle);
								d.close();
							}
							System.err.println("Switch to the main window.");
							driver.switchTo().window(currentHandle);
							driver.switchTo().defaultContent();
						}
					}
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append(e.toString());
			// throw new RuntimeException(e.toString());
		}
	}

	@Test(enabled = false)
	public void test12_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.2browserPopUp.html");
		WebElement button = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message button.btn-warning"))));
		assertThat(button, notNullValue());
		final String parentHandle = driver.getWindowHandle();

		// Act
		button.click();

		// Assert
		try { // Wait for the new browser widow to get shown
			String popupHandle = wait.until(new ExpectedCondition<String>() {
				@Override
				public String apply(WebDriver d) {
					Boolean result = false;
					System.err.println("Inspecting driver Window handles");
					Set<String> windowHandles = d.getWindowHandles();
					if (windowHandles.size() > 1) {
						windowHandles.remove(parentHandle);
						String s = (String) (windowHandles.toArray())[0];
						System.err.println("Found popup window handle: " + s);
						return s;
					} else {
						System.out.println("No popup found");
						return null;
					}
				}
			});
			// Act
			driver.switchTo().window(popupHandle);
			System.out.println("Popup Title: " + driver.getTitle());
			driver.close();
			System.out.println("Closed popup");
			driver.switchTo().window(parentHandle);
			System.out.println("switched to parent window: " + parentHandle);
			driver.switchTo().defaultContent();
			System.out.println("switched to default content.");
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append(e.toString());
		}

		// Assert
		try {
			assertThat(driver.getWindowHandles().size(), is(1));
		} catch (AssertionError e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append(e.toString());
		}
	}

	@Test(enabled = false)
	public void test12_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.2browserPopUp.html");
		wait.until(ExpectedConditions.textToBePresentInElement(
				driver.findElement(By
						.cssSelector(".container .row .intro-message button.btn-warning")),
				"Click Me"));

		WebElement button = driver.findElement(
				By.cssSelector(".container .row .intro-message button.btn-warning"));
		assertThat(button, notNullValue());
		final String parentHandle = driver.getWindowHandle();

		// Act
		button.click();
		while (true) { // Wait for the new browser widow to get shown and close it
			List<String> popupHandles = driver.getWindowHandles().stream()
					.filter(((Predicate<String>) (o -> o.equalsIgnoreCase(parentHandle)))
							.negate())
					.collect(Collectors.toList());
			if (popupHandles.size() > 0) {
				driver.switchTo().window(popupHandles.get(0));
				System.out.println("Popup Title: " + driver.getTitle());
				driver.close();
				System.out.println("Closed popup");
				driver.switchTo().window(parentHandle);
				System.out.println("switched to parent window: " + parentHandle);
				driver.switchTo().defaultContent();
				System.out.println("switched to default content.");
				break;
			} else {
				sleep(100);
			}
		}
		// Assert
		try {
			assertThat(driver.getWindowHandles().size(), is(1));
		} catch (AssertionError e) {
			System.err.println("Exception: " + e.toString());
			verificationErrors.append(e.toString());
		}
	}

	// This test appears to find the button even though it is inside iframe
	// without frame switch
	@Test(enabled = false)
	public void test13_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.3frame.html");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
				By.cssSelector(".container .row .intro-message iframe")));

		// Act
		WebElement buttonElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("h3 button"))));

		assertThat(buttonElement, notNullValue());
		// Assert

		WebElement currentElement = buttonElement;
		for (int cnt = 0; cnt != 4; cnt++) {
			try {
				WebElement containerElement = currentElement
						.findElement(By.xpath(".."));
				String elementHTML = containerElement.getAttribute("outerHTML");
				System.err.println("Parent element: " + (elementHTML.length() > 120
						? elementHTML.substring(0, 120) + "..." : elementHTML));
				currentElement = containerElement;
			} catch (InvalidSelectorException e) {
				// InvalidSelectorError: The result of the xpath expression ".." is:
				// [object HTMLDocument]. It should be an element.
				// ignore - reached top level
				break;
			}
		}
		buttonElement.click();
		// Assert
		try {
			alert = driver.switchTo().alert();
			String alertText = alert.getText();
			assertTrue(alertText.contains("You clicked a button within a frame"));
			// confirm alert
			alert.accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	// This test does switch frame fefore find the button in the iframed document
	@Test(enabled = false)
	public void test13_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.3frame.html");

		WebElement frameElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector(".container .row .intro-message iframe")));
		assertThat(frameElement, notNullValue());
		String frameElementDocument = frameElement.getAttribute("outerHTML");

		System.err
				.println("Switching to frame:\n" + (frameElementDocument.length() > 80
						? frameElementDocument.substring(0, 80) + "..."
						: frameElementDocument));

		driver.switchTo().frame(frameElement);
		System.err.println("Inside the frame");
		try {
			frameElement.getTagName();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			// Element belongs to a different frame than the current one
			System.err.println("Exception (expected, ignored): " + e.toString());
		}
		WebElement rootElement = driver.findElement(By.xpath("/*"));
		String documentText = rootElement.getAttribute("outerHTML");
		System.err.println("Root element:\n" + (documentText.length() > 80
				? documentText.substring(0, 80) + "..." : documentText));
		String pageSource = driver.getPageSource();
		System.err.println("Page source:\n" + (pageSource.length() > 80
				? pageSource.substring(0, 80) + "..." : pageSource));

		// Act
		WebElement buttonElement = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector("h3 button[onclick = 'myFunction()']"))));

		assertThat(buttonElement, notNullValue());
		buttonElement.click();
		// Assert

		try {
			// confirm alert
			alert = driver.switchTo().alert();
			String alertText = alert.getText();
			assertTrue(alertText.contains("You clicked a button within a frame"));
			alert.accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	@Test(enabled = false)
	public void test13_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.3frame.html");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
				By.cssSelector(".container .row .intro-message iframe")));

		// Act
		WebElement buttonElement = driver
				.findElement(By.xpath("//button[@onclick = 'myFunction()']"));
		assertThat(buttonElement, notNullValue());
		// Assert

		buttonElement.click();
		// Assert
		try {
			alert = driver.switchTo().alert();
			String alertText = alert.getText();
			assertTrue(alertText.contains("You clicked a button within a frame"));
			// confirm alert
			alert.accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
	}

	@Test(enabled = false)
	public void test14_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.4mouseHover.html");

		WebElement elementWithTooltip = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h3 a"))));
		assertThat(elementWithTooltip, notNullValue());
		// the link title attribute is present regardless of tooltip shown or not
		// Assert
		assertThat(elementWithTooltip.getAttribute("title"),
				equalTo("This is a tooltip."));
		// Act
		actions.moveToElement(elementWithTooltip).build().perform();
		// Assert
		assertThat(elementWithTooltip.getAttribute("title"),
				equalTo("This is a tooltip."));
		// How to discover whether core HTML tool tip is displayed ?

	}

	// https://docs.google.com/a/jazzteam.org/document/d/1PdfKMDfoqFIlF4tN1jKrOf1iZ1rqESy2xVMIj3uuV3g/pub#h.qkrwckq52qpd

	// http://sqa.stackexchange.com/questions/14247/how-can-i-get-the-value-of-the-tooltip
	@Test(enabled = false)
	public void test14_2() {
		// Arrange
		driver.get("http://yuilibrary.com/yui/docs/charts/charts-pie.html");

		WebElement elementWithTooltip = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
						"//*[contains(@class,'yui3-svgSvgPieSlice')][@fill = '#66007f']"))));
		assertThat(elementWithTooltip, notNullValue());

		WebElement chartTitle = driver
				.findElement(By.cssSelector("div#doc div.content h1"));
		// Act
		actions.moveToElement(chartTitle).build().perform();
		sleep(100);
		// Assert
		// the tooltip element is present on the page regardless of mouse position,
		// but is not visible.
		assertThat((int) driver
				.findElements(By.cssSelector("div[class $= 'chart-tooltip']")).stream()
				.filter(o -> {
					return (Boolean) (o.getAttribute("style")
							.indexOf("visibility: hidden;") == -1);
				}).count(), is(0));

		// Act
		actions.moveToElement(elementWithTooltip).build().perform();
		sleep(100);
		// Assert
		List<WebElement> tooltips = driver
				.findElements(By.xpath("//div[contains(@id, '_tooltip')]"));
		assertThat(tooltips.size(), is(1));
		// TODO: visibility check
		assertThat(tooltips.get(0).getText(), containsString("day: Monday"));
	}

	@Test(enabled = false)
	public void test15_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.5resize.html");
		WebElement textAreaElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h3 textarea"))));
		assertThat(textAreaElement, notNullValue());
		System.err.println(
				"Text area original width: " + textAreaElement.getSize().width);
		System.err.println(
				"Text area original height: " + textAreaElement.getSize().height);
		WebElement lineElement = driver.findElement(
				By.cssSelector(".container .row .intro-message h3 hr.intro-divider"));
		assertThat(lineElement, notNullValue());
		System.err.println("Line element width: " + lineElement.getSize().width);
		// Act
		int distance = (lineElement.getSize().width
				- textAreaElement.getSize().width);
		highlight(textAreaElement);
		int xOffset = textAreaElement.getSize().getWidth() - 1;
		int yOffset = textAreaElement.getSize().getHeight() - 1;
		System.err
				.println(String.format("Click and hold at (%d,%d)", xOffset, yOffset));

		actions.moveToElement(textAreaElement, xOffset, yOffset).clickAndHold()
				.moveByOffset(distance, 0);
		actions.release().build().perform();
		// Assert
		sleep(100);
		System.err.println(
				"Text area new width: " + textAreaElement.getSize().getWidth());
		System.err.println(
				"Text area new height: " + textAreaElement.getSize().getHeight());
	}

	@Test(enabled = false)
	public void test15_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/1.1link.html");

		String cssSelector = ".container .row .intro-message h3 a";
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		assertTrue(element.getText().equalsIgnoreCase("click here"),
				element.getText());

		// Act
		highlight(element);

		Mouse mouse = ((HasInputDevices) driver).getMouse();
		Coordinates coords = ((Locatable) element).getCoordinates();
		System.err.println(String.format("Mouse click at: (%-4d, %-4d)",
				coords.inViewPort().x, coords.inViewPort().y));
		mouse.click(coords);

		// Wait page to load
		try {
			wait.until(ExpectedConditions.urlContains("1.1link_validate.html"));
		} catch (UnreachableBrowserException e) {
		}

		// Assert
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					return d.findElement(By.className("intro-message")).getText()
							.contains("Link Successfully clicked");
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
	}

	// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.interactions.Mouse
	// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.interactions.internal.Coordinates
	// http://grepcode.com/file/repo1.maven.org/maven2/org.seleniumhq.selenium/selenium-api/2.40.0/org/openqa/selenium/interactions/Mouse.java#Mouse.mouseMove%28org.openqa.selenium.interactions.internal.Coordinates%2Clong%2Clong%29
	@Test(enabled = false)
	public void test15_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.5resize.html");
		WebElement textAreaElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h3 textarea"))));
		assertThat(textAreaElement, notNullValue());
		System.err.println(textAreaElement.getSize().width);
		System.err.println(textAreaElement.getSize().height);
		WebElement lineElement = driver.findElement(
				By.cssSelector(".container .row .intro-message h3 hr.intro-divider"));
		assertThat(lineElement, notNullValue());
		// Act
		int distance = (lineElement.getSize().width
				- textAreaElement.getSize().width) / 2;
		highlight(textAreaElement);
		int xOffset = textAreaElement.getSize().getWidth() - 1;
		int yOffset = textAreaElement.getSize().getHeight() - 1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		Locatable locatable = (Locatable) textAreaElement;
		Coordinates coords = locatable.getCoordinates();
		System.err.println(String.format("Mouse down at: (%d,%d)",
				coords.inViewPort().x, coords.inViewPort().y));
		mouse.mouseMove(coords, xOffset, yOffset);
		sleep(1000);
		mouse.mouseDown(coords);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		mouse.mouseUp(coords);
		mouse.click(coords);
		sleep(1000);
	}

	@Test(enabled = false)
	public void test16_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.6liCount.html");

		WebElement book2 = driver.findElement(By.xpath(
				"//div[@class='intro-message']/ul/li[ contains(text(), 'Book 2')]"));
		assertThat(book2.getText(), containsString("Book 2"));

		// Act
		int count = 0;
		Enumeration<WebElement> chapters = Collections
				.enumeration(book2.findElements(By.cssSelector("ul li")));

		while (chapters.hasMoreElements()) {
			WebElement currentChapter = chapters.nextElement();
			count++;
			try {
				highlight(currentChapter);
				currentChapter.click();
			} catch (WebDriverException e) {
				System.err
						.println(String.format("Exception (ignored):\n%s", e.toString()));
				// Element is not clickable
			}
		}
		// Assert
		assertThat(count, is(7));
		System.err.println(count);

		WebElement resultElement = driver
				.findElement(By.cssSelector("input#chapbook2"));
		resultElement.clear();
		resultElement.sendKeys(String.format("%d", count));
		sleep(1000);
	}

	@Test(enabled = false)
	public void test16_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.6liCount.html");
		// Act

		List<WebElement> elements = driver
				.findElements(
						By.cssSelector(".container .row .intro-message ul#books li"))
				.stream().filter(o -> {
					return (Boolean) (o.getText().indexOf("Book") > -1);
				}).map(o -> o.findElements(By.cssSelector("ul li")))
				.collect(ArrayList::new, List::addAll, List::addAll);
		// http://stackoverflow.com/questions/25147094/turn-a-list-of-lists-into-a-list-using-lambdas

		// Assert
		assertThat(elements.size(), is(15));
		System.err.println("Total:" + elements.size());
		WebElement resultElement = driver
				.findElement(By.cssSelector("input#chapall"));
		resultElement.clear();
		resultElement.sendKeys(String.format("%d", elements.size()));
	}

	@Test(enabled = false)
	public void test16_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.6liCount.html");

		// Act
		driver
				.findElements(
						By.cssSelector(".container .row .intro-message ul#books li"))
				.stream().filter(o -> {
					CharSequence text = "Book";
					return (Boolean) !(o.getText().contains(text));
				}).forEach(o -> {
					try {
						highlight(o);
						o.click();
					} catch (WebDriverException e) {
						System.err.println(
								String.format("Exception (ignored):\n%s", e.toString()));
						// Element is not clickable
					}
				});
	}

	@Test(enabled = false)
	public void test17() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.7waitUntil.html");

		WebElement clickElement = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		assertThat(clickElement, notNullValue());
		assertThat(clickElement.getText(), containsString("Click Me"));
		// Act
		long startTime = System.currentTimeMillis();
		clickElement.click();
		// Assert
		long retryInterval = 1000;
		long maxRetry = 30;
		long checkRetryDelay = 0;
		while (true) {
			try {
				// confirm alert
				driver.switchTo().alert().accept();
				break;
			} catch (NoAlertPresentException e) {
				// check if waited long enough already
				checkRetryDelay = System.currentTimeMillis() - startTime;
				if (Math.ceil(checkRetryDelay / retryInterval) > maxRetry + 1) {
					System.err.format("Alert not present after %d second\n",
							checkRetryDelay);
					throw new RuntimeException();
				}
				try {
					System.err.print(String.format("Sleep %4.2f sec ...",
							Math.ceil(retryInterval / 1000)));
					Thread.sleep(retryInterval);
				} catch (InterruptedException e2) {
					System.err.println("Unexpected Interrupted Exception: "
							+ e.getStackTrace().toString());
					throw new RuntimeException(e.toString());
				}
			} catch (Exception e) {
				System.err
						.println("Unexpected exception: " + e.getStackTrace().toString());
				throw new RuntimeException(e.toString());
			}
		}

		long delaySecond = (checkRetryDelay / 1000) % 60;
		long delayMinute = (checkRetryDelay / (1000 * 60)) % 60;
		long delayHour = (checkRetryDelay / (1000 * 60 * 60)) % 24;
		String delayTime = String.format("%02d:%02d:%02d", delayHour, delayMinute,
				delaySecond);
		System.err.format("Alert was confirmed at %s\n", delayTime);
	}

	@Test(enabled = false)
	public void test18_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.8progressBar.html");

		WebElement button1 = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message button:nth-of-type(1)"))));
		assertThat(button1, notNullValue());
		assertThat(button1.getAttribute("disabled"), nullValue());

		WebElement button2 = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message button#button2"))));
		assertThat(button2, notNullValue());
		assertThat(button2.getAttribute("disabled"), notNullValue());

		WebElement progressBar = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message div.w3-progress-container div#myBar"))));
		assertThat(progressBar, notNullValue());
		assertThat(progressBar.getAttribute("style"), notNullValue());

		// Act
		button1.click();
		// Brute force wait until button2 gets enabled - need a longer wait timeout
		try {
			(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					String t = button2.getAttribute("disabled");
					return (t == null);
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		// Assert
	}

	@Test(enabled = false)
	public void test18_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.8progressBar.html");

		WebElement button1 = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message button:nth-of-type(1)"))));
		assertThat(button1, notNullValue());
		assertThat(button1.getAttribute("disabled"), nullValue());

		WebElement button2 = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("button2"))));
		assertThat(button2, notNullValue());
		assertThat(button2.getAttribute("disabled"), notNullValue());

		WebElement progressBar = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("myBar"))));
		assertThat(progressBar, notNullValue());
		assertThat(progressBar.getAttribute("style"), notNullValue());
		WebElement progressBarContainer = progressBar.findElement(By.xpath(".."));
		int denom = Integer.parseInt(
				styleOfElement(progressBarContainer, "width").replace("px", ""));
		// Act
		button1.click();

		// Evaluate the progressBar width to reach 100% - need a longer wait timeout
		try {
			(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					Pattern p = Pattern.compile("([0-9.]+)px",
							Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
					float percentage = Float.parseFloat(
							p.matcher(styleOfElement(progressBar, "width")).replaceAll("$1"))
							/ denom;
					System.err.println("Progress Bar: " + percentage);
					return (percentage >= .97);
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		// Assert
		sleep(100);
		System.err.println(
				"Button2 attribute check (1) : " + button2.getAttribute("outerHTML"));
		button2.click();
		try {
			// confirm alert
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}
		// NOTE: latency
		System.err.println(
				"Button2 attribute check (2) : " + button2.getAttribute("outerHTML"));
	}

	@Test(enabled = false)
	public void test18_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.8progressBar.html");

		// html body div.intro-header div.container div.row div.col-lg-12
		// div.intro-message button.w3-btn.w3-dark-grey
		WebElement button1 = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message button:nth-of-type(1)"))));
		assertThat(button1, notNullValue());
		assertThat(button1.getAttribute("disabled"), nullValue());

		WebElement button2 = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("button2"))));
		assertThat(button2, notNullValue());
		assertThat(button2.getAttribute("disabled"), notNullValue());

		WebElement progressBar = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("myBar"))));
		assertThat(progressBar, notNullValue());
		assertThat(progressBar.getAttribute("style"), notNullValue());
		WebElement progressBarContainer = progressBar.findElement(By.xpath(".."));
		int denom = Integer.parseInt(
				styleOfElement(progressBarContainer, "width").replace("px", ""));
		// Act
		button1.click();

		// Evaluate the progressBar width to reach 100%
		// Use Javascript to evaluate the disabled property of the DOM element
		// that will be changing from true to false - need a longer wait timeout
		try {
			(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					Boolean disabled = Boolean.parseBoolean(
							executeScript("return arguments[0].disabled", button2)
									.toString());
					return !disabled;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
		System.err.println(
				"Button2 attribute check (3) : " + button2.getAttribute("outerHTML"));
	}

	@Test(enabled = false)
	public void test19_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.9greenColorBlock.html");

		WebElement greenBoxElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message table div.greenbox"))));
		// Act

		// Assert
		assertThat(greenBoxElement, notNullValue());
		// TODO: computed style of that element

		actions.moveToElement(greenBoxElement).build().perform();
		greenBoxElement.click();
		try {
			// confirm alert
			alert = driver.switchTo().alert();
			String alert_text = alert.getText();
			assertThat(alert_text, containsString("You clicked on Green"));

			alert.accept();
		} catch (NoAlertPresentException e) {
			// Alert not present - ignore
		} catch (WebDriverException e) {
			System.err
					.println("Alert was not handled : " + e.getStackTrace().toString());
			return;
		}

	}

	@Test(enabled = false)
	public void test19_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.9greenColorBlock.html");

		WebElement greenBoxElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message table div.greenbox"))));
		// Act

		// Assert
		assertThat(greenBoxElement, notNullValue());
		// simplified script for computed style an element does not work
		String script = "var element = arguments[0];window.document.defaultView.getComputedStyle(element,null).getPropertyValue(arguments[1]);";

		Object computedStyle = ((JavascriptExecutor) driver).executeScript(script,
				greenBoxElement, "background-color");
		try {
			System.err
					.println("Red Box background color: " + computedStyle.toString());
		} catch (NullPointerException e) {
			System.err.println("Script does not return the value : " + e.toString());
		}
		String style = styleOfElement(greenBoxElement);
		System.err.println("style:\n" + style);

		String backgroundColorAttribute = styleOfElement(greenBoxElement,
				"background-color");
		String heightAttribute = styleOfElement(greenBoxElement, "height");
		String widthAttribute = styleOfElement(greenBoxElement, "width");

		System.err
				.println("backgroundColorAttribute:\n" + backgroundColorAttribute);

		Pattern pattern = Pattern.compile("\\(\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\)");
		Matcher matcher = pattern.matcher(backgroundColorAttribute);
		int red = 0, green = 0, blue = 0;

		if (matcher.find()) {
			red = Integer.parseInt(matcher.group(1).toString());
			green = Integer.parseInt(matcher.group(2).toString());
			blue = Integer.parseInt(matcher.group(3).toString());
			assertTrue(red == 0);
		}
		pattern = Pattern
				.compile("\\(\\s*(?<red>\\d+),\\s*(?<green>\\d+),\\s*(?<blue>\\d+)\\)");
		matcher = pattern.matcher(backgroundColorAttribute);
		if (matcher.find()) {
			red = Integer.parseInt(matcher.group("red").toString());
			green = Integer.parseInt(matcher.group("green").toString());
			blue = Integer.parseInt(matcher.group("blue").toString());
			assertTrue(green >= 128);
		}
		System.err.println("green:" + green);

		System.err.println("heightAttribute:\n" + heightAttribute);
		System.err.println("widthAttribute:\n" + widthAttribute);
		// assertThat(greenBoxElement.getAttribute("background-color"),
		// equalTo("red"));

	}

	// Few failing attempts. Warning: Timing out with Firefox

	// Attempt #1
	@Test(enabled = false)
	public void test20_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h5#drag1 strong"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		actions.clickAndHold(draggableElement).moveToElement(dropElement).release()
				.build().perform();
		sleep(1000);

		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
	}

	// Attempt #2
	@Test(enabled = false)
	public void test20_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h5#drag1 strong"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		actions.dragAndDrop(draggableElement, dropElement).build().perform();
		sleep(1000);

		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
	}

	// Attempt #3
	@Test(enabled = false)
	public void test20_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h5#drag1 strong"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		Point source_location = draggableElement.getLocation();
		highlight(draggableElement);
		Point target_location = dropElement.getLocation();
		actions.dragAndDropBy(draggableElement, 0,
				target_location.y - source_location.y).build().perform();
		actions.release().build();
		actions.perform();
		sleep(1000);
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
	}

	// Attempt #4
	@Test(enabled = false)
	public void test20_4() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h5#drag1 strong"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		Coordinates source_coords = ((Locatable) draggableElement).getCoordinates();
		Coordinates target_coords = ((Locatable) dropElement).getCoordinates();
		System.err.println(String.format("Mouse down at: (%-4d, %-4d)",
				source_coords.inViewPort().x, source_coords.inViewPort().y));
		mouse.mouseDown(source_coords);

		mouse.mouseMove(source_coords, 0,
				target_coords.inViewPort().y - source_coords.inViewPort().y);
		System.err.println(String.format("Mouse up at: (%-4d, %-4d)",
				target_coords.inViewPort().x + 10, target_coords.inViewPort().y + 10));
		mouse.mouseUp(target_coords);
		sleep(1000);
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
	}

	// Attempt #5
	@Test(enabled = false)
	public void test20_5() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector(".container .row .intro-message h5#drag1 strong"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		Coordinates source_coords = ((Locatable) draggableElement).getCoordinates();
		Coordinates target_coords = ((Locatable) dropElement).getCoordinates();
		String simulateDragDropScript = getScriptContent("simulateDragDrop.js");
		System.err.println(String.format("Simulate drag an drop by: (%-4d, %-4d)",
				target_coords.inViewPort().x, target_coords.inViewPort().y));

		executeScript(simulateDragDropScript, draggableElement,
				target_coords.inViewPort().x, target_coords.inViewPort().y);

		sleep(1000);
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
	}

	// https://github.com/tourdedave/elemental-selenium-tips
	@Test(enabled = false)
	public void test20_6() {
		// Arrange
		driver.get("http://the-internet.herokuapp.com/drag_and_drop");

		WebElement draggableElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#column-a"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(), containsString("A"));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#column-b"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("B"));

		// Act
		String dragDropScript = String.format(
				"%s\n$('#column-a').simulateDragDrop({ dropTarget: '#column-b'});",
				getScriptContent("dnd.js"));
		((JavascriptExecutor) driver).executeScript(dragDropScript);

		// Assert
		assertThat(dropElement.getText(), containsString("B"));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
	}

	@Test(enabled = false)
	public void test20_7() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#div1"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		String dragDropScript = String.format(
				"%s\n$('#drag1').simulateDragDrop({ dropTarget: '#div1'});",
				getScriptContent("dnd.js"));
		// System.err.println(dragDropScript);

		((JavascriptExecutor) driver).executeScript(dragDropScript);

		// Assert
		assertThat(dropElement.getText(),
				containsString("This is a draggable text."));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
	}

	@Test(enabled = false)
	public void test20_8() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#div1"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		String dragDropScript = String.format("%s\n%s", getScriptContent("dnd.js"),
				"var dragElememt = $(arguments[0]); var dropTargetSelector = arguments[1]; dragElememt.simulateDragDrop({ dropTarget: dropTargetSelector});");
		// System.err.println(dragDropScript);

		((JavascriptExecutor) driver).executeScript(dragDropScript,
				String.format("#%s", draggableElement.getAttribute("id")),
				String.format("#%s", dropElement.getAttribute("id")));

		// Assert
		assertThat(dropElement.getText(),
				containsString("This is a draggable text."));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
	}

	@Test(enabled = false)
	public void test20_9() {
		// Arrange
		driver.get("http://suvian.in/selenium/2.10dragAndDrop.html");

		WebElement draggableElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getText(),
				containsString("This is a draggable text."));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("#div1"))));
		assertThat(dropElement, notNullValue());
		assertThat(dropElement.getText(), containsString("Drop Here"));

		// Act
		dragdrop(By.id("drag1"), By.id("div1"));
		// Assert
		assertThat(dropElement.getText(),
				containsString("This is a draggable text."));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		sleep(3000);
	}

	// NOTE: this test is failing
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
	public void test22_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.2dragAndDrop.html");

		WebElement draggableElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getAttribute("src"),
				containsString("img/img_logo.gif"));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());
		// Act

		// Act
		String dragDropScript = String.format("%s\n%s", getScriptContent("dnd.js"),
				"var dragElememt = $(arguments[0]); var dropTargetSelector = arguments[1]; dragElememt.simulateDragDrop({ dropTarget: dropTargetSelector});");
		// System.err.println(dragDropScript);

		((JavascriptExecutor) driver).executeScript(dragDropScript,
				String.format("#%s", draggableElement.getAttribute("id")),
				String.format("#%s", dropElement.getAttribute("id")));

		// Assert
		assertThat(dropElement.getAttribute("innerHTML"),
				containsString("img id=\"drag1\""));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
		Optional<WebElement> dragResult = dropElement
				.findElements(By.cssSelector("img#drag1")).stream().findFirst();
		WebElement dragResultElement = (dragResult.isPresent()) ? dragResult.get()
				: null;
		assertThat(dragResultElement, notNullValue());
	}

	// Few failing attempts
	// Attempt #1
	@Test(enabled = false)
	public void test22_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.2dragAndDrop.html");

		WebElement draggableElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getAttribute("src"),
				containsString("img/img_logo.gif"));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());

		// Act
		actions.clickAndHold(draggableElement).moveToElement(dropElement).release()
				.build().perform();
		sleep(1000);

		// Assert
		assertThat(dropElement.getAttribute("innerHTML"),
				containsString("img id=\"drag1\""));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
		Optional<WebElement> dragResult = dropElement
				.findElements(By.cssSelector("img#drag1")).stream().findFirst();
		WebElement dragResultElement = (dragResult.isPresent()) ? dragResult.get()
				: null;
		assertThat(dragResultElement, notNullValue());

	}

	// Attempt #2
	@Test(enabled = false)
	public void test22_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.2dragAndDrop.html");

		WebElement draggableElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getAttribute("src"),
				containsString("img/img_logo.gif"));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());

		// Act
		actions.dragAndDrop(draggableElement, dropElement).build().perform();
		sleep(1000);

		// Assert
		assertThat(dropElement.getAttribute("innerHTML"),
				containsString("img id=\"drag1\""));
		System.err.println("Result: " + dropElement.getAttribute("innerHTML"));
		// Assert
		Optional<WebElement> dragResult = dropElement
				.findElements(By.cssSelector("img#drag1")).stream().findFirst();
		WebElement dragResultElement = (dragResult.isPresent()) ? dragResult.get()
				: null;
		assertThat(dragResultElement, notNullValue());

	}

	// Few failing attempts. Warning: Timing out with Firefox
	@Test(enabled = false)
	public void test22_4() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.2dragAndDrop.html");

		WebElement draggableElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.id("drag1"))));
		assertThat(draggableElement, notNullValue());
		assertThat(draggableElement.getAttribute("src"),
				containsString("img/img_logo.gif"));

		WebElement dropElement = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[@id='div1']"))));
		assertThat(dropElement, notNullValue());

		// Attempt #3
		Point source_location = draggableElement.getLocation();
		highlight(draggableElement);
		Point target_location = dropElement.getLocation();
		actions.dragAndDropBy(draggableElement, 0,
				target_location.y - source_location.y).build().perform();
		actions.release().build();
		actions.perform();

		// Attempt #4
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		Coordinates source_coords = ((Locatable) draggableElement).getCoordinates();
		Coordinates target_coords = ((Locatable) dropElement).getCoordinates();
		System.err.println(String.format("Mouse down at: (%-4d, %-4d)",
				source_coords.inViewPort().x, source_coords.inViewPort().y));
		mouse.mouseDown(source_coords);

		mouse.mouseMove(source_coords, 0, target_location.y - source_location.y);
		System.err.println(String.format("Mouse up at: (%-4d, %-4d)",
				target_coords.inViewPort().x + 10, target_coords.inViewPort().y + 10));
		mouse.mouseUp(target_coords);

		// Assert
	}

	// only loads first 25 lines of code
	@Test(enabled = false)
	public void test23_1() {
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
		List<String> code = codeLines.stream().map(e -> {
			executeScript("arguments[0].scrollIntoView(true);", e);
			highlight(e);
			return e.getText();
		}).collect(Collectors.toList());

		code.stream().forEach(e -> System.err.println(e));
	}

	// loads all code
	@Test(enabled = false)
	public void test23_2() {
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

	// only loads first 35 lines of code
	@Test(enabled = false)
	public void test23_3() {
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

	@Test(enabled = false)
	public void test25_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.5cricketScorecard.html");

		WebElement link = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		// Act

		assertThat(link, notNullValue());
		assertThat(link.getText(), containsString("Generate Scorecard"));
		link.click();

		// Wait page to update

		try {
			wait.until(SuvianTest::hasScoreMethod);
		} catch (UnreachableBrowserException e) {
		}
		// Assert
		List<WebElement> rowElements = driver.findElements(
				By.cssSelector(".container .row .intro-message table tbody tr"));
		Map<String, Integer> playerScores = rowElements.stream()
				.collect(Collectors.toMap(
						o -> o.findElement(By.cssSelector("td:nth-of-type(1)")).getText()
								.trim(),
						o -> Integer.parseInt(
								o.findElement(By.cssSelector("td:nth-of-type(2)")).getText())));
		List<Map.Entry<String, Integer>> playerScoresList = new LinkedList<>(
				playerScores.entrySet());
		Collections.sort(playerScoresList,
				new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Map.Entry<String, Integer> first,
							Map.Entry<String, Integer> second) {
						return (second.getValue()).compareTo(first.getValue());
					}
				});

		String bestScoringPlayerName = playerScoresList.get(0).getKey();
		WebElement playerNameInput = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("form.form-inline input#score[name='topscorer']"))));
		playerNameInput.clear();
		playerNameInput.sendKeys(bestScoringPlayerName);
		System.err.println(bestScoringPlayerName);
		sleep(1000);
	}

	static boolean hasScoreMethod(WebDriver driver) {
		return driver
				.findElement(By.cssSelector(
						".container .row .intro-message table tbody tr td p#sachinruns"))
				.getText().trim().length() > 0;
	}

	// This test was developed with Selenium Driver 2.53 and needs update to work
	// with Selenium 3.x.x
	/*
	@Test(enabled = false)
	public void test25_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.5cricketScorecard.html");
	
		WebElement link = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		// Act
	
		assertThat(link, notNullValue());
		assertThat(link.getText(), containsString("Generate Scorecard"));
		link.click();
	
		// Wait page to update
		com.google.common.base.Predicate<WebDriver> hasScore = d -> {
			return (boolean) (d
					.findElement(By.cssSelector(
							".container .row .intro-message table tbody tr td p#sachinruns"))
					.getText().trim().length() > 0);
		};
		try {
			wait.until(hasScore);
		} catch (UnreachableBrowserException e) {
		}
		// Assert
		List<Integer> scores = driver
				.findElements(By.cssSelector(
						".container .row .intro-message table tbody tr td:nth-of-type(2) p[id]"))
				.stream().map(e -> Integer.parseInt(e.getText().trim()))
				.collect(Collectors.toList());
		Collections.sort(scores, Collections.reverseOrder());
		int maxScore = scores.get(0);
		int minScore = scores.get(scores.size() - 1);
		assertTrue(maxScore >= minScore);
	
		List<String> players = driver
				.findElements(By.cssSelector(
						".container .row .intro-message table tbody tr td:nth-of-type(1)"))
				.stream().filter(o -> {
					WebElement s = o.findElement(By.xpath("following-sibling::td"));
					return (Boolean) (Integer.parseInt(s.getText()) == maxScore);
				}).map(e -> e.getText()).collect(Collectors.toList());
		System.err.println(players.get(0));
		WebElement playerNameInput = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("form.form-inline input#score[name='topscorer']"))));
		playerNameInput.clear();
		playerNameInput.sendKeys(StringUtils.join(players, ","));
		Integer scoreSachin = Integer.parseInt(driver
				.findElement(By.cssSelector(
						".container .row .intro-message table tbody tr td p#sachinruns"))
				.getText().trim());
		WebElement scoreInput = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By
						.cssSelector("form.form-inline input#score[name='sachinruns']"))));
		scoreInput.clear();
		scoreInput.sendKeys(String.format("%d", scoreSachin));
		sleep(1000);
	}
	*/

	// This test was developed with Selenium Driver 2.53 and needs update to work
	// with Selenium 3.x.x
	/*
	@Test(enabled = false)
	public void test25_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.5cricketScorecard.html");
	
		WebElement link = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		assertThat(link, notNullValue());
	
		// Act
		link.click();
	
		// Wait page to update
		try {
			wait.until((com.google.common.base.Predicate<WebDriver>) (d -> d
					.findElement(By.cssSelector(
							".container .row .intro-message table tbody tr td p#sachinruns"))
					.getText().trim().length() > 0));
		} catch (UnreachableBrowserException e) {
		}
		Map<String, Integer> playerScores = driver
				.findElements(
						By.cssSelector(".container .row .intro-message table tbody tr"))
				.stream()
				.collect(Collectors.toMap(
						o -> o.findElement(By.xpath("td[1]")).getText().trim(),
						o -> Integer.parseInt(o.findElement(By.xpath("td[2]")).getText())));
		// Assert
		LinkedHashMap<String, Integer> playerScoresList = sortByValue(playerScores);
	
		// TODO : finish
		for (String key : playerScoresList.keySet()) {
			System.out.println(key + ":\t" + playerScoresList.get(key));
		}
	}
	
	// sorting example from
	// http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
	public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(
			Map<K, V> map) {
		return map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(e1, e2) -> e1, LinkedHashMap::new));
	}
	*/

	@Test(enabled = false)
	public void test26() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.6copyTextFromTextField.html");
		WebElement sourceElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message form textarea[name='field_one']"))));
		assertThat(sourceElement, notNullValue());
		WebElement targetElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
						"//div[@class = 'container']//div[@class = 'row']//div[@class = 'intro-message']//form/textarea[@name='field_two']"))));
		assertThat(targetElement, notNullValue());

		// Act
		String text = sourceElement.getText();
		executeScript("arguments[0].removeAttribute('readonly');", sourceElement);
		// arguments[0].removeAttribute('readonly',0)
		sourceElement.clear();
		targetElement.sendKeys((CharSequence) text);
		sleep(100);

		// Assert
		String textValue = executeScript("return arguments[0].value;",
				targetElement).toString();

		assertThat(textValue, containsString(text));
		System.err.println("value = " + textValue);

		assertThat(targetElement.getAttribute("value"), containsString(text));
		// Assert
		try {
			assertThat(targetElement.getText(), containsString(text));
		} catch (AssertionError e) {
			System.err.println("Exception(ignoredd): " + e.toString());
			// new textArea value is not possible to retrieve with getText()
		}
	}

	@Test(enabled = false)
	public void test27_1() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.7correspondingRadio.html");

		WebElement inputElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
						"//div[@class='intro-message']/ul[@id='tst']/li[.='India']/input[@type='radio' and @name='country']"))));
		assertThat(inputElement, notNullValue());
		inputElement.click();
		sleep(100);
		// Assert
		String script = "var radios = document.getElementsByTagName('input');\n"
				+ "var checkedRadioItemNumber = 1; for (var cnt = 0; cnt < radios.length; cnt++) {\n"
				+ "if (radios[cnt].type === 'radio' && radios[cnt].checked) {\n"
				+ "checkedRadioItemNumber = cnt; }\n}\n"
				+ "var value = radios[checkedRadioItemNumber].value;"
				+ "return (checkedRadioItemNumber + 1).toString();";
		String checkedRadioItemNumber = executeScript(script).toString();
		System.err.println("Checked is Radio item #" + checkedRadioItemNumber);
		WebElement itemElement = driver.findElement(By.xpath(
				String.format("//ul[@id='tst']/li[%s]", checkedRadioItemNumber)));
		assertThat(itemElement, notNullValue());
		System.err.println("Result : " + itemElement.getText());
	}

	@Test(enabled = false)
	public void test27_2() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.7correspondingRadio.html");

		WebElement itemList = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message ul#tst"))));
		assertThat(itemList, notNullValue());
		// Act

		WebElement inputElement = itemList.findElements(By.tagName("li")).stream()
				.filter(o -> o.getText().contains((CharSequence) "India"))
				.map(o -> o
						.findElement(By.xpath("input[@type='radio' and @name='country']")))
				.findFirst().orElseThrow(RuntimeException::new);
		inputElement.click();
		sleep(100);
		// Assert
		Optional<WebElement> result = driver.findElements(By.tagName("input"))
				.stream().filter(o -> o.getAttribute("type").equalsIgnoreCase("radio"))
				.filter(o -> o.getAttribute("checked") != null).findFirst();
		WebElement checkedRadioButton = (result.isPresent()) ? result.get() : null;
		assertThat(checkedRadioButton, notNullValue());
		System.err.println(String.format("Value : %s\tChecked: %s",
				checkedRadioButton.getAttribute("value"),
				checkedRadioButton.getAttribute("checked")));
	}

	// https://docs.google.com/a/jazzteam.org/document/d/1PdfKMDfoqFIlF4tN1jKrOf1iZ1rqESy2xVMIj3uuV3g/pub
	@Test(enabled = false)
	public void test27_3() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.7correspondingRadio.html");

		WebElement itemList = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message ul#tst"))));
		assertThat(itemList, notNullValue());
		// Act
		WebElement inputElement = itemList
				.findElements(By.xpath(
						"li[contains(text(), 'India')]//input[@type='radio' and @name='country']"))
				.stream()
				.filter(o -> o.findElement(By.xpath("..")).getText()
						.contains((CharSequence) "India"))
				.findFirst().orElseThrow(RuntimeException::new);
		inputElement.click();
		sleep(100);
		// Assert
		Optional<WebElement> result = driver.findElements(By.tagName("input"))
				.stream().filter(o -> o.getAttribute("type").equalsIgnoreCase("radio"))
				.filter(o -> o.getAttribute("checked") != null).findFirst();
		WebElement checkedRadioButton = (result.isPresent()) ? result.get() : null;
		assertThat(checkedRadioButton, notNullValue());
		System.err.println(String.format("Value : %s\tChecked: %s",
				checkedRadioButton.getAttribute("value"),
				checkedRadioButton.getAttribute("checked")));
	}

	@Test(enabled = false)
	public void test27_4() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.7correspondingRadio.html");

		// Act
		WebElement inputElement = driver
				.findElements(By.xpath(
						"//li[contains(text(), 'India')]//input[@type='radio' and @name='country']"))
				.stream()
				.filter(o -> o.findElement(By.xpath("..")).getText()
						.contains((CharSequence) "India"))
				.findFirst().orElseThrow(RuntimeException::new);
		inputElement.click();
		sleep(100);
		// Assert
		Optional<WebElement> result = driver.findElements(By.tagName("input"))
				.stream().filter(o -> o.getAttribute("type").equalsIgnoreCase("radio"))
				.filter(o -> o.findElement(By.xpath("..")).getText()
						.contains((CharSequence) "India"))
				.filter(o -> o.getAttribute("checked") != null).findFirst();
		WebElement checkedRadioButton = (result.isPresent()) ? result.get() : null;
		assertThat(checkedRadioButton, notNullValue());
		System.err.println(String.format("Value : %s\tChecked: %s",
				checkedRadioButton.getAttribute("value"),
				checkedRadioButton.getAttribute("checked")));
	}

	@Test(enabled = false)
	public void test28_1() {
		// Arrange
		driver.get("http://toolsqa.com/automation-practice-table/");
		// Act
		WebElement linkElement = driver.findElement(By.xpath(
				"//table[@summary='Sample Table']/tbody/tr/td[text()='Taipei']/../descendant::a[@href='#']"));
		assertThat(linkElement, notNullValue());
		highlight(linkElement);
	}

	@Test(enabled = false)
	public void test28_2() {
		// Arrange
		driver.get("http://toolsqa.com/automation-practice-table/");
		// Act
		WebElement linkElement = driver.findElement(By.xpath(
				"//table[@summary='Sample Table']/tbody/tr[td/text()='Taipei']/descendant::a[@href='#']"));
		assertThat(linkElement, notNullValue());
		highlight(linkElement);
	}

	@Test(enabled = false)
	public void test28_3() {
		// Arrange
		driver.get("http://toolsqa.com/automation-practice-table/");
		// Act
		Optional<WebElement> trylinkElement = driver
				.findElements(By.xpath("//table[@summary='Sample Table']/tbody/tr"))
				.stream()
				.filter(
						o -> o.findElements(By.xpath("td[text()='Taipei']")).size() != 0)
				.map(o -> o.findElement(By.xpath("td/a[@href='#']"))).findFirst();
		WebElement linkElement = (trylinkElement.isPresent()) ? trylinkElement.get()
				: null;
		assertThat(linkElement, notNullValue());
		highlight(linkElement);

		assertThat(linkElement, notNullValue());
		highlight(linkElement);
	}

	/*
	
	<div>
	<ul class="nav nav-pills nav-stacked">
	<li>
	<section>
	<span name="merchant">Credit Card</span>
	</section>
	<section>
	<span class="glyphicon glyphicon-pencil" name="edit"></span>
	<span class="glyphicon glyphicon-remove" name="delete"></span>
	</section>
	</li>
	</ul>
	<div class="add-item bottom" name="new-merchant">
	</div>
	
	"//li/section[span/text()='Credit Card']/../following-sibling::section/span[@name='edit']"
	*/

	@Test(enabled = false)
	public void test30() {
		// Arrange
		driver.get("http://suvian.in/selenium/3.10select1stFriday.html");

		WebElement calendarElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						".container .row .intro-message h4 input[name='calendar']"))));
		int xOffset = calendarElement.getSize().getWidth() - 5;
		int yOffset = calendarElement.getSize().getHeight() - 5;
		System.err.println(String.format("Hover at (%d, %d)", xOffset, yOffset));

		actions.moveToElement(calendarElement, xOffset, yOffset);
		actions.build().perform();
		sleep(1000);
		calendarElement.sendKeys("01/21/2017");
		actions.moveToElement(calendarElement, xOffset, yOffset).click().build()
				.perform();

		sleep(100);
		// NOTE: unable to locate individual calendar dates elements with Selenium
		// WebDriver
		driver.findElements(By.xpath("//*[contains(@class,'calendar')]")).stream()
				.forEach(o -> {
					System.err.println(o.getAttribute("outerHTML"));
				});
		for (int i = 0; i != 8; i++) {
			calendarElement.sendKeys(Keys.ARROW_RIGHT);
			sleep(100);
		}
		sleep(10000);
		// Assert
	}

	// bad practice ?
	// too much navigation and excessively detailed - possibly auto-generated -
	// locators
	// http://automated-testing.info/t/webdriver-java-ne-rabotaet-metod-click/13838/16
	@Test(enabled = false)
	public void test31_1() {
		// Arrange
		driver.get(
				"https://accounts.google.com/SignUp?service=mail&hl=ru&continue=http%3A%2F%2Fmail.google.com%2Fmail%2F%3Fpc%3Dtopnav-about-ru");
		// Act
		WebElement element = driver.findElement(By.xpath(
				"//strong[text()='Пол']/following-sibling::div/div[@role='listbox'][1]"));
		assertThat(element, notNullValue());
		highlight(element);
		element.click();
		sleep(300);
		element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.xpath("//div[text()='Мужской']/parent::div[@role='option']"))));
		highlight(element);
		element.click();

	}

	// somewhat refactored
	@Test(enabled = false)
	public void test31_2() {
		// Arrange
		driver.get(
				"https://accounts.google.com/SignUp?service=mail&hl=ru&continue=http%3A%2F%2Fmail.google.com%2Fmail%2F%3Fpc%3Dtopnav-about-ru");
		// Act

		List<WebElement> elements = driver.findElements(By.xpath(
				"//div/label[strong/text()='Пол']/div[@id='Gender']/div[@role='listbox']"));
		assertThat(elements.get(0), notNullValue());
		WebElement element = elements.get(0);
		highlight(element);
		sleep(300);
		element.click();
		sleep(300);
		element = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.xpath("//div[@role='option'][div/text()='Мужской']"))));
		highlight(element);
		element.click();
	}

	//
	@Test(enabled = false)
	public void test32_1() {
		// Arrange
		driver.get(
				"https://market.yandex.ru/catalog/54913/list?hid=90566&track=fr_ctlg&lr=0&local-offers-first=0&deliveryincluded=0&onstock=1");
		actions.moveToElement(
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
						.xpath("//div[@class='n-filter-panel-aside__content']/div[4]")))));

		// Act
		List<WebElement> elements = driver.findElements(By.xpath(
				"//div[@class='n-filter-panel-aside__content']/div[4]//span[@class='checkbox__box']/input[@type='checkbox']"));
		assertTrue(elements.size() > 0);
		assertThat(elements.get(0), notNullValue());
		for (WebElement element : elements) {
			System.err
					.println("Selecting the checkbox:\n" + element.getAttribute("id"));
			actions.moveToElement(element);
			element.sendKeys(Keys.SPACE);
			try {
				highlight(element, 100);
			} catch (Exception e) {
				System.err.println("highlight() Exception " + e.getMessage());
				// Exception Expected condition failed: waiting for visibility of
				// element
			}
			try {
				highlightNew(element, 100);
			} catch (Exception e) {
				System.err.println("highlightNew() Exception: " + e.getMessage());
				// Exception unknown command: session/.../element/.../rect
			}
			try {
				element.click();
			} catch (Exception e) {
				System.err.println("click() Exception: " + e.getMessage());
				// Element ... is not clickable at point ... Other element would receive
				// the click
			}
			sleep(100);
		}
	}

	@Test(enabled = false)
	public void test32_2() {
		// Arrange
		driver.get(
				"https://market.yandex.ru/catalog/54913/list?hid=90566&track=fr_ctlg&lr=0&local-offers-first=0&deliveryincluded=0&onstock=1");
		actions.moveToElement(
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By
						.xpath("//div[@class='n-filter-panel-aside__content']/div[4]")))));

		// Act
		List<WebElement> elements = driver.findElements(By.xpath(
				"//div[@class='n-filter-panel-aside__content']/div[4]//span[span/@class='checkbox__box']/label[@class='checkbox__label']"));
		System.err.println("Elements size=" + elements.size());
		assertThat(elements.get(0), notNullValue());
		for (WebElement element : elements) {
			actions.moveToElement(element);
			highlight(element);
			System.err.println("Click on label:" + element.getAttribute("for"));
			element.click();
			try {
				element.sendKeys(Keys.SPACE);
			} catch (Exception e) {
				// unknown error: cannot focus element
				System.err.println("sendKeys() Exception " + e.getMessage());
			}
		}
	}

	//
	@Test(enabled = true)
	public void test33_1() {
		// Arrange
		driver.get("https://datatables.net/examples/api/form.html");
		String table_id = "example";

		WebElement table_element;
		try {
			table_element = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}
		String text_input_css_selector = "input[id='row-5-age']";
		WebElement text_input_element = table_element
				.findElement(By.cssSelector(text_input_css_selector));
		actions.moveToElement(text_input_element).build().perform();
		String cell_text = "Software Developer";
		text_input_element.clear();
		// https://selenium.googlecode.com/svn/trunk/docs/api/java/org/openqa/selenium/Keys.html
		text_input_element.sendKeys(Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE,
				Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE));
		sleep(3000);
		text_input_element.sendKeys(Keys.chord("20", org.openqa.selenium.Keys.TAB,
				cell_text, org.openqa.selenium.Keys.ENTER));
		sleep(3000);
	}

	@Test(enabled = true)
	public void test33_2() {

		// Go to base url
		driver.get("https://datatables.net/examples/api/highlight.html");

		// Maximize Window
		driver.manage().window().maximize();

		String table_id = "example";
		WebElement table_element;

		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
		} catch (RuntimeException timeoutException) {
			return;
		}
		// http://stackoverflow.com/questions/6198947/how-to-get-text-from-each-cell-of-an-html-table
		String script = "var table_row_locator = 'div#example_wrapper table#example tbody tr';\n"
				+ "var rows = document.querySelectorAll(table_row_locator);\n"
				+ "var result = [];\n"
				+ "for (row_cnt = 0; row_cnt != rows.length; row_cnt++) {\n"
				+ "var row = rows[row_cnt];\n" + "if (row instanceof Element) {\n"
				+ "var cols = row.querySelectorAll('td');\n"
				+ "var check_col_num = 1;\n" + "var data_col_num = 0;\n"
				+ "if (cols[check_col_num].innerHTML.match(/^Software.*/ig)) {\n"
				+ "result.push(cols[data_col_num].innerHTML);\n" + "}\n" + "}\n" + "}\n"
				+ "return result.join();\n";

		System.out.println(
				String.format("Script: '%s'\nResult: '%s'", script, storeEval(script)));

		// NOTE: Works in IDE, does not work with WebDriver
		script = String.format("(function() { %s })();", script);
		System.out.println(
				String.format("Script: '%s'\nResult: '%s'", script, storeEval(script)));

		table_element = driver.findElement(By.id(table_id));
		// NOTE: no leading slash in XPath
		List<WebElement> rows = table_element.findElements(By.xpath("tbody/tr"));
		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rows.size());
		int row_num, cell_num, max_rows;
		max_rows = 3;
		row_num = 1;
		String row_role_attribute = "row";
		for (WebElement row : rows) {
			if (row_num > max_rows) {
				break;
			}
			assertTrue(
					// String.format("Unexpected title '%s'", row.getAttribute("role")),
					row.getAttribute("role").matches(row_role_attribute));
			cell_num = 1;
			List<WebElement> cells = row.findElements(By.xpath("td"));
			String checkColumn = cells.get(cell_num).getText();
			// System.out.println(checkColumn);
			if (checkColumn.matches("Software.*")) {
				// System.out.println("NUMBER OF COLUMNS=" + cells.size());
				cell_num = 1;
				for (WebElement cell : cells) {
					// Hover over cell
					actions.moveToElement(cell).build().perform();
					highlight(cell);
					System.out.println(String.format("row # %d, col # %d text='%s'",
							row_num, cell_num, cell.getText()));
					cell_num++;
				}
				row_num++;
			}
		}
	}

	// Utils

	private String storeEval(String script) {
		String result = null;
		if (driver instanceof JavascriptExecutor) {
			System.out.println("running the script");
			result = (String) ((JavascriptExecutor) driver).executeScript(script);
		}
		return result;
	}

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	private void highlightNew(WebElement element, long highlight_interval) {
		Rectangle elementRect = element.getRect();
		String highlightScript = getScriptContent("highlight.js");
		// append calling

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						String.format(
								"%s\nhighlight_create(arguments[0],arguments[1],arguments[2],arguments[3]);",
								highlightScript),
						elementRect.y, elementRect.x, elementRect.width,
						elementRect.height);
			}
			Thread.sleep(highlight_interval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						String.format("%s\nhighlight_remove();", highlightScript));
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

	}

	private boolean areElementsPresent(WebElement parentWebElement,
			By byLocator) {
		return parentWebElement.findElements(byLocator).size() == 1;
		// usage:
		// assertTrue(areElementsPresent(driver.findElements(By.cssSelector("li[class*=
		// product]")).get(0), By.cssSelector("[class*=sticker]")));
	}

	public boolean isGreaterThen(String a, String b) {
		return a.compareTo(b) < 0;
		// usage:
		// List<WebElement> rows =
		// table.findElements(By.cssSelector("td:nth-of-type(5)>a"));
		//
		// for (int i = 0; i<rows.size()-1;i++) {
		// assertTrue(isGreaterThen(rows.get(i).getAttribute("textContent"),rows.get(i+1).getAttribute("textContent")));
		// }
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

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	private String styleOfElement(WebElement element, Object... arguments) {
		String getStyleScript = getScriptContent("getStyle.js");

		return (String) executeScript(getStyleScript, element, arguments);
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = SuvianTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	// http://stackoverflow.com/questions/19384710/javascript-workaround-for-drag-and-drop-in-selenium-webdriver/26372276#26372276
	public void dragdrop(By ByFrom, By ByTo) {
		WebElement LocatorFrom = driver.findElement(ByFrom);
		WebElement LocatorTo = driver.findElement(ByTo);
		String xto = Integer.toString(LocatorTo.getLocation().x);
		String yto = Integer.toString(LocatorTo.getLocation().y);
		executeScript(getScriptContent("simulateDragDrop2.js"), LocatorFrom, xto,
				yto);
	}

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
