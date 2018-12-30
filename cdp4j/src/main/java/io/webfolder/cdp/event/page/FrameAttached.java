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
import io.webfolder.cdp.type.runtime.StackTrace;

/**
 * Fired when frame has been attached to its parent
 */
@Domain("Page")
@EventName("frameAttached")
public class FrameAttached {
    private String frameId;

    private String parentFrameId;

    private StackTrace stack;

    /**
     * Id of the frame that has been attached.
     */
    public String getFrameId() {
        return frameId;
    }

    /**
     * Id of the frame that has been attached.
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    /**
     * Parent frame identifier.
     */
    public String getParentFrameId() {
        return parentFrameId;
    }

    /**
     * Parent frame identifier.
     */
    public void setParentFrameId(String parentFrameId) {
        this.parentFrameId = parentFrameId;
    }

    /**
     * JavaScript stack trace of when frame was attached, only set if frame initiated from script.
     */
    public StackTrace getStack() {
        return stack;
    }

    /**
     * JavaScript stack trace of when frame was attached, only set if frame initiated from script.
     */
    public void setStack(StackTrace stack) {
        this.stack = stack;
    }
}
