package org.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

/**
 * Integration test of JSErrorReporter 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class AppTest {

	static WebDriver driver;
	static String baseUrl = "https://www.expedia.com/";
	// NOTE: "http://www.urbandictionary.com/" never finishes loading

	private static String urlSimpleHtml = getResource("simple.html");
	private static JavaScriptError errorSimpleHtml = new JavaScriptError(
			"TypeError: null has no properties", urlSimpleHtml, 9, null);
	private List<JavaScriptError> expectedErrors = Arrays.asList(errorSimpleHtml);

	@BeforeClass
	public static void setup() throws IOException {
		// System.out.println(System.getProperty("user.dir"));
		FirefoxProfile profile = new FirefoxProfile();
		try {
			profile.addExtension(new File(
					System.getProperty("user.dir") + "/src/main/resources/firefox"));
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
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Before
	public void beforeEach() {
		driver.navigate().to(baseUrl);
	}

	@Test
	public void testSliderKeyPress() {
		/*
		"http://www.hollandamerica.com/"
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

	@After
	public void afterTest() {

		List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
		System.err.println("Found " + jsErrors.size() + " errors.");
		Iterator<JavaScriptError> jsErrorsIterator = jsErrors.iterator();
		int cnt = 1;
		while (jsErrorsIterator.hasNext()) {
			JavaScriptError jsError = (JavaScriptError) jsErrorsIterator.next();
			System.err.format("%d: %s %s\n", cnt++, jsError.getClass(),
					jsError.getErrorMessage());
		}

	}

	@AfterClass
	public static void teardown() {
		driver.close();
		driver.quit();
	}

	private static String getResource(final String string) {
		String resource = App.class.getClassLoader().getResource(string)
				.toExternalForm();
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
	*/
	public static void addExtension(final FirefoxProfile profile)
			throws IOException {
		profile.addExtension(JavaScriptError.class, "JSErrorCollector.xpi");
	}
}