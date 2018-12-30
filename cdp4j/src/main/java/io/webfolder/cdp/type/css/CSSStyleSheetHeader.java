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
package io.webfolder.cdp.type.css;

/**
 * CSS stylesheet metainformation
 */
public class CSSStyleSheetHeader {
    private String styleSheetId;

    private String frameId;

    private String sourceURL;

    private String sourceMapURL;

    private StyleSheetOrigin origin;

    private String title;

    private Integer ownerNode;

    private Boolean disabled;

    private Boolean hasSourceURL;

    private Boolean isInline;

    private Double startLine;

    private Double startColumn;

    private Double length;

    /**
     * The stylesheet identifier.
     */
    public String getStyleSheetId() {
        return styleSheetId;
    }

    /**
     * The stylesheet identifier.
     */
    public void setStyleSheetId(String styleSheetId) {
        this.styleSheetId = styleSheetId;
    }

    /**
     * Owner frame identifier.
     */
    public String getFrameId() {
        return frameId;
    }

    /**
     * Owner frame identifier.
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    /**
     * Stylesheet resource URL.
     */
    public String getSourceURL() {
        return sourceURL;
    }

    /**
     * Stylesheet resource URL.
     */
    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    /**
     * URL of source map associated with the stylesheet (if any).
     */
    public String getSourceMapURL() {
        return sourceMapURL;
    }

    /**
     * URL of source map associated with the stylesheet (if any).
     */
    public void setSourceMapURL(String sourceMapURL) {
        this.sourceMapURL = sourceMapURL;
    }

    /**
     * Stylesheet origin.
     */
    public StyleSheetOrigin getOrigin() {
        return origin;
    }

    /**
     * Stylesheet origin.
     */
    public void setOrigin(StyleSheetOrigin origin) {
        this.origin = origin;
    }

    /**
     * Stylesheet title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Stylesheet title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The backend id for the owner node of the stylesheet.
     */
    public Integer getOwnerNode() {
        return ownerNode;
    }

    /**
     * The backend id for the owner node of the stylesheet.
     */
    public void setOwnerNode(Integer ownerNode) {
        this.ownerNode = ownerNode;
    }

    /**
     * Denotes whether the stylesheet is disabled.
     */
    public Boolean isDisabled() {
        return disabled;
    }

    /**
     * Denotes whether the stylesheet is disabled.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Whether the sourceURL field value comes from the sourceURL comment.
     */
    public Boolean isHasSourceURL() {
        return hasSourceURL;
    }

    /**
     * Whether the sourceURL field value comes from the sourceURL comment.
     */
    public void setHasSourceURL(Boolean hasSourceURL) {
        this.hasSourceURL = hasSourceURL;
    }

    /**
     * Whether this stylesheet is created for STYLE tag by parser. This flag is not set for document.written STYLE tags.
     */
    public Boolean isIsInline() {
        return isInline;
    }

    /**
     * Whether this stylesheet is created for STYLE tag by parser. This flag is not set for document.written STYLE tags.
     */
    public void setIsInline(Boolean isInline) {
        this.isInline = isInline;
    }

    /**
     * Line offset of the stylesheet within the resource (zero based).
     */
    public Double getStartLine() {
        return startLine;
    }

    /**
     * Line offset of the stylesheet within the resource (zero based).
     */
    public void setStartLine(Double startLine) {
        this.startLine = startLine;
    }

    /**
     * Column offset of the stylesheet within the resource (zero based).
     */
    public Double getStartColumn() {
        return startColumn;
    }

    /**
     * Column offset of the stylesheet within the resource (zero based).
     */
    public void setStartColumn(Double startColumn) {
        this.startColumn = startColumn;
    }

    /**
     * Size of the content (in characters).
     */
    public Double getLength() {
        return length;
    }

    /**
     * Size of the content (in characters).
     */
    public void setLength(Double length) {
        this.length = length;
    }
}
