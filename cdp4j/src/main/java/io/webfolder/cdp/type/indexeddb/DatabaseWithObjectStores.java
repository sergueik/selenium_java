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
 * Database with an array of object stores
 */
public class DatabaseWithObjectStores {
    private String name;

    private Integer version;

    private List<ObjectStore> objectStores = new ArrayList<>();

    /**
     * Database name.
     */
    public String getName() {
        return name;
    }

    /**
     * Database name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Database version.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Database version.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Object stores in this database.
     */
    public List<ObjectStore> getObjectStores() {
        return objectStores;
    }

    /**
     * Object stores in this database.
     */
    public void setObjectStores(List<ObjectStore> objectStores) {
        this.objectStores = objectStores;
    }
}
