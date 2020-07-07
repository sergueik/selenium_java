package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when WebSocket message is received.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketFrameReceivedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * WebSocket response data.
    */
    private jpuppeteer.cdp.cdp.entity.network.WebSocketFrame response;



}