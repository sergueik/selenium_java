package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHeapObjectIdRequest {

    /**
    * Identifier of the object to get heap object id for.
    */
    private String objectId;



}