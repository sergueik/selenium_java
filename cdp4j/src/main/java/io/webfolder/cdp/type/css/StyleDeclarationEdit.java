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
 * A descriptor of operation to mutate style declaration text
 */
public class StyleDeclarationEdit {
    private String styleSheetId;

    private SourceRange range;

    private String text;

    /**
     * The css style sheet identifier.
     */
    public String getStyleSheetId() {
        return styleSheetId;
    }

    /**
     * The css style sheet identifier.
     */
    public void setStyleSheetId(String styleSheetId) {
        this.styleSheetId = styleSheetId;
    }

    /**
     * The range of the style text in the enclosing stylesheet.
     */
    public SourceRange getRange() {
        return range;
    }

    /**
     * The range of the style text in the enclosing stylesheet.
     */
    public void setRange(SourceRange range) {
        this.range = range;
    }

    /**
     * New style text.
     */
    public String getText() {
        return text;
    }

    /**
     * New style text.
     */
    public void setText(String text) {
        this.text = text;
    }
}
