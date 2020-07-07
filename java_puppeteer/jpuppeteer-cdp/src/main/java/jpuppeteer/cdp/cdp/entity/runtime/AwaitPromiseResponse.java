package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AwaitPromiseResponse {

    /**
    * Promise result. Will contain rejected value if promise was rejected.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject result;

    /**
    * Exception details if stack strace is available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExceptionDetails exceptionDetails;



}