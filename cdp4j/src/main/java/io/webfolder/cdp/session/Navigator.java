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

import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.util.Collections.emptyMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.webfolder.cdp.command.DOM;
import io.webfolder.cdp.command.Network;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.type.dom.Node;
import io.webfolder.cdp.type.runtime.RemoteObject;

public interface Navigator {

    /**
     * This method stops window loading.
     * 
     * @return this
     */
    public default Session stop() {
        getThis().logEntry("stop");
        getThis().getCommand().getPage().stopLoading();
        return getThis();
    }

    /**
     * Returns the window to the previous item in the history.
     * 
     * @return this
     */
    public default Session back() {
        getThis().logEntry("back");
        getThis().evaluate("window.history.back()");
        return getThis();
    }

    /**
     * Moves the window one document forward in the history
     * 
     * @return this
     */
    public default Session forward() {
        getThis().logEntry("forward");
        getThis().evaluate("window.history.forward()");
        return getThis();
    }

    /**
     * This method reloads the resource from the current URL.
     * 
     * @return this
     */
    public default Session reload() {
        getThis().logEntry("reload");
        getThis().getCommand().getPage().reload();
        return getThis();
    }


    /**
     * Allows overriding user agent with the given string.
     * 
     * @param userAgent User agent to use
     * 
     * @return this
     */
    public default Session setUserAgent(final String userAgent) {
        getThis().logEntry("userAgent", userAgent);
        Network network = getThis().getCommand().getNetwork();
        network.enable();
        network.setUserAgentOverride(userAgent);
        return getThis();
    }

    /**
     * Document URL that <code>Document</code> points to.
     * 
     * @return document location
     */
    default String getLocation() {
        return getThis()
                    .getCommand()
                    .getDOM()
                    .getDocument()
                    .getDocumentURL();
    }

    /**
     * Retrieves <code>Location.pathname</code> property.
     * 
     * @return an initial <strong>/</strong> followed by the path of the URL
     */
    public default String getPathname() {
        DOM dom = getThis().getCommand().getDOM();
        Integer nodeId = dom.getDocument().getNodeId();
        RemoteObject remoteObject = dom.resolveNode(nodeId, null, null);
        String pathname = (String) getThis().getPropertyByObjectId(remoteObject.getObjectId(), "location.pathname");
        getThis().releaseObject(remoteObject.getObjectId());
        System.out.println(pathname);
        return pathname;
    }

    /**
     * Gets query string
     * 
     * @return key value pair
     */
    @SuppressWarnings("unchecked")
    public default Map<String, Object> getQueryString() {
        getThis().disableFlowLog();
        String json = (String) getThis()
                                .evaluate("JSON.stringify(Array.from(new URLSearchParams(document.location.search)))");
        getThis().enableFlowLog();
        if (json == null || json.trim().isEmpty()) {
            return emptyMap();
        }
        List<List<Object>> params = getThis().getGson().fromJson(json, List.class);
        Map<String, Object> map = new LinkedHashMap<>(params.size());
        for (List<Object> param : params) {
            if (param.size() == 0) {
                continue;
            }
            String key = valueOf(param.get(0));
            Object value = null;
            if (param.size() > 1) {
                value = param.size() == 2 ? param.get(1) : param.subList(1, param.size());
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * Gets the full HTML contents of the page, including the doctype.
     * 
     * @return string content of the document
     */
    default String getContent() {
        getThis().disableFlowLog();
        DOM dom = getThis().getCommand().getDOM();
        Integer nodeId = dom.getDocument().getNodeId();
        RemoteObject remoteObject = dom.resolveNode(nodeId, null, null);
        String title = (String) getThis().getPropertyByObjectId(remoteObject.getObjectId(), "documentElement.outerHTML");
        getThis().logExit("getContent", title);
        getThis().releaseObject(remoteObject.getObjectId());
        return title;
    }

    /**
     * Gets the title of the document.
     * 
     * @return string containing the document's title
     */
    default String getTitle() {
        DOM dom = getThis().getCommand().getDOM();
        Integer nodeId = dom.getDocument().getNodeId();
        RemoteObject remoteObject = dom.resolveNode(nodeId, null, null);
        String title = (String) getThis().getPropertyByObjectId(remoteObject.getObjectId(), "title");
        getThis().logExit("getTitle", title);
        getThis().releaseObject(remoteObject.getObjectId());
        return title;
    }

    /**
     * Gets the state of Document
     * 
     * @return <code>true</code> if Document.readyState property is <strong>complete</strong>
     */
    default boolean isDomReady() {
        try {
            getThis().disableFlowLog();
            DOM dom = getThis().getCommand().getDOM();
            if (dom == null) {
                return false;
            }
            Node document = dom.getDocument();
            if (document == null) {
                return false;
            }
            Integer nodeId = document.getNodeId();
            RemoteObject remoteObject = dom.resolveNode(nodeId, null, null);
            if (remoteObject == null) {
                return false;
            }
            String readyState = (String) getThis().getPropertyByObjectId(remoteObject.getObjectId(), "readyState");
            getThis().releaseObject(remoteObject.getObjectId());
            return "complete".equals(readyState);
        } catch (CdpException e) {
            return getThis().isConnected() &&
                        TRUE.equals(getThis().evaluate("window.document.readyState === 'complete'"));
        } finally {
            getThis().enableFlowLog();
        }
    }

    /**
     * Clears browser cache.
     * 
     * @return <code>true</code> if browser cache cleared.
     */
    public default boolean clearCache() {
        getThis().logEntry("clearCache");
        Network network = getThis().getCommand().getNetwork();
        network.enable();
        Boolean canClear = network.canClearBrowserCache();
        if (TRUE.equals(canClear)) {
            getThis()
                .getCommand()
                .getNetwork()
                .clearBrowserCache();
            return true;
        }
        return false;
    }

    /**
     * Clears all browser cookies.
     * 
     * @return <code>true</code> if browser cookies cleared.
     */
    public default boolean clearCookies() {
        getThis().logEntry("clearCookies");
        Network network = getThis().getCommand().getNetwork();
        network.enable();
        Boolean canClear = network.canClearBrowserCookies();
        if (TRUE.equals(canClear)) {
            getThis()
                .getCommand()
                .getNetwork()
                .clearBrowserCookies();
            return true;
        }
        return false;
    }

    Session getThis();
}
