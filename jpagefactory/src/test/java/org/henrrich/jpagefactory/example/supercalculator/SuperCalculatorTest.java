package org.henrrich.jpagefactory.example.supercalculator;

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
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by henrrich on 23/05/2016.
 */
public class SuperCalculatorTest {

	private NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	private final String baseUrl = "http://juliemr.github.io/protractor-demo/";
	private static final String chromeDriverPath = "${HOME}/Downloads/chromedriver";
	private static String osName = getOsName();

	// change to true to run on Chrome emulator
	private boolean isMobile = false;
	private final Channel channel = isMobile ? Channel.MOBILE : Channel.WEB;

	private SuperCalculatorPage superCalculatorPage;

	@Before
	public void setUp() throws Exception {

		// change according to platformm
		System.setProperty("webdriver.chrome.driver", osName.toLowerCase().startsWith("windows")
				? new File("c:/java/selenium/chromedriver.exe").getAbsolutePath() : resolveEnvVars(chromeDriverPath));

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
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
			/*
			 * DesiredCapabilities capabilities = new
			 * DesiredCapabilities("firefox", "", Platform.ANY); FirefoxProfile
			 * profile = new ProfilesIni().getProfile("default");
			 * profile.setEnableNativeEvents(false);
			 * capabilities.setCapability("firefox_profile", profile);
			 * seleniumDriver = new FirefoxDriver(capabilities);
			 */
			seleniumDriver = new ChromeDriver(capabilities);
			ngDriver = new NgWebDriver(seleniumDriver);
		}

		ngDriver.get(baseUrl);
		ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		superCalculatorPage = new SuperCalculatorPage();

		JPageFactory.initElements(ngDriver, channel, superCalculatorPage);
	}

	@Test
	public void testShouldAddOneAndTwo() throws Exception {
		superCalculatorPage.add("1", "2");
		Assert.assertTrue("Result is not 3!", superCalculatorPage.getLatestResult().equals("3"));
	}

	@Test
	public void testShouldTwoTimesThree() throws Exception {
		superCalculatorPage.times("2", "3");
		Assert.assertTrue("Result is not 6!", superCalculatorPage.getLatestResult().equals("6"));
	}

	@Test
	public void testShouldHaveAHistory() throws Exception {
		superCalculatorPage.add("1", "2");
		superCalculatorPage.add("3", "4");
		Assert.assertTrue("Should have 2 history!", superCalculatorPage.getNumberOfHistoryEntries() == 2);

		superCalculatorPage.add("5", "6");
		Assert.assertTrue("Should have 2 history!", superCalculatorPage.getNumberOfHistoryEntries() == 3);

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
			m.appendReplacement(sb, null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
