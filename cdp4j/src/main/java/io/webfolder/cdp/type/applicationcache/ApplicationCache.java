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

import java.util.ArrayList;
import java.util.List;

/**
 * Detailed application cache information
 */
public class ApplicationCache {
    private String manifestURL;

    private Double size;

    private Double creationTime;

    private Double updateTime;

    private List<ApplicationCacheResource> resources = new ArrayList<>();

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
     * Application cache size.
     */
    public Double getSize() {
        return size;
    }

    /**
     * Application cache size.
     */
    public void setSize(Double size) {
        this.size = size;
    }

    /**
     * Application cache creation time.
     */
    public Double getCreationTime() {
        return creationTime;
    }

    /**
     * Application cache creation time.
     */
    public void setCreationTime(Double creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Application cache update time.
     */
    public Double getUpdateTime() {
        return updateTime;
    }

    /**
     * Application cache update time.
     */
    public void setUpdateTime(Double updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Application cache resources.
     */
    public List<ApplicationCacheResource> getResources() {
        return resources;
    }

    /**
     * Application cache resources.
     */
    public void setResources(List<ApplicationCacheResource> resources) {
        this.resources = resources;
    }
}
