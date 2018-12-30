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
package io.webfolder.cdp.type.network;

import io.webfolder.cdp.type.constant.InitiatorType;
import io.webfolder.cdp.type.runtime.StackTrace;

/**
 * Information about the request initiator
 */
public class Initiator {
    private InitiatorType type;

    private StackTrace stack;

    private String url;

    private Double lineNumber;

    /**
     * Type of this initiator.
     */
    public InitiatorType getType() {
        return type;
    }

    /**
     * Type of this initiator.
     */
    public void setType(InitiatorType type) {
        this.type = type;
    }

    /**
     * Initiator JavaScript stack trace, set for Script only.
     */
    public StackTrace getStack() {
        return stack;
    }

    /**
     * Initiator JavaScript stack trace, set for Script only.
     */
    public void setStack(StackTrace stack) {
        this.stack = stack;
    }

    /**
     * Initiator URL, set for Parser type or for Script type (when script is importing module).
     */
    public String getUrl() {
        return url;
    }

    /**
     * Initiator URL, set for Parser type or for Script type (when script is importing module).
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Initiator line number, set for Parser type or for Script type (when script is importing module) (0-based).
     */
    public Double getLineNumber() {
        return lineNumber;
    }

    /**
     * Initiator line number, set for Parser type or for Script type (when script is importing module) (0-based).
     */
    public void setLineNumber(Double lineNumber) {
        this.lineNumber = lineNumber;
    }
}
