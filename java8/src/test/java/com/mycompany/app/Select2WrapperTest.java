package com.mycompany.app;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/*
 * This class tests the Select 2 Element https://s2Obj.github.io/examples.html
 * intended to create a custom class implementing subset of ISelect interface
 * influenced by [selenium s2Obj wrapper project by sskorol](https://github.com/sskorol/s2Obj-wrapper)
 * https://qa-automation-notes.blogspot.com/2015/11/webdriver-vs-select2.html
*/

public class Select2WrapperTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private static Actions actions;
	private int flexibleWait = 5;
	private long pollingInterval = 500;
	private static String baseURL = "https://select2.github.io/examples.html";
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final String browser = "chrome";
	private static String osName;

	private static final String selectOptionScript = "var selector = arguments[0]; var oVal = arguments[1]; var s2Obj = $(selector).select2(); option = s2Obj.val(oVal); option.trigger('select');";
	private static final String querySelectedValueScript = "var selector = arguments[0]; var s2Obj = $(selector).select2(); return s2Obj.val();";
	private static final String selectByVisibleTextScript = "var selector = arguments[0]; var s2Obj = $(selector).select2(); var text = arguments[1]; var foundOption = s2Obj.find('option:contains(\"' + text + '\")').val(); return foundOption";

	@Test(enabled = false)
	public void selectByOptionValueTest() {
		String selectOption = "FL";

		// Arrange
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("select.js-states"))));
		// Act

		executeScript(selectOptionScript, "select.js-states", selectOption);
		// TODO: appears without running the second script the result is not updated
		String result = (String) executeScript(querySelectedValueScript,
				"select.js-states");
		System.err.println("Selected via Javascript: " + result);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}

		// Assert
		// TODO: Update the Expectation condition with Iterator and String methods
		WebElement selectionElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"span.selection span.select2-selection span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	@Test(enabled = false)
	public void selectByVisibleOptionTextTest() {
		// Arrange
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("select.js-states"))));
		// Act
		String result = (String) executeScript(selectByVisibleTextScript,
				"select.js-states", "Florida");
		System.err.println("Selected via Javascript: " + result);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		// Assert
		// TODO: Update the Expectation condition with Iterator and String methods
		WebElement selectionElement = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"span.selection span.select2-selection span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	@Test(enabled = false)
	public void selectBySelectWrapperObjectTest() {
		// Arrange
		Select2 element = new Select2(driver, "select.js-states");
		// Act
		highlight(element.getWrappedElement());
		// Assert
		assertThat(element, notNullValue());
		// Act
		Map<String, String> stateMap = new HashMap<>();
		stateMap.put("Florida", "FL");
		stateMap.put("Washington", "WA");
		Iterator<String> stateiIterator = stateMap.keySet().iterator();
		while (stateiIterator.hasNext()) {
			String stateFullName = stateiIterator.next();
			String result = element.selectByVisibleText(stateFullName);
			// Assert
			assertTrue(result.equals(stateMap.get(stateFullName)),
					String.format("State %s code should be %s but was %s", stateFullName,
							stateMap.get(stateFullName), result));
			System.err.println("Result: " + result);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
			}
		}
	}

	@Test(enabled = false)
	public void select2VisualActionVerify1Test() {
		String selectOption = "FL";

		// Arrange
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.cssSelector("select.js-example-basic-single.js-states"))));
		// Act
		executeScript(selectOptionScript, "select.js-states", selectOption);
		String result = (String) executeScript(querySelectedValueScript,
				"select.js-states");
		// Assert
		assertTrue(result.equals(selectOption), String
				.format("State code should be %s but was %s", selectOption, result));
		System.err.println("Selected via Javascript: " + result);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		// Act
		WebElement arrowElement = driver
				.findElement(By.cssSelector("span.select2-selection__arrow"));
		highlight(arrowElement);
		arrowElement.click();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));
		List<WebElement> options = resultsElement
				.findElements(By.cssSelector("li.select2-results__option"));
		// available options
		// options.stream().forEach(o ->
		// System.err.println(o.getText()));
		WebElement highlightedOption = options.stream()
				.filter(o -> o.getAttribute("class").contains("highlighted"))
				.findFirst().get();
		System.err.println("Selected Visible Text: " + highlightedOption.getText());
		highlight(highlightedOption);
		highlightedOption.click();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		// Assert
		WebElement selectionElement = wait
				.until(ExpectedConditions.visibilityOf(driver
						.findElement(By.cssSelector("span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	// TODO: refactor
	@Test(enabled = false)
	public void select2VisualActionVerify2Test() {
		String selectOption = "FL";

		// Arrange
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.cssSelector("select.js-example-basic-single.js-states"))));
		// Act
		String result = (String) executeScript(selectByVisibleTextScript,
				"select.js-states", "Florida");
		System.err.println("Selected via Javascript: " + result);
		// Assert
		assertTrue(result.equals(selectOption), String
				.format("State code should be %s but was %s", selectOption, result));
		System.err.println("Selected via Javascript: " + result);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		// Act
		WebElement arrowElement = driver
				.findElement(By.cssSelector("span.select2-selection__arrow"));
		highlight(arrowElement);
		arrowElement.click();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));

		WebElement highlightedOption = resultsElement
				.findElements(By.cssSelector("li.select2-results__option")).stream()
				.filter(o -> o.getAttribute("class").contains("highlighted"))
				.findFirst().get();
		System.err.println("Selected Text: " + highlightedOption.getText());
		highlight(highlightedOption);
		highlightedOption.click();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		// Assert
		WebElement selectionElement = wait
				.until(ExpectedConditions.visibilityOf(driver
						.findElement(By.cssSelector("span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

	@Test(enabled = true)
	public void select2VisualActionSelectTest() {
		String selectOption = "MN";

		// Arrange
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.cssSelector("select.js-example-basic-single.js-states"))));
		// Act
		WebElement arrowElement = driver
				.findElement(By.cssSelector("span.select2-selection__arrow"));
		highlight(arrowElement);
		arrowElement.click();
		// TODO: convert to flexible
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		WebElement resultsElement = wait
				.until(ExpectedConditions.visibilityOf(driver.findElement(
						By.cssSelector("span.select2-container span.select2-results"))));
		System.err.println(resultsElement.getAttribute("innerHTML"));
		// TODO: scroll somehow
		// TODO: filter out role="group"
		WebElement selectedOption = resultsElement
				.findElements(By.cssSelector("li.select2-results__option")).stream()
				.filter(o -> o.getAttribute("role").matches("treeitem")).filter(o -> {
					String text = o.getText();
					System.err.println(String.format("Option text: \"%s\"", text));
					actions.moveToElement(o).build().perform();
					if (text.contains("Minnesota")) {
						return true;
					} else {
						return false;
					}
				}).findFirst().get();
		System.err.println("Selected Visible Text: " + selectedOption.getText());
		highlight(selectedOption);
		actions.moveToElement(selectedOption).click().build().perform();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}

		// Assert
		String result = (String) executeScript(querySelectedValueScript,
				"select.js-states");
		assertTrue(result.equals(selectOption), String
				.format("State code should be %s but was %s", selectOption, result));
		System.err.println("Selected: " + result);
		// Assert
		WebElement selectionElement = wait
				.until(ExpectedConditions.visibilityOf(driver
						.findElement(By.cssSelector("span.select2-selection__rendered"))));
		assertThat(selectionElement, notNullValue());
		highlight(selectionElement);
		System.err.println("Selection: " + selectionElement.getText());
	}

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

	// utilities
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
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlight_interval);
			executeScript("arguments[0].style.border=''", element);
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

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = Select2WrapperTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	// inspired by:
	// https://github.com/sskorol/select2-wrapper/blob/master/src/main/java/com/tools/qaa/elements/Select2.java
	// https://github.com/SeleniumHQ/selenium/blob/master/java/client/src/org/openqa/selenium/support/ui/Select.java
	public static class Select2 {

		private WebDriver driver = null;
		private WebElement element;
		private WebDriverWait wait;
		private boolean isMulti = false;
		private String elementCssSelector;
		private int flexibleWait = 5;
		private long pollingInterval = 500;

		public WebElement getWrappedElement() {
			return this.element;
		}

		public Select2(WebDriver driver, String elementCssSelector) {
			this.elementCssSelector = elementCssSelector;
			this.driver = driver;
			this.wait = new WebDriverWait(driver, this.flexibleWait);
			this.wait.pollingEvery(this.pollingInterval, TimeUnit.MILLISECONDS);
			this.element = wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.cssSelector(this.elementCssSelector))));
		}

		public String selectByVisibleText(final String text) {
			String selectByVisibleTextScript = "var s2Obj = $('"
					+ this.elementCssSelector
					+ "').select2(); var text = arguments[0] ; var foundOption = s2Obj.find('option:contains(\"' + text + '\")').val(); return foundOption";
			wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.cssSelector(this.elementCssSelector))));
			String optionValue = (String) executeScript(selectByVisibleTextScript,
					text);
			return optionValue;
		}

		public String getSelectedText() {
			wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.cssSelector(this.elementCssSelector))));
			// TODO:
			return (String) executeScript(
					"return $('" + this.elementCssSelector + "').select2('data').text;");
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
	}
}
