package org.utils;

// TODO: cleanup

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.utils.modules.ChromeDriverTestModule;

import com.google.inject.Inject;

import software.reinvent.headless.chrome.HeadlessChromeDriver;

@RunWith(JukitoRunner.class)
@UseModules({ ChromeDriverTestModule.class })
public class HeadlessChromeDriverTest {

	@Inject
	private HeadlessChromeDriver headlessChromeDriver;

	@Test
	public void testChromeDriver() throws Exception {
		assertThat(headlessChromeDriver.getDriver(), notNullValue());
	}

	@Test
	public void testElement() throws Exception {
		final ChromeDriver driver = headlessChromeDriver.getDriver();

		driver.get("https://habrahabr.ru/");
		final String text = driver
				.findElementsByCssSelector("div.main-navbar__section svg").get(0)
				.getAttribute("class");
		// 	System.err.println("Class: " + text);
		assertTrue(text.matches(".*icon-svg_logo-habrahabr.*"));
	}

	@Ignore
	@Test
	public void testScreenshot() throws Exception {
		final ChromeDriver driver = headlessChromeDriver.getDriver();
		driver.get("https://reinvent-software.de");
		final File pngFile = new File("/tmp/screenshot.png");
		headlessChromeDriver.screenshot(pngFile);
		assertTrue(pngFile.exists());
		assertThat(pngFile.length(), is(greaterThanOrEqualTo(500000L)));
	}
}
