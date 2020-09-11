package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GrantPermissionsRequest {

    /**
    */
    private String origin;

    /**
    */
    private java.util.List<String> permissions;

    /**
    * BrowserContext to override permissions. When omitted, default browser context is used.
    */
    private String browserContextId;



}