package jpuppeteer.cdp.cdp.entity.network;

/**
* HTTP request data.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Request {

    /**
    * Request URL (without fragment).
    */
    private String url;

    /**
    * Fragment of the requested URL starting with hash, if present.
    */
    private String urlFragment;

    /**
    * HTTP request method.
    */
    private String method;

    /**
    * HTTP request headers.
    */
    private java.util.Map<String, Object> headers;

    /**
    * HTTP POST request data.
    */
    private String postData;

    /**
    * True when the request has POST data. Note that postData might still be omitted when this flag is true when the data is too long.
    */
    private Boolean hasPostData;

    /**
    * The mixed content type of the request.
    */
    private String mixedContentType;

    /**
    * Priority of the resource request at the time request is sent.
    */
    private String initialPriority;

    /**
    * The referrer policy of the request, as defined in https://www.w3.org/TR/referrer-policy/
    */
    private String referrerPolicy;

    /**
    * Whether is loaded via link preload.
    */
    private Boolean isLinkPreload;



}