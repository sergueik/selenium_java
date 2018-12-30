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

import java.util.ArrayList;
import java.util.List;

/**
 * CSS style representation
 */
public class CSSStyle {
    private String styleSheetId;

    private List<CSSProperty> cssProperties = new ArrayList<>();

    private List<ShorthandEntry> shorthandEntries = new ArrayList<>();

    private String cssText;

    private SourceRange range;

    /**
     * The css style sheet identifier (absent for user agent stylesheet and user-specified stylesheet rules) this rule came from.
     */
    public String getStyleSheetId() {
        return styleSheetId;
    }

    /**
     * The css style sheet identifier (absent for user agent stylesheet and user-specified stylesheet rules) this rule came from.
     */
    public void setStyleSheetId(String styleSheetId) {
        this.styleSheetId = styleSheetId;
    }

    /**
     * CSS properties in the style.
     */
    public List<CSSProperty> getCssProperties() {
        return cssProperties;
    }

    /**
     * CSS properties in the style.
     */
    public void setCssProperties(List<CSSProperty> cssProperties) {
        this.cssProperties = cssProperties;
    }

    /**
     * Computed values for all shorthands found in the style.
     */
    public List<ShorthandEntry> getShorthandEntries() {
        return shorthandEntries;
    }

    /**
     * Computed values for all shorthands found in the style.
     */
    public void setShorthandEntries(List<ShorthandEntry> shorthandEntries) {
        this.shorthandEntries = shorthandEntries;
    }

    /**
     * Style declaration text (if available).
     */
    public String getCssText() {
        return cssText;
    }

    /**
     * Style declaration text (if available).
     */
    public void setCssText(String cssText) {
        this.cssText = cssText;
    }

    /**
     * Style declaration range in the enclosing stylesheet (if available).
     */
    public SourceRange getRange() {
        return range;
    }

    /**
     * Style declaration range in the enclosing stylesheet (if available).
     */
    public void setRange(SourceRange range) {
        this.range = range;
    }
}
