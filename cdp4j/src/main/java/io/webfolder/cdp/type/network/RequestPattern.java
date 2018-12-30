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
package io.webfolder.cdp.type.network;

import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.page.ResourceType;

/**
 * Request pattern for interception
 */
@Experimental
public class RequestPattern {
    private String urlPattern;

    private ResourceType resourceType;

    /**
     * Wildcards ('*' -> zero or more, '?' -> exactly one) are allowed. Escape character is backslash. Omitting is equivalent to "*".
     */
    public String getUrlPattern() {
        return urlPattern;
    }

    /**
     * Wildcards ('*' -> zero or more, '?' -> exactly one) are allowed. Escape character is backslash. Omitting is equivalent to "*".
     */
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    /**
     * If set, only requests for matching resource types will be intercepted.
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * If set, only requests for matching resource types will be intercepted.
     */
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
