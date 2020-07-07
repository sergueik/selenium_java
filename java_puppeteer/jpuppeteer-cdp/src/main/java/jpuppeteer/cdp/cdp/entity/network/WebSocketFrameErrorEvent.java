package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when WebSocket message error occurs.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketFrameErrorEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * WebSocket error message.
    */
    private String errorMessage;



}