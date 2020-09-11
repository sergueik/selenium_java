package jpuppeteer.cdp.cdp.entity.network;

/**
* WebSocket request data.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketRequest {

    /**
    * HTTP request headers.
    */
    private java.util.Map<String, Object> headers;



}