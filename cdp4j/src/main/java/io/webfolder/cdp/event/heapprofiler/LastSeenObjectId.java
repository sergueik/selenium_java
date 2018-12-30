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
package io.webfolder.cdp.event.heapprofiler;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;

/**
 * If heap objects tracking has been started then backend regularly sends a current value for last seen object id and corresponding timestamp
 * If the were changes in the heap since last event then one or more heapStatsUpdate events will be sent before a new lastSeenObjectId event
 */
@Domain("HeapProfiler")
@EventName("lastSeenObjectId")
public class LastSeenObjectId {
    private Integer lastSeenObjectId;

    private Double timestamp;

    public Integer getLastSeenObjectId() {
        return lastSeenObjectId;
    }

    public void setLastSeenObjectId(Integer lastSeenObjectId) {
        this.lastSeenObjectId = lastSeenObjectId;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }
}
