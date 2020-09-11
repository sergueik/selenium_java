package jpuppeteer.cdp.cdp.entity.fetch;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FulfillRequestRequest {

    /**
    * An id the client received in requestPaused event.
    */
    private String requestId;

    /**
    * An HTTP response code.
    */
    private Integer responseCode;

    /**
    * Response headers.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.fetch.HeaderEntry> responseHeaders;

    /**
    * Alternative way of specifying response headers as a \0-separated series of name: value pairs. Prefer the above method unless you need to represent some non-UTF8 values that can't be transmitted over the protocol as text.
    */
    private String binaryResponseHeaders;

    /**
    * A response body.
    */
    private String body;

    /**
    * A textual representation of responseCode. If absent, a standard phrase matching responseCode is used.
    */
    private String responsePhrase;



}