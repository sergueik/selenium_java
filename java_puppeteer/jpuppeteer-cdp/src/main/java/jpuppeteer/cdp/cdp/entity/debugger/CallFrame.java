package jpuppeteer.cdp.cdp.entity.debugger;

/**
* JavaScript call frame. Array of call frames form the call stack.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CallFrame {

    /**
    * Call frame identifier. This identifier is only valid while the virtual machine is paused.
    */
    private String callFrameId;

    /**
    * Name of the JavaScript function called on this call frame.
    */
    private String functionName;

    /**
    * Location in the source code.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location functionLocation;

    /**
    * Location in the source code.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location location;

    /**
    * JavaScript script name or url.
    */
    private String url;

    /**
    * Scope chain for this call frame.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.Scope> scopeChain;

    /**
    * `this` object for this call frame.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject self;

    /**
    * The value being returned, if the function is at return point.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject returnValue;



}