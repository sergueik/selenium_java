package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ResetPermissionsRequest {

    /**
    * BrowserContext to reset permissions. When omitted, default browser context is used.
    */
    private String browserContextId;



}