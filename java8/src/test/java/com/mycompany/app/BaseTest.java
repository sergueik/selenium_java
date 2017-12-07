package com.mycompany.app;

import static com.mycompany.app.BaseTest.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	protected static final Logger log = LogManager.getLogger(BaseTest.class);

	public int scriptTimeout = 5;
	public int flexibleWait = 120;
	public int implicitWait = 1;
	public long pollingInterval = 500;
	private static long highlightInterval = 100;

	public String baseURL = "about:blank";

	private List<String> chromeExtensions = new ArrayList<>();
	private static String osName = getOsName();

	private String extensionDir = String.format(
			"%s\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions",
			getPropertyEnv("USERPROFILE", "C:\\Users\\Serguei"));

	private static final String browser = getPropertyEnv("webdriver.driver",
			"chrome"); // "firefox";
	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome", "chromedriver.exe");
		browserDrivers.put("firefox", "geckodriver.exe");
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

	// without .crx extension
	public void addChromeExtension(String value) {
		this.chromeExtensions.add(value);
	}

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
					System.out.println(String.format(
							"Chrome extension successfully encoded and added: %s...",
							new String(base64EncodedByteArray).substring(0, 64)));
				} catch (FileNotFoundException e1) {
					System.out.println(
							"Chrome extension not found: " + extensionFilePath + " " + e1);
				} catch (IOException e2) {
					System.out.println("Problem with reading Chrome extension: " + e2);
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
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
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

			for (String optionAgrument : (new String[] {
					"--user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0",
					"--allow-running-insecure-content", "--allow-insecure-localhost",
					"--enable-local-file-accesses", "--disable-notifications",
					"--disable-save-password-bubble",
					/* "start-maximized" , */
					"--browser.download.folderList=2", "--disable-web-security",
					"--no-proxy-server",
					"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
					String.format("--browser.download.dir=%s", downloadFilepath)
					/* "--user-data-dir=/path/to/your/custom/profile"  , */

			})) {
				chromeOptions.addArguments(optionAgrument);
			}

			// chromeOptions.addArguments("user-data-dir=/path/to/your/custom/profile");
			// options for headless
			/*
			for (String optionAgrument : (new String[] { "headless",
					"window-size=1200x600", })) {
				options.addArguments(optionAgrument);
			}
			*/

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
	public void afterClass() throws Exception {
		driver.get("about:blank");
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		String methodName = method.getName();
		System.out.println("Test Name: " + methodName + "\n");
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

	protected void highlightNew(WebElement element, long highlightInterval) {
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
			Thread.sleep(highlightInterval);
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript(
						String.format("%s\nhighlight_remove();", highlightScript));
			}
		} catch (InterruptedException e) {
			// System.err.println("Ignored: " + e.toString());
		}

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
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'",
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
	public void scrolltoElement(WebElement element) {
		Coordinates coordinate = ((Locatable) element).getCoordinates();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.info("Element {} is visible", locator);
			return false;
		} catch (Exception e) {
			log.info("Element {} is not visible", locator);
			return true;
		}
	}

	protected String getBodyText() {
		log.info("Getting boby text");
		return driver.findElement(By.tagName("body")).getText();
	}

}
