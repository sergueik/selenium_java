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
package io.webfolder.cdp.type.accessibility;

public class AXRelatedNode {
    private Integer backendDOMNodeId;

    private String idref;

    private String text;

    /**
     * The BackendNodeId of the related DOM node.
     */
    public Integer getBackendDOMNodeId() {
        return backendDOMNodeId;
    }

    /**
     * The BackendNodeId of the related DOM node.
     */
    public void setBackendDOMNodeId(Integer backendDOMNodeId) {
        this.backendDOMNodeId = backendDOMNodeId;
    }

    /**
     * The IDRef value provided, if any.
     */
    public String getIdref() {
        return idref;
    }

    /**
     * The IDRef value provided, if any.
     */
    public void setIdref(String idref) {
        this.idref = idref;
    }

    /**
     * The text alternative of this node in the current context.
     */
    public String getText() {
        return text;
    }

    /**
     * The text alternative of this node in the current context.
     */
    public void setText(String text) {
        this.text = text;
    }
}
