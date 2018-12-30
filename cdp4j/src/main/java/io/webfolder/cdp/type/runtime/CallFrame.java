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

/**
 * Stack entry for runtime errors and assertions
 */
public class CallFrame {
    private String functionName;

    private String scriptId;

    private String url;

    private Integer lineNumber;

    private Integer columnNumber;

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
     * JavaScript script id.
     */
    public String getScriptId() {
        return scriptId;
    }

    /**
     * JavaScript script id.
     */
    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    /**
     * JavaScript script name or url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * JavaScript script name or url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * JavaScript script line number (0-based).
     */
    public Integer getLineNumber() {
        return lineNumber;
    }

    /**
     * JavaScript script line number (0-based).
     */
    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * JavaScript script column number (0-based).
     */
    public Integer getColumnNumber() {
        return columnNumber;
    }

    /**
     * JavaScript script column number (0-based).
     */
    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }
}
