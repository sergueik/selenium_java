package jpuppeteer.cdp.cdp.entity.network;

/**
* Information about the cached resource.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CachedResource {

    /**
    * Resource URL. This is the url of the original network request.
    */
    private String url;

    /**
    * Type of this resource.
    */
    private String type;

    /**
    * Cached response data.
    */
    private jpuppeteer.cdp.cdp.entity.network.Response response;

    /**
    * Cached response body size.
    */
    private Double bodySize;



}