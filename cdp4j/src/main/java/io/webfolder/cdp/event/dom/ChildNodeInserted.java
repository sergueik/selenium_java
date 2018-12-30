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
import io.webfolder.cdp.type.dom.Node;

/**
 * Mirrors <code>DOMNodeInserted</code> event
 */
@Domain("DOM")
@EventName("childNodeInserted")
public class ChildNodeInserted {
    private Integer parentNodeId;

    private Integer previousNodeId;

    private Node node;

    /**
     * Id of the node that has changed.
     */
    public Integer getParentNodeId() {
        return parentNodeId;
    }

    /**
     * Id of the node that has changed.
     */
    public void setParentNodeId(Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    /**
     * If of the previous siblint.
     */
    public Integer getPreviousNodeId() {
        return previousNodeId;
    }

    /**
     * If of the previous siblint.
     */
    public void setPreviousNodeId(Integer previousNodeId) {
        this.previousNodeId = previousNodeId;
    }

    /**
     * Inserted node data.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Inserted node data.
     */
    public void setNode(Node node) {
        this.node = node;
    }
}
