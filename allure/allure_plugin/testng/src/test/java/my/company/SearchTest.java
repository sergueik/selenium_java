package my.company;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.Issues;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static java.lang.String.format;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.testng.Assert.fail;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import my.company.steps.WebDriverSteps;

/**
 * based on https://github.com/pnkjngm/alluretestdemo/blob/master/src/test/java/my/company/SearchTest.java
 */
public class SearchTest {

	private WebDriverSteps steps;

	public static RemoteWebDriver driver;

	private static String osName = getOSName();

	private static final String downloadFilepath = String.format("%s\\Downloads",
			osName.equals("windows") ? System.getenv("USERPROFILE")
					: System.getenv("HOME"));

	@BeforeMethod
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver",
				Paths
						.get(osName.equals("windows") ? System.getenv("USERPROFILE")
								: System.getenv("HOME"))
						.resolve("Downloads")
						.resolve(
								osName.equals("windows") ? "chromedriver.exe" : "chromedriver")
						.toAbsolutePath().toString());

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary(osName.equals("windows") ? (new File(
				"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"))
						.getAbsolutePath()
				: "/usr/bin/google-chrome");
		for (String optionAgrument : (new String[] { "headless",
				"--window-size=1200x800", "disable-gpu" })) {
			chromeOptions.addArguments(optionAgrument);
		}
		capabilities.setCapability(
				org.openqa.selenium.chrome.ChromeOptions.CAPABILITY, chromeOptions);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver = new ChromeDriver(chromeOptions);
		// steps = new WebDriverSteps(new PhantomJSDriver(new
		// DesiredCapabilities()));
		steps = new WebDriverSteps(driver);
		steps.openMainPage("http://www.tizag.com/htmlT/forms.php");
	}

	@DataProvider
	public Object[][] inlineDataProvider() {
		return new Object[][] {
				{ "/html/body/table[3]/tbody/tr[1]/td[2]/table/tbody/tr/td/div[6]/form/input[2]" },
				{ "//table[3]/tbody/tr[1]/td[2]/table/tbody/tr/td/div[6]/form/input[2]" },
				{ "//table[3]//form[2]/input[2]" },
				{ "//table[3]//form[1]/input[3]" } };
	}

	@Test(dataProvider = "inlineDataProvider")
	@Issues({ @Issue("A-1"), @Issue("B-2") })
	@Features("The feature")
	@Stories("The Story")
	@Severity(SeverityLevel.CRITICAL)
	@TestCaseId("T-3")
	public void parametrizedSearchTest(@Parameter String parameter)
			throws Exception {
		steps.searchXPath(parameter, "submit");
		steps.makeScreenshot();
		steps.quit();
	}

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

}
