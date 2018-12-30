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
 * Screencast frame metadata
 */
@Experimental
public class ScreencastFrameMetadata {
    private Double offsetTop;

    private Double pageScaleFactor;

    private Double deviceWidth;

    private Double deviceHeight;

    private Double scrollOffsetX;

    private Double scrollOffsetY;

    private Double timestamp;

    /**
     * Top offset in DIP.
     */
    public Double getOffsetTop() {
        return offsetTop;
    }

    /**
     * Top offset in DIP.
     */
    public void setOffsetTop(Double offsetTop) {
        this.offsetTop = offsetTop;
    }

    /**
     * Page scale factor.
     */
    public Double getPageScaleFactor() {
        return pageScaleFactor;
    }

    /**
     * Page scale factor.
     */
    public void setPageScaleFactor(Double pageScaleFactor) {
        this.pageScaleFactor = pageScaleFactor;
    }

    /**
     * Device screen width in DIP.
     */
    public Double getDeviceWidth() {
        return deviceWidth;
    }

    /**
     * Device screen width in DIP.
     */
    public void setDeviceWidth(Double deviceWidth) {
        this.deviceWidth = deviceWidth;
    }

    /**
     * Device screen height in DIP.
     */
    public Double getDeviceHeight() {
        return deviceHeight;
    }

    /**
     * Device screen height in DIP.
     */
    public void setDeviceHeight(Double deviceHeight) {
        this.deviceHeight = deviceHeight;
    }

    /**
     * Position of horizontal scroll in CSS pixels.
     */
    public Double getScrollOffsetX() {
        return scrollOffsetX;
    }

    /**
     * Position of horizontal scroll in CSS pixels.
     */
    public void setScrollOffsetX(Double scrollOffsetX) {
        this.scrollOffsetX = scrollOffsetX;
    }

    /**
     * Position of vertical scroll in CSS pixels.
     */
    public Double getScrollOffsetY() {
        return scrollOffsetY;
    }

    /**
     * Position of vertical scroll in CSS pixels.
     */
    public void setScrollOffsetY(Double scrollOffsetY) {
        this.scrollOffsetY = scrollOffsetY;
    }

    /**
     * Frame swap timestamp.
     */
    public Double getTimestamp() {
        return timestamp;
    }

    /**
     * Frame swap timestamp.
     */
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }
}
