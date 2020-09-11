package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetRequestInterceptionRequest {

    /**
    * Requests matching any of these patterns will be forwarded and wait for the corresponding continueInterceptedRequest call.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.RequestPattern> patterns;



}