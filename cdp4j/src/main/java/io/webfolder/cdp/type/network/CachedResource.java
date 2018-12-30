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

import io.webfolder.cdp.type.page.ResourceType;

/**
 * Information about the cached resource
 */
public class CachedResource {
    private String url;

    private ResourceType type;

    private Response response;

    private Double bodySize;

    /**
     * Resource URL. This is the url of the original network request.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Resource URL. This is the url of the original network request.
     */
    public void setUrl(String url) {
        this.url = url;
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
     * Cached response data.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Cached response data.
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * Cached response body size.
     */
    public Double getBodySize() {
        return bodySize;
    }

    /**
     * Cached response body size.
     */
    public void setBodySize(Double bodySize) {
        this.bodySize = bodySize;
    }
}
