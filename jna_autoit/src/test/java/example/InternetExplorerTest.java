package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InternetExplorerTest {

	private String title = null;
	private String text = "";
	private static final int timeout = 1000;
	private String result = null;
	private AutoItX instance = null;
	private static final boolean debug = true;

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public JavascriptExecutor js;
	public TakesScreenshot screenshot;
	@SuppressWarnings("unused")
	private final static String baseURL = "https://file-examples.com/index.php/text-files-and-archives-download/";
	private final static String directURL = "https://file-examples.com/wp-content/uploads/2017/02/zip_9MB.zip";
	private String originalHandle;
	@SuppressWarnings("unused")
	private StringBuffer verificationErrors = new StringBuffer();

	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void beforeMethod() throws Exception {
		instance = AutoItX.getInstance();
		System.setProperty("webdriver.ie.driver",
				"c:/java/selenium/IEDriverServer.exe");
		// Started InternetExplorerDriver server (32-bit) 2.42.0.0
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(
				InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);
		driver = new InternetExplorerDriver(capabilities);
		// org.openqa.selenium.WebDriverException: java.net.SocketException:
		// Software caused connection abort: recv failed
		// https://stackoverflow.com/questions/21330079/i-o-exception-and-unable-to-find-element-in-ie-using-selenium-webdriver/21373224
		// possibly caused by incorrect IE security settings or the lagging
		// iedriverserver.exe
		// installing 3.14.0 (32 bit) from https://www.seleniumhq.org/download/
		// resolves the issue
		// For IE Internet zones see https://github.com/allquixotic/iepmm (NOTE:
		// cryptic)

		originalHandle = driver.getWindowHandle();
		System.err.println("The current window handle " + originalHandle);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(enabled = false)
	public void testDirectDownload() {
		// System.err.println("Getting: " + directURL);
		// driver.get(directURL);
		System.err.println("Navigating to: " + directURL);
		driver.navigate().to(directURL);
		// IE and Java surefire hangs here:
		// Windows Internet Explorer
		// "What do you want to do with zip_9MB.zip" -
		// is a modal dialog.
		sleep(timeout);
		title = "[ACTIVE]";
		String windowTitle = instance.WinGetTitle(title, text);
		assertThat(windowTitle, notNullValue());
		System.err.println(
				String.format("The active window title is \"%s\"", windowTitle));
		sleep(100);
		instance.Send("{DOWN}", true); // arrow key down
		sleep(100);
		instance.Send("{ENTER}", true);
		sleep(10000);
		result = instance.WinGetText(title, text);
		// NOTE: Mozilla Firefox Download Manager dialog button does not return any
		// text of interest
		assertThat(result, notNullValue());
		System.err.println(String.format("The result is \"%s\"", result));
		// Downloaded file will be found in the default location, that is

	}

	@AfterMethod
	public void afterMethod() {

		if (debug) {
			System.err.println("navigating to blank page");
		}
		driver.get("about:blank");
		if (driver != null) {
			try {
				if (debug) {
					System.err.println("closing the browser window " + originalHandle);
				}
				driver.close();
				if (debug) {
					System.err.println("quitting the browser " + originalHandle);
				}
				driver.quit();
			} catch (Exception e) {
				System.err.println("Exception (ignored)" + e.toString());
			}
		}
	}

	public void sleep(Integer milliSeconds) {
		try {
			if (debug) {
				System.err.println("Sleeping " + milliSeconds + " milliseconds.");
			}
			Thread.sleep((long) milliSeconds);
		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}

	}
}
