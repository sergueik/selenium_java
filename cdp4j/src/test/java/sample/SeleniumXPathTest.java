package sample;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumXPathTest extends BaseTest {

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final String page = "xpath_sibling_text_resource.htm";
	private static final String text = "text to collect via label1";
	private static final String selector = "//article[@class='eText']/p/b[. = 'label1:']/following-sibling::text()[1]";

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			System.err.println(String.format("Error in the method %s : %s", result.getMethod().getMethodName(),
					verificationErrors.toString()));
		}
		session.navigate("about:blank");
		super.afterMethod();
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		super.beforeClass();
		assertThat(session, notNullValue());
	}

	@BeforeMethod
	public void beforeMethod() {
		System.err.println("Navigate to file: " + page);
		session.navigate(super.getPageContent(page));
		session.waitDocumentReady(10000);
		// need a long timeout to avoid
		// io.webfolder.cdp.exception.CdpException: Unable to connect to the browser
		session.waitUntil(s -> s.getLocation().equals(String.format("%s", super.getPageContent(page).toString())));
		System.err.println("Verified location: " + session.getLocation());

	}

	@Test(enabled = false)
	public void test() {
		assertThat(session.getText(selector), containsString(text));
		System.err.println("Verified text: " + session.getText(selector)
				+ " found via xpath: " + selector);
	}

}
