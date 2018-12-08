package com.mycompany.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.StringBuilder;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
// error: a type with the same simple name is already defined by the single-type-import of TimeoutException
// import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class App {

	private static RemoteWebDriver driver = null;
	private static WebDriverWait wait = null;
	private static WebElement element = null;

	private static BrowserMobProxy proxyServer = null;
	private static Proxy seleniumProxy = null;
	private static boolean debug = false;
	private static long debugSleep = 120000;

	private static String selenium_host = "localhost";
	private static String selenium_port = "4444";
	private static String selenium_browser = "firefox";
	private static String selenium_run = "local";

	private static String baseUrl = "www.imdb.com";
	private static String filePath = "test.har";
	private static FileOutputStream outputStream;
	private static String urlFragment = "http://pubads.g.doubleclick.net";
	private static String search = "The Matrix";

	public static void main(String[] args) throws InterruptedException {

		proxyServer = new BrowserMobProxyServer();
		proxyServer.start(0);
		seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);

		// initialize Selenium driver
		// TODO: exercise using the proxy with remote driver
		if (selenium_browser.compareToIgnoreCase("remote") == 0) {
			String hub = "http://" + selenium_host + ":" + selenium_port + "/wd/hub";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if (selenium_browser.compareToIgnoreCase("chrome") == 0) {
				capabilities = new DesiredCapabilities("chrome", "", Platform.ANY);
				capabilities.setBrowserName("chrome");
			} else {
				capabilities = new DesiredCapabilities("firefox", "", Platform.ANY);
				capabilities.setBrowserName("firefox");
				capabilities.setCapability("firefox_profile",
						new ProfilesIni().getProfile("default"));
			}
			try {
				driver = new RemoteWebDriver(
						new URL(
								"http://" + selenium_host + ":" + selenium_port + "/wd/hub"),
						capabilities);
			} catch (MalformedURLException ex) {
			}

		} else { // standalone
			if (selenium_browser.compareToIgnoreCase("chrome") == 0) {
				System.setProperty("webdriver.chrome.driver",
						"c:/java/selenium/chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				/*
				 * String m_proxy = <host> + ":" + <port>;
				 * proxy.setHttpProxy(m_proxy).setFtpProxy(m_proxy);
				 */
				capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
				driver = new ChromeDriver(capabilities);
			} else {
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
				driver = new FirefoxDriver(capabilities);
			}
		}
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		proxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,
				CaptureType.RESPONSE_CONTENT);
		// create a new HAR
		proxyServer.newHar(baseUrl);
		System.err.println("created a new HAR for " + baseUrl);
		// needs longer timeout in the presence of the proxy
		wait = new WebDriverWait(driver, 10, 500);
		// TODO: parameter
		try {
			driver.get("http://" + baseUrl + "/");
			if (debug) {
				try {
					Thread.sleep(debugSleep);
				} catch (InterruptedException e) {
					// System.err.println("Exception (ignored): " + e.toString());
				}
			}
			// wait for the page to load
			element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("#navbar-query")));
			// TODO: multi-step transaction
			element.sendKeys(search);

			driver.findElement(By.cssSelector("#navbar-submit-button")).click();
			// wait for the search results
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
					"#main > div.article > h1.findHeader > span.findSearchTerm")));
			// print the node information
			// System.out.println(getIPOfNode(driver));

		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				Har har = proxyServer.getHar();
				HarLog harLog = har.getLog();
				// sample entry processing
				List<HarEntry> entries = harLog.getEntries();
				for (HarEntry entry : entries) {
					if (entry.getRequest().getUrl().contains(urlFragment)) {
						System.err
								.println(String.format("url: %s", entry.getRequest().getUrl()));
					}
				}
				// reset the har
				proxyServer.newHar();

				// dump the har to the file
				outputStream = new FileOutputStream(filePath);
				har.writeTo(outputStream);
				System.out.println(String.format("har written to: %s", filePath));
			} catch (IOException e) {
				System.out.println(e.toString());
			} finally {
				try {
					outputStream.close();
				} catch (IOException e) {
					// ignore
				}
			}

			// finish the run
			proxyServer.stop();
			driver.close();
			driver.quit();
		}
	}

	@SuppressWarnings("deprecation")
	private static String getIPOfNode(RemoteWebDriver remoteDriver) {
		String hostFound = null;
		try {
			HttpCommandExecutor ce = (HttpCommandExecutor) remoteDriver
					.getCommandExecutor();
			String hostName = ce.getAddressOfRemoteServer().getHost();
			int port = ce.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL(
					String.format("http://%s:%d/grid/api/testsession?session=%s",
							hostName, port, remoteDriver.getSessionId()));
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest(
					"POST", sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostFound = myURL.getHost();
			}
		} catch (Exception e) {
			// java.lang.ClassCastException:
			// org.openqa.selenium.firefox.FirefoxDriver$LazyCommandExecutor cannot be
			// cast to org.openqa.selenium.remote.HttpCommandExecutor
			System.err.println(e);
		}
		return hostFound;
	}

	private static JSONObject extractObject(HttpResponse response) {
		StringWriter writer = new StringWriter();
		try {
			InputStream contents = response.getEntity().getContent();
			IOUtils.copy(contents, writer, "UTF8");
		} catch (IOException e) {
			return null;
		}
		try {
			return new JSONObject(writer.toString());
		} catch (JSONException e) {
			return null;
		}
	}
}
