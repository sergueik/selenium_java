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
import java.util.ArrayList;
import java.util.List;

/**
 * CSS Shape Outside details
 */
@Experimental
public class ShapeOutsideInfo {
    private List<Double> bounds = new ArrayList<>();

    private List<Object> shape = new ArrayList<>();

    private List<Object> marginShape = new ArrayList<>();

    /**
     * Shape bounds
     */
    public List<Double> getBounds() {
        return bounds;
    }

    /**
     * Shape bounds
     */
    public void setBounds(List<Double> bounds) {
        this.bounds = bounds;
    }

    /**
     * Shape coordinate details
     */
    public List<Object> getShape() {
        return shape;
    }

    /**
     * Shape coordinate details
     */
    public void setShape(List<Object> shape) {
        this.shape = shape;
    }

    /**
     * Margin shape bounds
     */
    public List<Object> getMarginShape() {
        return marginShape;
    }

    /**
     * Margin shape bounds
     */
    public void setMarginShape(List<Object> marginShape) {
        this.marginShape = marginShape;
    }
}
