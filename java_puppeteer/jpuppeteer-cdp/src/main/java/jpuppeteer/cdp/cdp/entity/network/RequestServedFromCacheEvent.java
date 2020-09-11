package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired if request ended up loading from cache.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestServedFromCacheEvent {

    /**
    * Request identifier.
    */
    private String requestId;



}