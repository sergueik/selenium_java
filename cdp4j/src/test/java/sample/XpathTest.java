package sample;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.exception.CommandException;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class XpathTest {

	public String baseURL = "about:blank";
	private static final boolean useChromium = Boolean
			.parseBoolean(getEnvWithDefault("USE_CHROMIUM", "false"));

	private static final StringBuffer verificationErrors = new StringBuffer();
	private static final String page = "xpath_sibling_text_resource.htm";
	private static final String text = "text to collect via label1";
	private static final String selector = "//article[@class='eText']/p/b[. = 'label1:']/following-sibling::text()[1]";
	public Session session;

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		if (verificationErrors.length() != 0) {
			System.err.println(String.format("Error in the method %s : %s",
					result.getMethod().getMethodName(), verificationErrors.toString()));
		}
		session.navigate("about:blank");
		sleep(1000);
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		Launcher launcher = new Launcher();
		if (useChromium) {
			java.lang.System.setProperty("chrome_binary",
					"/usr/bin/chromium-browser");
		}

		SessionFactory factory = launcher.launch();
		sleep(1000);
		try {
			session = factory.create();
			sleep(1000);
			session.clearCache();
			session.setUserAgent(
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34");
			session.enableConsoleLog();
			session.navigate(baseURL);
		} catch (CommandException e) {
			// make unchecked
			throw new RuntimeException(e);
		}

		System.err.println("Location:" + session.getLocation());
		assertThat(session, notNullValue());
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		String methodName = method.getName();
		// System.err.println("Test Name: " + methodName + "\n");
		System.err.println("Navigate to file: " + page);
		session.navigate(getPageContent(page));
		session.waitDocumentReady(10000);
		// a long timeout needed to avoid
		// io.webfolder.cdp.exception.CdpException: Unable to connect to the browser
		session.waitUntil(s -> s.getLocation()
				.equals(String.format("%s", getPageContent(page).toString())));
		System.err.println("Verified location: " + session.getLocation());

	}

	@AfterClass
	public void afterClass() {
		if (session != null) {
			session.stop();
			session.close();
		}
	}

	@Test(enabled = true)
	public void test() {
		// executes
		// runtime.evaluate(String.format("$x(\"%s\")[0]", selector))
		assertThat(session.getText(selector), containsString(text));
		System.err.println("Verified text: " + session.getText(selector)
				+ " found via xpath: " + selector);
	}

	public static String getEnvWithDefault(String name, String defaultValue) {
		String value = System.getenv(name);
		if (value == null || value.length() == 0) {
			value = defaultValue;

		}
		return value;
	}

	protected void sleep(Integer timeoutSeconds) {
		long timeoutSecondsLong = (long) timeoutSeconds;
		try {
			Thread.sleep(timeoutSecondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = BaseTest.class.getClassLoader().getResource(pagename).toURI();
			System.err.println("Testing: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
