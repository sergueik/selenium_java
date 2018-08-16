package example;

import com.mozilla.example.PropertiesParser;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxBinary;
// import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class MinimalTest {

	public static FirefoxDriver driver;
	private static String osName = getOsName();
	static Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome", "chromeDriverPath");
		browserDrivers.put("firefox", "geckoDriverPath");
		browserDrivers.put("edge", "edgeDriverPath");
		browserDrivers.put("safari", null);
	}
	// application configuration file
	private static String propertiesFileName = "application.properties";
	private static Map<String, String> propertiesMap = new HashMap<>();

	@BeforeClass
	public static void setup() throws IOException {
		getOsName();

		propertiesMap = PropertiesParser
				.getProperties(String.format("%s/src/main/resources/%s",
						System.getProperty("user.dir"), propertiesFileName));

		// https://www.programcreek.com/java-api-examples/?api=org.openqa.selenium.firefox.FirefoxBinary
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		FirefoxBinary firefoxBinary = new FirefoxBinary();
		/*
		 * FirefoxBrowserProfile firefoxBrowserProfile = new
		 * FirefoxBrowserProfile(); String sProfile =
		 * firefoxBrowserProfile.getDefaultProfile(); try { firefoxBinary = new
		 * FirefoxBinary(new
		 * File(firefoxBrowserProfile.getFirefoxBinInstallPath())); } catch
		 * (Exception e) { }
		 */
		// TODO: convert to command line parameter

		/*
		firefoxBinary.addCommandLineOptions("--headless");
		// size argument appears to be ignored
		firefoxBinary.addCommandLineOptions("--window-size=320,200");
		*/
		String browserDriver = (propertiesMap
				.get(browserDrivers.get("browser")) != null)
						? propertiesMap.get(browserDrivers.get("browser")) :
						// assuming browser is firefox
						osName.equals("windows")
								? (new File("c:/java/selenium/geckodriver.exe"))
										.getAbsolutePath()
								: "/home/sergueik/Downloads/geckodriver";
		System.setProperty("webdriver.gecko.driver", browserDriver);
		// FirefoxOptions firefoxOptions = new FirefoxOptions();
		// firefoxOptions.setBinary(firefoxBinary);
		// driver = new FirefoxDriver(firefoxOptions);
		driver = new FirefoxDriver();
		// dynamicSearchButtonTest
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

	@Ignore
	@Test
	public void testChromeDriver() throws Exception {
		assertThat(driver, notNullValue());
	}

	@Ignore
	@Test
	public void dynamicSearchButtonTest() {
		try {
			driver.get("http://www.google.com");
			WebElement queryBox = driver.findElement(By.name("q"));
			queryBox.sendKeys("headless firefox");
			WebElement searchButtonnStatic = driver.findElement(By.name("btnK"));

			// if the script performing Google search is running slowly
			// enough search suggestions are found and the dropdown is pupulated
			// and hides the original search button
			// the page renders a new search button inside the dropdown
			WebElement searchButtonnDynamic = driver.findElement(By.cssSelector(
					"span.ds:nth-child(1) > span.lsbb:nth-child(1) > input.lsb"));
			if (searchButtonnDynamic != null) {
				System.err.println("clicking the dynamic search button");
				searchButtonnDynamic.click();
			} else {
				System.err.println("clicking the static search button");
				searchButtonnStatic.click();
			}
			WebElement iresDiv = driver.findElement(By.id("ires"));
			iresDiv.findElements(By.tagName("a")).get(0).click();
			System.err.println(
					"Response: " + driver.getPageSource().substring(0, 120) + "...");
		} catch (WebDriverException e) {
			System.err.println("Excepion (ignored) " + e.toString());
			// Without using dynamic search button,
			// approximately 1/3 (in headless mode, at least )
			// of the test runs result in exception
			// Element <input name="btnK" type="submit"> is not clickable at
			// point (607,411) because another element <div class="sbqs_c">
			// obscures it (the name of obscuring element varies)
			try {
				// take screenshot in catch block.
				System.err.println("Taking a screenshot");
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String currentDir = System.getProperty("user.dir");
				FileUtils.copyFile(scrFile,
						new File(FilenameUtils.concat(currentDir, "screenshot.png")));
			} catch (IOException ex) {
				System.err.println(
						"Excepion when taking the screenshot (ignored) " + ex.toString());
				// ignore
			}
		}
	}

	@Ignore
	@Test
	public void Test() {
		driver.navigate().to("https://ya.ru/");
		WebElement element = driver
				.findElements(
						By.cssSelector("div.search2__button > button > span.button__text"))
				.get(0);
		final String text = element.getAttribute("outerHTML");
		System.err.println("Text: " + text);
		Assert.assertEquals(element.getText(), "Найти");
		assertThat(element.getText(), containsString("Найти")); // quotes
	}

	@Test
	public void selectControlReloadPageTest() {
		String baseURL = "http://the-internet.herokuapp.com/context_menu";

		driver.get(baseURL);
		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(By.xpath("//*[@id=\"hot-spot\"]"));
		actions.contextClick(element);
		actions.build().perform();
		sleep(1000);
		actions.sendKeys(Keys.ARROW_DOWN);
		actions.build().perform();
		sleep(1000);

		actions.sendKeys(Keys.ARROW_DOWN);
		actions.build().perform();
		sleep(1000);

		actions.sendKeys(Keys.ARROW_DOWN);
		actions.build().perform();
		sleep(1000);
		actions.sendKeys(Keys.ARROW_DOWN);
		actions.build().perform();
		sleep(1000);
		actions.sendKeys(Keys.ENTER);
		actions.build().perform();
		sleep(1000);

	}

	@AfterClass
	public static void teardown() {
		if (driver != null) {
			try {
				// take screenshot in teardown.
				System.err.println("Taking a screenshot");
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String currentDir = System.getProperty("user.dir");
				FileUtils.copyFile(scrFile,
						new File(FilenameUtils.concat(currentDir, "screenshot.png")));
			} catch (IOException ex) {
				System.err.println(
						"Excepion when taking the screenshot (ignored) " + ex.toString());
				// ignore
			}
			driver.quit();
		}
	}

	// Utilities
	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
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
}
