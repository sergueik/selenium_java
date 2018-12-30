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
 * Data for a simple selector (these are delimited by commas in a selector list)
 */
public class Value {
    private String text;

    private SourceRange range;

    /**
     * Value text.
     */
    public String getText() {
        return text;
    }

    /**
     * Value text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Value range in the underlying resource (if available).
     */
    public SourceRange getRange() {
        return range;
    }

    /**
     * Value range in the underlying resource (if available).
     */
    public void setRange(SourceRange range) {
        this.range = range;
    }
}
