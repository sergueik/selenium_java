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
 * Visual viewport position, dimensions, and scale
 */
@Experimental
public class VisualViewport {
    private Double offsetX;

    private Double offsetY;

    private Double pageX;

    private Double pageY;

    private Double clientWidth;

    private Double clientHeight;

    private Double scale;

    /**
     * Horizontal offset relative to the layout viewport (CSS pixels).
     */
    public Double getOffsetX() {
        return offsetX;
    }

    /**
     * Horizontal offset relative to the layout viewport (CSS pixels).
     */
    public void setOffsetX(Double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Vertical offset relative to the layout viewport (CSS pixels).
     */
    public Double getOffsetY() {
        return offsetY;
    }

    /**
     * Vertical offset relative to the layout viewport (CSS pixels).
     */
    public void setOffsetY(Double offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Horizontal offset relative to the document (CSS pixels).
     */
    public Double getPageX() {
        return pageX;
    }

    /**
     * Horizontal offset relative to the document (CSS pixels).
     */
    public void setPageX(Double pageX) {
        this.pageX = pageX;
    }

    /**
     * Vertical offset relative to the document (CSS pixels).
     */
    public Double getPageY() {
        return pageY;
    }

    /**
     * Vertical offset relative to the document (CSS pixels).
     */
    public void setPageY(Double pageY) {
        this.pageY = pageY;
    }

    /**
     * Width (CSS pixels), excludes scrollbar if present.
     */
    public Double getClientWidth() {
        return clientWidth;
    }

    /**
     * Width (CSS pixels), excludes scrollbar if present.
     */
    public void setClientWidth(Double clientWidth) {
        this.clientWidth = clientWidth;
    }

    /**
     * Height (CSS pixels), excludes scrollbar if present.
     */
    public Double getClientHeight() {
        return clientHeight;
    }

    /**
     * Height (CSS pixels), excludes scrollbar if present.
     */
    public void setClientHeight(Double clientHeight) {
        this.clientHeight = clientHeight;
    }

    /**
     * Scale relative to the ideal viewport (size at width=device-width).
     */
    public Double getScale() {
        return scale;
    }

    /**
     * Scale relative to the ideal viewport (size at width=device-width).
     */
    public void setScale(Double scale) {
        this.scale = scale;
    }
}
