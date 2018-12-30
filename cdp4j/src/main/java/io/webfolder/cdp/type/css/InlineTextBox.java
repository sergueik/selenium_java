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

import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.dom.Rect;

/**
 * Details of post layout rendered text positions
 * The exact layout should not be regarded as stable and may change between versions
 */
@Experimental
public class InlineTextBox {
    private Rect boundingBox;

    private Integer startCharacterIndex;

    private Integer numCharacters;

    /**
     * The absolute position bounding box.
     */
    public Rect getBoundingBox() {
        return boundingBox;
    }

    /**
     * The absolute position bounding box.
     */
    public void setBoundingBox(Rect boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * The starting index in characters, for this post layout textbox substring.
     */
    public Integer getStartCharacterIndex() {
        return startCharacterIndex;
    }

    /**
     * The starting index in characters, for this post layout textbox substring.
     */
    public void setStartCharacterIndex(Integer startCharacterIndex) {
        this.startCharacterIndex = startCharacterIndex;
    }

    /**
     * The number of characters in this post layout textbox substring.
     */
    public Integer getNumCharacters() {
        return numCharacters;
    }

    /**
     * The number of characters in this post layout textbox substring.
     */
    public void setNumCharacters(Integer numCharacters) {
        this.numCharacters = numCharacters;
    }
}
