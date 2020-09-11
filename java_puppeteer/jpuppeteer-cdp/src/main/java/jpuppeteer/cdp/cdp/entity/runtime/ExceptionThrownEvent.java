package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Issued when exception was thrown and unhandled.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExceptionThrownEvent {

    /**
    * Timestamp of the exception.
    */
    private Double timestamp;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExceptionDetails exceptionDetails;



}