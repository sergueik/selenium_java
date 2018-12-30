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
package io.webfolder.cdp.type.dom;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Backend node with a friendly name
 */
@Experimental
public class BackendNode {
    private Integer nodeType;

    private String nodeName;

    private Integer backendNodeId;

    /**
     * <code>Node</code>'s nodeType.
     */
    public Integer getNodeType() {
        return nodeType;
    }

    /**
     * <code>Node</code>'s nodeType.
     */
    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * <code>Node</code>'s nodeName.
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * <code>Node</code>'s nodeName.
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getBackendNodeId() {
        return backendNodeId;
    }

    public void setBackendNodeId(Integer backendNodeId) {
        this.backendNodeId = backendNodeId;
    }
}
