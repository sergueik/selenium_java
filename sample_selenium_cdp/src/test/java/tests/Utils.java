package tests;

import org.json.JSONArray;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Utils {

	RemoteWebDriver driver;
	ChromeDriverService service;

	private static ThreadLocal<Utils> instance = new ThreadLocal<Utils>();

	public static Utils getInstance() {
		if (instance.get() == null) {
			instance.set(new Utils());
		}
		return instance.get();
	}

	public void waitFor(long time) {
		try {
			TimeUnit.SECONDS.sleep(time);
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public RemoteWebDriver launchBrowser() throws IOException {
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.notifications", 1);
		prefs.put("profile.default_content_setting_values.protocol-monitor", true);

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments(Arrays.asList("--start-maximized"));
		options.setExperimentalOption("prefs", prefs);

		DesiredCapabilities crcapabilities = new DesiredCapabilities();
		crcapabilities.setCapability(ChromeOptions.CAPABILITY, options);
		crcapabilities.setCapability(CapabilityType.BROWSER_NAME,
				BrowserType.CHROME);
		crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		crcapabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
				System.getProperty("user.dir") + "/target/chromedriver.log");
		service = new ChromeDriverService.Builder().usingAnyFreePort()
				.withVerbose(true).build();
		service.start();

		try {
			driver = new RemoteWebDriver(service.getUrl(), crcapabilities);
		} catch (Exception e) {
			throw e;
		}
		return driver;
	}

	public int getDynamicID() {
		int min = 100000;
		int max = 999999;
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public CDPClient getCdpClient() throws IOException {
		Map<?, ?> chrome = (Map<?, ?>) ((HasCapabilities) driver).getCapabilities()
				.getCapability("chrome");
		String userDataDir = (String) chrome.get("userDataDir");
		int port = Integer.parseInt(Files
				.readAllLines(Paths.get(userDataDir, "DevToolsActivePort")).get(0));
		URL url = new URL("http", "localhost", port, "/json");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		String json = org.apache.commons.io.IOUtils.toString(reader);
		JSONArray jsonObject = new JSONArray(json);
		String webSocketDebuggerUrl = jsonObject.getJSONObject(0)
				.get("webSocketDebuggerUrl").toString();

		return new CDPClient(webSocketDebuggerUrl);
	}
}
