package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* Cached response
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CachedResponse {

    /**
    * Entry content, base64-encoded.
    */
    private String body;



}