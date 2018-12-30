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
package io.webfolder.cdp.type.debugger;

import io.webfolder.cdp.type.constant.ScopeType;
import io.webfolder.cdp.type.runtime.RemoteObject;

/**
 * Scope description
 */
public class Scope {
    private ScopeType type;

    private RemoteObject object;

    private String name;

    private Location startLocation;

    private Location endLocation;

    /**
     * Scope type.
     */
    public ScopeType getType() {
        return type;
    }

    /**
     * Scope type.
     */
    public void setType(ScopeType type) {
        this.type = type;
    }

    /**
     * Object representing the scope. For <code>global</code> and <code>with</code> scopes it represents the actual object; for the rest of the scopes, it is artificial transient object enumerating scope variables as its properties.
     */
    public RemoteObject getObject() {
        return object;
    }

    /**
     * Object representing the scope. For <code>global</code> and <code>with</code> scopes it represents the actual object; for the rest of the scopes, it is artificial transient object enumerating scope variables as its properties.
     */
    public void setObject(RemoteObject object) {
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Location in the source code where scope starts
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * Location in the source code where scope starts
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Location in the source code where scope ends
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * Location in the source code where scope ends
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
}
