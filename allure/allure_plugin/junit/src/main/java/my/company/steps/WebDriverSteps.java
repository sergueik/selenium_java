package my.company.steps;

import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;


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
  // will not compile with Selenium 3.x
	//	new WebDriverWait(driver, 10).withMessage("Could not load results page")
	//			.until(mainContainLoaded());
  // [ERROR] method until in class org.openqa.selenium.support.ui.FluentWait<T> cannot be applied to given types;
  // [ERROR] required: java.util.function.Function<? super org.openqa.selenium.WebDriver,V>
  // [ERROR] found: com.google.common.base.Predicate<org.openqa.selenium.WebDriver>
  // [ERROR] reason: cannot infer type-variable(s) V
  // [ERROR] (argument mismatch; com.google.common.base.Predicate<org.openqa.selenium.WebDriver> cannot be converted to java.util.function.Function<? super org.openqa.selenium.WebDriver,V>)
		new WebDriverWait(driver, 10).withMessage("Could not load results page")
				.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.className("main__content"))));
        
	}

	@Attachment
	@Step("Make screen shot of results page")
	public byte[] makeScreenShot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public void quit() {
		driver.quit();
	}

  // will not compile with Selenium 3.x
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