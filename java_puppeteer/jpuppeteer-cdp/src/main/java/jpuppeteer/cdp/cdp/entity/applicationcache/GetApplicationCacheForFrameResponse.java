package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetApplicationCacheForFrameResponse {

    /**
    * Relevant application cache data for the document in given frame.
    */
    private jpuppeteer.cdp.cdp.entity.applicationcache.ApplicationCache applicationCache;



}