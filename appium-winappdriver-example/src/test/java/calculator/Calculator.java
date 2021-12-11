package calculator;

import io.appium.java_client.windows.WindowsDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class Calculator {

	static WindowsDriver driver;

	@BeforeClass
	public static void initDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("app",
				"Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
		capabilities.setCapability("deviceName", "Windows10");
		// driver = new WindowsDriver(new URL("http://127.0.0.1:4723/wd/hub"),
		// capabilities);
		// For Windows automation I have to override my server path to "/" rather
		// than the default of "/wd/hub"
		// https://github.com/Microsoft/WinAppDriver/issues/26
		driver = new WindowsDriver(new URL("http://127.0.0.1:4723/"), capabilities);

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void quitDriver() {
		driver.quit();
	}
}
