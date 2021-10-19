package example.pages;

import org.slf4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestLoginPage extends BasePageObject {

	private By usernameLocator = By.id("username");
	private By passwordLocator = By.id("password");
	private By submitButtonLocator = By.id("submit");
	private By errorMessageLocator = By.id("error");

	private String url = "https://practicetestautomation.com/practice-test-login/";

	public TestLoginPage(WebDriver driver, Logger logger) {
		super(driver, logger);
	}

	public TestLoginPage open() {
		driver.get(url);
		return this;
	}

	public LoggedInSuccessfullyPage logIn(String username, String password) {
		executeLogin(username, password);
		return new LoggedInSuccessfullyPage(driver, logger);
	}

	public void negativeLogIn(String username, String password) {
		executeLogin(username, password);
	}

	private void executeLogin(String username, String password) {
		logger.info("Executing LogIn with username [" + username + "] and password [" + password + "]");
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(submitButtonLocator);
	}

	public void waitForErrorMessage() {
		waitForVisibilityOf(errorMessageLocator);
	}

	public String getErrorMessageText() {
		return find(errorMessageLocator).getText();
	}

}
