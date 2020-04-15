package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Actions actions;

	protected List<WebElement> elements;
	protected WebElement element;

	private By notifyMessageText = By
			.xpath("//*[@class = 'notify__message__text']");
	private By notifyMessageAction = By.xpath("//*[@class = 'notify__action']");

	private By collapsedDtaft = By.xpath(
			"//*[@class = 'compose-collapsed__item']//*[text() = 'Тестирование отправки письма']");

	public BasePage(WebDriver driver) {
		assertThat(driver, notNullValue());
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 10);
		actions = new Actions(this.driver);
		this.driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public String getTextNotifyMessage() {
		try {
			element = wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(notifyMessageText)));
			return element.getText();
		} catch (ElementClickInterceptedException | NoSuchElementException e) {
		}
		return null;
	}

	public void clickActionNotifyMessage() {
		try {
			element = wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(notifyMessageAction)));
			element.click();
		} catch (ElementClickInterceptedException e) {
		}
	}

	public EmailPopapPage openDraft() {
		try {
			element = wait.until(
					ExpectedConditions.visibilityOf(driver.findElement(collapsedDtaft)));
			element.click();
		} catch (ElementClickInterceptedException | NoSuchElementException e) {
		}
		return new EmailPopapPage(driver);
	}
}
