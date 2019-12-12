package example;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import example.utils.UINotificationService;
import example.utils.Utils;

public class BaseNotificationServiceTest {
	protected WebDriver driver;
	protected Utils utils;

	protected UINotificationService uiNotificationService;

	@Before
	public void beforeTest() throws IOException {
		this.utils = Utils.getInstance();

		this.driver = utils.launchBrowser();
		this.uiNotificationService = UINotificationService.getInstance(driver);
	}

	@After
	public void afterTest() {
		utils.stopChrome();
	}

}
