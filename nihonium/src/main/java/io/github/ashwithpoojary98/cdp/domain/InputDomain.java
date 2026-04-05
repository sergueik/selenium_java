package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for the CDP {@code Input} domain.
 *
 * <p>Provides methods for simulating mouse clicks, keyboard events, and text input.
 * All string values are declared as named constants to avoid magic literals and to
 * serve as documentation of the underlying CDP protocol.
 *
 * @see <a href="https://chromedevtools.github.io/devtools-protocol/tot/Input/">CDP Input domain</a>
 */
public class InputDomain {

    // ── CDP method names ──────────────────────────────────────────────────────

    private static final String CMD_DISPATCH_MOUSE_EVENT = "Input.dispatchMouseEvent";
    private static final String CMD_DISPATCH_KEY_EVENT   = "Input.dispatchKeyEvent";
    private static final String CMD_INSERT_TEXT          = "Input.insertText";

    // ── Mouse event types (Input.dispatchMouseEvent) ──────────────────────────

    private static final String MOUSE_PRESSED  = "mousePressed";
    private static final String MOUSE_RELEASED = "mouseReleased";
    private static final String MOUSE_MOVED    = "mouseMoved";

    // ── Keyboard event types (Input.dispatchKeyEvent) ─────────────────────────

    private static final String KEY_DOWN = "keyDown";
    private static final String KEY_UP   = "keyUp";

    // ── Mouse buttons ─────────────────────────────────────────────────────────

    /** No mouse button (used for move events). */
    public static final String BUTTON_NONE   = "none";
    public static final String BUTTON_LEFT   = "left";
    public static final String BUTTON_RIGHT  = "right";
    public static final String BUTTON_MIDDLE = "middle";

    // ── Modifier bitmasks (matches CDP spec) ──────────────────────────────────

    /** No modifier keys active. */
    public static final int MODIFIER_NONE  = 0;
    /** Alt / Option key. */
    public static final int MODIFIER_ALT   = 1;
    /** Control key. */
    public static final int MODIFIER_CTRL  = 2;
    /** Meta / Command key. */
    public static final int MODIFIER_META  = 4;
    /** Shift key. */
    public static final int MODIFIER_SHIFT = 8;

    // ── CDP parameter keys ────────────────────────────────────────────────────

    private static final String PARAM_TYPE        = "type";
    private static final String PARAM_X           = "x";
    private static final String PARAM_Y           = "y";
    private static final String PARAM_BUTTON      = "button";
    private static final String PARAM_CLICK_COUNT = "clickCount";
    private static final String PARAM_KEY         = "key";
    private static final String PARAM_CODE        = "code";
    private static final String PARAM_MODIFIERS   = "modifiers";
    private static final String PARAM_TEXT        = "text";

    // ─────────────────────────────────────────────────────────────────────────

    private final NihoniumWebSocketClient wsClient;

    public InputDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    // ── Mouse ─────────────────────────────────────────────────────────────────

    /**
     * Dispatches a mouse event.
     *
     * @param type       event type — use one of the {@code MOUSE_*} constants
     * @param x          viewport x coordinate
     * @param y          viewport y coordinate
     * @param button     mouse button — use one of the {@code BUTTON_*} constants;
     *                   pass {@code null} or {@link #BUTTON_NONE} for move events
     * @param clickCount number of clicks (1 for single, 2 for double)
     * @return future that completes when the event is dispatched
     */
    public CompletableFuture<JsonObject> dispatchMouseEvent(
            String type, double x, double y, String button, int clickCount) {

        JsonObject params = new JsonObject();
        params.addProperty(PARAM_TYPE,   type);
        params.addProperty(PARAM_X,      x);
        params.addProperty(PARAM_Y,      y);
        // CDP requires "button" field always; default to "none" for move events
        params.addProperty(PARAM_BUTTON, button != null ? button : BUTTON_NONE);

        if (clickCount > 0) {
            params.addProperty(PARAM_CLICK_COUNT, clickCount);
        }

        return wsClient.sendCommand(CMD_DISPATCH_MOUSE_EVENT, params);
    }

    /**
     * Moves the mouse cursor to the given coordinates.
     *
     * @param x viewport x
     * @param y viewport y
     * @return future that completes when the event is dispatched
     */
    public CompletableFuture<JsonObject> mouseMove(double x, double y) {
        return dispatchMouseEvent(MOUSE_MOVED, x, y, BUTTON_NONE, 0);
    }

    /**
     * Dispatches a mouse click (move → press → release) with the specified button.
     *
     * @param x      viewport x
     * @param y      viewport y
     * @param button mouse button constant (e.g. {@link #BUTTON_LEFT})
     * @return future that completes when the full click sequence is dispatched
     */
    public CompletableFuture<JsonObject> click(double x, double y, String button) {
        return mouseMove(x, y)
                .thenCompose(v -> dispatchMouseEvent(MOUSE_PRESSED,  x, y, button, 1))
                .thenCompose(v -> dispatchMouseEvent(MOUSE_RELEASED, x, y, button, 1));
    }

    /**
     * Dispatches a left-button mouse click at the given coordinates.
     *
     * @param x viewport x
     * @param y viewport y
     * @return future that completes when the click sequence is dispatched
     */
    public CompletableFuture<JsonObject> click(double x, double y) {
        return click(x, y, BUTTON_LEFT);
    }

    /**
     * Dispatches a double-click at the given coordinates.
     *
     * @param x viewport x
     * @param y viewport y
     * @return future that completes when the double-click sequence is dispatched
     */
    public CompletableFuture<JsonObject> doubleClick(double x, double y) {
        return mouseMove(x, y)
                .thenCompose(v -> dispatchMouseEvent(MOUSE_PRESSED,  x, y, BUTTON_LEFT, 1))
                .thenCompose(v -> dispatchMouseEvent(MOUSE_RELEASED, x, y, BUTTON_LEFT, 1))
                .thenCompose(v -> dispatchMouseEvent(MOUSE_PRESSED,  x, y, BUTTON_LEFT, 2))
                .thenCompose(v -> dispatchMouseEvent(MOUSE_RELEASED, x, y, BUTTON_LEFT, 2));
    }

    // ── Keyboard ──────────────────────────────────────────────────────────────

    /**
     * Dispatches a keyboard event.
     *
     * @param type      event type — use one of the {@code KEY_*} constants
     * @param key       key value (e.g. {@code "Enter"}, {@code "Backspace"}, {@code "a"})
     * @param code      key code (e.g. {@code "Enter"}, {@code "Backspace"}, {@code "KeyA"});
     *                  may be {@code null}
     * @param modifiers modifier bitmask — OR the {@code MODIFIER_*} constants together
     * @return future that completes when the event is dispatched
     */
    public CompletableFuture<JsonObject> dispatchKeyEvent(
            String type, String key, String code, int modifiers) {

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

    /**
     * Dispatches a key-down followed by key-up for the given key.
     *
     * @param key key value (e.g. {@code "Enter"}, {@code "Tab"})
     * @return future that completes when both events are dispatched
     */
    public CompletableFuture<JsonObject> pressKey(String key) {
        return dispatchKeyEvent(KEY_DOWN, key, null, MODIFIER_NONE)
                .thenCompose(v -> dispatchKeyEvent(KEY_UP, key, null, MODIFIER_NONE));
    }

    /**
     * Inserts text directly into the focused element via {@code Input.insertText}.
     *
     * <p>This is the fastest way to fill inputs and is equivalent to Playwright's
     * {@code fill()} — it does not simulate individual key events.
     *
     * @param text text to insert; must not be {@code null}
     * @return future that completes when the text is inserted
     */
    public CompletableFuture<JsonObject> insertText(String text) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_TEXT, text);
        return wsClient.sendCommand(CMD_INSERT_TEXT, params);
    }

    /**
     * Types text character by character, firing {@code keyDown} and {@code keyUp}
     * events for each character. Slower than {@link #insertText} but fires key events.
     *
     * @param text text to type
     * @return future that completes when all characters have been typed
     */
    public CompletableFuture<Void> typeText(String text) {
        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);
        for (char c : text.toCharArray()) {
            String key = String.valueOf(c);
            chain = chain
                    .thenCompose(v -> dispatchKeyEvent(KEY_DOWN, key, null, MODIFIER_NONE))
                    .thenCompose(v -> dispatchKeyEvent(KEY_UP,   key, null, MODIFIER_NONE))
                    .thenApply(v -> null);
        }
        return chain;
    }
}
