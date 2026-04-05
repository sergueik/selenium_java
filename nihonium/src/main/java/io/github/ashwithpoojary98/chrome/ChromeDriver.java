package io.github.ashwithpoojary98.chrome;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.WebDriver;
import io.github.ashwithpoojary98.WebElement;
import io.github.ashwithpoojary98.browser.BrowserLauncher;
import io.github.ashwithpoojary98.browser.BrowserOptions;
import io.github.ashwithpoojary98.browser.LaunchResult;
import io.github.ashwithpoojary98.cdp.domain.BrowserDomain;
import io.github.ashwithpoojary98.cdp.domain.CSSDomain;
import io.github.ashwithpoojary98.cdp.domain.DOMDomain;
import io.github.ashwithpoojary98.cdp.domain.InputDomain;
import io.github.ashwithpoojary98.cdp.domain.NetworkDomain;
import io.github.ashwithpoojary98.cdp.domain.PageDomain;
import io.github.ashwithpoojary98.cdp.domain.RuntimeDomain;
import io.github.ashwithpoojary98.exception.BrowserLaunchException;
import io.github.ashwithpoojary98.exception.CDPException;
import io.github.ashwithpoojary98.exception.TimeoutException;
import io.github.ashwithpoojary98.network.NetworkMonitor;
import io.github.ashwithpoojary98.wait.WaitConfig;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * CDP-backed implementation of {@link WebDriver}.
 *
 * <p>Communicates directly with Chrome/Chromium over the DevTools Protocol WebSocket
 * — no ChromeDriver binary required.
 *
 * <h3>Window-handle model</h3>
 * <p>Nihonium uses the CDP {@code targetId} as the Selenium window handle.  This is
 * a stable, unique string (e.g. {@code "3F1A2B3C4D5E6F7G"}) that corresponds to
 * exactly one browser tab or window.
 */
public class ChromeDriver implements WebDriver {

    // ── CDP target type filter ────────────────────────────────────────────────

    private static final String TARGET_TYPE_PAGE = "page";
    private static final String TARGET_FIELD_TYPE = "type";
    private static final String TARGET_FIELD_ID = "targetId";
    private static final String TARGET_INFOS_FIELD = "targetInfos";

    // ── WebSocket URL parsing ─────────────────────────────────────────────────

    /**
     * The path segment that precedes the targetId in a CDP WebSocket URL.
     */
    private static final String WS_TARGET_PATH_PREFIX = "/devtools/page/";

    // ── CDP domains ───────────────────────────────────────────────────────────

    private final PageDomain pageDomain;
    private final DOMDomain domDomain;
    private final RuntimeDomain runtimeDomain;
    private final InputDomain inputDomain;
    private final NetworkDomain networkDomain;
    private final CSSDomain cssDomain;
    private final BrowserDomain browserDomain;

    // ── Infrastructure ────────────────────────────────────────────────────────

    private final BrowserLauncher launcher;
    private final NihoniumWebSocketClient wsClient;
    private final WaitConfig waitConfig;
    private final NetworkMonitor networkMonitor;

    /**
     * CDP target ID of the page this driver is connected to.
     */
    private final String currentTargetId;

    private volatile boolean closed = false;

    // ── Constructors ──────────────────────────────────────────────────────────

    public ChromeDriver() {
        this(new ChromeOptions(), WaitConfig.defaultConfig());
    }

    public ChromeDriver(ChromeOptions chromeOptions) {
        this(chromeOptions, WaitConfig.defaultConfig());
    }

    public ChromeDriver(ChromeOptions chromeOptions, WaitConfig waitConfig) {
        this.waitConfig = waitConfig;

        try {
            BrowserOptions options = BrowserOptions.builder()
                    .browserType(chromeOptions.getBrowserType())
                    .browserVersion(chromeOptions.getBrowserVersion())
                    .autoDownload(chromeOptions.isAutoDownload())
                    .binaryPath(chromeOptions.getBinaryPath())
                    .headless(chromeOptions.isHeadless())
                    .windowSize(chromeOptions.getWindowWidth(), chromeOptions.getWindowHeight())
                    .addArguments(chromeOptions.getArguments())
                    .build();

            launcher = new BrowserLauncher(options);
            LaunchResult launchResult = launcher.launch();

            // Extract the targetId from the WebSocket URL so window handles work
            this.currentTargetId = extractTargetId(launchResult.webSocketUrl());

            URI wsUri = new URI(launchResult.webSocketUrl());
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

    // ── WebDriver — navigation ────────────────────────────────────────────────

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
            JsonObject result =
                    runtimeDomain.evaluate("document.documentElement.outerHTML", false).join();
            return result.getAsJsonObject("result").get("value").getAsString();
        } catch (Exception e) {
            throw new CDPException("Failed to get page source", e);
        }
    }

    // ── DOM JSON keys (used by element-finding methods) ───────────────────────

    private static final String DOM_ROOT     = "root";
    private static final String DOM_NODE_ID  = "nodeId";
    private static final String DOM_NODE_IDS = "nodeIds";
    private static final String RUNTIME_RESULT = "result";
    private static final String RUNTIME_VALUE  = "value";

    // ── WebDriver — element finding ───────────────────────────────────────────

    @Override
    public WebElement findElement(By by) {
        return new ChromeElement(by, domDomain, runtimeDomain,
                inputDomain, cssDomain, waitConfig, networkMonitor);
    }

    @Override
    public List<WebElement> findElements(By by) {
        try {
            String cssSelector = by.toCssSelector();
            if (cssSelector != null) {
                JsonObject docResult = domDomain.getDocument().join();
                int documentNode = docResult.getAsJsonObject(DOM_ROOT)
                        .get(DOM_NODE_ID).getAsInt();
                JsonObject r = domDomain.querySelectorAll(documentNode, cssSelector).join();
                JsonArray nodeIds = r.getAsJsonArray(DOM_NODE_IDS);
                List<WebElement> elements = new ArrayList<>();
                if (nodeIds != null) {
                    for (int i = 0; i < nodeIds.size(); i++) {
                        elements.add(new ChromeElement(By.index(by, i), domDomain, runtimeDomain,
                                inputDomain, cssDomain, waitConfig, networkMonitor));
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
                elements.add(new ChromeElement(By.index(By.xpath(xpath), i), domDomain,
                        runtimeDomain, inputDomain, cssDomain, waitConfig, networkMonitor));
            }
            return elements;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // ── WebDriver — window handles ────────────────────────────────────────────

    /**
     * Returns the CDP target IDs of all open {@code page} targets.
     * Each ID can be passed to {@link TargetLocator#window(String)}.
     *
     * @return set of target ID strings (window handles)
     */
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

    /**
     * Returns the CDP target ID of the page this driver is currently connected to.
     *
     * @return target ID string (window handle)
     */
    @Override
    public String getWindowHandle() {
        return currentTargetId;
    }

    // ── WebDriver — lifecycle ─────────────────────────────────────────────────

    @Override
    public void close() {
        cleanup();
    }

    @Override
    public void quit() {
        cleanup();
    }

    // ── WebDriver — sub-interfaces ────────────────────────────────────────────

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

    // ── Package-visible accessors (used by helper classes) ────────────────────

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

    /**
     * Returns the CDP target ID of the currently connected page.
     */
    String getCurrentTargetId() {
        return currentTargetId;
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Blocks until the page's {@code document.readyState} is {@code "complete"},
     * meaning the DOM has been fully parsed and all sub-resources have loaded.
     *
     * <p>If {@link WaitConfig#isWaitForNetworkIdle()} is enabled, a second pass
     * waits for in-flight network requests to drain before returning.
     *
     * <p>The polling interval and maximum timeout are taken from the driver's
     * {@link WaitConfig}, so they respect whatever the caller configured (or the
     * {@link WaitConfig#defaultConfig() defaults}).
     *
     * @throws TimeoutException if the page does not become ready within the
     *                          configured timeout
     */
    private void waitForPageReady() {
        long deadline = System.currentTimeMillis() + waitConfig.getTimeoutMillis();

        while (System.currentTimeMillis() < deadline) {
            try {
                JsonObject result = runtimeDomain
                        .evaluate("document.readyState", false).join();
                String readyState = result.getAsJsonObject("result")
                        .get("value").getAsString();
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
                throw new TimeoutException(
                        "Interrupted while waiting for page to be ready");
            }
        }

        throw new TimeoutException(
                "Page did not reach readyState=complete within "
                        + waitConfig.getTimeoutMillis() + " ms");
    }

    /**
     * Polls {@link NetworkMonitor#isNetworkIdle} until the network is idle or
     * {@code deadline} is reached. Uses the same polling interval as the main
     * wait loop.
     *
     * @param deadline absolute timestamp (ms) after which a {@link TimeoutException} is thrown
     */
    private void waitForNetworkIdleWithDeadline(long deadline) {
        while (System.currentTimeMillis() < deadline) {
            if (networkMonitor.isNetworkIdle(
                    waitConfig.getNetworkIdleMaxConnections(),
                    waitConfig.getNetworkIdleDurationMillis())) {
                return;
            }
            try {
                Thread.sleep(waitConfig.getPollingIntervalMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TimeoutException(
                        "Interrupted while waiting for network idle");
            }
        }
        throw new TimeoutException(
                "Network did not become idle within "
                        + waitConfig.getTimeoutMillis() + " ms");
    }

    /**
     * Extracts the CDP target ID from the WebSocket debugger URL.
     *
     * <p>The URL format is {@code ws://localhost:PORT/devtools/page/TARGET_ID}.
     *
     * @param webSocketUrl WebSocket URL returned by the CDP JSON endpoint
     * @return the target ID segment
     * @throws IllegalArgumentException if the URL does not contain the expected path
     */
    private static String extractTargetId(String webSocketUrl) {
        URI uri = URI.create(webSocketUrl);
        String path = uri.getPath();
        int idx = path.lastIndexOf(WS_TARGET_PATH_PREFIX);
        if (idx < 0) {
            throw new IllegalArgumentException(
                    "Cannot extract targetId from WebSocket URL: " + webSocketUrl);
        }
        return path.substring(idx + WS_TARGET_PATH_PREFIX.length());
    }

    private void cleanup() {
        if (closed) return;
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
