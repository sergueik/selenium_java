package org.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class ChromeHeadlessTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
	}

	@Before
	public void setupTest() {
		ChromeOptions options = new ChromeOptions();
		// Tested in Google Chrome 59 on Linux. More info on:
		// https://developers.google.com/web/updates/2017/04/headless-chrome
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		driver = new ChromeDriver(options);
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() {
		driver.get("https://en.wikipedia.org/wiki/Main_Page");
		String title = driver.getTitle();
		System.out.println(title);
	}

}
