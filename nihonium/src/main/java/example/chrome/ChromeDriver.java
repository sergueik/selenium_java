package example.chrome;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import example.By;
import example.WebDriver;
import example.WebElement;
import example.browser.BrowserLauncher;
import example.browser.BrowserOptions;
import example.browser.LaunchResult;
import example.cdp.domain.BrowserDomain;
import example.cdp.domain.CSSDomain;
import example.cdp.domain.DOMDomain;
import example.cdp.domain.InputDomain;
import example.cdp.domain.NetworkDomain;
import example.cdp.domain.PageDomain;
import example.cdp.domain.RuntimeDomain;
import example.exception.BrowserLaunchException;
import example.exception.CDPException;
import example.exception.TimeoutException;
import example.network.NetworkMonitor;
import example.wait.WaitConfig;
import example.websocket.NihoniumWebSocketClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ChromeDriver implements WebDriver {

	private static final String TARGET_TYPE_PAGE = "page";
	private static final String TARGET_FIELD_TYPE = "type";
	private static final String TARGET_FIELD_ID = "targetId";
	private static final String TARGET_INFOS_FIELD = "targetInfos";

	private static final String WS_TARGET_PATH_PREFIX = "/devtools/page/";

	private final PageDomain pageDomain;
	private final DOMDomain domDomain;
	private final RuntimeDomain runtimeDomain;
	private final InputDomain inputDomain;
	private final NetworkDomain networkDomain;
	private final CSSDomain cssDomain;
	private final BrowserDomain browserDomain;

	private final BrowserLauncher launcher;
	private final NihoniumWebSocketClient wsClient;
	private final WaitConfig waitConfig;
	private final NetworkMonitor networkMonitor;

	private final String currentTargetId;

	private volatile boolean closed = false;

	public ChromeDriver() {
		this(new ChromeOptions(), WaitConfig.defaultConfig());
	}

	public ChromeDriver(ChromeOptions chromeOptions) {
		this(chromeOptions, WaitConfig.defaultConfig());
	}

	public ChromeDriver(ChromeOptions chromeOptions, WaitConfig waitConfig) {
		this.waitConfig = waitConfig;

		try {
			BrowserOptions options = BrowserOptions.builder().browserType(chromeOptions.getBrowserType())
					.browserVersion(chromeOptions.getBrowserVersion()).autoDownload(chromeOptions.isAutoDownload())
					.binaryPath(chromeOptions.getBinaryPath()).headless(chromeOptions.isHeadless())
					.windowSize(chromeOptions.getWindowWidth(), chromeOptions.getWindowHeight())
					.addArguments(chromeOptions.getArguments()).build();

			launcher = new BrowserLauncher(options);
			LaunchResult launchResult = launcher.launch();

			this.currentTargetId = extractTargetId(launchResult.getWebSocketUrl());

			URI wsUri = new URI(launchResult.getWebSocketUrl());
			wsClient = new NihoniumWebSocketClient(wsUri);
			wsClient.connectBlocking();

			boolean connected = wsClient.awaitConnection(10, TimeUnit.SECONDS);
			if (!connected) {
				throw new BrowserLaunchException("Timed out waiting for CDP WebSocket connection");
			}

			pageDomain = new PageDomain(wsClient);
			domDomain = new DOMDomain(wsClient);
			runtimeDomain = new RuntimeDomain(wsClient);
			inputDomain = new InputDomain(wsClient);
			networkDomain = new NetworkDomain(wsClient);
			cssDomain = new CSSDomain(wsClient);
			browserDomain = new BrowserDomain(wsClient);

			networkMonitor = new NetworkMonitor(networkDomain);
			if (waitConfig.isWaitForNetworkIdle()) {
				networkMonitor.enable();
			}

			pageDomain.enable().join();
			domDomain.enable().join();
			runtimeDomain.enable().join();

		} catch (BrowserLaunchException e) {
			throw e;
		} catch (Exception e) {
			cleanup();
			throw new BrowserLaunchException("Failed to initialise ChromeDriver", e);
		}
	}

	@Override
	public void get(String url) {
		try {
			pageDomain.navigate(url).join();
		} catch (Exception e) {
			throw new CDPException("Failed to navigate to: " + url, e);
		}
	}

	@Override
	public String getCurrentUrl() {
		waitForPageReady();
		try {
			JsonObject result = runtimeDomain.evaluate("window.location.href", false).join();
			return result.getAsJsonObject("result").get("value").getAsString();
		} catch (Exception e) {
			throw new CDPException("Failed to get current URL", e);
		}
	}

	@Override
	public String getTitle() {
		waitForPageReady();
		try {
			JsonObject result = runtimeDomain.evaluate("document.title", false).join();
			return result.getAsJsonObject("result").get("value").getAsString();
		} catch (Exception e) {
			throw new CDPException("Failed to get page title", e);
		}
	}

	@Override
	public String getPageSource() {
		waitForPageReady();
		try {
			JsonObject result = runtimeDomain.evaluate("document.documentElement.outerHTML", false).join();
			return result.getAsJsonObject("result").get("value").getAsString();
		} catch (Exception e) {
			throw new CDPException("Failed to get page source", e);
		}
	}

	private static final String DOM_ROOT = "root";
	private static final String DOM_NODE_ID = "nodeId";
	private static final String DOM_NODE_IDS = "nodeIds";
	private static final String RUNTIME_RESULT = "result";
	private static final String RUNTIME_VALUE = "value";

	@Override
	public WebElement findElement(By by) {
		return new ChromeElement(by, domDomain, runtimeDomain, inputDomain, cssDomain, waitConfig, networkMonitor);
	}

	@Override
	public List<WebElement> findElements(By by) {
		try {
			String cssSelector = by.toCssSelector();
			if (cssSelector != null) {
				JsonObject docResult = domDomain.getDocument().join();
				int documentNode = docResult.getAsJsonObject(DOM_ROOT).get(DOM_NODE_ID).getAsInt();
				JsonObject r = domDomain.querySelectorAll(documentNode, cssSelector).join();
				JsonArray nodeIds = r.getAsJsonArray(DOM_NODE_IDS);
				List<WebElement> elements = new ArrayList<>();
				if (nodeIds != null) {
					for (int i = 0; i < nodeIds.size(); i++) {
						elements.add(new ChromeElement(By.index(by, i), domDomain, runtimeDomain, inputDomain,
								cssDomain, waitConfig, networkMonitor));
					}
				}
				return elements;
			}

			if (by.isXPath()) {
				return findElementsByXPath(by.getSelector());
			}

			return new ArrayList<>();
		} catch (Exception e) {
			throw new CDPException("Failed to find elements: " + by, e);
		}
	}

	private List<WebElement> findElementsByXPath(String xpath) {
		String escaped = xpath.replace("\\", "\\\\").replace("'", "\\'");
		String countScript = "document.evaluate('" + escaped + "', document, null, "
				+ "XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null).snapshotLength";
		try {
			JsonObject result = runtimeDomain.evaluate(countScript, false).join();
			int count = result.getAsJsonObject(RUNTIME_RESULT).get(RUNTIME_VALUE).getAsInt();
			List<WebElement> elements = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				elements.add(new ChromeElement(By.index(By.xpath(xpath), i), domDomain, runtimeDomain, inputDomain,
						cssDomain, waitConfig, networkMonitor));
			}
			return elements;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Set<String> getWindowHandles() {
		try {
			JsonObject response = browserDomain.getTargets().join();
			JsonArray targets = response.getAsJsonArray(TARGET_INFOS_FIELD);
			Set<String> handles = new HashSet<>();
			for (JsonElement el : targets) {
				JsonObject target = el.getAsJsonObject();
				if (TARGET_TYPE_PAGE.equals(target.get(TARGET_FIELD_TYPE).getAsString())) {
					handles.add(target.get(TARGET_FIELD_ID).getAsString());
				}
			}
			return handles;
		} catch (Exception e) {
			throw new CDPException("Failed to get window handles", e);
		}
	}

	@Override
	public String getWindowHandle() {
		return currentTargetId;
	}

	@Override
	public void close() {
		cleanup();
	}

	@Override
	public void quit() {
		cleanup();
	}

	@Override
	public TargetLocator switchTo() {
		return new ChromeTargetLocator(this);
	}

	@Override
	public Navigation navigate() {
		return new ChromeNavigation(this);
	}

	@Override
	public Options manage() {
		return new ChromeManageOptions(this);
	}

	PageDomain getPageDomain() {
		return pageDomain;
	}

	BrowserDomain getBrowserDomain() {
		return browserDomain;
	}

	RuntimeDomain getRuntimeDomain() {
		return runtimeDomain;
	}

	DOMDomain getDomDomain() {
		return domDomain;
	}

	NetworkDomain getNetworkDomain() {
		return networkDomain;
	}

	String getCurrentTargetId() {
		return currentTargetId;
	}

	private void waitForPageReady() {
		long deadline = System.currentTimeMillis() + waitConfig.getTimeoutMillis();

		while (System.currentTimeMillis() < deadline) {
			try {
				JsonObject result = runtimeDomain.evaluate("document.readyState", false).join();
				String readyState = result.getAsJsonObject("result").get("value").getAsString();
				if ("complete".equals(readyState)) {
					// DOM is fully parsed — optionally drain in-flight network requests
					if (waitConfig.isWaitForNetworkIdle() && networkMonitor != null) {
						waitForNetworkIdleWithDeadline(deadline);
					}
					return;
				}
			} catch (Exception ignored) {
				// DOM not reachable yet — keep polling
			}

			try {
				Thread.sleep(waitConfig.getPollingIntervalMillis());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new TimeoutException("Interrupted while waiting for page to be ready");
			}
		}

		throw new TimeoutException(
				"Page did not reach readyState=complete within " + waitConfig.getTimeoutMillis() + " ms");
	}

	private void waitForNetworkIdleWithDeadline(long deadline) {
		while (System.currentTimeMillis() < deadline) {
			if (networkMonitor.isNetworkIdle(waitConfig.getNetworkIdleMaxConnections(),
					waitConfig.getNetworkIdleDurationMillis())) {
				return;
			}
			try {
				Thread.sleep(waitConfig.getPollingIntervalMillis());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new TimeoutException("Interrupted while waiting for network idle");
			}
		}
		throw new TimeoutException("Network did not become idle within " + waitConfig.getTimeoutMillis() + " ms");
	}

	private static String extractTargetId(String webSocketUrl) {
		URI uri = URI.create(webSocketUrl);
		String path = uri.getPath();
		int idx = path.lastIndexOf(WS_TARGET_PATH_PREFIX);
		if (idx < 0) {
			throw new IllegalArgumentException("Cannot extract targetId from WebSocket URL: " + webSocketUrl);
		}
		return path.substring(idx + WS_TARGET_PATH_PREFIX.length());
	}

	private void cleanup() {
		if (closed)
			return;
		closed = true;

		try {
			if (wsClient != null && wsClient.isOpen()) {
				wsClient.close();
			}
		} catch (Exception ignored) {
		}

		try {
			if (launcher != null) {
				launcher.shutdown();
			}
		} catch (Exception ignored) {
		}
	}
}
