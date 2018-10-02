package my.company.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class WebDriverSteps {

	private RemoteWebDriver driver;
	private final String url = "http://ya.ru";

	public WebDriverSteps(RemoteWebDriver driver) {
		this.driver = driver;
	}

	@Step
	public void openMainPage() {
		driver.get(url);
	}

	@Step
	public void search(String text) {
		driver.findElement(By.id("text")).sendKeys(text);
		driver.findElement(By.className("suggest2-form__button")).submit();
		new WebDriverWait(driver, 10).withMessage("Could not load results page")
				.until(ExpectedConditions
						.visibilityOf(driver.findElement(By.className("main__content"))));

	}

	@Attachment
	@Step("Make screen shot of results page")
	public byte[] makeScreenShot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public void quit() {
		driver.quit();
	}

}
