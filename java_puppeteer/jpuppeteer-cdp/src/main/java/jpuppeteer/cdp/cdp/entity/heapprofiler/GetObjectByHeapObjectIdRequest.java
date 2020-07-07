package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetObjectByHeapObjectIdRequest {

    /**
    */
    private String objectId;

    /**
    * Symbolic group name that can be used to release multiple objects.
    */
    private String objectGroup;



}