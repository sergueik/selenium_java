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

/**
 * Object property descriptor
 */
public class PropertyDescriptor {
    private String name;

    private RemoteObject value;

    private Boolean writable;

    private RemoteObject get;

    private RemoteObject set;

    private Boolean configurable;

    private Boolean enumerable;

    private Boolean wasThrown;

    private Boolean isOwn;

    private RemoteObject symbol;

    /**
     * Property name or symbol description.
     */
    public String getName() {
        return name;
    }

    /**
     * Property name or symbol description.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The value associated with the property.
     */
    public RemoteObject getValue() {
        return value;
    }

    /**
     * The value associated with the property.
     */
    public void setValue(RemoteObject value) {
        this.value = value;
    }

    /**
     * True if the value associated with the property may be changed (data descriptors only).
     */
    public Boolean isWritable() {
        return writable;
    }

    /**
     * True if the value associated with the property may be changed (data descriptors only).
     */
    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    /**
     * A function which serves as a getter for the property, or <code>undefined</code> if there is no getter (accessor descriptors only).
     */
    public RemoteObject getGet() {
        return get;
    }

    /**
     * A function which serves as a getter for the property, or <code>undefined</code> if there is no getter (accessor descriptors only).
     */
    public void setGet(RemoteObject get) {
        this.get = get;
    }

    /**
     * A function which serves as a setter for the property, or <code>undefined</code> if there is no setter (accessor descriptors only).
     */
    public RemoteObject getSet() {
        return set;
    }

    /**
     * A function which serves as a setter for the property, or <code>undefined</code> if there is no setter (accessor descriptors only).
     */
    public void setSet(RemoteObject set) {
        this.set = set;
    }

    /**
     * True if the type of this property descriptor may be changed and if the property may be deleted from the corresponding object.
     */
    public Boolean isConfigurable() {
        return configurable;
    }

    /**
     * True if the type of this property descriptor may be changed and if the property may be deleted from the corresponding object.
     */
    public void setConfigurable(Boolean configurable) {
        this.configurable = configurable;
    }

    /**
     * True if this property shows up during enumeration of the properties on the corresponding object.
     */
    public Boolean isEnumerable() {
        return enumerable;
    }

    /**
     * True if this property shows up during enumeration of the properties on the corresponding object.
     */
    public void setEnumerable(Boolean enumerable) {
        this.enumerable = enumerable;
    }

    /**
     * True if the result was thrown during the evaluation.
     */
    public Boolean isWasThrown() {
        return wasThrown;
    }

    /**
     * True if the result was thrown during the evaluation.
     */
    public void setWasThrown(Boolean wasThrown) {
        this.wasThrown = wasThrown;
    }

    /**
     * True if the property is owned for the object.
     */
    public Boolean isIsOwn() {
        return isOwn;
    }

    /**
     * True if the property is owned for the object.
     */
    public void setIsOwn(Boolean isOwn) {
        this.isOwn = isOwn;
    }

    /**
     * Property symbol object, if the property is of the <code>symbol</code> type.
     */
    public RemoteObject getSymbol() {
        return symbol;
    }

    /**
     * Property symbol object, if the property is of the <code>symbol</code> type.
     */
    public void setSymbol(RemoteObject symbol) {
        this.symbol = symbol;
    }
}
