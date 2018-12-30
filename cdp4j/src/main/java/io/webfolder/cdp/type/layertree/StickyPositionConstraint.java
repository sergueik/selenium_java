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

import io.webfolder.cdp.type.dom.Rect;

/**
 * Sticky position constraints
 */
public class StickyPositionConstraint {
    private Rect stickyBoxRect;

    private Rect containingBlockRect;

    private String nearestLayerShiftingStickyBox;

    private String nearestLayerShiftingContainingBlock;

    /**
     * Layout rectangle of the sticky element before being shifted
     */
    public Rect getStickyBoxRect() {
        return stickyBoxRect;
    }

    /**
     * Layout rectangle of the sticky element before being shifted
     */
    public void setStickyBoxRect(Rect stickyBoxRect) {
        this.stickyBoxRect = stickyBoxRect;
    }

    /**
     * Layout rectangle of the containing block of the sticky element
     */
    public Rect getContainingBlockRect() {
        return containingBlockRect;
    }

    /**
     * Layout rectangle of the containing block of the sticky element
     */
    public void setContainingBlockRect(Rect containingBlockRect) {
        this.containingBlockRect = containingBlockRect;
    }

    /**
     * The nearest sticky layer that shifts the sticky box
     */
    public String getNearestLayerShiftingStickyBox() {
        return nearestLayerShiftingStickyBox;
    }

    /**
     * The nearest sticky layer that shifts the sticky box
     */
    public void setNearestLayerShiftingStickyBox(String nearestLayerShiftingStickyBox) {
        this.nearestLayerShiftingStickyBox = nearestLayerShiftingStickyBox;
    }

    /**
     * The nearest sticky layer that shifts the containing block
     */
    public String getNearestLayerShiftingContainingBlock() {
        return nearestLayerShiftingContainingBlock;
    }

    /**
     * The nearest sticky layer that shifts the containing block
     */
    public void setNearestLayerShiftingContainingBlock(String nearestLayerShiftingContainingBlock) {
        this.nearestLayerShiftingContainingBlock = nearestLayerShiftingContainingBlock;
    }
}
