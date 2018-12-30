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

/**
 * Mirrors <code>DOMNodeRemoved</code> event
 */
@Domain("DOM")
@EventName("childNodeRemoved")
public class ChildNodeRemoved {
    private Integer parentNodeId;

    private Integer nodeId;

    /**
     * Parent id.
     */
    public Integer getParentNodeId() {
        return parentNodeId;
    }

    /**
     * Parent id.
     */
    public void setParentNodeId(Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    /**
     * Id of the node that has been removed.
     */
    public Integer getNodeId() {
        return nodeId;
    }

    /**
     * Id of the node that has been removed.
     */
    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}
