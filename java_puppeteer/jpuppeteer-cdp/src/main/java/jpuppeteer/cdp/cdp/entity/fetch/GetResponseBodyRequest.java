package jpuppeteer.cdp.cdp.entity.fetch;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResponseBodyRequest {

    /**
    * Identifier for the intercepted request to get body for.
    */
    private String requestId;



}