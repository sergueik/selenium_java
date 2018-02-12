package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class SessionTest {

	public RemoteWebDriver driver;
	public DesiredCapabilities capabilities;
	public WebDriverWait wait;
	public Actions actions;
	public Alert alert;
	public JavascriptExecutor js;
	public TakesScreenshot screenshot;

	private static final String browser = getPropertyEnv("webdriver.driver",
			"chrome"); // "firefox";
	private static String osName;
	public int scriptTimeout = 5;
	public int flexibleWait = 120;
	public int implicitWait = 1;
	public long pollingInterval = 500;
	public int pageLoadTimeout = 50;
	public int implicitlyWaitTimeout = 20;

	public String baseURL = "http://m.carnival.com/";

	@BeforeClass
	public void beforeClass() throws IOException {

		getOsName();
		if (browser.equals("chrome")) {
			capabilities = DesiredCapabilities.chrome();
		} else if (browser.equals("firefox")) {
			// capabilities = DesiredCapabilities.firefox();
			capabilities = new DesiredCapabilities("firefox", "", Platform.ANY);
			FirefoxProfile profile = new ProfilesIni().getProfile("default");
			capabilities.setCapability("firefox_profile", profile);
		}
		driver = null;

		try {
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),
					capabilities);
		} catch (MalformedURLException ex) {
		}

		assertThat(driver, notNullValue());

		actions = new Actions(driver);

		driver.manage().timeouts().setScriptTimeout(scriptTimeout,
				TimeUnit.SECONDS);
		// Declare a wait time
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		screenshot = ((TakesScreenshot) driver);
		js = ((JavascriptExecutor) driver);
		driver.manage().window().setSize(new Dimension(600, 800));
		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout,
				TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(implicitlyWaitTimeout,
				TimeUnit.SECONDS);
		// print the node information
		getIPOfNode(driver);
		// Go to URL
		driver.get(baseURL);
	}

	@Test
	public void test() {

		String value1 = "ddlDestinations";

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.className("ccl-logo")));

		String xpath_selector1 = String.format("//select[@id='%s']", value1);
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath(xpath_selector1)));
		WebElement element = driver.findElement(By.xpath(xpath_selector1));

		// System.out.println(element.getAttribute("id"));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();

		String csspath_selector2 = "div.find-cruise-submit > a";
		WebElement element2 = driver.findElement(By.cssSelector(csspath_selector2));
		// System.out.println(element2.getText());
		new Actions(driver).moveToElement(element2).click().build().perform();
	}

	@AfterClass
	public void afterClass() throws Exception {
		driver.get("about:blank");
		if (driver != null) {
			driver.quit();
		}
	}

	private static int getIPOfNode(RemoteWebDriver driver) {
		int proxyPort = 5555; // default
		try {
			HttpCommandExecutor ce = (HttpCommandExecutor) driver
					.getCommandExecutor();
			String hostName = ce.getAddressOfRemoteServer().getHost();
			int hubPort = ce.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, hubPort);
			@SuppressWarnings("deprecation")
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL(
					String.format("http://%s:%d/grid/api/testsession?session=%s",
							hostName, hubPort, driver.getSessionId()));

			System.err.println(sessionURL);
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest(
					"POST", sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				proxyPort = myURL.getPort();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		System.err.println(proxyPort);

		return proxyPort;
	}

	private static JSONObject extractObject(HttpResponse response)
			throws IOException, JSONException {
		InputStream contents = response.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(contents, writer, "UTF8");
		JSONObject obj = new JSONObject(writer.toString());
		return obj;
	}

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name");
		}
		return osName;
	}

	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}
}
