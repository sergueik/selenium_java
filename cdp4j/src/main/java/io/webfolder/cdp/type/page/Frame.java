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

/**
 * Information about the Frame on the page
 */
public class Frame {
    private String id;

    private String parentId;

    private String loaderId;

    private String name;

    private String url;

    private String securityOrigin;

    private String mimeType;

    private String unreachableUrl;

    /**
     * Frame unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Frame unique identifier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Parent frame identifier.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Parent frame identifier.
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Identifier of the loader associated with this frame.
     */
    public String getLoaderId() {
        return loaderId;
    }

    /**
     * Identifier of the loader associated with this frame.
     */
    public void setLoaderId(String loaderId) {
        this.loaderId = loaderId;
    }

    /**
     * Frame's name as specified in the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Frame's name as specified in the tag.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Frame document's URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Frame document's URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Frame document's security origin.
     */
    public String getSecurityOrigin() {
        return securityOrigin;
    }

    /**
     * Frame document's security origin.
     */
    public void setSecurityOrigin(String securityOrigin) {
        this.securityOrigin = securityOrigin;
    }

    /**
     * Frame document's mimeType as determined by the browser.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Frame document's mimeType as determined by the browser.
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * If the frame failed to load, this contains the URL that could not be loaded.
     */
    public String getUnreachableUrl() {
        return unreachableUrl;
    }

    /**
     * If the frame failed to load, this contains the URL that could not be loaded.
     */
    public void setUnreachableUrl(String unreachableUrl) {
        this.unreachableUrl = unreachableUrl;
    }
}
