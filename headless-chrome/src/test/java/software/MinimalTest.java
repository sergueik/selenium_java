import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MinimalTest {

	public static WebDriver driver;

	@BeforeClass
	public static void setup() throws IOException {
		System.setProperty("webdriver.chrome.driver", "/home/vagrant/chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.setBinary("/usr/bin/google-chrome");
		options.addArguments("headless");
		options.addArguments("disable-gpu");
		driver = new ChromeDriver(options);
	}
// // [WARNING] No processor claimed any of these annotations: org.junit.BeforeClass,org.junit.Test,org.junit.AfterClass
	@Test
	public void Test() {
		driver.navigate().to("https://www.baidu.com/");
		Assert.assertEquals(driver.findElement(By.id("su")).getAttribute("Value"),
				"百度一下");

	}

	@AfterClass
	public static void teardown() {
		driver.quit();
	}
}
