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
package io.webfolder.cdp.event.overlay;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.page.Viewport;

/**
 * Fired when user asks to capture screenshot of some area on the page
 */
@Domain("Overlay")
@EventName("screenshotRequested")
public class ScreenshotRequested {
    private Viewport viewport;

    /**
     * Viewport to capture, in CSS.
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Viewport to capture, in CSS.
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
