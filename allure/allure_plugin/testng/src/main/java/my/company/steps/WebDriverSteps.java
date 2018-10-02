package my.company.steps;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class WebDriverSteps {

	public RemoteWebDriver driver;

	public WebDriverSteps(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@Step
	public void openMainPage(String url) {
		driver.get(url);
	}

	@Step("Search by XPath \"{0}\"")
	public void searchXPath(String xpath, String type) {
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		WebElement element = elements.get(0);
		// ArrayIndexOutOfBoundsException exception will mark a test as broken.
		assertThat(element.getAttribute("type"), containsString(type));
	}

	@Attachment
	public byte[] makeScreenshot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public void quit() {
		driver.quit();
	}
}