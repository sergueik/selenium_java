package jpuppeteer.cdp.cdp.entity.storage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetCookiesResponse {

    /**
    * Array of cookie objects.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.Cookie> cookies;



}