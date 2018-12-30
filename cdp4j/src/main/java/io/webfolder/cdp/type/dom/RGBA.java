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

/**
 * A structure holding an RGBA color
 */
public class RGBA {
    private Integer r;

    private Integer g;

    private Integer b;

    private Double a;

    /**
     * The red component, in the [0-255] range.
     */
    public Integer getR() {
        return r;
    }

    /**
     * The red component, in the [0-255] range.
     */
    public void setR(Integer r) {
        this.r = r;
    }

    /**
     * The green component, in the [0-255] range.
     */
    public Integer getG() {
        return g;
    }

    /**
     * The green component, in the [0-255] range.
     */
    public void setG(Integer g) {
        this.g = g;
    }

    /**
     * The blue component, in the [0-255] range.
     */
    public Integer getB() {
        return b;
    }

    /**
     * The blue component, in the [0-255] range.
     */
    public void setB(Integer b) {
        this.b = b;
    }

    /**
     * The alpha component, in the [0-1] range (default: 1).
     */
    public Double getA() {
        return a;
    }

    /**
     * The alpha component, in the [0-1] range (default: 1).
     */
    public void setA(Double a) {
        this.a = a;
    }
}
