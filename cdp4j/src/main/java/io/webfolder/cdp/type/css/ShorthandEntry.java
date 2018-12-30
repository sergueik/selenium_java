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
package io.webfolder.cdp.type.css;

public class ShorthandEntry {
    private String name;

    private String value;

    private Boolean important;

    /**
     * Shorthand name.
     */
    public String getName() {
        return name;
    }

    /**
     * Shorthand name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Shorthand value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Shorthand value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Whether the property has "!important" annotation (implies <code>false</code> if absent).
     */
    public Boolean isImportant() {
        return important;
    }

    /**
     * Whether the property has "!important" annotation (implies <code>false</code> if absent).
     */
    public void setImportant(Boolean important) {
        this.important = important;
    }
}
