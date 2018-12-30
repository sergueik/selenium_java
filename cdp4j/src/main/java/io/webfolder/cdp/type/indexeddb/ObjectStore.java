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

import java.util.ArrayList;
import java.util.List;

/**
 * Object store
 */
public class ObjectStore {
    private String name;

    private KeyPath keyPath;

    private Boolean autoIncrement;

    private List<ObjectStoreIndex> indexes = new ArrayList<>();

    /**
     * Object store name.
     */
    public String getName() {
        return name;
    }

    /**
     * Object store name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Object store key path.
     */
    public KeyPath getKeyPath() {
        return keyPath;
    }

    /**
     * Object store key path.
     */
    public void setKeyPath(KeyPath keyPath) {
        this.keyPath = keyPath;
    }

    /**
     * If true, object store has auto increment flag set.
     */
    public Boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * If true, object store has auto increment flag set.
     */
    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * Indexes in this object store.
     */
    public List<ObjectStoreIndex> getIndexes() {
        return indexes;
    }

    /**
     * Indexes in this object store.
     */
    public void setIndexes(List<ObjectStoreIndex> indexes) {
        this.indexes = indexes;
    }
}
