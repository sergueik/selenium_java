package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Fired when the virtual machine stopped on breakpoint or exception or any other stop criteria.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PausedEvent {

    /**
    * Call stack the virtual machine stopped on.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.CallFrame> callFrames;

    /**
    * Pause reason.
    */
    private String reason;

    /**
    * Object containing break-specific auxiliary properties.
    */
    private java.util.Map<String, Object> data;

    /**
    * Hit breakpoints IDs
    */
    private java.util.List<String> hitBreakpoints;

    /**
    * Async stack trace, if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace asyncStackTrace;

    /**
    * Async stack trace, if any.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTraceId asyncStackTraceId;

    /**
    * Never present, will be removed.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTraceId asyncCallStackTraceId;



}