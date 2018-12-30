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
package io.webfolder.cdp.type.storage;

import com.google.gson.annotations.SerializedName;

/**
 * Enum of possible storage types
 */
public enum StorageType {
    @SerializedName("appcache")
    Appcache("appcache"),

    @SerializedName("cookies")
    Cookies("cookies"),

    @SerializedName("file_systems")
    FileSystems("file_systems"),

    @SerializedName("indexeddb")
    Indexeddb("indexeddb"),

    @SerializedName("local_storage")
    LocalStorage("local_storage"),

    @SerializedName("shader_cache")
    ShaderCache("shader_cache"),

    @SerializedName("websql")
    Websql("websql"),

    @SerializedName("service_workers")
    ServiceWorkers("service_workers"),

    @SerializedName("cache_storage")
    CacheStorage("cache_storage"),

    @SerializedName("all")
    All("all"),

    @SerializedName("other")
    Other("other");

    public final String value;

    StorageType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
