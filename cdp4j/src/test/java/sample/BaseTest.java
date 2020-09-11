package sample;

import static java.lang.System.err;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.exception.CommandException;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.runtime.CallArgument;
import io.webfolder.cdp.type.runtime.CallFunctionOnResult;
import io.webfolder.cdp.type.runtime.RemoteObject;

/**
 * Selected test scenarios for CDP
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class BaseTest {

	public String baseURL = "about:blank";
	public Session session;
	int waitTimeout = 5000;
	int pollingInterval = 500;
	private static final boolean useChromium = Boolean.parseBoolean(getEnvWithDefault("USE_CHROMIUM", "false"));

	public static String getEnvWithDefault(String name, String defaultValue) {
		String value = System.getenv(name);
		if (value == null || value.length() == 0) {
			value = defaultValue;

		}
		return value;
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		Launcher launcher = new Launcher();
		if (useChromium) {
			// which chromium-browser
			java.lang.System.setProperty("chrome_binary", "/usr/bin/chromium-browser");
		}
		// at io.webfolder.cdp.Launcher.internalLaunch(Launcher.java:211)

		SessionFactory factory = launcher.launch(/* "--remote-debugging-port=9222", "--disable-gpu", "--headless", "--window-size=1200x800" */ );
		sleep(1000);
		try {
			session = factory.create();
			sleep(1000);
			// install extensions
			// session.clearCookies();
			// session.clearCache();
			// session.setUserAgent(
			// 		"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34");
			session.navigate(baseURL);
		} catch (CommandException e) {
			throw new RuntimeException(e);
		}

		System.err.println("Location:" + session.getLocation());
	}

	@AfterClass
	public void afterClass() {
		if (session != null) {
			session.stop();
			session.close();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		String methodName = method.getName();
		System.err.println("Test Name: " + methodName + "\n");
	}

	@AfterMethod
	public void afterMethod() {
		session.navigate("about:blank");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
	}

	protected void sleep(long timeoutSeconds) {
		try {
			Thread.sleep(timeoutSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
