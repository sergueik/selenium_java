package example.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.devtools.v94.fetch.Fetch;
import org.openqa.selenium.devtools.v94.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v94.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v94.fetch.model.RequestPaused;
import org.openqa.selenium.devtools.v94.fetch.model.RequestStage;
import org.openqa.selenium.devtools.v94.network.model.ResourceType;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import example.base.BaseTest;

public class XHRTest extends BaseTest {

	public Actions actions;
	private final static String url = "https://en.wikipedia.org/wiki/XMLHttpRequest";
	private final int count = 5;

	@Test
	public void test() {
		// Arrange
		List<RequestPattern> reqPattern = new ArrayList<>();
		RequestPattern xhrReqPattern = new RequestPattern(Optional.of("*"),
				Optional.of(ResourceType.XHR), Optional.of(RequestStage.RESPONSE));

		reqPattern.add(xhrReqPattern);
		devTools.send(Fetch.enable(Optional.of(reqPattern), Optional.of(false)));
		final Gson gson = new Gson();
		try {
			devTools.addListener(Fetch.requestPaused(), (RequestPaused event) -> {
				try {

					List<HeaderEntry> headerEntries = event.getResponseHeaders()
							.isPresent() ? event.getResponseHeaders().get()
									: new ArrayList<>();

					List<String> headers = headerEntries.stream()
							.map((HeaderEntry entry) -> String.format("%s: %s",
									entry.getName(), entry.getValue()))
							.collect(Collectors.toList());

					System.err.println(String.format(
							"request id: %s" + "\t" + "resource type: %s" + "\t" + "URL: %s"
									+ "\n" + "response headers:\n%s" + "\n"
									+ "response status: %d",
							event.getRequestId().toString(),
							event.getResourceType().toString(), event.getRequest().getUrl(),
							headers, event.getResponseStatusCode().get()));

					event.getRequest().getPostData().ifPresent((data) -> {
						System.err.println("Post Data:\n" + data + "\n");
					});

					Fetch.GetResponseBodyResponse response = devTools
							.send(Fetch.getResponseBody(event.getRequestId()));
					String body = null;
					if (response.getBase64Encoded()) {
						try {
							body = new String(
									Base64.decodeBase64(response.getBody().getBytes("UTF8")));
						} catch (UnsupportedEncodingException e) {
							System.err.println(
									"UnsupportedEncoding exception (ignored): " + e.toString());
						}
					} else {
						body = response.getBody();
					}
					System.err.println("response body:\n" + body);
					@SuppressWarnings("unchecked")
					Map<String, Object> payload = (Map<String, Object>) gson
							.fromJson(body, Map.class);
					System.err.println("response extract:\n" + payload.get("extract"));
					// NOTE: this command is returning void
					devTools.send(Fetch.continueRequest(event.getRequestId(),
							Optional.empty(), Optional.empty(), Optional.empty(),
							Optional.empty(), Optional.empty()));
				} catch (DevToolsException e) {
					System.err.println("DevTools exception (ignored): " + e.getMessage());
				}
			});
			// Act
			// hover the links in the main wikipedia document
			driver.get(url);
			sleep(1000);
			List<WebElement> elements = driver.findElement(By.id("mw-content-text"))
					.findElements(By.tagName("a"));
			actions = new Actions(driver);
			elements.stream().limit(count).forEach(element -> {
				actions.moveToElement(element).build().perform();
				sleep(1000);
			});
		} catch (WebDriverException e) {
			System.err.println("Web Driver exception (ignored): " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			throw (new RuntimeException(e));
		}
		devTools.clearListeners();
		devTools.send(Fetch.disable());
	}

}
