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
 * Text range within a resource
 * All numbers are zero-based
 */
public class SourceRange {
    private Integer startLine;

    private Integer startColumn;

    private Integer endLine;

    private Integer endColumn;

    /**
     * Start line of range.
     */
    public Integer getStartLine() {
        return startLine;
    }

    /**
     * Start line of range.
     */
    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    /**
     * Start column of range (inclusive).
     */
    public Integer getStartColumn() {
        return startColumn;
    }

    /**
     * Start column of range (inclusive).
     */
    public void setStartColumn(Integer startColumn) {
        this.startColumn = startColumn;
    }

    /**
     * End line of range
     */
    public Integer getEndLine() {
        return endLine;
    }

    /**
     * End line of range
     */
    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    /**
     * End column of range (exclusive).
     */
    public Integer getEndColumn() {
        return endColumn;
    }

    /**
     * End column of range (exclusive).
     */
    public void setEndColumn(Integer endColumn) {
        this.endColumn = endColumn;
    }
}
