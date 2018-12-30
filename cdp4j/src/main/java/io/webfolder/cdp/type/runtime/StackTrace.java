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
package io.webfolder.cdp.type.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Call frames for assertions or error messages
 */
public class StackTrace {
    private String description;

    private List<CallFrame> callFrames = new ArrayList<>();

    private StackTrace parent;

    private CallFrame promiseCreationFrame;

    /**
     * String label of this stack trace. For async traces this may be a name of the function that initiated the async call.
     */
    public String getDescription() {
        return description;
    }

    /**
     * String label of this stack trace. For async traces this may be a name of the function that initiated the async call.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * JavaScript function name.
     */
    public List<CallFrame> getCallFrames() {
        return callFrames;
    }

    /**
     * JavaScript function name.
     */
    public void setCallFrames(List<CallFrame> callFrames) {
        this.callFrames = callFrames;
    }

    /**
     * Asynchronous JavaScript stack trace that preceded this stack, if available.
     */
    public StackTrace getParent() {
        return parent;
    }

    /**
     * Asynchronous JavaScript stack trace that preceded this stack, if available.
     */
    public void setParent(StackTrace parent) {
        this.parent = parent;
    }

    /**
     * Creation frame of the Promise which produced the next synchronous trace when resolved, if available.
     */
    public CallFrame getPromiseCreationFrame() {
        return promiseCreationFrame;
    }

    /**
     * Creation frame of the Promise which produced the next synchronous trace when resolved, if available.
     */
    public void setPromiseCreationFrame(CallFrame promiseCreationFrame) {
        this.promiseCreationFrame = promiseCreationFrame;
    }
}
