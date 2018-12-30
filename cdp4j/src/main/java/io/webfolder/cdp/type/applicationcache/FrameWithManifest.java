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
package io.webfolder.cdp.type.applicationcache;

/**
 * Frame identifier - manifest URL pair
 */
public class FrameWithManifest {
    private String frameId;

    private String manifestURL;

    private Integer status;

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

    /**
     * Manifest URL.
     */
    public String getManifestURL() {
        return manifestURL;
    }

    /**
     * Manifest URL.
     */
    public void setManifestURL(String manifestURL) {
        this.manifestURL = manifestURL;
    }

    /**
     * Application cache status.
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Application cache status.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
