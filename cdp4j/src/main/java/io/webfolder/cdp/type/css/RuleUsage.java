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
 * CSS coverage information
 */
@Experimental
public class RuleUsage {
    private String styleSheetId;

    private Double startOffset;

    private Double endOffset;

    private Boolean used;

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
     * Offset of the start of the rule (including selector) from the beginning of the stylesheet.
     */
    public Double getStartOffset() {
        return startOffset;
    }

    /**
     * Offset of the start of the rule (including selector) from the beginning of the stylesheet.
     */
    public void setStartOffset(Double startOffset) {
        this.startOffset = startOffset;
    }

    /**
     * Offset of the end of the rule body from the beginning of the stylesheet.
     */
    public Double getEndOffset() {
        return endOffset;
    }

    /**
     * Offset of the end of the rule body from the beginning of the stylesheet.
     */
    public void setEndOffset(Double endOffset) {
        this.endOffset = endOffset;
    }

    /**
     * Indicates whether the rule was actually used by some element in the page.
     */
    public Boolean isUsed() {
        return used;
    }

    /**
     * Indicates whether the rule was actually used by some element in the page.
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }
}
