package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetScriptSourceResponse {

    /**
    * New stack trace in case editing has happened while VM was stopped.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.CallFrame> callFrames;

    /**
    * Whether current call stack  was modified after applying the changes.
    */
    private Boolean stackChanged;

    /**
    * Async stack trace, if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace asyncStackTrace;

    /**
    * Async stack trace, if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTraceId asyncStackTraceId;

    /**
    * Exception details if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExceptionDetails exceptionDetails;



}