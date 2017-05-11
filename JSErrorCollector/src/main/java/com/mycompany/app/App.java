package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;

import net.jsourcerer.webdriver.jserrorcollector.*;

public class App {

	static WebDriver driver;
	private static String urlSimpleHtml = getResource("simple.html");
	private static JavaScriptError errorSimpleHtml = new JavaScriptError(
			"TypeError: null has no properties", urlSimpleHtml, 9, null);

	public static void main(String[] args)
			throws InterruptedException, java.io.IOException {
		List<JavaScriptError> expectedErrors = Arrays.asList(errorSimpleHtml);
		System.out.println(System.getProperty("user.dir"));
		File netExport = new File(
				System.getProperty("user.dir") + "/src/main/resources/firefox");

		FirefoxProfile profile = new FirefoxProfile();
		try {
			profile.addExtension(netExport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
		// http://www.atetric.com/atetric/javadoc/org.seleniumhq.selenium/selenium-firefox-driver/2.43.1/src-html/org/openqa/selenium/firefox/FirefoxProfile.html
		profile.setPreference("app.update.enabled", false);

		// Setting preferences
		// profile.setPreference("extensions.firebug.currentVersion", "2.0");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setBrowserName("firefox");
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);

		driver = new RemoteWebDriver(capabilities);

		navigate();
		List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
		System.err.println("Found " + jsErrors.size() + " errors.");
		Iterator<JavaScriptError> jsErrorsIterator = jsErrors.iterator();
		int cnt = 1;
		while (jsErrorsIterator.hasNext()) {
			JavaScriptError jsError = (JavaScriptError) jsErrorsIterator.next();
			System.err.format("%d: %s %s\n", cnt, jsError.getClass(),
					jsError.getErrorMessage());
			cnt++;
		}
		driver.close();
		driver.quit();

	}

	public static void navigate()
			throws InterruptedException, java.io.IOException {
		// Wait For Page To Load
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Go to URL
		driver.get("http://www.hollandamerica.com/");

		// Maximize Window
		driver.manage().window().maximize();

		// Wait For Page To Load
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		/*
		String value0 = "pnav-destinations";
		String title0  = "Destinations & Excursions - opens submenu";
		
		String css_selector0 = String.format("a#%s", value0);
		// Hover over menu
		WebElement element0 =  driver.findElement(By.cssSelector(css_selector0));
		Actions a1 = new Actions(driver);
		a1.moveToElement(element0).build().perform();
		// http://junit.sourceforge.net/javadoc/org/junit/Assert.html
		// assertEquals(200, response.getStatusLine().getStatusCode());
		assertTrue(String.format("Unexpected title '%s'", element0.getAttribute("title")), element0.getAttribute("title").matches(title0) );
		//take a screenshot
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		//save the screenshot in png format on the disk.
		FileUtils.copyFile(scrFile, new File(FilenameUtils.concat(currentDir, "screenshot.png")));
		//closing current driver window
		*/
		/*
		<script type="text/javascript">
		  window.onerror=function(msg){
		    var previous_errors = document.getElementsByTagName("body")[0].getAttribute("JSError");
		    $("body").attr("JSError",previous_errors + '\n' + msg );
		  }
		</script> 
		*/
	}

	private static String getResource(final String string) {
		String resource = App.class.getClassLoader().getResource(string)
				.toExternalForm();
		// non-static method getClass() cannot be referenced from a static contexts
		// String resource =
		// getClass().getClassLoader().getResource(string).toExternalForm();
		if (resource.startsWith("file:/") && !resource.startsWith("file:///")) {
			resource = "file://" + resource.substring(5);
		}
		return resource;
	}

	/**
	 * Adds the Firefox extension collecting JS errors to the profile what allows later use of {@link #readErrors(WebDriver)}.
	 * <p>
	 * Example:<br>
	 * <code><pre>
	 * final FirefoxProfile profile = new FirefoxProfile();
	 * JavaScriptError.addExtension(profile);
	 * final WebDriver driver = new FirefoxDriver(profile);
	 * </pre></code>
	 * @param ffProfile the Firefox profile to which the extension should be added.
	 * @throws IOException in case of problem
	 */
	public static void addExtension(final FirefoxProfile ffProfile)
			throws IOException {
		ffProfile.addExtension(JavaScriptError.class, "JSErrorCollector.xpi");
	}
}
