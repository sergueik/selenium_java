// origin: http://sahajamit.github.io/ChromeDriver-Mobile_Emulation/
// https://sites.google.com/a/chromium.org/chromedriver/mobile-emulation
package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class App {
	
	static String url = "https://www.wellsfargo.com";
	static DesiredCapabilities capabilities;
	// https://code.google.com/p/chromium/codesearch#chromium/src/chrome/test/chromedriver/chrome/mobile_device_list.cc
	static String[] deviceNames = new String[] { 
		"Google Nexus 5",
		"Samsung Galaxy S4", 
		"Samsung Galaxy Note 3", 
		"Samsung Galaxy Note II",
		"Apple iPhone 4", 
		"Apple iPhone 5" 
		/* , "Apple iPad 3 / 4" */
		};

	public static void main(String[] args) throws InterruptedException {
		for (int deviceCnt = 0; deviceCnt != deviceNames.length; deviceCnt++) {
			String deviceName = deviceNames[deviceCnt];

			String ChromeDriverPath = "c:/java/selenium/chromedriver.exe";
			// System.getProperty("user.dir") + "/lib/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", ChromeDriverPath);

			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", deviceName);

			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);

			capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			WebDriver driver = new ChromeDriver(capabilities);

			driver.manage().window().maximize();
			driver.navigate().to(url);
			Thread.sleep(300);
			driver.close();
			driver.quit();
		}
	}
}
