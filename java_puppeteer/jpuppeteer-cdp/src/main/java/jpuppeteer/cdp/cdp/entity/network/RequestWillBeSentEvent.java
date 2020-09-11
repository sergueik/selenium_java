package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when page is about to send HTTP request.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestWillBeSentEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Loader identifier. Empty string if the request is fetched from worker.
    */
    private String loaderId;

    /**
    * URL of the document this request is loaded for.
    */
    private String documentURL;

    /**
    * Request data.
    */
    private jpuppeteer.cdp.cdp.entity.network.Request request;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * Timestamp.
    */
    private Double wallTime;

    /**
    * Request initiator.
    */
    private jpuppeteer.cdp.cdp.entity.network.Initiator initiator;

    /**
    * Redirect response data.
    */
    private jpuppeteer.cdp.cdp.entity.network.Response redirectResponse;

    /**
    * Type of this resource.
    */
    private String type;

    /**
    * Frame identifier.
    */
    private String frameId;

    /**
    * Whether the request is initiated by a user gesture. Defaults to false.
    */
    private Boolean hasUserGesture;



}