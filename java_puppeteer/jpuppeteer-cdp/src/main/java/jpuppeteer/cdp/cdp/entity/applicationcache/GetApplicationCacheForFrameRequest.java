package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetApplicationCacheForFrameRequest {

    /**
    * Identifier of the frame containing document whose application cache is retrieved.
    */
    private String frameId;



}