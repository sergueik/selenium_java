package com.jprotractor.unit;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.jprotractor.NgConfiguration;

public class NgDriverEnchancer {
	private static TimeUnit TIMEUNIT = TimeUnit.MILLISECONDS;

	public static WebDriver enchance(WebDriver driver) throws IOException {
		return enchance(driver, null);
	}

	public static WebDriver enchance(WebDriver driver, URL configFile)
			throws IOException {
		return setup(driver, new NgConfiguration(configFile));
	}

	private static WebDriver setup(WebDriver driver, NgConfiguration config) {
		if (driver == null)
			return null;
		manageTimeouts(driver, config);
		return driver;
	}

	private static void manageTimeouts(WebDriver driver, NgConfiguration config) {
		driver.manage().timeouts()
				.pageLoadTimeout(config.getPageLoadTimeout(), TIMEUNIT)
				.implicitlyWait(config.getWebDriverTimeout(), TIMEUNIT)
				.setScriptTimeout(config.getAngularTimeout(), TIMEUNIT);
	}
}
