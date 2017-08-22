package software;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MinimalTest {

	public static WebDriver driver;
	private static String osName;

	@BeforeClass
	public static void setup() throws IOException {
		getOsName();

		System.setProperty("webdriver.chrome.driver",
				osName.toLowerCase().startsWith("windows")
						? (new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath()
						: "/home/vagrant/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.setBinary(osName.toLowerCase().startsWith("windows") ? (new File(
				"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"))
						.getAbsolutePath()
				: "/usr/bin/google-chrome");
		options.addArguments("headless");
		options.addArguments("disable-gpu");
		driver = new ChromeDriver(options);
	}

	// [WARNING] No processor claimed any of these annotations:
	// org.junit.BeforeClass,org.junit.Test,org.junit.AfterClass
	@Ignore
	@Test
	public void Test() {
		driver.navigate().to("https://ya.ru/");
		Assert.assertEquals(driver
				.findElement(
						By.cssSelector("div.search2__button > button > span.button__text"))
				.getText(), "Найти");
	}

	@Ignore
	@Test
	public void test2Element() throws Exception {

		driver.get("https://ya.ru/");
		final String text = driver
				.findElements(
						By.cssSelector("div.search2__button > button > span.button__text"))
				.get(0).getAttribute("outerHTML");
		System.err.println("Text: " + text);
		assertThat(text, equals("Найти")); // fails
	}

	@AfterClass
	public static void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// Utilities
	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}
}
