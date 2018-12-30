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

import java.util.Scanner;

import io.webfolder.cdp.command.DOM;
import io.webfolder.cdp.type.dom.Node;
import io.webfolder.cdp.type.runtime.RemoteObject;

public interface Sizzle {

    /**
     * @return <code>true</code> if sizzle is installed
     */
    public default boolean useSizzle() {
        return getThis().getSizzle();
    }

    /**
     * Remove sizzle CSS selector engine
     */
    public default void removeSizzle() {
        if (getThis().useSizzle()) {
            getThis().logEntry("removeSizzle");
            getThis().setSizzle(false);
            getThis().evaluate("window.cdp4j = undefined");
        }
    }

    /**
     * Install sizzle CSS selector engine instead of browser's native selector engine.
     * 
     * @return this
     */
    default Session installSizzle() {
        getThis().setSizzle(true);
        getThis().disableFlowLog();
        final boolean install = TRUE.equals(getThis().evaluate("typeof window.cdp4j === 'undefined'"));
        getThis().enableFlowLog();
        if (install) {
            String sizzle = null;
            try (Scanner scanner = new Scanner(getClass().getResourceAsStream("/cdp4j-sizzle-2.3.3.min.js"))) {
                scanner.useDelimiter("\\A");
                sizzle = scanner.hasNext() ? scanner.next() : "";
            }
            String func  = "window.cdp4j = {}; " +
                                        "window.cdp4j.query = function(selector) { " +
                                        sizzle + " var result = Sizzle(selector); if (result.length > 0) { return result[0]; } else { return null; } };";
                   func +=              "window.cdp4j.queryAll = function(selector) { " +
                                        sizzle + " var result = Sizzle(selector); if (result.length > 0) { return result; } else { return null; } };";
            getThis().disableFlowLog();
            getThis().evaluate(func);
            getThis().enableFlowLog();
            DOM dom = getThis().getCommand().getDOM();
            Node document = dom.getDocument();
            if (document != null) {
                RemoteObject remoteObject = dom.resolveNode(document.getNodeId(), null, null);
                if ( remoteObject != null && remoteObject.getObjectId() != null ) {
                    getThis().getCommand().getRuntime().releaseObject(remoteObject.getObjectId());
                }
            }
        }
        return getThis();
    }

    Session getThis();
}
