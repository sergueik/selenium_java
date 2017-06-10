package software.reinvent.headless.chrome;

import com.google.inject.Inject;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import software.reinvent.headless.chrome.modules.ChromeDriverTestModule;

import java.io.File;

// TODO: cleanup

// import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
	public void testExpectedElement() throws Exception {
		final ChromeDriver driver = headlessChromeDriver.getDriver();
		driver.get("https://reinvent-software.de");
		final String text = driver
				.findElementByXPath(".//*[@id='team']//p[@class='text-muted']")
				.getText();
		assertThat(text, equals("Head of Development"));
	}

	@Test
	public void testScreenshot() throws Exception {
		final ChromeDriver driver = headlessChromeDriver.getDriver();
		driver.get("https://reinvent-software.de");
		final File pngFile = new File("/tmp/screenshot.png");
		headlessChromeDriver.screenshot(pngFile);
		assertTrue(pngFile.exists());
		assertThat(pngFile.length(), is(greaterThanOrEqualTo(1000000L)));
	}
}
