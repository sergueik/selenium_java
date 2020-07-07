package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when HTTP request has failed to load.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LoadingFailedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * Resource type.
    */
    private String type;

    /**
    * User friendly error message.
    */
    private String errorText;

    /**
    * True if loading was canceled.
    */
    private Boolean canceled;

    /**
    * The reason why loading was blocked, if any.
    */
    private String blockedReason;



}