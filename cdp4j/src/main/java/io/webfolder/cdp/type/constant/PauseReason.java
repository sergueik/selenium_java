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
package io.webfolder.cdp.type.constant;

import com.google.gson.annotations.SerializedName;

public enum PauseReason {
    @SerializedName("XHR")
    XHR("XHR"),

    @SerializedName("DOM")
    DOM("DOM"),

    @SerializedName("EventListener")
    EventListener("EventListener"),

    @SerializedName("exception")
    Exception("exception"),

    @SerializedName("assert")
    Assert("assert"),

    @SerializedName("debugCommand")
    DebugCommand("debugCommand"),

    @SerializedName("promiseRejection")
    PromiseRejection("promiseRejection"),

    @SerializedName("OOM")
    OOM("OOM"),

    @SerializedName("other")
    Other("other"),

    @SerializedName("ambiguous")
    Ambiguous("ambiguous");

    public final String value;

    PauseReason(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
