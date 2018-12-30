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

public enum ConsoleApiCallType {
    @SerializedName("log")
    Log("log"),

    @SerializedName("debug")
    Debug("debug"),

    @SerializedName("info")
    Info("info"),

    @SerializedName("error")
    Error("error"),

    @SerializedName("warning")
    Warning("warning"),

    @SerializedName("dir")
    Dir("dir"),

    @SerializedName("dirxml")
    Dirxml("dirxml"),

    @SerializedName("table")
    Table("table"),

    @SerializedName("trace")
    Trace("trace"),

    @SerializedName("clear")
    Clear("clear"),

    @SerializedName("startGroup")
    StartGroup("startGroup"),

    @SerializedName("startGroupCollapsed")
    StartGroupCollapsed("startGroupCollapsed"),

    @SerializedName("endGroup")
    EndGroup("endGroup"),

    @SerializedName("assert")
    Assert("assert"),

    @SerializedName("profile")
    Profile("profile"),

    @SerializedName("profileEnd")
    ProfileEnd("profileEnd"),

    @SerializedName("count")
    Count("count"),

    @SerializedName("timeEnd")
    TimeEnd("timeEnd");

    public final String value;

    ConsoleApiCallType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
