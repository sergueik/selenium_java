package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Network {

    private jpuppeteer.cdp.CDPSession session;

    public Network(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Tells whether clearing browser cache is supported.
    */
    public jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCacheResponse canClearBrowserCache(int timeout) throws Exception {
        return session.send("Network.canClearBrowserCache", null, jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCacheResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCacheResponse> asyncCanClearBrowserCache() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.canClearBrowserCache");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCacheResponse>(future, jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCacheResponse.class);
    }

    /**
    * Tells whether clearing browser cookies is supported.
    */
    public jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCookiesResponse canClearBrowserCookies(int timeout) throws Exception {
        return session.send("Network.canClearBrowserCookies", null, jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCookiesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCookiesResponse> asyncCanClearBrowserCookies() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.canClearBrowserCookies");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCookiesResponse>(future, jpuppeteer.cdp.cdp.entity.network.CanClearBrowserCookiesResponse.class);
    }

    /**
    * Tells whether emulation of network conditions is supported.
    */
    public jpuppeteer.cdp.cdp.entity.network.CanEmulateNetworkConditionsResponse canEmulateNetworkConditions(int timeout) throws Exception {
        return session.send("Network.canEmulateNetworkConditions", null, jpuppeteer.cdp.cdp.entity.network.CanEmulateNetworkConditionsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.CanEmulateNetworkConditionsResponse> asyncCanEmulateNetworkConditions() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.canEmulateNetworkConditions");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.CanEmulateNetworkConditionsResponse>(future, jpuppeteer.cdp.cdp.entity.network.CanEmulateNetworkConditionsResponse.class);
    }

    /**
    * Clears browser cache.
    */
    public void clearBrowserCache(int timeout) throws Exception {
        session.send("Network.clearBrowserCache", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearBrowserCache() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.clearBrowserCache");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears browser cookies.
    */
    public void clearBrowserCookies(int timeout) throws Exception {
        session.send("Network.clearBrowserCookies", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearBrowserCookies() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.clearBrowserCookies");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Response to Network.requestIntercepted which either modifies the request to continue with any modifications, or blocks it, or completes it with the provided response bytes. If a network fetch occurs as a result which encounters a redirect an additional Network.requestIntercepted event will be sent with the same InterceptionId. Deprecated, use Fetch.continueRequest, Fetch.fulfillRequest and Fetch.failRequest instead.
    */
    public void continueInterceptedRequest(jpuppeteer.cdp.cdp.entity.network.ContinueInterceptedRequestRequest request, int timeout) throws Exception {
        session.send("Network.continueInterceptedRequest", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncContinueInterceptedRequest(jpuppeteer.cdp.cdp.entity.network.ContinueInterceptedRequestRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.continueInterceptedRequest", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Deletes browser cookies with matching name and url or domain/path pair.
    */
    public void deleteCookies(jpuppeteer.cdp.cdp.entity.network.DeleteCookiesRequest request, int timeout) throws Exception {
        session.send("Network.deleteCookies", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeleteCookies(jpuppeteer.cdp.cdp.entity.network.DeleteCookiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.deleteCookies", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables network tracking, prevents network events from being sent to the client.
    */
    public void disable(int timeout) throws Exception {
        session.send("Network.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Activates emulation of network conditions.
    */
    public void emulateNetworkConditions(jpuppeteer.cdp.cdp.entity.network.EmulateNetworkConditionsRequest request, int timeout) throws Exception {
        session.send("Network.emulateNetworkConditions", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEmulateNetworkConditions(jpuppeteer.cdp.cdp.entity.network.EmulateNetworkConditionsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.emulateNetworkConditions", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables network tracking, network events will now be delivered to the client.
    */
    public void enable(jpuppeteer.cdp.cdp.entity.network.EnableRequest request, int timeout) throws Exception {
        session.send("Network.enable", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable(jpuppeteer.cdp.cdp.entity.network.EnableRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.enable", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns all browser cookies. Depending on the backend support, will return detailed cookie information in the `cookies` field.
    */
    public jpuppeteer.cdp.cdp.entity.network.GetAllCookiesResponse getAllCookies(int timeout) throws Exception {
        return session.send("Network.getAllCookies", null, jpuppeteer.cdp.cdp.entity.network.GetAllCookiesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.GetAllCookiesResponse> asyncGetAllCookies() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.getAllCookies");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.GetAllCookiesResponse>(future, jpuppeteer.cdp.cdp.entity.network.GetAllCookiesResponse.class);
    }

    /**
    * Returns the DER-encoded certificate.
    */
    public jpuppeteer.cdp.cdp.entity.network.GetCertificateResponse getCertificate(jpuppeteer.cdp.cdp.entity.network.GetCertificateRequest request, int timeout) throws Exception {
        return session.send("Network.getCertificate", request, jpuppeteer.cdp.cdp.entity.network.GetCertificateResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.GetCertificateResponse> asyncGetCertificate(jpuppeteer.cdp.cdp.entity.network.GetCertificateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.getCertificate", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.GetCertificateResponse>(future, jpuppeteer.cdp.cdp.entity.network.GetCertificateResponse.class);
    }

    /**
    * Returns all browser cookies for the current URL. Depending on the backend support, will return detailed cookie information in the `cookies` field.
    */
    public jpuppeteer.cdp.cdp.entity.network.GetCookiesResponse getCookies(jpuppeteer.cdp.cdp.entity.network.GetCookiesRequest request, int timeout) throws Exception {
        return session.send("Network.getCookies", request, jpuppeteer.cdp.cdp.entity.network.GetCookiesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.GetCookiesResponse> asyncGetCookies(jpuppeteer.cdp.cdp.entity.network.GetCookiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.getCookies", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.GetCookiesResponse>(future, jpuppeteer.cdp.cdp.entity.network.GetCookiesResponse.class);
    }

    /**
    * Returns content served for the given request.
    */
    public jpuppeteer.cdp.cdp.entity.network.GetResponseBodyResponse getResponseBody(jpuppeteer.cdp.cdp.entity.network.GetResponseBodyRequest request, int timeout) throws Exception {
        return session.send("Network.getResponseBody", request, jpuppeteer.cdp.cdp.entity.network.GetResponseBodyResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.GetResponseBodyResponse> asyncGetResponseBody(jpuppeteer.cdp.cdp.entity.network.GetResponseBodyRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.getResponseBody", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.GetResponseBodyResponse>(future, jpuppeteer.cdp.cdp.entity.network.GetResponseBodyResponse.class);
    }

    /**
    * Returns post data sent with the request. Returns an error when no data was sent with the request.
    */
    public jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataResponse getRequestPostData(jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataRequest request, int timeout) throws Exception {
        return session.send("Network.getRequestPostData", request, jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataResponse> asyncGetRequestPostData(jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.getRequestPostData", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataResponse>(future, jpuppeteer.cdp.cdp.entity.network.GetRequestPostDataResponse.class);
    }

    /**
    * Returns content served for the given currently intercepted request.
    */
    public jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionResponse getResponseBodyForInterception(jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionRequest request, int timeout) throws Exception {
        return session.send("Network.getResponseBodyForInterception", request, jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionResponse> asyncGetResponseBodyForInterception(jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.getResponseBodyForInterception", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionResponse>(future, jpuppeteer.cdp.cdp.entity.network.GetResponseBodyForInterceptionResponse.class);
    }

    /**
    * Returns a handle to the stream representing the response body. Note that after this command, the intercepted request can't be continued as is -- you either need to cancel it or to provide the response body. The stream only supports sequential read, IO.read will fail if the position is specified.
    */
    public jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamResponse takeResponseBodyForInterceptionAsStream(jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamRequest request, int timeout) throws Exception {
        return session.send("Network.takeResponseBodyForInterceptionAsStream", request, jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamResponse> asyncTakeResponseBodyForInterceptionAsStream(jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.takeResponseBodyForInterceptionAsStream", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamResponse>(future, jpuppeteer.cdp.cdp.entity.network.TakeResponseBodyForInterceptionAsStreamResponse.class);
    }

    /**
    * This method sends a new XMLHttpRequest which is identical to the original one. The following parameters should be identical: method, url, async, request body, extra headers, withCredentials attribute, user, password.
    */
    public void replayXHR(jpuppeteer.cdp.cdp.entity.network.ReplayXHRRequest request, int timeout) throws Exception {
        session.send("Network.replayXHR", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncReplayXHR(jpuppeteer.cdp.cdp.entity.network.ReplayXHRRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.replayXHR", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Searches for given string in response content.
    */
    public jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyResponse searchInResponseBody(jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyRequest request, int timeout) throws Exception {
        return session.send("Network.searchInResponseBody", request, jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyResponse> asyncSearchInResponseBody(jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.searchInResponseBody", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyResponse>(future, jpuppeteer.cdp.cdp.entity.network.SearchInResponseBodyResponse.class);
    }

    /**
    * Blocks URLs from loading.
    */
    public void setBlockedURLs(jpuppeteer.cdp.cdp.entity.network.SetBlockedURLsRequest request, int timeout) throws Exception {
        session.send("Network.setBlockedURLs", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetBlockedURLs(jpuppeteer.cdp.cdp.entity.network.SetBlockedURLsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setBlockedURLs", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Toggles ignoring of service worker for each request.
    */
    public void setBypassServiceWorker(jpuppeteer.cdp.cdp.entity.network.SetBypassServiceWorkerRequest request, int timeout) throws Exception {
        session.send("Network.setBypassServiceWorker", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetBypassServiceWorker(jpuppeteer.cdp.cdp.entity.network.SetBypassServiceWorkerRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setBypassServiceWorker", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Toggles ignoring cache for each request. If `true`, cache will not be used.
    */
    public void setCacheDisabled(jpuppeteer.cdp.cdp.entity.network.SetCacheDisabledRequest request, int timeout) throws Exception {
        session.send("Network.setCacheDisabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetCacheDisabled(jpuppeteer.cdp.cdp.entity.network.SetCacheDisabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setCacheDisabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets a cookie with the given cookie data; may overwrite equivalent cookies if they exist.
    */
    public jpuppeteer.cdp.cdp.entity.network.SetCookieResponse setCookie(jpuppeteer.cdp.cdp.entity.network.SetCookieRequest request, int timeout) throws Exception {
        return session.send("Network.setCookie", request, jpuppeteer.cdp.cdp.entity.network.SetCookieResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.network.SetCookieResponse> asyncSetCookie(jpuppeteer.cdp.cdp.entity.network.SetCookieRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setCookie", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.network.SetCookieResponse>(future, jpuppeteer.cdp.cdp.entity.network.SetCookieResponse.class);
    }

    /**
    * Sets given cookies.
    */
    public void setCookies(jpuppeteer.cdp.cdp.entity.network.SetCookiesRequest request, int timeout) throws Exception {
        session.send("Network.setCookies", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetCookies(jpuppeteer.cdp.cdp.entity.network.SetCookiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setCookies", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * For testing.
    */
    public void setDataSizeLimitsForTest(jpuppeteer.cdp.cdp.entity.network.SetDataSizeLimitsForTestRequest request, int timeout) throws Exception {
        session.send("Network.setDataSizeLimitsForTest", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDataSizeLimitsForTest(jpuppeteer.cdp.cdp.entity.network.SetDataSizeLimitsForTestRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setDataSizeLimitsForTest", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Specifies whether to always send extra HTTP headers with the requests from this page.
    */
    public void setExtraHTTPHeaders(jpuppeteer.cdp.cdp.entity.network.SetExtraHTTPHeadersRequest request, int timeout) throws Exception {
        session.send("Network.setExtraHTTPHeaders", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetExtraHTTPHeaders(jpuppeteer.cdp.cdp.entity.network.SetExtraHTTPHeadersRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setExtraHTTPHeaders", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets the requests to intercept that match the provided patterns and optionally resource types. Deprecated, please use Fetch.enable instead.
    */
    public void setRequestInterception(jpuppeteer.cdp.cdp.entity.network.SetRequestInterceptionRequest request, int timeout) throws Exception {
        session.send("Network.setRequestInterception", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetRequestInterception(jpuppeteer.cdp.cdp.entity.network.SetRequestInterceptionRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setRequestInterception", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Allows overriding user agent with the given string.
    */
    public void setUserAgentOverride(jpuppeteer.cdp.cdp.entity.network.SetUserAgentOverrideRequest request, int timeout) throws Exception {
        session.send("Network.setUserAgentOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetUserAgentOverride(jpuppeteer.cdp.cdp.entity.network.SetUserAgentOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Network.setUserAgentOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}