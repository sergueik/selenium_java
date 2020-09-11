package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHeapObjectIdResponse {

    /**
    * Id of the heap snapshot object corresponding to the passed remote object id.
    */
    private String heapSnapshotObjectId;



}