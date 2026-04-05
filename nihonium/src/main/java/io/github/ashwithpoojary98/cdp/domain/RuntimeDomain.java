package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for the CDP Runtime domain.
 * Provides methods for JavaScript execution and object manipulation.
 */
public class RuntimeDomain {

    private final NihoniumWebSocketClient wsClient;

    public RuntimeDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    /**
     * Enables runtime domain notifications.
     *
     * @return CompletableFuture that completes when the domain is enabled
     */
    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand("Runtime.enable");
    }

    /**
     * Disables runtime domain notifications.
     *
     * @return CompletableFuture that completes when the domain is disabled
     */
    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand("Runtime.disable");
    }

    /**
     * Evaluates a JavaScript expression.
     *
     * @param expression JavaScript expression to evaluate
     * @param awaitPromise Whether to await for resulting value and return once awaited promise is settled
     * @return CompletableFuture with evaluation result
     */
    public CompletableFuture<JsonObject> evaluate(String expression, boolean awaitPromise) {
        JsonObject params = new JsonObject();
        params.addProperty("expression", expression);
        params.addProperty("awaitPromise", awaitPromise);
        params.addProperty("returnByValue", false);
        return wsClient.sendCommand("Runtime.evaluate", params);
    }

    /**
     * Evaluates a JavaScript expression and returns the value.
     *
     * @param expression JavaScript expression to evaluate
     * @param awaitPromise Whether to await for resulting value
     * @return CompletableFuture with evaluation result
     */
    public CompletableFuture<JsonObject> evaluateAndReturnByValue(String expression, boolean awaitPromise) {
        JsonObject params = new JsonObject();
        params.addProperty("expression", expression);
        params.addProperty("awaitPromise", awaitPromise);
        params.addProperty("returnByValue", true);
        return wsClient.sendCommand("Runtime.evaluate", params);
    }

    /**
     * Evaluates a JavaScript expression (without awaiting promises).
     *
     * @param expression JavaScript expression to evaluate
     * @return CompletableFuture with evaluation result
     */
    public CompletableFuture<JsonObject> evaluate(String expression) {
        return evaluate(expression, false);
    }

    /**
     * Calls a function on an object.
     *
     * @param objectId Object ID to call function on
     * @param functionDeclaration Function declaration to call
     * @param arguments Arguments to pass to the function
     * @return CompletableFuture with call result
     */
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

    /**
     * Calls a function on an object without arguments.
     *
     * @param objectId Object ID to call function on
     * @param functionDeclaration Function declaration to call
     * @return CompletableFuture with call result
     */
    public CompletableFuture<JsonObject> callFunctionOn(String objectId, String functionDeclaration) {
        return callFunctionOn(objectId, functionDeclaration, null);
    }

    /**
     * Gets properties of an object.
     *
     * @param objectId Object ID
     * @return CompletableFuture with object properties
     */
    public CompletableFuture<JsonObject> getProperties(String objectId) {
        JsonObject params = new JsonObject();
        params.addProperty("objectId", objectId);
        return wsClient.sendCommand("Runtime.getProperties", params);
    }

    /**
     * Releases a Remote Object.
     *
     * @param objectId Object ID to release
     * @return CompletableFuture that completes when object is released
     */
    public CompletableFuture<JsonObject> releaseObject(String objectId) {
        JsonObject params = new JsonObject();
        params.addProperty("objectId", objectId);
        return wsClient.sendCommand("Runtime.releaseObject", params);
    }

    /**
     * Releases all remote objects.
     *
     * @return CompletableFuture that completes when all objects are released
     */
    public CompletableFuture<JsonObject> releaseObjectGroup(String objectGroup) {
        JsonObject params = new JsonObject();
        params.addProperty("objectGroup", objectGroup);
        return wsClient.sendCommand("Runtime.releaseObjectGroup", params);
    }
}
