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
package io.webfolder.cdp.type.css;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Media query expression descriptor
 */
@Experimental
public class MediaQueryExpression {
    private Double value;

    private String unit;

    private String feature;

    private SourceRange valueRange;

    private Double computedLength;

    /**
     * Media query expression value.
     */
    public Double getValue() {
        return value;
    }

    /**
     * Media query expression value.
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Media query expression units.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Media query expression units.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Media query expression feature.
     */
    public String getFeature() {
        return feature;
    }

    /**
     * Media query expression feature.
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    /**
     * The associated range of the value text in the enclosing stylesheet (if available).
     */
    public SourceRange getValueRange() {
        return valueRange;
    }

    /**
     * The associated range of the value text in the enclosing stylesheet (if available).
     */
    public void setValueRange(SourceRange valueRange) {
        this.valueRange = valueRange;
    }

    /**
     * Computed length of media query expression (if applicable).
     */
    public Double getComputedLength() {
        return computedLength;
    }

    /**
     * Computed length of media query expression (if applicable).
     */
    public void setComputedLength(Double computedLength) {
        this.computedLength = computedLength;
    }
}
