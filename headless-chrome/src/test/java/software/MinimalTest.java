package software;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MinimalTest {

	public static WebDriver driver;
	private static String osName = getOsName();

	@BeforeClass
	public static void setup() throws IOException {
		getOsName();

		System.setProperty("webdriver.chrome.driver",
				osName.equals("windows")
						? (new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath()
						: "/home/vagrant/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.setBinary(osName.equals("windows") ? (new File(
				"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"))
						.getAbsolutePath()
				: "/usr/bin/google-chrome");
		options.addArguments("headless");
		options.addArguments("--window-size=320,1200");
		options.addArguments("disable-gpu");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

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
}