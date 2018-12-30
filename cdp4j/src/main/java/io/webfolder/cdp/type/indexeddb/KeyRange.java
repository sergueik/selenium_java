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

/**
 * Key range
 */
public class KeyRange {
    private Key lower;

    private Key upper;

    private Boolean lowerOpen;

    private Boolean upperOpen;

    /**
     * Lower bound.
     */
    public Key getLower() {
        return lower;
    }

    /**
     * Lower bound.
     */
    public void setLower(Key lower) {
        this.lower = lower;
    }

    /**
     * Upper bound.
     */
    public Key getUpper() {
        return upper;
    }

    /**
     * Upper bound.
     */
    public void setUpper(Key upper) {
        this.upper = upper;
    }

    /**
     * If true lower bound is open.
     */
    public Boolean isLowerOpen() {
        return lowerOpen;
    }

    /**
     * If true lower bound is open.
     */
    public void setLowerOpen(Boolean lowerOpen) {
        this.lowerOpen = lowerOpen;
    }

    /**
     * If true upper bound is open.
     */
    public Boolean isUpperOpen() {
        return upperOpen;
    }

    /**
     * If true upper bound is open.
     */
    public void setUpperOpen(Boolean upperOpen) {
        this.upperOpen = upperOpen;
    }
}
