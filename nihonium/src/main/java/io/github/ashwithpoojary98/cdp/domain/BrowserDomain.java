package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;

/**
 * Wrapper for the CDP {@code Browser} and {@code Target} domains.
 *
 * <p>Covers:
 * <ul>
 *   <li>Window bounds (size, position, state) via {@code Browser.*}</li>
 *   <li>Tab/window enumeration and activation via {@code Target.*}</li>
 *   <li>Browser version metadata</li>
 * </ul>
 *
 * @see <a href="https://chromedevtools.github.io/devtools-protocol/tot/Browser/">CDP Browser domain</a>
 * @see <a href="https://chromedevtools.github.io/devtools-protocol/tot/Target/">CDP Target domain</a>
 */
public class BrowserDomain {

    // ── CDP method names ──────────────────────────────────────────────────────

    private static final String CMD_GET_WINDOW_FOR_TARGET = "Browser.getWindowForTarget";
    private static final String CMD_SET_WINDOW_BOUNDS     = "Browser.setWindowBounds";
    private static final String CMD_GET_WINDOW_BOUNDS     = "Browser.getWindowBounds";
    private static final String CMD_GET_VERSION           = "Browser.getVersion";

    private static final String CMD_TARGET_GET_TARGETS    = "Target.getTargets";
    private static final String CMD_TARGET_ACTIVATE       = "Target.activateTarget";
    private static final String CMD_TARGET_CREATE         = "Target.createTarget";
    private static final String CMD_TARGET_GET_INFO       = "Target.getTargetInfo";

    // ── CDP parameter / field names ───────────────────────────────────────────

    private static final String PARAM_TARGET_ID  = "targetId";
    private static final String PARAM_WINDOW_ID  = "windowId";
    private static final String PARAM_BOUNDS     = "bounds";
    private static final String PARAM_URL        = "url";

    private static final String FIELD_WINDOW_ID  = "windowId";
    private static final String FIELD_BOUNDS     = "bounds";

    // ── Window state values ───────────────────────────────────────────────────

    private static final String WINDOW_STATE_NORMAL     = "normal";
    private static final String WINDOW_STATE_MAXIMIZED  = "maximized";
    private static final String WINDOW_STATE_MINIMIZED  = "minimized";
    private static final String WINDOW_STATE_FULLSCREEN = "fullscreen";
    private static final String FIELD_WINDOW_STATE      = "windowState";
    private static final String FIELD_WIDTH             = "width";
    private static final String FIELD_HEIGHT            = "height";
    private static final String FIELD_LEFT              = "left";
    private static final String FIELD_TOP               = "top";

    // ── Initial page ──────────────────────────────────────────────────────────

    private static final String INITIAL_PAGE_URL = "about:blank";

    // ─────────────────────────────────────────────────────────────────────────

    private final NihoniumWebSocketClient wsClient;

    public BrowserDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    // ── Window resolution helpers ─────────────────────────────────────────────

    /**
     * Returns the CDP window ID for the given target.
     *
     * @param targetId the CDP target ID of the current page
     * @return future completing with the {@code windowId}
     */
    public CompletableFuture<Integer> getWindowId(String targetId) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_TARGET_ID, targetId);
        return wsClient.sendCommand(CMD_GET_WINDOW_FOR_TARGET, params)
                .thenApply(result -> result.get(FIELD_WINDOW_ID).getAsInt());
    }

    // ── Window bounds ─────────────────────────────────────────────────────────

    /**
     * Returns the bounds (position, size, and state) of the given window.
     *
     * @param targetId the CDP target ID of the current page
     * @return future completing with the {@code bounds} JSON object
     *         ({@code left}, {@code top}, {@code width}, {@code height}, {@code windowState})
     */
    public CompletableFuture<JsonObject> getWindowBounds(String targetId) {
        return getWindowId(targetId).thenCompose(windowId -> {
            JsonObject params = new JsonObject();
            params.addProperty(PARAM_WINDOW_ID, windowId);
            return wsClient.sendCommand(CMD_GET_WINDOW_BOUNDS, params)
                    .thenApply(r -> r.getAsJsonObject(FIELD_BOUNDS));
        });
    }

    /**
     * Sets the bounds (position, size, or state) of the given window.
     *
     * @param targetId the CDP target ID of the current page
     * @param bounds   JSON object with any of {@code left}, {@code top},
     *                 {@code width}, {@code height}, {@code windowState}
     * @return future completing when the bounds have been applied
     */
    public CompletableFuture<JsonObject> setWindowBounds(String targetId, JsonObject bounds) {
        return getWindowId(targetId).thenCompose(windowId -> {
            JsonObject params = new JsonObject();
            params.addProperty(PARAM_WINDOW_ID, windowId);
            params.add(PARAM_BOUNDS, bounds);
            return wsClient.sendCommand(CMD_SET_WINDOW_BOUNDS, params);
        });
    }

    // ── Window state ──────────────────────────────────────────────────────────

    /**
     * Maximises the browser window.
     *
     * @param targetId the CDP target ID of the current page
     * @return future completing when the window is maximised
     */
    public CompletableFuture<JsonObject> maximizeWindow(String targetId) {
        JsonObject bounds = new JsonObject();
        bounds.addProperty(FIELD_WINDOW_STATE, WINDOW_STATE_MAXIMIZED);
        return setWindowBounds(targetId, bounds);
    }

    /**
     * Minimises the browser window.
     *
     * @param targetId the CDP target ID of the current page
     * @return future completing when the window is minimised
     */
    public CompletableFuture<JsonObject> minimizeWindow(String targetId) {
        JsonObject bounds = new JsonObject();
        bounds.addProperty(FIELD_WINDOW_STATE, WINDOW_STATE_MINIMIZED);
        return setWindowBounds(targetId, bounds);
    }

    /**
     * Puts the browser window into fullscreen mode.
     *
     * @param targetId the CDP target ID of the current page
     * @return future completing when fullscreen is active
     */
    public CompletableFuture<JsonObject> fullscreenWindow(String targetId) {
        JsonObject bounds = new JsonObject();
        bounds.addProperty(FIELD_WINDOW_STATE, WINDOW_STATE_FULLSCREEN);
        return setWindowBounds(targetId, bounds);
    }

    /**
     * Restores the window to the {@code normal} state (not maximised/minimised/fullscreen).
     *
     * @param targetId the CDP target ID of the current page
     * @param width    width in pixels
     * @param height   height in pixels
     * @return future completing when the window has been resized
     */
    public CompletableFuture<JsonObject> setWindowSize(String targetId, int width, int height) {
        JsonObject bounds = new JsonObject();
        bounds.addProperty(FIELD_WINDOW_STATE, WINDOW_STATE_NORMAL);
        bounds.addProperty(FIELD_WIDTH,  width);
        bounds.addProperty(FIELD_HEIGHT, height);
        return setWindowBounds(targetId, bounds);
    }

    /**
     * Moves the window to the given screen position.
     *
     * @param targetId the CDP target ID of the current page
     * @param left     x coordinate in pixels from the left of the screen
     * @param top      y coordinate in pixels from the top of the screen
     * @return future completing when the window has been moved
     */
    public CompletableFuture<JsonObject> setWindowPosition(String targetId, int left, int top) {
        JsonObject bounds = new JsonObject();
        bounds.addProperty(FIELD_WINDOW_STATE, WINDOW_STATE_NORMAL);
        bounds.addProperty(FIELD_LEFT, left);
        bounds.addProperty(FIELD_TOP,  top);
        return setWindowBounds(targetId, bounds);
    }

    // ── Target / tab management ───────────────────────────────────────────────

    /**
     * Returns information about all browser targets (tabs, workers, etc.).
     *
     * <p>The response contains a {@code targetInfos} array.  Each element has
     * {@code targetId}, {@code type} ({@code "page"}, {@code "worker"}, etc.),
     * {@code url}, and {@code title}.
     *
     * @return future completing with the CDP response
     */
    public CompletableFuture<JsonObject> getTargets() {
        return wsClient.sendCommand(CMD_TARGET_GET_TARGETS);
    }

    /**
     * Returns information about a specific target.
     *
     * @param targetId the CDP target ID to query
     * @return future completing with the CDP response (contains {@code targetInfo})
     */
    public CompletableFuture<JsonObject> getTargetInfo(String targetId) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_TARGET_ID, targetId);
        return wsClient.sendCommand(CMD_TARGET_GET_INFO, params);
    }

    /**
     * Brings the specified target to the foreground (activates that tab/window).
     *
     * @param targetId the CDP target ID to activate
     * @return future completing when the target is activated
     */
    public CompletableFuture<JsonObject> activateTarget(String targetId) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_TARGET_ID, targetId);
        return wsClient.sendCommand(CMD_TARGET_ACTIVATE, params);
    }

    /**
     * Creates a new browser tab/window navigated to {@code about:blank}.
     *
     * @return future completing with the CDP response (contains {@code targetId})
     */
    public CompletableFuture<JsonObject> createTarget() {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_URL, INITIAL_PAGE_URL);
        return wsClient.sendCommand(CMD_TARGET_CREATE, params);
    }

    // ── Browser metadata ──────────────────────────────────────────────────────

    /**
     * Returns the browser version and protocol version information.
     *
     * @return future completing with the CDP response
     */
    public CompletableFuture<JsonObject> getVersion() {
        return wsClient.sendCommand(CMD_GET_VERSION);
    }

    // ── Legacy compatibility (kept for any callers using the old API) ──────────

    /** @deprecated Use {@link #maximizeWindow(String)} with an explicit targetId. */
    @Deprecated(forRemoval = true)
    public CompletableFuture<JsonObject> maximizeCurrentWindow() {
        throw new UnsupportedOperationException(
                "maximizeCurrentWindow() requires a targetId. Use maximizeWindow(targetId) instead.");
    }

    /** @deprecated Use {@link #minimizeWindow(String)} with an explicit targetId. */
    @Deprecated(forRemoval = true)
    public CompletableFuture<JsonObject> minimizeCurrentWindow() {
        throw new UnsupportedOperationException(
                "minimizeCurrentWindow() requires a targetId. Use minimizeWindow(targetId) instead.");
    }

    /** @deprecated Use {@link #fullscreenWindow(String)} with an explicit targetId. */
    @Deprecated(forRemoval = true)
    public CompletableFuture<JsonObject> fullscreenCurrentWindow() {
        throw new UnsupportedOperationException(
                "fullscreenCurrentWindow() requires a targetId. Use fullscreenWindow(targetId) instead.");
    }
}
