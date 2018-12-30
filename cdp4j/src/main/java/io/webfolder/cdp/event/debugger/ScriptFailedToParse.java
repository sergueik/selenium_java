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
package io.webfolder.cdp.event.debugger;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.runtime.StackTrace;

/**
 * Fired when virtual machine fails to parse the script
 */
@Domain("Debugger")
@EventName("scriptFailedToParse")
public class ScriptFailedToParse {
    private String scriptId;

    private String url;

    private Integer startLine;

    private Integer startColumn;

    private Integer endLine;

    private Integer endColumn;

    private Integer executionContextId;

    private String hash;

    private Object executionContextAuxData;

    private String sourceMapURL;

    private Boolean hasSourceURL;

    private Boolean isModule;

    private Integer length;

    private StackTrace stackTrace;

    /**
     * Identifier of the script parsed.
     */
    public String getScriptId() {
        return scriptId;
    }

    /**
     * Identifier of the script parsed.
     */
    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    /**
     * URL or name of the script parsed (if any).
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL or name of the script parsed (if any).
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Line offset of the script within the resource with given URL (for script tags).
     */
    public Integer getStartLine() {
        return startLine;
    }

    /**
     * Line offset of the script within the resource with given URL (for script tags).
     */
    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    /**
     * Column offset of the script within the resource with given URL.
     */
    public Integer getStartColumn() {
        return startColumn;
    }

    /**
     * Column offset of the script within the resource with given URL.
     */
    public void setStartColumn(Integer startColumn) {
        this.startColumn = startColumn;
    }

    /**
     * Last line of the script.
     */
    public Integer getEndLine() {
        return endLine;
    }

    /**
     * Last line of the script.
     */
    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    /**
     * Length of the last line of the script.
     */
    public Integer getEndColumn() {
        return endColumn;
    }

    /**
     * Length of the last line of the script.
     */
    public void setEndColumn(Integer endColumn) {
        this.endColumn = endColumn;
    }

    /**
     * Specifies script creation context.
     */
    public Integer getExecutionContextId() {
        return executionContextId;
    }

    /**
     * Specifies script creation context.
     */
    public void setExecutionContextId(Integer executionContextId) {
        this.executionContextId = executionContextId;
    }

    /**
     * Content hash of the script.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Content hash of the script.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Embedder-specific auxiliary data.
     */
    public Object getExecutionContextAuxData() {
        return executionContextAuxData;
    }

    /**
     * Embedder-specific auxiliary data.
     */
    public void setExecutionContextAuxData(Object executionContextAuxData) {
        this.executionContextAuxData = executionContextAuxData;
    }

    /**
     * URL of source map associated with script (if any).
     */
    public String getSourceMapURL() {
        return sourceMapURL;
    }

    /**
     * URL of source map associated with script (if any).
     */
    public void setSourceMapURL(String sourceMapURL) {
        this.sourceMapURL = sourceMapURL;
    }

    /**
     * True, if this script has sourceURL.
     */
    public Boolean isHasSourceURL() {
        return hasSourceURL;
    }

    /**
     * True, if this script has sourceURL.
     */
    public void setHasSourceURL(Boolean hasSourceURL) {
        this.hasSourceURL = hasSourceURL;
    }

    /**
     * True, if this script is ES6 module.
     */
    public Boolean isIsModule() {
        return isModule;
    }

    /**
     * True, if this script is ES6 module.
     */
    public void setIsModule(Boolean isModule) {
        this.isModule = isModule;
    }

    /**
     * This script length.
     */
    public Integer getLength() {
        return length;
    }

    /**
     * This script length.
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * JavaScript top stack frame of where the script parsed event was triggered if available.
     */
    public StackTrace getStackTrace() {
        return stackTrace;
    }

    /**
     * JavaScript top stack frame of where the script parsed event was triggered if available.
     */
    public void setStackTrace(StackTrace stackTrace) {
        this.stackTrace = stackTrace;
    }
}
