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
package io.webfolder.cdp.type.runtime;

import io.webfolder.cdp.annotation.Experimental;

@Experimental
public class EntryPreview {
    private ObjectPreview key;

    private ObjectPreview value;

    /**
     * Preview of the key. Specified for map-like collection entries.
     */
    public ObjectPreview getKey() {
        return key;
    }

    /**
     * Preview of the key. Specified for map-like collection entries.
     */
    public void setKey(ObjectPreview key) {
        this.key = key;
    }

    /**
     * Preview of the value.
     */
    public ObjectPreview getValue() {
        return value;
    }

    /**
     * Preview of the value.
     */
    public void setValue(ObjectPreview value) {
        this.value = value;
    }
}
