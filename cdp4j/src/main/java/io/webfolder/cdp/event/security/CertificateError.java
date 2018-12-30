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
package io.webfolder.cdp.event.security;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;

/**
 * There is a certificate error
 * If overriding certificate errors is enabled, then it should be handled with the handleCertificateError command
 * Note: this event does not fire if the certificate error has been allowed internally
 */
@Domain("Security")
@EventName("certificateError")
public class CertificateError {
    private Integer eventId;

    private String errorType;

    private String requestURL;

    /**
     * The ID of the event.
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * The ID of the event.
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * The type of the error.
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * The type of the error.
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * The url that was requested.
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * The url that was requested.
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
}
