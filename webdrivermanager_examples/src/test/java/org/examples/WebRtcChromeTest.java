package org.examples;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebRtcChromeTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
	}

	@Before
	public void setupTest() {
		ChromeOptions options = new ChromeOptions();

		// This flag avoids to grant the user media
		options.addArguments("--use-fake-ui-for-media-stream");

		// This flag fakes user media with synthetic video (green with spinner
		// and timer)
		options.addArguments("--use-fake-device-for-media-stream");

		driver = new ChromeDriver(options);
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
