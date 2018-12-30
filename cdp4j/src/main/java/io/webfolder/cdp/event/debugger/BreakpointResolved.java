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
package io.webfolder.cdp.event.debugger;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.debugger.Location;

/**
 * Fired when breakpoint is resolved to an actual script and location
 */
@Domain("Debugger")
@EventName("breakpointResolved")
public class BreakpointResolved {
    private String breakpointId;

    private Location location;

    /**
     * Breakpoint unique identifier.
     */
    public String getBreakpointId() {
        return breakpointId;
    }

    /**
     * Breakpoint unique identifier.
     */
    public void setBreakpointId(String breakpointId) {
        this.breakpointId = breakpointId;
    }

    /**
     * Actual breakpoint location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Actual breakpoint location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
