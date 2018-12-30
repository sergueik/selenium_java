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
import io.webfolder.cdp.type.page.Frame;

/**
 * Fired once navigation of the frame has completed
 * Frame is now associated with the new loader
 */
@Domain("Page")
@EventName("frameNavigated")
public class FrameNavigated {
    private Frame frame;

    /**
     * Frame object.
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Frame object.
     */
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
