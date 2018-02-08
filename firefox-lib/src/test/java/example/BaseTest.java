package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class BaseTest {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions actions;
	public static Alert alert;
	public static JavascriptExecutor js;
	public static TakesScreenshot screenshot;

	// need a little more room for datepicker
	private static int width = 1200;
	private static int height = 980;

	protected static final Logger log = LogManager.getLogger(BaseTest.class);

	public static int scriptTimeout = 5;
	public static int flexibleWait = 60; // too long
	public static int implicitWait = 1;
	public static long pollingInterval = 500;
	private static long highlightInterval = 100;

	public static String baseURL = "about:blank";
	private static String osName = getOsName();

	private static final String browser = getPropertyEnv("webdriver.driver",
			"firefox");

	public static String getBrowser() {
		return browser;
	}

	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome", "chromedriver.exe");
		browserDrivers.put("firefox", "geckodriver.exe");
	}

	public void setScriptTimeout(int value) {
		this.scriptTimeout = value;
	}

	public void setFlexibleWait(int value) {
		this.flexibleWait = value;
	}

	public void setImplicitWait(int value) {
		this.implicitWait = value;
	}

	public void setPollingInterval(long value) {
		this.pollingInterval = value;
	}

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() throws IOException {

		System.err.println("Launching " + browser);

		System.setProperty("webdriver.gecko.driver",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
						: "/tmp/geckodriver");
		System.setProperty("webdriver.firefox.bin",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");
		// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
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
		// optional
		/* 
		 profile.setPreference("general.useragent.override",
		 		"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0");
		*/
		// System.out.println(System.getProperty("user.dir"));
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		try {
			driver = new FirefoxDriver(capabilities);
		} catch (WebDriverException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot initialize Firefox driver");
		}
		actions = new Actions(driver);

		driver.manage().window().setSize(new Dimension(width, height));
		driver.manage().timeouts().setScriptTimeout(scriptTimeout,
				TimeUnit.SECONDS);
		// Declare a wait time
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		screenshot = ((TakesScreenshot) driver);
		js = ((JavascriptExecutor) driver);
		// driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		// Go to URL
		driver.get(baseURL);
	}

	@AfterClass
	public static void afterClass() throws Exception {
		driver.get("about:blank");
		if (driver != null) {
			driver.quit();
		}
	}

	@After
	public void afterMethod() {
		driver.get("about:blank");
	}

	@AfterClass
	public static void afterTest() {
		// killProcess(browserDrivers.get(browser));
	}

	public void highlight(WebElement element) {
		highlight(element, 100);
	}

	public void highlight(WebElement element, long highlightInterval) {
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
			Thread.sleep(highlightInterval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
						.executeScript("arguments[0].style.border=''", element);
			}
		} catch (InterruptedException e) {
			// System.err.println("Exception (ignored): " + e.toString());
		}
	}

	public void highlight(By locator) throws InterruptedException {
		log.info("Highlighting element {}", locator);
		WebElement element = driver.findElement(locator);
		executeScript("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlightInterval);
		executeScript("arguments[0].style.border=''", element);
	}

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = BaseTest.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	public Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Utilities
	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}

	// https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
	public static void killProcess(String processName) {

		if (processName.isEmpty()) {
			return;
		}
		String command = String.format((osName.toLowerCase().startsWith("windows"))
				? "taskkill.exe /F /IM %s" : "killall %s", processName.trim());

		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command);
			// process.redirectErrorStream( true);

			BufferedReader stdoutBufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			BufferedReader stderrBufferedReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));
			String line = null;

			StringBuffer processOutput = new StringBuffer();
			while ((line = stdoutBufferedReader.readLine()) != null) {
				processOutput.append(line);
			}
			StringBuffer processError = new StringBuffer();
			while ((line = stderrBufferedReader.readLine()) != null) {
				processError.append(line);
			}
			int exitCode = process.waitFor();
			// ignore exit code 128: the process "<browser driver>" not found.
			if (exitCode != 0 && (exitCode ^ 128) != 0) {
				System.out.println("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					System.out.println("<OUTPUT>" + processOutput + "</OUTPUT>");
				}
				if (processError.length() > 0) {
					// e.g.
					// The process "chromedriver.exe"
					// with PID 5540 could not be terminated.
					// Reason: Access is denied.
					System.out.println("<ERROR>" + processError + "</ERROR>");
				}
			}
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.getMessage());
		}
	}

	protected String mouseMove(WebElement element) {
		return (String) executeScript(getScriptContent("mouseMove.js"), element);
	}

	protected String mouseDown(/* WebElement element */ ) {
		System.err.println("Script:" + getScriptContent("mouseDown.js"));
		/* return (String) executeScript(getScriptContent("mouseDown.js"),
				new Object[] {});
				*/
		return (String) executeScript(getScriptContent("mouseDown.js"));
	}

	protected String mouseUp(WebElement element) {
		return (String) executeScript(getScriptContent("mouseUp.js"), element);
	}

	protected String rightMouseButtonClick(WebElement element) {
		return (String) executeScript(getScriptContent("rightMouseButtonClick.js"),
				element);
	}

}
