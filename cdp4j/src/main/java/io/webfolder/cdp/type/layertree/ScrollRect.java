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

import io.webfolder.cdp.type.constant.RepaintReason;
import io.webfolder.cdp.type.dom.Rect;

/**
 * Rectangle where scrolling happens on the main thread
 */
public class ScrollRect {
    private Rect rect;

    private RepaintReason type;

    /**
     * Rectangle itself.
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * Rectangle itself.
     */
    public void setRect(Rect rect) {
        this.rect = rect;
    }

    /**
     * Reason for rectangle to force scrolling on the main thread
     */
    public RepaintReason getType() {
        return type;
    }

    /**
     * Reason for rectangle to force scrolling on the main thread
     */
    public void setType(RepaintReason type) {
        this.type = type;
    }
}
