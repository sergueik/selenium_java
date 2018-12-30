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
package io.webfolder.cdp.type.console;

import io.webfolder.cdp.type.constant.MessageSeverity;
import io.webfolder.cdp.type.constant.MessageSource;

/**
 * Console message
 */
public class ConsoleMessage {
    private MessageSource source;

    private MessageSeverity level;

    private String text;

    private String url;

    private Integer line;

    private Integer column;

    /**
     * Message source.
     */
    public MessageSource getSource() {
        return source;
    }

    /**
     * Message source.
     */
    public void setSource(MessageSource source) {
        this.source = source;
    }

    /**
     * Message severity.
     */
    public MessageSeverity getLevel() {
        return level;
    }

    /**
     * Message severity.
     */
    public void setLevel(MessageSeverity level) {
        this.level = level;
    }

    /**
     * Message text.
     */
    public String getText() {
        return text;
    }

    /**
     * Message text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * URL of the message origin.
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL of the message origin.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Line number in the resource that generated this message (1-based).
     */
    public Integer getLine() {
        return line;
    }

    /**
     * Line number in the resource that generated this message (1-based).
     */
    public void setLine(Integer line) {
        this.line = line;
    }

    /**
     * Column number in the resource that generated this message (1-based).
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * Column number in the resource that generated this message (1-based).
     */
    public void setColumn(Integer column) {
        this.column = column;
    }
}
