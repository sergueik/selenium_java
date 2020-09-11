package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestCacheNamesResponse {

    /**
    * Caches for the security origin.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.cachestorage.Cache> caches;



}