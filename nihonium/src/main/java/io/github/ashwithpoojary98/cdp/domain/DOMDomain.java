package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for the CDP DOM domain.
 * Provides methods for querying and manipulating the DOM.
 */
public class DOMDomain {

    private final NihoniumWebSocketClient wsClient;

    public DOMDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    /**
     * Enables DOM domain notifications.
     *
     * @return CompletableFuture that completes when the domain is enabled
     */
    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand("DOM.enable");
    }

    /**
     * Disables DOM domain notifications.
     *
     * @return CompletableFuture that completes when the domain is disabled
     */
    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand("DOM.disable");
    }

    /**
     * Gets the root document node.
     *
     * @return CompletableFuture with the document node (contains nodeId in "root")
     */
    public CompletableFuture<JsonObject> getDocument() {
        return wsClient.sendCommand("DOM.getDocument");
    }

    /**
     * Finds a node using a CSS selector.
     *
     * @param nodeId The ID of the context node
     * @param selector CSS selector
     * @return CompletableFuture with the found node ID
     */
    public CompletableFuture<JsonObject> querySelector(int nodeId, String selector) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        params.addProperty("selector", selector);
        return wsClient.sendCommand("DOM.querySelector", params);
    }

    /**
     * Finds all nodes matching a CSS selector.
     *
     * @param nodeId The ID of the context node
     * @param selector CSS selector
     * @return CompletableFuture with array of node IDs
     */
    public CompletableFuture<JsonObject> querySelectorAll(int nodeId, String selector) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        params.addProperty("selector", selector);
        return wsClient.sendCommand("DOM.querySelectorAll", params);
    }

    /**
     * Gets the box model for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with box model information
     */
    public CompletableFuture<JsonObject> getBoxModel(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("DOM.getBoxModel", params);
    }

    /**
     * Gets attributes for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with attributes array
     */
    public CompletableFuture<JsonObject> getAttributes(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("DOM.getAttributes", params);
    }

    /**
     * Sets an attribute value on a node.
     *
     * @param nodeId The node ID
     * @param name Attribute name
     * @param value Attribute value
     * @return CompletableFuture that completes when attribute is set
     */
    public CompletableFuture<JsonObject> setAttributeValue(int nodeId, String name, String value) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        params.addProperty("name", name);
        params.addProperty("value", value);
        return wsClient.sendCommand("DOM.setAttributeValue", params);
    }

    /**
     * Focuses on a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture that completes when node is focused
     */
    public CompletableFuture<JsonObject> focus(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("DOM.focus", params);
    }

    /**
     * Requests a Remote Object for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with object information (contains objectId)
     */
    public CompletableFuture<JsonObject> resolveNode(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("DOM.resolveNode", params);
    }

    /**
     * Requests a node ID for a Remote Object.
     *
     * @param objectId The Remote Object ID
     * @return CompletableFuture with node ID
     */
    public CompletableFuture<JsonObject> requestNode(String objectId) {
        JsonObject params = new JsonObject();
        params.addProperty("objectId", objectId);
        return wsClient.sendCommand("DOM.requestNode", params);
    }

    /**
     * Gets the outer HTML for a node.
     *
     * @param nodeId The node ID
     * @return CompletableFuture with outer HTML
     */
    public CompletableFuture<JsonObject> getOuterHTML(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("DOM.getOuterHTML", params);
    }

    /**
     * Scrolls the element into view if needed.
     *
     * @param nodeId The node ID
     * @return CompletableFuture that completes when scrolled
     */
    public CompletableFuture<JsonObject> scrollIntoViewIfNeeded(int nodeId) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        return wsClient.sendCommand("DOM.scrollIntoViewIfNeeded", params);
    }

    /**
     * Describes a node.
     *
     * @param nodeId The node ID
     * @param depth Depth of children to retrieve (optional)
     * @return CompletableFuture with node description
     */
    public CompletableFuture<JsonObject> describeNode(int nodeId, Integer depth) {
        JsonObject params = new JsonObject();
        params.addProperty("nodeId", nodeId);
        if (depth != null) {
            params.addProperty("depth", depth);
        }
        return wsClient.sendCommand("DOM.describeNode", params);
    }
}
