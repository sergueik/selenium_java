package jpuppeteer.cdp.cdp.entity.domdebugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetEventListenersResponse {

    /**
    * Array of relevant listeners.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domdebugger.EventListener> listeners;



}