package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetManifestForFrameRequest {

    /**
    * Identifier of the frame containing document whose manifest is retrieved.
    */
    private String frameId;



}