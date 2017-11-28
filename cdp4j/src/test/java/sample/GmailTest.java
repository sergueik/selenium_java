package sample;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.exception.CommandException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GmailTest extends BaseTest {

	private String baseURL = "https://www.google.com/gmail/about/#";
	private String accountsURL = "https://accounts.google.com/signin/v2/identifier\\?continue=";
	//

	// private String signInLink = "//*[contains(text(),'Sign in')]"; // xpath
	// private String signInLink =
	// "//nav/div/a[contains(@class,'gmail-nav__nav-link__sign-in')]"; // xpath
	private String signInLink = "body > nav > div > a.gmail-nav__nav-link.gmail-nav__nav-link__sign-in"; // cssSelector
	private String identifier = "#identifierId"; // cssSelector
	private String identifierNextButton = "//*[@id='identifierNext']/content/span[contains(text(),'Next')]"; // xpath
	private String passwordInput = "//*[@id='password']//input"; // xpath
	private String passwordNextButton = "//*[@id='passwordNext']/content/span[contains(text(),'Next')]"; // xpath
	private String profileImage = "//a[contains(@href,'https://accounts.google.com/SignOutOptions')]"; // xpath
	// "//*[@id='gb']/div[1]/div[1]/div[2]/div[4]/div[1]/a/span"
	private String signOutButton = "//div[@aria-label='Account Information']//a[contains(text(), 'Sign out')][contains(@href, 'https://accounts.google.com/Logout?')]"; // xpath
	// ".//*[@id='gb_71']"

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(session, notNullValue());
	}

	@BeforeMethod
	public void beforeMethod() {
		System.err.println("Navigate to URL: " + baseURL);
		session.navigate(baseURL);

		// Wait for page url to change
		Predicate<Session> urlChange = session -> session.getLocation()
				.matches(String.format("^%s.*", baseURL));
		session.waitUntil(urlChange, 1000, 100);
	}

	@Test(priority = 1, enabled = false)
	public void invalidUsernameTest() throws InterruptedException, IOException {

		// click on Sign in link
		highlight(signInLink);
		session.click(signInLink);

		Predicate<Session> urlChange = session -> session.getLocation()
				.matches(String.format("^%s.*", accountsURL));
		session.waitUntil(urlChange, 1000, 100);
		assertTrue(
				// String.format("Unexpected title '%s'", row.getAttribute("role")),
				session.getLocation().matches(String.format("^%s.*", accountsURL)));
		sleep(10000);

		// enter a non-existing account
		enterData(identifier, "InvalidUser_UVW");

		// Click on next button
		clickNextButton(identifierNextButton);

		// Inspect error messages
		List<String> errMsg = session.getObjectIds(
				"//*[contains (text(), \"Couldn't find your Google Account\")]");
		assertTrue(errMsg.size() > 0);
	}

	@Test(priority = 2, enabled = true)
	public void invalidPasswordTest() {

		// Sign in
		executeScript(session, "function() { this.click(); }", signInLink);

		// if we are on sign up page
		if (session.getLocation()
				.matches("https://accounts.google.com/SignUp.*$")) {
			// if we are on sign up page, there is a 'Sign in' link, click it again
			if (session.matches("#link-signin")) {
				session.focus("#link-signin");
				highlight("#link-signin");
				session.click("#link-signin");
			}
		} else {
			// we are on sign in page
		}

		Predicate<Session> urlChange = session -> session.getLocation()
				.matches(String.format("^%s.*", accountsURL));
		session.waitUntil(urlChange, 1000, 100);
		assertTrue(
				// String.format("Unexpected title '%s'", row.getAttribute("role")),
				session.getLocation().matches(String.format("^%s.*", accountsURL)));

		// Enter existing email id
		enterData(identifier, "automationnewuser24@gmail.com");

		// Click on next button
		clickNextButton(identifierNextButton);

		// Enter invalid password
		enterData(passwordInput, "InvalidPwd");

		// Click on next button
		clickNextButton(passwordNextButton);

		// Inspect error messages
		sleep(1000);
		List<String> errMsg = session
				.getObjectIds("//*[contains (text(),'Wrong password')]");
		assertTrue(errMsg.size() > 0);
	}

	private void enterData(String selector, String data) {
		session.waitUntil(o -> isVisible(selector), 1000, 100);
		try {
			session.focus(selector);
			session.sendKeys(data);
		} catch (CommandException e) {
			// Element is not focusable ?
			System.err.println("Exception in enerData: " + e.getMessage());
		}
	}

	private void clickNextButton(String selector) {
		session.waitUntil(o -> isVisible(selector), 1000, 100);
		try {
			session.focus(selector);
		} catch (CommandException e) {
			// Element is not focusable ?
			System.err.println("Exception in clickNextButton: " + e.getMessage());
		}
		highlight(selector);
		executeScript(session, "function() { this.click(); }", selector);
		// session.click is not too reliable
		// session.click(selector);
	}

}
