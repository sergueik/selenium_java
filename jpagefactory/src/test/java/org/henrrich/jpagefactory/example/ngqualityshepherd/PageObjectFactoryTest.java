package org.henrrich.jpagefactory.example.ngqualityshepherd;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.henrrich.jpagefactory.Channel;
import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.JPageFactory;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
// NOTE: The import org.openqa.selenium.support.FindAll collides with another import statement
// import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.PageFactory;

import com.github.sergueik.jprotractor.NgWebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageObjectFactoryTest {
	WebDriver driver;
	private NgWebDriver ngDriver;
	private static final String baseUrl = "http://qualityshepherd.com/angular/friends/";
	private static final String chromeDriverPath = "${HOME}/Downloads/chromedriver";
	private static String osName = getOsName();

	// change to true to run on Chrome emulator
	private boolean isMobile = false;
	private final Channel channel = isMobile ? Channel.MOBILE : Channel.WEB;

	@Before
	public void setUp() {
		// Create a new instance of a driver
		System.setProperty("webdriver.chrome.driver", osName.toLowerCase().startsWith("windows")
				? new File("c:/java/selenium/chromedriver.exe").getAbsolutePath() : resolveEnvVars(chromeDriverPath));
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		ngDriver = new NgWebDriver(driver);
		// Navigate to the page
		ngDriver.get(baseUrl);
		ngDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		// Close the browser
		ngDriver.quit();
		// driver.quit();
	}

	@Test
	public void shouldAlsoCountFriends() {
		// Create a new core org.openqa.selenium.support strongly typed
		// instance of the search page class
		// with regular WebElement fields in the page.
		QualityshepherdPage page = new QualityshepherdPage();
		page.setDriver(ngDriver);

		JPageFactory.initElements(ngDriver, channel, page);

		// And could the friends.
		assertThat("it will have more tnan 2 friends", page.countNgFriends(), greaterThan(2));
	}

	@Test
	public void shouldCountFriends() {
		// Create a new core org.openqa.selenium.support strongly typed
		// instance of the search page class
		// with regular WebElement fields in the page.
		QualityshepherdPage page = PageFactory.initElements(driver, QualityshepherdPage.class);
		// And could the friends.
		assertThat("it will have more tnan 2 friends", page.countFriends(), greaterThan(2));
	}

	public static class QualityshepherdPage {

		private NgWebDriver _driver;

		public void setDriver(NgWebDriver driver) {
			this._driver = driver;
		}

		// https://stackoverflow.com/questions/28243653/webdriver-pagefactory-find-elements-list
		@FindAll({ @FindBy(how = How.REPEATER, using = "row in rows") })
		public List<WebElement> ngFriends;

		@org.openqa.selenium.support.FindAll({ @org.openqa.selenium.support.FindBy(css = "table tbody td.ng-binding") })
		public List<WebElement> friends;

		public int countFriends() {
			return friends.size();
		}

		public int countNgFriends() {
			return ngFriends.size();
		}

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
