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
package io.webfolder.cdp.type.dom;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Rectangle
 */
@Experimental
public class Rect {
    private Double x;

    private Double y;

    private Double width;

    private Double height;

    /**
     * X coordinate
     */
    public Double getX() {
        return x;
    }

    /**
     * X coordinate
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Y coordinate
     */
    public Double getY() {
        return y;
    }

    /**
     * Y coordinate
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Rectangle width
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Rectangle width
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     * Rectangle height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Rectangle height
     */
    public void setHeight(Double height) {
        this.height = height;
    }
}
