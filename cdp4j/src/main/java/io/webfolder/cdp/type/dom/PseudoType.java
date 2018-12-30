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
package io.webfolder.cdp.type.dom;

import com.google.gson.annotations.SerializedName;

/**
 * Pseudo element type
 */
public enum PseudoType {
    @SerializedName("first-line")
    FirstLine("first-line"),

    @SerializedName("first-letter")
    FirstLetter("first-letter"),

    @SerializedName("before")
    Before("before"),

    @SerializedName("after")
    After("after"),

    @SerializedName("backdrop")
    Backdrop("backdrop"),

    @SerializedName("selection")
    Selection("selection"),

    @SerializedName("first-line-inherited")
    FirstLineInherited("first-line-inherited"),

    @SerializedName("scrollbar")
    Scrollbar("scrollbar"),

    @SerializedName("scrollbar-thumb")
    ScrollbarThumb("scrollbar-thumb"),

    @SerializedName("scrollbar-button")
    ScrollbarButton("scrollbar-button"),

    @SerializedName("scrollbar-track")
    ScrollbarTrack("scrollbar-track"),

    @SerializedName("scrollbar-track-piece")
    ScrollbarTrackPiece("scrollbar-track-piece"),

    @SerializedName("scrollbar-corner")
    ScrollbarCorner("scrollbar-corner"),

    @SerializedName("resizer")
    Resizer("resizer"),

    @SerializedName("input-list-button")
    InputListButton("input-list-button");

    public final String value;

    PseudoType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
