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

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import my.company.steps.WebDriverSteps;

/**
 * based on https://github.com/pnkjngm/alluretestdemo/blob/master/src/test/java/my/company/SearchTest.java
 */
public class SearchTest {

	private WebDriverSteps steps;

	@BeforeMethod
	public void setUp() throws Exception {
		steps = new WebDriverSteps(new PhantomJSDriver(new DesiredCapabilities()));
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
}
