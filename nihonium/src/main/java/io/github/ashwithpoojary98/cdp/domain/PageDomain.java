package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for the CDP Page domain.
 * Provides methods for page navigation, lifecycle management, and screenshots.
 */
public class PageDomain {

    private final NihoniumWebSocketClient wsClient;

    public PageDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    /**
     * Enables page domain notifications.
     *
     * @return CompletableFuture that completes when the domain is enabled
     */
    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand("Page.enable");
    }

    /**
     * Disables page domain notifications.
     *
     * @return CompletableFuture that completes when the domain is disabled
     */
    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand("Page.disable");
    }

    /**
     * Navigates to the specified URL.
     *
     * @param url The URL to navigate to
     * @return CompletableFuture with navigation result (contains frameId)
     */
    public CompletableFuture<JsonObject> navigate(String url) {
        JsonObject params = new JsonObject();
        params.addProperty("url", url);
        return wsClient.sendCommand("Page.navigate", params);
    }

    /**
     * Reloads the current page.
     *
     * @param ignoreCache Whether to ignore cache
     * @return CompletableFuture that completes when reload is initiated
     */
    public CompletableFuture<JsonObject> reload(boolean ignoreCache) {
        JsonObject params = new JsonObject();
        params.addProperty("ignoreCache", ignoreCache);
        return wsClient.sendCommand("Page.reload", params);
    }

    /**
     * Reloads the current page (without ignoring cache).
     *
     * @return CompletableFuture that completes when reload is initiated
     */
    public CompletableFuture<JsonObject> reload() {
        return reload(false);
    }

    /**
     * Captures a screenshot of the page.
     *
     * @param format Image format ("png" or "jpeg")
     * @param quality Compression quality (0-100, only for jpeg)
     * @return CompletableFuture with base64-encoded screenshot data
     */
    public CompletableFuture<JsonObject> captureScreenshot(String format, Integer quality) {
        JsonObject params = new JsonObject();
        params.addProperty("format", format);
        if (quality != null) {
            params.addProperty("quality", quality);
        }
        return wsClient.sendCommand("Page.captureScreenshot", params);
    }

    /**
     * Captures a PNG screenshot of the page.
     *
     * @return CompletableFuture with base64-encoded screenshot data
     */
    public CompletableFuture<JsonObject> captureScreenshot() {
        return captureScreenshot("png", null);
    }

    /**
     * Enables page lifecycle events.
     *
     * @param enabled Whether to enable lifecycle events
     * @return CompletableFuture that completes when lifecycle events are configured
     */
    public CompletableFuture<JsonObject> setLifecycleEventsEnabled(boolean enabled) {
        JsonObject params = new JsonObject();
        params.addProperty("enabled", enabled);
        return wsClient.sendCommand("Page.setLifecycleEventsEnabled", params);
    }

    /**
     * Gets layout metrics (viewport, content size, etc.).
     *
     * @return CompletableFuture with layout metrics
     */
    public CompletableFuture<JsonObject> getLayoutMetrics() {
        return wsClient.sendCommand("Page.getLayoutMetrics");
    }

    /**
     * Gets frame tree information.
     *
     * @return CompletableFuture with frame tree
     */
    public CompletableFuture<JsonObject> getFrameTree() {
        return wsClient.sendCommand("Page.getFrameTree");
    }

    /**
     * Gets navigation history.
     *
     * @return CompletableFuture with navigation history
     */
    public CompletableFuture<JsonObject> getNavigationHistory() {
        return wsClient.sendCommand("Page.getNavigationHistory");
    }

    /**
     * Navigates to a history entry.
     *
     * @param entryId Navigation history entry ID
     * @return CompletableFuture that completes when navigation is complete
     */
    public CompletableFuture<JsonObject> navigateToHistoryEntry(int entryId) {
        JsonObject params = new JsonObject();
        params.addProperty("entryId", entryId);
        return wsClient.sendCommand("Page.navigateToHistoryEntry", params);
    }
}
