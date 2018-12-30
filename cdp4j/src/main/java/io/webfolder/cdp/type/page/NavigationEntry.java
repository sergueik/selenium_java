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
package io.webfolder.cdp.type.page;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Navigation history entry
 */
@Experimental
public class NavigationEntry {
    private Integer id;

    private String url;

    private String userTypedURL;

    private String title;

    private TransitionType transitionType;

    /**
     * Unique id of the navigation history entry.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Unique id of the navigation history entry.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * URL of the navigation history entry.
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL of the navigation history entry.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * URL that the user typed in the url bar.
     */
    public String getUserTypedURL() {
        return userTypedURL;
    }

    /**
     * URL that the user typed in the url bar.
     */
    public void setUserTypedURL(String userTypedURL) {
        this.userTypedURL = userTypedURL;
    }

    /**
     * Title of the navigation history entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title of the navigation history entry.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Transition type.
     */
    public TransitionType getTransitionType() {
        return transitionType;
    }

    /**
     * Transition type.
     */
    public void setTransitionType(TransitionType transitionType) {
        this.transitionType = transitionType;
    }
}
