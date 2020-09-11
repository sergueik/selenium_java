package jpuppeteer.cdp.cdp.entity.storage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetCookiesRequest {

    /**
    * Browser context to use when called on the browser endpoint.
    */
    private String browserContextId;



}