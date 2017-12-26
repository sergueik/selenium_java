package com.github.sergueik.selenium;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriverException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// based on:
// https://github.com/TsvetomirSlavov/DynamicDataTablesAndCallendarsTsvetomir/blob/master/src/main/java/DragAndDropKendoUIGrid.java
public class DragAndDropKendoUIGridTest {

	public static WebElement dragItem1;
	public static WebElement dragItem2;
	static WebDriver driver = null;

	@SuppressWarnings("deprecation")
	@BeforeSuite
	public void beforeSuiteMethod() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();

		Map<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("enableNetwork", "true");
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("allow-running-insecure-content");
		options.addArguments("allow-insecure-localhost");
		options.addArguments("enable-local-file-accesses");
		options.addArguments("disable-notifications");
		// options.addArguments("start-maximized");
		options.addArguments("browser.download.folderList=2");
		options.addArguments(
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
		options.addArguments("browser.download.dir=" + downloadFilepath);
		// options.addArguments("user-data-dir=/path/to/your/custom/profile");
		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capabilities);

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demos.telerik.com/kendo-ui/grid/index");
	}

	@BeforeMethod
	public void loadPage() {
	}

	@Test(priority = 1, enabled = true)
	public void dragDropTest() {
		driver.findElement(By.xpath("//a[contains(.,'Contact Name')]")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("$(\"#grid\").kendoGrid({\n" + "  columns: [\n"
				+ "    { field: \"name\" },\n" + "    { field: \"age\" }\n" + "  ],\n"
				+ "  dataSource: [\n" + "      { name: \"Ceko\", age: 30 },\n"
				+ "      { name: \"Zai\", age: 33 }\n" + "  ]\n" + "});\n"
				+ "var grid = $(\"#grid\").data(\"kendoGrid\");\n"
				+ "grid.reorderColumn(1, grid.columns[0]);");
		WebElement columnValue = driver.findElement(
				By.xpath("//*[@id='grid']/div[3]/table/tbody/tr[1]/td[2]"));
		String text = columnValue.getText();
		Assert.assertEquals("Ceko", text);

		dragItem1 = driver.findElement(By.linkText("Contact Name"));
		System.out.println(dragItem1.getText());
		dragItem2 = driver.findElement(By.className("k-grouping-header"));
		System.out.println(dragItem2.getText());
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dragItem1.click();
		Actions action = new Actions(driver);
		action.clickAndHold(dragItem1).moveToElement(dragItem2).release();
		action.build();
		action.perform();

		// elementToBeClickable(dragItem1);
		// dragAndDrop(driver, dragItem1, dragItem2);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	// Actions always first build() then perform() because only perform does not
	// work in Firefox
	public static void dragAndDrop(WebDriver driver, WebElement srcElement,
			WebElement dstElement) {

		Actions action = new Actions(driver);
		action.dragAndDrop(srcElement, dstElement);
		action.build();
		action.perform();
	}

	public static WebElement elementToBeClickable(final WebElement element) {
		return (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

}
