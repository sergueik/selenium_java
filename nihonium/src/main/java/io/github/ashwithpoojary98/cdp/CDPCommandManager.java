package io.github.ashwithpoojary98.cdp;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.exception.CDPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Manages Chrome DevTools Protocol (CDP) command/response correlation and event routing.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Generating unique, monotonically-increasing command IDs</li>
 *   <li>Correlating asynchronous CDP responses with their originating commands</li>
 *   <li>Routing CDP events to registered subscribers</li>
 *   <li>Failing pending futures cleanly when the connection is closed</li>
 * </ul>
 *
 * <p>All public methods are thread-safe.
 */
public class CDPCommandManager {

    private static final Logger log = LoggerFactory.getLogger(CDPCommandManager.class);

    /** Default time to wait for a CDP command response before failing the future. */
    public static final long DEFAULT_TIMEOUT_SECONDS = 30L;

    private static final String CDP_KEY_ID      = "id";
    private static final String CDP_KEY_METHOD  = "method";
    private static final String CDP_KEY_RESULT  = "result";
    private static final String CDP_KEY_ERROR   = "error";
    private static final String CDP_KEY_PARAMS  = "params";
    private static final String CDP_KEY_MESSAGE = "message";
    private static final String CDP_KEY_CODE    = "code";

    private static final String ERROR_UNKNOWN      = "Unknown CDP error";
    private static final String ERROR_CLOSED       = "CDP connection closed";
    private static final int    ERROR_CODE_UNKNOWN = -1;

    private final AtomicLong commandIdGenerator = new AtomicLong(0);
    private final ConcurrentHashMap<Long, CompletableFuture<JsonObject>> pendingCommands =
            new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<Consumer<JsonObject>>> eventSubscribers =
            new ConcurrentHashMap<>();

    private final long defaultTimeoutSeconds;

    // ── Construction ──────────────────────────────────────────────────────────

    /**
     * Creates a {@code CDPCommandManager} with the {@link #DEFAULT_TIMEOUT_SECONDS} timeout.
     */
    public CDPCommandManager() {
        this(DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Creates a {@code CDPCommandManager} with a custom command timeout.
     *
     * @param defaultTimeoutSeconds seconds before a pending command future is failed
     */
    public CDPCommandManager(long defaultTimeoutSeconds) {
        this.defaultTimeoutSeconds = defaultTimeoutSeconds;
    }

    // ── Command lifecycle ─────────────────────────────────────────────────────

    /**
     * Returns the next unique command ID. Thread-safe and monotonically increasing.
     *
     * @return next command ID
     */
    public long nextCommandId() {
        return commandIdGenerator.incrementAndGet();
    }

    /**
     * Registers a pending command and returns a {@link CompletableFuture} that will
     * be completed (or exceptionally) when the matching CDP response arrives.
     *
     * <p>The future automatically times out after {@link #defaultTimeoutSeconds} seconds,
     * removing the entry from the pending map to prevent memory leaks.
     *
     * @param id command ID as returned by {@link #nextCommandId()}
     * @return future that completes with the {@code result} object from the CDP response
     */
    public CompletableFuture<JsonObject> registerCommand(long id) {
        CompletableFuture<JsonObject> future = new CompletableFuture<>();
        pendingCommands.put(id, future);

        future.orTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    pendingCommands.remove(id);
                    log.warn("CDP command {} timed out after {} s", id, defaultTimeoutSeconds);
                    return null;
                });

        return future;
    }

    // ── Message routing ───────────────────────────────────────────────────────

    /**
     * Routes an incoming CDP message to either {@link #handleResponse} or
     * {@link #handleEvent} based on the presence of an {@code id} or {@code method} key.
     *
     * @param message parsed CDP message
     */
    public void handleMessage(JsonObject message) {
        if (message.has(CDP_KEY_ID)) {
            handleResponse(message);
        } else if (message.has(CDP_KEY_METHOD)) {
            handleEvent(message);
        } else {
            log.trace("Received unrecognised CDP message (no id or method field)");
        }
    }

    /**
     * Completes (or fails) the pending future associated with a response message.
     *
     * @param message CDP response message
     */
    public void handleResponse(JsonObject message) {
        if (!message.has(CDP_KEY_ID)) {
            return;
        }

        long id = message.get(CDP_KEY_ID).getAsLong();
        CompletableFuture<JsonObject> future = pendingCommands.remove(id);
        if (future == null) {
            log.trace("No pending command for response id={}", id);
            return;
        }

        if (message.has(CDP_KEY_ERROR)) {
            JsonObject error = message.getAsJsonObject(CDP_KEY_ERROR);
            String errorMsg  = error.has(CDP_KEY_MESSAGE)
                    ? error.get(CDP_KEY_MESSAGE).getAsString()
                    : ERROR_UNKNOWN;
            int errorCode    = error.has(CDP_KEY_CODE)
                    ? error.get(CDP_KEY_CODE).getAsInt()
                    : ERROR_CODE_UNKNOWN;

            log.debug("CDP error response for command {}: [{}] {}", id, errorCode, errorMsg);
            future.completeExceptionally(
                    new CDPException("CDP error (code: " + errorCode + "): " + errorMsg));
        } else {
            JsonObject result = message.has(CDP_KEY_RESULT)
                    ? message.getAsJsonObject(CDP_KEY_RESULT)
                    : new JsonObject();
            future.complete(result);
        }
    }

    /**
     * Dispatches a CDP event to all registered subscribers for its method name.
     * Each subscriber is invoked asynchronously to avoid blocking the WebSocket
     * receive thread.
     *
     * @param message CDP event message
     */
    public void handleEvent(JsonObject message) {
        if (!message.has(CDP_KEY_METHOD)) {
            return;
        }

        String method     = message.get(CDP_KEY_METHOD).getAsString();
        JsonObject params = message.has(CDP_KEY_PARAMS)
                ? message.getAsJsonObject(CDP_KEY_PARAMS)
                : new JsonObject();

        List<Consumer<JsonObject>> handlers = eventSubscribers.get(method);
        if (handlers == null || handlers.isEmpty()) {
            return;
        }

        handlers.forEach(handler ->
                CompletableFuture.runAsync(() -> {
                    try {
                        handler.accept(params);
                    } catch (Exception e) {
                        log.error("Unhandled exception in event handler for '{}': {}",
                                method, e.getMessage(), e);
                    }
                })
        );
    }

    // ── Event subscriptions ───────────────────────────────────────────────────

    /**
     * Subscribes to a CDP event by method name.
     *
     * @param eventName fully-qualified event name (e.g. {@code "Page.frameNavigated"})
     * @param handler   consumer called with the event {@code params} object
     */
    public void subscribe(String eventName, Consumer<JsonObject> handler) {
        eventSubscribers
                .computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>())
                .add(handler);
    }

    /**
     * Removes a specific handler for the given event.
     *
     * @param eventName CDP event name
     * @param handler   the exact handler instance to remove
     */
    public void unsubscribe(String eventName, Consumer<JsonObject> handler) {
        List<Consumer<JsonObject>> handlers = eventSubscribers.get(eventName);
        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                eventSubscribers.remove(eventName);
            }
        }
    }

    /**
     * Removes all handlers for the given event.
     *
     * @param eventName CDP event name
     */
    public void unsubscribeAll(String eventName) {
        eventSubscribers.remove(eventName);
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    /**
     * Fails all pending command futures and clears all subscriptions.
     * Must be called when the underlying WebSocket connection is closed.
     */
    public void clear() {
        pendingCommands.values().forEach(future ->
                future.completeExceptionally(new CDPException(ERROR_CLOSED)));
        pendingCommands.clear();
        eventSubscribers.clear();
    }

    // ── Diagnostics ───────────────────────────────────────────────────────────

    /** Returns the number of commands awaiting a response. */
    public int getPendingCommandCount() {
        return pendingCommands.size();
    }

    /** Returns the number of distinct events that have at least one subscriber. */
    public int getEventSubscriptionCount() {
        return eventSubscribers.size();
    }
}
