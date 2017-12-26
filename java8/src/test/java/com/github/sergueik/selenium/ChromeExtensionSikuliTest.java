package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on
// https://stackoverflow.com/questions/25557533/open-a-chrome-extension-through-selenium-webdriver
// https://www.blazemeter.com/blog/6-easy-steps-testing-your-chrome-extension-selenium
// https://stackoverflow.com/questions/25557533/open-a-chrome-extension-through-selenium-webdriver
// http://www.software-testing-tutorials-automation.com/2016/05/how-to-get-browser-and-os-details-on.html
// http://screenster.io/running-tests-from-selenium-ide-in-chrome/ screenster
// http://screenster.io/running-tests-from-selenium-ide-in-chrome/ screenster
public class ChromeExtensionSikuliTest extends BaseTest {

	private String baseURL = "https://auth-demo.aerobatic.io/";
	private static List<String> chromeExtensions = new ArrayList<>();
	static {
		chromeExtensions.add("chropath"); // without .crx extension
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		for (String chromeExtention : chromeExtensions) {
			super.addChromeExtension(chromeExtention);
		}
		super.beforeClass();
		assertThat(driver, notNullValue());
		driver.get(baseURL);
	}

	@Test(priority = 1, enabled = false)
	public void openExtensionPopupTest() {

		Screen screen = new Screen();
		String imagePath = getResourcePath("chropath.png");
		try {
			screen.find(imagePath);
			screen.click(imagePath);
		} catch (FindFailed e) {
			e.printStackTrace();
		}

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for (String curWindow : allWindows) {
			if (!parentWindow.equals(curWindow)) {
				driver.switchTo().window(curWindow);
			}
		}
	}
}
