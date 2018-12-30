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
 * Fired when window
 * open() was called
 */
@Experimental
@Domain("Page")
@EventName("windowOpen")
public class WindowOpen {
    private String url;

    private String windowName;

    private String windowFeatures;

    private Boolean userGesture;

    /**
     * The URL for the new window.
     */
    public String getUrl() {
        return url;
    }

    /**
     * The URL for the new window.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Window name passed to window.open().
     */
    public String getWindowName() {
        return windowName;
    }

    /**
     * Window name passed to window.open().
     */
    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    /**
     * Window features passed to window.open().
     */
    public String getWindowFeatures() {
        return windowFeatures;
    }

    /**
     * Window features passed to window.open().
     */
    public void setWindowFeatures(String windowFeatures) {
        this.windowFeatures = windowFeatures;
    }

    /**
     * Whether or not window.open() was triggered by user gesture.
     */
    public Boolean isUserGesture() {
        return userGesture;
    }

    /**
     * Whether or not window.open() was triggered by user gesture.
     */
    public void setUserGesture(Boolean userGesture) {
        this.userGesture = userGesture;
    }
}
