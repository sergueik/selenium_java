package jpuppeteer.cdp.cdp.entity.storage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetCookiesRequest {

    /**
    * Cookies to be set.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.CookieParam> cookies;

    /**
    * Browser context to use when called on the browser endpoint.
    */
    private String browserContextId;



}