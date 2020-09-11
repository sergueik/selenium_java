package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RestartFrameResponse {

    /**
    * New stack trace.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.CallFrame> callFrames;

    /**
    * Async stack trace, if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace asyncStackTrace;

    /**
    * Async stack trace, if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTraceId asyncStackTraceId;



}