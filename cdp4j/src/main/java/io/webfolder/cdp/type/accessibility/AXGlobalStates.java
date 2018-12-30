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
package io.webfolder.cdp.type.accessibility;

import com.google.gson.annotations.SerializedName;

/**
 * States which apply to every AX node
 */
public enum AXGlobalStates {
    @SerializedName("busy")
    Busy("busy"),

    @SerializedName("disabled")
    Disabled("disabled"),

    @SerializedName("hidden")
    Hidden("hidden"),

    @SerializedName("hiddenRoot")
    HiddenRoot("hiddenRoot"),

    @SerializedName("invalid")
    Invalid("invalid"),

    @SerializedName("keyshortcuts")
    Keyshortcuts("keyshortcuts"),

    @SerializedName("roledescription")
    Roledescription("roledescription");

    public final String value;

    AXGlobalStates(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
