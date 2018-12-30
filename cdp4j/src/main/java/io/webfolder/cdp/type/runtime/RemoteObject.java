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

import io.webfolder.cdp.type.constant.ObjectSubtypeHint;
import io.webfolder.cdp.type.constant.ObjectType;

/**
 * Mirror object referencing original JavaScript object
 */
public class RemoteObject {
    private ObjectType type;

    private ObjectSubtypeHint subtype;

    private String className;

    private Object value;

    private UnserializableValue unserializableValue;

    private String description;

    private String objectId;

    private ObjectPreview preview;

    private CustomPreview customPreview;

    /**
     * Object type.
     */
    public ObjectType getType() {
        return type;
    }

    /**
     * Object type.
     */
    public void setType(ObjectType type) {
        this.type = type;
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

    /**
     * Object class (constructor) name. Specified for <code>object</code> type values only.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Object class (constructor) name. Specified for <code>object</code> type values only.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Remote object value in case of primitive values or JSON values (if it was requested).
     */
    public Object getValue() {
        return value;
    }

    /**
     * Remote object value in case of primitive values or JSON values (if it was requested).
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Primitive value which can not be JSON-stringified does not have <code>value</code>, but gets this property.
     */
    public UnserializableValue getUnserializableValue() {
        return unserializableValue;
    }

    /**
     * Primitive value which can not be JSON-stringified does not have <code>value</code>, but gets this property.
     */
    public void setUnserializableValue(UnserializableValue unserializableValue) {
        this.unserializableValue = unserializableValue;
    }

    /**
     * String representation of the object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * String representation of the object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Unique object identifier (for non-primitive values).
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Unique object identifier (for non-primitive values).
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Preview containing abbreviated property values. Specified for <code>object</code> type values only.
     */
    public ObjectPreview getPreview() {
        return preview;
    }

    /**
     * Preview containing abbreviated property values. Specified for <code>object</code> type values only.
     */
    public void setPreview(ObjectPreview preview) {
        this.preview = preview;
    }

    public CustomPreview getCustomPreview() {
        return customPreview;
    }

    public void setCustomPreview(CustomPreview customPreview) {
        this.customPreview = customPreview;
    }
}
