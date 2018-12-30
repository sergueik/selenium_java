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
package io.webfolder.cdp.type.browser;

/**
 * Browser window bounds information
 */
public class Bounds {
    private Integer left;

    private Integer top;

    private Integer width;

    private Integer height;

    private WindowState windowState;

    /**
     * The offset from the left edge of the screen to the window in pixels.
     */
    public Integer getLeft() {
        return left;
    }

    /**
     * The offset from the left edge of the screen to the window in pixels.
     */
    public void setLeft(Integer left) {
        this.left = left;
    }

    /**
     * The offset from the top edge of the screen to the window in pixels.
     */
    public Integer getTop() {
        return top;
    }

    /**
     * The offset from the top edge of the screen to the window in pixels.
     */
    public void setTop(Integer top) {
        this.top = top;
    }

    /**
     * The window width in pixels.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * The window width in pixels.
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * The window height in pixels.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * The window height in pixels.
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * The window state. Default to normal.
     */
    public WindowState getWindowState() {
        return windowState;
    }

    /**
     * The window state. Default to normal.
     */
    public void setWindowState(WindowState windowState) {
        this.windowState = windowState;
    }
}
