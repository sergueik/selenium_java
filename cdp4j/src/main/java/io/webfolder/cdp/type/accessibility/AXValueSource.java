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

/**
 * A single source for a computed AX property
 */
public class AXValueSource {
    private AXValueSourceType type;

    private AXValue value;

    private String attribute;

    private AXValue attributeValue;

    private Boolean superseded;

    private AXValueNativeSourceType nativeSource;

    private AXValue nativeSourceValue;

    private Boolean invalid;

    private String invalidReason;

    /**
     * What type of source this is.
     */
    public AXValueSourceType getType() {
        return type;
    }

    /**
     * What type of source this is.
     */
    public void setType(AXValueSourceType type) {
        this.type = type;
    }

    /**
     * The value of this property source.
     */
    public AXValue getValue() {
        return value;
    }

    /**
     * The value of this property source.
     */
    public void setValue(AXValue value) {
        this.value = value;
    }

    /**
     * The name of the relevant attribute, if any.
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * The name of the relevant attribute, if any.
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * The value of the relevant attribute, if any.
     */
    public AXValue getAttributeValue() {
        return attributeValue;
    }

    /**
     * The value of the relevant attribute, if any.
     */
    public void setAttributeValue(AXValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * Whether this source is superseded by a higher priority source.
     */
    public Boolean isSuperseded() {
        return superseded;
    }

    /**
     * Whether this source is superseded by a higher priority source.
     */
    public void setSuperseded(Boolean superseded) {
        this.superseded = superseded;
    }

    /**
     * The native markup source for this value, e.g. a <label> element.
     */
    public AXValueNativeSourceType getNativeSource() {
        return nativeSource;
    }

    /**
     * The native markup source for this value, e.g. a <label> element.
     */
    public void setNativeSource(AXValueNativeSourceType nativeSource) {
        this.nativeSource = nativeSource;
    }

    /**
     * The value, such as a node or node list, of the native source.
     */
    public AXValue getNativeSourceValue() {
        return nativeSourceValue;
    }

    /**
     * The value, such as a node or node list, of the native source.
     */
    public void setNativeSourceValue(AXValue nativeSourceValue) {
        this.nativeSourceValue = nativeSourceValue;
    }

    /**
     * Whether the value for this property is invalid.
     */
    public Boolean isInvalid() {
        return invalid;
    }

    /**
     * Whether the value for this property is invalid.
     */
    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    /**
     * Reason for the value being invalid, if it is.
     */
    public String getInvalidReason() {
        return invalidReason;
    }

    /**
     * Reason for the value being invalid, if it is.
     */
    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }
}
