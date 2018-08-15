package com.github.sergueik.selenium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.Nullable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class BaseTest {

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public Alert alert;
	public JavascriptExecutor js;
	public TakesScreenshot screenshot;
	private static String handle = null;

	public String getHandle() {
		return handle;
	}

	private String parentHandle;

	public String getParentHandle() {
		return parentHandle;
	}

	protected static final Logger log = LogManager.getLogger(BaseTest.class);

	public int scriptTimeout = 5;
	public int flexibleWait = 60; // too long
	public int implicitWait = 1;
	public long pollingInterval = 500;
	private static long highlightInterval = 100;

	public String baseURL = "about:blank";

	private List<String> chromeExtensions = new ArrayList<>();
	private static String osName = getOSName();

	private String extensionDir = String.format(
			"%s\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions",
			getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei"));

	private static final String browser = getPropertyEnv("webdriver.driver",
			"chrome");
	private static final boolean headless = Boolean
			.parseBoolean(getPropertyEnv("HEADLESS", "false"));

	public static String getBrowser() {
		return browser;
	}

	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome",
				osName.contains("windows") ? "chromedriver.exe" : "chromedriver");
		browserDrivers.put("firefox",
				osName.contains("windows") ? "geckodriver.exe" : "geckodriver");
		browserDrivers.put("edge", "MicrosoftWebDriver.exe");
	}

	public void setExtensionDir(String value) {
		this.extensionDir = value;
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

	// WARNING: do not use @Before... or @AfterSuite otherwise the descendant test
	// class may fail
	@AfterSuite
	public void afterSuite() throws Exception {
	}

	// WARMING: do not define or the descendant test class will fail
	@BeforeSuite
	public void beforeSuite() {
	}

	// https://intoli.com/blog/firefox-extensions-with-selenium/
	// without .crx extension
	public void addChromeExtension(String value) {
		this.chromeExtensions.add(value);
	}

	// https://intoli.com/blog/chrome-extensions-with-selenium/
	// https://stackoverflow.com/questions/35858679/adding-extension-to-selenium2webdriver-chrome-driver
	// https://productforums.google.com/forum/#!topic/chrome/g02KlhK12fU
	// NOTE: simpler solution for local driver exist
	// https://sites.google.com/a/chromium.org/chromedriver/capabilities#TOC-List-of-recognized-capabilities
	// alternative:
	// options = webdriver.ChromeOptions()
	// options.add_argument("--app-id = mbopgmdnpcbohhpnfglgohlbhfongabi")
	private void loadChromeExtensionsBase64Encoded(ChromeOptions chromeOptions) {
		List<String> chromeExtensionsBase64Encoded = new ArrayList<>();
		for (String extensionName : this.chromeExtensions) {
			String extensionFilePath = this.extensionDir + "\\" + extensionName
					+ ".crx";
			// System.err.println("About to load extension " + extensionFilePath);
			File extensionFile = new File(extensionFilePath);

			// origin:
			// http://www.oodlestechnologies.com/blogs/Encode-%26-Decode-Image-Using-Base64-encoding-and-Decoding
			// http://www.java2s.com/Code/Java/File-Input-Output/Base64encodedecodedatausingtheBase64encodingscheme.htm
			if (extensionFile.exists() && !extensionFile.isDirectory()) {
				try {
					FileInputStream extensionFileInputStream = new FileInputStream(
							extensionFile);
					byte extensionData[] = new byte[(int) extensionFile.length()];
					extensionFileInputStream.read(extensionData);

					byte[] base64EncodedByteArray = Base64.encodeBase64(extensionData);

					extensionFileInputStream.close();
					chromeExtensionsBase64Encoded.add(new String(base64EncodedByteArray));
					System.err.println(String.format(
							"Chrome extension successfully encoded and added: %s...",
							new String(base64EncodedByteArray).substring(0, 64)));
				} catch (FileNotFoundException e1) {
					System.err.println(
							"Chrome extension not found: " + extensionFilePath + " " + e1);
				} catch (IOException e2) {
					System.err.println("Problem with reading Chrome extension: " + e2);
				}
			}
			chromeOptions.addEncodedExtensions(chromeExtensionsBase64Encoded);
		}
	}

	@SuppressWarnings("deprecation")
	@BeforeClass
	public void beforeClass() throws IOException {

		/*	 TODO: TripadvisorTest: observed user agent problem with firefox - mobile version of
				 page is rendered, and the toast message displayed with the warning:
				 "We noticed that you're using an unsupported browser. The TripAdvisor
		     website may not display properly.We support the following browsers:
				 Windows: Internet Explorer, Mozilla Firefox, Google Chrome. Mac:
				 Safari".
		*/
		System.err.println("Launching " + browser);
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", osName.contains("windows")
					? (new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath()
					: String.format("%s/Downloads/chromedriver", System.getenv("HOME")));

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions chromeOptions = new ChromeOptions();

			Map<String, Object> chromePrefs = new HashMap<>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String downloadFilepath = System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "target"
					+ System.getProperty("file.separator");
			chromePrefs.put("download.prompt_for_download", "false");
			chromePrefs.put("download.directory_upgrade", "true");
			chromePrefs.put("plugins.always_open_pdf_externally", "true");

			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("enableNetwork", "true");
			chromeOptions.setExperimentalOption("prefs", chromePrefs);
			// TODO: jni
			if (System.getProperty("os.arch").contains("64")) {
				String[] paths = new String[] {
						"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
						"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe" };
				// check file existence
				for (String path : paths) {
					File exe = new File(path);
					System.err.println("Inspecting browser path: " + path);
					if (exe.exists()) {
						chromeOptions.setBinary(path);
					}
				}
			} else {
				chromeOptions.setBinary(
						"c:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
			}
			for (String optionAgrument : (new String[] {
					"--user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0",
					"--allow-running-insecure-content", "--allow-insecure-localhost",
					"--enable-local-file-accesses", "--disable-notifications",
					"--disable-save-password-bubble",
					/* "start-maximized" , */
					"--disable-default-app", "disable-infobars", "--no-sandbox ",
					"--browser.download.folderList=2", "--disable-web-security",
					"--no-proxy-server",
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
					String.format("--browser.download.dir=%s", downloadFilepath)
					/* "--user-data-dir=/path/to/your/custom/profile"  , */

			})) {
				chromeOptions.addArguments(optionAgrument);
			}

			// options for headless
			if (headless) {
				for (String optionAgrument : (new String[] { "headless",
						"window-size=1200x800" })) {
					chromeOptions.addArguments(optionAgrument);
				}
			}

			capabilities
					.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
			capabilities.setCapability(
					org.openqa.selenium.chrome.ChromeOptions.CAPABILITY, chromeOptions);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			loadChromeExtensionsBase64Encoded(chromeOptions);
			// see also:
			// https://github.com/pulkitsinghal/selenium/blob/master/java/client/src/org/openqa/selenium/chrome/ChromeOptions.java
			// For use with RemoteWebDriver
			/*
			RemoteWebDriver driver = new RemoteWebDriver(
			   new URL("http://localhost:4444/wd/hub"), capabilities);
			*/
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("firefox"))

		{

			// https://developer.mozilla.org/en-US/Firefox/Headless_mode
			// 3.5.3 and later
			System.setProperty("webdriver.gecko.driver", osName.contains("windows")
					? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
					: String.format("%s/Downloads/geckodriver", System.getenv("HOME")));
			System
					.setProperty("webdriver.firefox.bin",
							osName.contains("windows") ? new File(
									"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
											.getAbsolutePath()
									: "/usr/bin/firefox");
			// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			// use legacy FirefoxDriver
			// for Firefox v.59 no longer possible ?
			// capabilities.setCapability("marionette", false);
			// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
			capabilities.setCapability("locationContextEnabled", false);
			capabilities.setCapability("acceptSslCerts", true);
			capabilities.setCapability("elementScrollBehavior", 1);
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/octet-stream,text/csv");
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			// TODO: cannot find symbol: method
			// addPreference(java.lang.String,java.lang.String)location: variable
			// profile of type org.openqa.selenium.firefox.FirefoxProfile
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			profile.setEnableNativeEvents(false);
			profile.setPreference("dom.webnotifications.enabled", false);
			// optional
			/*
			 profile.setPreference("general.useragent.override",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0");
			*/
			// System.err.println(System.getProperty("user.dir"));
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			try {
				driver = new FirefoxDriver(capabilities);
			} catch (WebDriverException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"Cannot initialize Firefox driver: " + e.toString());
			}
		}
		actions = new Actions(driver);

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
	public void afterClass() {
		driver.get("about:blank");
		if (driver != null) {
			try {
				driver.close();
				driver.quit();
			} catch (Exception e) {
				/*
				 * 		org.apache.commons.exec.ExecuteException: The stop timeout of 2000 ms was exceeded (Exit value: -559038737)
				...
				at org.openqa.selenium.os.OsProcess.destroy(OsProcess.java:135)
				at org.openqa.selenium.os.CommandLine.destroy(CommandLine.java:153)
				...
				 */
			}
		}
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		String methodName = method.getName();
		System.err.println("Test Name: " + methodName + "\n");
	}

	@AfterMethod
	public void afterMethod() {
		// driver.get("about:blank");
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		killProcess(browserDrivers.get(browser));
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
			executeScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=''", element);
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

	// based on https://groups.google.com/forum/#!topic/selenium-users/3J27G1GxCa8
	public void setAttribute(WebElement element, String attributeName,
			String attributeValue) {
		executeScript(
				"var element = arguments[0]; var attributeName = arguments[1]; var attributeValue = arguments[2]; element.setAttribute(attributeName, attributeValue)",
				element, attributeName, attributeValue);
	}

	protected void highlightNew(WebElement element, long highlightInterval) {
		Rectangle elementRect = element.getRect();
		String highlightScript = getScriptContent("highlight.js");
		// append calling

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript(
					String.format(
							"%s\nhighlight_create(arguments[0],arguments[1],arguments[2],arguments[3]);",
							highlightScript),
					elementRect.y, elementRect.x, elementRect.width, elementRect.height);
			Thread.sleep(highlightInterval);
			executeScript(String.format("%s\nhighlight_remove();", highlightScript));
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

	}

	// hover
	// https://stackoverflow.com/questions/11038638/simulate-hover-in-jquery
	public void jqueryHover(String cssSelector) {
		executeScript(
				"var selector = arguments[0]; $(selector).mouseenter().mouseleave();",
				cssSelector);
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

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 3; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgcolor, element);
		}
	}

	public void changeColor(String color, WebElement element) {
		executeScript("arguments[0].style.backgroundColor = '" + color + "'",
				element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
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
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
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
		System.err.println("Killing the process: " + processName);

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
				System.err.println("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					System.err.println("<OUTPUT>" + processOutput + "</OUTPUT>");
				}
				if (processError.length() > 0) {
					// e.g.
					// The process "chromedriver.exe"
					// with PID 5540 could not be terminated.
					// Reason: Access is denied.
					System.err.println("<ERROR>" + processError + "</ERROR>");
				}
			}
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.getMessage());
		}
	}

	public String getResourcePath(String resourceFileName) {
		return String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
	}

	// based on:
	// https://github.com/lopukhDA/Angular-tests-on-c-sharp-and-protractor/blob/master/NUnit.Tests1/WebDriver.cs
	public Boolean checkElementAttribute(WebElement element, String value,
			Optional<String> attributeOpt) {
		String attribute = attributeOpt.isPresent() ? attributeOpt.get() : "class";
		return (element.getAttribute(attribute).contains(value)) ? true : false;
	}

	public Boolean checkElementAttribute(WebElement element, String value,
			String... attributes) {
		String attribute = attributes.length > 0 ? attributes[0] : "class";
		return (element.getAttribute(attribute).contains(value)) ? true : false;
	}

	protected boolean areElementsPresent(WebElement parentWebElement,
			By byLocator) {
		return parentWebElement.findElements(byLocator).size() == 1;
		// usage:
		// assertTrue(areElementsPresent(driver.findElements(By.cssSelector("li[class*=
		// product]")).get(0), By.cssSelector("[class*=sticker]")));
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
	// https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/interactions/internal/Coordinates.html
	// https://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.interactions.internal.Coordinates
	public void scrolltoElement(WebElement element) {
		Coordinates coordinate = ((Locatable) element).getCoordinates();
		// coordinate.onScreen()
		coordinate.onPage();
		coordinate.inViewPort();
	}

	protected String cssSelectorOfElement(WebElement element) {
		return (String) executeScript(getScriptContent("cssSelectorOfElement.js"),
				element);
	}

	protected String styleOfElement(WebElement element, Object... arguments) {
		return (String) executeScript(getScriptContent("getStyle.js"), element,
				arguments);
	}

	protected String cssSelectorOfElementAlternative(WebElement element) {
		return (String) executeScript(
				getScriptContent("cssSelectorOfElementAlternative.js"), element);
	}

	protected String xpathOfElement(WebElement element) {
		return (String) executeScript(getScriptContent("xpathOfElement.js"),
				new Object[] { element });
	}

	protected boolean isElementNotVisible(By locator) {
		try {
			// disable implicit wait
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return false;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return true;
		} finally {
			driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		}
	}

	// based on:
	// https://testerslittlehelper.wordpress.com/2016/03/25/quick-find-element/
	protected boolean isElementPresent(By locator) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		List<WebElement> list = driver.findElements(locator);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		return list.size() > 0;
	}

	protected String getBodyText() {
		log.info("Getting boby text");
		return driver.findElement(By.tagName("body")).getText();
	}

	private static int instanceCount = 0;

	// based on
	// http://automated-testing.info/t/kak-ya-mogu-otkrit-v-firefox-dve-vkladki-i-perehodit-s-odnoj-na-vtoruyu-pri-neobhodimosti/1741/4
	// Creates a new window / browser tab using script injection
	// Loads a specified url there
	protected String createWindow(String url) {

		Set<String> oldHandles = driver.getWindowHandles();
		parentHandle = driver.getWindowHandle();

		// Inject an anchor element
		String name = "Window_" + instanceCount++;
		executeScript("var anchorTag = document.createElement('a'); "
				+ "anchorTag.appendChild(document.createTextNode('nwh'));"
				+ "anchorTag.setAttribute('id', arguments[0]);"
				+ "anchorTag.setAttribute('href', arguments[1]);"
				+ "anchorTag.setAttribute('target', '_blank');"
				+ "anchorTag.setAttribute('style', 'display:block;');"
				+ "var firstElement = document.getElementsByTagName('body')[0].getElementsByTagName('*')[0];"
				+ "firstElement.parentElement.appendChild(anchorTag);", name, url);
		// common error with this approach: Element is not clickable at point
		// HTML, HEAD, BODY, some element

		WebElement element = driver.findElement(By.id(name));
		sleep(1000);
		try {
			// element.getLocation()
			Point location = element.getLocation();
			System.err.println("Scrolling to " + location.y);
			scroll(location.x, location.y);
		} catch (UnsupportedCommandException e) {

		}
		scrolltoElement(element);
		// Click on the anchor element
		element.click();
		Set<String> newHandles = driver.getWindowHandles();

		newHandles.removeAll(oldHandles);
		// the remaining item is the new window handle
		for (String handle : newHandles) {
			return handle;
		}
		return null;
	}

	private void confirmHanldeNotClosed(String windowHandle) {
		if (windowHandle == null || windowHandle.equals("")) {
			throw new WebDriverException("Window/Tab was closed");
		}
	}

	protected void close(String windowHandle) {
		switchToWindow(windowHandle).close();
		handle = null;
		driver.switchTo().window(parentHandle);
	}

	protected WebDriver switchToWindow(String windowHandle) {
		confirmHanldeNotClosed(windowHandle);
		handle = windowHandle;
		return driver.switchTo().window(windowHandle);
	}

	protected WebDriver switchToParent() {
		confirmHanldeNotClosed(handle);
		return driver.switchTo().window(parentHandle);
	}

	protected String xPathToCSS(String xpath /*, @Nullable WebElement element*/) {
		String result = null;
		try {
			result = (String) executeScript(getScriptContent("cssify.js"),
					new Object[] { xpath });
		} catch (WebDriverException e) {
		}
		return result;
	}

	// origin: https://github.com/RomanIovlev/Css-to-XPath-Java
	// see also: https://github.com/featurist/css-to-xpath
	// for Convert XPath to CSS selector
	// hguiney / cssify.js
	// https://gist.github.com/hguiney/3320053
	//
	public static class CssToXPath {
		public static String cssToXPath(String css) {
			if (css == null || css.isEmpty())
				return "";
			int i = 0;
			int start;
			int length = css.length();
			String result = "//";
			while (i < length) {
				char symbol = css.charAt(i);
				if (isTagLetter(symbol)) {
					start = i;
					while (i < length && isTagLetter(css.charAt(i)))
						i++;
					if (i == length)
						return result + css.substring(start);
					result += css.substring(start, i);
					continue;
				}
				if (symbol == ' ') {
					result += "//";
					i++;
					continue;
				}
				if (Arrays.asList('.', '#', '[').contains(symbol)) {
					List<String> attributes = new ArrayList<>();
					while (i < length && css.charAt(i) != ' ') {
						switch (css.charAt(i)) {
						case '.':
							i++;
							start = i;
							while (i < length && isAttrLetter(css.charAt(i)))
								i++;
							attributes.add(convertToClass(i == length ? css.substring(start)
									: css.substring(start, i)));
							break;
						case '#':
							i++;
							start = i;
							while (i < length && isAttrLetter(css.charAt(i)))
								i++;
							attributes.add(convertToId(i == length ? css.substring(start)
									: css.substring(start, i)));
							break;
						case '[':
							i++;
							String attribute = "@";
							while (i < length
									&& (!Arrays.asList('=', ']').contains(css.charAt(i)))) {
								attribute += css.charAt(i);
								i++;
							}
							if (css.charAt(i) == '=') {
								attribute += "=";
								i++;
								if (css.charAt(i) != '\'')
									attribute += "'";
								while (i < length && css.charAt(i) != ']') {
									attribute += css.charAt(i);
									i++;
								}
								if (i == length)
									throw new RuntimeException("Incorrect Css. No ']' symbol");
								if (attribute.charAt(attribute.length() - 1) != '\'')
									attribute += "'";
							}
							attributes.add(attribute);
							i++;
							break;
						default:
							throw new RuntimeException(String.format(
									"Can't process Css. Unexpected symbol %s in attributes",
									css.charAt(i)));
						}
					}
					if (result.charAt(result.length() - 1) == '/')
						result += "*";
					result += "[" + String.join(" and ", attributes) + "]";
					continue;
				}
				throw new RuntimeException(
						String.format("Can't process Css. Unexpected symbol '%s'", symbol));
			}
			return result;
		}

		private static String convertToClass(String value) {
			return "contains(@class,'" + value + "')";
		}

		private static String convertToId(String value) {
			return convertToAtribute("id", value);
		}

		private static String convertToAtribute(String attr, String value) {
			return "@" + attr + "='" + value + "'";
		}

		private static boolean isAttrLetter(char symbol) {
			return symbol >= 'a' && symbol <= 'z' || symbol >= 'A' && symbol <= 'Z'
					|| symbol >= '0' && symbol <= '9' || symbol == '-' || symbol == '_'
					|| symbol == '.' || symbol == ':';
		}

		private static boolean isTagLetter(char symbol) {
			return symbol >= 'a' && symbol <= 'z';
		}

	}

	protected String getPageContent(String pagename) {
		try {
			URI uri = BaseTest.class.getClassLoader().getResource(pagename).toURI();
			System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) { // NOTE: multi-catch statement is not
																			// supported in -source 1.6
			throw new RuntimeException(e);
		}
	}

	// for the ancient versions of Selenium Webdriver when
	// ExpectedConditions alertIaPresent
	// not yet availabe (that is pre v2.20.0)
	public Alert getAlert(final long time) {
		return new WebDriverWait(driver, time, 200)
				.until(new ExpectedCondition<Alert>() {
					@Override
					public Alert apply(WebDriver d) {
						Alert alert = null;
						try {
							System.err.println("getAlert evaluating alert");
							alert = d.switchTo().alert();
							if (alert != null) {
								System.err.println("getAlert detected alert");
								return alert;
							} else {
								System.err.println("getAlert see no alert");
								return null;
							}
						} catch (NoAlertPresentException e) {
							System.err.println("getAlert see no alert");
							return null;
						}
					}
				});
	}

}
