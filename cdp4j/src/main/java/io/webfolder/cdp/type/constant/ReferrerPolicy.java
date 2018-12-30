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

public enum ReferrerPolicy {
    @SerializedName("unsafe-url")
    UnsafeUrl("unsafe-url"),

    @SerializedName("no-referrer-when-downgrade")
    NoReferrerWhenDowngrade("no-referrer-when-downgrade"),

    @SerializedName("no-referrer")
    NoReferrer("no-referrer"),

    @SerializedName("origin")
    Origin("origin"),

    @SerializedName("origin-when-cross-origin")
    OriginWhenCrossOrigin("origin-when-cross-origin"),

    @SerializedName("same-origin")
    SameOrigin("same-origin"),

    @SerializedName("strict-origin")
    StrictOrigin("strict-origin"),

    @SerializedName("strict-origin-when-cross-origin")
    StrictOriginWhenCrossOrigin("strict-origin-when-cross-origin");

    public final String value;

    ReferrerPolicy(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
