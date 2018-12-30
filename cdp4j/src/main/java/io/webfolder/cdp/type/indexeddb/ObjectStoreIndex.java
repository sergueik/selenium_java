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
package io.webfolder.cdp.type.indexeddb;

/**
 * Object store index
 */
public class ObjectStoreIndex {
    private String name;

    private KeyPath keyPath;

    private Boolean unique;

    private Boolean multiEntry;

    /**
     * Index name.
     */
    public String getName() {
        return name;
    }

    /**
     * Index name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Index key path.
     */
    public KeyPath getKeyPath() {
        return keyPath;
    }

    /**
     * Index key path.
     */
    public void setKeyPath(KeyPath keyPath) {
        this.keyPath = keyPath;
    }

    /**
     * If true, index is unique.
     */
    public Boolean isUnique() {
        return unique;
    }

    /**
     * If true, index is unique.
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * If true, index allows multiple entries for a key.
     */
    public Boolean isMultiEntry() {
        return multiEntry;
    }

    /**
     * If true, index allows multiple entries for a key.
     */
    public void setMultiEntry(Boolean multiEntry) {
        this.multiEntry = multiEntry;
    }
}
