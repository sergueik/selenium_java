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
package io.webfolder.cdp.event.profiler;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.debugger.Location;
import io.webfolder.cdp.type.profiler.Profile;

@Domain("Profiler")
@EventName("consoleProfileFinished")
public class ConsoleProfileFinished {
    private String id;

    private Location location;

    private Profile profile;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Location of console.profileEnd().
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Location of console.profileEnd().
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Profile title passed as an argument to console.profile().
     */
    public String getTitle() {
        return title;
    }

    /**
     * Profile title passed as an argument to console.profile().
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
