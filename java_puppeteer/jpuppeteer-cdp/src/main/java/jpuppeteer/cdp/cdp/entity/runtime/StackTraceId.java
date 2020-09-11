package jpuppeteer.cdp.cdp.entity.runtime;

/**
* If `debuggerId` is set stack trace comes from another debugger and can be resolved there. This allows to track cross-debugger calls. See `Runtime.StackTrace` and `Debugger.paused` for usages.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StackTraceId {

    /**
    */
    private String id;

    /**
    */
    private String debuggerId;



}