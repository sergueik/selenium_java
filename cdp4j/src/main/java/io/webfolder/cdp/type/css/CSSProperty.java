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

/**
 * CSS property declaration data
 */
public class CSSProperty {
    private String name;

    private String value;

    private Boolean important;

    private Boolean implicit;

    private String text;

    private Boolean parsedOk;

    private Boolean disabled;

    private SourceRange range;

    /**
     * The property name.
     */
    public String getName() {
        return name;
    }

    /**
     * The property name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The property value.
     */
    public String getValue() {
        return value;
    }

    /**
     * The property value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Whether the property has "!important" annotation (implies <code>false</code> if absent).
     */
    public Boolean isImportant() {
        return important;
    }

    /**
     * Whether the property has "!important" annotation (implies <code>false</code> if absent).
     */
    public void setImportant(Boolean important) {
        this.important = important;
    }

    /**
     * Whether the property is implicit (implies <code>false</code> if absent).
     */
    public Boolean isImplicit() {
        return implicit;
    }

    /**
     * Whether the property is implicit (implies <code>false</code> if absent).
     */
    public void setImplicit(Boolean implicit) {
        this.implicit = implicit;
    }

    /**
     * The full property text as specified in the style.
     */
    public String getText() {
        return text;
    }

    /**
     * The full property text as specified in the style.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Whether the property is understood by the browser (implies <code>true</code> if absent).
     */
    public Boolean isParsedOk() {
        return parsedOk;
    }

    /**
     * Whether the property is understood by the browser (implies <code>true</code> if absent).
     */
    public void setParsedOk(Boolean parsedOk) {
        this.parsedOk = parsedOk;
    }

    /**
     * Whether the property is disabled by the user (present for source-based properties only).
     */
    public Boolean isDisabled() {
        return disabled;
    }

    /**
     * Whether the property is disabled by the user (present for source-based properties only).
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * The entire property range in the enclosing style declaration (if available).
     */
    public SourceRange getRange() {
        return range;
    }

    /**
     * The entire property range in the enclosing style declaration (if available).
     */
    public void setRange(SourceRange range) {
        this.range = range;
    }
}
