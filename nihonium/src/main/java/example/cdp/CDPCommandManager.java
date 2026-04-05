package example.cdp;

import com.google.gson.JsonObject;

import example.exception.CDPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class CDPCommandManager {

	private static final Logger log = LoggerFactory.getLogger(CDPCommandManager.class);

	public static final long DEFAULT_TIMEOUT_SECONDS = 30L;

	private static final String CDP_KEY_ID = "id";
	private static final String CDP_KEY_METHOD = "method";
	private static final String CDP_KEY_RESULT = "result";
	private static final String CDP_KEY_ERROR = "error";
	private static final String CDP_KEY_PARAMS = "params";
	private static final String CDP_KEY_MESSAGE = "message";
	private static final String CDP_KEY_CODE = "code";

	private static final String ERROR_UNKNOWN = "Unknown CDP error";
	private static final String ERROR_CLOSED = "CDP connection closed";
	private static final int ERROR_CODE_UNKNOWN = -1;

	private final AtomicLong commandIdGenerator = new AtomicLong(0);
	private final ConcurrentHashMap<Long, CompletableFuture<JsonObject>> pendingCommands = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, List<Consumer<JsonObject>>> eventSubscribers = new ConcurrentHashMap<>();

	private final long defaultTimeoutSeconds;

	public CDPCommandManager() {
		this(DEFAULT_TIMEOUT_SECONDS);
	}

	public CDPCommandManager(long defaultTimeoutSeconds) {
		this.defaultTimeoutSeconds = defaultTimeoutSeconds;
	}

	public long nextCommandId() {
		return commandIdGenerator.incrementAndGet();
	}

	public CompletableFuture<JsonObject> registerCommand(long id) {
		CompletableFuture<JsonObject> future = new CompletableFuture<>();
		pendingCommands.put(id, future);

		future.orTimeout(defaultTimeoutSeconds, TimeUnit.SECONDS).exceptionally(ex -> {
			pendingCommands.remove(id);
			log.warn("CDP command {} timed out after {} s", id, defaultTimeoutSeconds);
			return null;
		});

		return future;
	}

	public void handleMessage(JsonObject message) {
		if (message.has(CDP_KEY_ID)) {
			handleResponse(message);
		} else if (message.has(CDP_KEY_METHOD)) {
			handleEvent(message);
		} else {
			log.trace("Received unrecognised CDP message (no id or method field)");
		}
	}

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
			String errorMsg = error.has(CDP_KEY_MESSAGE) ? error.get(CDP_KEY_MESSAGE).getAsString() : ERROR_UNKNOWN;
			int errorCode = error.has(CDP_KEY_CODE) ? error.get(CDP_KEY_CODE).getAsInt() : ERROR_CODE_UNKNOWN;

			log.debug("CDP error response for command {}: [{}] {}", id, errorCode, errorMsg);
			future.completeExceptionally(new CDPException("CDP error (code: " + errorCode + "): " + errorMsg));
		} else {
			JsonObject result = message.has(CDP_KEY_RESULT) ? message.getAsJsonObject(CDP_KEY_RESULT)
					: new JsonObject();
			future.complete(result);
		}
	}

	public void handleEvent(JsonObject message) {
		if (!message.has(CDP_KEY_METHOD)) {
			return;
		}

		String method = message.get(CDP_KEY_METHOD).getAsString();
		JsonObject params = message.has(CDP_KEY_PARAMS) ? message.getAsJsonObject(CDP_KEY_PARAMS) : new JsonObject();

		List<Consumer<JsonObject>> handlers = eventSubscribers.get(method);
		if (handlers == null || handlers.isEmpty()) {
			return;
		}

		handlers.forEach(handler -> CompletableFuture.runAsync(() -> {
			try {
				handler.accept(params);
			} catch (Exception e) {
				log.error("Unhandled exception in event handler for '{}': {}", method, e.getMessage(), e);
			}
		}));
	}

	public void subscribe(String eventName, Consumer<JsonObject> handler) {
		eventSubscribers.computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>()).add(handler);
	}

	public void unsubscribe(String eventName, Consumer<JsonObject> handler) {
		List<Consumer<JsonObject>> handlers = eventSubscribers.get(eventName);
		if (handlers != null) {
			handlers.remove(handler);
			if (handlers.isEmpty()) {
				eventSubscribers.remove(eventName);
			}
		}
	}

	public void unsubscribeAll(String eventName) {
		eventSubscribers.remove(eventName);
	}

	public void clear() {
		pendingCommands.values().forEach(future -> future.completeExceptionally(new CDPException(ERROR_CLOSED)));
		pendingCommands.clear();
		eventSubscribers.clear();
	}

	public int getPendingCommandCount() {
		return pendingCommands.size();
	}

	public int getEventSubscriptionCount() {
		return eventSubscribers.size();
	}
}
