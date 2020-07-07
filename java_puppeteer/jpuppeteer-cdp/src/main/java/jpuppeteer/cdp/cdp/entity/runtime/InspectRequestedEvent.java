package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Issued when object should be inspected (for example, as a result of inspect() command line API call).
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InspectRequestedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject object;

    /**
    */
    private java.util.Map<String, Object> hints;



}