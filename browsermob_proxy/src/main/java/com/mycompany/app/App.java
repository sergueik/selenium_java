// https://groups.google.com/forum/#!topic/webdriver/aQl5o0TorqM
// http://amormoeba.blogspot.com/2014/02/how-to-use-browser-mob-proxy.html
// http://www.assertselenium.com/browsermob-proxy/performance-data-collection-using-browsermob-proxy-and-selenium/
package com.mycompany.app;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.StringBuilder;
import java.util.concurrent.TimeUnit;
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
import org.openqa.selenium.WebElement;

// import net.lightbody.bmp.proxy.ProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

import org.openqa.selenium.Dimension;

import org.openqa.selenium.Platform;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.openqa.selenium.interactions.Actions;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Proxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

public class App {

	private static RemoteWebDriver driver = null;
	private static String selenium_host = null;
	private static String selenium_port = null;
	private static String selenium_browser = null;
	private static String selenium_run = null;
	private static Properties props = new Properties();
	private static String baseUrl = "www.imdb.com";

	public static void main(String[] args) throws InterruptedException {

		selenium_host = "localhost";
		selenium_port = "4444";
		selenium_browser = "firefox";
		selenium_run = "local";

    BrowserMobProxy proxy = new BrowserMobProxyServer();
    proxy.start(0);

    // get the Selenium proxy object
    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

		// initialize driver
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
        String m_proxy = <host> + ":" + <port>;
        proxy.setHttpProxy(m_proxy).setFtpProxy(m_proxy);
        */
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				driver = new ChromeDriver(capabilities);
			} else {
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				driver = new FirefoxDriver(capabilities);
			}
		}
		driver.manage().window().setSize(new Dimension(600, 800));
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		try {

      proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
			// create a new HAR
			proxy.newHar(baseUrl);
			System.err.println("create a new HAR for " + baseUrl);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		try {
			driver.get("http://" + baseUrl + "/");
			WebDriverWait wait = new WebDriverWait(driver, 30);
			// wait for the page to load
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("#home_img_holder")));
      // TODO: multi-step transaction
			// print the node information
			// System.out.println(getIPOfNode(driver));

			Har har = proxy.getHar();
			String filePath = "test.har";
			har.writeTo(new FileOutputStream(filePath));
			System.out.println(String.format("har written to: %s", filePath));
		} catch (IOException ex) {
			System.out.println(ex.toString());
		} finally {
			proxy.stop();
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
      // java.lang.ClassCastException: org.openqa.selenium.firefox.FirefoxDriver$LazyCommandExecutor cannot be cast to org.openqa.selenium.remote.HttpCommandExecutor
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
