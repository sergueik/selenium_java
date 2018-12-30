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
 * CSS rule representation
 */
public class CSSRule {
    private String styleSheetId;

    private SelectorList selectorList;

    private StyleSheetOrigin origin;

    private CSSStyle style;

    private List<CSSMedia> media = new ArrayList<>();

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
     * Rule selector data.
     */
    public SelectorList getSelectorList() {
        return selectorList;
    }

    /**
     * Rule selector data.
     */
    public void setSelectorList(SelectorList selectorList) {
        this.selectorList = selectorList;
    }

    /**
     * Parent stylesheet's origin.
     */
    public StyleSheetOrigin getOrigin() {
        return origin;
    }

    /**
     * Parent stylesheet's origin.
     */
    public void setOrigin(StyleSheetOrigin origin) {
        this.origin = origin;
    }

    /**
     * Associated style declaration.
     */
    public CSSStyle getStyle() {
        return style;
    }

    /**
     * Associated style declaration.
     */
    public void setStyle(CSSStyle style) {
        this.style = style;
    }

    /**
     * Media list array (for rules involving media queries). The array enumerates media queries starting with the innermost one, going outwards.
     */
    public List<CSSMedia> getMedia() {
        return media;
    }

    /**
     * Media list array (for rules involving media queries). The array enumerates media queries starting with the innermost one, going outwards.
     */
    public void setMedia(List<CSSMedia> media) {
        this.media = media;
    }
}
