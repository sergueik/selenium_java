package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResponseBodyForInterceptionRequest {

    /**
    * Identifier for the intercepted request to get body for.
    */
    private String interceptionId;



}