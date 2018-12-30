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
import java.util.ArrayList;
import java.util.List;

/**
 * Coverage data for a JavaScript function
 */
@Experimental
public class FunctionCoverage {
    private String functionName;

    private List<CoverageRange> ranges = new ArrayList<>();

    private Boolean isBlockCoverage;

    /**
     * JavaScript function name.
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * JavaScript function name.
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * Source ranges inside the function with coverage data.
     */
    public List<CoverageRange> getRanges() {
        return ranges;
    }

    /**
     * Source ranges inside the function with coverage data.
     */
    public void setRanges(List<CoverageRange> ranges) {
        this.ranges = ranges;
    }

    /**
     * Whether coverage data for this function has block granularity.
     */
    public Boolean isIsBlockCoverage() {
        return isBlockCoverage;
    }

    /**
     * Whether coverage data for this function has block granularity.
     */
    public void setIsBlockCoverage(Boolean isBlockCoverage) {
        this.isBlockCoverage = isBlockCoverage;
    }
}
