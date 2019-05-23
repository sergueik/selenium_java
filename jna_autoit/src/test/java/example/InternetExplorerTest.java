package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

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
	private final static String baseURL = "https://file-examples.com/index.php/text-files-and-archives-download/";
	private final static String directURL = "https://file-examples.com/wp-content/uploads/2017/02/zip_9MB.zip";
	private String originalHandle;
	private StringBuffer verificationErrors = new StringBuffer();

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

	@Test(enabled = true)
	public void multiThreadTest() {
		try {
			App.main(new String[] { "dummy" });
		} catch (InterruptedException | MalformedURLException e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
	}

	@Test(enabled = false)
	public void testDirectDownload() {
		// System.err.println("Getting: " + directURL);
		// driver.get(directURL);
		System.err.println("Navigating to: " + directURL);
		driver.navigate().to(directURL);
		// hangs here:
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

	private static class App implements Runnable {
		public static WebDriver driver;
		private static Set<String> windowHandles;
		Thread thread;

		App() throws InterruptedException {
			thread = new Thread(this, "test");
			thread.start();
		}

		public void run() {
			String currentHandle = null;

			try {
				System.err.println("Thread: sleep 3 sec.");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println("Thread: wake.");
			// With modal window, WebDriver appears to be hanging on [get current
			// window handle]
			try {
				currentHandle = driver.getWindowHandle();
				System.err.println("Thread: Current Window handle" + currentHandle);
			} catch (NoSuchWindowException e) {

			}
			while (true) {
				try {
					System.err.println("Thread: wait .5 sec");
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread: inspecting all Window handles");
				// when a modal window is created by Javascript window.showModalDialog
				// WebDriver appears to be hanging on [get current window handle], [get
				// window handles]
				// Node console shows no Done: [get current window handle] or Done: [get
				// window handles]
				// if the window is closed manually, and cleater again, the problem goes
				// away
				windowHandles = driver.getWindowHandles();
				if (windowHandles.size() > 1) {
					System.err.println(
							"Found " + (windowHandles.size() - 1) + " additional Windows");
					break;
				} else {
					System.err.println("Thread: no other Windows");
				}
			}

			Iterator<String> windowHandleIterator = windowHandles.iterator();
			while (windowHandleIterator.hasNext()) {
				String handle = (String) windowHandleIterator.next();
				if (!handle.equals(currentHandle)) {
					System.err.println("Switch to " + handle);
					driver.switchTo().window(handle);
					// move, print attributes
					System.err.println("Switch to main window.");
					driver.switchTo().defaultContent();
				}
			}
			/*
			// the rest of example commented out
			String nextHandle = driver.getWindowHandle();
			System.out.println("nextHandle" + nextHandle);
			
			driver.findElement(By.xpath("//input[@type='button'][@value='Close']")).click();
			
			// Switch to main window
			for (String handle : driver.getWindowHandles()) {
			    driver.switchTo().window(handle);
			}
			// Accept alert
			driver.switchTo().alert().accept();
			*/
		}

		public static void main(String args[])
				throws InterruptedException, MalformedURLException {

			System.setProperty("webdriver.ie.driver",
					"c:/java/selenium/IEDriverServer.exe");
			driver = new InternetExplorerDriver();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			new App();
			// non-modal windows are handled successfully.
			// driver.get("http://www.naukri.com/");
			driver.get(
					"https://developer.mozilla.org/samples/domref/showModalDialog.html");
			// following two locator do not work with IE
			// driver.findElement(By.xpath("//input[@value='Open modal
			// dialog']")).click();
			// driver.findElement(By.cssSelector("input[type='button']")).click();
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.pollingEvery(500, TimeUnit.MILLISECONDS);
			Actions actions = new Actions(driver);

			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("html/body"))));

			WebElement body = driver.findElement(By.xpath("html/body"));
			body.findElement(By.xpath("input")).click();

			System.err.println("main: sleeping 10 sec");

			Thread.sleep(10000);
			System.err.println("main: closing the browser window");
			driver.close();
			System.err.println("main: quitting the browser");
			driver.quit();
		}

	}

}