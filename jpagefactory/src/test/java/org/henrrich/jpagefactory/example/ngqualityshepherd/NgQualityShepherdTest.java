package org.henrrich.jpagefactory.example.ngqualityshepherd;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.henrrich.jpagefactory.Channel;
import org.henrrich.jpagefactory.JPageFactory;

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

/**
 * Created by sergueik on 31/07/2016.
 */
public class NgQualityShepherdTest {

	private NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private static final String baseUrl = "http://qualityshepherd.com/angular/friends/";
	private static String osName = getOsName();

	// change to true to run on Chrome emulator
	private boolean isMobile = false;
	private final Channel channel = isMobile ? Channel.MOBILE : Channel.WEB;

	// strongly-typed Page object
	private NgQualityShepherdPage page;

	@Before
	public void setUp() throws Exception {

		// change according to platformm
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

			// ngDriver = new NgWebDriver(new ChromeDriver(), true);

		}

		ngDriver.get(baseUrl);
		ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		page = new NgQualityShepherdPage();
		page.setDriver(ngDriver);

		JPageFactory.initElements(ngDriver, channel, page);
	}

	@Test
	public void testShouldCountFriendRows() throws Exception {
		assertThat("number of friends rows is incorrect", page.countFriendRows(),
				is(3));
	}

	// @Ignore
	@Test
	public void testShouldSearchFriend() throws Exception {
		assertThat("unexpected friend #2 name", page.getFriendName(2), is("Lucie"));
	}

	// can use repeaterColumn
	@Test
	public void testShouldHaveFriendNamedJohn() throws Exception {
		assertThat("unexpected friend #1 name", page.getFirstFriendName(),
				containsString("John"));
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
