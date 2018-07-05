package my.company.steps;

import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 */
public class WebDriverSteps {

	private WebDriver driver;

	public WebDriverSteps(WebDriver driver) {
		this.driver = driver;
	}

	@Step
	public void openMainPage() {
		driver.get("http://ya.ru");
	}

	@Step
	public void search(String text) {
		driver.findElement(By.id("text")).sendKeys(text);
		driver.findElement(By.className("suggest2-form__button")).submit();
		new WebDriverWait(driver, 10).withMessage("Could not load results page")
				.until(mainContainLoaded());
	}

	@Attachment
	@Step("Make screen shot of results page")
	public byte[] makeScreenShot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public void quit() {
		driver.quit();
	}

	private Predicate<WebDriver> mainContainLoaded() {
		return new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver input) {
				return driver.findElement(By.className("main__content")).isDisplayed();
			}
			@Override
			public boolean test(WebDriver input) {
				return driver.findElement(By.className("main__content")).isDisplayed();
			}
		};
	}
}