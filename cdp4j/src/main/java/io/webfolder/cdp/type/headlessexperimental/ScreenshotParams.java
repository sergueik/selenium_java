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
package io.webfolder.cdp.type.headlessexperimental;

import io.webfolder.cdp.type.constant.ImageFormat;

/**
 * Encoding options for a screenshot
 */
public class ScreenshotParams {
    private ImageFormat format;

    private Integer quality;

    /**
     * Image compression format (defaults to png).
     */
    public ImageFormat getFormat() {
        return format;
    }

    /**
     * Image compression format (defaults to png).
     */
    public void setFormat(ImageFormat format) {
        this.format = format;
    }

    /**
     * Compression quality from range [0..100] (jpeg only).
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * Compression quality from range [0..100] (jpeg only).
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
    }
}
