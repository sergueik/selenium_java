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
package io.webfolder.cdp.type.accessibility;

public class AXProperty {
    private String name;

    private AXValue value;

    /**
     * The name of this property.
     */
    public String getName() {
        return name;
    }

    /**
     * The name of this property.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The value of this property.
     */
    public AXValue getValue() {
        return value;
    }

    /**
     * The value of this property.
     */
    public void setValue(AXValue value) {
        this.value = value;
    }
}
