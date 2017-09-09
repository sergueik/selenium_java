package org.utils;

import static org.testng.Assert.assertTrue;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import com.google.common.io.Files;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.im4java.core.CommandException;
import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public class VisualTest extends BaseTest {

	public String testName;
	public String testScreenShotDirectory;
	public String baseURL = "http://www.kariyer.net";
	public String parentScreenShotsLocation = System.getProperty("user.dir")
			+ "\\ScreenShots\\";

	// Main differences directory
	public String parentDifferencesLocation = System.getProperty("user.dir")
			+ "\\Differences\\";

	// Element screenshot paths
	public String baselinePath;
	public String screenshotPath;
	public String differencePath;

	public File baselineImageFile;
	public File actualImageFile;
	public File differenceImageFile;
	public File differenceFileForParent;

	private String imageMagickPath;

	@BeforeClass
	public void setupTestClass() throws IOException {

		// Determine path to ImageMagick
		imageMagickPath = Advapi32Util.registryGetStringValue(
				WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\ImageMagick\\Current", "BinPath");
		System.err.println("ImageMagick path: " + imageMagickPath);

		// Make sure the convert and compare are in the path
		assertTrue(new File(imageMagickPath + "\\" + "convert.exe").exists(),
				"\"convert.exe\" has to be present");
		assertTrue(new File(imageMagickPath + "\\" + "compare.exe").exists(),
				"\"compare.exe\" has to be present");

		// setup WebDriver
		super.setupTestClass();
		// Go to URL
		driver.get(baseURL);

		// Create screenshot and differences folders if they are not exist
		createFolder(parentScreenShotsLocation);
		createFolder(parentDifferencesLocation);
		// Clean Differences Root Folder
		File differencesFolder = new File(parentDifferencesLocation);
		FileUtils.cleanDirectory(differencesFolder);

		// Add Cookie for top banner
		addCookieforTopBanner();

	}

	@BeforeMethod
	public void setupTestMethod(Method method) {
		// Get the test name to create a specific screenshot folder for each test.
		testName = method.getName();
		System.out.println("Test Name: " + testName + "\n");

		testScreenShotDirectory = parentScreenShotsLocation + testName + "\\";
		createFolder(testScreenShotDirectory);

		// Declare element screenshot paths
		// Concatenate with the test name.
		declareScreenShotPaths(testName + "_Baseline.png", testName + "_Actual.png",
				testName + "_Diff.png");
	}

	@Test
	public void imageCompareTest() throws Exception {
		// Handle popup
		handlePopup(".ui-dialog-titlebar-close");

		// Close banner
		closeBanner();

		WebElement uzmanPhotoSection = driver
				.findElement(By.cssSelector(".item.uzman>a"));

		// Unhide Text which is changing A lot
		unhideElement(
				"document.getElementsByClassName('count')[0].style.display='none';");

		// Move To Operation
		moveToElement(uzmanPhotoSection);

		// Wait for 2 second for violet color animation
		Thread.sleep(2000);

		Screenshot screenShot = takeScreenshot(uzmanPhotoSection);

		writeScreenshotToFolder(screenShot);

		doComparison(screenShot);
	}

	@Test
	public void imageResizeTest() throws Exception {
		// Handle popup
		handlePopup(".ui-dialog-titlebar-close");

		// Close banner
		closeBanner();

		WebElement uzmanPhotoSection = driver
				.findElement(By.cssSelector(".item.uzman>a"));

		// Unhide Text which is changing A lot
		unhideElement(
				"document.getElementsByClassName('count')[0].style.display='none';");

		// Move To Operation
		moveToElement(uzmanPhotoSection);

		// Wait for 2 second for violet color animation
		Thread.sleep(2000);

		Screenshot screenShot = takeScreenshot(uzmanPhotoSection);

		takeScreenshotOfWebelement(driver, uzmanPhotoSection,
				testScreenShotDirectory + "\\" + "test.png");
		writeScreenshotToFolder(screenShot);

		// Resize
		resizeImagesWithImageMagick(screenshotPath);
	}

	// utils

	// Add Cookie to suppress top banner animation
	public void addCookieforTopBanner() {
		// Set cookie expiration to Next Month Last Date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date nextMonthLastDay = calendar.getTime();
		// provide name, domain, path, expiration date of the cookie
		Cookie topBannerCloseCookie = new Cookie.Builder("AA-kobiBannerClosed", "4") // Name
				.domain(baseURL.replaceAll("(?:https?://)([^/]+)(?:/.*)?$", "$1"))
				.path("/").expiresOn(nextMonthLastDay).build();

		driver.manage().addCookie(topBannerCloseCookie);
	}

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
		waitJS();
		List<WebElement> popup = driver.findElements(By.cssSelector(selector));
		if (!popup.isEmpty()) {
			popup.get(0).click();
			sleep(200);
		}
	}

	// Close Banner
	public void closeBanner() {
		waitJS();
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
		executeScript(unhideJS);
		waitJS();
	}

	// Move to Operation
	public void moveToElement(WebElement element) {
		waitJS();
		actions.moveToElement(element).build().perform();
	}

	// origin:
	// https://github.com/TsvetomirSlavov/JavaScriptForSeleniumMyCollection/blob/master/src/utils/UtilsQAAutoman.java
	public void takeScreenshotOfWebelement(WebDriver driver, WebElement element,
			String Destination) throws Exception {
		highlight(element);
		File v = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage bi = ImageIO.read(v);
		org.openqa.selenium.Point p = element.getLocation();
		int n = element.getSize().getWidth();
		int m = element.getSize().getHeight();
		BufferedImage d = bi.getSubimage(p.getX(), p.getY(), n, m);
		ImageIO.write(d, "png", v);

		FileUtils.copyFile(v, new File(Destination));
	}

	// using ashot
	public Screenshot takeScreenshot(WebElement element) {
		Screenshot elementScreenShot = new AShot().takeScreenshot(driver, element);
		/*Screenshot elementScreenShot = new AShot()
		        .coordsProvider(new WebDriverCoordsProvider())
		        .takeScreenshot(driver,element);*/

		System.out.println(String.format("Size: Height: %d Width: %d",
				elementScreenShot.getImage().getHeight(),
				elementScreenShot.getImage().getWidth()));

		return elementScreenShot;
	}

	public void writeScreenshotToFolder(Screenshot screenshot)
			throws IOException {
		ImageIO.write(screenshot.getImage(), "PNG", actualImageFile);
	}

	// Screenshot paths
	public void declareScreenShotPaths(String baseline, String actual,
			String diff) {
		// BaseLine, Actual, Difference Photo Paths
		baselinePath = testScreenShotDirectory + baseline;
		screenshotPath = testScreenShotDirectory + actual;
		differencePath = testScreenShotDirectory + diff;

		// BaseLine, Actual Photo Files
		baselineImageFile = new File(baselinePath);
		actualImageFile = new File(screenshotPath);
		differenceImageFile = new File(differencePath);

		// For copying difference to the parent Difference Folder
		differenceFileForParent = new File(parentDifferencesLocation + diff);
	}

	public void resizeImagesWithImageMagick(String... pImageNames)
			throws Exception {
		ConvertCmd cmd = new ConvertCmd();
		cmd.setSearchPath(imageMagickPath);
		IMOperation imOperation = new IMOperation();
		imOperation.addImage();
		imOperation.resize(200, 150);
		imOperation.addImage();
		for (String srcImage : pImageNames) {
			String dstImage = srcImage.substring(0, srcImage.lastIndexOf('.') - 1)
					+ "_small.jpg";
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

		ProcessStarter.setGlobalSearchPath(imageMagickPath);

		CompareCmd compare = new CompareCmd();
		compare.setSearchPath(imageMagickPath);

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

			System.out.println("Baseline: " + baselinePath + "\n" + "Actual: "
					+ screenshotPath + "\n" + "Diff: " + differencePath);

			// Try to use IM4Java for comparison
			compareImagesWithImageMagick(baselinePath, screenshotPath,
					differencePath);
		} else {
			System.out.println(
					"BaselineScreenshot is not exist! We put it into test screenshot folder.\n");
			// Put the screenshot to the specified folder
			ImageIO.write(elementScreenShot.getImage(), "PNG", baselineImageFile);

		}
	}

	public void sleep(int milis) {
		try {
			Thread.sleep((long) milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void waitJS() {
		// Wait for core Javascript to load
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString()
				.equals("complete");
		wait.until(jsLoad);

		// wait for JQuery
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
				.executeScript("return jQuery.active") == 0);
		wait.until(jQueryLoad);
	}

	private Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

}
