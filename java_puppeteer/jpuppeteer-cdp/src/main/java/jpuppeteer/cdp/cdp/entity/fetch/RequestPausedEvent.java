package jpuppeteer.cdp.cdp.entity.fetch;

/**
* Issued when the domain is enabled and the request URL matches the specified filter. The request is paused until the client responds with one of continueRequest, failRequest or fulfillRequest. The stage of the request can be determined by presence of responseErrorReason and responseStatusCode -- the request is at the response stage if either of these fields is present and in the request stage otherwise.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestPausedEvent {

    /**
    * Each request the page makes will have a unique id.
    */
    private String requestId;

    /**
    * The details of the request.
    */
    private jpuppeteer.cdp.cdp.entity.network.Request request;

    /**
    * The id of the frame that initiated the request.
    */
    private String frameId;

    /**
    * How the requested resource will be used.
    */
    private String resourceType;

    /**
    * Response error if intercepted at response stage.
    */
    private String responseErrorReason;

    /**
    * Response code if intercepted at response stage.
    */
    private Integer responseStatusCode;

    /**
    * Response headers if intercepted at the response stage.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.fetch.HeaderEntry> responseHeaders;

    /**
    * If the intercepted request had a corresponding Network.requestWillBeSent event fired for it, then this networkId will be the same as the requestId present in the requestWillBeSent event.
    */
    private String networkId;



}