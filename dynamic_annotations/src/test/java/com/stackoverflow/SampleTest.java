package com.stackoverflow;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SampleTest {

	private final Map<String, String> substitutions;

	@FindBy(how = How.ID, using = "somelocator_with_a dynamic_${id}")
	private WebElement someElement;
	private WebDriver driver;

	public SampleTest(final String idValue) {

		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();

		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("enableNetwork", "true");
		options.setExperimentalOption("prefs", chromePrefs);

		for (String optionAgrument : (new String[] {
				"allow-running-insecure-content", "allow-insecure-localhost",
				"enable-local-file-accesses", "disable-notifications",
				/* "start-maximized" , */
				"browser.download.folderList=2",
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
				String.format("browser.download.dir=%s", downloadFilepath)
				/* "user-data-dir=/path/to/your/custom/profile"  , */
		})) {
			options.addArguments(optionAgrument);
		}

		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());

		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capabilities);

		substitutions = new HashMap<>();
		substitutions.put("id", idValue);

	}

	// When you call PageFactory.initElements, you need to tell it to use the
	// DynamicElementLocatorFactory
	protected void load() {
		PageFactory.initElements(
				new DynamicElementLocatorFactory(driver, substitutions), this);
	}
}
