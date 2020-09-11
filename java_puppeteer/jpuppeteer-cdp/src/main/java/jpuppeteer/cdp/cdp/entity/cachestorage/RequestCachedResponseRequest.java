package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestCachedResponseRequest {

    /**
    * Id of cache that contains the entry.
    */
    private String cacheId;

    /**
    * URL spec of the request.
    */
    private String requestURL;

    /**
    * headers of the request.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.cachestorage.Header> requestHeaders;



}