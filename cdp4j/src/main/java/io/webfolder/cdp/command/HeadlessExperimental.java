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
import io.webfolder.cdp.annotation.Optional;
import io.webfolder.cdp.type.headlessexperimental.BeginFrameResult;
import io.webfolder.cdp.type.headlessexperimental.ScreenshotParams;

/**
 * This domain provides experimental commands only supported in headless mode
 */
@Experimental
@Domain("HeadlessExperimental")
public interface HeadlessExperimental {
    /**
     * Enables headless events for the target.
     */
    void enable();

    /**
     * Disables headless events for the target.
     */
    void disable();

    /**
     * Sends a BeginFrame to the target and returns when the frame was completed. Optionally captures a screenshot from the resulting frame. Requires that the target was created with enabled BeginFrameControl.
     * 
     * @param frameTime Timestamp of this BeginFrame (milliseconds since epoch). If not set, the current time will be used.
     * @param deadline Deadline of this BeginFrame (milliseconds since epoch). If not set, the deadline will be calculated from the frameTime and interval.
     * @param interval The interval between BeginFrames that is reported to the compositor, in milliseconds. Defaults to a 60 frames/second interval, i.e. about 16.666 milliseconds.
     * @param screenshot If set, a screenshot of the frame will be captured and returned in the response. Otherwise, no screenshot will be captured.
     * 
     * @return BeginFrameResult
     */
    BeginFrameResult beginFrame(@Optional Double frameTime, @Optional Double deadline,
            @Optional Double interval, @Optional ScreenshotParams screenshot);

    /**
     * Sends a BeginFrame to the target and returns when the frame was completed. Optionally captures a screenshot from the resulting frame. Requires that the target was created with enabled BeginFrameControl.
     * 
     * @return BeginFrameResult
     */
    BeginFrameResult beginFrame();
}
