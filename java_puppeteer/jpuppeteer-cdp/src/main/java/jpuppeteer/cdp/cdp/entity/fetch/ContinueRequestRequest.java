package jpuppeteer.cdp.cdp.entity.fetch;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContinueRequestRequest {

    /**
    * An id the client received in requestPaused event.
    */
    private String requestId;

    /**
    * If set, the request url will be modified in a way that's not observable by page.
    */
    private String url;

    /**
    * If set, the request method is overridden.
    */
    private String method;

    /**
    * If set, overrides the post data in the request.
    */
    private String postData;

    /**
    * If set, overrides the request headrts.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.fetch.HeaderEntry> headers;



}