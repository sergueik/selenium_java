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
 * Description of an isolated world
 */
public class ExecutionContextDescription {
    private Integer id;

    private String origin;

    private String name;

    private Object auxData;

    /**
     * Unique id of the execution context. It can be used to specify in which execution context script evaluation should be performed.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Unique id of the execution context. It can be used to specify in which execution context script evaluation should be performed.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Execution context origin.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Execution context origin.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Human readable name describing given context.
     */
    public String getName() {
        return name;
    }

    /**
     * Human readable name describing given context.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Embedder-specific auxiliary data.
     */
    public Object getAuxData() {
        return auxData;
    }

    /**
     * Embedder-specific auxiliary data.
     */
    public void setAuxData(Object auxData) {
        this.auxData = auxData;
    }
}
