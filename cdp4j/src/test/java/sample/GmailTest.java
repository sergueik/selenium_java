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
		session.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34");
		System.err.println("Navigate to URL: " + baseURL);
		session.navigate(baseURL);

		// Wait for page url to change
		Predicate<Session> urlChange = session -> session.getLocation()
				.matches(String.format("^%s.*", baseURL));
		session.waitUntil(urlChange, 1000, 100);
	}

	@Test(priority = 1, enabled = true)
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

		enterData(identifier, "InvalidUser_UVW");

		// Click on next button
		clickNextButton(identifierNextButton);

		// Inspect error messages
		List<String> errMsg = session.getObjectIds(
				"//*[contains (text(), \"Couldn't find your Google Account\")]");
		assertTrue(errMsg.size() > 0);
	}

	private void enterData(String selector, String data) {
		session.waitUntil(o -> isVisible(selector), 1000, 100);
		session.focus(selector);
		session.sendKeys(data);
	}

	private void clickNextButton(String selector) {
		session.waitUntil(o -> isVisible(selector), 1000, 100);
		session.focus(selector);
		highlight(selector);
		session.click(selector);
	}

}