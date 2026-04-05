package example.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import example.cdp.CDPCommandManager;
import example.exception.CDPException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class NihoniumWebSocketClient extends WebSocketClient {

	private static final Logger log = LoggerFactory.getLogger(NihoniumWebSocketClient.class);

	private final CDPCommandManager commandManager;
	private final Gson gson;
	private final CountDownLatch connectionLatch;

	private volatile boolean connected;
	private volatile Exception connectionError;

	private static final String KEY_ID = "id";
	private static final String KEY_METHOD = "method";
	private static final String KEY_PARAMS = "params";

	public NihoniumWebSocketClient(URI serverUri) {
		this(serverUri, new CDPCommandManager());
	}

	public NihoniumWebSocketClient(URI serverUri, CDPCommandManager commandManager) {
		super(serverUri);
		this.commandManager = commandManager;
		this.gson = new Gson();
		this.connectionLatch = new CountDownLatch(1);
		this.connected = false;
	}

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
		log.info("CDP WebSocket closed by {} — code: {}, reason: {}", remote ? "remote" : "local", code, reason);
		commandManager.clear();
	}

	@Override
	public void onError(Exception ex) {
		log.error("CDP WebSocket error: {}", ex.getMessage(), ex);
		connectionError = ex;
		connectionLatch.countDown();
	}

	public boolean awaitConnection(long timeout, TimeUnit unit) throws InterruptedException {
		boolean achieved = connectionLatch.await(timeout, unit);
		if (connectionError != null) {
			throw new CDPException("CDP WebSocket connection failed", connectionError);
		}
		return achieved && connected;
	}

	public CompletableFuture<JsonObject> sendCommand(String method, JsonObject params) {
		if (!connected) {
			return CompletableFuture.failedFuture(new CDPException("Cannot send command — WebSocket is not connected"));
		}

		long commandId = commandManager.nextCommandId();
		CompletableFuture<JsonObject> future = commandManager.registerCommand(commandId);

		JsonObject command = new JsonObject();
		command.addProperty(KEY_ID, commandId);
		command.addProperty(KEY_METHOD, method);
		if (params != null) {
			command.add(KEY_PARAMS, params);
		}

		log.trace("→ CDP {} (id={})", method, commandId);
		send(gson.toJson(command));
		return future;
	}

	public CompletableFuture<JsonObject> sendCommand(String method) {
		return sendCommand(method, null);
	}

	public void subscribeToEvent(String eventName, Consumer<JsonObject> handler) {
		commandManager.subscribe(eventName, handler);
	}

	public void unsubscribeFromEvent(String eventName, Consumer<JsonObject> handler) {
		commandManager.unsubscribe(eventName, handler);
	}

	public void unsubscribeAllFromEvent(String eventName) {
		commandManager.unsubscribeAll(eventName);
	}

	public boolean isConnected() {
		return connected && !isClosed();
	}

	public CDPCommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public void close() {
		commandManager.clear();
		super.close();
	}
}
