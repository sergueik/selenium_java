package org.examples;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.FirefoxDriverManager;

public class WebRtcFirefoxTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		FirefoxDriverManager.getInstance().setup();
	}

	@Before
	public void setupTest() {
		FirefoxOptions options = new FirefoxOptions();

		// This flag avoids granting the access to the camera
		options.addPreference("media.navigator.permission.disabled", true);

		// This flag force to use fake user media (synthetic video of multiple
		// color)
		options.addPreference("media.navigator.streams.fake", true);

		driver = new FirefoxDriver(options);
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() throws InterruptedException {
		// Test data
		int timeout = 30;
		String sutUrl = "https://webrtc.github.io/samples/src/content/devices/input-output/";

		// Implicit timeout
		driver.manage().timeouts().implicitlyWait(timeout, SECONDS);
		driver.manage().timeouts().implicitlyWait(timeout, SECONDS);

		// Open page
		driver.get(sutUrl);

		// Wait 10 seconds
		sleep(10000);
	}

}
