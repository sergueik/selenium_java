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
 * Detailed information about exception (or error) that was thrown during script compilation or execution
 */
public class ExceptionDetails {
    private Integer exceptionId;

    private String text;

    private Integer lineNumber;

    private Integer columnNumber;

    private String scriptId;

    private String url;

    private StackTrace stackTrace;

    private RemoteObject exception;

    private Integer executionContextId;

    /**
     * Exception id.
     */
    public Integer getExceptionId() {
        return exceptionId;
    }

    /**
     * Exception id.
     */
    public void setExceptionId(Integer exceptionId) {
        this.exceptionId = exceptionId;
    }

    /**
     * Exception text, which should be used together with exception object when available.
     */
    public String getText() {
        return text;
    }

    /**
     * Exception text, which should be used together with exception object when available.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Line number of the exception location (0-based).
     */
    public Integer getLineNumber() {
        return lineNumber;
    }

    /**
     * Line number of the exception location (0-based).
     */
    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Column number of the exception location (0-based).
     */
    public Integer getColumnNumber() {
        return columnNumber;
    }

    /**
     * Column number of the exception location (0-based).
     */
    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * Script ID of the exception location.
     */
    public String getScriptId() {
        return scriptId;
    }

    /**
     * Script ID of the exception location.
     */
    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    /**
     * URL of the exception location, to be used when the script was not reported.
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL of the exception location, to be used when the script was not reported.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * JavaScript stack trace if available.
     */
    public StackTrace getStackTrace() {
        return stackTrace;
    }

    /**
     * JavaScript stack trace if available.
     */
    public void setStackTrace(StackTrace stackTrace) {
        this.stackTrace = stackTrace;
    }

    /**
     * Exception object if available.
     */
    public RemoteObject getException() {
        return exception;
    }

    /**
     * Exception object if available.
     */
    public void setException(RemoteObject exception) {
        this.exception = exception;
    }

    /**
     * Identifier of the context where exception happened.
     */
    public Integer getExecutionContextId() {
        return executionContextId;
    }

    /**
     * Identifier of the context where exception happened.
     */
    public void setExecutionContextId(Integer executionContextId) {
        this.executionContextId = executionContextId;
    }
}
