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
package io.webfolder.cdp.event.tethering;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;

/**
 * Informs that port was successfully bound and got a specified connection id
 */
@Domain("Tethering")
@EventName("accepted")
public class Accepted {
    private Integer port;

    private String connectionId;

    /**
     * Port number that was successfully bound.
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Port number that was successfully bound.
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Connection id to be used.
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Connection id to be used.
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }
}
