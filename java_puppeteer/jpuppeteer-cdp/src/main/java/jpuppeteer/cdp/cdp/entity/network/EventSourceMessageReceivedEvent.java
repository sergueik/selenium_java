package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when EventSource message is received.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EventSourceMessageReceivedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * Message type.
    */
    private String eventName;

    /**
    * Message identifier.
    */
    private String eventId;

    /**
    * Message content.
    */
    private String data;



}