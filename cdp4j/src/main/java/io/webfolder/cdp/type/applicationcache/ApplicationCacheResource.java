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
 * Detailed application cache resource information
 */
public class ApplicationCacheResource {
    private String url;

    private Integer size;

    private String type;

    /**
     * Resource url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Resource url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Resource size.
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Resource size.
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Resource type.
     */
    public String getType() {
        return type;
    }

    /**
     * Resource type.
     */
    public void setType(String type) {
        this.type = type;
    }
}
