package example.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import example.base.BaseTest;
import example.pages.LoggedInSuccessfullyPage;
import example.pages.TestLoginPage;

public class LoginTests extends BaseTest {

	@Test(priority = 1)
	public void logInTest() {
		TestLoginPage testLoginPage = new TestLoginPage(driver, log).open();
		LoggedInSuccessfullyPage loggedInSuccessfullyPage = testLoginPage.logIn("student", "Password123");

		Assert.assertTrue(loggedInSuccessfullyPage.isLogOutButtonVisible(), "LogOut Button is not visible.");

		String expectedSuccessMessage = "Congratulations student. You successfully logged in!";
		String actualSuccessMessage = loggedInSuccessfullyPage.getCurrentPageSource();
		Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
				"actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
						+ expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);
	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2)
	@DataProvider(parallel = true)
	public Object[] negativeLoginTest(String username, String password, String expectedErrorMessage) {
		TestLoginPage testLoginPage = new TestLoginPage(driver, log).open();
		testLoginPage.negativeLogIn(username, password);

		testLoginPage.waitForErrorMessage();
		String message = testLoginPage.getErrorMessageText();

		Assert.assertTrue(message.contains(expectedErrorMessage), "Message doesn't contain expected text.");
		return new Object[0];
	}
}
