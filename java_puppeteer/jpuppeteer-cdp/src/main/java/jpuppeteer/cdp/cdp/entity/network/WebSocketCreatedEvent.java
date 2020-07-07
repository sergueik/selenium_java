package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired upon WebSocket creation.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketCreatedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * WebSocket request URL.
    */
    private String url;

    /**
    * Request initiator.
    */
    private jpuppeteer.cdp.cdp.entity.network.Initiator initiator;



}