package example.cdp.domain;

import com.google.gson.JsonObject;

import example.websocket.NihoniumWebSocketClient;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class NetworkDomain {

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

    private final NihoniumWebSocketClient wsClient;

    public NetworkDomain(NihoniumWebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    public CompletableFuture<JsonObject> enable() {
        return wsClient.sendCommand(CMD_ENABLE);
    }

    public CompletableFuture<JsonObject> disable() {
        return wsClient.sendCommand(CMD_DISABLE);
    }

    public CompletableFuture<JsonObject> getCookies() {
        return wsClient.sendCommand(CMD_GET_COOKIES);
    }

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

    public CompletableFuture<JsonObject> deleteCookies(String name, String domain) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_NAME, name);
        if (domain != null) {
            params.addProperty(PARAM_DOMAIN, domain);
        }
        return wsClient.sendCommand(CMD_DELETE_COOKIES, params);
    }

    public CompletableFuture<JsonObject> clearBrowserCookies() {
        return wsClient.sendCommand(CMD_CLEAR_BROWSER_COOKIES);
    }

    public CompletableFuture<JsonObject> setUserAgentOverride(String userAgent) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_USER_AGENT, userAgent);
        return wsClient.sendCommand(CMD_SET_USER_AGENT, params);
    }

    public CompletableFuture<JsonObject> setCacheDisabled(boolean cacheDisabled) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_CACHE_DISABLED, cacheDisabled);
        return wsClient.sendCommand(CMD_SET_CACHE_DISABLED, params);
    }

    public CompletableFuture<JsonObject> setExtraHTTPHeaders(JsonObject headers) {
        JsonObject params = new JsonObject();
        params.add(PARAM_HEADERS, headers);
        return wsClient.sendCommand(CMD_SET_EXTRA_HTTP_HEADERS, params);
    }

    public CompletableFuture<JsonObject> getResponseBody(String requestId) {
        JsonObject params = new JsonObject();
        params.addProperty(PARAM_REQUEST_ID, requestId);
        return wsClient.sendCommand(CMD_GET_RESPONSE_BODY, params);
    }

    public void subscribeToRequestWillBeSent(Consumer<JsonObject> handler) {
        wsClient.subscribeToEvent(EVENT_REQUEST_WILL_BE_SENT, handler);
    }

    public void subscribeToLoadingFinished(Consumer<JsonObject> handler) {
        wsClient.subscribeToEvent(EVENT_LOADING_FINISHED, handler);
    }

    public void subscribeToLoadingFailed(Consumer<JsonObject> handler) {
        wsClient.subscribeToEvent(EVENT_LOADING_FAILED, handler);
    }
}
