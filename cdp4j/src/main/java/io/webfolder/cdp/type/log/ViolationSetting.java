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
package io.webfolder.cdp.type.log;

import io.webfolder.cdp.type.constant.ViolationType;

/**
 * Violation configuration setting
 */
public class ViolationSetting {
    private ViolationType name;

    private Double threshold;

    /**
     * Violation type.
     */
    public ViolationType getName() {
        return name;
    }

    /**
     * Violation type.
     */
    public void setName(ViolationType name) {
        this.name = name;
    }

    /**
     * Time threshold to trigger upon.
     */
    public Double getThreshold() {
        return threshold;
    }

    /**
     * Time threshold to trigger upon.
     */
    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
}
