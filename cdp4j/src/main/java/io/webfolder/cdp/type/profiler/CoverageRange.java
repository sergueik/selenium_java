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
package io.webfolder.cdp.type.profiler;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Coverage data for a source range
 */
@Experimental
public class CoverageRange {
    private Integer startOffset;

    private Integer endOffset;

    private Integer count;

    /**
     * JavaScript script source offset for the range start.
     */
    public Integer getStartOffset() {
        return startOffset;
    }

    /**
     * JavaScript script source offset for the range start.
     */
    public void setStartOffset(Integer startOffset) {
        this.startOffset = startOffset;
    }

    /**
     * JavaScript script source offset for the range end.
     */
    public Integer getEndOffset() {
        return endOffset;
    }

    /**
     * JavaScript script source offset for the range end.
     */
    public void setEndOffset(Integer endOffset) {
        this.endOffset = endOffset;
    }

    /**
     * Collected execution count of the source range.
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Collected execution count of the source range.
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
