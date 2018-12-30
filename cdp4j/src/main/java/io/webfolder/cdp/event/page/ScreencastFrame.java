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
package io.webfolder.cdp.event.page;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.page.ScreencastFrameMetadata;

/**
 * Compressed image data requested by the <code>startScreencast</code>
 */
@Experimental
@Domain("Page")
@EventName("screencastFrame")
public class ScreencastFrame {
    private String data;

    private ScreencastFrameMetadata metadata;

    private Integer sessionId;

    /**
     * Base64-encoded compressed image.
     */
    public String getData() {
        return data;
    }

    /**
     * Base64-encoded compressed image.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Screencast frame metadata.
     */
    public ScreencastFrameMetadata getMetadata() {
        return metadata;
    }

    /**
     * Screencast frame metadata.
     */
    public void setMetadata(ScreencastFrameMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Frame number.
     */
    public Integer getSessionId() {
        return sessionId;
    }

    /**
     * Frame number.
     */
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
}
