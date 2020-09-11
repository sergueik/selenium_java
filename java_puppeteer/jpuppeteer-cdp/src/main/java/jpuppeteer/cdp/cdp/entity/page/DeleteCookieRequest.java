package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DeleteCookieRequest {

    /**
    * Name of the cookie to remove.
    */
    private String cookieName;

    /**
    * URL to match cooke domain and path.
    */
    private String url;



}