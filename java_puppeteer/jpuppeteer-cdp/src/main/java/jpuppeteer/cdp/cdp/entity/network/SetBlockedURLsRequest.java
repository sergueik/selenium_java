package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBlockedURLsRequest {

    /**
    * URL patterns to block. Wildcards ('*') are allowed.
    */
    private java.util.List<String> urls;



}