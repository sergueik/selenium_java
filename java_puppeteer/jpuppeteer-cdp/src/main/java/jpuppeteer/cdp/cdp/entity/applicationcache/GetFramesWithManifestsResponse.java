package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetFramesWithManifestsResponse {

    /**
    * Array of frame identifiers with manifest urls for each frame containing a document associated with some application cache.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.applicationcache.FrameWithManifest> frameIds;



}