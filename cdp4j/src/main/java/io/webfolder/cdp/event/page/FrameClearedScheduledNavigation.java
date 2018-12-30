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
package io.webfolder.cdp.event.page;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.annotation.Experimental;

/**
 * Fired when frame no longer has a scheduled navigation
 */
@Experimental
@Domain("Page")
@EventName("frameClearedScheduledNavigation")
public class FrameClearedScheduledNavigation {
    private String frameId;

    /**
     * Id of the frame that has cleared its scheduled navigation.
     */
    public String getFrameId() {
        return frameId;
    }

    /**
     * Id of the frame that has cleared its scheduled navigation.
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }
}
