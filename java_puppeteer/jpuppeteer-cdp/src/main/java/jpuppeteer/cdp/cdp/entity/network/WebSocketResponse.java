package jpuppeteer.cdp.cdp.entity.network;

/**
* WebSocket response data.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketResponse {

    /**
    * HTTP response status code.
    */
    private Integer status;

    /**
    * HTTP response status text.
    */
    private String statusText;

    /**
    * HTTP response headers.
    */
    private java.util.Map<String, Object> headers;

    /**
    * HTTP response headers text.
    */
    private String headersText;

    /**
    * HTTP request headers.
    */
    private java.util.Map<String, Object> requestHeaders;

    /**
    * HTTP request headers text.
    */
    private String requestHeadersText;



}