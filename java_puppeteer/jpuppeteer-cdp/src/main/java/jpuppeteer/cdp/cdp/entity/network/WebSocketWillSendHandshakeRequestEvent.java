package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when WebSocket is about to initiate handshake.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketWillSendHandshakeRequestEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * UTC Timestamp.
    */
    private Double wallTime;

    /**
    * WebSocket request data.
    */
    private jpuppeteer.cdp.cdp.entity.network.WebSocketRequest request;



}