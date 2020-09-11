package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TakeHeapSnapshotRequest {

    /**
    * If true 'reportHeapSnapshotProgress' events will be generated while snapshot is being taken.
    */
    private Boolean reportProgress;

    /**
    * If true, a raw snapshot without artifical roots will be generated
    */
    private Boolean treatGlobalObjectsAsRoots;



}