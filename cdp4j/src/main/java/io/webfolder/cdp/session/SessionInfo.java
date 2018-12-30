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
package io.webfolder.cdp.session;

public class SessionInfo {

    private String description;

    private String devtoolsFrontendUrl;

    private String id;

    private String title;

    private String type;

    private String url;

    private String webSocketDebuggerUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDevtoolsFrontendUrl() {
        return devtoolsFrontendUrl;
    }

    public void setDevtoolsFrontendUrl(String devtoolsFrontendUrl) {
        this.devtoolsFrontendUrl = devtoolsFrontendUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebSocketDebuggerUrl() {
        return webSocketDebuggerUrl;
    }

    public void setWebSocketDebuggerUrl(String webSocketDebuggerUrl) {
        this.webSocketDebuggerUrl = webSocketDebuggerUrl;
    }

    @Override
    public String toString() {
        return "SessionInfo [description=" + description + ", devtoolsFrontendUrl=" + devtoolsFrontendUrl + ", id=" + id
                + ", title=" + title + ", type=" + type + ", url=" + url + ", webSocketDebuggerUrl="
                + webSocketDebuggerUrl + "]";
    }
}
