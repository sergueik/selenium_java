package io.github.ashwithpoojary98.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.cdp.CDPCommandManager;
import io.github.ashwithpoojary98.exception.CDPException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * WebSocket client for Chrome DevTools Protocol (CDP) communication.
 *
 * <p>Manages the lifecycle of the WebSocket connection to Chrome/Chromium and
 * dispatches all incoming messages to {@link CDPCommandManager} for correlation
 * with pending commands and event subscribers.
 *
 * <p>Usage:
 * <pre>{@code
 * NihoniumWebSocketClient client = new NihoniumWebSocketClient(uri);
 * client.connectBlocking();
 * client.awaitConnection(10, TimeUnit.SECONDS);
 *
 * CompletableFuture<JsonObject> result = client.sendCommand("Page.navigate", params);
 * }</pre>
 */
public class NihoniumWebSocketClient extends WebSocketClient {

    private static final Logger log = LoggerFactory.getLogger(NihoniumWebSocketClient.class);

    private final CDPCommandManager commandManager;
    private final Gson              gson;
    private final CountDownLatch    connectionLatch;

    private volatile boolean   connected;
    private volatile Exception connectionError;

    // ── CDP message JSON keys ─────────────────────────────────────────────────

    private static final String KEY_ID     = "id";
    private static final String KEY_METHOD = "method";
    private static final String KEY_PARAMS = "params";

    // ── Construction ──────────────────────────────────────────────────────────

    /**
     * Creates a new client connected to the given CDP WebSocket URI.
     *
     * @param serverUri WebSocket URI (e.g. {@code ws://localhost:9222/devtools/page/…})
     */
    public NihoniumWebSocketClient(URI serverUri) {
        this(serverUri, new CDPCommandManager());
    }

    /**
     * Creates a new client with a custom {@link CDPCommandManager}.
     * Useful for testing.
     *
     * @param serverUri      WebSocket URI
     * @param commandManager command/response correlation manager
     */
    public NihoniumWebSocketClient(URI serverUri, CDPCommandManager commandManager) {
        super(serverUri);
        this.commandManager  = commandManager;
        this.gson            = new Gson();
        this.connectionLatch = new CountDownLatch(1);
        this.connected       = false;
    }

    // ── WebSocketClient callbacks ─────────────────────────────────────────────

    @Override
    public void onOpen(ServerHandshake handshake) {
        connected = true;
        connectionLatch.countDown();
        log.info("CDP WebSocket connection established: {}", getURI());
    }

    @Override
    public void onMessage(String message) {
        try {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            commandManager.handleMessage(json);
        } catch (Exception e) {
            log.error("Failed to handle CDP message: {}", e.getMessage(), e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        connected = false;
        log.info("CDP WebSocket closed by {} — code: {}, reason: {}",
                remote ? "remote" : "local", code, reason);
        commandManager.clear();
    }

    @Override
    public void onError(Exception ex) {
        log.error("CDP WebSocket error: {}", ex.getMessage(), ex);
        connectionError = ex;
        connectionLatch.countDown();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Blocks until the WebSocket connection is established or the timeout elapses.
     *
     * @param timeout maximum time to wait
     * @param unit    time unit
     * @return {@code true} if connected successfully within the timeout
     * @throws InterruptedException if the calling thread is interrupted
     * @throws CDPException         if the connection attempt itself threw an error
     */
    public boolean awaitConnection(long timeout, TimeUnit unit) throws InterruptedException {
        boolean achieved = connectionLatch.await(timeout, unit);
        if (connectionError != null) {
            throw new CDPException("CDP WebSocket connection failed", connectionError);
        }
        return achieved && connected;
    }

    /**
     * Sends a CDP command and returns a {@link CompletableFuture} that completes
     * with the {@code result} object from the response.
     *
     * @param method CDP method name (e.g. {@code "Page.navigate"})
     * @param params command parameters, or {@code null} if none
     * @return future completing with the CDP response result
     */
    public CompletableFuture<JsonObject> sendCommand(String method, JsonObject params) {
        if (!connected) {
            return CompletableFuture.failedFuture(
                    new CDPException("Cannot send command — WebSocket is not connected"));
        }

        long commandId = commandManager.nextCommandId();
        CompletableFuture<JsonObject> future = commandManager.registerCommand(commandId);

        JsonObject command = new JsonObject();
        command.addProperty(KEY_ID,     commandId);
        command.addProperty(KEY_METHOD, method);
        if (params != null) {
            command.add(KEY_PARAMS, params);
        }

        log.trace("→ CDP {} (id={})", method, commandId);
        send(gson.toJson(command));
        return future;
    }

    /**
     * Sends a CDP command with no parameters.
     *
     * @param method CDP method name
     * @return future completing with the CDP response result
     */
    public CompletableFuture<JsonObject> sendCommand(String method) {
        return sendCommand(method, null);
    }

    /**
     * Subscribes to a CDP event by method name.
     *
     * @param eventName fully-qualified event name (e.g. {@code "Page.frameNavigated"})
     * @param handler   consumer invoked with the event {@code params} object
     */
    public void subscribeToEvent(String eventName, Consumer<JsonObject> handler) {
        commandManager.subscribe(eventName, handler);
    }

    /**
     * Removes a specific event handler.
     *
     * @param eventName CDP event name
     * @param handler   the exact handler instance to remove
     */
    public void unsubscribeFromEvent(String eventName, Consumer<JsonObject> handler) {
        commandManager.unsubscribe(eventName, handler);
    }

    /**
     * Removes all handlers for an event.
     *
     * @param eventName CDP event name
     */
    public void unsubscribeAllFromEvent(String eventName) {
        commandManager.unsubscribeAll(eventName);
    }

    /**
     * Returns {@code true} if the WebSocket is currently open and connected.
     *
     * @return connection state
     */
    public boolean isConnected() {
        return connected && !isClosed();
    }

    /**
     * Returns the underlying {@link CDPCommandManager}.
     *
     * @return command manager
     */
    public CDPCommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Closes the WebSocket connection and cancels all pending CDP command futures.
     */
    @Override
    public void close() {
        commandManager.clear();
        super.close();
    }
}
