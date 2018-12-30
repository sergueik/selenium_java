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
package io.webfolder.cdp.event.network;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.network.BlockedReason;
import io.webfolder.cdp.type.page.ResourceType;

/**
 * Fired when HTTP request has failed to load
 */
@Domain("Network")
@EventName("loadingFailed")
public class LoadingFailed {
    private String requestId;

    private Double timestamp;

    private ResourceType type;

    private String errorText;

    private Boolean canceled;

    private BlockedReason blockedReason;

    /**
     * Request identifier.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Request identifier.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Timestamp.
     */
    public Double getTimestamp() {
        return timestamp;
    }

    /**
     * Timestamp.
     */
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Resource type.
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Resource type.
     */
    public void setType(ResourceType type) {
        this.type = type;
    }

    /**
     * User friendly error message.
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * User friendly error message.
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * True if loading was canceled.
     */
    public Boolean isCanceled() {
        return canceled;
    }

    /**
     * True if loading was canceled.
     */
    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * The reason why loading was blocked, if any.
     */
    public BlockedReason getBlockedReason() {
        return blockedReason;
    }

    /**
     * The reason why loading was blocked, if any.
     */
    public void setBlockedReason(BlockedReason blockedReason) {
        this.blockedReason = blockedReason;
    }
}
