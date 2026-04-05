package io.github.ashwithpoojary98.cdp.domain;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Wrapper for the CDP {@code Network} domain.
 *
 * <p>Provides network monitoring, header overrides, cache control, and
 * full cookie management matching the Selenium {@code WebDriver.Options} cookie API.
 *
 * @see <a href="https://chromedevtools.github.io/devtools-protocol/tot/Network/">CDP Network domain</a>
 */
public class NetworkDomain {

    // ── CDP method names ──────────────────────────────────────────────────────

    private static final String CMD_ENABLE                 = "Network.enable";
    private static final String CMD_DISABLE                = "Network.disable";
    private static final String CMD_GET_COOKIES            = "Network.getCookies";
    private static final String CMD_SET_COOKIE             = "Network.setCookie";
    private static final String CMD_DELETE_COOKIES         = "Network.deleteCookies";
    private static final String CMD_CLEAR_BROWSER_COOKIES  = "Network.clearBrowserCookies";
    private static final String CMD_SET_USER_AGENT         = "Network.setUserAgentOverride";
    private static final String CMD_SET_CACHE_DISABLED     = "Network.setCacheDisabled";
    private static final String CMD_SET_EXTRA_HTTP_HEADERS = "Network.setExtraHTTPHeaders";
    private static final String CMD_GET_RESPONSE_BODY      = "Network.getResponseBody";

    private static final String EVENT_REQUEST_WILL_BE_SENT = "Network.requestWillBeSent";
    private static final String EVENT_LOADING_FINISHED     = "Network.loadingFinished";
    private static final String EVENT_LOADING_FAILED       = "Network.loadingFailed";

    // ── CDP parameter / field names ───────────────────────────────────────────

    private static final String PARAM_USER_AGENT      = "userAgent";
    private static final String PARAM_CACHE_DISABLED  = "cacheDisabled";
    private static final String PARAM_HEADERS         = "headers";
    private static final String PARAM_REQUEST_ID      = "requestId";
    private static final String PARAM_NAME            = "name";
    private static final String PARAM_VALUE           = "value";
    private static final String PARAM_DOMAIN          = "domain";
    private static final String PARAM_PATH            = "path";
    private static final String PARAM_SECURE          = "secure";
    private static final String PARAM_HTTP_ONLY       = "httpOnly";
    private static final String PARAM_EXPIRES         = "expires";

    // ─────────────────────────────────────────────────────────────────────────

    private final NihoniumWebSocketClient wsClient;

    public NetworkDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    // ── Domain lifecycle ──────────────────────────────────────────────────────

    /** Enables network tracking so events and cookie APIs become available. */
    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand(CMD_ENABLE);
    }

    /** Disables network tracking. */
    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand(CMD_DISABLE);
    }

    // ── Cookie management ─────────────────────────────────────────────────────

    /**
     * Returns all cookies visible to the current page.
     *
     * <p>The response contains a {@code cookies} JSON array.  Each element has
     * fields: {@code name}, {@code value}, {@code domain}, {@code path},
     * {@code expires} (Unix timestamp, {@code -1} for session cookies),
     * {@code secure}, {@code httpOnly}.
     *
     * @return future completing with the CDP response
     */
    public CompletableFuture<JsonObject> getCookies() {
        return wsClient.sendCommand(CMD_GET_COOKIES);
    }

    /**
     * Sets (creates or updates) a single cookie.
     *
     * @param name     cookie name; must not be {@code null}
     * @param value    cookie value; must not be {@code null}
     * @param domain   cookie domain, or {@code null} to inherit from the current URL
     * @param path     cookie path, or {@code null} (defaults to {@code "/"})
     * @param secure   whether the cookie is Secure
     * @param httpOnly whether the cookie is HttpOnly
     * @param expires  expiry as a Unix timestamp in seconds, or {@code -1} for a session cookie
     * @return future completing with the CDP response (contains {@code success} boolean)
     */
    public CompletableFuture<JsonObject> setCookie(
            String name, String value,
            String domain, String path,
            boolean secure, boolean httpOnly,
            long expires) {

        JsonObject params = new JsonObject();
        params.addProperty(PARAM_NAME,  name);
        params.addProperty(PARAM_VALUE, value);

        if (domain != null) {
            params.addProperty(PARAM_DOMAIN, domain);
        }
        if (path != null) {
            params.addProperty(PARAM_PATH, path);
        }
        params.addProperty(PARAM_SECURE,    secure);
        params.addProperty(PARAM_HTTP_ONLY, httpOnly);
        if (expires > 0) {
            params.addProperty(PARAM_EXPIRES, expires);
        }

        return wsClient.sendCommand(CMD_SET_COOKIE, params);
    }

    /**
     * Deletes cookies matching the given name (and optionally domain).
     *
     * @param name   cookie name to delete; must not be {@code null}
     * @param domain restrict deletion to this domain, or {@code null} to delete across all domains
     * @return future completing when the deletion is complete
     */
    public CompletableFuture<JsonObject> deleteCookies(String name, String domain) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_NAME, name);
        if (domain != null) {
            params.addProperty(PARAM_DOMAIN, domain);
        }
        return wsClient.sendCommand(CMD_DELETE_COOKIES, params);
    }

    /**
     * Clears all cookies stored in the browser.
     *
     * @return future completing when all cookies have been cleared
     */
    public CompletableFuture<JsonObject> clearBrowserCookies() {
        return wsClient.sendCommand(CMD_CLEAR_BROWSER_COOKIES);
    }

    // ── Network overrides ─────────────────────────────────────────────────────

    /**
     * Overrides the browser's {@code User-Agent} header.
     *
     * @param userAgent new user agent string
     * @return future completing when the override is applied
     */
    public CompletableFuture<JsonObject> setUserAgentOverride(String userAgent) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_USER_AGENT, userAgent);
        return wsClient.sendCommand(CMD_SET_USER_AGENT, params);
    }

    /**
     * Enables or disables the browser cache.
     *
     * @param cacheDisabled {@code true} to disable caching
     * @return future completing when the setting is applied
     */
    public CompletableFuture<JsonObject> setCacheDisabled(boolean cacheDisabled) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_CACHE_DISABLED, cacheDisabled);
        return wsClient.sendCommand(CMD_SET_CACHE_DISABLED, params);
    }

    /**
     * Adds extra HTTP headers to every request.
     *
     * @param headers JSON object where keys are header names and values are header values
     * @return future completing when the headers are applied
     */
    public CompletableFuture<JsonObject> setExtraHTTPHeaders(JsonObject headers) {
        JsonObject params = new JsonObject();
        params.add(PARAM_HEADERS, headers);
        return wsClient.sendCommand(CMD_SET_EXTRA_HTTP_HEADERS, params);
    }

    /**
     * Fetches the response body for a completed network request.
     *
     * @param requestId CDP network request ID
     * @return future completing with the response body
     */
    public CompletableFuture<JsonObject> getResponseBody(String requestId) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_REQUEST_ID, requestId);
        return wsClient.sendCommand(CMD_GET_RESPONSE_BODY, params);
    }

    // ── Event subscriptions ───────────────────────────────────────────────────

    /** Subscribes to {@code Network.requestWillBeSent} events. */
    public void subscribeToRequestWillBeSent(Consumer<JsonObject> handler) {
        wsClient.subscribeToEvent(EVENT_REQUEST_WILL_BE_SENT, handler);
    }

    /** Subscribes to {@code Network.loadingFinished} events. */
    public void subscribeToLoadingFinished(Consumer<JsonObject> handler) {
        wsClient.subscribeToEvent(EVENT_LOADING_FINISHED, handler);
    }

    /** Subscribes to {@code Network.loadingFailed} events. */
    public void subscribeToLoadingFailed(Consumer<JsonObject> handler) {
        wsClient.subscribeToEvent(EVENT_LOADING_FAILED, handler);
    }
}
