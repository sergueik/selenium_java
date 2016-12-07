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

import net.lightbody.bmp.proxy.ProxyServer;
import net.lightbody.bmp.core.har.Har;

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
	private static String baseUrl = "www.carnival.com";

	public static void main(String[] args) throws InterruptedException {

		selenium_host = "localhost";
		selenium_port = "4444";
		selenium_browser = "firefox";
		selenium_run = "local";

		ProxyServer proxyServer = new ProxyServer(4444);
		proxyServer.start();
		// captures the mouse movements and navigations
		proxyServer.setCaptureHeaders(true);
		proxyServer.setCaptureContent(true);
		// set the Selenium proxy property
		org.openqa.selenium.Proxy proxy = proxyServer.seleniumProxy();
		// initialize driver
    // Remote configuration - not using the proxy
		if (selenium_browser.compareToIgnoreCase("remote") == 0) { 
			String hub = "http://" + selenium_host + ":" + selenium_port + "/wd/hub";
      DesiredCapabilities capabilities = new DesiredCapabilities();
			if (selenium_browser.compareToIgnoreCase("chrome") == 0) {
				capabilities = new DesiredCapabilities("chrome", "",
						Platform.ANY);
				capabilities.setBrowserName("chrome");
			} else {
				capabilities = new DesiredCapabilities("firefox",
						"", Platform.ANY);
				capabilities.setBrowserName("firefox");
				capabilities.setCapability("firefox_profile", new ProfilesIni().getProfile("default"));
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
			// create a new HAR
			proxyServer.newHar(baseUrl);
			System.err.println("create a new HAR for " + baseUrl);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		try {
			driver.get("http://" + baseUrl + "/");
			WebDriverWait wait = new WebDriverWait(driver, 30);
      // wait for the page to load
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.className("ccl-logo")));

      String value1 = "ddlDestinations";
			WebElement element =  wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath(String.format("//select[@id='%s']", value1))));

			System.out.println(element.getAttribute("id"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).build().perform();

			String csspath_selector2 = "div.find-cruise-submit > a";
			WebElement element2 = driver
					.findElement(By.cssSelector(csspath_selector2));
			System.out.println(element2.getText());
			new Actions(driver).moveToElement(element2).click().build().perform();
			Thread.sleep(5000);

			// print the node information
			System.out.println(getIPOfNode(driver));

			Har har = proxyServer.getHar();
			String strFilePath = "test.har";
			FileOutputStream fos = new FileOutputStream(strFilePath);
			har.writeTo(fos);
			System.out.println("written har");
		} catch (Exception ex) {
			System.out.println(ex.toString());
		} finally {
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
			System.err.println(e);
		}
		return hostFound;
	}

	private static JSONObject extractObject(HttpResponse resp)
			throws IOException, JSONException {
		InputStream contents = resp.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(contents, writer, "UTF8");
		JSONObject objToReturn = new JSONObject(writer.toString());
		return objToReturn;
	}
}
