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
 * Specifies a number of samples attributed to a certain source position
 */
@Experimental
public class PositionTickInfo {
    private Integer line;

    private Integer ticks;

    /**
     * Source line number (1-based).
     */
    public Integer getLine() {
        return line;
    }

    /**
     * Source line number (1-based).
     */
    public void setLine(Integer line) {
        this.line = line;
    }

    /**
     * Number of samples attributed to the source line.
     */
    public Integer getTicks() {
        return ticks;
    }

    /**
     * Number of samples attributed to the source line.
     */
    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }
}
