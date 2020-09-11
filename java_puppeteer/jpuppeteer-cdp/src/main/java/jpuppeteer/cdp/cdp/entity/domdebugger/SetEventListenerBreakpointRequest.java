package jpuppeteer.cdp.cdp.entity.domdebugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetEventListenerBreakpointRequest {

    /**
    * DOM Event name to stop on (any DOM event will do).
    */
    private String eventName;

    /**
    * EventTarget interface name to stop on. If equal to `"*"` or not provided, will stop on any EventTarget.
    */
    private String targetName;



}