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
package io.webfolder.cdp.event.tracing;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;

@Domain("Tracing")
@EventName("bufferUsage")
public class BufferUsage {
    private Double percentFull;

    private Double eventCount;

    private Double value;

    /**
     * A number in range [0..1] that indicates the used size of event buffer as a fraction of its total size.
     */
    public Double getPercentFull() {
        return percentFull;
    }

    /**
     * A number in range [0..1] that indicates the used size of event buffer as a fraction of its total size.
     */
    public void setPercentFull(Double percentFull) {
        this.percentFull = percentFull;
    }

    /**
     * An approximate number of events in the trace log.
     */
    public Double getEventCount() {
        return eventCount;
    }

    /**
     * An approximate number of events in the trace log.
     */
    public void setEventCount(Double eventCount) {
        this.eventCount = eventCount;
    }

    /**
     * A number in range [0..1] that indicates the used size of event buffer as a fraction of its total size.
     */
    public Double getValue() {
        return value;
    }

    /**
     * A number in range [0..1] that indicates the used size of event buffer as a fraction of its total size.
     */
    public void setValue(Double value) {
        this.value = value;
    }
}
