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
import io.webfolder.cdp.type.audits.GetEncodedResponseResult;
import io.webfolder.cdp.type.constant.Encoding;

/**
 * Audits domain allows investigation of page violations and possible improvements
 */
@Experimental
@Domain("Audits")
public interface Audits {
    /**
     * Returns the response body and size if it were re-encoded with the specified settings. Only applies to images.
     * 
     * @param requestId Identifier of the network request to get content for.
     * @param encoding The encoding to use.
     * @param quality The quality of the encoding (0-1). (defaults to 1)
     * @param sizeOnly Whether to only return the size information (defaults to false).
     * 
     * @return GetEncodedResponseResult
     */
    GetEncodedResponseResult getEncodedResponse(String requestId, Encoding encoding,
            @Optional Double quality, @Optional Boolean sizeOnly);

    /**
     * Returns the response body and size if it were re-encoded with the specified settings. Only applies to images.
     * 
     * @param requestId Identifier of the network request to get content for.
     * @param encoding The encoding to use.
     * 
     * @return GetEncodedResponseResult
     */
    GetEncodedResponseResult getEncodedResponse(String requestId, Encoding encoding);
}
