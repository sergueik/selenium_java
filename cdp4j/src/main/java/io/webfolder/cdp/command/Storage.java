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
package io.webfolder.cdp.command;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.storage.GetUsageAndQuotaResult;

@Experimental
@Domain("Storage")
public interface Storage {
    /**
     * Clears storage for origin.
     * 
     * @param origin Security origin.
     * @param storageTypes Comma separated origin names.
     */
    void clearDataForOrigin(String origin, String storageTypes);

    /**
     * Returns usage and quota in bytes.
     * 
     * @param origin Security origin.
     * 
     * @return GetUsageAndQuotaResult
     */
    GetUsageAndQuotaResult getUsageAndQuota(String origin);

    /**
     * Registers origin to be notified when an update occurs to its cache storage list.
     * 
     * @param origin Security origin.
     */
    void trackCacheStorageForOrigin(String origin);

    /**
     * Unregisters origin from receiving notifications for cache storage.
     * 
     * @param origin Security origin.
     */
    void untrackCacheStorageForOrigin(String origin);

    /**
     * Registers origin to be notified when an update occurs to its IndexedDB.
     * 
     * @param origin Security origin.
     */
    void trackIndexedDBForOrigin(String origin);

    /**
     * Unregisters origin from receiving notifications for IndexedDB.
     * 
     * @param origin Security origin.
     */
    void untrackIndexedDBForOrigin(String origin);
}
