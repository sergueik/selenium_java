package com.github.sergueik.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.sergueik.tests.retry.RetryAnalyzer;
import com.github.sergueik.tests.retry.TestListener;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;

@Listeners(TestListener.class)

public class BasicTestWithRetry {
	public static int cnt_retries_1 = 0;
	public static int cnt_retries_2 = 0;

	private static WebDriver driver;
	private static String osName = getOSName();

	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.gecko.driver", osName.equals("windows")
				? new File("c:/java/selenium/geckodriver.exe").getAbsolutePath()
				: /* String.format("%s/Downloads/geckodriver", System.getenv("HOME"))*/
				Paths.get(System.getProperty("user.home")).resolve("Downloads")
						.resolve("geckodriver").toAbsolutePath().toString());
		System.setProperty("webdriver.firefox.bin",
				osName.equals("windows")
						? new File("c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
								.getAbsolutePath()
						: "/usr/bin/firefox");
		// https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", false);

		driver = new FirefoxDriver(capabilities);
		driver.get("http://www.last.fm/ru/");
	}

	@AfterClass
	public static void close() {
		driver.close();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void findLive() {
		driver.findElement(By.cssSelector("[href='/ru/dashboard']")).click();
		if (cnt_retries_1 < 1) {
			System.err.println("findLive failed");
			assertTrue(false);
		} else {
			System.err.println("findLive passed");
			assertTrue(true);
		}
		cnt_retries_1++;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void findMusic() {
		driver.findElement(By.cssSelector("[href='/ru/music']")).click();
		if (cnt_retries_2 < 2) {
			System.err.println("findMusic failed in run " + cnt_retries_2);
			assertTrue(false);
		} else {
			System.err.println("findMusic passed in run " + cnt_retries_2);
			assertTrue(true);
		}
		cnt_retries_2++;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void findEvents() {
		driver.findElement(By.cssSelector("[href='/ru/events']")).click();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void findFeatures() {
		driver.findElement(By.cssSelector("[href='ru/featuresERROR']")).click();
	}

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}
}
