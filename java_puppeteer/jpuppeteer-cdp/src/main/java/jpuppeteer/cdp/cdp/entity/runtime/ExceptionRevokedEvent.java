package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Issued when unhandled exception was revoked.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExceptionRevokedEvent {

    /**
    * Reason describing why exception was revoked.
    */
    private String reason;

    /**
    * The id of revoked exception, as reported in `exceptionThrown`.
    */
    private Integer exceptionId;



}