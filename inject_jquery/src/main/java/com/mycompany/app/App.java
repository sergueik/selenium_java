package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.StringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class App {

	static WebDriver driver;
	static WebDriverWait wait;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static boolean inject_local_script = false;

	static String url = "http://poshcode.org/"; // "http://demo.testfire.net/";

	public static void main(String[] args)
			throws InterruptedException, java.io.IOException {

		System.out.println(System.getProperty("user.dir"));
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("chrome");
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);

		driver = new RemoteWebDriver(capabilities);
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(120, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		driver.get(url);
		inject();
		driver.close();
		driver.quit();

	}

	public static void inject() throws InterruptedException, java.io.IOException {

/*
 		WebElement loginLink = driver.findElement(By.id("_ctl0__ctl0_LoginLink"));
		loginLink.click();
		WebElement userId = driver.findElement(By.id("uid"));
		userId.clear();
		userId.sendKeys("nobody");
		WebElement passwId = driver.findElement(By.id("passw"));
		passwId.clear();
		passwId.sendKeys("nobody");
*/
		String needJqueryInjectionScript = "return this.$ === undefined;";
		boolean needJqueryInjection = (Boolean) (executeScript(
				needJqueryInjectionScript));
		System.err.println("needJqueryInjection : " + needJqueryInjection);

		if (needJqueryInjection) {
			if (inject_local_script) {
				// TODO: resource loading not tested
				try {
					URI resourceURL = App.class.getClassLoader().getResource("jquery.js")
							.toURI();
					String jqueryScript = resourceURL.toString( /* Charsets.UTF_8 */);
					executeScript(jqueryScript);
				} catch (URISyntaxException e) {
					throw new RuntimeException(e);
				}
			} else { // inline
				String jqueryLoaderScript = "var jqueryScriptElement = document.createElement('script');"
						+ "jqueryScriptElement.src = 'http://code.jquery.com/jquery-latest.min.js';"
						+ "document.getElementsByTagName('head')[0].appendChild(jqueryScriptElement);";
				executeScript(jqueryLoaderScript);
				Thread.sleep(10000);
			}
		}
		// TODO: disable the page animations : $('body').append('<style> *
		// {transition: none!important;}</style>')
		String jqueryEnabledScript = "if (window.jQuery) {  return true } else { return false  } ";
		boolean jqueryEnabled = (Boolean) (executeScript(jqueryEnabledScript));
		assertTrue(jqueryEnabled);
		String buttonElementLocator = "input[value='Search']";
		StringBuilder sb = new StringBuilder();
		// Send all output to the Appendable object sb
		Formatter formatter = new Formatter(sb, Locale.US);
		String jQueryElementLocatorScript = formatter
				.format("return jQuery(\"%s\")", buttonElementLocator).toString();

		System.err.println("Running : " + jQueryElementLocatorScript);
		@SuppressWarnings("unchecked")
		List<Object> loginButtonObjects = (List<Object>) (executeScript(
				jQueryElementLocatorScript));
		assertThat(loginButtonObjects, notNullValue());
		assertTrue(loginButtonObjects.size() > 0);

		Iterator<Object> loginButtonObjectIterator = loginButtonObjects.iterator();
		int cnt = 0;
		WebElement loginButtonElement = null;
		while (loginButtonObjectIterator.hasNext()) {
			Object loginButtonObject = loginButtonObjectIterator.next();
			// [type="submit", value="Login", name="btnSubmit"]
			// [org.openqa.selenium.remote.RemoteWebElement@30 -> unknown locator]
			System.err.println("Returned (raw): " + loginButtonObject.toString());
			loginButtonElement = (WebElement) loginButtonObject;
			System.err.format("%s %s %s\n", loginButtonElement.getTagName(),
					loginButtonElement.getAttribute("value"),
					loginButtonElement.getAttribute("type"));
			if (loginButtonElement.getAttribute("value").equalsIgnoreCase("search")) {
				cnt = cnt + 1;
				// highlight(loginButtonElement);
			}
		}

		System.err.println("Search Button found " + cnt + " times");
		assertThat(loginButtonElement, notNullValue());
		loginButtonElement.click();
		try {
			// http://obscuredclarity.blogspot.com/2012/10/unit-testing-runtime-exceptions-with.html
			WebElement accountButtonElement = driver
					.findElement(By.id("btnGetAccount"));
			// does one need to be a JUnit method
			fail("Expected exception");
		} catch (NoSuchElementException e) {
			// pass - not being caught by this block
			assertTrue(e.getMessage().contains("Unable to locate element: ")); // {"method":"id","selector":"btnGetAccount"}
		} catch (Exception e) {
			assertThat(e,
					instanceOf(org.openqa.selenium.NoSuchElementException.class));
			// cannot find symbol
			// assertThat(e.getMessage(), containsString("Unable to locate element:
			// "));
			assertTrue(e.getMessage().contains("Unable to locate element: ")); // {"method":"id","selector":"btnGetAccount"}
			// System.err.println("Cause: " + e.getCause()); // Cause:
			// org.openqa.selenium.remote.ScreenshotException
			// System.err.println("Full stack trace:\n" +
			// org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e));
		}
	}

	private static Object executeAsyncScript(String script, Object... args) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			return javascriptExecutor.executeAsyncScript(script, args);
		} else {
			throw new RuntimeException("cannot execute script.");
		}
	}

	private static Object executeScript(String script, Object... args) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			return javascriptExecutor.executeScript(script, args);
		} else {
			throw new RuntimeException("cannot execute script.");
		}
	}
}
