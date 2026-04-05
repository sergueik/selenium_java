package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for the CDP CSS domain.
 * Provides methods for querying and manipulating CSS styles.
 */
public class CSSDomain {

    private final NihoniumWebSocketClient wsClient;

    public CSSDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    /**
     * Enables CSS domain notifications.
     *
     * @return CompletableFuture that completes when the domain is enabled
     */
    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand("CSS.enable");
    }

    /**
     * Disables CSS domain notifications.
     *
     * @return CompletableFuture that completes when the domain is disabled
     */
    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand("CSS.disable");
    }

    /**
     * Gets computed styles for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with computed styles
     */
    public CompletableFuture<JsonObject> getComputedStyleForNode(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("CSS.getComputedStyleForNode", params);
    }

    /**
     * Gets inline styles for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with inline styles
     */
    public CompletableFuture<JsonObject> getInlineStylesForNode(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("CSS.getInlineStylesForNode", params);
    }

    /**
     * Gets matched styles for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with matched styles
     */
    public CompletableFuture<JsonObject> getMatchedStylesForNode(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("CSS.getMatchedStylesForNode", params);
    }
}
