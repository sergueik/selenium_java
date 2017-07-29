import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.im4java.core.CommandException;
import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.process.StandardStream;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.Advapi32Util;

public class BaseTest {

	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;

	// JSWaiter object
	JSWaiter jsWaiter;

	// Test name
	public String testName;

	// Test Screenshot directory
	public String testScreenShotDirectory;

	// URL of the test website
	public String url = "http://www.kariyer.net";

	// Main Directory of the test code
	public String currentDir = System.getProperty("user.dir");

	// Main screenshot directory
	public String parentScreenShotsLocation = currentDir + "\\ScreenShots\\";

	// Main differences directory
	public String parentDifferencesLocation = currentDir + "\\Differences\\";

	// Element screenshot paths
	public String baselineScreenShotPath;
	public String actualScreenShotPath;
	public String differenceScreenShotPath;

	// Image files
	public File baselineImageFile;
	public File actualImageFile;
	public File differenceImageFile;
	public File differenceFileForParent;

	// Setup Driver
	@BeforeClass
	public void setupTestClass() throws IOException {
		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		driver = new ChromeDriver();

		// Maximize the browser
		driver.manage().window().maximize();

		// Declare a 10 seconds wait time
		wait = new WebDriverWait(driver, 10);

		// JS Executor
		js = (JavascriptExecutor) driver;

		// JSWaiter
		jsWaiter = new JSWaiter(wait);

		// Create screenshot and differences folders if they are not exist
		createFolder(parentScreenShotsLocation);
		createFolder(parentDifferencesLocation);

		// Clean Differences Root Folder
		File differencesFolder = new File(parentDifferencesLocation);
		FileUtils.cleanDirectory(differencesFolder);

		// Go to URL
		driver.get(url);

		// Add Cookie for top banner
		addCookieforTopBanner();
	}

	@BeforeMethod
	public void setupTestMethod(Method method) {
		// Get the test name to create a specific screenshot folder for each test.
		testName = method.getName();
		System.out.println("Test Name: " + testName + "\n");

		// Create a specific directory for a test
		testScreenShotDirectory = parentScreenShotsLocation + testName + "\\";
		createFolder(testScreenShotDirectory);

		// Declare element screenshot paths
		// Concatenate with the test name.
		declareScreenShotPaths(testName + "_Baseline.png", testName + "_Actual.png",
				testName + "_Diff.png");
	}

	// Add Cookie not to see top banner animation
	public void addCookieforTopBanner() {
		// Get Next Month Last Date for cookie expiration
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date nextMonthLastDay = calendar.getTime();

		// Create/Build a cookie
		Cookie topBannerCloseCookie = new Cookie.Builder("AA-kobiBannerClosed", "4") // Name
				.domain("www.kariyer.net") // Domain of the cookie
				.path("/") // Path of the cookie
				.expiresOn(nextMonthLastDay) // Expiration date
				.build(); // Finally build it with .build() call

		// Add a cookie
		driver.manage().addCookie(topBannerCloseCookie);
	}

	// Create Folder Method
	public void createFolder(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!");
			} else {
				System.out.println("Failed to create directory: " + path);
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
	}

	// Close popup if exists
	public void handlePopup(String selector) throws InterruptedException {
		jsWaiter.waitJS();
		List<WebElement> popup = driver.findElements(By.cssSelector(selector));
		if (!popup.isEmpty()) {
			popup.get(0).click();
			sleep(200);
		}
	}

	// Close Banner
	public void closeBanner() {
		jsWaiter.waitJS();
		List<WebElement> banner = driver
				.findElements(By.cssSelector("body > div.kobi-head-banner > div > a"));
		if (!banner.isEmpty()) {
			banner.get(0).click();
			// Wait for 2 second for closing banner
			sleep(2000);
		}
	}

	// Unhide an Element with JSExecutor
	public void unhideElement(String unhideJS) {
		js.executeScript(unhideJS);
		jsWaiter.waitJS();
		sleep(200);
	}

	// Move to Operation
	public void moveToElement(WebElement element) {
		jsWaiter.waitJS();
		Actions actions = new Actions(driver);
		jsWaiter.waitJS();
		sleep(200);
		actions.moveToElement(element).build().perform();
	}

	// Take Screenshot with AShot
	public Screenshot takeScreenshot(WebElement element) {
		// Take screenshot with Ashot
		Screenshot elementScreenShot = new AShot().takeScreenshot(driver, element);
		/*Screenshot elementScreenShot = new AShot()
		        .coordsProvider(new WebDriverCoordsProvider())
		        .takeScreenshot(driver,element);*/

		// Print element size
		String size = "Height: " + elementScreenShot.getImage().getHeight() + "\n"
				+ "Width: " + elementScreenShot.getImage().getWidth() + "\n";
		System.out.print("Size: " + size);

		return elementScreenShot;
	}

	// Write
	public void writeScreenshotToFolder(Screenshot screenshot)
			throws IOException {
		ImageIO.write(screenshot.getImage(), "PNG", actualImageFile);
	}

	// Screenshot paths
	public void declareScreenShotPaths(String baseline, String actual,
			String diff) {
		// BaseLine, Actual, Difference Photo Paths
		baselineScreenShotPath = testScreenShotDirectory + baseline;
		actualScreenShotPath = testScreenShotDirectory + actual;
		differenceScreenShotPath = testScreenShotDirectory + diff;

		// BaseLine, Actual Photo Files
		baselineImageFile = new File(baselineScreenShotPath);
		actualImageFile = new File(actualScreenShotPath);
		differenceImageFile = new File(differenceScreenShotPath);

		// For copying difference to the parent Difference Folder
		differenceFileForParent = new File(parentDifferencesLocation + diff);
	}

	public void resizeImagesWithImageMagick(String... pImageNames) throws Exception {
		ConvertCmd cmd = new ConvertCmd();
		cmd.setSearchPath("C:\\Program Files\\ImageMagick-7.0.6-Q16");
		IMOperation imOperation = new IMOperation();
		imOperation.addImage();
		imOperation.resize(200, 150);
		imOperation.addImage();
		for (String srcImage : pImageNames) {
			String dstImage = srcImage.substring(0, srcImage.lastIndexOf('.') - 1) + "_small.jpg";
			try {
				System.err.println(String.format("Resized image: '%s'", dstImage));
				cmd.run(imOperation, srcImage, dstImage);
			} catch (IOException | InterruptedException ex) {
				ex.printStackTrace();
        throw ex;
			} catch (IM4JavaException ex) {
        System.err.println("Exception (ignored): " + ex.getClass());
				ex.printStackTrace();
			}
		}
	}

	// ImageMagick Compare Method
	public void compareImagesWithImageMagick(String expected, String actual,
			String difference) throws Exception {
		ProcessStarter
				.setGlobalSearchPath("C:\\Program Files\\ImageMagick-7.0.6-Q16");

		CompareCmd compare = new CompareCmd();
		compare.setSearchPath("C:\\Program Files\\ImageMagick-7.0.6-Q16");

		// fix java.lang.NullPointerExceptionTests
		// compare.setErrorConsumer(StandardStream.STDERR);

		IMOperation imOperation = new IMOperation();
		imOperation.fuzz(5.0);

		// The special "-metric" setting of 'AE' (short for "Absolute Error" count),
		// will report (to standard error),
		// a count of the actual number of pixels that were masked, at the current
		// fuzz factor.
		imOperation.metric("AE");

		// Add the expected image
		imOperation.addImage(expected);

		// Add the actual image
		imOperation.addImage(actual);

		// This stores the difference
		imOperation.addImage(difference);

		String script = "myscript";
		try {
			System.out.println("Comparison Started");
			compare.createScript(script, imOperation);
			System.out.println("Comparison Script written to " + script);
			compare.run(imOperation);
		} catch (CommandException ex) {
			// ignore
			System.err.println("Exception (ignored):" + ex.getClass());
			System.err.print(ex);
		} catch (Exception ex) {
			System.err.println("Comparison Failed!");
			System.err.print(ex);
			throw ex;
		}
		// Put the difference image to the global differences folder
		Files.copy(differenceImageFile, differenceFileForParent);
	}

	// Compare Operation
	public void doComparison(Screenshot elementScreenShot) throws Exception {
		// Did we capture baseline image before?
		if (baselineImageFile.exists()) {
			// Compare screenshot with baseline
			System.out.println("Comparison method will be called!\n");

			System.out.println("Baseline: " + baselineScreenShotPath + "\n"
					+ "Actual: " + actualScreenShotPath + "\n" + "Diff: "
					+ differenceScreenShotPath);

			// Try to use IM4Java for comparison
			compareImagesWithImageMagick(baselineScreenShotPath, actualScreenShotPath,
					differenceScreenShotPath);
		} else {
			System.out.println(
					"BaselineScreenshot is not exist! We put it into test screenshot folder.\n");
			// Put the screenshot to the specified folder
			ImageIO.write(elementScreenShot.getImage(), "PNG", baselineImageFile);

			// Try to use IM4Java to resize
			resizeImagesWithImageMagick(baselineScreenShotPath);

		}
	}

	// Sleep Function
	public void sleep(int milis) {
		Long milliseconds = (long) milis;
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
