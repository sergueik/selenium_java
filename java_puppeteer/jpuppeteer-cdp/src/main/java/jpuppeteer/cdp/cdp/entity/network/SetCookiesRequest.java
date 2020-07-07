package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetCookiesRequest {

    /**
    * Cookies to be set.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.CookieParam> cookies;



}