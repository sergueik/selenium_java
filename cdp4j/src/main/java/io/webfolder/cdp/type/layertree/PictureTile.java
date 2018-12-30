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
package io.webfolder.cdp.type.layertree;

/**
 * Serialized fragment of layer picture along with its offset within the layer
 */
public class PictureTile {
    private Double x;

    private Double y;

    private String picture;

    /**
     * Offset from owning layer left boundary
     */
    public Double getX() {
        return x;
    }

    /**
     * Offset from owning layer left boundary
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Offset from owning layer top boundary
     */
    public Double getY() {
        return y;
    }

    /**
     * Offset from owning layer top boundary
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Base64-encoded snapshot data.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Base64-encoded snapshot data.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
