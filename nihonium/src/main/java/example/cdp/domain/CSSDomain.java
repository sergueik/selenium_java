package example.cdp.domain;

import com.google.gson.JsonObject;

import example.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

public class CSSDomain {

	private final NihoniumWebSocketClient wsClient;

	public CSSDomain(NihoniumWebSocketClient wsClient) {
		this.wsClient = wsClient;
	}

	public CompletableFuture<JsonObject> enable() {
		return wsClient.sendCommand("CSS.enable");
	}

	public CompletableFuture<JsonObject> disable() {
		return wsClient.sendCommand("CSS.disable");
	}

	public CompletableFuture<JsonObject> getComputedStyleForNode(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("CSS.getComputedStyleForNode", params);
	}

	public CompletableFuture<JsonObject> getInlineStylesForNode(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("CSS.getInlineStylesForNode", params);
	}

	public CompletableFuture<JsonObject> getMatchedStylesForNode(int nodeId) {
		JsonObject params = new JsonObject();
		params.addProperty("nodeId", nodeId);
		return wsClient.sendCommand("CSS.getMatchedStylesForNode", params);
	}
}
