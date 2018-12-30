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
package io.webfolder.cdp.event.storage;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;

/**
 * The origin's IndexedDB object store has been modified
 */
@Domain("Storage")
@EventName("indexedDBContentUpdated")
public class IndexedDBContentUpdated {
    private String origin;

    private String databaseName;

    private String objectStoreName;

    /**
     * Origin to update.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Origin to update.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Database to update.
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Database to update.
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * ObjectStore to update.
     */
    public String getObjectStoreName() {
        return objectStoreName;
    }

    /**
     * ObjectStore to update.
     */
    public void setObjectStoreName(String objectStoreName) {
        this.objectStoreName = objectStoreName;
    }
}
