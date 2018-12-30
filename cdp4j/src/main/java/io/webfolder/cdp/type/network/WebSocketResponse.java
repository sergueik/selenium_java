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
package io.webfolder.cdp.type.network;

import io.webfolder.cdp.annotation.Experimental;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket response data
 */
@Experimental
public class WebSocketResponse {
    private Integer status;

    private String statusText;

    private Map<String, Object> headers = new HashMap<>();

    private String headersText;

    private Map<String, Object> requestHeaders = new HashMap<>();

    private String requestHeadersText;

    /**
     * HTTP response status code.
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * HTTP response status code.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * HTTP response status text.
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * HTTP response status text.
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    /**
     * HTTP response headers.
     */
    public Map<String, Object> getHeaders() {
        return headers;
    }

    /**
     * HTTP response headers.
     */
    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    /**
     * HTTP response headers text.
     */
    public String getHeadersText() {
        return headersText;
    }

    /**
     * HTTP response headers text.
     */
    public void setHeadersText(String headersText) {
        this.headersText = headersText;
    }

    /**
     * HTTP request headers.
     */
    public Map<String, Object> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * HTTP request headers.
     */
    public void setRequestHeaders(Map<String, Object> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /**
     * HTTP request headers text.
     */
    public String getRequestHeadersText() {
        return requestHeadersText;
    }

    /**
     * HTTP request headers text.
     */
    public void setRequestHeadersText(String requestHeadersText) {
        this.requestHeadersText = requestHeadersText;
    }
}
