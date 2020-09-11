package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when WebSocket is closed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketClosedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;



}