package com.mycompany.app;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
This class tests the Select 2 Element https://select2.github.io/examples.html
intended to create a custom class implementing subset of ISelect interface
influenced by [selenium select2 wrapper project by sskorol](https://github.com/sskorol/select2-wrapper)
*/

public class Select2WrapperTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private static Actions actions;
	private static Alert alert;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private static String baseURL = "https://select2.github.io/examples.html";
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

	@Test(enabled = true)
	public void test1() {
		// Arrange

		// Wait page to load
		WebElement element = wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("select.js-states"))));
		// Act
		executeScript(
				"var select2 = $('select.js-states').select2(); option = select2.val('FL'); option.trigger('select');");
		// TODO:  note without the second script the result is updated too late
		String result = (String) executeScript(
				"var select2 = $('select.js-states').select2(); return select2.val();");
		System.err.println("Result: " + result);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		// 2. TODO: Expectation condition with Iterator, use String methods
		element = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(
						"span.selection span.select2-selection span.select2-selection__rendered"))));
		// Assert
		assertThat(element, notNullValue());
		highlight(element);
		System.err.println(element.getText());
	}

	@Test(enabled = false)
	public void test2() {
		// Arrange
		// Wait page to load
		WebElement element = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.cssSelector(".container .row .intro-message h3 a"))));
		// A
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

  // origin: https://github.com/sskorol/select2-wrapper/blob/master/src/main/java/com/tools/qaa/elements/Select2.java
	// TODO: implement ISelect ?
	// https://github.com/SeleniumHQ/selenium/blob/master/java/client/src/org/openqa/selenium/support/ui/Select.java
	public static class Select2 {
		private WebElement element = null;
		private WebDriver driver = null;
		private boolean isMulti = false;
		private String elementValue;

		public Select2(WebDriver driver, WebElement element) {
			this.element = element;
			this.driver = driver;
		}

		public void selectByVisibleText(final String text) {
			// waitUntil(ExpectedConditions::visibilityOfElementLocated);
			executeScript("var value = $(\"" + this.elementValue
					+ "+select\").find('option:contains(\"" + text + "\")').val();"
					+ "$(\"" + this.elementValue + "\").select2(\"val\", value);");
		}

		public String getSelectedText() {
			// waitUntil(ExpectedConditions::visibilityOfElementLocated);
			return (String) executeScript(
					"return $(\"" + this.elementValue + "\").select2('data').text;");
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
