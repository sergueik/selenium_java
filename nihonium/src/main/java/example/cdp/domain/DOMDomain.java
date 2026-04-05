package example.cdp.domain;

import com.google.gson.JsonObject;

import example.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

public class DOMDomain {

	private final NihoniumWebSocketClient wsClient;

	public DOMDomain(NihoniumWebSocketClient wsClient) {
		this.wsClient = wsClient;
	}

	public CompletableFuture<JsonObject> enable() {
		return wsClient.sendCommand("DOM.enable");
	}

	public CompletableFuture<JsonObject> disable() {
		return wsClient.sendCommand("DOM.disable");
	}

	public CompletableFuture<JsonObject> getDocument() {
		return wsClient.sendCommand("DOM.getDocument");
	}

	public CompletableFuture<JsonObject> querySelector(int nodeId, String selector) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		params.addProperty("selector", selector);
		return wsClient.sendCommand("DOM.querySelector", params);
	}

	public CompletableFuture<JsonObject> querySelectorAll(int nodeId, String selector) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		params.addProperty("selector", selector);
		return wsClient.sendCommand("DOM.querySelectorAll", params);
	}

	public CompletableFuture<JsonObject> getBoxModel(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("DOM.getBoxModel", params);
	}

	public CompletableFuture<JsonObject> getAttributes(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("DOM.getAttributes", params);
	}

	public CompletableFuture<JsonObject> setAttributeValue(int nodeId, String name, String value) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		params.addProperty("name", name);
		params.addProperty("value", value);
		return wsClient.sendCommand("DOM.setAttributeValue", params);
	}

	public CompletableFuture<JsonObject> focus(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("DOM.focus", params);
	}

	public CompletableFuture<JsonObject> resolveNode(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("DOM.resolveNode", params);
	}

	public CompletableFuture<JsonObject> requestNode(String objectId) {
		JsonObject params = new JsonObject();
		params.addProperty("objectId", objectId);
		return wsClient.sendCommand("DOM.requestNode", params);
	}

	public CompletableFuture<JsonObject> getOuterHTML(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("DOM.getOuterHTML", params);
	}

	public CompletableFuture<JsonObject> scrollIntoViewIfNeeded(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("DOM.scrollIntoViewIfNeeded", params);
	}

	public CompletableFuture<JsonObject> describeNode(int nodeId, Integer depth) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		if (depth != null) {
			params.addProperty("depth", depth);
		}
		return wsClient.sendCommand("DOM.describeNode", params);
	}
}
