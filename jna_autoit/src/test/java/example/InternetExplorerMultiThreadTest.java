package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InternetExplorerMultiThreadTest {

	@SuppressWarnings("unused")
	private String title = null;
	@SuppressWarnings("unused")
	private String text = "";
	@SuppressWarnings("unused")
	private static final int timeout = 1000;
	@SuppressWarnings("unused")
	private String result = null;
	@SuppressWarnings("unused")
	private AutoItX instance = null;
	@SuppressWarnings("unused")
	private static final boolean debug = true;

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public JavascriptExecutor js;
	public TakesScreenshot screenshot;
	@SuppressWarnings("unused")
	private final static String baseURL = "https://file-examples.com/index.php/text-files-and-archives-download/";
	private final static String directURL = "https://file-examples.com/wp-content/uploads/2017/02/zip_9MB.zip";
	@SuppressWarnings("unused")
	private String originalHandle;
	@SuppressWarnings("unused")
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeMethod
	public void beforeMethod() throws Exception {
	}

	@Test(enabled = true)
	public void multiThreadTest() {
		try {
			App.main(new String[] { "dummy" });
		} catch (InterruptedException | MalformedURLException e) {
			System.err.println("Exception (ignored) " + e.toString());
		}
	}

	private static class App implements Runnable {
		private String title = null;
		private String text = "";
		@SuppressWarnings("unused")
		private static final int timeout = 1000;
		private String result = null;
		private AutoItX instance = null;
		private static final boolean debug = true;
		public static WebDriver driver;
		@SuppressWarnings("unused")
		private static Set<String> windowHandles;
		Thread thread;

		App() throws InterruptedException {
			thread = new Thread(this, "test");
			thread.start();
		}

		@SuppressWarnings("unused")
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
			instance = AutoItX.getInstance();

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
			// NOTE: Mozilla Firefox Download Manager dialog button does not return
			// any
			// text of interest
			assertThat(result, notNullValue());
			System.err.println(String.format("The result is \"%s\"", result));
			// Downloaded file will be found in the default location, that is
		}

		@SuppressWarnings("deprecation")
		public static void main(String args[])
				throws InterruptedException, MalformedURLException {

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

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			// launch second thread  to monitor download dialog option
			new App();
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.pollingEvery(500, TimeUnit.MILLISECONDS);
			Actions actions = new Actions(driver);

			System.err.println("Navigating to: " + directURL);
			driver.navigate().to(directURL);

			System.err.println("main: sleeping 10 sec");

			Thread.sleep(10000);
			System.err.println("main: closing the browser window");
			driver.close();
			System.err.println("main: quitting the browser");
			driver.quit();
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

}