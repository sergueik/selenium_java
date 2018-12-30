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
package io.webfolder.cdp.type.database;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Database object
 */
@Experimental
public class Database {
    private String id;

    private String domain;

    private String name;

    private String version;

    /**
     * Database ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Database ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Database domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Database domain.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

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
    public String getVersion() {
        return version;
    }

    /**
     * Database version.
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
