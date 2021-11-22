package tests;

import com.neovisionaries.ws.client.WebSocketException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static tests.MessageBuilder.buildRequestInterceptorPatternMessage;

public class Selenium3CDPTest {

	RemoteWebDriver driver;

	@Test
	public void clearStorageTest() throws IOException, WebSocketException {
		CDPClient cdpClient = Utils.getInstance().getCdpClient();
		driver.navigate().to("https://framework.realtime.co/demo/web-push");
		int id = Utils.getInstance().getDynamicID();
		JSONObject orderJSON = new JSONObject();
		JSONObject objects = new JSONObject();

		orderJSON.put("id", id);
		orderJSON.put("method", "Storage.clearDataForOrigin");

		objects.put("origin", "https://framework.realtime.co");
		objects.put("storageTypes", "all");
		orderJSON.put("params", objects);
		cdpClient.sendMessage(orderJSON.toString());
		waitForWebsiteToLoad();
	}

	@Test
	public void seleniumMockingImagesTest() throws Exception {
		byte[] fileContent = FileUtils.readFileToByteArray(
				new File(System.getProperty("user.dir") + "/logo.png"));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);

		CDPClient cdpClient = Utils.getInstance().getCdpClient();
		int id = Utils.getInstance().getDynamicID();
		cdpClient
				.sendMessage(buildRequestInterceptorPatternMessage(id, "*", "Image"));
		cdpClient.mockFunResponse(encodedString);
		driver.navigate().to("https://seleniumconf.co.uk/");
		waitForWebsiteToLoad();
	}

	@Before
	public void setUp() throws IOException {
		driver = Utils.getInstance().launchBrowser();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	public void waitForWebsiteToLoad() {
		Utils.getInstance().waitFor(10000);
	}
}
