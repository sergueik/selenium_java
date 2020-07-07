package jpuppeteer.cdp.cdp.entity.domdebugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RemoveEventListenerBreakpointRequest {

    /**
    * Event name.
    */
    private String eventName;

    /**
    * EventTarget interface name.
    */
    private String targetName;



}