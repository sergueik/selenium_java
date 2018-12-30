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
 * Transition type
 */
public enum TransitionType {
    @SerializedName("link")
    Link("link"),

    @SerializedName("typed")
    Typed("typed"),

    @SerializedName("auto_bookmark")
    AutoBookmark("auto_bookmark"),

    @SerializedName("auto_subframe")
    AutoSubframe("auto_subframe"),

    @SerializedName("manual_subframe")
    ManualSubframe("manual_subframe"),

    @SerializedName("generated")
    Generated("generated"),

    @SerializedName("auto_toplevel")
    AutoToplevel("auto_toplevel"),

    @SerializedName("form_submit")
    FormSubmit("form_submit"),

    @SerializedName("reload")
    Reload("reload"),

    @SerializedName("keyword")
    Keyword("keyword"),

    @SerializedName("keyword_generated")
    KeywordGenerated("keyword_generated"),

    @SerializedName("other")
    Other("other");

    public final String value;

    TransitionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
