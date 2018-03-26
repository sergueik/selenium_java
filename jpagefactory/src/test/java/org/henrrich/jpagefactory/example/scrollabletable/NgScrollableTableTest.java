package org.henrrich.jpagefactory.example.scrollabletable;

import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.henrrich.jpagefactory.Channel;
import org.henrrich.jpagefactory.JPageFactory;
import org.henrrich.jpagefactory.example.todolist.TodoListPage;

import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;
import com.github.sergueik.jprotractor.NgBy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

public class NgScrollableTableTest {
	private NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private static String baseUrl = "http://www.globalsqa.com/angularJs-protractor/Scrollable/";
	private static String osName = getOsName();

	// change to true to run on Chrome emulator
	private boolean isMobile = false;
	private final Channel channel = isMobile ? Channel.MOBILE : Channel.WEB;

	// strongly-typed Page object
	private NgScrollableTablePage page;

	@Before
	public void setUp() throws Exception {

		// change according to platform
		System.setProperty("webdriver.chrome.driver",
				"C:\\java\\selenium\\chromedriver.exe");

		if (isMobile) {
			Map<String, String> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", "Google Nexus 5");
			Map<String, Object> chromeOptions = new HashMap<>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			// set ignoreSynchronization to true to be able to handle the page sync by
			// ourselves instead of using waitForAngular call in JProtractor
			ngDriver = new NgWebDriver(new ChromeDriver(capabilities), true);
		} else {

			/*
			DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
					Platform.ANY);
			FirefoxProfile profile = new ProfilesIni().getProfile("default");
			profile.setEnableNativeEvents(false);
			capabilities.setCapability("firefox_profile", profile);
			seleniumDriver = new FirefoxDriver(capabilities);
			*/

			System.setProperty("webdriver.chrome.driver",
					osName.toLowerCase().startsWith("windows")
							? new File("c:/java/selenium/chromedriver.exe").getAbsolutePath()
							: "/var/run/chromedriver");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();

			seleniumDriver = new ChromeDriver(capabilities);
			ngDriver = new NgWebDriver(seleniumDriver, true);

		}

		ngDriver.get(baseUrl);
		ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		page = new NgScrollableTablePage();
		page.setDriver(ngDriver);

		JPageFactory.initElements(ngDriver, channel, page);

	}

	// @Ignore
	@Test
	public void testRowColumns() throws Exception {
		int expectNumRows = 20;
		Assert.assertThat(
				String.format("Should be able to find %d rows", expectNumRows),
				page.countRows(), equalTo(expectNumRows));
		Assert.assertThat(
				String.format("Should be able to find %d counting first names",
						expectNumRows),
				page.countFirstNames(), equalTo(expectNumRows));
		Assert.assertThat(
				String.format("Should be able to find %d counting first names",
						expectNumRows),
				page.countFirstNames(), equalTo(expectNumRows));
	}

	@Test
	public void testNames() throws Exception {
		int row = 1;
		Assert.assertThat("Should be named", page.getFullName(row), equalTo(
				String.format("%s %s", page.getFirstName(row), page.getLastName(row))));
	}

	@After
	public void tearDown() throws Exception {
		ngDriver.quit();
	}

	// Utilities
	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

}
