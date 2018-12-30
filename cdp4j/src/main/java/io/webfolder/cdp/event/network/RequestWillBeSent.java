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

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.network.Initiator;
import io.webfolder.cdp.type.network.Request;
import io.webfolder.cdp.type.network.Response;
import io.webfolder.cdp.type.page.ResourceType;

/**
 * Fired when page is about to send HTTP request
 */
@Domain("Network")
@EventName("requestWillBeSent")
public class RequestWillBeSent {
    private String requestId;

    private String loaderId;

    private String documentURL;

    private Request request;

    private Double timestamp;

    private Double wallTime;

    private Initiator initiator;

    private Response redirectResponse;

    private ResourceType type;

    private String frameId;

    /**
     * Request identifier.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Request identifier.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Loader identifier. Empty string if the request is fetched form worker.
     */
    public String getLoaderId() {
        return loaderId;
    }

    /**
     * Loader identifier. Empty string if the request is fetched form worker.
     */
    public void setLoaderId(String loaderId) {
        this.loaderId = loaderId;
    }

    /**
     * URL of the document this request is loaded for.
     */
    public String getDocumentURL() {
        return documentURL;
    }

    /**
     * URL of the document this request is loaded for.
     */
    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    /**
     * Request data.
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Request data.
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Timestamp.
     */
    public Double getTimestamp() {
        return timestamp;
    }

    /**
     * Timestamp.
     */
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Timestamp.
     */
    public Double getWallTime() {
        return wallTime;
    }

    /**
     * Timestamp.
     */
    public void setWallTime(Double wallTime) {
        this.wallTime = wallTime;
    }

    /**
     * Request initiator.
     */
    public Initiator getInitiator() {
        return initiator;
    }

    /**
     * Request initiator.
     */
    public void setInitiator(Initiator initiator) {
        this.initiator = initiator;
    }

    /**
     * Redirect response data.
     */
    public Response getRedirectResponse() {
        return redirectResponse;
    }

    /**
     * Redirect response data.
     */
    public void setRedirectResponse(Response redirectResponse) {
        this.redirectResponse = redirectResponse;
    }

    /**
     * Type of this resource.
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Type of this resource.
     */
    public void setType(ResourceType type) {
        this.type = type;
    }

    /**
     * Frame identifier.
     */
    public String getFrameId() {
        return frameId;
    }

    /**
     * Frame identifier.
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }
}
