package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AddInspectedHeapObjectRequest {

    /**
    * Heap snapshot object id to be accessible by means of $x command line API.
    */
    private String heapObjectId;



}