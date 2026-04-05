package example.cdp.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import example.websocket.NihoniumWebSocketClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RuntimeDomain {

    private final NihoniumWebSocketClient wsClient;

    public RuntimeDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand("Runtime.enable");
    }

    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand("Runtime.disable");
    }

    public CompletableFuture<JsonObject> evaluate(String expression, boolean awaitPromise) {
        JsonObject params = new JsonObject();
        params.addProperty("expression", expression);
        params.addProperty("awaitPromise", awaitPromise);
        params.addProperty("returnByValue", false);
        return wsClient.sendCommand("Runtime.evaluate", params);
    }

    public CompletableFuture<JsonObject> evaluateAndReturnByValue(String expression, boolean awaitPromise) {
        JsonObject params = new JsonObject();
        params.addProperty("expression", expression);
        params.addProperty("awaitPromise", awaitPromise);
        params.addProperty("returnByValue", true);
        return wsClient.sendCommand("Runtime.evaluate", params);
    }

    public CompletableFuture<JsonObject> evaluate(String expression) {
        return evaluate(expression, false);
    }

    public CompletableFuture<JsonObject> callFunctionOn(String objectId, String functionDeclaration, List<JsonObject> arguments) {
        JsonObject params = new JsonObject();
        params.addProperty("objectId", objectId);
        params.addProperty("functionDeclaration", functionDeclaration);

        if (arguments != null && !arguments.isEmpty()) {
            JsonArray argsArray = new JsonArray();
            arguments.forEach(argsArray::add);
            params.add("arguments", argsArray);
        }

        return wsClient.sendCommand("Runtime.callFunctionOn", params);
    }

    public CompletableFuture<JsonObject> callFunctionOn(String objectId, String functionDeclaration) {
        return callFunctionOn(objectId, functionDeclaration, null);
    }

    public CompletableFuture<JsonObject> getProperties(String objectId) {
        JsonObject params = new JsonObject();
        params.addProperty("objectId", objectId);
        return wsClient.sendCommand("Runtime.getProperties", params);
    }

    public CompletableFuture<JsonObject> releaseObject(String objectId) {
        JsonObject params = new JsonObject();
        params.addProperty("objectId", objectId);
        return wsClient.sendCommand("Runtime.releaseObject", params);
    }

    public CompletableFuture<JsonObject> releaseObjectGroup(String objectGroup) {
        JsonObject params = new JsonObject();
        params.addProperty("objectGroup", objectGroup);
        return wsClient.sendCommand("Runtime.releaseObjectGroup", params);
    }
}
