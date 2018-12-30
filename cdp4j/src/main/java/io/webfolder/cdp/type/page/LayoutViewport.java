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
 * Layout viewport position and dimensions
 */
@Experimental
public class LayoutViewport {
    private Integer pageX;

    private Integer pageY;

    private Integer clientWidth;

    private Integer clientHeight;

    /**
     * Horizontal offset relative to the document (CSS pixels).
     */
    public Integer getPageX() {
        return pageX;
    }

    /**
     * Horizontal offset relative to the document (CSS pixels).
     */
    public void setPageX(Integer pageX) {
        this.pageX = pageX;
    }

    /**
     * Vertical offset relative to the document (CSS pixels).
     */
    public Integer getPageY() {
        return pageY;
    }

    /**
     * Vertical offset relative to the document (CSS pixels).
     */
    public void setPageY(Integer pageY) {
        this.pageY = pageY;
    }

    /**
     * Width (CSS pixels), excludes scrollbar if present.
     */
    public Integer getClientWidth() {
        return clientWidth;
    }

    /**
     * Width (CSS pixels), excludes scrollbar if present.
     */
    public void setClientWidth(Integer clientWidth) {
        this.clientWidth = clientWidth;
    }

    /**
     * Height (CSS pixels), excludes scrollbar if present.
     */
    public Integer getClientHeight() {
        return clientHeight;
    }

    /**
     * Height (CSS pixels), excludes scrollbar if present.
     */
    public void setClientHeight(Integer clientHeight) {
        this.clientHeight = clientHeight;
    }
}
