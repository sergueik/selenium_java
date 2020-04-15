package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailPopapPage extends BasePage {
	private final By to = By.xpath("(//input[@type = 'text'])[2]");
	private final By toValue = By.xpath(
			"//*[@data-type = 'to']//span[text() = 'test_testov2021@mail.ru']");
	private final By subject = By.xpath("//*[@name = 'Subject']");
	private final By textArea = By
			.xpath("(//*[contains(@class, 'editable')])[2]/div[1]");
	private final By sendButton = By.xpath("(//*[text() = 'Отправить'])[1]");
	private final By saveDraftButton = By.xpath("//*[text() = 'Сохранить']");
	private List<WebElement> elements;
	private WebElement element;

	public EmailPopapPage(WebDriver driver) {
		super(driver);
		By isDisplayedPopap = By
				.xpath("//*[contains(@class, 'compose-app_popup')]");
		wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(isDisplayedPopap)));
		// wait.until(ExpectedConditions.presenceOfElementLocated(isDisplayedPopap));
	}

	public EmailPopapPage setTo(String text) {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(to));
		element.sendKeys(text);
		return this;
	}

	public String getTextTo() {
		return driver.findElement(toValue).getText();
	}

	public EmailPopapPage setSubject(String text) {
		driver.findElement(subject).sendKeys(text);
		return this;
	}

	public String getTextSubject() {
		return driver.findElement(subject).getAttribute("value");
	}

	public EmailPopapPage setTextArea(String text) {
		driver.findElement(textArea).sendKeys(text);
		return this;
	}

	public String getTextArea() {
		return driver.findElement(textArea).getText();
	}

	public EmailsListPage clickSendEmailButton() {
		driver.findElement(sendButton).click();
		return new EmailsListPage(driver);
	}

	public EmailPopapPage clickSaveEmailButton() {
		driver.findElement(saveDraftButton).click();
		return this;
	}

}
