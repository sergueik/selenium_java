package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

	private String login = null;
	private String password = null;

	private final String titlePage = "Mail.ru: почта, поиск в интернете, новости, игры";
	private final By title = By.xpath(
			"//title[text() = 'Mail.ru: почта, поиск в интернете, новости, игры']");
	private final By loginLocator = By.xpath("//*[@id = 'mailbox:login']");
	private final By enterPasswordButtonLocator = By
			.xpath("//*[text() = 'Ввести пароль']");
	private final By passwordLocator = By.xpath("//*[@id = 'mailbox:password']");
	private final By enterButton = By.xpath("//*[text() = 'Войти']");

	private List<WebElement> elements;
	private WebElement element;

	public void setLogin(String data) {
		login = data;
	}

	public void setPassword(String data) {
		password = data;
	}

	public LoginPage(WebDriver driver) {
		super(driver);

		wait.until(ExpectedConditions.presenceOfElementLocated(title));

		// if (!driver.getTitle().equals(titlePage)) {
		// throw new IllegalStateException("Заголовок страницы не соответствует
		// эталону");
		// }
	}

	public LoginPage typeLogin(String login) {
		element = wait
				.until(ExpectedConditions.visibilityOfElementLocated(loginLocator));
		element.sendKeys(login);
		return this;
	}

	public LoginPage clickEnterPasswordButton() {
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(enterPasswordButtonLocator));
		element.click();
		return this;
	}

	public LoginPage typePassword(String password) {
		element = wait
				.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
		element.sendKeys(password);
		return this;
	}

	public EmailsListPage clickSubmitEnterButton() {
		element = wait
				.until(ExpectedConditions.visibilityOfElementLocated(enterButton));
		element.click();
		return new EmailsListPage(driver);
	}

	// why the credentials are passed as arguments all around?
	// is it a page object
	// model feature?
	public EmailsListPage loginToMailRu(String login, String password) {
		this.login = login;
		this.password = password;
		typeLogin(login);
		clickEnterPasswordButton();
		typePassword(password);
		clickSubmitEnterButton();
		return new EmailsListPage(driver);
	}
}
