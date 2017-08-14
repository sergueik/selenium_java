package org.utils;

import org.utils.ChromePagePerformanceObject;
import org.utils.ChromePagePerformanceUtil;

import org.junit.Test;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.Function;
import org.sqlite.SQLiteConnection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChromePagePerformanceTest {

	private static WebDriver driver;
	private static Connection conn;
	private static String osName;
  private static boolean headless = false;
  
	// private static String baseURL = "https://www.royalcaribbean.com/";
	// private static By elementSelector = By.id("find-a-cruise");

	// private static String baseURL = "https://www.expedia.com/";
	// private static By elementSelector = By.cssSelector(
	// "#tab-flight-tab-hp > span.icons-container" );

	private static String baseURL = "https://www.priceline.com/";
	private static By elementSelector = By.cssSelector(
			"#global-header-nav-section > ul > li.global-header-nav-product-item.global-header-nav-product-item-hotels > a");

	@BeforeClass
	public static void beforeClass() throws IOException {
		getOsName();

		System.setProperty("webdriver.chrome.driver",
				osName.toLowerCase().startsWith("windows")
						? new File("c:/java/selenium/chromedriver.exe").getAbsolutePath()
						: "/var/run/chromedriver");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

		chromePrefs.put("profile.default_content_settings.popups", 0);
		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("enableNetwork", "true");
		options.setExperimentalOption("prefs", chromePrefs);

		for (String optionAgrument : (new String[] {
				"allow-running-insecure-content", "allow-insecure-localhost",
				"enable-local-file-accesses", "disable-notifications",
				/* "start-maximized" , */
				"browser.download.folderList=2",
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
				String.format("browser.download.dir=%s", downloadFilepath)
				/* "user-data-dir=/path/to/your/custom/profile"  , */
		})) {
			options.addArguments(optionAgrument);
		}
		// options for headless
    if (headless) {
      
		if (osName.toLowerCase().startsWith("windows")) {

			for (String headlessOptionAgrument : (new String[] { "headless",
					"disable-gpu", "disable-plugins", "window-size=1200x600",
					"window-position=-9999,0" })) {
				options.addArguments(headlessOptionAgrument);
			}
			// on Windows need ChromeDriver 2.31 / Chrome 60 to support headless
			// With earlier versions of chromedriver: chrome not reachable...
			// https://developers.google.com/web/updates/2017/04/headless-chrome
			// https://stackoverflow.com/questions/43880619/headless-chrome-and-selenium-on-windows
		} else {
			for (String headlessOptionAgrument : (new String[] { "headless",
					"disable-gpu", "remote-debugging-port=9222",
					"window-size=1200x600" })) {
				options.addArguments(headlessOptionAgrument);
			}
		}
    }

		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capabilities);

		try {
			// origin:
			// https://github.com/xerial/sqlite-jdbc/blob/master/demo/Sample.java
			// http://www.codejava.net/java-se/jdbc/connect-to-sqlite-via-jdbc
			// http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
			// https://www.tutorialspoint.com/sqlite/sqlite_java.htm
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:product.db";
			conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				System.out.println("Connected to the database");
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out
						.println("Product version: " + dm.getDatabaseProductVersion());
				conn.close();
			}
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			// TODO: refactor after saving the data is implemented
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		assertThat(driver, notNullValue());
	}

	@Before
	public void beforeMethod() throws IOException {

		driver.get(baseURL);
		// Wait for page url to update
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.pollingEvery(500, TimeUnit.MILLISECONDS);
		ExpectedCondition<Boolean> urlChange = driver -> driver.getCurrentUrl()
				.matches(String.format("^%s.*", baseURL));
		wait.until(urlChange);
		System.err.println("Current  URL: " + driver.getCurrentUrl());
		/*
		// Take screenshot
		// under headless Chrome, some vendor pages behave differently e.g.
		// www.royalcaribbean.com redirects to the
		// "Oops... Looks like RoyalCaribbean.com is on vacation" page
		File screenShot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		
		// To get the width of image.
		BufferedImage readImage = ImageIO.read(screenShot);
		int width = readImage.getWidth();
		FileUtils.copyFile(screenShot, new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "test.png"));
		 */
	}

	@AfterClass
	public static void teardown() {
		driver.close();
		driver.quit();
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}


  @Ignore
	@Test
	public void testSetTimer() {
		double test = new ChromePagePerformanceObject(driver, baseURL,
				elementSelector).getLoadTime();
		System.out.println(test);
	}

	@Test
	public void testUtil() {
		ChromePagePerformanceUtil pageLoadTimer = ChromePagePerformanceUtil
				.getInstance();
		double test = pageLoadTimer.getLoadTime(driver, baseURL, elementSelector);
		System.out.println(test);
	}

	// Utilities
	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}
}