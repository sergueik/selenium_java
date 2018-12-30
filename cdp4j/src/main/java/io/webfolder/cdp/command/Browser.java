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
package io.webfolder.cdp.command;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.type.browser.Bounds;
import io.webfolder.cdp.type.browser.GetVersionResult;
import io.webfolder.cdp.type.browser.GetWindowForTargetResult;

/**
 * The Browser domain defines methods and events for browser managing
 */
@Experimental
@Domain("Browser")
public interface Browser {
    /**
     * Close browser gracefully.
     */
    void close();

    /**
     * Get the browser window that contains the devtools target.
     * 
     * @param targetId Devtools agent host id.
     * 
     * @return GetWindowForTargetResult
     */
    GetWindowForTargetResult getWindowForTarget(String targetId);

    /**
     * Returns version information.
     * 
     * @return GetVersionResult
     */
    GetVersionResult getVersion();

    /**
     * Set position and/or size of the browser window.
     * 
     * @param windowId Browser window id.
     * @param bounds New window bounds. The 'minimized', 'maximized' and 'fullscreen' states cannot be combined with 'left', 'top', 'width' or 'height'. Leaves unspecified fields unchanged.
     */
    void setWindowBounds(Integer windowId, Bounds bounds);

    /**
     * Get position and size of the browser window.
     * 
     * @param windowId Browser window id.
     * 
     * @return Bounds information of the window. When window state is 'minimized', the restored window position and size are returned.
     */
    @Returns("bounds")
    Bounds getWindowBounds(Integer windowId);
}
