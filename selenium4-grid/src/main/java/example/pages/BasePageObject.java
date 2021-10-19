package example.pages;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// TODO: get rid of

public class BasePageObject {

	protected WebDriver driver;
	protected Logger logger;

	public BasePageObject(WebDriver driver, Logger logger) {
		this.driver = driver;
		this.logger = logger;
	}

	protected void openUrl(String url) {
		driver.get(url);
	}

	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}

	protected List<WebElement> findAll(By locator) {
		return driver.findElements(locator);
	}

	protected void click(By locator) {
		waitForVisibilityOf(locator, Duration.ofSeconds(5));
		find(locator).click();
	}

	protected void type(String text, By locator) {
		waitForVisibilityOf(locator, Duration.ofSeconds(5));
		find(locator).sendKeys(text);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getCurrentPageTitle() {
		return driver.getTitle();
	}

	public String getCurrentPageSource() {
		return driver.getPageSource();
	}

	private void waitFor(ExpectedCondition<WebElement> condition, Duration timeOut) {
		timeOut = timeOut != null ? timeOut : Duration.ofSeconds(30);
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(condition);
	}

	protected void waitForVisibilityOf(By locator, Duration... timeOut) {
		int attempts = 0;
		while (attempts < 2) {
			try {
				waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
						(timeOut.length > 0 ? timeOut[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}
}
