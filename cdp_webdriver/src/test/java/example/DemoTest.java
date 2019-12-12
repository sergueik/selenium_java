package example;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: get rid of
import com.neovisionaries.ws.client.WebSocketException;

import example.messaging.MessageBuilder;
import example.messaging.ServiceWorker;
import example.utils.Utils;

public class DemoTest extends BaseTest {
	private String URL = null;

	// @Ignore
	@Test
	public void doFakeGeoLocation()
			throws IOException, WebSocketException, InterruptedException {
		CDPClient.sendMessage(
				MessageBuilder.buildGeoLocationMessage(id, 37.422290, -122.084057));
		// google HQ
		utils.waitFor(3);
		URL = "https://www.google.com.sg/maps";
		driver.navigate().to(URL);
		uiUtils
				.findElement(By.cssSelector(
						"div[class *='widget-mylocation-button-icon-common']"), 120)
				.click();
		utils.waitFor(10);
		uiUtils.takeScreenShot();
	}

	// NOTE: for doNetworkTracking, need to switch to headless, e.g.
	// via setting BaseTest property and invoking super.beforeTest() explicitly
	// @Before
	// public void beforeTest() {
	// }

	@Ignore
	@Test
	public void doNetworkTracking()
			throws IOException, WebSocketException, InterruptedException {
		CDPClient.sendMessage(MessageBuilder.buildNetWorkEnableMessage(id));
		URL = "http://petstore.swagger.io/v2/swagger.json";
		driver.navigate().to(URL);
		utils.waitFor(3);
		String message = CDPClient.getResponseMessage("Network.requestWillBeSent");
		JSONObject jsonObject = new JSONObject(message);
		String reqId = jsonObject.getJSONObject("params").getString("requestId");
		int id2 = Utils.getInstance().getDynamicID();
		CDPClient
				.sendMessage(MessageBuilder.buildGetResponseBodyMessage(id2, reqId));
		String networkResponse = CDPClient.getResponseBodyMessage(id2);
		System.err.println("Here is the network Response: " + networkResponse);
		utils.waitFor(1);
		uiUtils.takeScreenShot();
	}

	@Ignore
	@Test
	public void doResponseMocking() throws Exception {
		CDPClient.sendMessage(MessageBuilder
				.buildRequestInterceptorPatternMessage(id, "*", "Document"));
		CDPClient.mockResponse("This is mocked!!!");
		URL = "http://petstore.swagger.io/v2/swagger.json";
		driver.navigate().to(URL);
		utils.waitFor(3);
	}

	@Ignore
	@Test
	public void doFunMocking() throws IOException, WebSocketException {
		byte[] fileContent = FileUtils.readFileToByteArray(
				new File(System.getProperty("user.dir") + "/data/durian.png"));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		CDPClient.sendMessage(
				MessageBuilder.buildRequestInterceptorPatternMessage(id, "*", "Image"));
		CDPClient.mockFunResponse(encodedString);
		URL = "https://sg.carousell.com/";
		driver.navigate().to(URL);
		utils.waitFor(300);
	}

	@Ignore
	@Test
	public void doClearSiteData() throws Exception {
		URL = "https://framework.realtime.co/demo/web-push";
		driver.navigate().to(URL);
		driver.manage().deleteAllCookies();
		CDPClient.sendMessage(MessageBuilder.buildClearBrowserCookiesMessage(id));
		CDPClient.sendMessage(MessageBuilder.buildClearDataForOriginMessage(id,
				"https://framework.realtime.co"));
		utils.waitFor(3);
	}

	// Page.handleJavaScriptDialog

	@Ignore
	@Test
	public void doElementScreenshot() throws Exception {
		URL = "https://www.google.com/";
		driver.navigate().to(URL);
		WebElement logo = uiUtils.findElement(By.cssSelector("img#hplogo"), 5);
		int x = logo.getLocation().getX();
		int y = logo.getLocation().getY();
		int width = logo.getSize().getWidth();
		int height = logo.getSize().getHeight();
		int scale = 1;
		CDPClient.sendMessage(MessageBuilder.buildTakeElementScreenShotMessage(id,
				x, y, height, width, scale));
		String encodedBytes = CDPClient.getResponseDataMessage(id);
		byte[] bytes = Base64.getDecoder().decode(encodedBytes);
		File f = new File(System.getProperty("user.dir") + "/target/img.png");
		if (f.exists())
			f.delete();
		Files.write(f.toPath(), bytes);
		uiUtils.takeScreenShot();
	}

	@Ignore
	// No message received
	// {"error":{"code":-32000,"message":"PrintToPDF is not implemented"}}
	@Test
	public void doprintPDF() throws Exception {
		URL = "https://www.wikipedia.com/";
		driver.navigate().to(URL);
		CDPClient.sendMessage(MessageBuilder.printPDFMessage(id));
		String encodedBytes = CDPClient.getResponseDataMessage(id);
		byte[] bytes = Base64.getDecoder().decode(encodedBytes);
		String start_time = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"))
				.format(new Date());
		String imageName = "cdp_img_" + start_time + ".pdf";
		File f = new File(System.getProperty("user.dir") + "/target/" + imageName);
		if (f.exists())
			f.delete();
		Files.write(f.toPath(), bytes);
	}

	@Ignore
	@Test
	public void doFullPageScreenshot() throws Exception {
		URL = "https://www.meetup.com/";
		driver.navigate().to(URL);
		long docWidth = (long) uiUtils
				.executeJavaScript("return document.body.offsetWidth");
		long docHeight = (long) uiUtils
				.executeJavaScript("return document.body.offsetHeight");
		int scale = 1;
		CDPClient.sendMessage(MessageBuilder.buildTakeElementScreenShotMessage(id,
				0, 0, docHeight, docWidth, scale));
		String encodedBytes = CDPClient.getResponseDataMessage(id);
		byte[] bytes = Base64.getDecoder().decode(encodedBytes);
		String start_time = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"))
				.format(new Date());
		String imageName = "cdp_img_" + start_time + ".png";
		File f = new File(System.getProperty("user.dir") + "/target/" + imageName);
		if (f.exists())
			f.delete();
		Files.write(f.toPath(), bytes);
		uiUtils.takeScreenShot();
	}

	@Ignore
	@Test
	public void doServiceWorkerTesting() throws Exception {
		URL = "https://www.meetup.com/";
		CDPClient.sendMessage(MessageBuilder.buildServiceWorkerEnableMessage(id));
		driver.navigate().to(URL);
		ServiceWorker serviceWorker = CDPClient.getServiceWorker(URL, 10,
				"activated");
		System.out.println(serviceWorker.toString());
		Assert.assertEquals(serviceWorker.getStatus(), "activated");
	}

	@Ignore
	@Test
	public void doPushNotificationTesting() throws Exception {
		URL = "https://framework.realtime.co/demo/web-push";
		CDPClient.sendMessage(MessageBuilder.buildServiceWorkerEnableMessage(id));
		driver.navigate().to(URL);
		utils.waitFor(5);
		ServiceWorker serviceWorker = CDPClient.getServiceWorker(URL, 5,
				"activated");
		int id1 = Utils.getInstance().getDynamicID();
		int id2 = Utils.getInstance().getDynamicID();

		CDPClient.sendMessage(MessageBuilder.buildEnableLogMessage(id1));
		CDPClient.sendMessage(MessageBuilder.buildEnableRuntimeMessage(id2));

		CDPClient.sendMessage(MessageBuilder.buildServiceWorkerInspectMessage(id2,
				serviceWorker.getVersionId()));
		WebElement elem = uiUtils.findElement(By.cssSelector("button#sendButton"),
				3);
		uiUtils.scrollToElement(elem);
		elem.click();
		utils.waitFor(3);
		utils.waitFor(60);
	}
}
