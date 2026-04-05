package example.cdp.domain;

import com.google.gson.JsonObject;

import example.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

public class PageDomain {

    private final NihoniumWebSocketClient wsClient;

    public PageDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand("Page.enable");
    }

    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand("Page.disable");
    }

    public CompletableFuture<JsonObject> navigate(String url) {
        JsonObject params = new JsonObject();
        params.addProperty("url", url);
        return wsClient.sendCommand("Page.navigate", params);
    }

    public CompletableFuture<JsonObject> reload(boolean ignoreCache) {
        JsonObject params = new JsonObject();
        params.addProperty("ignoreCache", ignoreCache);
        return wsClient.sendCommand("Page.reload", params);
    }

    public CompletableFuture<JsonObject> reload() {
        return reload(false);
    }

    public CompletableFuture<JsonObject> captureScreenshot(String format, Integer quality) {
        JsonObject params = new JsonObject();
        params.addProperty("format", format);
        if (quality != null) {
            params.addProperty("quality", quality);
        }
        return wsClient.sendCommand("Page.captureScreenshot", params);
    }

    public CompletableFuture<JsonObject> captureScreenshot() {
        return captureScreenshot("png", null);
    }

    public CompletableFuture<JsonObject> setLifecycleEventsEnabled(boolean enabled) {
        JsonObject params = new JsonObject();
        params.addProperty("enabled", enabled);
        return wsClient.sendCommand("Page.setLifecycleEventsEnabled", params);
    }

    public CompletableFuture<JsonObject> getLayoutMetrics() {
        return wsClient.sendCommand("Page.getLayoutMetrics");
    }

    public CompletableFuture<JsonObject> getFrameTree() {
        return wsClient.sendCommand("Page.getFrameTree");
    }

    public CompletableFuture<JsonObject> getNavigationHistory() {
        return wsClient.sendCommand("Page.getNavigationHistory");
    }

    public CompletableFuture<JsonObject> navigateToHistoryEntry(int entryId) {
        JsonObject params = new JsonObject();
        params.addProperty("entryId", entryId);
        return wsClient.sendCommand("Page.navigateToHistoryEntry", params);
    }
}
