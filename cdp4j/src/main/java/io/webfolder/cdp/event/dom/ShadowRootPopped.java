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
package io.webfolder.cdp.event.dom;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.annotation.Experimental;

/**
 * Called when shadow root is popped from the element
 */
@Experimental
@Domain("DOM")
@EventName("shadowRootPopped")
public class ShadowRootPopped {
    private Integer hostId;

    private Integer rootId;

    /**
     * Host element id.
     */
    public Integer getHostId() {
        return hostId;
    }

    /**
     * Host element id.
     */
    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    /**
     * Shadow root id.
     */
    public Integer getRootId() {
        return rootId;
    }

    /**
     * Shadow root id.
     */
    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }
}
