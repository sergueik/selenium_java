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
package io.webfolder.cdp.type.cachestorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Data entry
 */
public class DataEntry {
    private String requestURL;

    private String requestMethod;

    private List<Header> requestHeaders = new ArrayList<>();

    private Double responseTime;

    private Integer responseStatus;

    private String responseStatusText;

    private List<Header> responseHeaders = new ArrayList<>();

    /**
     * Request URL.
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * Request URL.
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * Request method.
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * Request method.
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Request headers
     */
    public List<Header> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * Request headers
     */
    public void setRequestHeaders(List<Header> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /**
     * Number of seconds since epoch.
     */
    public Double getResponseTime() {
        return responseTime;
    }

    /**
     * Number of seconds since epoch.
     */
    public void setResponseTime(Double responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * HTTP response status code.
     */
    public Integer getResponseStatus() {
        return responseStatus;
    }

    /**
     * HTTP response status code.
     */
    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * HTTP response status text.
     */
    public String getResponseStatusText() {
        return responseStatusText;
    }

    /**
     * HTTP response status text.
     */
    public void setResponseStatusText(String responseStatusText) {
        this.responseStatusText = responseStatusText;
    }

    /**
     * Response headers
     */
    public List<Header> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Response headers
     */
    public void setResponseHeaders(List<Header> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
