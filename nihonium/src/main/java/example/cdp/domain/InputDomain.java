package example.cdp.domain;

import com.google.gson.JsonObject;

import example.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

public class InputDomain {

	private static final String CMD_DISPATCH_MOUSE_EVENT = "Input.dispatchMouseEvent";
	private static final String CMD_DISPATCH_KEY_EVENT = "Input.dispatchKeyEvent";
	private static final String CMD_INSERT_TEXT = "Input.insertText";

	private static final String MOUSE_PRESSED = "mousePressed";
	private static final String MOUSE_RELEASED = "mouseReleased";
	private static final String MOUSE_MOVED = "mouseMoved";

	private static final String KEY_DOWN = "keyDown";
	private static final String KEY_UP = "keyUp";

	public static final String BUTTON_NONE = "none";
	public static final String BUTTON_LEFT = "left";
	public static final String BUTTON_RIGHT = "right";
	public static final String BUTTON_MIDDLE = "middle";

	public static final int MODIFIER_NONE = 0;
	public static final int MODIFIER_ALT = 1;
	public static final int MODIFIER_CTRL = 2;
	public static final int MODIFIER_META = 4;
	public static final int MODIFIER_SHIFT = 8;
	private static final String PARAM_TYPE = "type";
	private static final String PARAM_X = "x";
	private static final String PARAM_Y = "y";
	private static final String PARAM_BUTTON = "button";
	private static final String PARAM_CLICK_COUNT = "clickCount";
	private static final String PARAM_KEY = "key";
	private static final String PARAM_CODE = "code";
	private static final String PARAM_MODIFIERS = "modifiers";
	private static final String PARAM_TEXT = "text";

	private final NihoniumWebSocketClient wsClient;

	public InputDomain(NihoniumWebSocketClient wsClient) {
		this.wsClient = wsClient;
	}

	public CompletableFuture<JsonObject> dispatchMouseEvent(String type, double x, double y, String button,
			int clickCount) {

		JsonObject params = new JsonObject();
		params.addProperty(PARAM_TYPE, type);
		params.addProperty(PARAM_X, x);
		params.addProperty(PARAM_Y, y);
		// CDP requires "button" field always; default to "none" for move events
		params.addProperty(PARAM_BUTTON, button != null ? button : BUTTON_NONE);

		if (clickCount > 0) {
			params.addProperty(PARAM_CLICK_COUNT, clickCount);
		}

		return wsClient.sendCommand(CMD_DISPATCH_MOUSE_EVENT, params);
	}

	public CompletableFuture<JsonObject> mouseMove(double x, double y) {
		return dispatchMouseEvent(MOUSE_MOVED, x, y, BUTTON_NONE, 0);
	}

	public CompletableFuture<JsonObject> click(double x, double y, String button) {
		return mouseMove(x, y).thenCompose(v -> dispatchMouseEvent(MOUSE_PRESSED, x, y, button, 1))
				.thenCompose(v -> dispatchMouseEvent(MOUSE_RELEASED, x, y, button, 1));
	}

	public CompletableFuture<JsonObject> click(double x, double y) {
		return click(x, y, BUTTON_LEFT);
	}

	public CompletableFuture<JsonObject> doubleClick(double x, double y) {
		return mouseMove(x, y).thenCompose(v -> dispatchMouseEvent(MOUSE_PRESSED, x, y, BUTTON_LEFT, 1))
				.thenCompose(v -> dispatchMouseEvent(MOUSE_RELEASED, x, y, BUTTON_LEFT, 1))
				.thenCompose(v -> dispatchMouseEvent(MOUSE_PRESSED, x, y, BUTTON_LEFT, 2))
				.thenCompose(v -> dispatchMouseEvent(MOUSE_RELEASED, x, y, BUTTON_LEFT, 2));
	}

	public CompletableFuture<JsonObject> dispatchKeyEvent(String type, String key, String code, int modifiers) {

		JsonObject params = new JsonObject();
		params.addProperty(PARAM_TYPE, type);

		if (key != null) {
			params.addProperty(PARAM_KEY, key);
		}
		if (code != null) {
			params.addProperty(PARAM_CODE, code);
		}
		if (modifiers != MODIFIER_NONE) {
			params.addProperty(PARAM_MODIFIERS, modifiers);
		}

		return wsClient.sendCommand(CMD_DISPATCH_KEY_EVENT, params);
	}

	public CompletableFuture<JsonObject> pressKey(String key) {
		return dispatchKeyEvent(KEY_DOWN, key, null, MODIFIER_NONE)
				.thenCompose(v -> dispatchKeyEvent(KEY_UP, key, null, MODIFIER_NONE));
	}

	public CompletableFuture<JsonObject> insertText(String text) {
		JsonObject params = new JsonObject();
		params.addProperty(PARAM_TEXT, text);
		return wsClient.sendCommand(CMD_INSERT_TEXT, params);
	}

	public CompletableFuture<Void> typeText(String text) {
		CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);
		for (char c : text.toCharArray()) {
			String key = String.valueOf(c);
			chain = chain.thenCompose(v -> dispatchKeyEvent(KEY_DOWN, key, null, MODIFIER_NONE))
					.thenCompose(v -> dispatchKeyEvent(KEY_UP, key, null, MODIFIER_NONE)).thenApply(v -> null);
		}
		return chain;
	}
}
