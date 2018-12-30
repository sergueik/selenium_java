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
package io.webfolder.cdp.type.domstorage;

import io.webfolder.cdp.annotation.Experimental;

/**
 * DOM Storage identifier
 */
@Experimental
public class StorageId {
    private String securityOrigin;

    private Boolean isLocalStorage;

    /**
     * Security origin for the storage.
     */
    public String getSecurityOrigin() {
        return securityOrigin;
    }

    /**
     * Security origin for the storage.
     */
    public void setSecurityOrigin(String securityOrigin) {
        this.securityOrigin = securityOrigin;
    }

    /**
     * Whether the storage is local storage (not session storage).
     */
    public Boolean isIsLocalStorage() {
        return isLocalStorage;
    }

    /**
     * Whether the storage is local storage (not session storage).
     */
    public void setIsLocalStorage(Boolean isLocalStorage) {
        this.isLocalStorage = isLocalStorage;
    }
}
