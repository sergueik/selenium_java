package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Call frames for assertions or error messages.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StackTrace {

    /**
    * String label of this stack trace. For async traces this may be a name of the function that initiated the async call.
    */
    private String description;

    /**
    * JavaScript function name.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.CallFrame> callFrames;

    /**
    * Asynchronous JavaScript stack trace that preceded this stack, if available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace parent;

    /**
    * Asynchronous JavaScript stack trace that preceded this stack, if available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTraceId parentId;



}