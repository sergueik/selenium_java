package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired for top level page lifecycle events such as navigation, load, paint, etc.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LifecycleEvent {

    /**
    * Id of the frame.
    */
    private String frameId;

    /**
    * Loader identifier. Empty string if the request is fetched from worker.
    */
    private String loaderId;

    /**
    */
    private String name;

    /**
    */
    private Double timestamp;



}