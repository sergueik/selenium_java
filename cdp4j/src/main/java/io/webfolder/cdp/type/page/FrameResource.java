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
package io.webfolder.cdp.type.page;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Information about the Resource on the page
 */
@Experimental
public class FrameResource {
    private String url;

    private ResourceType type;

    private String mimeType;

    private Double lastModified;

    private Double contentSize;

    private Boolean failed;

    private Boolean canceled;

    /**
     * Resource URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Resource URL.
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
     * Resource mimeType as determined by the browser.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Resource mimeType as determined by the browser.
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * last-modified timestamp as reported by server.
     */
    public Double getLastModified() {
        return lastModified;
    }

    /**
     * last-modified timestamp as reported by server.
     */
    public void setLastModified(Double lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Resource content size.
     */
    public Double getContentSize() {
        return contentSize;
    }

    /**
     * Resource content size.
     */
    public void setContentSize(Double contentSize) {
        this.contentSize = contentSize;
    }

    /**
     * True if the resource failed to load.
     */
    public Boolean isFailed() {
        return failed;
    }

    /**
     * True if the resource failed to load.
     */
    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    /**
     * True if the resource was canceled during loading.
     */
    public Boolean isCanceled() {
        return canceled;
    }

    /**
     * True if the resource was canceled during loading.
     */
    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }
}
