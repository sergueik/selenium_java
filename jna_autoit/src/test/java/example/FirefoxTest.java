package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class FirefoxTest {

	private String title = null;
	private String text = "";
	private String result = null;
	private AutoItX instance = null;
	private static final boolean debug = true;

	// for Selenium part of the test
	public int scriptTimeout = 5;
	public int flexibleWait = 60; // too long
	public int implicitWait = 1;
	public int pollingInterval = 500;
	@SuppressWarnings("unused")
	private static long highlightInterval = 100;
	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public JavascriptExecutor js;
	public TakesScreenshot screenshot;

	private final static String directURL = "https://file-examples.com/wp-content/uploads/2017/02/zip_9MB.zip";
	private final static String baseURL = "https://file-examples.com/index.php/text-files-and-archives-download/";
	// see also:
	// private final static String baseURL =
	// "http://spreadsheetpage.com/index.php/file/C35/P10/"
	// private final static String xpath =
	// String.format"//*[@id='content']//a[contains(text(), '%s')]", "xls");
	private final static String locator = "table#table-files a.btn";
	private static final int timeout = 2000;
	private static String fileType = "zip";
	private final static String xpath = String.format(
			"//table[@id='table-files']//a[contains(@class,'btn')][contains(text(), '%s')]",
			fileType.toUpperCase());
	private static List<WebElement> elements;
	private static WebElement element;
	private static String osName = getOSName();

	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void beforeMethod() throws IOException {
		instance = AutoItX.getInstance();

		System.setProperty("webdriver.gecko.driver", osName.equals("windows")
				? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
				: /* String.format("%s/Downloads/geckodriver", System.getenv("HOME"))*/
				Paths.get(System.getProperty("user.home")).resolve("Downloads")
						.resolve("geckodriver").toAbsolutePath().toString());
		System.setProperty("webdriver.firefox.bin",
				osName.equals("windows")
						// TODO: detect architecture
						? new File("c:/Program Files/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");
		// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile();
		capabilities.setCapability("marionette", false);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		try {
			driver = new FirefoxDriver(capabilities);
		} catch (WebDriverException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Cannot initialize Firefox driver: " + e.toString());
		}

		assertThat(driver, notNullValue());
		actions = new Actions(driver);

		driver.manage().timeouts().setScriptTimeout(scriptTimeout,
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, flexibleWait);

		wait.pollingEvery(Duration.ofMillis(pollingInterval));

		screenshot = ((TakesScreenshot) driver);
		js = ((JavascriptExecutor) driver);

	}

	// using arrow and tab keys to select "Download"
	// https://www.autoitscript.com/autoit3/docs/appendix/SendKeys.htm
	@Test(enabled = true)
	public void testDirectURLDownoadManagerDialog() {
		driver.navigate().to(directURL);
		sleep(timeout);
		title = "[ACTIVE]";
		String windowTitle = instance.WinGetTitle(title, text);
		assertThat(windowTitle, notNullValue());
		System.err.println(
				String.format("The active window title is \"%s\"", windowTitle));
		sleep(100);
		instance.Send("{DOWN}", true); // arrow key down
		sleep(100);
		instance.Send("{TAB}", true);
		sleep(100);
		instance.Send("{TAB}", true);
		sleep(100);
		instance.Send("{TAB}", true);
		sleep(100);
		instance.Send("{ENTER}", true);
		sleep(10000);
		result = instance.WinGetText(title, text);
		// NOTE: Mozilla Firefox Download Manager dialog button does not return any
		// text of interest
		assertThat(result, notNullValue());
		System.err.println(String.format("The result is \"%s\"", result));
		// Downloaded file will be found in the default location, that is
		// configurable through Mozilla Firefox profile e.g.
		// $env:USERPROFILE:/Downloads
		sleep(1000);

	}

	@Test(enabled = false)
	public void testButtonClickDownoadManagerDialog() {
		driver.navigate().to(baseURL);
		elements = driver.findElements(By.cssSelector(locator));
		// inventory
		elements.stream().map(e -> e.getText()).forEach(System.err::println);
		elements.stream().forEach(button -> {
			actions.moveToElement(button).build().perform();
			highlight(button, 500);
		});
		// click on a mime type in question
		element = driver.findElement(By.xpath(xpath));
		assertThat(element, notNullValue());
		element.click();
		sleep(timeout);
	}

	// Utilities
	private static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
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

	public void highlight(WebElement element) {
		highlight(element, 100, "solid yellow");
	}

	public void highlight(WebElement element, long highlightInterval) {
		highlight(element, highlightInterval, "solid yellow");

	}

	public void highlight(WebElement element, long highlightInterval,
			String color) {
		System.err.println("Color: " + color);
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		wait.pollingEvery(Duration.ofMillis(pollingInterval));
		try {
			// wait.until(ExpectedConditions.visibilityOf(element));
			executeScript(String.format("arguments[0].style.border='3px %s'", color),
					element);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Exception (ignored): " + e.toString());
		}
	}

	@AfterMethod
	public void afterMethod() {

		driver.get("about:blank");
		if (driver != null) {
			try {
				driver.close();
				driver.quit();
			} catch (Exception e) {
			}
		}

	}
}
