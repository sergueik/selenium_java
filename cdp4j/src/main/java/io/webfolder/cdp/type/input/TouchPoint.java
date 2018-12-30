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
package io.webfolder.cdp.type.input;

import io.webfolder.cdp.annotation.Experimental;

@Experimental
public class TouchPoint {
    private Double x;

    private Double y;

    private Double radiusX;

    private Double radiusY;

    private Double rotationAngle;

    private Double force;

    private Double id;

    /**
     * X coordinate of the event relative to the main frame's viewport in CSS pixels.
     */
    public Double getX() {
        return x;
    }

    /**
     * X coordinate of the event relative to the main frame's viewport in CSS pixels.
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Y coordinate of the event relative to the main frame's viewport in CSS pixels. 0 refers to the top of the viewport and Y increases as it proceeds towards the bottom of the viewport.
     */
    public Double getY() {
        return y;
    }

    /**
     * Y coordinate of the event relative to the main frame's viewport in CSS pixels. 0 refers to the top of the viewport and Y increases as it proceeds towards the bottom of the viewport.
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * X radius of the touch area (default: 1.0).
     */
    public Double getRadiusX() {
        return radiusX;
    }

    /**
     * X radius of the touch area (default: 1.0).
     */
    public void setRadiusX(Double radiusX) {
        this.radiusX = radiusX;
    }

    /**
     * Y radius of the touch area (default: 1.0).
     */
    public Double getRadiusY() {
        return radiusY;
    }

    /**
     * Y radius of the touch area (default: 1.0).
     */
    public void setRadiusY(Double radiusY) {
        this.radiusY = radiusY;
    }

    /**
     * Rotation angle (default: 0.0).
     */
    public Double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Rotation angle (default: 0.0).
     */
    public void setRotationAngle(Double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    /**
     * Force (default: 1.0).
     */
    public Double getForce() {
        return force;
    }

    /**
     * Force (default: 1.0).
     */
    public void setForce(Double force) {
        this.force = force;
    }

    /**
     * Identifier used to track touch sources between events, must be unique within an event.
     */
    public Double getId() {
        return id;
    }

    /**
     * Identifier used to track touch sources between events, must be unique within an event.
     */
    public void setId(Double id) {
        this.id = id;
    }
}
