package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when WebSocket message is sent.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketFrameSentEvent {

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