package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CallFunctionOnResponse {

    /**
    * Call result.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject result;

    /**
    * Exception details.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExceptionDetails exceptionDetails;



}