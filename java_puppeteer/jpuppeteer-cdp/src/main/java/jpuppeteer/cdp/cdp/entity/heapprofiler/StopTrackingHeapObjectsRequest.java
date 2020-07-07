package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StopTrackingHeapObjectsRequest {

    /**
    * If true 'reportHeapSnapshotProgress' events will be generated while snapshot is being taken when the tracking is stopped.
    */
    private Boolean reportProgress;

    /**
    */
    private Boolean treatGlobalObjectsAsRoots;



}