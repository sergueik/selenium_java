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
import io.webfolder.cdp.type.security.CertificateErrorAction;

/**
 * Security
 */
@Experimental
@Domain("Security")
public interface Security {
    /**
     * Enables tracking security state changes.
     */
    void enable();

    /**
     * Disables tracking security state changes.
     */
    void disable();

    /**
     * Handles a certificate error that fired a certificateError event.
     * 
     * @param eventId The ID of the event.
     * @param action The action to take on the certificate error.
     */
    void handleCertificateError(Integer eventId, CertificateErrorAction action);

    /**
     * Enable/disable overriding certificate errors. If enabled, all certificate error events need to be handled by the DevTools client and should be answered with handleCertificateError commands.
     * 
     * @param override If true, certificate errors will be overridden.
     */
    void setOverrideCertificateErrors(Boolean override);
}
