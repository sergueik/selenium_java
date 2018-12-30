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

/**
 * Cache identifier
 */
public class Cache {
    private String cacheId;

    private String securityOrigin;

    private String cacheName;

    /**
     * An opaque unique id of the cache.
     */
    public String getCacheId() {
        return cacheId;
    }

    /**
     * An opaque unique id of the cache.
     */
    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    /**
     * Security origin of the cache.
     */
    public String getSecurityOrigin() {
        return securityOrigin;
    }

    /**
     * Security origin of the cache.
     */
    public void setSecurityOrigin(String securityOrigin) {
        this.securityOrigin = securityOrigin;
    }

    /**
     * The name of the cache.
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * The name of the cache.
     */
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
