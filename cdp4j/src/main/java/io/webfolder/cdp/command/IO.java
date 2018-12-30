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
import io.webfolder.cdp.type.io.ReadResult;

/**
 * Input/Output operations for streams produced by DevTools
 */
@Experimental
@Domain("IO")
public interface IO {
    /**
     * Read a chunk of the stream
     * 
     * @param handle Handle of the stream to read.
     * @param offset Seek to the specified offset before reading (if not specificed, proceed with offset following the last read).
     * @param size Maximum number of bytes to read (left upon the agent discretion if not specified).
     * 
     * @return ReadResult
     */
    ReadResult read(String handle, @Optional Integer offset, @Optional Integer size);

    /**
     * Close the stream, discard any temporary backing storage.
     * 
     * @param handle Handle of the stream to close.
     */
    void close(String handle);

    /**
     * Return UUID of Blob object specified by a remote object id.
     * 
     * @param objectId Object id of a Blob object wrapper.
     * 
     * @return UUID of the specified Blob.
     */
    @Returns("uuid")
    String resolveBlob(String objectId);

    /**
     * Read a chunk of the stream
     * 
     * @param handle Handle of the stream to read.
     * 
     * @return ReadResult
     */
    ReadResult read(String handle);
}
