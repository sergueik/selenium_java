package com.mycompany.app;

import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

// origin: https://github.com/TsvetomirSlavov/JavaScriptSwTestAcademy/blob/master/ExecuteAsyncXMLHttpRequestTest.java

//Ref1: https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/JavascriptExecutor.html
//Ref2: http://www.openjs.com/articles/ajax_xmlhttp_using_post.php

public class XMLHttpRequestAsyncTest {

	private static WebDriver driver;
	private WebDriverWait wait;
	private static Actions actions;
	private int flexibleWait = 5;
	private long pollingInterval = 500;
	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final String browser = "chrome";
	private static String osName;
	private static String baseURL = "http://phppot.com/demo/jquery-dependent-dropdown-list-countries-and-states/";
  
	@Test
	public void sendXMLHTTPRequestTest() {
		String response = null;

		// Set ScriptTimeout
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

		// Declare JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;
    int contry_index = 3;
    WebElement countryListElement = driver
				.findElement(By.xpath("//select[@id='country-list']"));
    try {
      Select select = new Select(countryListElement);
      select.selectByIndex(contry_index);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // selectDropdownByIndex(By.xpath("//select[@id='country-list']"), contry_index);
    // driver.findElement(By.xpath("//select[@id='country-list']")).click();
    countryListElement.click();
    sleep(100);

		// Injecting a XMLHttpRequest and waiting for the result
    // https://stackoverflow.com/questions/13452822/webdriver-executeasyncscript-vs-executescript
    // https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/JavascriptExecutor.html#executeAsyncScript-java.lang.String-java.lang.Object...-
    
    String inlineScript = "var callback = arguments[arguments.length - 1];\n" 
					+ "var option_index = (arguments.length > 1 )? arguments[arguments.length - 2]:1;\n"
			    +	"var http = new XMLHttpRequest();\n" 
					+ "var url = 'get_state.php';\n"
					+ "var params = 'country_id=' + option_index;\n"
					+	"http.open('POST', url, true);\n"
					+ "http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');\n"
					+ "http.setRequestHeader('Content-length', params.length);\n"
					+ "http.setRequestHeader('Connection', 'close');\n"
					+ "http.onreadystatechange = function() {\n"
          + "    if (http.readyState == 4) {\n"
          + "        callback(http.responseText);\n"
          + "    };\n" 
          + "};\n"
          + "http.send(params);";
 		try {
			response = (String ) js.executeAsyncScript( inlineScript , contry_index );
		} catch (UnhandledAlertException e) {
			System.err.println("Error executing XMLHttpRequest");
		}

		// Assert that returned cities are related with chosen country
		System.out.println(response);
        
		assertThat(response,
				org.hamcrest.CoreMatchers.containsString("<option value=\"13\">Picardie</option>"));
		try {
			
			// https://stackoverflow.com/questions/8923398/regex-doesnt-work-in-string-matches
			assertTrue(
					response.replace("\n", "").matches(".*<option value=\"\\d+\">Picardie</option>.*"));

		} catch (AssertionError e) {
			System.err.println("Exception (ignored) " + e.toString());
		}

		String stateName = "Picardie";
		Pattern pattern = Pattern.compile(String
				.format(".*<option value=\"(\\d+)\">%s</option>.*", stateName),
				Pattern.CASE_INSENSITIVE);
		// ".*<option value=\"(\\d+)\">([^<>]+)</option>.*",
		Matcher matcher = pattern.matcher(response);
		int stateIndex = 0;
		assertTrue(matcher.find());
		stateIndex = Integer.parseInt(matcher.group(1).toString());
		// stateName = matcher.group(2).toString();
		assertTrue(stateIndex > 0);
		assertThat(stateName, notNullValue());
		System.err.println("Selected state index: " + stateIndex);
		WebElement stateListElement = driver
				.findElement(By.xpath("//select[@id='state-list']"));
		try {
			Select select = new Select(stateListElement);
			select.selectByVisibleText(stateName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		stateListElement.click();
		sleep(100);
	}

  // common code
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

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

  public static void selectDropdownByIndex(By locator, int index) {
    try {
      Select select = new Select(driver.findElement(locator));
      select.selectByIndex(index);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	public static void selectDropdownByVisibleText(By locator, String text) {
		try {
			Select select = new Select(driver.findElement(locator));
			select.selectByVisibleText(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}