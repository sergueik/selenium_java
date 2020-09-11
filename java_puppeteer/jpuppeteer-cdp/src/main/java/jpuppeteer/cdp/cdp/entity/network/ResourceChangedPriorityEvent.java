package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when resource loading priority is changed
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ResourceChangedPriorityEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * New priority
    */
    private String newPriority;

    /**
    * Timestamp.
    */
    private Double timestamp;



}