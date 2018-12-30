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
package io.webfolder.cdp.type.network;

import com.google.gson.annotations.SerializedName;

/**
 * The underlying connection technology that the browser is supposedly using
 */
public enum ConnectionType {
    @SerializedName("none")
    None("none"),

    @SerializedName("cellular2g")
    Cellular2g("cellular2g"),

    @SerializedName("cellular3g")
    Cellular3g("cellular3g"),

    @SerializedName("cellular4g")
    Cellular4g("cellular4g"),

    @SerializedName("bluetooth")
    Bluetooth("bluetooth"),

    @SerializedName("ethernet")
    Ethernet("ethernet"),

    @SerializedName("wifi")
    Wifi("wifi"),

    @SerializedName("wimax")
    Wimax("wimax"),

    @SerializedName("other")
    Other("other");

    public final String value;

    ConnectionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
