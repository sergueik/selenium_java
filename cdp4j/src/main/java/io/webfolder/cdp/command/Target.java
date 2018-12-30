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
package io.webfolder.cdp.command;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.annotation.Optional;
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.type.target.RemoteLocation;
import io.webfolder.cdp.type.target.TargetInfo;
import java.util.List;

/**
 * Supports additional targets discovery and allows to attach to them
 */
@Experimental
@Domain("Target")
public interface Target {
    /**
     * Controls whether to discover available targets and notify via <tt>targetCreated/targetInfoChanged/targetDestroyed</tt> events.
     * 
     * @param discover Whether to discover available targets.
     */
    void setDiscoverTargets(Boolean discover);

    /**
     * Controls whether to automatically attach to new targets which are considered to be related to this one. When turned on, attaches to all existing related targets as well. When turned off, automatically detaches from all currently attached targets.
     * 
     * @param autoAttach Whether to auto-attach to related targets.
     * @param waitForDebuggerOnStart Whether to pause new targets when attaching to them. Use <code>Runtime.runIfWaitingForDebugger</code> to run paused targets.
     */
    void setAutoAttach(Boolean autoAttach, Boolean waitForDebuggerOnStart);

    void setAttachToFrames(Boolean value);

    /**
     * Enables target discovery for the specified locations, when <tt>setDiscoverTargets</tt> was set to <tt>true</tt>.
     * 
     * @param locations List of remote locations.
     */
    void setRemoteLocations(List<RemoteLocation> locations);

    /**
     * Sends protocol message over session with given id.
     * 
     * @param sessionId Identifier of the session.
     * @param targetId Deprecated.
     */
    void sendMessageToTarget(String message, @Optional String sessionId, @Optional String targetId);

    /**
     * Returns information about a target.
     * 
     */
    @Returns("targetInfo")
    TargetInfo getTargetInfo(String targetId);

    /**
     * Activates (focuses) the target.
     * 
     */
    void activateTarget(String targetId);

    /**
     * Closes the target. If the target is a page that gets closed too.
     * 
     */
    @Returns("success")
    Boolean closeTarget(String targetId);

    /**
     * Attaches to the target with given id.
     * 
     * 
     * @return Id assigned to the session.
     */
    @Returns("sessionId")
    String attachToTarget(String targetId);

    /**
     * Detaches session with given id.
     * 
     * @param sessionId Session to detach.
     * @param targetId Deprecated.
     */
    void detachFromTarget(@Optional String sessionId, @Optional String targetId);

    /**
     * Creates a new empty BrowserContext. Similar to an incognito profile but you can have more than one.
     * 
     * @return The id of the context created.
     */
    @Returns("browserContextId")
    String createBrowserContext();

    /**
     * Deletes a BrowserContext, will fail of any open page uses it.
     * 
     */
    @Returns("success")
    Boolean disposeBrowserContext(String browserContextId);

    /**
     * Creates a new page.
     * 
     * @param url The initial URL the page will be navigated to.
     * @param width Frame width in DIP (headless chrome only).
     * @param height Frame height in DIP (headless chrome only).
     * @param browserContextId The browser context to create the page in (headless chrome only).
     * @param enableBeginFrameControl Whether BeginFrames for this target will be controlled via DevTools (headless chrome only, not supported on MacOS yet, false by default).
     * 
     * @return The id of the page opened.
     */
    @Returns("targetId")
    String createTarget(String url, @Optional Integer width, @Optional Integer height,
            @Optional String browserContextId,
            @Experimental @Optional Boolean enableBeginFrameControl);

    /**
     * Retrieves a list of available targets.
     * 
     * @return The list of targets.
     */
    @Returns("targetInfos")
    List<TargetInfo> getTargets();

    /**
     * Sends protocol message over session with given id.
     * 
     */
    void sendMessageToTarget(String message);

    /**
     * Detaches session with given id.
     */
    void detachFromTarget();

    /**
     * Creates a new page.
     * 
     * @param url The initial URL the page will be navigated to.
     * 
     * @return The id of the page opened.
     */
    @Returns("targetId")
    String createTarget(String url);
}
