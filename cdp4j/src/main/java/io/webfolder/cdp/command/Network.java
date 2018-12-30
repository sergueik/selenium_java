/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.command;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.annotation.Optional;
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.type.network.AuthChallengeResponse;
import io.webfolder.cdp.type.network.ConnectionType;
import io.webfolder.cdp.type.network.Cookie;
import io.webfolder.cdp.type.network.CookieParam;
import io.webfolder.cdp.type.network.CookieSameSite;
import io.webfolder.cdp.type.network.ErrorReason;
import io.webfolder.cdp.type.network.GetResponseBodyResult;
import io.webfolder.cdp.type.network.RequestPattern;
import java.util.List;
import java.util.Map;

/**
 * Network domain allows tracking network activities of the page
 * It exposes information about http, file, data and other requests and responses, their headers, bodies, timing, etc
 */
@Domain("Network")
public interface Network {
    /**
     * Enables network tracking, network events will now be delivered to the client.
     * 
     * @param maxTotalBufferSize Buffer size in bytes to use when preserving network payloads (XHRs, etc).
     * @param maxResourceBufferSize Per-resource buffer size in bytes to use when preserving network payloads (XHRs, etc).
     */
    void enable(@Experimental @Optional Integer maxTotalBufferSize,
            @Experimental @Optional Integer maxResourceBufferSize);

    /**
     * Disables network tracking, prevents network events from being sent to the client.
     */
    void disable();

    /**
     * Allows overriding user agent with the given string.
     * 
     * @param userAgent User agent to use.
     */
    void setUserAgentOverride(String userAgent);

    /**
     * Specifies whether to always send extra HTTP headers with the requests from this page.
     * 
     * @param headers Map with extra HTTP headers.
     */
    void setExtraHTTPHeaders(Map<String, Object> headers);

    /**
     * Returns content served for the given request.
     * 
     * @param requestId Identifier of the network request to get content for.
     * 
     * @return GetResponseBodyResult
     */
    GetResponseBodyResult getResponseBody(String requestId);

    /**
     * Blocks URLs from loading.
     * 
     * @param urls URL patterns to block. Wildcards ('*') are allowed.
     */
    @Experimental
    void setBlockedURLs(List<String> urls);

    /**
     * This method sends a new XMLHttpRequest which is identical to the original one. The following parameters should be identical: method, url, async, request body, extra headers, withCredentials attribute, user, password.
     * 
     * @param requestId Identifier of XHR to replay.
     */
    @Experimental
    void replayXHR(String requestId);

    /**
     * Tells whether clearing browser cache is supported.
     * 
     * @return True if browser cache can be cleared.
     */
    @Returns("result")
    Boolean canClearBrowserCache();

    /**
     * Clears browser cache.
     */
    void clearBrowserCache();

    /**
     * Tells whether clearing browser cookies is supported.
     * 
     * @return True if browser cookies can be cleared.
     */
    @Returns("result")
    Boolean canClearBrowserCookies();

    /**
     * Clears browser cookies.
     */
    void clearBrowserCookies();

    /**
     * Returns all browser cookies for the current URL. Depending on the backend support, will return detailed cookie information in the <tt>cookies</tt> field.
     * 
     * @param urls The list of URLs for which applicable cookies will be fetched
     * 
     * @return Array of cookie objects.
     */
    @Experimental
    @Returns("cookies")
    List<Cookie> getCookies(@Optional List<String> urls);

    /**
     * Returns all browser cookies. Depending on the backend support, will return detailed cookie information in the <tt>cookies</tt> field.
     * 
     * @return Array of cookie objects.
     */
    @Experimental
    @Returns("cookies")
    List<Cookie> getAllCookies();

    /**
     * Deletes browser cookies with matching name and url or domain/path pair.
     * 
     * @param name Name of the cookies to remove.
     * @param url If specified, deletes all the cookies with the given name where domain and path match provided URL.
     * @param domain If specified, deletes only cookies with the exact domain.
     * @param path If specified, deletes only cookies with the exact path.
     */
    @Experimental
    void deleteCookies(String name, @Optional String url, @Optional String domain,
            @Optional String path);

    /**
     * Sets a cookie with the given cookie data; may overwrite equivalent cookies if they exist.
     * 
     * @param name Cookie name.
     * @param value Cookie value.
     * @param url The request-URI to associate with the setting of the cookie. This value can affect the default domain and path values of the created cookie.
     * @param domain Cookie domain.
     * @param path Cookie path.
     * @param secure True if cookie is secure.
     * @param httpOnly True if cookie is http-only.
     * @param sameSite Cookie SameSite type.
     * @param expires Cookie expiration date, session cookie if not set
     * 
     * @return True if successfully set cookie.
     */
    @Experimental
    @Returns("success")
    Boolean setCookie(String name, String value, @Optional String url, @Optional String domain,
            @Optional String path, @Optional Boolean secure, @Optional Boolean httpOnly,
            @Optional CookieSameSite sameSite, @Optional Double expires);

    /**
     * Sets given cookies.
     * 
     * @param cookies Cookies to be set.
     */
    @Experimental
    void setCookies(List<CookieParam> cookies);

    /**
     * Tells whether emulation of network conditions is supported.
     * 
     * @return True if emulation of network conditions is supported.
     */
    @Experimental
    @Returns("result")
    Boolean canEmulateNetworkConditions();

    /**
     * Activates emulation of network conditions.
     * 
     * @param offline True to emulate internet disconnection.
     * @param latency Minimum latency from request sent to response headers received (ms).
     * @param downloadThroughput Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.
     * @param uploadThroughput Maximal aggregated upload throughput (bytes/sec).  -1 disables upload throttling.
     * @param connectionType Connection type if known.
     */
    void emulateNetworkConditions(Boolean offline, Double latency, Double downloadThroughput,
            Double uploadThroughput, @Optional ConnectionType connectionType);

    /**
     * Toggles ignoring cache for each request. If <tt>true</tt>, cache will not be used.
     * 
     * @param cacheDisabled Cache disabled state.
     */
    void setCacheDisabled(Boolean cacheDisabled);

    /**
     * Toggles ignoring of service worker for each request.
     * 
     * @param bypass Bypass service worker and load from network.
     */
    @Experimental
    void setBypassServiceWorker(Boolean bypass);

    /**
     * For testing.
     * 
     * @param maxTotalSize Maximum total buffer size.
     * @param maxResourceSize Maximum per-resource size.
     */
    @Experimental
    void setDataSizeLimitsForTest(Integer maxTotalSize, Integer maxResourceSize);

    /**
     * Returns the DER-encoded certificate.
     * 
     * @param origin Origin to get certificate for.
     */
    @Experimental
    @Returns("tableNames")
    List<String> getCertificate(String origin);

    /**
     * Sets the requests to intercept that match a the provided patterns and optionally resource types.
     * 
     * @param patterns Requests matching any of these patterns will be forwarded and wait for the corresponding continueInterceptedRequest call.
     */
    @Experimental
    void setRequestInterception(List<RequestPattern> patterns);

    /**
     * Response to Network.requestIntercepted which either modifies the request to continue with any modifications, or blocks it, or completes it with the provided response bytes. If a network fetch occurs as a result which encounters a redirect an additional Network.requestIntercepted event will be sent with the same InterceptionId.
     * 
     * @param errorReason If set this causes the request to fail with the given reason. Passing <code>Aborted</code> for requests marked with <code>isNavigationRequest</code> also cancels the navigation. Must not be set in response to an authChallenge.
     * @param rawResponse If set the requests completes using with the provided base64 encoded raw response, including HTTP status line and headers etc... Must not be set in response to an authChallenge.
     * @param url If set the request url will be modified in a way that's not observable by page. Must not be set in response to an authChallenge.
     * @param method If set this allows the request method to be overridden. Must not be set in response to an authChallenge.
     * @param postData If set this allows postData to be set. Must not be set in response to an authChallenge.
     * @param headers If set this allows the request headers to be changed. Must not be set in response to an authChallenge.
     * @param authChallengeResponse Response to a requestIntercepted with an authChallenge. Must not be set otherwise.
     */
    @Experimental
    void continueInterceptedRequest(String interceptionId, @Optional ErrorReason errorReason,
            @Optional String rawResponse, @Optional String url, @Optional String method,
            @Optional String postData, @Optional Map<String, Object> headers,
            @Optional AuthChallengeResponse authChallengeResponse);

    /**
     * Enables network tracking, network events will now be delivered to the client.
     */
    void enable();

    /**
     * Returns all browser cookies for the current URL. Depending on the backend support, will return detailed cookie information in the <tt>cookies</tt> field.
     * 
     * @return Array of cookie objects.
     */
    @Experimental
    @Returns("cookies")
    List<Cookie> getCookies();

    /**
     * Deletes browser cookies with matching name and url or domain/path pair.
     * 
     * @param name Name of the cookies to remove.
     */
    @Experimental
    void deleteCookies(String name);

    /**
     * Sets a cookie with the given cookie data; may overwrite equivalent cookies if they exist.
     * 
     * @param name Cookie name.
     * @param value Cookie value.
     * 
     * @return True if successfully set cookie.
     */
    @Experimental
    @Returns("success")
    Boolean setCookie(String name, String value);

    /**
     * Activates emulation of network conditions.
     * 
     * @param offline True to emulate internet disconnection.
     * @param latency Minimum latency from request sent to response headers received (ms).
     * @param downloadThroughput Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.
     * @param uploadThroughput Maximal aggregated upload throughput (bytes/sec).  -1 disables upload throttling.
     */
    void emulateNetworkConditions(Boolean offline, Double latency, Double downloadThroughput,
            Double uploadThroughput);

    /**
     * Response to Network.requestIntercepted which either modifies the request to continue with any modifications, or blocks it, or completes it with the provided response bytes. If a network fetch occurs as a result which encounters a redirect an additional Network.requestIntercepted event will be sent with the same InterceptionId.
     * 
     */
    @Experimental
    void continueInterceptedRequest(String interceptionId);

    /**
     * Sets the requests to intercept that match a the provided patterns.
     * 
     * @param enabled Whether requests should be intercepted. If patterns is not set, matches all and resets any previously set patterns. Other parameters are ignored if false.
     * @param patterns URLs matching any of these patterns will be forwarded and wait for the corresponding continueInterceptedRequest call. Wildcards ('*' -> zero or more, '?' -> exactly one) are allowed. Escape character is backslash. If omitted equivalent to ['*'] (intercept all).
     */
    @Experimental
    void setRequestInterceptionEnabled(Boolean enabled, @Optional List<String> patterns);

    /**
     * Sets the requests to intercept that match a the provided patterns.
     * 
     * @param enabled Whether requests should be intercepted. If patterns is not set, matches all and resets any previously set patterns. Other parameters are ignored if false.
     */
    @Experimental
    void setRequestInterceptionEnabled(Boolean enabled);
}
