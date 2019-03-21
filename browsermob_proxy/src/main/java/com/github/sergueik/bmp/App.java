package com.github.sergueik.bmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import static org.junit.Assert.assertTrue;

// error: a type with the same simple name is already defined by 
// the single-type-import of TimeoutException
// import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

// import org.openqa.selenium.firefox.ProfileManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// import de.sstoehr.harreader.HarReader;
// import de.sstoehr.harreader.HarReaderException;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.CaptureType;

@SuppressWarnings("deprecation")
public class App {

	private static RemoteWebDriver driver = null;
	private static WebDriverWait wait = null;
	private static WebElement element = null;

	private static BrowserMobProxy proxyServer = null;
	private static String osName = getOSName();
	private static Proxy seleniumProxy = null;
	private static final boolean debug = Boolean
			.parseBoolean(System.getenv("DEBUG"));
	private static long debugSleep;

	private static String hubHost = "localhost";
	private static String hubPort = "4444";
	private static String browser = System.getenv("BROWSER"); // "firefox";
	private static String runMode = "local";

	// NOTE: no http://

	private static String targetHost = "bandi.servizi.politicheagricole.it"; // "www.imdb.com";
	private static String baseUrl = "http://bandi.servizi.politicheagricole.it/taxcredit/default.aspx";
	private static String filePath = "test.har";
	private static FileOutputStream outputStream;
	private static String urlFragment = "https://s.amazon-adsystem.com";
	private static String search = "The Matrix";

	public static void main(String[] args) throws InterruptedException {

		proxyServer = new BrowserMobProxyServer();
		// for application with invalid certificates
		proxyServer.setTrustAllServers(true);
		proxyServer.start(0);

		seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);
		// origin:
		// https://techblog.dotdash.com/selenium-browsermob-integration-c35f4713fb59
		// is it needed ?
		try {
			String hostIp = Inet4Address.getLocalHost().getHostAddress();
			seleniumProxy.setHttpProxy(hostIp + ":" + proxyServer.getPort());
			seleniumProxy.setSslProxy(hostIp + ":" + proxyServer.getPort());
		} catch (UnknownHostException e) {
			System.err.println("Exception : " + e.toString());
			throw new RuntimeException("invalid localhost ip address");
		}
		if (browser == null) {
			browser = "firefox";
		}
		// initialize Selenium driver
		// TODO: exercise using the proxy with remote driver
		if (runMode.compareToIgnoreCase("remote") == 0) {
			String hub = "http://" + hubHost + ":" + hubPort + "/wd/hub";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if (browser.compareToIgnoreCase("chrome") == 0) {
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
						new URL("http://" + hubHost + ":" + hubPort + "/wd/hub"),
						capabilities);
			} catch (MalformedURLException ex) {
			}

		} else { // standalone
			if (browser.compareToIgnoreCase("chrome") == 0) {

				System.setProperty("webdriver.chrome.driver",
						Paths.get(System.getProperty("user.home"))
								.resolve("Downloads").resolve(osName.equals("windows")
										? "chromedriver.exe" : "chromedriver")
								.toAbsolutePath().toString());
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				/*
				 * String m_proxy = <host> + ":" + <port>;
				 * proxy.setHttpProxy(m_proxy).setFtpProxy(m_proxy);
				 */
				capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
				driver = new ChromeDriver(capabilities);
			} else {
				System.setProperty("webdriver.gecko.driver",
						Paths.get(System.getProperty("user.home"))
								.resolve("Downloads").resolve(osName.equals("windows")
										? "geckodriver.exe" : "geckodriver")
								.toAbsolutePath().toString());
				System
						.setProperty("webdriver.firefox.bin",
								osName.equals("windows") ? new File(
										"c:/Program Files (x86)/Mozilla Firefox/firefox.exe")
												.getAbsolutePath()
										: "/usr/bin/firefox");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				System.err
						.println("Setting HTTP proxy to" + seleniumProxy.getHttpProxy());
				System.err
						.println("Setting SSL proxy to" + seleniumProxy.getSslProxy());
				capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
				capabilities.setCapability("marionette", false);
				// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.firefox.FirefoxProfile
				capabilities.setCapability("locationContextEnabled", false);
				capabilities.setCapability("acceptSslCerts", true);
				capabilities.setCapability("elementScrollBehavior", 1);
				driver = new FirefoxDriver(capabilities);
			}
		}
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		proxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,
				CaptureType.RESPONSE_CONTENT);
		// create a new HAR
		System.err.println("Running proxy on port:" + proxyServer.getPort());
		proxyServer.newHar(targetHost);
		System.err.println("Created a new HAR for " + baseUrl);
		// needs longer timeout in the presence of the proxy
		// TODO: parameter
		long debugWait = Long.parseLong(System.getenv("DEBUG_WAIT"));

		wait = new WebDriverWait(driver, debugWait > 0 ? debugWait : 120, 500);
		// TODO: parameter
		try {
			driver.get(baseUrl);
			if (debug) {
				try {
					debugSleep = Long.parseLong(System.getenv("DEBUG_SLEEP"));
				} catch (NumberFormatException e) {
					debugSleep = 10000;
				}
				if (debugSleep == 0) {
					debugSleep = 1000;
				}
				try {
					Thread.sleep(debugSleep);
				} catch (InterruptedException e) {
					// System.err.println("Exception (ignored): " + e.toString());
				}
			}

			WebElement element = driver
					.findElement(By.xpath("//*[contains(text(), 'A C C E D I')]"));
			highlight(element);
			element.click();
			wait.until(ExpectedConditions.urlContains(baseUrl));

			element = wait.until(ExpectedConditions.visibilityOf(
					driver.findElement(By.id("ctl00_phContent_Login_txtEmail"))));
			highlight(element);

			// #ctl00_phContent_Login_btnAccedi
			// TODO: press button to navigate
			// page URL does not change
			// http://bandi.servizi.politicheagricole.it/taxcredit/default.aspx
			// wait for the page to load
			element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
							"#ctl00_phContent_Login_btnAccedi" /* "#navbar-query" */)));
				// print the node information
			// System.out.println(getIPOfNode(driver));

		} catch (org.openqa.selenium.TimeoutException e) {
			System.err.println(e.toString());
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			try {
				Har har = proxyServer.getHar();
				HarLog harLog = har.getLog();
				// sample entry assertion
				HarEntry entry0 = harLog.getEntries().get(0);
				System.err.println(
						String.format("Processing main har entry (%s). Looking for status.",
								entry0.getPageref()));
				if (entry0.getPageref().equals(baseUrl)) {
					if (entry0.getResponse().getStatus() == 0 && entry0.getResponse()
							.getError().matches("No response received")) {
						// in the browser one probably sees the error:
						// This page isn’t working www.imdb.com didn’t send any data.
						// ERR_EMPTY_RESPONSE
						proxyServer.stop();
						throw new RuntimeException("Suspect BrowserMob Proxy error");
					}
				}
				// sample entry processing
				System.err.println(String
						.format("Processing har entries. Looking for %s", urlFragment));
				List<HarEntry> entries = harLog.getEntries();
				assertTrue(entries.size() > 0);
				for (HarEntry entry : entries) {
					String url = entry.getRequest().getUrl();
					if (url.startsWith(urlFragment)) {
						System.err.println(
								"Found matching har entry: " + url.substring(0, 20) + "...");
					}
				}
				// reset the har
				proxyServer.newHar();
				// dump the har to the file
				outputStream = new FileOutputStream(filePath);
				har.writeTo(outputStream);
				System.err.println(String.format("HAR written to: %s", filePath));
			} catch (IOException e) {
				System.err.println(e.toString());
			} finally {
				try {
					proxyServer.stop();
				} catch (IllegalStateException e) {
					// Proxy server is already stopped. Cannot re-stop.
					// ignore
				}
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					// ignore
				}
				driver.close();
				driver.quit();
			}

			// TODO: process the HAR
			/* 
			HarReader harReader = new HarReader();
			de.sstoehr.harreader.model.Har har = null;
			try {
				har = harReader.readFromFile(new File("test.har"));
			} catch (HarReaderException e) {
				System.err.println("Exception in the file: " + e.toString());
			}
			if (har != null) {
				System.out.println(har.getLog().getCreator().getName());
			}
			*/
		}
	}

	@SuppressWarnings({ "unused" })
	private static String getIPOfNode(RemoteWebDriver remoteDriver) {
		String hostFound = null;
		try {
			HttpCommandExecutor ce = (HttpCommandExecutor) remoteDriver
					.getCommandExecutor();
			String hostName = ce.getAddressOfRemoteServer().getHost();
			int port = ce.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings("resource")
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

	// Utilities
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public static void highlight(WebElement element) {
		highlight(element, 100, "solid yellow");
	}

	private static int scriptTimeout = 5;
	private static int flexibleWait = 60; // too long
	private static int implicitWait = 1;
	private static int pollingInterval = 500;
	private static long highlightInterval = 100;

	public void highlight(WebElement element, long highlightInterval) {
		highlight(element, highlightInterval, "solid yellow");

	}

	public static void highlight(WebElement element, long highlightInterval,
			String color) {
		System.err.println("Color: " + color);
		if (wait == null) {
			wait = new WebDriverWait(driver, flexibleWait);
		}
		// Selenium Driver version sensitive code: 3.13.0 vs. 3.8.0 and older
		// https://stackoverflow.com/questions/49687699/how-to-remove-deprecation-warning-on-timeout-and-polling-in-selenium-java-client
		// wait.pollingEvery(Duration.ofMillis((int) pollingInterval));

		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			executeScript(String.format("arguments[0].style.border='3px %s'", color),
					element);
			Thread.sleep(highlightInterval);
			executeScript("arguments[0].style.border=''", element);
		} catch (InterruptedException e) {
			// System.err.println("Exception (ignored): " + e.toString());
		}
	}

	public void highlight(By locator) throws InterruptedException {
		highlight(locator, "solid yellow");
	}

	public void highlight(By locator, String color) throws InterruptedException {
		WebElement element = driver.findElement(locator);
		executeScript(String.format("arguments[0].style.border='3px %s'", color),
				element);
		Thread.sleep(highlightInterval);
		executeScript("arguments[0].style.border=''", element);
	}

	public static Object executeScript(String script, Object... arguments) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor = JavascriptExecutor.class
					.cast(driver);
			/*
			 *
			 // currently unsafe
			System.err.println(arguments.length + " arguments received.");
			String argStr = "";
			
			for (int i = 0; i < arguments.length; i++) {
				argStr = argStr + " "
						+ (arguments[i] == null ? "null" : arguments[i].toString());
			}
			
			System.err.println("Calling " + script.substring(0, 40)
					+ "..." + \n" + "with arguments: " + argStr);
					*/
			return javascriptExecutor.executeScript(script, arguments);
		} else {
			throw new RuntimeException("Script execution failed.");
		}
	}

}
