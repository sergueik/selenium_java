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
package io.webfolder.cdp.type.page;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Error while paring app manifest
 */
@Experimental
public class AppManifestError {
    private String message;

    private Integer critical;

    private Integer line;

    private Integer column;

    /**
     * Error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Error message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * If criticial, this is a non-recoverable parse error.
     */
    public Integer getCritical() {
        return critical;
    }

    /**
     * If criticial, this is a non-recoverable parse error.
     */
    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    /**
     * Error line.
     */
    public Integer getLine() {
        return line;
    }

    /**
     * Error line.
     */
    public void setLine(Integer line) {
        this.line = line;
    }

    /**
     * Error column.
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * Error column.
     */
    public void setColumn(Integer column) {
        this.column = column;
    }
}
