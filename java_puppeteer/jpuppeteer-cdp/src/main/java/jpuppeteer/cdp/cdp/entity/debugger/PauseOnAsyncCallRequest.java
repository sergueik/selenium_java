package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PauseOnAsyncCallRequest {

    /**
    * Debugger will pause when async call with given stack trace is started.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTraceId parentStackTraceId;



}