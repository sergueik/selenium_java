package jpuppeteer.cdp.cdp.entity.network;

/**
* A cookie with was not sent with a request with the corresponding reason.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BlockedCookieWithReason {

    /**
    * The reason(s) the cookie was blocked.
    */
    private java.util.List<String> blockedReasons;

    /**
    * The cookie object representing the cookie which was not sent.
    */
    private jpuppeteer.cdp.cdp.entity.network.Cookie cookie;



}