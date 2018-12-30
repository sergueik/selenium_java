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

import com.google.gson.annotations.SerializedName;

/**
 * Resource type as it was perceived by the rendering engine
 */
public enum ResourceType {
    @SerializedName("Document")
    Document("Document"),

    @SerializedName("Stylesheet")
    Stylesheet("Stylesheet"),

    @SerializedName("Image")
    Image("Image"),

    @SerializedName("Media")
    Media("Media"),

    @SerializedName("Font")
    Font("Font"),

    @SerializedName("Script")
    Script("Script"),

    @SerializedName("TextTrack")
    TextTrack("TextTrack"),

    @SerializedName("XHR")
    XHR("XHR"),

    @SerializedName("Fetch")
    Fetch("Fetch"),

    @SerializedName("EventSource")
    EventSource("EventSource"),

    @SerializedName("WebSocket")
    WebSocket("WebSocket"),

    @SerializedName("Manifest")
    Manifest("Manifest"),

    @SerializedName("Other")
    Other("Other");

    public final String value;

    ResourceType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
