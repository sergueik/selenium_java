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
import io.webfolder.cdp.type.heapprofiler.SamplingHeapProfile;
import io.webfolder.cdp.type.runtime.RemoteObject;

@Experimental
@Domain("HeapProfiler")
public interface HeapProfiler {
    void enable();

    void disable();

    void startTrackingHeapObjects(@Optional Boolean trackAllocations);

    void stopTrackingHeapObjects(@Optional Boolean reportProgress);

    void takeHeapSnapshot(@Optional Boolean reportProgress);

    void collectGarbage();

    @Returns("result")
    RemoteObject getObjectByHeapObjectId(String objectId, @Optional String objectGroup);

    /**
     * Enables console to refer to the node with given id via  (see Command Line API for more details  functions).
     * 
     * @param heapObjectId Heap snapshot object id to be accessible by means of x command line API.
     */
    void addInspectedHeapObject(String heapObjectId);

    @Returns("heapSnapshotObjectId")
    String getHeapObjectId(String objectId);

    void startSampling(@Optional Double samplingInterval);

    @Returns("profile")
    SamplingHeapProfile stopSampling();

    void startTrackingHeapObjects();

    void stopTrackingHeapObjects();

    void takeHeapSnapshot();

    @Returns("result")
    RemoteObject getObjectByHeapObjectId(String objectId);

    void startSampling();
}
