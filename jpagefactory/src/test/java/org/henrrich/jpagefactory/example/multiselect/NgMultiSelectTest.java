package org.henrrich.jpagefactory.example.multiselect;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
// import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by sergueik on 31/07/2016.
 */
public class NgMultiSelectTest {

	private NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private static final String chromeDriverPath = "${HOME}/Downloads/chromedriver";
	private final String baseUrl = "http://amitava82.github.io/angular-multiselect/";;
	private static String osName = getOsName();

	// change to true to run on Chrome emulator
	private boolean isMobile = false;
	private final Channel channel = isMobile ? Channel.MOBILE : Channel.WEB;

	// strongly-typed Page object
	private NgMultiSelectPage page;

	@Before
	public void setUp() throws Exception {

		// change according to platform
		// NOTE: 
		// java.lang.IllegalStateException: The driver executable does not exist: /var/run/chromedriver
		// java.lang.IllegalStateException: The driver is not executable: /var/run/chromedriver

		System.setProperty("webdriver.chrome.driver", osName.toLowerCase().startsWith("windows")
				? new File("c:/java/selenium/chromedriver.exe").getAbsolutePath() : resolveEnvVars(chromeDriverPath));

    // DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("chrome");

		if (isMobile) {
			Map<String, String> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", "Google Nexus 5");
			Map<String, Object> chromeOptions = new HashMap<>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			seleniumDriver = new ChromeDriver(capabilities);
			// set ignoreSynchronization to true to handle page sync ourselves
			// instead of using waitForAngular call in JProtractor
			ngDriver = new NgWebDriver(seleniumDriver, true);
		} else {
			seleniumDriver = new ChromeDriver(capabilities);
			ngDriver = new NgWebDriver(seleniumDriver);
		}

		ngDriver.get(baseUrl);
		ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		page = new NgMultiSelectPage();
		page.setDriver(ngDriver);

		JPageFactory.initElements(ngDriver, channel, page);

	}

	// @Ignore
	@Test
	public void testSelectCarsOneByOne() throws Exception {
		page.openSelect();
		page.selectAllCars();
		System.err.println(page.getStatus());
		Assert.assertTrue("Should be able to select cars",
				page.getStatus().matches("There are (\\d+) car\\(s\\) selected"));
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
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}
	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern p = Pattern.compile("\\$(?:\\{([\\.\\w]+)\\}|(\\w+))");
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
			String envVarValue = getPropertyEnv(envVarName, "");
			m.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
