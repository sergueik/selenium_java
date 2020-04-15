package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailsListPage extends BasePage {
	private final String titlePage = "Почта Mail.ru";
	private final By title = By
			.xpath("//title[contains(text(), 'Почта Mail.ru')]");
	private final By emailList = By
			.xpath("//*[contains(@class, 'js-letter-list-item')]");
	private final By inbox = By.xpath("//a[contains(@title, 'Входящие')]");
	private final By createEmailButtonBroken = By
			.xpath("(//*[text() = 'Написать письмо'])[1]");
	private final By createEmailButton = By
			.cssSelector("span.compose-button[title = 'Написать письмо']");
	private final By emailSendedMessage = By
			.xpath("//span[text() = 'отправлено']");
	private final By contextMenuDelete = By
			.xpath("//*[@class = 'contextmenu__container']//*[text() = 'Удалить']");

	private List<WebElement> elements;
	private WebElement element;

	public EmailsListPage(WebDriver driver) {
		super(driver);
		wait.until(ExpectedConditions.presenceOfElementLocated(title));

		// if (!driver.getTitle().contains(titlePage)) {
		// throw new IllegalStateException("Заголовок страницы не соответствует
		// эталону");
		// }
	}

	public List<WebElement> getEmailList() {

		elements = driver.findElements(emailList);
		return elements;
	}

	public Integer getCountEmailList() {
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(inbox));
		String textTitle = element.getAttribute("title");
		String[] array = textTitle.split(",");
		Integer countEmails = Integer.parseInt(array[1].replaceAll("\\D", ""));
		return countEmails;
	}

	public EmailsListPage rightClickByEmail(List<WebElement> emailList,
			Integer pos) {
		actions.contextClick(emailList.get(pos)).build().perform();
		return this;
	}

	public EmailPopapPage clickCreateEmailButton() {
		// no such element: Unable to locate element:
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(createEmailButton));
		element.click();
		return new EmailPopapPage(driver);
	}

	public String getTextSendedMessage() {
		element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(emailSendedMessage));
		return element.getText();
	}

	public EmailsListPage deleteEmail() {
		element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(contextMenuDelete));
		element.click();
		return new EmailsListPage(driver);
	}

}
