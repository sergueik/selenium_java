package org.henrrich.jpagefactory.example.multiselect;

import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;
import com.jprotractor.NgBy;

import org.henrrich.jpagefactory.Channel;
import org.henrrich.jpagefactory.JPageFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergueik on 31/07/2016.
 */
public class NgMultiSelectTest {

	private NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private String baseUrl;

	// change this boolean flag to true to run on chrome emulator
	private boolean isMobile = false;

	private NgMultiSelectPage page;

	@Before
	public void setUp() throws Exception {

		// change according to platformm
		System.setProperty("webdriver.chrome.driver",
				"C:\\java\\selenium\\chromedriver.exe");

		if (isMobile) {
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Google Nexus 5");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			// set ignoreSynchronization to true to be able to handle the page sync by
			// ourselves instead of using waitForAngular call in JProtractor
			ngDriver = new NgWebDriver(new ChromeDriver(capabilities), true);
		} else {

			DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "",
					Platform.ANY);
			FirefoxProfile profile = new ProfilesIni().getProfile("default");
			profile.setEnableNativeEvents(false);
			capabilities.setCapability("firefox_profile", profile);
			seleniumDriver = new FirefoxDriver(capabilities);
			ngDriver = new NgWebDriver(seleniumDriver, true);

			// ngDriver = new NgWebDriver(new ChromeDriver(), true);

		}

		baseUrl = baseUrl = "http://amitava82.github.io/angular-multiselect/";
		ngDriver.get(baseUrl);
		ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		page = new NgMultiSelectPage();
		page.setDriver(ngDriver);
		Channel channel = Channel.WEB;
		if (isMobile) {
			channel = Channel.MOBILE;
		}
		JPageFactory.initElements(ngDriver, channel, page);

	}

	// @Ignore
	@Test
	public void testSelectCarsOneByOne() throws Exception {
		page.openSelect();
		page.selectAllCars();
    System.err.println(page
				.getStatus());
		Assert.assertTrue("Should be able to select cars", page
				.getStatus().matches("There are (\\d+) car\\(s\\) selected"));
	}

	@After
	public void tearDown() throws Exception {
		ngDriver.quit();
	}

}
