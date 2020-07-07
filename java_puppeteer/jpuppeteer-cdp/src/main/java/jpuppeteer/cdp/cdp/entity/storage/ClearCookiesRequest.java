package jpuppeteer.cdp.cdp.entity.storage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ClearCookiesRequest {

    /**
    * Browser context to use when called on the browser endpoint.
    */
    private String browserContextId;



}