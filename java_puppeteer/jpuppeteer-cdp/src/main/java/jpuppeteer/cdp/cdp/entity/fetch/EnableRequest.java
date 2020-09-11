package jpuppeteer.cdp.cdp.entity.fetch;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EnableRequest {

    /**
    * If specified, only requests matching any of these patterns will produce fetchRequested event and will be paused until clients response. If not set, all requests will be affected.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.fetch.RequestPattern> patterns;

    /**
    * If true, authRequired events will be issued and requests will be paused expecting a call to continueWithAuth.
    */
    private Boolean handleAuthRequests;



}