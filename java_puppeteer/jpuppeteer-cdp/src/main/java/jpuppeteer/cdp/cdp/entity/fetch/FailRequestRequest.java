package jpuppeteer.cdp.cdp.entity.fetch;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FailRequestRequest {

    /**
    * An id the client received in requestPaused event.
    */
    private String requestId;

    /**
    * Causes the request to fail with the given reason.
    */
    private String errorReason;



}