package example.pages;

import org.slf4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoggedInSuccessfullyPage extends BasePageObject {

	private By logOutButton = By.xpath("//a[text()='Log out']");

	public LoggedInSuccessfullyPage(WebDriver driver, Logger logger) {
		super(driver, logger);
	}

	public boolean isLogOutButtonVisible() {
		return find(logOutButton).isDisplayed();
	}
}
