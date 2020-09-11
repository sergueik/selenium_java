package jpuppeteer.cdp.cdp.entity.network;

/**
* A cookie which was not stored from a response with the corresponding reason.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BlockedSetCookieWithReason {

    /**
    * The reason(s) this cookie was blocked.
    */
    private java.util.List<String> blockedReasons;

    /**
    * The string representing this individual cookie as it would appear in the header. This is not the entire "cookie" or "set-cookie" header which could have multiple cookies.
    */
    private String cookieLine;

    /**
    * The cookie object which represents the cookie which was not stored. It is optional because sometimes complete cookie information is not available, such as in the case of parsing errors.
    */
    private jpuppeteer.cdp.cdp.entity.network.Cookie cookie;



}