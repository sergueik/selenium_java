package tests;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Message;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.devtools.network.model.ConnectionType;
import org.openqa.selenium.devtools.network.model.InterceptionStage;
import org.openqa.selenium.devtools.network.model.RequestPattern;
import org.openqa.selenium.devtools.network.model.ResourceType;
import org.openqa.selenium.devtools.performance.Performance;
import org.openqa.selenium.devtools.performance.model.TimeDomain;
import org.openqa.selenium.devtools.security.Security;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import static org.openqa.selenium.devtools.network.Network.*;
import static org.openqa.selenium.devtools.security.Security.setIgnoreCertificateErrors;

public class Selenium4CDPTest {

	ChromeDriver driver;
	DevTools devTools;

	@Test
	public void simulateNetworkBandwidthTest() throws InterruptedException {
		driver.get("http://www.google.com");
		devTools.createSession();
		devTools.send(
				enable(Optional.of(100000000), Optional.empty(), Optional.empty()));
		devTools.send(emulateNetworkConditions(false, 100, 1000, 2000,
				Optional.of(ConnectionType.cellular3g)));
		driver.get("https://seleniumconf.co.uk");
		waitForWebsiteToLoad();
	}

	@Test
	public void simulateBandwidthWithExecCDPTest() throws InterruptedException {
		MessageBuilder message = Messages.enableNetwork();
		driver.get("http://www.google.com");
		driver.executeCdpCommand(message.method, message.params);
		MessageBuilder simulateNetwork = Messages.setNetworkBandWidth();
		driver.executeCdpCommand(simulateNetwork.method, simulateNetwork.params);
		driver.get("https://seleniumconf.co.uk");
		waitForWebsiteToLoad();
	}

	@Test
	public void setGeolocationTest() throws InterruptedException {
		String getLocationLocator = "//*[@id=\"content\"]/div/button";
		driver.get("https://the-internet.herokuapp.com/geolocation");

		MessageBuilder simulateLocation = Messages.overrideLocation();
		driver.executeCdpCommand(simulateLocation.method, simulateLocation.params);
		driver.findElementByXPath(getLocationLocator).click();
		waitForWebsiteToLoad();
	}

	@Test
	public void mockWebResponseTest() throws InterruptedException {
		devTools.createSession();
		devTools.send(
				enable(Optional.of(100000000), Optional.empty(), Optional.empty()));

		devTools
				.addListener(requestIntercepted(),
						requestIntercepted -> devTools.send(continueInterceptedRequest(
								requestIntercepted.getInterceptionId(), Optional.empty(),
								Optional.of("This is Mocked!!!!!"), Optional.empty(),
								Optional.empty(), Optional.empty(), Optional.empty(),
								Optional.empty())));
		RequestPattern requestPattern = new RequestPattern("*",
				ResourceType.Document, InterceptionStage.HeadersReceived);
		devTools.send(setRequestInterception(ImmutableList.of(requestPattern)));
		driver.navigate().to("http://petstore.swagger.io/v2/swagger.json");
		waitForWebsiteToLoad();
	}

	@Test
	public void loadInsecureWebsiteTest() {
		devTools.send(Security.enable());
		devTools.send(setIgnoreCertificateErrors(false));
		driver.get("https://google.com/");
		devTools.send(Security.disable());
		waitForWebsiteToLoad();
	}

	@Test
	public void performanceTest() {
		devTools.createSession();
		devTools.send(Performance.setTimeDomain(TimeDomain.timeTicks));
		devTools.send(Performance.enable());
		System.out.println(devTools.send(Performance.getMetrics()));

		driver.get("http://www.facebook.com");
		devTools.close();
	}

	@After
	public void teardown() {
		driver.close();
		driver.quit();
	}

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("os.name").toLowerCase().contains("windows")
						? Paths.get(System.getProperty("user.home")).resolve("Downloads")
								.resolve("chromedriver.exe").toAbsolutePath().toString()
						: new File("/usr/local/chromedriver").exists()
								? "/usr/local/chromedriver"
								: Paths.get(System.getProperty("user.home"))
										.resolve("Downloads").resolve("chromedriver")
										.toAbsolutePath().toString());

		driver = new ChromeDriver();
		devTools = driver.getDevTools();
	}

	public void waitForWebsiteToLoad() {
		Utils.getInstance().waitFor(100);
	}
}