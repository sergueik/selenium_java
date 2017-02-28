package com.mycompany.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

// import com.jprotractor.NgBy;
// import com.jprotractor.NgWebDriver;
// import com.jprotractor.NgWebElement;

public class BrowserDriver {

	public static WebDriver driver;
	private static String location = "";
	private static String chromeDriverPath = "c:/java/selenium/chromedriver.exe";
	private static String iEDriverPath = "c:/java/selenium/IEDriverServer.exe";
	// private static String geckoDriverDriverPath;

	public static WebDriver initialize(String browser) {

		DesiredCapabilities capabilities = null;
		browser = browser.toLowerCase();
		if (browser.equals("firefox")) {
			capabilities = capabilitiesFirefox();
		} else if (browser.equals("phantomjs")) {
			capabilities = capabilitiesPhantomJS();
		} else if (browser.equals("chrome")) {
			capabilities = capabilitiesChrome();
		} else if (browser.equals("ie")) {
			capabilities = capabilitiesInternetExplorer();
		} else if (browser.equals("android")) {
			capabilities = capabilitiesAndroid();
		} else if (browser.equals("safari")) {
			capabilities = capabilitiesSafari();
		} else if (browser.equals("iphone")) {
			capabilities = capabilitiesiPhone();
		} else if (browser.equals("ipad")) {
			capabilities = capabilitiesiPad();
		}

		if (location.toLowerCase().contains("http:")) {
			try {
				// log.info("Running on Selenium Grid: " + location);
				driver = new RemoteWebDriver(new URL(location), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		} else if (browser.equals("firefox")) {
			driver = new FirefoxDriver(capabilities);
		} else if (browser.equals("phantomjs")) {
			driver = new PhantomJSDriver(capabilities);
		} else if (browser.equals("safari")) {
			SafariOptions options = new SafariOptions();
			driver = new SafariDriver(options);
		} else if (browser.equals("chrome")) {
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("iexplore")) {
			File file = new File(iEDriverPath);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			driver = new InternetExplorerDriver(capabilities);
		} else if (browser.equals("android")) {
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("iphone")) {
			driver = new ChromeDriver(capabilities);
		} else if (browser.equals("ipad")) {
			driver = new ChromeDriver(capabilities);
		}
		// ngDriver = new NgWebDriver(driver);
		return driver;
	}

	private static DesiredCapabilities capabilitiesSafari() {
		DesiredCapabilities capabilities = DesiredCapabilities.safari();
		SafariOptions options = new SafariOptions();
		options.setUseCleanSession(true);
		capabilities.setCapability(SafariOptions.CAPABILITY, options);
		return capabilities;
	}

	private static DesiredCapabilities capabilitiesPhantomJS() {

		DesiredCapabilities capabilities = new DesiredCapabilities("phantomjs", "",
				Platform.ANY);
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
				new String[] { "--web-security=false", "--ssl-protocol=any",
						"--ignore-ssl-errors=true", "--local-to-remote-url-access=true",
						"--webdriver-loglevel=INFO" });
		return capabilities;
	}

	private static DesiredCapabilities capabilitiesAndroid() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Google Nexus 5");

		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		return capabilities;
	}

	private static DesiredCapabilities capabilitiesiPhone() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Apple iPhone 6");

		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		return capabilities;
	}

	private static DesiredCapabilities capabilitiesiPad() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Apple iPad");

		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		return capabilities;
	}

	private static DesiredCapabilities capabilitiesFirefox() {
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();

		FirefoxProfile profile = new FirefoxProfile();
		// java.lang.IllegalArgumentException: Preference
		// network.http.phishy-userpass-length may not be overridden: frozen
		// value=255, requested value=255
		// profile.setPreference("network.http.phishy-userpass-length", 255);
		profile.setEnableNativeEvents(true);
		profile.setAcceptUntrustedCertificates(true);

		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
		return capabilities;
	}

	private static DesiredCapabilities capabilitiesChrome() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("enableNetwork", "true");

		ChromeOptions option = new ChromeOptions();
		option.addArguments("test-type");
		// option.addArguments("--start-maximized");
		option.setExperimentalOption("prefs", chromePrefs);
		option.addArguments("--browser.download.folderList=2");
		option.addArguments(
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
		option.addArguments("--browser.download.dir=" + downloadFilepath);
		option.addArguments("allow-running-insecure-content");

		File file = new File(chromeDriverPath);
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		capabilities.setCapability(ChromeOptions.CAPABILITY, option);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return capabilities;
	}

	private static DesiredCapabilities capabilitiesInternetExplorer() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();

		capabilities.setCapability(
				InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);
		capabilities.setCapability("ignoreZoomSetting", true);
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		capabilities.setBrowserName(
				DesiredCapabilities.internetExplorer().getBrowserName());
		return capabilities;
	}

	public static void close() {
		driver.close();
		if (driver != null) {
			driver.quit();
		}
	}
}
