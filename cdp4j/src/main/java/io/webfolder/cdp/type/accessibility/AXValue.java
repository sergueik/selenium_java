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

import java.util.ArrayList;
import java.util.List;

/**
 * A single computed AX property
 */
public class AXValue {
    private AXValueType type;

    private Object value;

    private List<AXRelatedNode> relatedNodes = new ArrayList<>();

    private List<AXValueSource> sources = new ArrayList<>();

    /**
     * The type of this value.
     */
    public AXValueType getType() {
        return type;
    }

    /**
     * The type of this value.
     */
    public void setType(AXValueType type) {
        this.type = type;
    }

    /**
     * The computed value of this property.
     */
    public Object getValue() {
        return value;
    }

    /**
     * The computed value of this property.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * One or more related nodes, if applicable.
     */
    public List<AXRelatedNode> getRelatedNodes() {
        return relatedNodes;
    }

    /**
     * One or more related nodes, if applicable.
     */
    public void setRelatedNodes(List<AXRelatedNode> relatedNodes) {
        this.relatedNodes = relatedNodes;
    }

    /**
     * The sources which contributed to the computation of this property.
     */
    public List<AXValueSource> getSources() {
        return sources;
    }

    /**
     * The sources which contributed to the computation of this property.
     */
    public void setSources(List<AXValueSource> sources) {
        this.sources = sources;
    }
}
