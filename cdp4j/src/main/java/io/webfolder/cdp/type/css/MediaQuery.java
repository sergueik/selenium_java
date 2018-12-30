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

import io.webfolder.cdp.annotation.Experimental;
import java.util.ArrayList;
import java.util.List;

/**
 * Media query descriptor
 */
@Experimental
public class MediaQuery {
    private List<MediaQueryExpression> expressions = new ArrayList<>();

    private Boolean active;

    /**
     * Array of media query expressions.
     */
    public List<MediaQueryExpression> getExpressions() {
        return expressions;
    }

    /**
     * Array of media query expressions.
     */
    public void setExpressions(List<MediaQueryExpression> expressions) {
        this.expressions = expressions;
    }

    /**
     * Whether the media query condition is satisfied.
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Whether the media query condition is satisfied.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
}
