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
package io.webfolder.cdp.event.network;

import java.util.HashMap;
import java.util.Map;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.network.AuthChallenge;
import io.webfolder.cdp.type.network.Request;
import io.webfolder.cdp.type.page.ResourceType;

/**
 * Details of an intercepted HTTP request, which must be either allowed, blocked, modified or mocked
 */
@Experimental
@Domain("Network")
@EventName("requestIntercepted")
public class RequestIntercepted {
    private String interceptionId;

    private Request request;

    private String frameId;

    private ResourceType resourceType;

    private Boolean isNavigationRequest;

    private Map<String, Object> redirectHeaders = new HashMap<>();

    private Integer redirectStatusCode;

    private String redirectUrl;

    private AuthChallenge authChallenge;

    /**
     * Each request the page makes will have a unique id, however if any redirects are encountered while processing that fetch, they will be reported with the same id as the original fetch. Likewise if HTTP authentication is needed then the same fetch id will be used.
     */
    public String getInterceptionId() {
        return interceptionId;
    }

    /**
     * Each request the page makes will have a unique id, however if any redirects are encountered while processing that fetch, they will be reported with the same id as the original fetch. Likewise if HTTP authentication is needed then the same fetch id will be used.
     */
    public void setInterceptionId(String interceptionId) {
        this.interceptionId = interceptionId;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * The id of the frame that initiated the request.
     */
    public String getFrameId() {
        return frameId;
    }

    /**
     * The id of the frame that initiated the request.
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    /**
     * How the requested resource will be used.
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * How the requested resource will be used.
     */
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * Whether this is a navigation request, which can abort the navigation completely.
     */
    public Boolean isIsNavigationRequest() {
        return isNavigationRequest;
    }

    /**
     * Whether this is a navigation request, which can abort the navigation completely.
     */
    public void setIsNavigationRequest(Boolean isNavigationRequest) {
        this.isNavigationRequest = isNavigationRequest;
    }

    /**
     * HTTP response headers, only sent if a redirect was intercepted.
     */
    public Map<String, Object> getRedirectHeaders() {
        return redirectHeaders;
    }

    /**
     * HTTP response headers, only sent if a redirect was intercepted.
     */
    public void setRedirectHeaders(Map<String, Object> redirectHeaders) {
        this.redirectHeaders = redirectHeaders;
    }

    /**
     * HTTP response code, only sent if a redirect was intercepted.
     */
    public Integer getRedirectStatusCode() {
        return redirectStatusCode;
    }

    /**
     * HTTP response code, only sent if a redirect was intercepted.
     */
    public void setRedirectStatusCode(Integer redirectStatusCode) {
        this.redirectStatusCode = redirectStatusCode;
    }

    /**
     * Redirect location, only sent if a redirect was intercepted.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Redirect location, only sent if a redirect was intercepted.
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * Details of the Authorization Challenge encountered. If this is set then continueInterceptedRequest must contain an authChallengeResponse.
     */
    public AuthChallenge getAuthChallenge() {
        return authChallenge;
    }

    /**
     * Details of the Authorization Challenge encountered. If this is set then continueInterceptedRequest must contain an authChallengeResponse.
     */
    public void setAuthChallenge(AuthChallenge authChallenge) {
        this.authChallenge = authChallenge;
    }
}
