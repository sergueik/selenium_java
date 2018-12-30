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
package io.webfolder.cdp.type.runtime;

import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.constant.ObjectSubtypeHint;
import io.webfolder.cdp.type.constant.PropertyPreviewType;

@Experimental
public class PropertyPreview {
    private String name;

    private PropertyPreviewType type;

    private String value;

    private ObjectPreview valuePreview;

    private ObjectSubtypeHint subtype;

    /**
     * Property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Property name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Object type. Accessor means that the property itself is an accessor property.
     */
    public PropertyPreviewType getType() {
        return type;
    }

    /**
     * Object type. Accessor means that the property itself is an accessor property.
     */
    public void setType(PropertyPreviewType type) {
        this.type = type;
    }

    /**
     * User-friendly property value string.
     */
    public String getValue() {
        return value;
    }

    /**
     * User-friendly property value string.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Nested value preview.
     */
    public ObjectPreview getValuePreview() {
        return valuePreview;
    }

    /**
     * Nested value preview.
     */
    public void setValuePreview(ObjectPreview valuePreview) {
        this.valuePreview = valuePreview;
    }

    /**
     * Object subtype hint. Specified for <code>object</code> type values only.
     */
    public ObjectSubtypeHint getSubtype() {
        return subtype;
    }

    /**
     * Object subtype hint. Specified for <code>object</code> type values only.
     */
    public void setSubtype(ObjectSubtypeHint subtype) {
        this.subtype = subtype;
    }
}
