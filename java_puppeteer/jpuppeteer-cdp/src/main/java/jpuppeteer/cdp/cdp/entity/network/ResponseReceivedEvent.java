package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when HTTP response is available.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ResponseReceivedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Loader identifier. Empty string if the request is fetched from worker.
    */
    private String loaderId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * Resource type.
    */
    private String type;

    /**
    * Response data.
    */
    private jpuppeteer.cdp.cdp.entity.network.Response response;

    /**
    * Frame identifier.
    */
    private String frameId;



}