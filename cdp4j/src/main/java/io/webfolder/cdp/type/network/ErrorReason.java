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
 * Network level fetch failure reason
 */
public enum ErrorReason {
    @SerializedName("Failed")
    Failed("Failed"),

    @SerializedName("Aborted")
    Aborted("Aborted"),

    @SerializedName("TimedOut")
    TimedOut("TimedOut"),

    @SerializedName("AccessDenied")
    AccessDenied("AccessDenied"),

    @SerializedName("ConnectionClosed")
    ConnectionClosed("ConnectionClosed"),

    @SerializedName("ConnectionReset")
    ConnectionReset("ConnectionReset"),

    @SerializedName("ConnectionRefused")
    ConnectionRefused("ConnectionRefused"),

    @SerializedName("ConnectionAborted")
    ConnectionAborted("ConnectionAborted"),

    @SerializedName("ConnectionFailed")
    ConnectionFailed("ConnectionFailed"),

    @SerializedName("NameNotResolved")
    NameNotResolved("NameNotResolved"),

    @SerializedName("InternetDisconnected")
    InternetDisconnected("InternetDisconnected"),

    @SerializedName("AddressUnreachable")
    AddressUnreachable("AddressUnreachable");

    public final String value;

    ErrorReason(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
