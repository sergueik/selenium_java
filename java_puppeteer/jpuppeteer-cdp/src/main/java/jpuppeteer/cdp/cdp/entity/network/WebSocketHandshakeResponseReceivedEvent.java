package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when WebSocket handshake response becomes available.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketHandshakeResponseReceivedEvent {

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
    private jpuppeteer.cdp.cdp.entity.network.WebSocketResponse response;



}